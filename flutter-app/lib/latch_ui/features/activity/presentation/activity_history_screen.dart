import 'package:flutter/material.dart';

import '../../../application/latch_controller.dart';
import '../../../core/components/feedback/latch_empty_state.dart';
import '../../../core/components/feedback/latch_error_state.dart';
import '../../../core/components/feedback/latch_loading_state.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_spacing.dart';
import '../../../data/api/latch_api.dart';
import '../../../data/models/latch_models.dart';

typedef ActivityItemCallback = Future<void> Function(int itemId);

class ActivityHistoryScreen extends StatefulWidget {
  const ActivityHistoryScreen({
    super.key,
    required this.controller,
    this.onViewItemPressed,
  });

  final LatchController controller;
  final ActivityItemCallback? onViewItemPressed;

  @override
  State<ActivityHistoryScreen> createState() => _ActivityHistoryScreenState();
}

class _ActivityHistoryScreenState extends State<ActivityHistoryScreen> {
  List<LatchActivity> _activities = const [];
  String? _nextCursor;
  String? _error;
  bool _loadingInitial = true;
  bool _loadingMore = false;

  @override
  void initState() {
    super.initState();
    _load(reset: true);
  }

  Future<void> _load({required bool reset}) async {
    if (!reset && (_loadingMore || _nextCursor == null)) return;

    if (reset) {
      setState(() {
        _loadingInitial = true;
        _error = null;
      });
    } else {
      setState(() => _loadingMore = true);
    }

    try {
      final page = await widget.controller.loadActivity(
        cursor: reset ? null : _nextCursor,
      );
      if (!mounted) return;
      setState(() {
        _activities = reset
            ? page.activities
            : [..._activities, ...page.activities];
        _nextCursor = page.nextCursor;
        _loadingInitial = false;
        _loadingMore = false;
        _error = null;
      });
    } on Object catch (error) {
      if (!mounted) return;
      final message = error is LatchApiException
          ? error.message
          : 'Could not load activity history.';
      setState(() {
        _loadingInitial = false;
        _loadingMore = false;
        if (reset) _error = message;
      });
      if (!reset) {
        ScaffoldMessenger.of(
          context,
        ).showSnackBar(SnackBar(content: Text(message)));
      }
    }
  }

  String _formatTime(DateTime? value) {
    if (value == null) return 'Time unavailable';
    final local = value.toLocal();
    final month = local.month.toString().padLeft(2, '0');
    final day = local.day.toString().padLeft(2, '0');
    final hour = local.hour.toString().padLeft(2, '0');
    final minute = local.minute.toString().padLeft(2, '0');
    return '${local.year}-$month-$day $hour:$minute';
  }

  ({IconData icon, Color color}) _presentation(String eventType) {
    if (eventType.startsWith('geofence_')) {
      return (icon: Icons.fence_rounded, color: AppColors.trackerAccent);
    }
    if (eventType.startsWith('battery_')) {
      return (icon: Icons.battery_alert_rounded, color: AppColors.warning);
    }
    if (eventType == 'device_offline') {
      return (icon: Icons.cloud_off_rounded, color: AppColors.error);
    }
    if (eventType == 'device_online') {
      return (icon: Icons.cloud_done_rounded, color: AppColors.success);
    }
    if (eventType == 'item_released') {
      return (icon: Icons.link_off_rounded, color: AppColors.error);
    }
    return (icon: Icons.inventory_2_outlined, color: AppColors.primary);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      appBar: AppBar(
        title: const Text('Activity History'),
        actions: [
          IconButton(
            onPressed: _loadingInitial ? null : () => _load(reset: true),
            tooltip: 'Refresh activity',
            icon: const Icon(Icons.refresh_rounded),
          ),
        ],
      ),
      body: SafeArea(child: _buildBody()),
    );
  }

  Widget _buildBody() {
    if (_loadingInitial) {
      return const LatchLoadingState(message: 'Loading activity history…');
    }
    if (_error != null) {
      return LatchErrorState(
        message: _error!,
        onRetryPressed: () => _load(reset: true),
      );
    }
    if (_activities.isEmpty) {
      return const LatchEmptyState(
        icon: Icons.history_rounded,
        title: 'No activity recorded yet.',
      );
    }

    return RefreshIndicator(
      onRefresh: () => _load(reset: true),
      child: ListView.separated(
        physics: const AlwaysScrollableScrollPhysics(),
        padding: const EdgeInsets.all(AppSpacing.md),
        itemCount: _activities.length + (_nextCursor == null ? 0 : 1),
        separatorBuilder: (_, _) => const SizedBox(height: AppSpacing.sm),
        itemBuilder: (context, index) {
          if (index == _activities.length) {
            return Padding(
              padding: const EdgeInsets.symmetric(vertical: AppSpacing.sm),
              child: FilledButton.tonalIcon(
                onPressed: _loadingMore ? null : () => _load(reset: false),
                icon: _loadingMore
                    ? const SizedBox.square(
                        dimension: 18,
                        child: CircularProgressIndicator(strokeWidth: 2),
                      )
                    : const Icon(Icons.expand_more_rounded),
                label: Text(
                  _loadingMore ? 'Loading more…' : 'Load older activity',
                ),
              ),
            );
          }
          return _ActivityCard(
            activity: _activities[index],
            time: _formatTime(_activities[index].occurredAt),
            presentation: _presentation(_activities[index].eventType),
            onViewItemPressed:
                _activities[index].itemId == null ||
                    widget.onViewItemPressed == null
                ? null
                : () => widget.onViewItemPressed!(_activities[index].itemId!),
          );
        },
      ),
    );
  }
}

class _ActivityCard extends StatelessWidget {
  const _ActivityCard({
    required this.activity,
    required this.time,
    required this.presentation,
    this.onViewItemPressed,
  });

  final LatchActivity activity;
  final String time;
  final ({IconData icon, Color color}) presentation;
  final Future<void> Function()? onViewItemPressed;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    return Card(
      margin: EdgeInsets.zero,
      child: Padding(
        padding: const EdgeInsets.all(AppSpacing.md),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Semantics(
              label: activity.eventType.replaceAll('_', ' '),
              child: CircleAvatar(
                backgroundColor: presentation.color.withValues(alpha: 0.12),
                foregroundColor: presentation.color,
                child: Icon(presentation.icon),
              ),
            ),
            const SizedBox(width: AppSpacing.md),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(activity.title, style: textTheme.titleSmall),
                  if (activity.description.isNotEmpty) ...[
                    const SizedBox(height: AppSpacing.xs),
                    Text(activity.description, style: textTheme.bodyMedium),
                  ],
                  const SizedBox(height: AppSpacing.xs),
                  Text(
                    [
                      if (activity.itemName != null) activity.itemName!,
                      time,
                      activity.source,
                    ].join(' • '),
                    style: textTheme.bodySmall?.copyWith(
                      color: AppColors.textSecondary,
                    ),
                  ),
                  if (onViewItemPressed != null) ...[
                    const SizedBox(height: AppSpacing.xs),
                    TextButton.icon(
                      onPressed: onViewItemPressed,
                      icon: const Icon(Icons.open_in_new_rounded, size: 18),
                      label: const Text('View item'),
                    ),
                  ],
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
