<?php

namespace App\Contracts;

use App\Data\OAuthIdentityData;

interface OAuthTokenVerifierInterface
{
    public function verifyGoogle(string $idToken): OAuthIdentityData;
}
