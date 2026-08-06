<?php

namespace App\Http\Requests\Profile;

use App\Http\Requests\ApiFormRequest;

class UpdateSettingsRequest extends ApiFormRequest
{
    public function rules(): array
    {
        return [
            'notifications_enabled' => ['required', 'boolean'],
            'notify_geofence_events' => ['sometimes', 'boolean'],
            'notify_battery_events' => ['sometimes', 'boolean'],
            'notify_device_status_events' => ['sometimes', 'boolean'],
        ];
    }
}
