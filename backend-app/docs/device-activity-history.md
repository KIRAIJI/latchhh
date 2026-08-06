# Device activity history

Activity is an append-only audit timeline, separate from coordinate history.

Endpoints:

- `GET /api/v1/activity`
- `GET /api/v1/items/{item}/activity`

Both accept `event_type`, `source`, paired UTC `from`/`to`, `cursor`, and `per_page` (`1..100`, default 50). The global endpoint also accepts normalized `device_uid`; the item endpoint rejects it. Results are newest first.

Event types:

`item_claimed`, `item_renamed`, `item_released`, `geofence_created`, `geofence_updated`, `geofence_activated`, `geofence_deactivated`, `geofence_deleted`, `geofence_enter`, `geofence_exit`, `battery_low`, `battery_critical`, `device_offline`, `device_online`.

Sources: `user`, `tracker`, `geofence`, `system`.

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
  "occurred_at": "2026-07-26T08:30:00Z"
}
```

Global history remains owned by the user after release. `device.item_id` becomes null when the device is no longer owned in the same claim epoch, so released/prior-claim rows must not navigate to current item details. Item activity is current-owner/current-claim only.

The API never exposes user IDs, actors, claim versions, event keys, provider identifiers, coordinates, raw payloads, or another user's history. There are no public activity write/update/delete routes.

Automated facts are recorded even when notification creation is disabled. Deterministic event keys make synchronization retries idempotent. Default retention is 365 days.
