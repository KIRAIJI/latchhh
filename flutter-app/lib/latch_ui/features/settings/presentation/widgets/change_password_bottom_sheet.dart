import 'package:flutter/material.dart';

import '../../../../core/components/buttons/latch_button.dart';
import '../../../../core/components/inputs/latch_text_field.dart';
import '../../../../core/theme/app_colors.dart';
import '../../../../core/theme/app_icons.dart';
import '../../../../core/theme/app_radius.dart';
import '../../../../core/theme/app_spacing.dart';

typedef ChangePasswordCallback =
    void Function(
      String currentPassword,
      String password,
      String passwordConfirmation,
    );

class ChangePasswordBottomSheet extends StatefulWidget {
  const ChangePasswordBottomSheet({
    super.key,
    required this.onSavePressed,
    required this.onCancelPressed,
    this.isLoading = false,
    this.currentPasswordError,
    this.passwordError,
    this.passwordConfirmationError,
    this.errorMessage,
  });

  final ChangePasswordCallback? onSavePressed;
  final VoidCallback? onCancelPressed;
  final bool isLoading;
  final String? currentPasswordError;
  final String? passwordError;
  final String? passwordConfirmationError;
  final String? errorMessage;

  @override
  State<ChangePasswordBottomSheet> createState() =>
      _ChangePasswordBottomSheetState();
}

class _ChangePasswordBottomSheetState extends State<ChangePasswordBottomSheet> {
  final TextEditingController _currentController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _confirmationController = TextEditingController();
  bool _obscureCurrent = true;
  bool _obscurePassword = true;
  bool _obscureConfirmation = true;

  @override
  void dispose() {
    _currentController.dispose();
    _passwordController.dispose();
    _confirmationController.dispose();
    super.dispose();
  }

  Widget _passwordField({
    required TextEditingController controller,
    required String label,
    required bool obscure,
    required VoidCallback onToggle,
    String? errorText,
  }) {
    return LatchTextField(
      controller: controller,
      label: label,
      obscureText: obscure,
      enabled: !widget.isLoading,
      errorText: errorText,
      prefixIcon: const Icon(AppIcons.lock),
      suffixIcon: IconButton(
        onPressed: onToggle,
        icon: Icon(
          obscure ? Icons.visibility_outlined : Icons.visibility_off_outlined,
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    final bottomInset = MediaQuery.viewInsetsOf(context).bottom;

    return Material(
      color: AppColors.surface,
      borderRadius: const BorderRadius.vertical(
        top: Radius.circular(AppRadius.large),
      ),
      child: SafeArea(
        top: false,
        child: SingleChildScrollView(
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
              const SizedBox(height: AppSpacing.md),
              Text('Change Password', style: textTheme.titleLarge),
              const SizedBox(height: AppSpacing.lg),
              _passwordField(
                controller: _currentController,
                label: 'Current Password',
                obscure: _obscureCurrent,
                errorText: widget.currentPasswordError,
                onToggle: () =>
                    setState(() => _obscureCurrent = !_obscureCurrent),
              ),
              const SizedBox(height: AppSpacing.md),
              _passwordField(
                controller: _passwordController,
                label: 'New Password',
                obscure: _obscurePassword,
                errorText: widget.passwordError,
                onToggle: () =>
                    setState(() => _obscurePassword = !_obscurePassword),
              ),
              const SizedBox(height: AppSpacing.md),
              _passwordField(
                controller: _confirmationController,
                label: 'Confirm New Password',
                obscure: _obscureConfirmation,
                errorText: widget.passwordConfirmationError,
                onToggle: () => setState(
                  () => _obscureConfirmation = !_obscureConfirmation,
                ),
              ),
              if (widget.errorMessage != null) ...[
                const SizedBox(height: AppSpacing.sm),
                Text(
                  widget.errorMessage!,
                  style: textTheme.bodySmall?.copyWith(color: AppColors.error),
                ),
              ],
              const SizedBox(height: AppSpacing.lg),
              LatchButtonRow(
                start: LatchButton(
                  label: 'Cancel',
                  variant: LatchButtonVariant.secondary,
                  fullWidth: true,
                  enabled: !widget.isLoading,
                  onPressed: widget.onCancelPressed,
                ),
                end: LatchButton(
                  label: 'Update Password',
                  fullWidth: true,
                  isLoading: widget.isLoading,
                  onPressed: () => widget.onSavePressed?.call(
                    _currentController.text,
                    _passwordController.text,
                    _confirmationController.text,
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
