import 'dart:async';

import 'package:app_links/app_links.dart';
import 'package:flutter/material.dart';

import '../application/latch_controller.dart';
import '../core/constants/app_strings.dart';
import '../core/theme/app_theme.dart';
import '../core/services/push_notification_service.dart';
import '../data/api/latch_api.dart';
import '../features/auth/presentation/auth_ui_flow.dart';
import '../features/auth/presentation/email_verification_screen.dart';
import '../features/auth/presentation/reset_password_screen.dart';
import '../features/onboarding/data/onboarding_storage.dart';
import '../features/onboarding/presentation/onboarding_screen.dart';
import '../features/startup/presentation/app_loading_screen.dart';
import 'latch_integrated_shell.dart';

enum _AppPhase {
  configurationError,
  loading,
  onboarding,
  auth,
  emailVerification,
  main,
}

/// Production entry point for the integrated LATCH application.
class LatchIntegratedApp extends StatefulWidget {
  const LatchIntegratedApp({
    super.key,
    this.pushNotifications,
    this.initialOnboardingCompleted,
  });

  final PushNotificationService? pushNotifications;
  final bool? initialOnboardingCompleted;

  @override
  State<LatchIntegratedApp> createState() => _LatchIntegratedAppState();
}

class _LatchIntegratedAppState extends State<LatchIntegratedApp>
    with WidgetsBindingObserver {
  late final LatchController _controller;
  late final AppLinks _appLinks;
  late final String? _configurationError;
  final _scaffoldMessengerKey = GlobalKey<ScaffoldMessengerState>();
  final _navigatorKey = GlobalKey<NavigatorState>();
  StreamSubscription<Uri>? _linkSubscription;
  String? _lastHandledLink;
  bool _emailVerificationLinkPending = false;
  bool _verificationCheckInProgress = false;
  _AppPhase _phase = _AppPhase.loading;

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
    _configurationError = LatchApiConfig.releaseConfigurationError;
    _controller = LatchController(
      pushNotifications: widget.pushNotifications,
      onSessionExpired: _handleSessionExpired,
    );
    widget.pushNotifications
      ?..onNotificationTapped = _controller.openNotificationFromPush
      ..onForegroundMessage = _controller.refreshNotifications;
    _appLinks = AppLinks();
    unawaited(_initializeLinks());
    if (_configurationError != null) {
      _phase = _AppPhase.configurationError;
    } else if (widget.initialOnboardingCompleted != null) {
      WidgetsBinding.instance.addPostFrameCallback((_) {
        unawaited(_handleLoadingFinished(widget.initialOnboardingCompleted!));
      });
    }
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    _linkSubscription?.cancel();
    widget.pushNotifications
      ?..onNotificationTapped = null
      ..onForegroundMessage = null;
    _controller.dispose();
    super.dispose();
  }

  Future<void> _handleLoadingFinished(bool onboardingCompleted) async {
    final authenticated = await _controller.restoreSession();
    if (!mounted) return;
    setState(() {
      _phase = !onboardingCompleted
          ? _AppPhase.onboarding
          : (authenticated ? _authenticatedPhase : _AppPhase.auth);
    });
    if (authenticated && _emailVerificationLinkPending) {
      unawaited(_checkEmailVerification(showResult: true));
    }
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    if (state == AppLifecycleState.resumed &&
        _phase == _AppPhase.emailVerification &&
        _controller.isAuthenticated) {
      unawaited(
        _checkEmailVerification(showResult: _emailVerificationLinkPending),
      );
    }
  }

  Future<void> _finishOnboarding() async {
    await OnboardingStorage.markCompleted();
    if (!mounted) return;
    setState(
      () => _phase = _controller.isAuthenticated
          ? _authenticatedPhase
          : _AppPhase.auth,
    );
  }

  _AppPhase get _authenticatedPhase => _controller.user?.emailVerified == true
      ? _AppPhase.main
      : _AppPhase.emailVerification;

  void _enterAuthenticatedApp() {
    setState(() => _phase = _authenticatedPhase);
  }

  void _returnToAuth() {
    if (!mounted) return;
    setState(() => _phase = _AppPhase.auth);
  }

  void _handleSessionExpired() {
    if (!mounted) return;
    setState(() => _phase = _AppPhase.auth);
    WidgetsBinding.instance.addPostFrameCallback((_) {
      _scaffoldMessengerKey.currentState
        ?..hideCurrentSnackBar()
        ..showSnackBar(
          const SnackBar(
            content: Text('Your session expired. Please sign in again.'),
          ),
        );
    });
  }

  Future<void> _initializeLinks() async {
    try {
      final initial = await _appLinks.getInitialLink();
      if (initial != null) _handleLink(initial);
      _linkSubscription = _appLinks.uriLinkStream.listen(
        _handleLink,
        onError: (_) {},
      );
    } on Object {
      // Deep links remain optional during local development.
    }
  }

  void _handleLink(Uri uri) {
    if (uri.scheme != 'latch' || _lastHandledLink == uri.toString()) return;
    _lastHandledLink = uri.toString();

    if (uri.host == 'email-verified') {
      _emailVerificationLinkPending = true;
      if (_controller.isAuthenticated) {
        unawaited(_checkEmailVerification(showResult: true));
      }
      return;
    }
    if (uri.host != 'reset-password') return;

    final token = uri.queryParameters['token'] ?? '';
    final email = uri.queryParameters['email'] ?? '';
    final isPasswordSetup = uri.queryParameters['mode'] == 'setup';
    if (token.isEmpty || email.isEmpty) return;

    WidgetsBinding.instance.addPostFrameCallback((_) {
      _navigatorKey.currentState?.push<void>(
        MaterialPageRoute<void>(
          builder: (_) => ResetPasswordScreen(
            controller: _controller,
            email: email,
            token: token,
            isPasswordSetup: isPasswordSetup,
          ),
        ),
      );
    });
  }

  Future<void> _checkEmailVerification({bool showResult = false}) async {
    if (_verificationCheckInProgress || !_controller.isAuthenticated) return;
    _verificationCheckInProgress = true;
    try {
      await _controller.refreshProfile();
      if (_controller.user?.emailVerified == true) {
        _emailVerificationLinkPending = false;
        await _controller.refreshDashboard();
        if (mounted) setState(() => _phase = _AppPhase.main);
        if (showResult) {
          _scaffoldMessengerKey.currentState?.showSnackBar(
            const SnackBar(content: Text('Email verified successfully.')),
          );
        }
      } else if (showResult) {
        _emailVerificationLinkPending = false;
        _scaffoldMessengerKey.currentState?.showSnackBar(
          const SnackBar(
            content: Text('The verification link is invalid or expired.'),
          ),
        );
      }
    } on Object {
      if (showResult) {
        _scaffoldMessengerKey.currentState?.showSnackBar(
          const SnackBar(content: Text('Could not verify the email yet.')),
        );
      }
    } finally {
      _verificationCheckInProgress = false;
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: AppStrings.appName,
      scaffoldMessengerKey: _scaffoldMessengerKey,
      navigatorKey: _navigatorKey,
      debugShowCheckedModeBanner: false,
      // Black root scaffold keeps the splash-to-app crossfade smooth.
      theme: AppTheme.light.copyWith(scaffoldBackgroundColor: Colors.black),
      home: AnimatedSwitcher(
        duration: AppLoadingScreen.exitFadeDuration,
        reverseDuration: AppLoadingScreen.exitFadeDuration,
        switchInCurve: Curves.easeOutCubic,
        switchOutCurve: Curves.easeInCubic,
        layoutBuilder: (currentChild, previousChildren) {
          return Stack(
            fit: StackFit.expand,
            children: [...previousChildren, ?currentChild],
          );
        },
        transitionBuilder: (child, animation) {
          return FadeTransition(
            opacity: CurvedAnimation(
              parent: animation,
              curve: Curves.easeOutCubic,
            ),
            child: child,
          );
        },
        child: KeyedSubtree(
          key: ValueKey<_AppPhase>(_phase),
          child: _buildPhase(),
        ),
      ),
    );
  }

  Widget _buildPhase() {
    return switch (_phase) {
      _AppPhase.configurationError => const _ConfigurationErrorScreen(),
      _AppPhase.loading =>
        widget.initialOnboardingCompleted == null
            ? AppLoadingScreen(onFinished: _handleLoadingFinished)
            : const Scaffold(
                backgroundColor: Colors.black,
                body: Center(
                  child: CircularProgressIndicator(color: Colors.white),
                ),
              ),
      _AppPhase.onboarding => OnboardingScreen(onFinished: _finishOnboarding),
      _AppPhase.auth => AuthUiFlow(
        controller: _controller,
        onEnterMainApp: _enterAuthenticatedApp,
      ),
      _AppPhase.emailVerification => EmailVerificationScreen(
        controller: _controller,
        onVerified: _checkEmailVerification,
        onSignedOut: _returnToAuth,
      ),
      _AppPhase.main => LatchIntegratedShell(
        controller: _controller,
        onSignedOut: _returnToAuth,
      ),
    };
  }
}

class _ConfigurationErrorScreen extends StatelessWidget {
  const _ConfigurationErrorScreen();

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      backgroundColor: Color(0xFFF7F8FA),
      body: SafeArea(
        child: Center(
          child: Padding(
            padding: EdgeInsets.all(24),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                Icon(
                  Icons.security_rounded,
                  size: 44,
                  color: Color(0xFFB42318),
                ),
                SizedBox(height: 12),
                Text(
                  'App configuration is incomplete',
                  textAlign: TextAlign.center,
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.w700),
                ),
                SizedBox(height: 8),
                Text(
                  'This release cannot connect securely. Please contact LATCH support.',
                  textAlign: TextAlign.center,
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
