import 'package:flutter/material.dart';

import '../../../../core/components/buttons/latch_button.dart';
import '../../../../core/theme/app_colors.dart';
import '../../../../core/theme/app_radius.dart';
import '../../../../core/theme/app_spacing.dart';

class UpdateProfilePhotoBottomSheet extends StatelessWidget {
  const UpdateProfilePhotoBottomSheet({
    super.key,
    required this.hasProfilePhoto,
    required this.onChooseFromGalleryPressed,
    required this.onRemovePhotoPressed,
    required this.onCancelPressed,
  });

  final bool hasProfilePhoto;
  final VoidCallback? onChooseFromGalleryPressed;
  final VoidCallback? onRemovePhotoPressed;
  final VoidCallback? onCancelPressed;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    final bottomInset = MediaQuery.viewPaddingOf(context).bottom;

    return Material(
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
              const SizedBox(height: AppSpacing.md),
              Text('Update Profile Photo', style: textTheme.titleLarge),
              const SizedBox(height: AppSpacing.sm),
              ListTile(
                contentPadding: EdgeInsets.zero,
                leading: const Icon(Icons.photo_library_outlined),
                title: const Text('Choose from Gallery'),
                onTap: onChooseFromGalleryPressed,
              ),
              if (hasProfilePhoto)
                ListTile(
                  contentPadding: EdgeInsets.zero,
                  leading: Icon(
                    Icons.delete_outline_rounded,
                    color: AppColors.error,
                  ),
                  title: Text(
                    'Remove Current Photo',
                    style: textTheme.bodyLarge?.copyWith(
                      color: AppColors.error,
                    ),
                  ),
                  onTap: onRemovePhotoPressed,
                ),
              const SizedBox(height: AppSpacing.xs),
              LatchButton(
                label: 'Cancel',
                variant: LatchButtonVariant.secondary,
                fullWidth: true,
                onPressed: onCancelPressed,
              ),
            ],
          ),
        ),
      ),
    );
  }
}
