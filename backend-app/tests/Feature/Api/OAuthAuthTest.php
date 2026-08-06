<?php

use App\Contracts\OAuthTokenVerifierInterface;
use App\Data\OAuthIdentityData;
use App\Exceptions\ApiException;
use App\Jobs\DeleteFirebaseAuthUser;
use App\Models\User;
use Illuminate\Support\Facades\Queue;
use Mockery\MockInterface;

beforeEach(function () {
    config()->set('latch.oauth.google.enabled', true);
});

function fakeGoogleIdentity(
    string $email = 'oauth@example.com',
    string $subject = 'google-subject-1',
    string $firebaseUid = 'firebase-uid-1',
): OAuthIdentityData {
    return new OAuthIdentityData(
        provider: 'google',
        providerSubject: $subject,
        firebaseUid: $firebaseUid,
        email: $email,
        name: 'OAuth User',
        authenticatedAt: now()->timestamp,
    );
}

function mockGoogleVerifier(OAuthIdentityData $identity): void
{
    test()->mock(
        OAuthTokenVerifierInterface::class,
        function (MockInterface $mock) use ($identity): void {
            $mock->shouldReceive('verifyGoogle')->andReturn($identity);
        },
    );
}

it('creates a verified passwordless account from a valid Google identity', function () {
    mockGoogleVerifier(fakeGoogleIdentity());

    $response = $this->postJson('/api/v1/auth/oauth/google', [
        'id_token' => 'valid-firebase-token',
        'accepted_terms' => true,
        'acknowledged_privacy' => true,
    ]);

    $response
        ->assertOk()
        ->assertJsonPath('data.user.email', 'oauth@example.com')
        ->assertJsonPath('data.user.email_verified', true)
        ->assertJsonPath('data.user.has_password', false)
        ->assertJsonPath('data.user.oauth_providers.0', 'google')
        ->assertJsonPath('data.is_new_user', true)
        ->assertJsonPath('data.token_type', 'Bearer');

    $user = User::query()->where('email', 'oauth@example.com')->firstOrFail();
    expect($user->password_set_at)->toBeNull()
        ->and($user->hasVerifiedEmail())->toBeTrue()
        ->and($user->oauthIdentities()->count())->toBe(1);
});

it('links Google only to an existing verified account and reuses it', function () {
    $user = User::factory()->create(['email' => 'linked@example.com']);
    mockGoogleVerifier(fakeGoogleIdentity(email: 'linked@example.com'));

    $this->postJson('/api/v1/auth/oauth/google', [
        'id_token' => 'valid-firebase-token',
        'accepted_terms' => true,
        'acknowledged_privacy' => true,
    ])
        ->assertOk()
        ->assertJsonPath('data.user.id', $user->id)
        ->assertJsonPath('data.is_new_user', false);

    $this->postJson('/api/v1/auth/oauth/google', [
        'id_token' => 'valid-firebase-token',
        'accepted_terms' => true,
        'acknowledged_privacy' => true,
    ])->assertOk();

    expect(User::query()->count())->toBe(1)
        ->and($user->oauthIdentities()->count())->toBe(1);
});

it('does not auto-link an unverified local account with the same email', function () {
    User::factory()->unverified()->create(['email' => 'conflict@example.com']);
    mockGoogleVerifier(fakeGoogleIdentity(email: 'conflict@example.com'));

    $this->postJson('/api/v1/auth/oauth/google', [
        'id_token' => 'valid-firebase-token',
        'accepted_terms' => true,
        'acknowledged_privacy' => true,
    ])
        ->assertStatus(409)
        ->assertJsonPath('code', 'OAUTH_ACCOUNT_CONFLICT');
});

it('rejects an invalid Google credential without creating an account', function () {
    $this->mock(
        OAuthTokenVerifierInterface::class,
        function (MockInterface $mock): void {
            $mock->shouldReceive('verifyGoogle')->andThrow(new ApiException(
                'OAUTH_INVALID_TOKEN',
                'The Google sign-in credential is invalid or expired.',
                401,
            ));
        },
    );

    $this->postJson('/api/v1/auth/oauth/google', [
        'id_token' => 'invalid',
        'accepted_terms' => true,
        'acknowledged_privacy' => true,
    ])
        ->assertUnauthorized()
        ->assertJsonPath('code', 'OAUTH_INVALID_TOKEN');

    expect(User::query()->count())->toBe(0);
});

it('reauthenticates an OAuth-only user before deletion and queues Firebase cleanup', function () {
    Queue::fake();
    $identity = fakeGoogleIdentity();
    mockGoogleVerifier($identity);

    $login = $this->postJson('/api/v1/auth/oauth/google', [
        'id_token' => 'valid-firebase-token',
        'accepted_terms' => true,
        'acknowledged_privacy' => true,
    ])->assertOk();

    $this->withToken($login->json('data.token'))
        ->deleteJson('/api/v1/account', ['oauth_id_token' => 'fresh-firebase-token'])
        ->assertNoContent();

    expect(User::query()->count())->toBe(0);
    Queue::assertPushed(
        DeleteFirebaseAuthUser::class,
        fn (DeleteFirebaseAuthUser $job): bool => $job->firebaseUid === $identity->firebaseUid,
    );
});
