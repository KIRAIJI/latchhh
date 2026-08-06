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

/* JADX INFO: compiled from: RectDrawer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\b\u0016\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u0010\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0014J\u0018\u0010\u0011\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J \u0010\u0014\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0014J\u0018\u0010\u0018\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u0013H\u0002J\u0010\u0010\u001a\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0018\u0010\u001b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u001c\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u001d\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u001e"}, d2 = {"Lcom/zhpan/indicator/drawer/RectDrawer;", "Lcom/zhpan/indicator/drawer/BaseDrawer;", "indicatorOptions", "Lcom/zhpan/indicator/option/IndicatorOptions;", "(Lcom/zhpan/indicator/option/IndicatorOptions;)V", "mRectF", "Landroid/graphics/RectF;", "getMRectF$indicator_release", "()Landroid/graphics/RectF;", "setMRectF$indicator_release", "(Landroid/graphics/RectF;)V", "drawCheckedSlider", "", "canvas", "Landroid/graphics/Canvas;", "drawColorSlider", "drawDash", "drawInequalitySlider", "pageSize", "", "drawRoundRect", "rx", "", "ry", "drawScaleSlider", "i", "drawSmoothSlider", "drawUncheckedSlider", "drawWormSlider", "onDraw", "indicator_release"}, k = 1, mv = {1, 1, 16})
public class RectDrawer extends BaseDrawer {
    private RectF mRectF;

    protected void drawDash(Canvas canvas) {
        Intrinsics.checkParameterIsNotNull(canvas, "canvas");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RectDrawer(IndicatorOptions indicatorOptions) {
        super(indicatorOptions);
        Intrinsics.checkParameterIsNotNull(indicatorOptions, "indicatorOptions");
        this.mRectF = new RectF();
    }

    /* JADX INFO: renamed from: getMRectF$indicator_release, reason: from getter */
    public final RectF getMRectF() {
        return this.mRectF;
    }

    public final void setMRectF$indicator_release(RectF rectF) {
        Intrinsics.checkParameterIsNotNull(rectF, "<set-?>");
        this.mRectF = rectF;
    }

    @Override // com.zhpan.indicator.drawer.IDrawer
    public void onDraw(Canvas canvas) {
        Intrinsics.checkParameterIsNotNull(canvas, "canvas");
        int pageSize = getMIndicatorOptions().getPageSize();
        if (pageSize > 1 || (getMIndicatorOptions().getShowIndicatorOneItem() && pageSize == 1)) {
            if (isWidthEquals() && getMIndicatorOptions().getSlideMode() != 0) {
                drawUncheckedSlider(canvas, pageSize);
                drawCheckedSlider(canvas);
            } else {
                if (getMIndicatorOptions().getSlideMode() != 4) {
                    drawInequalitySlider(canvas, pageSize);
                    return;
                }
                for (int i = 0; i < pageSize; i++) {
                    drawScaleSlider(canvas, i);
                }
            }
        }
    }

    private final void drawScaleSlider(Canvas canvas, int i) {
        float slideProgress;
        int checkedSliderColor = getMIndicatorOptions().getCheckedSliderColor();
        float sliderGap = getMIndicatorOptions().getSliderGap();
        float sliderHeight = getMIndicatorOptions().getSliderHeight();
        int currentPosition = getMIndicatorOptions().getCurrentPosition();
        float normalSliderWidth = getMIndicatorOptions().getNormalSliderWidth();
        float checkedSliderWidth = getMIndicatorOptions().getCheckedSliderWidth();
        if (i < currentPosition) {
            getMPaint().setColor(getMIndicatorOptions().getNormalSliderColor());
            if (currentPosition == getMIndicatorOptions().getPageSize() - 1) {
                float f = i;
                slideProgress = (f * normalSliderWidth) + (f * sliderGap) + ((checkedSliderWidth - normalSliderWidth) * getMIndicatorOptions().getSlideProgress());
            } else {
                float f2 = i;
                slideProgress = (f2 * normalSliderWidth) + (f2 * sliderGap);
            }
            this.mRectF.set(slideProgress, 0.0f, normalSliderWidth + slideProgress, sliderHeight);
            drawRoundRect(canvas, sliderHeight, sliderHeight);
            return;
        }
        if (i != currentPosition) {
            if (currentPosition + 1 != i || getMIndicatorOptions().getSlideProgress() == 0.0f) {
                getMPaint().setColor(getMIndicatorOptions().getNormalSliderColor());
                float f3 = i;
                float minWidth$indicator_release = (getMinWidth() * f3) + (f3 * sliderGap) + (checkedSliderWidth - getMinWidth());
                this.mRectF.set(minWidth$indicator_release, 0.0f, getMinWidth() + minWidth$indicator_release, sliderHeight);
                drawRoundRect(canvas, sliderHeight, sliderHeight);
                return;
            }
            return;
        }
        getMPaint().setColor(checkedSliderColor);
        float slideProgress2 = getMIndicatorOptions().getSlideProgress();
        if (currentPosition == getMIndicatorOptions().getPageSize() - 1) {
            ArgbEvaluator argbEvaluator$indicator_release = getArgbEvaluator();
            Object objEvaluate = argbEvaluator$indicator_release != null ? argbEvaluator$indicator_release.evaluate(slideProgress2, Integer.valueOf(checkedSliderColor), Integer.valueOf(getMIndicatorOptions().getNormalSliderColor())) : null;
            Paint mPaint$indicator_release = getMPaint();
            if (objEvaluate == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
            }
            mPaint$indicator_release.setColor(((Integer) objEvaluate).intValue());
            float pageSize = ((getMIndicatorOptions().getPageSize() - 1) * (getMIndicatorOptions().getSliderGap() + normalSliderWidth)) + checkedSliderWidth;
            this.mRectF.set((pageSize - checkedSliderWidth) + ((checkedSliderWidth - normalSliderWidth) * slideProgress2), 0.0f, pageSize, sliderHeight);
            drawRoundRect(canvas, sliderHeight, sliderHeight);
        } else {
            float f4 = 1;
            if (slideProgress2 < f4) {
                ArgbEvaluator argbEvaluator$indicator_release2 = getArgbEvaluator();
                Object objEvaluate2 = argbEvaluator$indicator_release2 != null ? argbEvaluator$indicator_release2.evaluate(slideProgress2, Integer.valueOf(checkedSliderColor), Integer.valueOf(getMIndicatorOptions().getNormalSliderColor())) : null;
                Paint mPaint$indicator_release2 = getMPaint();
                if (objEvaluate2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
                }
                mPaint$indicator_release2.setColor(((Integer) objEvaluate2).intValue());
                float f5 = i;
                float f6 = (f5 * normalSliderWidth) + (f5 * sliderGap);
                this.mRectF.set(f6, 0.0f, f6 + normalSliderWidth + ((checkedSliderWidth - normalSliderWidth) * (f4 - slideProgress2)), sliderHeight);
                drawRoundRect(canvas, sliderHeight, sliderHeight);
            }
        }
        if (currentPosition == getMIndicatorOptions().getPageSize() - 1) {
            if (slideProgress2 > 0) {
                ArgbEvaluator argbEvaluator$indicator_release3 = getArgbEvaluator();
                Object objEvaluate3 = argbEvaluator$indicator_release3 != null ? argbEvaluator$indicator_release3.evaluate(1 - slideProgress2, Integer.valueOf(checkedSliderColor), Integer.valueOf(getMIndicatorOptions().getNormalSliderColor())) : null;
                Paint mPaint$indicator_release3 = getMPaint();
                if (objEvaluate3 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
                }
                mPaint$indicator_release3.setColor(((Integer) objEvaluate3).intValue());
                this.mRectF.set(0.0f, 0.0f, normalSliderWidth + 0.0f + ((checkedSliderWidth - normalSliderWidth) * slideProgress2), sliderHeight);
                drawRoundRect(canvas, sliderHeight, sliderHeight);
                return;
            }
            return;
        }
        if (slideProgress2 > 0) {
            ArgbEvaluator argbEvaluator$indicator_release4 = getArgbEvaluator();
            Object objEvaluate4 = argbEvaluator$indicator_release4 != null ? argbEvaluator$indicator_release4.evaluate(1 - slideProgress2, Integer.valueOf(checkedSliderColor), Integer.valueOf(getMIndicatorOptions().getNormalSliderColor())) : null;
            Paint mPaint$indicator_release4 = getMPaint();
            if (objEvaluate4 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
            }
            mPaint$indicator_release4.setColor(((Integer) objEvaluate4).intValue());
            float f7 = i;
            float f8 = (f7 * normalSliderWidth) + (f7 * sliderGap) + normalSliderWidth + sliderGap + checkedSliderWidth;
            this.mRectF.set((f8 - normalSliderWidth) - ((checkedSliderWidth - normalSliderWidth) * slideProgress2), 0.0f, f8, sliderHeight);
            drawRoundRect(canvas, sliderHeight, sliderHeight);
        }
    }

    private final void drawUncheckedSlider(Canvas canvas, int pageSize) {
        for (int i = 0; i < pageSize; i++) {
            getMPaint().setColor(getMIndicatorOptions().getNormalSliderColor());
            float f = i;
            float maxWidth$indicator_release = (getMaxWidth() * f) + (f * getMIndicatorOptions().getSliderGap()) + (getMaxWidth() - getMinWidth());
            this.mRectF.set(maxWidth$indicator_release, 0.0f, getMinWidth() + maxWidth$indicator_release, getMIndicatorOptions().getSliderHeight());
            drawRoundRect(canvas, getMIndicatorOptions().getSliderHeight(), getMIndicatorOptions().getSliderHeight());
        }
    }

    private final void drawInequalitySlider(Canvas canvas, int pageSize) {
        int i = 0;
        float sliderGap = 0.0f;
        while (i < pageSize) {
            float maxWidth$indicator_release = i == getMIndicatorOptions().getCurrentPosition() ? getMaxWidth() : getMinWidth();
            getMPaint().setColor(i == getMIndicatorOptions().getCurrentPosition() ? getMIndicatorOptions().getCheckedSliderColor() : getMIndicatorOptions().getNormalSliderColor());
            this.mRectF.set(sliderGap, 0.0f, sliderGap + maxWidth$indicator_release, getMIndicatorOptions().getSliderHeight());
            drawRoundRect(canvas, getMIndicatorOptions().getSliderHeight(), getMIndicatorOptions().getSliderHeight());
            sliderGap += maxWidth$indicator_release + getMIndicatorOptions().getSliderGap();
            i++;
        }
    }

    private final void drawCheckedSlider(Canvas canvas) {
        getMPaint().setColor(getMIndicatorOptions().getCheckedSliderColor());
        int slideMode = getMIndicatorOptions().getSlideMode();
        if (slideMode == 2) {
            drawSmoothSlider(canvas);
        } else if (slideMode == 3) {
            drawWormSlider(canvas);
        } else {
            if (slideMode != 5) {
                return;
            }
            drawColorSlider(canvas);
        }
    }

    private final void drawColorSlider(Canvas canvas) {
        int currentPosition = getMIndicatorOptions().getCurrentPosition();
        float slideProgress = getMIndicatorOptions().getSlideProgress();
        float f = currentPosition;
        float minWidth$indicator_release = (getMinWidth() * f) + (f * getMIndicatorOptions().getSliderGap());
        if (slideProgress < 0.99d) {
            ArgbEvaluator argbEvaluator$indicator_release = getArgbEvaluator();
            Object objEvaluate = argbEvaluator$indicator_release != null ? argbEvaluator$indicator_release.evaluate(slideProgress, Integer.valueOf(getMIndicatorOptions().getCheckedSliderColor()), Integer.valueOf(getMIndicatorOptions().getNormalSliderColor())) : null;
            Paint mPaint$indicator_release = getMPaint();
            if (objEvaluate == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
            }
            mPaint$indicator_release.setColor(((Integer) objEvaluate).intValue());
            this.mRectF.set(minWidth$indicator_release, 0.0f, getMinWidth() + minWidth$indicator_release, getMIndicatorOptions().getSliderHeight());
            drawRoundRect(canvas, getMIndicatorOptions().getSliderHeight(), getMIndicatorOptions().getSliderHeight());
        }
        float sliderGap = minWidth$indicator_release + getMIndicatorOptions().getSliderGap() + getMIndicatorOptions().getNormalSliderWidth();
        if (currentPosition == getMIndicatorOptions().getPageSize() - 1) {
            sliderGap = 0.0f;
        }
        ArgbEvaluator argbEvaluator$indicator_release2 = getArgbEvaluator();
        Object objEvaluate2 = argbEvaluator$indicator_release2 != null ? argbEvaluator$indicator_release2.evaluate(1 - slideProgress, Integer.valueOf(getMIndicatorOptions().getCheckedSliderColor()), Integer.valueOf(getMIndicatorOptions().getNormalSliderColor())) : null;
        Paint mPaint$indicator_release2 = getMPaint();
        if (objEvaluate2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
        }
        mPaint$indicator_release2.setColor(((Integer) objEvaluate2).intValue());
        this.mRectF.set(sliderGap, 0.0f, getMinWidth() + sliderGap, getMIndicatorOptions().getSliderHeight());
        drawRoundRect(canvas, getMIndicatorOptions().getSliderHeight(), getMIndicatorOptions().getSliderHeight());
    }

    private final void drawWormSlider(Canvas canvas) {
        float sliderHeight = getMIndicatorOptions().getSliderHeight();
        float slideProgress = getMIndicatorOptions().getSlideProgress();
        int currentPosition = getMIndicatorOptions().getCurrentPosition();
        float sliderGap = getMIndicatorOptions().getSliderGap() + getMIndicatorOptions().getNormalSliderWidth();
        float coordinateX = IndicatorUtils.INSTANCE.getCoordinateX(getMIndicatorOptions(), getMaxWidth(), currentPosition);
        float f = 2;
        this.mRectF.set((RangesKt.coerceAtLeast(((slideProgress - 0.5f) * sliderGap) * 2.0f, 0.0f) + coordinateX) - (getMIndicatorOptions().getNormalSliderWidth() / f), 0.0f, coordinateX + RangesKt.coerceAtMost(slideProgress * sliderGap * 2.0f, sliderGap) + (getMIndicatorOptions().getNormalSliderWidth() / f), sliderHeight);
        drawRoundRect(canvas, sliderHeight, sliderHeight);
    }

    private final void drawSmoothSlider(Canvas canvas) {
        int currentPosition = getMIndicatorOptions().getCurrentPosition();
        float sliderGap = getMIndicatorOptions().getSliderGap();
        float sliderHeight = getMIndicatorOptions().getSliderHeight();
        float f = currentPosition;
        float maxWidth$indicator_release = (getMaxWidth() * f) + (f * sliderGap) + ((getMaxWidth() + sliderGap) * getMIndicatorOptions().getSlideProgress());
        this.mRectF.set(maxWidth$indicator_release, 0.0f, getMaxWidth() + maxWidth$indicator_release, sliderHeight);
        drawRoundRect(canvas, sliderHeight, sliderHeight);
    }

    protected void drawRoundRect(Canvas canvas, float rx, float ry) {
        Intrinsics.checkParameterIsNotNull(canvas, "canvas");
        drawDash(canvas);
    }
}
