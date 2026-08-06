<?php

use App\Contracts\PushProviderInterface;
use App\Enums\NotificationType;
use App\Enums\PushDeliveryResult;
use App\Jobs\SendPushNotification;
use App\Models\Device;
use App\Models\Notification;
use App\Models\PushToken;
use App\Models\User;
use App\Services\NotificationService;
use Illuminate\Support\Facades\Queue;

it('stores encrypted push tokens, moves them between accounts, and removes them', function () {
    $first = User::factory()->create();
    $second = User::factory()->create();
    $firstToken = $first->createToken('phone')->plainTextToken;
    $secondToken = $second->createToken('phone')->plainTextToken;
    $fcmToken = str_repeat('fcm-token-', 20);

    $this->withToken($firstToken)
        ->postJson('/api/v1/push-tokens', [
            'token' => $fcmToken,
            'platform' => 'android',
            'device_name' => 'Pixel test device',
            'app_version' => '1.0.0+1',
        ])
        ->assertNoContent();

    $stored = PushToken::query()->firstOrFail();
    expect($stored->user_id)->toBe($first->id)
        ->and($stored->token)->toBe($fcmToken)
        ->and($stored->token_hash)->toBe(hash('sha256', $fcmToken))
        ->and($stored->getRawOriginal('token'))->not->toBe($fcmToken);

    $this->app['auth']->forgetGuards();
    $this->withToken($secondToken)
        ->postJson('/api/v1/push-tokens', [
            'token' => $fcmToken,
            'platform' => 'android',
        ])
        ->assertNoContent();

    expect(PushToken::query()->count())->toBe(1)
        ->and($stored->fresh()->user_id)->toBe($second->id);

    $this->withToken($secondToken)
        ->deleteJson('/api/v1/push-tokens', ['token' => $fcmToken])
        ->assertNoContent();

    expect(PushToken::query()->count())->toBe(0);
});

it('supports granular notification preferences and queues push once', function () {
    Queue::fake();
    $user = User::factory()->create();
    $token = $user->createToken('settings')->plainTextToken;
    $device = Device::factory()->create([
        'user_id' => $user->id,
        'item_name' => 'Backpack',
        'claimed_at' => now(),
    ]);

    $this->withToken($token)
        ->patchJson('/api/v1/settings', [
            'notifications_enabled' => true,
            'notify_geofence_events' => false,
            'notify_battery_events' => true,
            'notify_device_status_events' => true,
        ])
        ->assertOk()
        ->assertJsonPath('data.notification_preferences.geofence_events', false)
        ->assertJsonPath('data.notification_preferences.battery_events', true);

    $service = app(NotificationService::class);
    expect($service->create(
        $user->fresh(),
        $device,
        NotificationType::GeofenceEnter,
        'Entered',
        'Entered home.',
        'push-geofence-disabled',
    ))->toBeNull();

    $notification = $service->create(
        $user->fresh(),
        $device,
        NotificationType::BatteryLow,
        'Battery low',
        'Charge your tracker.',
        'push-battery-low',
    );

    expect($notification)->not->toBeNull();
    Queue::assertPushed(
        SendPushNotification::class,
        fn (SendPushNotification $job) => $job->notificationId === $notification->id,
    );

    $service->create(
        $user->fresh(),
        $device,
        NotificationType::BatteryLow,
        'Battery low',
        'Charge your tracker.',
        'push-battery-low',
    );
    Queue::assertPushed(SendPushNotification::class, 1);
});

it('deletes an invalid FCM registration token after delivery rejection', function () {
    $user = User::factory()->create();
    $device = Device::factory()->create([
        'user_id' => $user->id,
        'item_name' => 'Keys',
        'claimed_at' => now(),
    ]);
    $pushToken = PushToken::query()->create([
        'user_id' => $user->id,
        'token_hash' => hash('sha256', 'invalid-fcm-token'),
        'token' => 'invalid-fcm-token',
        'platform' => 'android',
        'last_seen_at' => now(),
    ]);
    $notification = Notification::query()->create([
        'user_id' => $user->id,
        'device_id' => $device->id,
        'type' => NotificationType::DeviceOffline,
        'title' => 'Offline',
        'message' => 'Keys stopped communicating.',
        'event_key' => 'invalid-push-token',
    ]);

    app()->instance(PushProviderInterface::class, new class implements PushProviderInterface
    {
        public function send(PushToken $token, Notification $notification): PushDeliveryResult
        {
            return PushDeliveryResult::InvalidToken;
        }
    });

    SendPushNotification::dispatchSync($notification->id);

    expect($pushToken->fresh())->toBeNull();
});
