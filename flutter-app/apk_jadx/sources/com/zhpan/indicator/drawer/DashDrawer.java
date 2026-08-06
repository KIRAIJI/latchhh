package com.zhpan.indicator.drawer;

import android.graphics.Canvas;
import com.zhpan.indicator.option.IndicatorOptions;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DashDrawer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014¨\u0006\t"}, d2 = {"Lcom/zhpan/indicator/drawer/DashDrawer;", "Lcom/zhpan/indicator/drawer/RectDrawer;", "indicatorOptions", "Lcom/zhpan/indicator/option/IndicatorOptions;", "(Lcom/zhpan/indicator/option/IndicatorOptions;)V", "drawDash", "", "canvas", "Landroid/graphics/Canvas;", "indicator_release"}, k = 1, mv = {1, 1, 16})
public final class DashDrawer extends RectDrawer {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DashDrawer(IndicatorOptions indicatorOptions) {
        super(indicatorOptions);
        Intrinsics.checkParameterIsNotNull(indicatorOptions, "indicatorOptions");
    }

    @Override // com.zhpan.indicator.drawer.RectDrawer
    protected void drawDash(Canvas canvas) {
        Intrinsics.checkParameterIsNotNull(canvas, "canvas");
        canvas.drawRect(getMRectF(), getMPaint());
    }
}
