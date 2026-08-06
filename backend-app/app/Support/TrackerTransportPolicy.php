<?php

namespace App\Support;

final class TrackerTransportPolicy
{
    /**
     * @param  array<string, mixed>  $urlParts
     */
    public static function allows(array $urlParts): bool
    {
        $scheme = strtolower((string) ($urlParts['scheme'] ?? ''));

        if ($scheme === 'https') {
            return true;
        }

        if ($scheme !== 'http' || ! (bool) config('latch.traccar.allow_insecure_http')) {
            return false;
        }

        if (app()->environment(['local', 'testing'])) {
            return true;
        }

        return self::isLoopbackHost((string) ($urlParts['host'] ?? ''));
    }

    private static function isLoopbackHost(string $host): bool
    {
        $host = strtolower(trim($host, '[]'));

        if ($host === 'localhost') {
            return true;
        }

        $packedAddress = inet_pton($host);

        if ($packedAddress === false) {
            return false;
        }

        return $packedAddress === inet_pton('::1')
            || (strlen($packedAddress) === 4 && ord($packedAddress[0]) === 127);
    }
}
