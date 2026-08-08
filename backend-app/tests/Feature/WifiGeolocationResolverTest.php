<?php

use App\Data\TrackerPositionData;
use App\Services\WifiGeolocationResolver;
use Carbon\CarbonImmutable;
use Illuminate\Support\Facades\Cache;
use Illuminate\Support\Facades\Http;

beforeEach(function () {
    Cache::clear();
    config()->set([
        'latch.geolocation.enabled' => true,
        'latch.geolocation.api_key' => 'test-geolocation-key',
        'latch.geolocation.cache_seconds' => 300,
    ]);
});

it('resolves two wifi observations without exposing the key to the client model', function () {
    Http::fake([
        'https://www.googleapis.com/geolocation/v1/geolocate' => Http::response([
            'location' => ['lat' => 15.145, 'lng' => 120.588],
            'accuracy' => 24.5,
        ]),
    ]);

    $position = new TrackerPositionData(
        providerPositionId: 9,
        providerDeviceId: 42,
        latitude: null,
        longitude: null,
        recordedAt: CarbonImmutable::parse('2026-08-08T01:00:00Z'),
        gnssValid: false,
        batteryPercentage: 80,
        satellites: 0,
        hdop: null,
        gsmCsq: 20,
        wifiAccessPoints: [
            ['macAddress' => 'aa:bb:cc:dd:ee:01', 'signalStrength' => -45],
            ['macAddress' => 'aa:bb:cc:dd:ee:02', 'signalStrength' => -60],
        ],
    );

    $resolved = app(WifiGeolocationResolver::class)->resolve($position);

    expect($resolved->latitude)->toBe(15.145)
        ->and($resolved->longitude)->toBe(120.588)
        ->and($resolved->accuracyMeters)->toBe(24.5)
        ->and($resolved->approximate)->toBeTrue()
        ->and($resolved->gnssValid)->toBeTrue();

    Http::assertSent(fn ($request) => $request->hasHeader('X-Goog-Api-Key', 'test-geolocation-key')
        && $request['considerIp'] === false
        && count($request['wifiAccessPoints']) === 2);
});

it('does not call the provider with fewer than two access points', function () {
    Http::fake();

    $position = new TrackerPositionData(
        providerPositionId: 10,
        providerDeviceId: 42,
        latitude: null,
        longitude: null,
        recordedAt: CarbonImmutable::now(),
        gnssValid: false,
        batteryPercentage: null,
        satellites: null,
        hdop: null,
        gsmCsq: null,
        wifiAccessPoints: [
            ['macAddress' => 'aa:bb:cc:dd:ee:01', 'signalStrength' => -45],
        ],
    );

    expect(app(WifiGeolocationResolver::class)->resolve($position))->toBe($position);
    Http::assertNothingSent();
});
