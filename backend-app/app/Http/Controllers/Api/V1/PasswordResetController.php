<?php

namespace App\Http\Controllers\Api\V1;

use App\Exceptions\ApiException;
use App\Http\Controllers\Controller;
use App\Http\Requests\Auth\ForgotPasswordRequest;
use App\Http\Requests\Auth\ResetPasswordRequest;
use App\Support\ApiResponse;
use Illuminate\Auth\Events\PasswordReset;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Password;
use Illuminate\Support\Str;
use Throwable;

class PasswordResetController extends Controller
{
    public function forgot(ForgotPasswordRequest $request)
    {
        try {
            Password::sendResetLink($request->validated());
        } catch (Throwable $exception) {
            Log::error('Password reset notification could not be queued.', [
                'exception_type' => $exception::class,
            ]);
        }

        return ApiResponse::success(
            null,
            'If the account exists, a password reset link has been sent.',
            202,
        );
    }

    public function setupLink(Request $request)
    {
        $user = $request->user();

        if ($user->password_set_at !== null) {
            throw new ApiException(
                'AUTH_PASSWORD_ALREADY_SET',
                'A password is already set for this account.',
                409,
            );
        }

        try {
            $token = Password::broker()->createToken($user);
            $user->sendPasswordSetupNotification($token);
        } catch (Throwable $exception) {
            Log::error('Password setup notification could not be queued.', [
                'exception_type' => $exception::class,
            ]);

            throw new ApiException(
                'PASSWORD_SETUP_UNAVAILABLE',
                'The password setup email could not be sent. Please try again.',
                503,
            );
        }

        return ApiResponse::success(
            null,
            'A password setup link has been sent.',
            202,
        );
    }

    public function reset(ResetPasswordRequest $request)
    {
        $status = Password::reset(
            $request->validated(),
            function ($user, string $password): void {
                $user->forceFill([
                    'password' => Hash::make($password),
                    'password_set_at' => now(),
                    'remember_token' => Str::random(60),
                ])->save();
                $user->tokens()->delete();
                $user->pushTokens()->delete();
                event(new PasswordReset($user));
            },
        );

        if ($status !== Password::PASSWORD_RESET) {
            throw new ApiException(
                'PASSWORD_RESET_INVALID',
                'The password reset link is invalid or has expired.',
                422,
                ['token' => ['The password reset link is invalid or has expired.']],
            );
        }

        return ApiResponse::noContent();
    }

    public function bridge(Request $request)
    {
        $query = http_build_query([
            'token' => (string) $request->query('token'),
            'email' => (string) $request->query('email'),
            'mode' => $request->query('mode') === 'setup' ? 'setup' : 'reset',
        ]);

        return redirect()->away(
            config('latch.mobile.deep_link_scheme').'://reset-password?'.$query,
        );
    }
}
