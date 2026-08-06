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
import com.shangxian.pinkink.bean.AlbumBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* JADX INFO: loaded from: classes.dex */
public final class UserAlbumDao_Impl implements UserAlbumDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<AlbumBean> __insertionAdapterOfAlbumBean;
    private final SharedSQLiteStatement __preparedStmtOfDeleteAlbumByColumnId;
    private final EntityDeletionOrUpdateAdapter<AlbumBean> __updateAdapterOfAlbumBean;

    public UserAlbumDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfAlbumBean = new EntityInsertionAdapter<AlbumBean>(__db) { // from class: com.shangxian.pinkink.db.dao.UserAlbumDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR ABORT INTO `t_user_album` (`id`,`album_id`,`name`,`user_id`,`create_time`,`update_time`) VALUES (nullif(?, 0),?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement stmt, AlbumBean value) {
                stmt.bindLong(1, value.getColumnId());
                if (value.getId() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getId());
                }
                if (value.getName() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getName());
                }
                if (value.getUserId() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getUserId());
                }
                stmt.bindLong(5, value.getCreateTime());
                stmt.bindLong(6, value.getUpdateTime());
            }
        };
        this.__updateAdapterOfAlbumBean = new EntityDeletionOrUpdateAdapter<AlbumBean>(__db) { // from class: com.shangxian.pinkink.db.dao.UserAlbumDao_Impl.2
            @Override // androidx.room.EntityDeletionOrUpdateAdapter, androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE OR ABORT `t_user_album` SET `id` = ?,`album_id` = ?,`name` = ?,`user_id` = ?,`create_time` = ?,`update_time` = ? WHERE `id` = ?";
            }

            @Override // androidx.room.EntityDeletionOrUpdateAdapter
            public void bind(SupportSQLiteStatement stmt, AlbumBean value) {
                stmt.bindLong(1, value.getColumnId());
                if (value.getId() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getId());
                }
                if (value.getName() == null) {
                    stmt.bindNull(3);
                } else {
                    stmt.bindString(3, value.getName());
                }
                if (value.getUserId() == null) {
                    stmt.bindNull(4);
                } else {
                    stmt.bindString(4, value.getUserId());
                }
                stmt.bindLong(5, value.getCreateTime());
                stmt.bindLong(6, value.getUpdateTime());
                stmt.bindLong(7, value.getColumnId());
            }
        };
        this.__preparedStmtOfDeleteAlbumByColumnId = new SharedSQLiteStatement(__db) { // from class: com.shangxian.pinkink.db.dao.UserAlbumDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM t_user_album WHERE id = ?";
            }
        };
    }

    @Override // com.shangxian.pinkink.db.dao.UserAlbumDao
    public Object createUserAlbum(final AlbumBean albumBean, final Continuation<? super Long> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Long>() { // from class: com.shangxian.pinkink.db.dao.UserAlbumDao_Impl.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Long call() throws Exception {
                UserAlbumDao_Impl.this.__db.beginTransaction();
                try {
                    long jInsertAndReturnId = UserAlbumDao_Impl.this.__insertionAdapterOfAlbumBean.insertAndReturnId(albumBean);
                    UserAlbumDao_Impl.this.__db.setTransactionSuccessful();
                    return Long.valueOf(jInsertAndReturnId);
                } finally {
                    UserAlbumDao_Impl.this.__db.endTransaction();
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserAlbumDao
    public Object updateAlbum(final AlbumBean album, final Continuation<? super Unit> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.shangxian.pinkink.db.dao.UserAlbumDao_Impl.5
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                UserAlbumDao_Impl.this.__db.beginTransaction();
                try {
                    UserAlbumDao_Impl.this.__updateAdapterOfAlbumBean.handle(album);
                    UserAlbumDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    UserAlbumDao_Impl.this.__db.endTransaction();
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserAlbumDao
    public Object deleteAlbumByColumnId(final long columnId, final Continuation<? super Unit> continuation) {
        return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() { // from class: com.shangxian.pinkink.db.dao.UserAlbumDao_Impl.6
            @Override // java.util.concurrent.Callable
            public Unit call() throws Exception {
                SupportSQLiteStatement supportSQLiteStatementAcquire = UserAlbumDao_Impl.this.__preparedStmtOfDeleteAlbumByColumnId.acquire();
                supportSQLiteStatementAcquire.bindLong(1, columnId);
                UserAlbumDao_Impl.this.__db.beginTransaction();
                try {
                    supportSQLiteStatementAcquire.executeUpdateDelete();
                    UserAlbumDao_Impl.this.__db.setTransactionSuccessful();
                    return Unit.INSTANCE;
                } finally {
                    UserAlbumDao_Impl.this.__db.endTransaction();
                    UserAlbumDao_Impl.this.__preparedStmtOfDeleteAlbumByColumnId.release(supportSQLiteStatementAcquire);
                }
            }
        }, continuation);
    }

    @Override // com.shangxian.pinkink.db.dao.UserAlbumDao
    public Object getAllUserAlbumList(final String userId, final Continuation<? super List<AlbumBean>> continuation) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM t_user_album WHERE user_id = ? ORDER BY create_time DESC", 1);
        if (userId == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, userId);
        }
        return CoroutinesRoom.execute(this.__db, false, DBUtil.createCancellationSignal(), new Callable<List<AlbumBean>>() { // from class: com.shangxian.pinkink.db.dao.UserAlbumDao_Impl.7
            @Override // java.util.concurrent.Callable
            public List<AlbumBean> call() throws Exception {
                Cursor cursorQuery = DBUtil.query(UserAlbumDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "album_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "name");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "user_id");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "create_time");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "update_time");
                    ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                    while (cursorQuery.moveToNext()) {
                        AlbumBean albumBean = new AlbumBean();
                        albumBean.setColumnId(cursorQuery.getLong(columnIndexOrThrow));
                        albumBean.setId(cursorQuery.isNull(columnIndexOrThrow2) ? null : cursorQuery.getString(columnIndexOrThrow2));
                        albumBean.setName(cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3));
                        albumBean.setUserId(cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4));
                        albumBean.setCreateTime(cursorQuery.getLong(columnIndexOrThrow5));
                        albumBean.setUpdateTime(cursorQuery.getLong(columnIndexOrThrow6));
                        arrayList.add(albumBean);
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
