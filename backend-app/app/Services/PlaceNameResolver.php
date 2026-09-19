<?php

namespace App\Services;

use App\Models\Device;
use Illuminate\Http\Client\ConnectionException;
use Illuminate\Support\Facades\Cache;
use Illuminate\Support\Facades\Http;
use Throwable;

class PlaceNameResolver
{
    /** @var list<string> */
    private const PRIORITY_TYPES = [
        'school',
        'university',
        'primary_school',
        'secondary_school',
        'hospital',
        'shopping_mall',
        'lodging',
        'church',
    ];

    public function resolveForDevice(Device $device): void
    {
        if (
            ! config('latch.places.enabled')
            || blank(config('latch.places.api_key'))
            || $device->last_latitude === null
            || $device->last_longitude === null
            || $device->last_position_at === null
            || ($device->last_place_resolved_at
                && $device->last_place_resolved_at->greaterThanOrEqualTo($device->last_position_at))
        ) {
            return;
        }

        $positionAt = $device->last_position_at;

        try {
            $name = $this->resolve(
                (float) $device->last_latitude,
                (float) $device->last_longitude,
            );

            Device::query()
                ->whereKey($device->id)
                ->where('last_position_at', $positionAt)
                ->update([
                    'last_place_name' => $name,
                    'last_place_resolved_at' => $positionAt,
                ]);
        } catch (ConnectionException $exception) {
            report($exception);
        } catch (Throwable $exception) {
            report($exception);
        }
    }

    public function resolve(float $latitude, float $longitude): ?string
    {
        $cacheKey = 'place-name-v3:'.hash('sha256', sprintf('%.4F,%.4F', $latitude, $longitude));
        $cached = Cache::get($cacheKey);

        if (is_array($cached) && array_key_exists('name', $cached)) {
            return is_string($cached['name']) ? $cached['name'] : null;
        }

        $radius = (float) config('latch.places.search_radius_meters');
        $response = Http::connectTimeout((int) config('latch.places.connect_timeout_seconds'))
            ->timeout((int) config('latch.places.timeout_seconds'))
            ->acceptJson()
            ->withHeaders([
                'X-Goog-Api-Key' => (string) config('latch.places.api_key'),
                'X-Goog-FieldMask' => implode(',', [
                    'places.displayName',
                    'places.formattedAddress',
                    'places.location',
                    'places.types',
                    'places.businessStatus',
                ]),
            ])
            ->post('https://places.googleapis.com/v1/places:searchNearby', [
                'maxResultCount' => 10,
                'rankPreference' => 'DISTANCE',
                'locationRestriction' => [
                    'circle' => [
                        'center' => [
                            'latitude' => $latitude,
                            'longitude' => $longitude,
                        ],
                        'radius' => $radius,
                    ],
                ],
            ]);

        $response->throw();
        $name = $this->selectName($response->json('places', []), $latitude, $longitude, $radius);

        Cache::put(
            $cacheKey,
            ['name' => $name],
            (int) config('latch.places.cache_seconds'),
        );

        return $name;
    }

    private function selectName(mixed $places, float $latitude, float $longitude, float $radius): ?string
    {
        if (! is_array($places)) {
            return null;
        }

        $candidates = collect($places)
            ->filter(fn (mixed $place) => is_array($place)
                && ($place['businessStatus'] ?? 'OPERATIONAL') !== 'CLOSED_PERMANENTLY'
                && is_numeric(data_get($place, 'location.latitude'))
                && is_numeric(data_get($place, 'location.longitude')))
            ->map(function (array $place) use ($latitude, $longitude): array {
                $types = is_array($place['types'] ?? null) ? $place['types'] : [];
                $priority = collect(self::PRIORITY_TYPES)
                    ->search(fn (string $type) => in_array($type, $types, true));

                return [
                    'name' => data_get($place, 'displayName.text'),
                    'address' => data_get($place, 'formattedAddress'),
                    'distance' => $this->distanceMeters(
                        $latitude,
                        $longitude,
                        (float) data_get($place, 'location.latitude'),
                        (float) data_get($place, 'location.longitude'),
                    ),
                    'priority' => $priority === false ? count(self::PRIORITY_TYPES) : $priority,
                ];
            })
            ->filter(fn (array $place) => (
                    (is_string($place['name']) && trim($place['name']) !== '')
                    || (is_string($place['address'])
                        && trim($place['address']) !== '')
                ) && $place['distance'] <= $radius)
            ->sortBy(fn (array $place) => sprintf('%02d-%012.4f', $place['priority'], $place['distance']))
            ->first();

        if (! is_array($candidates)) {
            return null;
        }

        $name = is_string($candidates['name']) ? trim($candidates['name']) : '';
        $address = is_string($candidates['address'])
            ? trim($candidates['address'])
            : '';
        $label = $name !== '' && $address !== ''
            ? "{$name}, {$address}"
            : ($name !== '' ? $name : $address);

        return mb_substr($label, 0, 191);
    }

    private function distanceMeters(float $latA, float $lonA, float $latB, float $lonB): float
    {
        $latDelta = deg2rad($latB - $latA);
        $lonDelta = deg2rad($lonB - $lonA);
        $a = sin($latDelta / 2) ** 2
            + cos(deg2rad($latA)) * cos(deg2rad($latB)) * sin($lonDelta / 2) ** 2;

        return 6371000 * 2 * atan2(sqrt(min(1, max(0, $a))), sqrt(1 - min(1, max(0, $a))));
    }
}
