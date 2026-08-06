<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="referrer" content="no-referrer">
    <title>{{ $verified ? 'Email verified' : 'Verification unavailable' }} · LATCH</title>
    <style>
        :root {
            color-scheme: light;
            font-family: Inter, ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            color: #171717;
            background: #f6f7f9;
        }
        * { box-sizing: border-box; }
        body {
            min-height: 100vh;
            margin: 0;
            display: grid;
            place-items: center;
            padding: 24px;
        }
        main {
            width: min(100%, 440px);
            padding: 32px 28px;
            text-align: center;
            background: #fff;
            border: 1px solid #dedfe3;
            border-radius: 20px;
            box-shadow: 0 14px 40px rgba(0, 0, 0, .08);
        }
        .icon {
            width: 56px;
            height: 56px;
            margin: 0 auto 18px;
            display: grid;
            place-items: center;
            border-radius: 50%;
            color: {{ $verified ? '#087a3e' : '#a83a31' }};
            background: {{ $verified ? '#e8f7ef' : '#fcebea' }};
            font-size: 28px;
            font-weight: 800;
        }
        h1 { margin: 0 0 10px; font-size: 25px; line-height: 1.2; }
        p { margin: 0; color: #60646c; line-height: 1.55; }
        a {
            display: block;
            margin-top: 24px;
            padding: 14px 18px;
            color: #fff;
            background: #171717;
            border-radius: 12px;
            text-decoration: none;
            font-weight: 700;
        }
        small { display: block; margin-top: 14px; color: #777b83; line-height: 1.45; }
    </style>
</head>
<body>
<main>
    <div class="icon" aria-hidden="true">{{ $verified ? '✓' : '!' }}</div>
    @if ($verified)
        <h1>Email verified</h1>
        <p>Your LATCH account is ready. Open the app to continue.</p>
    @else
        <h1>Verification link unavailable</h1>
        <p>This link is invalid or expired. Open LATCH and request a new verification email.</p>
    @endif
    <a href="{{ $appLink }}">Open LATCH</a>
    <small>If the app does not open automatically, return to it manually. It will check your verification status.</small>
</main>
</body>
</html>
