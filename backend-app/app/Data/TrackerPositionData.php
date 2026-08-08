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
        public bool $approximate = false,
        public ?float $accuracyMeters = null,
        /** @var list<array{macAddress: string, signalStrength: int}> */
        public array $wifiAccessPoints = [],
    ) {}

    public function withResolvedLocation(
        float $latitude,
        float $longitude,
        float $accuracyMeters,
    ): self {
        return new self(
            providerPositionId: $this->providerPositionId,
            providerDeviceId: $this->providerDeviceId,
            latitude: $latitude,
            longitude: $longitude,
            recordedAt: $this->recordedAt,
            gnssValid: true,
            batteryPercentage: $this->batteryPercentage,
            satellites: $this->satellites,
            hdop: $this->hdop,
            gsmCsq: $this->gsmCsq,
            powerState: $this->powerState,
            firmwareVersion: $this->firmwareVersion,
            resetReason: $this->resetReason,
            approximate: true,
            accuracyMeters: $accuracyMeters,
            wifiAccessPoints: $this->wifiAccessPoints,
        );
    }
}
