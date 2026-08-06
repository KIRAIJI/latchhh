package com.shangxian.pinkink.mvvm.viewmodel;

import com.lazyee.klib.http.ApiCallback3;
import com.lazyee.klib.mvvm.MVVMBaseViewModel;
import com.lazyee.klib.util.LogUtils;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.bean.ApiResult;
import com.shangxian.pinkink.bean.FirmwareVersionBean;
import com.shangxian.pinkink.mvvm.repository.FirmwareRepository;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FirmwareViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\t\u001a\u00020\n2\u001c\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\n0\fj\b\u0012\u0004\u0012\u00020\r`\u000eR\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lcom/shangxian/pinkink/mvvm/viewmodel/FirmwareViewModel;", "Lcom/lazyee/klib/mvvm/MVVMBaseViewModel;", "()V", "firmwareRepository", "Lcom/shangxian/pinkink/mvvm/repository/FirmwareRepository;", "getFirmwareRepository", "()Lcom/shangxian/pinkink/mvvm/repository/FirmwareRepository;", "firmwareRepository$delegate", "Lkotlin/Lazy;", "getVersionInfo", "", "hasNewVersion", "Lkotlin/Function1;", "Lcom/shangxian/pinkink/bean/FirmwareVersionBean;", "Lcom/lazyee/klib/typed/TCallback;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class FirmwareViewModel extends MVVMBaseViewModel {

    /* JADX INFO: renamed from: firmwareRepository$delegate, reason: from kotlin metadata */
    private final Lazy firmwareRepository = LazyKt.lazy(new Function0<FirmwareRepository>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.FirmwareViewModel$firmwareRepository$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final FirmwareRepository invoke() {
            return new FirmwareRepository();
        }
    });

    private final FirmwareRepository getFirmwareRepository() {
        return (FirmwareRepository) this.firmwareRepository.getValue();
    }

    public final void getVersionInfo(final Function1<? super FirmwareVersionBean, Unit> hasNewVersion) {
        Intrinsics.checkNotNullParameter(hasNewVersion, "hasNewVersion");
        if (FirmwareRepository.INSTANCE.getVersion() == null) {
            toastShort(R.string.toast_eink_device_not_connected);
        } else {
            onLoading();
            getFirmwareRepository().getVersionInfo(new ApiCallback3<ApiResult<FirmwareVersionBean>>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.FirmwareViewModel.getVersionInfo.1
                @Override // com.lazyee.klib.http.ApiCallback
                public void onSuccess(ApiResult<FirmwareVersionBean> result) {
                    FirmwareVersionBean data;
                    LogUtils.INSTANCE.e("FirmwareViewModel", Intrinsics.stringPlus("firmware url:", (result == null || (data = result.getData()) == null) ? null : data.getUrl()));
                    FirmwareViewModel.this.onLoadSuccess();
                    if ((result == null ? null : result.getData()) == null) {
                        return;
                    }
                    FirmwareVersionBean data2 = result.getData();
                    if ((data2 == null ? null : data2.getVersion()) == null || FirmwareRepository.INSTANCE.getVersion() == null) {
                        return;
                    }
                    String version = FirmwareRepository.INSTANCE.getVersion();
                    FirmwareVersionBean data3 = result.getData();
                    if (Intrinsics.areEqual(version, data3 != null ? data3.getVersion() : null)) {
                        FirmwareViewModel.this.toastShort(R.string.toast_current_firmware_version_is_latest);
                        return;
                    }
                    Function1<FirmwareVersionBean, Unit> function1 = hasNewVersion;
                    FirmwareVersionBean data4 = result.getData();
                    Intrinsics.checkNotNull(data4);
                    function1.invoke(data4);
                }

                @Override // com.lazyee.klib.http.ApiCallback2
                public void onFailure(ApiResult<FirmwareVersionBean> result) {
                    FirmwareViewModel.this.onLoadFailure();
                    FirmwareViewModel.this.toastShort(result == null ? null : result.getMsg());
                }

                @Override // com.lazyee.klib.http.ApiCallback3
                public void onRequestFailure(Throwable throwable) {
                    Intrinsics.checkNotNullParameter(throwable, "throwable");
                    FirmwareViewModel.this.onLoadFailure();
                    FirmwareViewModel.this.toastShort(R.string.toast_request_failed);
                }
            });
        }
    }
}
