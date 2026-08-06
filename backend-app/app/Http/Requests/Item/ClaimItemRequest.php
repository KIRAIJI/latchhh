<?php

namespace App\Http\Requests\Item;

use App\Http\Requests\ApiFormRequest;

class ClaimItemRequest extends ApiFormRequest
{
    protected function prepareForValidation(): void
    {
        $this->merge([
            'device_uid' => is_string($this->device_uid)
                ? mb_strtoupper(trim($this->device_uid))
                : $this->device_uid,
            'item_name' => is_string($this->item_name) ? trim($this->item_name) : $this->item_name,
        ]);
    }

    public function rules(): array
    {
        return [
            'device_uid' => ['required', 'string', 'regex:/^LATCH-[A-Z0-9]{4}-[A-Z0-9]{4}$/'],
            'item_name' => ['required', 'string', 'min:1', 'max:100'],
            'user_id' => ['prohibited'],
            'tracker_unique_id' => ['prohibited'],
            'tracker_device_id' => ['prohibited'],
            'claim_version' => ['prohibited'],
            'claimed_at' => ['prohibited'],
            'last_latitude' => ['prohibited'],
            'last_longitude' => ['prohibited'],
        ];
    }
}
