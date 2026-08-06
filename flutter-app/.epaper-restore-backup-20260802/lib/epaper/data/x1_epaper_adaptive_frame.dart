import 'dart:typed_data';

import 'package:image/image.dart' as img;

import 'x1_epaper_block_dither.dart';
import 'x1_epaper_codec.dart';
import 'x1_epaper_compression.dart';

class X1AdaptiveFrame {
  const X1AdaptiveFrame({
    required this.preview,
    required this.packed,
    required this.profile,
    required this.fragmentCount,
    required this.compressedBytes,
    required this.blockSize,
    required this.inkCoverage,
  });

  final img.Image preview;
  final Uint8List packed;
  final String profile;
  final int fragmentCount;
  final int compressedBytes;
  final int blockSize;
  final double inkCoverage;
}

X1AdaptiveFrame buildX1AdaptiveFrame({
  required img.Image source,
  required X1EpaperScanMode scanMode,
  int maxFineFragments = 60,
  double maxInkCoverage = 0.34,
}) {
  if (maxFineFragments < 13) {
    throw ArgumentError.value(
      maxFineFragments,
      'maxFineFragments',
      'Must allow at least one fragment for each X1 page.',
    );
  }
  if (maxInkCoverage <= 0 || maxInkCoverage > 1) {
    throw ArgumentError.value(
      maxInkCoverage,
      'maxInkCoverage',
      'Must be greater than zero and at most one.',
    );
  }

  final X1AdaptiveFrame vendorFloyd = _buildCandidate(
    source: source,
    scanMode: scanMode,
    profile: 'vendor Floyd-Steinberg',
    blockSize: 1,
    pattern: X1DitherPattern.errorDiffusion,
  );
  if (vendorFloyd.fragmentCount <= maxFineFragments) {
    return vendorFloyd;
  }

  final X1AdaptiveFrame vendorAtkinson = _buildCandidate(
    source: source,
    scanMode: scanMode,
    profile: 'vendor Atkinson',
    blockSize: 1,
    pattern: X1DitherPattern.atkinson,
  );
  if (vendorAtkinson.fragmentCount <= maxFineFragments) {
    return vendorAtkinson;
  }

  final X1AdaptiveFrame balancedColor = _buildCandidate(
    source: source,
    scanMode: scanMode,
    profile: 'balanced fine color',
    blockSize: 1,
    pattern: X1DitherPattern.balancedColor8x8,
  );
  if (balancedColor.fragmentCount <= maxFineFragments &&
      balancedColor.inkCoverage <= maxInkCoverage) {
    return balancedColor;
  }

  final X1AdaptiveFrame powerSafeColor = _buildCandidate(
    source: source,
    scanMode: scanMode,
    profile: 'power-safe fine color',
    blockSize: 1,
    pattern: X1DitherPattern.powerSafeColor8x8,
  );
  if (powerSafeColor.fragmentCount <= maxFineFragments) {
    return powerSafeColor;
  }

  final List<X1AdaptiveFrame> candidates = <X1AdaptiveFrame>[
    vendorFloyd,
    vendorAtkinson,
    balancedColor,
    powerSafeColor,
  ];
  for (final double scale in <double>[0.75, 0.5]) {
    final img.Image filtered = _lowPassAtFullResolution(source, scale);
    final int percent = (scale * 100).round();
    final X1AdaptiveFrame filteredFloyd = _buildCandidate(
      source: filtered,
      scanMode: scanMode,
      profile: 'filtered vendor Floyd $percent%',
      blockSize: 1,
      pattern: X1DitherPattern.errorDiffusion,
    );
    final X1AdaptiveFrame filteredAtkinson = _buildCandidate(
      source: filtered,
      scanMode: scanMode,
      profile: 'filtered vendor Atkinson $percent%',
      blockSize: 1,
      pattern: X1DitherPattern.atkinson,
    );
    final X1AdaptiveFrame filteredBest =
        filteredAtkinson.fragmentCount < filteredFloyd.fragmentCount
        ? filteredAtkinson
        : filteredFloyd;
    candidates
      ..add(filteredFloyd)
      ..add(filteredAtkinson);
    if (filteredBest.fragmentCount <= maxFineFragments) {
      return filteredBest;
    }
  }

  return candidates.reduce((X1AdaptiveFrame best, X1AdaptiveFrame candidate) {
    final bool bestIsPowerSafe = best.inkCoverage <= maxInkCoverage;
    final bool candidateIsPowerSafe = candidate.inkCoverage <= maxInkCoverage;
    if (candidateIsPowerSafe != bestIsPowerSafe) {
      return candidateIsPowerSafe ? candidate : best;
    }
    return candidate.fragmentCount < best.fragmentCount ? candidate : best;
  });
}

img.Image _lowPassAtFullResolution(img.Image source, double scale) {
  final img.Image reduced = img.copyResize(
    source,
    width: (source.width * scale).round().clamp(1, source.width),
    height: (source.height * scale).round().clamp(1, source.height),
    interpolation: img.Interpolation.cubic,
  );
  return img.copyResize(
    reduced,
    width: source.width,
    height: source.height,
    interpolation: img.Interpolation.cubic,
  );
}

X1AdaptiveFrame _buildCandidate({
  required img.Image source,
  required X1EpaperScanMode scanMode,
  required String profile,
  required int blockSize,
  required X1DitherPattern pattern,
}) {
  final X1BlockDitheredFrame dithered = ditherX1FourColorFrame(
    source,
    blockSize: blockSize,
    pattern: pattern,
  );
  final Uint8List packed = packX1FourColorFrame(
    pixelCodes: dithered.pixelCodes,
    width: source.width,
    height: source.height,
    scanMode: scanMode,
  );
  final List<X1CompressedWriteFragment> fragments =
      buildX1CompressedWriteFragments(packed);
  final int compressedBytes = fragments.fold<int>(
    0,
    (int total, X1CompressedWriteFragment fragment) =>
        total + fragment.command.length - 7,
  );
  final int inkPixels = dithered.pixelCodes
      .where((int panelCode) => panelCode != 1)
      .length;
  return X1AdaptiveFrame(
    preview: dithered.preview,
    packed: packed,
    profile: profile,
    fragmentCount: fragments.length,
    compressedBytes: compressedBytes,
    blockSize: blockSize,
    inkCoverage: inkPixels / dithered.pixelCodes.length,
  );
}
