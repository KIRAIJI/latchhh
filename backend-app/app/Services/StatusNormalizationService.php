<?php

namespace App\Services;

use App\Enums\BatteryStatus;
use App\Enums\ConnectionStatus;
use App\Enums\GsmSignalLevel;
use App\Enums\LocationType;
use App\Models\Device;
use Carbon\CarbonInterface;

class StatusNormalizationService
{
    public function connection(Device $device, ?CarbonInterface $now = null): ConnectionStatus
    {
        if ($device->last_communication_at === null) {
            return ConnectionStatus::Unknown;
        }

        $now ??= now();
        $age = max(0, $device->last_communication_at->diffInSeconds($now));

        if (
            $device->last_provider_connection_status === 'offline'
            || $age > config('latch.tracker.offline_seconds')
        ) {
            return ConnectionStatus::Offline;
        }

        if (
            $device->last_provider_connection_status === 'online'
            && $age <= config('latch.tracker.current_seconds')
        ) {
            return ConnectionStatus::Online;
        }

        return ConnectionStatus::Stale;
    }

    public function location(Device $device, ?CarbonInterface $now = null): LocationType
    {
        if (
            $device->last_latitude === null
            || $device->last_longitude === null
            || $device->last_position_at === null
        ) {
            return LocationType::Unavailable;
        }

        $now ??= now();
        $age = max(0, $device->last_position_at->diffInSeconds($now));

        return $age <= config('latch.tracker.current_seconds')
            ? LocationType::Current
            : LocationType::LastKnown;
    }

    public function battery(?int $percentage): BatteryStatus
    {
        if ($percentage === null) {
            return BatteryStatus::Unknown;
        }

        if ($percentage <= config('latch.battery.critical_percentage')) {
            return BatteryStatus::Critical;
        }

        if ($percentage <= config('latch.battery.low_percentage')) {
            return BatteryStatus::Low;
        }

        return BatteryStatus::Normal;
    }

    public function signal(?int $csq): GsmSignalLevel
    {
        if ($csq === null) {
            return GsmSignalLevel::Unknown;
        }

        if ($csq === 99) {
            return GsmSignalLevel::Unknown;
        }

        return match (true) {
            $csq >= 20 => GsmSignalLevel::Excellent,
            $csq >= 15 => GsmSignalLevel::Good,
            $csq >= 10 => GsmSignalLevel::Fair,
            $csq >= 2 => GsmSignalLevel::Poor,
            default => GsmSignalLevel::NoSignal,
        };
    }
}
