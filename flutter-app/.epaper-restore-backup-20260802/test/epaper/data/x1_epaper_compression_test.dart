import 'dart:typed_data';

import 'package:flutter_test/flutter_test.dart';
import 'package:latch/latch_ui/features/epaper/data/x1_epaper_compression.dart';

Uint8List _decodeLzo1x(Uint8List source) {
  final List<int> output = <int>[];
  int inputOffset = 0;
  int token = source[inputOffset++];

  void copyLiterals(int count) {
    output.addAll(source.sublist(inputOffset, inputOffset + count));
    inputOffset += count;
  }

  void copyMatch(int matchOffset, int count) {
    for (int i = 0; i < count; i++) {
      output.add(output[matchOffset++]);
    }
  }

  if (token > 17) {
    token -= 17;
    if (token >= 4) {
      copyLiterals(token);
      token = source[inputOffset++];
    } else {
      copyLiterals(token);
      token = source[inputOffset++];
    }
  }

  while (true) {
    if (token < 16) {
      if (token == 0) {
        while (source[inputOffset] == 0) {
          token += 255;
          inputOffset++;
        }
        token += 15 + source[inputOffset++];
      }
      copyLiterals(token + 3);
      token = source[inputOffset++];
      if (token < 16) {
        int matchOffset =
            output.length - 0x801 - (token >> 2) - (source[inputOffset++] << 2);
        copyMatch(matchOffset, 3);
        final int trailing = token & 3;
        if (trailing == 0) {
          token = source[inputOffset++];
          continue;
        }
        copyLiterals(trailing);
        token = source[inputOffset++];
      }
    }

    int matchOffset;
    int matchLength;
    if (token >= 64) {
      matchOffset =
          output.length - 1 - ((token >> 2) & 7) - (source[inputOffset++] << 3);
      matchLength = (token >> 5) + 1;
    } else if (token >= 32) {
      matchLength = token & 31;
      if (matchLength == 0) {
        while (source[inputOffset] == 0) {
          matchLength += 255;
          inputOffset++;
        }
        matchLength += 31 + source[inputOffset++];
      }
      matchOffset =
          output.length -
          1 -
          ((source[inputOffset] | (source[inputOffset + 1] << 8)) >> 2);
      inputOffset += 2;
      matchLength += 2;
    } else if (token >= 16) {
      matchLength = token & 7;
      if (matchLength == 0) {
        while (source[inputOffset] == 0) {
          matchLength += 255;
          inputOffset++;
        }
        matchLength += 7 + source[inputOffset++];
      }
      matchOffset =
          output.length -
          ((token & 8) << 11) -
          ((source[inputOffset] | (source[inputOffset + 1] << 8)) >> 2);
      inputOffset += 2;
      if (matchOffset == output.length) {
        return Uint8List.fromList(output);
      }
      matchOffset -= 0x4000;
      matchLength += 2;
    } else {
      matchOffset =
          output.length - 1 - (token >> 2) - (source[inputOffset++] << 2);
      matchLength = 2;
    }

    copyMatch(matchOffset, matchLength);
    final int trailing = source[inputOffset - 2] & 3;
    if (trailing == 0) {
      token = source[inputOffset++];
      continue;
    }
    copyLiterals(trailing);
    token = source[inputOffset++];
  }
}

void main() {
  group('encodeX1Lzo1xBlock', () {
    test('matches the canonical short literal encoding', () {
      expect(encodeX1Lzo1xBlock(Uint8List.fromList(const <int>[0xA5])), <int>[
        0x12,
        0xA5,
        0x11,
        0x00,
        0x00,
      ]);
    });

    test('uses real LZO matches and round-trips a full X1 page', () {
      final Uint8List source = Uint8List(2000)..fillRange(0, 2000, 0xA5);
      final Uint8List encoded = encodeX1Lzo1xBlock(source);

      expect(encoded.length, lessThan(40));
      expect(encoded.sublist(encoded.length - 3), <int>[0x11, 0, 0]);
      expect(_decodeLzo1x(encoded), source);
    });

    test('round-trips mixed image-like data', () {
      final Uint8List source = Uint8List.fromList(
        List<int>.generate(
          2000,
          (int index) =>
              index % 173 < 120 ? 0xFF : (index * 37 + (index ~/ 11)) & 0xFF,
        ),
      );
      final Uint8List encoded = encodeX1Lzo1xBlock(source);

      expect(encoded.length, lessThan(source.length));
      expect(_decodeLzo1x(encoded), source);
    });
  });

  group('buildX1CompressedWriteFragments', () {
    test('matches X1 F0D3 framing and page boundaries', () {
      final Uint8List frame = Uint8List(24960)..fillRange(0, 24960, 0xA5);
      final List<X1CompressedWriteFragment> fragments =
          buildX1CompressedWriteFragments(frame);

      expect(fragments.map((value) => value.pageIndex).toSet(), hasLength(13));
      final List<X1CompressedWriteFragment> firstPage = fragments
          .where((X1CompressedWriteFragment value) => value.pageIndex == 0)
          .toList();
      expect(firstPage, hasLength(1));
      expect(firstPage.single.command.take(4), <int>[0xF0, 0xD3, 0x00, 0x01]);
      expect(firstPage.single.command[5], 0x00);
      expect(firstPage.single.command[6], 0x00);
      expect(
        _decodeLzo1x(Uint8List.fromList(firstPage.single.command.sublist(7))),
        frame.sublist(0, 2000),
      );

      final List<X1CompressedWriteFragment> lastPage = fragments
          .where((X1CompressedWriteFragment value) => value.pageIndex == 0x0C)
          .toList();
      expect(lastPage, hasLength(1));
      expect(lastPage.single.command[3], 0x01);
      expect(lastPage.single.command[5], 0x0C);
      expect(
        _decodeLzo1x(Uint8List.fromList(lastPage.single.command.sublist(7))),
        frame.sublist(24000),
      );
    });

    test('marks only the final compressed fragment as complete', () {
      int state = 0x12345678;
      final Uint8List frame = Uint8List.fromList(
        List<int>.generate(2000, (_) {
          state = (1664525 * state + 1013904223) & 0xFFFFFFFF;
          return state >> 24;
        }),
      );
      final List<X1CompressedWriteFragment> fragments =
          buildX1CompressedWriteFragments(frame);

      expect(fragments.length, greaterThan(1));
      expect(
        fragments.take(fragments.length - 1),
        everyElement(
          isA<X1CompressedWriteFragment>().having(
            (value) => value.command[3],
            'continuation flag',
            0x00,
          ),
        ),
      );
      expect(fragments.last.command[3], 0x01);
      expect(
        fragments,
        everyElement(
          isA<X1CompressedWriteFragment>().having(
            (value) => value.command.length,
            'APDU length',
            lessThanOrEqualTo(257),
          ),
        ),
      );
    });
  });
}
