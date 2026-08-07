# LATCH iPhone Build and Release Guide

Ang Flutter iOS target ng LATCH ay gumagamit ng:

- Minimum iOS version: **iOS 15**
- Bundle identifier: **`com.latch.mobile`**
- Firebase Cloud Messaging para sa push notifications
- Google Maps SDK for iOS
- Core NFC ISO 7816 para sa X1 E-Paper

## 1. Ihanda ang Mac

Install ang mga sumusunod:

- Latest compatible Xcode
- Flutter stable
- CocoaPods
- Git

Pagkatapos, patakbuhin:

```bash
sudo xcode-select -s /Applications/Xcode.app/Contents/Developer
sudo xcodebuild -runFirstLaunch
sudo xcodebuild -license
xcodebuild -downloadPlatform iOS

flutter doctor -v
flutter config --enable-swift-package-manager
```

Ayusin muna ang anumang error na lalabas sa `flutter doctor`.

Required ang Flutter Swift Package Manager support dahil ang
`pro_image_editor` dependency ay Swift Package Manager-only sa iOS.

## 2. Kunin ang repository

Para sa bagong checkout:

```bash
git clone https://github.com/KIRAIJI/latchhh.git
cd latchhh/flutter-app
flutter pub get
```

Kung cloned na ang repository:

```bash
git pull origin main
cd flutter-app
flutter pub get
```

## 3. I-configure ang Google Maps

Sa Google Cloud Console:

1. Enable ang **Maps SDK for iOS**.
2. Gumawa ng iOS API key.
3. Restrict ang API key sa iOS bundle identifier:

   ```text
   com.latch.mobile
   ```

4. Gumawa ng local file:

   ```text
   flutter-app/ios/Flutter/Local.xcconfig
   ```

5. Ilagay ang API key:

   ```xcconfig
   MAPS_API_KEY=YOUR_REAL_IOS_MAPS_API_KEY
   ```

Ang `Local.xcconfig` ay ignored ng Git para hindi ma-publish ang totoong API
key. May safe placeholder sa repository, pero hindi gagana ang map hangga't
walang real key sa local file.

## 4. Ihanda ang iOS dependencies

Mula sa `flutter-app` directory:

```bash
flutter config --enable-swift-package-manager
flutter pub get
```

Pagkatapos, buksan ang Xcode workspace:

```bash
open ios/Runner.xcworkspace
```

Laging buksan ang **`Runner.xcworkspace`**, hindi ang `Runner.xcodeproj`.

Huwag manual na patakbuhin ang `pod install`. Ang project ay gumagamit ng
Swift Package Manager-only plugin kasama ng CocoaPods-based Flutter plugins.
Hayaan ang `flutter build ios` o `flutter build ipa` na i-coordinate ang native
dependencies.

## 5. I-configure ang Apple signing

Sa Xcode:

1. Select ang **Runner** project.
2. Select ang **Runner** target.
3. Open **Signing & Capabilities**.
4. Enable **Automatically manage signing**.
5. Piliin ang tamang Apple Developer Team.
6. Confirm na ang Bundle Identifier ay:

   ```text
   com.latch.mobile
   ```

Sa Apple Developer portal, gumawa o i-confirm ang explicit App ID na:

```text
com.latch.mobile
```

Enable ang:

- Push Notifications
- Near Field Communication Tag Reading

Kapag binago ang App ID capabilities, maaaring kailangang gumawa ulit ng
provisioning profiles.

## 6. I-confirm ang Xcode capabilities

Sa **Runner > Signing & Capabilities**, dapat present ang:

- Push Notifications
- Background Modes
  - Remote notifications
- Near Field Communication Tag Reading

Naka-configure na sa repository ang:

- NFC entitlements
- NFC usage description
- ISO 7816 application identifier `D2760000850101`
- Photo-library permission
- Firebase configuration reference
- Google Sign-In URL scheme
- `latch://` custom URL scheme
- iOS 15 deployment target

## 7. I-configure ang Firebase iOS app

Sa Firebase Console:

1. Add o i-confirm ang Apple/iOS app.
2. Ang Bundle ID ay dapat eksaktong:

   ```text
   com.latch.mobile
   ```

3. Download ang `GoogleService-Info.plist`.
4. Confirm na ang file ay nasa:

   ```text
   flutter-app/ios/Runner/GoogleService-Info.plist
   ```

May existing plist ang repository. I-verify na galing ito sa tamang Firebase
project at para talaga sa `com.latch.mobile`.

### APNs authentication key

Para gumana ang Firebase push notifications:

1. Sa Apple Developer portal, gumawa ng APNs Authentication Key.
2. Download ang `.p8` file.
3. Itabi ang:
   - APNs Key ID
   - Apple Team ID
4. Sa Firebase Console, pumunta sa:
   - Project Settings
   - Cloud Messaging
   - Apple app configuration
5. Upload ang `.p8` APNs authentication key.
6. Ilagay ang Key ID at Team ID.

Huwag i-commit sa Git ang `.p8` file o ibang signing credentials.

## 8. Ikonekta ang physical iPhone

Sa iPhone:

1. Enable ang **Developer Mode**.
2. Connect ang iPhone sa Mac gamit ang USB.
3. Tap **Trust This Computer**.
4. Confirm na nakikita ito ng Flutter:

   ```bash
   flutter devices
   ```

5. Patakbuhin ang app:

   ```bash
   flutter run -d YOUR_IPHONE_DEVICE_ID
   ```

Kung may signing error, buksan ang `Runner.xcworkspace` sa Xcode at i-run ang
Runner target directly nang isang beses.

## 9. Physical iPhone test checklist

I-test ang bawat feature:

- [ ] App startup
- [ ] Registration at email/password login
- [ ] Google Sign-In
- [ ] Items map rendering
- [ ] Location at address lookup
- [ ] Profile image selection
- [ ] Push notification permission
- [ ] Foreground push notification
- [ ] Background push notification
- [ ] `latch://` deep link
- [ ] NFC availability
- [ ] X1 E-Paper tag discovery
- [ ] Simple white/control-frame E-Paper write
- [ ] Representative complex-image E-Paper write
- [ ] Successful physical panel refresh

Para sa NFC, panatilihin ang upper portion ng iPhone malapit sa tag hanggang
matapos ang transfer at refresh. Huwag alisin agad ang phone kapag nagsimula na
ang write.

Supported sa code ang dalawang posibleng Core NFC representations:

- ISO 7816
- MiFare ISO 7816 fallback

Hindi sapat ang successful build para masabing successful ang E-Paper write.
Kailangan makita ang actual panel refresh sa physical iPhone at tag.

Hindi kayang i-validate ng iOS Simulator ang:

- Core NFC
- Actual APNs delivery
- Physical E-Paper refresh

## 10. Local verification bago gumawa ng release

Mula sa `flutter-app`:

```bash
flutter clean
flutter config --enable-swift-package-manager
flutter pub get

flutter analyze
flutter test
```

Ayusin muna ang anumang error bago gumawa ng archive.

Ang GitHub macOS CI ay nagko-compile ng parehong debug simulator build at
unsigned iOS release build. Nakikita nito ang Xcode, SwiftPM, CocoaPods, at
release-mode compilation issues bago pa gamitin ang local Mac. Hindi nito
pinapalitan ang signed physical-device at TestFlight validation.

## 11. Gumawa ng TestFlight build

Increment ang `version` sa `pubspec.yaml`. Halimbawa:

```yaml
version: 1.0.0+2
```

Ang value pagkatapos ng `+` ay kailangang tumaas sa bawat bagong App Store
Connect upload.

Gumawa ng release IPA:

```bash
flutter build ipa --release
```

Makikita ang output sa:

```text
build/ios/archive/
build/ios/ipa/
```

Upload gamit ang isa sa mga sumusunod:

- Xcode Organizer
- Apple Transporter
- App Store Connect upload tooling

Sa Xcode Organizer:

1. Validate App
2. Resolve ang anumang signing o entitlement error
3. Distribute App
4. Upload to App Store Connect
5. Hintayin ang processing
6. Add ang build sa TestFlight

## 12. Release acceptance checklist

Bago ibigay sa testers:

- [ ] Tama ang version at build number
- [ ] Automatic signing succeeds
- [ ] Release IPA builds successfully
- [ ] App Store validation succeeds
- [ ] Google Maps renders on a real iPhone
- [ ] Google Sign-In succeeds
- [ ] Firebase login succeeds
- [ ] APNs/FCM push notification succeeds
- [ ] Background notification succeeds
- [ ] Simple E-Paper frame refresh succeeds
- [ ] Complex E-Paper frame refresh succeeds
- [ ] Walang crash sa startup at primary navigation

## Important files

- `ios/Podfile`
- `ios/Flutter/Local.defaults.xcconfig`
- `ios/Flutter/Local.xcconfig` — local secret, not committed
- `ios/Runner/AppDelegate.swift`
- `ios/Runner/Info.plist`
- `ios/Runner/Runner.entitlements`
- `ios/Runner/GoogleService-Info.plist`
- `ios/Runner.xcworkspace`
- `lib/latch_ui/features/epaper/data/x1_iso7816_transport.dart`
