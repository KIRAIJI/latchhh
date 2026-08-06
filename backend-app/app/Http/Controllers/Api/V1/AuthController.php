<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Controllers\Controller;
use App\Http\Requests\Auth\GoogleOAuthRequest;
use App\Http\Requests\Auth\LoginRequest;
use App\Http\Requests\Auth\RegisterRequest;
use App\Http\Resources\UserResource;
use App\Services\AuthService;
use App\Support\ApiResponse;
use Illuminate\Http\Request;

class AuthController extends Controller
{
    public function __construct(private readonly AuthService $auth) {}

    public function register(RegisterRequest $request)
    {
        $result = $this->auth->register($request->validated());

        return ApiResponse::success([
            'user' => (new UserResource($result['user']))->resolve($request),
            'token' => $result['token'],
            'token_type' => 'Bearer',
        ], 'Authenticated successfully.', 201);
    }

    public function login(LoginRequest $request)
    {
        $result = $this->auth->login(
            $request->validated('email'),
            $request->validated('password'),
        );

        return ApiResponse::success([
            'user' => (new UserResource($result['user']))->resolve($request),
            'token' => $result['token'],
            'token_type' => 'Bearer',
        ], 'Authenticated successfully.');
    }

    public function google(GoogleOAuthRequest $request)
    {
        $result = $this->auth->loginWithGoogle(
            $request->validated('id_token'),
            $request->safe()->only(['accepted_terms', 'acknowledged_privacy']),
        );

        return ApiResponse::success([
            'user' => (new UserResource($result['user']))->resolve($request),
            'token' => $result['token'],
            'token_type' => 'Bearer',
            'is_new_user' => $result['is_new_user'],
        ], 'Authenticated successfully.');
    }

    public function me(Request $request)
    {
        return ApiResponse::success(
            (new UserResource($request->user()))->resolve($request),
            'Authenticated user retrieved successfully.',
        );
    }

    public function logout(Request $request)
    {
        $this->auth->logout($request->user(), $request->user()->currentAccessToken());

        return ApiResponse::noContent();
    }

    public function logoutAll(Request $request)
    {
        $this->auth->logoutAll($request->user());

        return ApiResponse::noContent();
    }
}
