<?php

namespace App\Providers\Push;

use App\Contracts\PushProviderInterface;
use App\Enums\PushDeliveryResult;
use App\Models\Notification;
use App\Models\PushToken;

class NullPushProvider implements PushProviderInterface
{
    public function send(PushToken $token, Notification $notification): PushDeliveryResult
    {
        return PushDeliveryResult::Failed;
    }
}
