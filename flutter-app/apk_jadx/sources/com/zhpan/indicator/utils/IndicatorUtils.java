package com.zhpan.indicator.utils;

import android.content.res.Resources;
import com.zhpan.indicator.option.IndicatorOptions;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IndicatorUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\f\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006¨\u0006\r"}, d2 = {"Lcom/zhpan/indicator/utils/IndicatorUtils;", "", "()V", "dp2px", "", "dpValue", "", "getCoordinateX", "indicatorOptions", "Lcom/zhpan/indicator/option/IndicatorOptions;", "maxDiameter", "index", "getCoordinateY", "indicator_release"}, k = 1, mv = {1, 1, 16})
public final class IndicatorUtils {
    public static final IndicatorUtils INSTANCE = new IndicatorUtils();

    public final float getCoordinateY(float maxDiameter) {
        return maxDiameter / 2;
    }

    private IndicatorUtils() {
    }

    @JvmStatic
    public static final int dp2px(float dpValue) {
        Resources system = Resources.getSystem();
        Intrinsics.checkExpressionValueIsNotNull(system, "Resources.getSystem()");
        return (int) ((dpValue * system.getDisplayMetrics().density) + 0.5f);
    }

    public final float getCoordinateX(IndicatorOptions indicatorOptions, float maxDiameter, int index) {
        Intrinsics.checkParameterIsNotNull(indicatorOptions, "indicatorOptions");
        return (maxDiameter / 2) + ((indicatorOptions.getNormalSliderWidth() + indicatorOptions.getSliderGap()) * index);
    }
}
