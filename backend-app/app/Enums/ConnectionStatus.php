<?php

namespace App\Enums;

enum ConnectionStatus: string
{
    case Online = 'online';
    case Stale = 'stale';
    case Offline = 'offline';
    case Unknown = 'unknown';
}
