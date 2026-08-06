<?php

namespace App\Services;

use App\Jobs\DeleteStoredProfilePhoto;
use App\Models\User;
use Illuminate\Http\UploadedFile;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Storage;
use Throwable;

class ProfilePhotoService
{
    public function replace(User $user, UploadedFile $photo): User
    {
        $path = $photo->store('profile-photos/'.$user->id, 'public');

        if (! is_string($path) || $path === '') {
            throw new \RuntimeException('Profile photo storage failed.');
        }

        try {
            return DB::transaction(function () use ($user, $path): User {
                $locked = User::query()->lockForUpdate()->findOrFail($user->id);
                $oldPath = $locked->profile_photo_path;

                $locked->forceFill(['profile_photo_path' => $path])->save();

                if ($oldPath) {
                    DeleteStoredProfilePhoto::dispatch($oldPath);
                }

                return $locked->refresh();
            });
        } catch (Throwable $exception) {
            Storage::disk('public')->delete($path);
            throw $exception;
        }
    }

    public function remove(User $user): User
    {
        return DB::transaction(function () use ($user): User {
            $locked = User::query()->lockForUpdate()->findOrFail($user->id);
            $oldPath = $locked->profile_photo_path;

            if ($oldPath !== null) {
                $locked->forceFill(['profile_photo_path' => null])->save();
                DeleteStoredProfilePhoto::dispatch($oldPath);
            }

            return $locked->refresh();
        });
    }
}
