package com.shangxian.pinkink.mvvm.repository;

import com.shangxian.pinkink.bean.UserInfoBean;
import com.shangxian.pinkink.db.PinkInkDB;
import com.shangxian.pinkink.db.dao.UserLikeThemesDao;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: ThemesRepository.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
@DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.ThemesRepository$isLike$1$deferred$1", f = "ThemesRepository.kt", i = {}, l = {57}, m = "invokeSuspend", n = {}, s = {})
final class ThemesRepository$isLike$1$deferred$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
    final /* synthetic */ String $themeId;
    int label;
    final /* synthetic */ ThemesRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ThemesRepository$isLike$1$deferred$1(ThemesRepository themesRepository, String str, Continuation<? super ThemesRepository$isLike$1$deferred$1> continuation) {
        super(2, continuation);
        this.this$0 = themesRepository;
        this.$themeId = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ThemesRepository$isLike$1$deferred$1(this.this$0, this.$themeId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Boolean> continuation) {
        return ((ThemesRepository$isLike$1$deferred$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            UserInfoBean userInfo = this.this$0.userRepository.getUserInfo();
            if (userInfo == null) {
                return Boxing.boxBoolean(false);
            }
            UserLikeThemesDao userLikeThemesDao = PinkInkDB.INSTANCE.db().userLikeThemesDao();
            String str = this.$themeId;
            String id = userInfo.getId();
            Intrinsics.checkNotNullExpressionValue(id, "userInfo.id");
            this.label = 1;
            obj = userLikeThemesDao.getTheme(str, id, this);
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Boxing.boxBoolean(!((List) obj).isEmpty());
    }
}
