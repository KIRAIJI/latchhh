import 'dart:convert';

import 'package:flutter_test/flutter_test.dart';
import 'package:http/http.dart' as http;
import 'package:http/testing.dart';
import 'package:latch/latch_ui/application/latch_controller.dart';
import 'package:latch/latch_ui/data/api/latch_api.dart';
import 'package:latch/latch_ui/data/models/latch_models.dart';

void main() {
  test('refreshItem immediately requests tracker synchronization', () async {
    late http.Request capturedRequest;
    final api =
        LatchApi(
            baseUrl: 'https://example.test/api/v1',
            client: MockClient((request) async {
              capturedRequest = request;
              return http.Response(
                jsonEncode({
                  'data': {
                    'id': 42,
                    'device_uid': 'LATCH-TEST-0001',
                    'item_name': 'Backpack',
                    'location': {
                      'latitude': 15.178149,
                      'longitude': 120.650553,
                      'recorded_at': '2026-07-30T01:00:00.000000Z',
                      'type': 'current',
                    },
                    'status': {
                      'connection': 'online',
                      'battery_status': 'unknown',
                      'gnss_status': 'fixed',
                      'gsm_signal_level': 'unknown',
                    },
                  },
                }),
                200,
                headers: {'content-type': 'application/json'},
              );
            }),
          )
          ..token = 'test-token';

    final item = await api.refreshItem(42);

    expect(capturedRequest.method, 'POST');
    expect(capturedRequest.url.path, '/api/v1/items/42/refresh');
    expect(capturedRequest.headers['authorization'], 'Bearer test-token');
    expect(item.location.latitude, 15.178149);
    expect(item.location.longitude, 120.650553);
  });

  test('manual controller refresh replaces cached marker data', () async {
    var requestCount = 0;
    final api =
        LatchApi(
            baseUrl: 'https://example.test/api/v1',
            client: MockClient((request) async {
              requestCount++;
              return http.Response(
                jsonEncode({
                  'data': _itemJson(
                    latitude: 15.178149,
                    longitude: 120.650553,
                    recordedAt: '2026-07-30T01:01:00.000000Z',
                  ),
                }),
                200,
                headers: {'content-type': 'application/json'},
              );
            }),
          )
          ..token = 'test-token';
    final controller = LatchController(api: api)
      ..items = [
        LatchItem.fromJson(
          _itemJson(
            latitude: 15.145126,
            longitude: 120.594639,
            recordedAt: '2026-07-30T01:00:00.000000Z',
          ),
        ),
      ];

    await controller.refreshItemsFromTracker();

    expect(requestCount, 1);
    expect(controller.itemsError, isNull);
    expect(controller.items.single.location.latitude, 15.178149);
    expect(controller.items.single.location.longitude, 120.650553);
  });
}

Map<String, dynamic> _itemJson({
  required double latitude,
  required double longitude,
  required String recordedAt,
}) {
  return {
    'id': 42,
    'device_uid': 'LATCH-TEST-0001',
    'item_name': 'Backpack',
    'location': {
      'latitude': latitude,
      'longitude': longitude,
      'recorded_at': recordedAt,
      'type': 'current',
    },
    'status': {
      'connection': 'online',
      'battery_status': 'unknown',
      'gnss_status': 'fixed',
      'gsm_signal_level': 'unknown',
    },
  };
}
