import 'package:flutter_test/flutter_test.dart';
import 'package:image/image.dart' as img;
import 'package:latch/latch_ui/features/epaper/data/x1_epaper_block_dither.dart';
import 'package:latch/latch_ui/features/epaper/data/x1_epaper_codec.dart';
import 'package:latch/latch_ui/features/epaper/data/x1_epaper_compression.dart';

void main() {
  test('matches the vendor integer Floyd pixel pattern', () {
    final img.Image source = img.Image(width: 4, height: 3);
    const List<(int, int, int)> colors = <(int, int, int)>[
      (38, 44, 57),
      (121, 109, 96),
      (218, 174, 46),
      (175, 39, 52),
      (235, 225, 211),
      (90, 133, 151),
      (201, 83, 31),
      (62, 65, 72),
      (147, 146, 139),
      (248, 244, 231),
      (116, 28, 35),
      (223, 188, 76),
    ];
    for (int i = 0; i < colors.length; i++) {
      final (int red, int green, int blue) = colors[i];
      source.setPixelRgb(i % 4, i ~/ 4, red, green, blue);
    }

    final X1BlockDitheredFrame result = ditherX1FourColorFrame(source);

    expect(result.pixelCodes, <int>[0, 0, 2, 2, 0, 0, 2, 2, 1, 1, 0, 0]);
  });

  test('matches the vendor integer Atkinson pixel pattern', () {
    final img.Image source = img.Image(width: 4, height: 3);
    const List<(int, int, int)> colors = <(int, int, int)>[
      (38, 44, 57),
      (121, 109, 96),
      (218, 174, 46),
      (175, 39, 52),
      (235, 225, 211),
      (90, 133, 151),
      (201, 83, 31),
      (62, 65, 72),
      (147, 146, 139),
      (248, 244, 231),
      (116, 28, 35),
      (223, 188, 76),
    ];
    for (int i = 0; i < colors.length; i++) {
      final (int red, int green, int blue) = colors[i];
      source.setPixelRgb(i % 4, i ~/ 4, red, green, blue);
    }

    final X1BlockDitheredFrame result = ditherX1FourColorFrame(
      source,
      pattern: X1DitherPattern.atkinson,
    );

    expect(result.pixelCodes, <int>[0, 0, 2, 2, 0, 0, 2, 2, 1, 1, 3, 3]);
  });

  test('balanced color dithering keeps neutral tones black and white', () {
    final img.Image source = img.Image(width: 16, height: 16);
    for (int y = 0; y < source.height; y++) {
      for (int x = 0; x < source.width; x++) {
        final int gray = ((x + y) * 255) ~/ 30;
        source.setPixelRgb(x, y, gray, gray, gray);
      }
    }
    final X1BlockDitheredFrame result = ditherX1FourColorFrame(
      source,
      pattern: X1DitherPattern.balancedColor8x8,
    );

    expect(result.pixelCodes, everyElement(isIn(<int>[0, 1])));
  });

  test('balanced color dithering preserves strong accent colors', () {
    final img.Image source = img.Image(width: 16, height: 8);
    for (int y = 0; y < source.height; y++) {
      for (int x = 0; x < source.width; x++) {
        source.setPixelRgb(x, y, x < 8 ? 255 : 255, x < 8 ? 0 : 255, 0);
      }
    }
    final X1BlockDitheredFrame result = ditherX1FourColorFrame(
      source,
      pattern: X1DitherPattern.balancedColor8x8,
    );

    expect(result.pixelCodes.take(8), everyElement(3));
    expect(result.pixelCodes.skip(8).take(8), everyElement(2));
  });

  test('balanced color dithering mixes the full palette for warm tones', () {
    final img.Image source = img.Image(width: 8, height: 8);
    img.fill(source, color: img.ColorRgb8(192, 128, 64));

    final X1BlockDitheredFrame result = ditherX1FourColorFrame(
      source,
      pattern: X1DitherPattern.balancedColor8x8,
    );

    for (final int code in <int>[0, 1, 2, 3]) {
      expect(
        result.pixelCodes.where((int value) => value == code),
        hasLength(16),
      );
    }
  });

  test('power-safe color dithering lowers ink without losing warm hues', () {
    final img.Image source = img.Image(width: 8, height: 8);
    img.fill(source, color: img.ColorRgb8(192, 128, 64));

    final X1BlockDitheredFrame balanced = ditherX1FourColorFrame(
      source,
      pattern: X1DitherPattern.balancedColor8x8,
    );
    final X1BlockDitheredFrame powerSafe = ditherX1FourColorFrame(
      source,
      pattern: X1DitherPattern.powerSafeColor8x8,
    );

    int inkPixels(X1BlockDitheredFrame frame) =>
        frame.pixelCodes.where((int code) => code != 1).length;

    expect(inkPixels(powerSafe), lessThan(inkPixels(balanced)));
    expect(powerSafe.pixelCodes, contains(0));
    expect(powerSafe.pixelCodes, contains(2));
    expect(powerSafe.pixelCodes, contains(3));
    expect(powerSafe.pixelCodes, contains(1));
  });

  test('balanced color dithering handles slightly cool neutral pixels', () {
    final img.Image source = img.Image(width: 8, height: 8);
    img.fill(source, color: img.ColorRgb8(100, 110, 120));

    final X1BlockDitheredFrame result = ditherX1FourColorFrame(
      source,
      pattern: X1DitherPattern.balancedColor8x8,
    );

    expect(result.pixelCodes, everyElement(isIn(<int>[0, 1])));
  });

  test('block dithering preserves a uniform 2x2 physical pixel grid', () {
    final img.Image source = img.Image(width: 8, height: 6);
    for (int y = 0; y < source.height; y++) {
      for (int x = 0; x < source.width; x++) {
        source.setPixelRgb(x, y, x * 30, y * 40, 80);
      }
    }

    final X1BlockDitheredFrame result = ditherX1FourColorFrame(
      source,
      blockSize: 2,
    );

    expect(result.pixelCodes, hasLength(48));
    for (int y = 0; y < source.height; y += 2) {
      for (int x = 0; x < source.width; x += 2) {
        final int expected = result.pixelCodes[(y * source.width) + x];
        expect(result.pixelCodes[(y * source.width) + x + 1], expected);
        expect(result.pixelCodes[((y + 1) * source.width) + x], expected);
        expect(result.pixelCodes[((y + 1) * source.width) + x + 1], expected);
      }
    }
  });

  test('2x2 dithering produces fewer X1 transfer fragments than 1x1', () {
    final img.Image source = img.Image(width: 240, height: 416);
    for (int y = 0; y < source.height; y++) {
      for (int x = 0; x < source.width; x++) {
        source.setPixelRgb(
          x,
          y,
          (x * 255) ~/ 239,
          (y * 255) ~/ 415,
          ((x + y) * 255) ~/ 654,
        );
      }
    }

    int fragmentCount(int blockSize) {
      final X1BlockDitheredFrame dithered = ditherX1FourColorFrame(
        source,
        blockSize: blockSize,
      );
      final frame = packX1FourColorFrame(
        pixelCodes: dithered.pixelCodes,
        width: source.width,
        height: source.height,
        scanMode: X1EpaperScanMode.horizontal,
      );
      return buildX1CompressedWriteFragments(frame).length;
    }

    expect(fragmentCount(2), lessThan(fragmentCount(1)));
  });

  test('ordered fine dithering compresses better than error diffusion', () {
    final img.Image source = img.Image(width: 240, height: 416);
    for (int y = 0; y < source.height; y++) {
      for (int x = 0; x < source.width; x++) {
        source.setPixelRgb(
          x,
          y,
          (x * 255) ~/ 239,
          (y * 255) ~/ 415,
          ((x + y) * 255) ~/ 654,
        );
      }
    }

    int fragmentCount(X1DitherPattern pattern) {
      final X1BlockDitheredFrame dithered = ditherX1FourColorFrame(
        source,
        blockSize: 1,
        pattern: pattern,
      );
      final frame = packX1FourColorFrame(
        pixelCodes: dithered.pixelCodes,
        width: source.width,
        height: source.height,
        scanMode: X1EpaperScanMode.horizontal,
      );
      return buildX1CompressedWriteFragments(frame).length;
    }

    expect(
      fragmentCount(X1DitherPattern.ordered4x4),
      lessThan(fragmentCount(X1DitherPattern.errorDiffusion)),
    );
  });

  test('block dithering rejects a non-positive block size', () {
    expect(
      () =>
          ditherX1FourColorFrame(img.Image(width: 1, height: 1), blockSize: 0),
      throwsArgumentError,
    );
  });
}
