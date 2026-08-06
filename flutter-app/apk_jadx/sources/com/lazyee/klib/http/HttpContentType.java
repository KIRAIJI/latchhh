package com.lazyee.klib.http;

import kotlin.Metadata;

/* JADX INFO: compiled from: HttpContentType.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/lazyee/klib/http/HttpContentType;", "", "()V", "APPLICATION_JSON", "", "APPLICATION_X_WWW_FORM_URLENCODED", "MULTIPART_FORM_DATA", "TEXT_PLAIN", "library_release"}, k = 1, mv = {1, 4, 2})
public final class HttpContentType {
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public static final HttpContentType INSTANCE = new HttpContentType();
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public static final String TEXT_PLAIN = "text/plain";

    private HttpContentType() {
    }
}
