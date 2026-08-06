<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('users', function (Blueprint $table): void {
            $table->timestamp('password_set_at')->nullable()->after('password');
        });

        DB::table('users')
            ->whereNull('password_set_at')
            ->update(['password_set_at' => DB::raw('COALESCE(created_at, CURRENT_TIMESTAMP)')]);

        Schema::create('oauth_identities', function (Blueprint $table): void {
            $table->id();
            $table->foreignId('user_id')->constrained()->cascadeOnDelete();
            $table->string('provider', 32);
            $table->string('provider_subject', 255);
            $table->string('firebase_uid', 128);
            $table->string('provider_email');
            $table->timestamp('last_login_at');
            $table->timestamps();

            $table->unique(['provider', 'provider_subject']);
            $table->unique(['provider', 'firebase_uid']);
            $table->unique(['user_id', 'provider']);
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('oauth_identities');

        Schema::table('users', function (Blueprint $table): void {
            $table->dropColumn('password_set_at');
        });
    }
};
