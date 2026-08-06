package com.shangxian.pinkink.api;

import com.shangxian.pinkink.bean.ApiResult;
import com.shangxian.pinkink.bean.IndexPageDataBean;
import io.reactivex.Observable;
import kotlin.Metadata;
import retrofit2.http.GET;

/* JADX INFO: compiled from: IndexService.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H'¨\u0006\u0006"}, d2 = {"Lcom/shangxian/pinkink/api/IndexService;", "", "getIndexData", "Lio/reactivex/Observable;", "Lcom/shangxian/pinkink/bean/ApiResult;", "Lcom/shangxian/pinkink/bean/IndexPageDataBean;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public interface IndexService {
    @GET("index")
    Observable<ApiResult<IndexPageDataBean>> getIndexData();
}
