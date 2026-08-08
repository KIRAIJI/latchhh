import 'dart:async';
import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;

import '../models/latch_models.dart';

abstract final class LatchApiConfig {
  static const String _configuredBaseUrl = String.fromEnvironment(
    'LATCH_API_BASE_URL',
  );

  static String get baseUrl {
    if (_configuredBaseUrl.trim().isNotEmpty) {
      return _withoutTrailingSlash(_configuredBaseUrl.trim());
    }

    if (kIsWeb) {
      return 'http://localhost/latch/backend-app/public/api/v1';
    }

    return switch (defaultTargetPlatform) {
      TargetPlatform.android =>
        'http://10.0.2.2/latch/backend-app/public/api/v1',
      _ => 'http://localhost/latch/backend-app/public/api/v1',
    };
  }

  static String? get releaseConfigurationError {
    if (!kReleaseMode) return null;
    final configured = _configuredBaseUrl.trim();
    if (configured.isEmpty) {
      return 'LATCH_API_BASE_URL is required for release builds.';
    }

    final uri = Uri.tryParse(configured);
    if (uri == null ||
        uri.scheme != 'https' ||
        uri.host.isEmpty ||
        _isLocalHost(uri.host)) {
      return 'LATCH_API_BASE_URL must be a public HTTPS URL in release builds.';
    }
    const firebaseEnabled = bool.fromEnvironment(
      'LATCH_FIREBASE_ENABLED',
      defaultValue: false,
    );
    const supportEmail = String.fromEnvironment('LATCH_SUPPORT_EMAIL');
    if (!firebaseEnabled) {
      return 'Firebase push and crash reporting must be enabled for release builds.';
    }
    if (!supportEmail.contains('@') || supportEmail.endsWith('@example.com')) {
      return 'LATCH_SUPPORT_EMAIL must be configured for release builds.';
    }
    return null;
  }

  static bool _isLocalHost(String host) {
    final normalized = host.toLowerCase();
    return normalized == 'localhost' ||
        normalized == '127.0.0.1' ||
        normalized == '10.0.2.2' ||
        normalized == '::1';
  }

  static String _withoutTrailingSlash(String value) {
    return value.endsWith('/') ? value.substring(0, value.length - 1) : value;
  }
}

class LatchApiException implements Exception {
  const LatchApiException({
    required this.message,
    this.statusCode,
    this.code,
    this.errors = const {},
    this.retryAfterSeconds,
  });

  factory LatchApiException.fromResponse(
    int statusCode,
    Map<String, dynamic>? payload, {
    String? retryAfterHeader,
  }) {
    final rawErrors = payload?['errors'];
    final errors = <String, List<String>>{};
    if (rawErrors is Map) {
      for (final entry in rawErrors.entries) {
        final value = entry.value;
        errors[entry.key.toString()] = value is List
            ? value.map((message) => message.toString()).toList()
            : <String>[value.toString()];
      }
    }
    final bodyRetryAfter = _asInt(payload?['retry_after_seconds']);
    final headerRetryAfter = int.tryParse(retryAfterHeader ?? '');
    final retryAfter = bodyRetryAfter ?? headerRetryAfter;

    return LatchApiException(
      statusCode: statusCode,
      code: payload?['code']?.toString(),
      message:
          payload?['message']?.toString() ??
          'The server could not complete the request.',
      errors: errors,
      retryAfterSeconds: retryAfter != null && retryAfter > 0
          ? retryAfter
          : null,
    );
  }

  final int? statusCode;
  final String? code;
  final String message;
  final Map<String, List<String>> errors;
  final int? retryAfterSeconds;

  String? get formMessage => errors.isEmpty ? message : null;

  String? fieldError(String field) {
    final messages = errors[field];
    return messages == null || messages.isEmpty ? null : messages.first;
  }

  @override
  String toString() => message;
}

class LatchAuthResult {
  const LatchAuthResult({required this.user, required this.token});

  final LatchUser user;
  final String token;
}

class LatchNotificationFeed {
  const LatchNotificationFeed({
    required this.notifications,
    required this.unreadCount,
  });

  final List<LatchNotification> notifications;
  final int unreadCount;
}

class LatchActivityFeed {
  const LatchActivityFeed({
    required this.activities,
    required this.nextCursor,
    this.retentionCutoff,
  });

  final List<LatchActivity> activities;
  final String? nextCursor;
  final DateTime? retentionCutoff;
}

class LatchApi {
  LatchApi({http.Client? client, String? baseUrl})
    : _client = client ?? http.Client(),
      baseUrl = baseUrl ?? LatchApiConfig.baseUrl;

  final http.Client _client;
  final String baseUrl;
  String? _token;
  bool _unauthorizedNotified = false;
  VoidCallback? onUnauthorized;

  String? get token => _token;

  set token(String? value) {
    _token = value;
    if (value != null && value.isNotEmpty) {
      _unauthorizedNotified = false;
    }
  }

  bool get hasToken => _token != null && _token!.isNotEmpty;

  Future<LatchAuthResult> register({
    required String name,
    required String email,
    required String password,
    required String passwordConfirmation,
    required bool acceptedTerms,
    required bool acknowledgedPrivacy,
  }) async {
    final data = _asMap(
      await _request(
        'POST',
        'auth/register',
        body: {
          'name': name,
          'email': email,
          'password': password,
          'password_confirmation': passwordConfirmation,
          'accepted_terms': acceptedTerms,
          'acknowledged_privacy': acknowledgedPrivacy,
          'device_name': _deviceName,
          'platform': _platformName,
        },
        authenticated: false,
      ),
    );
    return LatchAuthResult(
      user: _user(data['user']),
      token: data['token']?.toString() ?? '',
    );
  }

  Future<LatchAuthResult> login({
    required String email,
    required String password,
  }) async {
    final data = _asMap(
      await _request(
        'POST',
        'auth/login',
        body: {
          'email': email,
          'password': password,
          'device_name': _deviceName,
          'platform': _platformName,
        },
        authenticated: false,
      ),
    );
    return LatchAuthResult(
      user: _user(data['user']),
      token: data['token']?.toString() ?? '',
    );
  }

  Future<LatchAuthResult> loginWithGoogle(String idToken) async {
    final data = _asMap(
      await _request(
        'POST',
        'auth/oauth/google',
        body: {
          'id_token': idToken,
          'accepted_terms': true,
          'acknowledged_privacy': true,
          'device_name': _deviceName,
          'platform': _platformName,
        },
        authenticated: false,
      ),
    );
    return LatchAuthResult(
      user: _user(data['user']),
      token: data['token']?.toString() ?? '',
    );
  }

  Future<LatchUser> me() async {
    return _user(await _request('GET', 'auth/me'));
  }

  Future<void> logout() => _request('POST', 'auth/logout');

  Future<void> logoutAll() => _request('POST', 'auth/logout-all');

  Future<void> forgotPassword(String email) => _request(
    'POST',
    'auth/forgot-password',
    body: {'email': email},
    authenticated: false,
  );

  Future<void> requestPasswordSetup() =>
      _request('POST', 'auth/password/setup-link');

  Future<void> resetPassword({
    required String email,
    required String token,
    required String password,
    required String passwordConfirmation,
  }) => _request(
    'POST',
    'auth/reset-password',
    body: {
      'email': email,
      'token': token,
      'password': password,
      'password_confirmation': passwordConfirmation,
    },
    authenticated: false,
  );

  Future<void> resendEmailVerification() =>
      _request('POST', 'auth/email/verification-notification');

  Future<List<LatchItem>> items() async {
    final data = await _request('GET', 'items');
    return _asList(data).map((row) => LatchItem.fromJson(_asMap(row))).toList();
  }

  Future<LatchItem> refreshItem(int itemId) async {
    return LatchItem.fromJson(
      _asMap(await _request('POST', 'items/$itemId/refresh')),
    );
  }

  Future<LatchItem> claimItem({
    required String deviceUid,
    required String itemName,
  }) async {
    return LatchItem.fromJson(
      _asMap(
        await _request(
          'POST',
          'items/claim',
          body: {'device_uid': deviceUid, 'item_name': itemName},
        ),
      ),
    );
  }

  Future<LatchItem> renameItem(int itemId, String itemName) async {
    return LatchItem.fromJson(
      _asMap(
        await _request('PATCH', 'items/$itemId', body: {'item_name': itemName}),
      ),
    );
  }

  Future<void> releaseItem(int itemId) => _request('DELETE', 'items/$itemId');

  Future<LatchGeofence?> geofence(int itemId) async {
    final data = await _request('GET', 'items/$itemId/geofence');
    return data == null ? null : LatchGeofence.fromJson(_asMap(data));
  }

  Future<LatchGeofence> saveGeofence({
    required int itemId,
    required String name,
    required double centerLatitude,
    required double centerLongitude,
    required double radiusMeters,
    required bool notifyOnEnter,
    required bool notifyOnExit,
    required bool isActive,
  }) async {
    return LatchGeofence.fromJson(
      _asMap(
        await _request(
          'PUT',
          'items/$itemId/geofence',
          body: {
            'name': name,
            'center_latitude': centerLatitude,
            'center_longitude': centerLongitude,
            'radius_meters': radiusMeters,
            'notify_on_enter': notifyOnEnter,
            'notify_on_exit': notifyOnExit,
            'is_active': isActive,
          },
        ),
      ),
    );
  }

  Future<void> deleteGeofence(int itemId) =>
      _request('DELETE', 'items/$itemId/geofence');

  Future<List<LatchPosition>> locationHistory(int itemId) async {
    final rows = <LatchPosition>[];
    String? cursor;
    final seenCursors = <String>{};

    do {
      final response = await _requestEnvelope(
        'GET',
        'items/$itemId/location-history',
        query: {'per_page': '1000', 'cursor': ?cursor},
      );
      rows.addAll(
        _asList(
          response['data'],
        ).map((row) => LatchPosition.fromJson(_asMap(row))),
      );
      cursor = _asNullableString(_asMap(response['meta'])['next_cursor']);
      if (cursor != null && !seenCursors.add(cursor)) {
        throw const LatchApiException(
          message: 'The history cursor repeated unexpectedly.',
        );
      }
    } while (cursor != null);

    return rows;
  }

  Future<LatchActivityFeed> activity({String? cursor, int perPage = 50}) async {
    final response = await _requestEnvelope(
      'GET',
      'activity',
      query: {
        'per_page': perPage.clamp(1, 100).toString(),
        if (cursor != null && cursor.isNotEmpty) 'cursor': cursor,
      },
    );
    final meta = _asMap(response['meta']);
    return LatchActivityFeed(
      activities: _asList(
        response['data'],
      ).map((row) => LatchActivity.fromJson(_asMap(row))).toList(),
      nextCursor: _asNullableString(meta['next_cursor']),
      retentionCutoff: DateTime.tryParse(
        _asNullableString(meta['retention_cutoff']) ?? '',
      )?.toUtc(),
    );
  }

  Future<LatchNotificationFeed> notifications() async {
    final notifications = <LatchNotification>[];
    String? cursor;
    var unreadCount = 0;
    final seenCursors = <String>{};

    do {
      final response = await _requestEnvelope(
        'GET',
        'notifications',
        query: {'per_page': '100', 'cursor': ?cursor},
      );
      notifications.addAll(
        _asList(
          response['data'],
        ).map((row) => LatchNotification.fromJson(_asMap(row))),
      );
      final meta = _asMap(response['meta']);
      unreadCount = _asInt(meta['unread_count']) ?? unreadCount;
      cursor = _asNullableString(meta['next_cursor']);
      if (cursor != null && !seenCursors.add(cursor)) {
        throw const LatchApiException(
          message: 'The notification cursor repeated unexpectedly.',
        );
      }
    } while (cursor != null);

    return LatchNotificationFeed(
      notifications: notifications,
      unreadCount: unreadCount,
    );
  }

  Future<LatchNotification> markNotificationRead(int notificationId) async {
    return LatchNotification.fromJson(
      _asMap(await _request('PATCH', 'notifications/$notificationId/read')),
    );
  }

  Future<void> markAllNotificationsRead() async {
    await _request('PATCH', 'notifications/read-all');
  }

  Future<void> deleteNotification(int notificationId) async {
    await _request('DELETE', 'notifications/$notificationId');
  }

  Future<LatchUser> updateProfile({
    required String name,
    required String email,
    String? currentPassword,
  }) async {
    return _user(
      await _request(
        'PATCH',
        'profile',
        body: {
          'name': name,
          'email': email,
          if (currentPassword != null && currentPassword.isNotEmpty)
            'current_password': currentPassword,
        },
      ),
    );
  }

  Future<int> changePassword({
    required String currentPassword,
    required String password,
    required String passwordConfirmation,
  }) async {
    final data = _asMap(
      await _request(
        'PUT',
        'profile/password',
        body: {
          'current_password': currentPassword,
          'password': password,
          'password_confirmation': passwordConfirmation,
        },
      ),
    );
    return _asInt(data['sessions_revoked']) ?? 0;
  }

  Future<LatchUser> updateSettings({
    required bool notificationsEnabled,
    bool? notifyGeofenceEvents,
    bool? notifyBatteryEvents,
    bool? notifyDeviceStatusEvents,
  }) async {
    return _user(
      await _request(
        'PATCH',
        'settings',
        body: {
          'notifications_enabled': notificationsEnabled,
          'notify_geofence_events': ?notifyGeofenceEvents,
          'notify_battery_events': ?notifyBatteryEvents,
          'notify_device_status_events': ?notifyDeviceStatusEvents,
        },
      ),
    );
  }

  Future<void> registerPushToken({
    required String pushToken,
    required String platform,
    String? deviceName,
    String? appVersion,
  }) => _request(
    'POST',
    'push-tokens',
    body: {
      'token': pushToken,
      'platform': platform,
      'device_name': ?deviceName,
      'app_version': ?appVersion,
    },
  );

  Future<void> deletePushToken(String pushToken) =>
      _request('DELETE', 'push-tokens', body: {'token': pushToken});

  Future<void> deleteAllPushTokens() => _request('DELETE', 'push-tokens/all');

  Future<List<LatchSession>> sessions() async =>
      _asList(await _request('GET', 'auth/sessions'))
          .map((value) => LatchSession.fromJson(_asMap(value)))
          .toList(growable: false);

  Future<bool> revokeSession(int sessionId) async {
    final data = _asMap(await _request('DELETE', 'auth/sessions/$sessionId'));
    return data['was_current'] == true;
  }

  Future<LatchUser> uploadProfilePhoto({
    required Uint8List bytes,
    required String filename,
  }) async {
    final request = http.MultipartRequest('POST', _uri('profile/photo'));
    request.headers.addAll(_headers(authenticated: true, includeJson: false));
    request.files.add(
      http.MultipartFile.fromBytes('photo', bytes, filename: filename),
    );
    final streamed = await _client
        .send(request)
        .timeout(const Duration(seconds: 30));
    final response = await http.Response.fromStream(streamed);
    final envelope = _decodeResponse(response, notifyUnauthorized: true);
    return _user(envelope['data']);
  }

  Future<LatchUser> removeProfilePhoto() async {
    return _user(await _request('DELETE', 'profile/photo'));
  }

  Future<void> deleteAccount({String? currentPassword, String? oauthIdToken}) =>
      _request(
        'DELETE',
        'account',
        body: {
          if (currentPassword != null && currentPassword.isNotEmpty)
            'current_password': currentPassword,
          if (oauthIdToken != null && oauthIdToken.isNotEmpty)
            'oauth_id_token': oauthIdToken,
        },
      );

  LatchUser _user(dynamic value) {
    final json = Map<String, dynamic>.from(_asMap(value));
    final photo = _asNullableString(json['profile_photo_url']);
    if (photo != null) {
      json['profile_photo_url'] = resolvePublicUrl(photo);
    }
    return LatchUser.fromJson(json);
  }

  String resolvePublicUrl(String value) {
    final resourceUri = Uri.tryParse(value);
    final apiUri = Uri.parse(baseUrl);
    if (resourceUri == null || resourceUri.host.isEmpty) return value;

    final isLocalBackendHost =
        resourceUri.host == 'localhost' ||
        resourceUri.host == '127.0.0.1' ||
        resourceUri.host == '10.0.2.2';
    if (!isLocalBackendHost) return value;

    final apiSuffix = '/api/v1';
    final publicPath = apiUri.path.endsWith(apiSuffix)
        ? apiUri.path.substring(0, apiUri.path.length - apiSuffix.length)
        : '';
    final resourcePath = resourceUri.path.startsWith('/')
        ? resourceUri.path
        : '/${resourceUri.path}';
    final resolvedPath =
        publicPath.isNotEmpty &&
            (resourcePath == publicPath ||
                resourcePath.startsWith('$publicPath/'))
        ? resourcePath
        : '$publicPath$resourcePath';

    return apiUri
        .replace(
          path: resolvedPath,
          query: resourceUri.hasQuery ? resourceUri.query : null,
          fragment: resourceUri.hasFragment ? resourceUri.fragment : null,
        )
        .toString();
  }

  Future<dynamic> _request(
    String method,
    String path, {
    Map<String, dynamic>? body,
    Map<String, String>? query,
    bool authenticated = true,
  }) async {
    final envelope = await _requestEnvelope(
      method,
      path,
      body: body,
      query: query,
      authenticated: authenticated,
    );
    return envelope['data'];
  }

  Future<Map<String, dynamic>> _requestEnvelope(
    String method,
    String path, {
    Map<String, dynamic>? body,
    Map<String, String>? query,
    bool authenticated = true,
  }) async {
    final uri = _uri(path, query);
    final headers = _headers(authenticated: authenticated);
    final encodedBody = body == null ? null : jsonEncode(body);
    late final http.Response response;
    try {
      response = switch (method) {
        'GET' =>
          await _client
              .get(uri, headers: headers)
              .timeout(const Duration(seconds: 20)),
        'POST' =>
          await _client
              .post(uri, headers: headers, body: encodedBody)
              .timeout(const Duration(seconds: 20)),
        'PATCH' =>
          await _client
              .patch(uri, headers: headers, body: encodedBody)
              .timeout(const Duration(seconds: 20)),
        'PUT' =>
          await _client
              .put(uri, headers: headers, body: encodedBody)
              .timeout(const Duration(seconds: 20)),
        'DELETE' =>
          await _client
              .delete(uri, headers: headers, body: encodedBody)
              .timeout(const Duration(seconds: 20)),
        _ => throw const LatchApiException(
          message: 'This action is not supported by the app.',
        ),
      };
    } on TimeoutException {
      throw const LatchApiException(
        code: 'NETWORK_TIMEOUT',
        message:
            'This is taking longer than expected. Check your internet '
            'connection, then try again.',
      );
    } on http.ClientException {
      throw const LatchApiException(
        code: 'NETWORK_UNAVAILABLE',
        message:
            'No internet connection. Check Wi-Fi or mobile data, then '
            'try again.',
      );
    }
    return _decodeResponse(response, notifyUnauthorized: authenticated);
  }

  Uri _uri(String path, [Map<String, String>? query]) {
    final uri = Uri.parse('$baseUrl/$path');
    return query == null ? uri : uri.replace(queryParameters: query);
  }

  Map<String, String> _headers({
    required bool authenticated,
    bool includeJson = true,
  }) {
    if (authenticated && !hasToken) {
      throw const LatchApiException(
        statusCode: 401,
        code: 'UNAUTHENTICATED',
        message: 'Please sign in again.',
      );
    }

    return {
      'Accept': 'application/json',
      if (includeJson) 'Content-Type': 'application/json',
      if (authenticated) 'Authorization': 'Bearer $_token',
    };
  }

  Map<String, dynamic> _decodeResponse(
    http.Response response, {
    bool notifyUnauthorized = false,
  }) {
    if (response.statusCode == 204) {
      return const <String, dynamic>{'data': null};
    }

    Map<String, dynamic>? payload;
    if (response.body.trim().isNotEmpty) {
      try {
        payload = _asMap(jsonDecode(response.body));
      } on FormatException {
        if (response.statusCode >= 500) {
          throw LatchApiException(
            statusCode: response.statusCode,
            code: 'SERVICE_UNAVAILABLE',
            message:
                'LATCH is temporarily unavailable. Please try again in '
                'a few moments.',
          );
        }
        throw LatchApiException(
          statusCode: response.statusCode,
          message: 'We could not complete that request. Please try again.',
        );
      }
    }

    if (response.statusCode < 200 || response.statusCode >= 300) {
      if (response.statusCode == 401 && notifyUnauthorized) {
        _notifyUnauthorized();
      }
      throw LatchApiException.fromResponse(
        response.statusCode,
        payload,
        retryAfterHeader: response.headers['retry-after'],
      );
    }

    return payload ?? const <String, dynamic>{'data': null};
  }

  void _notifyUnauthorized() {
    if (_unauthorizedNotified) return;
    _unauthorizedNotified = true;
    _token = null;
    final callback = onUnauthorized;
    if (callback != null) {
      scheduleMicrotask(callback);
    }
  }

  void close() {
    onUnauthorized = null;
    _client.close();
  }

  String get _platformName => kIsWeb
      ? 'web'
      : switch (defaultTargetPlatform) {
          TargetPlatform.android => 'android',
          TargetPlatform.iOS => 'ios',
          TargetPlatform.windows => 'windows',
          TargetPlatform.macOS => 'macos',
          TargetPlatform.linux => 'linux',
          TargetPlatform.fuchsia => 'android',
        };

  String get _deviceName => kIsWeb
      ? 'Web browser'
      : switch (defaultTargetPlatform) {
          TargetPlatform.android => 'Android device',
          TargetPlatform.iOS => 'iPhone or iPad',
          TargetPlatform.windows => 'Windows computer',
          TargetPlatform.macOS => 'Mac',
          TargetPlatform.linux => 'Linux computer',
          TargetPlatform.fuchsia => 'Mobile device',
        };
}

Map<String, dynamic> _asMap(dynamic value) {
  if (value is Map<String, dynamic>) return value;
  if (value is Map) {
    return value.map((key, value) => MapEntry(key.toString(), value));
  }
  return const <String, dynamic>{};
}

List<dynamic> _asList(dynamic value) {
  return value is List ? value : const <dynamic>[];
}

int? _asInt(dynamic value) {
  if (value is int) return value;
  if (value is num) return value.toInt();
  return int.tryParse(value?.toString() ?? '');
}

String? _asNullableString(dynamic value) {
  final text = value?.toString();
  return text == null || text.isEmpty ? null : text;
}
