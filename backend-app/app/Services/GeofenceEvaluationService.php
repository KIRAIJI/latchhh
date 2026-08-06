<?php

namespace App\Services;

use App\Data\TrackerPositionData;
use App\Enums\ActivitySource;
use App\Enums\DeviceActivityType;
use App\Enums\NotificationType;
use App\Models\Device;
use App\Models\Geofence;
use App\Models\User;

class GeofenceEvaluationService
{
    public function __construct(
        private readonly DeviceActivityService $activity,
        private readonly NotificationService $notifications,
    ) {}

    public function evaluate(
        User $user,
        Device $device,
        Geofence $geofence,
        TrackerPositionData $position,
    ): void {
        if (
            ! $geofence->is_active
            || $position->gnssValid !== true
            || $position->latitude === null
            || $position->longitude === null
            || $position->recordedAt === null
        ) {
            return;
        }

        if (
            $geofence->last_evaluated_at
            && ! $this->newer(
                $position->recordedAt->getTimestampMs(),
                $position->providerPositionId,
                $geofence->last_evaluated_at->getTimestampMs(),
                (int) $geofence->last_evaluated_position_id,
            )
        ) {
            return;
        }

        $distance = $this->haversineMeters(
            (float) $geofence->center_latitude,
            (float) $geofence->center_longitude,
            $position->latitude,
            $position->longitude,
        );
        $inside = $distance <= (float) $geofence->radius_meters;
        $previous = $geofence->last_inside;

        $geofence->last_inside = $inside;
        $geofence->last_evaluated_position_id = $position->providerPositionId;
        $geofence->last_evaluated_at = $position->recordedAt;
        $geofence->save();

        if ($previous === null || $previous === $inside) {
            return;
        }

        $entering = $inside;
        $type = $entering
            ? DeviceActivityType::GeofenceEnter
            : DeviceActivityType::GeofenceExit;
        $notificationType = $entering
            ? NotificationType::GeofenceEnter
            : NotificationType::GeofenceExit;
        $verb = $entering ? 'entered' : 'left';
        $eventKey = implode(':', [
            'geofence',
            $device->id,
            $device->claim_version,
            $geofence->id,
            $position->providerPositionId,
            $entering ? 'enter' : 'exit',
        ]);

        $this->activity->record(
            $user,
            $device,
            $type,
            ActivitySource::Geofence,
            "Item {$verb} {$geofence->name}",
            "{$device->item_name} {$verb} {$geofence->name}.",
            ['geofence_name' => $geofence->name],
            $eventKey,
            $position->recordedAt,
        );

        $flagEnabled = $entering
            ? $geofence->notify_on_enter
            : $geofence->notify_on_exit;

        if ($flagEnabled) {
            $this->notifications->create(
                $user,
                $device,
                $notificationType,
                "Item {$verb} {$geofence->name}",
                "{$device->item_name} {$verb} {$geofence->name}.",
                $eventKey,
            );
        }
    }

    private function haversineMeters(
        float $latitudeA,
        float $longitudeA,
        float $latitudeB,
        float $longitudeB,
    ): float {
        $latitudeDelta = deg2rad($latitudeB - $latitudeA);
        $longitudeDelta = deg2rad($longitudeB - $longitudeA);
        $a = sin($latitudeDelta / 2) ** 2
            + cos(deg2rad($latitudeA))
            * cos(deg2rad($latitudeB))
            * sin($longitudeDelta / 2) ** 2;
        $a = min(1, max(0, $a));

        return 6371000 * 2 * atan2(sqrt($a), sqrt(1 - $a));
    }

    private function newer(
        int $timestamp,
        int $id,
        int $otherTimestamp,
        int $otherId,
    ): bool {
        return $timestamp > $otherTimestamp
            || ($timestamp === $otherTimestamp && $id > $otherId);
    }
}
