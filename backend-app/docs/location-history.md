# Real location history

`GET /api/v1/items/{item}/location-history` reads locally retained, validated Traccar fixes. It never generates fake points and never calls Traccar during the mobile request.

Query fields:

- `from`, `to`: paired UTC ISO 8601 timestamps
- `per_page`: `1..1000`, default `500`
- `cursor`: opaque next cursor

Without an explicit range, the initial page captures the 24-hour window ending at request start. Explicit ranges must satisfy `from < to`, `to <= now`, and the configured maximum span (default seven days).

Rows are filtered by authenticated user, item, current `claim_version`, claim time, and the captured retention cutoff. They are returned oldest first:

```json
{
  "success": true,
  "message": "Location history retrieved successfully.",
  "data": [
    {
      "latitude": 15.145,
      "longitude": 120.588,
      "recorded_at": "2026-07-26T08:30:00Z"
    }
  ],
  "meta": {
    "from": "2026-07-25T09:00:00Z",
    "to": "2026-07-26T09:00:00Z",
    "retention_cutoff": "2026-06-26T09:00:00Z",
    "next_cursor": null
  }
}
```

Only rows with `gnss_valid === true`, valid coordinates, a valid Traccar `fixTime`, and acceptable clock skew are retained. History contains no provider IDs, battery data, status, addresses, interpolation, smoothing, or reverse geocoding.

Cursors are encrypted and claim-bound. Send the returned cursor unchanged; cursor-only follow-ups retain the captured window and page size.

Default retention is 30 days and daily pruning is bounded. Release/account deletion removes claim-scoped points immediately. Reclaim starts empty, even for the same user.

Known v1 limitation: the synchronization overlap captures ordinary delayed reports, but a report arriving later than that overlap may not be retained.
