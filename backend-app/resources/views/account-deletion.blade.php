<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Delete your LATCH account</title>
    <style>
        body { background:#f7f8fa; color:#182230; font:16px/1.6 system-ui,sans-serif; margin:0; }
        main { background:white; border:1px solid #e4e7ec; border-radius:16px; margin:32px auto; max-width:720px; padding:32px; }
        h1 { line-height:1.2; } a.button { background:#b42318; border-radius:8px; color:white; display:inline-block; padding:12px 18px; text-decoration:none; }
        @media (max-width:600px) { main { border:0; border-radius:0; margin:0; padding:24px; } }
    </style>
</head>
<body>
<main>
    <h1>Delete your LATCH account</h1>
    <p>In the app, open <strong>Profile → Settings → Delete Account Permanently</strong>. Confirm with your current password. This immediately signs out all sessions and releases your claimed trackers.</p>
    <p>If you no longer have the app, request deletion using the email address associated with your LATCH account. Support will verify account ownership before deletion.</p>
    <p><a class="button" href="mailto:{{ $supportEmail }}?subject=LATCH%20account%20deletion%20request">Request account deletion</a></p>
    <h2>Data deleted</h2>
    <p>The account, profile photo, geofences, notifications, claim-scoped positions, activity history, access tokens, and push tokens are deleted. The unowned master tracker registration remains so the physical tracker can be claimed again, but prior ownership and telemetry are cleared.</p>
    <h2>Retention exceptions</h2>
    <p>Limited security records or encrypted backups may remain until their documented expiry when required for security, fraud prevention, disaster recovery, or law. They are not restored for ordinary product use.</p>
</main>
</body>
</html>
