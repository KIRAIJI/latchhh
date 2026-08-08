<?php

namespace App\Services;

use App\Enums\ActivitySource;
use App\Enums\DeviceActivityType;
use App\Exceptions\ApiException;
use App\Models\Device;
use App\Models\DevicePosition;
use App\Models\Notification;
use App\Models\TrackerPositionReceipt;
use App\Models\User;
use Illuminate\Support\Facades\DB;
use Throwable;

class DeviceClaimService
{
    public function __construct(private readonly DeviceActivityService $activity) {}

    public function claim(User $user, string $deviceUid, string $itemName): Device
    {
        return DB::transaction(function () use ($user, $deviceUid, $itemName): Device {
            $owner = User::query()->lockForUpdate()->findOrFail($user->id);
            $device = Device::query()
                ->where('device_uid', $deviceUid)
                ->lockForUpdate()
                ->first();

            if (! $device) {
                throw new ApiException(
                    'DEVICE_NOT_REGISTERED',
                    'Device is not registered.',
                    404,
                );
            }

            if ($device->user_id !== null) {
                throw new ApiException(
                    'DEVICE_ALREADY_CLAIMED',
                    'Device is already claimed.',
                    409,
                );
            }

            $device->geofence()->lockForUpdate()->first()?->delete();
            Notification::query()->where('device_id', $device->id)->delete();
            TrackerPositionReceipt::query()->where('device_id', $device->id)->delete();
            DevicePosition::query()->where('device_id', $device->id)->delete();

            $this->clearClaimTelemetry($device);
            $device->claim_version++;
            $device->user_id = $owner->id;
            $device->item_name = $itemName;
            $device->claimed_at = now()->utc();
            $device->tracker_cursor_at = $device->claimed_at;
            $device->save();

            $this->activity->record(
                $owner,
                $device,
                DeviceActivityType::ItemClaimed,
                ActivitySource::User,
                'Item claimed',
                "{$itemName} was claimed.",
                eventKey: "claim:{$device->id}:{$device->claim_version}",
                actor: $owner,
            );

            return $device->load('geofence');
        }, 3);
    }

    public function rename(User $user, int $deviceId, string $itemName): Device
    {
        return DB::transaction(function () use ($user, $deviceId, $itemName): Device {
            $owner = User::query()->lockForUpdate()->findOrFail($user->id);
            $device = $this->ownedLockedDevice($owner, $deviceId);

            if ($device->item_name !== $itemName) {
                $oldName = $device->item_name;
                $device->item_name = $itemName;
                $device->save();

                $this->activity->record(
                    $owner,
                    $device,
                    DeviceActivityType::ItemRenamed,
                    ActivitySource::User,
                    'Item renamed',
                    "{$oldName} was renamed to {$itemName}.",
                    ['old_item_name' => $oldName, 'new_item_name' => $itemName],
                    actor: $owner,
                );
            }

            return $device->load('geofence');
        }, 3);
    }

    public function release(User $user, int $deviceId): void
    {
        try {
            DB::transaction(function () use ($user, $deviceId): void {
                $owner = User::query()->lockForUpdate()->findOrFail($user->id);
                $device = $this->ownedLockedDevice($owner, $deviceId);
                $geofence = $device->geofence()->lockForUpdate()->first();

                $this->activity->record(
                    $owner,
                    $device,
                    DeviceActivityType::ItemReleased,
                    ActivitySource::User,
                    'Item released',
                    "{$device->item_name} was released.",
                    eventKey: "release:{$device->id}:{$device->claim_version}",
                    actor: $owner,
                );

                $geofence?->delete();
                Notification::query()
                    ->where('user_id', $owner->id)
                    ->where('device_id', $device->id)
                    ->delete();
                TrackerPositionReceipt::query()->where('device_id', $device->id)->delete();
                DevicePosition::query()->where('device_id', $device->id)->delete();

                $device->claim_version++;
                $this->clearClaimTelemetry($device);
                $device->user_id = null;
                $device->item_name = null;
                $device->claimed_at = null;
                $device->tracker_cursor_at = null;
                $device->save();
            }, 3);
        } catch (ApiException $exception) {
            throw $exception;
        } catch (Throwable $exception) {
            report($exception);

            throw new ApiException(
                'DEVICE_RELEASE_FAILED',
                'The item could not be released.',
                500,
            );
        }
    }

    public function ownedDevice(User $user, int $deviceId): Device
    {
        $device = Device::query()
            ->whereKey($deviceId)
            ->where('user_id', $user->id)
            ->first();

        if (! $device) {
            throw new ApiException('ITEM_NOT_FOUND', 'Item not found.', 404);
        }

        return $device;
    }

    public function ownedLockedDevice(User $user, int $deviceId): Device
    {
        $device = Device::query()
            ->whereKey($deviceId)
            ->where('user_id', $user->id)
            ->lockForUpdate()
            ->first();

        if (! $device) {
            throw new ApiException('ITEM_NOT_FOUND', 'Item not found.', 404);
        }

        return $device;
    }

    public function clearClaimTelemetry(Device $device): void
    {
        $device->forceFill([
            'tracker_cursor_at' => null,
            'last_provider_position_id' => null,
            'last_latitude' => null,
            'last_longitude' => null,
            'last_location_source' => null,
            'last_location_accuracy_meters' => null,
            'last_place_name' => null,
            'last_place_resolved_at' => null,
            'last_position_at' => null,
            'last_telemetry_position_id' => null,
            'last_telemetry_at' => null,
            'last_communication_at' => null,
            'last_provider_connection_status' => null,
            'last_battery_percentage' => null,
            'last_gnss_status' => null,
            'last_satellites' => null,
            'last_hdop' => null,
            'last_gsm_csq' => null,
            'last_power_state' => null,
            'last_firmware_version' => null,
            'last_reset_reason' => null,
            'last_synced_at' => null,
            'notification_battery_state' => null,
            'notification_connection_state' => null,
            'notification_connection_reference_at' => null,
        ]);
    }
}
