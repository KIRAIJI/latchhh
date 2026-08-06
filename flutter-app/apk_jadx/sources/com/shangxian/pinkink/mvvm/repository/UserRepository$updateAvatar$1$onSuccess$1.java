package com.shangxian.pinkink.mvvm.repository;

import com.shangxian.pinkink.bean.ApiResult;
import com.shangxian.pinkink.bean.UpdateAvatarResultBean;
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
@DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository$updateAvatar$1$onSuccess$1", f = "UserRepository.kt", i = {}, l = {113}, m = "invokeSuspend", n = {}, s = {})
final class UserRepository$updateAvatar$1$onSuccess$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ ApiResult<UpdateAvatarResultBean> $result;
    int label;
    final /* synthetic */ UserRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UserRepository$updateAvatar$1$onSuccess$1(ApiResult<UpdateAvatarResultBean> apiResult, UserRepository userRepository, Continuation<? super UserRepository$updateAvatar$1$onSuccess$1> continuation) {
        super(2, continuation);
        this.$result = apiResult;
        this.this$0 = userRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new UserRepository$updateAvatar$1$onSuccess$1(this.$result, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((UserRepository$updateAvatar$1$onSuccess$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        UpdateAvatarResultBean data;
        String pic;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ApiResult<UpdateAvatarResultBean> apiResult = this.$result;
            String str = "";
            if (apiResult != null && (data = apiResult.getData()) != null && (pic = data.getPic()) != null) {
                str = pic;
            }
            UserInfoBean userInfoBean = this.this$0.userInfo;
            Intrinsics.checkNotNull(userInfoBean);
            userInfoBean.setAvatar(str);
            UserInfoDao userInfoDao = PinkInkDB.INSTANCE.db().userInfoDao();
            UserInfoBean userInfoBean2 = this.this$0.userInfo;
            Intrinsics.checkNotNull(userInfoBean2);
            this.label = 1;
            if (userInfoDao.updateUserInfo(userInfoBean2, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
