<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Controllers\Controller;
use App\Http\Requests\Activity\ActivityIndexRequest;
use App\Http\Resources\DeviceActivityResource;
use App\Services\ActivityHistoryService;
use App\Services\DeviceClaimService;
use App\Support\ApiResponse;
use Illuminate\Support\Collection;

class ActivityController extends Controller
{
    public function __construct(
        private readonly ActivityHistoryService $history,
        private readonly DeviceClaimService $devices,
    ) {}

    public function index(ActivityIndexRequest $request)
    {
        $result = $this->history->get($request->user(), $request->validated());

        return $this->response($request, $result);
    }

    public function item(ActivityIndexRequest $request, int $item)
    {
        $device = $this->devices->ownedDevice($request->user(), $item);
        $result = $this->history->get($request->user(), $request->validated(), $device);

        return $this->response($request, $result);
    }

    /**
     * @param  array{rows: Collection, meta: array<string, mixed>}  $result
     */
    private function response(ActivityIndexRequest $request, array $result)
    {
        return ApiResponse::success(
            $result['rows']
                ->map(fn ($activity) => (new DeviceActivityResource($activity))->resolve($request))
                ->all(),
            'Activity retrieved successfully.',
            meta: $result['meta'],
        );
    }
}
