<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Controllers\Controller;
use App\Http\Requests\Push\DeletePushTokenRequest;
use App\Http\Requests\Push\StorePushTokenRequest;
use App\Models\PushToken;
use App\Support\ApiResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class PushTokenController extends Controller
{
    public function store(StorePushTokenRequest $request)
    {
        $data = $request->validated();
        $hash = hash('sha256', $data['token']);

        DB::transaction(function () use ($request, $data, $hash): void {
            PushToken::query()->updateOrCreate(
                ['token_hash' => $hash],
                [
                    'user_id' => $request->user()->id,
                    'token' => $data['token'],
                    'platform' => $data['platform'],
                    'device_name' => $data['device_name'] ?? null,
                    'app_version' => $data['app_version'] ?? null,
                    'last_seen_at' => now(),
                ],
            );
        });

        return ApiResponse::noContent();
    }

    public function destroy(DeletePushTokenRequest $request)
    {
        PushToken::query()
            ->where('user_id', $request->user()->id)
            ->where('token_hash', hash('sha256', $request->validated('token')))
            ->delete();

        return ApiResponse::noContent();
    }

    public function destroyAll(Request $request)
    {
        $request->user()->pushTokens()->delete();

        return ApiResponse::noContent();
    }
}
