<?php

use App\Data\TrackerPositionData;
use App\Models\Device;
use App\Models\Geofence;
use App\Models\Notification;
use App\Models\User;
use App\Services\GeofenceEvaluationService;
use Carbon\CarbonImmutable;

it('requires two definite approximate observations and ignores boundary uncertainty', function () {
    $user = User::factory()->create();
    $device = Device::factory()->create([
        'user_id' => $user->id,
        'claim_version' => 1,
        'item_name' => 'School Bag',
    ]);
    $geofence = Geofence::query()->create([
        'device_id' => $device->id,
        'name' => 'School',
        'center_latitude' => 0,
        'center_longitude' => 0,
        'radius_meters' => 100,
        'notify_on_enter' => true,
        'notify_on_exit' => true,
        'is_active' => true,
    ]);
    $service = app(GeofenceEvaluationService::class);

    $position = fn (int $id, float $latitude, float $accuracy) => new TrackerPositionData(
        providerPositionId: $id,
        providerDeviceId: (int) $device->tracker_device_id,
        latitude: $latitude,
        longitude: 0,
        recordedAt: CarbonImmutable::parse("2026-08-08T00:0{$id}:00Z"),
        gnssValid: true,
        batteryPercentage: null,
        satellites: null,
        hdop: null,
        gsmCsq: null,
        approximate: true,
        accuracyMeters: $accuracy,
    );

    $service->evaluate($user, $device, $geofence, $position(1, 0, 20));
    expect($geofence->refresh()->last_inside)->toBeNull()
        ->and($geofence->pending_confirmation_count)->toBe(1);

    $service->evaluate($user, $device, $geofence, $position(2, 0, 20));
    expect($geofence->refresh()->last_inside)->toBeTrue();

    // About 89 m from center with +/- 20 m accuracy overlaps the 100 m boundary.
    $service->evaluate($user, $device, $geofence, $position(3, 0.0008, 20));
    expect($geofence->refresh()->last_inside)->toBeTrue()
        ->and($geofence->pending_confirmation_count)->toBe(0);

    // About 222 m from center is definitely outside, but requires two readings.
    $service->evaluate($user, $device, $geofence, $position(4, 0.002, 20));
    expect($geofence->refresh()->last_inside)->toBeTrue()
        ->and(Notification::query()->count())->toBe(0);

    $service->evaluate($user, $device, $geofence, $position(5, 0.002, 20));
    expect($geofence->refresh()->last_inside)->toBeFalse()
        ->and(Notification::query()->where('type', 'geofence_exit')->count())->toBe(1);
});
