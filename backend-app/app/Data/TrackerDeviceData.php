<?php

namespace App\Data;

use Carbon\CarbonImmutable;

final readonly class TrackerDeviceData
{
    public function __construct(
        public int $providerDeviceId,
        public string $uniqueId,
        public ?string $connectionStatus,
        public ?CarbonImmutable $lastCommunicationAt,
    ) {}
}
