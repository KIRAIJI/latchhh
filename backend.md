# LATCH Laravel Backend v1 Implementation Specification

You are the Laravel backend developer for the capstone project:

**LATCH: An IoT Tracking System with NFC-Powered Electrophoretic Display for Persistent Identification**

Implement the Laravel REST API in `backend-app` according to this specification.

This document is aligned with the current Flutter application in `flutter-app`. When terminology or scope from an older backend plan conflicts with this document, this document wins.

---

## 1. Goal

Build a small, production-shaped Laravel API that supports the Flutter application's visible server-backed flows plus the approved near-term security and history extensions:

1. Registration, login, authenticated-session restoration, current-session logout, and logout-all
2. Authenticated password change with other-session revocation
3. Profile viewing and editing
4. Profile-photo upload, replacement, and removal
5. Persisted global and category-level notification preferences
6. Permanent account deletion
7. Pre-registration and claiming of physical LATCH devices
8. Owned-item listing, details, renaming, and release
9. Cached location and tracker status supplied by Traccar
10. Bounded, claim-scoped location history retained locally by Laravel
11. An append-only owner-scoped device activity timeline
12. One locally evaluated circular geofence per item
13. In-app geofence, battery, offline, and online-recovery notifications
14. Safe operational diagnostics
15. Automated tests and integration documentation
16. Forgot-password email delivery and secure password reset
17. Verified-email gating and verification-email resend
18. Real Firebase Cloud Messaging delivery to Android and iOS
19. Granular geofence, battery, and device-status notification preferences
20. Versioned Terms acceptance, Privacy acknowledgement, public legal pages, account-deletion instructions, and a reviewable Data Safety declaration

From the user's perspective:

> One claimed LATCH device = one tracked item.

The public API therefore uses **item** terminology. The Laravel model and database table may remain `Device` and `devices`.

Example:

```text
Device UID: LATCH-7K3M-P9Q2
Item Name: Black Backpack
```

---

## 2. Repository Baseline

The current backend is a clean Laravel 12 application using PHP 8.2, MySQL, the database queue driver, and Pest. Verify these versions before implementation rather than silently assuming them.

Before changing backend code:

1. Inspect the entire `backend-app` structure.
2. Read its README, Composer manifest, routes, bootstrap configuration, models, migrations, configuration, tests, and `.env.example`.
3. Run the existing test suite and record the baseline.
4. Inspect pending changes and preserve unrelated user work.
5. Identify the exact dependencies and files required.
6. Present a short implementation plan.
7. Then implement the complete backend; do not stop at scaffolding.

Use:

- Laravel 12 and its supported PHP version
- Laravel Sanctum bearer tokens
- MySQL or MariaDB in development/production
- The existing SQLite in-memory test configuration where appropriate
- Laravel Form Requests
- Laravel Policies
- Laravel API Resources
- Focused service classes
- PHP backed enums for controlled values
- Laravel's HTTP client, scheduler, cache, filesystem, and database queue
- Pest and Laravel HTTP testing utilities

Do not install packages when Laravel already provides the required capability.

---

## 3. Security Warning and Integration Boundary

The original Flutter source directly called Traccar and contained a server address and credentials. The Laravel integration removed those values and direct calls from `flutter-app`; continue to treat the formerly exposed credential as compromised:

1. Do not copy them into this document, source code, tests, logs, seeders, or commits.
2. Rotate the exposed Traccar credential before real integration testing.
3. Store the replacement values only in the Laravel environment.
4. Flutter must call Laravel only; it must never receive or use Traccar credentials.
5. Production Flutter-to-Laravel and Laravel-to-Traccar traffic must use HTTPS.

The intended data flow is:

```text
Tracker hardware
    -> Traccar
    -> Laravel scheduled synchronization
    -> Laravel cached item/status data
    -> Flutter REST API
```

Laravel API requests must read the local cache. They must not wait for Traccar on every mobile request.

Use the official Traccar contracts as the external source of truth:

- API reference: https://www.traccar.org/api-reference/
- OpenAPI document: https://www.traccar.org/api-reference/openapi.yaml

---

### 1.1 Standards extension (implemented 2026-07-26)

This extension is part of the required v1 contract and overrides any older exclusion or deferred-work statement elsewhere in this document:

- Password recovery and email verification are required.
- New accounts receive a verification email. Authentication and verification-resend remain available before verification; profile, item, history, geofence, notification, and push-token operations require a verified email.
- Registration requires explicit Terms acceptance and Privacy acknowledgement. The accepted timestamps and version identifiers are retained for auditability.
- Laravel owns all FCM server credentials and sends real system push notifications through FCM HTTP v1. Flutter registers device tokens with Laravel and never receives a service-account credential.
- In-app notifications remain the durable source of truth. Push is a best-effort delivery channel for a newly created notification, not a replacement for the notification row.
- Public `/privacy`, `/terms`, and `/account-deletion` pages and `docs/data-safety.md` are required. Their operator identity, support contact, retention disclosures, and store declarations require owner/legal review before publication.
- Crash reporting is enabled only in non-debug Firebase builds and must not intentionally attach passwords, bearer tokens, tracker credentials, precise coordinates, or raw API payloads.
- The application must fail release validation when production HTTPS, Firebase, support-contact, a restricted native-map key, permanent application ID, or signing configuration is missing.

---

## 4. Explicit Non-Goals

Do not implement:

- Flutter UI or Flutter API wiring
- TTGO, ESP32, SIM800L, or GNSS firmware
- Traccar server installation or configuration
- NFC reading or writing
- E-Paper image selection, editing, conversion, preview, writing, or metrics
- Map-tile proxying
- Admin roles, admin API, or admin dashboard
- Organizations, teams, device sharing, subscriptions, or payments
- Multiple or polygon geofences
- Analytics dashboards
- Microservices, event sourcing, or external message brokers
- SMS-based recovery or verification
- A custom push provider other than FCM in this release
- Marketing email, marketing push, ad attribution, or behavioral analytics

Opening animation, onboarding completion, navigation state, app-version display, Google Maps SDK integration, image editing, NFC, and E-Paper behavior remain Flutter-owned. Google Maps Platform supplies the rendered map and must be named in privacy/store disclosures.

---

## 5. Non-Negotiable Domain Rules

1. A master device is pre-registered by a trusted server operator.
2. Ordinary users cannot create master device records.
3. `device_uid` is a permanent public claim code in `LATCH-XXXX-XXXX` format.
4. `device_uid`, Traccar's `uniqueId`, and Traccar's numeric device ID are separate identifiers.
5. A device may belong to only one user at a time.
6. Claiming must be concurrency-safe.
7. Only the current owner may list, view, rename, release, or configure an item.
8. A user may change only `item_name`; tracker identifiers are never user-editable.
9. Releasing a device preserves its master record and Traccar mapping.
10. Releasing clears all ownership and claim-scoped telemetry.
11. Releasing deletes the device's geofence and its former owner's notifications for that device.
12. A released device can be claimed by another user.
13. A new owner must never receive location or status cached during a previous claim.
14. One device may have at most one circular geofence.
15. Geofences are evaluated locally by Laravel, not synchronized to Traccar in v1.
16. Automated notifications are durable in-app database records and may additionally dispatch a best-effort FCM push when allowed by user preferences.
17. Tracker values are never fabricated. Missing or invalid values remain `null`.
18. Latitude `0` and longitude `0` are valid coordinates; do not use `0,0` as an unavailable sentinel.
19. All stored and returned timestamps use UTC.
20. E-Paper behavior is independent from claimed items and has no backend endpoint in v1.
21. Location history contains only validated fixes received during the current claim epoch.
22. Release/account deletion removes claim-scoped location history; a new owner starts with none.
23. Device activity is append-only and belongs to the user and claim epoch that generated it.
24. A former owner may retain their own global activity timeline, but never a later owner's activity.
25. Item-specific activity exposes only the item's current owner and current `claim_version`.
26. No public endpoint may create, update, or delete arbitrary activity records.

---

## 6. Laravel Architecture

Keep controllers thin. A controller may:

- accept an already validated request,
- authorize the action,
- call one focused service,
- return a Resource or standardized response.

Use Eloquent directly inside focused services. Do not add repository wrappers around Eloquent.

Required service boundaries:

- `AuthService`
- `DeviceClaimService`
- `AccountDeletionService`
- `ProfilePhotoService`
- `TrackerSyncService`
- `LocationHistoryService`
- `DeviceActivityService`
- `StatusNormalizationService`
- `GeofenceEvaluationService`
- `NotificationService`
- `PasswordResetController` using Laravel's password broker
- `EmailVerificationController` using Laravel signed verification URLs
- `PushProviderInterface`
- `FcmPushProvider` and a disabled/local `NullPushProvider`
- `SendPushNotification` queued job

### 6.1 Transaction and lock order

Any transaction that touches an owner, devices, and geofences must lock records in this order:

```text
user -> devices ordered by device ID -> geofences ordered by geofence ID
```

Use this order for claim, rename, release, account deletion, geofence writes/deletes, and the post-fetch commit phase of tracker synchronization. Insert claim-scoped positions and append activity only after the required owner/device/geofence locks are held. HTTP calls must happen before database locks are acquired. This gives account deletion, claim changes, history capture, activity creation, geofence evaluation, and queued synchronization one consistent deadlock-avoidance rule.

Required provider boundary:

```php
interface TrackerProviderInterface
{
    public function findDeviceByUniqueId(string $uniqueId): ?TrackerDeviceData;

    public function getDeviceState(int $providerDeviceId): ?TrackerDeviceData;

    /** @return Collection<int, TrackerPositionData> ordered oldest to newest */
    public function getPositions(
        int $providerDeviceId,
        CarbonInterface $from,
        CarbonInterface $to,
    ): Collection;
}
```

Required data-transfer objects:

```text
TrackerDeviceData
- provider_device_id: int
- unique_id: string
- connection_status: online|offline|unknown|null
- last_communication_at: datetime|null

TrackerPositionData
- provider_position_id: int
- provider_device_id: int
- latitude: float|null
- longitude: float|null
- recorded_at: datetime|null, mapped exclusively from Traccar `fixTime`
- gnss_valid: bool|null
- battery_percentage: int|null
- satellites: int|null
- hdop: float|null
- gsm_csq: int|null
```

Do not substitute Traccar `deviceTime` or `serverTime` for `fixTime`. Those timestamps describe different events and must not determine location freshness or geofence transitions.

All provider device and position IDs must be integers in `1..9223372036854775807`, matching PHP's positive signed-int64 range. Reject zero, negative, fractional, non-numeric, and out-of-range values before constructing persistence-ready DTOs.

Implementations:

- `TraccarProvider`: production implementation using Laravel's HTTP client
- `FakeTrackerProvider`: deterministic test implementation

Bind the interface through configuration. The production default is `traccar`; tests bind the fake.

---

## 7. API Conventions

Base path:

```text
/api/v1
```

Protected request headers:

```http
Authorization: Bearer {token}
Accept: application/json
Content-Type: application/json
```

`Content-Type: application/json` applies to JSON endpoints. Profile-photo upload uses `multipart/form-data`; the client must let its multipart library generate the boundary.

Use:

- snake_case database and JSON fields
- PascalCase PHP classes
- UPPER_SNAKE_CASE environment variables
- UTC database values
- ISO 8601 UTC timestamps such as `2026-07-25T08:30:00Z`
- raw enums, numbers, and timestamps in the API

Flutter is responsible for labels, icons, colors, signal bars, and relative-time strings.

For every cursor-paginated endpoint, the first-page cursor captures the normalized filters, resolved time window and retention cutoff where applicable, page size, and ordering. A follow-up request may send the opaque cursor by itself; recover that captured context instead of recomputing time-based defaults. If it also supplies query fields, they must normalize to the captured values or return `422 VALIDATION_ERROR`. A malformed/tampered cursor returns `422`; a structurally valid cursor bound to another user, item, or claim returns `404`.

### 7.1 Successful responses

Single record:

```json
{
  "success": true,
  "message": "Item retrieved successfully.",
  "data": {}
}
```

Collection:

```json
{
  "success": true,
  "message": "Items retrieved successfully.",
  "data": [],
  "meta": {}
}
```

For endpoints returning `204 No Content`, return no JSON body.

### 7.2 Error responses

```json
{
  "success": false,
  "message": "The given data was invalid.",
  "code": "VALIDATION_ERROR",
  "errors": {
    "email": [
      "The email has already been taken."
    ]
  }
}
```

Do not expose stack traces, SQL, paths, credentials, tokens, authorization headers, or internal exception messages.

Use:

- `200` successful retrieval or update
- `201` successful registration or claim
- `204` successful destructive action
- `400` malformed or impossible operation
- `401` unauthenticated
- `404` missing or inaccessible owned resource
- `409` claim/state conflict
- `422` validation failure
- `429` rate limit
- `500` unexpected internal failure
- `503` only when an operation explicitly requires the unavailable tracker provider

Normal item reads use cached data and must not become `503` merely because Traccar is temporarily unavailable.

---

## 8. Database Design

Use explicit foreign keys and indexes. Add new migrations; do not rewrite already-applied framework migrations merely for convenience.

Foreign-key actions:

- `devices.user_id` restricts raw user deletion so account deletion must use the invariant-preserving service.
- `oauth_identities.user_id` cascades on user deletion.
- `geofences.device_id` cascades on master-device deletion.
- `notifications.user_id` and `notifications.device_id` cascade on deletion.
- `tracker_position_receipts.user_id` and `tracker_position_receipts.device_id` cascade on deletion.
- `device_positions.user_id` and `device_positions.device_id` cascade on deletion.
- `device_activity_logs.user_id` and `device_activity_logs.device_id` cascade on deletion.
- `device_activity_logs.actor_user_id` becomes null when the actor is deleted.

Use millisecond-capable UTC columns for claim, cursor, provider, history, activity, evaluation, read, and synchronization times. Configure model casts for booleans, enums, JSON, and immutable datetimes. Decimal database columns must be returned by Resources as JSON numbers, not numeric strings.

### 8.1 `users`

Retain the existing Laravel user fields and add:

| Column | Type | Rules |
|---|---|---|
| `profile_photo_path` | nullable string | Relative path on the `public` disk |
| `notifications_enabled` | boolean | Default `true` |
| `notify_geofence_events` | boolean | Default `true`; category-level push/in-app preference |
| `notify_battery_events` | boolean | Default `true`; category-level push/in-app preference |
| `notify_device_status_events` | boolean | Default `true`; category-level push/in-app preference |
| `email_verified_at` | nullable timestamp | Laravel verified-email state |
| `password_set_at` | nullable timestamp | Non-null only when the user has established a usable local password |
| `terms_accepted_at` | nullable timestamp | UTC registration acceptance time |
| `terms_version` | nullable string | Published Terms version accepted |
| `privacy_acknowledged_at` | nullable timestamp | UTC registration acknowledgement time |
| `privacy_version` | nullable string | Published Privacy version acknowledged |

Do not add a role column.

### 8.2 `oauth_identities`

Store external identities separately from users so provider subjects, not mutable email addresses, remain the durable link.

| Column | Type | Rules |
|---|---|---|
| `id` | bigint | Primary key |
| `user_id` | foreign key | Cascade on account deletion |
| `provider` | string(32) | Controlled provider name; v1 supports `google` |
| `provider_subject` | string(255) | Provider-stable subject |
| `firebase_uid` | string(128) | Firebase Authentication user ID |
| `provider_email` | string(255) | Last verified provider email |
| `last_login_at` | timestamp | UTC |
| `created_at`, `updated_at` | timestamps | UTC |

Enforce unique `(provider, provider_subject)`, `(provider, firebase_uid)`, and `(user_id, provider)`. Never return provider subjects or Firebase UIDs through the API.

### 8.3 `devices`

| Column | Type | Rules |
|---|---|---|
| `id` | bigint | Primary key |
| `device_uid` | string(15) | Unique, uppercase |
| `tracker_unique_id` | string(191) | Unique, case-sensitive, never user-editable |
| `tracker_device_id` | nullable unsigned bigint | Unique Traccar numeric ID; positive and at most `9223372036854775807` |
| `user_id` | nullable foreign key | Current owner |
| `item_name` | nullable string(100) | Required only while claimed |
| `claim_version` | unsigned bigint | Default `0`; monotonically incremented on claim and release |
| `claimed_at` | nullable timestamp | UTC |
| `tracker_cursor_at` | nullable timestamp | Last successfully processed time |
| `last_provider_position_id` | nullable unsigned bigint | Positive provider ID paired with `last_position_at`; not a deduplication cursor |
| `last_latitude` | nullable decimal(10,7) | Valid latitude only |
| `last_longitude` | nullable decimal(10,7) | Valid longitude only |
| `last_position_at` | nullable timestamp | Provider position time |
| `last_telemetry_position_id` | nullable unsigned bigint | Positive provider ID paired with `last_telemetry_at` |
| `last_telemetry_at` | nullable timestamp | `fixTime` of the latest cached non-location telemetry |
| `last_communication_at` | nullable timestamp | Traccar device last update |
| `last_provider_connection_status` | nullable string(20) | Raw normalized provider state |
| `last_battery_percentage` | nullable unsigned tinyint | `0..100` |
| `last_gnss_status` | nullable string(20) | `fixed`, `no_fix`, or `unknown` |
| `last_satellites` | nullable unsigned smallint | Non-negative |
| `last_hdop` | nullable decimal(6,2) | Non-negative |
| `last_gsm_csq` | nullable unsigned tinyint | `0..31` or `99` |
| `last_synced_at` | nullable timestamp | Last successful sync |
| `notification_battery_state` | nullable string(20) | Internal transition state |
| `notification_connection_state` | nullable string(20) | Internal transition state |
| `notification_connection_reference_at` | nullable millisecond datetime | Communication timestamp anchoring the current connection episode |
| `created_at`, `updated_at` | timestamps | UTC |

Indexes:

- unique `device_uid`
- unique `tracker_unique_id`
- unique nullable `tracker_device_id`
- index `user_id`
- index `claimed_at`
- composite index on `user_id, claim_version`

Use a binary/case-sensitive database collation for `tracker_unique_id` and preserve the provider value exactly.

Invariant:

```text
unclaimed => user_id, item_name, and claimed_at are null
claimed   => user_id, item_name, and claimed_at are non-null
```

Enforce the invariant in services and tests.

`claim_version` is an ownership-epoch fence. It is never reset or exposed. Every queued job, retained position, activity, and automated event key must carry the version observed when the work was dispatched, preventing work from a former claim from writing into or leaking through a later claim.

### 8.4 `geofences`

| Column | Type | Rules |
|---|---|---|
| `id` | bigint | Primary key |
| `device_id` | foreign key | Unique; one geofence per device |
| `name` | string(100) | Required |
| `center_latitude` | decimal(10,7) | `-90..90` |
| `center_longitude` | decimal(10,7) | `-180..180` |
| `radius_meters` | decimal(8,2) | `50..5000`, suggested `100` |
| `notify_on_enter` | boolean | Default `false` |
| `notify_on_exit` | boolean | Default `false` |
| `is_active` | boolean | Default `false` |
| `last_inside` | nullable boolean | Evaluation baseline/state |
| `last_evaluated_position_id` | nullable unsigned bigint | Positive provider ID of the last evaluated fix; not a monotonic cursor |
| `last_evaluated_at` | nullable timestamp | UTC |
| `created_at`, `updated_at` | timestamps | UTC |

Do not add provider IDs, provider sync statuses, polygons, schedules, or geofence history.

### 8.5 `notifications`

| Column | Type | Rules |
|---|---|---|
| `id` | bigint | Primary key |
| `user_id` | foreign key | Cascade on user deletion |
| `device_id` | nullable foreign key | Related owned item |
| `type` | string(40) | Controlled enum |
| `title` | string(120) | Server-generated |
| `message` | text | Server-generated |
| `event_key` | string(191) | Unique case-sensitive idempotency key |
| `read_at` | nullable timestamp | UTC |
| `created_at`, `updated_at` | timestamps | UTC |

Indexes:

- unique `event_key`
- composite index on `user_id, read_at, created_at, id`
- composite index on `user_id, type, created_at, id`
- composite index on `user_id, type, read_at, created_at, id`
- composite index on `user_id, created_at, id`
- index `device_id`

Do not expose `event_key` in the API.

### 8.6 `push_tokens`

Store registered FCM installations separately from Sanctum tokens:

| Column | Rules |
|---|---|
| `user_id` | Current token owner; cascade on account deletion |
| `token` | Encrypted Eloquent cast; never log or return |
| `token_hash` | Unique SHA-256 lookup key |
| `platform` | Controlled `android` or `ios` |
| `device_name`, `app_version` | Nullable bounded metadata |
| timestamps | UTC |

An FCM token may move to the newly authenticated user on the same installation. Register/update it transactionally by `token_hash`. Delete an invalid or unregistered token after FCM reports it unusable. Logout removes the current installation token; logout-all and account deletion remove all of the user's push tokens.

### 8.7 `tracker_position_receipts`

This internal table deduplicates overlap-window results without assuming provider IDs are monotonic.

| Column | Type | Rules |
|---|---|---|
| `id` | bigint | Primary key |
| `user_id` | foreign key | Owner at capture time; cascade on account deletion |
| `device_id` | foreign key | Cascade on master-device deletion |
| `claim_version` | unsigned bigint | Ownership epoch |
| `provider_position_id` | unsigned bigint | Positive Traccar position ID, at most `9223372036854775807` |
| `recorded_at` | millisecond datetime | Traccar `fixTime` in UTC |
| `created_at` | timestamp | UTC |

Use a unique composite index on:

```text
device_id, claim_version, provider_position_id
```

Also add a composite index on `(created_at, id)` for bounded pruning.

The table stores no coordinates or telemetry and is not a location-history feature. Insert receipts inside the same transaction as cache, transition, notification, and cursor updates. Treat only the named composite unique-index violation as an already-seen no-op; do not use a broad ignore that can hide unrelated database failures. Delete a device's receipts on release/account deletion, and prune receipts older than 24 hours through the scheduler.

### 8.8 `device_positions`

This claim-scoped table stores only valid location fixes needed by the Flutter history map.

| Column | Type | Rules |
|---|---|---|
| `id` | bigint | Primary key |
| `user_id` | foreign key | Owner at capture time; cascade on account deletion |
| `device_id` | foreign key | Cascade on master-device deletion |
| `claim_version` | unsigned bigint | Ownership epoch |
| `provider_position_id` | unsigned bigint | Positive Traccar position ID, at most `9223372036854775807` |
| `latitude` | decimal(10,7) | Valid latitude |
| `longitude` | decimal(10,7) | Valid longitude |
| `recorded_at` | millisecond datetime | Traccar `fixTime` in UTC |
| `created_at` | timestamp | UTC |

Indexes:

- unique `(device_id, claim_version, provider_position_id)`
- cursor index `(user_id, device_id, claim_version, recorded_at, id)`
- pruning index `(recorded_at, id)`

Do not add `updated_at`, battery/status attributes, provider payloads, addresses, or reverse-geocoded data. Store only rows with `gnss_valid === true`, valid coordinates, a valid `fixTime`, and user/claim-version fences that still match inside the synchronization transaction.

### 8.9 `device_activity_logs`

This is an append-only, user-owned audit timeline. It is not coordinate history.

| Column | Type | Rules |
|---|---|---|
| `id` | bigint | Primary key |
| `user_id` | foreign key | Owner of this historical record; cascade on account deletion |
| `device_id` | foreign key | Related master device |
| `claim_version` | unsigned bigint | Ownership epoch |
| `actor_user_id` | nullable foreign key | Direct actor; null for automated events |
| `event_type` | string(50) | Controlled `DeviceActivityType` |
| `source` | string(20) | Controlled `ActivitySource` |
| `title` | string(120) | Server-generated |
| `description` | text | Server-generated |
| `event_key` | string(191) | Unique case-sensitive idempotency key |
| `device_uid_snapshot` | string(15) | Immutable safe snapshot |
| `item_name_snapshot` | nullable string(100) | Immutable safe snapshot |
| `event_data` | nullable JSON | Server-generated allowlisted metadata only |
| `occurred_at` | millisecond datetime | Actual event time in UTC |
| `created_at` | timestamp | UTC |

Indexes:

- unique `event_key`
- `(user_id, occurred_at, id)`
- `(device_id, user_id, claim_version, occurred_at, id)`
- `(user_id, event_type, occurred_at, id)`
- `(user_id, source, occurred_at, id)`
- `(user_id, device_uid_snapshot, occurred_at, id)`
- `(occurred_at, id)` for retention pruning

Use a binary/case-sensitive collation for the activity `event_key`. Do not add `updated_at`. No application path may update an activity after insert. `event_data` may contain bounded old/new item names for a rename, safe changed-field names, geofence name/radius, or normalized battery state, but never credentials, provider IDs, raw payloads, profile data, or coordinates.

### 8.10 Sanctum

Install and migrate Sanctum's `personal_access_tokens` table through Laravel's supported API installation flow.

---

## 9. Controlled Values

Create backed enums or an equivalent strongly controlled representation for:

### ConnectionStatus

```text
online
stale
offline
unknown
```

### LocationType

```text
current
last_known
unavailable
```

### GnssStatus

```text
fixed
no_fix
unknown
```

### BatteryStatus

```text
normal
low
critical
unknown
```

### GsmSignalLevel

```text
excellent
good
fair
poor
no_signal
unknown
```

### NotificationType

```text
geofence_enter
geofence_exit
battery_low
battery_critical
device_offline
device_online
```

### DeviceActivityType

```text
item_claimed
item_renamed
item_released
geofence_created
geofence_updated
geofence_activated
geofence_deactivated
geofence_deleted
geofence_enter
geofence_exit
battery_low
battery_critical
device_offline
device_online
```

### ActivitySource

```text
user
tracker
geofence
system
```

Do not create user-role, E-Paper, location-history-type, or geofence-sync enums.

---

## 10. Authentication

Use Sanctum bearer tokens.

Registration and login use this exact success payload:

```json
{
  "success": true,
  "message": "Authenticated successfully.",
  "data": {
    "user": {},
    "token": "plain-text-sanctum-token",
    "token_type": "Bearer"
  }
}
```

`user` is the complete `UserResource`. The plain-text token is returned only when it is created. Sanctum tokens expire after 30 days; v1 has no refresh endpoint, so Flutter returns to login after an expired-token `401`.

### `POST /api/v1/auth/register`

Request:

```json
{
  "name": "Sample User",
  "email": "user@example.com",
  "password": "StrongPass!42",
  "password_confirmation": "StrongPass!42",
  "accepted_terms": true,
  "acknowledged_privacy": true
}
```

Validation:

- `name`: required string, trimmed, `1..100`
- `email`: required valid email, lowercase-normalized, maximum `255`, unique
- `password`: required UTF-8 string, confirmed, minimum `8` characters, mixed case, at least one number and symbol, and maximum `72` UTF-8 bytes under the v1 bcrypt hasher
- `accepted_terms`: required and accepted
- `acknowledged_privacy`: required and accepted
- `role`, `notifications_enabled`, and `profile_photo_path`: prohibited

Implement the bcrypt maximum with a shared byte-aware password rule; a character-count-only `max` rule is insufficient for multibyte input. Registration, login input bounds, reauthentication fields, and password change must use the same upper bound. Do not trim or normalize passwords.

Behavior:

- create a user with `notifications_enabled = true`
- persist the configured Terms and Privacy versions plus UTC acceptance timestamps
- hash the password
- create one `flutter` token
- send Laravel's email-verification notification
- return `201` with user plus plain-text bearer token

### `POST /api/v1/auth/login`

Request:

```json
{
  "email": "user@example.com",
  "password": "password"
}
```

Validation:

- `email`: required valid email, lowercase-normalized, maximum `255`
- `password`: required UTF-8 string, maximum `72` bytes; do not trim or normalize it

Behavior:

- normalize email consistently with registration
- find the user, then inside a transaction lock that user and re-check the password against the locked hash
- reject invalid credentials without revealing which field was wrong
- create one `flutter` token while the user lock is still held, preventing an old-password login from racing a password change
- return `200` with user plus bearer token

### `POST /api/v1/auth/oauth/google`

Flutter authenticates interactively with Google through Firebase Authentication, obtains a fresh Firebase ID token, and submits:

```json
{
  "id_token": "firebase-id-token",
  "accepted_terms": true,
  "acknowledged_privacy": true
}
```

Laravel must cryptographically verify the JWT signature and its `alg`, `kid`, `exp`, `iat`, `auth_time`, `aud`, `iss`, and non-empty `sub` claims against the configured Firebase project. Require a verified email, `firebase.sign_in_provider = google.com`, and a stable Google provider subject. Never trust Flutter-supplied profile fields or use a raw Google access token as a LATCH session.

Behavior:

- reuse an identity by `(provider, provider_subject)` and refresh only safe provider metadata;
- link to an existing local account only when its matching email is already verified;
- return `409 OAUTH_ACCOUNT_CONFLICT` rather than auto-linking an unverified local account or a different Google identity;
- create a new, email-verified passwordless user when no account exists, recording legal acceptance and `password_set_at = null`;
- store the Firebase UID/provider subject only in `oauth_identities`;
- return the normal user plus a new Sanctum bearer token;
- rate-limit separately from password login.

Google-created users may establish a local password through the non-enumerating password-reset flow. Password login and current-password reauthentication remain unavailable while `password_set_at` is null.

### `GET /api/v1/auth/me`

Return the authenticated `UserResource`.

### `POST /api/v1/auth/logout`

Inside a transaction, lock the authenticated user, revoke only the token used for the current request, and return `204`.

### `POST /api/v1/auth/logout-all`

Inside a transaction, lock the authenticated user, revoke all of that user's Sanctum tokens including the current request token, and remove all of their registered push tokens. Return `204`.

After success, the caller is unauthenticated. Flutter must delete its secure local token and return to login. A later request with any formerly active token must receive `401`.

Rate limits:

- registration: 3 attempts per minute per IP
- login: 5 attempts per minute per normalized email and IP
- recovery: 5 attempts per minute per normalized email and IP
- verification send/consume: 6 attempts per minute per authenticated user or signed-link source

Hash the normalized email plus IP before using it as a login or recovery rate-limit cache key; do not store raw email addresses in cache-key names. Validate and length-limit the email before constructing the key.

### `POST /api/v1/auth/forgot-password`

Accept a normalized, valid, bounded `email`. Always return the same `202` response whether or not the account exists, preventing account enumeration. Existing users receive Laravel's broker reset notification. Delivery uses the configured production mail provider; local development may inspect the log mailer.

### `GET /api/v1/auth/password/reset-link`

This mail-only bridge accepts the broker `token` and `email`, validates their shape, and redirects to the configured mobile deep link:

```text
latch://reset-password?token=...&email=...
```

Do not expose whether a token is valid at the bridge. The mobile client submits it to the reset endpoint.

### `POST /api/v1/auth/reset-password`

Accept `email`, `token`, `password`, and `password_confirmation`. Apply the same strong, byte-bounded password rule as registration. On success, update the password, rotate the remember token, revoke every Sanctum token, remove all push tokens, and return `204`. Invalid/expired tokens return a field-keyed `422`.

### `POST /api/v1/auth/email/verification-notification`

Requires Sanctum authentication. If already verified, return an idempotent `204`. Otherwise resend the standard verification notification and return `204`.

### `GET /api/v1/auth/email/verify/{id}/{hash}`

Use a temporary signed URL and constant-time hash comparison. Resolve only the user identified by the signed URL, mark that matching email as verified, and redirect to `latch://email-verified?status=success`; invalid links redirect with `status=invalid`.

`GET /auth/me`, logout, logout-all, and verification resend are available to unverified authenticated users. All profile, settings, account, item, geofence, history, activity, notification, and push-token routes require the `verified.api` middleware. Cookie sessions and refresh tokens remain out of scope.

---

## 11. User, Profile, Settings, and Account

`UserResource` must return:

```json
{
  "id": 1,
  "name": "Sample User",
  "email": "user@example.com",
  "email_verified": true,
  "email_verified_at": "2026-07-25T08:31:00Z",
  "has_password": true,
  "oauth_providers": ["google"],
  "profile_photo_url": null,
  "notifications_enabled": true,
  "notification_preferences": {
    "geofence_events": true,
    "battery_events": true,
    "device_status_events": true
  },
  "created_at": "2026-07-25T08:30:00Z",
  "updated_at": "2026-07-25T08:30:00Z"
}
```

Never return password hashes, remember tokens, photo storage paths, legal audit timestamps/versions, FCM tokens/hashes, or bearer tokens inside the user object.

`profile_photo_url` is either `null` or an absolute URL. Outside local/testing environments it must use HTTPS and be generated from the configured application URL plus the `public` disk. Randomized bearerless public photo URLs are an accepted v1 policy; do not expose the underlying relative storage path.

### `PATCH /api/v1/profile`

Accepted fields:

- `name`: required string, trimmed, `1..100`
- `email`: required email, lowercase-normalized, maximum `255`, unique except current user
- `current_password`: required UTF-8 string with the shared `72`-byte maximum only when the normalized email actually changes
- new-password fields, `role`, `profile_photo_path`, and `notifications_enabled`: prohibited

Run the update transactionally and lock the authenticated user. If the normalized email changes, revalidate that the current personal-access-token row belongs to the locked user and is unexpired, then verify `current_password`. A wrong value returns `422 AUTH_PASSWORD_INCORRECT` with only a `current_password` field error and changes neither name nor email. Share the password-change sensitive-reauthentication limiter of 5 attempts per 15 minutes per user and IP. Never log `current_password`.

Return the updated `UserResource`. A name-only or no-op email update does not require reauthentication.

### `PUT /api/v1/profile/password`

Request:

```json
{
  "current_password": "old-password",
  "password": "new-password",
  "password_confirmation": "new-password"
}
```

Validation and behavior:

1. Require all three fields and prohibit every unrelated field.
2. Apply the same centralized bcrypt-aware password rule used by registration: UTF-8 string, confirmed, minimum `8` characters, maximum `72` bytes. Apply the same byte maximum to `current_password`.
3. Rate-limit to 5 attempts per 15 minutes per authenticated user and IP using a non-sensitive cache key.
4. Inside a transaction, lock the authenticated user and confirm the current personal-access-token row still exists, belongs to that user, and has not expired under the configured 30-day policy; otherwise return `401`.
5. Re-check the current password with Laravel's hashing service.
6. Return `422 AUTH_PASSWORD_INCORRECT` with a `current_password` field error when it is wrong.
7. Reject a new password that matches the current password with `422 VALIDATION_ERROR`.
8. Hash and persist the new password.
9. Revoke every other Sanctum token belonging to the user inside the same transaction.
10. Keep the bearer token used for this request active.
11. Return the number of other token rows revoked as `sessions_revoked`.

Success:

```json
{
  "success": true,
  "message": "Password changed successfully.",
  "data": {
    "sessions_revoked": 2
  }
}
```

Never log any submitted password value. If the password or token-revocation write fails, roll back both so the old password and sessions remain unchanged.

### `POST /api/v1/profile/photo`

Use `multipart/form-data`.

Field:

- `photo`: required image, JPEG/PNG/WebP only, maximum 5 MiB

Behavior:

1. Generate a non-user-controlled filename.
2. Store on the `public` disk under a per-user profile-photo directory.
3. Serialize concurrent replacements by locking the user during the database update.
4. Update the user only after the new file is stored.
5. If any part of the user-update transaction fails, including database-queue insertion, roll it back and delete the newly stored file; if that compensating deletion also fails, rely on the managed-directory orphan sweep described below.
6. In the same database transaction as the user update, enqueue `DeleteStoredProfilePhoto` when a previous path exists.
7. Ensure only the final referenced file remains after cleanup completes, including after concurrent requests.
8. Return `200` with the updated `UserResource`.

`DeleteStoredProfilePhoto` must explicitly use Laravel's `database` queue connection rather than inheriting the default connection. It accepts only the captured relative path, safely no-ops when the file is already absent, and retries transient storage failures with backoff. Insert its database-queue row inside the same transaction and on the same database connection as the profile change, with `queue.connections.database.after_commit = false`; the row is invisible to workers until commit and rolls back with a failed change. Do not rely on an after-commit callback that can be lost between commit and enqueue. Randomized filenames must never be reused, so delayed cleanup cannot delete a later replacement.

Run a daily, overlap-protected orphan sweep for the managed profile-photo directory. It may delete only randomized files older than 24 hours that are not referenced by any current `users.profile_photo_path`, and it must recheck the reference immediately before deletion. This recovers files left when storage succeeded but the database update and compensating deletion both failed.

Do not accept SVG or trust the uploaded filename.

### `DELETE /api/v1/profile/photo`

Clear the path and enqueue `DeleteStoredProfilePhoto` for the captured file, when present, inside the same database transaction. Return the updated `UserResource`.

Serialize removal with the same user lock used by replacement. Delete only the path captured by the committed removal so a concurrent replacement is not removed. The operation is idempotent when no photo exists.

### `PATCH /api/v1/settings`

Request:

```json
{
  "notifications_enabled": true,
  "notify_geofence_events": true,
  "notify_battery_events": false,
  "notify_device_status_events": true
}
```

`notifications_enabled` is required. Category fields are optional booleans and preserve their stored values when omitted. No other settings are accepted.

Existing notifications remain available. The global switch suppresses all new automated notification rows; each category switch suppresses only that event family. Evaluators still update device/geofence transition state and append factual activity. Re-enabling does not create retroactive notifications.

Persist the preference before returning `200` with the updated `UserResource`. A failed request must leave the stored value unchanged.

### `DELETE /api/v1/account`

Require recent proof of the account's active sign-in method in addition to the authenticated bearer token and Flutter's destructive confirmation dialog.

Password-account request:

```json
{
  "current_password": "current-password"
}
```

OAuth-only request:

```json
{
  "oauth_id_token": "recent-firebase-id-token"
}
```

For an account with `password_set_at`, require `current_password` as a UTF-8 string with the shared `72`-byte maximum. For a passwordless Google account, require a Firebase ID token whose `auth_time` is no more than five minutes old and whose provider subject and Firebase UID match the stored Google identity. Prohibit unrelated or mismatched reauthentication fields. Share the sensitive-reauthentication limiter of 5 attempts per 15 minutes per user and IP.

Inside a database transaction:

1. Lock the user; confirm the current personal-access-token row belongs to that user and is unexpired, then verify the required password or recent linked Google identity.
2. Lock all currently owned devices ordered by device ID, then lock their geofences ordered by geofence ID.
3. Delete each owned device's geofence.
4. Delete notifications related to those devices.
5. Delete tracker-position receipts for those devices.
6. Delete retained positions for those devices.
7. Increment each device's `claim_version`.
8. Clear every claim and telemetry field listed in the release procedure.
9. Delete all remaining user notifications.
10. Delete all user-owned activity records.
11. Revoke all Sanctum tokens.
12. Capture linked Firebase UIDs, delete the user, and enqueue idempotent Firebase Authentication cleanup after commit.

An absent or expired current token returns `401`. A wrong password returns `422 AUTH_PASSWORD_INCORRECT` with only a `current_password` field error. Invalid, stale, or mismatched Google proof returns a field-keyed `422 OAUTH_REAUTHENTICATION_REQUIRED`. Either failure occurs before device mutation and leaves the account unchanged.

Do not append item-release activity during account deletion because the owning user and all of their activity are being permanently removed.

Capture the profile-photo path while the user is locked and enqueue `DeleteStoredProfilePhoto` inside the account-deletion transaction. The job payload contains only that relative path, requires no surviving user row, and becomes visible only if deletion commits. It must be idempotent, validate that the path is inside the managed profile-photo directory, and retry storage failures with backoff. Log a safe cleanup warning after retry exhaustion; never restore the deleted account solely because filesystem cleanup failed.

Return `204`.

---

## 12. Device Pre-Registration

Create:

```text
php artisan latch:register-device {device_uid} {tracker_unique_id}
```

Option:

```text
--tracker-device-id=
```

Behavior:

1. Trim and uppercase `device_uid`.
2. Validate it against `^LATCH-[A-Z0-9]{4}-[A-Z0-9]{4}$`.
3. Trim and validate the non-empty Traccar unique ID, maximum `191` characters, without changing case.
4. Validate an optional numeric tracker ID as a positive integer no greater than `9223372036854775807`.
5. Reject duplicate claim UIDs, tracker unique IDs, or numeric tracker IDs.
6. Resolve the Traccar device even when `--tracker-device-id` is provided.
7. Verify both the numeric ID and case-sensitive unique ID against the provider response.
8. When `--tracker-device-id` is omitted, use `TrackerProviderInterface::findDeviceByUniqueId`.
9. When the provider is unavailable, fail safely without creating a partial device.
10. When either provider identifier differs, reject the operation.
11. Create one unclaimed master device.
12. Print only safe identifiers and the result; never print credentials.

There is no unverified/offline bypass in v1.

Create an explicitly named `DevelopmentSeeder` with clearly fake accounts and sample unclaimed devices. It must abort unless the application environment is `local` or `testing`, must not be invoked automatically by a production seeder, and must never use or document production credentials.

Also create a read-only operator command:

```text
php artisan latch:diagnose
```

It performs only read-only checks: a database read, reachability/readability of the configured database-cache and database-queue tables, inspection of the registered synchronization/maintenance schedule entries, validation of the public storage link, and presence/format validation of redacted Traccar configuration. It also reports aggregate counts for users, claimed/unclaimed devices, stale synchronizations, geofences, unread notifications, retained positions, activity rows, queued jobs, and failed jobs.

A claimed device is stale when `COALESCE(last_synced_at, claimed_at)` is earlier than `now - TRACKER_SYNC_STALE_SECONDS`, default `300`. The command exits non-zero when a required read/configuration/link/schedule check fails, when a claimed device is stale, or when failed jobs exist; queued jobs alone are informational.

Because it is strictly read-only and stores no heartbeat, `latch:diagnose` cannot prove cache writeability, queue-worker liveness, or that production cron actually invoked the registered scheduler. State those limitations in its output/documentation; table reachability and registered-task inspection must not be labeled as runtime-liveness proof.

The command must never print credentials, tokens, authorization headers, emails, profile-photo paths, device/tracker identifiers, coordinates, provider payloads, or activity/notification messages. It must not make a live Traccar request. It is diagnostic only and must not repair, migrate, prune, retry, dispatch, or otherwise mutate data.

---

## 13. Item API

All item routes require Sanctum authentication.

Use numeric item IDs for route binding and return `404` both for missing items and items owned by someone else.

### `GET /api/v1/items`

Return all devices owned by the authenticated user:

- no pagination
- sort by case-insensitive `item_name`, then `id`
- eager-load the optional geofence
- never return an unclaimed device
- never query Traccar during the request

### `POST /api/v1/items/claim`

Request:

```json
{
  "device_uid": "LATCH-7K3M-P9Q2",
  "item_name": "Black Backpack"
}
```

Validation:

- `device_uid`: required string matching the exact claim format after uppercase normalization
- `item_name`: required trimmed string, `1..100`
- ownership, tracker, claim-version, and telemetry fields: prohibited

Inside a database transaction:

1. Lock the authenticated user, then find and `lockForUpdate` the master device.
2. Return `404 DEVICE_NOT_REGISTERED` if it does not exist.
3. Return `409 DEVICE_ALREADY_CLAIMED` if `user_id` is not null.
4. Lock any stale geofence row, then defensively delete it, device-related notifications, tracker-position receipts, and retained positions; clear all claim-scoped telemetry and transition state.
5. Increment `claim_version`.
6. Set `user_id`, `item_name`, and millisecond-precision `claimed_at`.
7. Initialize `tracker_cursor_at` to `claimed_at`.
8. Append one `item_claimed` activity owned by this user and new claim version.
9. Return `201` with `ItemResource`.

The first tracker position visible to this claim must have a provider timestamp at or after `claimed_at`.

### `GET /api/v1/items/{item}`

Return the owned `ItemResource`.

### `POST /api/v1/items/{item}/refresh`

For an explicit Flutter refresh action, immediately synchronize the owned item
from Traccar and return the updated `ItemResource`. This endpoint is separate
from ordinary cached item reads and must never be called by routine screen
loading or background rebuilds.

Rate-limit each user to 12 manual tracker refreshes per minute and each owned
item to 3 per minute. Return the same non-enumerating `404` for an inaccessible
item. If Traccar cannot be reached or returns an invalid contract, retain the
last valid cached state and return `503 TRACKER_UNAVAILABLE`; never fabricate a
new position.

### `PATCH /api/v1/items/{item}`

Accepted request:

```json
{
  "item_name": "Blue Luggage"
}
```

Reject attempts to submit:

- `device_uid`
- tracker identifiers
- `user_id`
- claim timestamps
- cached telemetry
- notification transition state

Define explicit `prohibited` Form Request rules for these sensitive fields; do not rely on `validated()` silently ignoring them.

Rename inside a database transaction that locks the authenticated user and then the device, and re-authorize ownership after locking. When the normalized name actually changes, update it and append one `item_renamed` activity containing only the safe old/new names. An identical retry is a no-op and creates no activity. A release or ownership change that wins the race must make the rename return `404` rather than restoring `item_name` on an unclaimed or newly owned device.

Return the updated `ItemResource`.

### `DELETE /api/v1/items/{item}`

Inside a database transaction:

1. Lock the authenticated user, then lock and re-authorize the device.
2. Lock its geofence when present.
3. Capture safe UID/name snapshots and append `item_released` for the current owner and current claim version.
4. Delete its geofence without creating a separate manual-delete activity.
5. Delete all notifications belonging to the owner and related to the device.
6. Delete its tracker-position receipts and retained positions.
7. Increment `claim_version` to invalidate queued work from this claim.
8. Clear:
   - `user_id`
   - `item_name`
   - `claimed_at`
   - `tracker_cursor_at`
   - `last_provider_position_id`
   - `last_telemetry_position_id`
   - `last_telemetry_at`
   - every cached location/status field
   - `last_synced_at`
   - battery and connection notification state/reference timestamp
9. Preserve:
   - master device row
   - `device_uid`
   - `tracker_unique_id`
   - `tracker_device_id`
   - the incremented `claim_version`
   - activity records owned by the former user
10. Return `204`.

The former owner must immediately receive `404` for the item and location history, while their own release-era records remain available through the global activity endpoint. Another user must be able to claim the device afterward and must receive no earlier telemetry, positions, notifications, or activity through item-scoped APIs.

---

## 14. Item Resource Contract

Item list and detail endpoints use the same stable shape:

```json
{
  "id": 1,
  "device_uid": "LATCH-7K3M-P9Q2",
  "item_name": "Black Backpack",
  "claimed_at": "2026-07-25T08:00:00Z",
  "location": {
    "latitude": 15.145,
    "longitude": 120.588,
    "recorded_at": "2026-07-25T08:30:00Z",
    "type": "current"
  },
  "status": {
    "connection": "online",
    "last_communication_at": "2026-07-25T08:30:00Z",
    "battery_percentage": 75,
    "battery_status": "normal",
    "gnss_status": "fixed",
    "telemetry_recorded_at": "2026-07-25T08:30:00Z",
    "satellites": 9,
    "hdop": 1.2,
    "gsm_csq": 18,
    "gsm_signal_level": "good",
    "power_state": "battery",
    "firmware_version": "1.1.0",
    "reset_reason": "power_on"
  },
  "geofence": {
    "id": 2,
    "name": "Home",
    "radius_meters": 100,
    "is_active": true
  }
}
```

Unavailable telemetry:

```json
{
  "location": {
    "latitude": null,
    "longitude": null,
    "recorded_at": null,
    "type": "unavailable"
  },
  "status": {
    "connection": "unknown",
    "last_communication_at": null,
    "battery_percentage": null,
    "battery_status": "unknown",
    "gnss_status": "unknown",
    "telemetry_recorded_at": null,
    "satellites": null,
    "hdop": null,
    "gsm_csq": null,
    "gsm_signal_level": "unknown",
    "power_state": null,
    "firmware_version": null,
    "reset_reason": null
  },
  "geofence": null
}
```

`battery_percentage` remains a coarse tracker estimate when no fuel-gauge
hardware is present. `power_state` is controlled to `battery|charging|full`;
firmware and reset diagnostics remain nullable and must never be fabricated.

The Resource must not expose tracker IDs, `claim_version`, synchronization cursors, internal transition states, provider errors, or storage internals.

Do not create a separate `/status` endpoint. Item resources already contain the latest normalized status; retained points use only the dedicated `/location-history` contract below.

---

## 15. Location History

Location history is a locally retained, bounded sequence of validated fixes. Mobile requests must never trigger an on-demand Traccar report.

### `GET /api/v1/items/{item}/location-history`

Accepted query fields:

- `from`: optional UTC ISO 8601 timestamp
- `to`: optional UTC ISO 8601 timestamp
- `cursor`: optional opaque cursor returned by the previous page
- `per_page`: optional integer `1..1000`, default `500`

On an initial request without a cursor, when both `from` and `to` are omitted, use a fixed window ending at request start and beginning 24 hours earlier. When either is supplied, require both. Enforce `from < to`, `to <= now`, and a maximum requested span of `LOCATION_HISTORY_MAX_RANGE_DAYS`, default `7`. Cursor requests reuse the captured resolved window and retention cutoff under the global cursor rules; they do not create a new rolling 24-hour window.

Query only:

```text
user_id = authenticated user
device_id = owned item
claim_version = item's current claim_version
recorded_at >= max(requested from, claimed_at, retention cutoff)
recorded_at <= requested to
```

Return points oldest first by `(recorded_at, id)` using cursor pagination. The cursor must be signed or encrypted and bound to the authenticated user, item, claim version, resolved range, page size, and ordering. A malformed/tampered cursor returns `422 VALIDATION_ERROR`; a valid cursor bound to another user, item, or claim returns `404`. Never let a cursor bypass the current ownership query.

Response:

```json
{
  "success": true,
  "message": "Location history retrieved successfully.",
  "data": [
    {
      "latitude": 15.145,
      "longitude": 120.588,
      "recorded_at": "2026-07-25T08:30:00Z"
    }
  ],
  "meta": {
    "from": "2026-07-24T09:00:00Z",
    "to": "2026-07-25T09:00:00Z",
    "retention_cutoff": "2026-06-25T09:00:00Z",
    "next_cursor": "opaque-or-null"
  }
}
```

Rules:

- return `404` for a missing, released, or differently owned item
- return an empty `data` array when no retained fix exists
- never return provider position IDs, tracker identifiers, battery/status data, claim versions, or addresses
- never fabricate, interpolate, reverse-geocode, or smooth points
- do not log coordinates, history payloads, or cursor contents
- require HTTPS and encrypted production backups because retained coordinates are sensitive
- rate-limit history reads to 30 requests per minute per authenticated user

Retain positions for `LOCATION_HISTORY_RETENTION_DAYS`, default `30`, and prune older rows daily in bounded `(recorded_at, id)` chunks. Release deletes the current claim's positions transactionally. Reclaim begins empty, even for the same user.

---

## 16. Device Activity History

Activity history records meaningful ownership, configuration, and tracker transitions. It is separate from coordinate history.

Required endpoints:

```text
GET /api/v1/activity
GET /api/v1/items/{item}/activity
```

Rate-limit activity reads to 60 requests per minute per authenticated user.

### `GET /api/v1/activity`

Return only rows whose stored `user_id` is the authenticated user. This global endpoint may include the user's historical events for released items.

Accepted filters:

- `event_type`: optional controlled `DeviceActivityType`
- `source`: optional controlled `ActivitySource`
- `device_uid`: optional exact claim UID after uppercase normalization
- `from`, `to`: optional UTC ISO 8601 range; when either is supplied, require both, `from < to`, `to <= now`, and a span no greater than `ACTIVITY_HISTORY_MAX_RANGE_DAYS`
- `cursor`: optional opaque cursor
- `per_page`: optional integer `1..100`, default `50`

Sort newest first by `(occurred_at, id)`. Bind the signed/encrypted global cursor to the user, normalized filters, page size, retention cutoff, and ordering. Malformed/tampered cursors or changed filters/page size return `422`; a structurally valid cursor bound to another user returns `404`.

### `GET /api/v1/items/{item}/activity`

First require current ownership. Then query only rows matching:

```text
user_id = authenticated user
device_id = item
claim_version = item's current claim_version
```

Support `event_type`, `source`, `from`, `to`, `cursor`, and `per_page`; do not accept `device_uid` because the item is already selected. Bind this cursor to the user, item, current claim version, normalized filters, page size, retention cutoff, and ordering. Return `404` for a released/inaccessible item or a structurally valid cursor bound to another user, item, or claim.

### Activity resource

```json
{
  "id": 42,
  "event_type": "geofence_enter",
  "title": "Item entered Home",
  "description": "Black Backpack entered Home.",
  "source": "geofence",
  "device": {
    "item_id": 1,
    "device_uid": "LATCH-7K3M-P9Q2",
    "item_name": "Black Backpack"
  },
  "event_data": {
    "geofence_name": "Home"
  },
  "occurred_at": "2026-07-25T08:30:00Z"
}
```

`device.item_id` is nullable and returned only when the device is still owned by the activity owner **and** its current `claim_version` matches the activity row. Activity from a released or earlier claim retains safe UID/name snapshots but must not offer a navigable item ID, even when the same user later reclaims that device.

Both activity collections include `next_cursor` and `retention_cutoff` in `meta`; do not calculate an expensive total count.

Never expose `user_id`, `actor_user_id`, `claim_version`, `event_key`, provider identifiers, precise coordinates, raw payloads, or another user's data.

### Activity creation

`DeviceActivityService` is the only writer. There are no public create, update, or delete endpoints.

Create activity transactionally for:

- item claim, actual rename, and release
- geofence create, actual update, activation, deactivation, and manual delete
- geofence enter and exit transitions
- battery low and critical transitions
- offline and offline-to-online recovery transitions

Rules:

1. User mutations append activity in the same transaction as the state change.
2. An identical rename or geofence `PUT` retry is a no-op and appends nothing.
3. Claim/release keys include device and claim version; other user mutations use a generated unique operation key after an actual change is detected.
4. Automated activities use the same deterministic claim-scoped source event key as their related notification.
5. Handle only the named `event_key` unique-index conflict as an idempotent no-op; rethrow unrelated database failures.
6. Notification preferences suppress notification rows, not factual activity.
7. Initial geofence/connection baselines create no transition activity.
8. Event metadata is allowlisted per type and never contains coordinates or secrets.
9. A failed claim, rename, release, geofence operation, or sync transaction leaves no orphan activity.

Use the committed operation time for user mutations, the position `fixTime` for geofence/battery events, the threshold-crossing time for age-based offline detection, the evaluation time for an explicit provider-offline transition, and the new communication timestamp for online recovery.

Use `user` as the source for claim/rename/release and manual geofence changes, `geofence` for enter/exit, `tracker` for battery and provider-reported connection transitions, and `system` for age-derived offline detection.

Set `actor_user_id` to the authenticated user only for direct user mutations; automated events keep it null.

Retain activity for `ACTIVITY_HISTORY_RETENTION_DAYS`, default `365`, then prune in bounded `(occurred_at, id)` chunks. Account deletion cascades the user's activity. Release does not delete it.

---

## 17. Tracker Synchronization

### 17.1 Production provider

`TraccarProvider` uses:

- Basic authentication from Laravel environment variables
- the official device endpoint for device states and identifier resolution
- the official route/position report contract for positions within a time window
- a sanitized, validated base URL
- HTTPS with TLS certificate verification outside local/testing
- a short connect timeout and request timeout
- at most two retries after the initial request for connection errors, timeouts, and retryable 5xx responses

Define `TRACCAR_BASE_URL` as the Traccar API root, including the single terminal `/api` path and no trailing slash, query, fragment, or embedded credentials. Append the path segments `devices` and `reports/route` so requests end in `/api/devices` and `/api/reports/route`; never drop or append a second `/api`. Use scalar `id`, `uniqueId`, and `deviceId` query values for the v1 single-device calls. If batching is added later, encode array parameters as repeated keys exactly as defined by Traccar's OpenAPI contract, never as bracketed keys such as `id[0]`.

Traccar's device filter endpoints return JSON arrays even for one scalar filter. For `findDeviceByUniqueId` and `getDeviceState`:

- an empty array returns `null`,
- exactly one structurally valid row whose requested numeric/case-sensitive unique identifier matches returns a DTO,
- multiple rows, a non-array response, a malformed row, or an identifier mismatch is a provider-contract failure,
- never silently select the first row.

Registration treats `null` as `TRACKER_DEVICE_NOT_FOUND` and creates nothing. Synchronization treats `null` as a failed provider lookup, preserves cache/cursor, and follows its normal retry policy.

Do not retry:

- authentication failures
- authorization failures
- invalid requests
- provider validation errors

Never log the request Authorization header or credentials.

Plain HTTP is rejected unless local/testing explicitly sets `TRACCAR_ALLOW_INSECURE_HTTP=true`. HTTPS certificate verification must never be disabled. Validate provider configuration lazily when resolving the provider or running synchronization; bad/missing Traccar configuration must not prevent cached API routes from booting.

### 17.2 Scheduler and jobs

Register a repeatable scheduler task that dispatches synchronization every 10 seconds and:

1. Uses dispatcher-level overlap protection.
2. Selects claimed devices with a numeric Traccar device ID through `chunkById`.
3. Dispatches `SyncDevice(device_id, user_id, claim_version, claimed_at)`.
4. Makes the job `ShouldBeUnique` with unique ID `device_id:claim_version`.
5. Adds `WithoutOverlapping` middleware using the same identity and a finite lock expiry.
6. Prevents two jobs for the same ownership epoch from running concurrently.

Register daily, overlap-protected maintenance tasks that delete in bounded indexed chunks:

- tracker-position receipts whose `created_at` is older than 24 hours
- device positions whose `recorded_at` is older than `LOCATION_HISTORY_RETENTION_DAYS`
- activity whose `occurred_at` is older than `ACTIVITY_HISTORY_RETENTION_DAYS`
- expired Sanctum token rows through Sanctum's supported pruning command

Receipt retention must remain comfortably longer than the maximum queue retry horizon and position overlap.

Each device job:

1. Reloads a pre-fetch snapshot and confirms its user, claim version, claim timestamp, and tracker mapping still match the dispatched values.
2. Sets `from` to `tracker_cursor_at` or `claimed_at`.
3. Subtracts the configured overlap from later `from` values without going before `claimed_at`.
4. Bounds `to` to at most `TRACKER_SYNC_MAX_WINDOW_MINUTES` after the non-overlapped cursor; later runs continue catch-up.
5. Fetches the Traccar device state and incremental positions outside a database transaction.
6. Verifies the device state and every position carry a positive signed-int64 device ID matching the stored mapping, and that device state has the exact case-sensitive `tracker_unique_id`; an identifier mismatch fails the job as a provider-validation error.
7. Maps `recorded_at` exclusively from Traccar `fixTime` and discards, with a sanitized warning, any position whose position ID is not a positive signed-int64 value or whose `fixTime` is not parseable UTC.
8. Rejects positions whose `fixTime` is older than `claimed_at`.
9. Sorts structurally processable positions deterministically by `(recorded_at, provider_position_id)`; provider IDs are tie-breakers, not monotonic cursors.
10. Validates and normalizes every value.
11. Fetches no more data and then starts one database transaction.
12. Locks the expected user, then the device, then its geofence when present.
13. Revalidates `user_id`, `claim_version`, `claimed_at`, and both tracker identifiers against the dispatched/fetched values.
14. Reads the locked user's current `notifications_enabled` value.
15. Attempts to insert a receipt for each structurally processable row using `(device_id, claim_version, provider_position_id)`; an existing receipt makes that row an idempotent no-op.
16. Processes only newly receipted rows and compares `(recorded_at, provider_position_id)` tuples so a late or equal-time lower-order row cannot regress cached location, non-location telemetry, geofence state, or battery state.
17. For every newly receipted row with `gnss_valid === true`, valid coordinates, a timestamp accepted by the clock-skew rules, and `recorded_at` inside the retention window, inserts one `device_positions` row carrying the locked user, device, and claim version. A valid late point may enter history even when it is too old to change the latest cache.
18. Treats only the exact device-position unique-key conflict as an idempotent no-op and rethrows unrelated persistence failures.
19. Requires `gnss_valid === true` plus valid coordinates and a newer ordering tuple before updating `last_latitude`, `last_longitude`, `last_position_at`, `last_provider_position_id`, or evaluating a geofence.
20. Allows a newer row with `gnss_valid !== true` or unusable coordinates to update valid non-location telemetry and GNSS status, while preserving the last valid coordinates, location timestamp, history, and geofence state. Track this ordering with `last_telemetry_at` and `last_telemetry_position_id`.
21. Creates transition activity and any enabled notification through their claim-scoped unique event keys.
22. Stores receipts, retained positions, cache changes, geofence state, activity, notification inserts, transition states, cursor, and `last_synced_at` atomically.
23. Advances `tracker_cursor_at` to the bounded `to` only after the entire batch succeeds.

A structurally malformed position discarded in step 7 receives no receipt but does not make an otherwise valid provider batch retry forever. A successful run may advance past it. Identifier mismatches in step 6 fail the job because the response cannot be trusted as belonging to the claimed mapping.

If a job fails:

- do not advance the cursor,
- retain the last valid cache,
- roll back newly inserted history/activity rows,
- allow the queue retry policy to retry safely,
- log a sanitized device ID and error category,
- never fabricate replacement data.

API reads continue using the existing cache.

Required job mechanics:

- `$tries = 3`
- `$timeout = 90` seconds
- backoff of 60 seconds before the second attempt and 300 seconds before the third
- `retryUntil` no later than 30 minutes after initial dispatch
- unique/overlap lock expiry longer than the job timeout
- database queue `retry_after` longer than the job timeout
- application, workers, and scheduler sharing the same database-backed atomic cache

An old job whose claim fence no longer matches must exit without writing, notifying, or advancing a cursor.

### 17.3 Attribute mapping and validation

Traccar position attributes vary by protocol. Read attribute names from configuration:

```text
TRACCAR_BATTERY_ATTRIBUTE=batteryLevel
TRACCAR_SATELLITES_ATTRIBUTE=sat
TRACCAR_HDOP_ATTRIBUTE=hdop
TRACCAR_GSM_CSQ_ATTRIBUTE=csq
TRACCAR_POWER_STATE_ATTRIBUTE=powerState
TRACCAR_FIRMWARE_ATTRIBUTE=firmware
TRACCAR_RESET_REASON_ATTRIBUTE=resetReason
```

Rules:

- provider device ID must match the mapped device
- `recorded_at` must come from Traccar `fixTime`; never fall back to `deviceTime` or `serverTime`
- latitude must be numeric and within `-90..90`
- longitude must be numeric and within `-180..180`
- a missing coordinate makes the position unusable for location history, latest-location caching, and geofence evaluation
- battery must be numeric within `0..100`; store an integer
- satellites must be a non-negative integer
- HDOP must be a non-negative number
- GSM CSQ must be `0..31` or `99`
- GNSS is `fixed` only when Traccar's position-valid flag is exactly true
- GNSS is `no_fix` when it is false
- GNSS is `unknown` when absent
- `gnss_status`, satellites, HDOP, battery, and GSM values describe the last accepted telemetry report, not guaranteed live state; expose `telemetry_recorded_at` and label stale/offline values as last reported in clients
- only `gnss_valid === true` permits retained-location insertion, coordinate caching, and geofence evaluation
- a false or absent valid flag may still carry usable non-location telemetry, but it must not erase or move the last valid location
- invalid optional attributes become `null` and produce a sanitized warning
- a later processable position missing an optional attribute clears that cached attribute to `null`; without an attribute-specific timestamp, retaining it would misrepresent stale telemetry as current

Use `TRACKER_MAX_CLOCK_SKEW_SECONDS=30`. Accept provider timestamps no more than 30 seconds in the future and clamp them to the current server time only for freshness calculations. Persist the accepted original UTC `fixTime` in cache/history; do not replace the stored event time with the clamp. Such a point is absent from a history query whose `to` still precedes that `fixTime`. Treat timestamps further in the future as invalid for cache/evaluation/history, but still store their receipt so the same malformed row is not retried forever.

Cache `last_communication_at` and provider connection state only when the provider timestamp is at or after the current `claimed_at` and is not older than the cached communication timestamp. A pre-claim or regressive device state is unavailable to the current owner.

Do not treat RSSI, voltage, or another protocol-specific value as CSQ unless deployment configuration explicitly maps a verified CSQ attribute.

The configured overlap handles ordinary delayed delivery but cannot guarantee capture of a position whose provider timestamp arrives later than the overlap window. Document this late/out-of-order-report limitation.

---

## 18. Status Normalization

Defaults:

```text
TRACKER_CURRENT_SECONDS=120
TRACKER_OFFLINE_SECONDS=600
BATTERY_LOW_PERCENTAGE=20
BATTERY_CRITICAL_PERCENTAGE=10
```

Implementations must read these validated configuration values rather than hardcoding the defaults. Enforce:

```text
0 < TRACKER_CURRENT_SECONDS < TRACKER_OFFLINE_SECONDS
0 <= BATTERY_CRITICAL_PERCENTAGE < BATTERY_LOW_PERCENTAGE <= 100
```

### 18.1 Connection

Evaluate these rules in the order shown and stop at the first match. A null `last_communication_at` therefore returns `unknown`, even when the provider state says offline; there is no timestamp from which to establish an offline transition.

```text
unknown:
  last_communication_at is null

offline:
  provider state is offline
  OR communication age is greater than TRACKER_OFFLINE_SECONDS

online:
  provider state is online
  AND communication age is at most TRACKER_CURRENT_SECONDS

stale:
  a communication timestamp exists
  AND none of the conditions above matched
```

### 18.2 Location

```text
unavailable:
  latitude, longitude, or recorded_at is null

current:
  usable coordinates exist
  AND position age is at most TRACKER_CURRENT_SECONDS

last_known:
  usable coordinates exist
  AND position age is greater than TRACKER_CURRENT_SECONDS
```

Location freshness is based on `last_position_at`, not `last_communication_at`.

### 18.3 Battery

```text
unknown:  battery is null
critical: battery <= BATTERY_CRITICAL_PERCENTAGE
low:      battery > BATTERY_CRITICAL_PERCENTAGE
          and battery <= BATTERY_LOW_PERCENTAGE
normal:   battery > BATTERY_LOW_PERCENTAGE
```

Laravel classifies the provider value but never estimates the percentage.

### 18.4 GSM signal

```text
20..31 = excellent
15..19 = good
10..14 = fair
2..9   = poor
0..1   = no_signal
99     = unknown
null   = unknown
```

Preserve and return the validated CSQ value.

---

## 19. Geofence API

The API stores one circular geofence per owned item.

### `GET /api/v1/items/{item}/geofence`

Return the full `GeofenceResource`. If none exists, return `200` with `data: null`; absence is a normal UI state, not an error.

### `PUT /api/v1/items/{item}/geofence`

This is an idempotent create-or-replace operation matching the Flutter screen's single **Save** action.

Request:

```json
{
  "name": "Home Zone",
  "center_latitude": 15.145,
  "center_longitude": 120.588,
  "radius_meters": 100,
  "notify_on_enter": true,
  "notify_on_exit": true,
  "is_active": true
}
```

Validation:

- `name`: required trimmed string, `1..100`
- `center_latitude`: required numeric, `-90..90`
- `center_longitude`: required numeric, `-180..180`
- `radius_meters`: required numeric, `50..5000`
- all three booleans: required boolean

Return `200` with the saved resource whether it was created or updated.

Perform the upsert inside a database transaction. Lock the authenticated user, then the owned device, then its existing geofence when present; re-authorize the device after locking. The device lock serializes creation when no geofence row exists, while the unique `device_id` index remains the final one-row guarantee.

Append exactly one activity when persisted state actually changes:

- `geofence_created` for a new row
- `geofence_activated` or `geofence_deactivated` when `is_active` changes, with any other changed field names allowlisted in metadata
- `geofence_updated` for another actual change
- no activity and no timestamp churn for an identical idempotent retry

The geofence write, baseline changes, and activity insert must commit or roll back together. Activity metadata may include geofence name, radius, active state, and changed field names, but not center coordinates.

Reset `last_inside`, `last_evaluated_position_id`, and `last_evaluated_at` when:

- center changes,
- radius changes,
- an inactive geofence becomes active.

Clear the evaluation state when deactivated.

Changing only the name or notification flags does not reset the inside/outside baseline.

### `DELETE /api/v1/items/{item}/geofence`

Delete the geofence when present, append `geofence_deleted`, and return `204`. An already missing geofence remains an idempotent `204` and creates no activity.

Use the same transaction, lock order, and post-lock ownership check as `PUT`. The delete and activity insert commit together. This serializes deletion with tracker evaluation and account/item release.

No separate activate/deactivate endpoints are required; `is_active` is part of `PUT`.

### Geofence resource

```json
{
  "id": 2,
  "name": "Home Zone",
  "center_latitude": 15.145,
  "center_longitude": 120.588,
  "radius_meters": 100,
  "notify_on_enter": true,
  "notify_on_exit": true,
  "is_active": true,
  "created_at": "2026-07-25T08:30:00Z",
  "updated_at": "2026-07-25T08:30:00Z"
}
```

Do not expose the inside/outside evaluation state.

---

## 20. Local Geofence Evaluation

Evaluate an active geofence only for a newly receipted usable fix that passes the synchronization non-regression gate: its `(recorded_at, provider_position_id)` tuple must be newer than the device's previously cached location tuple and, when present, the geofence's `(last_evaluated_at, last_evaluated_position_id)` tuple. A valid late fix may enter location history but must not alter the geofence baseline or create a transition.

Use the Haversine distance in meters with Earth radius `6,371,000` meters.

Behavior:

1. Skip evaluation when no active geofence or usable coordinates exist.
2. Evaluate only a newly inserted tracker-position receipt that passes the tuple gate above; `last_evaluated_position_id` participates in deterministic ordering but is not by itself a monotonic cursor.
3. Compute whether distance is less than or equal to `radius_meters`.
4. When `last_inside` is null, establish the baseline without creating activity or a notification.
5. On `false -> true`, append `geofence_enter`; create the matching notification only when `notify_on_enter` and user-level notifications are enabled.
6. On `true -> false`, append `geofence_exit`; create the matching notification only when `notify_on_exit` and user-level notifications are enabled.
7. Update evaluation state and append factual activity even when notification creation is disabled.
8. Generate one claim-scoped source event key from device, provider position, geofence, and transition, and use it in both tables.
9. Rely on the unique event keys to make queue retries idempotent.

Do not generate a transition merely because the user edited or reactivated a geofence.

Known v1 boundary:

- geofences are not created in Traccar,
- all alert accuracy depends on Laravel receiving and processing each position in the incremental report window.

---

## 21. Battery and Offline Evaluation

### 21.1 Battery

Track the last normalized battery notification state on the device.

Rules:

- no battery value: do not notify and retain the previous battery transition state
- first known `normal`: establish baseline
- first known `low` or `critical`: append the matching activity and create one notification if enabled
- entering `low` from `normal`: append `battery_low` and create the matching notification if enabled
- entering `critical` from any non-critical state: append `battery_critical` and create the matching notification if enabled
- battery recovery updates the battery transition state without activity or a notification
- if notifications are disabled, update state and append activity without creating a notification row
- re-enabling does not create an alert for a state already observed while disabled

Use `claim_version` and the provider position ID in the unique event key.

### 21.2 Offline and recovery

Evaluate connection state during every scheduled cycle, including cycles with no new position.

Offline aging is a local calculation over cached `last_communication_at`. It must still run when the provider returns no positions or the provider fetch fails. On a fetch failure, perform this evaluation in an isolated transaction using the same user/device lock order and claim-version revalidation; it may update only connection transition state plus idempotent activity/notifications, and must not advance the tracker cursor or overwrite cached provider data.

Rules:

- the first known connection state establishes a baseline
- when a prior non-offline state changes to offline, append `device_offline` and create the matching notification if enabled
- create each offline event once for that transition
- once offline is established, stale or unknown evaluations do not close or re-arm that offline episode
- only an `online` state whose `last_communication_at` is strictly newer than `notification_connection_reference_at` closes the offline episode, appends `device_online`, and creates one recovery notification if enabled
- disabled notifications suppress notification rows but still update state and append activity
- re-enabling notifications creates no retroactive offline/online row

On connection baseline and each emitted offline/online transition, persist the communication timestamp that anchors the episode in `notification_connection_reference_at`. Ambiguous stale/unknown evaluations leave both the notification state and this reference unchanged. This prevents `offline -> stale/unknown -> offline` from producing a second event and ensures a later offline episode is re-armed only by a genuine recovery with newer communication.

Use deterministic offline/online event keys containing the device, `claim_version`, transition, and communication timestamp that caused it. Canonicalize the timestamp as UTC with fixed millisecond precision.

---

## 22. Notifications API

Notifications are an in-app feed only.

### `GET /api/v1/notifications`

Return:

- only the authenticated user's notifications
- newest first, then descending ID
- cursor pagination with default `50` and maximum `100`
- `unread_count` in metadata
- an opaque `next_cursor` in metadata when older rows exist
- an optional related-item summary only when the device is still owned by the user

Accepted query fields:

- `cursor`: optional opaque cursor produced by the previous response
- `per_page`: optional integer `1..100`, default `50`
- `type`: optional controlled `NotificationType`
- `read_state`: optional `all|unread|read`, default `all`

Sign or encrypt notification cursors and bind them to the authenticated user, normalized filters, page size, and ordering under the global cursor rules. Return `422 VALIDATION_ERROR` for malformed/tampered cursors or changed filters/page size, and `404` for a structurally valid cursor bound to another user. Compute `unread_count` across the user's complete unfiltered feed, not only the returned page.

Response item:

```json
{
  "id": 10,
  "type": "geofence_exit",
  "title": "Item left Home Zone",
  "message": "Black Backpack left Home Zone.",
  "created_at": "2026-07-25T08:30:00Z",
  "read_at": null,
  "is_read": false,
  "related_item": {
    "id": 1,
    "device_uid": "LATCH-7K3M-P9Q2",
    "item_name": "Black Backpack"
  }
}
```

Flutter maps `type` to the visible label, icon, and color.
Tapping a notification opens a dedicated detail screen; Back returns to the
notification feed, while a related item may be opened as a secondary action.

Collection metadata:

```json
{
  "unread_count": 3,
  "next_cursor": "opaque-or-null"
}
```

### `PATCH /api/v1/notifications/{notification}/read`

Mark an owned notification as read and return the updated resource.

The operation is idempotent.

### `DELETE /api/v1/notifications/{notification}`

Permanently delete one owned notification and return `204`. Return `404` when
the notification does not exist or belongs to another user. Flutter exposes
this through a confirmed swipe-to-delete action and the notification-detail
screen.

### `PATCH /api/v1/notifications/read-all`

Set `read_at` on all currently unread notifications for the authenticated user and return:

```json
{
  "success": true,
  "message": "Notifications marked as read.",
  "data": {
    "updated_count": 3,
    "unread_count": 0
  }
}
```

Declare the static `read-all` route before the dynamic notification route.

Return `404` for another user's notification.

---

### 22.1 System push delivery

Push-token routes require Sanctum authentication and verified email:

- `POST /api/v1/push-tokens` accepts `token`, `platform`, and optional bounded `device_name`/`app_version`; it upserts by the token's SHA-256 hash.
- `DELETE /api/v1/push-tokens` accepts the current raw token and unregisters only that installation.
- `DELETE /api/v1/push-tokens/all` removes every installation registered to the user.

When `NotificationService` creates a new notification row, queue one `SendPushNotification` job. Do not queue for an existing idempotent row. The job reloads the notification and user's current tokens, applies the global and category preference, and sends each token through `PushProviderInterface`.

`FcmPushProvider` must:

- use Google OAuth service-account credentials stored outside the repository,
- call the FCM HTTP v1 project endpoint over verified HTTPS,
- send a visible title/body plus data fields for `route`, `notification_id`, `type`, and nullable `item_id`,
- use the Android `latch_alerts` high-priority channel and iOS sound/category,
- cache only the short-lived OAuth access token,
- classify invalid/unregistered tokens so the job can delete them,
- use bounded HTTP retry for transient provider/server failures while retaining the token for a later notification, and
- never log the raw FCM token, OAuth token, service-account JSON, message body containing user data, or provider response payload.

When FCM is disabled, the null provider intentionally performs no external send while preserving the durable in-app record. A real production release requires FCM to be enabled and configured; disabled push is only acceptable for local/testing.

---

## 23. Notification and Activity Idempotency

`NotificationService` and `DeviceActivityService` must insert through their unique `event_key`.

If a retry attempts the same event:

- do not create a duplicate,
- return the existing record or an explicit no-op result,
- do not rely only on a pre-insert existence check,
- handle only the `event_key` unique-index race as the idempotent case,
- rethrow unrelated database failures.

Suggested event-key components:

```text
geofence:{device_id}:{claim_version}:{geofence_id}:{provider_position_id}:{transition}
battery:{device_id}:{claim_version}:{provider_position_id}:{battery_state}
offline:{device_id}:{claim_version}:{last_communication_at_utc_ms}
online:{device_id}:{claim_version}:{last_communication_at_utc_ms}
```

The notification and activity created by one automated transition use the same source event key in their separate tables. Do not put credentials, free-form exception text, or user secrets in event keys.

---

## 24. Authorization

Create a `DevicePolicy` for:

- view
- update
- release
- viewLocationHistory
- viewActivity
- manageGeofence

Ownership rule:

```php
$device->user_id === $user->id
```

Create a `NotificationPolicy` or equivalent scoped route binding for:

- view
- markAsRead

All collection queries must start from the authenticated user's relationship.

Global activity authorization uses the activity row's stored `user_id`, never only the device's current owner. Item activity additionally requires current device ownership and the current claim version.

Changing a numeric ID or cursor must never reveal another user's item, geofence, notification, location history, activity, or status.

---

## 25. Application Error Codes

Use a small stable set:

### Authentication and validation

```text
AUTH_INVALID_CREDENTIALS
AUTH_UNAUTHENTICATED
AUTH_PASSWORD_INCORRECT
VALIDATION_ERROR
RATE_LIMITED
```

### Items

```text
ITEM_NOT_FOUND
DEVICE_NOT_REGISTERED
DEVICE_ALREADY_CLAIMED
DEVICE_RELEASE_FAILED
```

### Geofence

```text
GEOFENCE_OPERATION_FAILED
```

### Notifications

```text
NOTIFICATION_NOT_FOUND
```

### Tracker and general

```text
TRACKER_PROVIDER_UNAVAILABLE
TRACKER_DEVICE_NOT_FOUND
ACCOUNT_DELETION_FAILED
INTERNAL_SERVER_ERROR
```

Except for the explicitly documented sensitive-reauthentication responses, request-field failures, including claim UID format and geofence radius/coordinates, return `422 VALIDATION_ERROR` with field-keyed errors. `AUTH_PASSWORD_INCORRECT` is also `422` and includes only a `current_password` field error. A missing geofence is the documented successful `data: null`/idempotent-delete state, so it has no error code.

Unavailable optional telemetry in an otherwise valid item is represented by nullable fields and `unknown`/`unavailable`, not by an error response.

---

## 26. Environment Configuration

Update `.env.example` with placeholders and safe defaults:

```dotenv
APP_NAME=LATCH
APP_ENV=local
APP_KEY=
APP_DEBUG=false
APP_URL=http://localhost

HASH_DRIVER=bcrypt

DB_CONNECTION=mysql
DB_HOST=127.0.0.1
DB_PORT=3306
DB_DATABASE=latch_app
DB_USERNAME=
DB_PASSWORD=

QUEUE_CONNECTION=database
DB_QUEUE_RETRY_AFTER=120
CACHE_STORE=database

SANCTUM_EXPIRATION_MINUTES=43200

MOBILE_DEEP_LINK_SCHEME=latch

GOOGLE_OAUTH_ENABLED=false
FIREBASE_PROJECT_ID=
FIREBASE_SERVICE_ACCOUNT_PATH=

FCM_ENABLED=false
FCM_PROJECT_ID=
FCM_SERVICE_ACCOUNT_PATH=

LEGAL_TERMS_VERSION=2026-07-26
LEGAL_PRIVACY_VERSION=2026-07-26
LEGAL_SUPPORT_EMAIL=support@example.com

TRACKER_PROVIDER=traccar

TRACCAR_BASE_URL=
TRACCAR_USERNAME=
TRACCAR_PASSWORD=
TRACCAR_ALLOW_INSECURE_HTTP=false
TRACCAR_CONNECT_TIMEOUT_SECONDS=3
TRACCAR_TIMEOUT_SECONDS=10
TRACCAR_RETRY_TIMES=2

TRACCAR_BATTERY_ATTRIBUTE=batteryLevel
TRACCAR_SATELLITES_ATTRIBUTE=sat
TRACCAR_HDOP_ATTRIBUTE=hdop
TRACCAR_GSM_CSQ_ATTRIBUTE=csq

TRACKER_POSITION_OVERLAP_SECONDS=120
TRACKER_SYNC_MAX_WINDOW_MINUTES=60
TRACKER_MAX_CLOCK_SKEW_SECONDS=30
TRACKER_SYNC_STALE_SECONDS=300
TRACKER_CURRENT_SECONDS=120
TRACKER_OFFLINE_SECONDS=600

LOCATION_HISTORY_RETENTION_DAYS=30
LOCATION_HISTORY_MAX_RANGE_DAYS=7
ACTIVITY_HISTORY_RETENTION_DAYS=365
ACTIVITY_HISTORY_MAX_RANGE_DAYS=90

BATTERY_LOW_PERCENTAGE=20
BATTERY_CRITICAL_PERCENTAGE=10
```

Rules:

- never commit real Traccar credentials,
- never commit Firebase service-account JSON; store it outside the web root/repository and configure an absolute `FCM_SERVICE_ACCOUNT_PATH`,
- enable Google as a Firebase Authentication sign-in provider and register the Android production/debug signing certificate fingerprints,
- require `GOOGLE_OAUTH_ENABLED=true`, a Firebase project ID, and readable service-account credentials before exposing Google sign-in; the OAuth settings may explicitly fall back to the equivalent FCM project/credential variables,
- use the same Firebase project for Flutter Authentication, backend ID-token verification, and server-side Authentication cleanup,
- require `FCM_ENABLED=true`, an FCM project ID, and a readable credentials file in production,
- require a non-example legal/support email and owner-reviewed Terms, Privacy, deletion, and Data Safety content before publication,
- configure a production mail transport and sender domain; the `log` mailer is local/testing only,
- require public HTTPS `APP_URL` and API URLs in production so recovery and verification links are valid,
- keep the mobile deep-link scheme synchronized across Laravel, Android, iOS, and Flutter,
- require `HASH_DRIVER=bcrypt` for this v1 contract; changing hashers requires revisiting the shared password byte-limit rule and tests,
- require `TRACCAR_BASE_URL` to be the validated API root ending in exactly one `/api` and no trailing slash,
- `TRACCAR_RETRY_TIMES` counts retries after the initial HTTP attempt; the default `2` permits at most three total attempts,
- ensure `TRACCAR_CONNECT_TIMEOUT_SECONDS <= TRACCAR_TIMEOUT_SECONDS` and keep the worst-case budget for the two sequential provider calls, including all attempts plus database work, below the sync job timeout; changing these values may require raising both the job timeout and queue `retry_after`,
- require HTTPS outside local/testing unless the explicit insecure-local override is enabled,
- keep TLS certificate verification enabled,
- validate provider configuration lazily and fail synchronization clearly without preventing cached API routes from booting,
- keep thresholds internally consistent,
- enforce `60 <= TRACKER_POSITION_OVERLAP_SECONDS <= 3600`; consequently the 24-hour receipt retention comfortably exceeds overlap plus the 30-minute job retry horizon,
- enforce `120 <= TRACKER_SYNC_STALE_SECONDS <= 86400`,
- enforce `1 <= LOCATION_HISTORY_MAX_RANGE_DAYS <= LOCATION_HISTORY_RETENTION_DAYS <= 90`,
- enforce `1 <= ACTIVITY_HISTORY_MAX_RANGE_DAYS <= ACTIVITY_HISTORY_RETENTION_DAYS <= 730`,
- require critical battery percentage to be lower than low battery percentage,
- use the explicit `public` disk for profile photos,
- use the same database connection for application transactions and the database queue, with queue `after_commit` disabled, so cleanup-job insertion participates in the profile/account transaction,
- keep database queue `retry_after` greater than the sync job timeout,
- migrate the database cache table because unique and overlap locks depend on it.

---

## 27. Logging and Failure Handling

Log safe events:

- registration/login rate-limit triggers
- sensitive-reauthentication rate-limit triggers and failure categories without submitted fields
- claim conflict
- release failure
- tracker provider category/status without response secrets
- invalid provider telemetry with safe device identifiers
- synchronization failure and retry exhaustion
- notification/activity idempotency conflict
- bounded history/activity pruning counts
- account/profile-photo cleanup failure
- diagnostic health-check category without checked values

Never log:

- passwords
- current-password or password-confirmation fields
- bearer tokens
- Traccar credentials
- authorization headers
- complete provider response bodies
- coordinates or location-history response bodies
- activity/notification message bodies or cursor contents
- uploaded image bytes
- environment values
- stack traces in production API responses

Provider failure must be isolated per device so one bad mapping does not stop all device jobs.

---

## 28. Automated Tests

Use Pest and Laravel's testing helpers. Run the complete suite before reporting completion.

### Authentication

- successful registration returns user and token
- duplicate email
- normalized email
- password confirmation
- shared bcrypt password rule enforces the exact 72-byte boundary, including multibyte input
- public role/settings injection rejected
- valid and invalid login
- valid Google Firebase ID token creates a verified passwordless account and returns a Sanctum token
- Google login reuses its stable provider subject and safely links only to an existing verified local email
- unverified-email conflicts, wrong Firebase project/issuer/provider, unverified provider email, malformed signature, expired token, and unknown key are rejected
- OAuth verifier tests use local fixed keys/fake provider responses and never live Google credentials
- login rejects overlong password input without trimming or normalization
- exact `data.user`, `data.token`, and `data.token_type` contract
- rate limiting
- rate-limit key does not contain raw email
- 30-day token expiration policy
- expired Sanctum-token pruning is registered on the daily schedule
- `/auth/me`
- missing/invalid token
- logout revokes only current token
- logout-all revokes every token including the caller
- formerly active tokens receive `401` after logout-all
- registration requires Terms acceptance and Privacy acknowledgement and stores configured versions/timestamps
- registration sends one verification email and returns `email_verified = false`
- unverified accounts may restore/authenticate, resend verification, and log out but receive `403 EMAIL_NOT_VERIFIED` for protected application data
- signed verification succeeds once, emits the verified event, rejects tampering, and deep-links back to Flutter
- verification resend is rate-limited and idempotent after verification
- forgot-password responses do not reveal whether the account exists
- valid reset tokens apply the shared strong password rule, change the hash, and revoke all Sanctum and push tokens
- invalid, expired, malformed, mismatched, and throttled reset attempts are field-keyed and leave the account unchanged

### Profile and account

- profile update
- name-only profile update does not require password reauthentication
- unique email excluding current user
- email change requires the correct current password and an active current token
- wrong-password, expired-token, rate-limit, and rollback behavior for email change
- correct current password permits password change
- incorrect current password returns field-keyed `AUTH_PASSWORD_INCORRECT`
- registration and password change share the same rule
- confirmation and same-password rejection
- new password is hashed and never returned or logged
- current Sanctum token remains active
- every other token is revoked and counted
- revoked/expired current token cannot complete the change
- password/token write failure rolls back both
- password-change rate limiting
- the sensitive-reauthentication limiter is shared by password change, email change, and account deletion for the same user/IP
- concurrent old-password login cannot mint a token after password change
- valid profile-photo upload
- invalid type and oversized photo
- replacing photo deletes the previous file
- failed database update deletes the newly stored replacement
- concurrent replacements leave only the final referenced file
- removing photo is idempotent
- replacement, removal, and account deletion persist the cleanup job in the same transaction
- a rolled-back profile/account transaction also rolls back its cleanup job
- a committed change makes its cleanup job visible to the database worker
- cleanup explicitly uses the database queue even when the application/test default is `sync`
- queue-insertion failure rolls back the profile/account change and triggers compensation for a newly uploaded file
- cleanup safely no-ops for an absent file and rejects paths outside the managed directory
- transient storage deletion failure is retried by `DeleteStoredProfilePhoto`
- the aged orphan sweep deletes only unreferenced managed files and rechecks before deletion
- notification preference update
- granular geofence, battery, and device-status preferences
- disabled preference suppresses future rows
- account deletion removes user data and tokens
- account deletion requires the correct current password and active current token
- passwordless account deletion requires a recent linked Google reauthentication and queues Firebase Authentication cleanup only after commit
- wrong-password, expired-token, throttled, and failed account-deletion attempts leave all data unchanged
- account deletion releases devices and clears telemetry/history
- account deletion removes the user's activity
- account deletion preserves master device and tracker mapping

Use `Storage::fake('public')` for photo tests.

`Queue::fake()` does not prove durable transactional enqueueing. For cleanup-visibility tests, override the current test default from `sync` to the `database` queue, use the same database connection as the profile/account transaction, and observe the jobs table from a separate connection. Use MySQL when available or a file-backed SQLite database; SQLite `:memory:` cannot prove cross-connection visibility.

### Device administration and claims

- valid command with explicit tracker ID
- valid command with provider lookup
- invalid claim UID
- zero, negative, and out-of-range numeric tracker IDs
- duplicate claim UID
- duplicate tracker mapping
- case-variant tracker unique IDs follow the configured binary uniqueness and exact-lookup rules
- provider unavailable
- `DevelopmentSeeder` refuses to run outside `local` and `testing`
- successful claim
- claim creates one current-epoch activity
- lowercase claim UID normalization
- unknown device
- already claimed conflict
- claim increments `claim_version`
- claim defensively removes stale geofence, notification, receipt, and retained-position rows before assigning the new epoch
- service uses transaction and row lock
- user-to-device lock order is consistent
- second competing claim cannot succeed
- list returns all and only owned items
- list has no pagination
- item response contract with full and null telemetry
- cross-user detail/update/release returns `404`
- rename changes only item name
- actual rename creates one activity with safe old/new names
- identical rename retry creates no activity
- concurrent rename and release cannot violate the claimed/unclaimed invariant
- concurrent rename and account deletion cannot restore claim data
- release deletes geofence and related notifications
- release deletes retained positions but preserves the former owner's global activity
- release activity is inserted before ownership is cleared
- release clears every claim-scoped status field
- released device can be reclaimed
- new owner cannot receive old telemetry, positions, or item-scoped activity

SQLite cannot prove MySQL row-lock semantics. Keep the service implementation concurrency-safe with `lockForUpdate`; add a MySQL concurrency test when the integration-test environment supports parallel transactions.

### Tracker provider and synchronization

- provider identifier resolution
- device lookup handles empty, singleton, multiple, malformed, and mismatched array responses
- provider numeric-ID/unique-ID mismatch
- provider authentication/configuration errors
- API-root normalization prevents missing or duplicated `/api`
- device/position queries use the documented scalar parameter names without bracket encoding
- cached API boots with missing/invalid provider configuration
- cleartext provider URL rejected outside local/testing
- TLS verification remains enabled
- DTO mapping
- connection timeout/retry policy
- claimed devices only
- dispatcher excludes unclaimed and unmapped devices
- unique job identity includes claim version
- overlap middleware and cache-lock expiry
- job timeout is lower than database queue `retry_after`
- incremental window and overlap
- bounded catch-up windows
- chronological processing
- `recorded_at` maps from `fixTime`, never `deviceTime` or `serverTime`
- non-monotonic provider position IDs are accepted and ordered by time plus ID
- an existing tracker-position receipt makes an overlap/retry row a no-op
- only the receipt composite-key conflict is swallowed; unrelated database errors fail and roll back the batch
- a novel lower numeric provider ID is not discarded merely because a higher ID was seen
- multiple overlap duplicates do not replay transitions
- late/out-of-order rows cannot regress cache
- position before `claimed_at` ignored
- device state before `claimed_at` ignored
- missing position timestamp rejected
- zero, negative, and out-of-range position IDs are discarded as malformed data without a database retry loop
- a mismatched or invalid provider device ID fails response validation before any write
- future timestamp skew handling
- an accepted within-skew fix follows the documented history-storage and freshness rules
- `valid=false` or absent valid flag preserves the last valid coordinates and geofence state
- a newer invalid-fix row may update valid non-location telemetry
- invalid coordinates never overwrite a prior valid location
- invalid optional attributes become null on an eligible newer telemetry row
- missing later optional attributes clear their cached values
- valid `0,0` is not treated as unavailable
- valid newly receipted fixes create one retained position
- invalid/no-fix positions and timestamps beyond `TRACKER_MAX_CLOCK_SKEW_SECONDS` create no history row
- valid late positions enter history without regressing current state
- a newly receipted late fix may enter history but cannot change geofence state or create a transition
- duplicate retained-position conflict is idempotent while unrelated failures roll back
- cursor advances only on full success
- receipts, retained positions, cache, transition state, activity, notifications, and cursor roll back together
- receipt retention pruning uses `created_at` and remains longer than the retry horizon
- receipt pruning uses the `(created_at, id)` index in bounded chunks
- provider failure retains cache and cursor
- release during a queued job cannot repopulate old telemetry
- old job after release and re-claim cannot write into the new claim
- notification/activity event keys isolate claim versions
- full status normalization
- current versus last-known location uses position time

Do not make live Traccar calls in the normal test suite.

Create a separate opt-in `traccar_live` acceptance group, skipped unless an explicit environment flag and rotated test credentials are present. It must authenticate, resolve one configured unique ID, fetch its state and a bounded position window, run one synchronization, and verify the cache plus valid retained-position insertion without printing secrets or coordinates. Fake-provider tests prove backend behavior; only this opt-in check may be reported as proof of connectivity to the deployed Traccar instance.

### Location history

- defaults to the resolved previous-24-hour window
- cursor-only follow-up pages reuse the first page's resolved window and retention cutoff even as server time advances
- validates paired UTC `from`/`to`, ordering, future values, maximum span, and page size
- queries local storage only and never calls the provider
- remains readable from retained data during provider outage
- returns only current-user/current-claim points oldest first
- identical timestamps paginate by ID without gaps or duplicates
- signed/encrypted cursor rejects tampering, another user, item, claim, filter, or range
- point resource exposes only numeric coordinates and UTC `recorded_at`
- empty history returns an empty collection
- cross-user and released-item access returns `404`
- release deletes rows and reclaim starts empty
- old queued claim work cannot insert into a later claim
- account deletion cascades rows
- daily retention pruning uses the `(recorded_at, id)` index in bounded chunks
- coordinates and response bodies are absent from logs
- the 31st location-history request within a minute returns `429`, with counters isolated by authenticated user

### Device activity

- claim, actual rename, release, geofence mutation, geofence transition, battery, offline, and online events use correct controlled types
- each event uses the documented operation/fix/threshold/communication occurrence time
- every mutation/activity pair rolls back together on failure
- automated retry creates one activity row through the unique event key
- identical rename/geofence retries create no activity
- disabled notifications still produce factual transition activity
- initial geofence and connection baselines create no transition activity
- global endpoint returns only the authenticated user's rows, including their released-item history
- item endpoint requires current ownership and current claim version
- former-owner activity does not leak to a new owner and future activity does not leak backward
- a same-user reclaim excludes previous claim epochs from item activity
- released/prior-claim global activity keeps `device.item_id = null`, including after a same-user reclaim
- cursor pagination/filter ordering and tamper protection
- cursor-only follow-ups preserve captured filters/page size, and the 61st request within a minute returns `429` with per-user isolation
- response hides user IDs, actor IDs, claim versions, event keys, provider IDs, and coordinates
- event-data allowlists reject or omit unsafe metadata
- no public create, update, or delete routes exist
- account deletion cascades rows and retention pruning is bounded

### Geofence

- missing geofence returns `data: null`
- create through `PUT`
- update through the same `PUT`
- create/update/activate/deactivate/manual-delete activity is transactional
- identical `PUT` and idempotent missing delete create no activity
- radius boundaries `50` and `5000`
- invalid radius and coordinates
- only one row per device
- ownership protection
- delete and idempotent delete
- concurrent geometry update and tracker evaluation serialize through the documented lock order
- concurrent delete and tracker evaluation cannot recreate or evaluate a deleted geofence
- geometry/reactivation resets baseline
- name/flag-only changes retain baseline
- first position creates no enter/exit event
- outside-to-inside enter
- inside-to-outside exit
- enter/exit always append activity and only conditionally notify
- disabled enter/exit flags
- inactive geofence
- duplicate position/event-key prevention

### Battery, offline, and notifications

- first low/critical reading behavior
- low transition
- critical transition
- recovery without notification
- offline transition only once
- stale cached communication can create one offline transition even when the current provider fetch fails
- null communication time normalizes to unknown even when provider state is offline
- offline-to-online creates one recovery activity and optional `device_online` notification
- startup online baseline creates no recovery event
- `offline -> stale/unknown -> offline` neither closes the episode nor creates a second offline event
- recovery requires a strictly newer communication timestamp before another offline episode can be armed
- reconnection resets transition state
- notification preference suppression without stale state
- newest-first list
- cursor pagination defaults and maximum
- validated notification type/read-state filters and cursor/filter binding
- signed/encrypted notification cursors reject tampering, cross-user reuse, and changed filters/page size
- unread count
- related-item summary
- mark one read
- mark all read
- idempotent read operations
- cross-user protection
- database unique constraint prevents duplicate events
- verified users can register, move, remove, and remove-all FCM tokens
- FCM token ciphertext differs from plaintext at rest and lookup uses only the SHA-256 hash
- logout/password reset/logout-all/account deletion remove the appropriate push tokens
- a newly created notification queues one push job; an idempotent retry does not
- fake-provider accepted, failed, and invalid-token results are handled without network access
- invalid provider tokens are deleted without exposing them in logs

### Contract checks

- snake_case fields
- UTC ISO 8601 timestamps
- standardized success/error envelopes
- `204` responses have no body
- private tracker/storage/internal fields never appear
- password change, logout-all, location-history, and activity routes match the documented contracts
- password recovery, email verification, and push-token routes match the documented contracts; deferred separate item-status and E-Paper-log routes do not exist

### Diagnostics

- healthy dependencies return zero exit status
- required dependency failure returns non-zero
- stale-sync classification uses the configured threshold and exact boundary
- stale devices or failed jobs return non-zero; queued jobs alone remain informational
- cache/queue table and registered-schedule checks are reported as reachability/configuration, never worker/cron liveness
- output contains only aggregate/redacted health data
- credentials, environment values, emails, identifiers, coordinates, paths, messages, and payloads never appear
- the command performs no writes or repair actions

---

## 29. Documentation Deliverables

After implementation, create or update:

- `README.md`
- `docs/api.md`
- `docs/password-change.md`
- `docs/location-history.md`
- `docs/device-activity-history.md`
- `docs/flutter-integration.md`
- `docs/traccar-integration.md`
- `docs/operations.md`
- `docs/testing.md`
- `docs/data-safety.md`
- `.github/workflows/ci.yml`
- `.env.example`
- a Postman collection or equivalent request examples

Document:

- every endpoint and authentication requirement
- password recovery, verified-email gate, mobile deep links, production mail delivery, and token revocation
- FCM HTTP v1 credentials, queue delivery, token lifecycle, granular preferences, and mobile Firebase/APNs prerequisites
- Terms/Privacy versioning, public policy URLs, account-deletion request path, retention statements, and the owner-reviewed store Data Safety declaration
- password-change and current/other-session revocation behavior
- request validation
- exact response fields and nullability
- enums and error codes
- ownership and release rules
- location-history retention, privacy, cursor, and claim-epoch rules
- activity ownership, snapshots, append-only behavior, retention, and released-item navigation rules
- sensitive reauthentication for email changes/account deletion and the bcrypt byte limit
- Traccar identifier mapping
- scheduler and queue requirements
- diagnostic stale-sync formula, exit-status rules, and the limits of read-only cache/queue/scheduler checks
- one-minute synchronization plus receipt, position, activity, expired-token, and profile-photo cleanup schedules
- profile-photo storage/link requirements
- environment variables without secrets
- known v1 limitations

Required run commands must include the correct equivalents of:

```text
composer install
php artisan key:generate
php artisan migrate
php artisan db:seed --class=DevelopmentSeeder
php artisan storage:link
php artisan queue:work
php artisan schedule:work
php artisan test
php artisan latch:register-device ...
php artisan latch:diagnose
```

Verify the exact commands against the implemented project before documenting them.

Label `DevelopmentSeeder` execution as local/testing-only; production setup must not run it. Document `schedule:work` as a local-development command. Production must use a supervised queue worker and a cron/scheduler entry that starts `php artisan schedule:run` every minute; Laravel keeps that command active within the minute for the 10-second repeatable synchronization event. Application, queue workers, and scheduler must share the same database/cache and use UTC. Deployment instructions must restart queue workers and interrupt any active sub-minute scheduler after releasing new code.

---

## 30. Flutter Integration Prerequisites

The paired Flutter application is part of the release acceptance surface. Keep these requirements documented and verified alongside the backend:

Implementation status as of 2026-07-27: Flutter covers secure email/password and Google/Firebase authentication, session restoration, strong field-keyed validation, forgot/reset-password deep links, email-verification gating/resend, multi-item API maps, real cursor-backed location history, item claim/rename/release, geofences with persisted centers, in-app notification details/delete/badges, FCM registration and system-notification taps, profile photos, granular settings rollback, password change/setup, password-or-Google reauthenticated account deletion, logout-all, activity timelines, legal/data-safety screens, and local cache clearing. Firebase initializes optionally in debug but is mandatory for release.

1. Rotate and remove the tracker credential currently present in Flutter source.
2. Remove all direct Traccar HTTP calls and connection-setting fields.
3. Add `android.permission.INTERNET` to the main Android manifest for release builds.
4. Add a Laravel API client with safe base-URL configuration.
5. Add controllers/value callbacks to login and registration fields.
6. Store bearer tokens in secure platform storage, not plain shared preferences.
7. Restore sessions through `/auth/me` during startup.
8. Return to auth after logout or account deletion.
9. Replace the single-device Traccar adapter with the `/items` collection.
10. Render multiple item coordinates on the map.
11. Wire add, rename, release, profile, settings, and account actions to the API.
12. Fetch and hydrate every existing geofence field before editing.
13. Initialize the map from the saved geofence center rather than the current device/fallback center.
14. For a new geofence, emit and retain the map's initial center even before the user moves it.
15. Retain and submit all later center changes from `GeofenceMap`.
16. Replace `TraccarMapBridge` geofence inputs with the selected item's API-derived coordinates.
17. Render nullable absolute `profile_photo_url`.
18. Wire the cursor-paginated notification list, badge, mark-one, and mark-all actions.
19. Roll back the optimistic notification-settings switch when persistence fails.
20. Show field-keyed `422` errors plus form-level authentication, network, and server errors.
21. Preserve loading/retry states and prevent duplicate submissions.
22. Add a Profile > Security > Change Password flow with current/new/confirmation fields and show `sessions_revoked` after success.
23. Collect and submit `current_password` when changing email or permanently deleting the account; keep the destructive deletion confirmation and render field-keyed reauthentication errors.
24. Add Logout All, clear secure storage after `204`, and return immediately to login.
25. Add an item location-history map/time-range flow that consumes cursor pages oldest first and treats cursors as opaque.
26. Add global and item-scoped activity timelines with filters; released-item global activity must not navigate back to item details.
27. Handle `device_online`, notification type/read-state filters, and factual activity that exists even when notifications were disabled.
28. Clear cached location/history/activity pages immediately after release or account deletion.
29. Run `flutterfire configure` for the permanent Android application ID and iOS bundle ID; commit only the generated client configuration files that Firebase documents as safe for apps.
30. Enable Android notification permission/channel behavior and iOS Push Notifications plus Background Modes; upload the APNs authentication key to Firebase.
31. Register refresh-token changes and unregister tokens on logout/account deletion; never place Firebase service-account JSON in Flutter.
32. Handle foreground, background, terminated, and tapped-notification navigation while preserving back navigation to the Notifications tab.
33. Route `latch://reset-password` and `latch://email-verified` links consistently on Android/iOS.
34. Publish and link HTTPS Privacy, Terms, and account-deletion pages; keep local copies consistent with the released legal versions.
35. Enable Crashlytics collection only outside debug and route Flutter/framework/platform errors without intentionally adding credentials, auth tokens, precise coordinates, or raw payloads.
36. Use the permanent mobile identifier `com.latch.mobile`, configure a production keystore, keep an Android-restricted Google Maps key only in ignored local properties, configure the support email and HTTPS API, and build/test a signed release artifact. Restrict the key to the package, installed signing SHA-1 certificates, and Maps SDK for Android.
37. Enable Google in Firebase Authentication, register Android SHA-1/SHA-256 fingerprints, obtain a Firebase ID token through `firebase_auth`, send it only to `/auth/oauth/google`, and continue using the returned Sanctum token for LATCH API calls.

The backend must not weaken its contract to accommodate currently unwired Flutter callbacks. In particular, geofence coordinates remain required.

---

## 31. Definition of Done

The Laravel backend is complete only when:

1. A user can register or log in with email/password or Google, restore the authenticated Sanctum session, log out, and log out all sessions.
2. A user can change the password securely while retaining only the current session.
3. A user can edit name/email and manage a profile photo; changing email requires current-password reauthentication.
4. A user can globally and per-category control future in-app notification creation and system push delivery.
5. A user can permanently delete the account and all user-owned history/activity only after current-password or recent linked-Google reauthentication, as appropriate.
6. A trusted operator can pre-register a device with distinct claim and Traccar identifiers.
7. A user can claim a registered device with an item name, and another user cannot claim or access it.
8. All owned items are returned in one list with the exact Flutter-required nested contract.
9. Scheduled synchronization passes provider-contract tests; before deployed Traccar connectivity is claimed, the opt-in live acceptance check stores real, validated data without exposing credentials.
10. Provider outages preserve the last cache/history and do not fabricate data.
11. An owner can view, rename, and release an item with transactional activity records.
12. Release removes the geofence, notifications, telemetry, and retained positions while preserving the master mapping and former owner's global activity.
13. A new owner cannot see the former owner's location, status, positions, notifications, or item-scoped activity.
14. An owner can retrieve bounded current-claim location history from local storage without a request-time provider call.
15. A user can view an append-only global activity timeline; item activity is current-owner/current-claim only.
16. An owner can save, edit, activate/deactivate, and delete one circular geofence through `GET`, `PUT`, and `DELETE`.
17. Geofence mutations and real enter/exit transitions create idempotent activity; notification flags control only notification rows.
18. Battery and connection episodes are stateful and idempotent; ambiguous connection states cannot re-arm duplicate offline events.
19. The filtered in-app feed, unread count, mark-one, and mark-all operations work.
20. Profile-photo storage is validated and old files are cleaned through a transactionally enqueued, idempotent retryable job plus an aged-orphan sweep.
21. Receipt, position, activity, expired-token, and orphan-file retention jobs are bounded and scheduled.
22. `latch:diagnose` reports redacted read-only reachability/configuration checks and aggregate warning counts without claiming worker/cron liveness.
23. Every protected route and cursor enforces ownership and prevents cross-user leakage.
24. All automated tests pass.
25. API, password/recovery/verification, history, activity, push, privacy/data-safety, Traccar, testing, environment, and Flutter-integration documentation is complete.
26. Forgot-password email delivery is non-enumerating, reset links deep-link safely to Flutter, and a successful reset revokes every server and push token.
27. New accounts must verify their email before protected application data is accessible; email changes require re-verification.
28. Newly created allowed notification events dispatch real FCM HTTP v1 pushes, invalid device tokens are removed, and no server credential enters Flutter or source control.
29. Terms/Privacy acceptance is versioned, public Privacy/Terms/deletion pages exist, and the Data Safety declaration is reviewed against the released binaries and backend behavior.
30. Release builds fail closed without production HTTPS, permanent IDs, Firebase Authentication/Google/FCM/APNs configuration, legal support contact, a platform-restricted native-map key, and signing.
31. E-Paper metrics/content, role/admin, provider-geofence synchronization, refresh-token, marketing, and real-time tracker credential surfaces remain absent.
32. CI blocks regressions through Laravel formatting/tests, Flutter formatting/analysis/tests, and Android debug compilation.

---

## 32. Final Implementation Report

After implementation, report:

1. Repository assessment and baseline
2. Dependencies installed
3. Files created and modified
4. Migrations and data relationships
5. Implemented endpoints
6. Authentication and authorization status
7. Password-change/recovery, email-verification, sensitive-reauthentication, current-token, and logout-all behavior
8. Device claim/release status
9. Traccar provider, synchronization, and retained-location status
10. Activity history and ownership-privacy status
11. Geofence evaluation status
12. In-app notification, FCM delivery/token, recovery-event, and preference status
13. Profile-photo and account-deletion status
14. Diagnostics status
15. Automated test results with exact command and counts
16. Documentation and Postman locations
17. Required environment variables
18. Queue, scheduler, storage-link, migration, seed, and run commands
19. Legal/privacy/Data Safety deliverables and owner-review status
20. Known limitations and remaining Flutter/Firebase/APNs/signing prerequisites

Do not claim a feature is complete unless it is implemented and covered by appropriate passing tests.
