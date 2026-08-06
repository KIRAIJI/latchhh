import 'package:flutter/material.dart';

import '../../theme/app_colors.dart';
import '../../theme/app_radius.dart';
import '../../theme/app_spacing.dart';

class LatchCard extends StatelessWidget {
  const LatchCard({
    super.key,
    required this.child,
    this.padding,
    this.onTap,
    this.selected = false,
  });

  final Widget child;
  final EdgeInsetsGeometry? padding;
  final VoidCallback? onTap;
  final bool selected;

  @override
  Widget build(BuildContext context) {
    final borderColor = selected ? AppColors.trackerAccent : AppColors.border;
    final borderWidth = selected ? 1.5 : 1.0;
    final backgroundColor = selected
        ? AppColors.trackerAccent.withValues(alpha: 0.06)
        : AppColors.surface;

    final content = Container(
      width: double.infinity,
      padding: padding ?? const EdgeInsets.all(AppSpacing.md),
      decoration: BoxDecoration(
        color: backgroundColor,
        borderRadius: BorderRadius.circular(AppRadius.medium),
        border: Border.all(color: borderColor, width: borderWidth),
      ),
      child: child,
    );

    if (onTap == null) {
      return content;
    }

    return Material(
      color: Colors.transparent,
      child: InkWell(
        onTap: onTap,
        borderRadius: BorderRadius.circular(AppRadius.medium),
        child: content,
      ),
    );
  }
}
