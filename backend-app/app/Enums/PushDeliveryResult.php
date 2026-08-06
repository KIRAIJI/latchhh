<?php

namespace App\Enums;

enum PushDeliveryResult: string
{
    case Accepted = 'accepted';
    case InvalidToken = 'invalid_token';
    case Failed = 'failed';
}
