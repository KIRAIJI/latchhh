package com.lazyee.klib.http.interceptor;

import com.lazyee.klib.http.HttpContentType;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ParamsInterceptor.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J4\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\"\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bH\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J4\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\"\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bH\u0002J6\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0007\u001a\u00020\u00062\"\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/lazyee/klib/http/interceptor/HttpParamsInterceptor;", "Lokhttp3/Interceptor;", "paramsProvider", "Lcom/lazyee/klib/http/interceptor/ParamsProvider;", "(Lcom/lazyee/klib/http/interceptor/ParamsProvider;)V", "addHeaderToRequest", "Lokhttp3/Request;", "request", "params", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "setGETRequestParams", "", "builder", "Lokhttp3/HttpUrl$Builder;", "setPOSTRequestParams", "Lokhttp3/RequestBody;", "library_release"}, k = 1, mv = {1, 4, 2})
public final class HttpParamsInterceptor implements Interceptor {
    private final ParamsProvider paramsProvider;

    public HttpParamsInterceptor(ParamsProvider paramsProvider) {
        Intrinsics.checkNotNullParameter(paramsProvider, "paramsProvider");
        this.paramsProvider = paramsProvider;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws JSONException, IOException {
        Request requestBuild;
        Intrinsics.checkNotNullParameter(chain, "chain");
        Request request = chain.request();
        HttpUrl.Builder builderNewBuilder = request.url().newBuilder();
        HashMap<String, String> mapProvideHeader = this.paramsProvider.provideHeader();
        HashMap<String, String> map = mapProvideHeader;
        if (!(map == null || map.isEmpty())) {
            request = addHeaderToRequest(request, mapProvideHeader);
        }
        HashMap<String, String> mapProvideParams = this.paramsProvider.provideParams();
        HashMap<String, String> map2 = mapProvideParams;
        if (map2 == null || map2.isEmpty()) {
            return chain.proceed(request);
        }
        if (Intrinsics.areEqual(request.method(), "POST")) {
            RequestBody pOSTRequestParams = setPOSTRequestParams(request, mapProvideParams);
            if (pOSTRequestParams != null) {
                requestBuild = request.newBuilder().post(pOSTRequestParams).build();
            } else {
                return chain.proceed(request);
            }
        } else {
            setGETRequestParams(builderNewBuilder, mapProvideParams);
            requestBuild = request.newBuilder().url(builderNewBuilder.build()).build();
        }
        return chain.proceed(requestBuild);
    }

    private final Request addHeaderToRequest(Request request, HashMap<String, String> params) {
        Request.Builder builderNewBuilder = request.newBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builderNewBuilder.addHeader(entry.getKey(), entry.getValue());
        }
        return builderNewBuilder.build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final RequestBody setPOSTRequestParams(Request request, HashMap<String, String> params) throws JSONException, IOException {
        if (params.isEmpty()) {
            return request.body();
        }
        RequestBody requestBodyBody = request.body();
        Charset charset = null;
        Object[] objArr = 0;
        String strValueOf = String.valueOf(requestBodyBody != null ? requestBodyBody.getContentType() : null);
        String str = strValueOf;
        if (StringsKt.contains$default((CharSequence) str, (CharSequence) HttpContentType.APPLICATION_X_WWW_FORM_URLENCODED, false, 2, (Object) null)) {
            FormBody.Builder builder = new FormBody.Builder(charset, 1, objArr == true ? 1 : 0);
            RequestBody requestBodyBody2 = request.body();
            Objects.requireNonNull(requestBodyBody2, "null cannot be cast to non-null type okhttp3.FormBody");
            FormBody formBody = (FormBody) requestBodyBody2;
            int size = formBody.size();
            for (int i = 0; i < size; i++) {
                builder.add(formBody.name(i), formBody.value(i));
            }
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            return builder.build();
        }
        if (StringsKt.contains$default((CharSequence) str, (CharSequence) HttpContentType.APPLICATION_JSON, false, 2, (Object) null)) {
            Buffer buffer = new Buffer();
            RequestBody requestBodyBody3 = request.body();
            if (requestBodyBody3 != null) {
                requestBodyBody3.writeTo(buffer);
            }
            JSONObject jSONObject = new JSONObject(buffer.readUtf8());
            for (Map.Entry<String, String> entry2 : params.entrySet()) {
                jSONObject.put(entry2.getKey(), entry2.getValue());
            }
            RequestBody.Companion companion = RequestBody.INSTANCE;
            String string = jSONObject.toString();
            Intrinsics.checkNotNullExpressionValue(string, "json.toString()");
            return companion.create(string, MediaType.INSTANCE.get(strValueOf));
        }
        return request.body();
    }

    private final void setGETRequestParams(HttpUrl.Builder builder, HashMap<String, String> params) {
        if (params.isEmpty()) {
            return;
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.addQueryParameter(entry.getKey(), entry.getValue());
        }
    }
}
