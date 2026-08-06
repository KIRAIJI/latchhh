<?php

namespace App\Http\Controllers;

class LegalController extends Controller
{
    public function privacy()
    {
        return view('legal', [
            'title' => 'LATCH Privacy Policy',
            'effective' => config('latch.legal.privacy_version'),
            'sections' => [
                'Information we process' => 'We process account identity and authentication data, including a linked Google/Firebase identity when you choose Google sign-in, optional profile photos, claimed tracker identifiers, precise tracker locations, geofences, telemetry, notifications, device push tokens, activity records, and security diagnostics required to operate LATCH.',
                'Purposes' => 'We use data to authenticate users, synchronize trackers, display locations, evaluate geofences, send alerts, recover accounts, prevent abuse, diagnose failures, and meet legal obligations. LATCH does not sell personal information or use it for advertising.',
                'Processors and transfers' => 'Google and Firebase Authentication process Google sign-in when selected. The configured tracker provider supplies tracker data. Google Maps Platform renders coordinates shown on in-app maps. Firebase Cloud Messaging delivers push notifications and Firebase Crashlytics may process production diagnostics. Hosting, email, and storage providers process data only as needed to provide their services.',
                'Retention' => 'Backend defaults retain precise position history for 30 days and activity history for 365 days. Notifications remain until deleted or account deletion. Profile photos remain until replaced, removed, or account deletion. Operational backups and security logs follow the operator’s documented expiry schedule.',
                'Controls and deletion' => 'Users can disable alert categories, delete notifications, remove photos, release trackers, and permanently delete their account in the app. Account deletion removes account data, claim-scoped positions, geofences, notifications, activity, sessions, and push tokens, subject to legal and backup-expiry obligations.',
                'Security' => 'Production traffic must use HTTPS, authentication tokens are stored using secure mobile storage, passwords are hashed, authorization is enforced per owner, and backend push tokens are encrypted at rest.',
            ],
        ]);
    }

    public function terms()
    {
        return view('legal', [
            'title' => 'LATCH Terms of Service',
            'effective' => config('latch.legal.terms_version'),
            'sections' => [
                'Account responsibilities' => 'You must provide accurate information, protect your credentials, and promptly report suspected unauthorized access.',
                'Authorized tracking only' => 'You may claim and monitor only trackers and property you own or are authorized to track. You are responsible for complying with privacy, property, employment, and tracking laws.',
                'Safety limitations' => 'LATCH is a convenience service and is not an emergency, medical, life-safety, or guaranteed theft-recovery system. Tracker locations and alerts can be delayed or inaccurate due to hardware, GNSS reception, connectivity, battery, provider, or infrastructure conditions.',
                'Acceptable use' => 'Stalking, unauthorized surveillance, abuse, illegal activity, credential theft, interference, reverse engineering intended to bypass security, and attempts to access another user’s data are prohibited.',
                'Availability and changes' => 'Features may evolve as hardware, firmware, maps, mobile platforms, and infrastructure change. Maintenance and circumstances outside reasonable control can interrupt service.',
                'Termination and deletion' => 'You may permanently delete your account in the app. The operator may restrict access when necessary to protect users, comply with law, or respond to serious violations. Provisions that by nature survive termination remain effective.',
            ],
        ]);
    }

    public function accountDeletion()
    {
        return view('account-deletion', [
            'supportEmail' => config('latch.legal.support_email'),
        ]);
    }
}
