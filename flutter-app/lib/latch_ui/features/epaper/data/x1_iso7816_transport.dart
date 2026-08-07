import 'package:flutter/foundation.dart';
import 'package:nfc_manager/nfc_manager.dart';
import 'package:nfc_manager/nfc_manager_android.dart';
import 'package:nfc_manager/nfc_manager_ios.dart';

abstract interface class X1Iso7816Transport {
  String get platformName;

  String get tagDescription;

  Future<void> configureTimeout(int timeoutMs);

  Future<int> getMaxTransceiveLength();

  Future<Uint8List> transceive(Uint8List command);

  static X1Iso7816Transport? fromTag(NfcTag tag, {TargetPlatform? platform}) {
    switch (platform ?? defaultTargetPlatform) {
      case TargetPlatform.android:
        final IsoDepAndroid? isoDep = IsoDepAndroid.from(tag);
        return isoDep == null ? null : _AndroidIsoDepTransport(isoDep, tag);
      case TargetPlatform.iOS:
        final Iso7816Ios? iso7816 = Iso7816Ios.from(tag);
        if (iso7816 != null) {
          return _IosIso7816Transport(iso7816);
        }
        final MiFareIos? miFare = MiFareIos.from(tag);
        return miFare == null ? null : _IosMiFareIso7816Transport(miFare);
      case TargetPlatform.fuchsia:
      case TargetPlatform.linux:
      case TargetPlatform.macOS:
      case TargetPlatform.windows:
        return null;
    }
  }
}

final class _AndroidIsoDepTransport implements X1Iso7816Transport {
  const _AndroidIsoDepTransport(this._isoDep, this._tag);

  final IsoDepAndroid _isoDep;
  final NfcTag _tag;

  @override
  String get platformName => 'Android IsoDep';

  @override
  String get tagDescription {
    final NfcTagAndroid? androidTag = NfcTagAndroid.from(_tag);
    return androidTag?.techList.join(', ') ?? platformName;
  }

  @override
  Future<void> configureTimeout(int timeoutMs) async {
    final dynamic isoDep = _isoDep;
    await isoDep.setTimeout(timeoutMs);
  }

  @override
  Future<int> getMaxTransceiveLength() => _isoDep.getMaxTransceiveLength();

  @override
  Future<Uint8List> transceive(Uint8List command) =>
      _isoDep.transceive(command);
}

final class _IosIso7816Transport implements X1Iso7816Transport {
  const _IosIso7816Transport(this._iso7816);

  static const int _coreNfcApduLimit = 261;

  final Iso7816Ios _iso7816;

  @override
  String get platformName => 'iOS Core NFC ISO 7816';

  @override
  String get tagDescription => platformName;

  @override
  Future<void> configureTimeout(int timeoutMs) async {
    // Core NFC owns the reader-session timeout and does not expose an
    // Android-style per-transceive timeout.
  }

  @override
  Future<int> getMaxTransceiveLength() async => _coreNfcApduLimit;

  @override
  Future<Uint8List> transceive(Uint8List command) async {
    final Iso7816ResponseApduIos response = await _iso7816.sendCommandRaw(
      data: command,
    );
    return encodeIosIso7816Response(
      response.payload,
      response.statusWord1,
      response.statusWord2,
    );
  }
}

final class _IosMiFareIso7816Transport implements X1Iso7816Transport {
  const _IosMiFareIso7816Transport(this._miFare);

  static const int _coreNfcApduLimit = 261;

  final MiFareIos _miFare;

  @override
  String get platformName => 'iOS Core NFC MiFare ISO 7816';

  @override
  String get tagDescription => '$platformName (${_miFare.mifareFamily.name})';

  @override
  Future<void> configureTimeout(int timeoutMs) async {
    // Core NFC owns the reader-session timeout.
  }

  @override
  Future<int> getMaxTransceiveLength() async => _coreNfcApduLimit;

  @override
  Future<Uint8List> transceive(Uint8List command) async {
    final Iso7816ResponseApduIos response = await _miFare
        .sendMiFareIso7816CommandRaw(data: command);
    return encodeIosIso7816Response(
      response.payload,
      response.statusWord1,
      response.statusWord2,
    );
  }
}

@visibleForTesting
Uint8List encodeIosIso7816Response(
  Uint8List payload,
  int statusWord1,
  int statusWord2,
) {
  if (statusWord1 < 0 ||
      statusWord1 > 0xff ||
      statusWord2 < 0 ||
      statusWord2 > 0xff) {
    throw RangeError('ISO 7816 status bytes must be between 0 and 255.');
  }
  return Uint8List.fromList(<int>[...payload, statusWord1, statusWord2]);
}
