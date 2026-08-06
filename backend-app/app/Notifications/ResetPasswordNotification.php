<?php

namespace App\Notifications;

use Illuminate\Auth\Notifications\ResetPassword;
use Illuminate\Bus\Queueable;
use Illuminate\Contracts\Queue\ShouldQueue;

class ResetPasswordNotification extends ResetPassword implements ShouldQueue
{
    use Queueable;

    public int $tries = 3;

    public function __construct(string $token)
    {
        parent::__construct($token);
        $this->onConnection('database');
    }

    public function backoff(): array
    {
        return [30, 120, 300];
    }
}
