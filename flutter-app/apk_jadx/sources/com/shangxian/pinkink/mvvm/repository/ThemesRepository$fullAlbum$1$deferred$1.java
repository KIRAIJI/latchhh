package com.shangxian.pinkink.mvvm.repository;

import android.text.TextUtils;
import com.shangxian.pinkink.bean.AlbumBean;
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

/* JADX INFO: compiled from: ThemesRepository.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "Lcom/shangxian/pinkink/bean/AlbumBean;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
@DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.ThemesRepository$fullAlbum$1$deferred$1", f = "ThemesRepository.kt", i = {}, l = {121}, m = "invokeSuspend", n = {}, s = {})
final class ThemesRepository$fullAlbum$1$deferred$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super AlbumBean>, Object> {
    final /* synthetic */ AlbumBean $album;
    int label;
    final /* synthetic */ ThemesRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ThemesRepository$fullAlbum$1$deferred$1(ThemesRepository themesRepository, AlbumBean albumBean, Continuation<? super ThemesRepository$fullAlbum$1$deferred$1> continuation) {
        super(2, continuation);
        this.this$0 = themesRepository;
        this.$album = albumBean;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ThemesRepository$fullAlbum$1$deferred$1(this.this$0, this.$album, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super AlbumBean> continuation) {
        return ((ThemesRepository$fullAlbum$1$deferred$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            UserInfoBean userInfo = this.this$0.userRepository.getUserInfo();
            if (userInfo == null) {
                return this.$album;
            }
            UserCreateThemeDao userCreateThemeDaoUserCrateThemeDao = PinkInkDB.INSTANCE.db().userCrateThemeDao();
            String id = userInfo.getId();
            Intrinsics.checkNotNullExpressionValue(id, "userInfo.id");
            this.label = 1;
            obj = userCreateThemeDaoUserCrateThemeDao.getUserCreateThemeListByUserIdAndAlbumColumnId(id, this.$album.getColumnId(), this);
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
        UserCreateThemeBean userCreateThemeBean = (UserCreateThemeBean) CollectionsKt.firstOrNull(list);
        String composeImagePath = null;
        if (TextUtils.isEmpty(userCreateThemeBean == null ? null : userCreateThemeBean.getComposeImagePath())) {
            if (userCreateThemeBean != null) {
                composeImagePath = userCreateThemeBean.getBackgroundImagePath();
            }
        } else if (userCreateThemeBean != null) {
            composeImagePath = userCreateThemeBean.getComposeImagePath();
        }
        AlbumBean albumBean = this.$album;
        if (composeImagePath == null) {
            composeImagePath = "";
        }
        albumBean.setCover(composeImagePath);
        this.$album.getUserCreateThemeList().addAll(list);
        return this.$album;
    }
}
