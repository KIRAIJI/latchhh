# LATCH API v1

Base path: `/api/v1`

Protected endpoints require:

```http
Authorization: Bearer <sanctum-token>
Accept: application/json
```

JSON endpoints use `Content-Type: application/json`. Photo upload uses `multipart/form-data`.

All JSON names are `snake_case`. Timestamps are UTC ISO 8601 values. Coordinates, telemetry, and related objects remain `null` when unavailable; the API never invents tracker values.

## Response conventions

Success:

```json
{
  "success": true,
  "message": "Item retrieved successfully.",
  "data": {}
}
```

Validation error:

```json
{
  "success": false,
  "message": "The given data was invalid.",
  "code": "VALIDATION_ERROR",
  "errors": {
    "item_name": ["The item name field is required."]
  }
}
```

Login is limited to five attempts per minute for each normalized email and IP
combination. A blocked attempt returns `429`, a numeric `Retry-After` header,
and the same duration in the JSON body:

```json
{
  "success": false,
  "message": "Too many sign-in attempts. Try again shortly.",
  "code": "RATE_LIMITED",
  "retry_after_seconds": 42
}
```

Destructive `204` responses have no body. Missing and differently owned item resources both return `404`.

Opaque cursors are encrypted and bound to the user, filters, ordering, page size, and—where relevant—the item and claim epoch. A malformed cursor or changed filter returns `422`; a valid cursor reused across an inaccessible user/item/claim returns `404`. A follow-up may send the cursor alone.

Stable error codes are:

`AUTH_INVALID_CREDENTIALS`, `AUTH_UNAUTHENTICATED`, `AUTH_PASSWORD_INCORRECT`, `AUTH_PASSWORD_ALREADY_SET`, `OAUTH_INVALID_TOKEN`, `OAUTH_ACCOUNT_CONFLICT`, `OAUTH_REAUTHENTICATION_REQUIRED`, `OAUTH_NOT_CONFIGURED`, `EMAIL_NOT_VERIFIED`, `PASSWORD_RESET_INVALID`, `PASSWORD_SETUP_UNAVAILABLE`, `VALIDATION_ERROR`, `RATE_LIMITED`, `ITEM_NOT_FOUND`, `DEVICE_NOT_REGISTERED`, `DEVICE_ALREADY_CLAIMED`, `DEVICE_RELEASE_FAILED`, `GEOFENCE_OPERATION_FAILED`, `NOTIFICATION_NOT_FOUND`, `TRACKER_PROVIDER_UNAVAILABLE`, `TRACKER_DEVICE_NOT_FOUND`, `ACCOUNT_DELETION_FAILED`, and `INTERNAL_SERVER_ERROR`.

## Endpoints

| Method | Path | Authentication | Request | Success |
|---|---|---|---|---|
| POST | `/auth/register` | Public | `name`, `email`, password fields, `accepted_terms`, `acknowledged_privacy` | `201`, unverified user and bearer token |
| POST | `/auth/login` | Public | `email`, `password` | `200`, user and bearer token |
| POST | `/auth/oauth/google` | Public | Firebase `id_token`, `accepted_terms`, `acknowledged_privacy` | `200`, verified user and bearer token |
| POST | `/auth/forgot-password` | Public | `email` | Non-enumerating `202` |
| POST | `/auth/password/setup-link` | Bearer, passwordless account | — | `202`, queued Set Password email |
| POST | `/auth/reset-password` | Public | `email`, `token`, password fields | `204`, all sessions/installations revoked |
| GET | `/auth/password/reset-link` | Email link | `email`, `token`, optional `mode=setup` query | Redirect to `latch://reset-password` with the flow mode |
| GET | `/auth/email/verify/{id}/{hash}` | Signed email link | Signed query | Redirect to `latch://email-verified` |
| GET | `/auth/me` | Bearer | — | Current user |
| POST | `/auth/email/verification-notification` | Bearer | — | Idempotent `204` |
| POST | `/auth/logout` | Bearer | — | `204`, current token revoked |
| POST | `/auth/logout-all` | Bearer | — | `204`, all auth/push tokens revoked |
| PATCH | `/profile` | Verified bearer | `name`, `email`, conditional `current_password` | Updated user |
| PUT | `/profile/password` | Verified bearer | `current_password`, `password`, `password_confirmation` | `sessions_revoked` |
| POST | `/profile/photo` | Verified bearer | Multipart `photo` | Updated user |
| DELETE | `/profile/photo` | Verified bearer | — | Updated user |
| PATCH | `/settings` | Verified bearer | Global and optional category notification preferences | Updated user |
| DELETE | `/account` | Verified bearer | `current_password`, or recent `oauth_id_token` for a passwordless account | `204` |
| GET | `/items` | Verified bearer | — | All owned items, unpaginated |
| POST | `/items/claim` | Verified bearer | `device_uid`, `item_name` | `201`, claimed item |
| GET | `/items/{item}` | Verified bearer | — | Owned item |
| POST | `/items/{item}/refresh` | Verified bearer | — | Immediately synchronized owned item; rate limited |
| PATCH | `/items/{item}` | Verified bearer | `item_name` | Updated item |
| DELETE | `/items/{item}` | Verified bearer | — | `204`, item released |
| GET | `/items/{item}/geofence` | Verified bearer | — | Full geofence or `data: null` |
| PUT | `/items/{item}/geofence` | Verified bearer | Full geofence form | Saved geofence |
| DELETE | `/items/{item}/geofence` | Verified bearer | — | Idempotent `204` |
| GET | `/items/{item}/location-history` | Verified bearer | `from`, `to`, `cursor`, `per_page` | Oldest-first real positions |
| GET | `/activity` | Verified bearer | Activity filters and cursor | Global newest-first activity |
| GET | `/items/{item}/activity` | Verified bearer | Activity filters and cursor | Current-claim activity |
| GET | `/notifications` | Verified bearer | `type`, `read_state`, cursor | Newest-first feed |
| PATCH | `/notifications/{notification}/read` | Verified bearer | — | Updated notification |
| DELETE | `/notifications/{notification}` | Verified bearer | — | `204` |
| PATCH | `/notifications/read-all` | Verified bearer | — | Counts |
| POST | `/push-tokens` | Verified bearer | FCM `token`, `platform`, optional device/app metadata | `204` |
| DELETE | `/push-tokens` | Verified bearer | FCM `token` | `204` |
| DELETE | `/push-tokens/all` | Verified bearer | — | `204` |

Undocumented request fields are rejected instead of silently ignored.

## Authentication and user

Registration/login data:

```json
{
  "user": {
    "id": 1,
    "name": "Sample User",
    "email": "user@example.com",
    "email_verified": false,
    "email_verified_at": null,
    "has_password": true,
    "oauth_providers": [],
    "profile_photo_url": null,
    "notifications_enabled": true,
    "notification_preferences": {
      "geofence_events": true,
      "battery_events": true,
      "device_status_events": true
    },
    "created_at": "2026-07-26T08:00:00Z",
    "updated_at": "2026-07-26T08:00:00Z"
  },
  "token": "<returned-once>",
  "token_type": "Bearer"
}
```

Tokens expire after 30 days and there is no refresh endpoint. Password recovery is email based and intentionally returns the same `202` for existing and missing accounts. A signed-in passwordless account uses the dedicated setup-link endpoint, whose email, button, and app screen say **Set Password** rather than **Reset Password**. Successful setup or reset revokes all Sanctum and FCM installation tokens.

Email/password registrations must verify email before accessing application-data routes. Google/Firebase accounts enter as verified because Laravel verifies the signed Firebase token, verified provider email, audience, issuer, provider, timestamps, and stable subjects before issuing a Sanctum token. Existing accounts are linked only when the matching local email is already verified; an unverified collision returns `409`.

Changing email clears verification and requires `current_password`. Account deletion requires `current_password` for password accounts or a linked Google Firebase ID token with an authentication time no more than five minutes old for passwordless accounts.

Passwords require at least 8 characters, mixed case, a number, and a symbol, with no more than 72 UTF-8 bytes under bcrypt. Registration also requires accepted Terms and acknowledged Privacy fields; the backend stores the configured versions and acceptance times.

Photo types: JPEG, PNG, WebP; maximum 5 MiB. `profile_photo_url` is null or absolute. Storage paths are never returned.

## Item resource

```json
{
  "id": 1,
  "device_uid": "LATCH-7K3M-P9Q2",
  "item_name": "Black Backpack",
  "claimed_at": "2026-07-26T08:00:00Z",
  "location": {
    "latitude": 15.145,
    "longitude": 120.588,
    "recorded_at": "2026-07-26T08:30:00Z",
    "type": "current"
  },
  "status": {
    "connection": "online",
    "last_communication_at": "2026-07-26T08:30:00Z",
    "battery_percentage": 75,
    "battery_status": "normal",
    "gnss_status": "fixed",
    "satellites": 9,
    "hdop": 1.2,
    "gsm_csq": 18,
    "gsm_signal_level": "good"
  },
  "geofence": {
    "id": 2,
    "name": "Home",
    "radius_meters": 100,
    "is_active": true
  }
}
```

Enums:

- location: `current`, `last_known`, `unavailable`
- connection: `online`, `stale`, `offline`, `unknown`
- battery: `normal`, `low`, `critical`, `unknown`
- GNSS: `fixed`, `no_fix`, `unknown`
- signal: `excellent`, `good`, `fair`, `poor`, `no_signal`, `unknown`

`GET /items` returns every owned item sorted case-insensitively by name, then ID. It never calls Traccar.

Claim codes normalize to uppercase `LATCH-XXXX-XXXX`. Releasing removes the current geofence, notifications, cache, receipts, and location history but preserves the master UID-to-Traccar mapping and the former owner's global activity snapshots.

## Geofence

`PUT` requires all fields:

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

Radius is `50..5000` metres. Latitude is `-90..90`; longitude is `-180..180`. An identical `PUT` is a true no-op. Geometry changes and reactivation reset the evaluation baseline; name/flag-only changes retain it.

## Notifications

Types: `geofence_enter`, `geofence_exit`, `battery_low`, `battery_critical`, `device_offline`, `device_online`.

`GET /notifications` defaults to 50 and accepts up to 100. `read_state` is `all`, `unread`, or `read`. Metadata includes the unfiltered `unread_count` and `next_cursor`.

Turning off `notifications_enabled` suppresses future notification rows and push. The category preferences independently control geofence, battery, and device-status event families. Device/geofence transition state and factual activity continue to update, preventing retroactive alerts when re-enabled.

The notification row is the durable source of truth. A newly created allowed row queues best-effort FCM delivery; a duplicate `event_key` does not queue another push. FCM tokens are encrypted at rest and are never returned.

## Public legal and deletion pages

These web routes are intentionally outside `/api/v1`:

- `/privacy`
- `/terms`
- `/account-deletion`

The deletion page documents both in-app permanent deletion and the support-request path. `docs/data-safety.md` is a working declaration that must be reviewed against every released binary and the final published policies.

Location and activity details are documented separately.
