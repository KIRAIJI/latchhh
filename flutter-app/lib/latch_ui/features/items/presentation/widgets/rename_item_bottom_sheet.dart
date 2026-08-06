import 'package:flutter/material.dart';

import '../../../../core/components/buttons/latch_button.dart';
import '../../../../core/components/inputs/latch_text_field.dart';
import '../../../../core/theme/app_colors.dart';
import '../../../../core/theme/app_icons.dart';
import '../../../../core/theme/app_radius.dart';
import '../../../../core/theme/app_spacing.dart';

typedef RenameItemSubmitCallback = void Function(String itemName);

class RenameItemBottomSheet extends StatefulWidget {
  const RenameItemBottomSheet({
    super.key,
    required this.deviceId,
    required this.initialItemName,
    required this.onRenamePressed,
    required this.onCancelPressed,
    this.isLoading = false,
    this.itemNameError,
  });

  final String deviceId;
  final String initialItemName;
  final RenameItemSubmitCallback? onRenamePressed;
  final VoidCallback? onCancelPressed;
  final bool isLoading;
  final String? itemNameError;

  @override
  State<RenameItemBottomSheet> createState() => _RenameItemBottomSheetState();
}

class _RenameItemBottomSheetState extends State<RenameItemBottomSheet> {
  late final TextEditingController _itemNameController;

  @override
  void initState() {
    super.initState();
    _itemNameController = TextEditingController(text: widget.initialItemName);
  }

  @override
  void dispose() {
    _itemNameController.dispose();
    super.dispose();
  }

  void _handleSubmit() {
    if (widget.isLoading) {
      return;
    }

    widget.onRenamePressed?.call(_itemNameController.text);
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
              Text('Rename Item', style: textTheme.titleLarge),
              const SizedBox(height: AppSpacing.lg),
              Text('Device ID', style: textTheme.labelMedium),
              const SizedBox(height: AppSpacing.xs),
              Text(
                widget.deviceId,
                style: textTheme.bodyMedium?.copyWith(
                  color: AppColors.textSecondary,
                ),
              ),
              const SizedBox(height: AppSpacing.md),
              LatchTextField(
                controller: _itemNameController,
                label: 'Item Name',
                hint: 'Black Backpack',
                errorText: widget.itemNameError,
                textInputAction: TextInputAction.done,
                enabled: !widget.isLoading,
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
                  label: 'Rename Item',
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
