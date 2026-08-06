<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class NotificationResource extends JsonResource
{
    public function toArray(Request $request): array
    {
        $related = null;

        if (
            $this->relationLoaded('device')
            && $this->device
            && $this->device->user_id === $this->user_id
        ) {
            $related = [
                'id' => $this->device->id,
                'device_uid' => $this->device->device_uid,
                'item_name' => $this->device->item_name,
            ];
        }

        return [
            'id' => $this->id,
            'type' => $this->type->value,
            'title' => $this->title,
            'message' => $this->message,
            'created_at' => $this->created_at?->utc()->toISOString(),
            'read_at' => $this->read_at?->utc()->toISOString(),
            'is_read' => $this->read_at !== null,
            'related_item' => $related,
        ];
    }
}
