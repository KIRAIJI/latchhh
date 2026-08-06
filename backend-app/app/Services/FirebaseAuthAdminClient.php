<?php

namespace App\Services;

use Google\Auth\Credentials\ServiceAccountCredentials;
use Illuminate\Support\Facades\Cache;
use Illuminate\Support\Facades\Http;
use RuntimeException;

class FirebaseAuthAdminClient
{
    private const SCOPE = 'https://www.googleapis.com/auth/identitytoolkit';

    public function deleteUser(string $firebaseUid): void
    {
        $projectId = (string) config('latch.oauth.firebase.project_id');
        if ($projectId === '') {
            throw new RuntimeException('Firebase project ID is not configured.');
        }

        $response = Http::asJson()
            ->acceptJson()
            ->withToken($this->accessToken())
            ->connectTimeout(5)
            ->timeout(15)
            ->retry(2, 250, throw: false)
            ->post(
                "https://identitytoolkit.googleapis.com/v1/projects/{$projectId}/accounts:delete",
                ['localId' => $firebaseUid],
            );

        $error = data_get($response->json(), 'error.message');
        if ($response->successful() || $error === 'USER_NOT_FOUND') {
            return;
        }

        throw new RuntimeException('Firebase Authentication user deletion failed.');
    }

    private function accessToken(): string
    {
        return Cache::remember(
            'latch:firebase-auth:oauth-access-token',
            now()->addMinutes(50),
            function (): string {
                $path = (string) config('latch.oauth.firebase.credentials_path');
                if ($path === '') {
                    throw new RuntimeException(
                        'Firebase service-account credentials are not configured.',
                    );
                }

                $credentials = new ServiceAccountCredentials(self::SCOPE, $path);
                $token = $credentials->fetchAuthToken();
                $accessToken = $token['access_token'] ?? null;

                if (! is_string($accessToken) || $accessToken === '') {
                    throw new RuntimeException(
                        'Firebase OAuth access token could not be obtained.',
                    );
                }

                return $accessToken;
            },
        );
    }
}
