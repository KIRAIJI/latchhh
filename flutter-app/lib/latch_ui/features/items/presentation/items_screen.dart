import 'package:flutter/material.dart';

import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_spacing.dart';
import 'widgets/item_compact_card.dart';
import 'widgets/item_details_bottom_sheet.dart';
import 'widgets/items_bottom_sheet.dart';

typedef ItemDetailsRequestCallback = void Function(ItemDetailsData details);

class ItemsScreen extends StatelessWidget {
  const ItemsScreen({
    super.key,
    required this.onAddItemPressed,
    this.onRetryPressed,
    this.onRefreshPressed,
    this.onItemDetailsRequested,
    this.items = const [],
    this.isLoading = false,
    this.errorMessage,
    this.itemCount,
    this.headerTitle = 'My Items',
    this.useHorizontalList = false,
    this.mapContent,
  });

  final VoidCallback? onAddItemPressed;
  final VoidCallback? onRetryPressed;
  final VoidCallback? onRefreshPressed;
  final ItemDetailsRequestCallback? onItemDetailsRequested;
  final List<ItemCompactCardData> items;
  final bool isLoading;
  final String? errorMessage;
  final int? itemCount;
  final String headerTitle;
  final bool useHorizontalList;
  final Widget? mapContent;

  List<ItemCompactCardData> _resolveItems(BuildContext sheetContext) {
    if (onItemDetailsRequested == null) {
      return items;
    }

    return items.map((item) {
      if (item.onTap != null || item.itemDetails == null) {
        return item;
      }

      final details = item.itemDetails!;
      return ItemCompactCardData(
        itemName: item.itemName,
        connectionStatusLabel: item.connectionStatusLabel,
        connectionStatusIcon: item.connectionStatusIcon,
        connectionStatusColor: item.connectionStatusColor,
        batteryLabel: item.batteryLabel,
        locationLabel: item.locationLabel,
        locationTimeLabel: item.locationTimeLabel,
        selected: item.selected,
        itemDetails: details,
        onTap: () {
          Navigator.pop(sheetContext);
          WidgetsBinding.instance.addPostFrameCallback((_) {
            onItemDetailsRequested?.call(details);
          });
        },
      );
    }).toList();
  }

  Future<void> _showItemListModal(BuildContext context) async {
    final result = await showModalBottomSheet<ItemsSheetAction>(
      context: context,
      isScrollControlled: true,
      useSafeArea: true,
      backgroundColor: Colors.transparent,
      isDismissible: true,
      enableDrag: true,
      builder: (sheetContext) {
        final resolvedItems = _resolveItems(sheetContext);

        return Padding(
          padding: EdgeInsets.only(
            top: MediaQuery.paddingOf(sheetContext).top + AppSpacing.sm,
          ),
          child: DraggableScrollableSheet(
            expand: false,
            initialChildSize: 0.55,
            minChildSize: 0.35,
            maxChildSize: 0.90,
            builder: (context, scrollController) {
              return ItemsBottomSheet(
                scrollController: scrollController,
                headerTitle: headerTitle,
                itemCount:
                    itemCount ??
                    (resolvedItems.isEmpty ? null : resolvedItems.length),
                items: resolvedItems,
                isLoading: isLoading,
                errorMessage: errorMessage,
                onRetryPressed: onRetryPressed,
                useHorizontalList: useHorizontalList,
              );
            },
          ),
        );
      },
    );

    if (result == ItemsSheetAction.addItem && context.mounted) {
      onAddItemPressed?.call();
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      body: Stack(
        children: [
          Positioned.fill(
            child:
                mapContent ??
                ColoredBox(
                  color: AppColors.surfaceVariant,
                  child: Center(
                    child: Icon(
                      Icons.map_outlined,
                      size: 48,
                      color: AppColors.textMuted.withValues(alpha: 0.6),
                    ),
                  ),
                ),
          ),
          Positioned(
            top: MediaQuery.paddingOf(context).top + AppSpacing.sm,
            right: AppSpacing.md,
            child: Material(
              color: AppColors.surface,
              elevation: 2,
              shape: const CircleBorder(),
              child: IconButton(
                onPressed: isLoading ? null : onRefreshPressed,
                tooltip: 'Refresh map locations',
                icon: isLoading
                    ? const SizedBox.square(
                        dimension: 20,
                        child: CircularProgressIndicator(strokeWidth: 2),
                      )
                    : const Icon(Icons.refresh_rounded),
                color: AppColors.textPrimary,
              ),
            ),
          ),
          Positioned(
            left: AppSpacing.md,
            right: AppSpacing.md,
            bottom: AppSpacing.lg,
            child: Row(
              children: [
                Expanded(
                  child: OutlinedButton.icon(
                    onPressed: () => _showItemListModal(context),
                    icon: const Icon(Icons.list_alt_rounded),
                    label: const Text('View Item List'),
                    style: OutlinedButton.styleFrom(
                      minimumSize: const Size(0, 48),
                      padding: const EdgeInsets.symmetric(
                        horizontal: AppSpacing.md,
                      ),
                      side: const BorderSide(color: AppColors.border),
                      foregroundColor: AppColors.textPrimary,
                      backgroundColor: AppColors.surface,
                    ),
                  ),
                ),
                const SizedBox(width: AppSpacing.sm),
                Material(
                  color: AppColors.primary,
                  elevation: 0,
                  shape: const CircleBorder(),
                  child: IconButton(
                    onPressed: onAddItemPressed,
                    tooltip: 'Add Item',
                    icon: const Icon(Icons.add_rounded),
                    color: AppColors.onPrimary,
                    style: IconButton.styleFrom(
                      minimumSize: const Size(48, 48),
                      tapTargetSize: MaterialTapTargetSize.padded,
                    ),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
