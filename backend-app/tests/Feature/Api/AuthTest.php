<?php

use App\Models\User;
use App\Notifications\ResetPasswordNotification;
use App\Notifications\SetPasswordNotification;
use App\Notifications\VerifyEmailNotification;
use Illuminate\Auth\Events\Verified;
use Illuminate\Support\Facades\Event;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Notification;
use Illuminate\Support\Facades\URL;

it('returns the API authentication envelope without an Accept header', function () {
    $this->get('/api/v1/auth/me')
        ->assertUnauthorized()
        ->assertJsonPath('code', 'AUTH_UNAUTHENTICATED');
});

it('rate limits repeated login attempts and reports when another attempt is allowed', function () {
    $credentials = [
        'email' => 'rate-limit-'.str()->uuid().'@example.com',
        'password' => 'WrongPassword!123',
    ];

    foreach (range(1, 5) as $attempt) {
        $this->postJson('/api/v1/auth/login', $credentials)
            ->assertUnauthorized();
    }

    $response = $this->postJson('/api/v1/auth/login', $credentials)
        ->assertTooManyRequests()
        ->assertJsonPath('success', false)
        ->assertJsonPath('code', 'RATE_LIMITED')
        ->assertJsonPath(
            'message',
            'Too many sign-in attempts. Try again shortly.',
        )
        ->assertJsonStructure(['retry_after_seconds']);

    $retryAfter = (int) $response->headers->get('Retry-After');

    expect($retryAfter)->toBeGreaterThan(0)
        ->and($response->json('retry_after_seconds'))->toBe($retryAfter);
});

it('registers, restores, and logs out an API user', function () {
    $response = $this->postJson('/api/v1/auth/register', [
        'name' => 'Sample User',
        'email' => 'USER@EXAMPLE.COM',
        'password' => 'Password!123',
        'password_confirmation' => 'Password!123',
        'accepted_terms' => true,
        'acknowledged_privacy' => true,
    ]);

    $response
        ->assertCreated()
        ->assertJsonPath('success', true)
        ->assertJsonPath('data.user.email', 'user@example.com')
        ->assertJsonPath('data.user.notifications_enabled', true)
        ->assertJsonPath('data.token_type', 'Bearer')
        ->assertJsonMissingPath('data.user.password');

    $token = $response->json('data.token');

    $this->withToken($token)
        ->getJson('/api/v1/auth/me')
        ->assertOk()
        ->assertJsonPath('data.email', 'user@example.com');

    $this->withToken($token)
        ->postJson('/api/v1/auth/logout')
        ->assertNoContent();

    expect(User::query()->firstOrFail()->tokens()->count())->toBe(0);
    $this->app['auth']->forgetGuards();

    $this->withToken($token)
        ->getJson('/api/v1/auth/me')
        ->assertUnauthorized();
});

it('changes the password and revokes every other token', function () {
    $user = User::factory()->create(['password' => Hash::make('OldPassword!1')]);
    $current = $user->createToken('current')->plainTextToken;
    $other = $user->createToken('other')->plainTextToken;

    $this->withToken($current)
        ->putJson('/api/v1/profile/password', [
            'current_password' => 'OldPassword!1',
            'password' => 'NewPassword!2',
            'password_confirmation' => 'NewPassword!2',
        ])
        ->assertOk()
        ->assertJsonPath('data.sessions_revoked', 1);

    expect(Hash::check('NewPassword!2', $user->fresh()->password))->toBeTrue();

    $this->app['auth']->forgetGuards();
    $this->withToken($current)->getJson('/api/v1/auth/me')->assertOk();
    $this->app['auth']->forgetGuards();
    $this->withToken($other)->getJson('/api/v1/auth/me')->assertUnauthorized();
});

it('returns field keyed errors for a wrong current password', function () {
    $user = User::factory()->create(['password' => Hash::make('OldPassword!1')]);
    $token = $user->createToken('current')->plainTextToken;

    $this->withToken($token)
        ->putJson('/api/v1/profile/password', [
            'current_password' => 'wrong-password',
            'password' => 'NewPassword!2',
            'password_confirmation' => 'NewPassword!2',
        ])
        ->assertUnprocessable()
        ->assertJsonPath('code', 'AUTH_PASSWORD_INCORRECT')
        ->assertJsonStructure(['errors' => ['current_password']]);
});

it('rejects bcrypt passwords over 72 bytes', function () {
    $password = str_repeat('a', 73);

    $this->postJson('/api/v1/auth/register', [
        'name' => 'Sample User',
        'email' => 'user@example.com',
        'password' => $password,
        'password_confirmation' => $password,
    ])
        ->assertUnprocessable()
        ->assertJsonPath('code', 'VALIDATION_ERROR')
        ->assertJsonStructure(['errors' => ['password']]);
});

it('rejects weak passwords and reports mismatched confirmation concisely', function () {
    $this->postJson('/api/v1/auth/register', [
        'name' => 'Sample User',
        'email' => 'weak@example.com',
        'password' => '12345678',
        'password_confirmation' => '12345678',
    ])
        ->assertUnprocessable()
        ->assertJsonStructure(['errors' => ['password']]);

    $this->postJson('/api/v1/auth/register', [
        'name' => 'Sample User',
        'email' => 'mismatch@example.com',
        'password' => 'Password!123',
        'password_confirmation' => 'Different!123',
    ])
        ->assertUnprocessable()
        ->assertJsonPath('errors.password.0', 'Passwords do not match.');
});

it('rejects undocumented request fields instead of silently ignoring them', function () {
    $this->postJson('/api/v1/auth/register', [
        'name' => 'Sample User',
        'email' => 'user@example.com',
        'password' => 'Password!123',
        'password_confirmation' => 'Password!123',
        'role' => 'admin',
        'unexpected_field' => true,
    ])
        ->assertUnprocessable()
        ->assertJsonPath('code', 'VALIDATION_ERROR')
        ->assertJsonStructure(['errors' => ['role', 'unexpected_field']]);
});

it('registers unverified users, sends verification, and gates tracker data', function () {
    Notification::fake();
    Event::fake([Verified::class]);

    $response = $this->postJson('/api/v1/auth/register', [
        'name' => 'Verify User',
        'email' => 'verify@example.com',
        'password' => 'Password!123',
        'password_confirmation' => 'Password!123',
        'accepted_terms' => true,
        'acknowledged_privacy' => true,
    ])->assertCreated()
        ->assertJsonPath('data.user.email_verified', false);

    $user = User::query()->where('email', 'verify@example.com')->firstOrFail();
    $token = $response->json('data.token');
    Notification::assertSentTo($user, VerifyEmailNotification::class);

    $this->withToken($token)
        ->getJson('/api/v1/items')
        ->assertForbidden()
        ->assertJsonPath('code', 'EMAIL_NOT_VERIFIED');

    $url = URL::temporarySignedRoute(
        'verification.verify',
        now()->addMinutes(30),
        ['id' => $user->id, 'hash' => sha1($user->email)],
    );

    $this->get($url)
        ->assertOk()
        ->assertSee('Email verified')
        ->assertSee('latch://email-verified?status=success', false);

    expect($user->fresh()->hasVerifiedEmail())->toBeTrue();
    Event::assertDispatched(Verified::class);

    $this->app['auth']->forgetGuards();
    $this->withToken($token)->getJson('/api/v1/items')->assertOk();
});

it('shows a useful browser result for an invalid verification link', function () {
    $user = User::factory()->unverified()->create();

    $this->get(
        "/api/v1/auth/email/verify/{$user->id}/invalid"
        .'?expires='.now()->addMinutes(30)->timestamp
        .'&signature=invalid',
    )
        ->assertUnprocessable()
        ->assertSee('Verification link unavailable')
        ->assertSee('latch://email-verified?status=invalid', false);
});

it('resends verification without sending again after verification', function () {
    Notification::fake();
    $user = User::factory()->unverified()->create();
    $token = $user->createToken('verify')->plainTextToken;

    $this->withToken($token)
        ->postJson('/api/v1/auth/email/verification-notification')
        ->assertNoContent();
    Notification::assertSentToTimes($user, VerifyEmailNotification::class, 1);

    $user->markEmailAsVerified();
    $this->app['auth']->forgetGuards();
    $this->withToken($token)
        ->postJson('/api/v1/auth/email/verification-notification')
        ->assertNoContent();
    Notification::assertSentToTimes($user, VerifyEmailNotification::class, 1);
});

it('sends a non-enumerating password reset and revokes sessions after reset', function () {
    Notification::fake();
    $user = User::factory()->create([
        'email' => 'reset@example.com',
        'password' => Hash::make('OldPassword!1'),
    ]);
    $user->createToken('phone');
    $user->pushTokens()->create([
        'token' => str_repeat('fcm-token-', 4),
        'token_hash' => hash('sha256', str_repeat('fcm-token-', 4)),
        'platform' => 'android',
        'last_seen_at' => now(),
    ]);

    $this->postJson('/api/v1/auth/forgot-password', [
        'email' => 'missing@example.com',
    ])->assertAccepted();

    $this->postJson('/api/v1/auth/forgot-password', [
        'email' => 'RESET@EXAMPLE.COM',
    ])->assertAccepted();

    $resetToken = null;
    Notification::assertSentTo(
        $user,
        ResetPasswordNotification::class,
        function (ResetPasswordNotification $notification) use (&$resetToken): bool {
            $resetToken = $notification->token;

            return true;
        },
    );

    $this->postJson('/api/v1/auth/reset-password', [
        'email' => 'reset@example.com',
        'token' => $resetToken,
        'password' => 'NewPassword!2',
        'password_confirmation' => 'NewPassword!2',
    ])->assertNoContent();

    expect(Hash::check('NewPassword!2', $user->fresh()->password))->toBeTrue()
        ->and($user->tokens()->count())->toBe(0)
        ->and($user->pushTokens()->count())->toBe(0);
});

it('sends a password setup email for an authenticated passwordless account', function () {
    Notification::fake();
    $user = User::factory()->create([
        'email' => 'google@example.com',
        'password_set_at' => null,
    ]);
    $token = $user->createToken('phone')->plainTextToken;

    $this->withToken($token)
        ->postJson('/api/v1/auth/password/setup-link')
        ->assertAccepted()
        ->assertJsonPath('message', 'A password setup link has been sent.');

    Notification::assertSentTo(
        $user,
        SetPasswordNotification::class,
        function (SetPasswordNotification $notification) use ($user): bool {
            $mail = $notification->toMail($user);

            expect($mail->subject)->toBe('Set Your LATCH Password')
                ->and($mail->actionText)->toBe('Set Password')
                ->and($mail->actionUrl)->toContain('mode=setup');

            return true;
        },
    );
});

it('does not send a setup email when a password already exists', function () {
    Notification::fake();
    $user = User::factory()->create([
        'password_set_at' => now(),
    ]);
    $token = $user->createToken('phone')->plainTextToken;

    $this->withToken($token)
        ->postJson('/api/v1/auth/password/setup-link')
        ->assertConflict()
        ->assertJsonPath('code', 'AUTH_PASSWORD_ALREADY_SET');

    Notification::assertNothingSent();
});

it('lists signed-in devices and revokes only the selected session', function () {
    $user = User::factory()->create([
        'email' => 'devices@example.com',
        'password' => Hash::make('Password!123'),
        'password_set_at' => now(),
    ]);

    $login = $this->postJson('/api/v1/auth/login', [
        'email' => 'devices@example.com',
        'password' => 'Password!123',
        'device_name' => 'iPhone or iPad',
        'platform' => 'ios',
    ])->assertOk();
    $currentToken = $login->json('data.token');
    $other = $user->createToken('flutter');
    $other->accessToken->forceFill([
        'device_name' => 'Android device',
        'platform' => 'android',
    ])->save();

    $this->withToken($currentToken)
        ->patchJson('/api/v1/auth/sessions/current', [
            'device_name' => 'Apple iPhone 16 Pro',
            'platform' => 'ios',
        ])
        ->assertOk();

    $sessions = $this->withToken($currentToken)
        ->getJson('/api/v1/auth/sessions')
        ->assertOk()
        ->assertJsonCount(2, 'data')
        ->assertJsonFragment([
            'device_name' => 'Apple iPhone 16 Pro',
            'platform' => 'ios',
            'is_current' => true,
        ]);

    $otherId = collect($sessions->json('data'))
        ->firstWhere('device_name', 'Android device')['id'];

    $this->withToken($currentToken)
        ->deleteJson("/api/v1/auth/sessions/{$otherId}")
        ->assertOk()
        ->assertJsonPath('data.was_current', false);

    expect($user->tokens()->count())->toBe(1)
        ->and($user->tokens()->first()->device_name)->toBe('Apple iPhone 16 Pro');
});
