<?php

namespace App\Services;

use App\Enums\NotificationType;
use App\Jobs\SendPushNotification;
use App\Models\Device;
use App\Models\Notification;
use App\Models\User;

class NotificationService
{
    public function create(
        User $user,
        Device $device,
        NotificationType $type,
        string $title,
        string $message,
        string $eventKey,
    ): ?Notification {
        if (! $user->notifications_enabled || ! $this->typeEnabled($user, $type)) {
            return null;
        }

        $notification = Notification::query()->createOrFirst(
            ['event_key' => $eventKey],
            [
                'user_id' => $user->id,
                'device_id' => $device->id,
                'type' => $type,
                'title' => $title,
                'message' => $message,
            ],
        );

        if ($notification->wasRecentlyCreated) {
            SendPushNotification::dispatch($notification->id)->afterCommit();
        }

        return $notification;
    }

    private function typeEnabled(User $user, NotificationType $type): bool
    {
        return match ($type) {
            NotificationType::GeofenceEnter,
            NotificationType::GeofenceExit => $user->notify_geofence_events,
            NotificationType::BatteryLow,
            NotificationType::BatteryCritical => $user->notify_battery_events,
            NotificationType::DeviceOffline,
            NotificationType::DeviceOnline => $user->notify_device_status_events,
        };
    }
}
