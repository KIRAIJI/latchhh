package com.shangxian.pinkink.mvvm.viewmodel;

import android.graphics.Bitmap;
import androidx.lifecycle.MutableLiveData;
import com.lazyee.klib.http.ApiCallback3;
import com.lazyee.klib.mvvm.MVVMBaseViewModel;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.bean.AlbumBean;
import com.shangxian.pinkink.bean.ApiResult;
import com.shangxian.pinkink.bean.PageDataBean;
import com.shangxian.pinkink.bean.ThemeBean;
import com.shangxian.pinkink.bean.ThemeCategoryBean;
import com.shangxian.pinkink.bean.ThemeMaterialBean;
import com.shangxian.pinkink.bean.UserCreateThemeBean;
import com.shangxian.pinkink.event.UserCreateThemeSaveEvent;
import com.shangxian.pinkink.mvvm.repository.ThemesRepository;
import com.shangxian.pinkink.mvvm.repository.UserRepository;
import com.shangxian.pinkink.util.InkFilterMode;
import com.shangxian.pinkink.util.InkPalette;
import com.shangxian.pinkink.util.InkScreenImageDitherUtils;
import com.shangxian.pinkink.widget.GestureScaleRotateView;
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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: compiled from: ThemesViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\"\u001a\u00020#2\u000e\u0010\u001d\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001e0$JF\u0010%\u001a\u00020#2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120$2\u0006\u0010&\u001a\u00020'2(\u0010(\u001a$\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\r\u0012\u0004\u0012\u00020#0)j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\r`*J\u0010\u0010+\u001a\u0004\u0018\u00010,2\u0006\u0010-\u001a\u00020,J&\u0010.\u001a\u00020#2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020,0$2\u0010\u0010(\u001a\f\u0012\u0004\u0012\u00020#00j\u0002`1J\u000e\u00102\u001a\u00020#2\u0006\u00103\u001a\u00020\u0012J\u000e\u00104\u001a\u00020#2\u0006\u00105\u001a\u00020'J\u000e\u00106\u001a\u00020#2\u0006\u00107\u001a\u00020,J\f\u00108\u001a\b\u0012\u0004\u0012\u00020,0$J\u000e\u00109\u001a\u00020#2\u0006\u00105\u001a\u00020'J\u000e\u0010:\u001a\u00020#2\u0006\u00105\u001a\u00020;J\u0006\u0010\u000f\u001a\u00020#J&\u0010\u0013\u001a\u00020#2\u0006\u0010<\u001a\u00020;2\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020>2\u0006\u0010@\u001a\u00020\u000bJ\u000e\u0010A\u001a\u00020#2\u0006\u0010&\u001a\u00020'J\u0006\u0010\u001f\u001a\u00020#J\u0006\u0010B\u001a\u00020#J\u0010\u0010C\u001a\u00020#2\b\u0010D\u001a\u0004\u0018\u00010\u0005J\u0010\u0010E\u001a\u00020#2\b\u0010D\u001a\u0004\u0018\u00010\u0005J\u0010\u0010F\u001a\u00020#2\b\u0010D\u001a\u0004\u0018\u00010\u0005J\u000e\u0010G\u001a\u00020#2\u0006\u00103\u001a\u00020\u0012J,\u0010H\u001a\u00020#2\u0006\u00105\u001a\u00020;2\u001c\u0010(\u001a\u0018\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020#0)j\b\u0012\u0004\u0012\u00020\u0012`*J\"\u0010I\u001a\u00020#2\u0006\u0010J\u001a\u00020\u001e2\u0012\u0010K\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030L\u0018\u00010\rJ\u000e\u0010M\u001a\u00020#2\u0006\u00105\u001a\u00020;J\u000e\u0010N\u001a\u00020#2\u0006\u00107\u001a\u00020,R\"\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0007R\u001d\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007R\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0007R\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\r0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0007R\u001b\u0010\u0017\u001a\u00020\u00188BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u0019\u0010\u001aR\u001d\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\r0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0007R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006O"}, d2 = {"Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "Lcom/lazyee/klib/mvvm/MVVMBaseViewModel;", "()V", "inkEffectBitmap", "Landroidx/lifecycle/MutableLiveData;", "Landroid/graphics/Bitmap;", "getInkEffectBitmap", "()Landroidx/lifecycle/MutableLiveData;", "setInkEffectBitmap", "(Landroidx/lifecycle/MutableLiveData;)V", "isLike", "", "themeCategoryList", "", "Lcom/shangxian/pinkink/bean/ThemeCategoryBean;", "getThemeCategoryList", "themeList", "Lcom/shangxian/pinkink/bean/PageDataBean;", "Lcom/shangxian/pinkink/bean/ThemeBean;", "getThemeList", "themeMaterialList", "Lcom/shangxian/pinkink/bean/ThemeMaterialBean;", "getThemeMaterialList", "themesRepository", "Lcom/shangxian/pinkink/mvvm/repository/ThemesRepository;", "getThemesRepository", "()Lcom/shangxian/pinkink/mvvm/repository/ThemesRepository;", "themesRepository$delegate", "Lkotlin/Lazy;", "userCreateThemeList", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "getUserCreateThemeList", "userRepository", "Lcom/shangxian/pinkink/mvvm/repository/UserRepository;", "batchDeleteUserCreateTheme", "", "", "batchSaveUserCreateTheme", "albumColumnId", "", "callback", "Lkotlin/Function1;", "Lcom/lazyee/klib/typed/TCallback;", "createAlbum", "Lcom/shangxian/pinkink/bean/AlbumBean;", "albumBean", "deleteAlbum", "albumList", "Lkotlin/Function0;", "Lcom/lazyee/klib/typed/VoidCallback;", "deleteLikeTheme", "theme", "deleteUserCreateTheme", "themeId", "fullAlbum", "album", "getAllAlbumByUser", "getCreateMaterialList", "getLikeStatus", "", "categoryId", "pageNum", "", "pageSize", "isShowPageLoading", "getUserAlbumCreateThemeList", "getUserLikeThemeList", "grayScaleEffect", "bitmap", "jitterEffect", "levelEffect", "likeTheme", "requestThemeDetail", "saveUserCreateTheme", "userCreateTheme", "views", "Lcom/shangxian/pinkink/widget/GestureScaleRotateView;", "unLikeTheme", "updateAlbum", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ThemesViewModel extends MVVMBaseViewModel {

    /* JADX INFO: renamed from: themesRepository$delegate, reason: from kotlin metadata */
    private final Lazy themesRepository = LazyKt.lazy(new Function0<ThemesRepository>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$themesRepository$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ThemesRepository invoke() {
            return new ThemesRepository();
        }
    });
    private final UserRepository userRepository = UserRepository.INSTANCE.getUserRepository();
    private final MutableLiveData<List<ThemeCategoryBean>> themeCategoryList = new MutableLiveData<>();
    private final MutableLiveData<PageDataBean<ThemeBean>> themeList = new MutableLiveData<>();
    private final MutableLiveData<List<UserCreateThemeBean>> userCreateThemeList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLike = new MutableLiveData<>();
    private final MutableLiveData<List<ThemeMaterialBean>> themeMaterialList = new MutableLiveData<>();
    private MutableLiveData<Bitmap> inkEffectBitmap = new MutableLiveData<>();

    /* JADX INFO: Access modifiers changed from: private */
    public final ThemesRepository getThemesRepository() {
        return (ThemesRepository) this.themesRepository.getValue();
    }

    public final MutableLiveData<List<ThemeCategoryBean>> getThemeCategoryList() {
        return this.themeCategoryList;
    }

    public final MutableLiveData<PageDataBean<ThemeBean>> getThemeList() {
        return this.themeList;
    }

    public final MutableLiveData<List<UserCreateThemeBean>> getUserCreateThemeList() {
        return this.userCreateThemeList;
    }

    public final MutableLiveData<Boolean> isLike() {
        return this.isLike;
    }

    public final MutableLiveData<List<ThemeMaterialBean>> getThemeMaterialList() {
        return this.themeMaterialList;
    }

    public final MutableLiveData<Bitmap> getInkEffectBitmap() {
        return this.inkEffectBitmap;
    }

    public final void setInkEffectBitmap(MutableLiveData<Bitmap> mutableLiveData) {
        Intrinsics.checkNotNullParameter(mutableLiveData, "<set-?>");
        this.inkEffectBitmap = mutableLiveData;
    }

    public final void requestThemeDetail(String themeId, final Function1<? super ThemeBean, Unit> callback) {
        Intrinsics.checkNotNullParameter(themeId, "themeId");
        Intrinsics.checkNotNullParameter(callback, "callback");
        onPageLoading();
        getThemesRepository().getThemeDetail(themeId, new ApiCallback3<ApiResult<ThemeBean>>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel.requestThemeDetail.1
            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<ThemeBean> result) {
                ThemesViewModel.this.onPageLoadSuccess();
                ThemeBean data = result == null ? null : result.getData();
                if (data == null) {
                    return;
                }
                callback.invoke(data);
            }

            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<ThemeBean> result) {
                ThemesViewModel.this.onPageLoadFailure();
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                ThemesViewModel.this.onPageLoadFailure();
            }
        });
    }

    /* JADX INFO: renamed from: getThemeCategoryList, reason: collision with other method in class */
    public final void m118getThemeCategoryList() {
        onPageLoading();
        getThemesRepository().getThemesCategoryList(new ApiCallback3<ApiResult<List<ThemeCategoryBean>>>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel.getThemeCategoryList.1
            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<List<ThemeCategoryBean>> result) {
                ThemesViewModel.this.onPageLoadFailure();
            }

            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<List<ThemeCategoryBean>> result) {
                ThemesViewModel.this.onPageLoadSuccess();
                ThemesViewModel.this.getThemeCategoryList().postValue(result == null ? null : result.getData());
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                ThemesViewModel.this.onPageLoadFailure();
            }
        });
    }

    public final void getUserLikeThemeList() {
        this.themeList.postValue(new PageDataBean<>(false, 0, 0, getThemesRepository().getUserLikeThemeList()));
    }

    /* JADX INFO: renamed from: getUserCreateThemeList, reason: collision with other method in class */
    public final void m119getUserCreateThemeList() {
        this.userCreateThemeList.postValue(this.userRepository.getUserCreateThemeList());
    }

    public final void getUserAlbumCreateThemeList(long albumColumnId) {
        this.userCreateThemeList.postValue(this.userRepository.getUserAlbumCreateThemeList(albumColumnId));
    }

    public final void getThemeList(String categoryId, final int pageNum, final int pageSize, final boolean isShowPageLoading) {
        Intrinsics.checkNotNullParameter(categoryId, "categoryId");
        if (isShowPageLoading) {
            onPageLoading();
        }
        getThemesRepository().getThemesList(categoryId, pageNum, pageSize, new ApiCallback3<ApiResult<List<ThemeBean>>>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel.getThemeList.1
            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<List<ThemeBean>> result) {
                if (isShowPageLoading) {
                    this.onPageLoadFailure();
                }
            }

            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<List<ThemeBean>> result) {
                if (isShowPageLoading) {
                    this.onPageLoadSuccess();
                }
                if (result == null) {
                    return;
                }
                this.getThemeList().postValue(new PageDataBean<>(result.getMore(), pageNum, pageSize, result.getData()));
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                if (isShowPageLoading) {
                    this.onPageLoadFailure();
                }
            }
        });
    }

    public final void getLikeStatus(String themeId) {
        Intrinsics.checkNotNullParameter(themeId, "themeId");
        this.isLike.postValue(Boolean.valueOf(getThemesRepository().isLike(themeId)));
    }

    public final void likeTheme(ThemeBean theme) {
        Intrinsics.checkNotNullParameter(theme, "theme");
        getThemesRepository().likeTheme(theme);
        this.isLike.postValue(true);
        toastShort(R.string.toast_collect_successfully);
    }

    public final void unLikeTheme(String themeId) {
        Intrinsics.checkNotNullParameter(themeId, "themeId");
        getThemesRepository().unLikeTheme(themeId);
        this.isLike.postValue(false);
        toastShort(R.string.toast_cancel_collect_successfully);
    }

    public final void deleteLikeTheme(ThemeBean theme) {
        Intrinsics.checkNotNullParameter(theme, "theme");
        getThemesRepository().unLikeTheme(theme.getId());
    }

    public final void deleteUserCreateTheme(long themeId) {
        this.userRepository.deleteUserCreateTheme(themeId);
    }

    public final void batchDeleteUserCreateTheme(List<UserCreateThemeBean> userCreateThemeList) {
        Intrinsics.checkNotNullParameter(userCreateThemeList, "userCreateThemeList");
        this.userRepository.batchDeleteUserCreateTheme(userCreateThemeList);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$saveUserCreateTheme$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ThemesViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$saveUserCreateTheme$1", f = "ThemesViewModel.kt", i = {}, l = {172, 173}, m = "invokeSuspend", n = {}, s = {})
    static final class C00981 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ UserCreateThemeBean $userCreateTheme;
        final /* synthetic */ List<GestureScaleRotateView<?>> $views;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00981(UserCreateThemeBean userCreateThemeBean, List<GestureScaleRotateView<?>> list, Continuation<? super C00981> continuation) {
            super(2, continuation);
            this.$userCreateTheme = userCreateThemeBean;
            this.$views = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ThemesViewModel.this.new C00981(this.$userCreateTheme, this.$views, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00981) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (ThemesViewModel.this.userRepository.saveUserCreateTheme(this.$userCreateTheme, this.$views, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    if (i == 2) {
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            this.label = 2;
            if (BuildersKt.withContext(Dispatchers.getMain(), new C00181(this.$userCreateTheme, ThemesViewModel.this, null), this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$saveUserCreateTheme$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ThemesViewModel.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
        @DebugMetadata(c = "com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$saveUserCreateTheme$1$1", f = "ThemesViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00181 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ UserCreateThemeBean $userCreateTheme;
            int label;
            final /* synthetic */ ThemesViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00181(UserCreateThemeBean userCreateThemeBean, ThemesViewModel themesViewModel, Continuation<? super C00181> continuation) {
                super(2, continuation);
                this.$userCreateTheme = userCreateThemeBean;
                this.this$0 = themesViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00181(this.$userCreateTheme, this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00181) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                EventBus.getDefault().post(new UserCreateThemeSaveEvent(this.$userCreateTheme));
                this.this$0.toastShort(R.string.toast_save_successfully);
                return Unit.INSTANCE;
            }
        }
    }

    public final void saveUserCreateTheme(UserCreateThemeBean userCreateTheme, List<GestureScaleRotateView<?>> views) {
        Intrinsics.checkNotNullParameter(userCreateTheme, "userCreateTheme");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C00981(userCreateTheme, views, null), 3, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$batchSaveUserCreateTheme$1, reason: invalid class name */
    /* JADX INFO: compiled from: ThemesViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$batchSaveUserCreateTheme$1", f = "ThemesViewModel.kt", i = {}, l = {185, 186}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ long $albumColumnId;
        final /* synthetic */ Function1<List<UserCreateThemeBean>, Unit> $callback;
        final /* synthetic */ List<ThemeBean> $themeList;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass1(List<ThemeBean> list, long j, Function1<? super List<UserCreateThemeBean>, Unit> function1, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$themeList = list;
            this.$albumColumnId = j;
            this.$callback = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ThemesViewModel.this.new AnonymousClass1(this.$themeList, this.$albumColumnId, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = ThemesViewModel.this.userRepository.batchSaveUserCreateTheme(this.$themeList, this.$albumColumnId, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    if (i == 2) {
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            MainCoroutineDispatcher main = Dispatchers.getMain();
            Function1<List<UserCreateThemeBean>, Unit> function1 = this.$callback;
            this.label = 2;
            if (BuildersKt.withContext(main, new C00161(function1, (List) obj, null), this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$batchSaveUserCreateTheme$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ThemesViewModel.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
        @DebugMetadata(c = "com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$batchSaveUserCreateTheme$1$1", f = "ThemesViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00161 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Function1<List<UserCreateThemeBean>, Unit> $callback;
            final /* synthetic */ List<UserCreateThemeBean> $userCreateThemeList;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            C00161(Function1<? super List<UserCreateThemeBean>, Unit> function1, List<UserCreateThemeBean> list, Continuation<? super C00161> continuation) {
                super(2, continuation);
                this.$callback = function1;
                this.$userCreateThemeList = list;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00161(this.$callback, this.$userCreateThemeList, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00161) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$callback.invoke(this.$userCreateThemeList);
                return Unit.INSTANCE;
            }
        }
    }

    public final void batchSaveUserCreateTheme(List<ThemeBean> themeList, long albumColumnId, Function1<? super List<UserCreateThemeBean>, Unit> callback) {
        Intrinsics.checkNotNullParameter(themeList, "themeList");
        Intrinsics.checkNotNullParameter(callback, "callback");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass1(themeList, albumColumnId, callback, null), 3, null);
    }

    public final void getCreateMaterialList(long themeId) {
        this.themeMaterialList.postValue(this.userRepository.getThemeMaterialList(themeId));
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$levelEffect$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ThemesViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$levelEffect$1", f = "ThemesViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C00961 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Bitmap $bitmap;
        int label;
        final /* synthetic */ ThemesViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00961(Bitmap bitmap, ThemesViewModel themesViewModel, Continuation<? super C00961> continuation) {
            super(2, continuation);
            this.$bitmap = bitmap;
            this.this$0 = themesViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C00961(this.$bitmap, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00961) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.this$0.getInkEffectBitmap().postValue(InkScreenImageDitherUtils.INSTANCE.ditheringCanvasByPalette(this.$bitmap, InkPalette.INSTANCE.getBWRY(), InkFilterMode.BINARY));
            return Unit.INSTANCE;
        }
    }

    public final void levelEffect(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C00961(bitmap, this, null), 3, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$jitterEffect$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ThemesViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$jitterEffect$1", f = "ThemesViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C00951 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Bitmap $bitmap;
        int label;
        final /* synthetic */ ThemesViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00951(Bitmap bitmap, ThemesViewModel themesViewModel, Continuation<? super C00951> continuation) {
            super(2, continuation);
            this.$bitmap = bitmap;
            this.this$0 = themesViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C00951(this.$bitmap, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00951) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.this$0.getInkEffectBitmap().postValue(InkScreenImageDitherUtils.INSTANCE.ditheringCanvasByPalette(this.$bitmap, InkPalette.INSTANCE.getBWRY(), InkFilterMode.FLOYD_STEINBERG));
            return Unit.INSTANCE;
        }
    }

    public final void jitterEffect(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C00951(bitmap, this, null), 3, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$grayScaleEffect$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ThemesViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$grayScaleEffect$1", f = "ThemesViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C00941 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Bitmap $bitmap;
        int label;
        final /* synthetic */ ThemesViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00941(Bitmap bitmap, ThemesViewModel themesViewModel, Continuation<? super C00941> continuation) {
            super(2, continuation);
            this.$bitmap = bitmap;
            this.this$0 = themesViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C00941(this.$bitmap, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00941) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.this$0.getInkEffectBitmap().postValue(InkScreenImageDitherUtils.INSTANCE.dithering(this.$bitmap, 127, InkFilterMode.FLOYD_STEINBERG));
            return Unit.INSTANCE;
        }
    }

    public final void grayScaleEffect(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C00941(bitmap, this, null), 3, null);
    }

    public final AlbumBean createAlbum(AlbumBean albumBean) {
        Intrinsics.checkNotNullParameter(albumBean, "albumBean");
        return getThemesRepository().createAlbum(albumBean);
    }

    public final List<AlbumBean> getAllAlbumByUser() {
        return getThemesRepository().getAllAlbumByUser();
    }

    public final void fullAlbum(AlbumBean album) {
        Intrinsics.checkNotNullParameter(album, "album");
        getThemesRepository().fullAlbum(album);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$deleteAlbum$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ThemesViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$deleteAlbum$1", f = "ThemesViewModel.kt", i = {}, l = {257, 258}, m = "invokeSuspend", n = {}, s = {})
    static final class C00911 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<AlbumBean> $albumList;
        final /* synthetic */ Function0<Unit> $callback;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00911(List<AlbumBean> list, Function0<Unit> function0, Continuation<? super C00911> continuation) {
            super(2, continuation);
            this.$albumList = list;
            this.$callback = function0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ThemesViewModel.this.new C00911(this.$albumList, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00911) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (ThemesViewModel.this.getThemesRepository().deleteAlbum(this.$albumList, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    if (i == 2) {
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            this.label = 2;
            if (BuildersKt.withContext(Dispatchers.getMain(), new C00171(this.$callback, null), this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$deleteAlbum$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ThemesViewModel.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
        @DebugMetadata(c = "com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel$deleteAlbum$1$1", f = "ThemesViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00171 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Function0<Unit> $callback;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00171(Function0<Unit> function0, Continuation<? super C00171> continuation) {
                super(2, continuation);
                this.$callback = function0;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00171(this.$callback, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00171) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$callback.invoke();
                return Unit.INSTANCE;
            }
        }
    }

    public final void deleteAlbum(List<AlbumBean> albumList, Function0<Unit> callback) {
        Intrinsics.checkNotNullParameter(albumList, "albumList");
        Intrinsics.checkNotNullParameter(callback, "callback");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C00911(albumList, callback, null), 3, null);
    }

    public final void updateAlbum(AlbumBean album) {
        Intrinsics.checkNotNullParameter(album, "album");
        getThemesRepository().updateAlbum(album);
    }
}
