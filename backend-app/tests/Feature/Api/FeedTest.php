<?php

use App\Enums\ActivitySource;
use App\Enums\DeviceActivityType;
use App\Enums\NotificationType;
use App\Models\Device;
use App\Models\DeviceActivityLog;
use App\Models\DevicePosition;
use App\Models\Notification;
use App\Models\User;
use Carbon\CarbonImmutable;

it('paginates and marks notifications without crossing user boundaries', function () {
    CarbonImmutable::setTestNow('2026-07-26T08:00:00Z');

    $user = User::factory()->create();
    $other = User::factory()->create();
    $device = Device::factory()->create([
        'user_id' => $user->id,
        'item_name' => 'Feed Item',
        'claim_version' => 1,
        'claimed_at' => now()->subHour(),
    ]);

    foreach ([1, 2, 3] as $minute) {
        Notification::query()->create([
            'user_id' => $user->id,
            'device_id' => $device->id,
            'type' => NotificationType::DeviceOffline,
            'title' => "Notification {$minute}",
            'message' => "Message {$minute}",
            'event_key' => "notification-feed-{$minute}",
            'created_at' => now()->subMinutes($minute),
            'updated_at' => now()->subMinutes($minute),
        ]);
    }

    $token = $user->createToken('feed')->plainTextToken;
    $first = $this->withToken($token)
        ->getJson('/api/v1/notifications?per_page=2&read_state=unread');

    $first
        ->assertOk()
        ->assertJsonCount(2, 'data')
        ->assertJsonPath('data.0.title', 'Notification 1')
        ->assertJsonPath('meta.unread_count', 3)
        ->assertJsonPath('data.0.related_item.id', $device->id)
        ->assertJsonMissingPath('data.0.event_key');

    $cursor = $first->json('meta.next_cursor');
    $second = $this->withToken($token)
        ->getJson('/api/v1/notifications?'.http_build_query(['cursor' => $cursor]));

    $second
        ->assertOk()
        ->assertJsonCount(1, 'data')
        ->assertJsonPath('data.0.title', 'Notification 3');

    $this->withToken($token)
        ->getJson('/api/v1/notifications?'.http_build_query([
            'cursor' => $cursor,
            'per_page' => 1,
        ]))
        ->assertUnprocessable()
        ->assertJsonPath('code', 'VALIDATION_ERROR');

    $this->app['auth']->forgetGuards();
    $this->withToken($other->createToken('feed')->plainTextToken)
        ->getJson('/api/v1/notifications?'.http_build_query(['cursor' => $cursor]))
        ->assertNotFound();

    $this->app['auth']->forgetGuards();
    $notificationId = $first->json('data.0.id');

    $this->withToken($token)
        ->patchJson("/api/v1/notifications/{$notificationId}/read")
        ->assertOk()
        ->assertJsonPath('data.is_read', true);
    $this->withToken($token)
        ->patchJson("/api/v1/notifications/{$notificationId}/read")
        ->assertOk();
    $this->app['auth']->forgetGuards();
    $this->withToken($other->createToken('other-feed')->plainTextToken)
        ->patchJson("/api/v1/notifications/{$notificationId}/read")
        ->assertNotFound();

    $this->withToken($other->createToken('delete-other-feed')->plainTextToken)
        ->deleteJson("/api/v1/notifications/{$notificationId}")
        ->assertNotFound();

    $this->app['auth']->forgetGuards();
    $this->withToken($token)
        ->deleteJson("/api/v1/notifications/{$notificationId}")
        ->assertNoContent();
    $this->withToken($token)
        ->deleteJson("/api/v1/notifications/{$notificationId}")
        ->assertNotFound();

    $this->withToken($token)
        ->patchJson('/api/v1/notifications/read-all')
        ->assertOk()
        ->assertJsonPath('data.unread_count', 0);

    expect(Notification::query()->whereNull('read_at')->count())->toBe(0);

    CarbonImmutable::setTestNow();
});

it('binds real location-history cursors to the owner, item, epoch, and filters', function () {
    CarbonImmutable::setTestNow('2026-07-26T08:00:00Z');

    $user = User::factory()->create();
    $other = User::factory()->create();
    $device = Device::factory()->create([
        'user_id' => $user->id,
        'item_name' => 'History Item',
        'claim_version' => 4,
        'claimed_at' => now()->subHour(),
    ]);
    $otherDevice = Device::factory()->create([
        'user_id' => $other->id,
        'item_name' => 'Other Item',
        'claim_version' => 2,
        'claimed_at' => now()->subHour(),
    ]);

    foreach ([3, 2, 1] as $minutesAgo) {
        DevicePosition::query()->create([
            'user_id' => $user->id,
            'device_id' => $device->id,
            'claim_version' => 4,
            'provider_position_id' => $minutesAgo,
            'latitude' => 15 + ($minutesAgo / 10),
            'longitude' => 120.5,
            'recorded_at' => now()->subMinutes($minutesAgo),
            'created_at' => now(),
        ]);
    }

    $token = $user->createToken('history')->plainTextToken;
    $first = $this->withToken($token)
        ->getJson("/api/v1/items/{$device->id}/location-history?per_page=2");

    $first
        ->assertOk()
        ->assertJsonCount(2, 'data')
        ->assertJsonPath('data.0.latitude', 15.3)
        ->assertJsonPath('data.1.latitude', 15.2);

    $cursor = $first->json('meta.next_cursor');

    $this->withToken($token)
        ->getJson("/api/v1/items/{$device->id}/location-history?".http_build_query([
            'cursor' => $cursor,
        ]))
        ->assertOk()
        ->assertJsonCount(1, 'data')
        ->assertJsonPath('data.0.latitude', 15.1);

    $this->withToken($token)
        ->getJson("/api/v1/items/{$device->id}/location-history?".http_build_query([
            'cursor' => $cursor,
            'per_page' => 3,
        ]))
        ->assertUnprocessable();

    $this->app['auth']->forgetGuards();
    $this->withToken($other->createToken('history')->plainTextToken)
        ->getJson("/api/v1/items/{$otherDevice->id}/location-history?".http_build_query([
            'cursor' => $cursor,
        ]))
        ->assertNotFound();

    CarbonImmutable::setTestNow();
});

it('keeps released activity globally while removing item navigation', function () {
    $user = User::factory()->create();
    $device = Device::factory()->create([
        'user_id' => $user->id,
        'item_name' => 'Activity Item',
        'claim_version' => 7,
        'claimed_at' => now()->subHour(),
    ]);

    DeviceActivityLog::query()->create([
        'user_id' => $user->id,
        'device_id' => $device->id,
        'claim_version' => 7,
        'actor_user_id' => $user->id,
        'event_type' => DeviceActivityType::ItemClaimed,
        'source' => ActivitySource::User,
        'title' => 'Item claimed',
        'description' => 'The item was claimed.',
        'event_key' => 'activity-feed-claim',
        'device_uid_snapshot' => $device->device_uid,
        'item_name_snapshot' => $device->item_name,
        'occurred_at' => now()->subMinute(),
        'created_at' => now()->subMinute(),
    ]);

    $device->forceFill([
        'user_id' => null,
        'item_name' => null,
        'claimed_at' => null,
        'claim_version' => 8,
    ])->save();

    $token = $user->createToken('activity')->plainTextToken;

    $this->withToken($token)
        ->getJson('/api/v1/activity')
        ->assertOk()
        ->assertJsonCount(1, 'data')
        ->assertJsonPath('data.0.device.item_id', null)
        ->assertJsonMissingPath('data.0.claim_version')
        ->assertJsonMissingPath('data.0.user_id');

    $this->withToken($token)
        ->getJson("/api/v1/items/{$device->id}/activity")
        ->assertNotFound();
});
