<?php

use App\Data\TrackerPositionData;
use App\Enums\ConnectionStatus;
use App\Enums\GsmSignalLevel;
use App\Models\Device;
use App\Models\DeviceActivityLog;
use App\Models\Notification;
use App\Models\User;
use App\Services\DeviceTransitionService;
use App\Services\StatusNormalizationService;
use Carbon\CarbonImmutable;

it('normalizes every GSM CSQ boundary exactly', function () {
    $status = app(StatusNormalizationService::class);

    expect($status->signal(null))->toBe(GsmSignalLevel::Unknown)
        ->and($status->signal(99))->toBe(GsmSignalLevel::Unknown)
        ->and($status->signal(0))->toBe(GsmSignalLevel::NoSignal)
        ->and($status->signal(1))->toBe(GsmSignalLevel::NoSignal)
        ->and($status->signal(2))->toBe(GsmSignalLevel::Poor)
        ->and($status->signal(9))->toBe(GsmSignalLevel::Poor)
        ->and($status->signal(10))->toBe(GsmSignalLevel::Fair)
        ->and($status->signal(14))->toBe(GsmSignalLevel::Fair)
        ->and($status->signal(15))->toBe(GsmSignalLevel::Good)
        ->and($status->signal(19))->toBe(GsmSignalLevel::Good)
        ->and($status->signal(20))->toBe(GsmSignalLevel::Excellent)
        ->and($status->signal(31))->toBe(GsmSignalLevel::Excellent);
});

it('keeps an offline episode closed until a genuinely newer online communication', function () {
    CarbonImmutable::setTestNow('2026-07-26T08:00:00Z');

    $user = User::factory()->create();
    $device = Device::factory()->create([
        'user_id' => $user->id,
        'item_name' => 'Connection Item',
        'claim_version' => 1,
        'claimed_at' => now()->subHour(),
        'last_communication_at' => now()->subSeconds(601),
        'last_provider_connection_status' => 'online',
        'notification_connection_state' => ConnectionStatus::Online->value,
        'notification_connection_reference_at' => now()->subSeconds(601),
    ]);
    $transitions = app(DeviceTransitionService::class);

    $transitions->connection($user, $device);
    $device->save();
    $transitions->connection($user, $device);
    $device->save();

    expect(DeviceActivityLog::query()->where('event_type', 'device_offline')->count())->toBe(1)
        ->and(Notification::query()->where('type', 'device_offline')->count())->toBe(1);

    $offlineReference = $device->notification_connection_reference_at;
    $device->last_provider_connection_status = null;
    $transitions->connection($user, $device);

    expect($device->notification_connection_state)->toBe(ConnectionStatus::Offline->value)
        ->and($device->notification_connection_reference_at->equalTo($offlineReference))->toBeTrue();

    $device->last_provider_connection_status = 'online';
    $device->last_communication_at = now();
    $transitions->connection($user, $device);
    $device->save();

    expect(DeviceActivityLog::query()->where('event_type', 'device_online')->count())->toBe(1)
        ->and(Notification::query()->where('type', 'device_online')->count())->toBe(1)
        ->and($device->notification_connection_state)->toBe(ConnectionStatus::Online->value);

    $recoveryReference = $device->notification_connection_reference_at;
    CarbonImmutable::setTestNow('2026-07-26T08:05:00Z');
    $transitions->connection($user, $device);

    expect($device->notification_connection_state)->toBe(ConnectionStatus::Online->value)
        ->and($device->notification_connection_reference_at->equalTo($recoveryReference))->toBeTrue();

    CarbonImmutable::setTestNow();
});

it('records factual battery activity while notification creation is disabled', function () {
    $user = User::factory()->create(['notifications_enabled' => false]);
    $device = Device::factory()->create([
        'user_id' => $user->id,
        'item_name' => 'Battery Item',
        'claim_version' => 2,
        'claimed_at' => now()->subHour(),
    ]);
    $position = new TrackerPositionData(
        providerPositionId: 10,
        providerDeviceId: $device->tracker_device_id,
        latitude: null,
        longitude: null,
        recordedAt: CarbonImmutable::now(),
        gnssValid: false,
        batteryPercentage: 10,
        satellites: null,
        hdop: null,
        gsmCsq: null,
    );

    app(DeviceTransitionService::class)->battery($user, $device, $position);
    $device->save();

    expect(DeviceActivityLog::query()->where('event_type', 'battery_critical')->count())->toBe(1)
        ->and(Notification::query()->count())->toBe(0)
        ->and($device->notification_battery_state)->toBe('critical');
});
