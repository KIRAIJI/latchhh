<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Controllers\Controller;
use App\Http\Requests\Notification\NotificationIndexRequest;
use App\Http\Resources\NotificationResource;
use App\Services\NotificationFeedService;
use App\Support\ApiResponse;
use Illuminate\Http\Request;

class NotificationController extends Controller
{
    public function __construct(private readonly NotificationFeedService $feed) {}

    public function index(NotificationIndexRequest $request)
    {
        $result = $this->feed->get($request->user(), $request->validated());

        return ApiResponse::success(
            $result['rows']
                ->map(fn ($notification) => (new NotificationResource($notification))->resolve($request))
                ->all(),
            'Notifications retrieved successfully.',
            meta: $result['meta'],
        );
    }

    public function readAll(Request $request)
    {
        return ApiResponse::success(
            $this->feed->markAllRead($request->user()),
            'Notifications marked as read.',
        );
    }

    public function read(Request $request, int $notification)
    {
        $notification = $this->feed->markRead($request->user(), $notification);

        return ApiResponse::success(
            (new NotificationResource($notification))->resolve($request),
            'Notification marked as read.',
        );
    }

    public function destroy(Request $request, int $notification)
    {
        $this->feed->deleteNotification($request->user(), $notification);

        return ApiResponse::noContent();
    }
}
