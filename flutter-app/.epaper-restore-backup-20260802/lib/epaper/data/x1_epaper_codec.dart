import 'dart:typed_data';

enum X1EpaperScanMode { vertical, horizontal }

Uint8List packX1FourColorFrame({
  required List<int> pixelCodes,
  required int width,
  required int height,
  required X1EpaperScanMode scanMode,
}) {
  if (width <= 0 || height <= 0) {
    throw ArgumentError('E-paper dimensions must be positive.');
  }
  if (pixelCodes.length != width * height) {
    throw ArgumentError.value(
      pixelCodes.length,
      'pixelCodes.length',
      'Expected ${width * height} codes for a ${width}x$height frame.',
    );
  }

  final List<int> orderedCodes = switch (scanMode) {
    X1EpaperScanMode.vertical => _orderVertical(pixelCodes, width, height),
    X1EpaperScanMode.horizontal => _orderHorizontal(pixelCodes, width, height),
  };

  return _packFourPixelsPerByte(orderedCodes);
}

List<int> _orderVertical(List<int> codes, int width, int height) {
  final int padding = (8 - (height % 8)) % 8;
  final List<int> ordered = <int>[];

  for (int x = 0; x < width; x++) {
    for (int y = 0; y < height; y++) {
      ordered.add(codes[(y * width) + x] & 0x03);
    }
    ordered.addAll(List<int>.filled(padding, 0));
  }

  return ordered;
}

List<int> _orderHorizontal(List<int> codes, int width, int height) {
  final int padding = (8 - (width % 8)) % 8;
  final List<int> ordered = <int>[];

  for (int y = 0; y < height; y++) {
    final int firstX = width - 1;
    for (int x = firstX; x >= 0; x--) {
      ordered.add(codes[(y * width) + x] & 0x03);
      if (x == firstX) {
        ordered.addAll(List<int>.filled(padding, 0));
      }
    }
  }

  return ordered;
}

Uint8List _packFourPixelsPerByte(List<int> codes) {
  final Uint8List packed = Uint8List((codes.length / 4).ceil());
  int sourceIndex = 0;

  for (int outputIndex = 0; outputIndex < packed.length; outputIndex++) {
    int nextCode() =>
        sourceIndex < codes.length ? codes[sourceIndex++] & 0x03 : 0;

    packed[outputIndex] =
        (nextCode() << 6) | (nextCode() << 4) | (nextCode() << 2) | nextCode();
  }

  return packed;
}
