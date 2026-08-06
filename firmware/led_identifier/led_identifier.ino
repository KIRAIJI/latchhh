namespace Config {
constexpr uint32_t kSerialBaud = 115200;
constexpr uint32_t kBlinkOnMs = 300;
constexpr uint32_t kBlinkOffMs = 300;
constexpr uint32_t kSolidOnMs = 2500;
constexpr uint32_t kBetweenPinsMs = 1200;
constexpr uint32_t kBetweenRoundsMs = 3000;
constexpr bool kLedActiveHigh = true;
}  // namespace Config

struct LedUnderTest {
  int pin;
  uint8_t blinkCount;
};

constexpr LedUnderTest kLeds[] = {
  {25, 1},
  {18, 2},
  {19, 3},
  {2, 4},
};

void writeLed(int pin, bool on) {
  digitalWrite(
    pin,
    on == Config::kLedActiveHigh ? HIGH : LOW
  );
}

void turnAllOff() {
  for (const LedUnderTest& led : kLeds) {
    writeLed(led.pin, false);
  }
}

void testLed(const LedUnderTest& led) {
  Serial.print("Testing GPIO");
  Serial.print(led.pin);
  Serial.print(" with ");
  Serial.print(led.blinkCount);
  Serial.println(" identifying blink(s), then solid light.");

  for (uint8_t count = 0; count < led.blinkCount; count++) {
    writeLed(led.pin, true);
    delay(Config::kBlinkOnMs);
    writeLed(led.pin, false);
    delay(Config::kBlinkOffMs);
  }

  writeLed(led.pin, true);
  delay(Config::kSolidOnMs);
  writeLed(led.pin, false);
  delay(Config::kBetweenPinsMs);
}

void setup() {
  Serial.begin(Config::kSerialBaud);
  for (const LedUnderTest& led : kLeds) {
    pinMode(led.pin, OUTPUT);
  }
  turnAllOff();

  Serial.println();
  Serial.println("LATCH LED identifier started.");
  Serial.println("1 blink = GPIO25");
  Serial.println("2 blinks = GPIO18");
  Serial.println("3 blinks = GPIO19");
  Serial.println("4 blinks = GPIO2");
  delay(1000);
}

void loop() {
  Serial.println("Starting a new LED identification round.");
  for (const LedUnderTest& led : kLeds) {
    testLed(led);
  }
  turnAllOff();
  Serial.println("Round complete. Repeating shortly.");
  delay(Config::kBetweenRoundsMs);
}
