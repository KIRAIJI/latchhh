import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:image/image.dart' as img;
import 'package:latch/main.dart';
// ignore: implementation_imports
import 'package:nfc_manager/src/nfc_manager_ios/pigeon.g.dart';

void main() {
  testWidgets(
    'iOS cancellation and native timeout release the writer for retry',
    (tester) async {
      debugDefaultTargetPlatformOverride = TargetPlatform.iOS;
      final bridge = UploaderBridge();
      final channels = <BasicMessageChannel<Object?>>[];
      var starts = 0;
      void handle(String name, Object? Function(Object?) result) {
        final channel = BasicMessageChannel<Object?>(
          'dev.flutter.pigeon.nfc_manager.HostApiPigeon.$name',
          HostApiPigeon.pigeonChannelCodec,
        );
        channels.add(channel);
        tester.binding.defaultBinaryMessenger
            .setMockDecodedMessageHandler<Object?>(
              channel,
              (message) async => result(message),
            );
      }

      handle('tagSessionReadingAvailable', (_) => [true]);
      handle('tagSessionBegin', (args) {
        starts++;
        expect(
          (args! as List).last,
          false,
          reason: 'Writing keeps the session open after discovery.',
        );
        return [null];
      });
      handle('tagSessionInvalidate', (_) => [null]);
      addTearDown(() {
        for (final channel in channels) {
          tester.binding.defaultBinaryMessenger
              .setMockDecodedMessageHandler<Object?>(channel, null);
        }
        bridge.dispose();
      });
      await tester.pumpWidget(
        MaterialApp(home: UploaderPage(bridge: bridge, embedded: true)),
      );
      await tester.pump();
      await bridge.requestIngestSelectedImageBytes(
        Uint8List.fromList(
          img.encodePng(
            img.Image(width: 240, height: 416)
              ..clear(img.ColorRgb8(255, 255, 255)),
          ),
        ),
      );
      for (final code in [
        NfcReaderErrorCodePigeon.readerSessionInvalidationErrorUserCanceled,
        NfcReaderErrorCodePigeon.readerSessionInvalidationErrorSessionTimeout,
      ]) {
        await bridge.requestWriteTag();
        expect(bridge.busy, isTrue);
        tester.binding.channelBuffers.push(
          'dev.flutter.pigeon.nfc_manager.FlutterApiPigeon.tagSessionDidInvalidateWithError',
          FlutterApiPigeon.pigeonChannelCodec.encodeMessage([
            NfcReaderSessionErrorPigeon(
              code: code,
              message: 'Simulated native session end',
            ),
          ]),
          (_) {},
        );
        await tester.pump();
        expect(bridge.busy, isFalse);
      }
      await bridge.requestWriteTag();
      expect(starts, 3);
    await bridge.requestCancelWriteTag();
    await tester.pumpWidget(const SizedBox());
    await tester.pump();
    debugDefaultTargetPlatformOverride = null;
    expect(tester.takeException(), isNull);
    },
  );
}
