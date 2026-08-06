import 'package:flutter_test/flutter_test.dart';
import 'package:image/image.dart' as img;
import 'package:latch/latch_ui/features/epaper/data/x1_vendor_image_pipeline.dart';

void main() {
  test('uses the vendor black-white-red-yellow palette order', () {
    final img.Image source = img.Image(width: 4, height: 1)
      ..setPixelRgb(0, 0, 0, 0, 0)
      ..setPixelRgb(1, 0, 255, 255, 255)
      ..setPixelRgb(2, 0, 255, 0, 0)
      ..setPixelRgb(3, 0, 255, 255, 0);

    expect(quantizeX1VendorBwry(source), <int>[
      x1VendorBlack,
      x1VendorWhite,
      x1VendorRed,
      x1VendorYellow,
    ]);
  });

  test('matches the vendor integer three-neighbour error diffusion', () {
    final img.Image source = img.Image(width: 2, height: 2);
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        source.setPixelRgb(x, y, 100, 100, 100);
      }
    }

    expect(quantizeX1VendorBwry(source), <int>[
      x1VendorBlack,
      x1VendorWhite,
      x1VendorBlack,
      x1VendorBlack,
    ]);
  });

  test('scales directly to the panel size', () {
    final img.Image source = img.Image(width: 6, height: 2)
      ..clear(img.ColorRgb8(12, 34, 56));

    final img.Image resized = resizeX1VendorImage(source, 240, 416);

    expect(resized.width, 240);
    expect(resized.height, 416);
    expect(resized.getPixel(0, 0).r.toInt(), 12);
    expect(resized.getPixel(239, 415).b.toInt(), 56);
  });
}
