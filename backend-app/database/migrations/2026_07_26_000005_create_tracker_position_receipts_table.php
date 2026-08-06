<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('tracker_position_receipts', function (Blueprint $table) {
            $table->id();
            $table->foreignId('user_id')->constrained()->cascadeOnDelete();
            $table->foreignId('device_id')->constrained()->cascadeOnDelete();
            $table->unsignedBigInteger('claim_version');
            $table->unsignedBigInteger('provider_position_id');
            $table->dateTime('recorded_at', 3);
            $table->timestamp('created_at', 3)->useCurrent();

            $table->unique(
                ['device_id', 'claim_version', 'provider_position_id'],
                'tracker_receipt_unique'
            );
            $table->index(['created_at', 'id'], 'tracker_receipt_prune_idx');
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('tracker_position_receipts');
    }
};
