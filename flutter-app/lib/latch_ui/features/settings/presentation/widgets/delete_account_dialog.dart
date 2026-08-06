import 'package:flutter/material.dart';

import '../../../../core/components/buttons/latch_button.dart';
import '../../../../core/components/inputs/latch_text_field.dart';
import '../../../../core/theme/app_colors.dart';
import '../../../../core/theme/app_icons.dart';
import '../../../../core/theme/app_radius.dart';
import '../../../../core/theme/app_spacing.dart';

typedef DeleteAccountCallback = void Function(String currentPassword);

class DeleteAccountDialog extends StatefulWidget {
  const DeleteAccountDialog({
    super.key,
    required this.onConfirmPressed,
    required this.onCancelPressed,
    this.requiresPassword = true,
    this.isLoading = false,
    this.passwordError,
    this.errorMessage,
  });

  final DeleteAccountCallback? onConfirmPressed;
  final VoidCallback? onCancelPressed;
  final bool requiresPassword;
  final bool isLoading;
  final String? passwordError;
  final String? errorMessage;

  @override
  State<DeleteAccountDialog> createState() => _DeleteAccountDialogState();
}

class _DeleteAccountDialogState extends State<DeleteAccountDialog> {
  final TextEditingController _passwordController = TextEditingController();
  bool _obscurePassword = true;

  @override
  void dispose() {
    _passwordController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;

    return AlertDialog(
      backgroundColor: AppColors.surface,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(AppRadius.large),
      ),
      title: const Text('Delete Account Permanently?'),
      content: SingleChildScrollView(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Text(
              'This action will permanently delete your account and cannot be undone.',
              style: textTheme.bodyMedium,
            ),
            const SizedBox(height: AppSpacing.md),
            if (widget.requiresPassword)
              LatchTextField(
                controller: _passwordController,
                label: 'Current Password',
                obscureText: _obscurePassword,
                enabled: !widget.isLoading,
                errorText: widget.passwordError,
                prefixIcon: const Icon(AppIcons.lock),
                suffixIcon: IconButton(
                  onPressed: () =>
                      setState(() => _obscurePassword = !_obscurePassword),
                  icon: Icon(
                    _obscurePassword
                        ? Icons.visibility_outlined
                        : Icons.visibility_off_outlined,
                  ),
                ),
              )
            else
              Text(
                'To protect your account, Google will ask you to sign in again before deletion.',
                style: textTheme.bodySmall?.copyWith(
                  color: AppColors.textSecondary,
                ),
              ),
            if (widget.errorMessage != null) ...[
              const SizedBox(height: AppSpacing.sm),
              Text(
                widget.errorMessage!,
                style: textTheme.bodySmall?.copyWith(color: AppColors.error),
              ),
            ],
          ],
        ),
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
                enabled: !widget.isLoading,
                onPressed: widget.onCancelPressed,
              ),
              const SizedBox(height: AppSpacing.sm),
              LatchButton(
                label: widget.requiresPassword
                    ? 'Delete Account'
                    : 'Continue with Google & Delete',
                variant: LatchButtonVariant.destructive,
                fullWidth: true,
                isLoading: widget.isLoading,
                onPressed: () =>
                    widget.onConfirmPressed?.call(_passwordController.text),
              ),
            ],
          ),
        ),
      ],
    );
  }
}
