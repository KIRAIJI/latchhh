package com.lazyee.nfc.util;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public class SpUtils {
    private static SharedPreferences sp;

    public static void putBooleanValue(Context context, String str, boolean z) {
        if (sp == null) {
            sp = context.getSharedPreferences("data", 0);
        }
        sp.edit().putBoolean(str, z).apply();
    }

    public static boolean getBooleanValue(Context context, String str, boolean z) {
        if (sp == null) {
            sp = context.getSharedPreferences("data", 0);
        }
        return sp.getBoolean(str, z);
    }

    public static void putStringValue(Context context, String str, String str2) {
        if (sp == null) {
            sp = context.getSharedPreferences("data", 0);
        }
        sp.edit().putString(str, str2).apply();
    }

    public static String getStringValue(Context context, String str, String str2) {
        if (sp == null) {
            sp = context.getSharedPreferences("data", 0);
        }
        return sp.getString(str, str2);
    }

    public static void remove(Context context, String str) {
        if (sp == null) {
            sp = context.getSharedPreferences("data", 0);
        }
        sp.edit().remove(str).apply();
    }

    public static int getIntValue(Context context, String str, int i) {
        if (sp == null) {
            sp = context.getSharedPreferences("data", 0);
        }
        return sp.getInt(str, i);
    }

    public static void putIntValue(Context context, String str, int i) {
        if (sp == null) {
            sp = context.getSharedPreferences("data", 0);
        }
        sp.edit().putInt(str, i).apply();
    }

    public static void clear(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences("data", 0);
        }
        sp.edit().clear().apply();
    }
}
