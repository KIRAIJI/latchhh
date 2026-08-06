class LatchUser {
  const LatchUser({
    required this.id,
    required this.name,
    required this.email,
    required this.emailVerified,
    required this.notificationsEnabled,
    required this.notifyGeofenceEvents,
    required this.notifyBatteryEvents,
    required this.notifyDeviceStatusEvents,
    this.hasPassword = true,
    this.oauthProviders = const <String>[],
    this.profilePhotoUrl,
  });

  factory LatchUser.fromJson(Map<String, dynamic> json) {
    return LatchUser(
      id: _asInt(json['id']) ?? 0,
      name: json['name']?.toString() ?? '',
      email: json['email']?.toString() ?? '',
      emailVerified: json['email_verified'] == true,
      hasPassword: json['has_password'] != false,
      oauthProviders: _asStringList(json['oauth_providers']),
      profilePhotoUrl: _asNullableString(json['profile_photo_url']),
      notificationsEnabled: json['notifications_enabled'] == true,
      notifyGeofenceEvents:
          _asMap(json['notification_preferences'])['geofence_events'] != false,
      notifyBatteryEvents:
          _asMap(json['notification_preferences'])['battery_events'] != false,
      notifyDeviceStatusEvents:
          _asMap(json['notification_preferences'])['device_status_events'] !=
          false,
    );
  }

  final int id;
  final String name;
  final String email;
  final bool emailVerified;
  final bool hasPassword;
  final List<String> oauthProviders;
  final String? profilePhotoUrl;
  final bool notificationsEnabled;
  final bool notifyGeofenceEvents;
  final bool notifyBatteryEvents;
  final bool notifyDeviceStatusEvents;
}

class LatchItem {
  const LatchItem({
    required this.id,
    required this.deviceUid,
    required this.itemName,
    required this.location,
    required this.status,
    this.claimedAt,
    this.geofence,
  });

  factory LatchItem.fromJson(Map<String, dynamic> json) {
    return LatchItem(
      id: _asInt(json['id']) ?? 0,
      deviceUid: json['device_uid']?.toString() ?? '',
      itemName: json['item_name']?.toString() ?? '',
      claimedAt: _asDateTime(json['claimed_at']),
      location: LatchLocation.fromJson(_asMap(json['location'])),
      status: LatchItemStatus.fromJson(_asMap(json['status'])),
      geofence: json['geofence'] is Map
          ? LatchGeofenceSummary.fromJson(_asMap(json['geofence']))
          : null,
    );
  }

  final int id;
  final String deviceUid;
  final String itemName;
  final DateTime? claimedAt;
  final LatchLocation location;
  final LatchItemStatus status;
  final LatchGeofenceSummary? geofence;
}

class LatchLocation {
  const LatchLocation({
    required this.type,
    this.latitude,
    this.longitude,
    this.recordedAt,
  });

  factory LatchLocation.fromJson(Map<String, dynamic> json) {
    return LatchLocation(
      latitude: _asDouble(json['latitude']),
      longitude: _asDouble(json['longitude']),
      recordedAt: _asDateTime(json['recorded_at']),
      type: json['type']?.toString() ?? 'unavailable',
    );
  }

  final double? latitude;
  final double? longitude;
  final DateTime? recordedAt;
  final String type;

  bool get hasCoordinates => latitude != null && longitude != null;
}

class LatchItemStatus {
  const LatchItemStatus({
    required this.connection,
    required this.batteryStatus,
    required this.gnssStatus,
    required this.gsmSignalLevel,
    this.lastCommunicationAt,
    this.lastTelemetryAt,
    this.batteryPercentage,
    this.satellites,
    this.hdop,
    this.gsmCsq,
    this.powerState,
    this.firmwareVersion,
    this.resetReason,
  });

  factory LatchItemStatus.fromJson(Map<String, dynamic> json) {
    return LatchItemStatus(
      connection: json['connection']?.toString() ?? 'unknown',
      lastCommunicationAt: _asDateTime(json['last_communication_at']),
      lastTelemetryAt: _asDateTime(json['telemetry_recorded_at']),
      batteryPercentage: _asInt(json['battery_percentage']),
      batteryStatus: json['battery_status']?.toString() ?? 'unknown',
      gnssStatus: json['gnss_status']?.toString() ?? 'unknown',
      satellites: _asInt(json['satellites']),
      hdop: _asDouble(json['hdop']),
      gsmCsq: _asInt(json['gsm_csq']),
      gsmSignalLevel: json['gsm_signal_level']?.toString() ?? 'unknown',
      powerState: _asNullableString(json['power_state']),
      firmwareVersion: _asNullableString(json['firmware_version']),
      resetReason: _asNullableString(json['reset_reason']),
    );
  }

  final String connection;
  final DateTime? lastCommunicationAt;
  final DateTime? lastTelemetryAt;
  final int? batteryPercentage;
  final String batteryStatus;
  final String gnssStatus;
  final int? satellites;
  final double? hdop;
  final int? gsmCsq;
  final String gsmSignalLevel;
  final String? powerState;
  final String? firmwareVersion;
  final String? resetReason;
}

class LatchGeofenceSummary {
  const LatchGeofenceSummary({
    required this.id,
    required this.name,
    required this.radiusMeters,
    required this.isActive,
  });

  factory LatchGeofenceSummary.fromJson(Map<String, dynamic> json) {
    return LatchGeofenceSummary(
      id: _asInt(json['id']) ?? 0,
      name: json['name']?.toString() ?? '',
      radiusMeters: _asDouble(json['radius_meters']) ?? 0,
      isActive: json['is_active'] == true,
    );
  }

  final int id;
  final String name;
  final double radiusMeters;
  final bool isActive;
}

class LatchGeofence {
  const LatchGeofence({
    required this.id,
    required this.name,
    required this.centerLatitude,
    required this.centerLongitude,
    required this.radiusMeters,
    required this.notifyOnEnter,
    required this.notifyOnExit,
    required this.isActive,
  });

  factory LatchGeofence.fromJson(Map<String, dynamic> json) {
    return LatchGeofence(
      id: _asInt(json['id']) ?? 0,
      name: json['name']?.toString() ?? '',
      centerLatitude: _asDouble(json['center_latitude']) ?? 0,
      centerLongitude: _asDouble(json['center_longitude']) ?? 0,
      radiusMeters: _asDouble(json['radius_meters']) ?? 0,
      notifyOnEnter: json['notify_on_enter'] == true,
      notifyOnExit: json['notify_on_exit'] == true,
      isActive: json['is_active'] == true,
    );
  }

  final int id;
  final String name;
  final double centerLatitude;
  final double centerLongitude;
  final double radiusMeters;
  final bool notifyOnEnter;
  final bool notifyOnExit;
  final bool isActive;
}

class LatchPosition {
  const LatchPosition({
    required this.latitude,
    required this.longitude,
    required this.recordedAt,
  });

  factory LatchPosition.fromJson(Map<String, dynamic> json) {
    final latitude = _asDouble(json['latitude']);
    final longitude = _asDouble(json['longitude']);
    final recordedAt = _asDateTime(json['recorded_at']);
    if (latitude == null ||
        latitude < -90 ||
        latitude > 90 ||
        longitude == null ||
        longitude < -180 ||
        longitude > 180 ||
        recordedAt == null) {
      throw const FormatException('Invalid location-history position.');
    }

    return LatchPosition(
      latitude: latitude,
      longitude: longitude,
      recordedAt: recordedAt,
    );
  }

  final double latitude;
  final double longitude;
  final DateTime recordedAt;
}

class LatchActivity {
  const LatchActivity({
    required this.id,
    required this.eventType,
    required this.title,
    required this.description,
    required this.source,
    required this.eventData,
    this.itemId,
    this.deviceUid,
    this.itemName,
    this.occurredAt,
  });

  factory LatchActivity.fromJson(Map<String, dynamic> json) {
    final device = _asMap(json['device']);
    return LatchActivity(
      id: _asInt(json['id']) ?? 0,
      eventType: json['event_type']?.toString() ?? 'unknown',
      title: json['title']?.toString() ?? 'Activity',
      description: json['description']?.toString() ?? '',
      source: json['source']?.toString() ?? 'unknown',
      itemId: _asInt(device['item_id']),
      deviceUid: _asNullableString(device['device_uid']),
      itemName: _asNullableString(device['item_name']),
      eventData: Map<String, dynamic>.unmodifiable(_asMap(json['event_data'])),
      occurredAt: _asDateTime(json['occurred_at']),
    );
  }

  final int id;
  final String eventType;
  final String title;
  final String description;
  final String source;
  final int? itemId;
  final String? deviceUid;
  final String? itemName;
  final Map<String, dynamic> eventData;
  final DateTime? occurredAt;
}

class LatchNotification {
  const LatchNotification({
    required this.id,
    required this.type,
    required this.title,
    required this.message,
    required this.isRead,
    this.createdAt,
    this.readAt,
    this.relatedItem,
  });

  factory LatchNotification.fromJson(Map<String, dynamic> json) {
    return LatchNotification(
      id: _asInt(json['id']) ?? 0,
      type: json['type']?.toString() ?? 'unknown',
      title: json['title']?.toString() ?? '',
      message: json['message']?.toString() ?? '',
      createdAt: _asDateTime(json['created_at']),
      readAt: _asDateTime(json['read_at']),
      isRead: json['is_read'] == true,
      relatedItem: json['related_item'] is Map
          ? LatchRelatedItem.fromJson(_asMap(json['related_item']))
          : null,
    );
  }

  final int id;
  final String type;
  final String title;
  final String message;
  final DateTime? createdAt;
  final DateTime? readAt;
  final bool isRead;
  final LatchRelatedItem? relatedItem;
}

class LatchRelatedItem {
  const LatchRelatedItem({
    required this.id,
    required this.deviceUid,
    required this.itemName,
  });

  factory LatchRelatedItem.fromJson(Map<String, dynamic> json) {
    return LatchRelatedItem(
      id: _asInt(json['id']) ?? 0,
      deviceUid: json['device_uid']?.toString() ?? '',
      itemName: json['item_name']?.toString() ?? '',
    );
  }

  final int id;
  final String deviceUid;
  final String itemName;
}

Map<String, dynamic> _asMap(dynamic value) {
  if (value is Map<String, dynamic>) {
    return value;
  }
  if (value is Map) {
    return value.map((key, value) => MapEntry(key.toString(), value));
  }
  return const <String, dynamic>{};
}

List<String> _asStringList(dynamic value) {
  return value is List
      ? value.map((entry) => entry.toString()).toList(growable: false)
      : const <String>[];
}

int? _asInt(dynamic value) {
  if (value is int) return value;
  if (value is num) return value.toInt();
  return int.tryParse(value?.toString() ?? '');
}

double? _asDouble(dynamic value) {
  if (value is num) return value.toDouble();
  return double.tryParse(value?.toString() ?? '');
}

String? _asNullableString(dynamic value) {
  final text = value?.toString();
  return text == null || text.isEmpty ? null : text;
}

DateTime? _asDateTime(dynamic value) {
  final text = _asNullableString(value);
  return text == null ? null : DateTime.tryParse(text)?.toUtc();
}
