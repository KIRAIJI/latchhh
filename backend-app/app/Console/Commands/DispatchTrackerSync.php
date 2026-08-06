<?php

namespace App\Console\Commands;

use App\Jobs\SyncDevice;
use App\Models\Device;
use Illuminate\Console\Command;

class DispatchTrackerSync extends Command
{
    protected $signature = 'latch:dispatch-sync';

    protected $description = 'Dispatch synchronization jobs for claimed LATCH devices';

    public function handle(): int
    {
        $count = 0;

        Device::query()
            ->whereNotNull('user_id')
            ->whereNotNull('claimed_at')
            ->whereNotNull('tracker_device_id')
            ->chunkById(100, function ($devices) use (&$count): void {
                foreach ($devices as $device) {
                    SyncDevice::dispatch(
                        $device->id,
                        $device->user_id,
                        $device->claim_version,
                        $device->claimed_at->utc()->toISOString(),
                    )->onConnection('database');
                    $count++;
                }
            });

        $this->info("Dispatched {$count} synchronization job(s).");

        return self::SUCCESS;
    }
}
