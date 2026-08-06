import 'dart:typed_data';

import 'package:flutter_test/flutter_test.dart';
import 'package:latch/latch_ui/features/epaper/data/x1_epaper_compression.dart';

void main() {
  group('encodeX1Lzo1xLiteralBlock', () {
    test('matches canonical short and extended literal encodings', () {
      expect(
        encodeX1Lzo1xLiteralBlock(Uint8List.fromList(const <int>[0xA5])),
        <int>[0x12, 0xA5, 0x11, 0x00, 0x00],
      );

      final Uint8List encoded239 = encodeX1Lzo1xLiteralBlock(
        Uint8List(239)..fillRange(0, 239, 0xA5),
      );
      expect(encoded239.take(2), <int>[0x00, 0xDD]);
      expect(encoded239.length, 244);
      expect(encoded239.sublist(encoded239.length - 3), <int>[0x11, 0, 0]);

      final Uint8List encoded2000 = encodeX1Lzo1xLiteralBlock(
        Uint8List(2000)..fillRange(0, 2000, 0xA5),
      );
      expect(encoded2000.take(9), <int>[
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0xC5,
      ]);
      expect(encoded2000.length, 2012);
      expect(encoded2000.sublist(encoded2000.length - 3), <int>[0x11, 0, 0]);
    });
  });

  group('buildX1CompressedWriteFragments', () {
    test('matches X1 F0D3 framing for full and final image pages', () {
      final Uint8List frame = Uint8List(24960)..fillRange(0, 24960, 0xA5);
      final List<X1CompressedWriteFragment> fragments =
          buildX1CompressedWriteFragments(frame);

      final List<X1CompressedWriteFragment> firstPage = fragments
          .where((X1CompressedWriteFragment value) => value.pageIndex == 0)
          .toList();
      expect(firstPage, hasLength(9));
      expect(firstPage[7].command.take(7), <int>[
        0xF0,
        0xD3,
        0x00,
        0x01,
        0xFC,
        0x00,
        0x07,
      ]);
      expect(firstPage[7].command, hasLength(257));
      expect(firstPage[8].command.take(7), <int>[
        0xF0,
        0xD3,
        0x00,
        0x01,
        0x0E,
        0x00,
        0x08,
      ]);
      expect(firstPage[8].command, hasLength(19));
      expect(
        firstPage[8].command.sublist(firstPage[8].command.length - 3),
        <int>[0x11, 0x00, 0x00],
      );

      final List<X1CompressedWriteFragment> lastPage = fragments
          .where((X1CompressedWriteFragment value) => value.pageIndex == 0x0C)
          .toList();
      expect(lastPage, hasLength(4));
      expect(lastPage[2].command.take(7), <int>[
        0xF0,
        0xD3,
        0x00,
        0x01,
        0xFC,
        0x0C,
        0x02,
      ]);
      expect(lastPage[3].command.take(7), <int>[
        0xF0,
        0xD3,
        0x00,
        0x01,
        0xDC,
        0x0C,
        0x03,
      ]);
      expect(lastPage[3].command, hasLength(225));
    });

    test('preserves the vendor exact-multiple fragment flag behavior', () {
      final Uint8List frame = Uint8List(494)..fillRange(0, 494, 0xA5);
      final List<X1CompressedWriteFragment> fragments =
          buildX1CompressedWriteFragments(frame);

      expect(fragments, hasLength(2));
      expect(fragments[0].command, hasLength(257));
      expect(fragments[1].command, hasLength(257));
      expect(fragments[0].command[3], 0x00);
      expect(fragments[1].command[3], 0x00);
    });
  });
}
