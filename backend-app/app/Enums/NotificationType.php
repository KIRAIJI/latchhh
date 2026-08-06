<?php

namespace App\Enums;

enum NotificationType: string
{
    case GeofenceEnter = 'geofence_enter';
    case GeofenceExit = 'geofence_exit';
    case BatteryLow = 'battery_low';
    case BatteryCritical = 'battery_critical';
    case DeviceOffline = 'device_offline';
    case DeviceOnline = 'device_online';
}
