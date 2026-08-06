import 'package:flutter/material.dart';

import '../../../../core/components/buttons/latch_button.dart';
import '../../../../core/theme/app_colors.dart';
import '../../../../core/theme/app_radius.dart';
import '../../../../core/theme/app_spacing.dart';

class RemoveDeviceDialog extends StatelessWidget {
  const RemoveDeviceDialog({
    super.key,
    required this.itemName,
    required this.onConfirmPressed,
    required this.onCancelPressed,
    this.isLoading = false,
  });

  final String itemName;
  final VoidCallback? onConfirmPressed;
  final VoidCallback? onCancelPressed;
  final bool isLoading;

  void _handleConfirm() {
    if (isLoading) {
      return;
    }

    onConfirmPressed?.call();
  }

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;

    return AlertDialog(
      backgroundColor: AppColors.surface,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(AppRadius.large),
      ),
      title: Text('Remove $itemName?'),
      content: Text(
        'The device will be released and can be added to another account. '
        'Its geofence will be deleted.',
        style: textTheme.bodyMedium,
      ),
      actionsPadding: const EdgeInsets.fromLTRB(
        AppSpacing.lg,
        0,
        AppSpacing.lg,
        AppSpacing.lg,
      ),
      actions: [
        SizedBox(
          width: double.maxFinite,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              LatchButton(
                label: 'Cancel',
                variant: LatchButtonVariant.secondary,
                fullWidth: true,
                enabled: !isLoading,
                onPressed: onCancelPressed,
              ),
              const SizedBox(height: AppSpacing.sm),
              LatchButton(
                label: 'Remove Device',
                variant: LatchButtonVariant.destructive,
                fullWidth: true,
                isLoading: isLoading,
                onPressed: _handleConfirm,
              ),
            ],
          ),
        ),
      ],
    );
  }
}
