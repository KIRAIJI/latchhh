import 'dart:convert';
import 'dart:typed_data';

import 'x1_epaper_codec.dart';

class X1NfcDeviceInfo {
  const X1NfcDeviceInfo({
    required this.manufacturerCode,
    required this.colorFamily,
    required this.width,
    required this.height,
    required this.scanMode,
    required this.colorCount,
    required this.blackCode,
    required this.whiteCode,
    required this.redCode,
    required this.yellowCode,
    required this.supportsCompression,
    required this.requiresPin,
    required this.isFourColorScreen,
  });

  final int manufacturerCode;
  final int colorFamily;
  final int width;
  final int height;
  final X1EpaperScanMode scanMode;
  final int colorCount;
  final int? blackCode;
  final int? whiteCode;
  final int? redCode;
  final int? yellowCode;
  final bool supportsCompression;
  final bool requiresPin;
  final bool isFourColorScreen;

  bool get isLatchDisplay =>
      width == 240 &&
      height == 416 &&
      colorCount == 4 &&
      blackCode == 0 &&
      whiteCode == 1 &&
      redCode == 3 &&
      yellowCode == 2;
}

class X1NfcEpaperProtocol {
  X1NfcEpaperProtocol._();

  static const int blockPayloadSize = 250;

  static final Uint8List selectApplication = Uint8List.fromList(const <int>[
    0x00,
    0xA4,
    0x04,
    0x00,
    0x07,
    0xD2,
    0x76,
    0x00,
    0x00,
    0x85,
    0x01,
    0x01,
  ]);

  static final Uint8List readDeviceInfo = Uint8List.fromList(const <int>[
    0x00,
    0xD1,
    0x00,
    0x00,
    0x00,
  ]);

  static final Uint8List readDeviceDescriptor = Uint8List.fromList(const <int>[
    0xF0,
    0xD8,
    0x00,
    0x00,
    0x05,
    0x00,
    0x00,
    0x00,
    0x00,
    0x0E,
  ]);

  static final Uint8List refreshPoll = Uint8List.fromList(const <int>[
    0xF0,
    0xDE,
    0x00,
    0x00,
    0x01,
  ]);

  static Uint8List buildWriteBlock({
    required int plane,
    required int blockIndex,
    required Uint8List payload,
    required int payloadOffset,
  }) {
    if (plane < 0 || plane > 0xFF) {
      throw RangeError.range(plane, 0, 0xFF, 'plane');
    }
    if (blockIndex < 0 || blockIndex > 0xFF) {
      throw RangeError.range(blockIndex, 0, 0xFF, 'blockIndex');
    }
    if (payloadOffset < 0 || payloadOffset > payload.length) {
      throw RangeError.range(payloadOffset, 0, payload.length, 'payloadOffset');
    }

    final Uint8List command = Uint8List(5 + blockPayloadSize);
    command.setAll(0, <int>[0xF0, 0xD2, plane, blockIndex, blockPayloadSize]);

    final int available = payload.length - payloadOffset;
    final int take = available.clamp(0, blockPayloadSize);
    if (take > 0) {
      command.setRange(5, 5 + take, payload, payloadOffset);
    }

    return command;
  }

  static Uint8List buildRefreshTrigger({
    bool alternateMode = false,
    int displayIndex = 0,
  }) {
    if (displayIndex < 0 || displayIndex > 0xFF) {
      throw RangeError.range(displayIndex, 0, 0xFF, 'displayIndex');
    }
    return Uint8List.fromList(<int>[
      0xF0,
      0xD4,
      alternateMode ? 0x85 : 0x05,
      displayIndex,
      0x00,
    ]);
  }

  static bool hasExactSuccessStatus(Uint8List response) =>
      response.length >= 2 &&
      response[response.length - 2] == 0x90 &&
      response[response.length - 1] == 0x00;

  static X1NfcDeviceInfo parseDeviceInfo({
    required Uint8List deviceInfoResponse,
    required Uint8List descriptorResponse,
  }) {
    final Uint8List bytes = Uint8List.fromList(<int>[
      ...deviceInfoResponse,
      ...descriptorResponse,
    ]);
    if (bytes.length < 11 || bytes[0] != 0xA0) {
      throw const FormatException('Missing X1 A0 device-info record.');
    }

    final int a0Length = bytes[1];
    final int a1Offset = 2 + a0Length;
    if (a1Offset + 4 > bytes.length || bytes[a1Offset] != 0xA1) {
      throw const FormatException('Missing X1 A1 display record.');
    }

    final int rawHeight = _readUint16(bytes, 5);
    final int width = _readUint16(bytes, 7);
    final int refreshScan = bytes[a1Offset + 2];
    if (refreshScan != 0 && refreshScan != 1) {
      throw FormatException('Unsupported X1 refreshScan value: $refreshScan.');
    }

    final int packedDisplay = bytes[a1Offset + 3];
    final int declaredColorCount = packedDisplay & 0x0F;
    if (declaredColorCount < 2 || declaredColorCount > 4) {
      throw FormatException('Unsupported X1 color count: $declaredColorCount.');
    }
    if (a1Offset + 4 + declaredColorCount > bytes.length) {
      throw const FormatException('Truncated X1 color descriptors.');
    }

    int? blackCode;
    int? whiteCode;
    int? redCode;
    int? yellowCode;
    final int codeShift = 6 - declaredColorCount;
    final int codeMask = declaredColorCount == 2 ? 0x01 : 0x03;
    for (int i = 0; i < declaredColorCount; i++) {
      final int descriptor = bytes[a1Offset + 4 + i];
      final int type = (descriptor >> 5) & 0x07;
      final int code = (descriptor >> codeShift) & codeMask;
      switch (type) {
        case 0:
          blackCode = code;
        case 1:
          whiteCode = code;
        case 2:
          redCode = code;
        case 3:
          yellowCode = code;
      }
    }

    bool supportsCompression = false;
    int cursor = a1Offset;
    final int dataEnd = bytes.length - 2;
    while (cursor + 2 <= dataEnd) {
      final int length = bytes[cursor + 1];
      final int next = cursor + 2 + length;
      if (next > dataEnd || next <= cursor) {
        break;
      }
      if (bytes[cursor] == 0xD1 && length >= 1) {
        supportsCompression = bytes[cursor + 2] != 0;
        break;
      }
      cursor = next;
    }

    final int statusWord =
        (bytes[bytes.length - 2] << 8) | bytes[bytes.length - 1];
    if (statusWord != 0x9000 && statusWord != 0x6985) {
      throw FormatException(
        'Unexpected X1 device-info status '
        '${statusWord.toRadixString(16).padLeft(4, '0').toUpperCase()}.',
      );
    }

    bool isFourColorScreen = false;
    if (statusWord == 0x9000 && bytes.length >= 16) {
      final String descriptor = ascii.decode(
        bytes.sublist(bytes.length - 16, bytes.length - 2),
        allowInvalid: true,
      );
      isFourColorScreen = descriptor == '4_color Screen';
    }

    return X1NfcDeviceInfo(
      manufacturerCode: bytes[2],
      colorFamily: bytes[4],
      width: width,
      height: isFourColorScreen ? rawHeight ~/ 2 : rawHeight,
      scanMode: refreshScan == 0
          ? X1EpaperScanMode.vertical
          : X1EpaperScanMode.horizontal,
      colorCount: isFourColorScreen ? 4 : declaredColorCount,
      blackCode: isFourColorScreen ? 0 : blackCode,
      whiteCode: isFourColorScreen ? 1 : whiteCode,
      redCode: isFourColorScreen ? 3 : redCode,
      yellowCode: isFourColorScreen ? 2 : yellowCode,
      supportsCompression: supportsCompression,
      requiresPin: statusWord == 0x6985,
      isFourColorScreen: isFourColorScreen,
    );
  }

  static int _readUint16(Uint8List bytes, int offset) {
    if (offset < 0 || offset + 1 >= bytes.length) {
      throw const FormatException('Truncated X1 dimension record.');
    }
    return (bytes[offset] << 8) | bytes[offset + 1];
  }
}
