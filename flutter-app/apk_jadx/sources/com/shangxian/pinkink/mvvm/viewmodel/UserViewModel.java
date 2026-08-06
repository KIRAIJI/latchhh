package com.shangxian.pinkink.mvvm.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.lifecycle.MutableLiveData;
import com.lazyee.klib.extension.BitmapExtensionsKt;
import com.lazyee.klib.http.ApiCallback3;
import com.lazyee.klib.mvvm.LoadingState;
import com.lazyee.klib.mvvm.MVVMBaseViewModel;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.bean.ApiResult;
import com.shangxian.pinkink.bean.UpdateAvatarResultBean;
import com.shangxian.pinkink.bean.UserInfoBean;
import com.shangxian.pinkink.mvvm.repository.UserRepository;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.ResponseBody;

/* JADX INFO: compiled from: UserViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J>\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0016\b\u0002\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0016j\u0004\u0018\u0001`\u00172\u0016\b\u0002\u0010\u0018\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0016j\u0004\u0018\u0001`\u0017J$\u0010\u0019\u001a\u00020\u00122\u001c\u0010\u001a\u001a\u0018\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00120\u001bj\b\u0012\u0004\u0012\u00020\u001c`\u001dJ\u000e\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u0014J\b\u0010 \u001a\u0004\u0018\u00010!J\u0006\u0010\"\u001a\u00020\u0012J\u001c\u0010#\u001a\u00020\u00122\u0014\u0010$\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0016j\u0004\u0018\u0001`\u0017J\u0016\u0010%\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0014J,\u0010&\u001a\u00020\u00122\u0006\u0010'\u001a\u00020\u001c2\u001c\u0010(\u001a\u0018\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00120\u001bj\b\u0012\u0004\u0012\u00020\u0014`\u001dJ,\u0010)\u001a\u00020\u00122\u0006\u0010*\u001a\u00020\u00142\u001c\u0010(\u001a\u0018\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00120\u001bj\b\u0012\u0004\u0012\u00020\u0014`\u001dR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0007R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0007R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lcom/shangxian/pinkink/mvvm/viewmodel/UserViewModel;", "Lcom/lazyee/klib/mvvm/MVVMBaseViewModel;", "()V", "createCount", "Landroidx/lifecycle/MutableLiveData;", "", "getCreateCount", "()Landroidx/lifecycle/MutableLiveData;", "likeCount", "getLikeCount", "requestLoginStatus", "Lcom/lazyee/klib/mvvm/LoadingState;", "getRequestLoginStatus", "requestLoginVerifyCodeStatus", "getRequestLoginVerifyCodeStatus", "userRepository", "Lcom/shangxian/pinkink/mvvm/repository/UserRepository;", "cancelUser", "", "verifyCode", "", "onCancelUserCallback", "Lkotlin/Function0;", "Lcom/lazyee/klib/typed/VoidCallback;", "onFail", "getCancelUserImageCode", "onGetImageCodeSuccessCallback", "Lkotlin/Function1;", "Landroid/graphics/Bitmap;", "Lcom/lazyee/klib/typed/TCallback;", "getLoginVerifyCode", "phone", "getUserInfo", "Lcom/shangxian/pinkink/bean/UserInfoBean;", "getUserThemeInfo", "logout", "onLogoutSuccessCallback", "phoneLogin", "updateAvatar", "bitmap", "callback", "updateNickName", "newNickName", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class UserViewModel extends MVVMBaseViewModel {
    private final UserRepository userRepository = UserRepository.INSTANCE.getUserRepository();
    private final MutableLiveData<LoadingState> requestLoginVerifyCodeStatus = new MutableLiveData<>();
    private final MutableLiveData<LoadingState> requestLoginStatus = new MutableLiveData<>();
    private final MutableLiveData<Integer> likeCount = new MutableLiveData<>();
    private final MutableLiveData<Integer> createCount = new MutableLiveData<>();

    public final MutableLiveData<LoadingState> getRequestLoginVerifyCodeStatus() {
        return this.requestLoginVerifyCodeStatus;
    }

    public final MutableLiveData<LoadingState> getRequestLoginStatus() {
        return this.requestLoginStatus;
    }

    public final MutableLiveData<Integer> getLikeCount() {
        return this.likeCount;
    }

    public final MutableLiveData<Integer> getCreateCount() {
        return this.createCount;
    }

    public final UserInfoBean getUserInfo() {
        return this.userRepository.getUserInfo();
    }

    public final void updateNickName(final String newNickName, final Function1<? super String, Unit> callback) {
        Intrinsics.checkNotNullParameter(newNickName, "newNickName");
        Intrinsics.checkNotNullParameter(callback, "callback");
        onLoading();
        this.userRepository.updateNickName(newNickName, new ApiCallback3<ApiResult<String>>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.UserViewModel.updateNickName.1
            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<String> result) {
                UserViewModel.this.onLoadSuccess();
                callback.invoke(newNickName);
            }

            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<String> result) {
                UserViewModel.this.onLoadFailure();
                UserViewModel.this.toastShort(result == null ? null : result.getMsg());
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                UserViewModel.this.onLoadFailure();
                UserViewModel.this.toastShort(R.string.toast_request_failed);
            }
        });
    }

    public final void updateAvatar(Bitmap bitmap, final Function1<? super String, Unit> callback) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(callback, "callback");
        onLoading();
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
        UserRepository userRepository = this.userRepository;
        Intrinsics.checkNotNullExpressionValue(newBitmap, "newBitmap");
        userRepository.updateAvatar(BitmapExtensionsKt.toBase64String$default(newBitmap, null, 0, 3, null), new ApiCallback3<ApiResult<UpdateAvatarResultBean>>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.UserViewModel.updateAvatar.1
            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<UpdateAvatarResultBean> result) {
                UpdateAvatarResultBean data;
                String pic;
                UserViewModel.this.onLoadSuccess();
                Function1<String, Unit> function1 = callback;
                String str = "";
                if (result != null && (data = result.getData()) != null && (pic = data.getPic()) != null) {
                    str = pic;
                }
                function1.invoke(str);
            }

            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<UpdateAvatarResultBean> result) {
                UserViewModel.this.onLoadFailure();
                UserViewModel.this.toastShort(result == null ? null : result.getMsg());
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                UserViewModel.this.onLoadFailure();
                UserViewModel.this.toastShort(R.string.toast_request_failed);
            }
        });
    }

    public final void getUserThemeInfo() {
        this.likeCount.postValue(Integer.valueOf(this.userRepository.getUserLikeThemeCount()));
        this.createCount.postValue(Integer.valueOf(this.userRepository.getUserCreateThemeCount()));
    }

    public final void getLoginVerifyCode(String phone) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        onLoading();
        this.userRepository.getLoginVerifyCode(phone, new ApiCallback3<ApiResult<String>>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.UserViewModel.getLoginVerifyCode.1
            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<String> data) {
                UserViewModel.this.onLoadFailure();
                UserViewModel.this.toastShort(data == null ? null : data.getMsg());
                UserViewModel.this.getRequestLoginVerifyCodeStatus().postValue(LoadingState.FAILURE);
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                UserViewModel.this.onLoadFailure();
                UserViewModel.this.toastShort(R.string.toast_request_failed);
                UserViewModel.this.getRequestLoginVerifyCodeStatus().postValue(LoadingState.FAILURE);
            }

            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<String> data) {
                UserViewModel.this.onLoadSuccess();
                UserViewModel.this.toastShort(R.string.toast_get_verify_code_successfully);
                UserViewModel.this.getRequestLoginVerifyCodeStatus().postValue(LoadingState.SUCCESS);
            }
        });
    }

    public final void phoneLogin(String phone, String verifyCode) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        Intrinsics.checkNotNullParameter(verifyCode, "verifyCode");
        onLoading();
        this.userRepository.phoneLogin(phone, verifyCode, new ApiCallback3<ApiResult<UserInfoBean>>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.UserViewModel.phoneLogin.1
            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<UserInfoBean> result) {
                UserViewModel.this.toastShort(result == null ? null : result.getMsg());
                UserViewModel.this.onLoadFailure();
            }

            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<UserInfoBean> result) {
                UserViewModel.this.onLoadSuccess();
                UserViewModel.this.getRequestLoginStatus().postValue(LoadingState.SUCCESS);
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                UserViewModel.this.toastShort(R.string.toast_request_failed);
                UserViewModel.this.onLoadFailure();
            }
        });
    }

    public final void logout(final Function0<Unit> onLogoutSuccessCallback) {
        onLoading();
        this.userRepository.logout(new ApiCallback3<ApiResult<String>>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.UserViewModel.logout.1
            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<String> result) {
                UserViewModel.this.onLoadSuccess();
                Function0<Unit> function0 = onLogoutSuccessCallback;
                if (function0 == null) {
                    return;
                }
                function0.invoke();
            }

            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<String> result) {
                UserViewModel.this.onLoadFailure();
                UserViewModel.this.toastShort(result == null ? null : result.getMsg());
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                UserViewModel.this.onLoadFailure();
                UserViewModel.this.toastShort(R.string.toast_request_failed);
            }
        });
    }

    public final void getCancelUserImageCode(final Function1<? super Bitmap, Unit> onGetImageCodeSuccessCallback) {
        Intrinsics.checkNotNullParameter(onGetImageCodeSuccessCallback, "onGetImageCodeSuccessCallback");
        onLoading();
        this.userRepository.getCancelUserImageCode(new ApiCallback3<ResponseBody>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.UserViewModel.getCancelUserImageCode.1
            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ResponseBody result) {
                UserViewModel.this.onLoadSuccess();
                byte[] bArrBytes = result == null ? null : result.bytes();
                if (bArrBytes == null) {
                    return;
                }
                Bitmap bitmap = BitmapFactory.decodeByteArray(bArrBytes, 0, bArrBytes.length);
                Function1<Bitmap, Unit> function1 = onGetImageCodeSuccessCallback;
                Intrinsics.checkNotNullExpressionValue(bitmap, "bitmap");
                function1.invoke(bitmap);
            }

            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ResponseBody result) {
                UserViewModel.this.onLoadFailure();
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                UserViewModel.this.onLoadFailure();
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void cancelUser$default(UserViewModel userViewModel, String str, Function0 function0, Function0 function02, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        if ((i & 4) != 0) {
            function02 = null;
        }
        userViewModel.cancelUser(str, function0, function02);
    }

    public final void cancelUser(String verifyCode, final Function0<Unit> onCancelUserCallback, final Function0<Unit> onFail) {
        Intrinsics.checkNotNullParameter(verifyCode, "verifyCode");
        onLoading();
        this.userRepository.cancelUser(verifyCode, new ApiCallback3<ApiResult<String>>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.UserViewModel.cancelUser.1
            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<String> result) {
                UserViewModel.this.onLoadSuccess();
                UserViewModel.this.toastShort(R.string.toast_cancel_account_successfully);
                Function0<Unit> function0 = onCancelUserCallback;
                if (function0 == null) {
                    return;
                }
                function0.invoke();
            }

            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<String> result) {
                UserViewModel.this.onLoadFailure();
                Function0<Unit> function0 = onFail;
                if (function0 == null) {
                    return;
                }
                function0.invoke();
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                UserViewModel.this.onLoadFailure();
                UserViewModel.this.toastShort(R.string.toast_request_failed);
                Function0<Unit> function0 = onFail;
                if (function0 == null) {
                    return;
                }
                function0.invoke();
            }
        });
    }
}
