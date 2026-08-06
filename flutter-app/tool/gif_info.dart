import 'dart:io';
import 'package:image/image.dart' as img;
Future<void> main() async {
  final gif = img.GifDecoder().decode(await File('assets/branding/opening.gif').readAsBytes())!;
  var total = 0;
  for (final f in gif.frames) { total += f.frameDuration; }
  print('frames=${gif.frames.length} totalMs=$total size=${gif.width}x${gif.height}');
}
