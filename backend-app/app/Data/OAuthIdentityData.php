<?php

namespace App\Data;

final readonly class OAuthIdentityData
{
    public function __construct(
        public string $provider,
        public string $providerSubject,
        public string $firebaseUid,
        public string $email,
        public string $name,
        public int $authenticatedAt,
    ) {}
}
