package com.shangxian.pinkink.api;

import com.shangxian.pinkink.bean.AiDrawingResultBean;
import com.shangxian.pinkink.bean.ApiResult;
import java.util.List;
import kotlin.Metadata;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/* JADX INFO: compiled from: AiDrawingService.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J.\u0010\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00040\u00032\b\b\u0001\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\t\u001a\u00020\bH'J\u001e\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00040\u00032\b\b\u0001\u0010\f\u001a\u00020\u000bH'¨\u0006\r"}, d2 = {"Lcom/shangxian/pinkink/api/AiDrawingService;", "", "getAiDrawingTaskList", "Lretrofit2/Call;", "Lcom/shangxian/pinkink/bean/ApiResult;", "", "Lcom/shangxian/pinkink/bean/AiDrawingResultBean;", "pageNum", "", "pageSize", "text2Img", "", "text", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public interface AiDrawingService {
    @FormUrlEncoded
    @POST("taskList")
    Call<ApiResult<List<AiDrawingResultBean>>> getAiDrawingTaskList(@Field("pageNum") int pageNum, @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("txtToimg")
    Call<ApiResult<String>> text2Img(@Field("text") String text);
}
