package com.lazyee.klib.http;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;

/* JADX INFO: compiled from: IApiResult.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\u000f\u0010\u0005\u001a\u0004\u0018\u00018\u0000H&¢\u0006\u0002\u0010\u0006J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0004H&¨\u0006\b"}, d2 = {"Lcom/lazyee/klib/http/IApiResult;", ExifInterface.GPS_DIRECTION_TRUE, "", "getICode", "", "getIData", "()Ljava/lang/Object;", "getIMsg", "library_release"}, k = 1, mv = {1, 4, 2})
public interface IApiResult<T> {
    String getICode();

    T getIData();

    String getIMsg();
}
