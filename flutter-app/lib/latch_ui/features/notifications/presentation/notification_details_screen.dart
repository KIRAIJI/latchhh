import 'package:flutter/material.dart';

import '../../../core/theme/app_colors.dart';
import '../../../core/theme/app_radius.dart';
import '../../../core/theme/app_spacing.dart';

class NotificationDetailsScreen extends StatefulWidget {
  const NotificationDetailsScreen({
    super.key,
    required this.typeLabel,
    required this.typeIcon,
    required this.typeIconColor,
    required this.title,
    required this.message,
    required this.time,
    this.relatedItemName,
    this.onViewItemPressed,
    this.onDeletePressed,
  });

  final String typeLabel;
  final IconData typeIcon;
  final Color typeIconColor;
  final String title;
  final String message;
  final String time;
  final String? relatedItemName;
  final VoidCallback? onViewItemPressed;
  final Future<bool> Function()? onDeletePressed;

  @override
  State<NotificationDetailsScreen> createState() =>
      _NotificationDetailsScreenState();
}

class _NotificationDetailsScreenState extends State<NotificationDetailsScreen> {
  bool _isDeleting = false;

  Future<void> _delete() async {
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (dialogContext) => AlertDialog(
        title: const Text('Delete notification?'),
        content: const Text('This notification will be permanently removed.'),
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
    );
    if (confirmed != true || !mounted) return;

    setState(() => _isDeleting = true);
    final deleted = await widget.onDeletePressed?.call() ?? false;
    if (!mounted) return;
    if (deleted) {
      Navigator.pop(context);
    } else {
      setState(() => _isDeleting = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;

    return Scaffold(
      backgroundColor: AppColors.background,
      appBar: AppBar(
        title: const Text('Notification'),
        actions: [
          if (widget.onDeletePressed != null)
            IconButton(
              onPressed: _isDeleting ? null : _delete,
              tooltip: 'Delete notification',
              icon: const Icon(Icons.delete_outline_rounded),
            ),
        ],
      ),
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(AppSpacing.lg),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              DecoratedBox(
                decoration: BoxDecoration(
                  color: AppColors.surface,
                  borderRadius: BorderRadius.circular(AppRadius.large),
                  border: Border.all(color: AppColors.border),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(AppSpacing.lg),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Row(
                        children: [
                          Container(
                            width: 44,
                            height: 44,
                            decoration: BoxDecoration(
                              color: widget.typeIconColor.withValues(
                                alpha: 0.12,
                              ),
                              borderRadius: BorderRadius.circular(
                                AppRadius.medium,
                              ),
                            ),
                            child: Icon(
                              widget.typeIcon,
                              color: widget.typeIconColor,
                            ),
                          ),
                          const SizedBox(width: AppSpacing.md),
                          Expanded(
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  widget.typeLabel,
                                  style: textTheme.labelMedium?.copyWith(
                                    color: AppColors.textSecondary,
                                  ),
                                ),
                                const SizedBox(height: AppSpacing.xs),
                                Text(
                                  widget.time,
                                  style: textTheme.labelSmall?.copyWith(
                                    color: AppColors.textMuted,
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ],
                      ),
                      const SizedBox(height: AppSpacing.lg),
                      Text(widget.title, style: textTheme.headlineSmall),
                      const SizedBox(height: AppSpacing.md),
                      Text(widget.message, style: textTheme.bodyLarge),
                      if (widget.relatedItemName != null) ...[
                        const SizedBox(height: AppSpacing.lg),
                        Text(
                          'Related item',
                          style: textTheme.labelSmall?.copyWith(
                            color: AppColors.textSecondary,
                          ),
                        ),
                        const SizedBox(height: AppSpacing.xs),
                        Text(
                          widget.relatedItemName!,
                          style: textTheme.titleMedium?.copyWith(
                            color: AppColors.trackerAccent,
                          ),
                        ),
                      ],
                    ],
                  ),
                ),
              ),
              if (widget.onViewItemPressed != null) ...[
                const SizedBox(height: AppSpacing.lg),
                FilledButton.icon(
                  onPressed: _isDeleting ? null : widget.onViewItemPressed,
                  icon: const Icon(Icons.open_in_new_rounded),
                  label: const Text('View Item Details'),
                ),
              ],
              const SizedBox(height: AppSpacing.md),
              if (_isDeleting)
                const Center(child: CircularProgressIndicator())
              else if (widget.onDeletePressed != null)
                OutlinedButton.icon(
                  onPressed: _delete,
                  icon: const Icon(Icons.delete_outline_rounded),
                  label: const Text('Delete Notification'),
                  style: OutlinedButton.styleFrom(
                    foregroundColor: AppColors.error,
                    side: const BorderSide(color: AppColors.error),
                  ),
                ),
            ],
          ),
        ),
      ),
    );
  }
}
