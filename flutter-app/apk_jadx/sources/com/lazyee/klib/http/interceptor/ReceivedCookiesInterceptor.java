package com.lazyee.klib.http.interceptor;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Interceptor;
import okhttp3.Response;

/* JADX INFO: compiled from: ReceivedCookiesInterceptor.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lcom/lazyee/klib/http/interceptor/ReceivedCookiesInterceptor;", "Lokhttp3/Interceptor;", "()V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "Companion", "library_release"}, k = 1, mv = {1, 4, 2})
public final class ReceivedCookiesInterceptor implements Interceptor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static String cookie = "";

    /* JADX INFO: compiled from: ReceivedCookiesInterceptor.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/lazyee/klib/http/interceptor/ReceivedCookiesInterceptor$Companion;", "", "()V", "cookie", "", "getCookie", "()Ljava/lang/String;", "setCookie", "(Ljava/lang/String;)V", "library_release"}, k = 1, mv = {1, 4, 2})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String getCookie() {
            return ReceivedCookiesInterceptor.cookie;
        }

        public final void setCookie(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            ReceivedCookiesInterceptor.cookie = str;
        }
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Intrinsics.checkNotNullParameter(chain, "chain");
        Response responseProceed = chain.proceed(chain.request());
        if (!responseProceed.headers("Set-Cookie").isEmpty()) {
            Iterator<String> it = responseProceed.headers("Set-Cookie").iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String next = it.next();
                String str = next;
                if (StringsKt.contains$default((CharSequence) str, (CharSequence) "JSESSIONID", false, 2, (Object) null)) {
                    int iIndexOf$default = StringsKt.indexOf$default((CharSequence) str, "JSESSIONID", 0, false, 6, (Object) null);
                    int iIndexOf$default2 = StringsKt.indexOf$default((CharSequence) str, ";", 0, false, 6, (Object) null);
                    Objects.requireNonNull(next, "null cannot be cast to non-null type java.lang.String");
                    String strSubstring = next.substring(iIndexOf$default, iIndexOf$default2);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    cookie = strSubstring;
                    break;
                }
            }
        }
        return responseProceed;
    }
}
