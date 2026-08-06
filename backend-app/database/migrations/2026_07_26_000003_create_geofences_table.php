<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('geofences', function (Blueprint $table) {
            $table->id();
            $table->foreignId('device_id')->unique()->constrained()->cascadeOnDelete();
            $table->string('name', 100);
            $table->decimal('center_latitude', 10, 7);
            $table->decimal('center_longitude', 10, 7);
            $table->decimal('radius_meters', 8, 2);
            $table->boolean('notify_on_enter')->default(false);
            $table->boolean('notify_on_exit')->default(false);
            $table->boolean('is_active')->default(false);
            $table->boolean('last_inside')->nullable();
            $table->unsignedBigInteger('last_evaluated_position_id')->nullable();
            $table->dateTime('last_evaluated_at', 3)->nullable();
            $table->timestamps(3);
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('geofences');
    }
};
