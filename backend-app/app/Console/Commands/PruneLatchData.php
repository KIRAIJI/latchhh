<?php

namespace App\Console\Commands;

use App\Models\DeviceActivityLog;
use App\Models\DevicePosition;
use App\Models\TrackerPositionReceipt;
use App\Models\User;
use Illuminate\Console\Command;
use Illuminate\Database\Eloquent\Builder;
use Illuminate\Support\Facades\Storage;
use Throwable;

class PruneLatchData extends Command
{
    protected $signature = 'latch:prune';

    protected $description = 'Prune expired LATCH receipts, history, activity, and orphaned photos';

    public function handle(): int
    {
        $receipts = $this->deleteInChunks(
            TrackerPositionReceipt::query()->where('created_at', '<', now()->subDay()),
            'created_at',
        );
        $positions = $this->deleteInChunks(
            DevicePosition::query()->where(
                'recorded_at',
                '<',
                now()->subDays((int) config('latch.location_history.retention_days')),
            ),
            'recorded_at',
        );
        $activity = $this->deleteInChunks(
            DeviceActivityLog::query()->where(
                'occurred_at',
                '<',
                now()->subDays((int) config('latch.activity_history.retention_days')),
            ),
            'occurred_at',
        );
        $photos = $this->prunePhotos();

        $this->info(
            "Pruned {$receipts} receipt(s), {$positions} position(s), "
            ."{$activity} activity row(s), and {$photos} orphan photo(s)."
        );

        return self::SUCCESS;
    }

    private function deleteInChunks(Builder $query, string $timestampColumn): int
    {
        $deleted = 0;

        do {
            $ids = (clone $query)
                ->orderBy($timestampColumn)
                ->orderBy('id')
                ->limit(500)
                ->pluck('id');
            $count = $ids->isEmpty()
                ? 0
                : $query->getModel()->newQuery()->whereKey($ids)->delete();
            $deleted += $count;
        } while ($count === 500);

        return $deleted;
    }

    private function prunePhotos(): int
    {
        $disk = Storage::disk('public');
        $cutoff = now()->subDay()->timestamp;
        $deleted = 0;

        foreach ($disk->allFiles('profile-photos') as $path) {
            try {
                if (
                    preg_match(
                        '#^profile-photos/[1-9]\d*/[A-Za-z0-9]{40}\.(?:jpe?g|png|webp)$#i',
                        $path,
                    ) !== 1
                ) {
                    continue;
                }

                if ($disk->lastModified($path) >= $cutoff) {
                    continue;
                }

                $referenced = User::query()
                    ->where('profile_photo_path', $path)
                    ->exists();

                if (! $referenced && $disk->delete($path)) {
                    $deleted++;
                }
            } catch (Throwable) {
                report(new \RuntimeException('Managed profile-photo orphan cleanup failed.'));
            }
        }

        return $deleted;
    }
}
