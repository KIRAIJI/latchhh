<?php

namespace App\Models;

use App\Enums\NotificationType;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class Notification extends Model
{
    protected $dateFormat = 'Y-m-d H:i:s.v';

    protected $guarded = [];

    protected $hidden = ['event_key'];

    protected function casts(): array
    {
        return [
            'type' => NotificationType::class,
            'read_at' => 'immutable_datetime',
        ];
    }

    public function user(): BelongsTo
    {
        return $this->belongsTo(User::class);
    }

    public function device(): BelongsTo
    {
        return $this->belongsTo(Device::class);
    }
}
