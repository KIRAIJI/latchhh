package com.shangxian.pinkink.db.dao;

import com.shangxian.pinkink.bean.ThemeBean;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* JADX INFO: compiled from: UserLikeThemesDao.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0006J'\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u000bJ\u0019\u0010\f\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0006J\u001f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u0004\u001a\u00020\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0006J\u0019\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\tH§@ø\u0001\u0000¢\u0006\u0002\u0010\u0011J!\u0010\u0012\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0013"}, d2 = {"Lcom/shangxian/pinkink/db/dao/UserLikeThemesDao;", "", "deleteUserLikeThemeByUserId", "", "userId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTheme", "", "Lcom/shangxian/pinkink/bean/ThemeBean;", "themeId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserLikeCount", "", "getUserThemeList", "like", "theme", "(Lcom/shangxian/pinkink/bean/ThemeBean;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "unlike", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public interface UserLikeThemesDao {
    Object deleteUserLikeThemeByUserId(String str, Continuation<? super Unit> continuation);

    Object getTheme(String str, String str2, Continuation<? super List<ThemeBean>> continuation);

    Object getUserLikeCount(String str, Continuation<? super Integer> continuation);

    Object getUserThemeList(String str, Continuation<? super List<ThemeBean>> continuation);

    Object like(ThemeBean themeBean, Continuation<? super Unit> continuation);

    Object unlike(String str, String str2, Continuation<? super Unit> continuation);
}
