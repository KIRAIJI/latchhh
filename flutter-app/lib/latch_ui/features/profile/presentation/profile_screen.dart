import 'package:flutter/material.dart';

import '../../../core/components/feedback/latch_error_state.dart';
import '../../../core/components/feedback/latch_loading_state.dart';
import '../../../core/components/buttons/latch_button.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_icons.dart';
import '../../../core/theme/app_spacing.dart';
import 'widgets/profile_info_card.dart';

class ProfileScreen extends StatelessWidget {
  const ProfileScreen({
    super.key,
    required this.displayName,
    required this.email,
    required this.onLogoutPressed,
    this.onEditProfilePressed,
    this.onSettingsPressed,
    this.onEditPhotoPressed,
    this.hasProfilePhoto = false,
    this.profilePhotoUrl,
    this.isLoading = false,
    this.errorMessage,
    this.onRetryPressed,
  });

  final String displayName;
  final String email;
  final bool isLoading;
  final String? errorMessage;
  final VoidCallback? onLogoutPressed;
  final VoidCallback? onEditProfilePressed;
  final VoidCallback? onSettingsPressed;
  final VoidCallback? onEditPhotoPressed;
  final bool hasProfilePhoto;
  final String? profilePhotoUrl;
  final VoidCallback? onRetryPressed;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;

    return SafeArea(
      child: Padding(
        padding: const EdgeInsets.all(AppSpacing.screenHorizontal),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Row(
              children: [
                Expanded(
                  child: Text('Profile', style: textTheme.headlineMedium),
                ),
                IconButton(
                  onPressed: onSettingsPressed,
                  tooltip: 'Settings',
                  icon: const Icon(Icons.settings_outlined),
                ),
              ],
            ),
            const SizedBox(height: AppSpacing.section),
            Expanded(
              child: SingleChildScrollView(
                child: _buildBody(context, textTheme),
              ),
            ),
            if (!isLoading && errorMessage == null) ...[
              const SizedBox(height: AppSpacing.md),
              LatchButton(
                label: 'Edit Profile',
                variant: LatchButtonVariant.secondary,
                fullWidth: true,
                onPressed: onEditProfilePressed,
              ),
              const SizedBox(height: AppSpacing.sm),
              LatchButton(
                label: 'Log Out',
                variant: LatchButtonVariant.secondary,
                fullWidth: true,
                onPressed: onLogoutPressed,
              ),
            ],
          ],
        ),
      ),
    );
  }

  Widget _buildBody(BuildContext context, TextTheme textTheme) {
    if (isLoading) {
      return const LatchLoadingState();
    }

    if (errorMessage != null) {
      return LatchErrorState(
        message: errorMessage!,
        onRetryPressed: onRetryPressed,
      );
    }

    return Column(
      children: [
        Center(
          child: _ProfilePhotoSection(
            displayName: displayName,
            profilePhotoUrl: profilePhotoUrl,
            textTheme: textTheme,
            onEditPhotoPressed: onEditPhotoPressed,
          ),
        ),
        const SizedBox(height: AppSpacing.md),
        Text(
          displayName,
          style: textTheme.titleLarge,
          textAlign: TextAlign.center,
        ),
        const SizedBox(height: AppSpacing.xs),
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(AppIcons.email, size: 16, color: AppColors.textSecondary),
            const SizedBox(width: AppSpacing.xs),
            Flexible(
              child: Text(
                email,
                style: textTheme.bodyMedium?.copyWith(
                  color: AppColors.textSecondary,
                ),
                maxLines: 1,
                overflow: TextOverflow.ellipsis,
                textAlign: TextAlign.center,
              ),
            ),
          ],
        ),
        const SizedBox(height: AppSpacing.lg),
        ProfileInfoCard(displayName: displayName, email: email),
      ],
    );
  }
}

class _ProfilePhotoSection extends StatelessWidget {
  const _ProfilePhotoSection({
    required this.displayName,
    required this.profilePhotoUrl,
    required this.textTheme,
    required this.onEditPhotoPressed,
  });

  final String displayName;
  final String? profilePhotoUrl;
  final TextTheme textTheme;
  final VoidCallback? onEditPhotoPressed;

  String _initialsFromDisplayName(String name) {
    final parts = name.trim().split(RegExp(r'\s+'));
    if (parts.isEmpty || parts.first.isEmpty) {
      return '?';
    }

    if (parts.length == 1) {
      return parts.first[0].toUpperCase();
    }

    return '${parts.first[0]}${parts.last[0]}'.toUpperCase();
  }

  @override
  Widget build(BuildContext context) {
    const avatarRadius = 40.0;
    const avatarSize = avatarRadius * 2;

    return SizedBox(
      width: avatarSize + AppSpacing.sm,
      height: avatarSize + AppSpacing.sm,
      child: Stack(
        clipBehavior: Clip.none,
        children: [
          Positioned(
            left: 0,
            bottom: 0,
            child: ClipOval(
              child: SizedBox.square(
                dimension: avatarSize,
                child: profilePhotoUrl == null
                    ? ColoredBox(
                        color: AppColors.surfaceVariant,
                        child: Center(
                          child: Text(
                            _initialsFromDisplayName(displayName),
                            style: textTheme.headlineSmall,
                          ),
                        ),
                      )
                    : Image.network(
                        profilePhotoUrl!,
                        fit: BoxFit.cover,
                        semanticLabel: 'Profile avatar for $displayName',
                        errorBuilder: (_, _, _) => ColoredBox(
                          color: AppColors.surfaceVariant,
                          child: Center(
                            child: Text(
                              _initialsFromDisplayName(displayName),
                              style: textTheme.headlineSmall,
                            ),
                          ),
                        ),
                      ),
              ),
            ),
          ),
          Positioned(
            top: 0,
            right: 0,
            child: Tooltip(
              message: 'Edit profile photo',
              child: Semantics(
                label: 'Edit profile photo',
                button: true,
                child: Material(
                  color: AppColors.surface,
                  shape: CircleBorder(
                    side: BorderSide(color: AppColors.border),
                  ),
                  elevation: 0,
                  child: InkWell(
                    onTap: onEditPhotoPressed,
                    customBorder: const CircleBorder(),
                    child: const SizedBox(
                      width: 36,
                      height: 36,
                      child: Icon(
                        Icons.edit_rounded,
                        size: 18,
                        color: AppColors.textPrimary,
                      ),
                    ),
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
