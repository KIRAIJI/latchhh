<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('device_activity_logs', function (Blueprint $table) {
            $table->id();
            $table->foreignId('user_id')->constrained()->cascadeOnDelete();
            $table->foreignId('device_id')->constrained()->cascadeOnDelete();
            $table->unsignedBigInteger('claim_version');
            $table->foreignId('actor_user_id')->nullable()->constrained('users')->nullOnDelete();
            $table->string('event_type', 50);
            $table->string('source', 20);
            $table->string('title', 120);
            $table->text('description');
            $eventKey = $table->string('event_key', 191);
            if (DB::getDriverName() === 'mysql') {
                $eventKey->collation('utf8mb4_bin');
            }
            $eventKey->unique();
            $table->string('device_uid_snapshot', 15);
            $table->string('item_name_snapshot', 100)->nullable();
            $table->json('event_data')->nullable();
            $table->dateTime('occurred_at', 3);
            $table->timestamp('created_at', 3)->useCurrent();

            $table->index(['user_id', 'occurred_at', 'id'], 'activity_feed_idx');
            $table->index(
                ['device_id', 'user_id', 'claim_version', 'occurred_at', 'id'],
                'activity_item_idx'
            );
            $table->index(['user_id', 'event_type', 'occurred_at', 'id'], 'activity_type_idx');
            $table->index(['user_id', 'source', 'occurred_at', 'id'], 'activity_source_idx');
            $table->index(['user_id', 'device_uid_snapshot', 'occurred_at', 'id'], 'activity_uid_idx');
            $table->index(['occurred_at', 'id'], 'activity_prune_idx');
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('device_activity_logs');
    }
};
