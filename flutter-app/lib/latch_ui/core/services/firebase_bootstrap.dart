import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_crashlytics/firebase_crashlytics.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/foundation.dart';

import '../../../firebase_options.dart';
import '../errors/app_error_reporter.dart';
import 'push_notification_service.dart';

@pragma('vm:entry-point')
Future<void> latchFirebaseMessagingBackgroundHandler(
  RemoteMessage message,
) async {
  if (Firebase.apps.isEmpty) {
    await Firebase.initializeApp(
      options: DefaultFirebaseOptions.currentPlatform,
    );
  }
}

abstract final class FirebaseBootstrap {
  static const enabled = bool.fromEnvironment(
    'LATCH_FIREBASE_ENABLED',
    defaultValue: false,
  );

  static Future<PushNotificationService?> initialize() async {
    if (!enabled) return null;

    try {
      await Firebase.initializeApp(
        options: DefaultFirebaseOptions.currentPlatform,
      );
      FirebaseMessaging.onBackgroundMessage(
        latchFirebaseMessagingBackgroundHandler,
      );
      await FirebaseCrashlytics.instance.setCrashlyticsCollectionEnabled(
        !kDebugMode,
      );
      AppErrorReporter.remoteSink =
          (error, stackTrace, {required fatal, required context}) async {
            await FirebaseCrashlytics.instance.recordError(
              error,
              stackTrace,
              fatal: fatal,
              reason: context,
            );
          };

      final service = PushNotificationService();
      await service.initialize();
      return service;
    } on Object catch (error, stackTrace) {
      AppErrorReporter.record(
        error,
        stackTrace,
        context: 'Firebase initialization',
      );
      return null;
    }
  }
}
