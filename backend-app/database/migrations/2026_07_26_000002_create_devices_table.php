<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('devices', function (Blueprint $table) {
            $table->id();
            $table->string('device_uid', 15)->unique();
            $trackerUniqueId = $table->string('tracker_unique_id', 191);
            if (DB::getDriverName() === 'mysql') {
                $trackerUniqueId->collation('utf8mb4_bin');
            }
            $trackerUniqueId->unique();
            $table->unsignedBigInteger('tracker_device_id')->nullable()->unique();
            $table->foreignId('user_id')->nullable()->constrained()->restrictOnDelete();
            $table->string('item_name', 100)->nullable();
            $table->unsignedBigInteger('claim_version')->default(0);
            $table->dateTime('claimed_at', 3)->nullable()->index();
            $table->dateTime('tracker_cursor_at', 3)->nullable();
            $table->unsignedBigInteger('last_provider_position_id')->nullable();
            $table->decimal('last_latitude', 10, 7)->nullable();
            $table->decimal('last_longitude', 10, 7)->nullable();
            $table->dateTime('last_position_at', 3)->nullable();
            $table->unsignedBigInteger('last_telemetry_position_id')->nullable();
            $table->dateTime('last_telemetry_at', 3)->nullable();
            $table->dateTime('last_communication_at', 3)->nullable();
            $table->string('last_provider_connection_status', 20)->nullable();
            $table->unsignedTinyInteger('last_battery_percentage')->nullable();
            $table->string('last_gnss_status', 20)->nullable();
            $table->unsignedSmallInteger('last_satellites')->nullable();
            $table->decimal('last_hdop', 6, 2)->nullable();
            $table->unsignedTinyInteger('last_gsm_csq')->nullable();
            $table->dateTime('last_synced_at', 3)->nullable();
            $table->string('notification_battery_state', 20)->nullable();
            $table->string('notification_connection_state', 20)->nullable();
            $table->dateTime('notification_connection_reference_at', 3)->nullable();
            $table->timestamps(3);

            $table->index(['user_id', 'claim_version'], 'devices_owner_claim_idx');
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('devices');
    }
};
