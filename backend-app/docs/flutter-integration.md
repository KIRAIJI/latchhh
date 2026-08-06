# Flutter integration and release checklist

The Flutter application is wired to Laravel for email/password and Google/Firebase authentication, session restoration, profile/photo/settings/account actions, items, real location history, activity, geofences, notifications, password change/recovery, email verification, and FCM token lifecycle. Flutter never calls Traccar directly.

## Runtime contract

- Configure the API with `--dart-define=LATCH_API_BASE_URL=https://api.example.com/api/v1`.
- Production rejects cleartext or loopback API URLs.
- Sanctum tokens use secure platform storage. A `401`, logout, logout-all, password reset, or account deletion clears the appropriate local session.
- Google sign-in uses `firebase_auth` and `google_sign_in`. Flutter sends the fresh Firebase ID token only to `/auth/oauth/google`; all later LATCH requests use the returned Sanctum token.
- Passwordless Google accounts can request a password-setup email. Their permanent deletion prompts for recent Google authentication instead of a nonexistent current password.
- Unverified authenticated users see the verification screen. They may resend verification, refresh `/auth/me`, open the signed email link, or sign out.
- Recovery email links pass through Laravel and open `latch://reset-password`; verification links return through `latch://email-verified`.
- Field-keyed `422` errors render beside their inputs. Password forms enforce the same mixed-case/number/symbol rule before submission.
- Notification taps select the Notifications tab, refresh the feed, open the matching detail, and preserve normal back navigation.
- The durable notification feed remains authoritative if push delivery is delayed or denied.

## Firebase and native push

Firebase is optional in debug (`LATCH_FIREBASE_ENABLED=false`) so ordinary local UI/API development does not require credentials. Release builds fail closed unless it is enabled.

Before a real release:

1. Use the permanent Android application ID and iOS bundle ID `com.latch.mobile`.
2. Install/configure FlutterFire and run `flutterfire configure` from `flutter-app`.
3. Add the generated Android/iOS Firebase client configuration for those exact IDs.
4. Enable Google under Firebase Console > Authentication > Sign-in method.
5. Register SHA-1 and SHA-256 fingerprints for every Android debug/release signing certificate, then refresh the generated Android configuration when Firebase changes it.
6. Set `--dart-define=LATCH_FIREBASE_ENABLED=true`.
7. Android: retain `POST_NOTIFICATIONS`, the `latch_alerts` default channel, and the app-link intent filter.
8. iOS: enable Push Notifications and Background Modes > Remote notifications in Xcode; upload an APNs authentication key to Firebase and configure Google sign-in for the iOS bundle.
9. Test Google create/login/link/cancel/logout and recent-reauth deletion, plus foreground, background, terminated, token-refresh, notification-permission-denied, and tap-navigation behavior on physical Android and iOS devices.

The Firebase service-account JSON is backend-only. Never bundle it with Flutter. For authentication Flutter sends a short-lived Firebase ID token; for push it sends its FCM registration token, platform, device label, and app version.

Crashlytics is enabled only outside debug after successful Firebase initialization. Do not add passwords, Sanctum/FCM tokens, tracker credentials, precise coordinates, or raw request/response bodies as crash keys or logs.

## Legal and store declarations

Flutter links its Privacy, Terms, and Data Safety screens to the backend's public HTTPS pages. Registration requires separate Terms acceptance and Privacy acknowledgement.

Before store submission:

- replace the placeholder support email with the real monitored address,
- have the operator/legal owner review the policies, version dates, retention language, and deletion instructions,
- publish `/privacy`, `/terms`, and `/account-deletion` on the production HTTPS domain,
- disclose Google Maps Platform as the map processor for coordinates shown in native maps,
- reconcile `docs/data-safety.md` against the exact release binary, SDK list, backend logging, and retention behavior.

## Release hardening

Provide:

- a permanent application ID/bundle ID,
- Android production keystore and `android/key.properties`,
- production API URL and support email,
- an Android-restricted Google Maps key in ignored `android/local.properties`, limited to `com.latch.mobile`, every installed signing certificate SHA-1, and Maps SDK for Android,
- Firebase client files and iOS APNs capability,
- tested production mail delivery for verification/recovery.

Suggested build defines:

```text
flutter build apk --release \
  --dart-define=LATCH_API_BASE_URL=https://api.example.com/api/v1 \
  --dart-define=LATCH_FIREBASE_ENABLED=true \
  --dart-define=LATCH_SUPPORT_EMAIL=support@example.com
```

Replace every example value. Release validation intentionally stops the build/runtime when these inputs are placeholders or insecure.

The Android app uses only the basic native Maps SDK. Do not enable or integrate a Map ID, Places, Routes, Street View, Google Geocoding web service, or another billable Maps SKU unless its cost, privacy, key restrictions, and store disclosures are separately reviewed. iOS needs its own iOS-restricted key and Maps SDK for iOS setup.

## Existing tracker UI requirements

- Render all owned items and only non-null provider coordinates.
- Use `POST /items/{item}/refresh` only for explicit user refreshes; routine screen loading reads the cached item list.
- Treat location-history/activity/notification cursors as opaque.
- Clear cached item/location/history/activity pages immediately after release or account deletion.
- Fetch the full saved geofence before editing and always submit its center, radius, flags, and active state together.
- Preserve null telemetry; do not infer a GNSS fix or invent a location because the device is offline.
