import 'dart:async';

import 'package:flutter/foundation.dart';

import '../core/services/google_oauth_service.dart';
import '../core/services/push_notification_service.dart';
import '../data/api/latch_api.dart';
import '../data/auth/token_storage.dart';
import '../data/models/latch_models.dart';

class LatchController extends ChangeNotifier {
  LatchController({
    LatchApi? api,
    TokenStorage? tokenStorage,
    GoogleOAuthService? googleOAuth,
    this.pushNotifications,
    this.onSessionExpired,
  }) : api = api ?? LatchApi(),
       _tokenStorage = tokenStorage ?? TokenStorage(),
       _googleOAuth = googleOAuth ?? FirebaseGoogleOAuthService() {
    this.api.onUnauthorized = _handleUnauthorized;
  }

  final LatchApi api;
  final TokenStorage _tokenStorage;
  final GoogleOAuthService _googleOAuth;
  final PushNotificationService? pushNotifications;
  final VoidCallback? onSessionExpired;
  bool _sessionClearInProgress = false;
  bool _silentItemsRefreshInProgress = false;

  LatchUser? user;
  List<LatchItem> items = const [];
  List<LatchNotification> notifications = const [];
  int unreadNotificationCount = 0;
  bool itemsLoading = false;
  bool notificationsLoading = false;
  String? itemsError;
  String? notificationsError;
  int? _pendingNotificationId;

  bool get isAuthenticated => user != null && api.hasToken;
  bool get isGoogleSignInAvailable => _googleOAuth.isAvailable;

  Future<bool> restoreSession() async {
    final storedToken = await _tokenStorage.read();
    if (storedToken == null || storedToken.isEmpty) {
      return false;
    }

    api.token = storedToken;
    try {
      user = await api.me();
      _activatePushIfEligible();
      notifyListeners();
      if (user!.emailVerified) {
        await refreshDashboard();
      }
      return true;
    } on Object {
      // Keep the encrypted token when session restoration fails because the
      // server is temporarily unreachable or misconfigured. LatchApi invokes
      // onUnauthorized separately for an actual 401, which is the only case
      // that should permanently clear the session.
      return false;
    }
  }

  Future<void> register({
    required String name,
    required String email,
    required String password,
    required String passwordConfirmation,
    required bool acceptedTerms,
    required bool acknowledgedPrivacy,
  }) async {
    final result = await api.register(
      name: name,
      email: email,
      password: password,
      passwordConfirmation: passwordConfirmation,
      acceptedTerms: acceptedTerms,
      acknowledgedPrivacy: acknowledgedPrivacy,
    );
    await _acceptAuthentication(result);
  }

  Future<void> login({required String email, required String password}) async {
    final result = await api.login(email: email, password: password);
    await _acceptAuthentication(result);
  }

  Future<void> loginWithGoogle() async {
    try {
      final idToken = await _googleOAuth.signIn();
      final result = await api.loginWithGoogle(idToken);
      await _acceptAuthentication(result);
    } on Object {
      await _googleOAuth.signOut();
      rethrow;
    }
  }

  Future<void> _acceptAuthentication(LatchAuthResult result) async {
    api.token = result.token;
    user = result.user;
    await _tokenStorage.write(result.token);
    _activatePushIfEligible();
    notifyListeners();
    if (user!.emailVerified) {
      await refreshDashboard();
    }
  }

  Future<void> logout() async {
    try {
      await pushNotifications?.deactivate(api);
      await api.logout();
    } finally {
      await _clearSession();
    }
  }

  Future<void> logoutAll() async {
    await pushNotifications?.deactivate(api);
    try {
      await api.logoutAll();
    } finally {
      await _clearSession();
    }
  }

  Future<void> _clearSession() async {
    await _googleOAuth.signOut();
    api.token = null;
    user = null;
    items = const [];
    notifications = const [];
    unreadNotificationCount = 0;
    itemsError = null;
    notificationsError = null;
    notifyListeners();
    try {
      await _tokenStorage.clear();
    } on Object catch (error) {
      debugPrint('Could not clear the stored authentication token: $error');
    }
  }

  void _activatePushIfEligible() {
    final currentUser = user;
    final service = pushNotifications;
    if (currentUser == null || !currentUser.emailVerified || service == null) {
      return;
    }

    unawaited(_activatePush(service, currentUser.id));
  }

  Future<void> _activatePush(
    PushNotificationService service,
    int userId,
  ) async {
    try {
      await service.activate(api, userId: userId);
    } on Object catch (error) {
      debugPrint(
        'Push activation failed after authentication; the session remains valid: '
        '$error',
      );
    }
  }

  Future<void> forgotPassword(String email) => api.forgotPassword(email);

  Future<void> requestPasswordSetup() => api.requestPasswordSetup();

  Future<void> resetPassword({
    required String email,
    required String token,
    required String password,
    required String passwordConfirmation,
  }) async {
    await api.resetPassword(
      email: email,
      token: token,
      password: password,
      passwordConfirmation: passwordConfirmation,
    );
    if (isAuthenticated) {
      await _clearSession();
    }
  }

  Future<void> resendEmailVerification() => api.resendEmailVerification();

  void openNotificationFromPush(int notificationId) {
    _pendingNotificationId = notificationId;
    notifyListeners();
  }

  int? consumePendingNotificationId() {
    final value = _pendingNotificationId;
    _pendingNotificationId = null;
    return value;
  }

  void _handleUnauthorized() {
    if (_sessionClearInProgress) return;
    _sessionClearInProgress = true;
    unawaited(
      _clearSession().whenComplete(() {
        _sessionClearInProgress = false;
        onSessionExpired?.call();
      }),
    );
  }

  Future<void> refreshDashboard() async {
    await Future.wait<void>([refreshItems(), refreshNotifications()]);
  }

  Future<void> refreshItems() async {
    itemsLoading = true;
    itemsError = null;
    notifyListeners();
    try {
      items = await api.items();
    } on Object catch (error) {
      itemsError = _message(error);
    } finally {
      itemsLoading = false;
      notifyListeners();
    }
  }

  Future<void> refreshItemsSilently() async {
    if (itemsLoading || _silentItemsRefreshInProgress) return;

    _silentItemsRefreshInProgress = true;
    try {
      items = await api.items();
      itemsError = null;
      notifyListeners();
    } on Object {
      // Keep the last usable map state during transient background failures.
    } finally {
      _silentItemsRefreshInProgress = false;
    }
  }

  Future<void> refreshItemsFromTracker() async {
    itemsLoading = true;
    itemsError = null;
    notifyListeners();
    try {
      if (items.isEmpty) {
        items = await api.items();
      } else {
        items = await Future.wait(
          items.map((item) => api.refreshItem(item.id)),
        );
      }
    } on Object catch (error) {
      itemsError = _message(error);
      try {
        items = await api.items();
      } on Object catch (fallbackError) {
        itemsError ??= _message(fallbackError);
      }
    } finally {
      itemsLoading = false;
      notifyListeners();
    }
  }

  Future<void> refreshNotifications() async {
    notificationsLoading = true;
    notificationsError = null;
    notifyListeners();
    try {
      final feed = await api.notifications();
      notifications = feed.notifications;
      unreadNotificationCount = feed.unreadCount;
    } on Object catch (error) {
      notificationsError = _message(error);
    } finally {
      notificationsLoading = false;
      notifyListeners();
    }
  }

  Future<void> claimItem(String deviceUid, String itemName) async {
    await api.claimItem(deviceUid: deviceUid, itemName: itemName);
    await refreshItems();
  }

  Future<void> renameItem(int itemId, String itemName) async {
    await api.renameItem(itemId, itemName);
    await refreshItems();
  }

  Future<void> releaseItem(int itemId) async {
    await api.releaseItem(itemId);
    await Future.wait<void>([refreshItems(), refreshNotifications()]);
  }

  Future<LatchGeofence?> loadGeofence(int itemId) => api.geofence(itemId);

  Future<void> saveGeofence({
    required int itemId,
    required String name,
    required double centerLatitude,
    required double centerLongitude,
    required double radiusMeters,
    required bool notifyOnEnter,
    required bool notifyOnExit,
    required bool isActive,
  }) async {
    await api.saveGeofence(
      itemId: itemId,
      name: name,
      centerLatitude: centerLatitude,
      centerLongitude: centerLongitude,
      radiusMeters: radiusMeters,
      notifyOnEnter: notifyOnEnter,
      notifyOnExit: notifyOnExit,
      isActive: isActive,
    );
    await refreshItems();
  }

  Future<void> deleteGeofence(int itemId) async {
    await api.deleteGeofence(itemId);
    await refreshItems();
  }

  Future<List<LatchPosition>> loadLocationHistory(int itemId) {
    return api.locationHistory(itemId);
  }

  Future<LatchActivityFeed> loadActivity({String? cursor, int perPage = 50}) {
    return api.activity(cursor: cursor, perPage: perPage);
  }

  Future<void> markNotificationRead(int notificationId) async {
    await api.markNotificationRead(notificationId);
    await refreshNotifications();
  }

  Future<void> markAllNotificationsRead() async {
    await api.markAllNotificationsRead();
    await refreshNotifications();
  }

  Future<void> deleteNotification(int notificationId) async {
    final previousNotifications = notifications;
    final previousUnreadCount = unreadNotificationCount;
    final deleted = notifications
        .where((notification) => notification.id == notificationId)
        .firstOrNull;

    notifications = notifications
        .where((notification) => notification.id != notificationId)
        .toList();
    if (deleted != null && !deleted.isRead && unreadNotificationCount > 0) {
      unreadNotificationCount--;
    }
    notifyListeners();

    try {
      await api.deleteNotification(notificationId);
    } on Object {
      notifications = previousNotifications;
      unreadNotificationCount = previousUnreadCount;
      notifyListeners();
      rethrow;
    }
  }

  Future<void> updateProfile({
    required String name,
    required String email,
    String? currentPassword,
  }) async {
    user = await api.updateProfile(
      name: name,
      email: email,
      currentPassword: currentPassword,
    );
    notifyListeners();
  }

  Future<void> refreshProfile() async {
    user = await api.me();
    _activatePushIfEligible();
    notifyListeners();
  }

  Future<int> changePassword({
    required String currentPassword,
    required String password,
    required String passwordConfirmation,
  }) {
    return api.changePassword(
      currentPassword: currentPassword,
      password: password,
      passwordConfirmation: passwordConfirmation,
    );
  }

  Future<void> updateNotificationSetting(bool enabled) async {
    final current = user;
    user = await api.updateSettings(
      notificationsEnabled: enabled,
      notifyGeofenceEvents: current?.notifyGeofenceEvents,
      notifyBatteryEvents: current?.notifyBatteryEvents,
      notifyDeviceStatusEvents: current?.notifyDeviceStatusEvents,
    );
    notifyListeners();
  }

  Future<void> updateNotificationPreferences({
    required bool enabled,
    required bool geofence,
    required bool battery,
    required bool deviceStatus,
  }) async {
    user = await api.updateSettings(
      notificationsEnabled: enabled,
      notifyGeofenceEvents: geofence,
      notifyBatteryEvents: battery,
      notifyDeviceStatusEvents: deviceStatus,
    );
    notifyListeners();
  }

  Future<void> uploadProfilePhoto({
    required Uint8List bytes,
    required String filename,
  }) async {
    user = await api.uploadProfilePhoto(bytes: bytes, filename: filename);
    notifyListeners();
  }

  Future<void> removeProfilePhoto() async {
    user = await api.removeProfilePhoto();
    notifyListeners();
  }

  Future<void> deleteAccount([String? currentPassword]) async {
    final currentUser = user;
    if (currentUser == null) return;

    String? oauthIdToken;
    if (!currentUser.hasPassword) {
      oauthIdToken = await _googleOAuth.reauthenticate();
    }

    await pushNotifications?.deactivate(api);
    await api.deleteAccount(
      currentPassword: currentPassword,
      oauthIdToken: oauthIdToken,
    );
    await _clearSession();
  }

  String _message(Object error) {
    return error is LatchApiException
        ? error.message
        : 'Could not reach the LATCH server. Check your connection.';
  }

  @override
  void dispose() {
    api.onUnauthorized = null;
    api.close();
    super.dispose();
  }
}
