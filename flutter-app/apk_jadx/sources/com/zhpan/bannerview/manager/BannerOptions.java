package com.zhpan.bannerview.manager;

import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.indicator.option.IndicatorOptions;

/* JADX INFO: loaded from: classes.dex */
public class BannerOptions {
    public static final int DEFAULT_REVEAL_WIDTH = -1000;
    private boolean disallowParentInterceptDownEvent;
    private int indicatorGravity;
    private int interval;
    private boolean isCanLoop;
    private IndicatorMargin mIndicatorMargin;
    private int roundRadius;
    private float[] roundRadiusArray;
    private boolean rtl;
    private int scrollDuration;
    private int offScreenPageLimit = -1;
    private boolean isAutoPlay = false;
    private int pageStyle = 0;
    private float pageScale = 0.85f;
    private int mIndicatorVisibility = 0;
    private boolean userInputEnabled = true;
    private int orientation = 0;
    private boolean stopLoopWhenDetachedFromWindow = true;
    private boolean autoScrollSmoothly = true;
    private final IndicatorOptions mIndicatorOptions = new IndicatorOptions();
    private int pageMargin = BannerUtils.dp2px(20.0f);
    private int rightRevealWidth = -1000;
    private int leftRevealWidth = -1000;

    public int getInterval() {
        return this.interval;
    }

    public void setInterval(int i) {
        this.interval = i;
    }

    public boolean isCanLoop() {
        return this.isCanLoop;
    }

    public void setCanLoop(boolean z) {
        this.isCanLoop = z;
    }

    public boolean isAutoPlay() {
        return this.isAutoPlay;
    }

    public void setAutoPlay(boolean z) {
        this.isAutoPlay = z;
    }

    public int getIndicatorGravity() {
        return this.indicatorGravity;
    }

    public void setIndicatorGravity(int i) {
        this.indicatorGravity = i;
    }

    public int getIndicatorNormalColor() {
        return this.mIndicatorOptions.getNormalSliderColor();
    }

    public int getIndicatorCheckedColor() {
        return this.mIndicatorOptions.getCheckedSliderColor();
    }

    public int getNormalIndicatorWidth() {
        return (int) this.mIndicatorOptions.getNormalSliderWidth();
    }

    public void setIndicatorSliderColor(int i, int i2) {
        this.mIndicatorOptions.setSliderColor(i, i2);
    }

    public void setIndicatorSliderWidth(int i, int i2) {
        this.mIndicatorOptions.setSliderWidth(i, i2);
    }

    public void showIndicatorWhenOneItem(boolean z) {
        this.mIndicatorOptions.setShowIndicatorOneItem(z);
    }

    public int getCheckedIndicatorWidth() {
        return (int) this.mIndicatorOptions.getCheckedSliderWidth();
    }

    public IndicatorOptions getIndicatorOptions() {
        return this.mIndicatorOptions;
    }

    public int getPageMargin() {
        return this.pageMargin;
    }

    public void setPageMargin(int i) {
        this.pageMargin = i;
    }

    public int getRightRevealWidth() {
        return this.rightRevealWidth;
    }

    public void setRightRevealWidth(int i) {
        this.rightRevealWidth = i;
    }

    public int getLeftRevealWidth() {
        return this.leftRevealWidth;
    }

    public void setLeftRevealWidth(int i) {
        this.leftRevealWidth = i;
    }

    public int getIndicatorStyle() {
        return this.mIndicatorOptions.getIndicatorStyle();
    }

    public void setIndicatorStyle(int i) {
        this.mIndicatorOptions.setIndicatorStyle(i);
    }

    public int getIndicatorSlideMode() {
        return this.mIndicatorOptions.getSlideMode();
    }

    public void setIndicatorSlideMode(int i) {
        this.mIndicatorOptions.setSlideMode(i);
    }

    public float getIndicatorGap() {
        return this.mIndicatorOptions.getSliderGap();
    }

    public void setIndicatorGap(float f) {
        this.mIndicatorOptions.setSliderGap(f);
    }

    public float getIndicatorHeight() {
        return this.mIndicatorOptions.getSliderHeight();
    }

    public void setIndicatorHeight(int i) {
        this.mIndicatorOptions.setSliderHeight(i);
    }

    public int getPageStyle() {
        return this.pageStyle;
    }

    public void setPageStyle(int i) {
        this.pageStyle = i;
    }

    public float getPageScale() {
        return this.pageScale;
    }

    public void setPageScale(float f) {
        this.pageScale = f;
    }

    public IndicatorMargin getIndicatorMargin() {
        return this.mIndicatorMargin;
    }

    public void setIndicatorMargin(int i, int i2, int i3, int i4) {
        this.mIndicatorMargin = new IndicatorMargin(i, i2, i3, i4);
    }

    public boolean isAutoScrollSmoothly() {
        return this.autoScrollSmoothly;
    }

    public void setAutoScrollSmoothly(boolean z) {
        this.autoScrollSmoothly = z;
    }

    public float[] getRoundRectRadiusArray() {
        return this.roundRadiusArray;
    }

    public int getRoundRectRadius() {
        return this.roundRadius;
    }

    public void setRoundRectRadius(int i) {
        this.roundRadius = i;
    }

    public void setRoundRectRadius(int i, int i2, int i3, int i4) {
        this.roundRadiusArray = new float[]{f, f, f, f, f, f, f, f};
        float f = i;
        float f2 = i2;
        float f3 = i4;
        float f4 = i3;
    }

    public int getScrollDuration() {
        return this.scrollDuration;
    }

    public void setScrollDuration(int i) {
        this.scrollDuration = i;
    }

    public int getIndicatorVisibility() {
        return this.mIndicatorVisibility;
    }

    public void setIndicatorVisibility(int i) {
        this.mIndicatorVisibility = i;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int i) {
        this.orientation = i;
        this.mIndicatorOptions.setOrientation(i);
    }

    public boolean isUserInputEnabled() {
        return this.userInputEnabled;
    }

    public void setUserInputEnabled(boolean z) {
        this.userInputEnabled = z;
    }

    public void resetIndicatorOptions() {
        this.mIndicatorOptions.setCurrentPosition(0);
        this.mIndicatorOptions.setSlideProgress(0.0f);
    }

    public boolean isDisallowParentInterceptDownEvent() {
        return this.disallowParentInterceptDownEvent;
    }

    public void setDisallowParentInterceptDownEvent(boolean z) {
        this.disallowParentInterceptDownEvent = z;
    }

    public int getOffScreenPageLimit() {
        return this.offScreenPageLimit;
    }

    public void setOffScreenPageLimit(int i) {
        this.offScreenPageLimit = i;
    }

    public boolean isRtl() {
        return this.rtl;
    }

    public void setRtl(boolean z) {
        this.rtl = z;
        this.mIndicatorOptions.setOrientation(z ? 3 : 0);
    }

    public boolean isStopLoopWhenDetachedFromWindow() {
        return this.stopLoopWhenDetachedFromWindow;
    }

    public void setStopLoopWhenDetachedFromWindow(boolean z) {
        this.stopLoopWhenDetachedFromWindow = z;
    }

    public static class IndicatorMargin {
        private final int bottom;
        private final int left;
        private final int right;
        private final int top;

        public IndicatorMargin(int i, int i2, int i3, int i4) {
            this.left = i;
            this.right = i3;
            this.top = i2;
            this.bottom = i4;
        }

        public int getLeft() {
            return this.left;
        }

        public int getRight() {
            return this.right;
        }

        public int getTop() {
            return this.top;
        }

        public int getBottom() {
            return this.bottom;
        }
    }
}
