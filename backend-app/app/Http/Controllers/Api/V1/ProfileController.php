<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Controllers\Controller;
use App\Http\Requests\Profile\ChangePasswordRequest;
use App\Http\Requests\Profile\DeleteAccountRequest;
use App\Http\Requests\Profile\UpdateProfileRequest;
use App\Http\Requests\Profile\UpdateSettingsRequest;
use App\Http\Requests\Profile\UploadProfilePhotoRequest;
use App\Http\Resources\UserResource;
use App\Services\AccountDeletionService;
use App\Services\ProfilePhotoService;
use App\Services\ProfileService;
use App\Support\ApiResponse;
use Illuminate\Http\Request;

class ProfileController extends Controller
{
    public function __construct(
        private readonly ProfileService $profile,
        private readonly ProfilePhotoService $photos,
        private readonly AccountDeletionService $accounts,
    ) {}

    public function update(UpdateProfileRequest $request)
    {
        $user = $this->profile->update(
            $request->user(),
            $request->validated(),
            $request->user()->currentAccessToken(),
        );

        return ApiResponse::success(
            (new UserResource($user))->resolve($request),
            'Profile updated successfully.',
        );
    }

    public function changePassword(ChangePasswordRequest $request)
    {
        $revoked = $this->profile->changePassword(
            $request->user(),
            $request->user()->currentAccessToken(),
            $request->validated('current_password'),
            $request->validated('password'),
        );

        return ApiResponse::success(
            ['sessions_revoked' => $revoked],
            'Password changed successfully.',
        );
    }

    public function uploadPhoto(UploadProfilePhotoRequest $request)
    {
        $user = $this->photos->replace(
            $request->user(),
            $request->file('photo'),
        );

        return ApiResponse::success(
            (new UserResource($user))->resolve($request),
            'Profile photo updated successfully.',
        );
    }

    public function removePhoto(Request $request)
    {
        $user = $this->photos->remove($request->user());

        return ApiResponse::success(
            (new UserResource($user))->resolve($request),
            'Profile photo removed successfully.',
        );
    }

    public function settings(UpdateSettingsRequest $request)
    {
        $user = $this->profile->settings(
            $request->user(),
            $request->validated(),
        );

        return ApiResponse::success(
            (new UserResource($user))->resolve($request),
            'Settings updated successfully.',
        );
    }

    public function deleteAccount(DeleteAccountRequest $request)
    {
        $this->accounts->delete(
            $request->user(),
            $request->user()->currentAccessToken(),
            $request->validated(),
        );

        return ApiResponse::noContent();
    }
}
