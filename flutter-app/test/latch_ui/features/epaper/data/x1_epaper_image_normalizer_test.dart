import 'dart:typed_data';

import 'package:flutter_test/flutter_test.dart';
import 'package:image/image.dart' as img;
import 'package:latch/latch_ui/features/epaper/data/x1_epaper_image_normalizer.dart';

void main() {
  group('decodeX1EpaperSource', () {
    test('flattens transparent pixels onto white', () {
      final source = img.Image(width: 2, height: 1, numChannels: 4)
        ..setPixelRgba(0, 0, 255, 0, 0, 0)
        ..setPixelRgba(1, 0, 0, 0, 0, 255);

      final normalized = decodeX1EpaperSource(
        Uint8List.fromList(img.encodePng(source)),
      );

      expect(normalized, isNotNull);
      final transparent = normalized!.getPixel(0, 0);
      expect(
        <int>[
          transparent.r.toInt(),
          transparent.g.toInt(),
          transparent.b.toInt(),
        ],
        <int>[255, 255, 255],
      );
      final opaque = normalized.getPixel(1, 0);
      expect(
        <int>[opaque.r.toInt(), opaque.g.toInt(), opaque.b.toInt()],
        <int>[0, 0, 0],
      );
    });

    test('returns null for unsupported bytes', () {
      expect(
        decodeX1EpaperSource(Uint8List.fromList(<int>[1, 2, 3, 4])),
        isNull,
      );
    });
  });
}
