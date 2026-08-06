import 'package:flutter_test/flutter_test.dart';

import 'package:latch/main.dart';

void main() {
  testWidgets('Uploader screen renders', (WidgetTester tester) async {
    await tester.pumpWidget(const EPaperNfcApp());

    expect(find.text('EPaper NFC Uploader'), findsOneWidget);
    expect(find.text('Upload Image'), findsOneWidget);
    expect(find.text('Write NFC'), findsOneWidget);
  });
}
