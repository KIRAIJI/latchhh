package com.shangxian.pinkink.mvvm.repository;

import com.shangxian.pinkink.bean.UserInfoBean;
import com.shangxian.pinkink.db.PinkInkDB;
import com.shangxian.pinkink.db.dao.UserInfoDao;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: UserRepository.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
@DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository$requestUserInfo$1$onSuccess$1$1", f = "UserRepository.kt", i = {0}, l = {171, 172}, m = "invokeSuspend", n = {"$this$invokeSuspend_u24lambda_u2d0"}, s = {"L$2"})
final class UserRepository$requestUserInfo$1$onSuccess$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ UserInfoBean $userInfo;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UserRepository$requestUserInfo$1$onSuccess$1$1(UserInfoBean userInfoBean, Continuation<? super UserRepository$requestUserInfo$1$onSuccess$1$1> continuation) {
        super(2, continuation);
        this.$userInfo = userInfoBean;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new UserRepository$requestUserInfo$1$onSuccess$1$1(this.$userInfo, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((UserRepository$requestUserInfo$1$onSuccess$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        UserInfoDao userInfoDao;
        UserInfoBean userInfoBean;
        UserInfoDao userInfoDao2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            userInfoDao = PinkInkDB.INSTANCE.db().userInfoDao();
            UserInfoBean userInfoBean2 = this.$userInfo;
            String id = userInfoBean2.getId();
            Intrinsics.checkNotNullExpressionValue(id, "userInfo.id");
            this.L$0 = userInfoDao;
            this.L$1 = userInfoBean2;
            this.L$2 = userInfoDao;
            this.label = 1;
            if (userInfoDao.deleteUserByUserId(id, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            userInfoBean = userInfoBean2;
            userInfoDao2 = userInfoDao;
        } else {
            if (i != 1) {
                if (i == 2) {
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            userInfoDao = (UserInfoDao) this.L$2;
            userInfoBean = (UserInfoBean) this.L$1;
            userInfoDao2 = (UserInfoDao) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        this.L$0 = userInfoDao2;
        this.L$1 = null;
        this.L$2 = null;
        this.label = 2;
        if (userInfoDao.addUserInfo(userInfoBean, this) == coroutine_suspended) {
            return coroutine_suspended;
        }
        return Unit.INSTANCE;
    }
}
