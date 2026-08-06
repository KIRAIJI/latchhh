abstract final class GeofenceRadius {
  static const double suggestedMeters = 100;
  static const double minMeters = 50;
  static const double maxMeters = 5000;
  static const String unitLabel = 'm';

  static double clamp(double meters) {
    return meters.clamp(minMeters, maxMeters).toDouble();
  }

  static String format(double meters) {
    if (meters == meters.roundToDouble()) {
      return meters.round().toString();
    }
    return meters.toStringAsFixed(1);
  }

  static String formatWithUnit(double meters) {
    return '${format(meters)} $unitLabel';
  }
}
