<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>{{ $title }}</title>
    <style>
        body { background:#f7f8fa; color:#182230; font:16px/1.6 system-ui,sans-serif; margin:0; }
        main { background:white; border:1px solid #e4e7ec; border-radius:16px; margin:32px auto; max-width:760px; padding:32px; }
        h1 { line-height:1.2; } h2 { font-size:1.15rem; margin-top:2rem; }
        .meta { color:#667085; } a { color:#175cd3; }
        @media (max-width:600px) { main { border:0; border-radius:0; margin:0; padding:24px; } }
    </style>
</head>
<body>
<main>
    <h1>{{ $title }}</h1>
    <p class="meta">Effective version: {{ $effective }}</p>
    @foreach ($sections as $heading => $body)
        <section>
            <h2>{{ $heading }}</h2>
            <p>{{ $body }}</p>
        </section>
    @endforeach
    <h2>Contact</h2>
    <p>Email <a href="mailto:{{ config('latch.legal.support_email') }}">{{ config('latch.legal.support_email') }}</a>.</p>
</main>
</body>
</html>
