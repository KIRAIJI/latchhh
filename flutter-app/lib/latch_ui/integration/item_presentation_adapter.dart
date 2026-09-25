import 'package:flutter/material.dart';

import '../core/services/location_address_resolver.dart';
import '../core/theme/app_colors.dart';
import '../data/models/latch_models.dart';
import '../features/items/presentation/widgets/item_compact_card.dart';
import '../features/items/presentation/widgets/item_details_bottom_sheet.dart';

abstract final class ItemPresentationAdapter {
  static List<ItemCompactCardData> buildItems(List<LatchItem> items) {
    return items.map(_buildItem).toList();
  }

  static ItemCompactCardData _buildItem(LatchItem item) {
    final connection = _connectionPresentation(item.status.connection);
    final isOffline = item.status.connection == 'offline';
    final isLive = item.status.connection == 'online';
    final geofence = item.geofence;
    final baseLocationLabel = isOffline && item.location.hasCoordinates
        ? item.location.source == 'wifi'
              ? 'Estimated last known location'
              : 'Last known location'
        : item.location.source == 'wifi'
        ? item.location.type == 'current'
              ? 'Estimated current location'
              : 'Estimated last known location'
        : _locationLabel(item.location.type);
    final specificPlace =
        item.location.placeName ??
        (geofence?.isActive == true && geofence?.lastInside == true
            ? geofence!.name
            : null);
    final locationLabel = specificPlace == null
        ? baseLocationLabel
        : '$baseLocationLabel · $specificPlace';
    final battery = switch (item.status.powerState) {
      _ when !isLive => isOffline
          ? 'Battery unavailable while offline'
          : 'Battery unavailable while connection is stale',
      'charging' => 'Charging',
      'full' => 'Fully charged',
      _ when item.status.batteryPercentage == null => 'Battery not reported',
      _ => '${item.status.batteryPercentage}% battery',
    };

    final details = ItemDetailsData(
      itemId: item.id,
      itemName: item.itemName,
      deviceId: item.deviceUid,
      connectionStatus: connection.label,
      connectionIcon: connection.icon,
      connectionColor: connection.color,
      lastCommunicationText: null,
      locationTypeText: locationLabel,
      locationPlaceName: item.location.placeName,
      locationTimestampText: null,
      locationLatitude: item.location.latitude,
      locationLongitude: item.location.longitude,
      locationCoordinatesText: item.location.hasCoordinates
          ? LocationAddressResolver.formatCoordinates(
              item.location.latitude!,
              item.location.longitude!,
            )
          : null,
        batteryPercentageText: !isLive || item.status.batteryPercentage == null
          ? null
          : '${item.status.batteryPercentage}%',
      batteryStatusText: _batteryStatusLabel(item.status),
      gnssStatusText: _gnssStatusLabel(
        item.status.gnssStatus,
        item.status.connection,
        item.location.hasCoordinates,
      ),
      gnssTimestampText: null,
      satellitesText: item.status.satellites == null
          ? null
          : '${item.status.satellites} satellites visible',
      hdopText: item.status.hdop == null ? null : 'HDOP ${item.status.hdop}',
        signalLevelText: !isLive
            ? isOffline
              ? 'Unavailable while offline'
              : 'Unavailable while not connected'
          : item.status.gsmCsq == null
          ? 'Not reported by tracker'
          : _title(item.status.gsmSignalLevel),
      gsmCsqText: item.status.gsmCsq == null
          ? null
          : 'CSQ ${item.status.gsmCsq}',
        networkSignalBarCount: !isLive
          ? null
          : _signalBars(item.status.gsmSignalLevel),
        showNetworkSignal: isLive,
      firmwareVersionText: item.status.firmwareVersion == null
          ? null
          : 'Version ${item.status.firmwareVersion}',
      resetReasonText: item.status.resetReason == null
          ? null
          : 'Last restart: ${_resetReasonLabel(item.status.resetReason!)}',
      geofenceSummaryText: geofence == null
          ? 'Set Geofence'
          : '${geofence.name} · ${geofence.radiusMeters.round()} m'
                '${!geofence.isActive ? ' · Inactive' : switch (geofence.lastInside) {
                        true => ' · Inside',
                        false => ' · Outside',
                        null => ' · Waiting for location',
                      }}',
    );

    return ItemCompactCardData(
      itemName: item.itemName,
      connectionStatusLabel: connection.label,
      connectionStatusIcon: connection.icon,
      connectionStatusColor: connection.color,
      batteryLabel: battery,
      locationLabel: locationLabel,
      locationTimeLabel: '',
      itemDetails: details,
    );
  }

  static ({String label, IconData icon, Color color}) _connectionPresentation(
    String value,
  ) {
    return switch (value) {
      'online' => (
        label: 'Online',
        icon: Icons.cloud_done_outlined,
        color: AppColors.success,
      ),
      'stale' => (
        label: 'Stale',
        icon: Icons.schedule_rounded,
        color: AppColors.warning,
      ),
      'offline' => (
        label: 'Offline',
        icon: Icons.cloud_off_outlined,
        color: AppColors.error,
      ),
      _ => (
        label: 'Unknown',
        icon: Icons.help_outline_rounded,
        color: AppColors.textSecondary,
      ),
    };
  }

  static int? _signalBars(String value) {
    return switch (value) {
      'excellent' => 4,
      'good' => 3,
      'fair' => 2,
      'poor' => 1,
      'none' => 0,
      _ => null,
    };
  }

  static String _batteryStatusLabel(LatchItemStatus status) {
    if (status.connection != 'online') {
      return status.connection == 'offline'
          ? 'Unavailable while offline'
          : 'Unavailable while connection is stale';
    }

    return switch (status.powerState) {
      'charging' => 'Charging',
      'full' => 'Fully charged',
      'battery' when status.batteryPercentage == null => 'On battery',
      _ when status.batteryPercentage == null => 'Not reported by tracker',
      _ => _title(status.batteryStatus),
    };
  }

  static String _resetReasonLabel(String value) {
    return switch (value) {
      'power_on' => 'Power on',
      'external' => 'External reset',
      'software' => 'Software restart',
      'panic' => 'System crash',
      'interrupt_watchdog' => 'Interrupt watchdog',
      'task_watchdog' => 'Task watchdog',
      'watchdog' => 'Watchdog',
      'deep_sleep' => 'Deep-sleep wake',
      'brownout' => 'Brownout',
      'sdio' => 'SDIO reset',
      _ => 'Unknown',
    };
  }

  static String _gnssStatusLabel(
    String status,
    String connection,
    bool hasCoordinates,
  ) {
    if (connection != 'online') {
      return hasCoordinates ? 'Last known location' : 'Location unavailable';
    }

    final label = switch (status) {
      'fixed' => 'Location available',
      'no_fix' => 'Finding location',
      _ => 'Unknown',
    };
    return label;
  }

  static String _title(String value) {
    if (value.isEmpty) return 'Unknown';
    return value
        .replaceAll('_', ' ')
        .split(' ')
        .map(
          (part) => part.isEmpty
              ? part
              : '${part[0].toUpperCase()}${part.substring(1)}',
        )
        .join(' ');
  }

  static String _locationLabel(String type) {
    return switch (type) {
      'current' => 'Current location',
      'last_known' => 'Last known location',
      _ => 'Location unavailable',
    };
  }
}
