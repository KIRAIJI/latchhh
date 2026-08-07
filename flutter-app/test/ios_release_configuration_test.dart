import 'dart:convert';
import 'dart:io';

import 'package:flutter_test/flutter_test.dart';
import 'package:image/image.dart' as img;

void main() {
  const bundleId = 'com.latch.mobile';
  const nfcApplicationId = 'D2760000850101';

  test('Xcode, Firebase, and URL schemes use the same iOS identity', () {
    final project = File(
      'ios/Runner.xcodeproj/project.pbxproj',
    ).readAsStringSync();
    final info = File('ios/Runner/Info.plist').readAsStringSync();
    final firebase = File(
      'ios/Runner/GoogleService-Info.plist',
    ).readAsStringSync();

    expect(project, contains('PRODUCT_BUNDLE_IDENTIFIER = $bundleId;'));
    expect(firebase, contains('<string>$bundleId</string>'));
    expect(
      info,
      contains(
        'com.googleusercontent.apps.'
        '1058513152685-h48jm2fhmo1j3ocj2vmg9b2rj1ad6ne0',
      ),
    );
    expect(info, contains('<string>latch</string>'));
    expect(info, contains('<string>comgooglemaps</string>'));
    expect(info, contains('<string>googlechromes</string>'));
  });

  test('iOS deployment and native capabilities remain release-ready', () {
    final project = File(
      'ios/Runner.xcodeproj/project.pbxproj',
    ).readAsStringSync();
    final podfile = File('ios/Podfile').readAsStringSync();
    final info = File('ios/Runner/Info.plist').readAsStringSync();
    final entitlements = File(
      'ios/Runner/Runner.entitlements',
    ).readAsStringSync();

    expect(project, isNot(contains('IPHONEOS_DEPLOYMENT_TARGET = 13.0;')));
    expect(project, contains('IPHONEOS_DEPLOYMENT_TARGET = 15.0;'));
    expect(podfile, contains("platform :ios, '15.0'"));
    expect(info, contains('<string>$nfcApplicationId</string>'));
    expect(info, contains('<key>NFCReaderUsageDescription</key>'));
    expect(info, contains('<key>NSPhotoLibraryUsageDescription</key>'));
    expect(info, contains('<string>remote-notification</string>'));
    expect(
      entitlements,
      contains('com.apple.developer.nfc.readersession.formats'),
    );
    expect(entitlements, contains('<string>TAG</string>'));
    expect(entitlements, contains('<key>aps-environment</key>'));
  });

  test('Maps key uses the ignored local xcconfig override', () {
    final debugConfig = File('ios/Flutter/Debug.xcconfig').readAsStringSync();
    final releaseConfig = File(
      'ios/Flutter/Release.xcconfig',
    ).readAsStringSync();
    final defaults = File(
      'ios/Flutter/Local.defaults.xcconfig',
    ).readAsStringSync();
    final gitignore = File('ios/.gitignore').readAsStringSync();
    final appDelegate = File('ios/Runner/AppDelegate.swift').readAsStringSync();

    for (final config in <String>[debugConfig, releaseConfig]) {
      expect(config, contains('#include "Local.defaults.xcconfig"'));
      expect(config, contains('#include? "Local.xcconfig"'));
    }
    expect(defaults, contains('MAPS_API_KEY=DEFAULT_API_KEY'));
    expect(gitignore, contains('Flutter/Local.xcconfig'));
    expect(appDelegate, contains('GMSServices.provideAPIKey(mapsApiKey)'));
    expect(appDelegate, contains('mapsApiKey != "DEFAULT_API_KEY"'));
  });

  test('all declared iOS icons exist at their declared dimensions', () {
    final iconDirectory = Directory(
      'ios/Runner/Assets.xcassets/AppIcon.appiconset',
    );
    final contents =
        jsonDecode(
              File('${iconDirectory.path}/Contents.json').readAsStringSync(),
            )
            as Map<String, dynamic>;
    final images = contents['images']! as List<dynamic>;

    for (final entry in images.cast<Map<String, dynamic>>()) {
      final filename = entry['filename']! as String;
      final size = double.parse((entry['size']! as String).split('x').first);
      final scale = int.parse((entry['scale']! as String).replaceAll('x', ''));
      final expectedPixels = (size * scale).round();
      final file = File('${iconDirectory.path}/$filename');

      expect(file.existsSync(), isTrue, reason: '$filename is missing');
      final decoded = img.decodeImage(file.readAsBytesSync());
      expect(decoded, isNotNull, reason: '$filename is not a valid image');
      expect(decoded!.width, expectedPixels, reason: '$filename width');
      expect(decoded.height, expectedPixels, reason: '$filename height');
    }
  });

  test('App Store marketing icon is opaque', () {
    final icon = img.decodePng(
      File(
        'ios/Runner/Assets.xcassets/AppIcon.appiconset/'
        'Icon-App-1024x1024@1x.png',
      ).readAsBytesSync(),
    );

    expect(icon, isNotNull);
    for (final pixel in icon!) {
      expect(pixel.a, 255, reason: 'App Store icon must not be transparent');
    }
  });
}
