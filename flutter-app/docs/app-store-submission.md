# LATCH App Store Submission Worksheet

Use this worksheet when creating the LATCH record in App Store Connect. Values
marked **Required owner decision** must be supplied by the legal owner and must
not be invented by a developer.

## App identity

| Field | Value |
|---|---|
| App name | LATCH |
| Bundle ID | `com.latch.mobile` |
| SKU | **Required owner decision** |
| Primary language | English |
| Minimum iOS version | iOS 15 |
| Category | Suggested: Utilities |
| Secondary category | **Required owner decision** |
| Content rights | The owner must confirm rights to all branding and bundled E-Paper vendor reference assets |

## Store listing draft

### Subtitle

Track your essentials with LATCH.

### Promotional text

Monitor compatible LATCH trackers, review recent locations, manage geofences,
receive alerts, and update a compatible E-Paper display from one app.

### Description

LATCH helps you monitor compatible personal trackers and keep important item
information close at hand.

Features include:

- View claimed trackers and their latest reported locations
- Review recent location and device activity
- Configure a circular geofence with entry and exit alerts
- Receive device and geofence notifications
- Manage account and tracker settings
- Prepare and write images to a compatible NFC E-Paper display

Location availability, update frequency, and accuracy depend on tracker
hardware, connectivity, provider availability, and environmental conditions.
LATCH is not an emergency or life-safety service.

### Keywords

```text
tracker,item finder,location,geofence,alerts,e-paper,NFC
```

### URLs

| Field | Required value |
|---|---|
| Support URL | **Required owner decision: public HTTPS support page** |
| Privacy Policy URL | **Required owner decision: public HTTPS privacy page** |
| Marketing URL | Optional |
| Account deletion URL | Production `/account-deletion` page |

The support and privacy URLs must be accessible without authentication.

## App review information

| Field | Required value |
|---|---|
| Contact name | **Required owner decision** |
| Contact email | **Required owner decision** |
| Contact phone | **Required owner decision** |
| Demo account | **Required before review if the app cannot be fully reviewed without login** |
| Review notes | Explain tracker claiming, map data availability, NFC E-Paper hardware requirement, and how to reach each feature |

Do not give App Review a personal production account. Create a dedicated review
account and compatible test data that can be removed after review.

## Screenshot plan

Capture screenshots from a release-like physical iPhone with no personal data.
Prepare the current App Store Connect-required iPhone sizes.

Suggested sequence:

1. Items map with a non-sensitive demonstration tracker
2. Item details and status
3. Location history
4. Geofence editor
5. Notifications
6. E-Paper editor and preview
7. Profile and settings

Do not show real home addresses, personal email addresses, device identifiers,
API keys, notification tokens, or precise locations belonging to a real user.

## App Privacy working declaration

This is an implementation-backed worksheet, not an automatic App Store
submission. The owner must confirm the final answers against current Apple
definitions and the production deployment.

| Data type | Collected | Linked to user | Tracking | Purpose |
|---|---:|---:|---:|---|
| Name | Yes | Yes | No | Account management and app functionality |
| Email address | Yes | Yes | No | Authentication, verification, recovery, and support |
| User ID | Yes | Yes | No | Authentication and account management |
| Photos | Optional | Yes | No | Profile personalization |
| Precise location | Yes, tracker location | Yes | No | Tracker map, history, geofence, and alerts |
| Device ID / other identifiers | Yes | Yes | No | Tracker claim, push delivery, authentication, and security |
| Product interaction | Yes | Yes | No | Tracker operations, notifications, and activity history |
| Crash data | Yes when Crashlytics is enabled | Potentially | No | Diagnostics and app stability |
| Performance data | Confirm Firebase production configuration | Potentially | No | Diagnostics |

Current implementation does not include advertising, ad attribution, contacts,
microphone capture, payments, health data, browsing history, or Firebase
Analytics.

### Third-party processors to confirm

- Firebase Authentication
- Firebase Cloud Messaging / Apple Push Notification service
- Firebase Crashlytics
- Google Maps Platform
- Tracker provider / Traccar
- Production hosting and storage provider
- Transactional email provider

## Account deletion

The app includes in-app account deletion under Profile > Settings. The
production web deletion resource must also remain publicly reachable at:

```text
https://PRODUCTION_HOST/account-deletion
```

Replace `PRODUCTION_HOST` only after the final production domain is confirmed.

## Export compliance

The owner must answer App Store Connect's export-compliance questions based on
the final binary and distribution territory. The app uses standard platform and
HTTPS encryption through Flutter and its dependencies; this worksheet does not
make a legal export-classification determination.

## Age rating and safety

Confirm the current App Store age-rating questionnaire. The implementation does
not contain gambling, sexual content, user-generated public feeds, or graphic
violence. Location tracking and account functionality should be described
accurately.

The store description and review notes must state that:

- LATCH is not an emergency or life-safety service.
- Tracker location can be delayed or inaccurate.
- Users must track only property they own or are authorized to monitor.

## Pre-submission checklist

- [ ] Legal operator identity finalized
- [ ] Production support email no longer uses `example.com`
- [ ] Public privacy-policy URL live
- [ ] Public support URL live
- [ ] Public account-deletion URL live
- [ ] App ID `com.latch.mobile` registered
- [ ] Push Notifications enabled for the App ID
- [ ] NFC Tag Reading enabled for the App ID
- [ ] APNs authentication key uploaded to Firebase
- [ ] Google Maps iOS key restricted to `com.latch.mobile`
- [ ] Review account prepared
- [ ] Screenshots contain no personal data
- [ ] App Privacy answers confirmed by the owner
- [ ] Export compliance answered by the owner
- [ ] Physical iPhone smoke test passed
- [ ] Simple and complex E-Paper refresh passed on physical hardware
- [ ] TestFlight build passed internal testing
