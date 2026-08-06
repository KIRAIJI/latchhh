import 'package:flutter/material.dart';

import '../../../../core/components/buttons/latch_button.dart';
import '../../../../core/components/inputs/latch_text_field.dart';
import '../../../../core/theme/app_colors.dart';
import '../../../../core/theme/app_icons.dart';
import '../../../../core/theme/app_radius.dart';
import '../../../../core/theme/app_spacing.dart';

typedef EditProfileSaveCallback =
    void Function(String displayName, String email, String currentPassword);

class EditProfileBottomSheet extends StatefulWidget {
  const EditProfileBottomSheet({
    super.key,
    required this.initialDisplayName,
    required this.initialEmail,
    required this.onSavePressed,
    required this.onCancelPressed,
    this.isLoading = false,
    this.displayNameError,
    this.emailError,
    this.currentPasswordError,
  });

  final String initialDisplayName;
  final String initialEmail;
  final EditProfileSaveCallback? onSavePressed;
  final VoidCallback? onCancelPressed;
  final bool isLoading;
  final String? displayNameError;
  final String? emailError;
  final String? currentPasswordError;

  @override
  State<EditProfileBottomSheet> createState() => _EditProfileBottomSheetState();
}

class _EditProfileBottomSheetState extends State<EditProfileBottomSheet> {
  late final TextEditingController _displayNameController;
  late final TextEditingController _emailController;
  final TextEditingController _currentPasswordController =
      TextEditingController();
  bool _obscureCurrentPassword = true;

  @override
  void initState() {
    super.initState();
    _displayNameController = TextEditingController(
      text: widget.initialDisplayName,
    );
    _emailController = TextEditingController(text: widget.initialEmail);
  }

  @override
  void dispose() {
    _displayNameController.dispose();
    _emailController.dispose();
    _currentPasswordController.dispose();
    super.dispose();
  }

  void _handleSave() {
    if (widget.isLoading) {
      return;
    }

    widget.onSavePressed?.call(
      _displayNameController.text,
      _emailController.text,
      _currentPasswordController.text,
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
              Text('Edit Profile', style: textTheme.titleLarge),
              const SizedBox(height: AppSpacing.lg),
              LatchTextField(
                controller: _displayNameController,
                label: 'Display Name',
                hint: 'Display Name',
                errorText: widget.displayNameError,
                textInputAction: TextInputAction.next,
                enabled: !widget.isLoading,
                prefixIcon: const Icon(AppIcons.profile),
              ),
              const SizedBox(height: AppSpacing.md),
              LatchTextField(
                controller: _currentPasswordController,
                label: 'Current Password',
                helperText: 'Required only when changing your email.',
                errorText: widget.currentPasswordError,
                obscureText: _obscureCurrentPassword,
                enabled: !widget.isLoading,
                prefixIcon: const Icon(AppIcons.lock),
                suffixIcon: IconButton(
                  onPressed: () => setState(
                    () => _obscureCurrentPassword = !_obscureCurrentPassword,
                  ),
                  icon: Icon(
                    _obscureCurrentPassword
                        ? Icons.visibility_outlined
                        : Icons.visibility_off_outlined,
                  ),
                ),
              ),
              const SizedBox(height: AppSpacing.md),
              LatchTextField(
                controller: _emailController,
                label: 'Email',
                hint: 'user@example.com',
                errorText: widget.emailError,
                textInputAction: TextInputAction.done,
                enabled: !widget.isLoading,
                prefixIcon: const Icon(AppIcons.email),
              ),
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
                  label: 'Save Changes',
                  fullWidth: true,
                  isLoading: widget.isLoading,
                  onPressed: _handleSave,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
