<?php

namespace App\Enums;

enum ActivitySource: string
{
    case User = 'user';
    case Tracker = 'tracker';
    case Geofence = 'geofence';
    case System = 'system';
}
