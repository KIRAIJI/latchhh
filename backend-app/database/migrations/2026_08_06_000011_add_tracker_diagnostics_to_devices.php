<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('devices', function (Blueprint $table) {
            $table->string('last_power_state', 20)
                ->nullable()
                ->after('last_battery_percentage');
            $table->string('last_firmware_version', 32)
                ->nullable()
                ->after('last_gsm_csq');
            $table->string('last_reset_reason', 32)
                ->nullable()
                ->after('last_firmware_version');
        });
    }

    public function down(): void
    {
        Schema::table('devices', function (Blueprint $table) {
            $table->dropColumn([
                'last_power_state',
                'last_firmware_version',
                'last_reset_reason',
            ]);
        });
    }
};
