# LATCH Backend

Laravel 12 REST API for the LATCH Flutter application and its Traccar-backed physical trackers. Flutter communicates only with this API; Traccar credentials never belong in the mobile app.

The backend provides Sanctum email/password and Google/Firebase authentication, strong password/session management, email verification, password recovery, profile photos and settings, device claiming, real retained location history, item activity, circular geofences, tracker synchronization, durable in-app notifications plus FCM HTTP v1 delivery, granular alert preferences, public legal/deletion pages, retention jobs, and read-only diagnostics.

## Requirements

- PHP 8.2 or newer with the normal Laravel extensions
- Composer
- MySQL 8 or MariaDB
- A database queue worker and database cache
- A reachable Traccar API for device registration and scheduled synchronization
- A production mail provider for recovery and verification
- A Firebase project/service account for production Google Authentication, server-side ID-token verification/cleanup, and system push

Application, worker, scheduler, cache, and queue must share the same database and use UTC.

## Local setup

```bash
composer install
cp .env.example .env
php artisan key:generate
php artisan migrate
php artisan storage:link
```

Configure the database and Traccar variables in `.env`. Local mail defaults to logs and FCM remains disabled until real credentials are provided. Never copy the tracker credentials formerly embedded in Flutter; rotate them and store the replacements only in the backend environment.

Optional local/testing sample data:

```bash
php artisan db:seed --class=DevelopmentSeeder
```

`DevelopmentSeeder` refuses to run outside `local` or `testing`. Do not run it in production.

Pre-register a verified tracker:

```bash
php artisan latch:register-device LATCH-7K3M-P9Q2 tracker-unique-id --tracker-device-id=123
```

There is no offline registration bypass. The command verifies both Traccar identifiers before creating the unclaimed master device.

Run the background processes locally:

```bash
php artisan queue:work --tries=3
php artisan schedule:work
```

Run verification:

```bash
php artisan test
php artisan latch:diagnose
```

`latch:diagnose` is read-only. A non-zero exit means a required dependency/configuration check failed, a claimed item is stale, or failed jobs exist.

## Production

- Set `APP_ENV=production`, `APP_DEBUG=false`, and HTTPS `APP_URL`.
- Use HTTPS for Laravel and any remotely networked Traccar endpoint. An explicitly enabled
  loopback-only backend connection may use HTTP because the traffic never leaves the host.
- Run a supervised `php artisan queue:work --tries=3`.
- Run `php artisan schedule:run` every minute from cron or the platform scheduler.
- Run `php artisan migrate --force` during deployment.
- Run `php artisan storage:link` once on persistent/shared storage.
- Restart queue workers after every release with `php artisan queue:restart`.
- Use encrypted database backups because location history is sensitive.
- Configure production mail, `GOOGLE_OAUTH_ENABLED=true`, `FCM_ENABLED=true`, the shared Firebase project ID, and an absolute readable service-account path outside the repository/web root.
- Publish and owner-review `/privacy`, `/terms`, `/account-deletion`, the real support address, and the store Data Safety declaration.

Normal API reads use Laravel's local cache tables and remain available during a Traccar outage.

## Documentation

- [API contract](docs/api.md)
- [Password and session security](docs/password-change.md)
- [Location history](docs/location-history.md)
- [Device activity history](docs/device-activity-history.md)
- [Flutter integration](docs/flutter-integration.md)
- [Traccar integration](docs/traccar-integration.md)
- [Operations](docs/operations.md)
- [Production deployment](docs/production-deployment.md)
- [Testing](docs/testing.md)
- [Data Safety working declaration](docs/data-safety.md)
- [HTTP request examples](docs/latch-api.http)

The authoritative scope and acceptance contract remains the repository-level `backend.md`.
