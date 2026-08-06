<?php

namespace App\Providers\Auth;

use App\Contracts\OAuthTokenVerifierInterface;
use App\Data\OAuthIdentityData;
use App\Exceptions\ApiException;
use Illuminate\Support\Facades\Cache;
use Illuminate\Support\Facades\Http;
use Throwable;

class FirebaseGoogleTokenVerifier implements OAuthTokenVerifierInterface
{
    private const CERTIFICATES_URL =
        'https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system.gserviceaccount.com';

    public function verifyGoogle(string $idToken): OAuthIdentityData
    {
        $projectId = (string) config('latch.oauth.firebase.project_id');

        if (! config('latch.oauth.google.enabled') || $projectId === '') {
            throw new ApiException(
                'OAUTH_NOT_CONFIGURED',
                'Google sign-in is not available.',
                503,
            );
        }

        $header = $this->decodeHeader($idToken);
        $keyId = $header['kid'] ?? null;

        if (($header['alg'] ?? null) !== 'RS256' || ! is_string($keyId) || $keyId === '') {
            throw $this->invalidToken();
        }

        $keys = $this->publicKeys();
        if (! isset($keys[$keyId])) {
            $keys = $this->publicKeys(refresh: true);
        }

        $certificate = $keys[$keyId] ?? null;
        if (! is_string($certificate) || $certificate === '') {
            throw $this->invalidToken();
        }

        $segments = explode('.', $idToken);
        $signature = $this->decodeSegment($segments[2]);
        $payload = $this->decodeSegment($segments[1]);
        $claims = json_decode($payload, true);
        $verified = openssl_verify(
            $segments[0].'.'.$segments[1],
            $signature,
            $certificate,
            OPENSSL_ALGO_SHA256,
        );

        if ($verified !== 1 || ! is_array($claims)) {
            throw $this->invalidToken();
        }

        $now = now()->timestamp;
        $issuer = "https://securetoken.google.com/{$projectId}";
        $expiresAt = $claims['exp'] ?? null;
        $issuedAt = $claims['iat'] ?? null;
        $subject = $claims['sub'] ?? null;
        $email = $claims['email'] ?? null;
        $authenticatedAt = $claims['auth_time'] ?? null;
        $firebase = isset($claims['firebase']) ? (array) $claims['firebase'] : [];
        $identities = isset($firebase['identities'])
            ? (array) $firebase['identities']
            : [];
        $googleSubjects = $identities['google.com'] ?? null;
        $providerSubject = is_array($googleSubjects)
            ? ($googleSubjects[0] ?? null)
            : null;

        if (
            ($claims['aud'] ?? null) !== $projectId
            || ($claims['iss'] ?? null) !== $issuer
            || ! is_int($expiresAt)
            || $expiresAt <= $now - 30
            || ! is_int($issuedAt)
            || $issuedAt > $now + 30
            || $issuedAt >= $expiresAt
            || ! is_string($subject)
            || $subject === ''
            || strlen($subject) > 128
            || ! is_int($authenticatedAt)
            || $authenticatedAt > $now + 30
            || $authenticatedAt > $issuedAt + 30
            || ($firebase['sign_in_provider'] ?? null) !== 'google.com'
            || ! is_string($providerSubject)
            || $providerSubject === ''
            || strlen($providerSubject) > 255
            || ! is_string($email)
            || ! filter_var($email, FILTER_VALIDATE_EMAIL)
            || mb_strlen($email) > 255
            || ($claims['email_verified'] ?? false) !== true
        ) {
            throw $this->invalidToken();
        }

        $name = trim(is_string($claims['name'] ?? null) ? $claims['name'] : '');
        if ($name === '') {
            $name = strstr($email, '@', true) ?: 'LATCH User';
        }

        return new OAuthIdentityData(
            provider: 'google',
            providerSubject: $providerSubject,
            firebaseUid: $subject,
            email: mb_strtolower($email),
            name: mb_substr($name, 0, 100),
            authenticatedAt: $authenticatedAt,
        );
    }

    /**
     * @return array<string, mixed>
     */
    private function decodeHeader(string $idToken): array
    {
        $segments = explode('.', $idToken);
        if (count($segments) !== 3) {
            throw $this->invalidToken();
        }

        $header = json_decode($this->decodeSegment($segments[0]), true);

        if (! is_array($header)) {
            throw $this->invalidToken();
        }

        return $header;
    }

    private function decodeSegment(string $encoded): string
    {
        $encoded = strtr($encoded, '-_', '+/');
        $encoded .= str_repeat('=', (4 - strlen($encoded) % 4) % 4);
        $decoded = base64_decode($encoded, true);

        if (! is_string($decoded)) {
            throw $this->invalidToken();
        }

        return $decoded;
    }

    /**
     * @return array<string, string>
     */
    private function publicKeys(bool $refresh = false): array
    {
        $cacheKey = 'latch:firebase-auth:public-keys';

        if (! $refresh) {
            $cached = Cache::get($cacheKey);
            if (is_array($cached) && $cached !== []) {
                return $cached;
            }
        }

        try {
            $response = Http::acceptJson()
                ->connectTimeout(3)
                ->timeout(10)
                ->retry(2, 200)
                ->get(self::CERTIFICATES_URL)
                ->throw();
        } catch (Throwable) {
            throw new ApiException(
                'OAUTH_PROVIDER_UNAVAILABLE',
                'Google sign-in could not be verified. Please try again.',
                503,
            );
        }

        $keys = $response->json();
        if (! is_array($keys) || $keys === []) {
            throw new ApiException(
                'OAUTH_PROVIDER_UNAVAILABLE',
                'Google sign-in could not be verified. Please try again.',
                503,
            );
        }

        $cacheControl = $response->header('Cache-Control');
        preg_match('/(?:^|,)\s*max-age=(\d+)/i', $cacheControl, $matches);
        $maxAge = max(60, min(21600, (int) ($matches[1] ?? 3600)));
        Cache::put($cacheKey, $keys, now()->addSeconds($maxAge));

        return $keys;
    }

    private function invalidToken(): ApiException
    {
        return new ApiException(
            'OAUTH_INVALID_TOKEN',
            'The Google sign-in credential is invalid or expired.',
            401,
        );
    }
}
