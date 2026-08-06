<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;
use Illuminate\Support\Facades\Storage;

class UserResource extends JsonResource
{
    public function toArray(Request $request): array
    {
        $photoUrl = $this->profile_photo_path
            ? Storage::disk('public')->url($this->profile_photo_path)
            : null;

        if ($photoUrl) {
            $baseUrl = rtrim($request->getSchemeAndHttpHost().$request->getBaseUrl(), '/');
            $photoPath = parse_url($photoUrl, PHP_URL_PATH) ?: $photoUrl;
            $photoUrl = $baseUrl.'/'.ltrim($photoPath, '/');
        }

        return [
            'id' => $this->id,
            'name' => $this->name,
            'email' => $this->email,
            'email_verified' => $this->hasVerifiedEmail(),
            'email_verified_at' => $this->email_verified_at?->utc()->toISOString(),
            'has_password' => $this->password_set_at !== null,
            'oauth_providers' => $this->oauthIdentities()
                ->orderBy('provider')
                ->pluck('provider')
                ->all(),
            'profile_photo_url' => $photoUrl,
            'notifications_enabled' => (bool) $this->notifications_enabled,
            'notification_preferences' => [
                'geofence_events' => (bool) $this->notify_geofence_events,
                'battery_events' => (bool) $this->notify_battery_events,
                'device_status_events' => (bool) $this->notify_device_status_events,
            ],
            'created_at' => $this->created_at?->utc()->toISOString(),
            'updated_at' => $this->updated_at?->utc()->toISOString(),
        ];
    }
}
