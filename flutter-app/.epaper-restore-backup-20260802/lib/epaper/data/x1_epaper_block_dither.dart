import 'dart:math' as math;

import 'package:image/image.dart' as img;

enum X1DitherPattern {
  balancedColor8x8,
  powerSafeColor8x8,
  errorDiffusion,
  atkinson,
  ordered4x4,
}

class X1BlockDitheredFrame {
  const X1BlockDitheredFrame({required this.pixelCodes, required this.preview});

  final List<int> pixelCodes;
  final img.Image preview;
}

X1BlockDitheredFrame ditherX1FourColorFrame(
  img.Image source, {
  int blockSize = 2,
  X1DitherPattern pattern = X1DitherPattern.errorDiffusion,
}) {
  if (blockSize <= 0) {
    throw ArgumentError.value(blockSize, 'blockSize', 'Must be positive.');
  }

  final int logicalWidth = (source.width / blockSize).ceil();
  final int logicalHeight = (source.height / blockSize).ceil();
  final img.Image logicalSource = blockSize == 1
      ? source
      : img.copyResize(
          source,
          width: logicalWidth,
          height: logicalHeight,
          interpolation: img.Interpolation.cubic,
        );
  final int logicalPixels = logicalWidth * logicalHeight;
  final List<int> red = List<int>.filled(logicalPixels, 0);
  final List<int> green = List<int>.filled(logicalPixels, 0);
  final List<int> blue = List<int>.filled(logicalPixels, 0);

  for (int y = 0; y < logicalHeight; y++) {
    for (int x = 0; x < logicalWidth; x++) {
      final int index = (y * logicalWidth) + x;
      final img.Pixel pixel = logicalSource.getPixel(x, y);
      red[index] = pixel.r.toInt();
      green[index] = pixel.g.toInt();
      blue[index] = pixel.b.toInt();
    }
  }

  const List<List<double>> palette = <List<double>>[
    <double>[0, 0, 0],
    <double>[255, 255, 255],
    <double>[255, 0, 0],
    <double>[255, 255, 0],
  ];
  const List<int> panelCodes = <int>[0, 1, 3, 2];
  final List<int> outputCodes = List<int>.filled(
    source.width * source.height,
    1,
  );
  final img.Image preview = img.Image(
    width: source.width,
    height: source.height,
  );

  void paintBlock(int x, int y, int paletteIndex) {
    final int panelCode = panelCodes[paletteIndex];
    final int startX = x * blockSize;
    final int startY = y * blockSize;
    final int endX = math.min(startX + blockSize, source.width);
    final int endY = math.min(startY + blockSize, source.height);
    for (int outputY = startY; outputY < endY; outputY++) {
      for (int outputX = startX; outputX < endX; outputX++) {
        outputCodes[(outputY * source.width) + outputX] = panelCode;
        preview.setPixelRgb(
          outputX,
          outputY,
          palette[paletteIndex][0].round(),
          palette[paletteIndex][1].round(),
          palette[paletteIndex][2].round(),
        );
      }
    }
  }

  if (pattern == X1DitherPattern.ordered4x4) {
    const List<List<int>> bayer4x4 = <List<int>>[
      <int>[0, 8, 2, 10],
      <int>[12, 4, 14, 6],
      <int>[3, 11, 1, 9],
      <int>[15, 7, 13, 5],
    ];
    for (int y = 0; y < logicalHeight; y++) {
      for (int x = 0; x < logicalWidth; x++) {
        final img.Pixel pixel = logicalSource.getPixel(x, y);
        int nearest = 0;
        int secondNearest = 0;
        double nearestDistance = double.infinity;
        double secondDistance = double.infinity;
        for (int i = 0; i < palette.length; i++) {
          final double dr = pixel.r.toDouble() - palette[i][0];
          final double dg = pixel.g.toDouble() - palette[i][1];
          final double db = pixel.b.toDouble() - palette[i][2];
          final double distance = (dr * dr) + (dg * dg) + (db * db);
          if (distance < nearestDistance) {
            secondNearest = nearest;
            secondDistance = nearestDistance;
            nearest = i;
            nearestDistance = distance;
          } else if (distance < secondDistance) {
            secondNearest = i;
            secondDistance = distance;
          }
        }
        final double nearestMagnitude = math.sqrt(nearestDistance);
        final double secondMagnitude = math.sqrt(secondDistance);
        final double secondWeight =
            nearestMagnitude == 0 ||
                !nearestMagnitude.isFinite ||
                !secondMagnitude.isFinite
            ? 0
            : nearestMagnitude / (nearestMagnitude + secondMagnitude);
        final double threshold = (bayer4x4[y % 4][x % 4] + 0.5) / 16;
        paintBlock(x, y, threshold < secondWeight ? secondNearest : nearest);
      }
    }
    return X1BlockDitheredFrame(pixelCodes: outputCodes, preview: preview);
  }

  int nearestPalette(int r, int g, int b) {
    int best = 0;
    double bestDistance = double.infinity;
    for (int i = 0; i < palette.length; i++) {
      final double dr = r - palette[i][0];
      final double dg = g - palette[i][1];
      final double db = b - palette[i][2];
      final double distance = (dr * dr) + (dg * dg) + (db * db);
      if (distance < bestDistance) {
        bestDistance = distance;
        best = i;
      }
    }
    return best;
  }

  if (pattern == X1DitherPattern.balancedColor8x8 ||
      pattern == X1DitherPattern.powerSafeColor8x8) {
    final double inkScale = pattern == X1DitherPattern.powerSafeColor8x8
        ? 0.68
        : 1;
    const List<List<int>> bayer8x8 = <List<int>>[
      <int>[0, 48, 12, 60, 3, 51, 15, 63],
      <int>[32, 16, 44, 28, 35, 19, 47, 31],
      <int>[8, 56, 4, 52, 11, 59, 7, 55],
      <int>[40, 24, 36, 20, 43, 27, 39, 23],
      <int>[2, 50, 14, 62, 1, 49, 13, 61],
      <int>[34, 18, 46, 30, 33, 17, 45, 29],
      <int>[10, 58, 6, 54, 9, 57, 5, 53],
      <int>[42, 26, 38, 22, 41, 25, 37, 21],
    ];
    for (int index = 0; index < logicalPixels; index++) {
      final int x = index % logicalWidth;
      final int y = index ~/ logicalWidth;
      final int r = red[index];
      final int g = green[index];
      final int b = blue[index];
      final double orderedThreshold = (bayer8x8[y % 8][x % 8] + 0.5) / 64;

      final int paletteIndex;
      if (r + 12 >= g && g + 12 >= b) {
        final int warmRed = math.max(r, g);
        final int warmGreen = g;
        final int warmBlue = math.min(b, warmGreen);
        final double blackWeight = ((255 - warmRed) / 255) * inkScale;
        final double redWeight = ((warmRed - warmGreen) / 255) * inkScale;
        final double yellowWeight = ((warmGreen - warmBlue) / 255) * inkScale;
        final double whiteWeight = 1 - blackWeight - redWeight - yellowWeight;
        final double whiteLimit = blackWeight + whiteWeight;
        final double redLimit = whiteLimit + redWeight;
        if (orderedThreshold < blackWeight) {
          paletteIndex = 0;
        } else if (orderedThreshold < whiteLimit) {
          paletteIndex = 1;
        } else if (orderedThreshold < redLimit) {
          paletteIndex = 2;
        } else {
          paletteIndex = 3;
        }
      } else {
        final double luminance = (0.2126 * r) + (0.7152 * g) + (0.0722 * b);
        final double blackWeight = (1 - (luminance / 255)) * inkScale;
        paletteIndex = orderedThreshold < blackWeight ? 0 : 1;
      }
      paintBlock(x, y, paletteIndex);
    }
    return X1BlockDitheredFrame(pixelCodes: outputCodes, preview: preview);
  }

  int floorDivide(int value, int divisor) => (value / divisor).floor();

  void addError(int index, int er, int eg, int eb, int weight) {
    if (index >= logicalPixels) {
      return;
    }
    red[index] = (red[index] + (er * weight)).clamp(0, 255);
    green[index] = (green[index] + (eg * weight)).clamp(0, 255);
    blue[index] = (blue[index] + (eb * weight)).clamp(0, 255);
  }

  for (int index = 0; index < logicalPixels; index++) {
    final int paletteIndex = nearestPalette(
      red[index],
      green[index],
      blue[index],
    );
    paintBlock(index % logicalWidth, index ~/ logicalWidth, paletteIndex);

    final int divisor = pattern == X1DitherPattern.atkinson ? 8 : 16;
    final int errorRed = floorDivide(
      red[index] - palette[paletteIndex][0].round(),
      divisor,
    );
    final int errorGreen = floorDivide(
      green[index] - palette[paletteIndex][1].round(),
      divisor,
    );
    final int errorBlue = floorDivide(
      blue[index] - palette[paletteIndex][2].round(),
      divisor,
    );

    if (pattern == X1DitherPattern.atkinson) {
      addError(index + 1, errorRed, errorGreen, errorBlue, 1);
      addError(index + 2, errorRed, errorGreen, errorBlue, 1);
      addError(index + logicalWidth - 1, errorRed, errorGreen, errorBlue, 1);
      addError(index + logicalWidth, errorRed, errorGreen, errorBlue, 1);
      addError(index + logicalWidth + 1, errorRed, errorGreen, errorBlue, 1);
      addError(index + (logicalWidth * 2), errorRed, errorGreen, errorBlue, 1);
    } else {
      addError(index + 1, errorRed, errorGreen, errorBlue, 7);
      addError(index + logicalWidth - 1, errorRed, errorGreen, errorBlue, 3);
      addError(index + logicalWidth, errorRed, errorGreen, errorBlue, 5);
    }
  }

  return X1BlockDitheredFrame(pixelCodes: outputCodes, preview: preview);
}
