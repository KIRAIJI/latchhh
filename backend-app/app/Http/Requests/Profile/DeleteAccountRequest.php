<?php

namespace App\Http\Requests\Profile;

use App\Http\Requests\ApiFormRequest;
use App\Support\PasswordRules;

class DeleteAccountRequest extends ApiFormRequest
{
    public function rules(): array
    {
        $hasPassword = $this->user()?->password_set_at !== null;

        return [
            'current_password' => $hasPassword
                ? PasswordRules::currentPassword()
                : ['prohibited'],
            'oauth_id_token' => $hasPassword
                ? ['prohibited']
                : ['required', 'string', 'max:10000'],
        ];
    }
}
