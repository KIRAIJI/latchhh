<?php

namespace App\Console\Commands;

use App\Contracts\TrackerProviderInterface;
use App\Exceptions\TrackerProviderException;
use App\Models\Device;
use Illuminate\Console\Command;
use Illuminate\Database\UniqueConstraintViolationException;

class RegisterDevice extends Command
{
    protected $signature = 'latch:register-device
        {device_uid : Public LATCH claim UID}
        {tracker_unique_id : Case-sensitive Traccar uniqueId}
        {--tracker-device-id= : Optional positive Traccar numeric ID}';

    protected $description = 'Pre-register a verified physical LATCH device';

    public function handle(TrackerProviderInterface $provider): int
    {
        $deviceUid = mb_strtoupper(trim((string) $this->argument('device_uid')));
        $trackerUniqueId = trim((string) $this->argument('tracker_unique_id'));
        $option = $this->option('tracker-device-id');

        if (preg_match('/^LATCH-[A-Z0-9]{4}-[A-Z0-9]{4}$/', $deviceUid) !== 1) {
            $this->error('The device UID format is invalid.');

            return self::INVALID;
        }

        if ($trackerUniqueId === '' || mb_strlen($trackerUniqueId) > 191) {
            $this->error('The tracker unique ID is invalid.');

            return self::INVALID;
        }

        $trackerDeviceId = null;

        if ($option !== null && $option !== '') {
            $trackerDeviceId = filter_var($option, FILTER_VALIDATE_INT, [
                'options' => ['min_range' => 1, 'max_range' => PHP_INT_MAX],
            ]);

            if (! is_int($trackerDeviceId)) {
                $this->error('The tracker device ID is invalid.');

                return self::INVALID;
            }
        }

        try {
            $tracker = $trackerDeviceId
                ? $provider->getDeviceState($trackerDeviceId)
                : $provider->findDeviceByUniqueId($trackerUniqueId);
        } catch (TrackerProviderException) {
            $this->error('Tracker verification failed.');

            return self::FAILURE;
        }

        if (
            ! $tracker
            || $tracker->uniqueId !== $trackerUniqueId
            || ($trackerDeviceId !== null && $tracker->providerDeviceId !== $trackerDeviceId)
        ) {
            $this->error('Tracker identifiers could not be verified.');

            return self::FAILURE;
        }

        try {
            Device::query()->create([
                'device_uid' => $deviceUid,
                'tracker_unique_id' => $trackerUniqueId,
                'tracker_device_id' => $tracker->providerDeviceId,
            ]);
        } catch (UniqueConstraintViolationException) {
            $this->error('A device with one of these identifiers already exists.');

            return self::FAILURE;
        }

        $this->info("Registered {$deviceUid}.");

        return self::SUCCESS;
    }
}
