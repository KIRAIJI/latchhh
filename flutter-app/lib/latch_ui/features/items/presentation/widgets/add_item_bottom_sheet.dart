import 'package:flutter/material.dart';

import '../../../../core/components/buttons/latch_button.dart';
import '../../../../core/components/inputs/latch_text_field.dart';
import '../../../../core/theme/app_colors.dart';
import '../../../../core/theme/app_icons.dart';
import '../../../../core/theme/app_radius.dart';
import '../../../../core/theme/app_spacing.dart';

typedef AddItemSubmitCallback = void Function(String deviceId, String itemName);

class AddItemBottomSheet extends StatefulWidget {
  const AddItemBottomSheet({
    super.key,
    required this.onAddItemPressed,
    required this.onCancelPressed,
    this.isLoading = false,
    this.deviceIdError,
    this.itemNameError,
    this.errorMessage,
  });

  final AddItemSubmitCallback? onAddItemPressed;
  final VoidCallback? onCancelPressed;
  final bool isLoading;
  final String? deviceIdError;
  final String? itemNameError;
  final String? errorMessage;

  @override
  State<AddItemBottomSheet> createState() => _AddItemBottomSheetState();
}

class _AddItemBottomSheetState extends State<AddItemBottomSheet> {
  final TextEditingController _deviceIdController = TextEditingController();
  final TextEditingController _itemNameController = TextEditingController();

  @override
  void dispose() {
    _deviceIdController.dispose();
    _itemNameController.dispose();
    super.dispose();
  }

  void _handleSubmit() {
    if (widget.isLoading) {
      return;
    }

    widget.onAddItemPressed?.call(
      _deviceIdController.text,
      _itemNameController.text,
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
              Text('Add Item', style: textTheme.titleLarge),
              const SizedBox(height: AppSpacing.sm),
              Text(
                'Enter the device ID and a name for your item.',
                style: textTheme.bodyMedium,
              ),
              if (widget.errorMessage != null) ...[
                const SizedBox(height: AppSpacing.md),
                Semantics(
                  liveRegion: true,
                  child: Container(
                    key: const Key('add-item-form-error'),
                    padding: const EdgeInsets.all(AppSpacing.md),
                    decoration: BoxDecoration(
                      color: AppColors.error.withValues(alpha: 0.08),
                      border: Border.all(
                        color: AppColors.error.withValues(alpha: 0.35),
                      ),
                      borderRadius: BorderRadius.circular(AppRadius.large),
                    ),
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Icon(
                          Icons.error_outline_rounded,
                          color: AppColors.error,
                          size: 20,
                        ),
                        const SizedBox(width: AppSpacing.sm),
                        Expanded(
                          child: Text(
                            widget.errorMessage!,
                            style: textTheme.bodyMedium?.copyWith(
                              color: AppColors.error,
                              fontWeight: FontWeight.w600,
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ],
              const SizedBox(height: AppSpacing.lg),
              LatchTextField(
                controller: _deviceIdController,
                label: 'Device ID',
                hint: 'LATCH-7K3M-P9Q2',
                helperText: 'Format: LATCH-XXXX-XXXX',
                errorText: widget.deviceIdError,
                textInputAction: TextInputAction.next,
                prefixIcon: const Icon(AppIcons.nfc),
              ),
              const SizedBox(height: AppSpacing.md),
              LatchTextField(
                controller: _itemNameController,
                label: 'Item Name',
                hint: 'Black Backpack',
                errorText: widget.itemNameError,
                textInputAction: TextInputAction.done,
                prefixIcon: const Icon(AppIcons.items),
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
                  label: 'Add Item',
                  fullWidth: true,
                  isLoading: widget.isLoading,
                  onPressed: _handleSubmit,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
