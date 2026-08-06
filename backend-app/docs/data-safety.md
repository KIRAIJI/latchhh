# Google Play Data Safety Working Declaration

This file is the implementation-backed working sheet for the Play Console owner. It is not submitted automatically. Re-check it whenever dependencies, backend processing, retention, or third-party providers change.

## Security and deletion answers

- Data is encrypted in transit in production: **Yes**, provided `APP_URL`, Flutter `LATCH_API_BASE_URL`, storage URLs, Traccar, and mail provider all use TLS.
- Account deletion is available in-app: **Yes**, under Profile → Settings.
- Account deletion web resource: `/account-deletion`.
- Independent security review: **No**, unless a qualifying external assessment is completed.

## Data collected

| Play category | LATCH data | Required? | Purpose | Shared with |
|---|---|---:|---|---|
| Personal info / Name | Account display name | Yes | Account management, app functionality | Hosting processor |
| Personal info / Email | Login, Google Authentication linking, verification, recovery | Yes | Account management, security | Google/Firebase Authentication, email provider |
| Photos | Optional profile photo | No | Personalization | Hosting/storage processor |
| Precise location | Tracker coordinates and geofence centers | Core tracker use | App functionality, notifications | Tracker provider, hosting, Google Maps Platform when coordinates are rendered on a map |
| Device or other IDs | Claim UID, tracker IDs, Google/Firebase account subjects, push registration token | Authentication/tracker/push use | Account management, app functionality, notifications, security | Google/Firebase Authentication, Traccar, Firebase Cloud Messaging |
| App activity | Claims, renames, releases, geofence and device transitions | Yes | Functionality, security, audit history | Hosting processor |
| App info and performance | Crash stack, app version, device platform when Firebase is enabled | Production default | Diagnostics, security | Firebase Crashlytics |
| Other data requiring owner classification | Password hash, session/security identifiers, hashed rate-limit keys | Yes | Authentication, abuse prevention, security | Hosting processor |

## Not collected by the current implementation

- Contacts, SMS, call logs, calendar, microphone, advertising ID, payment data, health data, browsing history, and user-generated public content.
- Firebase Analytics is not included. If it is added later, update this declaration and the Privacy Policy before release.

## Retention defaults

- Precise location history: 30 days.
- Device activity history: 365 days.
- Notifications: until user deletion, item release where applicable, or account deletion.
- Push tokens: until logout/token invalidation/account deletion.
- Google/Firebase identity link: until account deletion; backend cleanup then removes the corresponding Firebase Authentication user.
- Profile photo: until replacement/removal/account deletion.
- Password reset token: Laravel broker expiry (default 60 minutes).
- Android app data: cloud backup and device-transfer extraction are disabled by the manifest/rules; server data remains subject to the periods above.

## Required owner review before submission

1. Replace the support email and identify the legal operator.
2. Confirm the actual hosting region, backup expiry, mail processor, tracker processor, Firebase settings, and whether crash collection is optional by jurisdiction.
3. Confirm whether every row is “collected,” “shared,” optional/required, ephemeral, and user-controllable under Google's current definitions; processor treatment can depend on contracts and configuration.
4. Confirm Play SDK Index disclosures for every shipped SDK.
5. Submit the form in Play Console; keeping this Markdown file does not satisfy submission.
