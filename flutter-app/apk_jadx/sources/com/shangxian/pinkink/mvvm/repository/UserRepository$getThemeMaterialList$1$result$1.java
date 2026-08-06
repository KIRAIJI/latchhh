package com.shangxian.pinkink.mvvm.repository;

import com.shangxian.pinkink.bean.ThemeMaterialBean;
import com.shangxian.pinkink.db.PinkInkDB;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: UserRepository.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0001*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "Lcom/shangxian/pinkink/bean/ThemeMaterialBean;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
@DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository$getThemeMaterialList$1$result$1", f = "UserRepository.kt", i = {}, l = {350}, m = "invokeSuspend", n = {}, s = {})
final class UserRepository$getThemeMaterialList$1$result$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<ThemeMaterialBean>>, Object> {
    final /* synthetic */ long $themeId;
    int label;
    final /* synthetic */ UserRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UserRepository$getThemeMaterialList$1$result$1(UserRepository userRepository, long j, Continuation<? super UserRepository$getThemeMaterialList$1$result$1> continuation) {
        super(2, continuation);
        this.this$0 = userRepository;
        this.$themeId = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new UserRepository$getThemeMaterialList$1$result$1(this.this$0, this.$themeId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super List<ThemeMaterialBean>> continuation) {
        return ((UserRepository$getThemeMaterialList$1$result$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.this$0.getUserInfo() == null) {
                return null;
            }
            this.label = 1;
            obj = PinkInkDB.INSTANCE.db().userCrateThemeDao().getCreateMaterialListByCreateThemeId(this.$themeId, this);
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }
}
