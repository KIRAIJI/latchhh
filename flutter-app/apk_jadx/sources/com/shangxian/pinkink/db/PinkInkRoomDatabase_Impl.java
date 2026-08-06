package com.shangxian.pinkink.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.shangxian.pinkink.db.dao.UserAlbumDao;
import com.shangxian.pinkink.db.dao.UserAlbumDao_Impl;
import com.shangxian.pinkink.db.dao.UserCreateThemeDao;
import com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl;
import com.shangxian.pinkink.db.dao.UserFontDao;
import com.shangxian.pinkink.db.dao.UserFontDao_Impl;
import com.shangxian.pinkink.db.dao.UserInfoDao;
import com.shangxian.pinkink.db.dao.UserInfoDao_Impl;
import com.shangxian.pinkink.db.dao.UserLikeThemesDao;
import com.shangxian.pinkink.db.dao.UserLikeThemesDao_Impl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class PinkInkRoomDatabase_Impl extends PinkInkRoomDatabase {
    private volatile UserAlbumDao _userAlbumDao;
    private volatile UserCreateThemeDao _userCreateThemeDao;
    private volatile UserFontDao _userFontDao;
    private volatile UserInfoDao _userInfoDao;
    private volatile UserLikeThemesDao _userLikeThemesDao;

    @Override // androidx.room.RoomDatabase
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
        return configuration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(configuration.context).name(configuration.name).callback(new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) { // from class: com.shangxian.pinkink.db.PinkInkRoomDatabase_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPostMigrate(SupportSQLiteDatabase _db) {
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("CREATE TABLE IF NOT EXISTS `t_user_info` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_id` TEXT, `nick_name` TEXT, `mobile` TEXT, `state` INTEGER NOT NULL, `avatar` TEXT, `token` TEXT)");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `t_user_font` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `font_id` TEXT, `font_name` TEXT, `font_cover` TEXT, `font_file_path` TEXT, `user_id` TEXT)");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `t_user_like_themes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `theme_id` TEXT NOT NULL, `name` TEXT NOT NULL, `like_user_id` TEXT NOT NULL, `cover_url` TEXT, `isSelected` INTEGER NOT NULL)");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `t_user_create_theme` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_id` TEXT NOT NULL, `album_column_id` INTEGER NOT NULL, `compose_image_path` TEXT NOT NULL, `background_image_path` TEXT NOT NULL)");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `t_user_album` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `album_id` TEXT NOT NULL, `name` TEXT NOT NULL, `user_id` TEXT NOT NULL, `create_time` INTEGER NOT NULL, `update_time` INTEGER NOT NULL)");
                _db.execSQL("CREATE TABLE IF NOT EXISTS `t_theme_material` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `type` INTEGER NOT NULL, `content` TEXT NOT NULL, `angle` REAL NOT NULL, `x` INTEGER NOT NULL, `y` INTEGER NOT NULL, `width` REAL NOT NULL, `height` REAL NOT NULL, `create_theme_id` INTEGER NOT NULL, `text_size` REAL NOT NULL, `text_color` TEXT NOT NULL, `font_family_file_name` TEXT NOT NULL)");
                _db.execSQL(RoomMasterTable.CREATE_QUERY);
                _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '26619bd83b26f2569b686c99d474ada4')");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("DROP TABLE IF EXISTS `t_user_info`");
                _db.execSQL("DROP TABLE IF EXISTS `t_user_font`");
                _db.execSQL("DROP TABLE IF EXISTS `t_user_like_themes`");
                _db.execSQL("DROP TABLE IF EXISTS `t_user_create_theme`");
                _db.execSQL("DROP TABLE IF EXISTS `t_user_album`");
                _db.execSQL("DROP TABLE IF EXISTS `t_theme_material`");
                if (PinkInkRoomDatabase_Impl.this.mCallbacks != null) {
                    int size = PinkInkRoomDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) PinkInkRoomDatabase_Impl.this.mCallbacks.get(i)).onDestructiveMigration(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected void onCreate(SupportSQLiteDatabase _db) {
                if (PinkInkRoomDatabase_Impl.this.mCallbacks != null) {
                    int size = PinkInkRoomDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) PinkInkRoomDatabase_Impl.this.mCallbacks.get(i)).onCreate(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(SupportSQLiteDatabase _db) {
                PinkInkRoomDatabase_Impl.this.mDatabase = _db;
                PinkInkRoomDatabase_Impl.this.internalInitInvalidationTracker(_db);
                if (PinkInkRoomDatabase_Impl.this.mCallbacks != null) {
                    int size = PinkInkRoomDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) PinkInkRoomDatabase_Impl.this.mCallbacks.get(i)).onOpen(_db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPreMigrate(SupportSQLiteDatabase _db) {
                DBUtil.dropFtsSyncTriggers(_db);
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
                HashMap map = new HashMap(7);
                map.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
                map.put("user_id", new TableInfo.Column("user_id", "TEXT", false, 0, null, 1));
                map.put("nick_name", new TableInfo.Column("nick_name", "TEXT", false, 0, null, 1));
                map.put("mobile", new TableInfo.Column("mobile", "TEXT", false, 0, null, 1));
                map.put("state", new TableInfo.Column("state", "INTEGER", true, 0, null, 1));
                map.put("avatar", new TableInfo.Column("avatar", "TEXT", false, 0, null, 1));
                map.put("token", new TableInfo.Column("token", "TEXT", false, 0, null, 1));
                TableInfo tableInfo = new TableInfo("t_user_info", map, new HashSet(0), new HashSet(0));
                TableInfo tableInfo2 = TableInfo.read(_db, "t_user_info");
                if (!tableInfo.equals(tableInfo2)) {
                    return new RoomOpenHelper.ValidationResult(false, "t_user_info(com.shangxian.pinkink.bean.UserInfoBean).\n Expected:\n" + tableInfo + "\n Found:\n" + tableInfo2);
                }
                HashMap map2 = new HashMap(6);
                map2.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
                map2.put("font_id", new TableInfo.Column("font_id", "TEXT", false, 0, null, 1));
                map2.put("font_name", new TableInfo.Column("font_name", "TEXT", false, 0, null, 1));
                map2.put("font_cover", new TableInfo.Column("font_cover", "TEXT", false, 0, null, 1));
                map2.put("font_file_path", new TableInfo.Column("font_file_path", "TEXT", false, 0, null, 1));
                map2.put("user_id", new TableInfo.Column("user_id", "TEXT", false, 0, null, 1));
                TableInfo tableInfo3 = new TableInfo("t_user_font", map2, new HashSet(0), new HashSet(0));
                TableInfo tableInfo4 = TableInfo.read(_db, "t_user_font");
                if (!tableInfo3.equals(tableInfo4)) {
                    return new RoomOpenHelper.ValidationResult(false, "t_user_font(com.shangxian.pinkink.bean.FontBean).\n Expected:\n" + tableInfo3 + "\n Found:\n" + tableInfo4);
                }
                HashMap map3 = new HashMap(6);
                map3.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
                map3.put("theme_id", new TableInfo.Column("theme_id", "TEXT", true, 0, null, 1));
                map3.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, 1));
                map3.put("like_user_id", new TableInfo.Column("like_user_id", "TEXT", true, 0, null, 1));
                map3.put("cover_url", new TableInfo.Column("cover_url", "TEXT", false, 0, null, 1));
                map3.put("isSelected", new TableInfo.Column("isSelected", "INTEGER", true, 0, null, 1));
                TableInfo tableInfo5 = new TableInfo("t_user_like_themes", map3, new HashSet(0), new HashSet(0));
                TableInfo tableInfo6 = TableInfo.read(_db, "t_user_like_themes");
                if (!tableInfo5.equals(tableInfo6)) {
                    return new RoomOpenHelper.ValidationResult(false, "t_user_like_themes(com.shangxian.pinkink.bean.ThemeBean).\n Expected:\n" + tableInfo5 + "\n Found:\n" + tableInfo6);
                }
                HashMap map4 = new HashMap(5);
                map4.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
                map4.put("user_id", new TableInfo.Column("user_id", "TEXT", true, 0, null, 1));
                map4.put("album_column_id", new TableInfo.Column("album_column_id", "INTEGER", true, 0, null, 1));
                map4.put("compose_image_path", new TableInfo.Column("compose_image_path", "TEXT", true, 0, null, 1));
                map4.put("background_image_path", new TableInfo.Column("background_image_path", "TEXT", true, 0, null, 1));
                TableInfo tableInfo7 = new TableInfo("t_user_create_theme", map4, new HashSet(0), new HashSet(0));
                TableInfo tableInfo8 = TableInfo.read(_db, "t_user_create_theme");
                if (!tableInfo7.equals(tableInfo8)) {
                    return new RoomOpenHelper.ValidationResult(false, "t_user_create_theme(com.shangxian.pinkink.bean.UserCreateThemeBean).\n Expected:\n" + tableInfo7 + "\n Found:\n" + tableInfo8);
                }
                HashMap map5 = new HashMap(6);
                map5.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
                map5.put("album_id", new TableInfo.Column("album_id", "TEXT", true, 0, null, 1));
                map5.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, 1));
                map5.put("user_id", new TableInfo.Column("user_id", "TEXT", true, 0, null, 1));
                map5.put("create_time", new TableInfo.Column("create_time", "INTEGER", true, 0, null, 1));
                map5.put("update_time", new TableInfo.Column("update_time", "INTEGER", true, 0, null, 1));
                TableInfo tableInfo9 = new TableInfo("t_user_album", map5, new HashSet(0), new HashSet(0));
                TableInfo tableInfo10 = TableInfo.read(_db, "t_user_album");
                if (!tableInfo9.equals(tableInfo10)) {
                    return new RoomOpenHelper.ValidationResult(false, "t_user_album(com.shangxian.pinkink.bean.AlbumBean).\n Expected:\n" + tableInfo9 + "\n Found:\n" + tableInfo10);
                }
                HashMap map6 = new HashMap(12);
                map6.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
                map6.put("type", new TableInfo.Column("type", "INTEGER", true, 0, null, 1));
                map6.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, 1));
                map6.put("angle", new TableInfo.Column("angle", "REAL", true, 0, null, 1));
                map6.put("x", new TableInfo.Column("x", "INTEGER", true, 0, null, 1));
                map6.put("y", new TableInfo.Column("y", "INTEGER", true, 0, null, 1));
                map6.put("width", new TableInfo.Column("width", "REAL", true, 0, null, 1));
                map6.put("height", new TableInfo.Column("height", "REAL", true, 0, null, 1));
                map6.put("create_theme_id", new TableInfo.Column("create_theme_id", "INTEGER", true, 0, null, 1));
                map6.put("text_size", new TableInfo.Column("text_size", "REAL", true, 0, null, 1));
                map6.put("text_color", new TableInfo.Column("text_color", "TEXT", true, 0, null, 1));
                map6.put("font_family_file_name", new TableInfo.Column("font_family_file_name", "TEXT", true, 0, null, 1));
                TableInfo tableInfo11 = new TableInfo("t_theme_material", map6, new HashSet(0), new HashSet(0));
                TableInfo tableInfo12 = TableInfo.read(_db, "t_theme_material");
                if (!tableInfo11.equals(tableInfo12)) {
                    return new RoomOpenHelper.ValidationResult(false, "t_theme_material(com.shangxian.pinkink.bean.ThemeMaterialBean).\n Expected:\n" + tableInfo11 + "\n Found:\n" + tableInfo12);
                }
                return new RoomOpenHelper.ValidationResult(true, null);
            }
        }, "26619bd83b26f2569b686c99d474ada4", "d645a6d88e8a6a2eb22286482f283f43")).build());
    }

    @Override // androidx.room.RoomDatabase
    protected InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "t_user_info", "t_user_font", "t_user_like_themes", "t_user_create_theme", "t_user_album", "t_theme_material");
    }

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            writableDatabase.execSQL("DELETE FROM `t_user_info`");
            writableDatabase.execSQL("DELETE FROM `t_user_font`");
            writableDatabase.execSQL("DELETE FROM `t_user_like_themes`");
            writableDatabase.execSQL("DELETE FROM `t_user_create_theme`");
            writableDatabase.execSQL("DELETE FROM `t_user_album`");
            writableDatabase.execSQL("DELETE FROM `t_theme_material`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            writableDatabase.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!writableDatabase.inTransaction()) {
                writableDatabase.execSQL("VACUUM");
            }
        }
    }

    @Override // androidx.room.RoomDatabase
    protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
        HashMap map = new HashMap();
        map.put(UserInfoDao.class, UserInfoDao_Impl.getRequiredConverters());
        map.put(UserFontDao.class, UserFontDao_Impl.getRequiredConverters());
        map.put(UserLikeThemesDao.class, UserLikeThemesDao_Impl.getRequiredConverters());
        map.put(UserCreateThemeDao.class, UserCreateThemeDao_Impl.getRequiredConverters());
        map.put(UserAlbumDao.class, UserAlbumDao_Impl.getRequiredConverters());
        return map;
    }

    @Override // androidx.room.RoomDatabase
    public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
        return new HashSet();
    }

    @Override // androidx.room.RoomDatabase
    public List<Migration> getAutoMigrations(Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
        return Arrays.asList(new PinkInkRoomDatabase_AutoMigration_1_2_Impl());
    }

    @Override // com.shangxian.pinkink.db.PinkInkRoomDatabase
    public UserInfoDao userInfoDao() {
        UserInfoDao userInfoDao;
        if (this._userInfoDao != null) {
            return this._userInfoDao;
        }
        synchronized (this) {
            if (this._userInfoDao == null) {
                this._userInfoDao = new UserInfoDao_Impl(this);
            }
            userInfoDao = this._userInfoDao;
        }
        return userInfoDao;
    }

    @Override // com.shangxian.pinkink.db.PinkInkRoomDatabase
    public UserFontDao userFontDao() {
        UserFontDao userFontDao;
        if (this._userFontDao != null) {
            return this._userFontDao;
        }
        synchronized (this) {
            if (this._userFontDao == null) {
                this._userFontDao = new UserFontDao_Impl(this);
            }
            userFontDao = this._userFontDao;
        }
        return userFontDao;
    }

    @Override // com.shangxian.pinkink.db.PinkInkRoomDatabase
    public UserLikeThemesDao userLikeThemesDao() {
        UserLikeThemesDao userLikeThemesDao;
        if (this._userLikeThemesDao != null) {
            return this._userLikeThemesDao;
        }
        synchronized (this) {
            if (this._userLikeThemesDao == null) {
                this._userLikeThemesDao = new UserLikeThemesDao_Impl(this);
            }
            userLikeThemesDao = this._userLikeThemesDao;
        }
        return userLikeThemesDao;
    }

    @Override // com.shangxian.pinkink.db.PinkInkRoomDatabase
    public UserCreateThemeDao userCrateThemeDao() {
        UserCreateThemeDao userCreateThemeDao;
        if (this._userCreateThemeDao != null) {
            return this._userCreateThemeDao;
        }
        synchronized (this) {
            if (this._userCreateThemeDao == null) {
                this._userCreateThemeDao = new UserCreateThemeDao_Impl(this);
            }
            userCreateThemeDao = this._userCreateThemeDao;
        }
        return userCreateThemeDao;
    }

    @Override // com.shangxian.pinkink.db.PinkInkRoomDatabase
    public UserAlbumDao userAlbumDao() {
        UserAlbumDao userAlbumDao;
        if (this._userAlbumDao != null) {
            return this._userAlbumDao;
        }
        synchronized (this) {
            if (this._userAlbumDao == null) {
                this._userAlbumDao = new UserAlbumDao_Impl(this);
            }
            userAlbumDao = this._userAlbumDao;
        }
        return userAlbumDao;
    }
}
