// ignore_for_file: prefer_final_fields, unused_element, unused_field

import 'dart:async';
import 'dart:convert';
import 'dart:math' as math;

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:image/image.dart' as img;
import 'package:image_picker/image_picker.dart';
import 'package:ndef_record/ndef_record.dart';
import 'package:nfc_manager/nfc_manager.dart';
import 'package:nfc_manager/nfc_manager_android.dart';
import 'package:nfc_manager/nfc_manager_ios.dart';

import 'latch_ui/core/errors/app_error_reporter.dart';
import 'latch_ui/core/services/firebase_bootstrap.dart';
import 'latch_ui/core/services/push_notification_service.dart';
import 'latch_ui/features/epaper/data/x1_epaper_codec.dart';
import 'latch_ui/features/epaper/data/x1_epaper_compression.dart';
import 'latch_ui/features/epaper/data/x1_epaper_image_normalizer.dart';
import 'latch_ui/features/epaper/data/x1_iso7816_transport.dart';
import 'latch_ui/features/epaper/data/x1_nfc_epaper_protocol.dart';
import 'latch_ui/features/epaper/data/x1_nfc_transceive_retry.dart';
import 'latch_ui/features/epaper/data/x1_vendor_image_pipeline.dart';
import 'latch_ui/features/startup/presentation/app_loading_screen.dart';
import 'latch_ui/integration/latch_integrated_app.dart';

void main() {
  runZonedGuarded<Future<void>>(
    () async {
      WidgetsFlutterBinding.ensureInitialized();
      AppErrorReporter.initialize();
      runApp(
        LatchStartupBootstrap(
          pushNotifications: FirebaseBootstrap.initialize().timeout(
            const Duration(seconds: 8),
            onTimeout: () => null,
          ),
        ),
      );
    },
    (error, stackTrace) {
      AppErrorReporter.record(
        error,
        stackTrace,
        context: 'Uncaught root-zone error',
      );
    },
  );
}

class LatchStartupBootstrap extends StatefulWidget {
  const LatchStartupBootstrap({super.key, required this.pushNotifications});

  final Future<PushNotificationService?> pushNotifications;

  @override
  State<LatchStartupBootstrap> createState() => _LatchStartupBootstrapState();
}

class _LatchStartupBootstrapState extends State<LatchStartupBootstrap> {
  PushNotificationService? _pushNotifications;
  bool? _onboardingCompleted;

  Future<void> _finishStartup(bool onboardingCompleted) async {
    final pushNotifications = await widget.pushNotifications;
    if (!mounted) return;
    setState(() {
      _pushNotifications = pushNotifications;
      _onboardingCompleted = onboardingCompleted;
    });
  }

  @override
  Widget build(BuildContext context) {
    final onboardingCompleted = _onboardingCompleted;
    if (onboardingCompleted != null) {
      return LatchIntegratedApp(
        pushNotifications: _pushNotifications,
        initialOnboardingCompleted: onboardingCompleted,
      );
    }

    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: AppLoadingScreen(onFinished: _finishStartup),
    );
  }
}

/// Exposes [UploaderPage] state to integration adapters without duplicating logic.
class UploaderBridge extends ChangeNotifier {
  Future<void> Function()? pickImage;
  Future<void> Function(Uint8List bytes)? ingestSelectedImageBytes;
  Future<void> Function()? writeTag;
  Future<void> Function()? cancelWriteTag;
  Uint8List? Function()? readPreviewPngBytes;
  Uint8List? Function()? readOriginalBytes;
  bool Function()? readBusy;
  String Function()? readStatus;
  bool Function()? readHasProcessedImage;

  void publish() => notifyListeners();

  Future<void> requestPickImage() async {
    await pickImage?.call();
  }

  Future<void> requestIngestSelectedImageBytes(Uint8List bytes) async {
    await ingestSelectedImageBytes?.call(bytes);
  }

  Future<void> requestWriteTag() async {
    await writeTag?.call();
  }

  Future<void> requestCancelWriteTag() async {
    await cancelWriteTag?.call();
  }

  Uint8List? get previewPngBytes => readPreviewPngBytes?.call();

  Uint8List? get originalBytes => readOriginalBytes?.call();

  bool get busy => readBusy?.call() ?? false;

  String get status =>
      readStatus?.call() ?? 'Start with a blank canvas or template.';

  bool get hasProcessedImage => readHasProcessedImage?.call() ?? false;
}

class EPaperNfcApp extends StatelessWidget {
  const EPaperNfcApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'EPaper NFC Uploader',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: const Color(0xFF0A4B8E)),
        useMaterial3: true,
      ),
      home: const UploaderPage(),
    );
  }
}

enum NfcWriteMode { ndef, isoDepRaw }

enum _LogLevel { info, success, warning, error }

enum _RotationMode { deg0, deg90, deg180, deg270 }

enum _ApkScanMode { horizontal, vertical }

enum _AccentColor { red, yellow }

enum _ApkColorPipeline { fourColorPacked, dualPlane }

enum _PreviewMode { source, panel }

class _LogEntry {
  const _LogEntry({required this.line, required this.level});

  final String line;
  final _LogLevel level;
}

class UploaderPage extends StatefulWidget {
  const UploaderPage({super.key, this.bridge, this.embedded = false});

  final UploaderBridge? bridge;
  final bool embedded;

  @override
  State<UploaderPage> createState() => _UploaderPageState();
}

class _UploaderPageState extends State<UploaderPage>
    with WidgetsBindingObserver {
  static const int targetWidth = 240;
  static const int targetHeight = 416;
  static const int tagCapacityBytes = 498;
  static const int fitWidth = 48;
  static const int fitHeight = 82;
  static const int maxLogLines = 300;

  final ImagePicker _picker = ImagePicker();

  final TextEditingController _initCommandsController = TextEditingController(
    text: '',
  );
  final TextEditingController _chunkPrefixController = TextEditingController(
    text: '00D60000',
  );
  final TextEditingController _finalCommandsController = TextEditingController(
    text: '',
  );
  final TextEditingController _chunkSizeController = TextEditingController(
    text: '220',
  );
  final TextEditingController _probeCommandsController = TextEditingController(
    text:
        '00A4040007D2760000850101\n00A4000C02E103\n00B000000F\n00A4000C02E104\n00B0000002',
  );
  final TextEditingController
  _refreshSequencesController = TextEditingController(
    text:
        '00A4040007D2760000850101\n00A4000C02E103\n00B000000F\n\n00A4040007D2760000850101\n00A4000C02E104\n00B0000002\n\n00A4040007D2760000850101\n00A4000C02E104\n00B000000F',
  );
  final TextEditingController _refreshRepeatController = TextEditingController(
    text: '2',
  );
  final TextEditingController _refreshDelayMsController = TextEditingController(
    text: '250',
  );
  final TextEditingController _apduPacingMsController = TextEditingController(
    text: '0',
  );
  final TextEditingController _postWriteSettleMsController =
      TextEditingController(text: '0');
  final TextEditingController _f0PlaneController = TextEditingController(
    text: '00',
  );
  final TextEditingController _f0FixedBlockCountController =
      TextEditingController(text: '0');
  final TextEditingController _f0StartBlockController = TextEditingController(
    text: '0',
  );
  final TextEditingController _isoDepTimeoutMsController =
      TextEditingController(text: '50000');
  final TextEditingController _codeWhiteController = TextEditingController(
    text: '0',
  );
  final TextEditingController _codeBlackController = TextEditingController(
    text: '3',
  );
  final TextEditingController _codeRedController = TextEditingController(
    text: '2',
  );
  final TextEditingController _codeYellowController = TextEditingController(
    text: '1',
  );

  Uint8List? _originalBytes;
  Uint8List? _previewPngBytes;
  Uint8List? _packedFrameBytes;
  Uint8List? _packedFramePlane1Bytes;

  bool _invert = false;
  bool _dither = true;
  bool _fitToTagCapacity = true;
  bool _type4NlenFraming = false;
  double _threshold = 128;
  bool _busy = false;
  String _status = 'Start with a blank canvas or template.';
  NfcWriteMode _mode = NfcWriteMode.isoDepRaw;
  bool _autoOffsetP1P2 = false;
  bool _appendLe = true;
  bool _useF0D2BlockWrite = false;
  bool _useF0FixedBlockCount = false;
  bool _autoResumeF0FromLastBlock = true;
  bool _autoRunApkRefreshFlow = true;
  bool _useApkColorMapping = true;
  bool _uploadSecondColorPlane = false;
  _ApkScanMode _apkScanMode = _ApkScanMode.horizontal;
  _AccentColor _accentColor = _AccentColor.red;
  _ApkColorPipeline _apkColorPipeline = _ApkColorPipeline.fourColorPacked;
  bool _apkPixelizedResize = true;
  bool _photoPreserveQuantization = true;
  final bool _lockToApkExact = true;
  bool _txNibbleSwap = false;
  bool _txBitReverse = false;
  bool _packColumnMajor = false;
  bool _packLsbFirst = false;
  bool _mirrorX = false;
  bool _mirrorY = false;
  _RotationMode _rotationMode = _RotationMode.deg0;
  _PreviewMode _previewMode = _PreviewMode.panel;
  String _vendorFramePreview = '';
  final List<_LogEntry> _nfcLogs = <_LogEntry>[];
  int _frameWidth = targetWidth;
  int _frameHeight = targetHeight;
  int _writeSessionId = 0;
  bool _nfcWriteSessionActive = false;
  Future<void>? _nfcSessionStop;
  Future<void>? _nfcSessionStart;
  Timer? _nfcDiscoveryTimer;
  static final Object _writeSessionZoneKey = Object();

  bool get _isCurrentWriteContext {
    final sessionId = Zone.current[_writeSessionZoneKey];
    return mounted && (sessionId == null || sessionId == _writeSessionId);
  }

  void _ensureWriteActive() {
    if (!_isCurrentWriteContext) throw StateError('NFC write cancelled.');
  }

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
    _registerBridge();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      if (!mounted) {
        return;
      }
      _applyApkExactFlowPreset();
    });
  }

  void _registerBridge() {
    final bridge = widget.bridge;
    if (bridge == null) {
      return;
    }

    bridge.pickImage = _pickImage;
    bridge.ingestSelectedImageBytes = _ingestSelectedImageBytes;
    bridge.writeTag = _writeTag;
    bridge.cancelWriteTag = _cancelWriteTag;
    bridge.readPreviewPngBytes = () => _previewPngBytes;
    bridge.readOriginalBytes = () => _originalBytes;
    bridge.readBusy = () => _busy;
    bridge.readStatus = () => _status;
    bridge.readHasProcessedImage = () => _packedFrameBytes != null;
    bridge.publish();
  }

  void _unregisterBridge() {
    final bridge = widget.bridge;
    if (bridge == null) {
      return;
    }

    if (bridge.pickImage == _pickImage) {
      bridge.pickImage = null;
      bridge.ingestSelectedImageBytes = null;
      bridge.writeTag = null;
      bridge.cancelWriteTag = null;
      bridge.readPreviewPngBytes = null;
      bridge.readOriginalBytes = null;
      bridge.readBusy = null;
      bridge.readStatus = null;
      bridge.readHasProcessedImage = null;
    }
  }

  void _notifyBridge() {
    widget.bridge?.publish();
  }

  @override
  void setState(VoidCallback fn) {
    if (!_isCurrentWriteContext) return;
    super.setState(fn);
    _notifyBridge();
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    _nfcDiscoveryTimer?.cancel();
    if (_busy) {
      _writeSessionId++;
      unawaited(_stopNfcWriteSession());
    }
    _unregisterBridge();
    _initCommandsController.dispose();
    _chunkPrefixController.dispose();
    _finalCommandsController.dispose();
    _chunkSizeController.dispose();
    _probeCommandsController.dispose();
    _refreshSequencesController.dispose();
    _refreshRepeatController.dispose();
    _refreshDelayMsController.dispose();
    _apduPacingMsController.dispose();
    _postWriteSettleMsController.dispose();
    _f0PlaneController.dispose();
    _f0FixedBlockCountController.dispose();
    _f0StartBlockController.dispose();
    _isoDepTimeoutMsController.dispose();
    _codeWhiteController.dispose();
    _codeBlackController.dispose();
    _codeRedController.dispose();
    _codeYellowController.dispose();
    super.dispose();
  }

  Future<void> _stopNfcWriteSession() async {
    _nfcDiscoveryTimer?.cancel();
    final pendingStop = _nfcSessionStop;
    if (pendingStop != null) {
      try {
        await pendingStop;
      } on Object {
        /* The original cleanup logs it. */
      }
      return;
    }
    if (!_nfcWriteSessionActive) {
      return;
    }
    _nfcWriteSessionActive = false;
    final starting = _nfcSessionStart;
    final stop = () async {
      try {
        await NfcManager.instance.stopSession();
      } on Object catch (error) {
        _appendLog('NFC cleanup failed: $error', level: _LogLevel.warning);
      }
      if (starting != null) {
        // A cancelled start can finish after the first stop request.
        try {
          await starting;
        } on Object {
          // The attempt handles its own start error.
        }
        await NfcManager.instance.stopSession();
      }
    }();
    _nfcSessionStop = stop;
    try {
      await stop;
    } catch (error) {
      _appendLog('NFC cleanup failed: $error', level: _LogLevel.warning);
    } finally {
      if (identical(_nfcSessionStop, stop)) _nfcSessionStop = null;
    }
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    if (state == AppLifecycleState.paused ||
        state == AppLifecycleState.detached) {
      unawaited(_cancelWriteTag());
    }
  }

  Future<void> _restartNfcWriteSession({
    required int sessionId,
    required bool Function() shouldRestart,
    required Future<void> Function() restart,
  }) async {
    try {
      await _stopNfcWriteSession().timeout(const Duration(seconds: 5));
    } on Object catch (error) {
      _failNfcWrite(
        sessionId,
        'Update failed. Could not reconnect NFC. Please try again.',
        error,
      );
      return;
    }
    await Future<void>.delayed(const Duration(milliseconds: 350));
    if (!mounted || sessionId != _writeSessionId || !shouldRestart()) {
      return;
    }
    await restart();
  }

  int _parseMs(TextEditingController controller, int fallback) {
    final int v = int.tryParse(controller.text.trim()) ?? fallback;
    return v < 0 ? 0 : v;
  }

  bool _isStrictApkCloneActive() {
    return _lockToApkExact &&
        _useApkColorMapping &&
        !_fitToTagCapacity &&
        _apkColorPipeline == _ApkColorPipeline.fourColorPacked;
  }

  Future<void> _configureIsoDepSession(X1Iso7816Transport isoDep) async {
    _ensureWriteActive();
    final int timeoutMs = _parseMs(_isoDepTimeoutMsController, 50000);
    if (timeoutMs <= 0) {
      return;
    }
    try {
      await isoDep.configureTimeout(timeoutMs);
      _ensureWriteActive();
      _appendLog(
        '${isoDep.platformName} session configured '
        '(requested timeout ${timeoutMs}ms).',
      );
    } catch (_) {
      _ensureWriteActive();
      _appendLog(
        'IsoDep timeout API not available in this plugin build; using default timeout.',
        level: _LogLevel.warning,
      );
    }
  }

  Future<void> _runProbe() async {
    final NfcAvailability availability = await NfcManager.instance
        .checkAvailability();
    if (availability != NfcAvailability.enabled) {
      setState(() {
        _status = 'NFC is not available on this device.';
      });
      _appendLog('NFC unavailable: $availability', level: _LogLevel.error);
      return;
    }

    final List<Uint8List> probeCommands = _parseHexCommands(
      _probeCommandsController.text,
    );
    if (probeCommands.isEmpty) {
      setState(() {
        _status = 'No probe commands configured.';
      });
      return;
    }

    setState(() {
      _busy = true;
      _status = 'Hold your tag near the phone for APDU probe...';
    });

    _appendLog('=== APDU probe ===');

    await NfcManager.instance.startSession(
      pollingOptions: const <NfcPollingOption>{NfcPollingOption.iso14443},
      onDiscovered: (NfcTag tag) async {
        try {
          final X1Iso7816Transport? isoDep = X1Iso7816Transport.fromTag(tag);
          if (isoDep == null) {
            throw StateError(
              'Tag does not expose Android IsoDep or iOS ISO 7816.',
            );
          }
          await _configureIsoDepSession(isoDep);

          final int maxTx = await isoDep.getMaxTransceiveLength();
          _appendLog('Probe IsoDep connected. maxTx=$maxTx');

          for (int i = 0; i < probeCommands.length; i++) {
            await _sendApdu(
              isoDep,
              probeCommands[i],
              label: 'probe ${i + 1}/${probeCommands.length}',
              failOnStatus: false,
            );
          }

          await NfcManager.instance.stopSession();
          if (!mounted) {
            return;
          }
          setState(() {
            _busy = false;
            _status = 'Probe complete. Check NFC Log.';
          });
        } catch (e) {
          await NfcManager.instance.stopSession();
          if (!mounted) {
            return;
          }
          setState(() {
            _busy = false;
            _status = 'Probe failed: $e';
          });
          _appendLog('Probe failed: $e', level: _LogLevel.error);
        }
      },
    );
  }

  Future<void> _pickImage() async {
    final XFile? file = await _picker.pickImage(
      source: ImageSource.gallery,
      maxWidth: 1664,
      maxHeight: 1664,
      imageQuality: 100,
      requestFullMetadata: false,
    );
    if (file == null) {
      return;
    }

    final Uint8List bytes = await file.readAsBytes();
    await _ingestSelectedImageBytes(bytes);
  }

  Future<void> _ingestSelectedImageBytes(Uint8List bytes) async {
    setState(() {
      _originalBytes = bytes;
      _status = 'Preparing image…';
    });

    try {
      _rebuildImagePipeline();
    } catch (error, stackTrace) {
      if (!mounted) {
        return;
      }
      setState(() {
        _previewPngBytes = null;
        _packedFrameBytes = null;
        _packedFramePlane1Bytes = null;
        _status =
            'This image could not be prepared. Try a smaller JPG or PNG image.';
      });
      _appendLog(
        'Image preparation failed: $error\n$stackTrace',
        level: _LogLevel.error,
      );
    }
  }

  void _rebuildImagePipeline() {
    final Uint8List? src = _originalBytes;
    if (src == null) {
      return;
    }

    final img.Image? decoded = decodeX1EpaperSource(src);
    if (decoded == null) {
      setState(() {
        _previewPngBytes = null;
        _packedFrameBytes = null;
        _packedFramePlane1Bytes = null;
        _status =
            'This image could not be opened. Try a JPG or PNG image instead.';
      });
      return;
    }

    final int processW = _fitToTagCapacity ? fitWidth : targetWidth;
    final int processH = _fitToTagCapacity ? fitHeight : targetHeight;
    final bool swapWh =
        _rotationMode == _RotationMode.deg90 ||
        _rotationMode == _RotationMode.deg270;

    final bool strictApkClone = _isStrictApkCloneActive();

    img.Image working = strictApkClone
        ? resizeX1VendorImage(
            decoded,
            swapWh ? processH : processW,
            swapWh ? processW : processH,
          )
        : _resizeToCover(
            decoded,
            swapWh ? processH : processW,
            swapWh ? processW : processH,
            interpolation:
                (_useApkColorMapping &&
                    !_fitToTagCapacity &&
                    _apkPixelizedResize)
                ? img.Interpolation.nearest
                : img.Interpolation.cubic,
          );

    switch (_rotationMode) {
      case _RotationMode.deg0:
        break;
      case _RotationMode.deg90:
        working = img.copyRotate(working, angle: 90);
      case _RotationMode.deg180:
        working = img.copyRotate(working, angle: 180);
      case _RotationMode.deg270:
        working = img.copyRotate(working, angle: 270);
    }

    if (_mirrorX) {
      working = img.flipHorizontal(working);
    }
    if (_mirrorY) {
      working = img.flipVertical(working);
    }

    final _PreparedFrame prepared = _useApkColorMapping && !_fitToTagCapacity
        ? _prepareApkColorFrame(
            working,
            threshold: strictApkClone ? 128 : _threshold,
            dither: strictApkClone ? true : _dither,
            scanMode: _apkScanMode,
            accentColor: _accentColor,
            pipeline: _apkColorPipeline,
            photoPreserve: strictApkClone ? false : _photoPreserveQuantization,
            codeWhite: strictApkClone
                ? 1
                : (int.tryParse(_codeWhiteController.text.trim()) ?? 0).clamp(
                    0,
                    3,
                  ),
            codeBlack: strictApkClone
                ? 0
                : (int.tryParse(_codeBlackController.text.trim()) ?? 3).clamp(
                    0,
                    3,
                  ),
            codeRed: strictApkClone
                ? 3
                : (int.tryParse(_codeRedController.text.trim()) ?? 2).clamp(
                    0,
                    3,
                  ),
            codeYellow: strictApkClone
                ? 2
                : (int.tryParse(_codeYellowController.text.trim()) ?? 1).clamp(
                    0,
                    3,
                  ),
          )
        : _prepareMonochromeFrame(
            working,
            threshold: _threshold,
            invert: _invert,
            dither: _dither,
            columnMajor: _packColumnMajor,
            lsbFirst: _packLsbFirst,
          );

    final Uint8List sourcePreviewPng = Uint8List.fromList(
      img.encodePng(working),
    );
    final bool isApkColorPath = _useApkColorMapping && !_fitToTagCapacity;
    final Uint8List selectedPreviewPng = isApkColorPath
        ? (_previewMode == _PreviewMode.source
              ? sourcePreviewPng
              : prepared.previewPng)
        : prepared.previewPng;

    setState(() {
      _previewPngBytes = selectedPreviewPng;
      _packedFrameBytes = prepared.packed;
      _packedFramePlane1Bytes = prepared.packedPlane1;
      _frameWidth = processW;
      _frameHeight = processH;
      if (!_busy) {
        _status = 'Image ready.';
      }
    });
    _appendLog(
      prepared.packedPlane1 == null
          ? 'Prepared ${processW}x$processH frame: ${prepared.packed.length} bytes.'
          : 'Prepared ${processW}x$processH frame: plane0=${prepared.packed.length} bytes, plane1=${prepared.packedPlane1!.length} bytes.',
    );
  }

  void _applyZ1QuickPreset() {
    _applyApkExactFlowPreset();
    _appendLog(
      'Loaded Z1 quick preset alias -> APK exact flow.',
      level: _LogLevel.info,
    );
  }

  int _vendorCrc(List<int> bytes) {
    int sum = 0;
    for (final int b in bytes) {
      sum = (sum + b) & 0xFF;
    }
    return (0xFF - sum) & 0xFF;
  }

  Uint8List _vendorExecFrame(int cmd) {
    final List<int> body = <int>[0x7F, 0xF7, cmd & 0xFF];
    body.add(_vendorCrc(body));
    return Uint8List.fromList(body);
  }

  String _hexCompact(Uint8List bytes) {
    return bytes
        .map((int b) => b.toRadixString(16).padLeft(2, '0').toUpperCase())
        .join();
  }

  String _buildUpdateBinaryCommand(int offset, Uint8List data) {
    final List<int> cmd = <int>[
      0x00,
      0xD6,
      (offset >> 8) & 0xFF,
      offset & 0xFF,
      data.length,
      ...data,
    ];
    return _hexCompact(Uint8List.fromList(cmd));
  }

  String _buildNoDataApdu(int ins, int p1, int p2) {
    final List<int> cmd = <int>[0x00, ins & 0xFF, p1 & 0xFF, p2 & 0xFF, 0x00];
    return _hexCompact(Uint8List.fromList(cmd));
  }

  void _applyVendorBridgeProbePreset() {
    final Uint8List param = _vendorExecFrame(0x01);
    final Uint8List status = _vendorExecFrame(0x02);
    final Uint8List send = _vendorExecFrame(0x05);
    final Uint8List refreshFront = _vendorExecFrame(0x03);
    final Uint8List refreshBack = _vendorExecFrame(0x04);

    final List<String> direct = <String>[
      _hexCompact(param),
      _hexCompact(status),
      _hexCompact(send),
      _hexCompact(refreshFront),
      _hexCompact(refreshBack),
    ];

    final List<String> wrapped = <String>[
      _buildUpdateBinaryCommand(0x0000, param),
      _buildUpdateBinaryCommand(0x0004, status),
      _buildUpdateBinaryCommand(0x0008, send),
      _buildUpdateBinaryCommand(0x000C, refreshFront),
      _buildUpdateBinaryCommand(0x0010, refreshBack),
      '00D1000000',
      '00B0000014',
    ];

    setState(() {
      _chunkPrefixController.text = '00D60000';
      _autoOffsetP1P2 = true;
      _appendLe = false;
      _probeCommandsController.text = <String>[
        ...direct,
        '00A4040007D2760000850101',
        '00A4000C02E103',
        '00B000000F',
        '00A4000C02E104',
        '00B0000002',
        '00D1000000',
        ...wrapped,
      ].join('\n');

      _refreshSequencesController.text = <String>[
        direct.join('\n'),
        wrapped.join('\n'),
      ].join('\n\n');

      _finalCommandsController.text = <String>[
        _hexCompact(refreshFront),
        _hexCompact(refreshBack),
        '00D1000000',
        _buildUpdateBinaryCommand(0x000C, refreshFront),
        _buildUpdateBinaryCommand(0x0010, refreshBack),
      ].join('\n');

      _apduPacingMsController.text = '120';
      _postWriteSettleMsController.text = '1200';

      _status =
          'Vendor bridge probe preset loaded. Run APDU Probe, then Refresh Tester.';
    });
    _appendLog('Loaded vendor bridge probe preset.');
  }

  void _applyInsSweepProbePreset() {
    final List<String> commands = <String>[
      '00A4040007D2760000850101',
      '00A4000C02E103',
      '00B000000F',
      '00A4000C02E104',
      '00B0000002',
    ];

    for (int ins = 0xC0; ins <= 0xCF; ins++) {
      commands.add(
        '00${ins.toRadixString(16).padLeft(2, '0').toUpperCase()}000000',
      );
    }
    for (int ins = 0xD0; ins <= 0xDF; ins++) {
      commands.add(
        '00${ins.toRadixString(16).padLeft(2, '0').toUpperCase()}000000',
      );
    }

    setState(() {
      _chunkPrefixController.text = '00D60000';
      _autoOffsetP1P2 = true;
      _appendLe = false;
      _probeCommandsController.text = commands.join('\n');
      _refreshSequencesController.text =
          '00D1000000\n\n00D2000000\n\n00D3000000\n\n00D4000000';
      _apduPacingMsController.text = '80';
      _postWriteSettleMsController.text = '800';
      _status =
          'INS sweep preset loaded (00C0..00CF + 00D0..00DF). Run APDU Probe and share non-6E00 hits.';
    });
    _appendLog('Loaded INS sweep probe preset.');
  }

  void _applyD1D5D6FocusedPreset() {
    setState(() {
      _chunkPrefixController.text = '00D60000';
      _autoOffsetP1P2 = true;
      _appendLe = false;
      _probeCommandsController.text = <String>[
        '00A4040007D2760000850101',
        '00A4000C02E104',
        '00B0000002',
        '00D1000000',
        '00D5000000',
        '00D6000000',
        '00D1000000',
        '00B0000002',
      ].join('\n');

      _refreshSequencesController.text = <String>[
        // Baseline read/status cycle.
        '00D1000000\n00D5000000\n00D6000000\n00D1000000',
        // Same cycle but with file reselection around it.
        '00A4040007D2760000850101\n00A4000C02E104\n00D5000000\n00D6000000\n00D1000000',
      ].join('\n\n');

      _finalCommandsController.text = <String>[
        '00D5000000',
        '00D6000000',
        '00D1000000',
      ].join('\n');

      _apduPacingMsController.text = '300';
      _postWriteSettleMsController.text = '2000';

      _status =
          'Focused D1/D5/D6 preset loaded. Write NFC first, then run Refresh Tester.';
    });
    _appendLog('Loaded focused D1/D5/D6 preset.');
  }

  void _applyD5D6ModeSweepPreset() {
    final List<int> p1Values = <int>[0x00, 0x01, 0x02, 0x10, 0x20, 0x40, 0x80];
    final List<int> p2Values = <int>[0x00, 0x01];

    final List<String> probe = <String>[
      '00A4040007D2760000850101',
      '00A4000C02E104',
      '00B0000002',
      '00D1000000',
      '00D5000000',
      '00D6000000',
    ];

    for (final int p1 in p1Values) {
      for (final int p2 in p2Values) {
        probe.add(_buildNoDataApdu(0xD5, p1, p2));
        probe.add(_buildNoDataApdu(0xD6, p1, p2));
        probe.add('00D1000000');
      }
    }

    final List<String> groups = <String>[];
    for (final int p1 in p1Values) {
      groups.add(
        <String>[
          _buildNoDataApdu(0xD5, p1, 0x00),
          _buildNoDataApdu(0xD6, p1, 0x00),
          '00D1000000',
        ].join('\n'),
      );
    }

    setState(() {
      _chunkPrefixController.text = '00D60000';
      _autoOffsetP1P2 = true;
      _appendLe = false;
      _probeCommandsController.text = probe.join('\n');
      _refreshSequencesController.text = groups.join('\n\n');
      _finalCommandsController.text = '00D5000000\n00D6000000\n00D1000000';
      _refreshRepeatController.text = '1';
      _refreshDelayMsController.text = '600';
      _apduPacingMsController.text = '450';
      _postWriteSettleMsController.text = '3000';
      _status =
          'D5/D6 mode sweep loaded. Keep tag steady and run Probe, then Refresh Tester.';
    });
    _appendLog('Loaded D5/D6 mode sweep preset.');
  }

  void _applyApkExactFlowPreset() {
    setState(() {
      _mode = NfcWriteMode.isoDepRaw;
      _dither = true;
      _fitToTagCapacity = false;
      _type4NlenFraming = false;
      _useF0D2BlockWrite = true;
      _useF0FixedBlockCount = true;
      _autoResumeF0FromLastBlock = true;
      _autoRunApkRefreshFlow = true;
      _useApkColorMapping = true;
      _uploadSecondColorPlane = false;
      _apkScanMode = _ApkScanMode.horizontal;
      _accentColor = _AccentColor.red;
      _apkColorPipeline = _ApkColorPipeline.fourColorPacked;
      _apkPixelizedResize = true;
      _photoPreserveQuantization = false;
      _txNibbleSwap = false;
      _txBitReverse = false;
      _packColumnMajor = false;
      _packLsbFirst = false;
      _mirrorX = false;
      _mirrorY = false;
      _rotationMode = _RotationMode.deg0;
      _f0PlaneController.text = '00';
      _f0FixedBlockCountController.text = '100';
      _f0StartBlockController.text = '0';
      _codeWhiteController.text = '1';
      _codeBlackController.text = '0';
      _codeRedController.text = '3';
      _codeYellowController.text = '2';
      _chunkPrefixController.text = 'F0D20000';
      _chunkSizeController.text = '250';
      _autoOffsetP1P2 = false;
      _appendLe = false;

      _initCommandsController.text =
          '00A4040007D2760000850101\n00D1000000\nF0D8000005000000000E';
      _finalCommandsController.text = '';
      _refreshSequencesController.text = <String>[
        'F0D4050000\nF0DE000001\nF0DE000001\nF0DE000001',
        'F0D4850000\nF0DE000001\nF0DE000001\nF0DE000001',
      ].join('\n\n');
      _probeCommandsController.text = <String>[
        '00A4040007D2760000850101',
        '00D1000000',
        'F0D8000005000000000E',
        'F0D4050000',
        'F0DE000001',
      ].join('\n');
      _refreshRepeatController.text = '1';
      _refreshDelayMsController.text = '0';
      _apduPacingMsController.text = '0';
      _postWriteSettleMsController.text = '0';
      _isoDepTimeoutMsController.text = '50000';
    });

    // Rebuild image with new preset flags
    if (_originalBytes != null) {
      _rebuildImagePipeline();
    }
  }

  String _responseHex(Uint8List response) {
    return response
        .map((int b) => b.toRadixString(16).padLeft(2, '0').toUpperCase())
        .join();
  }

  bool _isLikelyTagLostError(Object error) {
    final String text = error.toString().toLowerCase();
    return text.contains('taglostexception') ||
        text.contains('tag was lost') ||
        text.contains('tag_not_found') ||
        text.contains('tag is out of date') ||
        text.contains('out of date') ||
        text.contains('permission denial') ||
        text.contains('securityexception') ||
        text.contains('i/o error') ||
        text.contains('ioexception') ||
        text.contains('call connect() first') ||
        text.contains('transceive failed') ||
        text.contains('socket') ||
        text.contains('connection') ||
        text.contains('disconnected') ||
        text.contains('timeout');
  }

  String _friendlyEpaperError(Object error) {
    final String text = error.toString().toLowerCase();
    if (text.contains('pin-protected')) {
      return 'This display is locked. Remove its PIN in the vendor app first.';
    }
    if (text.contains('unsupported e-paper profile')) {
      return 'This e-paper display is not compatible with LATCH.';
    }
    if (text.contains('invalid device information') ||
        text.contains('missing x1') ||
        text.contains('truncated x1')) {
      return 'The e-paper display could not be recognized. Try positioning '
          'the phone over its NFC area again.';
    }
    if (text.contains('did not acknowledge') ||
        text.contains('refresh ended')) {
      return 'The image was sent, but the display did not finish refreshing. '
          'Keep the phone in place and try again.';
    }
    if (_isLikelyTagLostError(error) ||
        text.contains('call connect() first') ||
        text.contains('transceive')) {
      return 'The NFC connection was interrupted. Keep the phone against the '
          'device and try again.';
    }
    return 'Keep the phone against the device and try again.';
  }

  bool _isSensitiveApduLabel(String label) {
    final String lower = label.toLowerCase();
    return lower.contains('f0d2') ||
        lower.contains('block') ||
        lower.contains('trigger') ||
        lower.contains('refresh') ||
        lower.contains('final') ||
        lower.contains('commit') ||
        lower.contains('poll');
  }

  int _tagLostRetryDelayMs(String label, int retry) {
    final bool sensitive = _isSensitiveApduLabel(label);
    final int base = sensitive ? 800 : 400;
    final int step = sensitive ? 250 : 100;
    return base + (retry * step);
  }

  int _statusRetryDelayMs(String label, int retry) {
    final bool sensitive = _isSensitiveApduLabel(label);
    final int base = sensitive ? 1200 : 500;
    final int step = sensitive ? 500 : 250;
    return base + (retry * step);
  }

  int? _extractF0TagLostBlock(Object error) {
    final RegExp re = RegExp(r'F0D2_TAG_LOST_AT_BLOCK:(\d+)');
    final Match? match = re.firstMatch(error.toString());
    if (match == null) {
      return null;
    }
    return int.tryParse(match.group(1) ?? '');
  }

  bool _isRefreshTagLostError(Object error) {
    return error.toString().contains('F0D2_REFRESH_TAG_LOST');
  }

  Future<bool> _apkExactRefreshPoll(
    X1Iso7816Transport isoDep, {
    required int refreshIndex,
    required bool firstBranchUsed,
    required bool variantMode85Used,
  }) async {
    Uint8List poll;
    try {
      poll = await _sendApdu(
        isoDep,
        X1NfcEpaperProtocol.refreshPoll,
        label: 'apk exact poll',
        failOnStatus: false,
      );
    } catch (e) {
      if (e is X1RefreshAcceptedConnectionLost ||
          e is X1RefreshTriggerConnectionLost) {
        rethrow;
      }
      if (_isLikelyTagLostError(e)) {
        _appendLog(
          'F0DE: refresh was already accepted; the panel dropped the NFC '
          'field while starting its automatic refresh.',
          level: _LogLevel.warning,
        );
        throw const X1RefreshAcceptedConnectionLost();
      }
      rethrow;
    }

    final String pollHex = _responseHex(poll);
    if (pollHex == '009000' || pollHex == '019000') {
      _appendLog('F0DE poll success: $pollHex', level: _LogLevel.success);
      return true;
    }
    if (pollHex == '698A') {
      _appendLog('F0DE failed: 698A', level: _LogLevel.error);
      return false;
    }
    if (pollHex == '6986') {
      _appendLog('F0DE failed: 6986', level: _LogLevel.error);
      return false;
    }
    if (pollHex == '68C6') {
      if (!firstBranchUsed) {
        _appendLog(
          'F0DE got 68C6: retrying F0D4 with first branch.',
          level: _LogLevel.warning,
        );
        return _apkExactRefreshTrigger(
          isoDep,
          refreshIndex: refreshIndex,
          firstBranchUsed: true,
          variantMode85Used: false,
        );
      }
      if (!variantMode85Used) {
        _appendLog(
          'F0DE got 68C6: retrying F0D4 with mode 85.',
          level: _LogLevel.warning,
        );
        return _apkExactRefreshTrigger(
          isoDep,
          refreshIndex: refreshIndex,
          firstBranchUsed: true,
          variantMode85Used: true,
        );
      }
      _appendLog('F0DE failed: repeated 68C6.', level: _LogLevel.error);
      return false;
    }

    // Despite the loop visible in the decompiled vendor method, every result
    // exits after its first F0DE response. Repeating an unknown status here is
    // what previously made LATCH appear frozen at 97-99%.
    _appendLog(
      'F0DE failed: unexpected response $pollHex.',
      level: _LogLevel.error,
    );
    return false;
  }

  Future<bool> _apkExactRefreshTrigger(
    X1Iso7816Transport isoDep, {
    required int refreshIndex,
    required bool firstBranchUsed,
    required bool variantMode85Used,
  }) async {
    int retries6986 = 5;

    while (true) {
      final int mode = variantMode85Used ? 0x85 : 0x05;
      final Uint8List triggerCmd = X1NfcEpaperProtocol.buildRefreshTrigger(
        alternateMode: variantMode85Used,
        displayIndex: refreshIndex,
      );

      Uint8List triggerResp;
      try {
        triggerResp = await _sendApdu(
          isoDep,
          triggerCmd,
          label:
              'apk exact trigger mode=0x${mode.toRadixString(16).toUpperCase()}',
          failOnStatus: false,
        );
      } catch (e) {
        if (_isLikelyTagLostError(e)) {
          _appendLog(
            'F0D4 trigger: tag lost before an acknowledged response.',
            level: _LogLevel.warning,
          );
          throw const X1RefreshTriggerConnectionLost();
        }
        rethrow;
      }

      final String triggerHex = _responseHex(triggerResp);
      if (triggerHex == '019000') {
        _appendLog('F0D4 immediate success: 019000', level: _LogLevel.success);
        return true;
      }
      if (triggerHex == '009000' || triggerHex == '9000') {
        _appendLog(
          'F0D4 accepted (009000/9000), starting poll...',
          level: _LogLevel.info,
        );
        return _apkExactRefreshPoll(
          isoDep,
          refreshIndex: refreshIndex,
          firstBranchUsed: firstBranchUsed,
          variantMode85Used: variantMode85Used,
        );
      }
      if (triggerHex == '698A') {
        _appendLog('F0D4 failed: 698A', level: _LogLevel.error);
        return false;
      }
      if (triggerHex == '6986') {
        if (retries6986 > 0) {
          retries6986--;
          _appendLog(
            'F0D4 got 6986, retrying ($retries6986 left).',
            level: _LogLevel.warning,
          );
          continue;
        }
        _appendLog(
          'F0D4 failed: repeated 6986 (out of retries).',
          level: _LogLevel.error,
        );
        return false;
      }
      if (triggerHex == '68C6') {
        if (!firstBranchUsed) {
          _appendLog(
            'F0D4 got 68C6: retry with first branch.',
            level: _LogLevel.warning,
          );
          return _apkExactRefreshTrigger(
            isoDep,
            refreshIndex: refreshIndex,
            firstBranchUsed: true,
            variantMode85Used: false,
          );
        }
        if (!variantMode85Used) {
          _appendLog(
            'F0D4 got 68C6: retry with mode 85.',
            level: _LogLevel.warning,
          );
          return _apkExactRefreshTrigger(
            isoDep,
            refreshIndex: refreshIndex,
            firstBranchUsed: true,
            variantMode85Used: true,
          );
        }
        _appendLog('F0D4 failed: repeated 68C6.', level: _LogLevel.error);
        return false;
      }

      final Uint8List fallbackCmd = X1NfcEpaperProtocol.buildRefreshTrigger(
        alternateMode: variantMode85Used,
        displayIndex: refreshIndex,
      );
      final Uint8List fallbackResp = await _sendApdu(
        isoDep,
        fallbackCmd,
        label: 'apk exact fallback',
        failOnStatus: false,
      );
      final String fallbackHex = _responseHex(fallbackResp);
      if (fallbackHex == '9000') {
        _appendLog(
          'F0D4 fallback success: $fallbackHex',
          level: _LogLevel.success,
        );
        return true;
      }
      _appendLog(
        'F0D4 unknown response: $triggerHex (fallback=$fallbackHex). Treating as error.',
        level: _LogLevel.error,
      );
      return false;
    }
  }

  Future<bool> _runApkRefreshWithRetries(
    X1Iso7816Transport isoDep, {
    required int attempts,
    required int gapMs,
  }) async {
    final int safeAttempts = attempts < 1 ? 1 : attempts;
    for (int attempt = 1; attempt <= safeAttempts; attempt++) {
      _appendLog(
        'APK refresh attempt $attempt/$safeAttempts',
        level: _LogLevel.info,
      );

      final bool ok = await _apkExactRefreshTrigger(
        isoDep,
        refreshIndex: 0,
        firstBranchUsed: false,
        variantMode85Used: false,
      );

      if (ok) {
        return true;
      }

      if (attempt < safeAttempts && gapMs > 0) {
        _appendLog('Waiting ${gapMs}ms before retry...', level: _LogLevel.info);
        await Future<void>.delayed(Duration(milliseconds: gapMs));
      }
    }
    return false;
  }

  Future<void> _runApkRefreshFlow() async {
    final NfcAvailability availability = await NfcManager.instance
        .checkAvailability();
    if (availability != NfcAvailability.enabled) {
      setState(() {
        _status = 'NFC is not available on this device.';
      });
      _appendLog('NFC unavailable: $availability', level: _LogLevel.error);
      return;
    }

    setState(() {
      _busy = true;
      _status = 'Hold your tag near phone for APK refresh flow...';
    });
    _appendLog('=== APK refresh flow ===');

    await NfcManager.instance.startSession(
      pollingOptions: const <NfcPollingOption>{NfcPollingOption.iso14443},
      onDiscovered: (NfcTag tag) async {
        try {
          final X1Iso7816Transport? isoDep = X1Iso7816Transport.fromTag(tag);
          if (isoDep == null) {
            throw StateError(
              'Tag does not expose Android IsoDep or iOS ISO 7816.',
            );
          }
          await _configureIsoDepSession(isoDep);

          await _sendApdu(
            isoDep,
            Uint8List.fromList(<int>[
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
            ]),
            label: 'apk flow select aid',
            failOnStatus: false,
          );
          final int attempts =
              int.tryParse(_refreshRepeatController.text.trim()) ?? 1;
          final int gapMs =
              int.tryParse(_refreshDelayMsController.text.trim()) ?? 250;
          final bool ok = await _runApkRefreshWithRetries(
            isoDep,
            attempts: attempts,
            gapMs: gapMs,
          );

          await NfcManager.instance.stopSession();
          if (!mounted) {
            return;
          }
          setState(() {
            _busy = false;
            _status = ok
                ? 'APK refresh flow complete (success response).'
                : 'APK refresh flow ended with error status.';
          });
        } catch (e) {
          await NfcManager.instance.stopSession();
          if (!mounted) {
            return;
          }
          final bool likelyTagLost = _isLikelyTagLostError(e);
          setState(() {
            _busy = false;
            _status = likelyTagLost
                ? 'APK refresh flow: tag lost during trigger/poll (often means refresh started).'
                : 'APK refresh flow failed: $e';
          });
          _appendLog(
            likelyTagLost
                ? 'APK refresh flow ended with TagLostException (treated as likely refresh start).'
                : 'APK refresh flow failed: $e',
            level: likelyTagLost ? _LogLevel.warning : _LogLevel.error,
          );
        }
      },
    );
  }

  List<Uint8List> _vendorDataFrames(Uint8List payload) {
    const int chunkSize = 780;
    final List<Uint8List> frames = <Uint8List>[];
    int payloadOffset = 0;
    int order = 0;

    while (payloadOffset < payload.length) {
      final int take = math.min(chunkSize, payload.length - payloadOffset);
      final List<int> body = <int>[
        0x7F,
        0xF7,
        0x05,
        0x01,
        0x01,
        0x01,
        order & 0xFF,
      ];
      body.addAll(payload.sublist(payloadOffset, payloadOffset + take));
      if (take < chunkSize) {
        body.addAll(List<int>.filled(chunkSize - take, 0x00));
      }
      body.add(_vendorCrc(body));
      frames.add(Uint8List.fromList(body));

      payloadOffset += take;
      order++;
    }

    return frames;
  }

  void _buildVendorFramesPreview() {
    final Uint8List? payload = _packedFrameBytes;
    if (payload == null || payload.isEmpty) {
      setState(() {
        _status =
            'Pick and process an image first before building vendor frames.';
      });
      return;
    }

    final Uint8List parameter = _vendorExecFrame(0x01);
    final Uint8List status = _vendorExecFrame(0x02);
    final Uint8List refreshFront = _vendorExecFrame(0x03);
    final Uint8List refreshBack = _vendorExecFrame(0x04);
    final Uint8List sendData = _vendorExecFrame(0x05);
    final List<Uint8List> dataFrames = _vendorDataFrames(payload);

    final StringBuffer sb = StringBuffer();
    sb.writeln('Vendor 7FF7 protocol preview');
    sb.writeln('Payload bytes: ${payload.length}');
    sb.writeln(
      'Data frames: ${dataFrames.length} (each ${dataFrames.first.length} bytes)',
    );
    sb.writeln('');
    sb.writeln('Command frames:');
    sb.writeln('PARAM   : ${_hex(parameter)}');
    sb.writeln('STATUS  : ${_hex(status)}');
    sb.writeln('SEND    : ${_hex(sendData)}');
    sb.writeln('REFRESH+: ${_hex(refreshFront)}');
    sb.writeln('REFRESH-: ${_hex(refreshBack)}');
    sb.writeln('');
    sb.writeln('Data frame head/tail sample:');
    sb.writeln('FIRST: ${_hex(dataFrames.first, maxBytes: 28)}');
    sb.writeln('LAST : ${_hex(dataFrames.last, maxBytes: 28)}');

    final String preview = sb.toString().trimRight();
    setState(() {
      _vendorFramePreview = preview;
      _status =
          'Vendor 7FF7 frames built. This is for matching the PC protocol format (not plain APDU).';
    });
    _appendLog('Built vendor 7FF7 preview: dataFrames=${dataFrames.length}.');
  }

  Future<void> _cancelWriteTag() async {
    if (!_busy) {
      return;
    }
    _writeSessionId++;
    _nfcDiscoveryTimer?.cancel();
    setState(() {
      _busy = false;
      _status = 'Update cancelled.';
    });
    _appendLog('Write cancelled by user.', level: _LogLevel.info);
    // Release the UI immediately. A new attempt waits for this cleanup before
    // enabling reader mode, and the old transfer checks its session identity.
    unawaited(_stopNfcWriteSession());
  }

  Future<void> _writeTag() async {
    if (_busy) {
      _appendLog(
        'Write request ignored: another write session is already running.',
        level: _LogLevel.warning,
      );
      return;
    }

    final Uint8List? payload = _packedFrameBytes;
    if (payload == null) {
      setState(() {
        _status = 'Choose an image before updating the display.';
      });
      return;
    }

    if (_mode == NfcWriteMode.ndef && payload.length > 498) {
      setState(() {
        _status =
            'NDEF mode cannot hold full frame (${payload.length} bytes). Use IsoDep raw mode.';
      });
      return;
    }

    if (payload.length > tagCapacityBytes) {
      if (_useF0D2BlockWrite) {
        _appendLog(
          'Payload (${payload.length} bytes) is expected for F0D2 full-frame mode; ignoring Type4/NDEF capacity hint (~$tagCapacityBytes bytes).',
          level: _LogLevel.info,
        );
      } else {
        _appendLog(
          'Payload (${payload.length} bytes) exceeds detected writable limit (~$tagCapacityBytes bytes).',
          level: _LogLevel.warning,
        );
      }
    }

    if (_mode == NfcWriteMode.isoDepRaw) {
      final String prefixText = _chunkPrefixController.text.trim();
      final Uint8List? testPrefix = _tryHex(prefixText);
      if (testPrefix == null || testPrefix.isEmpty) {
        setState(() {
          _status = 'Invalid chunk APDU prefix hex.';
        });
        return;
      }
    }

    setState(() {
      _busy = true;
      _status = 'NFC write in progress.';
    });
    _appendLog('=== New write attempt ===');
    _appendLog('Mode: ${_mode.name}; payload bytes: ${payload.length}');

    final int sessionId = ++_writeSessionId;
    try {
      // Wait for cleanup before enabling a new reader session.
      await _stopNfcWriteSession().timeout(const Duration(seconds: 5));
      if (!mounted || sessionId != _writeSessionId) return;
      final availability = await NfcManager.instance
          .checkAvailability()
          .timeout(const Duration(seconds: 5));
      if (!mounted || sessionId != _writeSessionId) return;
      if (availability != NfcAvailability.enabled) {
        _failNfcWrite(sessionId, 'Turn on NFC to update the e-paper display.');
        return;
      }
    } on Object catch (error) {
      _failNfcWrite(
        sessionId,
        'Update failed. Could not prepare NFC. Please try again.',
        error,
      );
      return;
    }
    bool completed = false;
    bool discoveryInProgress = false;
    bool pendingRefreshPollOnly = false;
    bool pendingRefreshTriggerOnly = false;
    int rediscoveryAttempts = 0;
    // One clean re-tap is enough for the post-refresh status check. Avoid the
    // old effectively endless reader restart loop when the panel is absent.
    const int maxRediscoveries = 4;

    late Future<void> Function() startAttempt;
    bool restartScheduled = false;

    void scheduleRestart() {
      if (restartScheduled || completed || sessionId != _writeSessionId) {
        return;
      }
      restartScheduled = true;
      unawaited(
        _restartNfcWriteSession(
          sessionId: sessionId,
          shouldRestart: () =>
              mounted && !completed && sessionId == _writeSessionId,
          restart: () => startAttempt(),
        ),
      );
    }

    startAttempt = () async {
      if (completed || sessionId != _writeSessionId) {
        return;
      }
      restartScheduled = false;
      _nfcWriteSessionActive = true;
      _nfcDiscoveryTimer?.cancel();
      _nfcDiscoveryTimer = Timer(const Duration(seconds: 60), () {
        _failNfcWrite(
          sessionId,
          'Update failed. No NFC connection. Place your phone on the device and try again.',
        );
      });
      try {
        final starting = NfcManager.instance.startSession(
          pollingOptions: const <NfcPollingOption>{NfcPollingOption.iso14443},
          noPlatformSoundsAndroid: true,
          invalidateAfterFirstReadIos: false,
          onSessionErrorIos: (error) {
            if (completed || sessionId != _writeSessionId) return;
            completed = true;
            _failNfcWrite(
              sessionId,
              error.code ==
                      NfcReaderErrorCodeIos
                          .readerSessionInvalidationErrorUserCanceled
                  ? 'Update cancelled.'
                  : 'Update failed. NFC session ended. Please try again.',
              error,
            );
          },
          onDiscovered: (NfcTag tag) async {
            if (sessionId != _writeSessionId ||
                completed ||
                discoveryInProgress) {
              _appendLog(
                'Ignoring discovered tag event. active=${sessionId == _writeSessionId}, completed=$completed, discoveryInProgress=$discoveryInProgress',
                level: _LogLevel.info,
              );
              return;
            }

            discoveryInProgress = true;
            _nfcDiscoveryTimer?.cancel();
            rediscoveryAttempts++;
            try {
              final X1Iso7816Transport? discoveredTransport =
                  X1Iso7816Transport.fromTag(tag);
              _appendLog(
                'Tag discovered (attempt $rediscoveryAttempts). '
                'Tech: ${discoveredTransport?.tagDescription ?? 'unknown'}',
              );
              if (rediscoveryAttempts > 1 && mounted) {
                _f0StartBlockController.text = '0';
                setState(() {
                  _status = pendingRefreshPollOnly
                      ? 'Connection restored. Checking the display refresh…'
                      : pendingRefreshTriggerOnly
                      ? 'Connection restored. Triggering the display refresh…'
                      : 'Connection restored. Restarting the update safely…';
                });
                _appendLog(
                  pendingRefreshPollOnly
                      ? 'X1 recovery: reselecting the device and polling '
                            'the accepted refresh only.'
                      : pendingRefreshTriggerOnly
                      ? 'X1 recovery: reselecting the device and retrying '
                            'the refresh trigger without re-uploading.'
                      : 'X1 recovery: starting a fresh transaction from '
                            'block 0.',
                  level: _LogLevel.info,
                );
              }
              await runZoned(() async {
                if (_mode == NfcWriteMode.ndef) {
                  await _writeAsNdef(tag, payload);
                } else {
                  await _writeAsIsoDep(
                    tag,
                    payload,
                    isResume: false,
                    refreshOnly: pendingRefreshPollOnly,
                    refreshTriggerOnly: pendingRefreshTriggerOnly,
                    alternateRefreshMode: rediscoveryAttempts >= 3,
                  );
                }
              }, zoneValues: {_writeSessionZoneKey: sessionId});

              if (sessionId != _writeSessionId) {
                return;
              }
              completed = true;
              pendingRefreshPollOnly = false;
              pendingRefreshTriggerOnly = false;
              await _stopNfcWriteSession().timeout(
                const Duration(seconds: 3),
                onTimeout: () {},
              );
              if (!mounted || sessionId != _writeSessionId) {
                return;
              }
              setState(() {
                _busy = false;
                _status = 'E-Paper updated successfully.';
              });
              _appendLog('Write complete.', level: _LogLevel.success);
            } catch (e) {
              if (sessionId != _writeSessionId) {
                return;
              }
              if (e is X1RefreshAcceptedConnectionLost) {
                pendingRefreshPollOnly = false;
                pendingRefreshTriggerOnly = false;
                _appendLog(
                  'The accepted refresh dropped ISO-DEP. '
                  'Keeping Android reader mode active to power the panel '
                  'through its refresh waveform.',
                  level: _LogLevel.warning,
                );
                const int refreshHoldSeconds = 35;
                for (
                  int remaining = refreshHoldSeconds;
                  remaining > 0;
                  remaining--
                ) {
                  if (!mounted || sessionId != _writeSessionId) {
                    return;
                  }
                  setState(() {
                    _status = 'Refreshing E-Paper… ($remaining s)';
                  });
                  await Future<void>.delayed(const Duration(seconds: 1));
                }
                if (!mounted || sessionId != _writeSessionId) {
                  return;
                }
                completed = true;
                await _stopNfcWriteSession();
                if (!mounted || sessionId != _writeSessionId) {
                  return;
                }
                setState(() {
                  _busy = false;
                  _status = 'E-Paper updated successfully.';
                });
                _appendLog(
                  'Panel refresh power-hold completed.',
                  level: _LogLevel.success,
                );
                return;
              }
              if (e is X1RefreshTriggerConnectionLost &&
                  rediscoveryAttempts < maxRediscoveries) {
                pendingRefreshPollOnly = false;
                pendingRefreshTriggerOnly = true;
                _appendLog(
                  'F0D4 was not acknowledged. Restarting reader mode to '
                  'retry refresh only (attempt '
                  '$rediscoveryAttempts/$maxRediscoveries).',
                  level: _LogLevel.warning,
                );
                if (!mounted) {
                  return;
                }
                setState(() {
                  _status =
                      'Reconnecting to trigger the refresh… Keep your phone '
                      'in place.';
                });
                scheduleRestart();
                return;
              }
              if (_isRefreshTagLostError(e) &&
                  rediscoveryAttempts < maxRediscoveries) {
                _f0StartBlockController.text = '0';
                pendingRefreshPollOnly = true;
                pendingRefreshTriggerOnly = false;
                _appendLog(
                  'NFC field dropped after the refresh trigger. Restarting '
                  'reader mode to poll refresh completion only (attempt '
                  '$rediscoveryAttempts/$maxRediscoveries).',
                  level: _LogLevel.warning,
                );
                if (!mounted) {
                  return;
                }
                setState(() {
                  _status = 'Checking display refresh.';
                });
                scheduleRestart();
                return;
              }

              final int? resumeBlock = _extractF0TagLostBlock(e);
              if (resumeBlock != null &&
                  rediscoveryAttempts < maxRediscoveries) {
                _f0StartBlockController.text = '0';
                pendingRefreshPollOnly = false;
                pendingRefreshTriggerOnly = false;
                _appendLog(
                  'Tag lost at block $resumeBlock. X1 recovery will restart '
                  'from block 0 (attempt '
                  '$rediscoveryAttempts/$maxRediscoveries).',
                  level: _LogLevel.warning,
                );
                if (!mounted) {
                  return;
                }
                setState(() {
                  _status = 'NFC connection interrupted.';
                });
                _appendLog(
                  'Tag lost during F0D2 block $resumeBlock. Restarting reader '
                  'mode for a fresh NFC connection.',
                  level: _LogLevel.warning,
                );
                scheduleRestart();
                return;
              }
              if (_isLikelyTagLostError(e) &&
                  rediscoveryAttempts < maxRediscoveries) {
                _f0StartBlockController.text = '0';
                pendingRefreshPollOnly = false;
                pendingRefreshTriggerOnly = false;
                _appendLog(
                  'Tag lost during X1 initialization/transfer. Restarting '
                  'the complete transaction from block 0 (attempt '
                  '$rediscoveryAttempts/$maxRediscoveries).',
                  level: _LogLevel.warning,
                );
                if (!mounted) {
                  return;
                }
                setState(() {
                  _status = 'NFC connection interrupted.';
                });
                scheduleRestart();
                return;
              }
              if (resumeBlock != null &&
                  rediscoveryAttempts >= maxRediscoveries) {
                _appendLog(
                  'Max rediscovery attempts reached ($maxRediscoveries). Stopping session.',
                  level: _LogLevel.error,
                );
              }

              completed = true;
              _failNfcWrite(
                sessionId,
                'Update failed. ${_friendlyEpaperError(e)}',
                e,
              );
            } finally {
              if (mounted && sessionId == _writeSessionId) {
                _appendLog(
                  'Discovery handler finished. Releasing discovery lock.',
                );
              }
              discoveryInProgress = false;
            }
          },
        );
        _nfcSessionStart = starting;
        try {
          await starting;
        } finally {
          if (identical(_nfcSessionStart, starting)) _nfcSessionStart = null;
        }
      } catch (e) {
        if (sessionId != _writeSessionId) {
          return;
        }
        completed = true;
        _failNfcWrite(
          sessionId,
          'Update failed. Could not start NFC. Make sure NFC is turned on, then try again.',
          e,
        );
      }
    };

    await startAttempt();

    // Keep _busy controlled by success/failure branches in the session callback.
  }

  void _failNfcWrite(int sessionId, String message, [Object? error]) {
    if (!mounted || sessionId != _writeSessionId) return;
    _writeSessionId++;
    _nfcDiscoveryTimer?.cancel();
    setState(() {
      _busy = false;
      _status = message;
    });
    if (error != null) {
      _appendLog('NFC write ended: $error', level: _LogLevel.error);
    }
    unawaited(_stopNfcWriteSession());
  }

  Future<void> _sendFinalCommandsOnly() async {
    final NfcAvailability availability = await NfcManager.instance
        .checkAvailability();
    if (availability != NfcAvailability.enabled) {
      setState(() {
        _status = 'NFC is not available on this device.';
      });
      _appendLog('NFC unavailable: $availability', level: _LogLevel.error);
      return;
    }

    final List<Uint8List> initCommands = _parseHexCommands(
      _initCommandsController.text,
    );
    final List<Uint8List> finalCommands = _parseHexCommands(
      _finalCommandsController.text,
    );

    if (finalCommands.isEmpty) {
      setState(() {
        _status = 'Add one or more Finalize APDUs first.';
      });
      return;
    }

    setState(() {
      _busy = true;
      _status = 'Hold your tag near the phone for finalize commands...';
    });
    _appendLog('=== Finalize-only run ===');

    await NfcManager.instance.startSession(
      pollingOptions: const <NfcPollingOption>{NfcPollingOption.iso14443},
      onDiscovered: (NfcTag tag) async {
        try {
          final X1Iso7816Transport? isoDep = X1Iso7816Transport.fromTag(tag);
          if (isoDep == null) {
            throw StateError(
              'Tag does not expose Android IsoDep or iOS ISO 7816.',
            );
          }

          for (int i = 0; i < initCommands.length; i++) {
            await _sendApdu(
              isoDep,
              initCommands[i],
              label: 'init ${i + 1}/${initCommands.length}',
            );
          }

          for (int i = 0; i < finalCommands.length; i++) {
            await _sendApdu(
              isoDep,
              finalCommands[i],
              label: 'final ${i + 1}/${finalCommands.length}',
            );
          }

          await NfcManager.instance.stopSession();
          if (!mounted) {
            return;
          }
          setState(() {
            _busy = false;
            _status = 'Finalize commands complete.';
          });
          _appendLog('Finalize-only run complete.', level: _LogLevel.success);
        } catch (e) {
          await NfcManager.instance.stopSession();
          if (!mounted) {
            return;
          }
          setState(() {
            _busy = false;
            _status = 'Finalize failed: $e';
          });
          _appendLog('Finalize failed: $e', level: _LogLevel.error);
        }
      },
    );
  }

  Future<void> _runRefreshTester() async {
    final NfcAvailability availability = await NfcManager.instance
        .checkAvailability();
    if (availability != NfcAvailability.enabled) {
      setState(() {
        _status = 'NFC is not available on this device.';
      });
      _appendLog('NFC unavailable: $availability', level: _LogLevel.error);
      return;
    }

    final List<List<Uint8List>> groups = _parseCommandGroups(
      _refreshSequencesController.text,
    );
    if (groups.isEmpty) {
      setState(() {
        _status = 'Add at least one refresh sequence group first.';
      });
      return;
    }

    final int repeats = int.tryParse(_refreshRepeatController.text.trim()) ?? 2;
    final int delayMs =
        int.tryParse(_refreshDelayMsController.text.trim()) ?? 250;

    setState(() {
      _busy = true;
      _status = 'Hold your tag near phone for refresh testing...';
    });
    _appendLog('=== Refresh tester ===');
    _appendLog('Groups=${groups.length}; repeats=$repeats; delayMs=$delayMs');

    await NfcManager.instance.startSession(
      pollingOptions: const <NfcPollingOption>{NfcPollingOption.iso14443},
      onDiscovered: (NfcTag tag) async {
        try {
          final X1Iso7816Transport? isoDep = X1Iso7816Transport.fromTag(tag);
          if (isoDep == null) {
            throw StateError(
              'Tag does not expose Android IsoDep or iOS ISO 7816.',
            );
          }
          await _configureIsoDepSession(isoDep);

          for (int g = 0; g < groups.length; g++) {
            _appendLog('Testing refresh group ${g + 1}/${groups.length}');
            bool groupAnySuccess = false;

            for (int r = 0; r < repeats; r++) {
              _appendLog('Group ${g + 1} run ${r + 1}/$repeats');
              bool allOk = true;
              for (int c = 0; c < groups[g].length; c++) {
                final Uint8List response = await _sendApdu(
                  isoDep,
                  groups[g][c],
                  label: 'refresh g${g + 1} c${c + 1}',
                  failOnStatus: false,
                );
                final int sw = _statusWord(response);
                if (sw != 0x9000 && sw != 0x9100) {
                  allOk = false;
                }
              }
              if (allOk) {
                groupAnySuccess = true;
              }
              if (r < repeats - 1 && delayMs > 0) {
                await Future<void>.delayed(Duration(milliseconds: delayMs));
              }
            }

            _appendLog(
              'Group ${g + 1} result: ${groupAnySuccess ? 'all status OK at least once' : 'non-OK status observed'}',
              level: groupAnySuccess ? _LogLevel.success : _LogLevel.warning,
            );
          }

          await NfcManager.instance.stopSession();
          if (!mounted) {
            return;
          }
          setState(() {
            _busy = false;
            _status = 'Refresh tester complete. Watch display and check log.';
          });
        } catch (e) {
          await NfcManager.instance.stopSession();
          if (!mounted) {
            return;
          }
          setState(() {
            _busy = false;
            _status = 'Refresh tester failed: $e';
          });
          _appendLog('Refresh tester failed: $e', level: _LogLevel.error);
        }
      },
    );
  }

  Future<void> _writeAsNdef(NfcTag tag, Uint8List payload) async {
    final NdefAndroid? ndef = NdefAndroid.from(tag);
    if (ndef == null) {
      throw StateError('Tag is not NDEF compatible.');
    }
    if (!ndef.isWritable) {
      throw StateError('Tag is read-only.');
    }

    final NdefMessage message = NdefMessage(
      records: <NdefRecord>[
        NdefRecord(
          typeNameFormat: TypeNameFormat.media,
          type: Uint8List.fromList(utf8.encode('application/octet-stream')),
          identifier: Uint8List(0),
          payload: payload,
        ),
      ],
    );

    if (ndef.maxSize < payload.length) {
      throw StateError(
        'Tag max size is ${ndef.maxSize} bytes; payload is ${payload.length} bytes.',
      );
    }

    _appendLog(
      'NDEF write. maxSize=${ndef.maxSize}, payload=${payload.length}',
    );
    await ndef.writeNdefMessage(message);
    _appendLog('NDEF write successful.', level: _LogLevel.success);
  }

  Future<void> _writeAsIsoDep(
    NfcTag tag,
    Uint8List payload, {
    bool isResume = false,
    int? resumeBlockOverride,
    bool refreshOnly = false,
    bool refreshTriggerOnly = false,
    bool alternateRefreshMode = false,
  }) async {
    final X1Iso7816Transport? isoDep = X1Iso7816Transport.fromTag(tag);
    if (isoDep == null) {
      throw StateError('Tag does not expose Android IsoDep or iOS ISO 7816.');
    }

    if (_isStrictApkCloneActive()) {
      await _writeAsIsoDepApkExact(
        isoDep,
        payload,
        resumeBlockOverride: isResume ? resumeBlockOverride : null,
        refreshOnly: refreshOnly,
        refreshTriggerOnly: refreshTriggerOnly,
        alternateRefreshMode: alternateRefreshMode,
      );
      return;
    }

    final List<Uint8List> initCommands = _parseHexCommands(
      _initCommandsController.text,
    );
    final List<Uint8List> finalCommands = _parseHexCommands(
      _finalCommandsController.text,
    );

    final Uint8List chunkPrefix = _tryHex(_chunkPrefixController.text.trim())!;
    final int chunkSize = int.tryParse(_chunkSizeController.text.trim()) ?? 220;
    final int maxChunk = _useF0D2BlockWrite ? 250 : 240;
    if (chunkSize < 1 || chunkSize > maxChunk) {
      throw StateError('Chunk size must be between 1 and $maxChunk.');
    }

    final int maxTx = await isoDep.getMaxTransceiveLength();
    await _configureIsoDepSession(isoDep);
    _appendLog(
      'IsoDep connected. maxTx=$maxTx; chunkSize=$chunkSize; prefix=${_hex(chunkPrefix)}',
    );
    if (!_autoOffsetP1P2 &&
        chunkPrefix.length >= 2 &&
        chunkPrefix[0] == 0x00 &&
        chunkPrefix[1] == 0xD6) {
      _appendLog(
        'Warning: Auto offset is OFF with UPDATE BINARY prefix. All chunks may overwrite the same card address.',
        level: _LogLevel.warning,
      );
    }

    final bool useNlenFramingRequested =
        !_useF0D2BlockWrite && _fitToTagCapacity && _type4NlenFraming;
    bool nlenFramingActive = useNlenFramingRequested;
    if (_useF0D2BlockWrite && (_fitToTagCapacity || _type4NlenFraming)) {
      _appendLog(
        'F0D2 mode active: forcing full-frame path and skipping Type4 NLEN fit framing.',
        level: _LogLevel.warning,
      );
    }
    if (nlenFramingActive) {
      final int maxPayload = tagCapacityBytes - 2;
      if (payload.length > maxPayload) {
        throw StateError(
          'Fit payload is ${payload.length} bytes but max with NLEN header is $maxPayload bytes.',
        );
      }
      final Uint8List clearNlen = _buildChunkApdu(
        chunkPrefix,
        Uint8List.fromList(<int>[0x00, 0x00]),
        offset: 0,
        autoOffsetP1P2: true,
        appendLe: _appendLe,
      );
      final Uint8List response = await _sendApdu(
        isoDep,
        clearNlen,
        label: 'nlen clear',
        failOnStatus: false,
      );
      final int sw = _statusWord(response);
      final bool ok = sw == 0x9000 || sw == 0x9100;
      if (!ok) {
        nlenFramingActive = false;
        _appendLog(
          'NLEN framing unsupported on this tag (SW=${sw.toRadixString(16).padLeft(4, '0').toUpperCase()}). Falling back to raw offset 0 writes.',
          level: _LogLevel.warning,
        );
      }
    }

    if (!isResume) {
      for (int i = 0; i < initCommands.length; i++) {
        await _sendApdu(
          isoDep,
          initCommands[i],
          label: 'init ${i + 1}/${initCommands.length}',
        );
      }
    } else {
      _appendLog(
        'Resuming F0D2 write from saved block (skipping init commands).',
      );
    }

    if (_useF0D2BlockWrite) {
      final String planeText = _f0PlaneController.text.trim();
      final int plane = int.tryParse(planeText, radix: 16) ?? 0;
      final Uint8List effectivePayload =
          (_useApkColorMapping &&
              !_fitToTagCapacity &&
              _apkColorPipeline == _ApkColorPipeline.fourColorPacked)
          ? _applyTxTransforms(payload)
          : payload;
      final List<MapEntry<int, Uint8List>> planePayloads =
          <MapEntry<int, Uint8List>>[
            MapEntry<int, Uint8List>(plane, effectivePayload),
          ];
      if (_packedFramePlane1Bytes != null &&
          _useApkColorMapping &&
          !_fitToTagCapacity &&
          _uploadSecondColorPlane) {
        planePayloads.add(
          MapEntry<int, Uint8List>(plane + 1, _packedFramePlane1Bytes!),
        );
      }
      const int blockSize = 250;
      final int startBlockRaw =
          resumeBlockOverride ??
          (int.tryParse(_f0StartBlockController.text.trim()) ?? 0);
      _appendLog(
        'F0D2 upload entry: isResume=$isResume, resumeBlockOverride=${resumeBlockOverride ?? -1}, controllerStart=${_f0StartBlockController.text.trim()}, startBlockRaw=$startBlockRaw',
      );
      for (
        int planeIndex = 0;
        planeIndex < planePayloads.length;
        planeIndex++
      ) {
        final int planeId = planePayloads[planeIndex].key;
        final Uint8List planeBytes = planePayloads[planeIndex].value;
        final int payloadBlocks = (planeBytes.length / blockSize).ceil();
        final int fixedBlocks =
            int.tryParse(_f0FixedBlockCountController.text.trim()) ?? 0;
        final int blocks = _useF0FixedBlockCount && fixedBlocks > 0
            ? math.max(payloadBlocks, fixedBlocks)
            : payloadBlocks;
        final int startBlock = planeIndex == 0
            ? startBlockRaw.clamp(0, math.max(0, blocks - 1))
            : 0;

        _appendLog(
          'F0D2 plane ${planeId.toRadixString(16).padLeft(2, '0').toUpperCase()} upload. blocks=$blocks, startBlock=$startBlock, startBlockRaw=$startBlockRaw, planeIndex=$planeIndex',
        );

        for (int i = startBlock; i < blocks; i++) {
          final int start = i * blockSize;
          final int end = math.min(start + blockSize, planeBytes.length);
          final List<int> data = List<int>.filled(blockSize, 0x00);
          for (int j = start; j < end; j++) {
            data[j - start] = planeBytes[j];
          }
          final Uint8List cmd = Uint8List.fromList(<int>[
            0xF0,
            0xD2,
            planeId & 0xFF,
            i & 0xFF,
            0xFA,
            ...data,
          ]);
          try {
            await _sendApdu(
              isoDep,
              cmd,
              label:
                  'f0d2 p${planeId.toRadixString(16).padLeft(2, '0').toUpperCase()} block ${i + 1}/$blocks ($start..${end - 1})',
            );
          } catch (e) {
            if (_isLikelyTagLostError(e) && planeIndex == 0) {
              _f0StartBlockController.text = i.toString();
              _appendLog(
                'F0D2 block $i failed with tag-loss. Allowing 500ms for card state stabilization.',
                level: _LogLevel.warning,
              );
              await Future<void>.delayed(const Duration(milliseconds: 500));
              throw StateError('F0D2_TAG_LOST_AT_BLOCK:$i');
            }
            rethrow;
          }
          if (!mounted) {
            continue;
          }
          final double percent = ((i + 1) / blocks) * 100;
          setState(() {
            _status =
                'Uploading F0D2 plane ${planeIndex + 1}/${planePayloads.length} ${percent.toStringAsFixed(1)}%';
          });
        }
      }

      if (_autoResumeF0FromLastBlock) {
        _f0StartBlockController.text = '0';
      }

      for (int i = 0; i < finalCommands.length; i++) {
        await _sendApdu(
          isoDep,
          finalCommands[i],
          label: 'final ${i + 1}/${finalCommands.length}',
        );
      }

      final int settleMs = _parseMs(_postWriteSettleMsController, 0);
      if (settleMs > 0) {
        _appendLog('Pre-refresh settle delay: ${settleMs}ms');
        await Future<void>.delayed(Duration(milliseconds: settleMs));
      }

      if (_autoRunApkRefreshFlow) {
        final int attempts =
            int.tryParse(_refreshRepeatController.text.trim()) ?? 1;
        final int gapMs =
            int.tryParse(_refreshDelayMsController.text.trim()) ?? 250;
        final bool refreshed = await _runApkRefreshWithRetries(
          isoDep,
          attempts: attempts,
          gapMs: gapMs,
        );
        if (!refreshed) {
          _appendLog(
            'Auto APK refresh flow ended without success.',
            level: _LogLevel.warning,
          );
        }
      }
      return;
    }

    int payloadOffset = 0;
    int cardOffset = nlenFramingActive ? 2 : 0;
    int chunkIndex = 0;
    final int totalChunks = (payload.length / chunkSize).ceil();
    while (payloadOffset < payload.length) {
      int currentChunkSize = math.min(
        chunkSize,
        payload.length - payloadOffset,
      );
      bool sent = false;

      while (!sent) {
        final int end = payloadOffset + currentChunkSize;
        final Uint8List chunk = payload.sublist(payloadOffset, end);
        final Uint8List command = _buildChunkApdu(
          chunkPrefix,
          chunk,
          offset: cardOffset,
          autoOffsetP1P2: _autoOffsetP1P2,
          appendLe: _appendLe,
        );

        chunkIndex++;
        final Uint8List response = await _sendApdu(
          isoDep,
          command,
          label:
              'chunk $chunkIndex/$totalChunks payload $payloadOffset..${end - 1} card@$cardOffset (${chunk.length})',
          failOnStatus: false,
        );

        final int sw = _statusWord(response);
        final bool ok = sw == 0x9000 || sw == 0x9100;
        if (ok) {
          payloadOffset = end;
          cardOffset += chunk.length;
          sent = true;
          continue;
        }

        if (sw == 0x6700 && currentChunkSize > 1) {
          final int reduced = math.max(1, currentChunkSize ~/ 2);
          _appendLog(
            'SW=6700 at payloadOffset=$payloadOffset cardOffset=$cardOffset size=$currentChunkSize, retrying with size=$reduced',
            level: _LogLevel.warning,
          );
          currentChunkSize = reduced;
          chunkIndex--;
          continue;
        }

        if (sw == 0x6700) {
          throw StateError(
            'Reached writable boundary near card offset $cardOffset bytes. Tag capacity is far below needed ${payload.length} bytes.',
          );
        }

        throw StateError(
          'APDU failed at payloadOffset $payloadOffset (cardOffset $cardOffset) with status ${sw.toRadixString(16).padLeft(4, '0')}',
        );
      }

      if (!mounted) {
        continue;
      }
      setState(() {
        _status =
            'Uploading ${((payloadOffset / payload.length) * 100).toStringAsFixed(1)}%';
      });
    }

    if (nlenFramingActive) {
      final int nlen = payload.length;
      final Uint8List commitNlen = _buildChunkApdu(
        chunkPrefix,
        Uint8List.fromList(<int>[(nlen >> 8) & 0xFF, nlen & 0xFF]),
        offset: 0,
        autoOffsetP1P2: true,
        appendLe: _appendLe,
      );
      await _sendApdu(isoDep, commitNlen, label: 'nlen commit $nlen');
    }

    for (int i = 0; i < finalCommands.length; i++) {
      await _sendApdu(
        isoDep,
        finalCommands[i],
        label: 'final ${i + 1}/${finalCommands.length}',
      );
    }

    final int settleMs = _parseMs(_postWriteSettleMsController, 0);
    if (settleMs > 0) {
      _appendLog('Post-write settle delay: ${settleMs}ms');
      await Future<void>.delayed(Duration(milliseconds: settleMs));
    }
  }

  Future<void> _writeAsIsoDepApkExact(
    X1Iso7816Transport isoDep,
    Uint8List payload, {
    int? resumeBlockOverride,
    bool refreshOnly = false,
    bool refreshTriggerOnly = false,
    bool alternateRefreshMode = false,
  }) async {
    await _configureIsoDepSession(isoDep);
    final int maxTx = await isoDep.getMaxTransceiveLength();
    if (maxTx < 255) {
      throw StateError(
        'This phone/tag connection supports only $maxTx-byte ISO-DEP '
        'transceives; the X1 protocol requires 255 bytes.',
      );
    }
    final int requestedStartBlock = resumeBlockOverride ?? 0;
    _appendLog(
      'APK exact path active. maxTx=$maxTx; payload=${payload.length} bytes; '
      'resumeBlockOverride=$requestedStartBlock; refreshOnly=$refreshOnly; '
      'refreshTriggerOnly=$refreshTriggerOnly; '
      'alternateRefreshMode=$alternateRefreshMode.',
    );

    await _sendApdu(
      isoDep,
      X1NfcEpaperProtocol.selectApplication,
      label: 'apk init 1/3 select application',
      acceptedStatusWords: const <int>{0x9000},
    );
    final Uint8List deviceInfoResponse = await _sendApdu(
      isoDep,
      X1NfcEpaperProtocol.readDeviceInfo,
      label: 'apk init 2/3 read device info',
      acceptedStatusWords: const <int>{0x9000},
    );
    final Uint8List descriptorResponse = await _sendApdu(
      isoDep,
      X1NfcEpaperProtocol.readDeviceDescriptor,
      label: 'apk init 3/3 read device descriptor',
      failOnStatus: false,
      acceptedStatusWords: const <int>{0x9000, 0x6985},
    );

    final X1NfcDeviceInfo deviceInfo;
    try {
      deviceInfo = X1NfcEpaperProtocol.parseDeviceInfo(
        deviceInfoResponse: deviceInfoResponse,
        descriptorResponse: descriptorResponse,
      );
    } on FormatException catch (error) {
      throw StateError(
        'The NFC display returned invalid device information: '
        '${error.message}',
      );
    }

    if (deviceInfo.requiresPin) {
      throw StateError(
        'This e-paper display is PIN-protected. Remove the PIN in the '
        'vendor app before updating it from LATCH.',
      );
    }
    if (!deviceInfo.isLatchDisplay) {
      throw StateError(
        'Unsupported e-paper profile: ${deviceInfo.width}x${deviceInfo.height}, '
        '${deviceInfo.colorCount} colors. LATCH currently supports the '
        'X1 240x416 four-color display.',
      );
    }

    final _ApkScanMode detectedScanMode =
        deviceInfo.scanMode == X1EpaperScanMode.vertical
        ? _ApkScanMode.vertical
        : _ApkScanMode.horizontal;
    Uint8List effectivePayload = payload;
    if (_apkScanMode != detectedScanMode) {
      _apkScanMode = detectedScanMode;
      _appendLog(
        'Device refreshScan=${deviceInfo.scanMode.name}; rebuilding the '
        'frame in the panel scan order.',
      );
      _rebuildImagePipeline();
      effectivePayload = _packedFrameBytes ?? payload;
    } else {
      _appendLog(
        'Device refreshScan=${deviceInfo.scanMode.name}; prepared frame '
        'already matches.',
      );
    }

    final int expectedPayloadBytes =
        (deviceInfo.width * deviceInfo.height * 2) ~/ 8;
    if (effectivePayload.length != expectedPayloadBytes) {
      throw StateError(
        'Prepared frame has ${effectivePayload.length} bytes; the display '
        'requires exactly $expectedPayloadBytes bytes.',
      );
    }

    const int blockSize = 250;
    const int plane = 0x00;
    final int blocks = (effectivePayload.length / blockSize).ceil();
    if (!refreshOnly && !refreshTriggerOnly) {
      if (deviceInfo.supportsCompression) {
        if (maxTx < 257) {
          throw StateError(
            'This phone/tag connection supports only $maxTx-byte ISO-DEP '
            'transceives; this X1 display requires 257 bytes.',
          );
        }
        final List<X1CompressedWriteFragment> fragments =
            buildX1CompressedWriteFragments(effectivePayload, plane: plane);
        _appendLog(
          'APK exact F0D3 compressed upload start: '
          'pages=${(effectivePayload.length / x1CompressionPageSize).ceil()} '
          'fragments=${fragments.length}.',
        );
        for (int i = 0; i < fragments.length; i++) {
          final X1CompressedWriteFragment fragment = fragments[i];
          try {
            await transceiveX1WriteFragment(
              command: fragment.command,
              isTransientError: _isLikelyTagLostError,
              transceive: (Uint8List command) => _sendApdu(
                isoDep,
                command,
                label:
                    'apk f0d3 page ${fragment.pageIndex + 1} '
                    'fragment ${fragment.fragmentIndex + 1}',
                acceptedStatusWords: const <int>{0x9000},
              ),
              onRetry:
                  (int retry, int maxRetries, Duration delay, Object error) {
                    _appendLog(
                      'F0D3 transient NFC failure; retrying the same fragment '
                      '$retry/$maxRetries after ${delay.inMilliseconds}ms.',
                      level: _LogLevel.warning,
                    );
                  },
            );
            await Future<void>.delayed(const Duration(milliseconds: 12));
          } catch (e) {
            if (_isLikelyTagLostError(e)) {
              _f0StartBlockController.text = '0';
              _appendLog(
                'F0D3 tag-loss at page ${fragment.pageIndex}, '
                'fragment ${fragment.fragmentIndex}.',
                level: _LogLevel.warning,
              );
              throw StateError('F0D2_TAG_LOST_AT_BLOCK:${fragment.pageIndex}');
            }
            rethrow;
          }
          if (mounted) {
            final double percent = ((i + 1) / fragments.length) * 100;
            setState(() {
              _status = 'Updating E-Paper… ${percent.round()}%';
            });
          }
        }
      } else {
        final int startBlock = requestedStartBlock.clamp(
          0,
          math.max(0, blocks - 1),
        );
        _appendLog(
          'APK exact F0D2 upload start: blocks=$blocks plane=00 startBlock=$startBlock',
        );
        for (int i = startBlock; i < blocks; i++) {
          final int start = i * blockSize;
          final int end = math.min(start + blockSize, effectivePayload.length);
          final Uint8List cmd = X1NfcEpaperProtocol.buildWriteBlock(
            plane: plane,
            blockIndex: i,
            payload: effectivePayload,
            payloadOffset: start,
          );
          try {
            await _sendApdu(
              isoDep,
              cmd,
              label: 'apk f0d2 block ${i + 1}/$blocks ($start..${end - 1})',
              acceptedStatusWords: const <int>{0x9000},
            );
          } catch (e) {
            if (_isLikelyTagLostError(e)) {
              _f0StartBlockController.text = '0';
              _appendLog(
                'APK exact tag-loss at block $i. Restarting the full X1 '
                'transaction after rediscovery.',
                level: _LogLevel.warning,
              );
              throw StateError('F0D2_TAG_LOST_AT_BLOCK:$i');
            }
            rethrow;
          }

          if (mounted) {
            final double percent = ((i + 1) / blocks) * 100;
            setState(() {
              _status = 'Updating E-Paper… ${percent.round()}%';
            });
          }
        }
      }
      _appendLog(
        'APK exact upload complete ($blocks blocks). Starting refresh...',
        level: _LogLevel.success,
      );
      if (mounted) {
        setState(() {
          _status = 'Refreshing E-Paper…';
        });
      }
    } else if (refreshOnly) {
      _appendLog(
        'APK exact refresh-poll recovery active. Skipping image upload '
        'and refresh trigger.',
        level: _LogLevel.warning,
      );
    } else {
      _appendLog(
        'APK exact refresh-trigger recovery active. Skipping image upload '
        'and retrying the refresh command.',
        level: _LogLevel.warning,
      );
    }

    final bool refreshed;
    try {
      refreshed = refreshOnly
          ? await _apkExactRefreshPoll(
              isoDep,
              refreshIndex: 0,
              firstBranchUsed: false,
              variantMode85Used: false,
            )
          : await _apkExactRefreshTrigger(
              isoDep,
              refreshIndex: 0,
              firstBranchUsed: false,
              variantMode85Used: alternateRefreshMode,
            );
    } catch (e) {
      if (e is X1RefreshAcceptedConnectionLost ||
          e is X1RefreshTriggerConnectionLost) {
        rethrow;
      }
      if (_isLikelyTagLostError(e)) {
        _appendLog(
          'Tag lost while triggering/checking the APK exact refresh. '
          'Deferring completion to rediscovery.',
          level: _LogLevel.warning,
        );
        throw StateError('F0D2_REFRESH_TAG_LOST');
      }
      rethrow;
    }

    if (!mounted) {
      return;
    }

    if (!refreshed) {
      _appendLog(
        'APK exact refresh ended without success status.',
        level: _LogLevel.error,
      );
      throw StateError(
        'Image data was transferred, but the display did not acknowledge '
        'the refresh.',
      );
    } else {
      _appendLog(
        'APK exact write+refresh complete (full success).',
        level: _LogLevel.success,
      );
      setState(() {
        _status = 'E-Paper updated successfully.';
      });
    }
  }

  Future<Uint8List> _sendApdu(
    X1Iso7816Transport isoDep,
    Uint8List cmd, {
    required String label,
    bool failOnStatus = true,
    Set<int> acceptedStatusWords = const <int>{0x9000, 0x9100},
  }) async {
    _ensureWriteActive();
    _appendLog('APDU -> $label | ${cmd.length}B | ${_hex(cmd, maxBytes: 24)}');
    Uint8List response;
    int transceiveRetries = 0;
    final bool isF0D2Block = label.contains('f0d2') && label.contains('block');
    final bool isApkF0D2Block = label.contains('apk f0d2 block');
    final bool isApkF0D3Fragment =
        label.contains('apk f0d3') && label.contains('fragment');
    final bool isApkWriteFragment = isApkF0D2Block || isApkF0D3Fragment;
    final bool isApkRefreshTrigger = label.contains('apk exact trigger');
    final bool isApkRefreshPoll = label.contains('apk exact poll');
    final bool isApkExactCommand = label.startsWith('apk ');
    final int maxTransceiveRetries =
        isApkExactCommand ||
            isApkF0D2Block ||
            isApkF0D3Fragment ||
            isApkRefreshTrigger ||
            isApkRefreshPoll
        ? 0
        : (isF0D2Block ? 200 : 20);
    int statusRetries = 0;
    final int maxStatusRetries = isApkExactCommand || isApkWriteFragment
        ? 0
        : 8;
    while (true) {
      _ensureWriteActive();
      try {
        response = await isoDep.transceive(cmd);
        _ensureWriteActive();
        break;
      } on Exception catch (e) {
        final String errText = e.toString();
        final bool isRecoverable =
            _isLikelyTagLostError(e) ||
            errText.contains('Call connect() first') ||
            errText.contains('transceive failed');
        if (isRecoverable && transceiveRetries < maxTransceiveRetries) {
          transceiveRetries++;
          final int waitMs = (isApkRefreshTrigger || isApkF0D2Block)
              ? 300
              : _tagLostRetryDelayMs(label, transceiveRetries);
          _appendLog(
            'APDU <- $label | tag lost/disconnected (retry $transceiveRetries/$maxTransceiveRetries, wait ${waitMs}ms for tag return).',
            level: _LogLevel.warning,
          );
          await Future<void>.delayed(Duration(milliseconds: waitMs));
          await _configureIsoDepSession(isoDep);
          continue;
        }
        _appendLog(
          'APDU <- $label | transceive failed (final): $e',
          level: _LogLevel.error,
        );
        rethrow;
      }
    }

    while (true) {
      if (response.length < 2) {
        _appendLog('APDU <- $label | invalid response (<2 bytes)');
        throw StateError('Invalid APDU response.');
      }

      final int sw1 = response[response.length - 2];
      final int sw2 = response[response.length - 1];
      final Uint8List data = response.length > 2
          ? response.sublist(0, response.length - 2)
          : Uint8List(0);
      final String sw =
          '${sw1.toRadixString(16).padLeft(2, '0').toUpperCase()}${sw2.toRadixString(16).padLeft(2, '0').toUpperCase()}';
      final int statusWord = (sw1 << 8) | sw2;
      final bool ok = acceptedStatusWords.contains(statusWord);

      if (!ok &&
          sw1 == 0x65 &&
          sw2 == 0x02 &&
          statusRetries < maxStatusRetries) {
        statusRetries++;
        final int waitMs = _statusRetryDelayMs(label, statusRetries);
        _appendLog(
          'APDU <- $label | SW=6502, waiting ${waitMs}ms before retry $statusRetries/$maxStatusRetries.',
          level: _LogLevel.warning,
        );
        await Future<void>.delayed(Duration(milliseconds: waitMs));
        await _configureIsoDepSession(isoDep);
        try {
          response = await isoDep.transceive(cmd);
          _ensureWriteActive();
          continue;
        } on Exception catch (e) {
          final String errText = e.toString();
          final bool isRecoverable =
              _isLikelyTagLostError(e) ||
              errText.contains('Call connect() first') ||
              errText.contains('transceive failed');
          if (isRecoverable && transceiveRetries < maxTransceiveRetries) {
            transceiveRetries++;
            final int waitRetryMs = _tagLostRetryDelayMs(
              label,
              transceiveRetries,
            );
            _appendLog(
              'APDU <- $label | tag lost/disconnected during 6502 retry (retry $transceiveRetries/$maxTransceiveRetries, wait ${waitRetryMs}ms for tag return).',
              level: _LogLevel.warning,
            );
            await Future<void>.delayed(Duration(milliseconds: waitRetryMs));
            await _configureIsoDepSession(isoDep);
            continue;
          }
          _appendLog(
            'APDU <- $label | transceive failed (final): $e',
            level: _LogLevel.error,
          );
          rethrow;
        }
      }

      _appendLog(
        'APDU <- $label | ${response.length}B | SW=$sw | DATA=${_hex(data, maxBytes: 24)}',
        level: ok ? _LogLevel.success : _LogLevel.error,
      );
      if (!ok && failOnStatus) {
        throw StateError(
          'APDU failed with status ${sw1.toRadixString(16).padLeft(2, '0')}${sw2.toRadixString(16).padLeft(2, '0')}',
        );
      }
      break;
    }

    final int pacingMs = _parseMs(_apduPacingMsController, 0);
    if (pacingMs > 0) {
      await Future<void>.delayed(Duration(milliseconds: pacingMs));
    }
    return response;
  }

  int _statusWord(Uint8List response) {
    if (response.length < 2) {
      return 0;
    }
    return (response[response.length - 2] << 8) | response[response.length - 1];
  }

  Uint8List _buildChunkApdu(
    Uint8List prefix,
    Uint8List chunk, {
    required int offset,
    required bool autoOffsetP1P2,
    required bool appendLe,
  }) {
    final Uint8List workingPrefix = Uint8List.fromList(prefix);
    if (autoOffsetP1P2 && workingPrefix.length >= 4) {
      workingPrefix[2] = (offset >> 8) & 0xFF;
      workingPrefix[3] = offset & 0xFF;
    }

    final List<int> out = <int>[...workingPrefix, chunk.length, ...chunk];
    if (appendLe) {
      out.add(0x00);
    }
    return Uint8List.fromList(out);
  }

  Uint8List _applyTxTransforms(Uint8List input) {
    if (_isStrictApkCloneActive() || (!_txNibbleSwap && !_txBitReverse)) {
      return input;
    }
    final Uint8List out = Uint8List.fromList(input);
    for (int i = 0; i < out.length; i++) {
      int b = out[i];
      if (_txNibbleSwap) {
        b = ((b & 0x0F) << 4) | ((b & 0xF0) >> 4);
      }
      if (_txBitReverse) {
        b = _reverseByte(b);
      }
      out[i] = b & 0xFF;
    }
    return out;
  }

  int _reverseByte(int value) {
    int v = value & 0xFF;
    v = ((v & 0xF0) >> 4) | ((v & 0x0F) << 4);
    v = ((v & 0xCC) >> 2) | ((v & 0x33) << 2);
    v = ((v & 0xAA) >> 1) | ((v & 0x55) << 1);
    return v & 0xFF;
  }

  List<Uint8List> _parseHexCommands(String input) {
    final List<String> lines = input
        .split(RegExp(r'\r?\n'))
        .map((String e) => e.trim())
        .where((String e) => e.isNotEmpty)
        .toList();

    final List<Uint8List> parsed = <Uint8List>[];
    for (final String line in lines) {
      final Uint8List? bytes = _tryHex(line);
      if (bytes == null || bytes.isEmpty) {
        throw StateError('Invalid hex command line: $line');
      }
      parsed.add(bytes);
    }
    return parsed;
  }

  List<List<Uint8List>> _parseCommandGroups(String input) {
    final List<String> rawGroups = input
        .split(RegExp(r'\r?\n\s*\r?\n'))
        .map((String g) => g.trim())
        .where((String g) => g.isNotEmpty)
        .toList();

    final List<List<Uint8List>> groups = <List<Uint8List>>[];
    for (final String raw in rawGroups) {
      groups.add(_parseHexCommands(raw));
    }
    return groups;
  }

  Uint8List? _tryHex(String text) {
    final String cleaned = text.replaceAll(RegExp(r'[^0-9a-fA-F]'), '');
    if (cleaned.isEmpty || cleaned.length.isOdd) {
      return null;
    }

    final List<int> out = <int>[];
    for (int i = 0; i < cleaned.length; i += 2) {
      final int? byte = int.tryParse(cleaned.substring(i, i + 2), radix: 16);
      if (byte == null) {
        return null;
      }
      out.add(byte);
    }
    return Uint8List.fromList(out);
  }

  void _appendLog(String message, {_LogLevel level = _LogLevel.info}) {
    final String timestamp = DateTime.now().toIso8601String().substring(11, 19);
    final String line = '[$timestamp] $message';
    assert(() {
      debugPrint('LATCH_EPD ${level.name}: $line');
      return true;
    }());
    if (!mounted) {
      return;
    }
    setState(() {
      _nfcLogs.add(_LogEntry(line: line, level: level));
      if (_nfcLogs.length > maxLogLines) {
        _nfcLogs.removeRange(0, _nfcLogs.length - maxLogLines);
      }
    });
  }

  Color _logColor(BuildContext context, _LogLevel level) {
    final ColorScheme scheme = Theme.of(context).colorScheme;
    switch (level) {
      case _LogLevel.success:
        return Colors.green.shade700;
      case _LogLevel.warning:
        return scheme.tertiary;
      case _LogLevel.error:
        return scheme.error;
      case _LogLevel.info:
        return Theme.of(context).textTheme.bodySmall?.color ?? scheme.onSurface;
    }
  }

  String _hex(Uint8List bytes, {int maxBytes = 0}) {
    final int limit = maxBytes > 0
        ? math.min(bytes.length, maxBytes)
        : bytes.length;
    final String head = bytes
        .take(limit)
        .map((int b) => b.toRadixString(16).padLeft(2, '0').toUpperCase())
        .join(' ');
    if (limit < bytes.length) {
      return '$head ...';
    }
    return head;
  }

  @override
  Widget build(BuildContext context) {
    if (widget.embedded) {
      return const SizedBox.shrink();
    }

    final Uint8List? packed = _packedFrameBytes;

    return Scaffold(
      appBar: AppBar(title: const Text('EPaper NFC Uploader')),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: <Widget>[
          Wrap(
            spacing: 10,
            runSpacing: 10,
            children: <Widget>[
              ElevatedButton.icon(
                onPressed: _busy ? null : _pickImage,
                icon: const Icon(Icons.photo_library_outlined),
                label: const Text('Upload Image'),
              ),
              ElevatedButton.icon(
                onPressed: _busy || packed == null ? null : _writeTag,
                icon: const Icon(Icons.nfc_outlined),
                label: const Text('Write NFC'),
              ),
            ],
          ),
          const SizedBox(height: 12),
          Text(_status),
          const SizedBox(height: 8),
          const SizedBox(height: 16),
          if (_previewPngBytes != null)
            AspectRatio(
              aspectRatio: _frameWidth / _frameHeight,
              child: DecoratedBox(
                decoration: BoxDecoration(
                  border: Border.all(
                    color: Theme.of(context).colorScheme.outline,
                  ),
                ),
                child: Image.memory(
                  _previewPngBytes!,
                  filterQuality: FilterQuality.none,
                  fit: BoxFit.contain,
                ),
              ),
            ),
          const SizedBox(height: 16),
        ],
      ),
    );
  }
}

class _PreparedFrame {
  const _PreparedFrame({
    required this.previewPng,
    required this.packed,
    this.packedPlane1,
  });

  final Uint8List previewPng;
  final Uint8List packed;
  final Uint8List? packedPlane1;
}

img.Image _flattenImageOnWhite(img.Image source) {
  final img.Image flattened = img.Image(
    width: source.width,
    height: source.height,
  );

  for (int y = 0; y < source.height; y++) {
    for (int x = 0; x < source.width; x++) {
      final img.Pixel pixel = source.getPixel(x, y);
      final double alpha = pixel.a.toDouble().clamp(0, 255) / 255;
      int flatten(num channel) =>
          ((channel.toDouble() * alpha) + (255 * (1 - alpha))).round().clamp(
            0,
            255,
          );

      flattened.setPixelRgb(
        x,
        y,
        flatten(pixel.r),
        flatten(pixel.g),
        flatten(pixel.b),
      );
    }
  }

  return flattened;
}

img.Image _resizeToCover(
  img.Image source,
  int targetW,
  int targetH, {
  img.Interpolation interpolation = img.Interpolation.cubic,
}) {
  final double srcAspect = source.width / source.height;
  final double targetAspect = targetW / targetH;

  int cropW = source.width;
  int cropH = source.height;
  int offsetX = 0;
  int offsetY = 0;

  if (srcAspect > targetAspect) {
    cropW = (source.height * targetAspect).round();
    offsetX = (source.width - cropW) ~/ 2;
  } else {
    cropH = (source.width / targetAspect).round();
    offsetY = (source.height - cropH) ~/ 2;
  }

  final img.Image cropped = img.copyCrop(
    source,
    x: offsetX,
    y: offsetY,
    width: cropW,
    height: cropH,
  );

  return img.copyResize(
    cropped,
    width: targetW,
    height: targetH,
    interpolation: interpolation,
  );
}

_PreparedFrame _prepareMonochromeFrame(
  img.Image source, {
  required double threshold,
  required bool invert,
  required bool dither,
  required bool columnMajor,
  required bool lsbFirst,
}) {
  final int w = source.width;
  final int h = source.height;

  final List<double> luminance = List<double>.filled(w * h, 0);

  for (int y = 0; y < h; y++) {
    for (int x = 0; x < w; x++) {
      final img.Pixel p = source.getPixel(x, y);
      final double l = 0.299 * p.r + 0.587 * p.g + 0.114 * p.b;
      luminance[y * w + x] = l;
    }
  }

  final Uint8List packed = Uint8List((w * h) ~/ 8);
  final img.Image preview = img.Image(width: w, height: h);

  if (dither) {
    final List<double> d = List<double>.from(luminance);

    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        final int idx = y * w + x;
        final double oldValue = d[idx];
        final double newValue = oldValue < threshold ? 0 : 255;
        final double error = oldValue - newValue;
        d[idx] = newValue;

        if (x + 1 < w) {
          d[idx + 1] += error * 7 / 16;
        }
        if (y + 1 < h && x > 0) {
          d[idx + w - 1] += error * 3 / 16;
        }
        if (y + 1 < h) {
          d[idx + w] += error * 5 / 16;
        }
        if (y + 1 < h && x + 1 < w) {
          d[idx + w + 1] += error * 1 / 16;
        }
      }
    }

    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        final int idx = y * w + x;
        bool black = d[idx] < threshold;
        if (invert) {
          black = !black;
        }
        final int packedIndex = columnMajor ? (x * h + y) : idx;
        _writePackedBit(packed, packedIndex, black, lsbFirst: lsbFirst);
        preview.setPixelRgb(
          x,
          y,
          black ? 0 : 255,
          black ? 0 : 255,
          black ? 0 : 255,
        );
      }
    }
  } else {
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        final int idx = y * w + x;
        bool black = luminance[idx] < threshold;
        if (invert) {
          black = !black;
        }
        final int packedIndex = columnMajor ? (x * h + y) : idx;
        _writePackedBit(packed, packedIndex, black, lsbFirst: lsbFirst);
        preview.setPixelRgb(
          x,
          y,
          black ? 0 : 255,
          black ? 0 : 255,
          black ? 0 : 255,
        );
      }
    }
  }

  final Uint8List previewPng = Uint8List.fromList(img.encodePng(preview));
  return _PreparedFrame(previewPng: previewPng, packed: packed);
}

_PreparedFrame _prepareApkColorFrame(
  img.Image source, {
  required double threshold,
  required bool dither,
  required _ApkScanMode scanMode,
  required _AccentColor accentColor,
  required _ApkColorPipeline pipeline,
  required bool photoPreserve,
  required int codeWhite,
  required int codeBlack,
  required int codeRed,
  required int codeYellow,
}) {
  final int w = source.width;
  final int h = source.height;
  final List<int> plane0Bits = List<int>.filled(w * h, 0);
  final List<int> plane1Bits = List<int>.filled(w * h, 0);
  final List<int> code4 = List<int>.filled(w * h, 0);
  final img.Image preview = img.Image(width: w, height: h);

  int nearestPalette(int r, int g, int b) {
    // 0=white, 1=black, 2=accent
    final double luma = 0.299 * r + 0.587 * g + 0.114 * b;
    if (luma < threshold * 0.65) {
      return 1;
    }

    if (accentColor == _AccentColor.red) {
      final bool strongRed = r > 95 && r > g + 35 && r > b + 35;
      if (strongRed) {
        return 2;
      }
      return 0;
    }

    final bool strongYellow =
        r > 110 && g > 100 && b < 120 && (r - g).abs() < 95;
    if (strongYellow) {
      return 2;
    }
    return 0;
  }

  int nearest4Palette(int r, int g, int b) {
    if (!photoPreserve) {
      // Vendor tie order is black, white, red, yellow. The fourth element is
      // the class used by the rest of this method (white=0, black=1, etc.).
      const List<List<int>> palette = <List<int>>[
        <int>[0, 0, 0, 1],
        <int>[255, 255, 255, 0],
        <int>[255, 0, 0, 2],
        <int>[255, 255, 0, 3],
      ];

      int best = 0;
      int bestDist = 195076;
      for (int i = 0; i < palette.length; i++) {
        final int dr = r - palette[i][0];
        final int dg = g - palette[i][1];
        final int db = b - palette[i][2];
        final int dist = (dr * dr) + (dg * dg) + (db * db);
        if (dist < bestDist) {
          bestDist = dist;
          best = palette[i][3];
        }
      }
      return best;
    }

    // Photo-preserving mode: reduce red/yellow flooding on skin/neutral tones.
    const List<List<int>> palette = <List<int>>[
      <int>[255, 255, 255],
      <int>[0, 0, 0],
      <int>[220, 35, 35],
      <int>[225, 190, 25],
    ];

    final double luma = 0.299 * r + 0.587 * g + 0.114 * b;
    if (luma < threshold * 0.58) {
      return 1;
    }

    int best = 0;
    double bestDist = double.infinity;
    for (int i = 0; i < palette.length; i++) {
      final double dr = (r - palette[i][0]).toDouble();
      final double dg = (g - palette[i][1]).toDouble();
      final double db = (b - palette[i][2]).toDouble();
      final double dist = (0.95 * dr * dr) + (1.2 * dg * dg) + (0.9 * db * db);
      if (dist < bestDist) {
        bestDist = dist;
        best = i;
      }
    }
    return best;
  }

  int mapCodeFromClass(int cls) {
    switch (cls) {
      case 1:
        return codeBlack;
      case 2:
        return codeRed;
      case 3:
        return codeYellow;
      default:
        return codeWhite;
    }
  }

  void paintPreviewFromOutputCode(int x, int y, int outputCode) {
    // Panel decode on this tag profile: 0=black, 1=white, 2=yellow, 3=red.
    switch (outputCode & 0x03) {
      case 0:
        preview.setPixelRgb(x, y, 0, 0, 0);
        break;
      case 1:
        preview.setPixelRgb(x, y, 255, 255, 255);
        break;
      case 2:
        preview.setPixelRgb(x, y, 255, 255, 0);
        break;
      default:
        preview.setPixelRgb(x, y, 255, 0, 0);
    }
  }

  if (pipeline == _ApkColorPipeline.fourColorPacked) {
    if (dither && !photoPreserve) {
      final Uint8List vendorClasses = quantizeX1VendorBwry(source);
      for (int y = 0; y < h; y++) {
        for (int x = 0; x < w; x++) {
          final int idx = (y * w) + x;
          final int outputCode = switch (vendorClasses[idx]) {
            x1VendorBlack => codeBlack,
            x1VendorWhite => codeWhite,
            x1VendorRed => codeRed,
            x1VendorYellow => codeYellow,
            _ => codeWhite,
          };
          code4[idx] = outputCode;
          paintPreviewFromOutputCode(x, y, outputCode);
        }
      }
    } else if (dither) {
      final List<double> rr = List<double>.filled(w * h, 0);
      final List<double> gg = List<double>.filled(w * h, 0);
      final List<double> bb = List<double>.filled(w * h, 0);
      for (int y = 0; y < h; y++) {
        for (int x = 0; x < w; x++) {
          final int idx = y * w + x;
          final img.Pixel p = source.getPixel(x, y);
          rr[idx] = p.r.toDouble();
          gg[idx] = p.g.toDouble();
          bb[idx] = p.b.toDouble();
        }
      }

      const List<List<double>> pal = <List<double>>[
        <double>[255, 255, 255],
        <double>[0, 0, 0],
        <double>[255, 0, 0],
        <double>[255, 255, 0],
      ];

      for (int y = 0; y < h; y++) {
        for (int x = 0; x < w; x++) {
          final int idx = y * w + x;
          final int cls = nearest4Palette(
            rr[idx].round().clamp(0, 255),
            gg[idx].round().clamp(0, 255),
            bb[idx].round().clamp(0, 255),
          );
          final int outputCode = mapCodeFromClass(cls);
          code4[idx] = outputCode;
          paintPreviewFromOutputCode(x, y, outputCode);

          final double er = rr[idx] - pal[cls][0];
          final double eg = gg[idx] - pal[cls][1];
          final double eb = bb[idx] - pal[cls][2];

          if (x + 1 < w) {
            final int i1 = idx + 1;
            rr[i1] += er * 7 / 16;
            gg[i1] += eg * 7 / 16;
            bb[i1] += eb * 7 / 16;
          }
          if (y + 1 < h && x > 0) {
            final int i2 = idx + w - 1;
            rr[i2] += er * 3 / 16;
            gg[i2] += eg * 3 / 16;
            bb[i2] += eb * 3 / 16;
          }
          if (y + 1 < h) {
            final int i3 = idx + w;
            rr[i3] += er * 5 / 16;
            gg[i3] += eg * 5 / 16;
            bb[i3] += eb * 5 / 16;
          }
          if (y + 1 < h && x + 1 < w) {
            final int i4 = idx + w + 1;
            rr[i4] += er * 1 / 16;
            gg[i4] += eg * 1 / 16;
            bb[i4] += eb * 1 / 16;
          }
        }
      }
    } else {
      for (int y = 0; y < h; y++) {
        for (int x = 0; x < w; x++) {
          final int idx = y * w + x;
          final img.Pixel p = source.getPixel(x, y);
          final int cls = nearest4Palette(
            p.r.toInt(),
            p.g.toInt(),
            p.b.toInt(),
          );
          final int outputCode = mapCodeFromClass(cls);
          code4[idx] = outputCode;
          paintPreviewFromOutputCode(x, y, outputCode);
        }
      }
    }
  } else {
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        final int idx = y * w + x;
        final img.Pixel p = source.getPixel(x, y);
        final int cls = nearestPalette(p.r.toInt(), p.g.toInt(), p.b.toInt());
        final int whiteCode = 0;
        final int blackCode = 3;
        final int accentCode = 2;
        final int colorCode = cls == 1
            ? blackCode
            : (cls == 2 ? accentCode : whiteCode);

        plane0Bits[idx] = colorCode > 1 ? 1 : 0;
        plane1Bits[idx] = (colorCode & 0x01);

        if (cls == 1) {
          preview.setPixelRgb(x, y, 0, 0, 0);
          code4[idx] = codeBlack;
        } else if (cls == 2) {
          if (accentColor == _AccentColor.red) {
            preview.setPixelRgb(x, y, 255, 0, 0);
          } else {
            preview.setPixelRgb(x, y, 255, 255, 0);
          }
          code4[idx] = accentColor == _AccentColor.red ? codeRed : codeYellow;
        } else {
          preview.setPixelRgb(x, y, 255, 255, 255);
          code4[idx] = codeWhite;
        }
      }
    }
  }

  if (pipeline == _ApkColorPipeline.fourColorPacked) {
    final Uint8List packed = packX1FourColorFrame(
      pixelCodes: code4,
      width: w,
      height: h,
      scanMode: scanMode == _ApkScanMode.vertical
          ? X1EpaperScanMode.vertical
          : X1EpaperScanMode.horizontal,
    );
    final Uint8List previewPng4 = Uint8List.fromList(img.encodePng(preview));
    return _PreparedFrame(previewPng: previewPng4, packed: packed);
  }

  final Uint8List plane0 = scanMode == _ApkScanMode.vertical
      ? _apkColorVerticalScanPack(plane0Bits, w, h)
      : _apkColorHorizontalScanPack(plane0Bits, w, h);
  final Uint8List plane1 = scanMode == _ApkScanMode.vertical
      ? _apkColorVerticalScanPack(plane1Bits, w, h)
      : _apkColorHorizontalScanPack(plane1Bits, w, h);

  final Uint8List previewPng = Uint8List.fromList(img.encodePng(preview));
  return _PreparedFrame(
    previewPng: previewPng,
    packed: plane0,
    packedPlane1: plane1,
  );
}

Uint8List _apk4ColorHorizontalPack(List<int> codes, int width, int height) {
  final int pad = (8 - (width % 8)) % 8;
  final List<int> linear = <int>[];
  for (int y = 0; y < height; y++) {
    final int firstX = width - 1;
    for (int x = width - 1; x >= 0; x--) {
      linear.add(codes[y * width + x] & 0x03);
      if (x == firstX) {
        for (int p = 0; p < pad; p++) {
          linear.add(0);
        }
      }
    }
  }
  return _pack4ColorLinear(linear);
}

Uint8List _apk4ColorVerticalPack(List<int> codes, int width, int height) {
  final int pad = (8 - (height % 8)) % 8;
  final List<int> linear = <int>[];
  for (int x = 0; x < width; x++) {
    for (int y = 0; y < height; y++) {
      linear.add(codes[y * width + x] & 0x03);
    }
    for (int p = 0; p < pad; p++) {
      linear.add(0);
    }
  }
  return _pack4ColorLinear(linear);
}

Uint8List _pack4ColorLinear(List<int> linearCodes) {
  final int outLen = (linearCodes.length / 4).ceil();
  final Uint8List out = Uint8List(outLen);
  int src = 0;
  for (int i = 0; i < outLen; i++) {
    final int c0 = src < linearCodes.length ? linearCodes[src++] & 0x03 : 0;
    final int c1 = src < linearCodes.length ? linearCodes[src++] & 0x03 : 0;
    final int c2 = src < linearCodes.length ? linearCodes[src++] & 0x03 : 0;
    final int c3 = src < linearCodes.length ? linearCodes[src++] & 0x03 : 0;
    out[i] = ((c0 << 6) | (c1 << 4) | (c2 << 2) | c3) & 0xFF;
  }
  return out;
}

Uint8List _apkColorHorizontalScanPack(List<int> bits, int width, int height) {
  final int pad = (8 - (width % 8)) % 8;
  final List<int> linear = <int>[];
  for (int y = 0; y < height; y++) {
    final int firstX = width - 1;
    for (int x = width - 1; x >= 0; x--) {
      linear.add(bits[y * width + x] & 0x01);
      if (x == firstX) {
        for (int p = 0; p < pad; p++) {
          linear.add(0);
        }
      }
    }
  }
  return _packBitLinearMsb(linear);
}

Uint8List _apkColorVerticalScanPack(List<int> bits, int width, int height) {
  final int pad = (8 - (height % 8)) % 8;
  final List<int> linear = <int>[];
  for (int x = 0; x < width; x++) {
    for (int y = 0; y < height; y++) {
      linear.add(bits[y * width + x] & 0x01);
    }
    for (int p = 0; p < pad; p++) {
      linear.add(0);
    }
  }
  return _packBitLinearMsb(linear);
}

Uint8List _packBitLinearMsb(List<int> linearBits) {
  final int outLen = (linearBits.length / 8).ceil();
  final Uint8List out = Uint8List(outLen);
  int src = 0;
  for (int i = 0; i < outLen; i++) {
    int b = 0;
    for (int k = 0; k < 8; k++) {
      final int bit = src < linearBits.length ? linearBits[src] : 0;
      b |= (bit & 0x01) << (7 - k);
      src++;
    }
    out[i] = b;
  }
  return out;
}

void _writePackedBit(
  Uint8List packed,
  int pixelIndex,
  bool black, {
  required bool lsbFirst,
}) {
  if (!black) {
    return;
  }

  final int byteIndex = pixelIndex >> 3;
  final int bitIndex = lsbFirst ? (pixelIndex & 7) : (7 - (pixelIndex & 7));
  packed[byteIndex] |= (1 << bitIndex);
}
