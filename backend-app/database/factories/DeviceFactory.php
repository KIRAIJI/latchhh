<?php

namespace Database\Factories;

use App\Models\Device;
use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends Factory<Device>
 */
class DeviceFactory extends Factory
{
    public function definition(): array
    {
        return [
            'device_uid' => 'LATCH-'.fake()->unique()->regexify('[A-Z0-9]{4}')
                .'-'.fake()->unique()->regexify('[A-Z0-9]{4}'),
            'tracker_unique_id' => fake()->unique()->uuid(),
            'tracker_device_id' => fake()->unique()->numberBetween(1, 2000000000),
        ];
    }
}
