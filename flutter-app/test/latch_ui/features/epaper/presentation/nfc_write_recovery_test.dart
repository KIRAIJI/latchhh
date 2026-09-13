import 'dart:async';
import 'dart:convert';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:image/image.dart' as img;
import 'package:latch/main.dart';
import 'package:latch/latch_ui/features/epaper/data/x1_nfc_epaper_protocol.dart';
// Exercise the actual local plugin channels with simulated native replies.
// ignore: implementation_imports
import 'package:nfc_manager/src/nfc_manager_android/pigeon.g.dart';

void main() {
  final binding = TestWidgetsFlutterBinding.ensureInitialized();
  late UploaderBridge bridge;
  late _NfcHost host;

  Future<void> open(WidgetTester tester) async {
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
    await tester.pump();
    expect(bridge.hasProcessedImage, isTrue);
  }

  testWidgets(
    'NFC cancellation, tag loss, failure, disposal and retry recover safely',
    (tester) async {
      bridge = UploaderBridge();
      host = _NfcHost(binding)..install();
      addTearDown(() {
        host.uninstall();
        bridge.dispose();
      });
      // Cancel during preflight.
      {
        await open(tester);
        final availability = Completer<Object?>();
        host.availability = () => availability.future;
        final starting = bridge.requestWriteTag();
        await tester.pump();
        await bridge.requestCancelWriteTag();
        availability.complete([true]);
        await starting;
        expect(bridge.busy, isFalse);
        expect(bridge.status, 'Update cancelled.');
        expect(host.starts, 0);
        host.availability = () async => [true];
        await bridge.requestWriteTag();
        expect(host.starts, 1);
        await bridge.requestCancelWriteTag();
        await tester.pump();
        final delayedStart = Completer<Object?>();
        host.startReply = () => delayedStart.future;
        final pending = bridge.requestWriteTag();
        await tester.pump();
        final stopsBefore = host.stops;
        await bridge.requestCancelWriteTag();
        await tester.pump();
        delayedStart.complete([null]);
        await pending;
        await tester.pump();
        expect(
          host.stops,
          stopsBefore + 2,
          reason: 'Close a start that finishes after cancellation.',
        );
        expect(bridge.busy, isFalse);
        host.startReply = null;
      }
      await tester.pumpWidget(const SizedBox());
      await tester.pump();
      bridge.dispose();
      bridge = UploaderBridge();
      host.uninstall();
      host = _NfcHost(binding)..install();
      // cancel stops a pending transfer and retry waits for cleanup.
      {
        await open(tester);
        final transfer = Completer<Object?>();
        final cleanup = Completer<Object?>();
        host.transfer = (_) => transfer.future;
        host.cleanup = () => cleanup.future;
        await bridge.requestWriteTag();
        host.discover();
        await tester.pump();
        expect(host.commands, 1);
        await bridge.requestCancelWriteTag();
        expect(bridge.busy, isFalse);
        final retry = bridge.requestWriteTag();
        await tester.pump();
        expect(host.starts, 1);
        transfer.complete([
          Uint8List.fromList([0x90, 0x00]),
        ]);
        await tester.pump();
        expect(
          host.commands,
          1,
          reason: 'Cancelled transfer must not send the next APDU.',
        );
        cleanup.complete([null]);
        await retry;
        expect(host.starts, 2);
        expect(bridge.busy, isTrue);
        expect(bridge.status, isNot(contains('successfully')));
        await bridge.requestCancelWriteTag();
        await tester.pump();
      }
      await tester.pumpWidget(const SizedBox());
      await tester.pump();
      bridge.dispose();
      bridge = UploaderBridge();
      host.uninstall();
      host = _NfcHost(binding)..install();
      // tag removal has a bounded reconnect wait and permits another write.
      {
        await open(tester);
        host.transfer = (_) async => ['tag_lost', 'TagLostException', null];
        await bridge.requestWriteTag();
        host.discover();
        await tester.pump();
        await tester.pump(const Duration(milliseconds: 400));
        expect(host.starts, 2);
        await tester.pump(const Duration(seconds: 61));
        expect(bridge.busy, isFalse);
        expect(bridge.status, startsWith('Update failed.'));
        await bridge.requestWriteTag();
        expect(host.starts, 3);
        await bridge.requestCancelWriteTag();
        await tester.pump();
      }
      await tester.pumpWidget(const SizedBox());
      await tester.pump();
      bridge.dispose();
      bridge = UploaderBridge();
      host.uninstall();
      host = _NfcHost(binding)..install();
      // permanent transfer failure and session-start failure both release busy state.
      {
        await open(tester);
        host.transfer = (_) async => [
          Uint8List.fromList([0x6a, 0x82]),
        ];
        await bridge.requestWriteTag();
        host.discover();
        await tester.pump();
        expect(bridge.busy, isFalse);
        expect(bridge.status, startsWith('Update failed.'));
        host.startError = true;
        await bridge.requestWriteTag();
        expect(bridge.busy, isFalse);
        host.startError = false;
        host.transfer = (command) async {
          if (listEquals(command, X1NfcEpaperProtocol.readDeviceInfo)) {
            return [_fixtureDeviceInfo()];
          }
          if (listEquals(command, X1NfcEpaperProtocol.readDeviceDescriptor)) {
            return [
              Uint8List.fromList([
                ...ascii.encode('4_color Screen'),
                0x90,
                0x00,
              ]),
            ];
          }
          if (listEquals(command, X1NfcEpaperProtocol.refreshPoll)) {
            return [
              Uint8List.fromList([0x00, 0x90, 0x00]),
            ];
          }
          return [
            Uint8List.fromList([0x90, 0x00]),
          ];
        };
        await bridge.requestWriteTag();
        expect(bridge.busy, isTrue);
        host.discover();
        for (var tick = 0; tick < 200 && bridge.busy; tick++) {
          await tester.pump(const Duration(milliseconds: 50));
        }
        expect(bridge.status, 'E-Paper updated successfully.');
        expect(bridge.busy, isFalse);
        expect(host.commands, greaterThan(3));
        await tester.pump();
      }
      await tester.pumpWidget(const SizedBox());
      await tester.pump();
      bridge.dispose();
      bridge = UploaderBridge();
      host.uninstall();
      host = _NfcHost(binding)..install();
      // leaving uploader cancels the active session.
      {
        await open(tester);
        await bridge.requestWriteTag();
        await tester.pumpWidget(const SizedBox());
        await tester.pump();
        expect(host.stops, 1);
        expect(bridge.busy, isFalse);
        expect(tester.takeException(), isNull);
      }
    },
  );
}

class _NfcHost {
  _NfcHost(this.binding);
  final TestWidgetsFlutterBinding binding;
  int starts = 0;
  int stops = 0;
  int commands = 0;
  bool startError = false;
  Future<Object?> Function()? startReply;
  Future<Object?> Function() availability = () async => [true];
  Future<Object?> Function() cleanup = () async => [null];
  Future<Object?> Function(Uint8List) transfer = (_) async => [
    Uint8List.fromList([0x90, 0x00]),
  ];
  final List<BasicMessageChannel<Object?>> _channels = [];

  void _handle(String name, Future<Object?> Function(Object?) handler) {
    final channel = BasicMessageChannel<Object?>(
      'dev.flutter.pigeon.nfc_manager.HostApiPigeon.$name',
      HostApiPigeon.pigeonChannelCodec,
    );
    _channels.add(channel);
    binding.defaultBinaryMessenger.setMockDecodedMessageHandler<Object?>(
      channel,
      handler,
    );
  }

  void install() {
    _handle('nfcAdapterIsEnabled', (_) => availability());
    _handle('nfcAdapterEnableReaderMode', (_) async {
      starts++;
      if (startReply != null) return startReply!();
      return startError ? ['start_failed', 'Reader unavailable', null] : [null];
    });
    _handle('nfcAdapterDisableReaderMode', (_) {
      stops++;
      return cleanup();
    });
    _handle('isoDepSetTimeout', (_) async => [null]);
    _handle('isoDepGetMaxTransceiveLength', (_) async => [261]);
    _handle('isoDepTransceive', (message) {
      commands++;
      return transfer((message! as List)[1] as Uint8List);
    });
  }

  void discover() {
    final tag = TagPigeon(
      handle: 'test-tag-$starts',
      id: Uint8List.fromList([1, 2, 3]),
      techList: ['android.nfc.tech.IsoDep'],
      isoDep: IsoDepPigeon(isExtendedLengthApduSupported: true),
    );
    binding.channelBuffers.push(
      'dev.flutter.pigeon.nfc_manager.FlutterApiPigeon.onTagDiscovered',
      FlutterApiPigeon.pigeonChannelCodec.encodeMessage([tag]),
      (_) {},
    );
  }

  void uninstall() {
    for (final channel in _channels) {
      binding.defaultBinaryMessenger.setMockDecodedMessageHandler(
        channel,
        null,
      );
    }
  }
}

// Same known descriptor fixture as the existing X1 protocol tests.
Uint8List _fixtureDeviceInfo() {
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
