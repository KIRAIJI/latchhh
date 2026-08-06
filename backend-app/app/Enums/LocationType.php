<?php

namespace App\Enums;

enum LocationType: string
{
    case Current = 'current';
    case LastKnown = 'last_known';
    case Unavailable = 'unavailable';
}
