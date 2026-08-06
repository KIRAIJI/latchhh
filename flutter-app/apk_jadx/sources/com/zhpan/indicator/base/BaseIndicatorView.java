package com.zhpan.indicator.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import com.zhpan.indicator.option.IndicatorOptions;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseIndicatorView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000U\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b/\n\u0002\u0010\u000b\n\u0000*\u0001\u0015\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0006\u0010\u001b\u001a\u00020\bJ\u0006\u0010\u001c\u001a\u00020\u000bJ\u0006\u0010\u001d\u001a\u00020\bJ\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u000bJ\u0006\u0010!\u001a\u00020\u000bJ\u0006\u0010\"\u001a\u00020\bJ\u0006\u0010#\u001a\u00020\bJ\u0006\u0010$\u001a\u00020\u000bJ\b\u0010%\u001a\u00020\u001fH\u0016J\u0010\u0010&\u001a\u00020\u001f2\u0006\u0010'\u001a\u00020\bH\u0016J \u0010(\u001a\u00020\u001f2\u0006\u0010)\u001a\u00020\b2\u0006\u0010*\u001a\u00020\u000b2\u0006\u0010+\u001a\u00020\bH\u0016J\u0010\u0010,\u001a\u00020\u001f2\u0006\u0010)\u001a\u00020\bH\u0016J\u0018\u0010-\u001a\u00020\u001f2\u0006\u0010)\u001a\u00020\b2\u0006\u0010*\u001a\u00020\u000bH\u0002J\u0010\u0010.\u001a\u00020\u001f2\b\b\u0001\u0010/\u001a\u00020\bJ\u000e\u00100\u001a\u00020\u001f2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u00101\u001a\u00020\u001f2\u0006\u00102\u001a\u00020\bJ\u000e\u00103\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u000bJ\u0010\u00104\u001a\u00020\u001f2\u0006\u00105\u001a\u00020\u000fH\u0016J\u000e\u00106\u001a\u00020\u00002\u0006\u00107\u001a\u00020\bJ\u0010\u00108\u001a\u00020\u001f2\b\b\u0001\u0010/\u001a\u00020\bJ\u000e\u00109\u001a\u00020\u001f2\u0006\u0010:\u001a\u00020\u000bJ\u000e\u0010;\u001a\u00020\u00002\u0006\u0010<\u001a\u00020\bJ\u000e\u0010=\u001a\u00020\u00002\u0006\u0010>\u001a\u00020\bJ\u000e\u0010?\u001a\u00020\u001f2\u0006\u0010@\u001a\u00020\u000bJ\u001a\u0010A\u001a\u00020\u00002\b\b\u0001\u0010/\u001a\u00020\b2\b\b\u0001\u0010B\u001a\u00020\bJ\u000e\u0010C\u001a\u00020\u00002\u0006\u0010D\u001a\u00020\u000bJ\u000e\u0010E\u001a\u00020\u00002\u0006\u0010F\u001a\u00020\u000bJ\u000e\u0010G\u001a\u00020\u00002\u0006\u0010H\u001a\u00020\u000bJ\u0016\u0010G\u001a\u00020\u00002\u0006\u0010:\u001a\u00020\u000b2\u0006\u0010I\u001a\u00020\u000bJ\b\u0010J\u001a\u00020\u001fH\u0002J\u000e\u0010K\u001a\u00020\u001f2\u0006\u0010L\u001a\u00020\u0018J\u000e\u0010K\u001a\u00020\u001f2\u0006\u0010M\u001a\u00020\u001aJ\u000e\u0010N\u001a\u00020\u001f2\u0006\u0010N\u001a\u00020OR\u0011\u0010\n\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0010\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0016R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006P"}, d2 = {"Lcom/zhpan/indicator/base/BaseIndicatorView;", "Landroid/view/View;", "Lcom/zhpan/indicator/base/IIndicator;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "checkedSliderWidth", "", "getCheckedSliderWidth", "()F", "mIndicatorOptions", "Lcom/zhpan/indicator/option/IndicatorOptions;", "getMIndicatorOptions", "()Lcom/zhpan/indicator/option/IndicatorOptions;", "setMIndicatorOptions", "(Lcom/zhpan/indicator/option/IndicatorOptions;)V", "mOnPageChangeCallback", "com/zhpan/indicator/base/BaseIndicatorView$mOnPageChangeCallback$1", "Lcom/zhpan/indicator/base/BaseIndicatorView$mOnPageChangeCallback$1;", "mViewPager", "Landroidx/viewpager/widget/ViewPager;", "mViewPager2", "Landroidx/viewpager2/widget/ViewPager2;", "getCheckedColor", "getCheckedSlideWidth", "getCurrentPosition", "getIndicatorGap", "", "indicatorGap", "getNormalSlideWidth", "getPageSize", "getSlideMode", "getSlideProgress", "notifyDataChanged", "onPageScrollStateChanged", "state", "onPageScrolled", "position", "positionOffset", "positionOffsetPixels", "onPageSelected", "scrollSlider", "setCheckedColor", "normalColor", "setCheckedSlideWidth", "setCurrentPosition", "currentPosition", "setIndicatorGap", "setIndicatorOptions", "options", "setIndicatorStyle", "indicatorStyle", "setNormalColor", "setNormalSlideWidth", "normalSliderWidth", "setPageSize", "pageSize", "setSlideMode", "slideMode", "setSlideProgress", "slideProgress", "setSliderColor", "selectedColor", "setSliderGap", "sliderGap", "setSliderHeight", "sliderHeight", "setSliderWidth", "sliderWidth", "selectedSliderWidth", "setupViewPager", "setupWithViewPager", "viewPager", "viewPager2", "showIndicatorWhenOneItem", "", "indicator_release"}, k = 1, mv = {1, 1, 16})
public class BaseIndicatorView extends View implements IIndicator {
    private IndicatorOptions mIndicatorOptions;
    private final BaseIndicatorView$mOnPageChangeCallback$1 mOnPageChangeCallback;
    private ViewPager mViewPager;
    private ViewPager2 mViewPager2;

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int state) {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.zhpan.indicator.base.BaseIndicatorView$mOnPageChangeCallback$1] */
    public BaseIndicatorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkParameterIsNotNull(context, "context");
        this.mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() { // from class: com.zhpan.indicator.base.BaseIndicatorView$mOnPageChangeCallback$1
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                this.this$0.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                this.this$0.onPageSelected(position);
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int state) {
                this.this$0.onPageScrollStateChanged(state);
            }
        };
        this.mIndicatorOptions = new IndicatorOptions();
    }

    public final IndicatorOptions getMIndicatorOptions() {
        return this.mIndicatorOptions;
    }

    public final void setMIndicatorOptions(IndicatorOptions indicatorOptions) {
        Intrinsics.checkParameterIsNotNull(indicatorOptions, "<set-?>");
        this.mIndicatorOptions = indicatorOptions;
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int position) {
        if (getSlideMode() == 0) {
            setCurrentPosition(position);
            setSlideProgress(0.0f);
            invalidate();
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (getSlideMode() == 0 || getPageSize() <= 1) {
            return;
        }
        scrollSlider(position, positionOffset);
        invalidate();
    }

    private final void scrollSlider(int position, float positionOffset) {
        if (this.mIndicatorOptions.getSlideMode() == 4 || this.mIndicatorOptions.getSlideMode() == 5) {
            setCurrentPosition(position);
            setSlideProgress(positionOffset);
        } else if (position % getPageSize() != getPageSize() - 1) {
            setCurrentPosition(position);
            setSlideProgress(positionOffset);
        } else if (positionOffset < 0.5d) {
            setCurrentPosition(position);
            setSlideProgress(0.0f);
        } else {
            setCurrentPosition(0);
            setSlideProgress(0.0f);
        }
    }

    public void notifyDataChanged() {
        setupViewPager();
        requestLayout();
        invalidate();
    }

    private final void setupViewPager() {
        ViewPager viewPager = this.mViewPager;
        if (viewPager != null) {
            if (viewPager != null) {
                viewPager.removeOnPageChangeListener(this);
            }
            ViewPager viewPager2 = this.mViewPager;
            if (viewPager2 != null) {
                viewPager2.addOnPageChangeListener(this);
            }
            ViewPager viewPager3 = this.mViewPager;
            if (viewPager3 != null && viewPager3.getAdapter() != null) {
                ViewPager viewPager4 = this.mViewPager;
                if (viewPager4 == null) {
                    Intrinsics.throwNpe();
                }
                PagerAdapter adapter = viewPager4.getAdapter();
                if (adapter == null) {
                    Intrinsics.throwNpe();
                }
                Intrinsics.checkExpressionValueIsNotNull(adapter, "mViewPager!!.adapter!!");
                setPageSize(adapter.getCount());
            }
        }
        ViewPager2 viewPager22 = this.mViewPager2;
        if (viewPager22 != null) {
            if (viewPager22 != null) {
                viewPager22.unregisterOnPageChangeCallback(this.mOnPageChangeCallback);
            }
            ViewPager2 viewPager23 = this.mViewPager2;
            if (viewPager23 != null) {
                viewPager23.registerOnPageChangeCallback(this.mOnPageChangeCallback);
            }
            ViewPager2 viewPager24 = this.mViewPager2;
            if (viewPager24 == null || viewPager24.getAdapter() == null) {
                return;
            }
            ViewPager2 viewPager25 = this.mViewPager2;
            if (viewPager25 == null) {
                Intrinsics.throwNpe();
            }
            RecyclerView.Adapter adapter2 = viewPager25.getAdapter();
            if (adapter2 == null) {
                Intrinsics.throwNpe();
            }
            Intrinsics.checkExpressionValueIsNotNull(adapter2, "mViewPager2!!.adapter!!");
            setPageSize(adapter2.getItemCount());
        }
    }

    public final float getNormalSlideWidth() {
        return this.mIndicatorOptions.getNormalSliderWidth();
    }

    public final void setNormalSlideWidth(float normalSliderWidth) {
        this.mIndicatorOptions.setNormalSliderWidth(normalSliderWidth);
    }

    public final float getCheckedSlideWidth() {
        return this.mIndicatorOptions.getCheckedSliderWidth();
    }

    public final void setCheckedSlideWidth(float checkedSliderWidth) {
        this.mIndicatorOptions.setCheckedSliderWidth(checkedSliderWidth);
    }

    public final float getCheckedSliderWidth() {
        return this.mIndicatorOptions.getCheckedSliderWidth();
    }

    public final void setCurrentPosition(int currentPosition) {
        this.mIndicatorOptions.setCurrentPosition(currentPosition);
    }

    public final int getCurrentPosition() {
        return this.mIndicatorOptions.getCurrentPosition();
    }

    public final void getIndicatorGap(float indicatorGap) {
        this.mIndicatorOptions.setSliderGap(indicatorGap);
    }

    public final void setIndicatorGap(float indicatorGap) {
        this.mIndicatorOptions.setSliderGap(indicatorGap);
    }

    public final void setCheckedColor(int normalColor) {
        this.mIndicatorOptions.setCheckedSliderColor(normalColor);
    }

    public final int getCheckedColor() {
        return this.mIndicatorOptions.getCheckedSliderColor();
    }

    public final void setNormalColor(int normalColor) {
        this.mIndicatorOptions.setNormalSliderColor(normalColor);
    }

    public final float getSlideProgress() {
        return this.mIndicatorOptions.getSlideProgress();
    }

    public final void setSlideProgress(float slideProgress) {
        this.mIndicatorOptions.setSlideProgress(slideProgress);
    }

    public final int getPageSize() {
        return this.mIndicatorOptions.getPageSize();
    }

    public final BaseIndicatorView setPageSize(int pageSize) {
        this.mIndicatorOptions.setPageSize(pageSize);
        return this;
    }

    public final BaseIndicatorView setSliderColor(int normalColor, int selectedColor) {
        this.mIndicatorOptions.setSliderColor(normalColor, selectedColor);
        return this;
    }

    public final BaseIndicatorView setSliderWidth(float sliderWidth) {
        this.mIndicatorOptions.setSliderWidth(sliderWidth);
        return this;
    }

    public final BaseIndicatorView setSliderWidth(float normalSliderWidth, float selectedSliderWidth) {
        this.mIndicatorOptions.setSliderWidth(normalSliderWidth, selectedSliderWidth);
        return this;
    }

    public final BaseIndicatorView setSliderGap(float sliderGap) {
        this.mIndicatorOptions.setSliderGap(sliderGap);
        return this;
    }

    public final int getSlideMode() {
        return this.mIndicatorOptions.getSlideMode();
    }

    public final BaseIndicatorView setSlideMode(int slideMode) {
        this.mIndicatorOptions.setSlideMode(slideMode);
        return this;
    }

    public final BaseIndicatorView setIndicatorStyle(int indicatorStyle) {
        this.mIndicatorOptions.setIndicatorStyle(indicatorStyle);
        return this;
    }

    public final BaseIndicatorView setSliderHeight(float sliderHeight) {
        this.mIndicatorOptions.setSliderHeight(sliderHeight);
        return this;
    }

    public final void setupWithViewPager(ViewPager viewPager) {
        Intrinsics.checkParameterIsNotNull(viewPager, "viewPager");
        this.mViewPager = viewPager;
        notifyDataChanged();
    }

    public final void setupWithViewPager(ViewPager2 viewPager2) {
        Intrinsics.checkParameterIsNotNull(viewPager2, "viewPager2");
        this.mViewPager2 = viewPager2;
        notifyDataChanged();
    }

    public final void showIndicatorWhenOneItem(boolean showIndicatorWhenOneItem) {
        this.mIndicatorOptions.setShowIndicatorOneItem(showIndicatorWhenOneItem);
    }

    public void setIndicatorOptions(IndicatorOptions options) {
        Intrinsics.checkParameterIsNotNull(options, "options");
        this.mIndicatorOptions = options;
    }
}
