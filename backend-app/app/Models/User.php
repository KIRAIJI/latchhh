<?php

namespace App\Models;

use App\Notifications\ResetPasswordNotification;
use App\Notifications\SetPasswordNotification;
use App\Notifications\VerifyEmailNotification;
use Database\Factories\UserFactory;
use Illuminate\Auth\MustVerifyEmail as MustVerifyEmailTrait;
use Illuminate\Contracts\Auth\MustVerifyEmail as MustVerifyEmailContract;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Laravel\Sanctum\HasApiTokens;

class User extends Authenticatable implements MustVerifyEmailContract
{
    /** @use HasFactory<UserFactory> */
    use HasApiTokens, HasFactory, MustVerifyEmailTrait, Notifiable;

    protected $attributes = [
        'notifications_enabled' => true,
        'notify_geofence_events' => true,
        'notify_battery_events' => true,
        'notify_device_status_events' => true,
    ];

    /**
     * The attributes that are mass assignable.
     *
     * @var list<string>
     */
    protected $fillable = [
        'name',
        'email',
        'password',
        'password_set_at',
        'profile_photo_path',
        'notifications_enabled',
        'notify_geofence_events',
        'notify_battery_events',
        'notify_device_status_events',
        'terms_accepted_at',
        'terms_version',
        'privacy_acknowledged_at',
        'privacy_version',
    ];

    /**
     * The attributes that should be hidden for serialization.
     *
     * @var list<string>
     */
    protected $hidden = [
        'password',
        'remember_token',
    ];

    /**
     * Get the attributes that should be cast.
     *
     * @return array<string, string>
     */
    protected function casts(): array
    {
        return [
            'email_verified_at' => 'datetime',
            'password' => 'hashed',
            'password_set_at' => 'immutable_datetime',
            'notifications_enabled' => 'boolean',
            'notify_geofence_events' => 'boolean',
            'notify_battery_events' => 'boolean',
            'notify_device_status_events' => 'boolean',
            'terms_accepted_at' => 'immutable_datetime',
            'privacy_acknowledged_at' => 'immutable_datetime',
        ];
    }

    public function devices()
    {
        return $this->hasMany(Device::class);
    }

    public function notifications()
    {
        return $this->hasMany(Notification::class);
    }

    public function positions()
    {
        return $this->hasMany(DevicePosition::class);
    }

    public function activityLogs()
    {
        return $this->hasMany(DeviceActivityLog::class);
    }

    public function pushTokens()
    {
        return $this->hasMany(PushToken::class);
    }

    public function oauthIdentities()
    {
        return $this->hasMany(OAuthIdentity::class);
    }

    public function sendEmailVerificationNotification(): void
    {
        $this->notify(new VerifyEmailNotification);
    }

    public function sendPasswordResetNotification($token): void
    {
        $this->notify(new ResetPasswordNotification($token));
    }

    public function sendPasswordSetupNotification(string $token): void
    {
        $this->notify(new SetPasswordNotification($token));
    }
}
