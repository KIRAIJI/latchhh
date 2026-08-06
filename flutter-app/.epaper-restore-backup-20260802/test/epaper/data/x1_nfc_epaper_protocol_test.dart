import 'dart:convert';
import 'dart:typed_data';

import 'package:flutter_test/flutter_test.dart';
import 'package:latch/latch_ui/features/epaper/data/x1_epaper_codec.dart';
import 'package:latch/latch_ui/features/epaper/data/x1_nfc_epaper_protocol.dart';

void main() {
  group('X1NfcEpaperProtocol', () {
    test('builds an exact padded F0D2 block', () {
      final payload = Uint8List.fromList(
        List<int>.generate(251, (index) => index & 0xFF),
      );

      final block = X1NfcEpaperProtocol.buildWriteBlock(
        plane: 0,
        blockIndex: 1,
        payload: payload,
        payloadOffset: 250,
      );

      expect(block, hasLength(255));
      expect(block.sublist(0, 5), <int>[0xF0, 0xD2, 0x00, 0x01, 0xFA]);
      expect(block[5], 250);
      expect(block.sublist(6).every((byte) => byte == 0), isTrue);
    });

    test('requires the exact 9000 acknowledgement', () {
      expect(
        X1NfcEpaperProtocol.hasExactSuccessStatus(
          Uint8List.fromList(<int>[0x01, 0x90, 0x00]),
        ),
        isTrue,
      );
      expect(
        X1NfcEpaperProtocol.hasExactSuccessStatus(
          Uint8List.fromList(<int>[0x91, 0x00]),
        ),
        isFalse,
      );
    });

    test('accepts both vendor refresh acknowledgement values', () {
      expect(
        X1NfcEpaperProtocol.parseRefreshState(
          Uint8List.fromList(<int>[0x01, 0x90, 0x00]),
        ),
        X1EpaperRefreshState.acknowledged,
      );
      expect(
        X1NfcEpaperProtocol.parseRefreshState(
          Uint8List.fromList(<int>[0x00, 0x90, 0x00]),
        ),
        X1EpaperRefreshState.acknowledged,
      );
      expect(
        X1NfcEpaperProtocol.parseRefreshState(
          Uint8List.fromList(<int>[0x69, 0x8A]),
        ),
        X1EpaperRefreshState.unknown,
      );
    });

    test('parses the X1 four-color 240x416 descriptor', () {
      final info = X1NfcEpaperProtocol.parseDeviceInfo(
        deviceInfoResponse: _deviceInfoResponse(),
        descriptorResponse: Uint8List.fromList(<int>[
          ...ascii.encode('4_color Screen'),
          0x90,
          0x00,
        ]),
      );

      expect(info.width, 240);
      expect(info.height, 416);
      expect(info.scanMode, X1EpaperScanMode.horizontal);
      expect(info.colorCount, 4);
      expect(info.blackCode, 0);
      expect(info.whiteCode, 1);
      expect(info.redCode, 3);
      expect(info.yellowCode, 2);
      expect(info.supportsCompression, isTrue);
      expect(info.requiresPin, isFalse);
      expect(info.isLatchDisplay, isTrue);
    });

    test('rejects malformed device information', () {
      expect(
        () => X1NfcEpaperProtocol.parseDeviceInfo(
          deviceInfoResponse: Uint8List.fromList(<int>[0x90, 0x00]),
          descriptorResponse: Uint8List.fromList(<int>[0x90, 0x00]),
        ),
        throwsFormatException,
      );
    });

    test('builds both five-byte refresh variants', () {
      expect(X1NfcEpaperProtocol.buildRefreshTrigger(), <int>[
        0xF0,
        0xD4,
        0x05,
        0x00,
        0x00,
      ]);
      expect(
        X1NfcEpaperProtocol.buildRefreshTrigger(
          alternateMode: true,
          displayIndex: 2,
        ),
        <int>[0xF0, 0xD4, 0x85, 0x02, 0x00],
      );
    });
  });
}

Uint8List _deviceInfoResponse() {
  return Uint8List.fromList(<int>[
    0xA0,
    0x07,
    0x00,
    0x00,
    0x40,
    0x03,
    0x40,
    0x00,
    0xF0,
    0xA1,
    0x06,
    0x01,
    0x44,
    0x00,
    0x24,
    0x4C,
    0x68,
    0xB1,
    0x01,
    0x01,
    0xB2,
    0x01,
    0x00,
    0xB3,
    0x01,
    0x01,
    0xC0,
    0x04,
    0x00,
    0x00,
    0x00,
    0x01,
    0xC1,
    0x04,
    0x00,
    0x00,
    0x00,
    0x02,
    0xD1,
    0x02,
    0x01,
    0x20,
    0x90,
    0x00,
  ]);
}
