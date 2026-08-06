package com.lazyee.klib.http.interceptor;

import com.lazyee.klib.http.IApiResult;
import kotlin.Metadata;

/* JADX INFO: compiled from: ApiResultInterceptor.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u00020\u00032\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/lazyee/klib/http/interceptor/ApiResultInterceptor;", "", "intercept", "", "result", "Lcom/lazyee/klib/http/IApiResult;", "library_release"}, k = 1, mv = {1, 4, 2})
public interface ApiResultInterceptor {
    boolean intercept(IApiResult<?> result);
}
