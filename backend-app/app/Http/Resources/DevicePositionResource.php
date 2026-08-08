<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class DevicePositionResource extends JsonResource
{
    public function toArray(Request $request): array
    {
        return [
            'latitude' => (float) $this->latitude,
            'longitude' => (float) $this->longitude,
            'source' => $this->source,
            'accuracy_meters' => $this->accuracy_meters !== null
                ? (float) $this->accuracy_meters
                : null,
            'recorded_at' => $this->recorded_at->utc()->toISOString(),
        ];
    }
}
