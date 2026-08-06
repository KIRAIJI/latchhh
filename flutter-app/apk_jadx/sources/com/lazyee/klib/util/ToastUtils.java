package com.lazyee.klib.util;

import android.content.Context;
import android.widget.Toast;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ToastUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J \u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0016\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0016\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\u000f"}, d2 = {"Lcom/lazyee/klib/util/ToastUtils;", "", "()V", "longToast", "", "context", "Landroid/content/Context;", NotificationCompat.CATEGORY_MESSAGE, "", "shortToast", "toast", TypedValues.TransitionType.S_DURATION, "", "toastLong", "toastShort", "library_release"}, k = 1, mv = {1, 4, 2})
public final class ToastUtils {
    public static final ToastUtils INSTANCE = new ToastUtils();

    private ToastUtils() {
    }

    @Deprecated(message = "use toastShort method")
    public final void shortToast(Context context, String msg) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(msg, "msg");
        toast(context, msg, 0);
    }

    public final void toastShort(Context context, String msg) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(msg, "msg");
        toast(context, msg, 0);
    }

    @Deprecated(message = "use toastLong method")
    public final void longToast(Context context, String msg) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(msg, "msg");
        toast(context, msg, 1);
    }

    public final void toastLong(Context context, String msg) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(msg, "msg");
        toast(context, msg, 1);
    }

    private final void toast(Context context, String msg, int duration) {
        Toast.makeText(context, msg, duration).show();
    }
}
