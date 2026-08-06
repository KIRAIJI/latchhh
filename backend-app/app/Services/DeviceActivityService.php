<?php

namespace App\Services;

use App\Enums\ActivitySource;
use App\Enums\DeviceActivityType;
use App\Models\Device;
use App\Models\DeviceActivityLog;
use App\Models\User;
use Carbon\CarbonInterface;
use Illuminate\Support\Str;

class DeviceActivityService
{
    /**
     * @param  array<string, mixed>|null  $eventData
     */
    public function record(
        User $owner,
        Device $device,
        DeviceActivityType $type,
        ActivitySource $source,
        string $title,
        string $description,
        ?array $eventData = null,
        ?string $eventKey = null,
        ?CarbonInterface $occurredAt = null,
        ?User $actor = null,
    ): DeviceActivityLog {
        $eventKey ??= 'user:'.$type->value.':'.Str::uuid()->toString();

        return DeviceActivityLog::query()->createOrFirst(
            ['event_key' => $eventKey],
            [
                'user_id' => $owner->id,
                'device_id' => $device->id,
                'claim_version' => $device->claim_version,
                'actor_user_id' => $actor?->id,
                'event_type' => $type,
                'source' => $source,
                'title' => $title,
                'description' => $description,
                'device_uid_snapshot' => $device->device_uid,
                'item_name_snapshot' => $device->item_name,
                'event_data' => $eventData,
                'occurred_at' => ($occurredAt ?? now())->utc(),
                'created_at' => now()->utc(),
            ],
        );
    }
}
