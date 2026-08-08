<?php

use App\Contracts\TrackerProviderInterface;
use App\Data\TrackerDeviceData;
use App\Data\TrackerPositionData;
use App\Models\Device;
use App\Models\DevicePosition;
use App\Models\User;
use App\Providers\Tracker\FakeTrackerProvider;
use Carbon\CarbonImmutable;

function bearerFor(User $user): string
{
    return $user->createToken('test')->plainTextToken;
}

it('claims, lists, renames, configures, and releases an item', function () {
    $user = User::factory()->create();
    $token = bearerFor($user);
    $device = Device::query()->create([
        'device_uid' => 'LATCH-7K3M-P9Q2',
        'tracker_unique_id' => 'tracker-001',
        'tracker_device_id' => 101,
    ]);

    $claim = $this->withToken($token)->postJson('/api/v1/items/claim', [
        'device_uid' => strtolower($device->device_uid),
        'item_name' => 'Black Backpack',
    ]);

    $claim
        ->assertCreated()
        ->assertJsonPath('data.device_uid', 'LATCH-7K3M-P9Q2')
        ->assertJsonPath('data.location.type', 'unavailable')
        ->assertJsonPath('data.status.connection', 'unknown')
        ->assertJsonPath('data.status.telemetry_recorded_at', null);

    $this->withToken($token)
        ->getJson('/api/v1/items')
        ->assertOk()
        ->assertJsonCount(1, 'data');

    $this->withToken($token)
        ->patchJson("/api/v1/items/{$device->id}", ['item_name' => 'Blue Luggage'])
        ->assertOk()
        ->assertJsonPath('data.item_name', 'Blue Luggage');

    $this->withToken($token)
        ->putJson("/api/v1/items/{$device->id}/geofence", [
            'name' => 'Home',
            'center_latitude' => 15.145,
            'center_longitude' => 120.588,
            'radius_meters' => 100,
            'notify_on_enter' => true,
            'notify_on_exit' => true,
            'is_active' => true,
        ])
        ->assertOk()
        ->assertJsonPath('data.name', 'Home')
        ->assertJsonPath('data.last_inside', null);

    $this->withToken($token)
        ->getJson("/api/v1/items/{$device->id}")
        ->assertOk()
        ->assertJsonPath('data.geofence.name', 'Home')
        ->assertJsonPath('data.geofence.last_inside', null)
        ->assertJsonMissingPath('data.geofence.center_latitude')
        ->assertJsonMissingPath('data.geofence.notify_on_enter');

    $this->withToken($token)
        ->deleteJson("/api/v1/items/{$device->id}")
        ->assertNoContent();

    $released = $device->fresh();

    expect($released->user_id)->toBeNull()
        ->and($released->last_latitude)->toBeNull()
        ->and($released->claim_version)->toBe(2)
        ->and($released->geofence)->toBeNull();

    $this->withToken($token)
        ->getJson("/api/v1/items/{$device->id}")
        ->assertNotFound();

    $this->withToken($token)
        ->getJson('/api/v1/activity')
        ->assertOk()
        ->assertJsonCount(4, 'data');
});

it('timestamps the last GNSS report so clients do not present it as live', function () {
    $user = User::factory()->create();
    $telemetryAt = now()->utc()->subMinutes(15);
    $device = Device::query()->create([
        'device_uid' => 'LATCH-GNSS-0001',
        'tracker_unique_id' => 'tracker-gnss',
        'tracker_device_id' => 103,
        'user_id' => $user->id,
        'item_name' => 'Tracker',
        'claim_version' => 1,
        'claimed_at' => now()->utc()->subHour(),
        'last_telemetry_at' => $telemetryAt,
        'last_gnss_status' => 'fixed',
        'last_power_state' => 'charging',
        'last_firmware_version' => '1.1.0',
        'last_reset_reason' => 'power_on',
    ]);

    $this->withToken(bearerFor($user))
        ->getJson("/api/v1/items/{$device->id}")
        ->assertOk()
        ->assertJsonPath('data.status.gnss_status', 'fixed')
        ->assertJsonPath('data.status.power_state', 'charging')
        ->assertJsonPath('data.status.firmware_version', '1.1.0')
        ->assertJsonPath('data.status.reset_reason', 'power_on')
        ->assertJsonPath(
            'data.status.telemetry_recorded_at',
            $device->fresh()->last_telemetry_at->toISOString(),
        );
});

it('isolates item ownership', function () {
    $owner = User::factory()->create();
    $other = User::factory()->create();
    $device = Device::query()->create([
        'device_uid' => 'LATCH-AAAA-BBBB',
        'tracker_unique_id' => 'tracker-002',
        'tracker_device_id' => 102,
        'user_id' => $owner->id,
        'item_name' => 'Owner Item',
        'claim_version' => 1,
        'claimed_at' => now(),
    ]);

    $this->withToken(bearerFor($other))
        ->getJson("/api/v1/items/{$device->id}")
        ->assertNotFound();
});

it('synchronizes an owned item immediately when manually refreshed', function () {
    CarbonImmutable::setTestNow('2026-07-30T01:00:00Z');

    $user = User::factory()->create();
    $device = Device::factory()->create([
        'user_id' => $user->id,
        'item_name' => 'Refreshable Item',
        'claim_version' => 1,
        'claimed_at' => now()->subMinutes(10),
        'tracker_cursor_at' => now()->subMinutes(10),
    ]);
    $positionTime = CarbonImmutable::now()->subSecond();
    $fake = (new FakeTrackerProvider)
        ->addDevice(new TrackerDeviceData(
            $device->tracker_device_id,
            $device->tracker_unique_id,
            'online',
            $positionTime,
        ))
        ->setPositions($device->tracker_device_id, [
            new TrackerPositionData(
                providerPositionId: 9001,
                providerDeviceId: $device->tracker_device_id,
                latitude: 15.178149,
                longitude: 120.650553,
                recordedAt: $positionTime,
                gnssValid: true,
                batteryPercentage: null,
                satellites: null,
                hdop: null,
                gsmCsq: null,
            ),
        ]);
    app()->instance(TrackerProviderInterface::class, $fake);

    $this->withToken(bearerFor($user))
        ->postJson("/api/v1/items/{$device->id}/refresh")
        ->assertOk()
        ->assertJsonPath('data.location.latitude', 15.178149)
        ->assertJsonPath('data.location.longitude', 120.650553)
        ->assertJsonPath('data.location.recorded_at', $positionTime->toISOString());

    expect($device->fresh()->last_synced_at)->not->toBeNull()
        ->and(DevicePosition::query()
            ->where('provider_position_id', 9001)
            ->exists())->toBeTrue();

    CarbonImmutable::setTestNow();
});

it('does not manually refresh another users item', function () {
    $owner = User::factory()->create();
    $other = User::factory()->create();
    $device = Device::factory()->create([
        'user_id' => $owner->id,
        'item_name' => 'Private Tracker',
        'claim_version' => 1,
        'claimed_at' => now()->subMinutes(10),
    ]);

    $this->withToken(bearerFor($other))
        ->postJson("/api/v1/items/{$device->id}/refresh")
        ->assertNotFound();
});

it('returns a safe service error when manual tracker refresh fails', function () {
    $user = User::factory()->create();
    $device = Device::factory()->create([
        'user_id' => $user->id,
        'item_name' => 'Unavailable Tracker',
        'claim_version' => 1,
        'claimed_at' => now()->subMinutes(10),
    ]);
    app()->instance(TrackerProviderInterface::class, new FakeTrackerProvider);

    $this->withToken(bearerFor($user))
        ->postJson("/api/v1/items/{$device->id}/refresh")
        ->assertServiceUnavailable()
        ->assertJsonPath('code', 'TRACKER_UNAVAILABLE')
        ->assertJsonMissingPath('exception');
});

it('returns real current claim location history oldest first', function () {
    $user = User::factory()->create();
    $device = Device::query()->create([
        'device_uid' => 'LATCH-CCCC-DDDD',
        'tracker_unique_id' => 'tracker-003',
        'tracker_device_id' => 103,
        'user_id' => $user->id,
        'item_name' => 'History Item',
        'claim_version' => 4,
        'claimed_at' => now()->subHour(),
    ]);

    DevicePosition::query()->create([
        'user_id' => $user->id,
        'device_id' => $device->id,
        'claim_version' => 4,
        'provider_position_id' => 2,
        'latitude' => 15.2,
        'longitude' => 120.6,
        'recorded_at' => now()->subMinutes(5),
        'created_at' => now(),
    ]);
    DevicePosition::query()->create([
        'user_id' => $user->id,
        'device_id' => $device->id,
        'claim_version' => 4,
        'provider_position_id' => 1,
        'latitude' => 15.1,
        'longitude' => 120.5,
        'recorded_at' => now()->subMinutes(10),
        'created_at' => now(),
    ]);

    $this->withToken(bearerFor($user))
        ->getJson("/api/v1/items/{$device->id}/location-history")
        ->assertOk()
        ->assertJsonCount(2, 'data')
        ->assertJsonPath('data.0.latitude', 15.1)
        ->assertJsonPath('data.1.latitude', 15.2)
        ->assertJsonMissingPath('data.0.provider_position_id');
});
