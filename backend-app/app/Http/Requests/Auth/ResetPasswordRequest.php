<?php

namespace App\Http\Requests\Auth;

use App\Http\Requests\ApiFormRequest;
use App\Support\PasswordRules;

class ResetPasswordRequest extends ApiFormRequest
{
    protected function prepareForValidation(): void
    {
        $this->merge([
            'email' => is_string($this->email)
                ? mb_strtolower(trim($this->email))
                : $this->email,
        ]);
    }

    public function rules(): array
    {
        return [
            'email' => ['required', 'email', 'max:255'],
            'token' => ['required', 'string', 'max:255'],
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
