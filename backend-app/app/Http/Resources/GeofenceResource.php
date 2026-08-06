<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class GeofenceResource extends JsonResource
{
    public function toArray(Request $request): array
    {
        return [
            'id' => $this->id,
            'name' => $this->name,
            'center_latitude' => (float) $this->center_latitude,
            'center_longitude' => (float) $this->center_longitude,
            'radius_meters' => (float) $this->radius_meters,
            'notify_on_enter' => (bool) $this->notify_on_enter,
            'notify_on_exit' => (bool) $this->notify_on_exit,
            'is_active' => (bool) $this->is_active,
            'created_at' => $this->created_at?->utc()->toISOString(),
            'updated_at' => $this->updated_at?->utc()->toISOString(),
        ];
    }
}
