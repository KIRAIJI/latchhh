<?php

namespace App\Enums;

enum GsmSignalLevel: string
{
    case Excellent = 'excellent';
    case Good = 'good';
    case Fair = 'fair';
    case Poor = 'poor';
    case NoSignal = 'no_signal';
    case Unknown = 'unknown';
}
