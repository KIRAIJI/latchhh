package com.shangxian.pinkink.api;

import android.app.Activity;
import android.util.Log;
import com.lazyee.klib.app.AppManager;
import com.lazyee.klib.extension.ContextExtensionsKt;
import com.lazyee.klib.http.ApiManager;
import com.lazyee.klib.http.IApiResult;
import com.lazyee.klib.http.interceptor.ApiResultInterceptor;
import com.lazyee.klib.http.interceptor.ParamsProvider;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.ui.login.LoginActivity;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Api.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/shangxian/pinkink/api/Api;", "", "()V", "Companion", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class Api {
    private static ApiManager apiManager;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final HashMap<String, String> httpHeader = new HashMap<>();
    private static final Api$Companion$paramsProvider$1 paramsProvider = new ParamsProvider() { // from class: com.shangxian.pinkink.api.Api$Companion$paramsProvider$1
        @Override // com.lazyee.klib.http.interceptor.ParamsProvider
        public HashMap<String, String> provideParams() {
            return null;
        }

        @Override // com.lazyee.klib.http.interceptor.ParamsProvider
        public HashMap<String, String> provideHeader() {
            Api.httpHeader.put("token", AppConfig.INSTANCE.getToken());
            Log.e("TAG", Intrinsics.stringPlus("httpHeader:", Api.httpHeader));
            return Api.httpHeader;
        }
    };
    private static final Api$Companion$apiResultInterceptor$1 apiResultInterceptor = new ApiResultInterceptor() { // from class: com.shangxian.pinkink.api.Api$Companion$apiResultInterceptor$1
        @Override // com.lazyee.klib.http.interceptor.ApiResultInterceptor
        public boolean intercept(IApiResult<?> result) throws Exception {
            Intrinsics.checkNotNullParameter(result, "result");
            if (!Intrinsics.areEqual(result.getICode(), "401")) {
                return false;
            }
            AppConfig.INSTANCE.setToken("");
            Activity foregroundActivity = AppManager.INSTANCE.getForegroundActivity();
            if (foregroundActivity != null) {
                ContextExtensionsKt.toastShort(foregroundActivity, "您的登录已失效，请重新登录");
            }
            Activity foregroundActivity2 = AppManager.INSTANCE.getForegroundActivity();
            if (foregroundActivity2 != null) {
                ContextExtensionsKt.goto$default(foregroundActivity2, LoginActivity.class, null, null, null, null, 30, null);
            }
            AppManager.INSTANCE.finishAllExcept(LoginActivity.class);
            return true;
        }
    };

    /* JADX INFO: compiled from: Api.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002*\u0002\u0006\r\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000f\u001a\u00020\u0004J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0007R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000e¨\u0006\u0013"}, d2 = {"Lcom/shangxian/pinkink/api/Api$Companion;", "", "()V", "apiManager", "Lcom/lazyee/klib/http/ApiManager;", "apiResultInterceptor", "com/shangxian/pinkink/api/Api$Companion$apiResultInterceptor$1", "Lcom/shangxian/pinkink/api/Api$Companion$apiResultInterceptor$1;", "httpHeader", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "paramsProvider", "com/shangxian/pinkink/api/Api$Companion$paramsProvider$1", "Lcom/shangxian/pinkink/api/Api$Companion$paramsProvider$1;", "getInstance", "init", "", "baseUrl", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ApiManager getInstance() {
            ApiManager apiManager = Api.apiManager;
            Intrinsics.checkNotNull(apiManager);
            return apiManager;
        }

        public final void init(String baseUrl) {
            Intrinsics.checkNotNullParameter(baseUrl, "baseUrl");
            Api.apiManager = new ApiManager.Builder().baseUrl(baseUrl).setParamsProvider(Api.paramsProvider).setSupportCookie(true, "cookie").addApiResultInterceptor(Api.apiResultInterceptor).build();
        }
    }
}
