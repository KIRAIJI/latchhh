import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:geocoding/geocoding.dart';
import 'package:latch/latch_ui/core/services/location_address_resolver.dart';
import 'package:latch/latch_ui/data/api/latch_api.dart';
import 'package:latch/latch_ui/data/models/latch_models.dart';
import 'package:latch/latch_ui/features/items/presentation/widgets/item_details_bottom_sheet.dart';
import 'package:latch/latch_ui/integration/item_presentation_adapter.dart';

void main() {
  group('human-readable place names', () {
    test('filters Plus Codes from the locality fallback', () {
      const plusCodeOnly = Placemark(
        name: '4HWV+2MX',
        street: '4HWV+2MX, Angeles, 2009 Pampanga, Philippines',
        locality: 'Angeles',
        subAdministrativeArea: 'Pampanga',
        administrativeArea: 'Central Luzon',
        country: 'Philippines',
        postalCode: '2009',
      );

      expect(
        LocationAddressResolver.formatPlacemarks(const [plusCodeOnly]),
        'Angeles, Pampanga, Central Luzon, Philippines',
      );
    });

    test('prefers a returned venue name over an address code', () {
      const codedAddress = Placemark(
        name: '4HWV+2MX',
        locality: 'Angeles',
        subAdministrativeArea: 'Pampanga',
        administrativeArea: 'Central Luzon',
        country: 'Philippines',
      );
      const university = Placemark(
        name: 'Angeles University Foundation',
        thoroughfare: 'MacArthur Highway',
        locality: 'Angeles',
        subAdministrativeArea: 'Pampanga',
        administrativeArea: 'Central Luzon',
        country: 'Philippines',
      );

      expect(
        LocationAddressResolver.formatPlacemarks(const [
          codedAddress,
          university,
        ]),
        'Angeles University Foundation, MacArthur Highway, Angeles, Pampanga, '
        'Central Luzon, Philippines',
      );
    });

    test('removes repeated locality components from compound addresses', () {
      const pandacaqui = Placemark(
        name: 'Pandacaqui',
        street: 'Pandacaqui, Mexico',
        locality: 'Mexico',
        subAdministrativeArea: 'Pampanga',
        administrativeArea: 'Central Luzon',
        country: 'Philippines',
      );

      expect(
        LocationAddressResolver.formatPlacemarks(const [pandacaqui]),
        'Pandacaqui, Mexico, Pampanga, Central Luzon, Philippines',
      );
    });
  });

  test('user model preserves verification and granular preferences', () {
    final user = LatchUser.fromJson({
      'id': 3,
      'name': 'Verified User',
      'email': 'verified@example.com',
      'email_verified': true,
      'has_password': false,
      'oauth_providers': ['google'],
      'profile_photo_url': null,
      'notifications_enabled': true,
      'notification_preferences': {
        'geofence_events': false,
        'battery_events': true,
        'device_status_events': false,
      },
    });

    expect(user.emailVerified, isTrue);
    expect(user.hasPassword, isFalse);
    expect(user.oauthProviders, ['google']);
    expect(user.notificationsEnabled, isTrue);
    expect(user.notifyGeofenceEvents, isFalse);
    expect(user.notifyBatteryEvents, isTrue);
    expect(user.notifyDeviceStatusEvents, isFalse);
  });

  test('item model preserves unavailable telemetry as null', () {
    final item = LatchItem.fromJson({
      'id': 7,
      'device_uid': 'LATCH-JAEG-2JUB',
      'item_name': 'Backpack',
      'claimed_at': '2026-07-26T01:00:00.000000Z',
      'location': {
        'latitude': null,
        'longitude': null,
        'recorded_at': null,
        'type': 'unavailable',
      },
      'status': {
        'connection': 'unknown',
        'last_communication_at': null,
        'battery_percentage': null,
        'battery_status': 'unknown',
        'gnss_status': 'unknown',
        'satellites': null,
        'hdop': null,
        'gsm_csq': null,
        'gsm_signal_level': 'unknown',
      },
      'geofence': null,
    });

    expect(item.deviceUid, 'LATCH-JAEG-2JUB');
    expect(item.location.hasCoordinates, isFalse);
    expect(item.status.batteryPercentage, isNull);
    expect(item.status.gsmCsq, isNull);
    expect(item.geofence, isNull);

    final card = ItemPresentationAdapter.buildItems([item]).single;
    expect(card.batteryLabel, 'Battery not reported');
    expect(card.itemDetails?.batteryStatusText, 'Not reported by tracker');
    expect(card.itemDetails?.signalLevelText, 'Not reported by tracker');
  });

  test('API validation errors remain field keyed', () {
    final error = LatchApiException.fromResponse(422, {
      'message': 'The given data was invalid.',
      'code': 'VALIDATION_ERROR',
      'errors': {
        'device_uid': ['The device UID format is invalid.'],
      },
    });

    expect(error.statusCode, 422);
    expect(error.code, 'VALIDATION_ERROR');
    expect(error.fieldError('device_uid'), 'The device UID format is invalid.');
  });

  test('last-known and offline GNSS data are labelled as historical', () {
    final item = LatchItem.fromJson({
      'id': 8,
      'device_uid': 'LATCH-AB12-CD34',
      'item_name': 'Keys',
      'location': {
        'latitude': 14.5995,
        'longitude': 120.9842,
        'recorded_at': '2026-07-20T01:00:00.000000Z',
        'type': 'last_known',
      },
      'status': {
        'connection': 'offline',
        'last_communication_at': '2026-07-20T01:00:00.000000Z',
        'telemetry_recorded_at': '2026-07-20T00:59:00.000000Z',
        'battery_percentage': 60,
        'battery_status': 'normal',
        'gnss_status': 'fixed',
        'satellites': 8,
        'hdop': 1.2,
        'gsm_csq': 12,
        'gsm_signal_level': 'fair',
        'power_state': 'charging',
        'firmware_version': '1.1.0',
        'reset_reason': 'power_on',
      },
    });

    final card = ItemPresentationAdapter.buildItems([item]).single;

    expect(card.locationLabel, 'Last known location');
    expect(card.itemDetails?.locationCoordinatesText, '14.599500, 120.984200');
    expect(card.itemDetails?.locationLatitude, 14.5995);
    expect(card.itemDetails?.locationLongitude, 120.9842);
    expect(
      card.itemDetails?.gnssStatusText,
      'GPS fix available (last reported)',
    );
    expect(card.itemDetails?.satellitesText, '8 satellites visible');
    expect(card.itemDetails?.gnssTimestampText, startsWith('Reported '));
    expect(card.itemDetails?.batteryStatusText, 'Charging');
    expect(card.itemDetails?.firmwareVersionText, 'Version 1.1.0');
    expect(card.itemDetails?.resetReasonText, 'Last restart: Power on');
  });

  test(
    'malformed history coordinates are rejected instead of mapped to zero',
    () {
      expect(
        () => LatchPosition.fromJson({
          'latitude': null,
          'longitude': 120.9842,
          'recorded_at': '2026-07-20T01:00:00.000000Z',
        }),
        throwsFormatException,
      );
    },
  );

  test(
    'activity model preserves the device snapshot and navigation target',
    () {
      final activity = LatchActivity.fromJson({
        'id': 14,
        'event_type': 'device_offline',
        'title': 'Tracker offline',
        'description': 'The tracker stopped communicating.',
        'source': 'system',
        'device': {
          'item_id': 7,
          'device_uid': 'LATCH-AB12-CD34',
          'item_name': 'Backpack',
        },
        'event_data': {'last_seen_minutes_ago': 12},
        'occurred_at': '2026-07-26T01:00:00.000000Z',
      });

      expect(activity.id, 14);
      expect(activity.itemId, 7);
      expect(activity.itemName, 'Backpack');
      expect(activity.deviceUid, 'LATCH-AB12-CD34');
      expect(activity.occurredAt, DateTime.utc(2026, 7, 26, 1));
    },
  );

  testWidgets('item details show place name and copyable coordinates', (
    tester,
  ) async {
    final item = LatchItem.fromJson({
      'id': 9,
      'device_uid': 'LATCH-CD34-EF56',
      'item_name': 'Bag',
      'location': {
        'latitude': 14.5995,
        'longitude': 120.9842,
        'recorded_at': '2026-07-20T01:00:00.000000Z',
        'type': 'current',
      },
      'status': {
        'connection': 'online',
        'last_communication_at': '2026-07-20T01:00:00.000000Z',
        'battery_percentage': 80,
        'battery_status': 'normal',
        'gnss_status': 'fixed',
        'satellites': 9,
        'hdop': 0.9,
        'gsm_csq': 18,
        'gsm_signal_level': 'good',
        'power_state': 'full',
        'firmware_version': '1.1.0',
        'reset_reason': 'software',
      },
    });
    final details = ItemPresentationAdapter.buildItems([
      item,
    ]).single.itemDetails!;

    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: ItemDetailsBottomSheet.fromData(
            details,
            locationAddressLookup: (_, _) async =>
                'Manila City Hall, Ermita, Manila',
          ),
        ),
      ),
    );
    await tester.pumpAndSettle();

    expect(find.text('Current location'), findsOneWidget);
    expect(find.text('Manila City Hall, Ermita, Manila'), findsOneWidget);
    expect(find.text('14.599500, 120.984200'), findsOneWidget);
    expect(find.text('Fully charged'), findsOneWidget);
    expect(find.text('Version 1.1.0'), findsOneWidget);
    expect(find.text('Last restart: Software restart'), findsOneWidget);
    expect(
      tester.widget<SelectableText>(
        find.widgetWithText(SelectableText, '14.599500, 120.984200'),
      ),
      isA<SelectableText>(),
    );
  });
}
