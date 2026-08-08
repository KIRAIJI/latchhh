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
    final geofence = item.geofence;
    final baseLocationLabel = _locationLabel(item.location.type);
    final locationLabel =
        geofence?.isActive == true && geofence?.lastInside == true
        ? '$baseLocationLabel · ${geofence!.name}'
        : baseLocationLabel;
    final recordedAt = _formatDate(item.location.recordedAt);
    final battery = item.status.batteryPercentage == null
        ? 'Battery not reported'
        : '${item.status.batteryPercentage}% battery';

    final details = ItemDetailsData(
      itemId: item.id,
      itemName: item.itemName,
      deviceId: item.deviceUid,
      connectionStatus: connection.label,
      connectionIcon: connection.icon,
      connectionColor: connection.color,
      lastCommunicationText: _formatDate(item.status.lastCommunicationAt),
      locationTypeText: locationLabel,
      locationTimestampText: recordedAt == null ? null : 'Recorded $recordedAt',
      locationLatitude: item.location.latitude,
      locationLongitude: item.location.longitude,
      locationCoordinatesText: item.location.hasCoordinates
          ? LocationAddressResolver.formatCoordinates(
              item.location.latitude!,
              item.location.longitude!,
            )
          : null,
      batteryPercentageText: item.status.batteryPercentage == null
          ? null
          : '${item.status.batteryPercentage}%',
      batteryStatusText: _batteryStatusLabel(item.status),
      gnssStatusText: _gnssStatusLabel(
        item.status.gnssStatus,
        item.status.connection,
      ),
      gnssTimestampText: item.status.lastTelemetryAt == null
          ? null
          : 'Reported ${_formatDate(item.status.lastTelemetryAt)}',
      satellitesText: item.status.satellites == null
          ? null
          : '${item.status.satellites} satellites visible',
      hdopText: item.status.hdop == null ? null : 'HDOP ${item.status.hdop}',
      signalLevelText: item.status.gsmCsq == null
          ? 'Not reported by tracker'
          : _title(item.status.gsmSignalLevel),
      gsmCsqText: item.status.gsmCsq == null
          ? null
          : 'CSQ ${item.status.gsmCsq}',
      networkSignalBarCount: _signalBars(item.status.gsmSignalLevel),
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
      locationTimeLabel: recordedAt ?? 'Location unavailable',
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

  static String _gnssStatusLabel(String status, String connection) {
    final label = switch (status) {
      'fixed' => 'GPS fix available',
      'no_fix' => 'Searching for GPS fix',
      _ => 'Unknown',
    };
    return connection == 'online' || status == 'unknown'
        ? label
        : '$label (last reported)';
  }

  static String? _formatDate(DateTime? value) {
    if (value == null) return null;
    final local = value.toLocal();
    final month = local.month.toString().padLeft(2, '0');
    final day = local.day.toString().padLeft(2, '0');
    final hour = local.hour.toString().padLeft(2, '0');
    final minute = local.minute.toString().padLeft(2, '0');
    return '${local.year}-$month-$day $hour:$minute';
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
