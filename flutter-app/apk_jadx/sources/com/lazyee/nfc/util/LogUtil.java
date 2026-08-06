package com.lazyee.nfc.util;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class LogUtil {
    public static String TAG = "ESL";
    private static boolean isDebug = false;

    public static void v(Object obj) {
        if (isDebug) {
            Log.v(TAG, String.valueOf(obj));
        }
    }

    public static void v(String str, Object obj) {
        if (isDebug) {
            Log.v(str, String.valueOf(obj));
        }
    }

    public static void e(Object obj) {
        if (isDebug) {
            Log.e(TAG, String.valueOf(obj));
        }
    }

    public static void e(String str, Object obj) {
        if (isDebug) {
            Log.e(str, String.valueOf(obj));
        }
    }

    public static void d(Object obj) {
        if (isDebug) {
            Log.d(TAG, String.valueOf(obj));
        }
    }

    public static void d(String str, Object obj) {
        if (isDebug) {
            Log.d(str, String.valueOf(obj));
        }
    }

    public static void i(Object obj) {
        if (isDebug) {
            Log.i(TAG, String.valueOf(obj));
        }
    }

    public static void i(String str, Object obj) {
        if (isDebug) {
            Log.i(str, String.valueOf(obj));
        }
    }
}
