package com.zhpan.indicator.drawer;

import android.animation.ArgbEvaluator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.zhpan.indicator.option.IndicatorOptions;
import com.zhpan.indicator.utils.IndicatorUtils;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: CircleDrawer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fH\u0002J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u0010\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u0012\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u0014\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0014J\u0010\u0010\u0017\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/zhpan/indicator/drawer/CircleDrawer;", "Lcom/zhpan/indicator/drawer/BaseDrawer;", "indicatorOptions", "Lcom/zhpan/indicator/option/IndicatorOptions;", "(Lcom/zhpan/indicator/option/IndicatorOptions;)V", "rectF", "Landroid/graphics/RectF;", "drawCircle", "", "canvas", "Landroid/graphics/Canvas;", "coordinateX", "", "coordinateY", "radius", "drawCircleSlider", "drawColor", "drawNormal", "drawScaleSlider", "drawSlider", "drawWormSlider", "measureHeight", "", "onDraw", "indicator_release"}, k = 1, mv = {1, 1, 16})
public final class CircleDrawer extends BaseDrawer {
    private final RectF rectF;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CircleDrawer(IndicatorOptions indicatorOptions) {
        super(indicatorOptions);
        Intrinsics.checkParameterIsNotNull(indicatorOptions, "indicatorOptions");
        this.rectF = new RectF();
    }

    @Override // com.zhpan.indicator.drawer.BaseDrawer
    protected int measureHeight() {
        return ((int) getMaxWidth()) + 6;
    }

    @Override // com.zhpan.indicator.drawer.IDrawer
    public void onDraw(Canvas canvas) {
        Intrinsics.checkParameterIsNotNull(canvas, "canvas");
        int pageSize = getMIndicatorOptions().getPageSize();
        if (pageSize > 1 || (getMIndicatorOptions().getShowIndicatorOneItem() && pageSize == 1)) {
            drawNormal(canvas);
            drawSlider(canvas);
        }
    }

    private final void drawNormal(Canvas canvas) {
        float normalSliderWidth = getMIndicatorOptions().getNormalSliderWidth();
        getMPaint().setColor(getMIndicatorOptions().getNormalSliderColor());
        int pageSize = getMIndicatorOptions().getPageSize();
        for (int i = 0; i < pageSize; i++) {
            drawCircle(canvas, IndicatorUtils.INSTANCE.getCoordinateX(getMIndicatorOptions(), getMaxWidth(), i), IndicatorUtils.INSTANCE.getCoordinateY(getMaxWidth()), normalSliderWidth / 2);
        }
    }

    private final void drawSlider(Canvas canvas) {
        getMPaint().setColor(getMIndicatorOptions().getCheckedSliderColor());
        int slideMode = getMIndicatorOptions().getSlideMode();
        if (slideMode == 0 || slideMode == 2) {
            drawCircleSlider(canvas);
            return;
        }
        if (slideMode == 3) {
            drawWormSlider(canvas);
        } else if (slideMode == 4) {
            drawScaleSlider(canvas);
        } else {
            if (slideMode != 5) {
                return;
            }
            drawColor(canvas);
        }
    }

    private final void drawColor(Canvas canvas) {
        float normalSliderWidth;
        int currentPosition = getMIndicatorOptions().getCurrentPosition();
        float slideProgress = getMIndicatorOptions().getSlideProgress();
        float coordinateX = IndicatorUtils.INSTANCE.getCoordinateX(getMIndicatorOptions(), getMaxWidth(), currentPosition);
        float coordinateY = IndicatorUtils.INSTANCE.getCoordinateY(getMaxWidth());
        ArgbEvaluator argbEvaluator$indicator_release = getArgbEvaluator();
        Object objEvaluate = argbEvaluator$indicator_release != null ? argbEvaluator$indicator_release.evaluate(slideProgress, Integer.valueOf(getMIndicatorOptions().getCheckedSliderColor()), Integer.valueOf(getMIndicatorOptions().getNormalSliderColor())) : null;
        Paint mPaint$indicator_release = getMPaint();
        if (objEvaluate == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
        }
        mPaint$indicator_release.setColor(((Integer) objEvaluate).intValue());
        float f = 2;
        drawCircle(canvas, coordinateX, coordinateY, getMIndicatorOptions().getNormalSliderWidth() / f);
        ArgbEvaluator argbEvaluator$indicator_release2 = getArgbEvaluator();
        Object objEvaluate2 = argbEvaluator$indicator_release2 != null ? argbEvaluator$indicator_release2.evaluate(1 - slideProgress, Integer.valueOf(getMIndicatorOptions().getCheckedSliderColor()), Integer.valueOf(getMIndicatorOptions().getNormalSliderColor())) : null;
        Paint mPaint$indicator_release2 = getMPaint();
        if (objEvaluate2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
        }
        mPaint$indicator_release2.setColor(((Integer) objEvaluate2).intValue());
        if (currentPosition == getMIndicatorOptions().getPageSize() - 1) {
            normalSliderWidth = IndicatorUtils.INSTANCE.getCoordinateX(getMIndicatorOptions(), getMaxWidth(), 0);
        } else {
            normalSliderWidth = getMIndicatorOptions().getNormalSliderWidth() + coordinateX + getMIndicatorOptions().getSliderGap();
        }
        drawCircle(canvas, normalSliderWidth, coordinateY, getMIndicatorOptions().getCheckedSliderWidth() / f);
    }

    private final void drawScaleSlider(Canvas canvas) {
        Object objEvaluate;
        int currentPosition = getMIndicatorOptions().getCurrentPosition();
        float slideProgress = getMIndicatorOptions().getSlideProgress();
        float coordinateX = IndicatorUtils.INSTANCE.getCoordinateX(getMIndicatorOptions(), getMaxWidth(), currentPosition);
        float coordinateY = IndicatorUtils.INSTANCE.getCoordinateY(getMaxWidth());
        if (slideProgress < 1) {
            ArgbEvaluator argbEvaluator$indicator_release = getArgbEvaluator();
            Object objEvaluate2 = argbEvaluator$indicator_release != null ? argbEvaluator$indicator_release.evaluate(slideProgress, Integer.valueOf(getMIndicatorOptions().getCheckedSliderColor()), Integer.valueOf(getMIndicatorOptions().getNormalSliderColor())) : null;
            Paint mPaint$indicator_release = getMPaint();
            if (objEvaluate2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
            }
            mPaint$indicator_release.setColor(((Integer) objEvaluate2).intValue());
            float f = 2;
            drawCircle(canvas, coordinateX, coordinateY, (getMIndicatorOptions().getCheckedSliderWidth() / f) - (((getMIndicatorOptions().getCheckedSliderWidth() / f) - (getMIndicatorOptions().getNormalSliderWidth() / f)) * slideProgress));
        }
        if (currentPosition == getMIndicatorOptions().getPageSize() - 1) {
            ArgbEvaluator argbEvaluator$indicator_release2 = getArgbEvaluator();
            objEvaluate = argbEvaluator$indicator_release2 != null ? argbEvaluator$indicator_release2.evaluate(slideProgress, Integer.valueOf(getMIndicatorOptions().getNormalSliderColor()), Integer.valueOf(getMIndicatorOptions().getCheckedSliderColor())) : null;
            Paint mPaint$indicator_release2 = getMPaint();
            if (objEvaluate == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
            }
            mPaint$indicator_release2.setColor(((Integer) objEvaluate).intValue());
            float f2 = 2;
            drawCircle(canvas, getMaxWidth() / f2, coordinateY, (getMinWidth() / f2) + (((getMaxWidth() / f2) - (getMinWidth() / f2)) * slideProgress));
            return;
        }
        if (slideProgress > 0) {
            ArgbEvaluator argbEvaluator$indicator_release3 = getArgbEvaluator();
            objEvaluate = argbEvaluator$indicator_release3 != null ? argbEvaluator$indicator_release3.evaluate(slideProgress, Integer.valueOf(getMIndicatorOptions().getNormalSliderColor()), Integer.valueOf(getMIndicatorOptions().getCheckedSliderColor())) : null;
            Paint mPaint$indicator_release3 = getMPaint();
            if (objEvaluate == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
            }
            mPaint$indicator_release3.setColor(((Integer) objEvaluate).intValue());
            float f3 = 2;
            drawCircle(canvas, coordinateX + getMIndicatorOptions().getSliderGap() + getMIndicatorOptions().getNormalSliderWidth(), coordinateY, (getMIndicatorOptions().getNormalSliderWidth() / f3) + (((getMIndicatorOptions().getCheckedSliderWidth() / f3) - (getMIndicatorOptions().getNormalSliderWidth() / f3)) * slideProgress));
        }
    }

    private final void drawCircleSlider(Canvas canvas) {
        int currentPosition = getMIndicatorOptions().getCurrentPosition();
        float coordinateX = IndicatorUtils.INSTANCE.getCoordinateX(getMIndicatorOptions(), getMaxWidth(), currentPosition);
        drawCircle(canvas, coordinateX + ((IndicatorUtils.INSTANCE.getCoordinateX(getMIndicatorOptions(), getMaxWidth(), (currentPosition + 1) % getMIndicatorOptions().getPageSize()) - coordinateX) * getMIndicatorOptions().getSlideProgress()), IndicatorUtils.INSTANCE.getCoordinateY(getMaxWidth()), getMIndicatorOptions().getCheckedSliderWidth() / 2);
    }

    private final void drawWormSlider(Canvas canvas) {
        float normalSliderWidth = getMIndicatorOptions().getNormalSliderWidth();
        float slideProgress = getMIndicatorOptions().getSlideProgress();
        int currentPosition = getMIndicatorOptions().getCurrentPosition();
        float sliderGap = getMIndicatorOptions().getSliderGap() + getMIndicatorOptions().getNormalSliderWidth();
        float coordinateX = IndicatorUtils.INSTANCE.getCoordinateX(getMIndicatorOptions(), getMaxWidth(), currentPosition);
        float f = 2;
        float fCoerceAtLeast = (RangesKt.coerceAtLeast(((slideProgress - 0.5f) * sliderGap) * 2.0f, 0.0f) + coordinateX) - (getMIndicatorOptions().getNormalSliderWidth() / f);
        float f2 = 3;
        this.rectF.set(fCoerceAtLeast + f2, f2, coordinateX + RangesKt.coerceAtMost(slideProgress * sliderGap * 2.0f, sliderGap) + (getMIndicatorOptions().getNormalSliderWidth() / f) + f2, normalSliderWidth + f2);
        canvas.drawRoundRect(this.rectF, normalSliderWidth, normalSliderWidth, getMPaint());
    }

    private final void drawCircle(Canvas canvas, float coordinateX, float coordinateY, float radius) {
        float f = 3;
        canvas.drawCircle(coordinateX + f, coordinateY + f, radius, getMPaint());
    }
}
