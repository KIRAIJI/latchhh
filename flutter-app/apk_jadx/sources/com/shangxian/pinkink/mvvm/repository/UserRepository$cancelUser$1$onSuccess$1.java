package com.shangxian.pinkink.mvvm.repository;

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
@DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository$cancelUser$1$onSuccess$1", f = "UserRepository.kt", i = {0, 0, 1, 1, 2, 2}, l = {387, 388, 389, 390}, m = "invokeSuspend", n = {"$this$invokeSuspend_u24lambda_u2d0", "userId", "$this$invokeSuspend_u24lambda_u2d0", "userId", "$this$invokeSuspend_u24lambda_u2d0", "userId"}, s = {"L$0", "L$1", "L$0", "L$1", "L$0", "L$1"})
final class UserRepository$cancelUser$1$onSuccess$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ UserRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UserRepository$cancelUser$1$onSuccess$1(UserRepository userRepository, Continuation<? super UserRepository$cancelUser$1$onSuccess$1> continuation) {
        super(2, continuation);
        this.this$0 = userRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new UserRepository$cancelUser$1$onSuccess$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((UserRepository$cancelUser$1$onSuccess$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0083 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a7 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) throws java.lang.Throwable {
        /*
            r8 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r8.label
            r2 = 4
            r3 = 3
            r4 = 2
            r5 = 1
            r6 = 0
            if (r1 == 0) goto L47
            if (r1 == r5) goto L3b
            if (r1 == r4) goto L2f
            if (r1 == r3) goto L22
            if (r1 != r2) goto L1a
            kotlin.ResultKt.throwOnFailure(r9)
            goto La8
        L1a:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L22:
            java.lang.Object r1 = r8.L$1
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object r3 = r8.L$0
            com.shangxian.pinkink.db.PinkInkRoomDatabase r3 = (com.shangxian.pinkink.db.PinkInkRoomDatabase) r3
            kotlin.ResultKt.throwOnFailure(r9)
            goto L97
        L2f:
            java.lang.Object r1 = r8.L$1
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object r4 = r8.L$0
            com.shangxian.pinkink.db.PinkInkRoomDatabase r4 = (com.shangxian.pinkink.db.PinkInkRoomDatabase) r4
            kotlin.ResultKt.throwOnFailure(r9)
            goto L85
        L3b:
            java.lang.Object r1 = r8.L$1
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object r5 = r8.L$0
            com.shangxian.pinkink.db.PinkInkRoomDatabase r5 = (com.shangxian.pinkink.db.PinkInkRoomDatabase) r5
            kotlin.ResultKt.throwOnFailure(r9)
            goto L73
        L47:
            kotlin.ResultKt.throwOnFailure(r9)
            com.shangxian.pinkink.db.PinkInkDB r9 = com.shangxian.pinkink.db.PinkInkDB.INSTANCE
            com.shangxian.pinkink.db.PinkInkRoomDatabase r9 = r9.db()
            com.shangxian.pinkink.mvvm.repository.UserRepository r1 = r8.this$0
            com.shangxian.pinkink.bean.UserInfoBean r1 = com.shangxian.pinkink.mvvm.repository.UserRepository.access$getUserInfo$p(r1)
            if (r1 != 0) goto L5a
            r1 = r6
            goto L5e
        L5a:
            java.lang.String r1 = r1.getId()
        L5e:
            if (r1 != 0) goto L61
            goto La8
        L61:
            com.shangxian.pinkink.db.dao.UserInfoDao r7 = r9.userInfoDao()
            r8.L$0 = r9
            r8.L$1 = r1
            r8.label = r5
            java.lang.Object r5 = r7.deleteUserByUserId(r1, r8)
            if (r5 != r0) goto L72
            return r0
        L72:
            r5 = r9
        L73:
            com.shangxian.pinkink.db.dao.UserCreateThemeDao r9 = r5.userCrateThemeDao()
            r8.L$0 = r5
            r8.L$1 = r1
            r8.label = r4
            java.lang.Object r9 = r9.deleteUserCreateThemeByUserId(r1, r8)
            if (r9 != r0) goto L84
            return r0
        L84:
            r4 = r5
        L85:
            com.shangxian.pinkink.db.dao.UserLikeThemesDao r9 = r4.userLikeThemesDao()
            r8.L$0 = r4
            r8.L$1 = r1
            r8.label = r3
            java.lang.Object r9 = r9.deleteUserLikeThemeByUserId(r1, r8)
            if (r9 != r0) goto L96
            return r0
        L96:
            r3 = r4
        L97:
            com.shangxian.pinkink.db.dao.UserFontDao r9 = r3.userFontDao()
            r8.L$0 = r6
            r8.L$1 = r6
            r8.label = r2
            java.lang.Object r9 = r9.deleteUserFontByUserId(r1, r8)
            if (r9 != r0) goto La8
            return r0
        La8:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shangxian.pinkink.mvvm.repository.UserRepository$cancelUser$1$onSuccess$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
