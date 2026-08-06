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
import com.shangxian.pinkink.bean.FontBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* JADX INFO: loaded from: classes.dex */
public final class UserFontDao_Impl implements UserFontDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<FontBean> __insertionAdapterOfFontBean;
    private final SharedSQLiteStatement __preparedStmtOfDeleteUserFontByUserId;

    public UserFontDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfFontBean = new EntityInsertionAdapter<FontBean>(__db) { // from class: com.shangxian.pinkink.db.dao.UserFontDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR ABORT INTO `t_user_font` (`id`,`font_id`,`font_name`,`font_cover`,`font_file_path`,`user_id`) VALUES (nullif(?, 0),?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, FontBean value) {
                stmt.bindLong(1, value.getColumnId());
                if (value.getId() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getId());
                }
                if (value.getFontName() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getFontName());
                }
                if (value.getFontCover() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getFontCover());
                }
                if (value.getFontFilePath() == null) {
                    stmt.bindNull(5);
                } else {
                    stmt.bindString(5, value.getFontFilePath());
                }
                if (value.getUserId() == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.getUserId());
                }
            }
        };
        this.__preparedStmtOfDeleteUserFontByUserId = new SharedSQLiteStatement(__db) { // from class: com.shangxian.pinkink.db.dao.UserFontDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM t_user_font WHERE user_id = ?";
            }
        };
    }

    @Override // com.shangxian.pinkink.db.dao.UserFontDao
    public Object addFont(final FontBean font, final Continuation<? super Unit> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.shangxian.pinkink.db.dao.UserFontDao_Impl.3
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                UserFontDao_Impl.this.__db.beginTransaction();
                try {
                    UserFontDao_Impl.this.__insertionAdapterOfFontBean.insert(font);
                    UserFontDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    UserFontDao_Impl.this.__db.endTransaction();
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserFontDao
    public Object deleteUserFontByUserId(final String userId, final Continuation<? super Unit> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.shangxian.pinkink.db.dao.UserFontDao_Impl.4
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement supportSQLiteStatementAcquire = UserFontDao_Impl.this.__preparedStmtOfDeleteUserFontByUserId.acquire();
                String str = userId;
                if (str == null) {
                    supportSQLiteStatementAcquire.bindNull(1);
                } else {
                    supportSQLiteStatementAcquire.bindString(1, str);
                }
                UserFontDao_Impl.this.__db.beginTransaction();
                try {
                    supportSQLiteStatementAcquire.executeUpdateDelete();
                    UserFontDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    UserFontDao_Impl.this.__db.endTransaction();
                    UserFontDao_Impl.this.__preparedStmtOfDeleteUserFontByUserId.release(supportSQLiteStatementAcquire);
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserFontDao
    public Object getUserFontList(final String userId, final Continuation<? super List<FontBean>> continuation) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM t_user_font WHERE user_id = ?", 1);
        if (userId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, userId);
        }
        return CoroutinesRoom.execute(this.__db, false, DBUtil.createCancellationSignal(), new Callable<List<FontBean>>() { // from class: com.shangxian.pinkink.db.dao.UserFontDao_Impl.5
            @Override // java.util.concurrent.Callable
            public List<FontBean> call() throws Exception {
                Cursor cursorQuery = DBUtil.query(UserFontDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "font_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "font_name");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "font_cover");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "font_file_path");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "user_id");
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        FontBean fontBean = new FontBean();
                        fontBean.setColumnId(cursorQuery.getLong(columnIndexOrThrow));
                        fontBean.setId(cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2));
                        fontBean.setFontName(cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3));
                        fontBean.setFontCover(cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4));
                        fontBean.setFontFilePath(cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getString(columnIndexOrThrow5));
                        fontBean.setUserId(cursorQuery.isNull(columnIndexOrThrow6) ? null : cursorQuery.getString(columnIndexOrThrow6));
                        arrayList.add(fontBean);
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
