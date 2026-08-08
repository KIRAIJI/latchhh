<?php

namespace App\Services;

use App\Data\TrackerPositionData;
use Illuminate\Support\Facades\Cache;
use Illuminate\Support\Facades\Http;
use Throwable;

class WifiGeolocationResolver
{
    public function resolve(TrackerPositionData $position): TrackerPositionData
    {
        if (
            ! config('latch.geolocation.enabled')
            || blank(config('latch.geolocation.api_key'))
            || $position->gnssValid === true
            || count($position->wifiAccessPoints) < 2
        ) {
            return $position;
        }

        $points = collect($position->wifiAccessPoints)
            ->sortBy('macAddress')
            ->values()
            ->all();
        $cacheKey = 'wifi-location:'.hash(
            'sha256',
            implode(',', array_column($points, 'macAddress')),
        );

        try {
            $resolved = Cache::remember(
                $cacheKey,
                (int) config('latch.geolocation.cache_seconds'),
                fn () => $this->request($points),
            );

            if (! is_array($resolved)) {
                return $position;
            }

            return $position->withResolvedLocation(
                $resolved['latitude'],
                $resolved['longitude'],
                $resolved['accuracy'],
            );
        } catch (Throwable $exception) {
            report($exception);

            return $position;
        }
    }

    /**
     * @param  list<array{macAddress: string, signalStrength: int}>  $points
     * @return array{latitude: float, longitude: float, accuracy: float}|null
     */
    private function request(array $points): ?array
    {
        $response = Http::connectTimeout((int) config('latch.geolocation.connect_timeout_seconds'))
            ->timeout((int) config('latch.geolocation.timeout_seconds'))
            ->acceptJson()
            ->withHeaders([
                'X-Goog-Api-Key' => (string) config('latch.geolocation.api_key'),
            ])
            ->post(
                'https://www.googleapis.com/geolocation/v1/geolocate',
                [
                    'considerIp' => false,
                    'wifiAccessPoints' => $points,
                ],
            );

        if ($response->status() === 404) {
            return null;
        }

        $response->throw();
        $latitude = $response->json('location.lat');
        $longitude = $response->json('location.lng');
        $accuracy = $response->json('accuracy');

        if (
            ! is_numeric($latitude)
            || ! is_numeric($longitude)
            || ! is_numeric($accuracy)
            || (float) $latitude < -90
            || (float) $latitude > 90
            || (float) $longitude < -180
            || (float) $longitude > 180
            || (float) $accuracy <= 0
            || (float) $accuracy > 50000
        ) {
            return null;
        }

        return [
            'latitude' => (float) $latitude,
            'longitude' => (float) $longitude,
            'accuracy' => (float) $accuracy,
        ];
    }
}
