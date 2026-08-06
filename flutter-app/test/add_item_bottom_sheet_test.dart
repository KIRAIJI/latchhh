import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:latch/latch_ui/features/items/presentation/widgets/add_item_bottom_sheet.dart';

void main() {
  testWidgets('shows a claim error inside the open add item form', (
    tester,
  ) async {
    const message = 'This device is already claimed.';

    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: AddItemBottomSheet(
            errorMessage: message,
            onAddItemPressed: (_, _) {},
            onCancelPressed: () {},
          ),
        ),
      ),
    );

    expect(find.byType(AddItemBottomSheet), findsOneWidget);
    expect(find.byKey(const Key('add-item-form-error')), findsOneWidget);
    expect(find.text(message), findsOneWidget);
    expect(find.byType(SnackBar), findsNothing);
  });
}
