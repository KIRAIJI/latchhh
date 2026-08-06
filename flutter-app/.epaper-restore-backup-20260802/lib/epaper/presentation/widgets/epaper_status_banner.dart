import 'package:flutter/material.dart';

import '../../../../core/theme/app_colors.dart';
import '../../../../core/theme/app_radius.dart';
import '../../../../core/theme/app_spacing.dart';

enum EpaperStatusKind { idle, processing, ready, nfc, error, info }

class EpaperStatusPresentation {
  const EpaperStatusPresentation({required this.label, required this.kind});

  final String label;
  final EpaperStatusKind kind;

  Color get backgroundColor => switch (kind) {
    EpaperStatusKind.ready => AppColors.successContainer,
    EpaperStatusKind.processing => AppColors.warningContainer,
    EpaperStatusKind.error => AppColors.errorContainer,
    EpaperStatusKind.nfc => AppColors.surfaceVariant,
    EpaperStatusKind.idle || EpaperStatusKind.info => AppColors.surfaceVariant,
  };

  Color get foregroundColor => switch (kind) {
    EpaperStatusKind.ready => AppColors.success,
    EpaperStatusKind.processing => AppColors.warning,
    EpaperStatusKind.error => AppColors.error,
    EpaperStatusKind.nfc => AppColors.trackerAccent,
    EpaperStatusKind.idle || EpaperStatusKind.info => AppColors.textSecondary,
  };

  IconData get icon => switch (kind) {
    EpaperStatusKind.ready => Icons.check_circle_outline_rounded,
    EpaperStatusKind.processing => Icons.hourglass_top_rounded,
    EpaperStatusKind.error => Icons.error_outline_rounded,
    EpaperStatusKind.nfc => Icons.nfc_rounded,
    EpaperStatusKind.idle => Icons.info_outline_rounded,
    EpaperStatusKind.info => Icons.info_outline_rounded,
  };

  static EpaperStatusPresentation fromStatus({
    required String status,
    required bool isBusy,
    required bool isProcessing,
  }) {
    final lower = status.toLowerCase();

    if (lower.startsWith('update failed') ||
        status.startsWith('Failed') ||
        lower.contains('failed') ||
        lower.contains('not available') ||
        lower.contains('unavailable') ||
        lower.contains('invalid') ||
        lower.contains('turn on nfc') ||
        lower.contains('could not be opened') ||
        lower.contains('could not be prepared')) {
      return EpaperStatusPresentation(
        label: status,
        kind: EpaperStatusKind.error,
      );
    }

    if (isProcessing ||
        lower.contains('preparing') ||
        lower.contains('updating') ||
        lower.contains('refreshing') ||
        lower.contains('continuing') ||
        lower.contains('restarting') ||
        lower.contains('checking')) {
      return EpaperStatusPresentation(
        label: status,
        kind: EpaperStatusKind.processing,
      );
    }

    if (lower.contains('hold your phone') ||
        lower.contains('connection interrupted') ||
        lower.contains('reposition your phone') ||
        lower.contains('retry refresh') ||
        (isBusy && lower.contains('nfc area'))) {
      return EpaperStatusPresentation(
        label: status,
        kind: EpaperStatusKind.nfc,
      );
    }

    if (lower == 'image ready.' ||
        lower.contains('updated successfully') ||
        status.startsWith('Ready')) {
      return EpaperStatusPresentation(
        label: status,
        kind: EpaperStatusKind.ready,
      );
    }

    if (lower.contains('pick an image') || lower.contains('choose an image')) {
      return EpaperStatusPresentation(
        label: status,
        kind: EpaperStatusKind.idle,
      );
    }

    return EpaperStatusPresentation(label: status, kind: EpaperStatusKind.info);
  }
}

class EpaperStatusBanner extends StatelessWidget {
  const EpaperStatusBanner({super.key, required this.presentation});

  final EpaperStatusPresentation presentation;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;

    return DecoratedBox(
      decoration: BoxDecoration(
        color: presentation.backgroundColor,
        borderRadius: BorderRadius.circular(AppRadius.medium),
      ),
      child: Padding(
        padding: const EdgeInsets.symmetric(
          horizontal: AppSpacing.md,
          vertical: AppSpacing.sm + 2,
        ),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Icon(
              presentation.icon,
              size: 20,
              color: presentation.foregroundColor,
            ),
            const SizedBox(width: AppSpacing.sm),
            Expanded(
              child: Text(
                presentation.label,
                style: textTheme.bodyMedium?.copyWith(
                  color: presentation.foregroundColor,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
