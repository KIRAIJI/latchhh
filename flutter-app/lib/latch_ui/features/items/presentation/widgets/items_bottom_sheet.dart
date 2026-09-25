import 'package:flutter/material.dart';

import '../../../../core/components/buttons/latch_button.dart';
import '../../../../core/components/feedback/latch_empty_state.dart';
import '../../../../core/components/feedback/latch_error_state.dart';
import '../../../../core/components/feedback/latch_loading_state.dart';
import '../../../../core/theme/app_colors.dart';
import '../../../../core/theme/app_icons.dart';
import '../../../../core/theme/app_radius.dart';
import '../../../../core/theme/app_spacing.dart';
import 'item_compact_card.dart';

enum ItemsSheetAction { addItem }

class ItemsBottomSheet extends StatelessWidget {
  const ItemsBottomSheet({
    super.key,
    required this.scrollController,
    this.headerTitle = 'My Items',
    this.itemCount,
    this.items = const [],
    this.isLoading = false,
    this.errorMessage,
    this.onRetryPressed,
    this.useHorizontalList = false,
  });

  final ScrollController scrollController;
  final String headerTitle;
  final int? itemCount;
  final List<ItemCompactCardData> items;
  final bool isLoading;
  final String? errorMessage;
  final VoidCallback? onRetryPressed;
  final bool useHorizontalList;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;

    return Material(
      color: AppColors.surface,
      borderRadius: const BorderRadius.vertical(
        top: Radius.circular(AppRadius.large),
      ),
      clipBehavior: Clip.antiAlias,
      child: DecoratedBox(
        decoration: BoxDecoration(
          color: AppColors.surface,
          borderRadius: const BorderRadius.vertical(
            top: Radius.circular(AppRadius.large),
          ),
          border: Border.all(color: AppColors.border),
        ),
        child: SizedBox.expand(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              _SheetHeader(
                headerTitle: headerTitle,
                itemCount: itemCount,
                textTheme: textTheme,
              ),
              Expanded(child: _buildScrollableContent(context)),
              _AddItemFooter(
                onPressed: () =>
                    Navigator.of(context).pop(ItemsSheetAction.addItem),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildScrollableContent(BuildContext context) {
    if (isLoading) {
      return const LatchLoadingState();
    }

    if (errorMessage != null) {
      return LatchErrorState(
        message: errorMessage!,
        onRetryPressed: onRetryPressed,
      );
    }

    if (items.isEmpty) {
      return const LatchEmptyState(
        icon: AppIcons.items,
        title: 'No items added.',
      );
    }

    if (useHorizontalList) {
      return ListView.builder(
        controller: scrollController,
        shrinkWrap: false,
        padding: const EdgeInsets.fromLTRB(
          AppSpacing.lg,
          0,
          AppSpacing.lg,
          AppSpacing.sm,
        ),
        itemCount: 1,
        itemBuilder: (context, index) {
          return SizedBox(
            height: 148,
            child: ListView.separated(
              scrollDirection: Axis.horizontal,
              itemCount: items.length,
              separatorBuilder: (_, _) => const SizedBox(width: AppSpacing.sm),
              itemBuilder: (context, itemIndex) {
                final item = items[itemIndex];
                return SizedBox(
                  width: 280,
                  child: ItemCompactCard(
                    itemName: item.itemName,
                    connectionStatusLabel: item.connectionStatusLabel,
                    connectionStatusIcon: item.connectionStatusIcon,
                    connectionStatusColor: item.connectionStatusColor,
                    batteryLabel: item.batteryLabel,
                    locationLabel: item.locationLabel,
                    locationTimeLabel: item.locationTimeLabel,
                    itemDetails: item.itemDetails,
                    selected: item.selected,
                    onTap: item.onTap,
                  ),
                );
              },
            ),
          );
        },
      );
    }

    return ListView.builder(
      controller: scrollController,
      shrinkWrap: false,
      padding: const EdgeInsets.fromLTRB(
        AppSpacing.lg,
        0,
        AppSpacing.lg,
        AppSpacing.sm,
      ),
      itemCount: items.length,
      itemBuilder: (context, index) {
        final item = items[index];
        return Padding(
          padding: EdgeInsets.only(
            bottom: index < items.length - 1 ? AppSpacing.sm : 0,
          ),
          child: ItemCompactCard(
            itemName: item.itemName,
            connectionStatusLabel: item.connectionStatusLabel,
            connectionStatusIcon: item.connectionStatusIcon,
            connectionStatusColor: item.connectionStatusColor,
            batteryLabel: item.batteryLabel,
            locationLabel: item.locationLabel,
            locationTimeLabel: item.locationTimeLabel,
            itemDetails: item.itemDetails,
            selected: item.selected,
            onTap: item.onTap,
          ),
        );
      },
    );
  }
}

class _SheetHeader extends StatelessWidget {
  const _SheetHeader({
    required this.headerTitle,
    required this.itemCount,
    required this.textTheme,
  });

  final String headerTitle;
  final int? itemCount;
  final TextTheme textTheme;

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        const SizedBox(height: AppSpacing.sm),
        Center(
          child: Semantics(
            label: 'Sheet handle',
            child: Container(
              width: 40,
              height: 4,
              decoration: BoxDecoration(
                color: AppColors.divider,
                borderRadius: BorderRadius.circular(AppRadius.pill),
              ),
            ),
          ),
        ),
        Padding(
          padding: const EdgeInsets.fromLTRB(
            AppSpacing.lg,
            AppSpacing.md,
            AppSpacing.lg,
            AppSpacing.sm,
          ),
          child: Row(
            children: [
              Expanded(child: Text(headerTitle, style: textTheme.titleLarge)),
              if (itemCount != null)
                Text('$itemCount', style: textTheme.bodySmall),
            ],
          ),
        ),
      ],
    );
  }
}

class _AddItemFooter extends StatelessWidget {
  const _AddItemFooter({required this.onPressed});

  final VoidCallback onPressed;

  @override
  Widget build(BuildContext context) {
    return DecoratedBox(
      decoration: const BoxDecoration(
        color: AppColors.surface,
        border: Border(top: BorderSide(color: AppColors.divider)),
      ),
      child: SafeArea(
        top: false,
        child: Padding(
          padding: const EdgeInsets.fromLTRB(
            AppSpacing.lg,
            AppSpacing.sm,
            AppSpacing.lg,
            AppSpacing.md,
          ),
          child: LatchButton(
            label: 'Add Item',
            leadingIcon: Icons.add_rounded,
            fullWidth: true,
            onPressed: onPressed,
          ),
        ),
      ),
    );
  }
}
