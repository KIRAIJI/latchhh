import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import '../../../application/latch_controller.dart';
import '../../../core/services/google_oauth_service.dart';
import '../../../core/validation/input_validation.dart';
import '../../../data/api/latch_api.dart';
import '../login/presentation/login_screen.dart';
import '../register/presentation/register_screen.dart';
import 'forgot_password_screen.dart';
import '../../legal/presentation/legal_document_screen.dart';

class AuthUiFlow extends StatefulWidget {
  const AuthUiFlow({
    super.key,
    required this.controller,
    required this.onEnterMainApp,
  });

  final LatchController controller;
  final VoidCallback onEnterMainApp;

  @override
  State<AuthUiFlow> createState() => _AuthUiFlowState();
}

class _AuthUiFlowState extends State<AuthUiFlow> {
  bool _showRegister = false;
  bool _isLoading = false;
  String? _errorMessage;
  Map<String, List<String>> _fieldErrors = const {};
  Timer? _loginRetryTimer;
  int _loginRetryAfterSeconds = 0;

  @override
  void dispose() {
    _loginRetryTimer?.cancel();
    super.dispose();
  }

  void _startLoginRetryCountdown(int seconds) {
    _loginRetryTimer?.cancel();
    setState(() => _loginRetryAfterSeconds = seconds < 1 ? 1 : seconds);
    _loginRetryTimer = Timer.periodic(const Duration(seconds: 1), (timer) {
      if (!mounted) {
        timer.cancel();
        return;
      }
      if (_loginRetryAfterSeconds <= 1) {
        timer.cancel();
        setState(() => _loginRetryAfterSeconds = 0);
        return;
      }
      setState(() => _loginRetryAfterSeconds--);
    });
  }

  String? _fieldError(String field) {
    final errors = _fieldErrors[field];
    return errors == null || errors.isEmpty ? null : errors.first;
  }

  void _openLogin() => setState(() {
    _showRegister = false;
    _errorMessage = null;
    _fieldErrors = const {};
  });

  void _openRegister() => setState(() {
    _showRegister = true;
    _errorMessage = null;
    _fieldErrors = const {};
  });

  void _openForgotPassword() {
    Navigator.of(context).push<void>(
      MaterialPageRoute<void>(
        builder: (_) => ForgotPasswordScreen(controller: widget.controller),
      ),
    );
  }

  void _openLegalDocument(LegalDocumentType type) {
    Navigator.of(context).push<void>(
      MaterialPageRoute<void>(builder: (_) => LegalDocumentScreen(type: type)),
    );
  }

  Future<void> _login(String email, String password) async {
    if (_loginRetryAfterSeconds > 0) return;

    final normalizedEmail = email.trim();
    final errors = <String, List<String>>{};
    final emailError = InputValidation.email(normalizedEmail);
    final passwordError = InputValidation.requiredPassword(password);
    if (emailError != null) errors['email'] = [emailError];
    if (passwordError != null) errors['password'] = [passwordError];
    if (errors.isNotEmpty) {
      setState(() {
        _errorMessage = null;
        _fieldErrors = errors;
      });
      return;
    }

    setState(() {
      _isLoading = true;
      _errorMessage = null;
      _fieldErrors = const {};
    });
    try {
      await widget.controller.login(email: normalizedEmail, password: password);
      if (mounted) widget.onEnterMainApp();
    } on LatchApiException catch (error) {
      if (!mounted) return;
      setState(() {
        _errorMessage = error.formMessage;
        _fieldErrors = error.errors;
      });
      final retryAfter = error.statusCode == 429
          ? error.retryAfterSeconds
          : null;
      if (retryAfter != null) {
        _startLoginRetryCountdown(retryAfter);
      }
    } on Object {
      if (!mounted) return;
      setState(() {
        _errorMessage =
            'Could not reach the LATCH server. Check your connection.';
      });
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  Future<void> _googleSignIn({
    bool acceptedTerms = true,
    bool acknowledgedPrivacy = true,
  }) async {
    final errors = <String, List<String>>{};
    if (!acceptedTerms) {
      errors['accepted_terms'] = ['You must accept the Terms of Service.'];
    }
    if (!acknowledgedPrivacy) {
      errors['acknowledged_privacy'] = [
        'You must acknowledge the Privacy Policy.',
      ];
    }
    if (errors.isNotEmpty) {
      setState(() {
        _errorMessage = null;
        _fieldErrors = errors;
      });
      return;
    }

    setState(() {
      _isLoading = true;
      _errorMessage = null;
      _fieldErrors = const {};
    });
    try {
      await widget.controller.loginWithGoogle();
      if (mounted) widget.onEnterMainApp();
    } on GoogleOAuthException catch (error) {
      if (!mounted || error.canceled) return;
      setState(() => _errorMessage = error.message);
    } on LatchApiException catch (error) {
      if (!mounted) return;
      setState(() {
        _errorMessage = error.formMessage;
        _fieldErrors = error.errors;
      });
    } on TimeoutException {
      if (!mounted) return;
      setState(() {
        _errorMessage = kDebugMode
            ? 'Google account verified, but LATCH did not respond. '
                  'Start Apache and MySQL, then try again.'
            : 'Google account verified. Check your internet connection, then '
                  'try again.';
      });
    } on http.ClientException {
      if (!mounted) return;
      setState(() {
        _errorMessage = kDebugMode
            ? 'Google account verified, but LATCH is unavailable. '
                  'Start Apache and MySQL, then try again.'
            : 'Google account verified. Check Wi-Fi or mobile data, then try '
                  'again.';
      });
    } on Object {
      if (!mounted) return;
      setState(() {
        _errorMessage =
            'Google account could not be linked to LATCH. Please try again.';
      });
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  Future<void> _register(
    String name,
    String email,
    String password,
    String passwordConfirmation,
    bool acceptedTerms,
    bool acknowledgedPrivacy,
  ) async {
    final normalizedName = name.trim();
    final normalizedEmail = email.trim();
    final errors = <String, List<String>>{};
    final nameError = InputValidation.name(normalizedName);
    final emailError = InputValidation.email(normalizedEmail);
    final passwordError = InputValidation.newPassword(password);
    final confirmationError = InputValidation.passwordConfirmation(
      password,
      passwordConfirmation,
    );
    if (nameError != null) errors['name'] = [nameError];
    if (emailError != null) errors['email'] = [emailError];
    if (passwordError != null) errors['password'] = [passwordError];
    if (confirmationError != null) {
      errors['password_confirmation'] = [confirmationError];
    }
    if (!acceptedTerms) {
      errors['accepted_terms'] = ['You must accept the Terms of Service.'];
    }
    if (!acknowledgedPrivacy) {
      errors['acknowledged_privacy'] = [
        'You must acknowledge the Privacy Policy.',
      ];
    }
    if (errors.isNotEmpty) {
      setState(() {
        _errorMessage = null;
        _fieldErrors = errors;
      });
      return;
    }

    setState(() {
      _isLoading = true;
      _errorMessage = null;
      _fieldErrors = const {};
    });
    try {
      await widget.controller.register(
        name: normalizedName,
        email: normalizedEmail,
        password: password,
        passwordConfirmation: passwordConfirmation,
        acceptedTerms: acceptedTerms,
        acknowledgedPrivacy: acknowledgedPrivacy,
      );
      if (mounted) widget.onEnterMainApp();
    } on LatchApiException catch (error) {
      if (!mounted) return;
      setState(() {
        _errorMessage = error.formMessage;
        _fieldErrors = error.errors;
      });
    } on Object {
      if (!mounted) return;
      setState(() {
        _errorMessage =
            'Could not reach the LATCH server. Check your connection.';
      });
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return AnimatedSwitcher(
      duration: const Duration(milliseconds: 280),
      switchInCurve: Curves.easeOutCubic,
      switchOutCurve: Curves.easeInCubic,
      transitionBuilder: (child, animation) {
        return FadeTransition(opacity: animation, child: child);
      },
      child: KeyedSubtree(
        key: ValueKey<bool>(_showRegister),
        child: _showRegister
            ? RegisterScreen(
                onRegisterPressed: _register,
                onGooglePressed: (acceptedTerms, acknowledgedPrivacy) =>
                    _googleSignIn(
                      acceptedTerms: acceptedTerms,
                      acknowledgedPrivacy: acknowledgedPrivacy,
                    ),
                onSignInPressed: _openLogin,
                onTermsPressed: () =>
                    _openLegalDocument(LegalDocumentType.terms),
                onPrivacyPressed: () =>
                    _openLegalDocument(LegalDocumentType.privacy),
                showGoogleSignIn: widget.controller.isGoogleSignInAvailable,
                isLoading: _isLoading,
                errorMessage: _errorMessage,
                nameError: _fieldError('name'),
                emailError: _fieldError('email'),
                passwordError: _fieldError('password'),
                passwordConfirmationError: _fieldError('password_confirmation'),
                termsError: _fieldError('accepted_terms'),
                privacyError: _fieldError('acknowledged_privacy'),
              )
            : LoginScreen(
                onLoginPressed: _login,
                onGooglePressed: _googleSignIn,
                onRegisterPressed: _openRegister,
                onForgotPasswordPressed: _openForgotPassword,
                onTermsPressed: () =>
                    _openLegalDocument(LegalDocumentType.terms),
                onPrivacyPressed: () =>
                    _openLegalDocument(LegalDocumentType.privacy),
                showGoogleSignIn: widget.controller.isGoogleSignInAvailable,
                isLoading: _isLoading,
                errorMessage: _errorMessage,
                emailError: _fieldError('email'),
                passwordError: _fieldError('password'),
                retryAfterSeconds: _loginRetryAfterSeconds,
              ),
      ),
    );
  }
}
