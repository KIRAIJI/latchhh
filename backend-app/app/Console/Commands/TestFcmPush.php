<?php

namespace App\Console\Commands;

use App\Enums\PushDeliveryResult;
use App\Models\PushToken;
use App\Providers\Push\FcmPushProvider;
use Illuminate\Console\Command;
use Throwable;

class TestFcmPush extends Command
{
    protected $signature = 'latch:test-push
        {--user-id= : Send to the most recently seen token for this user}';

    protected $description = 'Send a transport-only FCM test without creating app data';

    public function handle(FcmPushProvider $provider): int
    {
        if (! app()->environment(['local', 'testing'])) {
            $this->error('FCM transport tests are restricted to local and testing environments.');

            return self::FAILURE;
        }

        $userId = $this->option('user-id');
        if ($userId !== null && (! ctype_digit((string) $userId) || (int) $userId < 1)) {
            $this->error('The user-id option must be a positive integer.');

            return self::INVALID;
        }

        $token = PushToken::query()
            ->when($userId !== null, fn ($query) => $query->where('user_id', (int) $userId))
            ->latest('last_seen_at')
            ->latest('id')
            ->first();

        if ($token === null) {
            $this->error('No registered push token matched the request.');

            return self::FAILURE;
        }

        try {
            $result = $provider->sendTest($token);
        } catch (Throwable $exception) {
            report($exception);
            $this->error('FCM delivery failed before the test was accepted.');

            return self::FAILURE;
        }

        if ($result === PushDeliveryResult::InvalidToken) {
            $token->delete();
            $this->error('FCM rejected the registered token; it was removed.');

            return self::FAILURE;
        }

        if ($result !== PushDeliveryResult::Accepted) {
            $this->error('FCM did not accept the transport test.');

            return self::FAILURE;
        }

        $this->info('FCM accepted the transport test.');

        return self::SUCCESS;
    }
}
