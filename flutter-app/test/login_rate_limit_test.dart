import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:http/http.dart' as http;
import 'package:http/testing.dart';
import 'package:latch/latch_ui/application/latch_controller.dart';
import 'package:latch/latch_ui/core/services/google_oauth_service.dart';
import 'package:latch/latch_ui/data/api/latch_api.dart';
import 'package:latch/latch_ui/features/auth/presentation/auth_ui_flow.dart';

void main() {
  test('login rate-limit response exposes retry duration', () async {
    final client = MockClient(
      (_) async => http.Response(
        jsonEncode({
          'success': false,
          'code': 'RATE_LIMITED',
          'message': 'Too many sign-in attempts. Try again shortly.',
          'retry_after_seconds': 42,
        }),
        429,
        headers: {'retry-after': '42'},
      ),
    );
    final api = LatchApi(
      client: client,
      baseUrl: 'https://example.test/api/v1',
    );

    await expectLater(
      api.login(email: 'user@example.com', password: 'WrongPassword!123'),
      throwsA(
        isA<LatchApiException>()
            .having((error) => error.statusCode, 'statusCode', 429)
            .having((error) => error.code, 'code', 'RATE_LIMITED')
            .having(
              (error) => error.retryAfterSeconds,
              'retryAfterSeconds',
              42,
            ),
      ),
    );

    api.close();
  });

  testWidgets('login button displays the retry countdown and is disabled', (
    tester,
  ) async {
    await tester.binding.setSurfaceSize(const Size(1080, 2400));
    addTearDown(() => tester.binding.setSurfaceSize(null));
    final api = LatchApi(
      client: MockClient(
        (_) async => http.Response(
          jsonEncode({
            'success': false,
            'code': 'RATE_LIMITED',
            'message': 'Too many sign-in attempts. Try again shortly.',
            'retry_after_seconds': 2,
          }),
          429,
          headers: {'retry-after': '2'},
        ),
      ),
      baseUrl: 'https://example.test/api/v1',
    );
    final controller = LatchController(
      api: api,
      googleOAuth: const _UnavailableGoogleOAuthService(),
    );
    addTearDown(controller.dispose);

    await tester.pumpWidget(
      MaterialApp(
        home: AuthUiFlow(controller: controller, onEnterMainApp: () {}),
      ),
    );

    await tester.enterText(find.byType(TextField).at(0), 'user@example.com');
    await tester.enterText(find.byType(TextField).at(1), 'WrongPassword!123');
    await tester.tap(find.text('Sign In'));
    await tester.pump();

    expect(find.text('Try again in 2s'), findsOneWidget);
    final disabledButton = tester.widget<FilledButton>(
      find.widgetWithText(FilledButton, 'Try again in 2s'),
    );
    expect(disabledButton.onPressed, isNull);

    await tester.pump(const Duration(seconds: 1));
    expect(find.text('Try again in 1s'), findsOneWidget);
    await tester.pump(const Duration(seconds: 1));
    expect(find.text('Sign In'), findsOneWidget);
  });
}

class _UnavailableGoogleOAuthService implements GoogleOAuthService {
  const _UnavailableGoogleOAuthService();

  @override
  bool get isAvailable => false;

  @override
  Future<String> reauthenticate() => throw UnimplementedError();

  @override
  Future<void> signOut() async {}

  @override
  Future<String> signIn() => throw UnimplementedError();
}
