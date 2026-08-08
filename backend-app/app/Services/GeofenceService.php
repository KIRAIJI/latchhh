<?php

namespace App\Services;

use App\Enums\ActivitySource;
use App\Enums\DeviceActivityType;
use App\Exceptions\ApiException;
use App\Models\Geofence;
use App\Models\User;
use Illuminate\Support\Facades\DB;
use Throwable;

class GeofenceService
{
    public function __construct(
        private readonly DeviceClaimService $devices,
        private readonly DeviceActivityService $activity,
    ) {}

    /**
     * @param  array<string, mixed>  $data
     */
    public function upsert(User $user, int $deviceId, array $data): Geofence
    {
        try {
            return DB::transaction(function () use ($user, $deviceId, $data): Geofence {
                $owner = User::query()->lockForUpdate()->findOrFail($user->id);
                $device = $this->devices->ownedLockedDevice($owner, $deviceId);
                $geofence = $device->geofence()->lockForUpdate()->first();

                $values = [
                    'name' => $data['name'],
                    'center_latitude' => round((float) $data['center_latitude'], 7),
                    'center_longitude' => round((float) $data['center_longitude'], 7),
                    'radius_meters' => round((float) $data['radius_meters'], 2),
                    'notify_on_enter' => (bool) $data['notify_on_enter'],
                    'notify_on_exit' => (bool) $data['notify_on_exit'],
                    'is_active' => (bool) $data['is_active'],
                ];

                if (! $geofence) {
                    $geofence = $device->geofence()->create($values);
                    $type = DeviceActivityType::GeofenceCreated;
                    $changed = array_keys($values);
                } else {
                    $changed = collect($values)
                        ->filter(fn (mixed $value, string $key) => $this->changed($geofence, $key, $value))
                        ->keys()
                        ->all();

                    if ($changed === []) {
                        return $geofence;
                    }

                    $wasActive = $geofence->is_active;
                    $geometryChanged = array_intersect(
                        $changed,
                        ['center_latitude', 'center_longitude', 'radius_meters'],
                    ) !== [];

                    $geofence->fill($values);

                    if ($geometryChanged || (! $wasActive && $geofence->is_active)) {
                        $geofence->last_inside = null;
                        $geofence->last_evaluated_position_id = null;
                        $geofence->last_evaluated_at = null;
                        $geofence->pending_inside = null;
                        $geofence->pending_confirmation_count = 0;
                    } elseif ($wasActive && ! $geofence->is_active) {
                        $geofence->last_inside = null;
                        $geofence->last_evaluated_position_id = null;
                        $geofence->last_evaluated_at = null;
                        $geofence->pending_inside = null;
                        $geofence->pending_confirmation_count = 0;
                    }

                    $geofence->save();

                    $type = match (true) {
                        in_array('is_active', $changed, true) && $geofence->is_active => DeviceActivityType::GeofenceActivated,
                        in_array('is_active', $changed, true) => DeviceActivityType::GeofenceDeactivated,
                        default => DeviceActivityType::GeofenceUpdated,
                    };
                }

                $this->activity->record(
                    $owner,
                    $device,
                    $type,
                    ActivitySource::User,
                    'Geofence changed',
                    "Geofence {$geofence->name} was changed.",
                    [
                        'geofence_name' => $geofence->name,
                        'radius_meters' => (float) $geofence->radius_meters,
                        'is_active' => $geofence->is_active,
                        'changed_fields' => $changed,
                    ],
                    actor: $owner,
                );

                return $geofence->refresh();
            }, 3);
        } catch (ApiException $exception) {
            throw $exception;
        } catch (Throwable $exception) {
            report($exception);

            throw new ApiException(
                'GEOFENCE_OPERATION_FAILED',
                'The geofence could not be saved.',
                500,
            );
        }
    }

    public function delete(User $user, int $deviceId): void
    {
        try {
            DB::transaction(function () use ($user, $deviceId): void {
                $owner = User::query()->lockForUpdate()->findOrFail($user->id);
                $device = $this->devices->ownedLockedDevice($owner, $deviceId);
                $geofence = $device->geofence()->lockForUpdate()->first();

                if (! $geofence) {
                    return;
                }

                $name = $geofence->name;
                $geofence->delete();

                $this->activity->record(
                    $owner,
                    $device,
                    DeviceActivityType::GeofenceDeleted,
                    ActivitySource::User,
                    'Geofence deleted',
                    "Geofence {$name} was deleted.",
                    ['geofence_name' => $name],
                    actor: $owner,
                );
            }, 3);
        } catch (ApiException $exception) {
            throw $exception;
        } catch (Throwable $exception) {
            report($exception);

            throw new ApiException(
                'GEOFENCE_OPERATION_FAILED',
                'The geofence could not be deleted.',
                500,
            );
        }
    }

    private function changed(Geofence $geofence, string $key, mixed $value): bool
    {
        if (in_array($key, ['center_latitude', 'center_longitude', 'radius_meters'], true)) {
            return (float) $geofence->{$key} !== (float) $value;
        }

        return $geofence->{$key} !== $value;
    }
}
