package com.shangxian.pinkink.api;

import com.shangxian.pinkink.bean.ApiResult;
import com.shangxian.pinkink.bean.FirmwareVersionBean;
import io.reactivex.Observable;
import kotlin.Metadata;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/* JADX INFO: compiled from: FirmwareService.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u0007H'J\u0014\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00040\u0003H'¨\u0006\n"}, d2 = {"Lcom/shangxian/pinkink/api/FirmwareService;", "", "checkFirmwareUIDLegal", "Lio/reactivex/Observable;", "Lcom/shangxian/pinkink/bean/ApiResult;", "", "uID", "", "getFirmwareVersionInfo", "Lcom/shangxian/pinkink/bean/FirmwareVersionBean;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public interface FirmwareService {
    @FormUrlEncoded
    @POST("checkDevice")
    Observable<ApiResult<Boolean>> checkFirmwareUIDLegal(@Field("number") String uID);

    @GET("firmware")
    Observable<ApiResult<FirmwareVersionBean>> getFirmwareVersionInfo();
}
