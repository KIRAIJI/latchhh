<?php

namespace App\Http\Requests\Push;

use App\Http\Requests\ApiFormRequest;

class DeletePushTokenRequest extends ApiFormRequest
{
    public function rules(): array
    {
        return [
            'token' => ['required', 'string', 'min:20', 'max:4096'],
        ];
    }
}
