import 'dart:async';

import 'package:geocoding/geocoding.dart';

class LocationAddressResolver {
  LocationAddressResolver({Geocoding? geocoding})
    : _geocoding = geocoding ?? Geocoding();

  static final LocationAddressResolver shared = LocationAddressResolver();

  final Geocoding _geocoding;
  final Map<String, Future<String>> _cache = {};
  Future<void> _queue = Future<void>.value();

  Future<String> resolve(double latitude, double longitude) {
    final key =
        '${latitude.toStringAsFixed(5)},${longitude.toStringAsFixed(5)}';
    return _cache.putIfAbsent(
      key,
      () => _enqueue(latitude: latitude, longitude: longitude),
    );
  }

  static String formatCoordinates(double latitude, double longitude) {
    return '${latitude.toStringAsFixed(6)}, ${longitude.toStringAsFixed(6)}';
  }

  static String formatPlacemarks(Iterable<Placemark> values) {
    final placemarks = values.toList(growable: false);
    if (placemarks.isEmpty) return 'Place name unavailable';

    var best = placemarks.first;
    var bestScore = _placemarkScore(best);
    for (final placemark in placemarks.skip(1)) {
      final score = _placemarkScore(placemark);
      if (score > bestScore) {
        best = placemark;
        bestScore = score;
      }
    }

    return _formatPlacemark(best);
  }

  Future<String> _enqueue({
    required double latitude,
    required double longitude,
  }) {
    final completer = Completer<String>();
    _queue = _queue.then((_) async {
      try {
        completer.complete(
          await _lookup(latitude: latitude, longitude: longitude),
        );
      } on Object {
        completer.complete('Place name unavailable');
      }
    });
    return completer.future;
  }

  Future<String> _lookup({
    required double latitude,
    required double longitude,
  }) async {
    if (!await _geocoding.isPresent()) {
      return 'Place name unavailable';
    }

    final placemarks = await _geocoding.placemarkFromCoordinates(
      latitude,
      longitude,
    );
    if (placemarks.isEmpty) {
      return 'Place name unavailable';
    }

    return formatPlacemarks(placemarks);
  }

  static int _placemarkScore(Placemark placemark) {
    var score = 0;
    if (_descriptiveName(placemark) != null) score += 100;
    if (_humanAddressComponent(placemark.thoroughfare) != null) score += 20;
    if (_humanAddressComponent(placemark.street) != null) score += 10;
    if (_normalized(placemark.subLocality) != null) score += 4;
    if (_normalized(placemark.locality) != null) score += 4;
    if (_normalized(placemark.subAdministrativeArea) != null) score += 2;
    if (_normalized(placemark.administrativeArea) != null) score += 2;
    if (_normalized(placemark.country) != null) score += 1;
    return score;
  }

  static String _formatPlacemark(Placemark placemark) {
    final parts = <String>[];
    final seenComponents = <String>{};

    void add(String? value) {
      final normalized = _normalized(value);
      if (normalized == null) return;

      final uniqueComponents = <String>[];
      for (final rawComponent in normalized.split(',')) {
        final component = _normalized(rawComponent);
        if (component == null) continue;
        if (seenComponents.add(component.toLowerCase())) {
          uniqueComponents.add(component);
        }
      }
      if (uniqueComponents.isNotEmpty) {
        parts.add(uniqueComponents.join(', '));
      }
    }

    add(_descriptiveName(placemark));
    add(
      _humanAddressComponent(placemark.thoroughfare) ??
          _humanAddressComponent(placemark.street),
    );
    add(placemark.subLocality);
    add(placemark.locality);
    add(placemark.subAdministrativeArea);
    add(placemark.administrativeArea);
    add(placemark.country);

    return parts.isEmpty ? 'Place name unavailable' : parts.join(', ');
  }

  static String? _descriptiveName(Placemark placemark) {
    final name = _humanAddressComponent(placemark.name);
    if (name == null) return null;

    final genericValues = <String?>[
      placemark.street,
      placemark.thoroughfare,
      placemark.subThoroughfare,
      placemark.subLocality,
      placemark.locality,
      placemark.subAdministrativeArea,
      placemark.administrativeArea,
      placemark.postalCode,
      placemark.country,
    ].map(_normalized).whereType<String>();
    if (genericValues.any(
      (value) => value.toLowerCase() == name.toLowerCase(),
    )) {
      return null;
    }

    return name;
  }

  static String? _humanAddressComponent(String? value) {
    final normalized = _normalized(value);
    if (normalized == null ||
        _plusCode.hasMatch(normalized) ||
        _postalPrefix.hasMatch(normalized)) {
      return null;
    }
    return RegExp(r'[A-Za-z]').hasMatch(normalized) ? normalized : null;
  }

  static String? _normalized(String? value) {
    final normalized = value?.trim().replaceAll(RegExp(r'\s+'), ' ');
    return normalized == null || normalized.isEmpty ? null : normalized;
  }

  static final RegExp _plusCode = RegExp(
    r'^[23456789CFGHJMPQRVWX]{4,8}\+[23456789CFGHJMPQRVWX]{2,3}\b',
    caseSensitive: false,
  );
  static final RegExp _postalPrefix = RegExp(r'^\d{4,6}\b');
}
