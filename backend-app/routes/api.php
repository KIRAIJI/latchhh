<?php

use App\Http\Controllers\Api\V1\ActivityController;
use App\Http\Controllers\Api\V1\AuthController;
use App\Http\Controllers\Api\V1\EmailVerificationController;
use App\Http\Controllers\Api\V1\GeofenceController;
use App\Http\Controllers\Api\V1\ItemController;
use App\Http\Controllers\Api\V1\LocationHistoryController;
use App\Http\Controllers\Api\V1\NotificationController;
use App\Http\Controllers\Api\V1\PasswordResetController;
use App\Http\Controllers\Api\V1\ProfileController;
use App\Http\Controllers\Api\V1\PushTokenController;
use App\Http\Controllers\Api\V1\SessionController;
use Illuminate\Support\Facades\Route;

Route::prefix('v1')->group(function (): void {
    Route::post('auth/register', [AuthController::class, 'register'])
        ->middleware('throttle:auth.register');
    Route::post('auth/login', [AuthController::class, 'login'])
        ->middleware('throttle:auth.login');
    Route::post('auth/oauth/google', [AuthController::class, 'google'])
        ->middleware('throttle:auth.oauth');
    Route::post('auth/forgot-password', [PasswordResetController::class, 'forgot'])
        ->middleware('throttle:auth.recovery');
    Route::post('auth/reset-password', [PasswordResetController::class, 'reset'])
        ->middleware('throttle:auth.recovery');
    Route::get('auth/password/reset-link', [PasswordResetController::class, 'bridge'])
        ->name('password.bridge')
        ->middleware('throttle:auth.recovery');
    Route::get('auth/email/verify/{id}/{hash}', [EmailVerificationController::class, 'verify'])
        ->whereNumber('id')
        ->name('verification.verify')
        ->middleware('throttle:auth.verification');

    Route::middleware('auth:sanctum')->group(function (): void {
        Route::get('auth/me', [AuthController::class, 'me']);
        Route::post('auth/logout', [AuthController::class, 'logout']);
        Route::post('auth/logout-all', [AuthController::class, 'logoutAll']);
        Route::get('auth/sessions', [SessionController::class, 'index']);
        Route::patch('auth/sessions/current', [SessionController::class, 'updateCurrent']);
        Route::delete('auth/sessions/{session}', [SessionController::class, 'destroy'])
            ->whereNumber('session')
            ->middleware('throttle:sensitive');
        Route::post(
            'auth/password/setup-link',
            [PasswordResetController::class, 'setupLink'],
        )->middleware('throttle:sensitive');
        Route::post(
            'auth/email/verification-notification',
            [EmailVerificationController::class, 'send'],
        )->middleware('throttle:auth.verification');

        Route::middleware('verified.api')->group(function (): void {
            Route::patch('profile', [ProfileController::class, 'update'])
                ->middleware('throttle:sensitive');
            Route::put('profile/password', [ProfileController::class, 'changePassword'])
                ->middleware('throttle:sensitive');
            Route::post('profile/photo', [ProfileController::class, 'uploadPhoto']);
            Route::delete('profile/photo', [ProfileController::class, 'removePhoto']);
            Route::patch('settings', [ProfileController::class, 'settings']);
            Route::delete('account', [ProfileController::class, 'deleteAccount'])
                ->middleware('throttle:sensitive');

            Route::get('items', [ItemController::class, 'index']);
            Route::post('items/claim', [ItemController::class, 'claim']);
            Route::post('items/{item}/refresh', [ItemController::class, 'refresh'])
                ->whereNumber('item')
                ->middleware('throttle:tracker.refresh');
            Route::get('items/{item}', [ItemController::class, 'show'])->whereNumber('item');
            Route::patch('items/{item}', [ItemController::class, 'update'])->whereNumber('item');
            Route::delete('items/{item}', [ItemController::class, 'destroy'])->whereNumber('item');

            Route::get('items/{item}/geofence', [GeofenceController::class, 'show'])->whereNumber('item');
            Route::put('items/{item}/geofence', [GeofenceController::class, 'upsert'])->whereNumber('item');
            Route::delete('items/{item}/geofence', [GeofenceController::class, 'destroy'])->whereNumber('item');

            Route::get('items/{item}/location-history', LocationHistoryController::class)
                ->whereNumber('item')
                ->middleware('throttle:history');

            Route::get('activity', [ActivityController::class, 'index'])
                ->middleware('throttle:activity');
            Route::get('items/{item}/activity', [ActivityController::class, 'item'])
                ->whereNumber('item')
                ->middleware('throttle:activity');

            Route::get('notifications', [NotificationController::class, 'index']);
            Route::patch('notifications/read-all', [NotificationController::class, 'readAll']);
            Route::patch('notifications/{notification}/read', [NotificationController::class, 'read'])
                ->whereNumber('notification');
            Route::delete('notifications/{notification}', [NotificationController::class, 'destroy'])
                ->whereNumber('notification');

            Route::post('push-tokens', [PushTokenController::class, 'store']);
            Route::delete('push-tokens', [PushTokenController::class, 'destroy']);
            Route::delete('push-tokens/all', [PushTokenController::class, 'destroyAll']);
        });
    });
});
