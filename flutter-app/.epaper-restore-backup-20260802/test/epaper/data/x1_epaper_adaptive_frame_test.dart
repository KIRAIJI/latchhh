import 'package:flutter_test/flutter_test.dart';
import 'package:image/image.dart' as img;
import 'package:latch/latch_ui/features/epaper/data/x1_epaper_adaptive_frame.dart';
import 'package:latch/latch_ui/features/epaper/data/x1_epaper_codec.dart';

void main() {
  test('keeps an already compressible image at full fine resolution', () {
    final img.Image source = img.Image(width: 240, height: 416);
    img.fill(source, color: img.ColorRgb8(255, 255, 255));

    final X1AdaptiveFrame result = buildX1AdaptiveFrame(
      source: source,
      scanMode: X1EpaperScanMode.horizontal,
    );

    expect(result.profile, 'vendor Floyd-Steinberg');
    expect(result.blockSize, 1);
    expect(result.fragmentCount, 13);
    expect(result.packed, hasLength(24960));
  });

  test('retains full resolution for a photo-like gradient', () {
    final img.Image source = img.Image(width: 240, height: 416);
    for (int y = 0; y < source.height; y++) {
      for (int x = 0; x < source.width; x++) {
        final int noise = ((x * 17 + y * 31) & 15) - 8;
        source.setPixelRgb(
          x,
          y,
          ((x * 255 ~/ 239) + noise).clamp(0, 255),
          ((y * 255 ~/ 415) + noise).clamp(0, 255),
          (((x + y) * 255 ~/ 654) + noise).clamp(0, 255),
        );
      }
    }

    final X1AdaptiveFrame result = buildX1AdaptiveFrame(
      source: source,
      scanMode: X1EpaperScanMode.horizontal,
      maxFineFragments: 60,
    );

    expect(result.blockSize, 1);
    expect(result.profile, 'power-safe fine color');
    expect(result.fragmentCount, lessThanOrEqualTo(60));
    expect(result.inkCoverage, lessThanOrEqualTo(0.35));
    expect(result.profile, isNot(contains('fallback')));
    expect(result.packed, hasLength(24960));
    expect(result.preview.width, 240);
    expect(result.preview.height, 416);
  });

  test('rejects a ceiling below the thirteen X1 compression pages', () {
    expect(
      () => buildX1AdaptiveFrame(
        source: img.Image(width: 1, height: 1),
        scanMode: X1EpaperScanMode.horizontal,
        maxFineFragments: 12,
      ),
      throwsArgumentError,
    );
  });
}
