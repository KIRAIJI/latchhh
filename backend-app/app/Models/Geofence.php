<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class Geofence extends Model
{
    use HasFactory;

    protected $dateFormat = 'Y-m-d H:i:s.v';

    protected $guarded = [];

    protected function casts(): array
    {
        return [
            'notify_on_enter' => 'boolean',
            'notify_on_exit' => 'boolean',
            'is_active' => 'boolean',
            'last_inside' => 'boolean',
            'last_evaluated_position_id' => 'integer',
            'center_latitude' => 'float',
            'center_longitude' => 'float',
            'radius_meters' => 'float',
            'last_evaluated_at' => 'immutable_datetime',
        ];
    }

    public function device(): BelongsTo
    {
        return $this->belongsTo(Device::class);
    }
}
