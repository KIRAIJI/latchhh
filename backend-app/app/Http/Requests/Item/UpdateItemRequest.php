<?php

namespace App\Http\Requests\Item;

use App\Http\Requests\ApiFormRequest;

class UpdateItemRequest extends ApiFormRequest
{
    protected function prepareForValidation(): void
    {
        $this->merge([
            'item_name' => is_string($this->item_name) ? trim($this->item_name) : $this->item_name,
        ]);
    }

    public function rules(): array
    {
        return [
            'item_name' => ['required', 'string', 'min:1', 'max:100'],
            'device_uid' => ['prohibited'],
            'tracker_unique_id' => ['prohibited'],
            'tracker_device_id' => ['prohibited'],
            'user_id' => ['prohibited'],
            'claim_version' => ['prohibited'],
            'claimed_at' => ['prohibited'],
            'last_latitude' => ['prohibited'],
            'last_longitude' => ['prohibited'],
            'notification_battery_state' => ['prohibited'],
            'notification_connection_state' => ['prohibited'],
        ];
    }
}
