package com.shangxian.pinkink.mvvm.repository;

import com.shangxian.pinkink.bean.UserCreateThemeBean;
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
@DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository$batchDeleteUserCreateTheme$1$1$1", f = "UserRepository.kt", i = {0, 1}, l = {314, 315}, m = "invokeSuspend", n = {"it", "it"}, s = {"L$2", "L$2"})
final class UserRepository$batchDeleteUserCreateTheme$1$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ UserCreateThemeDao $userCreateThemeDao;
    final /* synthetic */ List<UserCreateThemeBean> $userCreateThemeList;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UserRepository$batchDeleteUserCreateTheme$1$1$1(List<UserCreateThemeBean> list, UserCreateThemeDao userCreateThemeDao, Continuation<? super UserRepository$batchDeleteUserCreateTheme$1$1$1> continuation) {
        super(2, continuation);
        this.$userCreateThemeList = list;
        this.$userCreateThemeDao = userCreateThemeDao;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new UserRepository$batchDeleteUserCreateTheme$1$1$1(this.$userCreateThemeList, this.$userCreateThemeDao, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((UserRepository$batchDeleteUserCreateTheme$1$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a1  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x007d -> B:23:0x0080). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) throws java.lang.Throwable {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L3a
            if (r1 == r3) goto L28
            if (r1 != r2) goto L20
            java.lang.Object r1 = r9.L$2
            com.shangxian.pinkink.bean.UserCreateThemeBean r1 = (com.shangxian.pinkink.bean.UserCreateThemeBean) r1
            java.lang.Object r4 = r9.L$1
            java.util.Iterator r4 = (java.util.Iterator) r4
            java.lang.Object r5 = r9.L$0
            com.shangxian.pinkink.db.dao.UserCreateThemeDao r5 = (com.shangxian.pinkink.db.dao.UserCreateThemeDao) r5
            kotlin.ResultKt.throwOnFailure(r10)
            r10 = r4
            r4 = r9
            goto L80
        L20:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L28:
            java.lang.Object r1 = r9.L$2
            com.shangxian.pinkink.bean.UserCreateThemeBean r1 = (com.shangxian.pinkink.bean.UserCreateThemeBean) r1
            java.lang.Object r4 = r9.L$1
            java.util.Iterator r4 = (java.util.Iterator) r4
            java.lang.Object r5 = r9.L$0
            com.shangxian.pinkink.db.dao.UserCreateThemeDao r5 = (com.shangxian.pinkink.db.dao.UserCreateThemeDao) r5
            kotlin.ResultKt.throwOnFailure(r10)
            r10 = r4
            r4 = r9
            goto L6d
        L3a:
            kotlin.ResultKt.throwOnFailure(r10)
            java.util.List<com.shangxian.pinkink.bean.UserCreateThemeBean> r10 = r9.$userCreateThemeList
            java.lang.Iterable r10 = (java.lang.Iterable) r10
            com.shangxian.pinkink.db.dao.UserCreateThemeDao r1 = r9.$userCreateThemeDao
            java.util.Iterator r10 = r10.iterator()
            r4 = r9
        L48:
            boolean r5 = r10.hasNext()
            if (r5 == 0) goto La6
            java.lang.Object r5 = r10.next()
            com.shangxian.pinkink.bean.UserCreateThemeBean r5 = (com.shangxian.pinkink.bean.UserCreateThemeBean) r5
            if (r5 != 0) goto L57
            goto L48
        L57:
            long r6 = r5.getColumnId()
            r4.L$0 = r1
            r4.L$1 = r10
            r4.L$2 = r5
            r4.label = r3
            java.lang.Object r6 = r1.deleteUserCreateThemeById(r6, r4)
            if (r6 != r0) goto L6a
            return r0
        L6a:
            r8 = r5
            r5 = r1
            r1 = r8
        L6d:
            long r6 = r1.getColumnId()
            r4.L$0 = r5
            r4.L$1 = r10
            r4.L$2 = r1
            r4.label = r2
            java.lang.Object r6 = r5.deleteCreateMaterialByCreateThemeId(r6, r4)
            if (r6 != r0) goto L80
            return r0
        L80:
            java.io.File r6 = new java.io.File
            java.lang.String r7 = r1.getComposeImagePath()
            r6.<init>(r7)
            boolean r7 = r6.exists()
            if (r7 == 0) goto L92
            r6.delete()
        L92:
            java.io.File r6 = new java.io.File
            java.lang.String r1 = r1.getBackgroundImagePath()
            r6.<init>(r1)
            boolean r1 = r6.exists()
            if (r1 == 0) goto La4
            r6.delete()
        La4:
            r1 = r5
            goto L48
        La6:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shangxian.pinkink.mvvm.repository.UserRepository$batchDeleteUserCreateTheme$1$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
