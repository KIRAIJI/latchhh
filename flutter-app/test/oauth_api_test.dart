import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:http/testing.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:latch/latch_ui/data/api/latch_api.dart';

void main() {
  test(
    'Google OAuth sends the Firebase token and parses auth capabilities',
    () async {
      final client = MockClient((request) async {
        expect(request.method, 'POST');
        expect(request.url.path, '/api/v1/auth/oauth/google');
        expect(jsonDecode(request.body), {
          'id_token': 'firebase-id-token',
          'accepted_terms': true,
          'acknowledged_privacy': true,
        });

        return http.Response(
          jsonEncode({
            'data': {
              'user': {
                'id': 7,
                'name': 'Google User',
                'email': 'google@example.com',
                'email_verified': true,
                'has_password': false,
                'oauth_providers': ['google'],
                'notifications_enabled': true,
                'notification_preferences': {
                  'geofence_events': true,
                  'battery_events': true,
                  'device_status_events': true,
                },
              },
              'token': 'sanctum-token',
            },
          }),
          200,
          headers: {'content-type': 'application/json'},
        );
      });
      final api = LatchApi(
        client: client,
        baseUrl: 'https://example.test/api/v1',
      );

      final result = await api.loginWithGoogle('firebase-id-token');

      expect(result.token, 'sanctum-token');
      expect(result.user.emailVerified, isTrue);
      expect(result.user.hasPassword, isFalse);
      expect(result.user.oauthProviders, ['google']);
      api.close();
    },
  );

  test(
    'OAuth-only account deletion sends recent reauthentication token',
    () async {
      final client = MockClient((request) async {
        expect(request.method, 'DELETE');
        expect(request.url.path, '/api/v1/account');
        expect(request.headers['authorization'], 'Bearer sanctum-token');
        expect(jsonDecode(request.body), {
          'oauth_id_token': 'recent-firebase-id-token',
        });
        return http.Response('', 204);
      });
      final api = LatchApi(
        client: client,
        baseUrl: 'https://example.test/api/v1',
      )..token = 'sanctum-token';

      await api.deleteAccount(oauthIdToken: 'recent-firebase-id-token');

      api.close();
    },
  );

  test('passwordless account requests an authenticated setup link', () async {
    final client = MockClient((request) async {
      expect(request.method, 'POST');
      expect(request.url.path, '/api/v1/auth/password/setup-link');
      expect(request.headers['authorization'], 'Bearer sanctum-token');

      return http.Response(
        jsonEncode({
          'success': true,
          'data': null,
          'message': 'A password setup link has been sent.',
        }),
        202,
        headers: {'content-type': 'application/json'},
      );
    });
    final api = LatchApi(client: client, baseUrl: 'https://example.test/api/v1')
      ..token = 'sanctum-token';

    await api.requestPasswordSetup();

    api.close();
  });
}
