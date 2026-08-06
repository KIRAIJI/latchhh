package com.shangxian.pinkink.mvvm.repository;

import com.shangxian.pinkink.bean.AlbumBean;
import com.shangxian.pinkink.bean.UserInfoBean;
import com.shangxian.pinkink.db.PinkInkDB;
import com.shangxian.pinkink.db.dao.UserAlbumDao;
import java.util.Iterator;
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

/* JADX INFO: compiled from: ThemesRepository.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "Lcom/shangxian/pinkink/bean/AlbumBean;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
@DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.ThemesRepository$getAllAlbumByUser$1$deferred$1", f = "ThemesRepository.kt", i = {}, l = {102}, m = "invokeSuspend", n = {}, s = {})
final class ThemesRepository$getAllAlbumByUser$1$deferred$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<? extends AlbumBean>>, Object> {
    int label;
    final /* synthetic */ ThemesRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ThemesRepository$getAllAlbumByUser$1$deferred$1(ThemesRepository themesRepository, Continuation<? super ThemesRepository$getAllAlbumByUser$1$deferred$1> continuation) {
        super(2, continuation);
        this.this$0 = themesRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ThemesRepository$getAllAlbumByUser$1$deferred$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super List<? extends AlbumBean>> continuation) {
        return invoke2(coroutineScope, (Continuation<? super List<AlbumBean>>) continuation);
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final Object invoke2(CoroutineScope coroutineScope, Continuation<? super List<AlbumBean>> continuation) {
        return ((ThemesRepository$getAllAlbumByUser$1$deferred$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            UserInfoBean userInfo = this.this$0.userRepository.getUserInfo();
            if (userInfo == null) {
                return CollectionsKt.emptyList();
            }
            UserAlbumDao userAlbumDao = PinkInkDB.INSTANCE.db().userAlbumDao();
            PinkInkDB.INSTANCE.db().userCrateThemeDao();
            String id = userInfo.getId();
            Intrinsics.checkNotNullExpressionValue(id, "userInfo.id");
            this.label = 1;
            obj = userAlbumDao.getAllUserAlbumList(id, this);
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        List list = (List) obj;
        ThemesRepository themesRepository = this.this$0;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            themesRepository.fullAlbum((AlbumBean) it.next());
        }
        return list;
    }
}
