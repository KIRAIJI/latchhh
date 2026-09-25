import 'package:flutter/material.dart';

import '../../../../core/components/cards/latch_card.dart';
import '../../../../core/services/location_address_resolver.dart';
import '../../../../core/theme/app_spacing.dart';
import 'item_details_bottom_sheet.dart';

class ItemCompactCardData {
  const ItemCompactCardData({
    required this.itemName,
    required this.connectionStatusLabel,
    required this.connectionStatusIcon,
    required this.connectionStatusColor,
    required this.batteryLabel,
    required this.locationLabel,
    required this.locationTimeLabel,
    this.selected = false,
    this.onTap,
    this.itemDetails,
  });

  final String itemName;
  final String connectionStatusLabel;
  final IconData connectionStatusIcon;
  final Color connectionStatusColor;
  final String batteryLabel;
  final String locationLabel;
  final String locationTimeLabel;
  final bool selected;
  final VoidCallback? onTap;
  final ItemDetailsData? itemDetails;
}

class ItemCompactCard extends StatelessWidget {
  const ItemCompactCard({
    super.key,
    required this.itemName,
    required this.connectionStatusLabel,
    required this.connectionStatusIcon,
    required this.connectionStatusColor,
    required this.batteryLabel,
    required this.locationLabel,
    required this.locationTimeLabel,
    this.selected = false,
    this.onTap,
    this.itemDetails,
  });

  final String itemName;
  final String connectionStatusLabel;
  final IconData connectionStatusIcon;
  final Color connectionStatusColor;
  final String batteryLabel;
  final String locationLabel;
  final String locationTimeLabel;
  final bool selected;
  final VoidCallback? onTap;
  final ItemDetailsData? itemDetails;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;

    return LatchCard(
      selected: selected,
      onTap: onTap,
      padding: const EdgeInsets.all(AppSpacing.sm + AppSpacing.xs),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            children: [
              Expanded(
                child: Text(
                  itemName,
                  style: textTheme.titleMedium,
                  maxLines: 1,
                  overflow: TextOverflow.ellipsis,
                ),
              ),
              const SizedBox(width: AppSpacing.sm),
              Icon(
                connectionStatusIcon,
                size: 16,
                color: connectionStatusColor,
              ),
              const SizedBox(width: AppSpacing.xs),
              Text(
                connectionStatusLabel,
                style: textTheme.labelSmall?.copyWith(
                  color: connectionStatusColor,
                ),
              ),
            ],
          ),
          const SizedBox(height: AppSpacing.xs),
          Text(batteryLabel, style: textTheme.bodySmall),
          const SizedBox(height: AppSpacing.xs),
          _LocationLabel(
            fallback: locationLabel,
            details: itemDetails,
            style: textTheme.bodyMedium,
          ),
          if (locationTimeLabel.isNotEmpty) ...[
            const SizedBox(height: AppSpacing.xs),
            Text(locationTimeLabel, style: textTheme.labelSmall),
          ],
        ],
      ),
    );
  }
}

class _LocationLabel extends StatelessWidget {
  const _LocationLabel({
    required this.fallback,
    required this.details,
    required this.style,
  });

  final String fallback;
  final ItemDetailsData? details;
  final TextStyle? style;

  @override
  Widget build(BuildContext context) {
    final details = this.details;
    if (details?.locationPlaceName != null) {
      return Text(
        fallback,
        style: style,
        maxLines: 1,
        overflow: TextOverflow.ellipsis,
      );
    }
    if (details?.locationLatitude == null ||
        details?.locationLongitude == null) {
      return Text(
        fallback,
        style: style,
        maxLines: 1,
        overflow: TextOverflow.ellipsis,
      );
    }

    return FutureBuilder<String>(
      future: LocationAddressResolver.shared.resolve(
        details!.locationLatitude!,
        details.locationLongitude!,
      ),
      builder: (context, snapshot) => Text(
        snapshot.data?.trim().isNotEmpty == true ? snapshot.data! : fallback,
        style: style,
        maxLines: 1,
        overflow: TextOverflow.ellipsis,
      ),
    );
  }
}
