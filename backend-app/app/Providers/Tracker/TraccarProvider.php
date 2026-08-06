<?php

namespace App\Providers\Tracker;

use App\Contracts\TrackerProviderInterface;
use App\Data\TrackerDeviceData;
use App\Data\TrackerPositionData;
use App\Exceptions\TrackerProviderException;
use App\Support\TrackerTransportPolicy;
use Carbon\CarbonImmutable;
use Carbon\CarbonInterface;
use Illuminate\Http\Client\ConnectionException;
use Illuminate\Http\Client\PendingRequest;
use Illuminate\Http\Client\RequestException;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\Http;
use Throwable;

class TraccarProvider implements TrackerProviderInterface
{
    public function findDeviceByUniqueId(string $uniqueId): ?TrackerDeviceData
    {
        $rows = $this->getJson('devices', ['uniqueId' => $uniqueId]);

        return $this->singleDevice($rows, uniqueId: $uniqueId);
    }

    public function getDeviceState(int $providerDeviceId): ?TrackerDeviceData
    {
        $rows = $this->getJson('devices', ['id' => $providerDeviceId]);

        return $this->singleDevice($rows, providerDeviceId: $providerDeviceId);
    }

    public function getPositions(
        int $providerDeviceId,
        CarbonInterface $from,
        CarbonInterface $to,
    ): Collection {
        $rows = $this->getJson('reports/route', [
            'deviceId' => $providerDeviceId,
            'from' => $from->copy()->utc()->format('Y-m-d\TH:i:s.v\Z'),
            'to' => $to->copy()->utc()->format('Y-m-d\TH:i:s.v\Z'),
        ]);

        if (! array_is_list($rows)) {
            throw new TrackerProviderException('contract', 'Tracker positions response is malformed.');
        }

        return collect($rows)
            ->map(fn (mixed $row): ?TrackerPositionData => $this->mapPosition($row))
            ->filter()
            ->sort(function (TrackerPositionData $left, TrackerPositionData $right): int {
                $timeComparison = ($left->recordedAt?->getTimestampMs() ?? PHP_INT_MAX)
                    <=> ($right->recordedAt?->getTimestampMs() ?? PHP_INT_MAX);

                return $timeComparison !== 0
                    ? $timeComparison
                    : $left->providerPositionId <=> $right->providerPositionId;
            })
            ->values();
    }

    private function request(): PendingRequest
    {
        $username = (string) config('latch.traccar.username');
        $password = (string) config('latch.traccar.password');

        if ($username === '' || $password === '') {
            throw new TrackerProviderException('configuration', 'Tracker provider is not configured.');
        }

        return Http::baseUrl($this->baseUrl())
            ->acceptJson()
            ->withBasicAuth($username, $password)
            ->connectTimeout((int) config('latch.traccar.connect_timeout_seconds'))
            ->timeout((int) config('latch.traccar.timeout_seconds'))
            ->retry(
                (int) config('latch.traccar.retry_times') + 1,
                200,
                fn (Throwable $exception) => $exception instanceof ConnectionException
                    || ($exception instanceof RequestException
                        && $exception->response->serverError()),
                throw: false,
            );
    }

    /**
     * @param  array<string, scalar>  $query
     * @return array<mixed>
     */
    private function getJson(string $path, array $query): array
    {
        try {
            $response = $this->request()->get($path, $query);

            if ($response->unauthorized() || $response->forbidden()) {
                throw new TrackerProviderException('authentication', 'Tracker provider authentication failed.');
            }

            if (! $response->successful()) {
                throw new TrackerProviderException('http', 'Tracker provider returned an unsuccessful response.');
            }

            $json = $response->json();

            if (! is_array($json)) {
                throw new TrackerProviderException('contract', 'Tracker provider returned malformed JSON.');
            }

            return $json;
        } catch (TrackerProviderException $exception) {
            throw $exception;
        } catch (Throwable $exception) {
            throw new TrackerProviderException('connection', previous: $exception);
        }
    }

    private function baseUrl(): string
    {
        $baseUrl = trim((string) config('latch.traccar.base_url'));
        $parts = parse_url($baseUrl);

        $valid = $baseUrl !== ''
            && is_array($parts)
            && isset($parts['scheme'], $parts['host'])
            && ! isset($parts['user'], $parts['pass'], $parts['query'], $parts['fragment'])
            && ($parts['path'] ?? '') === '/api'
            && ! str_ends_with($baseUrl, '/');

        if (! $valid) {
            throw new TrackerProviderException('configuration', 'Tracker base URL is invalid.');
        }

        if (! TrackerTransportPolicy::allows($parts)) {
            throw new TrackerProviderException('configuration', 'Tracker base URL must use HTTPS.');
        }

        return $baseUrl;
    }

    /**
     * @param  array<mixed>  $rows
     */
    private function singleDevice(
        array $rows,
        ?int $providerDeviceId = null,
        ?string $uniqueId = null,
    ): ?TrackerDeviceData {
        if (! array_is_list($rows)) {
            throw new TrackerProviderException('contract', 'Tracker device response is malformed.');
        }

        if ($rows === []) {
            return null;
        }

        if (count($rows) !== 1 || ! is_array($rows[0])) {
            throw new TrackerProviderException('contract', 'Tracker device response is ambiguous.');
        }

        $device = $this->mapDevice($rows[0]);

        if (
            ($providerDeviceId !== null && $device->providerDeviceId !== $providerDeviceId)
            || ($uniqueId !== null && $device->uniqueId !== $uniqueId)
        ) {
            throw new TrackerProviderException('contract', 'Tracker device identifiers do not match.');
        }

        return $device;
    }

    /**
     * @param  array<string, mixed>  $row
     */
    private function mapDevice(array $row): TrackerDeviceData
    {
        $id = $this->positiveInt($row['id'] ?? null);
        $uniqueId = $row['uniqueId'] ?? null;

        if ($id === null || ! is_string($uniqueId) || $uniqueId === '') {
            throw new TrackerProviderException('contract', 'Tracker device row is malformed.');
        }

        $status = is_string($row['status'] ?? null)
            && in_array($row['status'], ['online', 'offline', 'unknown'], true)
                ? $row['status']
                : null;

        return new TrackerDeviceData(
            providerDeviceId: $id,
            uniqueId: $uniqueId,
            connectionStatus: $status,
            lastCommunicationAt: $this->date($row['lastUpdate'] ?? null),
        );
    }

    private function mapPosition(mixed $row): ?TrackerPositionData
    {
        if (! is_array($row)) {
            return null;
        }

        $id = $this->positiveInt($row['id'] ?? null);
        $deviceId = $this->positiveInt($row['deviceId'] ?? null);
        $recordedAt = $this->date($row['fixTime'] ?? null);

        if ($id === null || $deviceId === null || $recordedAt === null) {
            return null;
        }

        $attributes = is_array($row['attributes'] ?? null) ? $row['attributes'] : [];

        return new TrackerPositionData(
            providerPositionId: $id,
            providerDeviceId: $deviceId,
            latitude: $this->floatInRange($row['latitude'] ?? null, -90, 90),
            longitude: $this->floatInRange($row['longitude'] ?? null, -180, 180),
            recordedAt: $recordedAt,
            gnssValid: is_bool($row['valid'] ?? null) ? $row['valid'] : null,
            batteryPercentage: $this->intInRange(
                $attributes[config('latch.traccar.attributes.battery')] ?? null,
                0,
                100,
            ),
            satellites: $this->intInRange(
                $attributes[config('latch.traccar.attributes.satellites')] ?? null,
                0,
                65535,
            ),
            hdop: $this->floatInRange(
                $attributes[config('latch.traccar.attributes.hdop')] ?? null,
                0,
                9999,
            ),
            gsmCsq: $this->gsmCsq(
                $attributes[config('latch.traccar.attributes.gsm_csq')]
                    ?? $attributes['signal']
                    ?? null
            ),
            powerState: $this->allowedString(
                $attributes[config('latch.traccar.attributes.power_state')] ?? null,
                ['battery', 'charging', 'full'],
            ),
            firmwareVersion: $this->firmwareVersion(
                $attributes[config('latch.traccar.attributes.firmware_version')] ?? null,
            ),
            resetReason: $this->allowedString(
                $attributes[config('latch.traccar.attributes.reset_reason')] ?? null,
                [
                    'unknown',
                    'power_on',
                    'external',
                    'software',
                    'panic',
                    'interrupt_watchdog',
                    'task_watchdog',
                    'watchdog',
                    'deep_sleep',
                    'brownout',
                    'sdio',
                ],
            ),
        );
    }

    /**
     * @param  list<string>  $allowed
     */
    private function allowedString(mixed $value, array $allowed): ?string
    {
        if (! is_string($value)) {
            return null;
        }

        $normalized = strtolower(trim($value));

        return in_array($normalized, $allowed, true) ? $normalized : null;
    }

    private function firmwareVersion(mixed $value): ?string
    {
        if (! is_string($value)) {
            return null;
        }

        $normalized = trim($value);

        return preg_match('/^[A-Za-z0-9][A-Za-z0-9._+-]{0,31}$/D', $normalized) === 1
            ? $normalized
            : null;
    }

    private function positiveInt(mixed $value): ?int
    {
        if (is_int($value)) {
            return $value > 0 ? $value : null;
        }

        if (is_string($value) && preg_match('/^[1-9]\d*$/', $value) === 1) {
            $filtered = filter_var($value, FILTER_VALIDATE_INT, [
                'options' => ['min_range' => 1, 'max_range' => PHP_INT_MAX],
            ]);

            return is_int($filtered) ? $filtered : null;
        }

        return null;
    }

    private function date(mixed $value): ?CarbonImmutable
    {
        if (
            ! is_string($value)
            || preg_match(
                '/^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(?:\.\d{1,6})?(?:Z|[+-]\d{2}:\d{2})$/',
                $value,
            ) !== 1
        ) {
            return null;
        }

        try {
            return CarbonImmutable::parse($value)->utc();
        } catch (Throwable) {
            return null;
        }
    }

    private function floatInRange(mixed $value, float $min, float $max): ?float
    {
        if (! is_int($value) && ! is_float($value) && ! is_numeric($value)) {
            return null;
        }

        $number = (float) $value;

        return is_finite($number) && $number >= $min && $number <= $max
            ? $number
            : null;
    }

    private function intInRange(mixed $value, int $min, int $max): ?int
    {
        if (is_int($value)) {
            return $value >= $min && $value <= $max ? $value : null;
        }

        // Traccar serializes numeric position attributes as JSON numbers and
        // may decode whole-number values such as 75 and 24 as PHP floats.
        if (is_float($value)) {
            return is_finite($value)
                && floor($value) === $value
                && $value >= $min
                && $value <= $max
                    ? (int) $value
                    : null;
        }

        if (is_string($value) && preg_match('/^-?\d+$/', $value) === 1) {
            $value = (int) $value;

            return $value >= $min && $value <= $max ? $value : null;
        }

        return null;
    }

    private function gsmCsq(mixed $value): ?int
    {
        $csq = $this->intInRange($value, 0, 99);

        return $csq !== null && ($csq <= 31 || $csq === 99) ? $csq : null;
    }
}
