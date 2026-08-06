<?php

namespace App\Jobs;

use App\Exceptions\TrackerProviderException;
use App\Services\TrackerSyncService;
use Carbon\CarbonImmutable;
use Illuminate\Contracts\Queue\ShouldBeUnique;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Foundation\Queue\Queueable;
use Illuminate\Queue\Middleware\WithoutOverlapping;
use Throwable;

class SyncDevice implements ShouldBeUnique, ShouldQueue
{
    use Queueable;

    public int $tries = 3;

    public int $timeout = 90;

    public int $uniqueFor = 180;

    public readonly string $dispatchedAt;

    public function __construct(
        public readonly int $deviceId,
        public readonly int $userId,
        public readonly int $claimVersion,
        public readonly string $claimedAt,
    ) {
        $this->dispatchedAt = CarbonImmutable::now('UTC')->toISOString();
        $this->onConnection('database');
    }

    public function uniqueId(): string
    {
        return "{$this->deviceId}:{$this->claimVersion}";
    }

    public function middleware(): array
    {
        return [
            (new WithoutOverlapping('sync:'.$this->uniqueId()))
                ->releaseAfter(60)
                ->expireAfter(180),
        ];
    }

    public function backoff(): array
    {
        return [60, 300];
    }

    public function retryUntil(): CarbonImmutable
    {
        return CarbonImmutable::parse($this->dispatchedAt)->addMinutes(30);
    }

    public function handle(TrackerSyncService $service): void
    {
        $service->sync(
            $this->deviceId,
            $this->userId,
            $this->claimVersion,
            $this->claimedAt,
        );
    }

    public function failed(Throwable $exception): void
    {
        $category = $exception instanceof TrackerProviderException
            ? $exception->category
            : 'internal';

        logger()->warning('Tracker synchronization exhausted its retries.', [
            'device_id' => $this->deviceId,
            'claim_version' => $this->claimVersion,
            'category' => $category,
        ]);
    }
}
