package com.zhpan.indicator.drawer;

import android.graphics.Canvas;
import com.zhpan.indicator.drawer.BaseDrawer;
import kotlin.Metadata;

/* JADX INFO: compiled from: IDrawer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J0\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\nH&J\u001c\u0010\u000e\u001a\u00060\u000fR\u00020\u00102\u0006\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\nH&¨\u0006\u0013"}, d2 = {"Lcom/zhpan/indicator/drawer/IDrawer;", "", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "onLayout", "changed", "", "left", "", "top", "right", "bottom", "onMeasure", "Lcom/zhpan/indicator/drawer/BaseDrawer$MeasureResult;", "Lcom/zhpan/indicator/drawer/BaseDrawer;", "widthMeasureSpec", "heightMeasureSpec", "indicator_release"}, k = 1, mv = {1, 1, 16})
public interface IDrawer {
    void onDraw(Canvas canvas);

    void onLayout(boolean changed, int left, int top, int right, int bottom);

    BaseDrawer.MeasureResult onMeasure(int widthMeasureSpec, int heightMeasureSpec);
}
