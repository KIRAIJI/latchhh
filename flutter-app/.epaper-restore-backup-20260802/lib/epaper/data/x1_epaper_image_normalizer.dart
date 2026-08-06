import 'dart:typed_data';

import 'package:image/image.dart' as img;

img.Image? decodeX1EpaperSource(Uint8List bytes) {
  try {
    final img.Image? decoded = img.decodeImage(bytes);
    if (decoded == null) {
      return null;
    }

    return flattenX1EpaperImageOnWhite(img.bakeOrientation(decoded));
  } on Object {
    return null;
  }
}

img.Image flattenX1EpaperImageOnWhite(img.Image source) {
  final img.Image flattened = img.Image(
    width: source.width,
    height: source.height,
  );

  for (int y = 0; y < source.height; y++) {
    for (int x = 0; x < source.width; x++) {
      final img.Pixel pixel = source.getPixel(x, y);
      final double alpha = pixel.a.toDouble().clamp(0, 255) / 255;
      int flatten(num channel) =>
          ((channel.toDouble() * alpha) + (255 * (1 - alpha))).round().clamp(
            0,
            255,
          );

      flattened.setPixelRgb(
        x,
        y,
        flatten(pixel.r),
        flatten(pixel.g),
        flatten(pixel.b),
      );
    }
  }

  return flattened;
}
