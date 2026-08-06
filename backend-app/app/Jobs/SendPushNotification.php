<?php

namespace App\Jobs;

use App\Contracts\PushProviderInterface;
use App\Enums\PushDeliveryResult;
use App\Models\Notification;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Foundation\Queue\Queueable;
use Throwable;

class SendPushNotification implements ShouldQueue
{
    use Queueable;

    public int $tries = 3;

    public function __construct(public readonly int $notificationId) {}

    public function backoff(): array
    {
        return [30, 120, 300];
    }

    public function handle(PushProviderInterface $provider): void
    {
        $notification = Notification::query()
            ->with(['user.pushTokens', 'device'])
            ->find($this->notificationId);

        if (! $notification || ! $notification->user?->notifications_enabled) {
            return;
        }

        foreach ($notification->user->pushTokens as $token) {
            try {
                $result = $provider->send($token, $notification);
                if ($result === PushDeliveryResult::InvalidToken) {
                    $token->delete();
                }
            } catch (Throwable $exception) {
                report($exception);
            }
        }
    }
}
