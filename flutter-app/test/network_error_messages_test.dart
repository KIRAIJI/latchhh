import 'package:flutter_test/flutter_test.dart';
import 'package:http/http.dart' as http;
import 'package:http/testing.dart';
import 'package:latch/latch_ui/data/api/latch_api.dart';

void main() {
  test('connection failures explain how the user can recover', () async {
    final api = LatchApi(
      baseUrl: 'https://example.test/api/v1',
      client: MockClient((_) => throw http.ClientException('offline')),
    );

    await expectLater(
      api.login(email: 'user@example.com', password: 'Password!123'),
      throwsA(
        isA<LatchApiException>()
            .having((error) => error.code, 'code', 'NETWORK_UNAVAILABLE')
            .having(
              (error) => error.message,
              'message',
              contains('Check Wi-Fi or mobile data'),
            ),
      ),
    );
  });

  test('non-JSON service failures use a friendly temporary message', () async {
    final api = LatchApi(
      baseUrl: 'https://example.test/api/v1',
      client: MockClient((_) async => http.Response('<html>down</html>', 503)),
    );

    await expectLater(
      api.login(email: 'user@example.com', password: 'Password!123'),
      throwsA(
        isA<LatchApiException>()
            .having((error) => error.code, 'code', 'SERVICE_UNAVAILABLE')
            .having(
              (error) => error.message,
              'message',
              'LATCH is temporarily unavailable. Please try again in a few moments.',
            ),
      ),
    );
  });
}
