<?php

namespace App\Http\Requests\Geofence;

use App\Http\Requests\ApiFormRequest;

class UpsertGeofenceRequest extends ApiFormRequest
{
    protected function prepareForValidation(): void
    {
        $this->merge([
            'name' => is_string($this->name) ? trim($this->name) : $this->name,
        ]);
    }

    public function rules(): array
    {
        return [
            'name' => ['required', 'string', 'min:1', 'max:100'],
            'center_latitude' => ['required', 'numeric', 'between:-90,90'],
            'center_longitude' => ['required', 'numeric', 'between:-180,180'],
            'radius_meters' => ['required', 'numeric', 'between:50,5000'],
            'notify_on_enter' => ['required', 'boolean'],
            'notify_on_exit' => ['required', 'boolean'],
            'is_active' => ['required', 'boolean'],
        ];
    }
}
