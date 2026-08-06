import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';

import '../../../../main.dart';
import '../../../core/components/buttons/latch_button.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_icons.dart';
import '../../../core/theme/app_spacing.dart';
import 'epaper_image_editor_screen.dart';
import 'widgets/epaper_preview_frame.dart';
import 'widgets/epaper_status_banner.dart';
import 'widgets/epaper_write_modal.dart';

/// User-facing E-Paper tab that drives the existing [UploaderPage] pipeline
/// through [UploaderBridge] without duplicating processing or NFC logic.
class EpaperTabScreen extends StatefulWidget {
  const EpaperTabScreen({super.key, required this.bridge});

  final UploaderBridge bridge;

  @override
  State<EpaperTabScreen> createState() => _EpaperTabScreenState();
}

class _EpaperTabScreenState extends State<EpaperTabScreen> {
  final ImagePicker _picker = ImagePicker();

  @override
  void initState() {
    super.initState();
    widget.bridge.addListener(_onBridgeChanged);
  }

  @override
  void dispose() {
    widget.bridge.removeListener(_onBridgeChanged);
    super.dispose();
  }

  void _onBridgeChanged() {
    if (mounted) {
      setState(() {});
    }
  }

  bool get _isBusy => widget.bridge.busy;

  bool get _isProcessing {
    final status = widget.bridge.status.toLowerCase();
    return status.contains('preparing') ||
        status.contains('updating') ||
        status.contains('refreshing') ||
        status.contains('continuing') ||
        status.contains('restarting') ||
        status.contains('checking');
  }

  bool get _hasPreview {
    final bytes = widget.bridge.previewPngBytes;
    return bytes != null && EpaperPreviewFrame.isEncodedImageBytes(bytes);
  }

  bool get _canWrite => _hasPreview && !_isBusy;

  bool get _hasUploadedImage {
    final bytes = widget.bridge.originalBytes;
    return bytes != null && bytes.isNotEmpty;
  }

  bool get _canEditImage => _hasUploadedImage && !_isBusy;

  Future<void> _pickImageFromGallery() async {
    if (_isBusy) {
      return;
    }

    try {
      final file = await _picker.pickImage(
        source: ImageSource.gallery,
        maxWidth: 1664,
        maxHeight: 1664,
        imageQuality: 100,
        requestFullMetadata: false,
      );
      if (file == null) {
        return;
      }

      final bytes = await file.readAsBytes();
      if (!mounted) {
        return;
      }

      await _openEditorAndIngest(bytes);
    } catch (error) {
      if (!mounted) {
        return;
      }
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('Could not load image: $error')));
    }
  }

  Future<void> _editCurrentImage() async {
    if (!_canEditImage) {
      return;
    }

    final bytes = widget.bridge.originalBytes;
    if (bytes == null) {
      return;
    }

    try {
      await _openEditorAndIngest(bytes);
    } catch (error) {
      if (!mounted) {
        return;
      }
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('Could not edit image: $error')));
    }
  }

  Future<void> _openEditorAndIngest(Uint8List sourceBytes) async {
    final editedBytes = await EpaperImageEditorScreen.open(
      context,
      imageBytes: sourceBytes,
    );
    if (editedBytes == null || !mounted) {
      return;
    }

    await widget.bridge.requestIngestSelectedImageBytes(editedBytes);
  }

  Future<void> _writeToEpaper() async {
    if (!_canWrite) {
      return;
    }

    await showModalBottomSheet<void>(
      context: context,
      isScrollControlled: true,
      useSafeArea: true,
      isDismissible: false,
      enableDrag: false,
      backgroundColor: Colors.transparent,
      builder: (sheetContext) {
        return EpaperWriteModal(
          bridge: widget.bridge,
          onClose: () => Navigator.pop(sheetContext),
        );
      },
    );
  }

  Widget _buildBottomActions() {
    final keyboardInset = MediaQuery.viewInsetsOf(context).bottom;

    return Padding(
      padding: EdgeInsets.fromLTRB(
        AppSpacing.screenHorizontal,
        AppSpacing.md,
        AppSpacing.screenHorizontal,
        AppSpacing.md + keyboardInset,
      ),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          if (!_hasUploadedImage)
            LatchButton(
              label: 'Upload Image',
              leadingIcon: Icons.photo_library_outlined,
              variant: LatchButtonVariant.primary,
              fullWidth: true,
              enabled: !_isBusy,
              onPressed: _pickImageFromGallery,
            )
          else
            Row(
              children: [
                Expanded(
                  child: LatchButton(
                    label: 'Change Image',
                    leadingIcon: Icons.photo_library_outlined,
                    variant: LatchButtonVariant.secondary,
                    fullWidth: true,
                    enabled: !_isBusy,
                    onPressed: _pickImageFromGallery,
                  ),
                ),
                const SizedBox(width: AppSpacing.sm),
                Expanded(
                  child: LatchButton(
                    label: 'Edit Image',
                    leadingIcon: Icons.edit_outlined,
                    variant: LatchButtonVariant.secondary,
                    fullWidth: true,
                    enabled: _canEditImage,
                    onPressed: _editCurrentImage,
                  ),
                ),
              ],
            ),
          const SizedBox(height: 12),
          LatchButton(
            label: 'Write to E-Paper',
            leadingIcon: AppIcons.nfc,
            variant: LatchButtonVariant.primary,
            fullWidth: true,
            enabled: _canWrite,
            onPressed: _writeToEpaper,
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    final status = EpaperStatusPresentation.fromStatus(
      status: widget.bridge.status,
      isBusy: _isBusy,
      isProcessing: _isProcessing,
    );

    return Scaffold(
      backgroundColor: AppColors.background,
      body: SafeArea(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Padding(
              padding: const EdgeInsets.fromLTRB(
                AppSpacing.screenHorizontal,
                AppSpacing.md,
                AppSpacing.screenHorizontal,
                AppSpacing.sm,
              ),
              child: Text('E-Paper', style: textTheme.headlineMedium),
            ),
            Expanded(
              child: SingleChildScrollView(
                padding: const EdgeInsets.fromLTRB(
                  AppSpacing.screenHorizontal,
                  AppSpacing.sm,
                  AppSpacing.screenHorizontal,
                  AppSpacing.sm,
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    Text(
                      'Upload an image, review the preview at display size, then write it to your tag.',
                      style: textTheme.bodyMedium?.copyWith(
                        color: AppColors.textSecondary,
                      ),
                    ),
                    const SizedBox(height: AppSpacing.lg),
                    EpaperStatusBanner(presentation: status),
                    const SizedBox(height: AppSpacing.sm),
                    Center(
                      child: LayoutBuilder(
                        builder: (context, constraints) {
                          final maxWidth = constraints.maxWidth.clamp(
                            200.0,
                            300.0,
                          );

                          return EpaperPreviewFrame(
                            previewBytes: widget.bridge.previewPngBytes,
                            isLoading: _isProcessing,
                            maxWidth: maxWidth,
                          );
                        },
                      ),
                    ),
                  ],
                ),
              ),
            ),
            _buildBottomActions(),
          ],
        ),
      ),
    );
  }
}
