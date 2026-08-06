package com.shangxian.pinkink.db.dao;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.shangxian.pinkink.bean.ThemeBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* JADX INFO: loaded from: classes.dex */
public final class UserLikeThemesDao_Impl implements UserLikeThemesDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<ThemeBean> __insertionAdapterOfThemeBean;
    private final SharedSQLiteStatement __preparedStmtOfDeleteUserLikeThemeByUserId;
    private final SharedSQLiteStatement __preparedStmtOfUnlike;

    public UserLikeThemesDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfThemeBean = new EntityInsertionAdapter<ThemeBean>(__db) { // from class: com.shangxian.pinkink.db.dao.UserLikeThemesDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR ABORT INTO `t_user_like_themes` (`id`,`theme_id`,`name`,`like_user_id`,`cover_url`,`isSelected`) VALUES (nullif(?, 0),?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, ThemeBean themeBean) {
                supportSQLiteStatement.bindLong(1, themeBean.getColumnId());
                if (themeBean.getId() == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, themeBean.getId());
                }
                if (themeBean.getName() == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, themeBean.getName());
                }
                if (themeBean.getLikeUserId() == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, themeBean.getLikeUserId());
                }
                if (themeBean.getCover() == null) {
                    supportSQLiteStatement.bindNull(5);
                } else {
                    supportSQLiteStatement.bindString(5, themeBean.getCover());
                }
                supportSQLiteStatement.bindLong(6, themeBean.getIsSelected() ? 1L : 0L);
            }
        };
        this.__preparedStmtOfUnlike = new SharedSQLiteStatement(__db) { // from class: com.shangxian.pinkink.db.dao.UserLikeThemesDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM t_user_like_themes WHERE theme_id = ? AND like_user_id = ?";
            }
        };
        this.__preparedStmtOfDeleteUserLikeThemeByUserId = new SharedSQLiteStatement(__db) { // from class: com.shangxian.pinkink.db.dao.UserLikeThemesDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM t_user_like_themes WHERE like_user_id = ?";
            }
        };
    }

    @Override // com.shangxian.pinkink.db.dao.UserLikeThemesDao
    public Object like(final ThemeBean theme, final Continuation<? super Unit> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.shangxian.pinkink.db.dao.UserLikeThemesDao_Impl.4
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                UserLikeThemesDao_Impl.this.__db.beginTransaction();
                try {
                    UserLikeThemesDao_Impl.this.__insertionAdapterOfThemeBean.insert(theme);
                    UserLikeThemesDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    UserLikeThemesDao_Impl.this.__db.endTransaction();
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserLikeThemesDao
    public Object unlike(final String themeId, final String userId, final Continuation<? super Unit> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.shangxian.pinkink.db.dao.UserLikeThemesDao_Impl.5
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement supportSQLiteStatementAcquire = UserLikeThemesDao_Impl.this.__preparedStmtOfUnlike.acquire();
                String str = themeId;
                if (str == null) {
                    supportSQLiteStatementAcquire.bindNull(1);
                } else {
                    supportSQLiteStatementAcquire.bindString(1, str);
                }
                String str2 = userId;
                if (str2 == null) {
                    supportSQLiteStatementAcquire.bindNull(2);
                } else {
                    supportSQLiteStatementAcquire.bindString(2, str2);
                }
                UserLikeThemesDao_Impl.this.__db.beginTransaction();
                try {
                    supportSQLiteStatementAcquire.executeUpdateDelete();
                    UserLikeThemesDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    UserLikeThemesDao_Impl.this.__db.endTransaction();
                    UserLikeThemesDao_Impl.this.__preparedStmtOfUnlike.release(supportSQLiteStatementAcquire);
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserLikeThemesDao
    public Object deleteUserLikeThemeByUserId(final String userId, final Continuation<? super Unit> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.shangxian.pinkink.db.dao.UserLikeThemesDao_Impl.6
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement supportSQLiteStatementAcquire = UserLikeThemesDao_Impl.this.__preparedStmtOfDeleteUserLikeThemeByUserId.acquire();
                String str = userId;
                if (str == null) {
                    supportSQLiteStatementAcquire.bindNull(1);
                } else {
                    supportSQLiteStatementAcquire.bindString(1, str);
                }
                UserLikeThemesDao_Impl.this.__db.beginTransaction();
                try {
                    supportSQLiteStatementAcquire.executeUpdateDelete();
                    UserLikeThemesDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    UserLikeThemesDao_Impl.this.__db.endTransaction();
                    UserLikeThemesDao_Impl.this.__preparedStmtOfDeleteUserLikeThemeByUserId.release(supportSQLiteStatementAcquire);
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserLikeThemesDao
    public Object getUserLikeCount(final String userId, final Continuation<? super Integer> continuation) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT COUNT(*) FROM t_user_like_themes WHERE like_user_id = ?", 1);
        if (userId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, userId);
        }
        return CoroutinesRoom.execute(this.__db, false, DBUtil.createCancellationSignal(), new Callable<Integer>() { // from class: com.shangxian.pinkink.db.dao.UserLikeThemesDao_Impl.7
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Integer call() throws Exception {
                Integer numValueOf = null;
                Cursor cursorQuery = DBUtil.query(UserLikeThemesDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
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

    @Override // com.shangxian.pinkink.db.dao.UserLikeThemesDao
    public Object getTheme(final String themeId, final String userId, final Continuation<? super List<ThemeBean>> continuation) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM t_user_like_themes WHERE theme_id = ? AND like_user_id = ?", 2);
        if (themeId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, themeId);
        }
        if (userId == null) {
            roomSQLiteQueryAcquire.bindNull(2);
        } else {
            roomSQLiteQueryAcquire.bindString(2, userId);
        }
        return CoroutinesRoom.execute(this.__db, false, DBUtil.createCancellationSignal(), new Callable<List<ThemeBean>>() { // from class: com.shangxian.pinkink.db.dao.UserLikeThemesDao_Impl.8
            @Override // java.util.concurrent.Callable
            public List<ThemeBean> call() throws Exception {
                Cursor cursorQuery = DBUtil.query(UserLikeThemesDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "theme_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "name");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "like_user_id");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "cover_url");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isSelected");
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        ThemeBean themeBean = new ThemeBean(cursorQuery.getLong(columnIndexOrThrow), cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2), cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3), cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4), cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getString(columnIndexOrThrow5));
                        themeBean.setSelected(cursorQuery.getInt(columnIndexOrThrow6) != 0);
                        arrayList.add(themeBean);
                    }
                    return arrayList;
                } finally {
                    cursorQuery.close();
                    roomSQLiteQueryAcquire.release();
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserLikeThemesDao
    public Object getUserThemeList(final String userId, final Continuation<? super List<ThemeBean>> continuation) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM t_user_like_themes WHERE like_user_id = ?", 1);
        if (userId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, userId);
        }
        return CoroutinesRoom.execute(this.__db, false, DBUtil.createCancellationSignal(), new Callable<List<ThemeBean>>() { // from class: com.shangxian.pinkink.db.dao.UserLikeThemesDao_Impl.9
            @Override // java.util.concurrent.Callable
            public List<ThemeBean> call() throws Exception {
                Cursor cursorQuery = DBUtil.query(UserLikeThemesDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "theme_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "name");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "like_user_id");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "cover_url");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "isSelected");
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        ThemeBean themeBean = new ThemeBean(cursorQuery.getLong(columnIndexOrThrow), cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2), cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3), cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4), cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getString(columnIndexOrThrow5));
                        themeBean.setSelected(cursorQuery.getInt(columnIndexOrThrow6) != 0);
                        arrayList.add(themeBean);
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
