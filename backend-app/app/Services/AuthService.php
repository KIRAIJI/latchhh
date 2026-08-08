<?php

namespace App\Services;

use App\Contracts\OAuthTokenVerifierInterface;
use App\Data\OAuthIdentityData;
use App\Exceptions\ApiException;
use App\Models\OAuthIdentity;
use App\Models\User;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Str;
use Laravel\Sanctum\PersonalAccessToken;

class AuthService
{
    public function __construct(
        private readonly OAuthTokenVerifierInterface $oauthTokens,
    ) {}

    /**
     * @param  array{name: string, email: string, password: string}  $data
     * @return array{user: User, token: string}
     */
    public function register(array $data): array
    {
        $result = DB::transaction(function () use ($data): array {
            $user = User::query()->create([
                'name' => $data['name'],
                'email' => $data['email'],
                'password' => Hash::make($data['password']),
                'password_set_at' => now(),
                'notifications_enabled' => true,
                'terms_accepted_at' => now(),
                'terms_version' => config('latch.legal.terms_version'),
                'privacy_acknowledged_at' => now(),
                'privacy_version' => config('latch.legal.privacy_version'),
            ]);
            $token = $this->createSessionToken($user, $data);
            $user->sendEmailVerificationNotification();

            return [
                'user' => $user,
                'token' => $token,
            ];
        });

        return $result;
    }

    /**
     * @return array{user: User, token: string}
     */
    public function login(string $email, string $password, array $session = []): array
    {
        $userId = User::query()->where('email', $email)->value('id');

        if ($userId === null) {
            throw $this->invalidCredentials();
        }

        return DB::transaction(function () use ($userId, $password, $session): array {
            $user = User::query()->lockForUpdate()->find($userId);

            if (
                ! $user
                || $user->password_set_at === null
                || ! Hash::check($password, $user->password)
            ) {
                throw $this->invalidCredentials();
            }

            $token = $this->createSessionToken($user, $session);

            return [
                'user' => $user,
                'token' => $token,
            ];
        });
    }

    /**
     * @param  array{accepted_terms: bool, acknowledged_privacy: bool}  $data
     * @return array{user: User, token: string, is_new_user: bool}
     */
    public function loginWithGoogle(string $idToken, array $data): array
    {
        if (! config('latch.oauth.google.enabled')) {
            throw new ApiException(
                'OAUTH_NOT_CONFIGURED',
                'Google sign-in is not available.',
                503,
            );
        }

        $identity = $this->oauthTokens->verifyGoogle($idToken);

        return DB::transaction(function () use ($identity, $data): array {
            $linked = OAuthIdentity::query()
                ->where('provider', $identity->provider)
                ->where('provider_subject', $identity->providerSubject)
                ->lockForUpdate()
                ->first();

            if ($linked) {
                $user = User::query()->lockForUpdate()->findOrFail($linked->user_id);
                $this->refreshIdentity($linked, $identity);

                return $this->oauthResult($user, false, $data);
            }

            $user = User::query()
                ->where('email', $identity->email)
                ->lockForUpdate()
                ->first();
            $isNewUser = $user === null;

            if ($user && ! $user->hasVerifiedEmail()) {
                throw new ApiException(
                    'OAUTH_ACCOUNT_CONFLICT',
                    'An unverified account already uses this email. Verify it or reset its password before linking Google.',
                    409,
                );
            }

            if ($user && $user->oauthIdentities()->where('provider', 'google')->exists()) {
                throw new ApiException(
                    'OAUTH_ACCOUNT_CONFLICT',
                    'This account is already linked to another Google identity.',
                    409,
                );
            }

            if (! $user) {
                $user = User::query()->create([
                    'name' => $identity->name,
                    'email' => $identity->email,
                    'password' => Hash::make(Str::random(64)),
                    'password_set_at' => null,
                    'notifications_enabled' => true,
                    'terms_accepted_at' => now(),
                    'terms_version' => config('latch.legal.terms_version'),
                    'privacy_acknowledged_at' => now(),
                    'privacy_version' => config('latch.legal.privacy_version'),
                ]);
                $user->forceFill(['email_verified_at' => now()])->save();
            }

            $user->oauthIdentities()->create([
                'provider' => $identity->provider,
                'provider_subject' => $identity->providerSubject,
                'firebase_uid' => $identity->firebaseUid,
                'provider_email' => $identity->email,
                'last_login_at' => now(),
            ]);

            return $this->oauthResult($user, $isNewUser, $data);
        }, 3);
    }

    public function verifyRecentGoogleIdentity(
        User $user,
        string $idToken,
        int $maxAgeSeconds = 300,
    ): void {
        $identity = $this->oauthTokens->verifyGoogle($idToken);
        $isLinked = $user->oauthIdentities()
            ->where('provider', 'google')
            ->where('provider_subject', $identity->providerSubject)
            ->where('firebase_uid', $identity->firebaseUid)
            ->exists();

        if (
            ! $isLinked
            || $identity->authenticatedAt < now()->subSeconds($maxAgeSeconds)->timestamp
        ) {
            throw new ApiException(
                'OAUTH_REAUTHENTICATION_REQUIRED',
                'Please sign in with Google again to continue.',
                422,
                ['oauth_id_token' => ['Recent Google authentication is required.']],
            );
        }
    }

    public function logout(User $user, mixed $currentToken): void
    {
        DB::transaction(function () use ($user, $currentToken): void {
            User::query()->lockForUpdate()->findOrFail($user->id);

            if ($currentToken instanceof PersonalAccessToken) {
                PersonalAccessToken::query()
                    ->whereKey($currentToken->id)
                    ->where('tokenable_type', $user->getMorphClass())
                    ->where('tokenable_id', $user->id)
                    ->delete();
            }
        });
    }

    public function logoutAll(User $user): void
    {
        DB::transaction(function () use ($user): void {
            $locked = User::query()->lockForUpdate()->findOrFail($user->id);
            $locked->pushTokens()->delete();
            $locked->tokens()->delete();
        });
    }

    public function requireActiveToken(User $user, mixed $currentToken): PersonalAccessToken
    {
        if (! $currentToken instanceof PersonalAccessToken) {
            throw new ApiException('AUTH_UNAUTHENTICATED', 'Unauthenticated.', 401);
        }

        $token = PersonalAccessToken::query()
            ->whereKey($currentToken->id)
            ->where('tokenable_type', $user->getMorphClass())
            ->where('tokenable_id', $user->id)
            ->first();

        $expiration = (int) config('sanctum.expiration');
        $expiredByAge = $expiration > 0
            && $token?->created_at?->addMinutes($expiration)->isPast();
        $expiredExplicitly = $token?->expires_at?->isPast() ?? false;

        if (! $token || $expiredByAge || $expiredExplicitly) {
            throw new ApiException('AUTH_UNAUTHENTICATED', 'Unauthenticated.', 401);
        }

        return $token;
    }

    private function invalidCredentials(): ApiException
    {
        return new ApiException(
            'AUTH_INVALID_CREDENTIALS',
            'The provided credentials are invalid.',
            401,
        );
    }

    private function createSessionToken(User $user, array $session): string
    {
        $created = $user->createToken('flutter');
        $created->accessToken->forceFill([
            'device_name' => $session['device_name'] ?? null,
            'platform' => $session['platform'] ?? null,
        ])->save();

        return $created->plainTextToken;
    }

    private function refreshIdentity(
        OAuthIdentity $linked,
        OAuthIdentityData $identity,
    ): void {
        if ($linked->firebase_uid !== $identity->firebaseUid) {
            throw new ApiException(
                'OAUTH_INVALID_TOKEN',
                'The Google sign-in credential is invalid or expired.',
                401,
            );
        }

        $linked->forceFill([
            'provider_email' => $identity->email,
            'last_login_at' => now(),
        ])->save();
    }

    /**
     * @return array{user: User, token: string, is_new_user: bool}
     */
    private function oauthResult(User $user, bool $isNewUser, array $session = []): array
    {
        return [
            'user' => $user->refresh(),
            'token' => $this->createSessionToken($user, $session),
            'is_new_user' => $isNewUser,
        ];
    }
}
