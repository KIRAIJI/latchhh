<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('users', function (Blueprint $table) {
            $table->boolean('notify_geofence_events')->default(true)
                ->after('notifications_enabled');
            $table->boolean('notify_battery_events')->default(true)
                ->after('notify_geofence_events');
            $table->boolean('notify_device_status_events')->default(true)
                ->after('notify_battery_events');
        });

        Schema::create('push_tokens', function (Blueprint $table) {
            $table->id();
            $table->foreignId('user_id')->constrained()->cascadeOnDelete();
            $table->char('token_hash', 64)->unique();
            $table->text('token');
            $table->string('platform', 16);
            $table->string('device_name', 100)->nullable();
            $table->string('app_version', 50)->nullable();
            $table->timestamp('last_seen_at');
            $table->timestamps();
            $table->index(['user_id', 'last_seen_at']);
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('push_tokens');

        Schema::table('users', function (Blueprint $table) {
            $table->dropColumn([
                'notify_geofence_events',
                'notify_battery_events',
                'notify_device_status_events',
            ]);
        });
    }
};
