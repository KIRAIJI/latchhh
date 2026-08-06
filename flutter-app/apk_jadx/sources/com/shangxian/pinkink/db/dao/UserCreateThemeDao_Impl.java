package com.shangxian.pinkink.db.dao;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.shangxian.pinkink.bean.ThemeMaterialBean;
import com.shangxian.pinkink.bean.UserCreateThemeBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* JADX INFO: loaded from: classes.dex */
public final class UserCreateThemeDao_Impl implements UserCreateThemeDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<ThemeMaterialBean> __insertionAdapterOfThemeMaterialBean;
    private final EntityInsertionAdapter<UserCreateThemeBean> __insertionAdapterOfUserCreateThemeBean;
    private final SharedSQLiteStatement __preparedStmtOfDeleteCreateMaterialByCreateThemeId;
    private final SharedSQLiteStatement __preparedStmtOfDeleteUserCreateThemeByAlbumColumnId;
    private final SharedSQLiteStatement __preparedStmtOfDeleteUserCreateThemeById;
    private final SharedSQLiteStatement __preparedStmtOfDeleteUserCreateThemeByUserId;
    private final EntityDeletionOrUpdateAdapter<UserCreateThemeBean> __updateAdapterOfUserCreateThemeBean;

    public UserCreateThemeDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfUserCreateThemeBean = new EntityInsertionAdapter<UserCreateThemeBean>(__db) { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR ABORT INTO `t_user_create_theme` (`id`,`user_id`,`album_column_id`,`compose_image_path`,`background_image_path`) VALUES (nullif(?, 0),?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, UserCreateThemeBean value) {
                stmt.bindLong(1, value.getColumnId());
                if (value.getUserId() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getUserId());
                }
                stmt.bindLong(3, value.getAlbumColumnId());
                if (value.getComposeImagePath() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getComposeImagePath());
                }
                if (value.getBackgroundImagePath() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getBackgroundImagePath());
                }
            }
        };
        this.__insertionAdapterOfThemeMaterialBean = new EntityInsertionAdapter<ThemeMaterialBean>(__db) { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR ABORT INTO `t_theme_material` (`id`,`type`,`content`,`angle`,`x`,`y`,`width`,`height`,`create_theme_id`,`text_size`,`text_color`,`font_family_file_name`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, ThemeMaterialBean value) {
                stmt.bindLong(1, value.getColumnId());
                stmt.bindLong(2, value.getType());
                if (value.getContent() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getContent());
                }
                stmt.bindDouble(4, value.getAngle());
                stmt.bindLong(5, value.getX());
                stmt.bindLong(6, value.getY());
                stmt.bindDouble(7, value.getWidth());
                stmt.bindDouble(8, value.getHeight());
                stmt.bindLong(9, value.getCreateThemeId());
                stmt.bindDouble(10, value.getTextSize());
                if (value.getTextColor() == null) {
                    stmt.bindNull(11);
                } else {
                    stmt.bindString(11, value.getTextColor());
                }
                if (value.getFontFamilyFileName() == null) {
                    stmt.bindNull(12);
                } else {
                    stmt.bindString(12, value.getFontFamilyFileName());
                }
            }
        };
        this.__updateAdapterOfUserCreateThemeBean = new EntityDeletionOrUpdateAdapter<UserCreateThemeBean>(__db) { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.3
            @Override // androidx.room.EntityDeletionOrUpdateAdapter, androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE OR ABORT `t_user_create_theme` SET `id` = ?,`user_id` = ?,`album_column_id` = ?,`compose_image_path` = ?,`background_image_path` = ? WHERE `id` = ?";
            }

            @Override // androidx.room.EntityDeletionOrUpdateAdapter
            public void bind(SupportSQLiteStatement stmt, UserCreateThemeBean value) {
                stmt.bindLong(1, value.getColumnId());
                if (value.getUserId() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getUserId());
                }
                stmt.bindLong(3, value.getAlbumColumnId());
                if (value.getComposeImagePath() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getComposeImagePath());
                }
                if (value.getBackgroundImagePath() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getBackgroundImagePath());
                }
                stmt.bindLong(6, value.getColumnId());
            }
        };
        this.__preparedStmtOfDeleteCreateMaterialByCreateThemeId = new SharedSQLiteStatement(__db) { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.4
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM t_theme_material WHERE create_theme_id = ?";
            }
        };
        this.__preparedStmtOfDeleteUserCreateThemeById = new SharedSQLiteStatement(__db) { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.5
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM t_user_create_theme WHERE id = ?";
            }
        };
        this.__preparedStmtOfDeleteUserCreateThemeByAlbumColumnId = new SharedSQLiteStatement(__db) { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.6
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM t_user_create_theme WHERE album_column_id = ?";
            }
        };
        this.__preparedStmtOfDeleteUserCreateThemeByUserId = new SharedSQLiteStatement(__db) { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.7
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM t_user_create_theme WHERE user_id = ?";
            }
        };
    }

    @Override // com.shangxian.pinkink.db.dao.UserCreateThemeDao
    public Object saveUserCreateTheme(final UserCreateThemeBean userCreateThemeBean, final Continuation<? super Long> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Long>() { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.8
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Long call() throws Exception {
                UserCreateThemeDao_Impl.this.__db.beginTransaction();
                try {
                    long jInsertAndReturnId = UserCreateThemeDao_Impl.this.__insertionAdapterOfUserCreateThemeBean.insertAndReturnId(userCreateThemeBean);
                    UserCreateThemeDao_Impl.this.__db.setTransactionSuccessful();
                    return Long.valueOf(jInsertAndReturnId);
                } finally {
                    UserCreateThemeDao_Impl.this.__db.endTransaction();
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserCreateThemeDao
    public Object saveUserCreateMaterialList(final List<ThemeMaterialBean> createMaterialList, final Continuation<? super List<Long>> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<List<Long>>() { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.9
            @Override // java.util.concurrent.Callable
            public List<Long> call() throws Exception {
                UserCreateThemeDao_Impl.this.__db.beginTransaction();
                try {
                    List<Long> listInsertAndReturnIdsList = UserCreateThemeDao_Impl.this.__insertionAdapterOfThemeMaterialBean.insertAndReturnIdsList(createMaterialList);
                    UserCreateThemeDao_Impl.this.__db.setTransactionSuccessful();
                    return listInsertAndReturnIdsList;
                } finally {
                    UserCreateThemeDao_Impl.this.__db.endTransaction();
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserCreateThemeDao
    public Object updateUserCreateTheme(final UserCreateThemeBean userCreateThemeBean, final Continuation<? super Unit> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.10
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                UserCreateThemeDao_Impl.this.__db.beginTransaction();
                try {
                    UserCreateThemeDao_Impl.this.__updateAdapterOfUserCreateThemeBean.handle(userCreateThemeBean);
                    UserCreateThemeDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    UserCreateThemeDao_Impl.this.__db.endTransaction();
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserCreateThemeDao
    public Object deleteCreateMaterialByCreateThemeId(final long createThemeId, final Continuation<? super Unit> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.11
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement supportSQLiteStatementAcquire = UserCreateThemeDao_Impl.this.__preparedStmtOfDeleteCreateMaterialByCreateThemeId.acquire();
                supportSQLiteStatementAcquire.bindLong(1, createThemeId);
                UserCreateThemeDao_Impl.this.__db.beginTransaction();
                try {
                    supportSQLiteStatementAcquire.executeUpdateDelete();
                    UserCreateThemeDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    UserCreateThemeDao_Impl.this.__db.endTransaction();
                    UserCreateThemeDao_Impl.this.__preparedStmtOfDeleteCreateMaterialByCreateThemeId.release(supportSQLiteStatementAcquire);
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserCreateThemeDao
    public Object deleteUserCreateThemeById(final long themeId, final Continuation<? super Unit> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.12
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement supportSQLiteStatementAcquire = UserCreateThemeDao_Impl.this.__preparedStmtOfDeleteUserCreateThemeById.acquire();
                supportSQLiteStatementAcquire.bindLong(1, themeId);
                UserCreateThemeDao_Impl.this.__db.beginTransaction();
                try {
                    supportSQLiteStatementAcquire.executeUpdateDelete();
                    UserCreateThemeDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    UserCreateThemeDao_Impl.this.__db.endTransaction();
                    UserCreateThemeDao_Impl.this.__preparedStmtOfDeleteUserCreateThemeById.release(supportSQLiteStatementAcquire);
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserCreateThemeDao
    public Object deleteUserCreateThemeByAlbumColumnId(final long albumColumnId, final Continuation<? super Unit> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.13
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement supportSQLiteStatementAcquire = UserCreateThemeDao_Impl.this.__preparedStmtOfDeleteUserCreateThemeByAlbumColumnId.acquire();
                supportSQLiteStatementAcquire.bindLong(1, albumColumnId);
                UserCreateThemeDao_Impl.this.__db.beginTransaction();
                try {
                    supportSQLiteStatementAcquire.executeUpdateDelete();
                    UserCreateThemeDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    UserCreateThemeDao_Impl.this.__db.endTransaction();
                    UserCreateThemeDao_Impl.this.__preparedStmtOfDeleteUserCreateThemeByAlbumColumnId.release(supportSQLiteStatementAcquire);
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserCreateThemeDao
    public Object deleteUserCreateThemeByUserId(final String userId, final Continuation<? super Unit> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.14
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement supportSQLiteStatementAcquire = UserCreateThemeDao_Impl.this.__preparedStmtOfDeleteUserCreateThemeByUserId.acquire();
                String str = userId;
                if (str == null) {
                    supportSQLiteStatementAcquire.bindNull(1);
                } else {
                    supportSQLiteStatementAcquire.bindString(1, str);
                }
                UserCreateThemeDao_Impl.this.__db.beginTransaction();
                try {
                    supportSQLiteStatementAcquire.executeUpdateDelete();
                    UserCreateThemeDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    UserCreateThemeDao_Impl.this.__db.endTransaction();
                    UserCreateThemeDao_Impl.this.__preparedStmtOfDeleteUserCreateThemeByUserId.release(supportSQLiteStatementAcquire);
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserCreateThemeDao
    public Object getUserCreateThemeListByUserId(final String userId, final Continuation<? super List<UserCreateThemeBean>> continuation) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM t_user_create_theme WHERE user_id = ?", 1);
        if (userId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, userId);
        }
        return CoroutinesRoom.execute(this.__db, false, DBUtil.createCancellationSignal(), new Callable<List<UserCreateThemeBean>>() { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.15
            @Override // java.util.concurrent.Callable
            public List<UserCreateThemeBean> call() throws Exception {
                Cursor cursorQuery = DBUtil.query(UserCreateThemeDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "user_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "album_column_id");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "compose_image_path");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "background_image_path");
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        arrayList.add(new UserCreateThemeBean(cursorQuery.getLong(columnIndexOrThrow), cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2), cursorQuery.getLong(columnIndexOrThrow3), cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4), cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getString(columnIndexOrThrow5)));
                    }
                    return arrayList;
                } finally {
                    cursorQuery.close();
                    roomSQLiteQueryAcquire.release();
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserCreateThemeDao
    public Object getUserCreateThemeListByUserIdAndAlbumColumnId(final String userId, final long albumColumnId, final Continuation<? super List<UserCreateThemeBean>> continuation) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM t_user_create_theme WHERE user_id = ? AND album_column_id = ?", 2);
        if (userId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, userId);
        }
        roomSQLiteQueryAcquire.bindLong(2, albumColumnId);
        return CoroutinesRoom.execute(this.__db, false, DBUtil.createCancellationSignal(), new Callable<List<UserCreateThemeBean>>() { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.16
            @Override // java.util.concurrent.Callable
            public List<UserCreateThemeBean> call() throws Exception {
                Cursor cursorQuery = DBUtil.query(UserCreateThemeDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "user_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "album_column_id");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "compose_image_path");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "background_image_path");
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        arrayList.add(new UserCreateThemeBean(cursorQuery.getLong(columnIndexOrThrow), cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2), cursorQuery.getLong(columnIndexOrThrow3), cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4), cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getString(columnIndexOrThrow5)));
                    }
                    return arrayList;
                } finally {
                    cursorQuery.close();
                    roomSQLiteQueryAcquire.release();
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserCreateThemeDao
    public Object getFirstUserCreateThemeListByUserId(final String userId, final Continuation<? super List<UserCreateThemeBean>> continuation) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM t_user_create_theme WHERE user_id = ? ORDER BY id ASC LIMIT 1", 1);
        if (userId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, userId);
        }
        return CoroutinesRoom.execute(this.__db, false, DBUtil.createCancellationSignal(), new Callable<List<UserCreateThemeBean>>() { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.17
            @Override // java.util.concurrent.Callable
            public List<UserCreateThemeBean> call() throws Exception {
                Cursor cursorQuery = DBUtil.query(UserCreateThemeDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "user_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "album_column_id");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "compose_image_path");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "background_image_path");
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        arrayList.add(new UserCreateThemeBean(cursorQuery.getLong(columnIndexOrThrow), cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2), cursorQuery.getLong(columnIndexOrThrow3), cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4), cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getString(columnIndexOrThrow5)));
                    }
                    return arrayList;
                } finally {
                    cursorQuery.close();
                    roomSQLiteQueryAcquire.release();
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserCreateThemeDao
    public Object getUserCreateThemeCountByUserId(final String userId, final Continuation<? super Integer> continuation) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT COUNT(*) FROM t_user_create_theme WHERE user_id = ?", 1);
        if (userId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, userId);
        }
        return CoroutinesRoom.execute(this.__db, false, DBUtil.createCancellationSignal(), new Callable<Integer>() { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.18
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Integer call() throws Exception {
                Integer numValueOf = null;
                Cursor cursorQuery = DBUtil.query(UserCreateThemeDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    if (cursorQuery.moveToFirst() && !cursorQuery.isNull(0)) {
                        numValueOf = Integer.valueOf(cursorQuery.getInt(0));
                    }
                    return numValueOf;
                } finally {
                    cursorQuery.close();
                    roomSQLiteQueryAcquire.release();
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserCreateThemeDao
    public Object getCreateMaterialListByCreateThemeId(final long createThemeId, final Continuation<? super List<ThemeMaterialBean>> continuation) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM t_theme_material WHERE create_theme_id = ?", 1);
        roomSQLiteQueryAcquire.bindLong(1, createThemeId);
        return CoroutinesRoom.execute(this.__db, false, DBUtil.createCancellationSignal(), new Callable<List<ThemeMaterialBean>>() { // from class: com.shangxian.pinkink.db.dao.UserCreateThemeDao_Impl.19
            @Override // java.util.concurrent.Callable
            public List<ThemeMaterialBean> call() throws Exception {
                Cursor cursorQuery = DBUtil.query(UserCreateThemeDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "type");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "content");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "angle");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "x");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "y");
                    int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "width");
                    int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "height");
                    int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "create_theme_id");
                    int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "text_size");
                    int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "text_color");
                    int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "font_family_file_name");
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        arrayList.add(new ThemeMaterialBean(cursorQuery.getLong(columnIndexOrThrow), cursorQuery.getInt(columnIndexOrThrow2), cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3), cursorQuery.getFloat(columnIndexOrThrow4), cursorQuery.getInt(columnIndexOrThrow5), cursorQuery.getInt(columnIndexOrThrow6), cursorQuery.getFloat(columnIndexOrThrow7), cursorQuery.getFloat(columnIndexOrThrow8), cursorQuery.getLong(columnIndexOrThrow9), cursorQuery.getFloat(columnIndexOrThrow10), cursorQuery.isNull(columnIndexOrThrow11) ? null : cursorQuery.getString(columnIndexOrThrow11), cursorQuery.isNull(columnIndexOrThrow12) ? null : cursorQuery.getString(columnIndexOrThrow12)));
                    }
                    return arrayList;
                } finally {
                    cursorQuery.close();
                    roomSQLiteQueryAcquire.release();
                }
            }
        }, continuation);
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}
