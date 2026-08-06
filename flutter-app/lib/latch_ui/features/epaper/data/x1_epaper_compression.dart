import 'dart:typed_data';

const int x1CompressionPageSize = 2000;
const int x1CompressionFragmentSize = 250;

// The reference updater keeps one MiniLZO work dictionary and reuses it for
// every independently compressed 2,000-byte page.
final List<int> _x1VendorDictionary = List<int>.filled(0x20000, 0);

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

void _writeLiteralRun(List<int> output, Uint8List source, int start, int end) {
  final int length = end - start;
  if (length == 0) {
    return;
  }

  if (output.isEmpty && length <= 238) {
    output.add(17 + length);
  } else if (length <= 3) {
    // MiniLZO stores up to three trailing literals in the low bits of the
    // preceding match's first offset byte.
    output[output.length - 2] |= length;
  } else if (length <= 18) {
    output.add(length - 3);
  } else {
    int remaining = length - 18;
    output.add(0x00);
    while (remaining > 255) {
      output.add(0x00);
      remaining -= 255;
    }
    output.add(remaining);
  }
  output.addAll(source.sublist(start, end));
}

int _dictionaryIndex(Uint8List source, int offset) {
  final int first =
      (((source[offset + 3] << 6) ^ source[offset + 2]) << 5) ^
      source[offset + 1];
  final int value = (first << 5) ^ source[offset];
  return ((value * 0x21) >> 5) & 0x3FFF;
}

void _writeMatch(List<int> output, int offset, int length) {
  if (offset <= 0 || offset > 0xBFFF || length < 3) {
    throw StateError('Invalid LZO1X match: offset=$offset, length=$length');
  }

  if (offset <= 0x0800 && length <= 8) {
    final int encodedOffset = offset - 1;
    output
      ..add(((length - 1) << 5) | ((encodedOffset & 7) << 2))
      ..add(encodedOffset >> 3);
    return;
  }

  if (offset <= 0x4000) {
    final int encodedOffset = offset - 1;
    if (length <= 33) {
      output.add(0x20 | (length - 2));
    } else {
      int remaining = length - 33;
      output.add(0x20);
      while (remaining > 255) {
        output.add(0x00);
        remaining -= 255;
      }
      output.add(remaining);
    }
    output
      ..add((encodedOffset & 63) << 2)
      ..add(encodedOffset >> 6);
    return;
  }

  final int encodedOffset = offset - 0x4000;
  final int offsetMarker = (encodedOffset & 0x4000) >> 11;
  if (length <= 9) {
    output.add(0x10 | offsetMarker | (length - 2));
  } else {
    int remaining = length - 9;
    output.add(0x10 | offsetMarker);
    while (remaining > 255) {
      output.add(0x00);
      remaining -= 255;
    }
    output.add(remaining);
  }
  output
    ..add((encodedOffset & 63) << 2)
    ..add(encodedOffset >> 6);
}

/// Encodes one independently decompressible page in the LZO1X-1 format used
/// by the vendor updater. Unlike the old literal-only stream, this emits real
/// matches and materially shortens the NFC transaction.
Uint8List encodeX1Lzo1xBlock(Uint8List source) {
  final List<int> output = <int>[];
  int literalStart = 0;

  if (source.length > 13) {
    final int matchSearchEnd = source.length - 13;
    int inputOffset = 4;

    while (inputOffset < matchSearchEnd) {
      int dictionaryIndex = _dictionaryIndex(source, inputOffset);
      int matchOffset = _x1VendorDictionary[dictionaryIndex];
      int distance = inputOffset - matchOffset;

      bool candidate = matchOffset > 0 && distance > 0 && distance <= 0xBFFF;
      if (candidate &&
          distance > 0x0800 &&
          source[matchOffset + 3] != source[inputOffset + 3]) {
        dictionaryIndex = (dictionaryIndex & 0x07FF) ^ (0x2000 | 0x001F);
        matchOffset = _x1VendorDictionary[dictionaryIndex];
        distance = inputOffset - matchOffset;
        candidate = matchOffset > 0 && distance > 0 && distance <= 0xBFFF;
      }

      final bool matched =
          candidate &&
          source[matchOffset] == source[inputOffset] &&
          source[matchOffset + 1] == source[inputOffset + 1] &&
          source[matchOffset + 2] == source[inputOffset + 2] &&
          source[matchOffset + 3] == source[inputOffset + 3];

      _x1VendorDictionary[dictionaryIndex] = inputOffset;
      if (!matched) {
        inputOffset += 1 + ((inputOffset - literalStart) >> 5);
        continue;
      }

      _writeLiteralRun(output, source, literalStart, inputOffset);

      int matchLength = 4;
      while (inputOffset + matchLength < source.length &&
          source[matchOffset + matchLength] ==
              source[inputOffset + matchLength]) {
        matchLength++;
      }
      _writeMatch(output, distance, matchLength);
      inputOffset += matchLength;
      literalStart = inputOffset;
    }
  }

  _writeLiteralRun(output, source, literalStart, source.length);
  output.addAll(const <int>[0x11, 0x00, 0x00]);
  return Uint8List.fromList(output);
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
    final Uint8List compressed = encodeX1Lzo1xBlock(
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
      final int fragmentFlag = fragmentIndex == fragmentCount - 1 ? 0x01 : 0x00;

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
