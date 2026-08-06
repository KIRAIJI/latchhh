package com.zhpan.bannerview.transform;

import android.view.View;
import androidx.viewpager2.widget.ViewPager2;
import com.zhpan.indicator.utils.IndicatorUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OverlapPageTransformer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0005H\u0016R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/zhpan/bannerview/transform/OverlapPageTransformer;", "Landroidx/viewpager2/widget/ViewPager2$PageTransformer;", "orientation", "", "minScale", "", "unSelectedItemRotation", "unSelectedItemAlpha", "itemGap", "(IFFFF)V", "scalingValue", "transformPage", "", "page", "Landroid/view/View;", "position", "bannerview_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
public final class OverlapPageTransformer implements ViewPager2.PageTransformer {
    private final float itemGap;
    private final float minScale;
    private final int orientation;
    private float scalingValue;
    private final float unSelectedItemAlpha;
    private final float unSelectedItemRotation;

    public OverlapPageTransformer(int i, float f, float f2, float f3, float f4) {
        this.orientation = i;
        this.minScale = f;
        this.unSelectedItemRotation = f2;
        this.unSelectedItemAlpha = f3;
        this.itemGap = f4;
        if (!(0.0f <= f && f <= 1.0f)) {
            throw new IllegalArgumentException("minScale value should be between 1.0 to 0.0".toString());
        }
        if (!(0.0f <= f3 && f3 <= 1.0f)) {
            throw new IllegalArgumentException("unSelectedItemAlpha value should be between 1.0 to 0.0".toString());
        }
        this.scalingValue = 0.2f;
    }

    public /* synthetic */ OverlapPageTransformer(int i, float f, float f2, float f3, float f4, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? 0.0f : f, (i2 & 4) != 0 ? 0.0f : f2, (i2 & 8) != 0 ? 0.0f : f3, (i2 & 16) != 0 ? 0.0f : f4);
    }

    @Override // androidx.viewpager2.widget.ViewPager2.PageTransformer
    public void transformPage(View page, float position) {
        int width;
        float fAbs;
        int width2;
        Intrinsics.checkNotNullParameter(page, "page");
        float f = this.minScale;
        this.scalingValue = ((double) f) >= 0.8d ? 0.2f : ((double) f) >= 0.6d ? 0.3f : 0.4f;
        page.setElevation(-Math.abs(position));
        float fMax = Math.max(1.0f - Math.abs(position * 0.5f), 0.5f);
        float f2 = this.unSelectedItemRotation;
        if (!(f2 == 0.0f)) {
            float f3 = 1 - fMax;
            if (position <= 0.0f) {
                f2 = -f2;
            }
            page.setRotationY(f3 * f2);
        }
        float fMax2 = Math.max(1.0f - Math.abs(this.scalingValue * position), this.minScale);
        page.setScaleX(fMax2);
        page.setScaleY(fMax2);
        int iDp2px = IndicatorUtils.dp2px(((int) this.itemGap) / 2);
        int i = this.orientation;
        if (i == 0) {
            float f4 = iDp2px * position;
            if (position > 0.0f) {
                width = -page.getWidth();
            } else {
                width = page.getWidth();
            }
            page.setTranslationX(f4 + (width * (1.0f - fMax2)));
        } else if (i == 1) {
            float f5 = iDp2px * position;
            if (position > 0.0f) {
                width2 = -page.getWidth();
            } else {
                width2 = page.getWidth();
            }
            page.setTranslationY(f5 + (width2 * (1.0f - fMax2)));
        } else {
            throw new IllegalArgumentException("Gives correct orientation value, ViewPager2.ORIENTATION_HORIZONTAL or ViewPager2.ORIENTATION_VERTICAL");
        }
        if (this.unSelectedItemAlpha == 1.0f) {
            return;
        }
        if (position >= -1.0f && position <= 1.0f) {
            fAbs = ((1 - Math.abs(position)) * 0.5f) + 0.5f;
        } else {
            fAbs = 0.5f / Math.abs(position * position);
        }
        page.setAlpha(fAbs);
    }
}
