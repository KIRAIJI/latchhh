# LATCH

Flutter client for the LATCH tracker, geofence, notification, profile, and
NFC-powered E-Paper features.

Permanent Android application ID and Apple bundle ID: `com.latch.mobile`.

## Laravel API

Flutter talks only to the Laravel `/api/v1` API. Traccar credentials and
identifiers must never be added to this project.

The default API URL is:

- Android emulator:
  `http://10.0.2.2/latch/backend-app/public/api/v1`
- Other local platforms:
  `http://localhost/latch/backend-app/public/api/v1`

For a physical phone, use the development computer's LAN address:

```bash
flutter run --dart-define=LATCH_API_BASE_URL=http://192.168.1.10/latch/backend-app/public/api/v1
```

For release builds, provide the HTTPS API, Firebase, and support values:

```bash
flutter build apk --release \
  --dart-define=LATCH_API_BASE_URL=https://api.example.com/api/v1 \
  --dart-define=LATCH_FIREBASE_ENABLED=true \
  --dart-define=LATCH_SUPPORT_EMAIL=support@example.com
```

Replace every example. Release validation also requires a permanent Android
application ID and production keystore.

## Google Maps

Android maps use the native Google Maps SDK. Add the Android-restricted key only
to the ignored `android/local.properties` file:

```properties
MAPS_API_KEY=your_restricted_android_key
```

Restrict the key to package `com.latch.mobile`, every installed signing
certificate SHA-1, and only **Maps SDK for Android**. The release build fails if
the key is absent or still the non-secret default. Never commit or send the key
in chat. No Map ID, Places, Routes, Street View, Google Geocoding web service, or
other paid Maps SKU is configured by this app.

The location-history text uses the platform geocoder independently of the Maps
SDK. iOS requires a separate iOS-restricted key and Maps SDK for iOS setup before
an iOS release.

Sanctum tokens are stored with platform secure storage. Android release builds
have Internet permission but do not permit cleartext HTTP; local cleartext is
enabled only in Android debug/profile manifests.

## Email, push notifications, and crash reporting

Forgot-password and email-verification links use the `latch://` mobile scheme.
Laravel sends the emails; the app never holds mail or Traccar credentials.

Firebase is deliberately optional in debug. To test real system notifications:

1. Choose the permanent Android application ID and iOS bundle ID.
2. Run `flutterfire configure` for the Firebase project.
3. Enable iOS Push Notifications and Remote notifications, then upload the APNs
   authentication key to Firebase.
4. Run with `--dart-define=LATCH_FIREBASE_ENABLED=true`.
5. Test notification permission, foreground/background/terminated delivery,
   token refresh, taps, and logout on physical devices.

The Firebase service-account JSON belongs only on the Laravel server. Do not add
it to this Flutter project. Crashlytics collects only in non-debug Firebase
builds; do not attach credentials, bearer/FCM tokens, precise coordinates, or
raw API payloads.

Registration requires separate Terms acceptance and Privacy acknowledgement.
Before publishing, replace the support-email placeholder and have the operator
review the public Privacy, Terms, account-deletion, and Data Safety disclosures.

After signing in, claim the registered tracker with:

```text
LATCH-JAEG-2JUB
```

## What This App Does

- Uploads an image from gallery.
- Resizes/crops to the panel resolution: `240x416`.
- Converts to monochrome `1-bit` (with optional dithering/invert).
- Packs pixels into a raw frame buffer (`12480` bytes).
- Writes to tag with either:
	- `IsoDep Raw` command mode (recommended for e-paper transfer)
	- `NDEF` mode (only for very small payloads)

## Why IsoDep Raw Mode

Your NFC scan shows low NDEF capacity (around 498 bytes), which is too small for
a full 1-bit image frame. Full-frame transfer typically needs command/chunk
upload over ISO-DEP APDUs.

## Setup

1. Install Flutter dependencies:

```bash
flutter pub get
```

2. Keep Apache and MySQL running in XAMPP. Run the Laravel queue and scheduler
   in separate terminals:

```bash
cd ../backend-app
php artisan queue:work
php artisan schedule:work
```

3. Run on an Android phone with NFC:

```bash
flutter run
```

## Protocol Configuration

In `IsoDep Raw` mode, fill these fields from your tag vendor protocol docs:

- `Chunk APDU Prefix (hex)`
- `Chunk size bytes`
- `Init APDUs` (optional, one command per line)
- `Finalize APDUs` (optional, one command per line)

Default chunk prefix is `90D60000` as a placeholder pattern only.
Replace it with your panel/tag specific APDU format.
