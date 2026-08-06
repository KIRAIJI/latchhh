package com.zhpan.indicator.drawer;

import android.animation.ArgbEvaluator;
import android.graphics.Paint;
import com.zhpan.indicator.option.IndicatorOptions;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: BaseDrawer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000b\b&\u0018\u0000 /2\u00020\u0001:\u0002/0B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\"\u001a\u00020#H\u0014J\b\u0010$\u001a\u00020#H\u0002J0\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\f2\u0006\u0010(\u001a\u00020#2\u0006\u0010)\u001a\u00020#2\u0006\u0010*\u001a\u00020#2\u0006\u0010+\u001a\u00020#H\u0016J\u001c\u0010,\u001a\u00060\u0012R\u00020\u00002\u0006\u0010-\u001a\u00020#2\u0006\u0010.\u001a\u00020#H\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\rR\u001a\u0010\u0002\u001a\u00020\u0003X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0004R\u0012\u0010\u0011\u001a\u00060\u0012R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\u0014X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001aX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020\u001aX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001c\"\u0004\b!\u0010\u001e¨\u00061"}, d2 = {"Lcom/zhpan/indicator/drawer/BaseDrawer;", "Lcom/zhpan/indicator/drawer/IDrawer;", "mIndicatorOptions", "Lcom/zhpan/indicator/option/IndicatorOptions;", "(Lcom/zhpan/indicator/option/IndicatorOptions;)V", "argbEvaluator", "Landroid/animation/ArgbEvaluator;", "getArgbEvaluator$indicator_release", "()Landroid/animation/ArgbEvaluator;", "setArgbEvaluator$indicator_release", "(Landroid/animation/ArgbEvaluator;)V", "isWidthEquals", "", "()Z", "getMIndicatorOptions$indicator_release", "()Lcom/zhpan/indicator/option/IndicatorOptions;", "setMIndicatorOptions$indicator_release", "mMeasureResult", "Lcom/zhpan/indicator/drawer/BaseDrawer$MeasureResult;", "mPaint", "Landroid/graphics/Paint;", "getMPaint$indicator_release", "()Landroid/graphics/Paint;", "setMPaint$indicator_release", "(Landroid/graphics/Paint;)V", "maxWidth", "", "getMaxWidth$indicator_release", "()F", "setMaxWidth$indicator_release", "(F)V", "minWidth", "getMinWidth$indicator_release", "setMinWidth$indicator_release", "measureHeight", "", "measureWidth", "onLayout", "", "changed", "left", "top", "right", "bottom", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "Companion", "MeasureResult", "indicator_release"}, k = 1, mv = {1, 1, 16})
public abstract class BaseDrawer implements IDrawer {
    public static final int INDICATOR_PADDING = 3;
    public static final int INDICATOR_PADDING_ADDITION = 6;
    private ArgbEvaluator argbEvaluator;
    private IndicatorOptions mIndicatorOptions;
    private final MeasureResult mMeasureResult;
    private Paint mPaint;
    private float maxWidth;
    private float minWidth;

    @Override // com.zhpan.indicator.drawer.IDrawer
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    public BaseDrawer(IndicatorOptions mIndicatorOptions) {
        Intrinsics.checkParameterIsNotNull(mIndicatorOptions, "mIndicatorOptions");
        this.mIndicatorOptions = mIndicatorOptions;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mMeasureResult = new MeasureResult();
        if (this.mIndicatorOptions.getSlideMode() == 4 || this.mIndicatorOptions.getSlideMode() == 5) {
            this.argbEvaluator = new ArgbEvaluator();
        }
    }

    /* JADX INFO: renamed from: getMIndicatorOptions$indicator_release, reason: from getter */
    public final IndicatorOptions getMIndicatorOptions() {
        return this.mIndicatorOptions;
    }

    public final void setMIndicatorOptions$indicator_release(IndicatorOptions indicatorOptions) {
        Intrinsics.checkParameterIsNotNull(indicatorOptions, "<set-?>");
        this.mIndicatorOptions = indicatorOptions;
    }

    /* JADX INFO: renamed from: getMaxWidth$indicator_release, reason: from getter */
    public final float getMaxWidth() {
        return this.maxWidth;
    }

    public final void setMaxWidth$indicator_release(float f) {
        this.maxWidth = f;
    }

    /* JADX INFO: renamed from: getMinWidth$indicator_release, reason: from getter */
    public final float getMinWidth() {
        return this.minWidth;
    }

    public final void setMinWidth$indicator_release(float f) {
        this.minWidth = f;
    }

    /* JADX INFO: renamed from: getMPaint$indicator_release, reason: from getter */
    public final Paint getMPaint() {
        return this.mPaint;
    }

    public final void setMPaint$indicator_release(Paint paint) {
        Intrinsics.checkParameterIsNotNull(paint, "<set-?>");
        this.mPaint = paint;
    }

    /* JADX INFO: renamed from: getArgbEvaluator$indicator_release, reason: from getter */
    public final ArgbEvaluator getArgbEvaluator() {
        return this.argbEvaluator;
    }

    public final void setArgbEvaluator$indicator_release(ArgbEvaluator argbEvaluator) {
        this.argbEvaluator = argbEvaluator;
    }

    protected final boolean isWidthEquals() {
        return this.mIndicatorOptions.getNormalSliderWidth() == this.mIndicatorOptions.getCheckedSliderWidth();
    }

    @Override // com.zhpan.indicator.drawer.IDrawer
    public MeasureResult onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.maxWidth = RangesKt.coerceAtLeast(this.mIndicatorOptions.getNormalSliderWidth(), this.mIndicatorOptions.getCheckedSliderWidth());
        this.minWidth = RangesKt.coerceAtMost(this.mIndicatorOptions.getNormalSliderWidth(), this.mIndicatorOptions.getCheckedSliderWidth());
        if (this.mIndicatorOptions.getOrientation() == 1) {
            this.mMeasureResult.setMeasureResult$indicator_release(measureHeight(), measureWidth());
        } else {
            this.mMeasureResult.setMeasureResult$indicator_release(measureWidth(), measureHeight());
        }
        return this.mMeasureResult;
    }

    protected int measureHeight() {
        return ((int) this.mIndicatorOptions.getSliderHeight()) + 3;
    }

    private final int measureWidth() {
        float pageSize = this.mIndicatorOptions.getPageSize() - 1;
        return ((int) ((this.mIndicatorOptions.getSliderGap() * pageSize) + this.maxWidth + (pageSize * this.minWidth))) + 6;
    }

    /* JADX INFO: compiled from: BaseDrawer.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001d\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0000¢\u0006\u0002\b\u000fR$\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR$\u0010\n\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\t¨\u0006\u0010"}, d2 = {"Lcom/zhpan/indicator/drawer/BaseDrawer$MeasureResult;", "", "(Lcom/zhpan/indicator/drawer/BaseDrawer;)V", "<set-?>", "", "measureHeight", "getMeasureHeight", "()I", "setMeasureHeight$indicator_release", "(I)V", "measureWidth", "getMeasureWidth", "setMeasureWidth$indicator_release", "setMeasureResult", "", "setMeasureResult$indicator_release", "indicator_release"}, k = 1, mv = {1, 1, 16})
    public final class MeasureResult {
        private int measureHeight;
        private int measureWidth;

        public MeasureResult() {
        }

        public final int getMeasureWidth() {
            return this.measureWidth;
        }

        public final void setMeasureWidth$indicator_release(int i) {
            this.measureWidth = i;
        }

        public final int getMeasureHeight() {
            return this.measureHeight;
        }

        public final void setMeasureHeight$indicator_release(int i) {
            this.measureHeight = i;
        }

        public final void setMeasureResult$indicator_release(int measureWidth, int measureHeight) {
            this.measureWidth = measureWidth;
            this.measureHeight = measureHeight;
        }
    }
}
