package com.shangxian.pinkink.mvvm.repository;

import com.lazyee.klib.http.ApiCallback2;
import com.lazyee.klib.mvvm.MVVMBaseRepository;
import com.shangxian.pinkink.api.Api;
import com.shangxian.pinkink.api.IndexService;
import com.shangxian.pinkink.bean.ApiResult;
import com.shangxian.pinkink.bean.IndexPageDataBean;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IndexRepository.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fR\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lcom/shangxian/pinkink/mvvm/repository/IndexRepository;", "Lcom/lazyee/klib/mvvm/MVVMBaseRepository;", "()V", "indexService", "Lcom/shangxian/pinkink/api/IndexService;", "getIndexService", "()Lcom/shangxian/pinkink/api/IndexService;", "indexService$delegate", "Lkotlin/Lazy;", "getIndexData", "", "callback", "Lcom/lazyee/klib/http/ApiCallback2;", "Lcom/shangxian/pinkink/bean/ApiResult;", "Lcom/shangxian/pinkink/bean/IndexPageDataBean;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class IndexRepository extends MVVMBaseRepository {

    /* JADX INFO: renamed from: indexService$delegate, reason: from kotlin metadata */
    private final Lazy indexService = LazyKt.lazy(new Function0<IndexService>() { // from class: com.shangxian.pinkink.mvvm.repository.IndexRepository$indexService$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final IndexService invoke() {
            return (IndexService) Api.INSTANCE.getInstance().create(IndexService.class);
        }
    });

    private final IndexService getIndexService() {
        return (IndexService) this.indexService.getValue();
    }

    public final void getIndexData(ApiCallback2<ApiResult<IndexPageDataBean>> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Api.INSTANCE.getInstance().request(this, getIndexService().getIndexData(), callback);
    }
}
