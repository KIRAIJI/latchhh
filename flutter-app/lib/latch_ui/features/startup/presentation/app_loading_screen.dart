import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import '../opening_gif_duration.dart';
import '../../onboarding/data/onboarding_storage.dart';

/// Opening screen — shows the LATCH GIF while startup checks run.
class AppLoadingScreen extends StatefulWidget {
  const AppLoadingScreen({super.key, required this.onFinished});

  /// Called after onboarding is resolved and the GIF finishes one full loop.
  final FutureOr<void> Function(bool onboardingCompleted) onFinished;

  /// Matches the splash exit fade and onboarding/login crossfade.
  static const Duration exitFadeDuration = Duration(milliseconds: 720);
  static const Duration maximumPlaybackDuration = Duration(seconds: 6);

  @override
  State<AppLoadingScreen> createState() => _AppLoadingScreenState();
}

class _AppLoadingScreenState extends State<AppLoadingScreen>
    with SingleTickerProviderStateMixin {
  OpeningGifTiming? _timing;
  int _maxFrameIndex = -1;
  bool _startupStarted = false;
  bool _startupFinished = false;

  late final AnimationController _exitFadeController;
  late final Animation<double> _contentOpacity;

  @override
  void initState() {
    super.initState();
    _exitFadeController = AnimationController(
      vsync: this,
      duration: AppLoadingScreen.exitFadeDuration,
    );
    _contentOpacity = Tween<double>(begin: 1, end: 0).animate(
      CurvedAnimation(
        parent: _exitFadeController,
        curve: Curves.easeInOutCubic,
      ),
    );
    unawaited(_prepareStartup());
  }

  @override
  void dispose() {
    _exitFadeController.dispose();
    super.dispose();
  }

  Future<void> _prepareStartup() async {
    _timing = await OpeningGifTiming.resolve();
    if (!mounted || _startupStarted) return;

    // Start only after the splash is on screen.
    WidgetsBinding.instance.addPostFrameCallback((_) {
      if (!mounted || _startupStarted) return;
      unawaited(_runStartup());
    });
  }

  Future<void> _runStartup() async {
    if (_startupStarted || _timing == null) return;
    _startupStarted = true;

    final timing = _timing!;

    late final bool onboardingCompleted;
    await Future.wait<void>([
      _waitForGifPlayback(timing),
      OnboardingStorage.isCompleted().then(
        (value) => onboardingCompleted = value,
      ),
    ]);

    if (!mounted || _startupFinished) return;
    await widget.onFinished(onboardingCompleted);
    if (!mounted) return;
    _startupFinished = true;
    unawaited(_exitFadeController.forward());
  }

  Future<void> _waitForGifPlayback(OpeningGifTiming timing) async {
    final playbackBudget =
        timing.totalDuration < AppLoadingScreen.maximumPlaybackDuration
        ? timing.totalDuration
        : AppLoadingScreen.maximumPlaybackDuration;

    await _waitForLastFrameHold(
      timing,
    ).timeout(playbackBudget, onTimeout: () {});
  }

  Future<void> _waitForLastFrameHold(OpeningGifTiming timing) async {
    while (mounted && _maxFrameIndex < timing.lastFrameIndex) {
      await Future<void>.delayed(const Duration(milliseconds: 16));
    }
    await Future<void>.delayed(
      Duration(milliseconds: timing.durationMsForFrame(timing.lastFrameIndex)),
    );
  }

  void _trackFrame(int? frame) {
    if (frame == null) return;
    if (frame > _maxFrameIndex) {
      _maxFrameIndex = frame;
    }
  }

  @override
  Widget build(BuildContext context) {
    return AnnotatedRegion<SystemUiOverlayStyle>(
      value: SystemUiOverlayStyle.light.copyWith(
        statusBarColor: Colors.transparent,
        systemNavigationBarColor: Colors.black,
        systemNavigationBarIconBrightness: Brightness.light,
      ),
      child: PopScope(
        canPop: false,
        child: Scaffold(
          backgroundColor: Colors.black,
          body: ColoredBox(
            color: Colors.black,
            child: LayoutBuilder(
              builder: (context, constraints) {
                final maxWidth = constraints.maxWidth * 0.92;
                final maxHeight = constraints.maxHeight * 0.92;

                return FadeTransition(
                  opacity: _contentOpacity,
                  child: Center(
                    child: ConstrainedBox(
                      constraints: BoxConstraints(
                        maxWidth: maxWidth,
                        maxHeight: maxHeight,
                      ),
                      child: Image.asset(
                        OpeningGifTiming.assetPath,
                        fit: BoxFit.contain,
                        gaplessPlayback: true,
                        filterQuality: FilterQuality.medium,
                        frameBuilder:
                            (context, child, frame, wasSynchronouslyLoaded) {
                              _trackFrame(frame);
                              return child;
                            },
                      ),
                    ),
                  ),
                );
              },
            ),
          ),
        ),
      ),
    );
  }
}
