#include <HardwareSerial.h>
#include <Preferences.h>
#include <TinyGPS++.h>
#include <Wire.h>
#include <WiFi.h>
#include <esp_sleep.h>
#include <esp_system.h>

namespace Config {
constexpr char kFirmwareVersion[] = "1.6.8";
constexpr char kApns[][24] = {
  "smartlte",
  "internet.globe.com.ph",
  // Common carrier-neutral defaults. Keep the known working entries first;
  // the successful index is persisted and tried first on later boots.
  "internet",
  "",
};
constexpr uint8_t kApnCount = sizeof(kApns) / sizeof(kApns[0]);
constexpr char kTrackerBaseUrl[] = "http://161.118.252.4:5055/?id=";
constexpr char kPreferencesNamespace[] = "latch";
constexpr char kTrackerIdPreferenceKey[] = "tracker_id";
constexpr size_t kTrackerIdMaxLength = 64;
constexpr uint32_t kProvisioningReminderIntervalMs = 5000;

constexpr uint32_t kSerialBaud = 115200;
constexpr uint32_t kModemBaud = 9600;
constexpr uint32_t kGpsBaud = 9600;

constexpr uint32_t kSendIntervalMs = 10000;
constexpr uint32_t kRetryIntervalMs = 10000;
constexpr uint32_t kModemStatusIntervalMs = 60000;
constexpr uint32_t kPowerStatusIntervalMs = 2000;
constexpr uint32_t kGpsFixMaxAgeMs = 15000;
constexpr uint8_t kGpsMinimumSatellites = 4;
constexpr float kGpsMaximumHdop = 3.0F;
constexpr uint32_t kGpsDataTimeoutMs = 10000;
constexpr uint32_t kWifiScanIntervalMs = 300000;
constexpr uint32_t kWifiScanRetryIntervalMs = 30000;
constexpr uint32_t kLedBlinkHalfPeriodMs = 500;
constexpr int kLowBatteryThresholdPercent = 25;
constexpr uint32_t kFullChargeGracePeriodMs = 120000;
constexpr uint8_t kLowBatteryReadingsRequired = 3;
constexpr uint8_t kFullChargeReadingsRequired = 3;
constexpr uint32_t kIdleLightSleepMs = 100;
constexpr uint32_t kModemWakeSettleMs = 150;
constexpr uint32_t kChargeModePollMs = 2000;
constexpr uint32_t kGpsPowerTransitionMs = 250;
constexpr uint32_t kModemRestartSettleMs = 12000;
constexpr uint8_t kChargeModeReadFailureLimit = 3;
constexpr uint8_t kMaxConsecutiveSendFailures = 3;
constexpr uint8_t kMaxWifiAccessPoints = 6;
constexpr bool kBalancedBatteryMode = true;
constexpr bool kChargeOnlyWhenUsbPowered = true;

constexpr int kModemTx = 27;
constexpr int kModemRx = 26;
constexpr int kModemPowerKey = 4;
constexpr int kModemPowerOn = 23;
constexpr int kModemDtr = 32;
constexpr int kGpsRx = 14;
constexpr int kGpsTx = 13;

constexpr int kPowerSda = 21;
constexpr int kPowerScl = 22;
constexpr uint8_t kIp5306Address = 0x75;
constexpr uint8_t kIp5306SystemControl0 = 0x00;
constexpr uint8_t kIp5306SystemControl1 = 0x01;
constexpr uint8_t kIp5306PowerSource = 0x70;
constexpr uint8_t kIp5306BatteryFull = 0x71;
constexpr uint8_t kIp5306BatteryLevel = 0x78;

constexpr int kRedChargingLed = 2;
constexpr int kGreenChargedLed = 18;
constexpr int kBlueGnssLed = 25;
constexpr int kYellowNetworkLed = 19;

// Change an individual value to false if that LED is wired active-low.
constexpr bool kBlueGnssLedActiveHigh = true;
constexpr bool kYellowNetworkLedActiveHigh = true;
constexpr bool kRedChargingLedActiveHigh = true;
constexpr bool kGreenChargedLedActiveHigh = true;
}  // namespace Config

enum class TelemetryResult : uint8_t {
  kSent,
  kFailed,
};

struct ModemStatus {
  bool responsive = false;
  bool registered = false;
  bool bearerOpen = false;
  int signalCsq = -1;
};

struct PowerStatus {
  bool ip5306Available = false;
  bool externalPower = false;
  bool batteryFull = false;
  int chargeState = -1;
  int batteryPercentage = -1;
  int batteryMillivolts = -1;
};

struct WifiObservation {
  String bssid;
  int32_t rssi = 0;
};

HardwareSerial sim800(1);
HardwareSerial gpsSerial(2);
TinyGPSPlus gps;
ModemStatus modemStatus;
PowerStatus powerStatus;

uint32_t nextSendAt = 0;
uint32_t nextModemStatusAt = 0;
uint32_t nextPowerStatusAt = 0;
uint32_t lastGpsByteAt = 0;
uint8_t consecutiveSendFailures = 0;
bool gpsDataSeen = false;
bool hasLastLocation = false;
bool gpsSerialStarted = false;
bool modemSerialStarted = false;
bool modemSleeping = false;
bool modemSleepSupported = true;
bool wifiScanInProgress = false;
double lastLatitude = 0;
double lastLongitude = 0;
uint32_t nextWifiScanAt = 0;
uint32_t fullChargeGraceUntil = 0;
uint8_t lowBatteryReadings = 0;
uint8_t fullChargeReadings = 0;
int batterySamples[3] = {-1, -1, -1};
uint8_t batterySampleCount = 0;
uint8_t wifiObservationCount = 0;
WifiObservation wifiObservations[Config::kMaxWifiAccessPoints];
uint8_t preferredApnIndex = 0;
String trackerUniqueId;
String serialCommandBuffer;
bool serialCommandOverflow = false;
uint32_t nextProvisioningReminderAt = 0;

void enterChargeOnlyMode();
void requestGpsSoftwareBackup();
void refreshSim800PowerFallback();
bool timeReached(uint32_t now, uint32_t deadline);

const byte kUbxRate1Hz[] = {
  0xB5, 0x62, 0x06, 0x08, 0x06, 0x00,
  0xE8, 0x03, 0x01, 0x00, 0x01, 0x00,
  0x01, 0x39
};

const byte kUbxNav5AutomotivePayload[] = {
  0xFF, 0xFF, 0x04, 0x03, 0x00, 0x00, 0x00, 0x00,
  0x10, 0x27, 0x00, 0x00, 0x05, 0x00, 0xFA, 0x00,
  0xFA, 0x00, 0x64, 0x00, 0x2C, 0x01, 0x00, 0x3C,
  0x00, 0x00, 0x00, 0x00, 0xC8, 0x00, 0x00, 0x00,
  0x00, 0x00, 0x00, 0x00
};
static_assert(
  sizeof(kUbxNav5AutomotivePayload) == 36,
  "UBX-CFG-NAV5 payload must be 36 bytes."
);

const byte kUbxSoftwareBackupPayload[] = {
  // Version and reserved bytes.
  0x00, 0x00, 0x00, 0x00,
  // Duration 0: remain inactive until a configured wake source is used.
  0x00, 0x00, 0x00, 0x00,
  // Flags: enter software backup mode.
  0x02, 0x00, 0x00, 0x00,
  // Wake sources: activity on UART RX.
  0x08, 0x00, 0x00, 0x00
};

// UBX-CFG-NAVX5, message version 2 (u-blox M8 protocol 18+).
// Apply only the AssistNow Autonomous fields, enable AOP, and retain the
// receiver firmware's default maximum modeled orbit error.
const byte kUbxAssistNowAutonomousPayload[] = {
  0x02, 0x00,              // version
  0x00, 0x40,              // mask1: apply aopCfg/aopOrbMaxErr only
  0x00, 0x00, 0x00, 0x00,  // mask2
  0x00, 0x00,              // reserved1
  0x00,                    // minSVs
  0x00,                    // maxSVs
  0x00,                    // minCNO
  0x00,                    // reserved2
  0x00,                    // iniFix3D
  0x00, 0x00,              // reserved3
  0x00,                    // ackAiding
  0x00, 0x00,              // wknRollover
  0x00,                    // sigAttenCompMode
  0x00,                    // reserved4
  0x00, 0x00,              // reserved5
  0x00, 0x00,              // reserved6
  0x00,                    // usePPP
  0x01,                    // aopCfg: useAOP
  0x00, 0x00,              // reserved7
  0x00, 0x00,              // aopOrbMaxErr: receiver default
  0x00, 0x00, 0x00, 0x00,  // reserved8
  0x00, 0x00, 0x00,        // reserved9
  0x00                     // useAdr
};
static_assert(
  sizeof(kUbxAssistNowAutonomousPayload) == 40,
  "UBX-CFG-NAVX5 version 2 payload must be 40 bytes."
);

const char* resetReasonName() {
  switch (esp_reset_reason()) {
    case ESP_RST_POWERON:
      return "power_on";
    case ESP_RST_EXT:
      return "external";
    case ESP_RST_SW:
      return "software";
    case ESP_RST_PANIC:
      return "panic";
    case ESP_RST_INT_WDT:
      return "interrupt_watchdog";
    case ESP_RST_TASK_WDT:
      return "task_watchdog";
    case ESP_RST_WDT:
      return "watchdog";
    case ESP_RST_DEEPSLEEP:
      return "deep_sleep";
    case ESP_RST_BROWNOUT:
      return "brownout";
    case ESP_RST_SDIO:
      return "sdio";
    default:
      return "unknown";
  }
}

const char* powerStateName(int chargeState) {
  switch (chargeState) {
    case 0:
      return "battery";
    case 1:
      return "charging";
    case 2:
      return "full";
    default:
      return "unknown";
  }
}

bool hasFreshGpsFix() {
  return gps.location.isValid()
    && gps.location.age() <= Config::kGpsFixMaxAgeMs
    && gps.satellites.isValid()
    && gps.satellites.value() >= Config::kGpsMinimumSatellites
    && gps.hdop.isValid()
    && gps.hdop.hdop() <= Config::kGpsMaximumHdop;
}

int smoothBatteryPercentage(int percentage) {
  if (percentage < 0 || percentage > 100) {
    return -1;
  }

  batterySamples[batterySampleCount % 3] = percentage;
  batterySampleCount++;
  const uint8_t count = min<uint8_t>(batterySampleCount, 3);
  int sorted[3];
  for (uint8_t index = 0; index < count; index++) {
    sorted[index] = batterySamples[index];
  }
  for (uint8_t index = 1; index < count; index++) {
    const int value = sorted[index];
    int previous = index;
    while (previous > 0 && sorted[previous - 1] > value) {
      sorted[previous] = sorted[previous - 1];
      previous--;
    }
    sorted[previous] = value;
  }
  return sorted[count / 2];
}

void writeLed(int pin, bool on, bool activeHigh) {
  digitalWrite(pin, on == activeHigh ? HIGH : LOW);
}

void updateStatusLeds() {
  const uint32_t now = millis();
  const bool blinkOn =
    (now / Config::kLedBlinkHalfPeriodMs) % 2 == 0;
  const bool gpsModuleResponsive = gpsDataSeen
    && now - lastGpsByteAt <= Config::kGpsDataTimeoutMs;

  // Blue: solid with a fresh fix, blinking while acquiring, off with no data.
  const bool blueOn = hasFreshGpsFix()
    || (gpsModuleResponsive && blinkOn);

  // Yellow: solid when registered, blinking while the modem is searching.
  const bool yellowOn = modemStatus.registered
    || (modemStatus.responsive && blinkOn);

  // Charging is steady red; low battery blinks red only while on battery.
  const bool lowBattery = powerStatus.chargeState == 0
    && powerStatus.batteryPercentage >= 0
    && powerStatus.batteryPercentage
      <= Config::kLowBatteryThresholdPercent
    && lowBatteryReadings >= Config::kLowBatteryReadingsRequired
    && !timeReached(now, fullChargeGraceUntil);
  const bool redOn = powerStatus.chargeState == 1
    || (lowBattery && blinkOn);
  // Power state 2 means charging has finished.
  const bool greenOn = powerStatus.chargeState == 2;

  writeLed(
    Config::kBlueGnssLed,
    blueOn,
    Config::kBlueGnssLedActiveHigh
  );
  writeLed(
    Config::kYellowNetworkLed,
    yellowOn,
    Config::kYellowNetworkLedActiveHigh
  );
  writeLed(
    Config::kRedChargingLed,
    redOn,
    Config::kRedChargingLedActiveHigh
  );
  writeLed(
    Config::kGreenChargedLed,
    greenOn,
    Config::kGreenChargedLedActiveHigh
  );
}

void configureStatusLeds() {
  pinMode(Config::kBlueGnssLed, OUTPUT);
  pinMode(Config::kYellowNetworkLed, OUTPUT);
  pinMode(Config::kRedChargingLed, OUTPUT);
  pinMode(Config::kGreenChargedLed, OUTPUT);

  writeLed(
    Config::kBlueGnssLed,
    false,
    Config::kBlueGnssLedActiveHigh
  );
  writeLed(
    Config::kYellowNetworkLed,
    false,
    Config::kYellowNetworkLedActiveHigh
  );
  writeLed(
    Config::kRedChargingLed,
    false,
    Config::kRedChargingLedActiveHigh
  );
  writeLed(
    Config::kGreenChargedLed,
    false,
    Config::kGreenChargedLedActiveHigh
  );
}

bool isValidTrackerUniqueId(const String& value) {
  if (
    value.length() == 0
    || value.length() > Config::kTrackerIdMaxLength
  ) {
    return false;
  }

  for (size_t index = 0; index < value.length(); index++) {
    const char character = value.charAt(index);
    const bool allowed =
      (character >= 'A' && character <= 'Z')
      || (character >= 'a' && character <= 'z')
      || (character >= '0' && character <= '9')
      || character == '-'
      || character == '_'
      || character == '.';
    if (!allowed) {
      return false;
    }
  }
  return true;
}

bool loadTrackerUniqueId() {
  Preferences preferences;
  if (!preferences.begin(Config::kPreferencesNamespace, true)) {
    Serial.println("Could not open tracker provisioning storage.");
    return false;
  }

  const String storedId = preferences.getString(
    Config::kTrackerIdPreferenceKey,
    ""
  );
  preferences.end();

  if (!isValidTrackerUniqueId(storedId)) {
    trackerUniqueId = "";
    return false;
  }

  trackerUniqueId = storedId;
  Serial.printf("Tracker ID: %s.\n", trackerUniqueId.c_str());
  return true;
}

bool saveTrackerUniqueId(const String& value) {
  if (!isValidTrackerUniqueId(value)) {
    return false;
  }

  Preferences preferences;
  if (!preferences.begin(Config::kPreferencesNamespace, false)) {
    return false;
  }
  const size_t written = preferences.putString(
    Config::kTrackerIdPreferenceKey,
    value
  );
  preferences.end();

  if (written != value.length()) {
    return false;
  }
  trackerUniqueId = value;
  return true;
}

void processSerialCommand(String command) {
  command.trim();
  if (command.length() == 0) {
    return;
  }

  if (command == "GET_TRACKER_ID") {
    if (trackerUniqueId.length() == 0) {
      Serial.println("Tracker ID: <not provisioned>.");
    } else {
      Serial.printf("Tracker ID: %s.\n", trackerUniqueId.c_str());
    }
    return;
  }

  const String prefix = "SET_TRACKER_ID=";
  if (!command.startsWith(prefix)) {
    Serial.println("Unknown provisioning command.");
    return;
  }

  String requestedId = command.substring(prefix.length());
  requestedId.trim();
  if (!isValidTrackerUniqueId(requestedId)) {
    Serial.println(
      "Invalid tracker ID. Use 1-64 letters, numbers, dots, dashes, or underscores."
    );
    return;
  }

  if (!saveTrackerUniqueId(requestedId)) {
    Serial.println("Could not save tracker ID.");
    return;
  }

  Serial.printf("Tracker ID saved: %s.\n", trackerUniqueId.c_str());
  Serial.println("Restarting with the provisioned identity.");
  Serial.flush();
  delay(250);
  ESP.restart();
}

void serviceSerialCommands() {
  while (Serial.available()) {
    const char character = static_cast<char>(Serial.read());
    if (character == '\r') {
      continue;
    }
    if (character == '\n') {
      if (serialCommandOverflow) {
        Serial.println("Provisioning command was too long.");
      } else {
        processSerialCommand(serialCommandBuffer);
      }
      serialCommandBuffer = "";
      serialCommandOverflow = false;
      continue;
    }

    if (serialCommandOverflow) {
      continue;
    }
    if (
      serialCommandBuffer.length()
      >= Config::kTrackerIdMaxLength + 32
    ) {
      serialCommandOverflow = true;
      continue;
    }
    serialCommandBuffer += character;
  }
}

void waitForTrackerProvisioning() {
  if (trackerUniqueId.length() > 0) {
    return;
  }

  Serial.println("Device not provisioned; telemetry is disabled.");
  Serial.println("Send SET_TRACKER_ID=<id> over USB serial.");
  nextProvisioningReminderAt =
    millis() + Config::kProvisioningReminderIntervalMs;

  while (trackerUniqueId.length() == 0) {
    serviceSerialCommands();
    const uint32_t now = millis();
    if (static_cast<int32_t>(now - nextProvisioningReminderAt) >= 0) {
      Serial.println("Waiting for tracker ID provisioning.");
      nextProvisioningReminderAt =
        now + Config::kProvisioningReminderIntervalMs;
    }
    updateStatusLeds();
    delay(10);
  }
}

void drainGpsInput() {
  if (!gpsSerialStarted) {
    return;
  }

  while (gpsSerial.available()) {
    const char value = static_cast<char>(gpsSerial.read());
    gpsDataSeen = true;
    lastGpsByteAt = millis();

    if (
      gps.encode(value)
      && gps.location.isValid()
      && gps.location.isUpdated()
    ) {
      lastLatitude = gps.location.lat();
      lastLongitude = gps.location.lng();
      hasLastLocation = true;
    }
  }
}

void finishWifiScan(int networkCount) {
  wifiObservationCount = 0;

  for (
    int networkIndex = 0;
    networkIndex < networkCount
      && wifiObservationCount < Config::kMaxWifiAccessPoints;
    networkIndex++
  ) {
    const String bssid = WiFi.BSSIDstr(networkIndex);
    if (bssid.length() != 17) {
      continue;
    }

    wifiObservations[wifiObservationCount].bssid = bssid;
    wifiObservations[wifiObservationCount].rssi = WiFi.RSSI(networkIndex);
    wifiObservationCount++;
  }

  WiFi.scanDelete();
  WiFi.mode(WIFI_OFF);
  wifiScanInProgress = false;
  nextWifiScanAt = millis()
    + (wifiObservationCount >= 2
      ? Config::kWifiScanIntervalMs
      : Config::kWifiScanRetryIntervalMs);
  Serial.printf(
    "Wi-Fi assistance scan captured %u access point(s).\n",
    wifiObservationCount
  );
}

void serviceWifiScan() {
  if (!wifiScanInProgress) {
    return;
  }

  const int result = WiFi.scanComplete();
  if (result >= 0) {
    finishWifiScan(result);
  } else if (result == WIFI_SCAN_FAILED) {
    WiFi.scanDelete();
    WiFi.mode(WIFI_OFF);
    wifiScanInProgress = false;
    nextWifiScanAt = millis() + Config::kWifiScanRetryIntervalMs;
    Serial.println("Wi-Fi assistance scan failed; GNSS remains primary.");
  }
}

void startWifiScanIfNeeded() {
  if (
    wifiScanInProgress
    || hasFreshGpsFix()
    || !timeReached(millis(), nextWifiScanAt)
  ) {
    return;
  }

  WiFi.mode(WIFI_STA);
  WiFi.disconnect(false, true);
  const int result = WiFi.scanNetworks(true, true);
  if (result == WIFI_SCAN_FAILED) {
    WiFi.mode(WIFI_OFF);
    nextWifiScanAt = millis() + Config::kWifiScanRetryIntervalMs;
    Serial.println("Could not start Wi-Fi assistance scan.");
    return;
  }

  wifiScanInProgress = true;
  Serial.println("Scanning nearby Wi-Fi for location assistance.");
}

void serviceRuntime() {
  serviceSerialCommands();
  drainGpsInput();
  serviceWifiScan();
  updateStatusLeds();
}

void waitWithServices(uint32_t durationMs) {
  const uint32_t startedAt = millis();
  while (millis() - startedAt < durationMs) {
    serviceRuntime();
    delay(10);
  }
}

void clearModemInput() {
  while (sim800.available()) {
    sim800.read();
  }
}

String sendAt(
  const String& command,
  uint32_t timeoutMs,
  const String& completionToken = "OK"
) {
  clearModemInput();
  sim800.println(command);

  String response;
  response.reserve(256);
  const uint32_t startedAt = millis();
  while (millis() - startedAt < timeoutMs) {
    serviceRuntime();
    while (sim800.available()) {
      response += static_cast<char>(sim800.read());
    }

    if (
      response.indexOf(completionToken) >= 0
      || response.indexOf("ERROR") >= 0
    ) {
      break;
    }
    delay(10);
  }

  Serial.print("AT> ");
  Serial.println(command);
  Serial.println(response);
  return response;
}

bool wakeModem() {
  if (!modemSerialStarted) {
    return false;
  }
  if (!modemSleeping) {
    return true;
  }

  digitalWrite(Config::kModemDtr, LOW);
  modemSleeping = false;
  waitWithServices(Config::kModemWakeSettleMs);

  String response = sendAt("AT", 2000);
  if (response.indexOf("OK") < 0) {
    waitWithServices(500);
    response = sendAt("AT", 2000);
  }
  if (response.indexOf("OK") < 0) {
    modemStatus.responsive = false;
    return false;
  }

  sendAt("AT+CSCLK=0", 2000);
  modemStatus.responsive = true;
  return true;
}

bool sleepModem() {
  if (
    !Config::kBalancedBatteryMode
    || !modemSerialStarted
    || modemSleeping
    || !modemSleepSupported
    || !modemStatus.responsive
  ) {
    return modemSleeping;
  }

  if (sendAt("AT+CSCLK=1", 2000).indexOf("OK") < 0) {
    modemSleepSupported = false;
    Serial.println("SIM800 sleep mode is unavailable; continuing safely.");
    return false;
  }

  digitalWrite(Config::kModemDtr, HIGH);
  modemSleeping = true;
  return true;
}

void idleLightSleep() {
  if (!Config::kBalancedBatteryMode) {
    delay(5);
    return;
  }

  esp_sleep_enable_timer_wakeup(
    static_cast<uint64_t>(Config::kIdleLightSleepMs) * 1000ULL
  );
  esp_light_sleep_start();
}

int parseIntegerBetween(
  const String& response,
  const String& prefix,
  int valueIndex
) {
  const int prefixIndex = response.indexOf(prefix);
  if (prefixIndex < 0) {
    return -1;
  }

  int start = prefixIndex + prefix.length();
  for (int index = 0; index < valueIndex; index++) {
    start = response.indexOf(',', start);
    if (start < 0) {
      return -1;
    }
    start++;
  }

  int end = response.indexOf(',', start);
  const int carriageReturn = response.indexOf('\r', start);
  const int lineFeed = response.indexOf('\n', start);
  if (end < 0 || (carriageReturn >= 0 && carriageReturn < end)) {
    end = carriageReturn;
  }
  if (end < 0 || (lineFeed >= 0 && lineFeed < end)) {
    end = lineFeed;
  }
  if (end < 0) {
    end = response.length();
  }

  String value = response.substring(start, end);
  value.trim();
  if (value.length() == 0) {
    return -1;
  }
  return value.toInt();
}

bool isRegisteredStatus(int status) {
  return status == 1 || status == 5;
}

int registrationStatus(
  const String& response,
  const String& prefix
) {
  int status = parseIntegerBetween(response, prefix, 1);
  if (status < 0) {
    status = parseIntegerBetween(response, prefix, 0);
  }
  return status;
}

bool responseShowsModem(
  const String& response,
  const String& expectedPrefix
) {
  return response.indexOf("OK") >= 0
    || response.indexOf("ERROR") >= 0
    || response.indexOf(expectedPrefix) >= 0;
}

bool refreshNetworkRegistration() {
  const String circuitResponse = sendAt("AT+CREG?", 2000);
  bool responsive = responseShowsModem(circuitResponse, "+CREG:");
  bool registered = isRegisteredStatus(
    registrationStatus(circuitResponse, "+CREG:")
  );

  if (!registered) {
    const String packetResponse = sendAt("AT+CGREG?", 2000);
    responsive = responsive
      || responseShowsModem(packetResponse, "+CGREG:");
    registered = isRegisteredStatus(
      registrationStatus(packetResponse, "+CGREG:")
    );
  }

  modemStatus.responsive = responsive;
  modemStatus.registered = registered;
  if (!registered) {
    modemStatus.bearerOpen = false;
  }
  return registered;
}

void refreshSignalQuality() {
  const String response = sendAt("AT+CSQ", 2000);
  const int csq = parseIntegerBetween(response, "+CSQ:", 0);
  modemStatus.signalCsq = csq >= 0 && csq <= 31 ? csq : -1;
}

int readIp5306Register(uint8_t registerAddress) {
  Wire.beginTransmission(Config::kIp5306Address);
  Wire.write(registerAddress);
  if (Wire.endTransmission(false) != 0) {
    return -1;
  }

  if (
    Wire.requestFrom(
      Config::kIp5306Address,
      static_cast<size_t>(1)
    ) != 1
  ) {
    return -1;
  }
  return Wire.read();
}

bool configurePowerManagement() {
  const int control0 = readIp5306Register(
    Config::kIp5306SystemControl0
  );
  const int control1 = readIp5306Register(
    Config::kIp5306SystemControl1
  );
  if (control0 < 0 || control1 < 0) {
    Serial.println("IP5306 power controller was not detected.");
    return false;
  }

  // This T-Call revision already ships with a working physical-button
  // configuration. Do not rewrite SYS_CTL0/SYS_CTL1: forcing boost output on
  // during every ESP32 boot can immediately revive the board after the
  // IP5306 has handled its shutdown gesture.
  Serial.printf(
    "IP5306 detected; preserving factory button settings "
    "(SYS_CTL0=0x%02X, SYS_CTL1=0x%02X).\n",
    control0,
    control1
  );
  return true;
}

int batteryPercentageFromIp5306(int levelRegister) {
  const uint8_t levelBits =
    (static_cast<uint8_t>(levelRegister) >> 4) & 0x0F;
  switch (levelBits) {
    case 0x0F:
      return 100;
    case 0x07:
      return 75;
    case 0x03:
      return 50;
    case 0x01:
      return 25;
    default:
      return -1;
  }
}

bool refreshIp5306PowerStatus() {
  const bool wasExternalPower = powerStatus.externalPower;
  const int source = readIp5306Register(Config::kIp5306PowerSource);
  const int full = readIp5306Register(Config::kIp5306BatteryFull);
  const int level = readIp5306Register(Config::kIp5306BatteryLevel);
  if (source < 0 || full < 0 || level < 0) {
    return false;
  }

  const bool rawBatteryFull = (full & 0x08) != 0;
  const bool rawExternalPower = (source & 0x08) != 0;
  if (rawExternalPower && rawBatteryFull) {
    if (fullChargeReadings < Config::kFullChargeReadingsRequired) {
      fullChargeReadings++;
    }
  } else {
    fullChargeReadings = 0;
  }

  powerStatus.ip5306Available = true;
  powerStatus.externalPower = rawExternalPower;
  powerStatus.batteryFull = powerStatus.externalPower
    && fullChargeReadings >= Config::kFullChargeReadingsRequired;
  // IP5306 reports stable coarse levels. Use the modem estimate only when its
  // level register is unavailable, rather than exposing noisy pseudo-precision.
  powerStatus.batteryPercentage = powerStatus.externalPower
    ? (powerStatus.batteryFull ? 100 : batteryPercentageFromIp5306(level))
    : batteryPercentageFromIp5306(level);
  powerStatus.batteryMillivolts = -1;
  powerStatus.chargeState = powerStatus.externalPower
    ? (powerStatus.batteryFull ? 2 : 1)
    : 0;
  if (powerStatus.externalPower && powerStatus.batteryFull) {
    fullChargeGraceUntil = millis() + Config::kFullChargeGracePeriodMs;
    lowBatteryReadings = 0;
  } else if (powerStatus.externalPower || powerStatus.batteryPercentage
      > Config::kLowBatteryThresholdPercent) {
    lowBatteryReadings = 0;
  } else if (!wasExternalPower || powerStatus.chargeState == 0) {
    if (lowBatteryReadings < Config::kLowBatteryReadingsRequired) {
      lowBatteryReadings++;
    }
  }
  Serial.printf(
    "IP5306 raw: source=0x%02X full=0x%02X level=0x%02X; battery=%d%%.\n",
    source,
    full,
    level,
    powerStatus.batteryPercentage
  );
  return true;
}

void refreshSim800BatteryFallbackForIp5306() {
  if (!modemStatus.responsive || modemSleeping) {
    return;
  }

  const bool ip5306ExternalPower = powerStatus.externalPower;
  const bool ip5306BatteryFull = powerStatus.batteryFull;
  const int ip5306ChargeState = powerStatus.chargeState;
  refreshSim800PowerFallback();
  if (powerStatus.batteryPercentage < 0) {
    return;
  }

  powerStatus.ip5306Available = true;
  powerStatus.externalPower = ip5306ExternalPower;
  powerStatus.batteryFull = ip5306BatteryFull;
  powerStatus.chargeState = ip5306ChargeState;
}

void refreshSim800PowerFallback() {
  const String response = sendAt("AT+CBC", 2000);
  const int chargeState = parseIntegerBetween(response, "+CBC:", 0);
  const int percentage = parseIntegerBetween(response, "+CBC:", 1);
  const int millivolts = parseIntegerBetween(response, "+CBC:", 2);

  powerStatus.ip5306Available = false;
  powerStatus.chargeState =
    chargeState >= 0 && chargeState <= 3 ? chargeState : -1;
  powerStatus.externalPower = chargeState == 1 || chargeState == 2;
  powerStatus.batteryFull = chargeState == 2;
  powerStatus.batteryPercentage =
    smoothBatteryPercentage(percentage);
  powerStatus.batteryMillivolts =
    millivolts > 0 ? millivolts : -1;
}

void refreshPowerStatus() {
  if (refreshIp5306PowerStatus()) {
    if (powerStatus.batteryPercentage < 0) {
      refreshSim800BatteryFallbackForIp5306();
    }
    return;
  }

  powerStatus = PowerStatus{};
  if (modemSleeping) {
    wakeModem();
  }
  if (modemStatus.responsive) {
    refreshSim800PowerFallback();
  }
}

void powerDownTrackingForChargeMode() {
  if (gpsSerialStarted) {
    requestGpsSoftwareBackup();
    gpsSerial.end();
    gpsSerialStarted = false;
    gpsDataSeen = false;
    lastGpsByteAt = 0;
  }

  if (modemSerialStarted) {
    if (modemStatus.responsive) {
      wakeModem();
      sendAt("AT+HTTPTERM", 2000);
      sendAt("AT+CPOWD=1", 5000, "NORMAL POWER DOWN");
    }
    sim800.end();
    modemSerialStarted = false;
    modemSleeping = false;
  }

  pinMode(Config::kModemPowerKey, OUTPUT);
  digitalWrite(Config::kModemPowerKey, HIGH);
  pinMode(Config::kModemDtr, OUTPUT);
  digitalWrite(Config::kModemDtr, LOW);
  pinMode(Config::kModemPowerOn, OUTPUT);
  digitalWrite(Config::kModemPowerOn, LOW);
  modemStatus = ModemStatus{};
}

void enterChargeOnlyMode() {
  if (!Config::kChargeOnlyWhenUsbPowered) {
    return;
  }

  Serial.println("USB power detected. Entering charge-only mode.");
  powerDownTrackingForChargeMode();
  updateStatusLeds();

  uint8_t powerStatusReadFailures = 0;
  while (true) {
    serviceSerialCommands();
    esp_sleep_enable_timer_wakeup(
      static_cast<uint64_t>(Config::kChargeModePollMs) * 1000ULL
    );
    Serial.flush();
    esp_light_sleep_start();
    delay(10);

    const bool powerStatusRead = refreshIp5306PowerStatus();
    if (powerStatusRead) {
      powerStatusReadFailures = 0;
    } else {
      powerStatusReadFailures++;
      Serial.printf(
        "IP5306 status read failed (%u/%u).\n",
        powerStatusReadFailures,
        Config::kChargeModeReadFailureLimit
      );
    }

    if (
      (powerStatusRead && !powerStatus.externalPower)
      || powerStatusReadFailures >= Config::kChargeModeReadFailureLimit
    ) {
      break;
    }
    updateStatusLeds();
  }

  updateStatusLeds();
  Serial.println("USB removed. Starting normal tracking mode.");
  Serial.flush();
  delay(100);
  ESP.restart();

  while (true) {
    delay(1000);
  }
}

bool refreshBearerStatus() {
  const String response = sendAt("AT+SAPBR=2,1", 3000);
  modemStatus.bearerOpen = response.indexOf("+SAPBR: 1,1,") >= 0
    && response.indexOf("0.0.0.0") < 0;
  return modemStatus.bearerOpen;
}

void refreshModemStatus() {
  wakeModem();
  const String response = sendAt("AT", 2000);
  if (response.indexOf("OK") < 0) {
    modemStatus = ModemStatus{};
    return;
  }

  modemStatus.responsive = true;
  refreshNetworkRegistration();
  refreshSignalQuality();
  refreshBearerStatus();
}

void loadPreferredApn() {
  Preferences preferences;
  if (!preferences.begin("latch", true)) {
    return;
  }
  preferredApnIndex = preferences.getUChar("apn", 0);
  preferences.end();
  if (preferredApnIndex >= Config::kApnCount) {
    preferredApnIndex = 0;
  }
}

void savePreferredApn(uint8_t apnIndex) {
  if (apnIndex == preferredApnIndex) {
    return;
  }

  Preferences preferences;
  if (!preferences.begin("latch", false)) {
    return;
  }
  preferences.putUChar("apn", apnIndex);
  preferences.end();
  preferredApnIndex = apnIndex;
}

bool openBearerWithApn(uint8_t apnIndex) {
  sendAt("AT+SAPBR=0,1", 3000);
  const String apnCommand =
    String("AT+SAPBR=3,1,\"APN\",\"")
    + Config::kApns[apnIndex]
    + "\"";
  if (sendAt(apnCommand, 3000).indexOf("OK") < 0) {
    return false;
  }

  Serial.printf("Trying mobile-data APN: %s.\n", Config::kApns[apnIndex]);
  sendAt("AT+SAPBR=1,1", 30000);
  if (!refreshBearerStatus()) {
    return false;
  }

  savePreferredApn(apnIndex);
  Serial.printf("Mobile data connected using %s.\n", Config::kApns[apnIndex]);
  return true;
}

bool ensureBearer() {
  if (!wakeModem()) {
    return false;
  }

  if (!refreshNetworkRegistration()) {
    Serial.println("GSM network is not registered.");
    return false;
  }

  if (refreshBearerStatus()) {
    return true;
  }

  if (
    sendAt("AT+SAPBR=3,1,\"CONTYPE\",\"GPRS\"", 3000)
      .indexOf("OK") < 0
  ) {
    return false;
  }

  if (sendAt("AT+CGATT=1", 10000).indexOf("OK") < 0) {
    return false;
  }

  for (uint8_t offset = 0; offset < Config::kApnCount; offset++) {
    const uint8_t apnIndex =
      (preferredApnIndex + offset) % Config::kApnCount;
    if (openBearerWithApn(apnIndex)) {
      return true;
    }
  }

  Serial.println("No configured APN could open mobile data.");
  return false;
}

void sendUbx(const byte* message, size_t length) {
  for (size_t index = 0; index < length; index++) {
    gpsSerial.write(message[index]);
  }
}

void sendUbxPacket(
  byte messageClass,
  byte messageId,
  const byte* payload,
  uint16_t payloadLength
) {
  gpsSerial.write(0xB5);
  gpsSerial.write(0x62);

  byte checksumA = 0;
  byte checksumB = 0;
  const auto writeChecked = [&](byte value) {
    gpsSerial.write(value);
    checksumA = static_cast<byte>(checksumA + value);
    checksumB = static_cast<byte>(checksumB + checksumA);
  };

  writeChecked(messageClass);
  writeChecked(messageId);
  writeChecked(static_cast<byte>(payloadLength & 0xFF));
  writeChecked(static_cast<byte>(payloadLength >> 8));
  for (uint16_t index = 0; index < payloadLength; index++) {
    writeChecked(payload[index]);
  }
  gpsSerial.write(checksumA);
  gpsSerial.write(checksumB);
  gpsSerial.flush();
}

void requestGpsSoftwareBackup() {
  if (!gpsSerialStarted) {
    return;
  }

  Serial.println("Putting NEO-M8N into software backup mode.");
  sendUbxPacket(
    0x02,
    0x41,
    kUbxSoftwareBackupPayload,
    sizeof(kUbxSoftwareBackupPayload)
  );
  waitWithServices(Config::kGpsPowerTransitionMs);
}

void wakeGpsReceiver() {
  if (!gpsSerialStarted) {
    return;
  }

  // UART RX activity is the configured wake source. The dummy byte may be
  // consumed during wake-up, so configuration packets follow after a delay.
  gpsSerial.write(0xFF);
  gpsSerial.flush();
  waitWithServices(Config::kGpsPowerTransitionMs);
}

void configureGps() {
  Serial.println("Configuring NEO-M8N.");
  sendUbx(kUbxRate1Hz, sizeof(kUbxRate1Hz));
  waitWithServices(500);
  sendUbxPacket(
    0x06,
    0x24,
    kUbxNav5AutomotivePayload,
    sizeof(kUbxNav5AutomotivePayload)
  );
  waitWithServices(500);
  sendUbxPacket(
    0x06,
    0x23,
    kUbxAssistNowAutonomousPayload,
    sizeof(kUbxAssistNowAutonomousPayload)
  );
  Serial.println(
    "Token-free AssistNow Autonomous requested; learned orbit data and "
    "backup state will aid later starts."
  );
  waitWithServices(500);
}

bool httpGet(const String& url) {
  sendAt("AT+HTTPTERM", 2000);
  if (sendAt("AT+HTTPINIT", 3000).indexOf("OK") < 0) {
    return false;
  }
  if (sendAt("AT+HTTPPARA=\"CID\",1", 2000).indexOf("OK") < 0) {
    sendAt("AT+HTTPTERM", 2000);
    return false;
  }
  if (
    sendAt("AT+HTTPPARA=\"URL\",\"" + url + "\"", 5000)
      .indexOf("OK") < 0
  ) {
    sendAt("AT+HTTPTERM", 2000);
    return false;
  }

  const String response = sendAt(
    "AT+HTTPACTION=0",
    30000,
    "+HTTPACTION:"
  );
  sendAt("AT+HTTPTERM", 2000);

  const int status = parseIntegerBetween(response, "+HTTPACTION:", 1);
  return status >= 200 && status < 300;
}

bool isFreshGpsValue(bool valid, uint32_t age) {
  return valid && age <= Config::kGpsFixMaxAgeMs;
}

String buildTelemetryUrl(bool hasFix) {
  String url;
  url.reserve(384);
  url = Config::kTrackerBaseUrl;
  url += trackerUniqueId;
  url += "&valid=";
  url += hasFix ? "true" : "false";

  if (hasLastLocation) {
    url += "&lat=";
    url += String(lastLatitude, 6);
    url += "&lon=";
    url += String(lastLongitude, 6);
  } else {
    // OsmAnd accepts a position without coordinates and marks it outdated,
    // allowing Traccar to retain the device's last real server location.
    // This lets battery/network/GNSS state arrive before the first local fix
    // without ever fabricating a 0,0 location.
    url += "&statusOnly=true";
  }

  if (hasFix && isFreshGpsValue(gps.speed.isValid(), gps.speed.age())) {
    // The Traccar OsmAnd protocol uses knots by default.
    url += "&speed=";
    url += String(gps.speed.knots(), 2);
  }
  if (
    hasFix
    && isFreshGpsValue(gps.altitude.isValid(), gps.altitude.age())
  ) {
    url += "&altitude=";
    url += String(gps.altitude.meters(), 2);
  }
  if (hasFix && isFreshGpsValue(gps.course.isValid(), gps.course.age())) {
    url += "&bearing=";
    url += String(gps.course.deg(), 2);
  }

  if (!hasFix && wifiObservationCount >= 2) {
    for (uint8_t index = 0; index < wifiObservationCount; index++) {
      url += "&wifi=";
      url += wifiObservations[index].bssid;
      url += ",";
      url += String(wifiObservations[index].rssi);
    }
    url += "&locationSource=wifi";
  }
  if (
    isFreshGpsValue(gps.satellites.isValid(), gps.satellites.age())
  ) {
    url += "&sat=";
    url += String(gps.satellites.value());
  }
  if (isFreshGpsValue(gps.hdop.isValid(), gps.hdop.age())) {
    url += "&hdop=";
    url += String(gps.hdop.hdop(), 2);
  }

  if (modemStatus.signalCsq >= 0) {
    url += "&csq=";
    url += String(modemStatus.signalCsq);
  }
  if (powerStatus.batteryPercentage >= 0) {
    url += "&batt=";
    url += String(powerStatus.batteryPercentage);
  }
  if (powerStatus.chargeState >= 0) {
    url += "&charge=";
    url += powerStatus.chargeState == 1 ? "true" : "false";
    url += "&powerState=";
    url += powerStateName(powerStatus.chargeState);
  }
  if (powerStatus.batteryMillivolts > 0) {
    url += "&batteryVoltage=";
    url += String(powerStatus.batteryMillivolts / 1000.0F, 3);
  }

  url += "&uptime=";
  url += String(millis() / 1000);
  url += "&firmware=";
  url += Config::kFirmwareVersion;
  url += "&resetReason=";
  url += resetReasonName();
  return url;
}

TelemetryResult sendTelemetry() {
  if (!isValidTrackerUniqueId(trackerUniqueId)) {
    Serial.println("Telemetry disabled: device is not provisioned.");
    return TelemetryResult::kFailed;
  }

  bool freshFix = hasFreshGpsFix();
  if (freshFix) {
    lastLatitude = gps.location.lat();
    lastLongitude = gps.location.lng();
    hasLastLocation = true;
  }

  if (!ensureBearer()) {
    Serial.println("Telemetry failed: GPRS bearer is unavailable.");
    return TelemetryResult::kFailed;
  }

  refreshSignalQuality();
  refreshPowerStatus();

  // Bearer setup and modem queries can take several seconds. Re-evaluate
  // immediately before building the request so a fix acquired during that
  // work is not incorrectly uploaded as valid=false.
  freshFix = hasFreshGpsFix();
  if (freshFix) {
    lastLatitude = gps.location.lat();
    lastLongitude = gps.location.lng();
    hasLastLocation = true;
  }

  const String url = buildTelemetryUrl(freshFix);
  if (!hasLastLocation) {
    Serial.println(
      "No GPS fix yet; sending device-status heartbeat without coordinates."
    );
  }
  Serial.println("Sending telemetry:");
  Serial.println(url);

  if (httpGet(url)) {
    modemStatus.bearerOpen = true;
    Serial.println("Telemetry accepted by Traccar.");
    return TelemetryResult::kSent;
  }

  Serial.println("Telemetry failed; the bearer will be rebuilt.");
  sendAt("AT+SAPBR=0,1", 5000);
  modemStatus.bearerOpen = false;
  return TelemetryResult::kFailed;
}

void restartModem() {
  Serial.println("Restarting SIM800 after repeated network failures.");
  wakeModem();
  sendAt("AT+HTTPTERM", 2000);
  sendAt("AT+SAPBR=0,1", 5000);
  sendAt("AT+CFUN=1,1", 5000);
  modemStatus = ModemStatus{};
  modemSleeping = false;
  modemSleepSupported = true;
  digitalWrite(Config::kModemDtr, LOW);
  waitWithServices(Config::kModemRestartSettleMs);
  sendAt("AT", 2000);
  sendAt("ATE0", 2000);
  sendAt("AT+CNETLIGHT=0", 2000);
  refreshModemStatus();
}

void powerOnModem() {
  pinMode(Config::kModemDtr, OUTPUT);
  digitalWrite(Config::kModemDtr, LOW);
  modemSleeping = false;
  modemSleepSupported = true;

  pinMode(Config::kModemPowerOn, OUTPUT);
  digitalWrite(Config::kModemPowerOn, HIGH);

  pinMode(Config::kModemPowerKey, OUTPUT);
  digitalWrite(Config::kModemPowerKey, HIGH);
  waitWithServices(100);
  digitalWrite(Config::kModemPowerKey, LOW);
  waitWithServices(1000);
  digitalWrite(Config::kModemPowerKey, HIGH);
}

bool timeReached(uint32_t now, uint32_t deadline) {
  return deadline == 0
    || static_cast<int32_t>(now - deadline) >= 0;
}

void setup() {
  configureStatusLeds();
  Serial.begin(Config::kSerialBaud);
  Serial.println();
  Serial.println("Starting LATCH tracker.");
  Serial.printf(
    "Firmware %s; reset reason: %s.\n",
    Config::kFirmwareVersion,
    resetReasonName()
  );
  loadTrackerUniqueId();
  waitForTrackerProvisioning();
  loadPreferredApn();

  Wire.begin(Config::kPowerSda, Config::kPowerScl, 400000);
  configurePowerManagement();

  gpsSerial.begin(
    Config::kGpsBaud,
    SERIAL_8N1,
    Config::kGpsRx,
    Config::kGpsTx
  );
  gpsSerialStarted = true;

  refreshIp5306PowerStatus();
  if (powerStatus.externalPower) {
    enterChargeOnlyMode();
  }

  wakeGpsReceiver();
  waitWithServices(1000);
  configureGps();

  powerOnModem();
  sim800.begin(
    Config::kModemBaud,
    SERIAL_8N1,
    Config::kModemRx,
    Config::kModemTx
  );
  modemSerialStarted = true;
  waitWithServices(5000);

  sendAt("AT", 2000);
  sendAt("ATE0", 2000);
  sendAt("AT+CNETLIGHT=0", 2000);
  refreshModemStatus();
  refreshPowerStatus();

  nextModemStatusAt = millis() + Config::kModemStatusIntervalMs;
  nextPowerStatusAt = millis() + Config::kPowerStatusIntervalMs;
  nextWifiScanAt = millis();
  startWifiScanIfNeeded();

  Serial.println(
    "Sending startup status now; a GNSS fix is not required."
  );
  const TelemetryResult startupResult = sendTelemetry();
  if (startupResult == TelemetryResult::kSent) {
    consecutiveSendFailures = 0;
    nextSendAt = millis() + Config::kSendIntervalMs;
  } else {
    consecutiveSendFailures = 1;
    nextSendAt = millis() + Config::kRetryIntervalMs;
  }
  Serial.println("LATCH tracker is ready.");
}

void loop() {
  serviceRuntime();
  startWifiScanIfNeeded();

  uint32_t now = millis();
  if (timeReached(now, nextPowerStatusAt)) {
    refreshPowerStatus();
    if (powerStatus.externalPower) {
      if (modemSerialStarted) {
        Serial.println("Reporting USB charging state before charge-only mode.");
        sendTelemetry();
      }
      enterChargeOnlyMode();
    }
    nextPowerStatusAt = millis() + Config::kPowerStatusIntervalMs;
  }

  now = millis();
  if (timeReached(now, nextModemStatusAt)) {
    refreshModemStatus();
    nextModemStatusAt = millis() + Config::kModemStatusIntervalMs;
  }

  now = millis();
  if (!timeReached(now, nextSendAt)) {
    sleepModem();
    idleLightSleep();
    return;
  }

  const TelemetryResult result = sendTelemetry();
  if (result == TelemetryResult::kSent) {
    consecutiveSendFailures = 0;
    nextSendAt = millis() + Config::kSendIntervalMs;
    sleepModem();
    return;
  }

  if (result == TelemetryResult::kFailed) {
    consecutiveSendFailures++;
    if (
      consecutiveSendFailures >= Config::kMaxConsecutiveSendFailures
    ) {
      restartModem();
      consecutiveSendFailures = 0;
    }
  }

  nextSendAt = millis() + Config::kRetryIntervalMs;
  sleepModem();
}
