import 'package:flutter/material.dart';

import '../../theme/app_icons.dart';

class LatchNavigationBar extends StatelessWidget {
  const LatchNavigationBar({
    super.key,
    required this.selectedIndex,
    required this.onDestinationSelected,
    this.notificationsUnreadCount,
  });

  final int selectedIndex;
  final ValueChanged<int> onDestinationSelected;
  final int? notificationsUnreadCount;

  bool get _showNotificationsBadge {
    final count = notificationsUnreadCount;
    return count != null && count > 0;
  }

  Widget _notificationsIcon(IconData icon) {
    if (!_showNotificationsBadge) {
      return Icon(icon);
    }

    final label = notificationsUnreadCount! > 99
        ? '99+'
        : '$notificationsUnreadCount';

    return Badge(
      label: Text(label),
      child: Semantics(label: '$label unread notifications', child: Icon(icon)),
    );
  }

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
      builder: (context, constraints) {
        final fontSize = constraints.maxWidth < 350
            ? 9.0
            : constraints.maxWidth < 400
            ? 10.0
            : 12.0;
        final navigation = NavigationBarTheme(
          data: NavigationBarTheme.of(context).copyWith(
            labelTextStyle: WidgetStateProperty.resolveWith((states) {
              return TextStyle(
                fontSize: fontSize,
                height: 1.1,
                fontWeight: states.contains(WidgetState.selected)
                    ? FontWeight.w600
                    : FontWeight.w500,
              );
            }),
          ),
          child: NavigationBar(
            selectedIndex: selectedIndex,
            labelBehavior: NavigationDestinationLabelBehavior.alwaysShow,
            onDestinationSelected: onDestinationSelected,
            destinations: [
              const NavigationDestination(
                icon: Icon(AppIcons.items),
                selectedIcon: Icon(AppIcons.itemsSelected),
                label: 'Items',
              ),
              const NavigationDestination(
                icon: Icon(AppIcons.ePaper),
                selectedIcon: Icon(AppIcons.ePaperSelected),
                label: 'E-Paper',
              ),
              NavigationDestination(
                icon: _notificationsIcon(AppIcons.notifications),
                selectedIcon: _notificationsIcon(
                  AppIcons.notificationsSelected,
                ),
                label: 'Notifications',
              ),
              const NavigationDestination(
                icon: Icon(AppIcons.profile),
                selectedIcon: Icon(AppIcons.profileSelected),
                label: 'Profile',
              ),
            ],
          ),
        );

        return MediaQuery.withClampedTextScaling(
          minScaleFactor: 0.85,
          maxScaleFactor: 1.15,
          child: navigation,
        );
      },
    );
  }
}
