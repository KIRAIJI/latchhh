import 'dart:typed_data';

import 'package:flutter_test/flutter_test.dart';
import 'package:latch/latch_ui/features/epaper/data/x1_nfc_transceive_retry.dart';

void main() {
  test('retries the same fragment after transient NFC failures', () async {
    final Uint8List command = Uint8List.fromList(<int>[0xF0, 0xD3, 0, 1]);
    final List<Uint8List> attempts = <Uint8List>[];
    final List<Duration> waits = <Duration>[];

    final Uint8List response = await transceiveX1WriteFragment(
      command: command,
      transceive: (Uint8List value) async {
        attempts.add(Uint8List.fromList(value));
        if (attempts.length < 3) {
          throw StateError('transceive failed');
        }
        return Uint8List.fromList(<int>[0x90, 0x00]);
      },
      isTransientError: (_) => true,
      delay: (Duration value) async => waits.add(value),
    );

    expect(response, <int>[0x90, 0x00]);
    expect(attempts, <Uint8List>[command, command, command]);
    expect(waits, <Duration>[
      const Duration(milliseconds: 120),
      const Duration(milliseconds: 240),
    ]);
  });

  test('does not retry a permanent protocol failure', () async {
    var attempts = 0;

    await expectLater(
      transceiveX1WriteFragment(
        command: Uint8List.fromList(<int>[0xF0, 0xD3]),
        transceive: (Uint8List _) async {
          attempts++;
          throw const FormatException('bad APDU');
        },
        isTransientError: (_) => false,
      ),
      throwsFormatException,
    );
    expect(attempts, 1);
  });

  test('stops after the bounded retry limit', () async {
    var attempts = 0;

    await expectLater(
      transceiveX1WriteFragment(
        command: Uint8List.fromList(<int>[0xF0, 0xD3]),
        maxRetries: 3,
        transceive: (Uint8List _) async {
          attempts++;
          throw StateError('tag lost');
        },
        isTransientError: (_) => true,
        delay: (_) async {},
      ),
      throwsStateError,
    );
    expect(attempts, 4);
  });
}
