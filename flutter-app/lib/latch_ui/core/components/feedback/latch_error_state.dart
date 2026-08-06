import 'package:flutter/material.dart';

import '../buttons/latch_button.dart';
import '../../theme/app_colors.dart';
import '../../theme/app_radius.dart';
import '../../theme/app_spacing.dart';

class LatchErrorState extends StatelessWidget {
  const LatchErrorState({
    super.key,
    required this.message,
    this.onRetryPressed,
    this.compact = false,
    this.icon = Icons.error_outline_rounded,
  });

  final String message;
  final VoidCallback? onRetryPressed;
  final bool compact;
  final IconData icon;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;

    if (compact) {
      return Container(
        width: double.infinity,
        padding: const EdgeInsets.all(AppSpacing.md),
        decoration: BoxDecoration(
          color: AppColors.errorContainer,
          borderRadius: BorderRadius.circular(AppRadius.medium),
          border: Border.all(color: AppColors.error.withValues(alpha: 0.24)),
        ),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Icon(icon, size: 20, color: AppColors.error),
                const SizedBox(width: AppSpacing.sm),
                Expanded(
                  child: Text(
                    message,
                    style: textTheme.bodyMedium?.copyWith(
                      color: AppColors.error,
                    ),
                  ),
                ),
              ],
            ),
            if (onRetryPressed != null) ...[
              const SizedBox(height: AppSpacing.sm),
              LatchButton(
                label: 'Retry',
                variant: LatchButtonVariant.secondary,
                fullWidth: true,
                onPressed: onRetryPressed,
              ),
            ],
          ],
        ),
      );
    }

    return SingleChildScrollView(
      padding: const EdgeInsets.all(AppSpacing.lg),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        mainAxisSize: MainAxisSize.min,
        children: [
          Icon(icon, size: 40, color: AppColors.error),
          const SizedBox(height: AppSpacing.md),
          Text(
            message,
            style: textTheme.bodyLarge,
            textAlign: TextAlign.center,
          ),
          if (onRetryPressed != null) ...[
            const SizedBox(height: AppSpacing.md),
            LatchButton(
              label: 'Retry',
              variant: LatchButtonVariant.secondary,
              onPressed: onRetryPressed,
            ),
          ],
        ],
      ),
    );
  }
}
