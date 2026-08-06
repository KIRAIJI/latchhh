import 'dart:convert';

import 'package:flutter_test/flutter_test.dart';
import 'package:http/http.dart' as http;
import 'package:http/testing.dart';
import 'package:latch/latch_ui/data/api/latch_api.dart';

void main() {
  test('an authenticated 401 expires the local API session once', () async {
    var expiryCount = 0;
    final api =
        LatchApi(
            baseUrl: 'https://example.test/api/v1',
            client: MockClient(
              (_) async => http.Response(
                jsonEncode({
                  'message': 'Unauthenticated.',
                  'code': 'UNAUTHENTICATED',
                }),
                401,
                headers: {'content-type': 'application/json'},
              ),
            ),
          )
          ..token = 'expired-token'
          ..onUnauthorized = () => expiryCount++;

    await expectLater(
      api.items(),
      throwsA(
        isA<LatchApiException>().having(
          (error) => error.statusCode,
          'statusCode',
          401,
        ),
      ),
    );
    await Future<void>.delayed(Duration.zero);

    expect(expiryCount, 1);
    expect(api.hasToken, isFalse);

    await expectLater(api.notifications(), throwsA(isA<LatchApiException>()));
    expect(expiryCount, 1);
  });
}
