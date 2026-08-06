package com.gyf.immersionbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import com.gyf.immersionbar.GestureUtils;

/* JADX INFO: loaded from: classes.dex */
class BarConfig {
    private final int mActionBarHeight;
    private final boolean mHasNavigationBar;
    private final boolean mInPortrait;
    private final int mNavigationBarHeight;
    private final int mNavigationBarWidth;
    private final float mSmallestWidthDp;
    private final int mStatusBarHeight;

    BarConfig(Activity activity) {
        this.mInPortrait = activity.getResources().getConfiguration().orientation == 1;
        this.mSmallestWidthDp = getSmallestWidthDp(activity);
        this.mStatusBarHeight = getInternalDimensionSize(activity, "status_bar_height");
        this.mActionBarHeight = getActionBarHeight(activity);
        int navigationBarHeight = getNavigationBarHeight(activity);
        this.mNavigationBarHeight = navigationBarHeight;
        this.mNavigationBarWidth = getNavigationBarWidth(activity);
        this.mHasNavigationBar = navigationBarHeight > 0;
    }

    private int getActionBarHeight(Activity activity) {
        if (Build.VERSION.SDK_INT < 14) {
            return 0;
        }
        View viewFindViewById = activity.getWindow().findViewById(R.id.action_bar_container);
        int measuredHeight = viewFindViewById != null ? viewFindViewById.getMeasuredHeight() : 0;
        if (measuredHeight != 0) {
            return measuredHeight;
        }
        TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true);
        return TypedValue.complexToDimensionPixelSize(typedValue.data, activity.getResources().getDisplayMetrics());
    }

    private int getNavigationBarHeight(Context context) {
        if (Build.VERSION.SDK_INT < 14 || !hasNavBar((Activity) context)) {
            return 0;
        }
        return getNavigationBarHeightInternal(context);
    }

    private int getNavigationBarWidth(Context context) {
        if (Build.VERSION.SDK_INT < 14 || !hasNavBar((Activity) context)) {
            return 0;
        }
        return getInternalDimensionSize(context, "navigation_bar_width");
    }

    private boolean hasNavBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= 17) {
            GestureUtils.GestureBean gestureBean = GestureUtils.getGestureBean(activity);
            if (!gestureBean.checkNavigation && gestureBean.isGesture) {
                return false;
            }
        }
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealMetrics(displayMetrics);
        }
        int i = displayMetrics.heightPixels;
        int i2 = displayMetrics.widthPixels;
        DisplayMetrics displayMetrics2 = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics2);
        return i2 - displayMetrics2.widthPixels > 0 || i - displayMetrics2.heightPixels > 0;
    }

    static int getInternalDimensionSize(Context context, String str) {
        try {
            int identifier = Resources.getSystem().getIdentifier(str, "dimen", "android");
            if (identifier > 0) {
                int dimensionPixelSize = context.getResources().getDimensionPixelSize(identifier);
                int dimensionPixelSize2 = Resources.getSystem().getDimensionPixelSize(identifier);
                if (dimensionPixelSize2 >= dimensionPixelSize && (Build.VERSION.SDK_INT < 29 || str.equals("status_bar_height"))) {
                    return dimensionPixelSize2;
                }
                float f = (dimensionPixelSize * Resources.getSystem().getDisplayMetrics().density) / context.getResources().getDisplayMetrics().density;
                return (int) (f >= 0.0f ? f + 0.5f : f - 0.5f);
            }
        } catch (Resources.NotFoundException unused) {
        }
        return 0;
    }

    private float getSmallestWidthDp(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 16) {
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        } else {
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }
        return Math.min(displayMetrics.widthPixels / displayMetrics.density, displayMetrics.heightPixels / displayMetrics.density);
    }

    boolean isNavigationAtBottom() {
        return this.mSmallestWidthDp >= 600.0f || this.mInPortrait;
    }

    int getStatusBarHeight() {
        return this.mStatusBarHeight;
    }

    int getActionBarHeight() {
        return this.mActionBarHeight;
    }

    boolean hasNavigationBar() {
        return this.mHasNavigationBar;
    }

    int getNavigationBarHeight() {
        return this.mNavigationBarHeight;
    }

    int getNavigationBarWidth() {
        return this.mNavigationBarWidth;
    }

    static int getNavigationBarHeightInternal(Context context) {
        return getInternalDimensionSize(context, context.getResources().getConfiguration().orientation == 1 ? "navigation_bar_height" : "navigation_bar_height_landscape");
    }

    static int getNavigationBarWidthInternal(Context context) {
        return getInternalDimensionSize(context, "navigation_bar_width");
    }
}
