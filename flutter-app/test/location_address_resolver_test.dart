import 'dart:async';
import 'package:flutter/widgets.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:geocoding/geocoding.dart';
import 'package:latch/latch_ui/core/services/location_address_resolver.dart';

void main() {
  test('includes genuine place, house number and road without duplicates', () {
    expect(
      LocationAddressResolver.formatPlacemarks([
        const Placemark(
          name: 'K-Mart',
          thoroughfare: 'Pandacaqui Road',
          subThoroughfare: '1234',
          street: '1234 Pandacaqui Road, Pandacaqui',
          subLocality: 'Pandacaqui',
          locality: 'Mexico',
          subAdministrativeArea: 'Pampanga',
          administrativeArea: 'Pampanga',
        ),
      ]),
      'K-Mart, 1234 Pandacaqui Road, Pandacaqui, Mexico, Pampanga',
    );
  });
  test('chooses street level result over a general locality', () {
    expect(
      LocationAddressResolver.formatPlacemarks([
        const Placemark(
          name: 'Pandacaqui',
          locality: 'Mexico',
          administrativeArea: 'Pampanga',
        ),
        const Placemark(
          name: 'Pandacaqui Road',
          thoroughfare: 'Pandacaqui Road',
          subLocality: 'Pandacaqui',
          locality: 'Mexico',
          administrativeArea: 'Pampanga',
        ),
      ]),
      'Pandacaqui Road, Pandacaqui, Mexico, Pampanga',
    );
  });
  test('uses street fallback and supports non-Latin addresses', () {
    expect(
      LocationAddressResolver.formatPlacemarks([
        const Placemark(
          name: '中央通り',
          street: '中央通り, 中央区',
          subLocality: '中央区',
          locality: '東京',
        ),
      ]),
      '中央通り, 中央区, 東京',
    );
  });
  test('keeps general fallback and excludes plus codes', () {
    expect(
      LocationAddressResolver.formatPlacemarks([
        const Placemark(
          name: '7Q72+MX',
          subLocality: 'Pandacaqui',
          locality: 'Mexico',
          administrativeArea: 'Pampanga',
        ),
      ]),
      'Pandacaqui, Mexico, Pampanga',
    );
    expect(
      LocationAddressResolver.formatPlacemarks([]),
      'Place name unavailable',
    );
  });
  test('does not present a block and lot as a landmark', () {
    expect(
      LocationAddressResolver.formatPlacemarks([
        const Placemark(
          name: 'B9 L21',
          street: 'B9 L21',
          subLocality: 'Barangay San Jose',
          locality: 'Mexico',
        ),
      ]),
      'Barangay San Jose, Mexico',
    );
  });
  test(
    'deduplicates lookups and retries a transient failure after cooldown',
    () async {
      var now = DateTime(2026);
      final geocoder = _Geocoder()
        ..lookup = () async => throw StateError('Offline');
      final resolver = LocationAddressResolver(
        geocoding: geocoder,
        now: () => now,
      );
      final first = resolver.resolve(15, 120);
      expect(identical(first, resolver.resolve(15, 120)), isTrue);
      expect(await first, 'Place name unavailable');
      expect(geocoder.calls, 1);
      geocoder.lookup = () async => [
        const Placemark(street: 'Pandacaqui Road'),
      ];
      now = now.add(const Duration(seconds: 31));
      expect(await resolver.resolve(15, 120), 'Pandacaqui Road');
      expect(geocoder.calls, 2);
    },
  );
  testWidgets('stalled geocoder does not block later addresses indefinitely', (
    tester,
  ) async {
    final geocoder = _Geocoder()
      ..lookup = () => Completer<List<Placemark>>().future;
    final resolver = LocationAddressResolver(geocoding: geocoder);
    final first = resolver.resolve(15, 120);
    await tester.pump();
    geocoder.lookup = () async => [const Placemark(street: 'Next Road')];
    final second = resolver.resolve(16, 120);
    await tester.pump(const Duration(seconds: 7));
    expect(await first, 'Place name unavailable');
    expect(await second, 'Next Road');
  });
  test('invalid coordinates never call the geocoder', () async {
    final geocoder = _Geocoder();
    expect(
      await LocationAddressResolver(
        geocoding: geocoder,
      ).resolve(double.nan, 120),
      'Place name unavailable',
    );
    expect(geocoder.calls, 0);
  });
}

class _Geocoder implements Geocoding {
  int calls = 0;
  Future<List<Placemark>> Function() lookup = () async => [];
  @override
  Future<bool> isPresent() async => true;
  @override
  Future<List<Placemark>> placemarkFromCoordinates(
    double latitude,
    double longitude, {
    Locale? locale,
  }) {
    calls++;
    return lookup();
  }

  @override
  dynamic noSuchMethod(Invocation invocation) => super.noSuchMethod(invocation);
}
