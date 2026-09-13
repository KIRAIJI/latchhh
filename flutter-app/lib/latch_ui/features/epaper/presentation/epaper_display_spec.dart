/// Physical dimensions for the E-Paper display used in the tab preview frame.
abstract final class EpaperDisplaySpec {
  static const double widthMm = 48;
  static const double heightMm = 83;
  // Raster dimensions used by the existing X1 uploader in main.dart.
  static const int pixelWidth = 240;
  static const int pixelHeight = 416;
  static const double aspectRatio = pixelWidth / pixelHeight;
}
