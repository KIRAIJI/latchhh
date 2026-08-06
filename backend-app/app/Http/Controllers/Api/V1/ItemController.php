<?php

namespace App\Http\Controllers\Api\V1;

use App\Exceptions\ApiException;
use App\Exceptions\TrackerProviderException;
use App\Http\Controllers\Controller;
use App\Http\Requests\Item\ClaimItemRequest;
use App\Http\Requests\Item\UpdateItemRequest;
use App\Http\Resources\ItemResource;
use App\Models\Device;
use App\Services\DeviceClaimService;
use App\Services\TrackerSyncService;
use App\Support\ApiResponse;
use Illuminate\Http\Request;

class ItemController extends Controller
{
    public function __construct(
        private readonly DeviceClaimService $devices,
        private readonly TrackerSyncService $trackerSync,
    ) {}

    public function index(Request $request)
    {
        $items = Device::query()
            ->with('geofence')
            ->where('user_id', $request->user()->id)
            ->orderByRaw('LOWER(item_name)')
            ->orderBy('id')
            ->get()
            ->map(fn (Device $device) => (new ItemResource($device))->resolve($request))
            ->all();

        return ApiResponse::success($items, 'Items retrieved successfully.', meta: []);
    }

    public function claim(ClaimItemRequest $request)
    {
        $item = $this->devices->claim(
            $request->user(),
            $request->validated('device_uid'),
            $request->validated('item_name'),
        );

        return ApiResponse::success(
            (new ItemResource($item))->resolve($request),
            'Item claimed successfully.',
            201,
        );
    }

    public function show(Request $request, int $item)
    {
        $device = $this->devices
            ->ownedDevice($request->user(), $item)
            ->load('geofence');

        return ApiResponse::success(
            (new ItemResource($device))->resolve($request),
            'Item retrieved successfully.',
        );
    }

    public function refresh(Request $request, int $item)
    {
        $device = $this->devices->ownedDevice($request->user(), $item);

        try {
            $this->trackerSync->sync(
                $device->id,
                $request->user()->id,
                $device->claim_version,
                $device->claimed_at->utc()->toISOString(),
            );
        } catch (TrackerProviderException $exception) {
            report($exception);

            throw new ApiException(
                'TRACKER_UNAVAILABLE',
                'The tracker service could not be refreshed. Try again shortly.',
                503,
            );
        }

        $device = $this->devices
            ->ownedDevice($request->user(), $item)
            ->load('geofence');

        return ApiResponse::success(
            (new ItemResource($device))->resolve($request),
            'Item refreshed successfully.',
        );
    }

    public function update(UpdateItemRequest $request, int $item)
    {
        $device = $this->devices->rename(
            $request->user(),
            $item,
            $request->validated('item_name'),
        );

        return ApiResponse::success(
            (new ItemResource($device))->resolve($request),
            'Item updated successfully.',
        );
    }

    public function destroy(Request $request, int $item)
    {
        $this->devices->release($request->user(), $item);

        return ApiResponse::noContent();
    }
}
