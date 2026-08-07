import 'dart:typed_data';

import 'package:flutter_test/flutter_test.dart';
import 'package:latch/latch_ui/features/epaper/data/x1_iso7816_transport.dart';

void main() {
  test('iOS response appends ISO 7816 status bytes to the payload', () {
    final Uint8List response = encodeIosIso7816Response(
      Uint8List.fromList(<int>[0x01, 0x02]),
      0x90,
      0x00,
    );

    expect(response, <int>[0x01, 0x02, 0x90, 0x00]);
  });

  test('iOS response preserves a status-only APDU response', () {
    final Uint8List response = encodeIosIso7816Response(
      Uint8List(0),
      0x68,
      0xca,
    );

    expect(response, <int>[0x68, 0xca]);
  });

  test('iOS response rejects invalid status bytes', () {
    expect(
      () => encodeIosIso7816Response(Uint8List(0), 0x100, 0),
      throwsRangeError,
    );
  });
}
