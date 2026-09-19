<?php

namespace App\Services;

use App\Data\TrackerPositionData;
use App\Enums\ActivitySource;
use App\Enums\BatteryStatus;
use App\Enums\ConnectionStatus;
use App\Enums\DeviceActivityType;
use App\Enums\NotificationType;
use App\Models\Device;
use App\Models\User;
use Carbon\CarbonImmutable;

class DeviceTransitionService
{
    public function __construct(
        private readonly StatusNormalizationService $status,
        private readonly DeviceActivityService $activity,
        private readonly NotificationService $notifications,
    ) {}

    public function battery(
        User $user,
        Device $device,
        TrackerPositionData $position,
    ): void {
        if ($position->batteryPercentage === null || $position->recordedAt === null) {
            return;
        }

        $current = $this->status->battery($position->batteryPercentage);
        $previous = $device->notification_battery_state
            ? BatteryStatus::tryFrom($device->notification_battery_state)
            : null;

        $device->notification_battery_state = $current->value;

        $eventType = match (true) {
            $current === BatteryStatus::Critical && $previous !== BatteryStatus::Critical => DeviceActivityType::BatteryCritical,
            $current === BatteryStatus::Low
                && ($previous === null || $previous === BatteryStatus::Normal) => DeviceActivityType::BatteryLow,
            default => null,
        };

        if ($eventType === null) {
            return;
        }

        $critical = $eventType === DeviceActivityType::BatteryCritical;
        $notificationType = $critical
            ? NotificationType::BatteryCritical
            : NotificationType::BatteryLow;
        $label = $critical ? 'critical' : 'low';
        $eventKey = implode(':', [
            'battery',
            $device->id,
            $device->claim_version,
            $position->providerPositionId,
            $current->value,
        ]);

        $this->activity->record(
            $user,
            $device,
            $eventType,
            ActivitySource::Tracker,
            "Battery {$label}",
            "{$device->item_name} battery is {$label}.",
            ['battery_status' => $current->value],
            $eventKey,
            $position->recordedAt,
        );
        $this->notifications->create(
            $user,
            $device,
            $notificationType,
            "Battery {$label}",
            "{$device->item_name} battery is {$label}.",
            $eventKey,
        );
    }

    public function connection(User $user, Device $device, ?CarbonImmutable $evaluatedAt = null): void
    {
        $evaluatedAt ??= CarbonImmutable::now('UTC');
        $current = $this->status->connection($device, $evaluatedAt);

        if ($current === ConnectionStatus::Unknown) {
            return;
        }

        $previous = $device->notification_connection_state
            ? ConnectionStatus::tryFrom($device->notification_connection_state)
            : null;

        if ($previous === null) {
            $device->notification_connection_state = $current->value;
            $device->notification_connection_reference_at = $device->last_communication_at;

            return;
        }

        if ($previous === ConnectionStatus::Offline) {
            $reference = $device->notification_connection_reference_at;
            $newCommunication = $device->last_communication_at
                && (! $reference || $device->last_communication_at->greaterThan($reference));

            if ($current !== ConnectionStatus::Online || ! $newCommunication) {
                return;
            }

            $eventKey = $this->connectionKey($device, 'online');
            $this->activity->record(
                $user,
                $device,
                DeviceActivityType::DeviceOnline,
                ActivitySource::Tracker,
                'Item is online',
                'Back online.',
                eventKey: $eventKey,
                occurredAt: $device->last_communication_at,
            );
            $this->notifications->create(
                $user,
                $device,
                NotificationType::DeviceOnline,
                'Item is online',
                'Back online.',
                $eventKey,
            );

            $device->notification_connection_state = ConnectionStatus::Online->value;
            $device->notification_connection_reference_at = $device->last_communication_at;

            return;
        }

        if ($current === ConnectionStatus::Stale) {
            return;
        }

        if ($current === ConnectionStatus::Offline) {
            $ageDerived = $device->last_provider_connection_status !== 'offline';
            $occurredAt = $ageDerived && $device->last_communication_at
                ? CarbonImmutable::instance($device->last_communication_at)
                    ->addSeconds((int) config('latch.tracker.offline_seconds'))
                : $evaluatedAt;
            $source = $ageDerived ? ActivitySource::System : ActivitySource::Tracker;
            $eventKey = $this->connectionKey($device, 'offline');

            $this->activity->record(
                $user,
                $device,
                DeviceActivityType::DeviceOffline,
                $source,
                'Item is offline',
                "{$device->item_name} is offline.",
                eventKey: $eventKey,
                occurredAt: $occurredAt,
            );
            $this->notifications->create(
                $user,
                $device,
                NotificationType::DeviceOffline,
                'Item is offline',
                "{$device->item_name} is offline.",
                $eventKey,
            );

            $device->notification_connection_state = ConnectionStatus::Offline->value;
            $device->notification_connection_reference_at = $device->last_communication_at;

            return;
        }

        if ($current === ConnectionStatus::Online) {
            $device->notification_connection_state = $current->value;
            $device->notification_connection_reference_at = $device->last_communication_at;
        }
    }

    private function connectionKey(Device $device, string $transition): string
    {
        $communication = $device->last_communication_at?->utc()->format('Y-m-d\TH:i:s.v\Z')
            ?? 'none';

        return implode(':', [
            $transition,
            $device->id,
            $device->claim_version,
            $communication,
        ]);
    }
}
