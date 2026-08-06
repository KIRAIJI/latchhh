import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

import 'package:latch/latch_ui/core/components/navigation/latch_navigation_bar.dart';
import 'package:latch/latch_ui/core/map/latch_google_map.dart';
import 'package:latch/latch_ui/core/validation/input_validation.dart';
import 'package:latch/latch_ui/data/api/latch_api.dart';
import 'package:latch/latch_ui/features/items/presentation/items_screen.dart';

void main() {
  group('Google Maps viewport', () {
    test('builds bounds around every tracker position', () {
      final bounds = LatchGoogleMap.boundsFor(const [
        LatLng(14.60, 120.98),
        LatLng(14.55, 121.05),
        LatLng(14.63, 121.01),
      ]);

      expect(bounds, isNotNull);
      expect(bounds!.southwest, const LatLng(14.55, 120.98));
      expect(bounds.northeast, const LatLng(14.63, 121.05));
    });

    test('does not build bounds when fewer than two positions exist', () {
      expect(LatchGoogleMap.boundsFor(const []), isNull);
      expect(LatchGoogleMap.boundsFor(const [LatLng(14.60, 120.98)]), isNull);
    });
  });

  group('input validation', () {
    test('rejects weak and mismatched passwords with concise messages', () {
      expect(InputValidation.newPassword('12345678'), isNotNull);
      expect(InputValidation.newPassword('Strong!123'), isNull);
      expect(
        InputValidation.passwordConfirmation('Strong!123', 'Strong!124'),
        'Passwords do not match.',
      );
    });

    test('validates item identifiers and names', () {
      expect(InputValidation.deviceUid('LATCH-AB12-CD34'), isNull);
      expect(InputValidation.deviceUid('AB12-CD34'), isNotNull);
      expect(InputValidation.itemName('   '), isNotNull);
    });
  });

  test('resolves local profile photos against the XAMPP public path', () {
    final api = LatchApi(
      baseUrl: 'http://192.168.100.22/latch/backend-app/public/api/v1',
    );

    expect(
      api.resolvePublicUrl(
        'http://localhost:8000/storage/profile-photos/avatar.png',
      ),
      'http://192.168.100.22/latch/backend-app/public/storage/'
      'profile-photos/avatar.png',
    );
    expect(
      api.resolvePublicUrl(
        'http://10.0.2.2/latch/backend-app/public/storage/'
        'profile-photos/avatar.png',
      ),
      'http://192.168.100.22/latch/backend-app/public/storage/'
      'profile-photos/avatar.png',
    );

    api.close();
  });

  testWidgets('bottom navigation labels fit a narrow screen', (tester) async {
    await tester.binding.setSurfaceSize(const Size(320, 640));
    addTearDown(() => tester.binding.setSurfaceSize(null));

    await tester.pumpWidget(
      MaterialApp(
        builder: (context, child) => MediaQuery(
          data: MediaQuery.of(
            context,
          ).copyWith(textScaler: const TextScaler.linear(2)),
          child: child!,
        ),
        home: Scaffold(
          bottomNavigationBar: LatchNavigationBar(
            selectedIndex: 0,
            notificationsUnreadCount: 2,
            onDestinationSelected: (_) {},
          ),
        ),
      ),
    );
    await tester.pump();

    expect(find.text('Notifications'), findsOneWidget);
    expect(tester.takeException(), isNull);
  });

  testWidgets('map refresh control reloads item locations', (tester) async {
    var refreshCount = 0;

    await tester.pumpWidget(
      MaterialApp(
        home: ItemsScreen(
          onAddItemPressed: () {},
          onRefreshPressed: () => refreshCount++,
          mapContent: const ColoredBox(color: Colors.white),
        ),
      ),
    );

    final refreshButton = find.byTooltip('Refresh map locations');
    expect(refreshButton, findsOneWidget);
    await tester.tap(refreshButton);
    await tester.pump();

    expect(refreshCount, 1);
    expect(tester.takeException(), isNull);
  });
}
