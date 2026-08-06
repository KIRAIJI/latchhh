package com.shangxian.pinkink.mvvm.repository;

import com.lazyee.klib.http.ApiCallback2;
import com.lazyee.klib.mvvm.MVVMBaseRepository;
import com.shangxian.pinkink.api.Api;
import com.shangxian.pinkink.api.FontService;
import com.shangxian.pinkink.bean.ApiResult;
import com.shangxian.pinkink.bean.FontBean;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FontRepository.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\t\u001a\u00020\n2\u0018\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\r0\fR\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/shangxian/pinkink/mvvm/repository/FontRepository;", "Lcom/lazyee/klib/mvvm/MVVMBaseRepository;", "()V", "fontService", "Lcom/shangxian/pinkink/api/FontService;", "getFontService", "()Lcom/shangxian/pinkink/api/FontService;", "fontService$delegate", "Lkotlin/Lazy;", "getFontList", "", "callback", "Lcom/lazyee/klib/http/ApiCallback2;", "Lcom/shangxian/pinkink/bean/ApiResult;", "", "Lcom/shangxian/pinkink/bean/FontBean;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class FontRepository extends MVVMBaseRepository {

    /* JADX INFO: renamed from: fontService$delegate, reason: from kotlin metadata */
    private final Lazy fontService = LazyKt.lazy(new Function0<FontService>() { // from class: com.shangxian.pinkink.mvvm.repository.FontRepository$fontService$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final FontService invoke() {
            return (FontService) Api.INSTANCE.getInstance().create(FontService.class);
        }
    });

    private final FontService getFontService() {
        return (FontService) this.fontService.getValue();
    }

    public final void getFontList(ApiCallback2<ApiResult<List<FontBean>>> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Api.INSTANCE.getInstance().request(this, getFontService().getFontList(), callback);
    }
}
