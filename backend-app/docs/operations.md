# Operations

## Deployment

```bash
composer install --no-dev --optimize-autoloader
php artisan migrate --force
php artisan storage:link
php artisan optimize
php artisan queue:restart
```

Before `optimize`, production configuration must pass the application's fail-closed checks:

- `APP_DEBUG=false` and public HTTPS `APP_URL`
- a real SMTP/API mail transport and verified sender for recovery/verification
- `GOOGLE_OAUTH_ENABLED=true`, the Firebase project ID, and readable backend service-account credentials
- `FCM_ENABLED=true`, the Firebase project ID, and an absolute readable service-account path outside the repository/web root
- real legal support email and reviewed legal version identifiers
- HTTPS Traccar endpoint with rotated credentials

Never commit or place Firebase service-account JSON or Traccar credentials in public storage. Restrict the service-account file to the application/worker identity. Use the same Firebase project for mobile Google Authentication, backend ID-token verification, Firebase-user cleanup, and FCM.

Use a supervised database queue worker:

```bash
php artisan queue:work --tries=3
```

Invoke the scheduler every minute. On the production VPS this is installed as
`/etc/cron.d/latch-backend`, runs as `www-data`, and uses the active-release
symlink:

```cron
* * * * * www-data cd /var/www/latch-backend/current && /usr/bin/php artisan schedule:run >> /dev/null 2>&1
```

`php artisan schedule:work` is for local development only.

Application, workers, scheduler, database cache, and database queue must share the same database and use UTC. `DB_QUEUE_RETRY_AFTER` must remain greater than the 90-second synchronization timeout.

## Scheduled work

- every minute: dispatch unique tracker synchronization jobs
- daily: prune receipts older than 24 hours
- daily: prune retained positions/activity using configured retention
- daily: remove aged, unreferenced managed profile photos
- daily: prune expired Sanctum tokens

Profile-photo deletion jobs use the database connection explicitly and are inserted transactionally with profile/account changes.

## Diagnostics

```bash
php artisan latch:diagnose
```

Checks are read-only and redacted:

- database read
- configured cache/queue/failed-job table readability
- registered scheduler entries
- public storage link
- Traccar configuration shape/presence (no live call)
- aggregate users/items/geofences/notifications/history/activity/jobs

A claimed item is stale when `COALESCE(last_synced_at, claimed_at)` is earlier than `now - TRACKER_SYNC_STALE_SECONDS`. The exact boundary is not stale. Any required failure, stale item, or failed job makes the exit status non-zero. Queued jobs alone are informational.

Diagnostics cannot prove cache writes, worker liveness, or cron execution because they deliberately perform no writes and store no heartbeat.

## Local FCM transport test

After the Flutter app has registered a push token, send a real FCM message without creating a notification/history row:

```bash
php artisan latch:test-push
```

Use `--user-id=<id>` only when a local database has multiple registered installations. The command is unavailable outside `local` and `testing`, never prints the token or account email, removes a token rejected as invalid, and reports success only after FCM accepts the message.

For Android validation, test while the app is in the foreground, background, and process-killed state. Do not use OS force-stop as a substitute for termination because Android intentionally blocks delivery until the user opens a force-stopped app again. A transport-test tap only opens LATCH; real tracker notifications additionally exercise notification-detail navigation.

## Storage and retention

Profile photos use `storage/app/public/profile-photos/{user}` and require `public/storage`.

Defaults:

- location history: 30 days; maximum request range 7 days
- activity: 365 days; maximum request range 90 days
- tracker receipts: 24 hours
- Sanctum tokens: 30-day expiration plus daily pruning

Use HTTPS and encrypted backups. Coordinates and provider payloads must not enter routine logs.

FCM registration tokens are encrypted in the database and located by SHA-256 hash. Include the application key and secret-management procedure in the encrypted backup/recovery plan; losing `APP_KEY` makes encrypted values unreadable. Logout removes the current installation token, while logout-all, successful password reset, and account deletion remove all installations for that user.

## Mail, push, legal, and deletion checks

Before each release:

1. Send a verification and password-reset email through production and confirm both HTTPS links open the installed app.
2. Test Google create/login/link/cancel/logout and recent-reauth account deletion on a physical signed build.
3. Send a real notification to physical Android and iOS devices in foreground, background, and terminated states.
4. Confirm denied notification permission does not break the in-app feed.
5. Confirm `/privacy`, `/terms`, and `/account-deletion` are publicly reachable without authentication.
6. Reconcile `docs/data-safety.md` with the exact SDK dependency tree, log destinations, backups, retention jobs, support process, and store form.
7. Test in-app account deletion and the published external deletion-request channel.

Policy text and store answers are operational/legal assertions, not values engineering can safely invent. The operator must provide the legal identity, monitored contact, jurisdiction-specific language, and final retention exceptions.

## Failure handling

API reads remain cache-backed during Traccar outages. Failed sync does not advance the cursor. Review:

```bash
php artisan queue:failed
php artisan latch:diagnose
```

After correcting an external issue, retry selected failed jobs through Laravel's normal queue commands. Never put credentials or raw payloads in tickets/log excerpts.
