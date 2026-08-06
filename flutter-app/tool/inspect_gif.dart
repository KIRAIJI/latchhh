import 'dart:io';

import 'package:image/image.dart' as img;

Future<void> main() async {
  for (final path in [
    'assets/branding/opening.gif',
    r'C:\Users\Rhea\Downloads\LOGO.gif',
    'assets/branding/launch_frame.png',
  ]) {
    final file = File(path);
    if (!file.existsSync()) {
      stdout.writeln('MISSING: $path');
      continue;
    }

    final bytes = await file.readAsBytes();
    stdout.writeln('\n=== $path (${bytes.length} bytes) ===');

    if (path.endsWith('.gif')) {
      final gif = img.GifDecoder().decode(bytes);
      if (gif == null) {
        stdout.writeln('decode failed');
        continue;
      }
      var totalMs = 0;
      for (final frame in gif.frames) {
        totalMs += frame.frameDuration;
      }
      stdout.writeln(
        'canvas=${gif.width}x${gif.height} frames=${gif.frames.length} totalMs=$totalMs',
      );

      for (var i = 0; i < gif.frames.length; i++) {
        final frame = gif.frames[i];
        final bounds = _nonTransparentBounds(frame);
        stdout.writeln(
          'frame[$i] ${frame.width}x${frame.height} dur=${frame.frameDuration}ms bounds=$bounds',
        );
        if (i >= 2 && i < gif.frames.length - 1 && i % 15 == 0) {
          // sampled mid frames
        } else if (i > 2 && i != gif.frames.length - 1) {
          continue;
        }
      }
    } else {
      final image = img.decodeImage(bytes);
      if (image == null) {
        stdout.writeln('decode failed');
        continue;
      }
      stdout.writeln(
        'size=${image.width}x${image.height} bounds=${_nonTransparentBounds(image)}',
      );
    }
  }
}

({int left, int top, int right, int bottom, int w, int h})? _nonTransparentBounds(
  img.Image image,
) {
  var left = image.width;
  var top = image.height;
  var right = 0;
  var bottom = 0;

  for (var y = 0; y < image.height; y++) {
    for (var x = 0; x < image.width; x++) {
      final p = image.getPixel(x, y);
      if (p.a > 10) {
        if (x < left) left = x;
        if (y < top) top = y;
        if (x > right) right = x;
        if (y > bottom) bottom = y;
      }
    }
  }

  if (right < left || bottom < top) return null;
  return (
    left: left,
    top: top,
    right: right,
    bottom: bottom,
    w: right - left + 1,
    h: bottom - top + 1,
  );
}
