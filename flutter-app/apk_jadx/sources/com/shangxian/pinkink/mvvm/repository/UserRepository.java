package com.shangxian.pinkink.mvvm.repository;

import com.lazyee.klib.http.ApiCallback3;
import com.lazyee.klib.mvvm.MVVMBaseRepository;
import com.shangxian.pinkink.api.Api;
import com.shangxian.pinkink.api.UserService;
import com.shangxian.pinkink.bean.ApiResult;
import com.shangxian.pinkink.bean.ThemeBean;
import com.shangxian.pinkink.bean.ThemeMaterialBean;
import com.shangxian.pinkink.bean.UpdateAvatarResultBean;
import com.shangxian.pinkink.bean.UserCreateThemeBean;
import com.shangxian.pinkink.bean.UserInfoBean;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.db.PinkInkDB;
import com.shangxian.pinkink.db.PinkInkRoomDatabase;
import com.shangxian.pinkink.db.dao.UserCreateThemeDao;
import com.shangxian.pinkink.mvvm.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import okhttp3.ResponseBody;

/* JADX INFO: compiled from: UserRepository.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 <2\u00020\u0001:\u0001<B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u000b\u001a\u00020\f2\u000e\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000eJ-\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000e2\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0016J\"\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u00192\u0012\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u001c0\u001bJ\u000e\u0010\u001d\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\u0015J\u0014\u0010\u001f\u001a\u00020\f2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020 0\u001bJ\b\u0010!\u001a\u0004\u0018\u00010\u000fJ\"\u0010\"\u001a\u00020\f2\u0006\u0010#\u001a\u00020\u00192\u0012\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u001c0\u001bJ\u0016\u0010$\u001a\n\u0012\u0004\u0012\u00020%\u0018\u00010\u00112\u0006\u0010\u001e\u001a\u00020\u0015J\u0014\u0010&\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00112\u0006\u0010'\u001a\u00020\u0015J\u0006\u0010(\u001a\u00020)J\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011J\b\u0010+\u001a\u0004\u0018\u00010\u0004J\u0012\u0010,\u001a\u0004\u0018\u00010\u00042\u0006\u0010-\u001a\u00020\u0019H\u0002J\u0006\u0010.\u001a\u00020)J\u001a\u0010/\u001a\u00020\f2\u0012\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u001c0\u001bJ*\u00100\u001a\u00020\f2\u0006\u0010#\u001a\u00020\u00192\u0006\u0010\u0018\u001a\u00020\u00192\u0012\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u001c0\u001bJ\u001a\u00101\u001a\u00020\f2\u0012\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u001c0\u001bJ-\u00102\u001a\u00020\f2\u0006\u00103\u001a\u00020\u000f2\u0012\u00104\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u000305\u0018\u00010\u0011H\u0086@ø\u0001\u0000¢\u0006\u0002\u00106J\"\u00107\u001a\u00020\f2\u0006\u00108\u001a\u00020\u00192\u0012\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002090\u001c0\u001bJ\"\u0010:\u001a\u00020\f2\u0006\u0010;\u001a\u00020\u00192\u0012\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u001c0\u001bR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006="}, d2 = {"Lcom/shangxian/pinkink/mvvm/repository/UserRepository;", "Lcom/lazyee/klib/mvvm/MVVMBaseRepository;", "()V", "userInfo", "Lcom/shangxian/pinkink/bean/UserInfoBean;", "userService", "Lcom/shangxian/pinkink/api/UserService;", "getUserService", "()Lcom/shangxian/pinkink/api/UserService;", "userService$delegate", "Lkotlin/Lazy;", "batchDeleteUserCreateTheme", "", "userCreateThemeList", "", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "batchSaveUserCreateTheme", "", "themeList", "Lcom/shangxian/pinkink/bean/ThemeBean;", "albumId", "", "(Ljava/util/List;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancelUser", "verifyCode", "", "callback", "Lcom/lazyee/klib/http/ApiCallback3;", "Lcom/shangxian/pinkink/bean/ApiResult;", "deleteUserCreateTheme", "themeId", "getCancelUserImageCode", "Lokhttp3/ResponseBody;", "getFirstUserCreateTheme", "getLoginVerifyCode", "phone", "getThemeMaterialList", "Lcom/shangxian/pinkink/bean/ThemeMaterialBean;", "getUserAlbumCreateThemeList", "albumColumnId", "getUserCreateThemeCount", "", "getUserCreateThemeList", "getUserInfo", "getUserInfoFromDB", "token", "getUserLikeThemeCount", "logout", "phoneLogin", "requestUserInfo", "saveUserCreateTheme", "userCreateTheme", "views", "Lcom/shangxian/pinkink/widget/GestureScaleRotateView;", "(Lcom/shangxian/pinkink/bean/UserCreateThemeBean;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateAvatar", "avatarBase64", "Lcom/shangxian/pinkink/bean/UpdateAvatarResultBean;", "updateNickName", "newNickName", "Companion", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class UserRepository extends MVVMBaseRepository {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Lazy<UserRepository> instance$delegate = LazyKt.lazy(new Function0<UserRepository>() { // from class: com.shangxian.pinkink.mvvm.repository.UserRepository$Companion$instance$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UserRepository invoke() {
            return new UserRepository();
        }
    });
    private UserInfoBean userInfo;

    /* JADX INFO: renamed from: userService$delegate, reason: from kotlin metadata */
    private final Lazy userService = LazyKt.lazy(new Function0<UserService>() { // from class: com.shangxian.pinkink.mvvm.repository.UserRepository$userService$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UserService invoke() {
            return (UserService) Api.INSTANCE.getInstance().create(UserService.class);
        }
    });

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.UserRepository$saveUserCreateTheme$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: UserRepository.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository", f = "UserRepository.kt", i = {0, 0, 0, 1, 1, 1, 2, 2}, l = {230, 234, 236, 253}, m = "saveUserCreateTheme", n = {"userCreateTheme", "views", "themeId", "views", "themeId", "userCreateThemeDao", "views", "themeId"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$0", "L$1"})
    static final class C00851 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        C00851(Continuation<? super C00851> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return UserRepository.this.saveUserCreateTheme(null, null, this);
        }
    }

    /* JADX INFO: compiled from: UserRepository.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\u0004R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"Lcom/shangxian/pinkink/mvvm/repository/UserRepository$Companion;", "", "()V", "instance", "Lcom/shangxian/pinkink/mvvm/repository/UserRepository;", "getInstance", "()Lcom/shangxian/pinkink/mvvm/repository/UserRepository;", "instance$delegate", "Lkotlin/Lazy;", "getUserRepository", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        private final UserRepository getInstance() {
            return (UserRepository) UserRepository.instance$delegate.getValue();
        }

        public final UserRepository getUserRepository() {
            return getInstance();
        }
    }

    private final UserService getUserService() {
        return (UserService) this.userService.getValue();
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.UserRepository$getUserLikeThemeCount$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: UserRepository.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository$getUserLikeThemeCount$1", f = "UserRepository.kt", i = {}, l = {54}, m = "invokeSuspend", n = {}, s = {})
    static final class C00811 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Integer>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        C00811(Continuation<? super C00811> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00811 c00811 = UserRepository.this.new C00811(continuation);
            c00811.L$0 = obj;
            return c00811;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Integer> continuation) {
            return ((C00811) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = BuildersKt__Builders_commonKt.async$default((CoroutineScope) this.L$0, null, null, new UserRepository$getUserLikeThemeCount$1$result$1(UserRepository.this, null), 3, null).await(this);
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

    public final int getUserLikeThemeCount() {
        return ((Number) BuildersKt__BuildersKt.runBlocking$default(null, new C00811(null), 1, null)).intValue();
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.UserRepository$getUserCreateThemeCount$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: UserRepository.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository$getUserCreateThemeCount$1", f = "UserRepository.kt", i = {}, l = {66}, m = "invokeSuspend", n = {}, s = {})
    static final class C00781 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Integer>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        C00781(Continuation<? super C00781> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00781 c00781 = UserRepository.this.new C00781(continuation);
            c00781.L$0 = obj;
            return c00781;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Integer> continuation) {
            return ((C00781) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = BuildersKt__Builders_commonKt.async$default((CoroutineScope) this.L$0, null, null, new UserRepository$getUserCreateThemeCount$1$result$1(UserRepository.this, null), 3, null).await(this);
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

    public final int getUserCreateThemeCount() {
        return ((Number) BuildersKt__BuildersKt.runBlocking$default(null, new C00781(null), 1, null)).intValue();
    }

    public final UserInfoBean getUserInfo() {
        if (this.userInfo == null) {
            this.userInfo = getUserInfoFromDB(AppConfig.INSTANCE.getToken());
        }
        return this.userInfo;
    }

    public final void updateNickName(final String newNickName, final ApiCallback3<ApiResult<String>> callback) {
        Intrinsics.checkNotNullParameter(newNickName, "newNickName");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Api.INSTANCE.getInstance().request(this, getUserService().updateNickName(newNickName), new ApiCallback3<ApiResult<String>>() { // from class: com.shangxian.pinkink.mvvm.repository.UserRepository.updateNickName.1
            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<String> result) {
                callback.onSuccess(result);
                if (this.userInfo != null) {
                    BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new UserRepository$updateNickName$1$onSuccess$1(this, newNickName, null), 3, null);
                }
            }

            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<String> result) {
                callback.onFailure(result);
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                callback.onRequestFailure(throwable);
            }
        });
    }

    public final void updateAvatar(String avatarBase64, final ApiCallback3<ApiResult<UpdateAvatarResultBean>> callback) {
        Intrinsics.checkNotNullParameter(avatarBase64, "avatarBase64");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Api.INSTANCE.getInstance().request(this, getUserService().updateAvatar(avatarBase64), new ApiCallback3<ApiResult<UpdateAvatarResultBean>>() { // from class: com.shangxian.pinkink.mvvm.repository.UserRepository.updateAvatar.1
            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<UpdateAvatarResultBean> result) {
                callback.onSuccess(result);
                if (this.userInfo != null) {
                    BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new UserRepository$updateAvatar$1$onSuccess$1(result, this, null), 3, null);
                }
            }

            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<UpdateAvatarResultBean> result) {
                callback.onFailure(result);
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                callback.onRequestFailure(throwable);
            }
        });
    }

    public final void getLoginVerifyCode(String phone, ApiCallback3<ApiResult<String>> callback) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Api.INSTANCE.getInstance().request(this, getUserService().getLoginVerifyCode(phone), callback);
    }

    public final void phoneLogin(String phone, String verifyCode, final ApiCallback3<ApiResult<UserInfoBean>> callback) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        Intrinsics.checkNotNullParameter(verifyCode, "verifyCode");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Api.INSTANCE.getInstance().request(this, getUserService().phoneLogin(phone, verifyCode, "android", String.valueOf(System.currentTimeMillis())), new ApiCallback3<ApiResult<String>>() { // from class: com.shangxian.pinkink.mvvm.repository.UserRepository.phoneLogin.1
            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<String> result) {
                if (result == null) {
                    return;
                }
                ApiResult<UserInfoBean> apiResult = new ApiResult<>();
                apiResult.setMsg(result.getMsg());
                apiResult.setCode(result.getCode());
                callback.onFailure(apiResult);
            }

            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<String> result) {
                AppConfig.INSTANCE.setToken(result == null ? null : result.getData());
                this.requestUserInfo(callback);
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                callback.onRequestFailure(throwable);
            }
        });
    }

    public final void requestUserInfo(final ApiCallback3<ApiResult<UserInfoBean>> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Api.INSTANCE.getInstance().request(this, getUserService().getUserInfo(), new ApiCallback3<ApiResult<UserInfoBean>>() { // from class: com.shangxian.pinkink.mvvm.repository.UserRepository.requestUserInfo.1
            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<UserInfoBean> result) {
                callback.onFailure(result);
            }

            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<UserInfoBean> result) {
                UserInfoBean data;
                callback.onSuccess(result);
                if (result == null || (data = result.getData()) == null) {
                    return;
                }
                UserRepository userRepository = this;
                data.setToken(AppConfig.INSTANCE.getToken());
                userRepository.userInfo = data;
                BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new UserRepository$requestUserInfo$1$onSuccess$1$1(data, null), 3, null);
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                callback.onRequestFailure(throwable);
            }
        });
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.UserRepository$getUserInfoFromDB$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: UserRepository.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "Lcom/shangxian/pinkink/bean/UserInfoBean;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository$getUserInfoFromDB$1", f = "UserRepository.kt", i = {}, l = {186}, m = "invokeSuspend", n = {}, s = {})
    static final class C00801 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super UserInfoBean>, Object> {
        final /* synthetic */ String $token;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00801(String str, Continuation<? super C00801> continuation) {
            super(2, continuation);
            this.$token = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00801 c00801 = new C00801(this.$token, continuation);
            c00801.L$0 = obj;
            return c00801;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super UserInfoBean> continuation) {
            return ((C00801) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = BuildersKt__Builders_commonKt.async$default((CoroutineScope) this.L$0, null, null, new UserRepository$getUserInfoFromDB$1$result$1(this.$token, null), 3, null).await(this);
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

    private final UserInfoBean getUserInfoFromDB(String token) {
        return (UserInfoBean) BuildersKt__BuildersKt.runBlocking$default(null, new C00801(token, null), 1, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.UserRepository$getUserCreateThemeList$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: UserRepository.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository$getUserCreateThemeList$1", f = "UserRepository.kt", i = {}, l = {200}, m = "invokeSuspend", n = {}, s = {})
    static final class C00791 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<UserCreateThemeBean>>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        C00791(Continuation<? super C00791> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00791 c00791 = UserRepository.this.new C00791(continuation);
            c00791.L$0 = obj;
            return c00791;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super List<UserCreateThemeBean>> continuation) {
            return ((C00791) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = BuildersKt__Builders_commonKt.async$default((CoroutineScope) this.L$0, null, null, new UserRepository$getUserCreateThemeList$1$result$1(UserRepository.this, null), 3, null).await(this);
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

    public final List<UserCreateThemeBean> getUserCreateThemeList() {
        return (List) BuildersKt__BuildersKt.runBlocking$default(null, new C00791(null), 1, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.UserRepository$getUserAlbumCreateThemeList$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: UserRepository.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository$getUserAlbumCreateThemeList$1", f = "UserRepository.kt", i = {}, l = {214}, m = "invokeSuspend", n = {}, s = {})
    static final class C00771 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<UserCreateThemeBean>>, Object> {
        final /* synthetic */ long $albumColumnId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00771(long j, Continuation<? super C00771> continuation) {
            super(2, continuation);
            this.$albumColumnId = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00771 c00771 = UserRepository.this.new C00771(this.$albumColumnId, continuation);
            c00771.L$0 = obj;
            return c00771;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super List<UserCreateThemeBean>> continuation) {
            return ((C00771) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = BuildersKt__Builders_commonKt.async$default((CoroutineScope) this.L$0, null, null, new UserRepository$getUserAlbumCreateThemeList$1$result$1(UserRepository.this, this.$albumColumnId, null), 3, null).await(this);
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

    public final List<UserCreateThemeBean> getUserAlbumCreateThemeList(long albumColumnId) {
        return (List) BuildersKt__BuildersKt.runBlocking$default(null, new C00771(albumColumnId, null), 1, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00fc A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x018e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object saveUserCreateTheme(com.shangxian.pinkink.bean.UserCreateThemeBean r18, java.util.List<com.shangxian.pinkink.widget.GestureScaleRotateView<?>> r19, kotlin.coroutines.Continuation<? super kotlin.Unit> r20) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 402
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shangxian.pinkink.mvvm.repository.UserRepository.saveUserCreateTheme(com.shangxian.pinkink.bean.UserCreateThemeBean, java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object batchSaveUserCreateTheme(final List<ThemeBean> list, final long j, Continuation<? super List<UserCreateThemeBean>> continuation) {
        final ArrayList arrayList = new ArrayList();
        final UserCreateThemeDao userCreateThemeDaoUserCrateThemeDao = PinkInkDB.INSTANCE.db().userCrateThemeDao();
        final UserInfoBean userInfo = getUserInfo();
        if (userInfo == null) {
            return arrayList;
        }
        PinkInkDB.INSTANCE.db().runInTransaction(new Runnable() { // from class: com.shangxian.pinkink.mvvm.repository.UserRepository$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws InterruptedException {
                UserRepository.m116batchSaveUserCreateTheme$lambda2(list, userInfo, j, userCreateThemeDaoUserCrateThemeDao, arrayList);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: batchSaveUserCreateTheme$lambda-2, reason: not valid java name */
    public static final void m116batchSaveUserCreateTheme$lambda2(List themeList, UserInfoBean userInfoBean, long j, UserCreateThemeDao userCreateThemeDao, List userCreateThemeList) throws InterruptedException {
        Intrinsics.checkNotNullParameter(themeList, "$themeList");
        Intrinsics.checkNotNullParameter(userCreateThemeDao, "$userCreateThemeDao");
        Intrinsics.checkNotNullParameter(userCreateThemeList, "$userCreateThemeList");
        BuildersKt__BuildersKt.runBlocking$default(null, new UserRepository$batchSaveUserCreateTheme$2$1(themeList, userInfoBean, j, userCreateThemeDao, userCreateThemeList, null), 1, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.UserRepository$deleteUserCreateTheme$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: UserRepository.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository$deleteUserCreateTheme$1", f = "UserRepository.kt", i = {0}, l = {296, 297}, m = "invokeSuspend", n = {"userCreateThemeDao"}, s = {"L$0"})
    static final class C00741 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ long $themeId;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00741(long j, Continuation<? super C00741> continuation) {
            super(2, continuation);
            this.$themeId = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return UserRepository.this.new C00741(this.$themeId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00741) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            UserCreateThemeDao userCreateThemeDaoUserCrateThemeDao;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                userCreateThemeDaoUserCrateThemeDao = PinkInkDB.INSTANCE.db().userCrateThemeDao();
                if (UserRepository.this.getUserInfo() == null) {
                    return Unit.INSTANCE;
                }
                this.L$0 = userCreateThemeDaoUserCrateThemeDao;
                this.label = 1;
                if (userCreateThemeDaoUserCrateThemeDao.deleteCreateMaterialByCreateThemeId(this.$themeId, this) == coroutine_suspended) {
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
                userCreateThemeDaoUserCrateThemeDao = (UserCreateThemeDao) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (userCreateThemeDaoUserCrateThemeDao.deleteUserCreateThemeById(this.$themeId, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    public final void deleteUserCreateTheme(long themeId) {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C00741(themeId, null), 3, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.UserRepository$batchDeleteUserCreateTheme$1, reason: invalid class name */
    /* JADX INFO: compiled from: UserRepository.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository$batchDeleteUserCreateTheme$1", f = "UserRepository.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<UserCreateThemeBean> $userCreateThemeList;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(List<UserCreateThemeBean> list, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$userCreateThemeList = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return UserRepository.this.new AnonymousClass1(this.$userCreateThemeList, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (UserRepository.this.getUserInfo() == null) {
                return Unit.INSTANCE;
            }
            PinkInkRoomDatabase pinkInkRoomDatabaseDb = PinkInkDB.INSTANCE.db();
            final UserCreateThemeDao userCreateThemeDaoUserCrateThemeDao = pinkInkRoomDatabaseDb.userCrateThemeDao();
            final List<UserCreateThemeBean> list = this.$userCreateThemeList;
            pinkInkRoomDatabaseDb.runInTransaction(new Runnable() { // from class: com.shangxian.pinkink.mvvm.repository.UserRepository$batchDeleteUserCreateTheme$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    UserRepository.AnonymousClass1.m117invokeSuspend$lambda0(list, userCreateThemeDaoUserCrateThemeDao);
                }
            });
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m117invokeSuspend$lambda0(List list, UserCreateThemeDao userCreateThemeDao) {
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new UserRepository$batchDeleteUserCreateTheme$1$1$1(list, userCreateThemeDao, null), 3, null);
        }
    }

    public final void batchDeleteUserCreateTheme(List<UserCreateThemeBean> userCreateThemeList) {
        Intrinsics.checkNotNullParameter(userCreateThemeList, "userCreateThemeList");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass1(userCreateThemeList, null), 3, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.UserRepository$getFirstUserCreateTheme$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: UserRepository.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository$getFirstUserCreateTheme$1", f = "UserRepository.kt", i = {}, l = {340}, m = "invokeSuspend", n = {}, s = {})
    static final class C00751 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super UserCreateThemeBean>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        C00751(Continuation<? super C00751> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00751 c00751 = UserRepository.this.new C00751(continuation);
            c00751.L$0 = obj;
            return c00751;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super UserCreateThemeBean> continuation) {
            return ((C00751) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = BuildersKt__Builders_commonKt.async$default((CoroutineScope) this.L$0, null, null, new UserRepository$getFirstUserCreateTheme$1$result$1(UserRepository.this, null), 3, null).await(this);
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

    public final UserCreateThemeBean getFirstUserCreateTheme() {
        return (UserCreateThemeBean) BuildersKt__BuildersKt.runBlocking$default(null, new C00751(null), 1, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.mvvm.repository.UserRepository$getThemeMaterialList$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: UserRepository.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0001*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "Lcom/shangxian/pinkink/bean/ThemeMaterialBean;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.mvvm.repository.UserRepository$getThemeMaterialList$1", f = "UserRepository.kt", i = {}, l = {352}, m = "invokeSuspend", n = {}, s = {})
    static final class C00761 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<ThemeMaterialBean>>, Object> {
        final /* synthetic */ long $themeId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00761(long j, Continuation<? super C00761> continuation) {
            super(2, continuation);
            this.$themeId = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00761 c00761 = UserRepository.this.new C00761(this.$themeId, continuation);
            c00761.L$0 = obj;
            return c00761;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super List<ThemeMaterialBean>> continuation) {
            return ((C00761) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = BuildersKt__Builders_commonKt.async$default((CoroutineScope) this.L$0, null, null, new UserRepository$getThemeMaterialList$1$result$1(UserRepository.this, this.$themeId, null), 3, null).await(this);
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

    public final List<ThemeMaterialBean> getThemeMaterialList(long themeId) {
        return (List) BuildersKt__BuildersKt.runBlocking$default(null, new C00761(themeId, null), 1, null);
    }

    public final void logout(final ApiCallback3<ApiResult<String>> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Api.INSTANCE.getInstance().request(this, getUserService().logout(), new ApiCallback3<ApiResult<String>>() { // from class: com.shangxian.pinkink.mvvm.repository.UserRepository.logout.1
            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<String> result) {
                callback.onSuccess(result);
                AppConfig.INSTANCE.setToken(null);
            }

            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<String> result) {
                callback.onFailure(result);
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                callback.onRequestFailure(throwable);
            }
        });
    }

    public final void getCancelUserImageCode(ApiCallback3<ResponseBody> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Api.INSTANCE.getInstance().request(this, getUserService().getCancelUserImageCode(), callback);
    }

    public final void cancelUser(String verifyCode, final ApiCallback3<ApiResult<String>> callback) {
        Intrinsics.checkNotNullParameter(verifyCode, "verifyCode");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Api.INSTANCE.getInstance().request(this, getUserService().cancelUser(verifyCode), new ApiCallback3<ApiResult<String>>() { // from class: com.shangxian.pinkink.mvvm.repository.UserRepository.cancelUser.1
            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<String> result) {
                BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new UserRepository$cancelUser$1$onSuccess$1(this, null), 3, null);
                callback.onSuccess(result);
            }

            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<String> result) {
                callback.onFailure(result);
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                callback.onRequestFailure(throwable);
            }
        });
    }
}
