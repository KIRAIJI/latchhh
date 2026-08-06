package com.lazyee.klib.extension;

import android.content.res.Resources;
import android.util.TypedValue;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NumberExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\u0010\u0004\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0001\u001a\n\u0010\u0000\u001a\u00020\u0002*\u00020\u0002\u001a\n\u0010\u0000\u001a\u00020\u0003*\u00020\u0003\u001a\n\u0010\u0004\u001a\u00020\u0001*\u00020\u0001\u001a\n\u0010\u0004\u001a\u00020\u0002*\u00020\u0002\u001a\n\u0010\u0004\u001a\u00020\u0003*\u00020\u0003\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0001\u001a\n\u0010\u0005\u001a\u00020\u0002*\u00020\u0002\u001a\n\u0010\u0005\u001a\u00020\u0003*\u00020\u0003\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0001\u001a\n\u0010\u0006\u001a\u00020\u0002*\u00020\u0002\u001a\n\u0010\u0006\u001a\u00020\u0003*\u00020\u0003\u001a\f\u0010\u0007\u001a\u00020\b*\u0004\u0018\u00010\t¨\u0006\n"}, d2 = {"dp2px", "", "", "", "px2dp", "px2sp", "sp2px", "toDisplayPrice", "", "", "library_release"}, k = 2, mv = {1, 4, 2})
public final class NumberExtensionsKt {
    public static final float dp2px(float f) {
        Resources system = Resources.getSystem();
        Intrinsics.checkNotNullExpressionValue(system, "Resources.getSystem()");
        return TypedValue.applyDimension(1, f, system.getDisplayMetrics());
    }

    public static final double dp2px(double d) {
        Resources system = Resources.getSystem();
        Intrinsics.checkNotNullExpressionValue(system, "Resources.getSystem()");
        return d * ((double) system.getDisplayMetrics().density);
    }

    public static final int dp2px(int i) {
        Resources system = Resources.getSystem();
        Intrinsics.checkNotNullExpressionValue(system, "Resources.getSystem()");
        return (int) ((i * system.getDisplayMetrics().density) + 0.5f);
    }

    public static final float px2dp(float f) {
        Resources system = Resources.getSystem();
        Intrinsics.checkNotNullExpressionValue(system, "Resources.getSystem()");
        return (f / system.getDisplayMetrics().density) + 0.5f;
    }

    public static final int px2dp(int i) {
        Resources system = Resources.getSystem();
        Intrinsics.checkNotNullExpressionValue(system, "Resources.getSystem()");
        return (int) ((i / system.getDisplayMetrics().density) + 0.5f);
    }

    public static final double px2dp(double d) {
        Resources system = Resources.getSystem();
        Intrinsics.checkNotNullExpressionValue(system, "Resources.getSystem()");
        return (d / ((double) system.getDisplayMetrics().density)) + ((double) 0.5f);
    }

    public static final int px2sp(int i) {
        Resources system = Resources.getSystem();
        Intrinsics.checkNotNullExpressionValue(system, "Resources.getSystem()");
        return (int) ((i / system.getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static final float px2sp(float f) {
        Resources system = Resources.getSystem();
        Intrinsics.checkNotNullExpressionValue(system, "Resources.getSystem()");
        return f / system.getDisplayMetrics().scaledDensity;
    }

    public static final double px2sp(double d) {
        Resources system = Resources.getSystem();
        Intrinsics.checkNotNullExpressionValue(system, "Resources.getSystem()");
        return d / ((double) system.getDisplayMetrics().scaledDensity);
    }

    public static final int sp2px(int i) {
        Resources system = Resources.getSystem();
        Intrinsics.checkNotNullExpressionValue(system, "Resources.getSystem()");
        return (int) ((i * system.getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static final double sp2px(double d) {
        Resources system = Resources.getSystem();
        Intrinsics.checkNotNullExpressionValue(system, "Resources.getSystem()");
        return d * ((double) system.getDisplayMetrics().scaledDensity);
    }

    public static final float sp2px(float f) {
        Resources system = Resources.getSystem();
        Intrinsics.checkNotNullExpressionValue(system, "Resources.getSystem()");
        return f * system.getDisplayMetrics().scaledDensity;
    }

    public static final String toDisplayPrice(Number number) {
        if (number == null) {
            return "";
        }
        double dFloatValue = number instanceof Float ? ((double) ((int) (number.floatValue() * 100))) / 100.0d : number.doubleValue();
        DecimalFormat decimalFormat = new DecimalFormat(dFloatValue % 1.0d > ((double) 0) ? "#,##0.00" : "#,###");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        String str = decimalFormat.format(dFloatValue);
        Intrinsics.checkNotNullExpressionValue(str, "df.format(number)");
        return str;
    }
}
