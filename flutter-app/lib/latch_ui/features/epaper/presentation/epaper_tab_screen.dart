import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';

import '../../../../main.dart';
import '../../../core/components/buttons/latch_button.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_icons.dart';
import '../../../core/theme/app_spacing.dart';
import 'epaper_image_editor_screen.dart';
import 'epaper_layout.dart';
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
  EpaperLayout? _layout;

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
      await _openEditorAndIngest(
        _layout == null ? bytes : null,
        layout: _layout,
      );
    } catch (error) {
      if (!mounted) {
        return;
      }
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('Could not edit image: $error')));
    }
  }

  Future<void> _openEditorAndIngest(
    Uint8List? sourceBytes, {
    EpaperLayout? layout,
  }) async {
    final result = await EpaperImageEditorScreen.open(
      context,
      imageBytes: sourceBytes,
      layout: layout,
    );
    if (result == null || !mounted) {
      return;
    }
    _layout = result.layout;
    await widget.bridge.requestIngestSelectedImageBytes(result.pngBytes);
  }

  Future<void> _chooseTemplate() async {
    final template = await showModalBottomSheet<EpaperTemplate>(
      context: context,
      useSafeArea: true,
      builder: (context) => SafeArea(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            const Padding(
              padding: EdgeInsets.all(16),
              child: Text('Choose a template'),
            ),
            for (final template in EpaperTemplate.values)
              ListTile(
                title: Text(template.title),
                trailing: const Icon(Icons.chevron_right),
                onTap: () => Navigator.pop(context, template),
              ),
          ],
        ),
      ),
    );
    if (!mounted || template == null) return;
    final values = await showDialog<List<String>>(
      context: context,
      builder: (_) => _TemplateForm(template: template),
    );
    if (!mounted || values == null) return;
    await _openEditorAndIngest(null, layout: template.create(values));
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
          Row(
            children: [
              Expanded(
                child: LatchButton(
                  label: 'Templates',
                  leadingIcon: Icons.dashboard_customize_outlined,
                  variant: LatchButtonVariant.secondary,
                  enabled: !_isBusy,
                  onPressed: _chooseTemplate,
                ),
              ),
              const SizedBox(width: AppSpacing.sm),
              Expanded(
                child: LatchButton(
                  label: 'Blank canvas',
                  leadingIcon: Icons.note_add_outlined,
                  variant: LatchButtonVariant.secondary,
                  enabled: !_isBusy,
                  onPressed: () => _openEditorAndIngest(null),
                ),
              ),
            ],
          ),
          const SizedBox(height: 12),
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
                    label: 'Edit Layout',
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
                      'Choose a template, start a blank canvas, or add an image. Review the final display preview before writing.',
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

class _TemplateForm extends StatefulWidget {
  const _TemplateForm({required this.template});
  final EpaperTemplate template;
  @override
  State<_TemplateForm> createState() => _TemplateFormState();
}

class _TemplateFormState extends State<_TemplateForm> {
  final _formKey = GlobalKey<FormState>();
  late final _controllers = List.generate(
    widget.template.fields.length,
    (_) => TextEditingController(),
  );
  @override
  void dispose() {
    for (final controller in _controllers) {
      controller.dispose();
    }
    super.dispose();
  }

  @override
  Widget build(BuildContext context) => AlertDialog(
    title: Text(widget.template.title),
    content: SizedBox(
      width: 360,
      child: Form(
        key: _formKey,
        child: SingleChildScrollView(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              for (var i = 0; i < _controllers.length; i++)
                Padding(
                  padding: const EdgeInsets.only(bottom: 12),
                  child: TextFormField(
                    controller: _controllers[i],
                    decoration: InputDecoration(
                      labelText: widget.template.fields[i],
                    ),
                    maxLength: widget.template.fields[i].startsWith('Message')
                        ? 300
                        : 100,
                    maxLines: widget.template.fields[i].startsWith('Message')
                        ? 3
                        : 1,
                    keyboardType: widget.template.fields[i].contains('number')
                        ? TextInputType.phone
                        : widget.template.fields[i].startsWith('Email')
                        ? TextInputType.emailAddress
                        : TextInputType.text,
                    validator: (value) =>
                        !widget.template.fields[i].contains('(optional)') &&
                            (value?.trim().isEmpty ?? true)
                        ? 'Enter ${widget.template.fields[i].toLowerCase()}.'
                        : null,
                  ),
                ),
            ],
          ),
        ),
      ),
    ),
    actions: [
      TextButton(
        onPressed: () => Navigator.pop(context),
        child: const Text('Cancel'),
      ),
      FilledButton(
        onPressed: () {
          if (_formKey.currentState!.validate()) {
            Navigator.pop(
              context,
              _controllers.map((controller) => controller.text).toList(),
            );
          }
        },
        child: const Text('Use template'),
      ),
    ],
  );
}
