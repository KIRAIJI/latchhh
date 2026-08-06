import 'dart:typed_data';

import 'package:flutter/material.dart';

import '../../../../core/theme/app_colors.dart';
import '../../../../core/theme/app_radius.dart';
import '../../../../core/theme/app_spacing.dart';
import '../epaper_display_spec.dart';

class EpaperPreviewFrame extends StatelessWidget {
  const EpaperPreviewFrame({
    super.key,
    this.previewBytes,
    this.isLoading = false,
    this.maxWidth = 280,
  });

  final Uint8List? previewBytes;
  final bool isLoading;
  final double maxWidth;

  static bool isEncodedImageBytes(Uint8List bytes) {
    if (bytes.isEmpty) {
      return false;
    }

    final isPng =
        bytes.length >= 8 &&
        bytes[0] == 0x89 &&
        bytes[1] == 0x50 &&
        bytes[2] == 0x4E &&
        bytes[3] == 0x47;

    final isJpeg =
        bytes.length >= 3 &&
        bytes[0] == 0xFF &&
        bytes[1] == 0xD8 &&
        bytes[2] == 0xFF;

    return isPng || isJpeg;
  }

  @override
  Widget build(BuildContext context) {
    final encodedPreview = _resolvePreview(previewBytes);
    final hasPreview = encodedPreview != null;

    return ConstrainedBox(
      constraints: BoxConstraints(maxWidth: maxWidth),
      child: AspectRatio(
        aspectRatio: EpaperDisplaySpec.aspectRatio,
        child: DecoratedBox(
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(AppRadius.medium),
            border: Border.all(color: AppColors.border, width: 1.5),
            boxShadow: [
              BoxShadow(
                color: AppColors.primary.withValues(alpha: 0.06),
                blurRadius: 16,
                offset: const Offset(0, 6),
              ),
            ],
          ),
          child: ClipRRect(
            borderRadius: BorderRadius.circular(AppRadius.medium - 1),
            child: Stack(
              fit: StackFit.expand,
              children: [
                if (hasPreview)
                  Image.memory(
                    encodedPreview,
                    fit: BoxFit.contain,
                    filterQuality: FilterQuality.none,
                    errorBuilder: (context, error, stackTrace) {
                      return _EmptyPreviewContent(
                        icon: Icons.broken_image_outlined,
                        title: 'Preview unavailable',
                        subtitle: 'Try uploading the image again.',
                      );
                    },
                  )
                else
                  const _EmptyPreviewContent(
                    icon: Icons.screenshot_monitor_outlined,
                    title: 'E-Paper preview',
                    subtitle:
                        'Your processed image will appear here\nat display size.',
                  ),
                if (isLoading)
                  ColoredBox(
                    color: Colors.white.withValues(alpha: 0.72),
                    child: const Center(
                      child: CircularProgressIndicator(strokeWidth: 2.5),
                    ),
                  ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Uint8List? _resolvePreview(Uint8List? bytes) {
    if (bytes == null || bytes.isEmpty) {
      return null;
    }
    if (!isEncodedImageBytes(bytes)) {
      return null;
    }
    return bytes;
  }
}

class _EmptyPreviewContent extends StatelessWidget {
  const _EmptyPreviewContent({
    required this.icon,
    required this.title,
    required this.subtitle,
  });

  final IconData icon;
  final String title;
  final String subtitle;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;

    return Padding(
      padding: const EdgeInsets.all(AppSpacing.lg),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(icon, size: 40, color: AppColors.textMuted),
          const SizedBox(height: AppSpacing.sm),
          Text(
            title,
            textAlign: TextAlign.center,
            style: textTheme.titleSmall?.copyWith(
              color: AppColors.textSecondary,
            ),
          ),
          const SizedBox(height: AppSpacing.xs),
          Text(
            subtitle,
            textAlign: TextAlign.center,
            style: textTheme.bodySmall?.copyWith(color: AppColors.textMuted),
          ),
        ],
      ),
    );
  }
}
