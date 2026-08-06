<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class DeviceActivityResource extends JsonResource
{
    public function toArray(Request $request): array
    {
        $navigableItemId = null;

        if (
            $this->relationLoaded('device')
            && $this->device
            && $this->device->user_id === $this->user_id
            && $this->device->claim_version === $this->claim_version
        ) {
            $navigableItemId = $this->device->id;
        }

        return [
            'id' => $this->id,
            'event_type' => $this->event_type->value,
            'title' => $this->title,
            'description' => $this->description,
            'source' => $this->source->value,
            'device' => [
                'item_id' => $navigableItemId,
                'device_uid' => $this->device_uid_snapshot,
                'item_name' => $this->item_name_snapshot,
            ],
            'event_data' => $this->event_data,
            'occurred_at' => $this->occurred_at->utc()->toISOString(),
        ];
    }
}
