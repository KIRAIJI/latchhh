import 'package:flutter/services.dart';
import 'package:image/image.dart' as img;

/// Frame timing read from [assetPath] metadata (centisecond GIF delays).
class OpeningGifTiming {
  OpeningGifTiming._({
    required this.frameCount,
    required this.frameDurationsMs,
  });

  static const String assetPath = 'assets/branding/opening.gif';

  static OpeningGifTiming? _cached;

  final int frameCount;
  final List<int> frameDurationsMs;

  int get lastFrameIndex => frameCount - 1;

  Duration get totalDuration => Duration(
    milliseconds: frameDurationsMs.fold<int>(0, (sum, ms) => sum + ms),
  );

  int durationMsForFrame(int index) => frameDurationsMs[index];

  /// Reads GIF control-extension delays without decoding pixel data.
  static Future<OpeningGifTiming> resolve() async {
    if (_cached != null) return _cached!;

    final bytes = (await rootBundle.load(assetPath)).buffer.asUint8List();
    final info = img.GifDecoder().startDecode(bytes);
    if (info == null || info.frames.isEmpty) {
      throw StateError('Could not read animation timing from $assetPath');
    }

    final durations = <int>[];
    for (final frame in info.frames) {
      final centiseconds = frame.duration;
      // GIF delay 0 means "use default" (~100 ms).
      durations.add((centiseconds <= 0 ? 10 : centiseconds) * 10);
    }

    _cached = OpeningGifTiming._(
      frameCount: durations.length,
      frameDurationsMs: durations,
    );
    return _cached!;
  }
}
