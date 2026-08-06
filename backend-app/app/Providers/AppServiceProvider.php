<?php

namespace App\Providers;

use App\Contracts\OAuthTokenVerifierInterface;
use App\Contracts\PushProviderInterface;
use App\Contracts\TrackerProviderInterface;
use App\Models\Device;
use App\Policies\DevicePolicy;
use App\Providers\Auth\FirebaseGoogleTokenVerifier;
use App\Providers\Push\FcmPushProvider;
use App\Providers\Push\NullPushProvider;
use App\Providers\Tracker\FakeTrackerProvider;
use App\Providers\Tracker\TraccarProvider;
use Illuminate\Auth\Notifications\ResetPassword;
use Illuminate\Cache\RateLimiting\Limit;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Gate;
use Illuminate\Support\Facades\RateLimiter;
use Illuminate\Support\ServiceProvider;
use InvalidArgumentException;

class AppServiceProvider extends ServiceProvider
{
    /**
     * Register any application services.
     */
    public function register(): void
    {
        $this->app->bind(
            OAuthTokenVerifierInterface::class,
            FirebaseGoogleTokenVerifier::class,
        );

        $this->app->bind(
            TrackerProviderInterface::class,
            fn ($app) => match (config('latch.tracker.provider')) {
                'fake' => $app->make(FakeTrackerProvider::class),
                'traccar' => $app->make(TraccarProvider::class),
                default => throw new InvalidArgumentException('Unsupported tracker provider.'),
            },
        );

        $this->app->bind(
            PushProviderInterface::class,
            fn ($app) => config('latch.push.enabled')
                ? $app->make(FcmPushProvider::class)
                : $app->make(NullPushProvider::class),
        );
    }

    /**
     * Bootstrap any application services.
     */
    public function boot(): void
    {
        Gate::policy(Device::class, DevicePolicy::class);

        ResetPassword::createUrlUsing(
            fn ($user, string $token): string => route('password.bridge', [
                'token' => $token,
                'email' => $user->getEmailForPasswordReset(),
            ]),
        );

        RateLimiter::for('auth.register', fn (Request $request) => Limit::perMinute(3)
            ->by(hash('sha256', (string) $request->ip())));

        RateLimiter::for('auth.login', function (Request $request) {
            $email = mb_strtolower(substr((string) $request->input('email'), 0, 255));

            return Limit::perMinute(5)->by(
                hash('sha256', $email.'|'.(string) $request->ip())
            );
        });

        RateLimiter::for('auth.oauth', fn (Request $request) => [
            Limit::perMinute(10)->by(hash('sha256', (string) $request->ip())),
            Limit::perHour(100)->by(hash('sha256', (string) $request->ip())),
        ]);

        RateLimiter::for('auth.recovery', function (Request $request) {
            $email = mb_strtolower(substr((string) $request->input('email'), 0, 255));

            return [
                Limit::perMinutes(15, 5)->by(
                    hash('sha256', $email.'|'.(string) $request->ip()),
                ),
                Limit::perHour(30)->by(hash('sha256', (string) $request->ip())),
            ];
        });

        RateLimiter::for('auth.verification', fn (Request $request) => Limit::perMinutes(15, 5)
            ->by(hash('sha256', (string) ($request->user()?->id ?? $request->ip()))));

        RateLimiter::for('sensitive', fn (Request $request) => Limit::perMinutes(15, 5)
            ->by(hash('sha256', ($request->user()?->id ?? 'guest').'|'.(string) $request->ip())));

        RateLimiter::for('history', fn (Request $request) => Limit::perMinute(30)
            ->by((string) ($request->user()?->id ?? $request->ip())));

        RateLimiter::for('activity', fn (Request $request) => Limit::perMinute(60)
            ->by((string) ($request->user()?->id ?? $request->ip())));

        RateLimiter::for('tracker.refresh', fn (Request $request) => [
            Limit::perMinute(12)
                ->by((string) ($request->user()?->id ?? $request->ip())),
            Limit::perMinute(3)->by(
                ($request->user()?->id ?? $request->ip())
                .'|'.(string) $request->route('item'),
            ),
        ]);

        $this->validateConfiguration();
    }

    private function validateConfiguration(): void
    {
        $current = (int) config('latch.tracker.current_seconds');
        $offline = (int) config('latch.tracker.offline_seconds');
        $overlap = (int) config('latch.tracker.position_overlap_seconds');
        $syncWindow = (int) config('latch.tracker.sync_max_window_minutes');
        $clockSkew = (int) config('latch.tracker.max_clock_skew_seconds');
        $syncStale = (int) config('latch.tracker.sync_stale_seconds');
        $connectTimeout = (int) config('latch.traccar.connect_timeout_seconds');
        $requestTimeout = (int) config('latch.traccar.timeout_seconds');
        $retries = (int) config('latch.traccar.retry_times');
        $critical = (int) config('latch.battery.critical_percentage');
        $low = (int) config('latch.battery.low_percentage');
        $locationRetention = (int) config('latch.location_history.retention_days');
        $locationRange = (int) config('latch.location_history.max_range_days');
        $activityRetention = (int) config('latch.activity_history.retention_days');
        $activityRange = (int) config('latch.activity_history.max_range_days');
        $providerBudget = 2 * (
            (($retries + 1) * $requestTimeout)
            + ($retries * 0.2)
        );
        $oauthEnabled = (bool) config('latch.oauth.google.enabled');
        $firebaseProjectId = (string) config('latch.oauth.firebase.project_id');
        $firebaseCredentials = (string) config(
            'latch.oauth.firebase.credentials_path',
        );

        if (
            config('hashing.driver') !== 'bcrypt'
            || $current <= 0
            || $offline <= $current
            || $overlap < 60
            || $overlap > 3600
            || $syncWindow < 1
            || $clockSkew < 0
            || $syncStale < 120
            || $syncStale > 86400
            || $connectTimeout < 1
            || $requestTimeout < $connectTimeout
            || $retries < 0
            || $providerBudget >= 90
            || $critical < 0
            || $low <= $critical
            || $low > 100
            || $locationRange < 1
            || $locationRange > $locationRetention
            || $locationRetention > 90
            || $activityRange < 1
            || $activityRange > $activityRetention
            || $activityRetention > 730
        ) {
            throw new InvalidArgumentException('LATCH configuration thresholds are inconsistent.');
        }

        if (
            ! $this->app->environment('testing')
            && $oauthEnabled
            && (
                $firebaseProjectId === ''
                || preg_match('/^[a-z0-9][a-z0-9-]{4,29}$/', $firebaseProjectId) !== 1
                || $firebaseCredentials === ''
                || ! is_file($firebaseCredentials)
            )
        ) {
            throw new InvalidArgumentException(
                'Google OAuth Firebase configuration is incomplete.',
            );
        }

        if (! $this->app->environment('testing')) {
            $queue = config('queue.connections.database');
            $queueConnection = $queue['connection'] ?? null;
            $cache = config('cache.stores.database');
            $cacheConnection = $cache['connection'] ?? null;

            if (
                config('queue.default') !== 'database'
                || ($queueConnection !== null
                    && $queueConnection !== config('database.default'))
                || (bool) ($queue['after_commit'] ?? true)
                || (int) ($queue['retry_after'] ?? 0) <= 90
                || config('cache.default') !== 'database'
                || ($cacheConnection !== null
                    && $cacheConnection !== config('database.default'))
            ) {
                throw new InvalidArgumentException('LATCH queue or cache configuration is unsafe.');
            }
        }

        if (! $this->app->environment(['local', 'testing'])) {
            $appUrl = parse_url((string) config('app.url'));
            $photoUrl = parse_url((string) config('filesystems.disks.public.url'));
            $supportEmail = (string) config('latch.legal.support_email');
            $credentialsPath = (string) config('latch.push.fcm.credentials_path');
            $deepLinkScheme = (string) config('latch.mobile.deep_link_scheme');

            if (
                (bool) config('app.debug')
                ||
                ! is_array($appUrl)
                || strtolower((string) ($appUrl['scheme'] ?? '')) !== 'https'
                || ! isset($appUrl['host'])
                || ! is_array($photoUrl)
                || strtolower((string) ($photoUrl['scheme'] ?? '')) !== 'https'
                || ! isset($photoUrl['host'])
                || ! config('latch.push.enabled')
                || (string) config('latch.push.fcm.project_id') === ''
                || $credentialsPath === ''
                || ! is_file($credentialsPath)
                || config('mail.default') === 'log'
                || ! filter_var($supportEmail, FILTER_VALIDATE_EMAIL)
                || str_ends_with(mb_strtolower($supportEmail), '@example.com')
                || preg_match('/^[a-z][a-z0-9+.-]*$/', $deepLinkScheme) !== 1
            ) {
                throw new InvalidArgumentException(
                    'LATCH production security, mail, push, or legal configuration is incomplete.',
                );
            }
        }
    }
}
