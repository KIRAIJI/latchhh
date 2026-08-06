<?php

namespace App\Enums;

enum DeviceActivityType: string
{
    case ItemClaimed = 'item_claimed';
    case ItemRenamed = 'item_renamed';
    case ItemReleased = 'item_released';
    case GeofenceCreated = 'geofence_created';
    case GeofenceUpdated = 'geofence_updated';
    case GeofenceActivated = 'geofence_activated';
    case GeofenceDeactivated = 'geofence_deactivated';
    case GeofenceDeleted = 'geofence_deleted';
    case GeofenceEnter = 'geofence_enter';
    case GeofenceExit = 'geofence_exit';
    case BatteryLow = 'battery_low';
    case BatteryCritical = 'battery_critical';
    case DeviceOffline = 'device_offline';
    case DeviceOnline = 'device_online';
}
