package com.shangxian.pinkink.db;

import androidx.room.RoomDatabase;
import com.shangxian.pinkink.db.dao.UserAlbumDao;
import com.shangxian.pinkink.db.dao.UserCreateThemeDao;
import com.shangxian.pinkink.db.dao.UserFontDao;
import com.shangxian.pinkink.db.dao.UserInfoDao;
import com.shangxian.pinkink.db.dao.UserLikeThemesDao;
import kotlin.Metadata;

/* JADX INFO: compiled from: PinkInkRoomDatabase.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b'\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&¨\u0006\r"}, d2 = {"Lcom/shangxian/pinkink/db/PinkInkRoomDatabase;", "Landroidx/room/RoomDatabase;", "()V", "userAlbumDao", "Lcom/shangxian/pinkink/db/dao/UserAlbumDao;", "userCrateThemeDao", "Lcom/shangxian/pinkink/db/dao/UserCreateThemeDao;", "userFontDao", "Lcom/shangxian/pinkink/db/dao/UserFontDao;", "userInfoDao", "Lcom/shangxian/pinkink/db/dao/UserInfoDao;", "userLikeThemesDao", "Lcom/shangxian/pinkink/db/dao/UserLikeThemesDao;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public abstract class PinkInkRoomDatabase extends RoomDatabase {
    public abstract UserAlbumDao userAlbumDao();

    public abstract UserCreateThemeDao userCrateThemeDao();

    public abstract UserFontDao userFontDao();

    public abstract UserInfoDao userInfoDao();

    public abstract UserLikeThemesDao userLikeThemesDao();
}
