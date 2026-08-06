package com.lazyee.klib.http.interceptor;

import android.text.TextUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: compiled from: AddCookiesInterceptor.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lcom/lazyee/klib/http/interceptor/AddCookiesInterceptor;", "Lokhttp3/Interceptor;", "()V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "Companion", "library_release"}, k = 1, mv = {1, 4, 2})
public final class AddCookiesInterceptor implements Interceptor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static String cookieKeyName = "Cookie";

    /* JADX INFO: compiled from: AddCookiesInterceptor.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/lazyee/klib/http/interceptor/AddCookiesInterceptor$Companion;", "", "()V", "cookieKeyName", "", "getCookieKeyName", "()Ljava/lang/String;", "setCookieKeyName", "(Ljava/lang/String;)V", "library_release"}, k = 1, mv = {1, 4, 2})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String getCookieKeyName() {
            return AddCookiesInterceptor.cookieKeyName;
        }

        public final void setCookieKeyName(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            AddCookiesInterceptor.cookieKeyName = str;
        }
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) {
        Intrinsics.checkNotNullParameter(chain, "chain");
        Request.Builder builderNewBuilder = chain.request().newBuilder();
        if (!TextUtils.isEmpty(ReceivedCookiesInterceptor.INSTANCE.getCookie())) {
            builderNewBuilder.addHeader(cookieKeyName, ReceivedCookiesInterceptor.INSTANCE.getCookie());
        }
        return chain.proceed(builderNewBuilder.build());
    }
}
