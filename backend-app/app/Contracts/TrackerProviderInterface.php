<?php

namespace App\Contracts;

use App\Data\TrackerDeviceData;
use App\Data\TrackerPositionData;
use Carbon\CarbonInterface;
use Illuminate\Support\Collection;

interface TrackerProviderInterface
{
    public function findDeviceByUniqueId(string $uniqueId): ?TrackerDeviceData;

    public function getDeviceState(int $providerDeviceId): ?TrackerDeviceData;

    /**
     * @return Collection<int, TrackerPositionData>
     */
    public function getPositions(
        int $providerDeviceId,
        CarbonInterface $from,
        CarbonInterface $to,
    ): Collection;
}
