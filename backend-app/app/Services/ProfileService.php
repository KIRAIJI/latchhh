<?php

namespace App\Services;

use App\Exceptions\ApiException;
use App\Models\User;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

class ProfileService
{
    public function __construct(private readonly AuthService $auth) {}

    /**
     * @param  array{name: string, email: string, current_password?: string|null}  $data
     */
    public function update(User $user, array $data, mixed $currentToken): User
    {
        $emailChanged = $user->email !== $data['email'];
        $updated = DB::transaction(function () use ($user, $data, $currentToken): User {
            $locked = User::query()->lockForUpdate()->findOrFail($user->id);

            if ($locked->email !== $data['email']) {
                $this->auth->requireActiveToken($locked, $currentToken);
                $this->verifyCurrentPassword($locked, (string) ($data['current_password'] ?? ''));
            }

            $locked->forceFill([
                'name' => $data['name'],
                'email' => $data['email'],
                'email_verified_at' => $locked->email !== $data['email']
                    ? null
                    : $locked->email_verified_at,
            ])->save();

            return $locked->refresh();
        });

        if ($emailChanged) {
            $updated->sendEmailVerificationNotification();
        }

        return $updated;
    }

    public function changePassword(
        User $user,
        mixed $currentToken,
        string $currentPassword,
        string $newPassword,
    ): int {
        return DB::transaction(function () use (
            $user,
            $currentToken,
            $currentPassword,
            $newPassword,
        ): int {
            $locked = User::query()->lockForUpdate()->findOrFail($user->id);
            $token = $this->auth->requireActiveToken($locked, $currentToken);
            $this->verifyCurrentPassword($locked, $currentPassword);

            if (Hash::check($newPassword, $locked->password)) {
                throw new ApiException(
                    'VALIDATION_ERROR',
                    'The given data was invalid.',
                    422,
                    ['password' => ['The new password must be different from the current password.']],
                );
            }

            $locked->forceFill([
                'password' => Hash::make($newPassword),
                'password_set_at' => $locked->password_set_at ?? now(),
            ])->save();

            return $locked->tokens()->whereKeyNot($token->id)->delete();
        });
    }

    public function settings(User $user, array $settings): User
    {
        return DB::transaction(function () use ($user, $settings): User {
            $locked = User::query()->lockForUpdate()->findOrFail($user->id);
            $locked->forceFill($settings)->save();

            return $locked->refresh();
        });
    }

    public function verifyCurrentPassword(User $user, string $password): void
    {
        if ($user->password_set_at === null) {
            throw new ApiException(
                'AUTH_PASSWORD_NOT_SET',
                'This account does not have a password yet. Use Forgot Password to set one.',
                422,
                ['current_password' => ['Set a password through Forgot Password first.']],
            );
        }

        if (! Hash::check($password, $user->password)) {
            throw new ApiException(
                'AUTH_PASSWORD_INCORRECT',
                'The current password is incorrect.',
                422,
                ['current_password' => ['The current password is incorrect.']],
            );
        }
    }
}
