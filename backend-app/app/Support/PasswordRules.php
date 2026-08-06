<?php

namespace App\Support;

use App\Rules\MaxPasswordBytes;
use Illuminate\Validation\Rules\Password;

final class PasswordRules
{
    /**
     * @return list<mixed>
     */
    public static function newPassword(): array
    {
        return [
            'required',
            'string',
            Password::min(8)
                ->mixedCase()
                ->numbers()
                ->symbols(),
            new MaxPasswordBytes,
            'confirmed',
        ];
    }

    /**
     * @return list<mixed>
     */
    public static function currentPassword(): array
    {
        return ['required', 'string', new MaxPasswordBytes];
    }
}
