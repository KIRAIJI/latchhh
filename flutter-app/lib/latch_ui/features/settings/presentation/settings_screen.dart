import 'package:flutter/material.dart';

import '../../../core/components/buttons/latch_button.dart';
import '../../../core/components/cards/latch_card.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_icons.dart';
import '../../../core/theme/app_spacing.dart';

typedef NotificationSettingCallback =
    Future<bool> Function({
      required bool enabled,
      required bool geofence,
      required bool battery,
      required bool deviceStatus,
    });

class SettingsScreen extends StatefulWidget {
  const SettingsScreen({
    super.key,
    required this.notificationsEnabled,
    required this.notifyGeofenceEvents,
    required this.notifyBatteryEvents,
    required this.notifyDeviceStatusEvents,
    required this.appVersion,
    required this.onNotificationsChanged,
    required this.onActivityHistoryPressed,
    required this.onPrivacyPressed,
    required this.onTermsPressed,
    required this.onDataSafetyPressed,
    required this.onChangePasswordPressed,
    required this.onLoggedInDevicesPressed,
    required this.onLogoutAllPressed,
    required this.onDeleteAccountPressed,
    required this.onBackPressed,
    this.passwordActionLabel = 'Change Password',
  });

  final bool notificationsEnabled;
  final bool notifyGeofenceEvents;
  final bool notifyBatteryEvents;
  final bool notifyDeviceStatusEvents;
  final String appVersion;
  final NotificationSettingCallback? onNotificationsChanged;
  final VoidCallback? onActivityHistoryPressed;
  final VoidCallback? onPrivacyPressed;
  final VoidCallback? onTermsPressed;
  final VoidCallback? onDataSafetyPressed;
  final VoidCallback? onChangePasswordPressed;
  final VoidCallback? onLoggedInDevicesPressed;
  final String passwordActionLabel;
  final VoidCallback? onLogoutAllPressed;
  final VoidCallback? onDeleteAccountPressed;
  final VoidCallback? onBackPressed;

  @override
  State<SettingsScreen> createState() => _SettingsScreenState();
}

class _SettingsScreenState extends State<SettingsScreen> {
  late bool _notificationsEnabled;
  late bool _notifyGeofenceEvents;
  late bool _notifyBatteryEvents;
  late bool _notifyDeviceStatusEvents;
  bool _savingPreferences = false;

  @override
  void initState() {
    super.initState();
    _notificationsEnabled = widget.notificationsEnabled;
    _notifyGeofenceEvents = widget.notifyGeofenceEvents;
    _notifyBatteryEvents = widget.notifyBatteryEvents;
    _notifyDeviceStatusEvents = widget.notifyDeviceStatusEvents;
  }

  @override
  void didUpdateWidget(SettingsScreen oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (oldWidget.notificationsEnabled != widget.notificationsEnabled) {
      _notificationsEnabled = widget.notificationsEnabled;
      _notifyGeofenceEvents = widget.notifyGeofenceEvents;
      _notifyBatteryEvents = widget.notifyBatteryEvents;
      _notifyDeviceStatusEvents = widget.notifyDeviceStatusEvents;
    }
  }

  Future<void> _savePreferences({
    bool? enabled,
    bool? geofence,
    bool? battery,
    bool? deviceStatus,
  }) async {
    if (_savingPreferences) return;
    final previous = (
      enabled: _notificationsEnabled,
      geofence: _notifyGeofenceEvents,
      battery: _notifyBatteryEvents,
      deviceStatus: _notifyDeviceStatusEvents,
    );
    setState(() {
      _savingPreferences = true;
      _notificationsEnabled = enabled ?? _notificationsEnabled;
      _notifyGeofenceEvents = geofence ?? _notifyGeofenceEvents;
      _notifyBatteryEvents = battery ?? _notifyBatteryEvents;
      _notifyDeviceStatusEvents = deviceStatus ?? _notifyDeviceStatusEvents;
    });
    final saved =
        await widget.onNotificationsChanged?.call(
          enabled: _notificationsEnabled,
          geofence: _notifyGeofenceEvents,
          battery: _notifyBatteryEvents,
          deviceStatus: _notifyDeviceStatusEvents,
        ) ??
        true;
    if (!saved && mounted) {
      setState(() {
        _notificationsEnabled = previous.enabled;
        _notifyGeofenceEvents = previous.geofence;
        _notifyBatteryEvents = previous.battery;
        _notifyDeviceStatusEvents = previous.deviceStatus;
      });
    }
    if (mounted) setState(() => _savingPreferences = false);
  }

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;

    return Scaffold(
      backgroundColor: AppColors.background,
      appBar: AppBar(
        leading: BackButton(onPressed: widget.onBackPressed),
        title: const Text('Settings'),
      ),
      body: SafeArea(
        child: ListView(
          padding: const EdgeInsets.all(AppSpacing.screenHorizontal),
          children: [
            Text('Notification Settings', style: textTheme.titleMedium),
            const SizedBox(height: AppSpacing.sm),
            LatchCard(
              padding: EdgeInsets.zero,
              child: SwitchListTile(
                value: _notificationsEnabled,
                onChanged: _savingPreferences
                    ? null
                    : (value) => _savePreferences(enabled: value),
                title: Text('Enable Notifications', style: textTheme.bodyLarge),
                subtitle: Text(
                  'Receive alerts for geofence events, battery, and device status.',
                  style: textTheme.bodySmall?.copyWith(
                    color: AppColors.textSecondary,
                  ),
                ),
                secondary: Icon(
                  AppIcons.notifications,
                  color: AppColors.textSecondary,
                ),
                contentPadding: const EdgeInsets.symmetric(
                  horizontal: AppSpacing.md,
                  vertical: AppSpacing.xs,
                ),
              ),
            ),
            const SizedBox(height: AppSpacing.sm),
            LatchCard(
              padding: EdgeInsets.zero,
              child: Column(
                children: [
                  SwitchListTile(
                    value: _notifyGeofenceEvents,
                    onChanged: !_notificationsEnabled || _savingPreferences
                        ? null
                        : (value) => _savePreferences(geofence: value),
                    title: const Text('Geofence events'),
                    subtitle: const Text('Enter and exit alerts'),
                    secondary: const Icon(Icons.fence_rounded),
                  ),
                  const Divider(height: 1),
                  SwitchListTile(
                    value: _notifyBatteryEvents,
                    onChanged: !_notificationsEnabled || _savingPreferences
                        ? null
                        : (value) => _savePreferences(battery: value),
                    title: const Text('Battery alerts'),
                    subtitle: const Text('Low and critical battery'),
                    secondary: const Icon(Icons.battery_alert_rounded),
                  ),
                  const Divider(height: 1),
                  SwitchListTile(
                    value: _notifyDeviceStatusEvents,
                    onChanged: !_notificationsEnabled || _savingPreferences
                        ? null
                        : (value) => _savePreferences(deviceStatus: value),
                    title: const Text('Tracker status'),
                    subtitle: const Text('Online and offline transitions'),
                    secondary: const Icon(Icons.router_outlined),
                  ),
                ],
              ),
            ),
            const SizedBox(height: AppSpacing.section),
            Text('Application Information', style: textTheme.titleMedium),
            const SizedBox(height: AppSpacing.sm),
            LatchCard(
              child: Row(
                children: [
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          'App Version',
                          style: textTheme.labelSmall?.copyWith(
                            color: AppColors.textSecondary,
                          ),
                        ),
                        const SizedBox(height: AppSpacing.xs),
                        Text(widget.appVersion, style: textTheme.bodyMedium),
                      ],
                    ),
                  ),
                ],
              ),
            ),
            const SizedBox(height: AppSpacing.section),
            Text('Legal & Privacy', style: textTheme.titleMedium),
            const SizedBox(height: AppSpacing.sm),
            LatchCard(
              padding: EdgeInsets.zero,
              child: Column(
                children: [
                  ListTile(
                    leading: const Icon(Icons.privacy_tip_outlined),
                    title: const Text('Privacy Policy'),
                    trailing: const Icon(Icons.chevron_right_rounded),
                    onTap: widget.onPrivacyPressed,
                  ),
                  const Divider(height: 1),
                  ListTile(
                    leading: const Icon(Icons.description_outlined),
                    title: const Text('Terms of Service'),
                    trailing: const Icon(Icons.chevron_right_rounded),
                    onTap: widget.onTermsPressed,
                  ),
                  const Divider(height: 1),
                  ListTile(
                    leading: const Icon(Icons.shield_outlined),
                    title: const Text('Data Safety'),
                    trailing: const Icon(Icons.chevron_right_rounded),
                    onTap: widget.onDataSafetyPressed,
                  ),
                ],
              ),
            ),
            const SizedBox(height: AppSpacing.section),
            Text('Account', style: textTheme.titleMedium),
            const SizedBox(height: AppSpacing.sm),
            LatchButton(
              label: 'Activity History',
              variant: LatchButtonVariant.secondary,
              fullWidth: true,
              onPressed: widget.onActivityHistoryPressed,
            ),
            const SizedBox(height: AppSpacing.sm),
            LatchButton(
              label: widget.passwordActionLabel,
              variant: LatchButtonVariant.secondary,
              fullWidth: true,
              onPressed: widget.onChangePasswordPressed,
            ),
            const SizedBox(height: AppSpacing.sm),
            LatchButton(
              label: 'View Logged-in Devices',
              variant: LatchButtonVariant.secondary,
              fullWidth: true,
              onPressed: widget.onLoggedInDevicesPressed,
            ),
            const SizedBox(height: AppSpacing.sm),
            LatchButton(
              label: 'Log Out of All Devices',
              variant: LatchButtonVariant.secondary,
              fullWidth: true,
              onPressed: widget.onLogoutAllPressed,
            ),
            const SizedBox(height: AppSpacing.sm),
            LatchButton(
              label: 'Delete Account Permanently',
              variant: LatchButtonVariant.destructive,
              fullWidth: true,
              onPressed: widget.onDeleteAccountPressed,
            ),
          ],
        ),
      ),
    );
  }
}
