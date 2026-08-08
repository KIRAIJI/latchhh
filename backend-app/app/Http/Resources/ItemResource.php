<?php

namespace App\Http\Resources;

use App\Enums\GnssStatus;
use App\Services\StatusNormalizationService;
use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class ItemResource extends JsonResource
{
    public function toArray(Request $request): array
    {
        $status = app(StatusNormalizationService::class);
        $locationType = $status->location($this->resource);
        $connection = $status->connection($this->resource);
        $battery = $status->battery($this->last_battery_percentage);
        $signal = $status->signal($this->last_gsm_csq);

        return [
            'id' => $this->id,
            'device_uid' => $this->device_uid,
            'item_name' => $this->item_name,
            'claimed_at' => $this->claimed_at?->utc()->toISOString(),
            'location' => [
                'latitude' => $this->last_latitude !== null ? (float) $this->last_latitude : null,
                'longitude' => $this->last_longitude !== null ? (float) $this->last_longitude : null,
                'recorded_at' => $this->last_position_at?->utc()->toISOString(),
                'type' => $locationType->value,
            ],
            'status' => [
                'connection' => $connection->value,
                'last_communication_at' => $this->last_communication_at?->utc()->toISOString(),
                'battery_percentage' => $this->last_battery_percentage,
                'battery_status' => $battery->value,
                'gnss_status' => ($this->last_gnss_status ?? GnssStatus::Unknown)->value,
                'telemetry_recorded_at' => $this->last_telemetry_at?->utc()->toISOString(),
                'satellites' => $this->last_satellites,
                'hdop' => $this->last_hdop !== null ? (float) $this->last_hdop : null,
                'gsm_csq' => $this->last_gsm_csq,
                'gsm_signal_level' => $signal->value,
                'power_state' => $this->last_power_state,
                'firmware_version' => $this->last_firmware_version,
                'reset_reason' => $this->last_reset_reason,
            ],
            'geofence' => $this->whenLoaded(
                'geofence',
                fn () => $this->geofence
                    ? [
                        'id' => $this->geofence->id,
                        'name' => $this->geofence->name,
                        'radius_meters' => (float) $this->geofence->radius_meters,
                        'is_active' => (bool) $this->geofence->is_active,
                        'last_inside' => $this->geofence->last_inside,
                    ]
                    : null,
                null,
            ),
        ];
    }
}
