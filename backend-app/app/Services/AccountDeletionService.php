<?php

namespace App\Services;

use App\Exceptions\ApiException;
use App\Jobs\DeleteFirebaseAuthUser;
use App\Jobs\DeleteStoredProfilePhoto;
use App\Models\Device;
use App\Models\DeviceActivityLog;
use App\Models\DevicePosition;
use App\Models\Geofence;
use App\Models\Notification;
use App\Models\TrackerPositionReceipt;
use App\Models\User;
use Illuminate\Support\Facades\DB;
use Throwable;

class AccountDeletionService
{
    public function __construct(
        private readonly AuthService $auth,
        private readonly ProfileService $profile,
        private readonly DeviceClaimService $devices,
    ) {}

    /**
     * @param  array{current_password?: string|null, oauth_id_token?: string|null}  $credentials
     */
    public function delete(User $user, mixed $currentToken, array $credentials): void
    {
        try {
            DB::transaction(function () use ($user, $currentToken, $credentials): void {
                $locked = User::query()->lockForUpdate()->findOrFail($user->id);
                $this->auth->requireActiveToken($locked, $currentToken);

                if ($locked->password_set_at !== null) {
                    $this->profile->verifyCurrentPassword(
                        $locked,
                        (string) ($credentials['current_password'] ?? ''),
                    );
                } else {
                    $this->auth->verifyRecentGoogleIdentity(
                        $locked,
                        (string) ($credentials['oauth_id_token'] ?? ''),
                    );
                }

                $firebaseUids = $locked->oauthIdentities()
                    ->pluck('firebase_uid')
                    ->unique()
                    ->values()
                    ->all();

                $ownedDevices = Device::query()
                    ->where('user_id', $locked->id)
                    ->orderBy('id')
                    ->lockForUpdate()
                    ->get();

                $deviceIds = $ownedDevices->modelKeys();

                if ($deviceIds !== []) {
                    Geofence::query()
                        ->whereIn('device_id', $deviceIds)
                        ->orderBy('id')
                        ->lockForUpdate()
                        ->get();

                    Geofence::query()->whereIn('device_id', $deviceIds)->delete();
                    Notification::query()->whereIn('device_id', $deviceIds)->delete();
                    TrackerPositionReceipt::query()->whereIn('device_id', $deviceIds)->delete();
                    DevicePosition::query()->whereIn('device_id', $deviceIds)->delete();
                }

                foreach ($ownedDevices as $device) {
                    $device->claim_version++;
                    $this->devices->clearClaimTelemetry($device);
                    $device->user_id = null;
                    $device->item_name = null;
                    $device->claimed_at = null;
                    $device->tracker_cursor_at = null;
                    $device->save();
                }

                Notification::query()->where('user_id', $locked->id)->delete();
                DeviceActivityLog::query()->where('user_id', $locked->id)->delete();
                DevicePosition::query()->where('user_id', $locked->id)->delete();
                TrackerPositionReceipt::query()->where('user_id', $locked->id)->delete();
                $locked->tokens()->delete();

                $photoPath = $locked->profile_photo_path;
                $locked->delete();

                foreach ($firebaseUids as $firebaseUid) {
                    DeleteFirebaseAuthUser::dispatch($firebaseUid)->afterCommit();
                }

                if ($photoPath) {
                    DeleteStoredProfilePhoto::dispatch($photoPath);
                }
            }, 3);
        } catch (ApiException $exception) {
            throw $exception;
        } catch (Throwable $exception) {
            report($exception);

            throw new ApiException(
                'ACCOUNT_DELETION_FAILED',
                'The account could not be deleted.',
                500,
            );
        }
    }
}
