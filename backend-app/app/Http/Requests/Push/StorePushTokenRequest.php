<?php

namespace App\Http\Requests\Push;

use App\Http\Requests\ApiFormRequest;
use Illuminate\Validation\Rule;

class StorePushTokenRequest extends ApiFormRequest
{
    public function rules(): array
    {
        return [
            'token' => ['required', 'string', 'min:20', 'max:4096'],
            'platform' => ['required', Rule::in(['android', 'ios'])],
            'device_name' => ['nullable', 'string', 'max:100'],
            'app_version' => ['nullable', 'string', 'max:50'],
        ];
    }
}
