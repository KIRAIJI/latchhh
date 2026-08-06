# Password change and session security

Use `PUT /api/v1/profile/password` with the active bearer token:

```json
{
  "current_password": "old-password",
  "password": "new-password",
  "password_confirmation": "new-password"
}
```

All fields are required; unrelated fields are rejected. The new password must be confirmed, at least 8 characters, no more than 72 UTF-8 bytes, and different from the current password. Password strings are never trimmed or normalized.

The backend locks the user, revalidates that the current Sanctum token still exists and is unexpired, checks the current password, changes the hash, and revokes every other token in one transaction. The current token remains active.

```json
{
  "success": true,
  "message": "Password changed successfully.",
  "data": {
    "sessions_revoked": 2
  }
}
```

A wrong current password returns `422 AUTH_PASSWORD_INCORRECT` with only a `current_password` field error. An absent/expired current token returns `401 AUTH_UNAUTHENTICATED`.

The endpoint shares the sensitive-action limit of five attempts per 15 minutes per user and IP.

`POST /auth/logout` revokes only the current token. `POST /auth/logout-all` revokes all tokens, including the caller. Flutter must remove secure local credentials and navigate to login after logout-all.

Changing the normalized profile email requires current-password reauthentication. Permanent deletion uses the current password when `has_password` is true; a passwordless Google account instead submits a linked Firebase ID token with an `auth_time` no more than five minutes old. Name-only profile changes do not require reauthentication.

Google-created passwordless users request an authenticated setup email through `POST /api/v1/auth/password/setup-link`. Its subject, action, deep-link mode, and Flutter screen use **Set Password** wording while reusing the same single-use broker token and strong-password validation as recovery. Completing setup revokes all existing Sanctum sessions and FCM installations, so the app clears local credentials and returns to sign-in. Until setup completes, password login, password change, and email change are unavailable.
