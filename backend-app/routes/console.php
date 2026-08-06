<?php

use Illuminate\Foundation\Inspiring;
use Illuminate\Support\Facades\Artisan;
use Illuminate\Support\Facades\Schedule;

Artisan::command('inspire', function () {
    $this->comment(Inspiring::quote());
})->purpose('Display an inspiring quote');

Schedule::command('latch:dispatch-sync')
    ->name('latch.sync')
    ->everyTenSeconds()
    ->onOneServer()
    ->withoutOverlapping(2);

Schedule::command('latch:prune')
    ->name('latch.prune')
    ->daily()
    ->onOneServer()
    ->withoutOverlapping(60);

Schedule::command('sanctum:prune-expired --hours=24')
    ->name('latch.sanctum-prune')
    ->daily()
    ->onOneServer()
    ->withoutOverlapping(60);
