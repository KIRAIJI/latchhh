package com.zhpan.bannerview.utils;

import android.content.res.Resources;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class BannerUtils {
    private static final String TAG = "BVP";
    private static boolean debugMode = false;

    public static void setDebugMode(boolean z) {
        debugMode = z;
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static int dp2px(float f) {
        return (int) ((f * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    public static void log(String str, String str2) {
        if (isDebugMode()) {
            Log.e(str, str2);
        }
    }

    public static void log(String str) {
        if (isDebugMode()) {
            log(TAG, str);
        }
    }

    public static int getRealPosition(int i, int i2) {
        if (i2 == 0) {
            return 0;
        }
        return (i + i2) % i2;
    }

    public static int getOriginalPosition(int i) {
        return 500 - (500 % i);
    }
}
