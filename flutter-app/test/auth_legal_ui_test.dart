import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:latch/latch_ui/application/latch_controller.dart';
import 'package:latch/latch_ui/data/api/latch_api.dart';
import 'package:latch/latch_ui/features/auth/login/presentation/login_screen.dart';
import 'package:latch/latch_ui/features/auth/presentation/reset_password_screen.dart';
import 'package:latch/latch_ui/features/legal/presentation/legal_document_screen.dart';

void main() {
  testWidgets('login legal links stay compact', (tester) async {
    await tester.binding.setSurfaceSize(const Size(1080, 2400));
    addTearDown(() => tester.binding.setSurfaceSize(null));

    await tester.pumpWidget(
      MaterialApp(
        home: LoginScreen(
          onLoginPressed: (_, _) {},
          onGooglePressed: () {},
          onRegisterPressed: () {},
          onForgotPasswordPressed: () {},
          onTermsPressed: () {},
          onPrivacyPressed: () {},
          showGoogleSignIn: true,
        ),
      ),
    );

    expect(tester.getSize(find.text('Terms')).height, lessThanOrEqualTo(24));
    expect(
      find.ancestor(of: find.text('Terms'), matching: find.byType(TextButton)),
      findsNothing,
    );
    expect(
      tester.getSize(find.text('Privacy Policy')).height,
      lessThanOrEqualTo(24),
    );
    expect(
      find.ancestor(
        of: find.text('Privacy Policy'),
        matching: find.byType(TextButton),
      ),
      findsNothing,
    );
  });

  testWidgets('legal document stays inside the app', (tester) async {
    await tester.pumpWidget(
      const MaterialApp(
        home: LegalDocumentScreen(type: LegalDocumentType.terms),
      ),
    );

    expect(find.byIcon(Icons.open_in_browser_rounded), findsNothing);
    expect(find.text('Terms of Service'), findsOneWidget);
  });

  testWidgets('password setup link uses setup wording', (tester) async {
    final controller = LatchController(
      api: LatchApi(baseUrl: 'https://example.test/api/v1'),
    );
    addTearDown(controller.dispose);

    await tester.pumpWidget(
      MaterialApp(
        home: ResetPasswordScreen(
          controller: controller,
          email: 'google@example.com',
          token: 'setup-token',
          isPasswordSetup: true,
        ),
      ),
    );

    expect(find.text('Set Password'), findsNWidgets(2));
    expect(find.text('Password'), findsOneWidget);
    expect(find.text('Confirm Password'), findsOneWidget);
    expect(find.text('Reset Password'), findsNothing);
  });
}
