import 'dart:async';

import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:image_picker/image_picker.dart';
import 'package:package_info_plus/package_info_plus.dart';

import '../../main.dart';
import '../application/latch_controller.dart';
import '../core/components/navigation/latch_navigation_bar.dart';
import '../core/services/google_oauth_service.dart';
import '../core/theme/app_colors.dart';
import '../core/theme/app_spacing.dart';
import '../core/validation/input_validation.dart';
import '../data/api/latch_api.dart';
import '../data/models/latch_models.dart';
import '../features/activity/presentation/activity_history_screen.dart';
import '../features/epaper/presentation/epaper_tab_screen.dart';
import '../features/geofence/presentation/geofence_flow_screen.dart';
import '../features/items/presentation/items_screen.dart';
import '../features/items/presentation/location_history_screen.dart';
import '../features/items/presentation/widgets/add_item_bottom_sheet.dart';
import '../features/items/presentation/widgets/item_details_bottom_sheet.dart';
import '../features/items/presentation/widgets/items_map.dart';
import '../features/items/presentation/widgets/remove_device_dialog.dart';
import '../features/items/presentation/widgets/rename_item_bottom_sheet.dart';
import '../features/legal/presentation/legal_document_screen.dart';
import '../features/notifications/presentation/notifications_screen.dart';
import '../features/notifications/presentation/notification_details_screen.dart';
import '../features/notifications/presentation/widgets/notification_card.dart';
import '../features/profile/presentation/profile_screen.dart';
import '../features/profile/presentation/widgets/edit_profile_bottom_sheet.dart';
import '../features/profile/presentation/widgets/logout_confirmation_dialog.dart';
import '../features/profile/presentation/widgets/update_profile_photo_bottom_sheet.dart';
import '../features/settings/presentation/settings_screen.dart';
import '../features/settings/presentation/widgets/change_password_bottom_sheet.dart';
import '../features/settings/presentation/widgets/delete_account_dialog.dart';
import 'item_presentation_adapter.dart';

class LatchIntegratedShell extends StatefulWidget {
  const LatchIntegratedShell({
    super.key,
    required this.controller,
    required this.onSignedOut,
  });

  final LatchController controller;
  final VoidCallback onSignedOut;

  @override
  State<LatchIntegratedShell> createState() => _LatchIntegratedShellState();
}

class _LatchIntegratedShellState extends State<LatchIntegratedShell>
    with WidgetsBindingObserver {
  int _selectedIndex = 0;
  final UploaderBridge _uploaderBridge = UploaderBridge();
  final ImagePicker _imagePicker = ImagePicker();
  Timer? _dashboardRefreshTimer;
  Timer? _itemsRefreshTimer;
  AppLifecycleState _lifecycleState = AppLifecycleState.resumed;
  String _appVersion = '1.0.0 (1)';

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
    widget.controller.addListener(_onControllerChanged);
    _loadAppVersion();
    if (widget.controller.items.isEmpty) {
      WidgetsBinding.instance.addPostFrameCallback((_) {
        if (mounted) {
          unawaited(widget.controller.refreshDashboard());
        }
      });
    }
    _itemsRefreshTimer = Timer.periodic(const Duration(seconds: 10), (_) {
      if (_lifecycleState == AppLifecycleState.resumed &&
          _selectedIndex == 0 &&
          widget.controller.isAuthenticated) {
        unawaited(widget.controller.refreshItemsSilently());
      }
    });
    _dashboardRefreshTimer = Timer.periodic(const Duration(seconds: 60), (_) {
      if (_lifecycleState == AppLifecycleState.resumed &&
          widget.controller.isAuthenticated) {
        if (_selectedIndex == 0) {
          unawaited(widget.controller.refreshNotifications());
        } else {
          unawaited(widget.controller.refreshDashboard());
        }
      }
    });
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    _lifecycleState = state;
    if (state == AppLifecycleState.resumed &&
        widget.controller.isAuthenticated) {
      unawaited(widget.controller.refreshDashboard());
    }
  }

  Future<void> _loadAppVersion() async {
    final info = await PackageInfo.fromPlatform();
    if (mounted) {
      setState(() => _appVersion = '${info.version} (${info.buildNumber})');
    }
  }

  void _onControllerChanged() {
    if (!mounted) return;
    if (!widget.controller.isAuthenticated) {
      WidgetsBinding.instance.addPostFrameCallback((_) {
        if (mounted) widget.onSignedOut();
      });
      return;
    }
    setState(() {});
    final notificationId = widget.controller.consumePendingNotificationId();
    if (notificationId != null) {
      WidgetsBinding.instance.addPostFrameCallback((_) {
        if (mounted) unawaited(_openPushNotification(notificationId));
      });
    }
  }

  Future<void> _openPushNotification(int notificationId) async {
    setState(() => _selectedIndex = 2);
    await widget.controller.refreshNotifications();
    if (!mounted) return;
    final card = _notificationCards()
        .where((entry) => entry.notificationId == notificationId)
        .firstOrNull;
    if (card == null) {
      _showMessage('This notification is no longer available.');
      return;
    }
    card.onTap?.call();
  }

  @override
  void dispose() {
    _dashboardRefreshTimer?.cancel();
    _itemsRefreshTimer?.cancel();
    widget.controller.removeListener(_onControllerChanged);
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  String _errorMessage(Object error) {
    if (error is LatchApiException) return error.message;
    if (error is GoogleOAuthException) return error.message;
    return 'Could not complete the request. Check your connection.';
  }

  void _showMessage(String message, {bool error = false}) {
    if (!mounted) return;
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(message),
        backgroundColor: error ? AppColors.error : null,
      ),
    );
  }

  void _showApiError(LatchApiException error) {
    final message = error.formMessage;
    if (message != null) _showMessage(message, error: true);
  }

  Future<void> _showAddItemModal() async {
    var loading = false;
    String? deviceIdError;
    String? itemNameError;
    String? formError;

    await showModalBottomSheet<void>(
      context: context,
      isScrollControlled: true,
      useSafeArea: true,
      backgroundColor: Colors.transparent,
      builder: (sheetContext) {
        return StatefulBuilder(
          builder: (context, setModalState) {
            return AddItemBottomSheet(
              isLoading: loading,
              deviceIdError: deviceIdError,
              itemNameError: itemNameError,
              errorMessage: formError,
              onCancelPressed: () => Navigator.pop(sheetContext),
              onAddItemPressed: (deviceUid, itemName) async {
                final normalizedDeviceUid = deviceUid.trim().toUpperCase();
                final normalizedItemName = itemName.trim();
                final nextDeviceIdError = InputValidation.deviceUid(
                  normalizedDeviceUid,
                );
                final nextItemNameError = InputValidation.itemName(
                  normalizedItemName,
                );
                if (nextDeviceIdError != null || nextItemNameError != null) {
                  setModalState(() {
                    deviceIdError = nextDeviceIdError;
                    itemNameError = nextItemNameError;
                    formError = null;
                  });
                  return;
                }
                setModalState(() {
                  loading = true;
                  deviceIdError = null;
                  itemNameError = null;
                  formError = null;
                });
                try {
                  await widget.controller.claimItem(
                    normalizedDeviceUid,
                    normalizedItemName,
                  );
                  if (sheetContext.mounted) Navigator.pop(sheetContext);
                  _showMessage('Item claimed successfully.');
                } on LatchApiException catch (error) {
                  if (!sheetContext.mounted) return;
                  final nextDeviceIdError = error.fieldError('device_uid');
                  final nextItemNameError = error.fieldError('item_name');
                  setModalState(() {
                    loading = false;
                    deviceIdError = nextDeviceIdError;
                    itemNameError = nextItemNameError;
                    formError =
                        nextDeviceIdError == null && nextItemNameError == null
                        ? error.message
                        : null;
                  });
                } on Object catch (error) {
                  if (sheetContext.mounted) {
                    setModalState(() {
                      loading = false;
                      formError = _errorMessage(error);
                    });
                  }
                }
              },
            );
          },
        );
      },
    );
  }

  LatchItem? _itemById(int id) {
    for (final item in widget.controller.items) {
      if (item.id == id) return item;
    }
    return null;
  }

  Future<void> _refreshTrackerItems() async {
    final previousTimes = {
      for (final item in widget.controller.items)
        item.id: item.location.recordedAt,
    };
    final hadItems = previousTimes.isNotEmpty;

    await widget.controller.refreshItemsFromTracker();
    if (!mounted) return;

    final error = widget.controller.itemsError;
    if (error != null) {
      _showMessage(error, error: true);
      return;
    }

    if (!hadItems) {
      _showMessage('No items to refresh.');
      return;
    }

    final hasNewLocation = widget.controller.items.any((item) {
      final previous = previousTimes[item.id];
      final current = item.location.recordedAt;
      return current != null && (previous == null || current.isAfter(previous));
    });

    _showMessage(
      hasNewLocation
          ? 'Latest tracker location loaded.'
          : 'Tracker checked. No newer location was reported.',
    );
  }

  Future<void> _showRenameItemModal(LatchItem item) async {
    var loading = false;
    String? itemNameError;

    await showModalBottomSheet<void>(
      context: context,
      isScrollControlled: true,
      useSafeArea: true,
      backgroundColor: Colors.transparent,
      builder: (sheetContext) {
        return StatefulBuilder(
          builder: (context, setModalState) {
            return RenameItemBottomSheet(
              deviceId: item.deviceUid,
              initialItemName: item.itemName,
              isLoading: loading,
              itemNameError: itemNameError,
              onCancelPressed: () => Navigator.pop(sheetContext),
              onRenamePressed: (name) async {
                final normalizedName = name.trim();
                final nextItemNameError = InputValidation.itemName(
                  normalizedName,
                );
                if (nextItemNameError != null) {
                  setModalState(() => itemNameError = nextItemNameError);
                  return;
                }
                setModalState(() {
                  loading = true;
                  itemNameError = null;
                });
                try {
                  await widget.controller.renameItem(item.id, normalizedName);
                  if (sheetContext.mounted) Navigator.pop(sheetContext);
                  _showMessage('Item renamed successfully.');
                } on LatchApiException catch (error) {
                  if (sheetContext.mounted) {
                    setModalState(() {
                      loading = false;
                      itemNameError = error.fieldError('item_name');
                    });
                  }
                  _showApiError(error);
                } on Object catch (error) {
                  if (sheetContext.mounted) {
                    setModalState(() => loading = false);
                  }
                  _showMessage(_errorMessage(error), error: true);
                }
              },
            );
          },
        );
      },
    );
  }

  Future<void> _showRemoveDeviceDialog(LatchItem item) async {
    var loading = false;
    await showDialog<void>(
      context: context,
      barrierDismissible: !loading,
      builder: (dialogContext) {
        return StatefulBuilder(
          builder: (context, setDialogState) {
            return RemoveDeviceDialog(
              itemName: item.itemName,
              isLoading: loading,
              onCancelPressed: () => Navigator.pop(dialogContext),
              onConfirmPressed: () async {
                setDialogState(() => loading = true);
                try {
                  await widget.controller.releaseItem(item.id);
                  if (dialogContext.mounted) Navigator.pop(dialogContext);
                  _showMessage('Device released successfully.');
                } on Object catch (error) {
                  if (dialogContext.mounted) {
                    setDialogState(() => loading = false);
                  }
                  _showMessage(_errorMessage(error), error: true);
                }
              },
            );
          },
        );
      },
    );
  }

  Future<void> _openGeofenceScreen(LatchItem item) async {
    LatchGeofence? geofence;
    try {
      geofence = await widget.controller.loadGeofence(item.id);
    } on Object catch (error) {
      _showMessage(_errorMessage(error), error: true);
      return;
    }
    if (!mounted) return;

    final devicePoint = item.location.hasCoordinates
        ? LatLng(item.location.latitude!, item.location.longitude!)
        : null;
    final initialCenter = geofence == null
        ? (devicePoint ?? const LatLng(14.5995, 120.9842))
        : LatLng(geofence.centerLatitude, geofence.centerLongitude);

    await Navigator.of(context).push<void>(
      MaterialPageRoute<void>(
        builder: (routeContext) => GeofenceFlowScreen(
          itemName: item.itemName,
          initialCenter: initialCenter,
          devicePoint: devicePoint,
          initialGeofence: geofence,
          onBackPressed: () => Navigator.pop(routeContext),
          onSavePressed:
              (
                name,
                radius,
                notifyOnEnter,
                notifyOnExit,
                isActive,
                center,
              ) async {
                await widget.controller.saveGeofence(
                  itemId: item.id,
                  name: name,
                  centerLatitude: center.latitude,
                  centerLongitude: center.longitude,
                  radiusMeters: radius,
                  notifyOnEnter: notifyOnEnter,
                  notifyOnExit: notifyOnExit,
                  isActive: isActive,
                );
                if (routeContext.mounted) Navigator.pop(routeContext);
                _showMessage('Geofence saved successfully.');
              },
          onDeletePressed: () async {
            await widget.controller.deleteGeofence(item.id);
            if (routeContext.mounted) Navigator.pop(routeContext);
            _showMessage('Geofence deleted.');
          },
        ),
      ),
    );
  }

  void _openHistory(LatchItem item) {
    Navigator.of(context).push<void>(
      MaterialPageRoute<void>(
        builder: (_) =>
            LocationHistoryScreen(controller: widget.controller, item: item),
      ),
    );
  }

  void _showItemDetailsModal(ItemDetailsData details) {
    showModalBottomSheet<void>(
      context: context,
      isScrollControlled: true,
      useSafeArea: true,
      backgroundColor: Colors.transparent,
      builder: (sheetContext) {
        final maxHeight = MediaQuery.sizeOf(sheetContext).height * 0.88;
        void closeAndRun(VoidCallback action) {
          Navigator.pop(sheetContext);
          WidgetsBinding.instance.addPostFrameCallback((_) => action());
        }

        return Padding(
          padding: EdgeInsets.only(
            top: MediaQuery.paddingOf(sheetContext).top + AppSpacing.sm,
          ),
          child: ConstrainedBox(
            constraints: BoxConstraints(maxHeight: maxHeight),
            child: ItemDetailsBottomSheet.fromData(
              details,
              onRenamePressed: () {
                final item = _itemById(details.itemId);
                if (item != null) closeAndRun(() => _showRenameItemModal(item));
              },
              onManageGeofencePressed: () {
                final item = _itemById(details.itemId);
                if (item != null) closeAndRun(() => _openGeofenceScreen(item));
              },
              onViewHistoryPressed: () {
                final item = _itemById(details.itemId);
                if (item != null) closeAndRun(() => _openHistory(item));
              },
              onRemovePressed: () {
                final item = _itemById(details.itemId);
                if (item != null) {
                  closeAndRun(() => _showRemoveDeviceDialog(item));
                }
              },
            ),
          ),
        );
      },
    );
  }

  Future<void> _showEditProfileModal() async {
    final user = widget.controller.user;
    if (user == null) return;
    var loading = false;
    String? nameError;
    String? emailError;
    String? currentPasswordError;

    await showModalBottomSheet<void>(
      context: context,
      isScrollControlled: true,
      useSafeArea: true,
      backgroundColor: Colors.transparent,
      builder: (sheetContext) {
        return StatefulBuilder(
          builder: (context, setModalState) {
            return EditProfileBottomSheet(
              initialDisplayName: user.name,
              initialEmail: user.email,
              isLoading: loading,
              displayNameError: nameError,
              emailError: emailError,
              currentPasswordError: currentPasswordError,
              onCancelPressed: () => Navigator.pop(sheetContext),
              onSavePressed: (name, email, currentPassword) async {
                final normalizedName = name.trim();
                final normalizedEmail = email.trim();
                final nextNameError = InputValidation.name(normalizedName);
                var nextEmailError = InputValidation.email(normalizedEmail);
                final emailChanged =
                    normalizedEmail.toLowerCase() != user.email.toLowerCase();
                if (emailChanged && !user.hasPassword) {
                  nextEmailError ??=
                      'Set a password from Settings before changing your email.';
                }
                final nextCurrentPasswordError =
                    emailChanged && user.hasPassword && currentPassword.isEmpty
                    ? 'Current password is required to change your email.'
                    : null;
                if (nextNameError != null ||
                    nextEmailError != null ||
                    nextCurrentPasswordError != null) {
                  setModalState(() {
                    nameError = nextNameError;
                    emailError = nextEmailError;
                    currentPasswordError = nextCurrentPasswordError;
                  });
                  return;
                }
                setModalState(() {
                  loading = true;
                  nameError = null;
                  emailError = null;
                  currentPasswordError = null;
                });
                try {
                  await widget.controller.updateProfile(
                    name: normalizedName,
                    email: normalizedEmail,
                    currentPassword: currentPassword,
                  );
                  if (sheetContext.mounted) Navigator.pop(sheetContext);
                  _showMessage('Profile updated successfully.');
                } on LatchApiException catch (error) {
                  if (sheetContext.mounted) {
                    setModalState(() {
                      loading = false;
                      nameError = error.fieldError('name');
                      emailError = error.fieldError('email');
                      currentPasswordError = error.fieldError(
                        'current_password',
                      );
                    });
                  }
                  _showApiError(error);
                } on Object catch (error) {
                  if (sheetContext.mounted) {
                    setModalState(() => loading = false);
                  }
                  _showMessage(_errorMessage(error), error: true);
                }
              },
            );
          },
        );
      },
    );
  }

  void _showUpdateProfilePhotoModal() {
    final user = widget.controller.user;
    if (user == null) return;
    showModalBottomSheet<void>(
      context: context,
      isScrollControlled: true,
      useSafeArea: true,
      backgroundColor: Colors.transparent,
      builder: (sheetContext) {
        return UpdateProfilePhotoBottomSheet(
          hasProfilePhoto: user.profilePhotoUrl != null,
          onChooseFromGalleryPressed: () async {
            Navigator.pop(sheetContext);
            final file = await _imagePicker.pickImage(
              source: ImageSource.gallery,
              imageQuality: 90,
              maxWidth: 1600,
            );
            if (file == null) return;
            _showMessage('Uploading profile photo…');
            try {
              await widget.controller.uploadProfilePhoto(
                bytes: await file.readAsBytes(),
                filename: file.name,
              );
              if (mounted) {
                ScaffoldMessenger.of(context).hideCurrentSnackBar();
              }
              _showMessage('Profile photo updated.');
            } on Object catch (error) {
              if (mounted) {
                ScaffoldMessenger.of(context).hideCurrentSnackBar();
              }
              _showMessage(_errorMessage(error), error: true);
            }
          },
          onRemovePhotoPressed: () async {
            Navigator.pop(sheetContext);
            try {
              await widget.controller.removeProfilePhoto();
              _showMessage('Profile photo removed.');
            } on Object catch (error) {
              _showMessage(_errorMessage(error), error: true);
            }
          },
          onCancelPressed: () => Navigator.pop(sheetContext),
        );
      },
    );
  }

  Future<void> _showChangePasswordModal(BuildContext parentContext) async {
    var loading = false;
    String? currentError;
    String? passwordError;
    String? confirmationError;
    String? errorMessage;

    await showModalBottomSheet<void>(
      context: parentContext,
      isScrollControlled: true,
      useSafeArea: true,
      backgroundColor: Colors.transparent,
      builder: (sheetContext) {
        return StatefulBuilder(
          builder: (context, setModalState) {
            return ChangePasswordBottomSheet(
              isLoading: loading,
              currentPasswordError: currentError,
              passwordError: passwordError,
              passwordConfirmationError: confirmationError,
              errorMessage: errorMessage,
              onCancelPressed: () => Navigator.pop(sheetContext),
              onSavePressed: (current, password, confirmation) async {
                final nextCurrentError = InputValidation.requiredPassword(
                  current,
                );
                final nextPasswordError = InputValidation.newPassword(password);
                final nextConfirmationError =
                    InputValidation.passwordConfirmation(
                      password,
                      confirmation,
                    );
                if (nextCurrentError != null ||
                    nextPasswordError != null ||
                    nextConfirmationError != null) {
                  setModalState(() {
                    currentError = nextCurrentError;
                    passwordError = nextPasswordError;
                    confirmationError = nextConfirmationError;
                    errorMessage = null;
                  });
                  return;
                }
                setModalState(() {
                  loading = true;
                  currentError = null;
                  passwordError = null;
                  confirmationError = null;
                  errorMessage = null;
                });
                try {
                  final revoked = await widget.controller.changePassword(
                    currentPassword: current,
                    password: password,
                    passwordConfirmation: confirmation,
                  );
                  if (sheetContext.mounted) Navigator.pop(sheetContext);
                  _showMessage(
                    revoked == 0
                        ? 'Password changed successfully.'
                        : 'Password changed. $revoked other '
                              '${revoked == 1 ? 'session was' : 'sessions were'} '
                              'revoked.',
                  );
                } on LatchApiException catch (error) {
                  if (!sheetContext.mounted) return;
                  setModalState(() {
                    loading = false;
                    currentError = error.fieldError('current_password');
                    passwordError = error.fieldError('password');
                    confirmationError = error.fieldError(
                      'password_confirmation',
                    );
                    errorMessage = error.formMessage;
                  });
                } on Object catch (error) {
                  if (sheetContext.mounted) {
                    setModalState(() {
                      loading = false;
                      errorMessage = _errorMessage(error);
                    });
                  }
                }
              },
            );
          },
        );
      },
    );
  }

  Future<void> _showLogoutConfirmationDialog() async {
    var loading = false;
    await showDialog<void>(
      context: context,
      barrierDismissible: !loading,
      builder: (dialogContext) {
        return StatefulBuilder(
          builder: (context, setDialogState) {
            return LogoutConfirmationDialog(
              isLoading: loading,
              onCancelPressed: () => Navigator.pop(dialogContext),
              onConfirmPressed: () async {
                setDialogState(() => loading = true);
                try {
                  await widget.controller.logout();
                  if (dialogContext.mounted) Navigator.pop(dialogContext);
                  widget.onSignedOut();
                } on Object catch (error) {
                  if (dialogContext.mounted) {
                    setDialogState(() => loading = false);
                  }
                  _showMessage(_errorMessage(error), error: true);
                }
              },
            );
          },
        );
      },
    );
  }

  Future<void> _showDeleteAccountDialog(BuildContext parentContext) async {
    final requiresPassword = widget.controller.user?.hasPassword ?? true;
    var loading = false;
    String? passwordError;
    String? errorMessage;

    await showDialog<void>(
      context: parentContext,
      barrierDismissible: !loading,
      builder: (dialogContext) {
        return StatefulBuilder(
          builder: (context, setDialogState) {
            return DeleteAccountDialog(
              requiresPassword: requiresPassword,
              isLoading: loading,
              passwordError: passwordError,
              errorMessage: errorMessage,
              onCancelPressed: () => Navigator.pop(dialogContext),
              onConfirmPressed: (currentPassword) async {
                final nextPasswordError = requiresPassword
                    ? InputValidation.requiredPassword(currentPassword)
                    : null;
                if (nextPasswordError != null) {
                  setDialogState(() {
                    passwordError = nextPasswordError;
                    errorMessage = null;
                  });
                  return;
                }
                setDialogState(() {
                  loading = true;
                  passwordError = null;
                  errorMessage = null;
                });
                try {
                  await widget.controller.deleteAccount(
                    requiresPassword ? currentPassword : null,
                  );
                  if (dialogContext.mounted) Navigator.pop(dialogContext);
                  if (parentContext.mounted) Navigator.pop(parentContext);
                  widget.onSignedOut();
                } on LatchApiException catch (error) {
                  if (!dialogContext.mounted) return;
                  setDialogState(() {
                    loading = false;
                    passwordError = error.fieldError('current_password');
                    errorMessage =
                        error.fieldError('oauth_id_token') ?? error.formMessage;
                  });
                } on Object catch (error) {
                  if (dialogContext.mounted) {
                    setDialogState(() {
                      loading = false;
                      errorMessage = _errorMessage(error);
                    });
                  }
                }
              },
            );
          },
        );
      },
    );
  }

  void _openSettingsScreen() {
    final user = widget.controller.user;
    if (user == null) return;
    Navigator.of(context).push<void>(
      MaterialPageRoute<void>(
        builder: (routeContext) => SettingsScreen(
          notificationsEnabled: user.notificationsEnabled,
          notifyGeofenceEvents: user.notifyGeofenceEvents,
          notifyBatteryEvents: user.notifyBatteryEvents,
          notifyDeviceStatusEvents: user.notifyDeviceStatusEvents,
          appVersion: _appVersion,
          passwordActionLabel: user.hasPassword
              ? 'Change Password'
              : 'Set Password',
          onNotificationsChanged:
              ({
                required enabled,
                required geofence,
                required battery,
                required deviceStatus,
              }) async {
                try {
                  await widget.controller.updateNotificationPreferences(
                    enabled: enabled,
                    geofence: geofence,
                    battery: battery,
                    deviceStatus: deviceStatus,
                  );
                  return true;
                } on Object catch (error) {
                  _showMessage(_errorMessage(error), error: true);
                  return false;
                }
              },
          onActivityHistoryPressed: () {
            Navigator.of(context).push<void>(
              MaterialPageRoute<void>(
                builder: (_) => ActivityHistoryScreen(
                  controller: widget.controller,
                  onViewItemPressed: (itemId) async {
                    var item = _itemById(itemId);
                    if (item == null) {
                      await widget.controller.refreshItems();
                      item = _itemById(itemId);
                    }
                    if (!mounted) return;
                    if (item == null) {
                      _showMessage(
                        'This item is no longer available to this account.',
                        error: true,
                      );
                      return;
                    }
                    final details = ItemPresentationAdapter.buildItems([
                      item,
                    ]).first.itemDetails;
                    if (details != null) {
                      _showItemDetailsModal(details);
                    }
                  },
                ),
              ),
            );
          },
          onPrivacyPressed: () => Navigator.of(context).push<void>(
            MaterialPageRoute<void>(
              builder: (_) =>
                  const LegalDocumentScreen(type: LegalDocumentType.privacy),
            ),
          ),
          onTermsPressed: () => Navigator.of(context).push<void>(
            MaterialPageRoute<void>(
              builder: (_) =>
                  const LegalDocumentScreen(type: LegalDocumentType.terms),
            ),
          ),
          onDataSafetyPressed: () => Navigator.of(context).push<void>(
            MaterialPageRoute<void>(
              builder: (_) =>
                  const LegalDocumentScreen(type: LegalDocumentType.dataSafety),
            ),
          ),
          onChangePasswordPressed: () async {
            if (user.hasPassword) {
              await _showChangePasswordModal(routeContext);
              return;
            }
            try {
              await widget.controller.requestPasswordSetup();
              _showMessage(
                'We sent a secure password setup link to ${user.email}.',
              );
            } on Object catch (error) {
              _showMessage(_errorMessage(error), error: true);
            }
          },
          onLogoutAllPressed: () async {
            final confirmed = await showDialog<bool>(
              context: routeContext,
              builder: (dialogContext) => AlertDialog(
                title: const Text('Log out of all devices?'),
                content: const Text(
                  'Every LATCH session for this account, including this one, '
                  'will be signed out.',
                ),
                actions: [
                  TextButton(
                    onPressed: () => Navigator.pop(dialogContext, false),
                    child: const Text('Cancel'),
                  ),
                  FilledButton(
                    onPressed: () => Navigator.pop(dialogContext, true),
                    child: const Text('Log Out All'),
                  ),
                ],
              ),
            );
            if (confirmed != true) return;
            try {
              await widget.controller.logoutAll();
              if (routeContext.mounted) Navigator.pop(routeContext);
              widget.onSignedOut();
            } on Object catch (error) {
              _showMessage(_errorMessage(error), error: true);
            }
          },
          onDeleteAccountPressed: () => _showDeleteAccountDialog(routeContext),
          onBackPressed: () => Navigator.pop(routeContext),
        ),
      ),
    );
  }

  Widget _buildEpaperTab() {
    return Stack(
      fit: StackFit.expand,
      children: [
        IgnorePointer(
          child: Align(
            alignment: Alignment.topLeft,
            child: SizedBox(
              width: 1,
              height: 1,
              child: UploaderPage(bridge: _uploaderBridge, embedded: true),
            ),
          ),
        ),
        EpaperTabScreen(bridge: _uploaderBridge),
      ],
    );
  }

  String _formatNotificationTime(DateTime? value) {
    if (value == null) return 'Time unavailable';
    final local = value.toLocal();
    final now = DateTime.now();
    final difference = now.difference(local);
    if (difference.inMinutes < 1) return 'Just now';
    if (difference.inHours < 1) return '${difference.inMinutes}m ago';
    if (difference.inDays < 1) return '${difference.inHours}h ago';
    if (difference.inDays < 7) return '${difference.inDays}d ago';
    return '${local.year}-${local.month.toString().padLeft(2, '0')}-'
        '${local.day.toString().padLeft(2, '0')}';
  }

  ({String label, IconData icon, Color color}) _notificationType(String type) {
    if (type.contains('geofence')) {
      return (
        label: 'Geofence',
        icon: Icons.fence_rounded,
        color: AppColors.trackerAccent,
      );
    }
    if (type.contains('battery')) {
      return (
        label: 'Battery',
        icon: Icons.battery_alert_outlined,
        color: AppColors.warning,
      );
    }
    return (
      label: 'Device Status',
      icon: Icons.router_outlined,
      color: AppColors.textSecondary,
    );
  }

  List<NotificationCardData> _notificationCards() {
    return widget.controller.notifications.map((notification) {
      final presentation = _notificationType(notification.type);

      Future<void> markRead() async {
        try {
          await widget.controller.markNotificationRead(notification.id);
        } on Object catch (error) {
          _showMessage(_errorMessage(error), error: true);
        }
      }

      Future<bool> deleteNotification() async {
        try {
          await widget.controller.deleteNotification(notification.id);
          _showMessage('Notification deleted.');
          return true;
        } on Object catch (error) {
          _showMessage(_errorMessage(error), error: true);
          return false;
        }
      }

      Future<void> openNotification() async {
        if (!notification.isRead) {
          await markRead();
        }

        if (!mounted) return;
        final relatedItemId = notification.relatedItem?.id;
        var item = relatedItemId == null ? null : _itemById(relatedItemId);
        if (relatedItemId != null && item == null) {
          await widget.controller.refreshItems();
          item = _itemById(relatedItemId);
        }
        if (!mounted) return;

        final details = item == null
            ? null
            : ItemPresentationAdapter.buildItems([item]).first.itemDetails;

        await Navigator.of(context).push<void>(
          MaterialPageRoute<void>(
            builder: (_) => NotificationDetailsScreen(
              typeLabel: presentation.label,
              typeIcon: presentation.icon,
              typeIconColor: presentation.color,
              title: notification.title,
              message: notification.message,
              time: _formatNotificationTime(notification.createdAt),
              relatedItemName: notification.relatedItem?.itemName,
              onViewItemPressed: details == null
                  ? null
                  : () => _showItemDetailsModal(details),
              onDeletePressed: () => deleteNotification(),
            ),
          ),
        );
      }

      return NotificationCardData(
        notificationId: notification.id,
        typeLabel: presentation.label,
        typeIcon: presentation.icon,
        typeIconColor: presentation.color,
        title: notification.title,
        message: notification.message,
        time: _formatNotificationTime(notification.createdAt),
        relatedItemName: notification.relatedItem?.itemName,
        isUnread: !notification.isRead,
        onMarkAsRead: notification.isRead ? null : markRead,
        onTap: openNotification,
        onDelete: () async {
          await deleteNotification();
        },
      );
    }).toList();
  }

  @override
  Widget build(BuildContext context) {
    final controller = widget.controller;
    final user = controller.user;
    final itemCards = ItemPresentationAdapter.buildItems(controller.items);
    final destinations = <Widget>[
      ItemsScreen(
        mapContent: ItemsMap(items: controller.items),
        onAddItemPressed: _showAddItemModal,
        onRetryPressed: controller.refreshItems,
        onRefreshPressed: _refreshTrackerItems,
        onItemDetailsRequested: _showItemDetailsModal,
        items: itemCards,
        itemCount: itemCards.length,
        isLoading: controller.itemsLoading,
        errorMessage: controller.itemsError,
      ),
      _buildEpaperTab(),
      NotificationsScreen(
        notifications: _notificationCards(),
        unreadCount: controller.unreadNotificationCount,
        isLoading: controller.notificationsLoading,
        errorMessage: controller.notificationsError,
        onMarkAllAsRead: controller.unreadNotificationCount == 0
            ? null
            : () async {
                try {
                  await controller.markAllNotificationsRead();
                } on Object catch (error) {
                  _showMessage(_errorMessage(error), error: true);
                }
              },
        onRetry: controller.refreshNotifications,
      ),
      ProfileScreen(
        displayName: user?.name ?? '',
        email: user?.email ?? '',
        profilePhotoUrl: user?.profilePhotoUrl,
        hasProfilePhoto: user?.profilePhotoUrl != null,
        onEditPhotoPressed: _showUpdateProfilePhotoModal,
        onEditProfilePressed: _showEditProfileModal,
        onSettingsPressed: _openSettingsScreen,
        onLogoutPressed: _showLogoutConfirmationDialog,
        onRetryPressed: () async {
          try {
            await widget.controller.refreshProfile();
          } on Object catch (error) {
            _showMessage(_errorMessage(error), error: true);
          }
        },
      ),
    ];

    return Scaffold(
      backgroundColor: AppColors.background,
      body: IndexedStack(index: _selectedIndex, children: destinations),
      bottomNavigationBar: LatchNavigationBar(
        selectedIndex: _selectedIndex,
        notificationsUnreadCount: controller.unreadNotificationCount,
        onDestinationSelected: (index) {
          setState(() => _selectedIndex = index);
          if (index == 0) unawaited(controller.refreshItems());
          if (index == 2) unawaited(controller.refreshNotifications());
        },
      ),
    );
  }
}
