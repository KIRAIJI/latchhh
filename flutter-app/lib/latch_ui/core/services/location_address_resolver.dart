import 'dart:async';

import 'package:geocoding/geocoding.dart';

class LocationAddressResolver {
  LocationAddressResolver({Geocoding? geocoding, DateTime Function()? now})
    : _geocoding = geocoding ?? Geocoding(),
      _now = now ?? DateTime.now;

  static final LocationAddressResolver shared = LocationAddressResolver();

  final Geocoding _geocoding;
  final DateTime Function() _now;
  final Map<String, ({Future<String> result, DateTime expires})> _cache = {};
  Future<void> _queue = Future<void>.value();

  Future<String> resolve(double latitude, double longitude) {
    if (!latitude.isFinite ||
        !longitude.isFinite ||
        latitude.abs() > 90 ||
        longitude.abs() > 180) {
      return Future.value('Place name unavailable');
    }
    final key =
        '${latitude.toStringAsFixed(5)},${longitude.toStringAsFixed(5)}';
    final now = _now();
    final cached = _cache[key];
    if (cached != null && now.isBefore(cached.expires)) return cached.result;
    _cache.remove(key);
    if (_cache.length >= 128) _cache.remove(_cache.keys.first);
    final result = _enqueue(latitude: latitude, longitude: longitude);
    _cache[key] = (
      result: result,
      expires: now.add(const Duration(minutes: 30)),
    );
    unawaited(
      result.then((value) {
        if (value == 'Place name unavailable' &&
            _cache[key]?.result == result) {
          // Retry transient failures, without issuing a request on every rebuild.
          _cache[key] = (
            result: result,
            expires: _now().add(const Duration(seconds: 30)),
          );
        }
      }),
    );
    return result;
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
          await _lookup(
            latitude: latitude,
            longitude: longitude,
          ).timeout(const Duration(seconds: 6)),
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
    if (_descriptiveName(placemark) != null) score += 40;
    if (_humanAddressComponent(placemark.thoroughfare) != null) score += 60;
    if (_humanAddressComponent(placemark.street) != null) score += 30;
    if (_normalized(placemark.subThoroughfare) != null) score += 5;
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

    final road = _humanAddressComponent(placemark.thoroughfare);
    final number = _normalized(placemark.subThoroughfare);
    final street = road == null
        ? _humanAddressComponent(placemark.street)
        : number == null ||
              road.toLowerCase().startsWith('${number.toLowerCase()} ')
        ? road
        : '$number $road';
    final name = _descriptiveName(placemark);
    if (name?.toLowerCase() != street?.toLowerCase()) add(name);
    if (!_looksLikeAddress(street)) add(street);
    add(placemark.subLocality);
    add(placemark.locality);
    add(placemark.subAdministrativeArea);
    add(placemark.administrativeArea);
    add(placemark.country);

    return parts.isEmpty ? 'Place name unavailable' : parts.join(', ');
  }

  static String? _descriptiveName(Placemark placemark) {
    final name = _humanAddressComponent(placemark.name);
    if (name == null || _looksLikeAddress(name)) return null;

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
    final genericComponents = genericValues
        .expand((value) => value.split(','))
        .map((value) => value.trim().toLowerCase())
        .toSet();
    if (name
        .split(',')
        .every(
          (part) => genericComponents.contains(part.trim().toLowerCase()),
        )) {
      return null;
    }

    return name;
  }

  static bool _looksLikeAddress(String? value) {
    final normalized = _normalized(value);
    if (normalized == null) return false;

    return RegExp(
      r'^(?:(?:b|blk|block)\.?\s*\w+\s+(?:l|lot)\.?\s*\w+)$',
      caseSensitive: false,
    ).hasMatch(normalized);
  }

  static String? _humanAddressComponent(String? value) {
    final normalized = _normalized(value);
    if (normalized == null || _plusCode.hasMatch(normalized)) {
      return null;
    }
    return RegExp(r'\p{L}', unicode: true).hasMatch(normalized)
        ? normalized
        : null;
  }

  static String? _normalized(String? value) {
    final normalized = value?.trim().replaceAll(RegExp(r'\s+'), ' ');
    return normalized == null || normalized.isEmpty ? null : normalized;
  }

  static final RegExp _plusCode = RegExp(
    r'^[23456789CFGHJMPQRVWX]{4,8}\+[23456789CFGHJMPQRVWX]{2,3}\b',
    caseSensitive: false,
  );
}
