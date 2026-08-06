package com.lazyee.klib.http.interceptor;

import java.util.HashMap;
import kotlin.Metadata;

/* JADX INFO: compiled from: ParamsInterceptor.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\"\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003j\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u0005H&J(\u0010\u0006\u001a\"\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003j\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u0005H&¨\u0006\u0007"}, d2 = {"Lcom/lazyee/klib/http/interceptor/ParamsProvider;", "", "provideHeader", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "provideParams", "library_release"}, k = 1, mv = {1, 4, 2})
public interface ParamsProvider {
    HashMap<String, String> provideHeader();

    HashMap<String, String> provideParams();
}
