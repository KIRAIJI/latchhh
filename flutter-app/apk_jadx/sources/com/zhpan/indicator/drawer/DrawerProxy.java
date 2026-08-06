package com.zhpan.indicator.drawer;

import android.graphics.Canvas;
import com.zhpan.indicator.drawer.BaseDrawer;
import com.zhpan.indicator.option.IndicatorOptions;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DrawerProxy.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nH\u0016J0\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000fH\u0016J\u001c\u0010\u0013\u001a\u00060\u0014R\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000fH\u0016J\u000e\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u0003R\u000e\u0010\u0005\u001a\u00020\u0001X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/zhpan/indicator/drawer/DrawerProxy;", "Lcom/zhpan/indicator/drawer/IDrawer;", "indicatorOptions", "Lcom/zhpan/indicator/option/IndicatorOptions;", "(Lcom/zhpan/indicator/option/IndicatorOptions;)V", "mIDrawer", "init", "", "onDraw", "canvas", "Landroid/graphics/Canvas;", "onLayout", "changed", "", "left", "", "top", "right", "bottom", "onMeasure", "Lcom/zhpan/indicator/drawer/BaseDrawer$MeasureResult;", "Lcom/zhpan/indicator/drawer/BaseDrawer;", "widthMeasureSpec", "heightMeasureSpec", "setIndicatorOptions", "indicator_release"}, k = 1, mv = {1, 1, 16})
public final class DrawerProxy implements IDrawer {
    private IDrawer mIDrawer;

    @Override // com.zhpan.indicator.drawer.IDrawer
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    public DrawerProxy(IndicatorOptions indicatorOptions) {
        Intrinsics.checkParameterIsNotNull(indicatorOptions, "indicatorOptions");
        init(indicatorOptions);
    }

    private final void init(IndicatorOptions indicatorOptions) {
        this.mIDrawer = DrawerFactory.INSTANCE.createDrawer(indicatorOptions);
    }

    @Override // com.zhpan.indicator.drawer.IDrawer
    public BaseDrawer.MeasureResult onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        IDrawer iDrawer = this.mIDrawer;
        if (iDrawer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mIDrawer");
        }
        return iDrawer.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override // com.zhpan.indicator.drawer.IDrawer
    public void onDraw(Canvas canvas) {
        Intrinsics.checkParameterIsNotNull(canvas, "canvas");
        IDrawer iDrawer = this.mIDrawer;
        if (iDrawer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mIDrawer");
        }
        iDrawer.onDraw(canvas);
    }

    public final void setIndicatorOptions(IndicatorOptions indicatorOptions) {
        Intrinsics.checkParameterIsNotNull(indicatorOptions, "indicatorOptions");
        init(indicatorOptions);
    }
}
