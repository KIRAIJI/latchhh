<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Controllers\Controller;
use App\Support\ApiResponse;
use Illuminate\Http\Request;
use Laravel\Sanctum\PersonalAccessToken;

class SessionController extends Controller
{
    public function index(Request $request)
    {
        $currentId = $request->user()->currentAccessToken()?->id;
        $sessions = $request->user()->tokens()
            ->latest('last_used_at')
            ->latest('created_at')
            ->get()
            ->map(fn (PersonalAccessToken $token): array => [
                'id' => $token->id,
                'device_name' => $token->device_name ?: 'LATCH app',
                'platform' => $token->platform,
                'is_current' => $token->id === $currentId,
                'last_active_at' => ($token->last_used_at ?? $token->created_at)?->utc()->toISOString(),
                'created_at' => $token->created_at?->utc()->toISOString(),
            ])
            ->values();

        return ApiResponse::success($sessions, 'Signed-in devices retrieved successfully.');
    }

    public function destroy(Request $request, int $session)
    {
        $token = $request->user()->tokens()->whereKey($session)->firstOrFail();
        $wasCurrent = $token->id === $request->user()->currentAccessToken()?->id;
        $token->delete();

        return ApiResponse::success(
            ['was_current' => $wasCurrent],
            'Device logged out successfully.',
        );
    }
}
