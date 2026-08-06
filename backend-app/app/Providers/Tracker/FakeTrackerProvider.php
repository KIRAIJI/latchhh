<?php

namespace App\Providers\Tracker;

use App\Contracts\TrackerProviderInterface;
use App\Data\TrackerDeviceData;
use App\Data\TrackerPositionData;
use Carbon\CarbonInterface;
use Illuminate\Support\Collection;

class FakeTrackerProvider implements TrackerProviderInterface
{
    /** @var array<int, TrackerDeviceData> */
    private array $devices = [];

    /** @var array<int, Collection<int, TrackerPositionData>> */
    private array $positions = [];

    public function addDevice(TrackerDeviceData $device): self
    {
        $this->devices[$device->providerDeviceId] = $device;

        return $this;
    }

    /**
     * @param  iterable<TrackerPositionData>  $positions
     */
    public function setPositions(int $providerDeviceId, iterable $positions): self
    {
        $this->positions[$providerDeviceId] = collect($positions)->values();

        return $this;
    }

    public function findDeviceByUniqueId(string $uniqueId): ?TrackerDeviceData
    {
        return collect($this->devices)
            ->first(fn (TrackerDeviceData $device) => $device->uniqueId === $uniqueId);
    }

    public function getDeviceState(int $providerDeviceId): ?TrackerDeviceData
    {
        return $this->devices[$providerDeviceId] ?? null;
    }

    public function getPositions(
        int $providerDeviceId,
        CarbonInterface $from,
        CarbonInterface $to,
    ): Collection {
        return ($this->positions[$providerDeviceId] ?? collect())
            ->filter(fn (TrackerPositionData $position) => $position->recordedAt !== null
                && $position->recordedAt->greaterThanOrEqualTo($from)
                && $position->recordedAt->lessThanOrEqualTo($to))
            ->sort(function (TrackerPositionData $left, TrackerPositionData $right): int {
                $timeComparison = $left->recordedAt?->getTimestampMs()
                    <=> $right->recordedAt?->getTimestampMs();

                return $timeComparison !== 0
                    ? $timeComparison
                    : $left->providerPositionId <=> $right->providerPositionId;
            })
            ->values();
    }
}
