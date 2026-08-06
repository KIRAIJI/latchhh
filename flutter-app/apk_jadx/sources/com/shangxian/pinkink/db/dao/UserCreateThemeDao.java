package com.shangxian.pinkink.db.dao;

import com.shangxian.pinkink.bean.ThemeMaterialBean;
import com.shangxian.pinkink.bean.UserCreateThemeBean;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* JADX INFO: compiled from: UserCreateThemeDao.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0006J\u0019\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0006J\u0019\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH§@ø\u0001\u0000¢\u0006\u0002\u0010\u000eJ\u001f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0004\u001a\u00020\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0006J\u001f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00102\u0006\u0010\f\u001a\u00020\rH§@ø\u0001\u0000¢\u0006\u0002\u0010\u000eJ\u0019\u0010\u0014\u001a\u00020\u00152\u0006\u0010\f\u001a\u00020\rH§@ø\u0001\u0000¢\u0006\u0002\u0010\u000eJ\u001f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\u00102\u0006\u0010\f\u001a\u00020\rH§@ø\u0001\u0000¢\u0006\u0002\u0010\u000eJ'\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00130\u00102\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0018J%\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H§@ø\u0001\u0000¢\u0006\u0002\u0010\u001cJ\u0019\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u0013H§@ø\u0001\u0000¢\u0006\u0002\u0010\u001fJ\u0019\u0010 \u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u0013H§@ø\u0001\u0000¢\u0006\u0002\u0010\u001f\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006!"}, d2 = {"Lcom/shangxian/pinkink/db/dao/UserCreateThemeDao;", "", "deleteCreateMaterialByCreateThemeId", "", "createThemeId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteUserCreateThemeByAlbumColumnId", "albumColumnId", "deleteUserCreateThemeById", "themeId", "deleteUserCreateThemeByUserId", "userId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCreateMaterialListByCreateThemeId", "", "Lcom/shangxian/pinkink/bean/ThemeMaterialBean;", "getFirstUserCreateThemeListByUserId", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "getUserCreateThemeCountByUserId", "", "getUserCreateThemeListByUserId", "getUserCreateThemeListByUserIdAndAlbumColumnId", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveUserCreateMaterialList", "", "createMaterialList", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveUserCreateTheme", "userCreateThemeBean", "(Lcom/shangxian/pinkink/bean/UserCreateThemeBean;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateUserCreateTheme", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public interface UserCreateThemeDao {
    Object deleteCreateMaterialByCreateThemeId(long j, Continuation<? super Unit> continuation);

    Object deleteUserCreateThemeByAlbumColumnId(long j, Continuation<? super Unit> continuation);

    Object deleteUserCreateThemeById(long j, Continuation<? super Unit> continuation);

    Object deleteUserCreateThemeByUserId(String str, Continuation<? super Unit> continuation);

    Object getCreateMaterialListByCreateThemeId(long j, Continuation<? super List<ThemeMaterialBean>> continuation);

    Object getFirstUserCreateThemeListByUserId(String str, Continuation<? super List<UserCreateThemeBean>> continuation);

    Object getUserCreateThemeCountByUserId(String str, Continuation<? super Integer> continuation);

    Object getUserCreateThemeListByUserId(String str, Continuation<? super List<UserCreateThemeBean>> continuation);

    Object getUserCreateThemeListByUserIdAndAlbumColumnId(String str, long j, Continuation<? super List<UserCreateThemeBean>> continuation);

    Object saveUserCreateMaterialList(List<ThemeMaterialBean> list, Continuation<? super List<Long>> continuation);

    Object saveUserCreateTheme(UserCreateThemeBean userCreateThemeBean, Continuation<? super Long> continuation);

    Object updateUserCreateTheme(UserCreateThemeBean userCreateThemeBean, Continuation<? super Unit> continuation);
}
