<?php

namespace App\Http\Requests\Profile;

use App\Http\Requests\ApiFormRequest;
use App\Support\PasswordRules;

class ChangePasswordRequest extends ApiFormRequest
{
    public function rules(): array
    {
        return [
            'current_password' => PasswordRules::currentPassword(),
            'password' => PasswordRules::newPassword(),
        ];
    }

    public function messages(): array
    {
        return [
            'password.confirmed' => 'Passwords do not match.',
        ];
    }
}
