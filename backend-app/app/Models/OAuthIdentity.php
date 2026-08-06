<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class OAuthIdentity extends Model
{
    protected $table = 'oauth_identities';

    protected $fillable = [
        'provider',
        'provider_subject',
        'firebase_uid',
        'provider_email',
        'last_login_at',
    ];

    protected function casts(): array
    {
        return [
            'last_login_at' => 'immutable_datetime',
        ];
    }

    public function user()
    {
        return $this->belongsTo(User::class);
    }
}
