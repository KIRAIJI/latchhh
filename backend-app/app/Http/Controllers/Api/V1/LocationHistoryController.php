<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Controllers\Controller;
use App\Http\Requests\History\LocationHistoryRequest;
use App\Http\Resources\DevicePositionResource;
use App\Services\DeviceClaimService;
use App\Services\LocationHistoryService;
use App\Support\ApiResponse;

class LocationHistoryController extends Controller
{
    public function __construct(
        private readonly DeviceClaimService $devices,
        private readonly LocationHistoryService $history,
    ) {}

    public function __invoke(LocationHistoryRequest $request, int $item)
    {
        $device = $this->devices->ownedDevice($request->user(), $item);
        $result = $this->history->get($request->user(), $device, $request->validated());

        return ApiResponse::success(
            $result['rows']
                ->map(fn ($position) => (new DevicePositionResource($position))->resolve($request))
                ->all(),
            'Location history retrieved successfully.',
            meta: $result['meta'],
        );
    }
}
