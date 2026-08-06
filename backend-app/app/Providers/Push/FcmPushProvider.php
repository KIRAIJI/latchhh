<?php

namespace App\Providers\Push;

use App\Contracts\PushProviderInterface;
use App\Enums\PushDeliveryResult;
use App\Models\Notification;
use App\Models\PushToken;
use Google\Auth\Credentials\ServiceAccountCredentials;
use Illuminate\Support\Facades\Cache;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Log;
use RuntimeException;

class FcmPushProvider implements PushProviderInterface
{
    private const SCOPE = 'https://www.googleapis.com/auth/firebase.messaging';

    public function send(PushToken $token, Notification $notification): PushDeliveryResult
    {
        return $this->sendMessage(
            $this->message($token, $notification),
            $notification->id,
        );
    }

    public function sendTest(PushToken $token): PushDeliveryResult
    {
        return $this->sendMessage($this->testMessage($token), null);
    }

    private function sendMessage(array $message, ?int $notificationId): PushDeliveryResult
    {
        $projectId = (string) config('latch.push.fcm.project_id');
        if ($projectId === '') {
            throw new RuntimeException('FCM project ID is not configured.');
        }

        $response = Http::asJson()
            ->acceptJson()
            ->withToken($this->accessToken())
            ->connectTimeout(5)
            ->timeout(15)
            ->retry(2, 250, throw: false)
            ->post(
                "https://fcm.googleapis.com/v1/projects/{$projectId}/messages:send",
                ['message' => $message],
            );

        if ($response->successful()) {
            return PushDeliveryResult::Accepted;
        }

        $errorCode = data_get($response->json(), 'error.details.0.errorCode');
        if (in_array($errorCode, ['UNREGISTERED', 'SENDER_ID_MISMATCH'], true)) {
            return PushDeliveryResult::InvalidToken;
        }

        Log::warning('FCM rejected a push notification.', [
            'notification_id' => $notificationId,
            'transport_test' => $notificationId === null,
            'http_status' => $response->status(),
            'fcm_error_code' => is_string($errorCode) ? $errorCode : null,
        ]);

        return PushDeliveryResult::Failed;
    }

    private function accessToken(): string
    {
        return Cache::remember('latch:fcm:oauth-access-token', now()->addMinutes(50), function (): string {
            $credentialsPath = (string) config('latch.push.fcm.credentials_path');
            if ($credentialsPath === '') {
                throw new RuntimeException('FCM service-account credentials are not configured.');
            }

            $credentials = new ServiceAccountCredentials(self::SCOPE, $credentialsPath);
            $token = $credentials->fetchAuthToken();
            $accessToken = $token['access_token'] ?? null;

            if (! is_string($accessToken) || $accessToken === '') {
                throw new RuntimeException('FCM OAuth access token could not be obtained.');
            }

            return $accessToken;
        });
    }

    private function message(PushToken $token, Notification $notification): array
    {
        $itemId = $notification->device_id;

        return [
            'token' => $token->token,
            'notification' => [
                'title' => $notification->title,
                'body' => $notification->message,
            ],
            'data' => [
                'route' => 'notification',
                'notification_id' => (string) $notification->id,
                'notification_type' => $notification->type->value,
                'item_id' => $itemId === null ? '' : (string) $itemId,
            ],
            'android' => [
                'priority' => 'high',
                'notification' => [
                    'channel_id' => 'latch_alerts',
                    'sound' => 'default',
                ],
            ],
            'apns' => [
                'payload' => [
                    'aps' => [
                        'sound' => 'default',
                        'category' => 'LATCH_ALERT',
                    ],
                ],
            ],
        ];
    }

    private function testMessage(PushToken $token): array
    {
        return [
            'token' => $token->token,
            'notification' => [
                'title' => 'LATCH notification test',
                'body' => 'Background notifications are configured correctly.',
            ],
            'data' => [
                'route' => 'notifications',
                'notification_type' => 'system_test',
            ],
            'android' => [
                'priority' => 'high',
                'notification' => [
                    'channel_id' => 'latch_alerts',
                    'sound' => 'default',
                ],
            ],
            'apns' => [
                'payload' => [
                    'aps' => [
                        'sound' => 'default',
                        'category' => 'LATCH_ALERT',
                    ],
                ],
            ],
        ];
    }
}
