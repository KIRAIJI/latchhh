<?php

namespace App\Data;

use Carbon\CarbonImmutable;

final readonly class TrackerPositionData
{
    public function __construct(
        public int $providerPositionId,
        public int $providerDeviceId,
        public ?float $latitude,
        public ?float $longitude,
        public ?CarbonImmutable $recordedAt,
        public ?bool $gnssValid,
        public ?int $batteryPercentage,
        public ?int $satellites,
        public ?float $hdop,
        public ?int $gsmCsq,
        public ?string $powerState = null,
        public ?string $firmwareVersion = null,
        public ?string $resetReason = null,
    ) {}
}
