<?php

namespace App\Models;

use App\Enums\ActivitySource;
use App\Enums\DeviceActivityType;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class DeviceActivityLog extends Model
{
    public $timestamps = false;

    protected $dateFormat = 'Y-m-d H:i:s.v';

    protected $guarded = [];

    protected $hidden = [
        'user_id',
        'actor_user_id',
        'claim_version',
        'event_key',
    ];

    protected function casts(): array
    {
        return [
            'claim_version' => 'integer',
            'event_type' => DeviceActivityType::class,
            'source' => ActivitySource::class,
            'event_data' => 'array',
            'occurred_at' => 'immutable_datetime',
            'created_at' => 'immutable_datetime',
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

    public function actor(): BelongsTo
    {
        return $this->belongsTo(User::class, 'actor_user_id');
    }
}
