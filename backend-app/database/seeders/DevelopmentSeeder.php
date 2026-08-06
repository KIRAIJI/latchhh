<?php

namespace Database\Seeders;

use App\Models\Device;
use App\Models\User;
use Illuminate\Database\Seeder;
use RuntimeException;

class DevelopmentSeeder extends Seeder
{
    public function run(): void
    {
        if (! app()->environment(['local', 'testing'])) {
            throw new RuntimeException('DevelopmentSeeder may run only in local or testing.');
        }

        User::factory()->create([
            'name' => 'LATCH Demo User',
            'email' => 'demo@example.test',
            'password' => 'password',
        ]);

        Device::factory()->count(3)->create();
    }
}
