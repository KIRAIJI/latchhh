package com.shangxian.pinkink.mvvm.repository;

import com.shangxian.pinkink.bean.UserCreateThemeBean;
import com.shangxian.pinkink.bean.UserInfoBean;
import com.shangxian.pinkink.db.PinkInkDB;
import com.shangxian.pinkink.db.dao.UserCreateThemeDao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
@DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository$getUserAlbumCreateThemeList$1$result$1", f = "UserRepository.kt", i = {0}, l = {211}, m = "invokeSuspend", n = {"list"}, s = {"L$0"})
final class UserRepository$getUserAlbumCreateThemeList$1$result$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<UserCreateThemeBean>>, Object> {
    final /* synthetic */ long $albumColumnId;
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ UserRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UserRepository$getUserAlbumCreateThemeList$1$result$1(UserRepository userRepository, long j, Continuation<? super UserRepository$getUserAlbumCreateThemeList$1$result$1> continuation) {
        super(2, continuation);
        this.this$0 = userRepository;
        this.$albumColumnId = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new UserRepository$getUserAlbumCreateThemeList$1$result$1(this.this$0, this.$albumColumnId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super List<UserCreateThemeBean>> continuation) {
        return ((UserRepository$getUserAlbumCreateThemeList$1$result$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        List list;
        List list2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ArrayList arrayList = new ArrayList();
            UserInfoBean userInfo = this.this$0.getUserInfo();
            if (userInfo == null) {
                return arrayList;
            }
            UserCreateThemeDao userCreateThemeDaoUserCrateThemeDao = PinkInkDB.INSTANCE.db().userCrateThemeDao();
            String id = userInfo.getId();
            Intrinsics.checkNotNullExpressionValue(id, "userInfo.id");
            this.L$0 = arrayList;
            this.L$1 = arrayList;
            this.label = 1;
            Object userCreateThemeListByUserIdAndAlbumColumnId = userCreateThemeDaoUserCrateThemeDao.getUserCreateThemeListByUserIdAndAlbumColumnId(id, this.$albumColumnId, this);
            if (userCreateThemeListByUserIdAndAlbumColumnId == coroutine_suspended) {
                return coroutine_suspended;
            }
            list = arrayList;
            obj = userCreateThemeListByUserIdAndAlbumColumnId;
            list2 = list;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            list = (List) this.L$1;
            list2 = (List) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        list.addAll((Collection) obj);
        return list2;
    }
}
