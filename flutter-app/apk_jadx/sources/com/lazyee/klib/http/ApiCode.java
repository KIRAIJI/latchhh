package com.lazyee.klib.http;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ApiCode.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005J\u0006\u0010\n\u001a\u00020\bJ\u001a\u0010\u000b\u001a\u00020\f\"\u0004\b\u0000\u0010\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\r0\u000fJ\u0010\u0010\u000b\u001a\u00020\f2\b\u0010\t\u001a\u0004\u0018\u00010\u0005R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/lazyee/klib/http/ApiCode;", "", "()V", "apiSuccessCodeList", "", "", "defaultSuccessCode", "addSuccessCode", "", "code", "clearSuccessCode", "isSuccessful", "", ExifInterface.GPS_DIRECTION_TRUE, "apiResult", "Lcom/lazyee/klib/http/IApiResult;", "library_release"}, k = 1, mv = {1, 4, 2})
public final class ApiCode {
    public static final ApiCode INSTANCE = new ApiCode();
    private static final List<String> apiSuccessCodeList;
    public static final String defaultSuccessCode = "200";

    static {
        ArrayList arrayList = new ArrayList();
        arrayList.add(defaultSuccessCode);
        apiSuccessCodeList = arrayList;
    }

    private ApiCode() {
    }

    public final boolean isSuccessful(String code) {
        Object next;
        Iterator<T> it = apiSuccessCodeList.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (Intrinsics.areEqual((String) next, code)) {
                break;
            }
        }
        return next != null;
    }

    public final <T> boolean isSuccessful(IApiResult<T> apiResult) {
        T next;
        Intrinsics.checkNotNullParameter(apiResult, "apiResult");
        Iterator<T> it = apiSuccessCodeList.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = (T) null;
                break;
            }
            next = it.next();
            if (Intrinsics.areEqual((String) next, apiResult.getICode())) {
                break;
            }
        }
        return next != null;
    }

    public final void addSuccessCode(String code) {
        Intrinsics.checkNotNullParameter(code, "code");
        apiSuccessCodeList.add(code);
    }

    public final void clearSuccessCode() {
        List<String> list = apiSuccessCodeList;
        list.clear();
        list.add(defaultSuccessCode);
    }
}
