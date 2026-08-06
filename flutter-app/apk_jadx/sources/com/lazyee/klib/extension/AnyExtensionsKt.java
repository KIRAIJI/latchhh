package com.lazyee.klib.extension;

import android.content.res.Resources;
import android.os.Looper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AnyExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\u001a\n\u0010\u0007\u001a\u00020\b*\u00020\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004¨\u0006\t"}, d2 = {"screenHeight", "", "", "getScreenHeight", "(Ljava/lang/Object;)I", "screenWidth", "getScreenWidth", "isMainThread", "", "library_release"}, k = 2, mv = {1, 4, 2})
public final class AnyExtensionsKt {
    public static final boolean isMainThread(Object isMainThread) {
        Intrinsics.checkNotNullParameter(isMainThread, "$this$isMainThread");
        return Intrinsics.areEqual(Looper.getMainLooper(), Looper.myLooper());
    }

    public static final int getScreenWidth(Object screenWidth) {
        Intrinsics.checkNotNullParameter(screenWidth, "$this$screenWidth");
        Resources system = Resources.getSystem();
        Intrinsics.checkNotNullExpressionValue(system, "Resources.getSystem()");
        return system.getDisplayMetrics().widthPixels;
    }

    public static final int getScreenHeight(Object screenHeight) {
        Intrinsics.checkNotNullParameter(screenHeight, "$this$screenHeight");
        Resources system = Resources.getSystem();
        Intrinsics.checkNotNullExpressionValue(system, "Resources.getSystem()");
        return system.getDisplayMetrics().heightPixels;
    }
}
