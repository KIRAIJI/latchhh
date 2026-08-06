package com.shangxian.pinkink.api;

import com.shangxian.pinkink.bean.ApiResult;
import com.shangxian.pinkink.bean.ThemeBean;
import com.shangxian.pinkink.bean.ThemeCategoryBean;
import io.reactivex.Observable;
import java.util.List;
import kotlin.Metadata;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/* JADX INFO: compiled from: ThemesService.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00040\u0003H'J\u001e\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00040\u00032\b\b\u0001\u0010\t\u001a\u00020\nH'J8\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00050\u00040\u00032\b\b\u0001\u0010\f\u001a\u00020\n2\b\b\u0001\u0010\r\u001a\u00020\u000e2\b\b\u0001\u0010\u000f\u001a\u00020\u000eH'¨\u0006\u0010"}, d2 = {"Lcom/shangxian/pinkink/api/ThemesService;", "", "getThemeCategoryList", "Lio/reactivex/Observable;", "Lcom/shangxian/pinkink/bean/ApiResult;", "", "Lcom/shangxian/pinkink/bean/ThemeCategoryBean;", "getThemeDetail", "Lcom/shangxian/pinkink/bean/ThemeBean;", "themeId", "", "getThemeList", "categoryId", "pageNum", "", "pageSize", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public interface ThemesService {
    @GET("category")
    Observable<ApiResult<List<ThemeCategoryBean>>> getThemeCategoryList();

    @GET("theme/{themeId}")
    Observable<ApiResult<ThemeBean>> getThemeDetail(@Path("themeId") String themeId);

    @FormUrlEncoded
    @POST("theme")
    Observable<ApiResult<List<ThemeBean>>> getThemeList(@Field("categoryId") String categoryId, @Field("pageNum") int pageNum, @Field("pageSize") int pageSize);
}
