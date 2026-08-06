import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:google_sign_in/google_sign_in.dart';

import 'firebase_bootstrap.dart';

class GoogleOAuthException implements Exception {
  const GoogleOAuthException(this.message, {this.canceled = false});

  final String message;
  final bool canceled;

  @override
  String toString() => message;
}

abstract interface class GoogleOAuthService {
  bool get isAvailable;

  Future<String> signIn();

  Future<String> reauthenticate();

  Future<void> signOut();
}

class FirebaseGoogleOAuthService implements GoogleOAuthService {
  FirebaseGoogleOAuthService({
    FirebaseAuth? firebaseAuth,
    GoogleSignIn? googleSignIn,
  }) : _firebaseAuth = firebaseAuth,
       _googleSignIn = googleSignIn ?? GoogleSignIn.instance;

  final FirebaseAuth? _firebaseAuth;
  final GoogleSignIn _googleSignIn;
  bool _initialized = false;

  FirebaseAuth get _auth => _firebaseAuth ?? FirebaseAuth.instance;

  @override
  bool get isAvailable =>
      FirebaseBootstrap.enabled && Firebase.apps.isNotEmpty && !kIsWeb;

  Future<void> _initialize() async {
    if (!isAvailable) {
      throw const GoogleOAuthException(
        'Google sign-in is not available in this app build.',
      );
    }
    if (_initialized) return;

    await _googleSignIn.initialize();
    _initialized = true;
    if (!_googleSignIn.supportsAuthenticate()) {
      throw const GoogleOAuthException(
        'Google sign-in is not supported on this device.',
      );
    }
  }

  Future<AuthCredential> _googleCredential() async {
    await _initialize();
    final account = await _googleSignIn.authenticate();
    final idToken = account.authentication.idToken;
    if (idToken == null || idToken.isEmpty) {
      throw const GoogleOAuthException(
        'Google did not return a valid sign-in credential.',
      );
    }

    return GoogleAuthProvider.credential(idToken: idToken);
  }

  @override
  Future<String> signIn() async {
    try {
      final result = await _auth.signInWithCredential(
        await _googleCredential(),
      );
      return _firebaseIdToken(result.user);
    } on GoogleSignInException catch (error) {
      throw _googleError(error);
    } on FirebaseAuthException catch (error) {
      throw GoogleOAuthException(_firebaseMessage(error));
    } on FirebaseException catch (error, stackTrace) {
      _logUnexpected(error, stackTrace);
      throw GoogleOAuthException(_firebaseCoreMessage(error));
    } on PlatformException catch (error, stackTrace) {
      _logUnexpected(error, stackTrace);
      throw GoogleOAuthException(_platformMessage(error));
    } on Object catch (error, stackTrace) {
      _logUnexpected(error, stackTrace);
      throw GoogleOAuthException(
        'Google sign-in could not be completed on this device. '
        'Check its Google account and try again.',
      );
    }
  }

  @override
  Future<String> reauthenticate() async {
    try {
      await _initialize();
      await _googleSignIn.signOut();
      final credential = await _googleCredential();
      final currentUser = _auth.currentUser;
      final result = currentUser == null
          ? await _auth.signInWithCredential(credential)
          : await currentUser.reauthenticateWithCredential(credential);
      return _firebaseIdToken(result.user);
    } on GoogleSignInException catch (error) {
      throw _googleError(error);
    } on FirebaseAuthException catch (error) {
      throw GoogleOAuthException(_firebaseMessage(error));
    } on FirebaseException catch (error, stackTrace) {
      _logUnexpected(error, stackTrace);
      throw GoogleOAuthException(_firebaseCoreMessage(error));
    } on PlatformException catch (error, stackTrace) {
      _logUnexpected(error, stackTrace);
      throw GoogleOAuthException(_platformMessage(error));
    } on Object catch (error, stackTrace) {
      _logUnexpected(error, stackTrace);
      throw GoogleOAuthException(
        'Google verification could not be completed on this device. '
        'Check its Google account and try again.',
      );
    }
  }

  Future<String> _firebaseIdToken(User? user) async {
    final token = await user?.getIdToken(true);
    if (token == null || token.isEmpty) {
      throw const GoogleOAuthException(
        'Firebase did not return a valid sign-in credential.',
      );
    }
    return token;
  }

  GoogleOAuthException _googleError(GoogleSignInException error) {
    return switch (error.code) {
      GoogleSignInExceptionCode.canceled ||
      GoogleSignInExceptionCode.interrupted => const GoogleOAuthException(
        'Google sign-in was canceled.',
        canceled: true,
      ),
      GoogleSignInExceptionCode.clientConfigurationError ||
      GoogleSignInExceptionCode.providerConfigurationError =>
        const GoogleOAuthException(
          'Google sign-in is not configured correctly for this app.',
        ),
      GoogleSignInExceptionCode.uiUnavailable => const GoogleOAuthException(
        'Google sign-in could not open. Please try again.',
      ),
      GoogleSignInExceptionCode.userMismatch => const GoogleOAuthException(
        'Please choose the Google account linked to this LATCH account.',
      ),
      _ => GoogleOAuthException(
        error.description ?? 'Google sign-in failed. Please try again.',
      ),
    };
  }

  String _firebaseMessage(FirebaseAuthException error) {
    return switch (error.code) {
      'account-exists-with-different-credential' =>
        'This email already uses another sign-in method.',
      'network-request-failed' =>
        'Could not reach Google. Check your connection and try again.',
      'user-mismatch' =>
        'Please choose the Google account linked to this LATCH account.',
      'user-disabled' => 'This account has been disabled.',
      _ => error.message ?? 'Google sign-in failed. Please try again.',
    };
  }

  String _firebaseCoreMessage(FirebaseException error) {
    return switch (error.code) {
      'operation-not-allowed' =>
        'Google sign-in is not enabled for this Firebase project.',
      'invalid-api-key' || 'app-not-authorized' =>
        'This app is not authorized by Firebase. Check its Android app configuration.',
      'network-request-failed' =>
        'Could not reach Firebase. Check your connection and try again.',
      _ =>
        error.message ??
            'Firebase could not complete Google sign-in. Please try again.',
    };
  }

  String _platformMessage(PlatformException error) {
    final details = '${error.message ?? ''} ${error.details ?? ''}'
        .toLowerCase();
    if (error.code == 'network_error' || details.contains('network')) {
      return 'Could not reach Google. Check your connection and try again.';
    }
    if (error.code == 'sign_in_canceled' ||
        error.code == '12501' ||
        details.contains('canceled')) {
      return 'Google sign-in was canceled.';
    }
    if (error.code == 'sign_in_failed' &&
        (details.contains('10') || details.contains('developer_error'))) {
      return 'Google sign-in is not configured correctly for this app.';
    }
    return error.message?.trim().isNotEmpty == true
        ? error.message!.trim()
        : 'Google sign-in could not be completed on this device. '
              'Check its Google account and try again.';
  }

  void _logUnexpected(Object error, StackTrace stackTrace) {
    debugPrint(
      'Google OAuth ${error.runtimeType}: $error\n'
      '${stackTrace.toString().split('\n').take(8).join('\n')}',
    );
  }

  @override
  Future<void> signOut() async {
    if (!FirebaseBootstrap.enabled || Firebase.apps.isEmpty) return;

    try {
      await Future.wait<void>([
        _auth.signOut(),
        if (_initialized) _googleSignIn.signOut(),
      ]);
    } on Object {
      // A stale provider session must not block the local LATCH sign-out.
    }
  }
}
