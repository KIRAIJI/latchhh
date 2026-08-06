# Traccar integration

Data flow:

```text
tracker hardware -> Traccar -> Laravel scheduled sync -> Laravel API -> Flutter
```

Flutter must never receive Traccar credentials.

## Configuration

```dotenv
TRACKER_PROVIDER=traccar
TRACCAR_BASE_URL=https://tracker.example/api
TRACCAR_USERNAME=
TRACCAR_PASSWORD=
TRACCAR_ALLOW_INSECURE_HTTP=false
TRACCAR_CONNECT_TIMEOUT_SECONDS=3
TRACCAR_TIMEOUT_SECONDS=10
TRACCAR_RETRY_TIMES=2
```

`TRACCAR_BASE_URL` is the exact API root ending in one `/api`, with no trailing slash, query, fragment, or embedded credentials. Plain HTTP is accepted only in `local`/`testing` when explicitly enabled. TLS verification remains enabled.

Rotate the credential that was exposed in Flutter before live testing.

## Identifier mapping

- `device_uid`: permanent public claim code such as `LATCH-7K3M-P9Q2`
- `tracker_unique_id`: case-sensitive Traccar `uniqueId`
- `tracker_device_id`: positive Traccar numeric device ID

They are distinct and never user-editable.

Pre-registration always verifies Traccar:

```bash
php artisan latch:register-device LATCH-7K3M-P9Q2 tracker-unique-id
php artisan latch:register-device LATCH-7K3M-P9Q2 tracker-unique-id --tracker-device-id=123
```

## Provider contract

Laravel requests `/api/devices` for device state/identifier resolution and `/api/reports/route` for the incremental position window. `recorded_at` comes only from `fixTime`; `deviceTime` and `serverTime` are never substitutes.

Attribute names are configurable:

```dotenv
TRACCAR_BATTERY_ATTRIBUTE=batteryLevel
TRACCAR_SATELLITES_ATTRIBUTE=sat
TRACCAR_HDOP_ATTRIBUTE=hdop
TRACCAR_GSM_CSQ_ATTRIBUTE=csq
```

Invalid/missing attributes normalize to null. Only an exactly true Traccar `valid` flag with valid coordinates can create location history, update latest location, or evaluate a geofence. A newer no-fix row may still update non-location telemetry and GNSS status without erasing the last valid location.

## Synchronization

The scheduler dispatches one unique database job per claimed item/claim epoch every minute. Jobs fetch outside DB locks, then atomically store:

- deduplication receipt
- real retained position, when valid
- latest cache and telemetry
- geofence state
- battery/connection transitions
- activity and enabled notifications
- cursor and successful-sync timestamp

Jobs use three attempts, 60/300-second backoff, a 90-second timeout, and a 30-minute retry horizon. A stale claim fence exits without writing. Provider failures preserve cache and cursor; local offline aging still evaluates from the last communication time.

Defaults:

```dotenv
TRACKER_POSITION_OVERLAP_SECONDS=120
TRACKER_SYNC_MAX_WINDOW_MINUTES=60
TRACKER_MAX_CLOCK_SKEW_SECONDS=30
TRACKER_CURRENT_SECONDS=120
TRACKER_OFFLINE_SECONDS=600
```

The overlap cannot guarantee capture of a report delivered later than the overlap window.

For an opt-in live acceptance check, register a non-production test tracker, run `php artisan latch:dispatch-sync` and a queue worker, then inspect only the Laravel item/history API. Do not print credentials or raw provider payloads.
