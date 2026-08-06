package com.lazyee.klib.util;

import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.lazyee.klib.log.LogLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LogUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0001J\u001a\u0010\n\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0001J\u0017\u0010\u000b\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0001H\u0000¢\u0006\u0002\b\fJ\u0017\u0010\r\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0000¢\u0006\u0002\b\u000eJ\u001a\u0010\u000f\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0001J\u000e\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0004J%\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\bH\u0000¢\u0006\u0002\b\u0015J\u001a\u0010\u0016\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0001J\u001a\u0010\u0017\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0001R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/lazyee/klib/util/LogUtils;", "", "()V", "isDebug", "", "d", "", "tag", "", "any", "e", "getMsg", "getMsg$library_release", "getTag", "getTag$library_release", "i", "init", "log", "level", "Lcom/lazyee/klib/log/LogLevel;", NotificationCompat.CATEGORY_MESSAGE, "log$library_release", "v", "w", "library_release"}, k = 1, mv = {1, 4, 2})
public final class LogUtils {
    public static final LogUtils INSTANCE = new LogUtils();
    private static boolean isDebug = true;

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 2})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LogLevel.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[LogLevel.W.ordinal()] = 1;
            iArr[LogLevel.I.ordinal()] = 2;
            iArr[LogLevel.E.ordinal()] = 3;
            iArr[LogLevel.V.ordinal()] = 4;
            iArr[LogLevel.D.ordinal()] = 5;
        }
    }

    private LogUtils() {
    }

    public final void init(boolean isDebug2) {
        isDebug = isDebug2;
    }

    public final void d(String tag, Object any) {
        log$library_release(LogLevel.D, getTag$library_release(tag), getMsg$library_release(any));
    }

    public final void e(String tag, Object any) {
        log$library_release(LogLevel.E, getTag$library_release(tag), getMsg$library_release(any));
    }

    public final void i(String tag, Object any) {
        log$library_release(LogLevel.I, getTag$library_release(tag), getMsg$library_release(any));
    }

    public final void w(String tag, Object any) {
        log$library_release(LogLevel.W, getTag$library_release(tag), getMsg$library_release(any));
    }

    public final void v(String tag, Object any) {
        log$library_release(LogLevel.V, getTag$library_release(tag), getMsg$library_release(any));
    }

    public final String getTag$library_release(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return "[TAG]";
        }
        Intrinsics.checkNotNull(tag);
        return tag;
    }

    public final String getMsg$library_release(Object any) {
        return any == null ? "[null]" : any instanceof String ? (String) any : any.toString();
    }

    public final void log$library_release(LogLevel level, String tag, String msg) {
        Intrinsics.checkNotNullParameter(level, "level");
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(msg, "msg");
        if (isDebug) {
            int i = WhenMappings.$EnumSwitchMapping$0[level.ordinal()];
            if (i == 1) {
                Log.w(tag, msg);
                return;
            }
            if (i == 2) {
                Log.i(tag, msg);
                return;
            }
            if (i == 3) {
                Log.e(tag, msg);
            } else if (i == 4) {
                Log.v(tag, msg);
            } else {
                if (i != 5) {
                    return;
                }
                Log.d(tag, msg);
            }
        }
    }
}
