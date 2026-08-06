package com.shangxian.pinkink.db;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/* JADX INFO: loaded from: classes.dex */
class PinkInkRoomDatabase_AutoMigration_1_2_Impl extends Migration {
    public PinkInkRoomDatabase_AutoMigration_1_2_Impl() {
        super(1, 2);
    }

    @Override // androidx.room.migration.Migration
    public void migrate(SupportSQLiteDatabase database) {
        database.execSQL("ALTER TABLE `t_user_info` ADD COLUMN `avatar` TEXT DEFAULT NULL");
    }
}
