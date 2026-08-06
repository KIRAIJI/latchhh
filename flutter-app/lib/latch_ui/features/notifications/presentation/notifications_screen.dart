import 'package:flutter/material.dart';

import '../../../core/components/feedback/latch_empty_state.dart';
import '../../../core/components/feedback/latch_error_state.dart';
import '../../../core/components/feedback/latch_loading_state.dart';
import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_icons.dart';
import '../../../core/theme/app_spacing.dart';
import 'widgets/notification_card.dart';

class NotificationsScreen extends StatelessWidget {
  const NotificationsScreen({
    super.key,
    this.notifications = const [],
    this.unreadCount,
    this.isLoading = false,
    this.errorMessage,
    this.onMarkAllAsRead,
    this.onRetry,
  });

  final List<NotificationCardData> notifications;
  final int? unreadCount;
  final bool isLoading;
  final String? errorMessage;
  final VoidCallback? onMarkAllAsRead;
  final VoidCallback? onRetry;

  bool get _hasUnread {
    if (unreadCount != null && unreadCount! > 0) {
      return true;
    }

    return notifications.any((notification) => notification.isUnread);
  }

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;

    return SafeArea(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          Padding(
            padding: const EdgeInsets.fromLTRB(
              AppSpacing.screenHorizontal,
              AppSpacing.md,
              AppSpacing.screenHorizontal,
              AppSpacing.sm,
            ),
            child: LayoutBuilder(
              builder: (context, constraints) {
                final stackHeader = constraints.maxWidth < 360;

                if (stackHeader) {
                  return Column(
                    crossAxisAlignment: CrossAxisAlignment.stretch,
                    children: [
                      Text('Notifications', style: textTheme.headlineMedium),
                      if (unreadCount != null && unreadCount! > 0) ...[
                        const SizedBox(height: AppSpacing.xs),
                        Text(
                          '$unreadCount unread',
                          style: textTheme.bodySmall?.copyWith(
                            color: AppColors.textSecondary,
                          ),
                        ),
                      ],
                      if (_hasUnread) ...[
                        const SizedBox(height: AppSpacing.sm),
                        Align(
                          alignment: Alignment.centerLeft,
                          child: TextButton(
                            onPressed: onMarkAllAsRead,
                            child: const Text('Mark All as Read'),
                          ),
                        ),
                      ],
                    ],
                  );
                }

                return Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Expanded(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            'Notifications',
                            style: textTheme.headlineMedium,
                          ),
                          if (unreadCount != null && unreadCount! > 0) ...[
                            const SizedBox(height: AppSpacing.xs),
                            Text(
                              '$unreadCount unread',
                              style: textTheme.bodySmall?.copyWith(
                                color: AppColors.textSecondary,
                              ),
                            ),
                          ],
                        ],
                      ),
                    ),
                    if (_hasUnread)
                      TextButton(
                        onPressed: onMarkAllAsRead,
                        child: const Text('Mark All as Read'),
                      ),
                  ],
                );
              },
            ),
          ),
          Expanded(child: _buildBody(context)),
        ],
      ),
    );
  }

  Widget _buildBody(BuildContext context) {
    if (isLoading) {
      return const LatchLoadingState();
    }

    if (errorMessage != null) {
      return LatchErrorState(message: errorMessage!, onRetryPressed: onRetry);
    }

    if (notifications.isEmpty) {
      return const LatchEmptyState(
        icon: AppIcons.notifications,
        title: 'No notifications available.',
      );
    }

    return ListView.separated(
      padding: const EdgeInsets.fromLTRB(
        AppSpacing.lg,
        AppSpacing.sm,
        AppSpacing.lg,
        AppSpacing.lg,
      ),
      itemCount: notifications.length,
      separatorBuilder: (_, _) => const SizedBox(height: AppSpacing.sm),
      itemBuilder: (context, index) {
        final notification = notifications[index];
        final card = NotificationCard.fromData(notification);
        if (notification.onDelete == null) return card;

        return Dismissible(
          key: ValueKey<int>(notification.notificationId),
          direction: DismissDirection.endToStart,
          confirmDismiss: (_) => _confirmDelete(context),
          onDismissed: (_) => notification.onDelete?.call(),
          background: Container(
            alignment: Alignment.centerRight,
            padding: const EdgeInsets.symmetric(horizontal: AppSpacing.lg),
            decoration: BoxDecoration(
              color: AppColors.error,
              borderRadius: BorderRadius.circular(12),
            ),
            child: const Icon(
              Icons.delete_outline_rounded,
              color: AppColors.onPrimary,
            ),
          ),
          child: card,
        );
      },
    );
  }

  Future<bool> _confirmDelete(BuildContext context) async {
    return await showDialog<bool>(
          context: context,
          builder: (dialogContext) => AlertDialog(
            title: const Text('Delete notification?'),
            content: const Text(
              'This notification will be permanently removed.',
            ),
            actions: [
              TextButton(
                onPressed: () => Navigator.pop(dialogContext, false),
                child: const Text('Cancel'),
              ),
              FilledButton(
                onPressed: () => Navigator.pop(dialogContext, true),
                style: FilledButton.styleFrom(backgroundColor: AppColors.error),
                child: const Text('Delete'),
              ),
            ],
          ),
        ) ??
        false;
  }
}
