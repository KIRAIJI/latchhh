<?php

namespace App\Http\Requests\Profile;

use App\Http\Requests\ApiFormRequest;
use App\Rules\MaxPasswordBytes;
use Illuminate\Validation\Rule;

class UpdateProfileRequest extends ApiFormRequest
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
        $emailChanges = is_string($this->email)
            && $this->user()
            && $this->email !== $this->user()->email;

        return [
            'name' => ['required', 'string', 'min:1', 'max:100'],
            'email' => [
                'required',
                'email',
                'max:255',
                Rule::unique('users', 'email')->ignore($this->user()?->id),
            ],
            'current_password' => [
                Rule::requiredIf($emailChanges),
                'nullable',
                'string',
                new MaxPasswordBytes,
            ],
            'password' => ['prohibited'],
            'password_confirmation' => ['prohibited'],
            'role' => ['prohibited'],
            'profile_photo_path' => ['prohibited'],
            'notifications_enabled' => ['prohibited'],
        ];
    }
}
