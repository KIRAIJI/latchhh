import 'package:flutter/material.dart';

import '../../theme/app_colors.dart';
import '../../theme/app_radius.dart';
import '../../theme/app_spacing.dart';

enum LatchButtonVariant { primary, secondary, destructive }

class LatchButton extends StatelessWidget {
  const LatchButton({
    super.key,
    required this.label,
    this.leadingIcon,
    this.onPressed,
    this.isLoading = false,
    this.fullWidth = false,
    this.enabled = true,
    this.variant = LatchButtonVariant.primary,
  });

  final String label;
  final IconData? leadingIcon;
  final VoidCallback? onPressed;
  final bool isLoading;
  final bool fullWidth;
  final bool enabled;
  final LatchButtonVariant variant;

  bool get _isInteractive => enabled && !isLoading && onPressed != null;

  @override
  Widget build(BuildContext context) {
    final button = switch (variant) {
      LatchButtonVariant.primary => _buildPrimary(context),
      LatchButtonVariant.secondary => _buildSecondary(context),
      LatchButtonVariant.destructive => _buildDestructive(context),
    };

    if (fullWidth) {
      return SizedBox(width: double.infinity, child: button);
    }

    return button;
  }

  Widget _buildPrimary(BuildContext context) {
    return FilledButton(
      onPressed: _isInteractive ? onPressed : null,
      style: FilledButton.styleFrom(
        minimumSize: const Size(0, 48),
        padding: const EdgeInsets.symmetric(horizontal: AppSpacing.md),
        backgroundColor: AppColors.primary,
        foregroundColor: AppColors.onPrimary,
        disabledBackgroundColor: AppColors.primary.withValues(alpha: 0.38),
        disabledForegroundColor: AppColors.onPrimary.withValues(alpha: 0.38),
        shape: const RoundedRectangleBorder(
          borderRadius: BorderRadius.all(Radius.circular(AppRadius.medium)),
        ),
      ),
      child: _buildChild(context),
    );
  }

  Widget _buildSecondary(BuildContext context) {
    return OutlinedButton(
      onPressed: _isInteractive ? onPressed : null,
      style: OutlinedButton.styleFrom(
        minimumSize: const Size(0, 48),
        padding: const EdgeInsets.symmetric(horizontal: AppSpacing.md),
        shape: const RoundedRectangleBorder(
          borderRadius: BorderRadius.all(Radius.circular(AppRadius.medium)),
        ),
        side: const BorderSide(color: AppColors.border),
        foregroundColor: AppColors.textPrimary,
      ),
      child: _buildChild(context),
    );
  }

  Widget _buildDestructive(BuildContext context) {
    return FilledButton(
      onPressed: _isInteractive ? onPressed : null,
      style: FilledButton.styleFrom(
        minimumSize: const Size(0, 48),
        padding: const EdgeInsets.symmetric(horizontal: AppSpacing.md),
        backgroundColor: AppColors.error,
        foregroundColor: AppColors.onError,
        disabledBackgroundColor: AppColors.error.withValues(alpha: 0.38),
        disabledForegroundColor: AppColors.onError.withValues(alpha: 0.38),
        shape: const RoundedRectangleBorder(
          borderRadius: BorderRadius.all(Radius.circular(AppRadius.medium)),
        ),
      ),
      child: _buildChild(context),
    );
  }

  Widget _buildChild(BuildContext context) {
    final labelStyle = Theme.of(context).textTheme.labelMedium;
    final contentColor = switch (variant) {
      LatchButtonVariant.primary => AppColors.onPrimary,
      LatchButtonVariant.destructive => AppColors.onError,
      _ => null,
    };

    if (isLoading) {
      return SizedBox(
        height: 20,
        width: 20,
        child: CircularProgressIndicator(
          strokeWidth: 2,
          color: _loadingIndicatorColor(context),
        ),
      );
    }

    final children = <Widget>[
      if (leadingIcon != null) ...[
        Icon(leadingIcon, size: 20),
        const SizedBox(width: AppSpacing.sm),
      ],
      Flexible(
        child: Text(
          label,
          style: labelStyle?.copyWith(color: contentColor),
          maxLines: 2,
          overflow: TextOverflow.ellipsis,
          textAlign: TextAlign.center,
        ),
      ),
    ];

    return Row(
      mainAxisSize: MainAxisSize.min,
      mainAxisAlignment: MainAxisAlignment.center,
      children: children,
    );
  }

  Color _loadingIndicatorColor(BuildContext context) {
    return switch (variant) {
      LatchButtonVariant.primary => Theme.of(context).colorScheme.onPrimary,
      LatchButtonVariant.destructive => AppColors.onError,
      LatchButtonVariant.secondary => AppColors.textPrimary,
    };
  }
}

/// Lays out two action buttons side by side on wider screens and stacked on
/// narrow screens to prevent horizontal overflow.
class LatchButtonRow extends StatelessWidget {
  const LatchButtonRow({
    super.key,
    required this.start,
    required this.end,
    this.stackWhenWidthBelow = 360,
  });

  final Widget start;
  final Widget end;
  final double stackWhenWidthBelow;

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
      builder: (context, constraints) {
        final stackVertically = constraints.maxWidth < stackWhenWidthBelow;

        if (stackVertically) {
          return Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              start,
              const SizedBox(height: AppSpacing.sm),
              end,
            ],
          );
        }

        return Row(
          children: [
            Expanded(child: start),
            const SizedBox(width: AppSpacing.sm),
            Expanded(child: end),
          ],
        );
      },
    );
  }
}
