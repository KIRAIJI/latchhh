<?php

namespace App\Http\Requests\Auth;

use App\Http\Requests\ApiFormRequest;
use App\Support\PasswordRules;
use Illuminate\Validation\Rule;

class RegisterRequest extends ApiFormRequest
{
    protected function prepareForValidation(): void
    {
        $this->merge([
            'name' => is_string($this->name) ? trim($this->name) : $this->name,
            'email' => is_string($this->email) ? mb_strtolower(trim($this->email)) : $this->email,
        ]);
    }

    public function rules(): array
    {
        return [
            'name' => ['required', 'string', 'min:1', 'max:100'],
            'email' => ['required', 'email', 'max:255', Rule::unique('users', 'email')],
            'password' => PasswordRules::newPassword(),
            'accepted_terms' => ['required', 'accepted'],
            'acknowledged_privacy' => ['required', 'accepted'],
            'device_name' => ['nullable', 'string', 'max:100'],
            'platform' => ['nullable', 'string', 'in:android,ios,web,windows,macos,linux'],
            'role' => ['prohibited'],
            'notifications_enabled' => ['prohibited'],
            'profile_photo_path' => ['prohibited'],
        ];
    }

    public function messages(): array
    {
        return [
            'password.confirmed' => 'Passwords do not match.',
        ];
    }
}
