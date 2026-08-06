import 'dart:typed_data';

const int x1CompressionPageSize = 2000;
const int x1CompressionFragmentSize = 250;

final class X1CompressedWriteFragment {
  const X1CompressedWriteFragment({
    required this.pageIndex,
    required this.fragmentIndex,
    required this.command,
  });

  final int pageIndex;
  final int fragmentIndex;
  final Uint8List command;
}

/// Produces a standards-compatible LZO1X stream containing one literal run.
///
/// X1 displays only require a valid LZO1X stream; matches are an optimization,
/// not a protocol requirement. Keeping each 2000-byte page as one literal run
/// makes the encoder deterministic while remaining compatible with MiniLZO.
Uint8List encodeX1Lzo1xLiteralBlock(Uint8List source) {
  if (source.isEmpty) {
    return Uint8List.fromList(const <int>[0x11, 0x00, 0x00]);
  }

  final BytesBuilder output = BytesBuilder(copy: false);
  if (source.length <= 238) {
    output.addByte(17 + source.length);
  } else {
    int remaining = source.length - 18;
    output.addByte(0x00);
    while (remaining > 255) {
      output.addByte(0x00);
      remaining -= 255;
    }
    output.addByte(remaining);
  }
  output
    ..add(source)
    ..add(const <int>[0x11, 0x00, 0x00]);
  return output.takeBytes();
}

List<X1CompressedWriteFragment> buildX1CompressedWriteFragments(
  Uint8List frame, {
  int plane = 0x00,
}) {
  if (plane < 0 || plane > 0xFF) {
    throw RangeError.range(plane, 0, 0xFF, 'plane');
  }
  if (frame.isEmpty) {
    throw ArgumentError.value(frame, 'frame', 'must not be empty');
  }

  final int pageCount = (frame.length / x1CompressionPageSize).ceil();
  if (pageCount > 0x100) {
    throw RangeError(
      'X1 F0D3 supports at most 256 independently compressed pages.',
    );
  }

  final List<X1CompressedWriteFragment> result = <X1CompressedWriteFragment>[];
  for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
    final int pageStart = pageIndex * x1CompressionPageSize;
    final int pageEnd = (pageStart + x1CompressionPageSize).clamp(
      0,
      frame.length,
    );
    final Uint8List compressed = encodeX1Lzo1xLiteralBlock(
      Uint8List.sublistView(frame, pageStart, pageEnd),
    );
    final int fullFragmentCount =
        compressed.length ~/ x1CompressionFragmentSize;
    final int remainder = compressed.length % x1CompressionFragmentSize;
    final int fragmentCount = fullFragmentCount + (remainder == 0 ? 0 : 1);

    for (
      int fragmentIndex = 0;
      fragmentIndex < fragmentCount;
      fragmentIndex++
    ) {
      final int dataStart = fragmentIndex * x1CompressionFragmentSize;
      final int dataEnd = (dataStart + x1CompressionFragmentSize).clamp(
        0,
        compressed.length,
      );
      final int dataLength = dataEnd - dataStart;
      final bool isRemainder = fragmentIndex >= fullFragmentCount;
      final bool isLastFullBeforeRemainder =
          remainder > 0 && fragmentIndex == fullFragmentCount - 1;
      final int fragmentFlag =
          compressed.length <= x1CompressionFragmentSize ||
              isRemainder ||
              isLastFullBeforeRemainder
          ? 0x01
          : 0x00;

      final Uint8List command = Uint8List(7 + dataLength)
        ..[0] = 0xF0
        ..[1] = 0xD3
        ..[2] = plane
        ..[3] = fragmentFlag
        ..[4] = dataLength + 2
        ..[5] = pageIndex
        ..[6] = fragmentIndex
        ..setRange(7, 7 + dataLength, compressed, dataStart);
      result.add(
        X1CompressedWriteFragment(
          pageIndex: pageIndex,
          fragmentIndex: fragmentIndex,
          command: command,
        ),
      );
    }
  }
  return result;
}
