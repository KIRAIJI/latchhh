package com.luck.picture.lib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.luck.picture.lib.config.PictureConfig;

/* JADX INFO: loaded from: classes.dex */
public class SpUtils {
    private static SharedPreferences pictureSpUtils;

    private static SharedPreferences getSp(Context context) {
        if (pictureSpUtils == null) {
            pictureSpUtils = context.getSharedPreferences(PictureConfig.SP_NAME, 0);
        }
        return pictureSpUtils;
    }

    public static void putString(Context context, String str, String str2) {
        getSp(context).edit().putString(str, str2).apply();
    }

    public static void putBoolean(Context context, String str, boolean z) {
        getSp(context).edit().putBoolean(str, z).apply();
    }

    public static boolean getBoolean(Context context, String str, boolean z) {
        return getSp(context).getBoolean(str, z);
    }
}
