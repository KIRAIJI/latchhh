# LATCH tracker firmware

The production Traccar device uses unique ID `LATCH001`. The corrected sketch is
in `latch_tracker/latch_tracker.ino`.

## Target hardware

This sketch preserves the pinout of the existing tracker firmware:

- ESP32/TTGO T-Call-style board with SIM800
- SIM800 TX 27, RX 26, PWRKEY 4, POWER_ON 23
- External u-blox GPS RX 14, TX 13
- Smart LTE APN `smartlte`

Do not flash this pin mapping to the older XIAO ESP32-C3 Fritzing design without
rewiring and changing the serial/pin configuration.

## Telemetry contract

The tracker sends an OsmAnd request every 60 seconds with:

- `valid`: the official Traccar GNSS-valid flag
- `lat`, `lon`: fresh or last coordinates; invalid fixes do not replace backend
  location history
- `sat`, `hdop`: GPS quality, only when TinyGPS++ reports valid values
- `csq`: SIM800 `AT+CSQ` value (`0`-`31`, or `99` for unknown)
- `batt`: SIM800 `AT+CBC` battery percentage; Traccar stores this as
  `batteryLevel`

The sketch checks network registration, rebuilds the GPRS bearer when needed,
requires a GPS fix no older than 15 seconds, and checks the HTTP status returned
by Traccar. Failed transmissions retry after 15 seconds; three consecutive
failures restart the SIM800 radio stack. GPS input continues to be drained while
the modem is waiting for AT/HTTP responses so long network operations do not
make a valid GPS fix stale.

Firmware 1.2.0 enables u-blox AssistNow Autonomous at every GPS startup. The
NEO-M8N learns orbit data from received satellite ephemerides, keeps it in its
battery-backed memory, and can use it to reduce time-to-first-fix on later
starts. It requires no cloud credential, but it must first operate with enough
sky visibility to learn useful data. It is assistance rather than an indoor
location replacement; only a fresh, valid GNSS fix is accepted as position.

Online AssistNow and Wi-Fi/cell positioning are intentionally not configured in
the firmware. Those services require private provider credentials and must be
proxied by the LATCH backend before they are enabled on production trackers.

The ESP32 brownout detector remains enabled. If the board reboots when the
SIM800 transmits, fix the power supply, wiring, grounding, and bulk capacitance;
do not hide an inadequate supply by disabling brownout protection.

## Upload checklist

1. Install the ESP32 Arduino core and TinyGPSPlus library.
2. Confirm the board, serial pins, APN, Traccar URL, and unique ID.
3. Compile and upload `latch_tracker/latch_tracker.ino`.
4. Open Serial Monitor at 115200 baud.
5. Confirm `Telemetry accepted by Traccar.`
6. Wait up to two minutes, then refresh the LATCH item screen.

At boot, firmware 1.2.0 also prints `AssistNow Autonomous requested`. Confirming
that line proves the configuration packet was sent; improvement in acquisition
time must be measured over later starts after the receiver has learned orbit
data outdoors.

Battery percentage is available only when `AT+CBC` on the installed SIM800
power arrangement returns a value from 0 to 100. If it returns an unsupported
or inaccurate value, add a properly designed battery-voltage divider to an ESP32
ADC pin and calibrate the LiPo discharge curve instead of fabricating a percent.
