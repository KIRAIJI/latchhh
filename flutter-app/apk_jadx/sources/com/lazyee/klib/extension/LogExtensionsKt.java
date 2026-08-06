package com.lazyee.klib.extension;

import android.content.Context;
import com.lazyee.klib.base.BaseActivity;
import com.lazyee.klib.base.BaseFragment;
import com.lazyee.klib.util.LogUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LogExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00072\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u001e\u0010\t\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u0014\u0010\t\u001a\u00020\u0001*\u00020\u00072\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u0014\u0010\t\u001a\u00020\u0001*\u00020\b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u001e\u0010\n\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u0014\u0010\n\u001a\u00020\u0001*\u00020\u00072\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u0014\u0010\n\u001a\u00020\u0001*\u00020\b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u001e\u0010\u000b\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u0014\u0010\u000b\u001a\u00020\u0001*\u00020\u00072\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u0014\u0010\u000b\u001a\u00020\u0001*\u00020\b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u001e\u0010\f\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u0014\u0010\f\u001a\u00020\u0001*\u00020\u00072\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u0014\u0010\f\u001a\u00020\u0001*\u00020\b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¨\u0006\r"}, d2 = {"d", "", "Landroid/content/Context;", "tag", "", "any", "", "Lcom/lazyee/klib/base/BaseActivity;", "Lcom/lazyee/klib/base/BaseFragment;", "e", "i", "v", "w", "library_release"}, k = 2, mv = {1, 4, 2})
public final class LogExtensionsKt {
    public static final void e(BaseActivity e, Object obj) {
        Intrinsics.checkNotNullParameter(e, "$this$e");
        LogUtils.INSTANCE.e(e.getTAG(), obj);
    }

    public static final void i(BaseActivity i, Object obj) {
        Intrinsics.checkNotNullParameter(i, "$this$i");
        LogUtils.INSTANCE.i(i.getTAG(), obj);
    }

    public static final void d(BaseActivity d, Object obj) {
        Intrinsics.checkNotNullParameter(d, "$this$d");
        LogUtils.INSTANCE.d(d.getTAG(), obj);
    }

    public static final void v(BaseActivity v, Object obj) {
        Intrinsics.checkNotNullParameter(v, "$this$v");
        LogUtils.INSTANCE.v(v.getTAG(), obj);
    }

    public static final void w(BaseActivity w, Object obj) {
        Intrinsics.checkNotNullParameter(w, "$this$w");
        LogUtils.INSTANCE.w(w.getTAG(), obj);
    }

    public static final void e(BaseFragment e, Object obj) {
        Intrinsics.checkNotNullParameter(e, "$this$e");
        LogUtils.INSTANCE.e(e.getTAG(), obj);
    }

    public static final void i(BaseFragment i, Object obj) {
        Intrinsics.checkNotNullParameter(i, "$this$i");
        LogUtils.INSTANCE.i(i.getTAG(), obj);
    }

    public static final void d(BaseFragment d, Object obj) {
        Intrinsics.checkNotNullParameter(d, "$this$d");
        LogUtils.INSTANCE.d(d.getTAG(), obj);
    }

    public static final void v(BaseFragment v, Object obj) {
        Intrinsics.checkNotNullParameter(v, "$this$v");
        LogUtils.INSTANCE.v(v.getTAG(), obj);
    }

    public static final void w(BaseFragment w, Object obj) {
        Intrinsics.checkNotNullParameter(w, "$this$w");
        LogUtils.INSTANCE.w(w.getTAG(), obj);
    }

    public static final void e(Context e, String str, Object obj) {
        Intrinsics.checkNotNullParameter(e, "$this$e");
        LogUtils.INSTANCE.e(str, obj);
    }

    public static final void i(Context i, String str, Object obj) {
        Intrinsics.checkNotNullParameter(i, "$this$i");
        LogUtils.INSTANCE.i(str, obj);
    }

    public static final void d(Context d, String str, Object obj) {
        Intrinsics.checkNotNullParameter(d, "$this$d");
        LogUtils.INSTANCE.d(str, obj);
    }

    public static final void v(Context v, String str, Object obj) {
        Intrinsics.checkNotNullParameter(v, "$this$v");
        LogUtils.INSTANCE.v(str, obj);
    }

    public static final void w(Context w, String str, Object obj) {
        Intrinsics.checkNotNullParameter(w, "$this$w");
        LogUtils.INSTANCE.w(str, obj);
    }
}
