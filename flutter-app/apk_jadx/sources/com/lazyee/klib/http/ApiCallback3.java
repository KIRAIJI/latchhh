package com.lazyee.klib.http;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;

/* JADX INFO: compiled from: ApiCallback.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u0007"}, d2 = {"Lcom/lazyee/klib/http/ApiCallback3;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/lazyee/klib/http/ApiCallback2;", "onRequestFailure", "", "throwable", "", "library_release"}, k = 1, mv = {1, 4, 2})
public interface ApiCallback3<T> extends ApiCallback2<T> {
    void onRequestFailure(Throwable throwable);
}
