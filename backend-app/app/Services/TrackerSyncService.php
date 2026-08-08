<?php

namespace App\Services;

use App\Contracts\TrackerProviderInterface;
use App\Data\TrackerDeviceData;
use App\Data\TrackerPositionData;
use App\Enums\GnssStatus;
use App\Exceptions\TrackerProviderException;
use App\Models\Device;
use App\Models\DevicePosition;
use App\Models\TrackerPositionReceipt;
use App\Models\User;
use Carbon\CarbonImmutable;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\DB;

class TrackerSyncService
{
    public function __construct(
        private readonly TrackerProviderInterface $provider,
        private readonly GeofenceEvaluationService $geofences,
        private readonly DeviceTransitionService $transitions,
        private readonly PlaceNameResolver $places,
        private readonly WifiGeolocationResolver $wifiGeolocation,
    ) {}

    public function sync(
        int $deviceId,
        int $userId,
        int $claimVersion,
        string $claimedAt,
    ): void {
        $snapshot = Device::query()->find($deviceId);

        if (! $this->matchesFence($snapshot, $userId, $claimVersion, $claimedAt)) {
            return;
        }

        if ($snapshot->tracker_device_id === null) {
            return;
        }

        [$from, $to] = $this->window($snapshot);

        try {
            $state = $this->provider->getDeviceState($snapshot->tracker_device_id);

            if (! $state) {
                throw new TrackerProviderException('not_found', 'Tracker device was not found.');
            }

            $this->validateDeviceState($snapshot, $state);
            $positions = $this->provider->getPositions(
                $snapshot->tracker_device_id,
                $from,
                $to,
            );
            $positions = $positions
                ->map(fn (TrackerPositionData $position) => $this->wifiGeolocation->resolve($position));
            $this->validatePositionMappings($snapshot, $positions);
        } catch (TrackerProviderException $exception) {
            $this->evaluateOfflineOnly($deviceId, $userId, $claimVersion);
            throw $exception;
        }

        DB::transaction(function () use (
            $deviceId,
            $userId,
            $claimVersion,
            $claimedAt,
            $state,
            $positions,
            $to,
        ): void {
            $user = User::query()->lockForUpdate()->find($userId);
            $device = Device::query()->lockForUpdate()->find($deviceId);

            if (! $user || ! $this->matchesFence($device, $userId, $claimVersion, $claimedAt)) {
                return;
            }

            $this->validateDeviceState($device, $state);
            $geofence = $device->geofence()->lockForUpdate()->first();
            $this->cacheDeviceState($device, $state);

            foreach ($positions as $position) {
                $this->processPosition($user, $device, $geofence, $position);
            }

            $this->transitions->connection($user, $device);
            $device->tracker_cursor_at = $to;
            $device->last_synced_at = now()->utc();
            $device->save();
        }, 3);

        $freshDevice = Device::query()->find($deviceId);
        if ($freshDevice) {
            $this->places->resolveForDevice($freshDevice);
        }
    }

    public function evaluateOfflineOnly(int $deviceId, int $userId, int $claimVersion): void
    {
        DB::transaction(function () use ($deviceId, $userId, $claimVersion): void {
            $user = User::query()->lockForUpdate()->find($userId);
            $device = Device::query()->lockForUpdate()->find($deviceId);

            if (
                ! $user
                || ! $device
                || $device->user_id !== $userId
                || $device->claim_version !== $claimVersion
            ) {
                return;
            }

            $this->transitions->connection($user, $device);
            $device->save();
        }, 3);
    }

    /**
     * @return array{CarbonImmutable, CarbonImmutable}
     */
    private function window(Device $device): array
    {
        $claimedAt = CarbonImmutable::instance($device->claimed_at);
        $cursor = $device->tracker_cursor_at
            ? CarbonImmutable::instance($device->tracker_cursor_at)
            : $claimedAt;
        $from = $cursor->equalTo($claimedAt)
            ? $claimedAt
            : $cursor
                ->subSeconds((int) config('latch.tracker.position_overlap_seconds'))
                ->max($claimedAt);
        $to = $cursor
            ->addMinutes((int) config('latch.tracker.sync_max_window_minutes'))
            ->min(CarbonImmutable::now('UTC'));

        return [$from, $to];
    }

    private function processPosition(
        User $user,
        Device $device,
        mixed $geofence,
        TrackerPositionData $position,
    ): void {
        if (
            $position->recordedAt === null
            || $position->recordedAt->lessThan($device->claimed_at)
        ) {
            return;
        }

        $receipt = TrackerPositionReceipt::query()->createOrFirst(
            [
                'device_id' => $device->id,
                'claim_version' => $device->claim_version,
                'provider_position_id' => $position->providerPositionId,
            ],
            [
                'user_id' => $user->id,
                'recorded_at' => $position->recordedAt,
                'created_at' => now()->utc(),
            ],
        );

        if (! $receipt->wasRecentlyCreated) {
            return;
        }

        $now = CarbonImmutable::now('UTC');
        $futureLimit = $now->addSeconds((int) config('latch.tracker.max_clock_skew_seconds'));
        $timestampAccepted = $position->recordedAt->lessThanOrEqualTo($futureLimit);
        $hasValidLocation = $timestampAccepted
            && $position->gnssValid === true
            && $position->latitude !== null
            && $position->longitude !== null
            && (! $position->approximate
                || ($position->accuracyMeters !== null
                    && $position->accuracyMeters <= (float) config(
                        'latch.location_history.max_approximate_accuracy_meters'
                    )));
        $locationSource = $position->approximate ? 'wifi' : 'gnss';

        if (
            $hasValidLocation
            && $position->recordedAt->greaterThanOrEqualTo(
                $now->subDays((int) config('latch.location_history.retention_days'))
            )
        ) {
            DevicePosition::query()->createOrFirst(
                [
                    'device_id' => $device->id,
                    'claim_version' => $device->claim_version,
                    'provider_position_id' => $position->providerPositionId,
                ],
                [
                    'user_id' => $user->id,
                    'latitude' => $position->latitude,
                    'longitude' => $position->longitude,
                    'source' => $locationSource,
                    'accuracy_meters' => $position->accuracyMeters,
                    'recorded_at' => $position->recordedAt,
                    'created_at' => now()->utc(),
                ],
            );
        }

        $newerLocation = $hasValidLocation && $this->newerThan(
            $position,
            $device->last_position_at,
            $device->last_provider_position_id,
        );
        $newerTelemetry = $timestampAccepted && $this->newerThan(
            $position,
            $device->last_telemetry_at,
            $device->last_telemetry_position_id,
        );

        if ($newerTelemetry) {
            $device->last_telemetry_at = $position->recordedAt;
            $device->last_telemetry_position_id = $position->providerPositionId;
            $device->last_battery_percentage = $position->batteryPercentage;
            $device->last_gnss_status = match ($position->gnssValid) {
                true => $position->approximate ? GnssStatus::NoFix : GnssStatus::Fixed,
                false => GnssStatus::NoFix,
                null => GnssStatus::Unknown,
            };
            $device->last_satellites = $position->satellites;
            $device->last_hdop = $position->hdop;
            $device->last_gsm_csq = $position->gsmCsq;
            $device->last_power_state = $position->powerState;
            $device->last_firmware_version = $position->firmwareVersion;
            $device->last_reset_reason = $position->resetReason;
            $this->transitions->battery($user, $device, $position);
        }

        if ($newerLocation) {
            $device->last_latitude = $position->latitude;
            $device->last_longitude = $position->longitude;
            $device->last_location_source = $locationSource;
            $device->last_location_accuracy_meters = $position->accuracyMeters;
            $device->last_place_name = null;
            $device->last_place_resolved_at = null;
            $device->last_position_at = $position->recordedAt;
            $device->last_provider_position_id = $position->providerPositionId;

            if ($geofence) {
                $this->geofences->evaluate($user, $device, $geofence, $position);
            }
        }
    }

    private function cacheDeviceState(Device $device, TrackerDeviceData $state): void
    {
        if (
            $state->lastCommunicationAt === null
            || $state->lastCommunicationAt->lessThan($device->claimed_at)
            || ($device->last_communication_at
                && $state->lastCommunicationAt->lessThan($device->last_communication_at))
        ) {
            return;
        }

        $device->last_communication_at = $state->lastCommunicationAt;
        $device->last_provider_connection_status = $state->connectionStatus;
    }

    private function validateDeviceState(Device $device, TrackerDeviceData $state): void
    {
        if (
            $state->providerDeviceId <= 0
            || $state->providerDeviceId !== $device->tracker_device_id
            || $state->uniqueId !== $device->tracker_unique_id
        ) {
            throw new TrackerProviderException('contract', 'Tracker device identifiers do not match.');
        }
    }

    /**
     * @param  Collection<int, TrackerPositionData>  $positions
     */
    private function validatePositionMappings(Device $device, Collection $positions): void
    {
        foreach ($positions as $position) {
            if (
                ! $position instanceof TrackerPositionData
                || $position->providerPositionId <= 0
                || $position->providerDeviceId <= 0
                || $position->providerDeviceId !== $device->tracker_device_id
            ) {
                throw new TrackerProviderException('contract', 'Tracker position device ID does not match.');
            }
        }
    }

    private function matchesFence(
        ?Device $device,
        int $userId,
        int $claimVersion,
        string $claimedAt,
    ): bool {
        return $device
            && $device->user_id === $userId
            && $device->claim_version === $claimVersion
            && $device->claimed_at?->utc()->toISOString()
                === CarbonImmutable::parse($claimedAt)->utc()->toISOString();
    }

    private function newerThan(
        TrackerPositionData $position,
        mixed $currentTime,
        ?int $currentId,
    ): bool {
        if ($currentTime === null || $currentId === null) {
            return true;
        }

        $positionTime = $position->recordedAt->getTimestampMs();
        $currentTimestamp = $currentTime->getTimestampMs();

        return $positionTime > $currentTimestamp
            || ($positionTime === $currentTimestamp
                && $position->providerPositionId > $currentId);
    }
}
