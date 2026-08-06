import 'dart:typed_data';

import 'package:image/image.dart' as img;

const int x1VendorBlack = 0;
const int x1VendorWhite = 1;
const int x1VendorRed = 2;
const int x1VendorYellow = 3;

const List<(int, int, int)> _vendorBwryPalette = <(int, int, int)>[
  (0, 0, 0),
  (255, 255, 255),
  (255, 0, 0),
  (255, 255, 0),
];

/// Matches `Bitmap.createScaledBitmap(..., filter: true)` in the reference
/// editor: scale directly to the panel dimensions without cover-cropping.
img.Image resizeX1VendorImage(img.Image source, int width, int height) {
  if (width <= 0 || height <= 0) {
    throw ArgumentError('E-paper dimensions must be positive.');
  }
  return img.copyResize(
    source,
    width: width,
    height: height,
    interpolation: img.Interpolation.linear,
  );
}

/// Applies the reference app's BWRY Floyd-Steinberg variant and returns its
/// palette indexes: black, white, red, yellow.
///
/// The vendor implementation intentionally uses integer, floor-rounded error
/// values and only the 7/16, 3/16, and 5/16 neighbours. It does not apply the
/// conventional bottom-right 1/16 term or row-boundary special cases.
Uint8List quantizeX1VendorBwry(img.Image source) {
  final int width = source.width;
  final int pixelCount = width * source.height;
  final List<int> red = List<int>.filled(pixelCount, 0);
  final List<int> green = List<int>.filled(pixelCount, 0);
  final List<int> blue = List<int>.filled(pixelCount, 0);
  final Uint8List classes = Uint8List(pixelCount);

  for (int y = 0; y < source.height; y++) {
    for (int x = 0; x < width; x++) {
      final int index = (y * width) + x;
      final img.Pixel pixel = source.getPixel(x, y);
      red[index] = pixel.r.toInt();
      green[index] = pixel.g.toInt();
      blue[index] = pixel.b.toInt();
    }
  }

  int clampChannel(int value) => value.clamp(0, 255);

  void addError(
    int index,
    int errorRed,
    int errorGreen,
    int errorBlue,
    int rate,
  ) {
    if (index >= pixelCount) {
      return;
    }
    red[index] = clampChannel(red[index] + (errorRed * rate));
    green[index] = clampChannel(green[index] + (errorGreen * rate));
    blue[index] = clampChannel(blue[index] + (errorBlue * rate));
  }

  for (int index = 0; index < pixelCount; index++) {
    int nearest = x1VendorBlack;
    int nearestDistance = 195076;
    for (
      int paletteIndex = 0;
      paletteIndex < _vendorBwryPalette.length;
      paletteIndex++
    ) {
      final (int paletteRed, int paletteGreen, int paletteBlue) =
          _vendorBwryPalette[paletteIndex];
      final int deltaRed = red[index] - paletteRed;
      final int deltaGreen = green[index] - paletteGreen;
      final int deltaBlue = blue[index] - paletteBlue;
      final int distance =
          (deltaRed * deltaRed) +
          (deltaGreen * deltaGreen) +
          (deltaBlue * deltaBlue);
      if (distance < nearestDistance) {
        nearestDistance = distance;
        nearest = paletteIndex;
      }
    }

    classes[index] = nearest;
    final (int paletteRed, int paletteGreen, int paletteBlue) =
        _vendorBwryPalette[nearest];
    final int errorRed = ((red[index] - paletteRed) / 16).floor();
    final int errorGreen = ((green[index] - paletteGreen) / 16).floor();
    final int errorBlue = ((blue[index] - paletteBlue) / 16).floor();

    addError(index + 1, errorRed, errorGreen, errorBlue, 7);
    addError(index + width - 1, errorRed, errorGreen, errorBlue, 3);
    addError(index + width, errorRed, errorGreen, errorBlue, 5);
  }

  return classes;
}
