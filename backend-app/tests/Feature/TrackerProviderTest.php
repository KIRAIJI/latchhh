<?php

use App\Exceptions\TrackerProviderException;
use App\Providers\Tracker\TraccarProvider;
use Carbon\CarbonImmutable;
use Illuminate\Support\Facades\Http;

beforeEach(function () {
    config()->set([
        'latch.traccar.base_url' => 'https://tracker.example/api',
        'latch.traccar.username' => 'backend-user',
        'latch.traccar.password' => 'backend-password',
        'latch.traccar.retry_times' => 0,
    ]);
});

it('maps official device and route fields without substituting fixTime', function () {
    Http::fake([
        'https://tracker.example/api/devices*' => Http::response([[
            'id' => 42,
            'uniqueId' => 'hardware-42',
            'status' => 'online',
            'lastUpdate' => '2026-07-26T07:59:50Z',
        ]]),
        'https://tracker.example/api/reports/route*' => Http::response([
            [
                'id' => 3,
                'deviceId' => 42,
                'latitude' => 15.3,
                'longitude' => 120.6,
                'fixTime' => '2026-07-26T07:59:00Z',
                'valid' => true,
                'accuracy' => 42.5,
                'network' => [
                    'wifiAccessPoints' => [
                        ['macAddress' => 'AA:BB:CC:DD:EE:01', 'signalStrength' => -45],
                        ['macAddress' => 'AA:BB:CC:DD:EE:02', 'signalStrength' => -60],
                        ['macAddress' => 'invalid', 'signalStrength' => -20],
                    ],
                ],
                'attributes' => [
                    'batteryLevel' => 18.0,
                    'sat' => 9.0,
                    'hdop' => 0.8,
                    'csq' => 31.0,
                    'powerState' => 'charging',
                    'firmware' => '1.1.0',
                    'resetReason' => 'power_on',
                    'approximate' => true,
                ],
            ],
            [
                'id' => 2,
                'deviceId' => 42,
                'latitude' => 15.2,
                'longitude' => 120.5,
                'fixTime' => '2026-07-26T07:58:00Z',
                'valid' => true,
                'attributes' => [
                    'batteryLevel' => 101,
                    'sat' => -1,
                    'hdop' => -0.1,
                    'csq' => 50,
                    'powerState' => 'plugged_in',
                    'firmware' => '<script>',
                    'resetReason' => 'anything',
                ],
            ],
            [
                'id' => 1,
                'deviceId' => 42,
                'latitude' => 15.1,
                'longitude' => 120.4,
                'deviceTime' => '2026-07-26T07:57:00Z',
                'serverTime' => '2026-07-26T07:57:01Z',
                'valid' => true,
            ],
        ]),
    ]);

    $provider = app(TraccarProvider::class);
    $device = $provider->getDeviceState(42);
    $positions = $provider->getPositions(
        42,
        CarbonImmutable::parse('2026-07-26T07:00:00Z'),
        CarbonImmutable::parse('2026-07-26T08:00:00Z'),
    );

    expect($device?->providerDeviceId)->toBe(42)
        ->and($device?->uniqueId)->toBe('hardware-42')
        ->and($positions->pluck('providerPositionId')->all())->toBe([2, 3])
        ->and($positions[0]->batteryPercentage)->toBeNull()
        ->and($positions[0]->satellites)->toBeNull()
        ->and($positions[0]->hdop)->toBeNull()
        ->and($positions[0]->gsmCsq)->toBeNull()
        ->and($positions[0]->powerState)->toBeNull()
        ->and($positions[0]->firmwareVersion)->toBeNull()
        ->and($positions[0]->resetReason)->toBeNull()
        ->and($positions[1]->batteryPercentage)->toBe(18)
        ->and($positions[1]->satellites)->toBe(9)
        ->and($positions[1]->hdop)->toBe(0.8)
        ->and($positions[1]->gsmCsq)->toBe(31)
        ->and($positions[1]->powerState)->toBe('charging')
        ->and($positions[1]->firmwareVersion)->toBe('1.1.0')
        ->and($positions[1]->resetReason)->toBe('power_on')
        ->and($positions[1]->approximate)->toBeTrue()
        ->and($positions[1]->accuracyMeters)->toBe(42.5)
        ->and($positions[1]->wifiAccessPoints)->toHaveCount(2)
        ->and($positions[1]->wifiAccessPoints[0]['macAddress'])->toBe('aa:bb:cc:dd:ee:01');

    Http::assertSent(fn ($request) => $request->hasHeader('Authorization')
        && ! str_contains($request->url(), 'backend-password'));
});

it('accepts the legacy firmware signal attribute as GSM CSQ', function () {
    Http::fake([
        'https://tracker.example/api/reports/route*' => Http::response([[
            'id' => 4,
            'deviceId' => 42,
            'latitude' => 15.3,
            'longitude' => 120.6,
            'fixTime' => '2026-07-26T08:00:00Z',
            'valid' => true,
            'attributes' => [
                'signal' => 17,
            ],
        ]]),
    ]);

    $positions = app(TraccarProvider::class)->getPositions(
        42,
        CarbonImmutable::parse('2026-07-26T07:00:00Z'),
        CarbonImmutable::parse('2026-07-26T09:00:00Z'),
    );

    expect($positions)->toHaveCount(1)
        ->and($positions[0]->gsmCsq)->toBe(17);
});

it('rejects ambiguous or mismatched provider device contracts', function () {
    Http::fake([
        'https://tracker.example/api/devices*' => Http::response([[
            'id' => 99,
            'uniqueId' => 'hardware-99',
            'status' => 'online',
        ]]),
    ]);

    expect(fn () => app(TraccarProvider::class)->getDeviceState(42))
        ->toThrow(TrackerProviderException::class);
});

it('allows explicitly configured loopback HTTP in production', function () {
    app()->detectEnvironment(fn (): string => 'production');
    config()->set([
        'latch.traccar.base_url' => 'http://127.0.0.1:8082/api',
        'latch.traccar.allow_insecure_http' => true,
    ]);
    Http::fake([
        'http://127.0.0.1:8082/api/devices*' => Http::response([[
            'id' => 42,
            'uniqueId' => 'hardware-42',
            'status' => 'online',
        ]]),
    ]);

    try {
        expect(app(TraccarProvider::class)->getDeviceState(42)?->uniqueId)
            ->toBe('hardware-42');
    } finally {
        app()->detectEnvironment(fn (): string => 'testing');
    }
});

it('rejects explicitly enabled remote HTTP in production', function () {
    app()->detectEnvironment(fn (): string => 'production');
    config()->set([
        'latch.traccar.base_url' => 'http://tracker.example/api',
        'latch.traccar.allow_insecure_http' => true,
    ]);

    try {
        expect(fn () => app(TraccarProvider::class)->getDeviceState(42))
            ->toThrow(TrackerProviderException::class);
    } finally {
        app()->detectEnvironment(fn (): string => 'testing');
    }
});
