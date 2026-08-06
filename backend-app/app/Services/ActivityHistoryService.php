<?php

namespace App\Services;

use App\Exceptions\ApiException;
use App\Models\Device;
use App\Models\DeviceActivityLog;
use App\Models\User;
use App\Support\CursorToken;
use Carbon\CarbonImmutable;
use Illuminate\Support\Collection;

class ActivityHistoryService
{
    public function __construct(private readonly CursorToken $cursors) {}

    /**
     * @param  array<string, mixed>  $input
     * @return array{rows: Collection<int, DeviceActivityLog>, meta: array<string, mixed>}
     */
    public function get(User $user, array $input, ?Device $device = null): array
    {
        if ($device && isset($input['device_uid'])) {
            throw $this->validation('device_uid', 'The device_uid filter is not accepted for item activity.');
        }

        $cursor = isset($input['cursor']) ? (string) $input['cursor'] : null;

        if ($cursor) {
            $decoded = $this->cursors->decode($cursor);
            $context = $decoded['context'];
            $expectedKind = $device ? 'item_activity' : 'global_activity';

            if (
                ($context['kind'] ?? null) !== $expectedKind
                || (int) ($context['user_id'] ?? 0) !== $user->id
                || ($device && (
                    (int) ($context['item_id'] ?? 0) !== $device->id
                    || (int) ($context['claim_version'] ?? -1) !== $device->claim_version
                ))
            ) {
                $this->cursors->inaccessible();
            }

            $this->assertCursorInputs($input, $context, $device !== null);
            $position = $decoded['position'];
        } else {
            $context = $this->initialContext($user, $input, $device);
            $position = null;
        }

        $query = DeviceActivityLog::query()
            ->with('device')
            ->where('user_id', $user->id)
            ->whereBetween('occurred_at', [$context['query_from'], $context['to_db']])
            ->when($device, fn ($query) => $query
                ->where('device_id', $device->id)
                ->where('claim_version', $device->claim_version))
            ->when($context['event_type'], fn ($query, $type) => $query->where('event_type', $type))
            ->when($context['source'], fn ($query, $source) => $query->where('source', $source))
            ->when($context['device_uid'], fn ($query, $uid) => $query->where('device_uid_snapshot', $uid))
            ->orderByDesc('occurred_at')
            ->orderByDesc('id');

        if ($position !== null) {
            $occurredAt = $position['occurred_at'] ?? null;
            $id = $position['id'] ?? null;

            if (! is_string($occurredAt) || ! is_int($id)) {
                $this->cursors->mismatch();
            }

            $query->where(function ($query) use ($occurredAt, $id): void {
                $query->where('occurred_at', '<', $occurredAt)
                    ->orWhere(function ($query) use ($occurredAt, $id): void {
                        $query->where('occurred_at', $occurredAt)->where('id', '<', $id);
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
                'retention_cutoff' => $context['retention_cutoff'],
                'next_cursor' => $hasMore && $last
                    ? $this->cursors->encode($context, [
                        'occurred_at' => $last->occurred_at->utc()->format('Y-m-d H:i:s.v'),
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
    private function initialContext(User $user, array $input, ?Device $device): array
    {
        $now = CarbonImmutable::now('UTC');
        $retention = $now->subDays((int) config('latch.activity_history.retention_days'));
        $from = isset($input['from']) ? $this->utcDate((string) $input['from'], 'from') : $retention;
        $to = isset($input['to']) ? $this->utcDate((string) $input['to'], 'to') : $now;

        if (($input['from'] ?? null) xor ($input['to'] ?? null)) {
            throw $this->validation('from', 'Both from and to are required together.');
        }

        if ($from->greaterThanOrEqualTo($to) || $to->greaterThan($now)) {
            throw $this->validation('from', 'The activity range is invalid.');
        }

        if (
            isset($input['from'])
            && $from->diffInSeconds($to)
                > (int) config('latch.activity_history.max_range_days') * 86400
        ) {
            throw $this->validation('from', 'The activity range is too large.');
        }

        $queryFrom = $from->greaterThan($retention) ? $from : $retention;

        return [
            'kind' => $device ? 'item_activity' : 'global_activity',
            'user_id' => $user->id,
            'item_id' => $device?->id,
            'claim_version' => $device?->claim_version,
            'event_type' => $input['event_type'] ?? null,
            'source' => $input['source'] ?? null,
            'device_uid' => $device ? null : ($input['device_uid'] ?? null),
            'from' => $from->toISOString(),
            'to' => $to->toISOString(),
            'query_from' => $queryFrom->format('Y-m-d H:i:s.v'),
            'to_db' => $to->format('Y-m-d H:i:s.v'),
            'retention_cutoff' => $retention->toISOString(),
            'per_page' => (int) ($input['per_page'] ?? 50),
            'order' => 'newest',
        ];
    }

    /**
     * @param  array<string, mixed>  $input
     * @param  array<string, mixed>  $context
     */
    private function assertCursorInputs(array $input, array $context, bool $item): void
    {
        foreach (['event_type', 'source', 'device_uid'] as $field) {
            if (isset($input[$field]) && $input[$field] !== ($context[$field] ?? null)) {
                $this->cursors->mismatch($field);
            }
        }

        if (
            isset($input['per_page'])
            && (int) $input['per_page'] !== (int) ($context['per_page'] ?? 0)
        ) {
            $this->cursors->mismatch('per_page');
        }

        foreach (['from', 'to'] as $field) {
            if (
                isset($input[$field])
                && $this->utcDate((string) $input[$field], $field)->toISOString()
                    !== ($context[$field] ?? null)
            ) {
                $this->cursors->mismatch($field);
            }
        }

        if ($item && isset($input['device_uid'])) {
            $this->cursors->mismatch('device_uid');
        }
    }

    private function utcDate(string $value, string $field): CarbonImmutable
    {
        try {
            $date = CarbonImmutable::parse($value);
        } catch (\Throwable) {
            throw $this->validation($field, "The {$field} field is invalid.");
        }

        if ($date->offset !== 0) {
            throw $this->validation($field, "The {$field} field must use UTC.");
        }

        return $date->utc();
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
