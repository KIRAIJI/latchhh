import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:latch/latch_ui/features/notifications/presentation/notification_details_screen.dart';

void main() {
  testWidgets('notification details returns to the notification feed', (
    tester,
  ) async {
    var deleted = false;

    await tester.pumpWidget(
      MaterialApp(
        home: Builder(
          builder: (context) => Scaffold(
            body: Column(
              children: [
                const Text('Notifications feed'),
                FilledButton(
                  onPressed: () => Navigator.push<void>(
                    context,
                    MaterialPageRoute<void>(
                      builder: (_) => NotificationDetailsScreen(
                        typeLabel: 'Device Status',
                        typeIcon: Icons.router_outlined,
                        typeIconColor: Colors.orange,
                        title: 'Tracker offline',
                        message: 'The tracker stopped communicating.',
                        time: 'Just now',
                        relatedItemName: 'Backpack',
                        onDeletePressed: () async {
                          deleted = true;
                          return true;
                        },
                      ),
                    ),
                  ),
                  child: const Text('Open notification'),
                ),
              ],
            ),
          ),
        ),
      ),
    );

    await tester.tap(find.text('Open notification'));
    await tester.pumpAndSettle();
    expect(find.text('Tracker offline'), findsOneWidget);

    await tester.pageBack();
    await tester.pumpAndSettle();
    expect(find.text('Notifications feed'), findsOneWidget);

    await tester.tap(find.text('Open notification'));
    await tester.pumpAndSettle();
    await tester.tap(find.text('Delete Notification'));
    await tester.pumpAndSettle();
    await tester.tap(find.widgetWithText(FilledButton, 'Delete'));
    await tester.pumpAndSettle();

    expect(deleted, isTrue);
    expect(find.text('Notifications feed'), findsOneWidget);
  });
}
