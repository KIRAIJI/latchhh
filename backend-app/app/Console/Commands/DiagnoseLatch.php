<?php

namespace App\Console\Commands;

use App\Models\Device;
use App\Models\DeviceActivityLog;
use App\Models\DevicePosition;
use App\Models\Geofence;
use App\Models\Notification;
use App\Models\User;
use App\Support\TrackerTransportPolicy;
use Illuminate\Console\Command;
use Illuminate\Console\Scheduling\Schedule;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Schema;
use Throwable;

class DiagnoseLatch extends Command
{
    protected $signature = 'latch:diagnose';

    protected $description = 'Run read-only LATCH configuration and reachability diagnostics';

    public function handle(Schedule $schedule): int
    {
        $healthy = true;
        $cacheTable = (string) config('cache.stores.database.table', 'cache');
        $queueTable = (string) config('queue.connections.database.table', 'jobs');
        $failedTable = (string) config('queue.failed.table', 'failed_jobs');
        $checks = [
            'database' => $this->check(fn () => DB::select('select 1')),
            'cache_table' => config('cache.default') === 'database'
                && $this->check(fn () => $this->assertTable($cacheTable)),
            'queue_table' => config('queue.default') === 'database'
                && $this->check(fn () => $this->assertTable($queueTable)),
            'failed_jobs_table' => $this->check(fn () => $this->assertTable($failedTable)),
            'scheduler_registration' => $this->scheduleConfigured($schedule),
            'public_storage_link' => file_exists(public_path('storage')),
            'tracker_configuration' => $this->trackerConfigured(),
        ];

        foreach ($checks as $name => $passed) {
            $healthy = $healthy && $passed;
            $this->line(strtoupper($name).': '.($passed ? 'PASS' : 'FAIL'));
        }

        if ($checks['database']) {
            try {
                $cutoff = now()->subSeconds((int) config('latch.tracker.sync_stale_seconds'));
                $stale = Device::query()
                    ->whereNotNull('user_id')
                    ->whereRaw('COALESCE(last_synced_at, claimed_at) < ?', [$cutoff])
                    ->count();
                $failedJobs = Schema::hasTable($failedTable)
                    ? DB::table($failedTable)->count()
                    : 0;

                $counts = [
                    'users' => User::query()->count(),
                    'claimed_devices' => Device::query()->whereNotNull('user_id')->count(),
                    'unclaimed_devices' => Device::query()->whereNull('user_id')->count(),
                    'stale_synchronizations' => $stale,
                    'geofences' => Geofence::query()->count(),
                    'unread_notifications' => Notification::query()->whereNull('read_at')->count(),
                    'retained_positions' => DevicePosition::query()->count(),
                    'activity_rows' => DeviceActivityLog::query()->count(),
                    'queued_jobs' => Schema::hasTable($queueTable)
                        ? DB::table($queueTable)->count()
                        : 0,
                    'failed_jobs' => $failedJobs,
                ];

                foreach ($counts as $name => $count) {
                    $this->line(strtoupper($name).": {$count}");
                }

                $healthy = $healthy && $stale === 0 && $failedJobs === 0;
            } catch (Throwable) {
                $this->line('AGGREGATE_DATA: FAIL');
                $healthy = false;
            }
        }

        $this->newLine();
        $this->warn(
            'Read-only checks do not prove cache writes, queue-worker liveness, or cron execution.'
        );

        return $healthy ? self::SUCCESS : self::FAILURE;
    }

    private function check(callable $check): bool
    {
        try {
            $check();

            return true;
        } catch (Throwable) {
            return false;
        }
    }

    private function assertTable(string $table): void
    {
        if (! Schema::hasTable($table)) {
            throw new \RuntimeException('Required table is unavailable.');
        }

        DB::table($table)->limit(1)->get();
    }

    private function scheduleConfigured(Schedule $schedule): bool
    {
        $names = collect($schedule->events())->pluck('description');

        return $names->contains('latch.sync')
            && $names->contains('latch.prune')
            && $names->contains('latch.sanctum-prune');
    }

    private function trackerConfigured(): bool
    {
        $baseUrl = trim((string) config('latch.traccar.base_url'));
        $parts = parse_url($baseUrl);
        $transportAllowed = is_array($parts)
            && TrackerTransportPolicy::allows($parts);

        return config('latch.tracker.provider') === 'traccar'
            && is_array($parts)
            && isset($parts['scheme'], $parts['host'])
            && ($parts['path'] ?? null) === '/api'
            && ! isset($parts['user'], $parts['pass'], $parts['query'], $parts['fragment'])
            && $transportAllowed
            && (string) config('latch.traccar.username') !== ''
            && (string) config('latch.traccar.password') !== '';
    }
}
