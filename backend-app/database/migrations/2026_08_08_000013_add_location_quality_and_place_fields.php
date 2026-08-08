<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('devices', function (Blueprint $table) {
            $table->string('last_location_source', 16)->nullable()->after('last_longitude');
            $table->decimal('last_location_accuracy_meters', 8, 2)->nullable()->after('last_location_source');
            $table->string('last_place_name', 191)->nullable()->after('last_location_accuracy_meters');
            $table->dateTime('last_place_resolved_at', 3)->nullable()->after('last_place_name');
        });

        Schema::table('device_positions', function (Blueprint $table) {
            $table->string('source', 16)->default('gnss')->after('longitude');
            $table->decimal('accuracy_meters', 8, 2)->nullable()->after('source');
        });

        Schema::table('geofences', function (Blueprint $table) {
            $table->boolean('pending_inside')->nullable()->after('last_inside');
            $table->unsignedTinyInteger('pending_confirmation_count')->default(0)->after('pending_inside');
        });
    }

    public function down(): void
    {
        Schema::table('geofences', function (Blueprint $table) {
            $table->dropColumn(['pending_inside', 'pending_confirmation_count']);
        });

        Schema::table('device_positions', function (Blueprint $table) {
            $table->dropColumn(['source', 'accuracy_meters']);
        });

        Schema::table('devices', function (Blueprint $table) {
            $table->dropColumn([
                'last_location_source',
                'last_location_accuracy_meters',
                'last_place_name',
                'last_place_resolved_at',
            ]);
        });
    }
};
