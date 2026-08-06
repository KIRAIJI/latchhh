import 'package:google_maps_flutter/google_maps_flutter.dart';

abstract final class LatchGoogleMap {
  static const LatLng fallbackCenter = LatLng(14.5995, 120.9842);

  static LatLngBounds? boundsFor(Iterable<LatLng> positions) {
    final points = positions.toList(growable: false);
    if (points.length < 2) return null;

    var south = points.first.latitude;
    var north = points.first.latitude;
    var west = points.first.longitude;
    var east = points.first.longitude;

    for (final point in points.skip(1)) {
      if (point.latitude < south) south = point.latitude;
      if (point.latitude > north) north = point.latitude;
      if (point.longitude < west) west = point.longitude;
      if (point.longitude > east) east = point.longitude;
    }

    return LatLngBounds(
      southwest: LatLng(south, west),
      northeast: LatLng(north, east),
    );
  }
}
