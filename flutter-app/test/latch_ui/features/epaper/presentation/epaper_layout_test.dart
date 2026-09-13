import 'dart:typed_data';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:image/image.dart' as img;
import 'package:latch/latch_ui/features/epaper/presentation/epaper_image_editor_screen.dart';
import 'package:latch/latch_ui/features/epaper/presentation/epaper_layout.dart';
import 'package:latch/latch_ui/features/epaper/presentation/epaper_tab_screen.dart';
import 'package:latch/main.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();
  for (final template in EpaperTemplate.values) {
    test('${template.title} keeps every supplied field as editable text', () {
      final values = List.generate(template.fields.length, (i) => 'Value $i');
      final layout = template.create(values);
      expect(layout.layers.length, values.length + 1);
      for (var i = 0; i < values.length; i++) {
        expect(layout.layers[i + 1].text, contains(values[i]));
        expect(layout.layers[i + 1].bounds.bottom, lessThanOrEqualTo(416));
      }
      final copy = layout.copy();
      copy.layers.last.text = 'Edited';
      expect(layout.layers.last.text, isNot('Edited'));
    });
  }
  test('optional empty fields do not appear on the canvas', () {
    final layout = EpaperTemplate.contact.create([
      'Ana',
      '09123456789',
      '',
      '',
    ]);
    expect(layout.layers.length, 3);
  });
  testWidgets('blank canvas adds, edits, moves, resizes and deletes text', (
    tester,
  ) async {
    await tester.pumpWidget(const MaterialApp(home: EpaperImageEditorScreen()));
    await tester.pumpAndSettle();
    await tester.tap(find.text('Add text'));
    await tester.pumpAndSettle();
    await tester.enterText(find.byType(TextField), 'Hello');
    await tester.tap(find.text('Save'));
    await tester.pumpAndSettle();
    expect(find.byTooltip('Edit text'), findsOneWidget);
    final canvas = find.byKey(const ValueKey('epaper-canvas'));
    final origin = tester.getTopLeft(canvas);
    final scale = tester.getSize(canvas).width / 240;
    await tester.dragFrom(
      origin + const Offset(30, 25) * scale,
      const Offset(30, 40),
    );
    await tester.pumpAndSettle();
    await tester.tap(find.byTooltip('Edit text'));
    await tester.pumpAndSettle();
    expect(find.text('Hello'), findsOneWidget);
    await tester.enterText(find.byType(TextField), 'Updated');
    await tester.tap(find.text('Save'));
    await tester.pumpAndSettle();
    await tester.drag(
      find.byKey(const ValueKey('epaper-layer-size')),
      const Offset(30, 0),
    );
    await tester.pumpAndSettle();
    await tester.tap(find.byTooltip('Delete selected'));
    await tester.pumpAndSettle();
    expect(find.byTooltip('Delete selected'), findsNothing);
    expect(tester.takeException(), isNull);
  });
  testWidgets(
    'image proportions survive resize and exported raster has white margins',
    (tester) async {
      final bytes = Uint8List.fromList(
        img.encodePng(
          img.Image(width: 80, height: 40)..clear(img.ColorRgb8(0, 0, 0)),
        ),
      );
      EpaperLayoutResult? result;
      await tester.pumpWidget(
        MaterialApp(
          home: Builder(
            builder: (context) => Scaffold(
              body: TextButton(
                onPressed: () async {
                  result = await EpaperImageEditorScreen.open(
                    context,
                    imageBytes: bytes,
                  );
                },
                child: const Text('Open'),
              ),
            ),
          ),
        ),
      );
      await tester.tap(find.text('Open'));
      await tester.pump();
      await tester.runAsync(() async {
        await Future<void>.delayed(const Duration(milliseconds: 100));
      });
      await tester.pumpAndSettle();
      await tester.drag(
        find.byKey(const ValueKey('epaper-layer-size')),
        const Offset(-30, 0),
      );
      await tester.pumpAndSettle();
      await tester.tap(find.text('Preview'));
      await tester.runAsync(() async {
        await Future<void>.delayed(const Duration(milliseconds: 100));
      });
      await tester.pumpAndSettle();
      expect(result, isNotNull);
      final layer = result!.layout.layers.single;
      expect(layer.bounds.width / layer.bounds.height, 2);
      final raster = img.decodePng(result!.pngBytes)!;
      expect((raster.width, raster.height), (240, 416));
      expect(raster.getPixel(0, 0).r, 255);
      expect(raster.getPixel(120, 208).r, 0);
    },
  );
  testWidgets(
    'template form opens an editable layout and exposes blank canvas',
    (tester) async {
      final bridge = UploaderBridge();
      addTearDown(bridge.dispose);
      await tester.pumpWidget(
        MaterialApp(home: EpaperTabScreen(bridge: bridge)),
      );
      await tester.tap(find.text('Templates'));
      await tester.pumpAndSettle();
      for (final template in EpaperTemplate.values) {
        expect(find.text(template.title), findsOneWidget);
      }
      await tester.tap(find.text('Contact Information'));
      await tester.pumpAndSettle();
      await tester.enterText(find.byType(TextFormField).at(0), 'Ana');
      await tester.enterText(find.byType(TextFormField).at(1), '09123456789');
      await tester.tap(find.text('Use template'));
      await tester.pumpAndSettle();
      expect(find.byKey(const ValueKey('epaper-canvas')), findsOneWidget);
      await tester.tap(find.byType(CloseButton));
      await tester.pumpAndSettle();
      await tester.tap(find.text('Blank canvas'));
      await tester.pumpAndSettle();
      expect(find.text('Add text'), findsOneWidget);
      expect(tester.takeException(), isNull);
    },
  );
}
