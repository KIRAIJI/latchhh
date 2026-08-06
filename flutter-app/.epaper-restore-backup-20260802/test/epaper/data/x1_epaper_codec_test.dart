import 'package:flutter_test/flutter_test.dart';
import 'package:latch/latch_ui/features/epaper/data/x1_epaper_codec.dart';

void main() {
  group('packX1FourColorFrame', () {
    test('packs horizontal rows right-to-left and MSB-first', () {
      final bytes = packX1FourColorFrame(
        pixelCodes: const <int>[0, 1, 2, 3, 3, 2, 1, 0],
        width: 8,
        height: 1,
        scanMode: X1EpaperScanMode.horizontal,
      );

      expect(bytes, <int>[0x1B, 0xE4]);
    });

    test('packs vertical columns top-to-bottom', () {
      final bytes = packX1FourColorFrame(
        pixelCodes: const <int>[0, 1, 2, 3, 1, 2, 3, 0, 0, 3, 2, 1, 3, 2, 1, 0],
        width: 2,
        height: 8,
        scanMode: X1EpaperScanMode.vertical,
      );

      expect(bytes, <int>[0x27, 0x2D, 0x78, 0xD8]);
    });

    test('produces the exact 240x416 X1 frame size', () {
      final bytes = packX1FourColorFrame(
        pixelCodes: List<int>.filled(240 * 416, 1),
        width: 240,
        height: 416,
        scanMode: X1EpaperScanMode.horizontal,
      );

      expect(bytes, hasLength(24960));
      expect(bytes.every((byte) => byte == 0x55), isTrue);
    });

    test('rejects a pixel buffer with the wrong size', () {
      expect(
        () => packX1FourColorFrame(
          pixelCodes: const <int>[0, 1, 2],
          width: 2,
          height: 2,
          scanMode: X1EpaperScanMode.horizontal,
        ),
        throwsArgumentError,
      );
    });
  });
}
