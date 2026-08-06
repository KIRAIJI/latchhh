package com.shangxian.pinkink.mvvm.repository;

import com.lazyee.klib.http.ApiCallback3;
import com.lazyee.klib.mvvm.MVVMBaseRepository;
import com.shangxian.pinkink.api.Api;
import com.shangxian.pinkink.api.FirmwareService;
import com.shangxian.pinkink.bean.ApiResult;
import com.shangxian.pinkink.bean.FirmwareVersionBean;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FirmwareRepository.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fR\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/shangxian/pinkink/mvvm/repository/FirmwareRepository;", "Lcom/lazyee/klib/mvvm/MVVMBaseRepository;", "()V", "firmwareService", "Lcom/shangxian/pinkink/api/FirmwareService;", "getFirmwareService", "()Lcom/shangxian/pinkink/api/FirmwareService;", "firmwareService$delegate", "Lkotlin/Lazy;", "getVersionInfo", "", "callback", "Lcom/lazyee/klib/http/ApiCallback3;", "Lcom/shangxian/pinkink/bean/ApiResult;", "Lcom/shangxian/pinkink/bean/FirmwareVersionBean;", "Companion", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class FirmwareRepository extends MVVMBaseRepository {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static String version;

    /* JADX INFO: renamed from: firmwareService$delegate, reason: from kotlin metadata */
    private final Lazy firmwareService = LazyKt.lazy(new Function0<FirmwareService>() { // from class: com.shangxian.pinkink.mvvm.repository.FirmwareRepository$firmwareService$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final FirmwareService invoke() {
            return (FirmwareService) Api.INSTANCE.getInstance().create(FirmwareService.class);
        }
    });

    /* JADX INFO: compiled from: FirmwareRepository.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\b\u001a\u00020\u00062\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/shangxian/pinkink/mvvm/repository/FirmwareRepository$Companion;", "", "()V", "version", "", "clearVersion", "", "getVersion", "setVersion", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void setVersion(String version) {
            FirmwareRepository.version = version;
        }

        public final String getVersion() {
            return FirmwareRepository.version;
        }

        public final void clearVersion() {
            FirmwareRepository.version = null;
        }
    }

    private final FirmwareService getFirmwareService() {
        return (FirmwareService) this.firmwareService.getValue();
    }

    public final void getVersionInfo(ApiCallback3<ApiResult<FirmwareVersionBean>> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Api.INSTANCE.getInstance().request(this, getFirmwareService().getFirmwareVersionInfo(), callback);
    }
}
