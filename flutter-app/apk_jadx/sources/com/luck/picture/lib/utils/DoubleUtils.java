package com.luck.picture.lib.utils;

import android.os.SystemClock;

/* JADX INFO: loaded from: classes.dex */
public class DoubleUtils {
    private static final long TIME = 600;
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (jElapsedRealtime - lastClickTime < TIME) {
            return true;
        }
        lastClickTime = jElapsedRealtime;
        return false;
    }
}
