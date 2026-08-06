<?php

namespace App\Services;

use App\Exceptions\ApiException;
use App\Models\Device;
use App\Models\DevicePosition;
use App\Models\User;
use App\Support\CursorToken;
use Carbon\CarbonImmutable;
use Illuminate\Support\Collection;

class LocationHistoryService
{
    public function __construct(private readonly CursorToken $cursors) {}

    /**
     * @param  array<string, mixed>  $input
     * @return array{rows: Collection<int, DevicePosition>, meta: array<string, mixed>}
     */
    public function get(User $user, Device $device, array $input): array
    {
        $cursor = isset($input['cursor']) ? (string) $input['cursor'] : null;

        if ($cursor) {
            $decoded = $this->cursors->decode($cursor);
            $context = $decoded['context'];

            if (
                ($context['kind'] ?? null) !== 'location'
                || (int) ($context['user_id'] ?? 0) !== $user->id
                || (int) ($context['item_id'] ?? 0) !== $device->id
                || (int) ($context['claim_version'] ?? -1) !== $device->claim_version
            ) {
                $this->cursors->inaccessible();
            }

            $this->assertCursorInputs($input, $context);
            $position = $decoded['position'];
        } else {
            $context = $this->initialContext($user, $device, $input);
            $position = null;
        }

        $query = DevicePosition::query()
            ->where('user_id', $user->id)
            ->where('device_id', $device->id)
            ->where('claim_version', $device->claim_version)
            ->whereBetween('recorded_at', [$context['query_from'], $context['to_db']])
            ->orderBy('recorded_at')
            ->orderBy('id');

        if ($position !== null) {
            $recordedAt = $position['recorded_at'] ?? null;
            $id = $position['id'] ?? null;

            if (! is_string($recordedAt) || ! is_int($id)) {
                $this->cursors->mismatch();
            }

            $query->where(function ($query) use ($recordedAt, $id): void {
                $query->where('recorded_at', '>', $recordedAt)
                    ->orWhere(function ($query) use ($recordedAt, $id): void {
                        $query->where('recorded_at', $recordedAt)->where('id', '>', $id);
                    });
            });
        }

        $perPage = (int) $context['per_page'];
        $rows = $query->limit($perPage + 1)->get();
        $hasMore = $rows->count() > $perPage;
        $rows = $rows->take($perPage)->values();
        $last = $rows->last();

        return [
            'rows' => $rows,
            'meta' => [
                'from' => $context['from'],
                'to' => $context['to'],
                'retention_cutoff' => $context['retention_cutoff'],
                'next_cursor' => $hasMore && $last
                    ? $this->cursors->encode($context, [
                        'recorded_at' => $last->recorded_at->utc()->format('Y-m-d H:i:s.v'),
                        'id' => $last->id,
                    ])
                    : null,
            ],
        ];
    }

    /**
     * @param  array<string, mixed>  $input
     * @return array<string, mixed>
     */
    private function initialContext(User $user, Device $device, array $input): array
    {
        $requestStart = CarbonImmutable::now('UTC');
        $from = isset($input['from'])
            ? $this->utcDate((string) $input['from'], 'from')
            : $requestStart->subDay();
        $to = isset($input['to'])
            ? $this->utcDate((string) $input['to'], 'to')
            : $requestStart;

        if (($input['from'] ?? null) xor ($input['to'] ?? null)) {
            $this->invalidRange('Both from and to are required together.');
        }

        $this->validateRange(
            $from,
            $to,
            (int) config('latch.location_history.max_range_days'),
            $requestStart,
        );

        $retention = $requestStart->subDays(
            (int) config('latch.location_history.retention_days')
        );
        $claimedAt = CarbonImmutable::instance($device->claimed_at);
        $queryFrom = collect([$from, $retention, $claimedAt])
            ->sortBy(fn (CarbonImmutable $date) => $date->getTimestampMs())
            ->last();

        return [
            'kind' => 'location',
            'user_id' => $user->id,
            'item_id' => $device->id,
            'claim_version' => $device->claim_version,
            'from' => $from->toISOString(),
            'query_from' => $queryFrom->format('Y-m-d H:i:s.v'),
            'to' => $to->toISOString(),
            'to_db' => $to->format('Y-m-d H:i:s.v'),
            'retention_cutoff' => $retention->toISOString(),
            'per_page' => (int) ($input['per_page'] ?? 500),
            'order' => 'oldest',
        ];
    }

    /**
     * @param  array<string, mixed>  $input
     * @param  array<string, mixed>  $context
     */
    private function assertCursorInputs(array $input, array $context): void
    {
        if (
            isset($input['per_page'])
            && (int) $input['per_page'] !== (int) ($context['per_page'] ?? 0)
        ) {
            $this->cursors->mismatch('per_page');
        }

        foreach (['from', 'to'] as $field) {
            if (isset($input[$field])) {
                $date = $this->utcDate((string) $input[$field], $field)->toISOString();
                $expected = $context[$field] ?? null;

                if ($date !== $expected) {
                    $this->cursors->mismatch($field);
                }
            }
        }
    }

    private function utcDate(string $value, string $field): CarbonImmutable
    {
        try {
            $date = CarbonImmutable::parse($value);
        } catch (\Throwable) {
            throw $this->validation($field, "The {$field} field must be a valid UTC timestamp.");
        }

        if ($date->offset !== 0) {
            throw $this->validation($field, "The {$field} field must use UTC.");
        }

        return $date->utc();
    }

    private function validateRange(
        CarbonImmutable $from,
        CarbonImmutable $to,
        int $maxDays,
        CarbonImmutable $now,
    ): void {
        if ($from->greaterThanOrEqualTo($to)) {
            $this->invalidRange('The from timestamp must be earlier than to.');
        }

        if ($to->greaterThan($now)) {
            $this->invalidRange('The to timestamp must not be in the future.');
        }

        if ($from->diffInSeconds($to) > $maxDays * 86400) {
            $this->invalidRange("The requested range must not exceed {$maxDays} days.");
        }
    }

    private function invalidRange(string $message): never
    {
        throw $this->validation('from', $message);
    }

    private function validation(string $field, string $message): ApiException
    {
        return new ApiException(
            'VALIDATION_ERROR',
            'The given data was invalid.',
            422,
            [$field => [$message]],
        );
    }
}
