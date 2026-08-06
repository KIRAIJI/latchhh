import 'dart:async';

import 'package:firebase_crashlytics/firebase_crashlytics.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import 'package:package_info_plus/package_info_plus.dart';

import '../../data/api/latch_api.dart';
import '../errors/app_error_reporter.dart';

class PushNotificationService {
  static const AndroidNotificationChannel _channel = AndroidNotificationChannel(
    'latch_alerts',
    'LATCH alerts',
    description: 'Geofence, battery, and tracker-status alerts.',
    importance: Importance.high,
  );

  final FlutterLocalNotificationsPlugin _localNotifications =
      FlutterLocalNotificationsPlugin();
  LatchApi? _api;
  String? _registeredToken;
  int? _pendingNotificationId;
  bool _initialized = false;

  ValueChanged<int>? onNotificationTapped;
  AsyncCallback? onForegroundMessage;

  Future<void> initialize() async {
    if (_initialized) return;
    _initialized = true;

    await _localNotifications.initialize(
      settings: const InitializationSettings(
        android: AndroidInitializationSettings('@mipmap/ic_launcher'),
        iOS: DarwinInitializationSettings(),
      ),
      onDidReceiveNotificationResponse: (response) {
        final id = int.tryParse(response.payload ?? '');
        if (id != null) _deliverTap(id);
      },
    );

    await _localNotifications
        .resolvePlatformSpecificImplementation<
          AndroidFlutterLocalNotificationsPlugin
        >()
        ?.createNotificationChannel(_channel);

    FirebaseMessaging.onMessage.listen(_handleForegroundMessage);
    FirebaseMessaging.onMessageOpenedApp.listen(_handleOpenedMessage);

    final initialMessage = await FirebaseMessaging.instance
        .getInitialMessage()
        .timeout(const Duration(seconds: 3), onTimeout: () => null);
    if (initialMessage != null) {
      _handleOpenedMessage(initialMessage);
    }

    FirebaseMessaging.instance.onTokenRefresh.listen((token) {
      unawaited(_registerToken(token));
    });
  }

  Future<void> activate(LatchApi api, {required int userId}) async {
    _api = api;
    await FirebaseCrashlytics.instance.setUserIdentifier(userId.toString());

    final settings = await FirebaseMessaging.instance.requestPermission(
      alert: true,
      badge: true,
      sound: true,
    );
    if (settings.authorizationStatus == AuthorizationStatus.denied) return;

    final token = await FirebaseMessaging.instance.getToken().timeout(
      const Duration(seconds: 8),
      onTimeout: () => null,
    );
    if (token != null && token.isNotEmpty) {
      await _registerToken(token);
    }
    _deliverPendingTap();
  }

  Future<void> deactivate(LatchApi api) async {
    final token = _registeredToken;
    if (token != null && api.hasToken) {
      try {
        await api.deletePushToken(token);
      } on Object catch (error, stackTrace) {
        AppErrorReporter.record(
          error,
          stackTrace,
          context: 'Unregister push token',
        );
      }
    }
    _registeredToken = null;
    _api = null;
    await FirebaseCrashlytics.instance.setUserIdentifier('');
  }

  Future<void> _registerToken(String token) async {
    final api = _api;
    if (api == null || !api.hasToken) return;

    try {
      final info = await PackageInfo.fromPlatform();
      await api.registerPushToken(
        pushToken: token,
        platform: defaultTargetPlatform == TargetPlatform.iOS
            ? 'ios'
            : 'android',
        deviceName: '${defaultTargetPlatform.name} device',
        appVersion: '${info.version}+${info.buildNumber}',
      );
      _registeredToken = token;
    } on Object catch (error, stackTrace) {
      AppErrorReporter.record(
        error,
        stackTrace,
        context: 'Register push token',
      );
    }
  }

  Future<void> _handleForegroundMessage(RemoteMessage message) async {
    await onForegroundMessage?.call();
    final notification = message.notification;
    if (notification == null) return;
    final notificationId = int.tryParse(
      message.data['notification_id']?.toString() ?? '',
    );

    await _localNotifications.show(
      id: notificationId ?? message.hashCode & 0x7fffffff,
      title: notification.title ?? 'LATCH alert',
      body: notification.body,
      notificationDetails: const NotificationDetails(
        android: AndroidNotificationDetails(
          'latch_alerts',
          'LATCH alerts',
          channelDescription: 'Geofence, battery, and tracker-status alerts.',
          importance: Importance.high,
          priority: Priority.high,
        ),
        iOS: DarwinNotificationDetails(),
      ),
      payload: notificationId?.toString(),
    );
  }

  void _handleOpenedMessage(RemoteMessage message) {
    final id = int.tryParse(message.data['notification_id']?.toString() ?? '');
    if (id != null) _deliverTap(id);
  }

  void _deliverTap(int id) {
    final callback = onNotificationTapped;
    if (callback == null) {
      _pendingNotificationId = id;
      return;
    }
    callback(id);
  }

  void _deliverPendingTap() {
    final id = _pendingNotificationId;
    if (id == null || onNotificationTapped == null) return;
    _pendingNotificationId = null;
    onNotificationTapped!(id);
  }
}
