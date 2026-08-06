package com.shangxian.pinkink.mvvm.repository;

import com.lazyee.klib.http.ApiCallback3;
import com.lazyee.klib.mvvm.MVVMBaseRepository;
import com.shangxian.pinkink.api.Api;
import com.shangxian.pinkink.api.ThemesService;
import com.shangxian.pinkink.bean.AlbumBean;
import com.shangxian.pinkink.bean.ApiResult;
import com.shangxian.pinkink.bean.ThemeBean;
import com.shangxian.pinkink.bean.ThemeCategoryBean;
import com.shangxian.pinkink.bean.UserInfoBean;
import com.shangxian.pinkink.db.PinkInkDB;
import com.shangxian.pinkink.db.dao.UserLikeThemesDao;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: ThemesRepository.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\fJ\u001f\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u0011H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0012J\u000e\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\fJ\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\f0\u0011J\"\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00182\u0012\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u001b0\u001aJ \u0010\u001d\u001a\u00020\u000f2\u0018\u0010\u0019\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e0\u001b0\u001aJ8\u0010 \u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\u00182\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#2\u0018\u0010\u0019\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u001e0\u001b0\u001aJ\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001eJ\u000e\u0010&\u001a\u00020'2\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010(\u001a\u00020\u000f2\u0006\u0010)\u001a\u00020\u001cJ\u000e\u0010*\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010+\u001a\u00020\u000f2\u0006\u0010\r\u001a\u00020\fR\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006,"}, d2 = {"Lcom/shangxian/pinkink/mvvm/repository/ThemesRepository;", "Lcom/lazyee/klib/mvvm/MVVMBaseRepository;", "()V", "themesService", "Lcom/shangxian/pinkink/api/ThemesService;", "getThemesService", "()Lcom/shangxian/pinkink/api/ThemesService;", "themesService$delegate", "Lkotlin/Lazy;", "userRepository", "Lcom/shangxian/pinkink/mvvm/repository/UserRepository;", "createAlbum", "Lcom/shangxian/pinkink/bean/AlbumBean;", "albumBean", "deleteAlbum", "", "albumList", "", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fullAlbum", "album", "getAllAlbumByUser", "getThemeDetail", "themeId", "", "callback", "Lcom/lazyee/klib/http/ApiCallback3;", "Lcom/shangxian/pinkink/bean/ApiResult;", "Lcom/shangxian/pinkink/bean/ThemeBean;", "getThemesCategoryList", "", "Lcom/shangxian/pinkink/bean/ThemeCategoryBean;", "getThemesList", "categoryId", "pageNum", "", "pageSize", "getUserLikeThemeList", "isLike", "", "likeTheme", "theme", "unLikeTheme", "updateAlbum", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ThemesRepository extends MVVMBaseRepository {
    private final UserRepository userRepository = UserRepository.INSTANCE.getUserRepository();

    /* JADX INFO: renamed from: themesService$delegate, reason: from kotlin metadata */
    private final Lazy themesService = LazyKt.lazy(new Function0<ThemesService>() { // from class: com.shangxian.pinkink.mvvm.repository.ThemesRepository$themesService$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ThemesService invoke() {
            return (ThemesService) Api.INSTANCE.getInstance().create(ThemesService.class);
        }
    });

    private final ThemesService getThemesService() {
        return (ThemesService) this.themesService.getValue();
    }

    public final void getThemesCategoryList(ApiCallback3<ApiResult<List<ThemeCategoryBean>>> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Api.INSTANCE.getInstance().request(this, getThemesService().getThemeCategoryList(), callback);
    }

    public final void getThemesList(String categoryId, int pageNum, int pageSize, ApiCallback3<ApiResult<List<ThemeBean>>> callback) {
        Intrinsics.checkNotNullParameter(categoryId, "categoryId");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Api.INSTANCE.getInstance().request(this, getThemesService().getThemeList(categoryId, pageNum, pageSize), callback);
    }

    public final void getThemeDetail(String themeId, ApiCallback3<ApiResult<ThemeBean>> callback) {
        Intrinsics.checkNotNullParameter(themeId, "themeId");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Api.INSTANCE.getInstance().request(this, getThemesService().getThemeDetail(themeId), callback);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.ThemesRepository$getUserLikeThemeList$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ThemesRepository.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "Lcom/shangxian/pinkink/bean/ThemeBean;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.ThemesRepository$getUserLikeThemeList$1", f = "ThemesRepository.kt", i = {}, l = {49}, m = "invokeSuspend", n = {}, s = {})
    static final class C00681 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<ThemeBean>>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        C00681(Continuation<? super C00681> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00681 c00681 = ThemesRepository.this.new C00681(continuation);
            c00681.L$0 = obj;
            return c00681;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super List<ThemeBean>> continuation) {
            return ((C00681) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = BuildersKt__Builders_commonKt.async$default((CoroutineScope) this.L$0, null, null, new ThemesRepository$getUserLikeThemeList$1$result$1(ThemesRepository.this, null), 3, null).await(this);
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

    public final List<ThemeBean> getUserLikeThemeList() {
        return (List) BuildersKt__BuildersKt.runBlocking$default(null, new C00681(null), 1, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.ThemesRepository$isLike$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ThemesRepository.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.ThemesRepository$isLike$1", f = "ThemesRepository.kt", i = {}, l = {60}, m = "invokeSuspend", n = {}, s = {})
    static final class C00691 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
        final /* synthetic */ String $themeId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00691(String str, Continuation<? super C00691> continuation) {
            super(2, continuation);
            this.$themeId = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00691 c00691 = ThemesRepository.this.new C00691(this.$themeId, continuation);
            c00691.L$0 = obj;
            return c00691;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Boolean> continuation) {
            return ((C00691) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = BuildersKt__Builders_commonKt.async$default((CoroutineScope) this.L$0, null, null, new ThemesRepository$isLike$1$deferred$1(ThemesRepository.this, this.$themeId, null), 3, null).await(this);
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

    public final boolean isLike(String themeId) {
        Intrinsics.checkNotNullParameter(themeId, "themeId");
        return ((Boolean) BuildersKt__BuildersKt.runBlocking$default(null, new C00691(themeId, null), 1, null)).booleanValue();
    }

    public final void likeTheme(ThemeBean theme) {
        Intrinsics.checkNotNullParameter(theme, "theme");
        UserInfoBean userInfo = this.userRepository.getUserInfo();
        if (userInfo == null) {
            return;
        }
        String id = userInfo.getId();
        Intrinsics.checkNotNullExpressionValue(id, "userInfo.id");
        theme.setLikeUserId(id);
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C00701(theme, null), 3, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.ThemesRepository$likeTheme$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ThemesRepository.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.ThemesRepository$likeTheme$1", f = "ThemesRepository.kt", i = {}, l = {68}, m = "invokeSuspend", n = {}, s = {})
    static final class C00701 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ ThemeBean $theme;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00701(ThemeBean themeBean, Continuation<? super C00701> continuation) {
            super(2, continuation);
            this.$theme = themeBean;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C00701(this.$theme, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00701) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (PinkInkDB.INSTANCE.db().userLikeThemesDao().like(this.$theme, this) == coroutine_suspended) {
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

    public final void unLikeTheme(String themeId) {
        Intrinsics.checkNotNullParameter(themeId, "themeId");
        UserInfoBean userInfo = this.userRepository.getUserInfo();
        if (userInfo == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C00711(themeId, userInfo, null), 3, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.ThemesRepository$unLikeTheme$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ThemesRepository.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.ThemesRepository$unLikeTheme$1", f = "ThemesRepository.kt", i = {}, l = {76}, m = "invokeSuspend", n = {}, s = {})
    static final class C00711 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $themeId;
        final /* synthetic */ UserInfoBean $userInfo;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00711(String str, UserInfoBean userInfoBean, Continuation<? super C00711> continuation) {
            super(2, continuation);
            this.$themeId = str;
            this.$userInfo = userInfoBean;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C00711(this.$themeId, this.$userInfo, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00711) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                UserLikeThemesDao userLikeThemesDao = PinkInkDB.INSTANCE.db().userLikeThemesDao();
                String str = this.$themeId;
                String id = this.$userInfo.getId();
                Intrinsics.checkNotNullExpressionValue(id, "userInfo.id");
                this.label = 1;
                if (userLikeThemesDao.unlike(str, id, this) == coroutine_suspended) {
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

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.ThemesRepository$createAlbum$1, reason: invalid class name */
    /* JADX INFO: compiled from: ThemesRepository.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "Lcom/shangxian/pinkink/bean/AlbumBean;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.ThemesRepository$createAlbum$1", f = "ThemesRepository.kt", i = {}, l = {93}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super AlbumBean>, Object> {
        final /* synthetic */ AlbumBean $albumBean;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(AlbumBean albumBean, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$albumBean = albumBean;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = ThemesRepository.this.new AnonymousClass1(this.$albumBean, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super AlbumBean> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = BuildersKt__Builders_commonKt.async$default((CoroutineScope) this.L$0, null, null, new ThemesRepository$createAlbum$1$deferred$1(ThemesRepository.this, this.$albumBean, null), 3, null).await(this);
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

    public final AlbumBean createAlbum(AlbumBean albumBean) {
        Intrinsics.checkNotNullParameter(albumBean, "albumBean");
        return (AlbumBean) BuildersKt__BuildersKt.runBlocking$default(null, new AnonymousClass1(albumBean, null), 1, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.ThemesRepository$getAllAlbumByUser$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ThemesRepository.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "Lcom/shangxian/pinkink/bean/AlbumBean;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.ThemesRepository$getAllAlbumByUser$1", f = "ThemesRepository.kt", i = {}, l = {113}, m = "invokeSuspend", n = {}, s = {})
    static final class C00671 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<? extends AlbumBean>>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        C00671(Continuation<? super C00671> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00671 c00671 = ThemesRepository.this.new C00671(continuation);
            c00671.L$0 = obj;
            return c00671;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super List<? extends AlbumBean>> continuation) {
            return invoke2(coroutineScope, (Continuation<? super List<AlbumBean>>) continuation);
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(CoroutineScope coroutineScope, Continuation<? super List<AlbumBean>> continuation) {
            return ((C00671) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = BuildersKt__Builders_commonKt.async$default((CoroutineScope) this.L$0, null, null, new ThemesRepository$getAllAlbumByUser$1$deferred$1(ThemesRepository.this, null), 3, null).await(this);
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

    public final List<AlbumBean> getAllAlbumByUser() {
        return (List) BuildersKt__BuildersKt.runBlocking$default(null, new C00671(null), 1, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.ThemesRepository$fullAlbum$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ThemesRepository.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "Lcom/shangxian/pinkink/bean/AlbumBean;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.ThemesRepository$fullAlbum$1", f = "ThemesRepository.kt", i = {}, l = {128}, m = "invokeSuspend", n = {}, s = {})
    static final class C00661 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super AlbumBean>, Object> {
        final /* synthetic */ AlbumBean $album;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00661(AlbumBean albumBean, Continuation<? super C00661> continuation) {
            super(2, continuation);
            this.$album = albumBean;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00661 c00661 = ThemesRepository.this.new C00661(this.$album, continuation);
            c00661.L$0 = obj;
            return c00661;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super AlbumBean> continuation) {
            return ((C00661) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = BuildersKt__Builders_commonKt.async$default((CoroutineScope) this.L$0, null, null, new ThemesRepository$fullAlbum$1$deferred$1(ThemesRepository.this, this.$album, null), 3, null).await(this);
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

    public final AlbumBean fullAlbum(AlbumBean album) {
        Intrinsics.checkNotNullParameter(album, "album");
        return (AlbumBean) BuildersKt__BuildersKt.runBlocking$default(null, new C00661(album, null), 1, null);
    }

    public final Object deleteAlbum(final List<AlbumBean> list, Continuation<? super Unit> continuation) {
        PinkInkDB.INSTANCE.db().runInTransaction(new Runnable() { // from class: com.shangxian.pinkink.mvvm.repository.ThemesRepository$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws InterruptedException {
                ThemesRepository.m114deleteAlbum$lambda1(list);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: deleteAlbum$lambda-1, reason: not valid java name */
    public static final void m114deleteAlbum$lambda1(List albumList) throws InterruptedException {
        Intrinsics.checkNotNullParameter(albumList, "$albumList");
        Iterator it = albumList.iterator();
        while (it.hasNext()) {
            BuildersKt__BuildersKt.runBlocking$default(null, new ThemesRepository$deleteAlbum$2$1$1((AlbumBean) it.next(), null), 1, null);
        }
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.ThemesRepository$updateAlbum$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ThemesRepository.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.ThemesRepository$updateAlbum$1", f = "ThemesRepository.kt", i = {}, l = {151}, m = "invokeSuspend", n = {}, s = {})
    static final class C00721 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ AlbumBean $albumBean;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00721(AlbumBean albumBean, Continuation<? super C00721> continuation) {
            super(2, continuation);
            this.$albumBean = albumBean;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C00721(this.$albumBean, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00721) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (PinkInkDB.INSTANCE.db().userAlbumDao().updateAlbum(this.$albumBean, this) == coroutine_suspended) {
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

    public final void updateAlbum(AlbumBean albumBean) {
        Intrinsics.checkNotNullParameter(albumBean, "albumBean");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C00721(albumBean, null), 3, null);
    }
}
