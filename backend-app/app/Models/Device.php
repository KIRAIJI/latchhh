<?php

namespace App\Models;

use App\Enums\GnssStatus;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\HasMany;
use Illuminate\Database\Eloquent\Relations\HasOne;

class Device extends Model
{
    use HasFactory;

    protected $dateFormat = 'Y-m-d H:i:s.v';

    protected $guarded = [];

    protected function casts(): array
    {
        return [
            'claim_version' => 'integer',
            'tracker_device_id' => 'integer',
            'last_provider_position_id' => 'integer',
            'last_telemetry_position_id' => 'integer',
            'last_battery_percentage' => 'integer',
            'last_satellites' => 'integer',
            'last_gsm_csq' => 'integer',
            'last_latitude' => 'float',
            'last_longitude' => 'float',
            'last_location_accuracy_meters' => 'float',
            'last_hdop' => 'float',
            'last_gnss_status' => GnssStatus::class,
            'claimed_at' => 'immutable_datetime',
            'tracker_cursor_at' => 'immutable_datetime',
            'last_position_at' => 'immutable_datetime',
            'last_place_resolved_at' => 'immutable_datetime',
            'last_telemetry_at' => 'immutable_datetime',
            'last_communication_at' => 'immutable_datetime',
            'last_synced_at' => 'immutable_datetime',
            'notification_connection_reference_at' => 'immutable_datetime',
        ];
    }

    public function user(): BelongsTo
    {
        return $this->belongsTo(User::class);
    }

    public function geofence(): HasOne
    {
        return $this->hasOne(Geofence::class);
    }

    public function notifications(): HasMany
    {
        return $this->hasMany(Notification::class);
    }

    public function positions(): HasMany
    {
        return $this->hasMany(DevicePosition::class);
    }

    public function activityLogs(): HasMany
    {
        return $this->hasMany(DeviceActivityLog::class);
    }
}
