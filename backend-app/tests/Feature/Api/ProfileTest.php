<?php

use App\Enums\ActivitySource;
use App\Enums\DeviceActivityType;
use App\Enums\NotificationType;
use App\Jobs\DeleteStoredProfilePhoto;
use App\Models\Device;
use App\Models\DeviceActivityLog;
use App\Models\DevicePosition;
use App\Models\Geofence;
use App\Models\Notification;
use App\Models\TrackerPositionReceipt;
use App\Models\User;
use Illuminate\Http\UploadedFile;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Queue;
use Illuminate\Support\Facades\Storage;

it('updates protected profile fields and notification settings', function () {
    $user = User::factory()->create([
        'email' => 'old@example.com',
        'password' => Hash::make('current-password'),
    ]);
    $token = $user->createToken('profile')->plainTextToken;

    $this->withToken($token)
        ->patchJson('/api/v1/profile', [
            'name' => 'Updated Name',
            'email' => 'old@example.com',
        ])
        ->assertOk()
        ->assertJsonPath('data.name', 'Updated Name');

    $this->withToken($token)
        ->patchJson('/api/v1/profile', [
            'name' => 'Updated Name',
            'email' => 'new@example.com',
        ])
        ->assertUnprocessable()
        ->assertJsonStructure(['errors' => ['current_password']]);

    $this->withToken($token)
        ->patchJson('/api/v1/profile', [
            'name' => 'Updated Name',
            'email' => 'new@example.com',
            'current_password' => 'current-password',
        ])
        ->assertOk()
        ->assertJsonPath('data.email', 'new@example.com')
        ->assertJsonPath('data.email_verified', false);

    $user->fresh()->markEmailAsVerified();
    $this->app['auth']->forgetGuards();

    $this->withToken($token)
        ->patchJson('/api/v1/settings', ['notifications_enabled' => false])
        ->assertOk()
        ->assertJsonPath('data.notifications_enabled', false);
});

it('uploads, replaces, and removes managed profile photos', function () {
    Storage::fake('public');
    Queue::fake();
    config()->set('filesystems.disks.public.url', 'http://localhost:8000/storage');

    $user = User::factory()->create();
    $token = $user->createToken('profile')->plainTextToken;

    $png = base64_decode(
        'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=',
        true,
    );

    $first = $this->withToken($token)
        ->post('http://192.168.100.22/api/v1/profile/photo', [
            'photo' => UploadedFile::fake()->createWithContent('first.png', $png),
        ], ['Accept' => 'application/json']);

    $first
        ->assertOk()
        ->assertJsonPath('success', true);

    $firstPath = $user->fresh()->profile_photo_path;

    expect($firstPath)->not->toBeNull()
        ->and($first->json('data.profile_photo_url'))
        ->toBe("http://192.168.100.22/storage/{$firstPath}");
    Storage::disk('public')->assertExists($firstPath);

    $this->withToken($token)
        ->post('/api/v1/profile/photo', [
            'photo' => UploadedFile::fake()->createWithContent('second.png', $png),
        ], ['Accept' => 'application/json'])
        ->assertOk();

    $secondPath = $user->fresh()->profile_photo_path;

    expect($secondPath)->not->toBe($firstPath);
    Queue::assertPushed(
        DeleteStoredProfilePhoto::class,
        fn (DeleteStoredProfilePhoto $job) => $job->path === $firstPath,
    );

    $this->withToken($token)
        ->deleteJson('/api/v1/profile/photo')
        ->assertOk()
        ->assertJsonPath('data.profile_photo_url', null);

    Queue::assertPushed(
        DeleteStoredProfilePhoto::class,
        fn (DeleteStoredProfilePhoto $job) => $job->path === $secondPath,
    );
});

it('permanently deletes an account while preserving the master tracker mapping', function () {
    Queue::fake();

    $user = User::factory()->create([
        'password' => Hash::make('current-password'),
        'profile_photo_path' => 'profile-photos/1/photo.jpg',
    ]);
    $token = $user->createToken('account')->plainTextToken;
    $user->createToken('other');
    $device = Device::factory()->create([
        'user_id' => $user->id,
        'item_name' => 'Account Item',
        'claim_version' => 3,
        'claimed_at' => now()->subHour(),
        'tracker_cursor_at' => now()->subMinute(),
        'last_latitude' => 15.1,
        'last_longitude' => 120.5,
        'last_position_at' => now()->subMinute(),
    ]);

    Geofence::query()->create([
        'device_id' => $device->id,
        'name' => 'Home',
        'center_latitude' => 15.1,
        'center_longitude' => 120.5,
        'radius_meters' => 100,
    ]);
    Notification::query()->create([
        'user_id' => $user->id,
        'device_id' => $device->id,
        'type' => NotificationType::DeviceOffline,
        'title' => 'Offline',
        'message' => 'Item is offline.',
        'event_key' => 'account-delete-notification',
    ]);
    TrackerPositionReceipt::query()->create([
        'user_id' => $user->id,
        'device_id' => $device->id,
        'claim_version' => 3,
        'provider_position_id' => 1,
        'recorded_at' => now()->subMinute(),
        'created_at' => now(),
    ]);
    DevicePosition::query()->create([
        'user_id' => $user->id,
        'device_id' => $device->id,
        'claim_version' => 3,
        'provider_position_id' => 1,
        'latitude' => 15.1,
        'longitude' => 120.5,
        'recorded_at' => now()->subMinute(),
        'created_at' => now(),
    ]);
    DeviceActivityLog::query()->create([
        'user_id' => $user->id,
        'device_id' => $device->id,
        'claim_version' => 3,
        'actor_user_id' => $user->id,
        'event_type' => DeviceActivityType::ItemClaimed,
        'source' => ActivitySource::User,
        'title' => 'Claimed',
        'description' => 'Item claimed.',
        'event_key' => 'account-delete-activity',
        'device_uid_snapshot' => $device->device_uid,
        'item_name_snapshot' => $device->item_name,
        'occurred_at' => now(),
        'created_at' => now(),
    ]);

    $this->withToken($token)
        ->deleteJson('/api/v1/account', ['current_password' => 'wrong'])
        ->assertUnprocessable();

    expect(User::query()->whereKey($user->id)->exists())->toBeTrue();

    $this->withToken($token)
        ->deleteJson('/api/v1/account', ['current_password' => 'current-password'])
        ->assertNoContent();

    $released = $device->fresh();

    expect(User::query()->whereKey($user->id)->exists())->toBeFalse()
        ->and($released->user_id)->toBeNull()
        ->and($released->item_name)->toBeNull()
        ->and($released->claim_version)->toBe(4)
        ->and($released->tracker_unique_id)->toBe($device->tracker_unique_id)
        ->and($released->tracker_device_id)->toBe($device->tracker_device_id)
        ->and($released->last_latitude)->toBeNull()
        ->and(Geofence::query()->count())->toBe(0)
        ->and(Notification::query()->count())->toBe(0)
        ->and(TrackerPositionReceipt::query()->count())->toBe(0)
        ->and(DevicePosition::query()->count())->toBe(0)
        ->and(DeviceActivityLog::query()->count())->toBe(0);

    Queue::assertPushed(
        DeleteStoredProfilePhoto::class,
        fn (DeleteStoredProfilePhoto $job) => $job->path === 'profile-photos/1/photo.jpg',
    );
});
