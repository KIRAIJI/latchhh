<?php

namespace App\Services;

use App\Exceptions\ApiException;
use App\Models\Notification;
use App\Models\User;
use App\Support\CursorToken;
use Illuminate\Support\Collection;
use Illuminate\Support\Facades\DB;

class NotificationFeedService
{
    public function __construct(private readonly CursorToken $cursors) {}

    /**
     * @param  array<string, mixed>  $input
     * @return array{rows: Collection<int, Notification>, meta: array<string, mixed>}
     */
    public function get(User $user, array $input): array
    {
        $cursor = isset($input['cursor']) ? (string) $input['cursor'] : null;

        if ($cursor) {
            $decoded = $this->cursors->decode($cursor);
            $context = $decoded['context'];

            if (
                ($context['kind'] ?? null) !== 'notifications'
                || (int) ($context['user_id'] ?? 0) !== $user->id
            ) {
                $this->cursors->inaccessible();
            }

            foreach (['type', 'read_state'] as $field) {
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

            $position = $decoded['position'];
        } else {
            $context = [
                'kind' => 'notifications',
                'user_id' => $user->id,
                'type' => $input['type'] ?? null,
                'read_state' => $input['read_state'] ?? 'all',
                'per_page' => (int) ($input['per_page'] ?? 50),
                'order' => 'newest',
            ];
            $position = null;
        }

        $query = Notification::query()
            ->with('device')
            ->where('user_id', $user->id)
            ->when($context['type'], fn ($query, $type) => $query->where('type', $type))
            ->when($context['read_state'] === 'unread', fn ($query) => $query->whereNull('read_at'))
            ->when($context['read_state'] === 'read', fn ($query) => $query->whereNotNull('read_at'))
            ->orderByDesc('created_at')
            ->orderByDesc('id');

        if ($position !== null) {
            $createdAt = $position['created_at'] ?? null;
            $id = $position['id'] ?? null;

            if (! is_string($createdAt) || ! is_int($id)) {
                $this->cursors->mismatch();
            }

            $query->where(function ($query) use ($createdAt, $id): void {
                $query->where('created_at', '<', $createdAt)
                    ->orWhere(function ($query) use ($createdAt, $id): void {
                        $query->where('created_at', $createdAt)->where('id', '<', $id);
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
                'unread_count' => Notification::query()
                    ->where('user_id', $user->id)
                    ->whereNull('read_at')
                    ->count(),
                'next_cursor' => $hasMore && $last
                    ? $this->cursors->encode($context, [
                        'created_at' => $last->created_at->utc()->format('Y-m-d H:i:s.v'),
                        'id' => $last->id,
                    ])
                    : null,
            ],
        ];
    }

    public function markRead(User $user, int $notificationId): Notification
    {
        return DB::transaction(function () use ($user, $notificationId): Notification {
            $notification = Notification::query()
                ->where('user_id', $user->id)
                ->whereKey($notificationId)
                ->lockForUpdate()
                ->first();

            if (! $notification) {
                throw new ApiException(
                    'NOTIFICATION_NOT_FOUND',
                    'Notification not found.',
                    404,
                );
            }

            if ($notification->read_at === null) {
                $notification->read_at = now()->utc();
                $notification->save();
            }

            return $notification->load('device');
        });
    }

    /**
     * @return array{updated_count: int, unread_count: int}
     */
    public function markAllRead(User $user): array
    {
        $updated = Notification::query()
            ->where('user_id', $user->id)
            ->whereNull('read_at')
            ->update(['read_at' => now()->utc(), 'updated_at' => now()->utc()]);

        return ['updated_count' => $updated, 'unread_count' => 0];
    }

    public function deleteNotification(User $user, int $notificationId): void
    {
        DB::transaction(function () use ($user, $notificationId): void {
            $notification = Notification::query()
                ->where('user_id', $user->id)
                ->whereKey($notificationId)
                ->lockForUpdate()
                ->first();

            if (! $notification) {
                throw new ApiException(
                    'NOTIFICATION_NOT_FOUND',
                    'Notification not found.',
                    404,
                );
            }

            $notification->delete();
        });
    }
}
