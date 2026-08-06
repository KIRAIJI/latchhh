import 'package:flutter/material.dart';

import '../../../../core/components/cards/latch_card.dart';
import '../../../../core/theme/app_colors.dart';
import '../../../../core/theme/app_spacing.dart';

class NotificationCardData {
  const NotificationCardData({
    required this.notificationId,
    required this.typeLabel,
    required this.typeIcon,
    required this.typeIconColor,
    required this.title,
    required this.message,
    required this.time,
    this.relatedItemName,
    this.isUnread = false,
    this.onMarkAsRead,
    this.onTap,
    this.onDelete,
  });

  final int notificationId;
  final String typeLabel;
  final IconData typeIcon;
  final Color typeIconColor;
  final String title;
  final String message;
  final String time;
  final String? relatedItemName;
  final bool isUnread;
  final VoidCallback? onMarkAsRead;
  final VoidCallback? onTap;
  final Future<void> Function()? onDelete;
}

class NotificationCard extends StatelessWidget {
  const NotificationCard({
    super.key,
    required this.typeLabel,
    required this.typeIcon,
    required this.typeIconColor,
    required this.title,
    required this.message,
    required this.time,
    this.relatedItemName,
    this.isUnread = false,
    this.onMarkAsRead,
    this.onTap,
  });

  factory NotificationCard.fromData(NotificationCardData data) {
    return NotificationCard(
      typeLabel: data.typeLabel,
      typeIcon: data.typeIcon,
      typeIconColor: data.typeIconColor,
      title: data.title,
      message: data.message,
      time: data.time,
      relatedItemName: data.relatedItemName,
      isUnread: data.isUnread,
      onMarkAsRead: data.onMarkAsRead,
      onTap: data.onTap,
    );
  }

  final String typeLabel;
  final IconData typeIcon;
  final Color typeIconColor;
  final String title;
  final String message;
  final String time;
  final String? relatedItemName;
  final bool isUnread;
  final VoidCallback? onMarkAsRead;
  final VoidCallback? onTap;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;

    return LatchCard(
      onTap: onTap,
      selected: isUnread,
      padding: const EdgeInsets.all(AppSpacing.sm + AppSpacing.xs),
      child: Semantics(
        label: isUnread ? 'Unread notification: $title' : title,
        button: onTap != null,
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Container(
              width: 36,
              height: 36,
              decoration: BoxDecoration(
                color: typeIconColor.withValues(alpha: 0.12),
                borderRadius: BorderRadius.circular(8),
              ),
              child: Icon(typeIcon, size: 18, color: typeIconColor),
            ),
            const SizedBox(width: AppSpacing.sm),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    children: [
                      Expanded(
                        child: Text(
                          title,
                          style: textTheme.titleSmall?.copyWith(
                            fontWeight: isUnread
                                ? FontWeight.w600
                                : FontWeight.w500,
                          ),
                          maxLines: 2,
                          overflow: TextOverflow.ellipsis,
                        ),
                      ),
                      if (isUnread) ...[
                        const SizedBox(width: AppSpacing.xs),
                        Semantics(
                          label: 'Unread',
                          child: Container(
                            width: 8,
                            height: 8,
                            decoration: const BoxDecoration(
                              color: AppColors.trackerAccent,
                              shape: BoxShape.circle,
                            ),
                          ),
                        ),
                      ],
                    ],
                  ),
                  const SizedBox(height: AppSpacing.xs),
                  Text(
                    typeLabel,
                    style: textTheme.labelSmall?.copyWith(
                      color: AppColors.textSecondary,
                    ),
                  ),
                  const SizedBox(height: AppSpacing.xs),
                  Text(
                    message,
                    style: textTheme.bodySmall,
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                  ),
                  if (relatedItemName != null) ...[
                    const SizedBox(height: AppSpacing.xs),
                    Text(
                      relatedItemName!,
                      style: textTheme.labelSmall?.copyWith(
                        color: AppColors.trackerAccent,
                      ),
                      maxLines: 1,
                      overflow: TextOverflow.ellipsis,
                    ),
                  ],
                  const SizedBox(height: AppSpacing.xs),
                  LayoutBuilder(
                    builder: (context, constraints) {
                      final stackActions = constraints.maxWidth < 220;

                      final timeText = Text(
                        time,
                        style: textTheme.labelSmall?.copyWith(
                          color: AppColors.textMuted,
                        ),
                      );

                      final markAsReadButton = isUnread && onMarkAsRead != null
                          ? TextButton(
                              onPressed: onMarkAsRead,
                              child: const Text('Mark as Read'),
                            )
                          : null;

                      if (stackActions && markAsReadButton != null) {
                        return Column(
                          crossAxisAlignment: CrossAxisAlignment.stretch,
                          children: [
                            timeText,
                            Align(
                              alignment: Alignment.centerLeft,
                              child: markAsReadButton,
                            ),
                          ],
                        );
                      }

                      return Row(
                        children: [
                          Expanded(child: timeText),
                          ?markAsReadButton,
                        ],
                      );
                    },
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
