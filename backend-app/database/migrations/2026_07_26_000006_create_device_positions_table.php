<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('device_positions', function (Blueprint $table) {
            $table->id();
            $table->foreignId('user_id')->constrained()->cascadeOnDelete();
            $table->foreignId('device_id')->constrained()->cascadeOnDelete();
            $table->unsignedBigInteger('claim_version');
            $table->unsignedBigInteger('provider_position_id');
            $table->decimal('latitude', 10, 7);
            $table->decimal('longitude', 10, 7);
            $table->dateTime('recorded_at', 3);
            $table->timestamp('created_at', 3)->useCurrent();

            $table->unique(
                ['device_id', 'claim_version', 'provider_position_id'],
                'device_position_unique'
            );
            $table->index(
                ['user_id', 'device_id', 'claim_version', 'recorded_at', 'id'],
                'device_position_cursor_idx'
            );
            $table->index(['recorded_at', 'id'], 'device_position_prune_idx');
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('device_positions');
    }
};
