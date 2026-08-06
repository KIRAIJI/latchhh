package com.gyf.immersionbar;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;

/* JADX INFO: loaded from: classes.dex */
public class NotchUtils {
    private static final String NOTCH_HUA_WEI = "com.huawei.android.util.HwNotchSizeUtil";
    private static final String NOTCH_LENOVO = "config_screen_has_notch";
    private static final String NOTCH_MEIZU = "flyme.config.FlymeFeature";
    private static final String NOTCH_OPPO = "com.oppo.feature.screen.heteromorphism";
    private static final String NOTCH_VIVO = "android.util.FtFeature";
    private static final String NOTCH_XIAO_MI = "ro.miui.notch";
    private static final String SYSTEM_PROPERTIES = "android.os.SystemProperties";

    public static boolean hasNotchScreen(Activity activity) {
        if (activity == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 28) {
            return hasNotchAtAndroidP(activity);
        }
        return hasNotchAtXiaoMi(activity) || hasNotchAtHuaWei(activity) || hasNotchAtOPPO(activity) || hasNotchAtVIVO(activity) || hasNotchAtLenovo(activity) || hasNotchAtMeiZu();
    }

    public static boolean hasNotchScreen(View view) {
        if (view == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 28) {
            return hasNotchAtAndroidP(view);
        }
        return hasNotchAtXiaoMi(view.getContext()) || hasNotchAtHuaWei(view.getContext()) || hasNotchAtOPPO(view.getContext()) || hasNotchAtVIVO(view.getContext());
    }

    private static boolean hasNotchAtAndroidP(View view) {
        return getDisplayCutout(view) != null;
    }

    private static boolean hasNotchAtAndroidP(Activity activity) {
        return getDisplayCutout(activity) != null;
    }

    private static DisplayCutout getDisplayCutout(Activity activity) {
        return getDisplayCutout(activity.getWindow().getDecorView());
    }

    private static DisplayCutout getDisplayCutout(View view) {
        WindowInsets rootWindowInsets;
        if (Build.VERSION.SDK_INT < 28 || view == null || (rootWindowInsets = view.getRootWindowInsets()) == null) {
            return null;
        }
        return rootWindowInsets.getDisplayCutout();
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static boolean hasNotchAtXiaoMi(android.content.Context r6) {
        /*
            boolean r0 = com.gyf.immersionbar.OSUtils.isXiaoMi()
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L3c
            java.lang.ClassLoader r6 = r6.getClassLoader()     // Catch: java.lang.Throwable -> L3c
            java.lang.String r0 = "android.os.SystemProperties"
            java.lang.Class r6 = r6.loadClass(r0)     // Catch: java.lang.Throwable -> L3c
            java.lang.String r0 = "getInt"
            r3 = 2
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch: java.lang.Throwable -> L3c
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            r4[r2] = r5     // Catch: java.lang.Throwable -> L3c
            java.lang.Class r5 = java.lang.Integer.TYPE     // Catch: java.lang.Throwable -> L3c
            r4[r1] = r5     // Catch: java.lang.Throwable -> L3c
            java.lang.reflect.Method r0 = r6.getMethod(r0, r4)     // Catch: java.lang.Throwable -> L3c
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.Throwable -> L3c
            java.lang.String r4 = "ro.miui.notch"
            r3[r2] = r4     // Catch: java.lang.Throwable -> L3c
            java.lang.Integer r4 = java.lang.Integer.valueOf(r2)     // Catch: java.lang.Throwable -> L3c
            r3[r1] = r4     // Catch: java.lang.Throwable -> L3c
            java.lang.Object r6 = r0.invoke(r6, r3)     // Catch: java.lang.Throwable -> L3c
            if (r6 == 0) goto L3c
            java.lang.Integer r6 = (java.lang.Integer) r6     // Catch: java.lang.Throwable -> L3c
            int r6 = r6.intValue()     // Catch: java.lang.Throwable -> L3c
            goto L3d
        L3c:
            r6 = r2
        L3d:
            if (r6 != r1) goto L40
            goto L41
        L40:
            r1 = r2
        L41:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gyf.immersionbar.NotchUtils.hasNotchAtXiaoMi(android.content.Context):boolean");
    }

    private static boolean hasNotchAtHuaWei(Context context) {
        if (!OSUtils.isHuaWei()) {
            return false;
        }
        try {
            Class<?> clsLoadClass = context.getClassLoader().loadClass(NOTCH_HUA_WEI);
            return ((Boolean) clsLoadClass.getMethod("hasNotchInScreen", new Class[0]).invoke(clsLoadClass, new Object[0])).booleanValue();
        } catch (ClassNotFoundException | NoSuchMethodException | Exception unused) {
            return false;
        }
    }

    private static boolean hasNotchAtVIVO(Context context) {
        if (!OSUtils.isVivo()) {
            return false;
        }
        try {
            Class<?> clsLoadClass = context.getClassLoader().loadClass(NOTCH_VIVO);
            return ((Boolean) clsLoadClass.getMethod("isFeatureSupport", Integer.TYPE).invoke(clsLoadClass, 32)).booleanValue();
        } catch (ClassNotFoundException | NoSuchMethodException | Exception unused) {
            return false;
        }
    }

    private static boolean hasNotchAtOPPO(Context context) {
        if (OSUtils.isOppo()) {
            try {
                return context.getPackageManager().hasSystemFeature(NOTCH_OPPO);
            } catch (Exception unused) {
            }
        }
        return false;
    }

    private static boolean hasNotchAtLenovo(Context context) {
        int identifier;
        if (!OSUtils.isLenovo() || (identifier = context.getResources().getIdentifier(NOTCH_LENOVO, "bool", "android")) <= 0) {
            return false;
        }
        return context.getResources().getBoolean(identifier);
    }

    private static boolean hasNotchAtMeiZu() {
        if (OSUtils.isMeizu()) {
            try {
                return ((Boolean) Class.forName(NOTCH_MEIZU).getDeclaredField("IS_FRINGE_DEVICE").get(null)).booleanValue();
            } catch (Exception unused) {
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x006a A[PHI: r1
  0x006a: PHI (r1v4 int) = (r1v3 int), (r1v7 int) binds: [B:27:0x0063, B:29:0x0067] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int getNotchHeight(android.app.Activity r6) {
        /*
            boolean r0 = hasNotchScreen(r6)
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            int r0 = com.gyf.immersionbar.ImmersionBar.getStatusBarHeight(r6)
            android.view.DisplayCutout r2 = getDisplayCutout(r6)
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 28
            r5 = 1
            if (r3 < r4) goto L3a
            if (r2 == 0) goto L3a
            android.content.res.Resources r6 = r6.getResources()
            android.content.res.Configuration r6 = r6.getConfiguration()
            int r6 = r6.orientation
            if (r6 != r5) goto L2a
            int r6 = r2.getSafeInsetTop()
            goto L81
        L2a:
            int r6 = r2.getSafeInsetLeft()
            if (r6 != 0) goto L35
            int r6 = r2.getSafeInsetRight()
            goto L81
        L35:
            int r6 = r2.getSafeInsetLeft()
            goto L81
        L3a:
            boolean r2 = hasNotchAtXiaoMi(r6)
            if (r2 == 0) goto L44
            int r1 = getXiaoMiNotchHeight(r6)
        L44:
            boolean r2 = hasNotchAtHuaWei(r6)
            if (r2 == 0) goto L50
            int[] r1 = getHuaWeiNotchSize(r6)
            r1 = r1[r5]
        L50:
            boolean r2 = hasNotchAtVIVO(r6)
            if (r2 == 0) goto L5f
            r1 = 32
            int r1 = dp2px(r6, r1)
            if (r1 >= r0) goto L5f
            r1 = r0
        L5f:
            boolean r2 = hasNotchAtOPPO(r6)
            if (r2 == 0) goto L6a
            r1 = 80
            if (r1 >= r0) goto L6a
            goto L6b
        L6a:
            r0 = r1
        L6b:
            boolean r1 = hasNotchAtLenovo(r6)
            if (r1 == 0) goto L75
            int r0 = getLenovoNotchHeight(r6)
        L75:
            boolean r1 = hasNotchAtMeiZu()
            if (r1 == 0) goto L80
            int r6 = getMeizuNotchHeight(r6)
            goto L81
        L80:
            r6 = r0
        L81:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gyf.immersionbar.NotchUtils.getNotchHeight(android.app.Activity):int");
    }

    public static void getNotchHeight(final Activity activity, final NotchCallback notchCallback) {
        activity.getWindow().getDecorView().post(new Runnable() { // from class: com.gyf.immersionbar.NotchUtils.1
            @Override // java.lang.Runnable
            public void run() {
                NotchCallback notchCallback2 = notchCallback;
                if (notchCallback2 != null) {
                    notchCallback2.onNotchHeight(NotchUtils.getNotchHeight(activity));
                }
            }
        });
    }

    private static int getXiaoMiNotchHeight(Context context) {
        int identifier = context.getResources().getIdentifier("notch_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    private static int[] getHuaWeiNotchSize(Context context) {
        int[] iArr = {0, 0};
        try {
            Class<?> clsLoadClass = context.getClassLoader().loadClass(NOTCH_HUA_WEI);
            return (int[]) clsLoadClass.getMethod("getNotchSize", new Class[0]).invoke(clsLoadClass, new Object[0]);
        } catch (ClassNotFoundException | NoSuchMethodException | Exception unused) {
            return iArr;
        }
    }

    private static int getLenovoNotchHeight(Context context) {
        int identifier = context.getResources().getIdentifier("notch_h", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    private static int getMeizuNotchHeight(Context context) {
        int identifier = context.getResources().getIdentifier("fringe_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    private static int dp2px(Context context, int i) {
        return (int) TypedValue.applyDimension(1, i, context.getResources().getDisplayMetrics());
    }
}
