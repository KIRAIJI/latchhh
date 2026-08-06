<?php

use App\Exceptions\ApiException;
use App\Providers\Auth\FirebaseGoogleTokenVerifier;
use Illuminate\Support\Facades\Cache;
use Illuminate\Support\Facades\Http;

function firebaseTestToken(array $overrides = []): array
{
    $privateKey = <<<'PEM'
-----BEGIN PRIVATE KEY-----
MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC5+mFww+3QaB+z
oh3sdMFRRI7J9aS/pS8TTyWA8QBuyIxFwr3YEh9nFFGDYAXSAeMTHjTUQ+y+uaQ3
PYd9C9WYXSyoOB4Qx3fvh3T2dX0bPCCnxegcZuWUBBSMxv+/LTiJe/f8cusAHlFr
b0IgWfptXTcauEcyN4KlYBPjNBUAlyciCgP3+jS+PP7VOlT4GycClMFVX0JAr8Nz
acCSkgSvmZYO4sHRq2HqxTlIbldO2lOv/1/nhe3vhDILM9oNu4xpygG8OeeQDc8i
c303y9D53Vr+NAK8JCvIcSLI6TbM/LFmFCiO1+UYWZPSw44IXRpJeEOMcel2SzgS
HaobqpWTAgMBAAECggEAA9XRf5lSxKr5CGPN+SvZNrtStIB/0sHtzBikPdn6d85j
8iZXcubAxgfpvGtVA2UtkR3c2gnCjpZWeBspOZ9uWrOFMbzihdcPNp3VACYhG9gs
FjmjFT7b+ibxY4vRB1i/4znuvcKC7SAy7qO/OdqRKwMymTb2pOb3J0DpeBSBYF80
m8I0KeTEZwt5TlRKogTu2UIr+4cUuUNWXg4yXUkt1pVKHsj8JHEzWLM4CnwLhrqT
RPKehiSYNz7+rh+vlsM7d8dtg5BFsV7Hf1SUKrKHx8aMt1UUPbTRl1Yd1u6Gk/ob
y6zK9J3JmkbqjHGWgHPkm0LMhmlP9QelDb8/ka6yjQKBgQDdihqqd6M4Ir3lz0cC
tPaGtRvmdomOBueWkhSi/8BlKxTh0OEU6lYCZyW9bQ5dlz0CNPzqiagzf/dY5Pmg
7HqIfZ9P2uoZmoqV3cADfdQ7+DPuc9UR/6/gGoXInsETRYxYKPT187wIr3bb1uFW
o9wlRc8FSQrnR42VK9v3SasWHwKBgQDW6DCGR8+vdGejViapPdOsrF4BqeFOQMdk
lvkq7kzRGIZ3C7IiEr1/u/Z57JiD2L5fTcJwrR2W2uk0PMsu6ThCJQMlPO+4btdB
lQAYmMgKxGq6UcX4csJHrBScvII0q8WhWo9PIXSoNmbfICGun8/WxdVrHaTg+Csz
EWMSNLrKDQKBgG5V2fIMo73Rj8Jk+Xjfp+hvSvX9E4Uo6y6SkrSbq7a3Pi85WOCR
1URdGu684eq66CexPEWTSbJciVQ8thbvsBdKeY3L+cvGrD73sioLASjc9QBNsFDG
h1GV5AuHBaITgVJnE5lrSrsIqKnU22XLNllZ60E2n7eQa3Nf5K3EgvEBAoGAM+E3
naMGWxfh0QeCK8cWK5As3X/yOR7gQ8NCgX+noa6m/2Qea6VUg9qPZN2d7+5J/t3Z
u7O++eT8+TRX5Io+n5Ep0jqEsGRpqJZcwJNZb2f9T99fyFuD1SOQkknbsPVqr0oa
YIL2E4mWifTKzModtX5qyAIPBJREcX6YCRQbp50CgYEAumSv4dyweolfCRVrbuYL
og9Qaz7Pry/cX4dU5T64lOwzwpX8uq8BoTfNWsflYUaa40O+fAELRI4lMlpHVs0b
a0AeaUfMlEf3VstSGGEwyHemnEbYHeEqhteXYtboj2m1uD9PxI+IZvtBWw7BjSzT
s+LQgrElAzLbhtRp1UmmdEE=
-----END PRIVATE KEY-----
PEM;
    $key = openssl_pkey_get_private($privateKey);
    $publicKey = openssl_pkey_get_details($key)['key'];
    $now = now()->timestamp;
    $claims = array_replace_recursive([
        'aud' => 'latch-test-project',
        'iss' => 'https://securetoken.google.com/latch-test-project',
        'sub' => 'firebase-uid',
        'iat' => $now - 5,
        'exp' => $now + 3600,
        'auth_time' => $now - 10,
        'email' => 'verified@example.com',
        'email_verified' => true,
        'name' => 'Verified User',
        'firebase' => [
            'sign_in_provider' => 'google.com',
            'identities' => ['google.com' => ['google-subject']],
        ],
    ], $overrides);

    $encode = static fn (array $value): string => rtrim(strtr(
        base64_encode(json_encode($value, JSON_THROW_ON_ERROR)),
        '+/',
        '-_',
    ), '=');
    $header = $encode(['alg' => 'RS256', 'typ' => 'JWT', 'kid' => 'test-key']);
    $payload = $encode($claims);
    openssl_sign($header.'.'.$payload, $signature, $privateKey, OPENSSL_ALGO_SHA256);
    $encodedSignature = rtrim(strtr(base64_encode($signature), '+/', '-_'), '=');

    return [$header.'.'.$payload.'.'.$encodedSignature, $publicKey];
}

it('cryptographically verifies Firebase Google ID token claims', function () {
    Cache::clear();
    config()->set('latch.oauth.google.enabled', true);
    config()->set('latch.oauth.firebase.project_id', 'latch-test-project');
    [$token, $publicKey] = firebaseTestToken();
    Http::fake([
        '*' => Http::response(
            ['test-key' => $publicKey],
            200,
            ['Cache-Control' => 'public, max-age=3600'],
        ),
    ]);

    $identity = app(FirebaseGoogleTokenVerifier::class)->verifyGoogle($token);

    expect($identity->provider)->toBe('google')
        ->and($identity->providerSubject)->toBe('google-subject')
        ->and($identity->firebaseUid)->toBe('firebase-uid')
        ->and($identity->email)->toBe('verified@example.com');
});

it('rejects Firebase tokens issued for another project or provider', function (
    array $overrides,
) {
    Cache::clear();
    config()->set('latch.oauth.google.enabled', true);
    config()->set('latch.oauth.firebase.project_id', 'latch-test-project');
    [$token, $publicKey] = firebaseTestToken($overrides);
    Http::fake(['*' => Http::response(['test-key' => $publicKey])]);

    expect(fn () => app(FirebaseGoogleTokenVerifier::class)->verifyGoogle($token))
        ->toThrow(ApiException::class);
})->with([
    'wrong audience' => [['aud' => 'another-project']],
    'wrong provider' => [['firebase' => ['sign_in_provider' => 'password']]],
    'unverified email' => [['email_verified' => false]],
]);
