package com.shangxian.pinkink.api;

import com.shangxian.pinkink.bean.ApiResult;
import com.shangxian.pinkink.bean.UpdateAvatarResultBean;
import com.shangxian.pinkink.bean.UserInfoBean;
import io.reactivex.Observable;
import kotlin.Metadata;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/* JADX INFO: compiled from: UserService.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u0005H'J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H'J\u001e\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0001\u0010\n\u001a\u00020\u0005H'J\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00040\u0003H'J\u0014\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H'J<\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0001\u0010\u000f\u001a\u00020\u00052\b\b\u0001\u0010\u0010\u001a\u00020\u00052\b\b\u0001\u0010\u0011\u001a\u00020\u00052\b\b\u0001\u0010\u0012\u001a\u00020\u0005H'J\u001e\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00040\u00032\b\b\u0001\u0010\u0015\u001a\u00020\u0005H'J\u001e\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0001\u0010\u0017\u001a\u00020\u0005H'¨\u0006\u0018"}, d2 = {"Lcom/shangxian/pinkink/api/UserService;", "", "cancelUser", "Lio/reactivex/Observable;", "Lcom/shangxian/pinkink/bean/ApiResult;", "", "imgCode", "getCancelUserImageCode", "Lokhttp3/ResponseBody;", "getLoginVerifyCode", "account", "getUserInfo", "Lcom/shangxian/pinkink/bean/UserInfoBean;", "logout", "phoneLogin", "phone", "verifyCode", "os", "pushId", "updateAvatar", "Lcom/shangxian/pinkink/bean/UpdateAvatarResultBean;", "avatarBase64", "updateNickName", "nickName", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public interface UserService {
    @GET("logoff")
    Observable<ApiResult<String>> cancelUser(@Query("imgCode") String imgCode);

    @GET("imgCode")
    Observable<ResponseBody> getCancelUserImageCode();

    @GET("captcha")
    Observable<ApiResult<String>> getLoginVerifyCode(@Query("mobile") String account);

    @GET("member")
    Observable<ApiResult<UserInfoBean>> getUserInfo();

    @GET("logout")
    Observable<ApiResult<String>> logout();

    @FormUrlEncoded
    @POST("phoneLogin")
    Observable<ApiResult<String>> phoneLogin(@Field("mobile") String phone, @Field("captcha") String verifyCode, @Field("os") String os, @Field("pushId") String pushId);

    @FormUrlEncoded
    @POST("member/face")
    Observable<ApiResult<UpdateAvatarResultBean>> updateAvatar(@Field("face") String avatarBase64);

    @FormUrlEncoded
    @POST("member/save")
    Observable<ApiResult<String>> updateNickName(@Field("nickname") String nickName);
}
