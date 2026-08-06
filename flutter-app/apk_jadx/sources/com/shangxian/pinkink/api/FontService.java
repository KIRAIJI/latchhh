package com.shangxian.pinkink.api;

import com.shangxian.pinkink.bean.ApiResult;
import com.shangxian.pinkink.bean.FontBean;
import io.reactivex.Observable;
import java.util.List;
import kotlin.Metadata;
import retrofit2.http.GET;

/* JADX INFO: compiled from: FontService.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00040\u0003H'¨\u0006\u0007"}, d2 = {"Lcom/shangxian/pinkink/api/FontService;", "", "getFontList", "Lio/reactivex/Observable;", "Lcom/shangxian/pinkink/bean/ApiResult;", "", "Lcom/shangxian/pinkink/bean/FontBean;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public interface FontService {
    @GET("font")
    Observable<ApiResult<List<FontBean>>> getFontList();
}
