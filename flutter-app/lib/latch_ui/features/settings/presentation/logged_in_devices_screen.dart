import 'package:flutter/material.dart';

import '../../../application/latch_controller.dart';
import '../../../core/components/feedback/latch_error_state.dart';
import '../../../core/components/feedback/latch_loading_state.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_spacing.dart';
import '../../../data/models/latch_models.dart';

class LoggedInDevicesScreen extends StatefulWidget {
  const LoggedInDevicesScreen({
    super.key,
    required this.controller,
    required this.onCurrentDeviceLoggedOut,
  });
  final LatchController controller;
  final VoidCallback onCurrentDeviceLoggedOut;
  @override
  State<LoggedInDevicesScreen> createState() => _LoggedInDevicesScreenState();
}

class _LoggedInDevicesScreenState extends State<LoggedInDevicesScreen> {
  List<LatchSession>? _sessions;
  String? _error;
  final Set<int> _revoking = <int>{};

  @override
  void initState() {
    super.initState();
    _load();
  }

  Future<void> _load() async {
    setState(() => _error = null);
    try {
      final result = await widget.controller.sessions();
      if (mounted) setState(() => _sessions = result);
    } on Object catch (error) {
      if (mounted) setState(() => _error = error.toString());
    }
  }

  Future<void> _revoke(LatchSession session) async {
    if (_revoking.contains(session.id)) return;
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(
          session.isCurrent ? 'Log out this device?' : 'Log out device?',
        ),
        content: Text(
          session.isCurrent
              ? 'You will return to the sign-in screen on this device.'
              : '${session.deviceName} will need to sign in again.',
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context, false),
            child: const Text('Cancel'),
          ),
          FilledButton(
            onPressed: () => Navigator.pop(context, true),
            child: const Text('Log Out'),
          ),
        ],
      ),
    );
    if (confirmed != true || !mounted) return;
    setState(() => _revoking.add(session.id));
    try {
      final current = await widget.controller.revokeSession(session.id);
      if (!mounted) return;
      if (current) {
        widget.onCurrentDeviceLoggedOut();
        return;
      }
      setState(
        () => _sessions = _sessions
            ?.where((item) => item.id != session.id)
            .toList(),
      );
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('${session.deviceName} was logged out.')),
      );
    } on Object catch (error) {
      if (mounted) {
        ScaffoldMessenger.of(
          context,
        ).showSnackBar(SnackBar(content: Text(error.toString())));
      }
    } finally {
      if (mounted) setState(() => _revoking.remove(session.id));
    }
  }

  String _lastActive(LatchSession session) {
    final value = session.lastActiveAt?.toLocal();
    if (value == null) return 'Activity unavailable';
    final difference = DateTime.now().difference(value);
    if (difference.inMinutes < 2) return 'Active now';
    if (difference.inHours < 1) {
      return 'Active ${difference.inMinutes} minutes ago';
    }
    if (difference.inDays < 1) return 'Active ${difference.inHours} hours ago';
    return 'Active ${difference.inDays} days ago';
  }

  @override
  Widget build(BuildContext context) {
    final sessions = _sessions;
    return Scaffold(
      backgroundColor: AppColors.background,
      appBar: AppBar(title: const Text('Logged-in Devices')),
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(AppSpacing.screenHorizontal),
          child: _error != null
              ? LatchErrorState(message: _error!, onRetryPressed: _load)
              : sessions == null
              ? const LatchLoadingState()
              : ListView.separated(
                  itemCount: sessions.length,
                  separatorBuilder: (_, _) => const Divider(height: 1),
                  itemBuilder: (context, index) {
                    final session = sessions[index];
                    return ListTile(
                      contentPadding: EdgeInsets.zero,
                      leading: Icon(
                        session.platform == 'ios'
                            ? Icons.phone_iphone
                            : Icons.devices_rounded,
                      ),
                      title: Text(session.deviceName),
                      subtitle: Text(
                        '${session.isCurrent ? 'This device · ' : ''}${_lastActive(session)}',
                      ),
                      trailing: _revoking.contains(session.id)
                          ? const SizedBox.square(
                              dimension: 22,
                              child: CircularProgressIndicator(strokeWidth: 2),
                            )
                          : TextButton(
                              onPressed: () => _revoke(session),
                              child: const Text('Log Out'),
                            ),
                    );
                  },
                ),
        ),
      ),
    );
  }
}
