<?php

namespace App\Http\Requests\Activity;

use App\Enums\ActivitySource;
use App\Enums\DeviceActivityType;
use App\Http\Requests\ApiFormRequest;
use Illuminate\Validation\Rule;

class ActivityIndexRequest extends ApiFormRequest
{
    protected function prepareForValidation(): void
    {
        if (is_string($this->device_uid)) {
            $this->merge(['device_uid' => mb_strtoupper(trim($this->device_uid))]);
        }
    }

    public function rules(): array
    {
        return [
            'event_type' => ['nullable', Rule::enum(DeviceActivityType::class)],
            'source' => ['nullable', Rule::enum(ActivitySource::class)],
            'device_uid' => ['nullable', 'string', 'regex:/^LATCH-[A-Z0-9]{4}-[A-Z0-9]{4}$/'],
            'from' => ['nullable', 'date_format:Y-m-d\TH:i:sP', 'required_with:to'],
            'to' => ['nullable', 'date_format:Y-m-d\TH:i:sP', 'required_with:from'],
            'cursor' => ['nullable', 'string', 'max:4096'],
            'per_page' => ['nullable', 'integer', 'between:1,100'],
        ];
    }
}
