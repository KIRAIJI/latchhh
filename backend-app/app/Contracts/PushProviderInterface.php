<?php

namespace App\Contracts;

use App\Enums\PushDeliveryResult;
use App\Models\Notification;
use App\Models\PushToken;

interface PushProviderInterface
{
    public function send(PushToken $token, Notification $notification): PushDeliveryResult;
}
