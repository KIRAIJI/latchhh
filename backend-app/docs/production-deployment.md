# Production Deployment

## Live environment

- API base URL: `https://161.118.252.4/api/v1`
- Public pages:
  - `https://161.118.252.4/privacy`
  - `https://161.118.252.4/terms`
  - `https://161.118.252.4/account-deletion`
- Support email: `latch.support@gmail.com`
- Server: Ubuntu on Oracle Cloud
- Web stack: Nginx and PHP-FPM
- Application database: local MariaDB on `127.0.0.1`
- Tracker provider: Traccar on the same host through its loopback API
- Application root: `/var/www/latch-backend`
- Active release: `/var/www/latch-backend/current`
- Shared environment and storage: `/var/www/latch-backend/shared`

The MariaDB instance runs inside the existing VPS; no paid Oracle managed
database is used. Traccar remains independently containerized and publicly
available on port `8082`.

## Android production configuration

Build the release with:

```powershell
flutter build appbundle --release --no-pub `
  --dart-define=LATCH_API_BASE_URL=https://161.118.252.4/api/v1 `
  --dart-define=LATCH_FIREBASE_ENABLED=true `
  --dart-define=LATCH_SUPPORT_EMAIL=latch.support@gmail.com
```

The Android application ID is `com.latch.mobile`. Production credentials,
keystores, service-account files, and passwords must stay outside both
repositories.

## Runtime services

Nginx, PHP-FPM, MariaDB, Supervisor, and the existing Traccar container must be
running. Supervisor keeps the Laravel database queue worker alive.
`/etc/cron.d/latch-backend` invokes Laravel's scheduler as `www-data` every
minute.

Useful read-only checks:

```bash
sudo systemctl status nginx php8.3-fpm mariadb supervisor
sudo supervisorctl status
sudo cat /etc/cron.d/latch-backend
sudo docker ps --filter name=traccar
cd /var/www/latch-backend/current && sudo -u www-data php artisan latch:diagnose
```

Do not print the production environment or service-account JSON while
troubleshooting.

## HTTPS

The server uses a trusted Let's Encrypt IP-address certificate and redirects
HTTP to HTTPS. Because IP certificates are short-lived, automatic renewal is
required. Certbot's systemd timer is enabled, and a deploy hook reloads Nginx
after renewal.

Check renewal without changing the active certificate:

```bash
sudo systemctl status snap.certbot.renew.timer
sudo certbot renew --dry-run --cert-name 161.118.252.4
```

Ports `80` and `443` must remain open in both the Oracle Cloud ingress rules and
the VPS firewall. Port `80` is also needed for normal certificate renewal.

## Releases and rollback

Each deployment is stored in `/var/www/latch-backend/releases`. The `current`
symbolic link selects the active release. Persistent uploads, environment
configuration, and secrets remain under `shared`.

After activating a release:

```bash
cd /var/www/latch-backend/current
sudo -u www-data php artisan migrate --force
sudo -u www-data php artisan optimize
sudo -u www-data php artisan queue:restart
sudo systemctl reload php8.3-fpm
```

To roll back application code, repoint `current` to the previously verified
release, reload PHP-FPM, and restart the queue workers. Database rollback is a
separate decision: first confirm that the previous code is compatible with the
current schema. Never run a destructive migration rollback automatically.

## Backups

A daily local backup runs at `03:17 UTC` and retains 14 days under
`/var/backups/latch/daily`. It includes the application database and managed
profile photos. Release/deployment backups are also kept under
`/var/backups/latch`.

Local backups protect against application mistakes but not loss of the VPS.
Before accepting real users, add an encrypted off-site copy and test a restore.
The encrypted backup/recovery plan must include Laravel's `APP_KEY`, because
stored FCM registration tokens cannot be decrypted without it.

## Initial production use

Production starts without user accounts. Register through the signed app using
Google or email/password, complete email verification when applicable, and then
claim the pre-registered tracker with:

```text
LATCH-JAEG-2JUB
```

The claim UID is safe to enter in the app but should be shared only with the
tracker's intended owner. Traccar credentials and identifiers must never be
embedded in Flutter.

## Release acceptance

Before a public store release:

1. Test registration, email verification, password reset, Google sign-in,
   logout, and account deletion using the signed production build.
2. Test tracker claiming, current location, history, geofence transitions, and
   notification deletion on a physical device.
3. Test push delivery in foreground, background, and process-terminated states.
   Android force-stop intentionally prevents delivery until the app is opened.
4. Owner-review the privacy policy, terms, deletion instructions, and Google
   Play Data Safety answers.
5. Add and restore-test encrypted off-site backups.
6. Prefer a stable owned domain before store publication so the app endpoint is
   not tied permanently to the VPS IP address.

An iOS release additionally requires a Mac, Apple Developer configuration, and
APNs credentials connected to Firebase.
