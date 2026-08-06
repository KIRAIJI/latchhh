<?php

namespace App\Enums;

enum BatteryStatus: string
{
    case Normal = 'normal';
    case Low = 'low';
    case Critical = 'critical';
    case Unknown = 'unknown';
}
