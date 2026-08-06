<?php

namespace App\Enums;

enum GnssStatus: string
{
    case Fixed = 'fixed';
    case NoFix = 'no_fix';
    case Unknown = 'unknown';
}
