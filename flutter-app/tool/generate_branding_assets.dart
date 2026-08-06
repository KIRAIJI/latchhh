import 'dart:io';

import 'package:image/image.dart' as img;

Future<void> main() async {
  final root = Directory.current.path;
  final gifPath = '$root/assets/branding/opening.gif';

  final gifBytes = await File(gifPath).readAsBytes();
  final gif = img.GifDecoder().decode(gifBytes);
  if (gif == null) {
    stderr.writeln('Failed to decode GIF');
    exit(1);
  }

  final frame = gif.frames.isNotEmpty ? gif.frames.first : gif;
  final launchPng = img.encodePng(frame);
  await File('$root/assets/branding/launch_frame.png').writeAsBytes(launchPng);

  // iOS native launch uses the GIF first frame; Android native splash is black-only.
  final iosLaunch =
      Directory('$root/ios/Runner/Assets.xcassets/LaunchImage.imageset');
  for (final name in [
    'LaunchImage.png',
    'LaunchImage@2x.png',
    'LaunchImage@3x.png',
  ]) {
    await File('${iosLaunch.path}/$name').writeAsBytes(launchPng);
  }

  stdout.writeln(
    'Generated launch frame (${frame.width}x${frame.height}, '
    '${gif.frames.length} frames)',
  );
}
