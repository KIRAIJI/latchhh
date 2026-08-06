import 'dart:async';

import 'package:flutter/material.dart';

import '../../../../../main.dart';
import '../../../../core/components/buttons/latch_button.dart';
import '../../../../core/theme/app_colors.dart';
import '../../../../core/theme/app_icons.dart';
import '../../../../core/theme/app_radius.dart';
import '../../../../core/theme/app_spacing.dart';
import 'epaper_status_banner.dart';

/// Slide-up modal shown while waiting for an NFC tap to write the E-Paper image.
class EpaperWriteModal extends StatefulWidget {
  const EpaperWriteModal({
    super.key,
    required this.bridge,
    required this.onClose,
  });

  final UploaderBridge bridge;
  final VoidCallback onClose;

  @override
  State<EpaperWriteModal> createState() => _EpaperWriteModalState();
}

class _EpaperWriteModalState extends State<EpaperWriteModal>
    with SingleTickerProviderStateMixin {
  late final AnimationController _pulseController;
  bool _writeStarted = false;
  bool _cancelling = false;

  @override
  void initState() {
    super.initState();
    _pulseController = AnimationController(
      vsync: this,
      duration: const Duration(milliseconds: 1400),
    )..repeat();
    widget.bridge.addListener(_onBridgeChanged);
    WidgetsBinding.instance.addPostFrameCallback((_) {
      _beginWrite();
    });
  }

  @override
  void dispose() {
    if (widget.bridge.busy) {
      unawaited(widget.bridge.requestCancelWriteTag());
    }
    widget.bridge.removeListener(_onBridgeChanged);
    _pulseController.dispose();
    super.dispose();
  }

  void _onBridgeChanged() {
    if (!mounted) {
      return;
    }

    if (widget.bridge.busy) {
      _writeStarted = true;
    }
    setState(() {});
  }

  Future<void> _beginWrite() async {
    await widget.bridge.requestWriteTag();
    if (!mounted) {
      return;
    }
    if (widget.bridge.busy) {
      _writeStarted = true;
    }
    setState(() {});
  }

  Future<void> _cancelWrite() async {
    if (_cancelling || _isTerminal) {
      return;
    }

    setState(() => _cancelling = true);
    await widget.bridge.requestCancelWriteTag();
    if (!mounted) {
      return;
    }
    widget.onClose();
  }

  String get _status => widget.bridge.status;

  bool get _isBusy => widget.bridge.busy;

  bool get _isTerminal => _resolvePhase() != _EpaperWritePhase.waiting;

  _EpaperWritePhase get _phase => _resolvePhase();

  _EpaperWritePhase _resolvePhase() {
    final lower = _status.toLowerCase();

    if (lower.contains('e-paper updated successfully')) {
      return _EpaperWritePhase.success;
    }
    if (lower.startsWith('update failed') || lower == 'update cancelled.') {
      return _EpaperWritePhase.failure;
    }

    if (!_writeStarted && !_isBusy) {
      if (lower.contains('turn on nfc') ||
          lower.startsWith('choose an image') ||
          lower.contains('invalid chunk') ||
          lower.contains('ndef mode cannot')) {
        return _EpaperWritePhase.failure;
      }
    }

    return _EpaperWritePhase.waiting;
  }

  bool get _canDismiss => _isTerminal;

  EpaperStatusPresentation get _statusPresentation {
    final lower = _status.toLowerCase();
    final isProcessing =
        lower.contains('preparing') ||
        lower.contains('updating') ||
        lower.contains('refreshing') ||
        lower.contains('continuing') ||
        lower.contains('restarting');

    return EpaperStatusPresentation.fromStatus(
      status: _status,
      isBusy: _isBusy,
      isProcessing: isProcessing,
    );
  }

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    final bottomInset = MediaQuery.viewPaddingOf(context).bottom;
    final phase = _phase;

    return PopScope(
      canPop: _canDismiss,
      child: Material(
        color: AppColors.surface,
        borderRadius: const BorderRadius.vertical(
          top: Radius.circular(AppRadius.large),
        ),
        child: SafeArea(
          top: false,
          child: Padding(
            padding: EdgeInsets.fromLTRB(
              AppSpacing.lg,
              AppSpacing.sm,
              AppSpacing.lg,
              AppSpacing.lg + bottomInset,
            ),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [
                Center(
                  child: Container(
                    width: 40,
                    height: 4,
                    decoration: BoxDecoration(
                      color: AppColors.divider,
                      borderRadius: BorderRadius.circular(AppRadius.pill),
                    ),
                  ),
                ),
                const SizedBox(height: AppSpacing.lg),
                _NfcScanIndicator(
                  pulseAnimation: _pulseController,
                  phase: phase,
                ),
                const SizedBox(height: AppSpacing.md),
                Text(
                  phase == _EpaperWritePhase.success
                      ? 'Update Complete'
                      : phase == _EpaperWritePhase.failure
                      ? 'Update Failed'
                      : _status.toLowerCase().contains('connection interrupted')
                      ? 'Reconnect to Continue'
                      : 'Updating E-Paper',
                  style: textTheme.titleLarge?.copyWith(
                    color: AppColors.textPrimary,
                  ),
                  textAlign: TextAlign.center,
                ),
                const SizedBox(height: AppSpacing.sm),
                Text(
                  phase == _EpaperWritePhase.waiting
                      ? _status.toLowerCase().contains('connection interrupted')
                            ? 'Place your phone back on the NFC area of the '
                                  'LATCH device.'
                            : 'Keep your phone on the NFC area until the '
                                  'display finishes updating.'
                      : phase == _EpaperWritePhase.success
                      ? 'Your image was written to the device.'
                      : 'Check the status below and try again when ready.',
                  style: textTheme.bodyMedium?.copyWith(
                    color: AppColors.textSecondary,
                  ),
                  textAlign: TextAlign.center,
                ),
                const SizedBox(height: AppSpacing.lg),
                EpaperStatusBanner(presentation: _statusPresentation),
                const SizedBox(height: AppSpacing.lg),
                if (phase == _EpaperWritePhase.waiting)
                  LatchButton(
                    label: 'Cancel',
                    variant: LatchButtonVariant.secondary,
                    fullWidth: true,
                    isLoading: _cancelling,
                    enabled: !_cancelling,
                    onPressed: _cancelWrite,
                  )
                else
                  LatchButton(
                    label: 'Done',
                    variant: LatchButtonVariant.primary,
                    fullWidth: true,
                    onPressed: widget.onClose,
                  ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}

enum _EpaperWritePhase { waiting, success, failure }

class _NfcScanIndicator extends StatelessWidget {
  const _NfcScanIndicator({required this.pulseAnimation, required this.phase});

  final Animation<double> pulseAnimation;
  final _EpaperWritePhase phase;

  @override
  Widget build(BuildContext context) {
    final iconColor = switch (phase) {
      _EpaperWritePhase.success => AppColors.success,
      _EpaperWritePhase.failure => AppColors.error,
      _EpaperWritePhase.waiting => AppColors.primary,
    };

    return SizedBox(
      height: 96,
      child: Center(
        child: AnimatedBuilder(
          animation: pulseAnimation,
          builder: (context, child) {
            if (phase != _EpaperWritePhase.waiting) {
              return child!;
            }

            final t = pulseAnimation.value;
            return Stack(
              alignment: Alignment.center,
              children: [
                _PulseRing(
                  progress: t,
                  delay: 0,
                  color: AppColors.primary.withValues(alpha: 0.12),
                ),
                _PulseRing(
                  progress: t,
                  delay: 0.35,
                  color: AppColors.primary.withValues(alpha: 0.08),
                ),
                child!,
              ],
            );
          },
          child: Container(
            width: 64,
            height: 64,
            decoration: BoxDecoration(
              color: AppColors.surfaceVariant,
              shape: BoxShape.circle,
              border: Border.all(color: AppColors.border),
            ),
            child: Icon(
              phase == _EpaperWritePhase.success
                  ? Icons.check_rounded
                  : phase == _EpaperWritePhase.failure
                  ? Icons.close_rounded
                  : AppIcons.nfc,
              size: 32,
              color: iconColor,
            ),
          ),
        ),
      ),
    );
  }
}

class _PulseRing extends StatelessWidget {
  const _PulseRing({
    required this.progress,
    required this.delay,
    required this.color,
  });

  final double progress;
  final double delay;
  final Color color;

  @override
  Widget build(BuildContext context) {
    final shifted = (progress + delay) % 1.0;
    final scale = 0.85 + (shifted * 0.55);
    final opacity = (1 - shifted).clamp(0.0, 1.0);

    return Transform.scale(
      scale: scale,
      child: Container(
        width: 88,
        height: 88,
        decoration: BoxDecoration(
          shape: BoxShape.circle,
          color: color.withValues(alpha: color.a * opacity),
        ),
      ),
    );
  }
}
