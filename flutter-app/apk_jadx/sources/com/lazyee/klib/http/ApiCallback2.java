package com.lazyee.klib.http;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;

/* JADX INFO: compiled from: ApiCallback.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002J\u0017\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00018\u0000H&¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/lazyee/klib/http/ApiCallback2;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/lazyee/klib/http/ApiCallback;", "onFailure", "", "result", "(Ljava/lang/Object;)V", "library_release"}, k = 1, mv = {1, 4, 2})
public interface ApiCallback2<T> extends ApiCallback<T> {
    void onFailure(T result);
}
