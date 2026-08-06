import 'dart:async';

import 'package:flutter_test/flutter_test.dart';
import 'package:latch/latch_ui/application/latch_controller.dart';
import 'package:latch/latch_ui/core/services/push_notification_service.dart';
import 'package:latch/latch_ui/data/api/latch_api.dart';
import 'package:latch/latch_ui/data/auth/token_storage.dart';
import 'package:latch/latch_ui/data/models/latch_models.dart';

void main() {
  test('session restoration does not wait for push registration', () async {
    final controller = LatchController(
      api: _HealthyApi(),
      tokenStorage: _MemoryTokenStorage(),
      pushNotifications: _HangingPushNotifications(),
    );

    final restored = await controller.restoreSession().timeout(
      const Duration(seconds: 1),
    );

    expect(restored, isTrue);
    expect(controller.isAuthenticated, isTrue);
  });

  test(
    'temporary restoration failure does not erase the stored token',
    () async {
      final storage = _MemoryTokenStorage();
      final api = _OfflineApi();
      final controller = LatchController(api: api, tokenStorage: storage);

      final restored = await controller.restoreSession();

      expect(restored, isFalse);
      expect(controller.isAuthenticated, isFalse);
      expect(api.token, 'stored-token');
      expect(storage.clearCalls, 0);
    },
  );
}

class _MemoryTokenStorage extends TokenStorage {
  int clearCalls = 0;

  @override
  Future<String?> read() async => 'stored-token';

  @override
  Future<void> write(String token) async {}

  @override
  Future<void> clear() async {
    clearCalls++;
  }
}

class _OfflineApi extends LatchApi {
  _OfflineApi() : super(baseUrl: 'https://example.test/api/v1');

  @override
  Future<LatchUser> me() => throw StateError('Server temporarily unavailable');
}

class _HealthyApi extends LatchApi {
  _HealthyApi() : super(baseUrl: 'https://example.test/api/v1');

  @override
  Future<LatchUser> me() async => const LatchUser(
    id: 1,
    name: 'QA User',
    email: 'qa@example.test',
    emailVerified: true,
    notificationsEnabled: true,
    notifyGeofenceEvents: true,
    notifyBatteryEvents: true,
    notifyDeviceStatusEvents: true,
  );

  @override
  Future<List<LatchItem>> items() async => const [];

  @override
  Future<LatchNotificationFeed> notifications() async =>
      const LatchNotificationFeed(notifications: [], unreadCount: 0);
}

class _HangingPushNotifications extends PushNotificationService {
  @override
  Future<void> activate(LatchApi api, {required int userId}) =>
      Completer<void>().future;
}
