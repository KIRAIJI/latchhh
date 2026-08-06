package com.shangxian.pinkink.mvvm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.lazyee.klib.http.ApiCallback3;
import com.lazyee.klib.mvvm.MVVMBaseViewModel;
import com.shangxian.pinkink.bean.ApiResult;
import com.shangxian.pinkink.bean.IndexPageDataBean;
import com.shangxian.pinkink.mvvm.repository.IndexRepository;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IndexViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\b\u001a\u00020\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006\u0012"}, d2 = {"Lcom/shangxian/pinkink/mvvm/viewmodel/IndexViewModel;", "Lcom/lazyee/klib/mvvm/MVVMBaseViewModel;", "()V", "indexPageData", "Landroidx/lifecycle/MutableLiveData;", "Lcom/shangxian/pinkink/bean/IndexPageDataBean;", "getIndexPageData", "()Landroidx/lifecycle/MutableLiveData;", "indexRepository", "Lcom/shangxian/pinkink/mvvm/repository/IndexRepository;", "getIndexRepository", "()Lcom/shangxian/pinkink/mvvm/repository/IndexRepository;", "indexRepository$delegate", "Lkotlin/Lazy;", "getIndexData", "", "isShowPageLoading", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class IndexViewModel extends MVVMBaseViewModel {

    /* JADX INFO: renamed from: indexRepository$delegate, reason: from kotlin metadata */
    private final Lazy indexRepository = LazyKt.lazy(new Function0<IndexRepository>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.IndexViewModel$indexRepository$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final IndexRepository invoke() {
            return new IndexRepository();
        }
    });
    private final MutableLiveData<IndexPageDataBean> indexPageData = new MutableLiveData<>();

    private final IndexRepository getIndexRepository() {
        return (IndexRepository) this.indexRepository.getValue();
    }

    public final MutableLiveData<IndexPageDataBean> getIndexPageData() {
        return this.indexPageData;
    }

    public final void getIndexData(boolean isShowPageLoading) {
        if (isShowPageLoading) {
            onPageLoading();
        }
        getIndexRepository().getIndexData(new ApiCallback3<ApiResult<IndexPageDataBean>>() { // from class: com.shangxian.pinkink.mvvm.viewmodel.IndexViewModel.getIndexData.1
            @Override // com.lazyee.klib.http.ApiCallback2
            public void onFailure(ApiResult<IndexPageDataBean> result) {
                IndexViewModel.this.onPageLoadFailure();
            }

            @Override // com.lazyee.klib.http.ApiCallback3
            public void onRequestFailure(Throwable throwable) {
                Intrinsics.checkNotNullParameter(throwable, "throwable");
                IndexViewModel.this.onPageLoadFailure();
            }

            @Override // com.lazyee.klib.http.ApiCallback
            public void onSuccess(ApiResult<IndexPageDataBean> result) {
                IndexViewModel.this.getIndexPageData().postValue(result == null ? null : result.getData());
            }
        });
    }
}
