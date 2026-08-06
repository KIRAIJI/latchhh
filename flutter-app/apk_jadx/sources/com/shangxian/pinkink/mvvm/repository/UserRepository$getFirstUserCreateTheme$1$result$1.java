package com.shangxian.pinkink.mvvm.repository;

import com.shangxian.pinkink.bean.UserCreateThemeBean;
import com.shangxian.pinkink.bean.UserInfoBean;
import com.shangxian.pinkink.db.PinkInkDB;
import com.shangxian.pinkink.db.dao.UserCreateThemeDao;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: UserRepository.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
@DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository$getFirstUserCreateTheme$1$result$1", f = "UserRepository.kt", i = {}, l = {338}, m = "invokeSuspend", n = {}, s = {})
final class UserRepository$getFirstUserCreateTheme$1$result$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super UserCreateThemeBean>, Object> {
    int label;
    final /* synthetic */ UserRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UserRepository$getFirstUserCreateTheme$1$result$1(UserRepository userRepository, Continuation<? super UserRepository$getFirstUserCreateTheme$1$result$1> continuation) {
        super(2, continuation);
        this.this$0 = userRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new UserRepository$getFirstUserCreateTheme$1$result$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super UserCreateThemeBean> continuation) {
        return ((UserRepository$getFirstUserCreateTheme$1$result$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            UserInfoBean userInfo = this.this$0.getUserInfo();
            if (userInfo == null) {
                return null;
            }
            UserCreateThemeDao userCreateThemeDaoUserCrateThemeDao = PinkInkDB.INSTANCE.db().userCrateThemeDao();
            String id = userInfo.getId();
            Intrinsics.checkNotNullExpressionValue(id, "userInfo.id");
            this.label = 1;
            obj = userCreateThemeDaoUserCrateThemeDao.getFirstUserCreateThemeListByUserId(id, this);
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return CollectionsKt.firstOrNull((List) obj);
    }
}
