<?php

namespace App\Http\Requests\Auth;

use App\Http\Requests\ApiFormRequest;

class GoogleOAuthRequest extends ApiFormRequest
{
    public function rules(): array
    {
        return [
            'id_token' => ['required', 'string', 'max:10000'],
            'accepted_terms' => ['required', 'accepted'],
            'acknowledged_privacy' => ['required', 'accepted'],
        ];
    }
}
