<?php

namespace App\Jobs;

use App\Services\FirebaseAuthAdminClient;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Foundation\Queue\Queueable;

class DeleteFirebaseAuthUser implements ShouldQueue
{
    use Queueable;

    public int $tries = 5;

    public function __construct(public readonly string $firebaseUid)
    {
        $this->onConnection('database');
    }

    public function backoff(): array
    {
        return [30, 120, 300, 900];
    }

    public function handle(FirebaseAuthAdminClient $firebase): void
    {
        $firebase->deleteUser($this->firebaseUid);
    }
}
