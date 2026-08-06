package com.shangxian.pinkink.bean;

import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.lazyee.klib.http.IApiResult;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ApiResult.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u000b\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0019\u001a\u00020\u0005H\u0016J\u000f\u0010\u001a\u001a\u0004\u0018\u00018\u0000H\u0016¢\u0006\u0002\u0010\fJ\n\u0010\u001b\u001a\u0004\u0018\u00010\u0005H\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001e\u0010\n\u001a\u0004\u0018\u00018\u0000X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0007\"\u0004\b\u0018\u0010\t¨\u0006\u001c"}, d2 = {"Lcom/shangxian/pinkink/bean/ApiResult;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/lazyee/klib/http/IApiResult;", "()V", "code", "", "getCode", "()Ljava/lang/String;", "setCode", "(Ljava/lang/String;)V", "data", "getData", "()Ljava/lang/Object;", "setData", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "more", "", "getMore", "()Z", "setMore", "(Z)V", NotificationCompat.CATEGORY_MESSAGE, "getMsg", "setMsg", "getICode", "getIData", "getIMsg", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ApiResult<T> implements IApiResult<T> {
    private String code = "";
    private T data;
    private boolean more;
    private String msg;

    public final String getCode() {
        return this.code;
    }

    public final void setCode(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.code = str;
    }

    public final T getData() {
        return this.data;
    }

    public final void setData(T t) {
        this.data = t;
    }

    public final String getMsg() {
        return this.msg;
    }

    public final void setMsg(String str) {
        this.msg = str;
    }

    public final boolean getMore() {
        return this.more;
    }

    public final void setMore(boolean z) {
        this.more = z;
    }

    @Override // com.lazyee.klib.http.IApiResult
    public String getICode() {
        return this.code.toString();
    }

    @Override // com.lazyee.klib.http.IApiResult
    public T getIData() {
        return this.data;
    }

    @Override // com.lazyee.klib.http.IApiResult
    /* JADX INFO: renamed from: getIMsg, reason: from getter */
    public String getMsg() {
        return this.msg;
    }
}
