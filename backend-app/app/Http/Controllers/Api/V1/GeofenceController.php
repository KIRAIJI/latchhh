<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Controllers\Controller;
use App\Http\Requests\Geofence\UpsertGeofenceRequest;
use App\Http\Resources\GeofenceResource;
use App\Services\DeviceClaimService;
use App\Services\GeofenceService;
use App\Support\ApiResponse;
use Illuminate\Http\Request;

class GeofenceController extends Controller
{
    public function __construct(
        private readonly DeviceClaimService $devices,
        private readonly GeofenceService $geofences,
    ) {}

    public function show(Request $request, int $item)
    {
        $device = $this->devices
            ->ownedDevice($request->user(), $item)
            ->load('geofence');

        return ApiResponse::success(
            $device->geofence
                ? (new GeofenceResource($device->geofence))->resolve($request)
                : null,
            'Geofence retrieved successfully.',
        );
    }

    public function upsert(UpsertGeofenceRequest $request, int $item)
    {
        $geofence = $this->geofences->upsert(
            $request->user(),
            $item,
            $request->validated(),
        );

        return ApiResponse::success(
            (new GeofenceResource($geofence))->resolve($request),
            'Geofence saved successfully.',
        );
    }

    public function destroy(Request $request, int $item)
    {
        $this->geofences->delete($request->user(), $item);

        return ApiResponse::noContent();
    }
}
