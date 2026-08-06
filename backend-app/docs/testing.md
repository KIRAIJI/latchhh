# Testing

The automated suite uses Pest and an in-memory SQLite database:

```bash
php artisan test
```

Run formatting verification:

```bash
vendor/bin/pint --test
```

The suite covers:

- Sanctum registration/login/session restoration/logout
- cryptographic Firebase/Google ID-token verification, verified-email linking rules, passwordless accounts, and recent OAuth reauthentication for deletion
- Terms/Privacy consent, email-verification delivery/gating/resend/signed links
- non-enumerating password recovery, strong reset validation, and session/push-token revocation
- password byte limit, password change, other-session revocation, and reauthentication errors
- protected profile/email/settings, managed photos, and account deletion
- device claim/rename/release, ownership isolation, and master mapping preservation
- real current-claim location history and encrypted cursor binding
- append-only released-item activity privacy
- geofence save/release behavior
- Traccar DTO mapping, strict `fixTime`, invalid attributes, and identifier mismatches
- valid/invalid GNSS synchronization, receipt deduplication, real history, geofence/battery transitions
- connection episode behavior, GSM boundaries, and notification suppression
- notification pagination, unread counts, read operations, and cross-user protection
- granular notification preferences, encrypted FCM token lifecycle, one-time queue dispatch, and invalid-token cleanup

Repository CI at `.github/workflows/ci.yml` runs backend formatting/tests plus Flutter formatting, analysis, tests, and Android debug compilation on every push and pull request. Branch protection should require both jobs before merge.

Before reporting backend completion, also run:

```bash
php artisan route:list --path=api/v1
php artisan schedule:list
php artisan latch:diagnose
```

`latch:diagnose` may correctly return non-zero on an unconfigured developer machine (missing Traccar values/storage link). Configure those dependencies before treating it as a deployment acceptance result.

## Live Traccar acceptance

Live testing is opt-in and must use rotated credentials in local environment variables. Never add them to PHPUnit, fixtures, source, screenshots, or logs.

1. Configure a dedicated test tracker.
2. Pre-register it with `latch:register-device`.
3. Claim it through the API.
4. Run the scheduler/worker.
5. Verify the item resource and retained history reflect real validated fixes.
6. Confirm Flutter has no Traccar credential/direct call.

The fake provider remains the deterministic default for automated synchronization tests.
