package com.shangxian.pinkink.mvvm.repository;

import com.shangxian.pinkink.bean.ThemeBean;
import com.shangxian.pinkink.bean.UserCreateThemeBean;
import com.shangxian.pinkink.bean.UserInfoBean;
import com.shangxian.pinkink.db.dao.UserCreateThemeDao;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: UserRepository.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
@DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository$batchSaveUserCreateTheme$2$1", f = "UserRepository.kt", i = {0, 1}, l = {272, 278}, m = "invokeSuspend", n = {"saveFile", "userCreateThemeBean"}, s = {"L$4", "L$4"})
final class UserRepository$batchSaveUserCreateTheme$2$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ long $albumId;
    final /* synthetic */ List<ThemeBean> $themeList;
    final /* synthetic */ UserCreateThemeDao $userCreateThemeDao;
    final /* synthetic */ List<UserCreateThemeBean> $userCreateThemeList;
    final /* synthetic */ UserInfoBean $userInfo;
    long J$0;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UserRepository$batchSaveUserCreateTheme$2$1(List<ThemeBean> list, UserInfoBean userInfoBean, long j, UserCreateThemeDao userCreateThemeDao, List<UserCreateThemeBean> list2, Continuation<? super UserRepository$batchSaveUserCreateTheme$2$1> continuation) {
        super(2, continuation);
        this.$themeList = list;
        this.$userInfo = userInfoBean;
        this.$albumId = j;
        this.$userCreateThemeDao = userCreateThemeDao;
        this.$userCreateThemeList = list2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new UserRepository$batchSaveUserCreateTheme$2$1(this.$themeList, this.$userInfo, this.$albumId, this.$userCreateThemeDao, this.$userCreateThemeList, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((UserRepository$batchSaveUserCreateTheme$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x012a  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x0105 -> B:22:0x0108). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0115 -> B:24:0x0117). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r26) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 301
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shangxian.pinkink.mvvm.repository.UserRepository$batchSaveUserCreateTheme$2$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
