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
import com.shangxian.pinkink.bean.UserInfoBean;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* JADX INFO: loaded from: classes.dex */
public final class UserInfoDao_Impl implements UserInfoDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<UserInfoBean> __insertionAdapterOfUserInfoBean;
    private final SharedSQLiteStatement __preparedStmtOfDeleteUserByUserId;
    private final EntityDeletionOrUpdateAdapter<UserInfoBean> __updateAdapterOfUserInfoBean;

    public UserInfoDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfUserInfoBean = new EntityInsertionAdapter<UserInfoBean>(__db) { // from class: com.shangxian.pinkink.db.dao.UserInfoDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR ABORT INTO `t_user_info` (`id`,`user_id`,`nick_name`,`mobile`,`state`,`avatar`,`token`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, UserInfoBean value) {
                stmt.bindLong(1, value.getColumnId());
                if (value.getId() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getId());
                }
                if (value.getNikeName() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getNikeName());
                }
                if (value.getMobile() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getMobile());
                }
                stmt.bindLong(5, value.getState());
                if (value.getAvatar() == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.getAvatar());
                }
                if (value.getToken() == null) {
                    stmt.bindNull(7);
                } else {
                    stmt.bindString(7, value.getToken());
                }
            }
        };
        this.__updateAdapterOfUserInfoBean = new EntityDeletionOrUpdateAdapter<UserInfoBean>(__db) { // from class: com.shangxian.pinkink.db.dao.UserInfoDao_Impl.2
            @Override // androidx.room.EntityDeletionOrUpdateAdapter, androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE OR ABORT `t_user_info` SET `id` = ?,`user_id` = ?,`nick_name` = ?,`mobile` = ?,`state` = ?,`avatar` = ?,`token` = ? WHERE `id` = ?";
            }

            @Override // androidx.room.EntityDeletionOrUpdateAdapter
            public void bind(SupportSQLiteStatement stmt, UserInfoBean value) {
                stmt.bindLong(1, value.getColumnId());
                if (value.getId() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getId());
                }
                if (value.getNikeName() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getNikeName());
                }
                if (value.getMobile() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getMobile());
                }
                stmt.bindLong(5, value.getState());
                if (value.getAvatar() == null) {
                    stmt.bindNull(6);
                } else {
                    stmt.bindString(6, value.getAvatar());
                }
                if (value.getToken() == null) {
                    stmt.bindNull(7);
                } else {
                    stmt.bindString(7, value.getToken());
                }
                stmt.bindLong(8, value.getColumnId());
            }
        };
        this.__preparedStmtOfDeleteUserByUserId = new SharedSQLiteStatement(__db) { // from class: com.shangxian.pinkink.db.dao.UserInfoDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM t_user_info WHERE user_id = ?";
            }
        };
    }

    @Override // com.shangxian.pinkink.db.dao.UserInfoDao
    public Object addUserInfo(final UserInfoBean userInfo, final Continuation<? super Unit> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.shangxian.pinkink.db.dao.UserInfoDao_Impl.4
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                UserInfoDao_Impl.this.__db.beginTransaction();
                try {
                    UserInfoDao_Impl.this.__insertionAdapterOfUserInfoBean.insert(userInfo);
                    UserInfoDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    UserInfoDao_Impl.this.__db.endTransaction();
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserInfoDao
    public Object updateUserInfo(final UserInfoBean userInfo, final Continuation<? super Unit> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.shangxian.pinkink.db.dao.UserInfoDao_Impl.5
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                UserInfoDao_Impl.this.__db.beginTransaction();
                try {
                    UserInfoDao_Impl.this.__updateAdapterOfUserInfoBean.handle(userInfo);
                    UserInfoDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    UserInfoDao_Impl.this.__db.endTransaction();
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserInfoDao
    public Object deleteUserByUserId(final String userId, final Continuation<? super Unit> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.shangxian.pinkink.db.dao.UserInfoDao_Impl.6
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement supportSQLiteStatementAcquire = UserInfoDao_Impl.this.__preparedStmtOfDeleteUserByUserId.acquire();
                String str = userId;
                if (str == null) {
                    supportSQLiteStatementAcquire.bindNull(1);
                } else {
                    supportSQLiteStatementAcquire.bindString(1, str);
                }
                UserInfoDao_Impl.this.__db.beginTransaction();
                try {
                    supportSQLiteStatementAcquire.executeUpdateDelete();
                    UserInfoDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    UserInfoDao_Impl.this.__db.endTransaction();
                    UserInfoDao_Impl.this.__preparedStmtOfDeleteUserByUserId.release(supportSQLiteStatementAcquire);
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserInfoDao
    public Object getUserByToken(final String token, final Continuation<? super UserInfoBean> continuation) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM t_user_info WHERE token = ?", 1);
        if (token == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, token);
        }
        return CoroutinesRoom.execute(this.__db, false, DBUtil.createCancellationSignal(), new Callable<UserInfoBean>() { // from class: com.shangxian.pinkink.db.dao.UserInfoDao_Impl.7
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public UserInfoBean call() throws Exception {
                UserInfoBean userInfoBean = null;
                String string = null;
                Cursor cursorQuery = DBUtil.query(UserInfoDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "user_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "nick_name");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "mobile");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "state");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "avatar");
                    int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "token");
                    if (cursorQuery.moveToFirst()) {
                        UserInfoBean userInfoBean2 = new UserInfoBean();
                        userInfoBean2.setColumnId(cursorQuery.getLong(columnIndexOrThrow));
                        userInfoBean2.setId(cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2));
                        userInfoBean2.setNikeName(cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3));
                        userInfoBean2.setMobile(cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4));
                        userInfoBean2.setState(cursorQuery.getInt(columnIndexOrThrow5));
                        userInfoBean2.setAvatar(cursorQuery.isNull(columnIndexOrThrow6) ? null : cursorQuery.getString(columnIndexOrThrow6));
                        if (!cursorQuery.isNull(columnIndexOrThrow7)) {
                            string = cursorQuery.getString(columnIndexOrThrow7);
                        }
                        userInfoBean2.setToken(string);
                        userInfoBean = userInfoBean2;
                    }
                    return userInfoBean;
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
