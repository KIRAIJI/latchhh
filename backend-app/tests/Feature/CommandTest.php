<?php

use App\Contracts\TrackerProviderInterface;
use App\Data\TrackerDeviceData;
use App\Enums\ActivitySource;
use App\Enums\DeviceActivityType;
use App\Enums\PushDeliveryResult;
use App\Jobs\SyncDevice;
use App\Models\Device;
use App\Models\DeviceActivityLog;
use App\Models\DevicePosition;
use App\Models\Notification;
use App\Models\PushToken;
use App\Models\TrackerPositionReceipt;
use App\Models\User;
use App\Providers\Push\FcmPushProvider;
use App\Providers\Tracker\FakeTrackerProvider;
use Carbon\CarbonImmutable;
use Illuminate\Console\Scheduling\Schedule;
use Illuminate\Support\Facades\Artisan;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Queue;

it('schedules tracker synchronization every ten seconds', function () {
    $event = collect(app(Schedule::class)->events())
        ->first(fn ($event) => $event->description === 'latch.sync');

    expect($event)->not->toBeNull()
        ->and($event->repeatSeconds)->toBe(10);
});

it('pre-registers only a provider-verified master device', function () {
    $provider = (new FakeTrackerProvider)->addDevice(new TrackerDeviceData(
        providerDeviceId: 123,
        uniqueId: 'CaseSensitiveTracker',
        connectionStatus: 'online',
        lastCommunicationAt: CarbonImmutable::now(),
    ));
    app()->instance(TrackerProviderInterface::class, $provider);

    $this->artisan('latch:register-device', [
        'device_uid' => 'latch-7k3m-p9q2',
        'tracker_unique_id' => 'CaseSensitiveTracker',
        '--tracker-device-id' => '123',
    ])
        ->expectsOutput('Registered LATCH-7K3M-P9Q2.')
        ->assertSuccessful();

    $device = Device::query()->firstOrFail();

    expect($device->device_uid)->toBe('LATCH-7K3M-P9Q2')
        ->and($device->tracker_unique_id)->toBe('CaseSensitiveTracker')
        ->and($device->tracker_device_id)->toBe(123)
        ->and($device->user_id)->toBeNull();

    $this->artisan('latch:register-device', [
        'device_uid' => 'LATCH-7K3M-P9Q2',
        'tracker_unique_id' => 'CaseSensitiveTracker',
    ])->assertFailed();

    expect(Device::query()->count())->toBe(1);
});

it('dispatches one claim-fenced database sync job per eligible item', function () {
    Queue::fake();

    $user = User::factory()->create();
    $eligible = Device::factory()->create([
        'user_id' => $user->id,
        'item_name' => 'Eligible Item',
        'claim_version' => 4,
        'claimed_at' => now()->subMinute(),
    ]);
    Device::factory()->create([
        'tracker_device_id' => null,
        'user_id' => $user->id,
        'item_name' => 'No Mapping',
        'claim_version' => 1,
        'claimed_at' => now(),
    ]);
    Device::factory()->create();

    $this->artisan('latch:dispatch-sync')
        ->expectsOutput('Dispatched 1 synchronization job(s).')
        ->assertSuccessful();

    Queue::assertPushed(
        SyncDevice::class,
        fn (SyncDevice $job) => $job->deviceId === $eligible->id
            && $job->userId === $user->id
            && $job->claimVersion === 4
            && $job->connection === 'database',
    );
});

it('sends an isolated FCM transport test without creating notification data', function () {
    $user = User::factory()->create();
    $pushToken = PushToken::query()->create([
        'user_id' => $user->id,
        'token_hash' => hash('sha256', 'test-fcm-token'),
        'token' => 'test-fcm-token',
        'platform' => 'android',
        'last_seen_at' => now(),
    ]);
    $provider = new class extends FcmPushProvider
    {
        public ?int $sentTokenId = null;

        public function sendTest(PushToken $token): PushDeliveryResult
        {
            $this->sentTokenId = $token->id;

            return PushDeliveryResult::Accepted;
        }
    };
    app()->instance(FcmPushProvider::class, $provider);

    $this->artisan('latch:test-push')
        ->expectsOutput('FCM accepted the transport test.')
        ->assertSuccessful();

    expect($provider->sentTokenId)->toBe($pushToken->id)
        ->and(Notification::query()->count())->toBe(0);
});

it('fails an FCM transport test safely when no registered token exists', function () {
    $this->artisan('latch:test-push')
        ->expectsOutput('No registered push token matched the request.')
        ->assertFailed();
});

it('prunes old receipts, real positions, and activity without removing retained rows', function () {
    CarbonImmutable::setTestNow('2026-07-26T08:00:00Z');

    $user = User::factory()->create();
    $device = Device::factory()->create([
        'user_id' => $user->id,
        'item_name' => 'Retention Item',
        'claim_version' => 1,
        'claimed_at' => now()->subDays(40),
    ]);

    foreach ([now()->subDays(31), now()->subDay()] as $index => $recordedAt) {
        TrackerPositionReceipt::query()->create([
            'user_id' => $user->id,
            'device_id' => $device->id,
            'claim_version' => 1,
            'provider_position_id' => $index + 1,
            'recorded_at' => $recordedAt,
            'created_at' => $index === 0 ? now()->subDays(2) : now(),
        ]);
        DevicePosition::query()->create([
            'user_id' => $user->id,
            'device_id' => $device->id,
            'claim_version' => 1,
            'provider_position_id' => $index + 1,
            'latitude' => 15.1,
            'longitude' => 120.5,
            'recorded_at' => $recordedAt,
            'created_at' => now(),
        ]);
    }

    foreach ([now()->subDays(366), now()->subDay()] as $index => $occurredAt) {
        DeviceActivityLog::query()->create([
            'user_id' => $user->id,
            'device_id' => $device->id,
            'claim_version' => 1,
            'event_type' => DeviceActivityType::ItemClaimed,
            'source' => ActivitySource::User,
            'title' => 'Retention event',
            'description' => 'Retention event.',
            'event_key' => "retention-event-{$index}",
            'device_uid_snapshot' => $device->device_uid,
            'item_name_snapshot' => $device->item_name,
            'occurred_at' => $occurredAt,
            'created_at' => $occurredAt,
        ]);
    }

    $this->artisan('latch:prune')->assertSuccessful();

    expect(TrackerPositionReceipt::query()->count())->toBe(1)
        ->and(DevicePosition::query()->count())->toBe(1)
        ->and(DeviceActivityLog::query()->count())->toBe(1);

    CarbonImmutable::setTestNow();
});

it('reports healthy and exact-boundary stale diagnostics without exposing secrets', function () {
    CarbonImmutable::setTestNow('2026-07-26T08:00:00Z');
    config()->set([
        'cache.default' => 'database',
        'queue.default' => 'database',
        'latch.tracker.provider' => 'traccar',
        'latch.traccar.base_url' => 'https://tracker.example/api',
        'latch.traccar.username' => 'diagnostic-user',
        'latch.traccar.password' => 'diagnostic-password',
    ]);

    $publicStorage = public_path('storage');
    $createdStorageDirectory = ! file_exists($publicStorage) && ! is_link($publicStorage);

    if ($createdStorageDirectory) {
        File::makeDirectory($publicStorage);
    }

    try {
        $user = User::factory()->create(['email' => 'private@example.com']);
        $device = Device::factory()->create([
            'user_id' => $user->id,
            'item_name' => 'Diagnostic Item',
            'claim_version' => 1,
            'claimed_at' => now()->subHour(),
            'last_synced_at' => now()->subSeconds(300),
        ]);

        $exit = Artisan::call('latch:diagnose');
        $output = Artisan::output();

        expect($exit)->toBe(0)
            ->and($output)->toContain('STALE_SYNCHRONIZATIONS: 0')
            ->and($output)->not->toContain('diagnostic-password')
            ->and($output)->not->toContain('private@example.com')
            ->and($output)->not->toContain($device->device_uid);

        app()->detectEnvironment(fn (): string => 'production');
        config()->set([
            'latch.traccar.base_url' => 'http://127.0.0.1:8082/api',
            'latch.traccar.allow_insecure_http' => true,
        ]);

        expect(Artisan::call('latch:diagnose'))->toBe(0)
            ->and(Artisan::output())->toContain('TRACKER_CONFIGURATION: PASS');

        app()->detectEnvironment(fn (): string => 'testing');

        $device->forceFill([
            'last_synced_at' => now()->subSeconds(300)->subMillisecond(),
        ])->save();

        expect(Artisan::call('latch:diagnose'))->toBe(1)
            ->and(Artisan::output())->toContain('STALE_SYNCHRONIZATIONS: 1');
    } finally {
        if ($createdStorageDirectory) {
            File::deleteDirectory($publicStorage);
        }

        app()->detectEnvironment(fn (): string => 'testing');
        CarbonImmutable::setTestNow();
    }
});
