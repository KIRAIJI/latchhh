<?php

use App\Contracts\TrackerProviderInterface;
use App\Data\TrackerDeviceData;
use App\Data\TrackerPositionData;
use App\Models\Device;
use App\Models\DeviceActivityLog;
use App\Models\DevicePosition;
use App\Models\Geofence;
use App\Models\Notification;
use App\Models\User;
use App\Providers\Tracker\FakeTrackerProvider;
use App\Services\TrackerSyncService;
use Carbon\CarbonImmutable;

it('synchronizes validated fixes into real history and evaluates transitions once', function () {
    CarbonImmutable::setTestNow('2026-07-26T01:00:00Z');

    $user = User::factory()->create();
    $device = Device::factory()->create([
        'user_id' => $user->id,
        'item_name' => 'Tracker Item',
        'claim_version' => 1,
        'claimed_at' => now()->subMinutes(10),
        'tracker_cursor_at' => now()->subMinutes(10),
    ]);
    Geofence::query()->create([
        'device_id' => $device->id,
        'name' => 'Home',
        'center_latitude' => 0,
        'center_longitude' => 0,
        'radius_meters' => 100,
        'notify_on_enter' => true,
        'notify_on_exit' => true,
        'is_active' => true,
    ]);

    $fake = new FakeTrackerProvider;
    $fake->addDevice(new TrackerDeviceData(
        $device->tracker_device_id,
        $device->tracker_unique_id,
        'online',
        CarbonImmutable::now()->subSeconds(10),
    ));
    $fake->setPositions($device->tracker_device_id, [
        new TrackerPositionData(
            providerPositionId: 1,
            providerDeviceId: $device->tracker_device_id,
            latitude: 1.0,
            longitude: 1.0,
            recordedAt: CarbonImmutable::now()->subMinute(),
            gnssValid: true,
            batteryPercentage: 50,
            satellites: 8,
            hdop: 1.2,
            gsmCsq: 20,
            powerState: 'battery',
            firmwareVersion: '1.1.0',
            resetReason: 'power_on',
        ),
    ]);
    app()->instance(TrackerProviderInterface::class, $fake);

    app(TrackerSyncService::class)->sync(
        $device->id,
        $user->id,
        1,
        $device->claimed_at->utc()->toISOString(),
    );

    expect(DevicePosition::query()->count())->toBe(1)
        ->and($device->fresh()->last_latitude)->toBe(1.0)
        ->and($device->fresh()->last_power_state)->toBe('battery')
        ->and($device->fresh()->last_firmware_version)->toBe('1.1.0')
        ->and($device->fresh()->last_reset_reason)->toBe('power_on')
        ->and($device->fresh()->geofence->last_inside)->toBeFalse()
        ->and(Notification::query()->count())->toBe(0);

    CarbonImmutable::setTestNow('2026-07-26T01:01:00Z');
    $fake->addDevice(new TrackerDeviceData(
        $device->tracker_device_id,
        $device->tracker_unique_id,
        'online',
        CarbonImmutable::now()->subSeconds(5),
    ));
    $fake->setPositions($device->tracker_device_id, [
        new TrackerPositionData(
            providerPositionId: 1,
            providerDeviceId: $device->tracker_device_id,
            latitude: 1.0,
            longitude: 1.0,
            recordedAt: CarbonImmutable::parse('2026-07-26T00:59:00Z'),
            gnssValid: true,
            batteryPercentage: 50,
            satellites: 8,
            hdop: 1.2,
            gsmCsq: 20,
        ),
        new TrackerPositionData(
            providerPositionId: 2,
            providerDeviceId: $device->tracker_device_id,
            latitude: 0.0,
            longitude: 0.0,
            recordedAt: CarbonImmutable::parse('2026-07-26T01:00:30Z'),
            gnssValid: true,
            batteryPercentage: 15,
            satellites: 9,
            hdop: 0.9,
            gsmCsq: 25,
        ),
    ]);

    app(TrackerSyncService::class)->sync(
        $device->id,
        $user->id,
        1,
        $device->claimed_at->utc()->toISOString(),
    );

    expect(DevicePosition::query()->count())->toBe(2)
        ->and(Notification::query()->where('type', 'geofence_enter')->count())->toBe(1)
        ->and(Notification::query()->where('type', 'battery_low')->count())->toBe(1)
        ->and(DeviceActivityLog::query()->where('event_type', 'geofence_enter')->count())->toBe(1);

    app(TrackerSyncService::class)->sync(
        $device->id,
        $user->id,
        1,
        $device->claimed_at->utc()->toISOString(),
    );

    expect(DevicePosition::query()->count())->toBe(2)
        ->and(Notification::query()->where('type', 'geofence_enter')->count())->toBe(1);

    CarbonImmutable::setTestNow();
});

it('never stores an invalid GNSS fix as location history', function () {
    CarbonImmutable::setTestNow('2026-07-26T02:00:00Z');

    $user = User::factory()->create();
    $device = Device::factory()->create([
        'user_id' => $user->id,
        'item_name' => 'Invalid Fix Item',
        'claim_version' => 1,
        'claimed_at' => now()->subMinutes(5),
        'tracker_cursor_at' => now()->subMinutes(5),
    ]);
    $fake = (new FakeTrackerProvider)
        ->addDevice(new TrackerDeviceData(
            $device->tracker_device_id,
            $device->tracker_unique_id,
            'online',
            CarbonImmutable::now(),
        ))
        ->setPositions($device->tracker_device_id, [
            new TrackerPositionData(
                providerPositionId: 5,
                providerDeviceId: $device->tracker_device_id,
                latitude: 15.0,
                longitude: 120.0,
                recordedAt: CarbonImmutable::now()->subMinute(),
                gnssValid: false,
                batteryPercentage: null,
                satellites: null,
                hdop: null,
                gsmCsq: null,
            ),
        ]);
    app()->instance(TrackerProviderInterface::class, $fake);

    app(TrackerSyncService::class)->sync(
        $device->id,
        $user->id,
        1,
        $device->claimed_at->utc()->toISOString(),
    );

    expect(DevicePosition::query()->count())->toBe(0)
        ->and($device->fresh()->last_latitude)->toBeNull()
        ->and($device->fresh()->last_gnss_status->value)->toBe('no_fix');

    CarbonImmutable::setTestNow();
});
