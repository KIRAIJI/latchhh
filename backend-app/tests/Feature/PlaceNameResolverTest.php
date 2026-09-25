<?php

use App\Services\PlaceNameResolver;
use Illuminate\Support\Facades\Cache;
use Illuminate\Support\Facades\Http;

beforeEach(function () {
    Cache::clear();
    config()->set([
        'latch.places.enabled' => true,
        'latch.places.api_key' => 'test-server-key',
        'latch.places.search_radius_meters' => 150,
        'latch.places.cache_seconds' => 86400,
    ]);
});

it('selects the closest useful landmark and caches the result', function () {
    Http::fake([
        'https://places.googleapis.com/v1/places:searchNearby' => Http::response([
            'places' => [
                [
                    'displayName' => ['text' => 'Angeles University Foundation'],
                    'formattedAddress' => 'MacArthur Highway, Angeles City, Pampanga',
                    'location' => ['latitude' => 15.1454, 'longitude' => 120.5883],
                    'types' => ['university', 'school'],
                    'businessStatus' => 'OPERATIONAL',
                ],
                [
                    'displayName' => ['text' => 'Nearby Convenience Store'],
                    'location' => ['latitude' => 15.1451, 'longitude' => 120.5881],
                    'types' => ['convenience_store', 'store'],
                    'businessStatus' => 'OPERATIONAL',
                ],
            ],
        ]),
    ]);

    $resolver = app(PlaceNameResolver::class);

    expect($resolver->resolve(15.145, 120.588))
        ->toBe('Nearby Convenience Store')
        ->and($resolver->resolve(15.145, 120.588))
        ->toBe('Nearby Convenience Store');

    Http::assertSentCount(1);
    Http::assertSent(fn ($request) => $request->hasHeader('X-Goog-Api-Key', 'test-server-key')
        && $request['rankPreference'] === 'DISTANCE');
});
