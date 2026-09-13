import 'package:flutter/material.dart';

import '../../../../core/components/buttons/latch_button.dart';
import '../../../../core/components/indicators/cellular_signal_strength_indicator.dart';
import '../../../../core/services/location_address_resolver.dart';
import '../../../../core/theme/app_colors.dart';
import '../../../../core/theme/app_icons.dart';
import '../../../../core/theme/app_radius.dart';
import '../../../../core/theme/app_spacing.dart';

typedef LocationAddressLookup =
    Future<String> Function(double latitude, double longitude);

class ItemDetailsData {
  const ItemDetailsData({
    required this.itemId,
    required this.itemName,
    required this.deviceId,
    required this.connectionStatus,
    required this.connectionIcon,
    required this.connectionColor,
    this.lastCommunicationText,
    required this.locationTypeText,
    this.locationTimestampText,
    this.locationLatitude,
    this.locationLongitude,
    this.locationCoordinatesText,
    this.batteryPercentageText,
    this.batteryStatusText,
    required this.gnssStatusText,
    this.gnssTimestampText,
    this.satellitesText,
    this.hdopText,
    required this.signalLevelText,
    this.gsmCsqText,
    this.networkSignalBarCount,
    this.firmwareVersionText,
    this.resetReasonText,
    required this.geofenceSummaryText,
  });

  final int itemId;
  final String itemName;
  final String deviceId;
  final String connectionStatus;
  final IconData connectionIcon;
  final Color connectionColor;
  final String? lastCommunicationText;
  final String locationTypeText;
  final String? locationTimestampText;
  final double? locationLatitude;
  final double? locationLongitude;
  final String? locationCoordinatesText;
  final String? batteryPercentageText;
  final String? batteryStatusText;
  final String gnssStatusText;
  final String? gnssTimestampText;
  final String? satellitesText;
  final String? hdopText;
  final String signalLevelText;
  final String? gsmCsqText;
  final int? networkSignalBarCount;
  final String? firmwareVersionText;
  final String? resetReasonText;
  final String geofenceSummaryText;
}

class ItemDetailsBottomSheet extends StatelessWidget {
  const ItemDetailsBottomSheet({
    super.key,
    required this.itemId,
    required this.itemName,
    required this.deviceId,
    required this.connectionStatus,
    required this.connectionIcon,
    required this.connectionColor,
    this.lastCommunicationText,
    required this.locationTypeText,
    this.locationTimestampText,
    this.locationLatitude,
    this.locationLongitude,
    this.locationCoordinatesText,
    this.batteryPercentageText,
    this.batteryStatusText,
    required this.gnssStatusText,
    this.gnssTimestampText,
    this.satellitesText,
    this.hdopText,
    required this.signalLevelText,
    this.gsmCsqText,
    this.networkSignalBarCount,
    this.firmwareVersionText,
    this.resetReasonText,
    required this.geofenceSummaryText,
    this.onRenamePressed,
    this.onManageGeofencePressed,
    this.onViewHistoryPressed,
    this.onRemovePressed,
    this.locationAddressLookup,
  });

  factory ItemDetailsBottomSheet.fromData(
    ItemDetailsData details, {
    VoidCallback? onRenamePressed,
    VoidCallback? onManageGeofencePressed,
    VoidCallback? onViewHistoryPressed,
    VoidCallback? onRemovePressed,
    LocationAddressLookup? locationAddressLookup,
  }) {
    return ItemDetailsBottomSheet(
      itemId: details.itemId,
      itemName: details.itemName,
      deviceId: details.deviceId,
      connectionStatus: details.connectionStatus,
      connectionIcon: details.connectionIcon,
      connectionColor: details.connectionColor,
      lastCommunicationText: details.lastCommunicationText,
      locationTypeText: details.locationTypeText,
      locationTimestampText: details.locationTimestampText,
      locationLatitude: details.locationLatitude,
      locationLongitude: details.locationLongitude,
      locationCoordinatesText: details.locationCoordinatesText,
      batteryPercentageText: details.batteryPercentageText,
      batteryStatusText: details.batteryStatusText,
      gnssStatusText: details.gnssStatusText,
      gnssTimestampText: details.gnssTimestampText,
      satellitesText: details.satellitesText,
      hdopText: details.hdopText,
      signalLevelText: details.signalLevelText,
      gsmCsqText: details.gsmCsqText,
      networkSignalBarCount: details.networkSignalBarCount,
      firmwareVersionText: details.firmwareVersionText,
      resetReasonText: details.resetReasonText,
      geofenceSummaryText: details.geofenceSummaryText,
      onRenamePressed: onRenamePressed,
      onManageGeofencePressed: onManageGeofencePressed,
      onViewHistoryPressed: onViewHistoryPressed,
      onRemovePressed: onRemovePressed,
      locationAddressLookup: locationAddressLookup,
    );
  }

  final int itemId;
  final String itemName;
  final String deviceId;
  final String connectionStatus;
  final IconData connectionIcon;
  final Color connectionColor;
  final String? lastCommunicationText;
  final String locationTypeText;
  final String? locationTimestampText;
  final double? locationLatitude;
  final double? locationLongitude;
  final String? locationCoordinatesText;
  final String? batteryPercentageText;
  final String? batteryStatusText;
  final String gnssStatusText;
  final String? gnssTimestampText;
  final String? satellitesText;
  final String? hdopText;
  final String signalLevelText;
  final String? gsmCsqText;
  final int? networkSignalBarCount;
  final String? firmwareVersionText;
  final String? resetReasonText;
  final String geofenceSummaryText;
  final VoidCallback? onRenamePressed;
  final VoidCallback? onManageGeofencePressed;
  final VoidCallback? onViewHistoryPressed;
  final VoidCallback? onRemovePressed;
  final LocationAddressLookup? locationAddressLookup;

  String? get _batteryMainValue {
    if (batteryPercentageText != null) {
      return batteryPercentageText;
    }
    return batteryStatusText;
  }

  String? get _batterySupportingValue {
    if (batteryPercentageText != null && batteryStatusText != null) {
      return batteryStatusText;
    }
    return null;
  }

  String? get _gnssSupportingValue {
    final parts = <String>[?gnssTimestampText];
    if (parts.isEmpty) {
      return null;
    }
    return parts.join(' · ');
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
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
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
              const SizedBox(height: AppSpacing.md),
              _HeaderSection(
                itemName: itemName,
                deviceId: deviceId,
                connectionStatus: connectionStatus,
                connectionIcon: connectionIcon,
                connectionColor: connectionColor,
                textTheme: textTheme,
              ),
              const SizedBox(height: AppSpacing.md),
              _DeviceOverviewCard(
                connectionIcon: connectionIcon,
                connectionColor: connectionColor,
                connectionStatus: connectionStatus,
                lastCommunicationText: lastCommunicationText,
                batteryMainValue: _batteryMainValue,
                batterySupportingValue: _batterySupportingValue,
                locationTypeText: locationTypeText,
                locationTimestampText: locationTimestampText,
                locationLatitude: locationLatitude,
                locationLongitude: locationLongitude,
                locationCoordinatesText: locationCoordinatesText,
                locationAddressLookup: locationAddressLookup,
                textTheme: textTheme,
              ),
              const SizedBox(height: AppSpacing.md),
              Text('Tracking Status', style: textTheme.titleMedium),
              const SizedBox(height: AppSpacing.sm),
              _DetailsCard(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    _MetricTile(
                      icon: AppIcons.gps,
                      iconColor: AppColors.textPrimary,
                      label: 'Location status',
                      value: gnssStatusText,
                      supportingValue: _gnssSupportingValue,
                      textTheme: textTheme,
                    ),
                    const SizedBox(height: AppSpacing.md),
                    _MetricTile(
                      icon: Icons.signal_cellular_alt_rounded,
                      iconColor: AppColors.textPrimary,
                      label: 'Network Signal',
                      value: networkSignalBarCount == null
                          ? signalLevelText
                          : null,
                      valueWidget: networkSignalBarCount == null
                          ? null
                          : CellularSignalStrengthIndicator(
                              barCount: networkSignalBarCount!,
                              label: signalLevelText,
                            ),
                      textTheme: textTheme,
                    ),
                  ],
                ),
              ),
              if (firmwareVersionText != null || resetReasonText != null) ...[
                const SizedBox(height: AppSpacing.md),
                Text('Device Information', style: textTheme.titleMedium),
                const SizedBox(height: AppSpacing.sm),
                _DetailsCard(
                  child: _MetricTile(
                    icon: Icons.memory_rounded,
                    iconColor: AppColors.textPrimary,
                    label: 'Firmware',
                    value: firmwareVersionText ?? 'Not reported by tracker',
                    supportingValue: resetReasonText,
                    textTheme: textTheme,
                  ),
                ),
              ],
              const SizedBox(height: AppSpacing.md),
              _GeofenceCard(
                summaryText: geofenceSummaryText,
                onManagePressed: onManageGeofencePressed,
                textTheme: textTheme,
              ),
              const SizedBox(height: AppSpacing.md),
              LatchButton(
                label: 'View Location History',
                variant: LatchButtonVariant.secondary,
                fullWidth: true,
                onPressed: onViewHistoryPressed,
              ),
              const SizedBox(height: AppSpacing.lg),
              _ActionArea(
                onRenamePressed: onRenamePressed,
                onRemovePressed: onRemovePressed,
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class _HeaderSection extends StatelessWidget {
  const _HeaderSection({
    required this.itemName,
    required this.deviceId,
    required this.connectionStatus,
    required this.connectionIcon,
    required this.connectionColor,
    required this.textTheme,
  });

  final String itemName;
  final String deviceId;
  final String connectionStatus;
  final IconData connectionIcon;
  final Color connectionColor;
  final TextTheme textTheme;

  @override
  Widget build(BuildContext context) {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                itemName,
                style: textTheme.headlineMedium,
                maxLines: 2,
                overflow: TextOverflow.ellipsis,
              ),
              const SizedBox(height: AppSpacing.xs),
              Text(
                deviceId,
                style: textTheme.bodySmall?.copyWith(
                  color: AppColors.textSecondary,
                ),
                maxLines: 1,
                overflow: TextOverflow.ellipsis,
              ),
            ],
          ),
        ),
        const SizedBox(width: AppSpacing.sm),
        _ConnectionChip(
          label: connectionStatus,
          icon: connectionIcon,
          color: connectionColor,
          textTheme: textTheme,
        ),
      ],
    );
  }
}

class _ConnectionChip extends StatelessWidget {
  const _ConnectionChip({
    required this.label,
    required this.icon,
    required this.color,
    required this.textTheme,
  });

  final String label;
  final IconData icon;
  final Color color;
  final TextTheme textTheme;

  @override
  Widget build(BuildContext context) {
    return Semantics(
      label: label,
      child: Container(
        padding: const EdgeInsets.symmetric(
          horizontal: AppSpacing.sm,
          vertical: AppSpacing.xs,
        ),
        decoration: BoxDecoration(
          color: color.withValues(alpha: 0.12),
          borderRadius: BorderRadius.circular(AppRadius.pill),
          border: Border.all(color: color.withValues(alpha: 0.35)),
        ),
        child: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            Icon(icon, size: 14, color: color),
            const SizedBox(width: AppSpacing.xs),
            Text(
              label,
              style: textTheme.labelSmall?.copyWith(
                color: color,
                fontWeight: FontWeight.w600,
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _DeviceOverviewCard extends StatelessWidget {
  const _DeviceOverviewCard({
    required this.connectionIcon,
    required this.connectionColor,
    required this.connectionStatus,
    required this.lastCommunicationText,
    required this.batteryMainValue,
    required this.batterySupportingValue,
    required this.locationTypeText,
    required this.locationTimestampText,
    required this.locationLatitude,
    required this.locationLongitude,
    required this.locationCoordinatesText,
    required this.locationAddressLookup,
    required this.textTheme,
  });

  final IconData connectionIcon;
  final Color connectionColor;
  final String connectionStatus;
  final String? lastCommunicationText;
  final String? batteryMainValue;
  final String? batterySupportingValue;
  final String locationTypeText;
  final String? locationTimestampText;
  final double? locationLatitude;
  final double? locationLongitude;
  final String? locationCoordinatesText;
  final LocationAddressLookup? locationAddressLookup;
  final TextTheme textTheme;

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
      builder: (context, constraints) {
        final useTwoColumns = constraints.maxWidth >= 520;

        final connectionTile = _MetricTile(
          icon: connectionIcon,
          iconColor: connectionColor,
          label: 'Connection',
          value: connectionStatus,
          supportingValue: lastCommunicationText,
          textTheme: textTheme,
        );
        final batteryTile = _MetricTile(
          icon: AppIcons.battery,
          iconColor: AppColors.textPrimary,
          label: 'Estimated Battery',
          value: batteryMainValue,
          supportingValue: batterySupportingValue,
          textTheme: textTheme,
        );
        final locationTile = _MetricTile(
          icon: AppIcons.location,
          iconColor: AppColors.textPrimary,
          label: 'Location',
          valueWidget: _LocationValue(
            locationTypeText: locationTypeText,
            latitude: locationLatitude,
            longitude: locationLongitude,
            coordinatesText: locationCoordinatesText,
            addressLookup: locationAddressLookup,
            textTheme: textTheme,
          ),
          supportingValue: locationTimestampText,
          textTheme: textTheme,
        );

        if (useTwoColumns) {
          return _DetailsCard(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [
                Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Expanded(child: connectionTile),
                    const SizedBox(width: AppSpacing.md),
                    Expanded(child: batteryTile),
                  ],
                ),
                const SizedBox(height: AppSpacing.md),
                locationTile,
              ],
            ),
          );
        }

        return _DetailsCard(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              connectionTile,
              const SizedBox(height: AppSpacing.md),
              batteryTile,
              const SizedBox(height: AppSpacing.md),
              locationTile,
            ],
          ),
        );
      },
    );
  }
}

class _LocationValue extends StatelessWidget {
  const _LocationValue({
    required this.locationTypeText,
    required this.latitude,
    required this.longitude,
    required this.coordinatesText,
    required this.addressLookup,
    required this.textTheme,
  });

  final String locationTypeText;
  final double? latitude;
  final double? longitude;
  final String? coordinatesText;
  final LocationAddressLookup? addressLookup;
  final TextTheme textTheme;

  @override
  Widget build(BuildContext context) {
    final latitude = this.latitude;
    final longitude = this.longitude;
    final coordinatesText = this.coordinatesText;

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          locationTypeText,
          style: textTheme.bodyMedium?.copyWith(fontWeight: FontWeight.w500),
        ),
        if (latitude != null &&
            longitude != null &&
            coordinatesText != null) ...[
          const SizedBox(height: AppSpacing.xs),
          FutureBuilder<String>(
            future: (addressLookup ?? LocationAddressResolver.shared.resolve)(
              latitude,
              longitude,
            ),
            builder: (context, snapshot) {
              final placeName =
                  snapshot.connectionState == ConnectionState.waiting
                  ? 'Finding place name…'
                  : snapshot.hasError ||
                        snapshot.data == null ||
                        snapshot.data!.trim().isEmpty
                  ? 'Place name unavailable'
                  : snapshot.data!;
              return Text(
                placeName,
                style: textTheme.bodySmall?.copyWith(
                  color: AppColors.textSecondary,
                ),
                maxLines: 3,
                overflow: TextOverflow.ellipsis,
              );
            },
          ),
        ],
      ],
    );
  }
}

class _GeofenceCard extends StatelessWidget {
  const _GeofenceCard({
    required this.summaryText,
    required this.onManagePressed,
    required this.textTheme,
  });

  final String summaryText;
  final VoidCallback? onManagePressed;
  final TextTheme textTheme;

  @override
  Widget build(BuildContext context) {
    return _DetailsCard(
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Icon(Icons.fence_rounded, size: 20, color: AppColors.trackerAccent),
          const SizedBox(width: AppSpacing.sm),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text('Geofence', style: textTheme.labelSmall),
                const SizedBox(height: AppSpacing.xs),
                Text(
                  summaryText,
                  style: textTheme.bodyMedium,
                  maxLines: 2,
                  overflow: TextOverflow.ellipsis,
                ),
              ],
            ),
          ),
          if (onManagePressed != null) ...[
            const SizedBox(width: AppSpacing.sm),
            TextButton(
              onPressed: onManagePressed,
              style: TextButton.styleFrom(
                visualDensity: VisualDensity.compact,
                padding: const EdgeInsets.symmetric(horizontal: AppSpacing.sm),
              ),
              child: const Text('Manage'),
            ),
          ],
        ],
      ),
    );
  }
}

class _ActionArea extends StatelessWidget {
  const _ActionArea({
    required this.onRenamePressed,
    required this.onRemovePressed,
  });

  final VoidCallback? onRenamePressed;
  final VoidCallback? onRemovePressed;

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
      builder: (context, constraints) {
        final stackActions = constraints.maxWidth < 360;

        final renameButton = LatchButton(
          label: 'Rename Item',
          variant: LatchButtonVariant.secondary,
          fullWidth: true,
          onPressed: onRenamePressed,
        );
        final removeButton = LatchButton(
          label: 'Remove Device',
          variant: LatchButtonVariant.destructive,
          fullWidth: true,
          onPressed: onRemovePressed,
        );

        if (stackActions) {
          return Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              renameButton,
              const SizedBox(height: AppSpacing.sm),
              removeButton,
            ],
          );
        }

        return Row(
          children: [
            Expanded(child: renameButton),
            const SizedBox(width: AppSpacing.sm),
            Expanded(child: removeButton),
          ],
        );
      },
    );
  }
}

class _DetailsCard extends StatelessWidget {
  const _DetailsCard({required this.child});

  final Widget child;

  @override
  Widget build(BuildContext context) {
    return DecoratedBox(
      decoration: BoxDecoration(
        color: AppColors.surface,
        borderRadius: BorderRadius.circular(AppRadius.medium),
        border: Border.all(color: AppColors.border),
      ),
      child: Padding(
        padding: const EdgeInsets.all(AppSpacing.md),
        child: child,
      ),
    );
  }
}

class _MetricTile extends StatelessWidget {
  const _MetricTile({
    required this.icon,
    required this.iconColor,
    required this.label,
    required this.textTheme,
    this.value,
    this.supportingValue,
    this.valueWidget,
  });

  final IconData icon;
  final Color iconColor;
  final String label;
  final String? value;
  final String? supportingValue;
  final Widget? valueWidget;
  final TextTheme textTheme;

  @override
  Widget build(BuildContext context) {
    return Semantics(
      label: value == null ? label : '$label: $value',
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Icon(icon, size: 18, color: iconColor),
          const SizedBox(width: AppSpacing.sm),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  label,
                  style: textTheme.labelSmall?.copyWith(
                    color: AppColors.textSecondary,
                  ),
                ),
                if (valueWidget != null) ...[
                  const SizedBox(height: AppSpacing.xs),
                  valueWidget!,
                ] else if (value != null) ...[
                  const SizedBox(height: AppSpacing.xs),
                  Text(
                    value!,
                    style: textTheme.bodyMedium?.copyWith(
                      fontWeight: FontWeight.w500,
                    ),
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                  ),
                ],
                if (supportingValue != null) ...[
                  const SizedBox(height: AppSpacing.xs),
                  Text(
                    supportingValue!,
                    style: textTheme.bodySmall?.copyWith(
                      color: AppColors.textMuted,
                    ),
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                  ),
                ],
              ],
            ),
          ),
        ],
      ),
    );
  }
}
