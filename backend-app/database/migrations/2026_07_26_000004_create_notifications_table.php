<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('notifications', function (Blueprint $table) {
            $table->id();
            $table->foreignId('user_id')->constrained()->cascadeOnDelete();
            $table->foreignId('device_id')->nullable()->constrained()->cascadeOnDelete();
            $table->string('type', 40);
            $table->string('title', 120);
            $table->text('message');
            $eventKey = $table->string('event_key', 191);
            if (DB::getDriverName() === 'mysql') {
                $eventKey->collation('utf8mb4_bin');
            }
            $eventKey->unique();
            $table->dateTime('read_at', 3)->nullable();
            $table->timestamps(3);

            $table->index(['user_id', 'read_at', 'created_at', 'id'], 'notif_read_feed_idx');
            $table->index(['user_id', 'type', 'created_at', 'id'], 'notif_type_feed_idx');
            $table->index(['user_id', 'type', 'read_at', 'created_at', 'id'], 'notif_type_read_idx');
            $table->index(['user_id', 'created_at', 'id'], 'notif_feed_idx');
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('notifications');
    }
};
