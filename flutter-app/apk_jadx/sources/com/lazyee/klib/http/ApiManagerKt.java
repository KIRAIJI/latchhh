package com.lazyee.klib.http;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ApiManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001d\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003*\u0001\b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0016\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\t¨\u0006\n"}, d2 = {"TAG", "", "defaultHostnameVerifier", "Ljavax/net/ssl/HostnameVerifier;", "defaultSSLSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "kotlin.jvm.PlatformType", "defaultX509TrustManager", "com/lazyee/klib/http/ApiManagerKt$defaultX509TrustManager$1", "Lcom/lazyee/klib/http/ApiManagerKt$defaultX509TrustManager$1;", "library_release"}, k = 2, mv = {1, 4, 2})
public final class ApiManagerKt {
    private static final String TAG = "[ApiManager]";
    private static final HostnameVerifier defaultHostnameVerifier = new HostnameVerifier() { // from class: com.lazyee.klib.http.ApiManagerKt$defaultHostnameVerifier$1
        @Override // javax.net.ssl.HostnameVerifier
        public final boolean verify(String str, SSLSession sSLSession) {
            return true;
        }
    };
    private static final SSLSocketFactory defaultSSLSocketFactory;
    private static final ApiManagerKt$defaultX509TrustManager$1 defaultX509TrustManager;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.lazyee.klib.http.ApiManagerKt$defaultX509TrustManager$1] */
    static {
        ?? r0 = new X509TrustManager() { // from class: com.lazyee.klib.http.ApiManagerKt$defaultX509TrustManager$1
            @Override // javax.net.ssl.X509TrustManager
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                Intrinsics.checkNotNullParameter(chain, "chain");
                Intrinsics.checkNotNullParameter(authType, "authType");
            }

            @Override // javax.net.ssl.X509TrustManager
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                Intrinsics.checkNotNullParameter(chain, "chain");
                Intrinsics.checkNotNullParameter(authType, "authType");
            }

            @Override // javax.net.ssl.X509TrustManager
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        defaultX509TrustManager = r0;
        SSLContext it = SSLContext.getInstance("SSL");
        it.init(null, new ApiManagerKt$defaultX509TrustManager$1[]{r0}, new SecureRandom());
        Intrinsics.checkNotNullExpressionValue(it, "it");
        defaultSSLSocketFactory = it.getSocketFactory();
    }
}
