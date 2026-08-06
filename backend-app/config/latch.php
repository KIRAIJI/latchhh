<?php

return [
    'mobile' => [
        'deep_link_scheme' => env('MOBILE_DEEP_LINK_SCHEME', 'latch'),
    ],

    'push' => [
        'enabled' => (bool) env('FCM_ENABLED', false),
        'fcm' => [
            'project_id' => env('FCM_PROJECT_ID'),
            'credentials_path' => env('FCM_SERVICE_ACCOUNT_PATH'),
        ],
    ],

    'oauth' => [
        'google' => [
            'enabled' => (bool) env('GOOGLE_OAUTH_ENABLED', false),
        ],
        'firebase' => [
            'project_id' => env('FIREBASE_PROJECT_ID', env('FCM_PROJECT_ID')),
            'credentials_path' => env(
                'FIREBASE_SERVICE_ACCOUNT_PATH',
                env('FCM_SERVICE_ACCOUNT_PATH'),
            ),
        ],
    ],

    'legal' => [
        'terms_version' => env('LEGAL_TERMS_VERSION', '2026-07-26'),
        'privacy_version' => env('LEGAL_PRIVACY_VERSION', '2026-07-26'),
        'support_email' => env('LEGAL_SUPPORT_EMAIL', 'support@example.com'),
    ],

    'tracker' => [
        'provider' => env('TRACKER_PROVIDER', 'traccar'),
        'current_seconds' => (int) env('TRACKER_CURRENT_SECONDS', 120),
        'offline_seconds' => (int) env('TRACKER_OFFLINE_SECONDS', 600),
        'position_overlap_seconds' => (int) env('TRACKER_POSITION_OVERLAP_SECONDS', 120),
        'sync_max_window_minutes' => (int) env('TRACKER_SYNC_MAX_WINDOW_MINUTES', 60),
        'max_clock_skew_seconds' => (int) env('TRACKER_MAX_CLOCK_SKEW_SECONDS', 30),
        'sync_stale_seconds' => (int) env('TRACKER_SYNC_STALE_SECONDS', 300),
    ],

    'traccar' => [
        'base_url' => env('TRACCAR_BASE_URL'),
        'username' => env('TRACCAR_USERNAME'),
        'password' => env('TRACCAR_PASSWORD'),
        'allow_insecure_http' => (bool) env('TRACCAR_ALLOW_INSECURE_HTTP', false),
        'connect_timeout_seconds' => (int) env('TRACCAR_CONNECT_TIMEOUT_SECONDS', 3),
        'timeout_seconds' => (int) env('TRACCAR_TIMEOUT_SECONDS', 10),
        'retry_times' => (int) env('TRACCAR_RETRY_TIMES', 2),
        'attributes' => [
            'battery' => env('TRACCAR_BATTERY_ATTRIBUTE', 'batteryLevel'),
            'satellites' => env('TRACCAR_SATELLITES_ATTRIBUTE', 'sat'),
            'hdop' => env('TRACCAR_HDOP_ATTRIBUTE', 'hdop'),
            'gsm_csq' => env('TRACCAR_GSM_CSQ_ATTRIBUTE', 'csq'),
            'power_state' => env('TRACCAR_POWER_STATE_ATTRIBUTE', 'powerState'),
            'firmware_version' => env('TRACCAR_FIRMWARE_ATTRIBUTE', 'firmware'),
            'reset_reason' => env('TRACCAR_RESET_REASON_ATTRIBUTE', 'resetReason'),
        ],
    ],

    'battery' => [
        'low_percentage' => (int) env('BATTERY_LOW_PERCENTAGE', 20),
        'critical_percentage' => (int) env('BATTERY_CRITICAL_PERCENTAGE', 10),
    ],

    'location_history' => [
        'retention_days' => (int) env('LOCATION_HISTORY_RETENTION_DAYS', 30),
        'max_range_days' => (int) env('LOCATION_HISTORY_MAX_RANGE_DAYS', 7),
    ],

    'activity_history' => [
        'retention_days' => (int) env('ACTIVITY_HISTORY_RETENTION_DAYS', 365),
        'max_range_days' => (int) env('ACTIVITY_HISTORY_MAX_RANGE_DAYS', 90),
    ],
];
