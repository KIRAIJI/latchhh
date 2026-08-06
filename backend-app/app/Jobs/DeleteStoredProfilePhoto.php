<?php

namespace App\Jobs;

use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Foundation\Queue\Queueable;
use Illuminate\Support\Facades\Storage;
use InvalidArgumentException;
use Throwable;

class DeleteStoredProfilePhoto implements ShouldQueue
{
    use Queueable;

    public int $tries = 5;

    /**
     * @var list<int>
     */
    public array $backoff = [30, 120, 300, 900];

    public function __construct(public readonly string $path)
    {
        $this->onConnection('database');
    }

    public function handle(): void
    {
        if (
            ! str_starts_with($this->path, 'profile-photos/')
            || str_contains($this->path, '..')
            || str_starts_with($this->path, '/')
            || str_starts_with($this->path, '\\')
        ) {
            throw new InvalidArgumentException('Invalid managed profile-photo path.');
        }

        $disk = Storage::disk('public');

        if (! $disk->exists($this->path)) {
            return;
        }

        if (! $disk->delete($this->path) && $disk->exists($this->path)) {
            throw new \RuntimeException('Managed profile-photo cleanup failed.');
        }
    }

    public function failed(Throwable $exception): void
    {
        logger()->warning('Managed profile-photo cleanup exhausted its retries.');
    }
}
