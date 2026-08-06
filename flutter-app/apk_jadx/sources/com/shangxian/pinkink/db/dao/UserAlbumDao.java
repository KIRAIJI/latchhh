package com.shangxian.pinkink.db.dao;

import com.shangxian.pinkink.bean.AlbumBean;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* JADX INFO: compiled from: UserAlbumDao.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H§@ø\u0001\u0000¢\u0006\u0002\u0010\nJ\u001f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\u0006\u0010\r\u001a\u00020\u000eH§@ø\u0001\u0000¢\u0006\u0002\u0010\u000fJ\u0019\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, d2 = {"Lcom/shangxian/pinkink/db/dao/UserAlbumDao;", "", "createUserAlbum", "", "albumBean", "Lcom/shangxian/pinkink/bean/AlbumBean;", "(Lcom/shangxian/pinkink/bean/AlbumBean;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAlbumByColumnId", "", "columnId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllUserAlbumList", "", "userId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateAlbum", "album", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public interface UserAlbumDao {
    Object createUserAlbum(AlbumBean albumBean, Continuation<? super Long> continuation);

    Object deleteAlbumByColumnId(long j, Continuation<? super Unit> continuation);

    Object getAllUserAlbumList(String str, Continuation<? super List<AlbumBean>> continuation);

    Object updateAlbum(AlbumBean albumBean, Continuation<? super Unit> continuation);
}
