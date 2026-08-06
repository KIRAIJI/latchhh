package com.zhpan.bannerview.manager;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import com.zhpan.bannerview.transform.OverlapPageTransformer;
import com.zhpan.bannerview.transform.ScaleInTransformer;

/* JADX INFO: loaded from: classes.dex */
public class BannerManager {
    private final AttributeController mAttributeController;
    private BannerOptions mBannerOptions;
    private final CompositePageTransformer mCompositePageTransformer;
    private ViewPager2.PageTransformer mDefaultPageTransformer;
    private MarginPageTransformer mMarginPageTransformer;

    public BannerManager() {
        BannerOptions bannerOptions = new BannerOptions();
        this.mBannerOptions = bannerOptions;
        this.mAttributeController = new AttributeController(bannerOptions);
        this.mCompositePageTransformer = new CompositePageTransformer();
    }

    public BannerOptions getBannerOptions() {
        if (this.mBannerOptions == null) {
            this.mBannerOptions = new BannerOptions();
        }
        return this.mBannerOptions;
    }

    public void initAttrs(Context context, AttributeSet attributeSet) {
        this.mAttributeController.init(context, attributeSet);
    }

    public CompositePageTransformer getCompositePageTransformer() {
        return this.mCompositePageTransformer;
    }

    public void addTransformer(ViewPager2.PageTransformer pageTransformer) {
        this.mCompositePageTransformer.addTransformer(pageTransformer);
    }

    public void removeTransformer(ViewPager2.PageTransformer pageTransformer) {
        this.mCompositePageTransformer.removeTransformer(pageTransformer);
    }

    public void removeMarginPageTransformer() {
        MarginPageTransformer marginPageTransformer = this.mMarginPageTransformer;
        if (marginPageTransformer != null) {
            this.mCompositePageTransformer.removeTransformer(marginPageTransformer);
        }
    }

    public void removeDefaultPageTransformer() {
        ViewPager2.PageTransformer pageTransformer = this.mDefaultPageTransformer;
        if (pageTransformer != null) {
            this.mCompositePageTransformer.removeTransformer(pageTransformer);
        }
    }

    public void setPageMargin(int i) {
        this.mBannerOptions.setPageMargin(i);
    }

    public void createMarginTransformer() {
        removeMarginPageTransformer();
        MarginPageTransformer marginPageTransformer = new MarginPageTransformer(this.mBannerOptions.getPageMargin());
        this.mMarginPageTransformer = marginPageTransformer;
        this.mCompositePageTransformer.addTransformer(marginPageTransformer);
    }

    public void setMultiPageStyle(boolean z, float f) {
        removeDefaultPageTransformer();
        if (z && Build.VERSION.SDK_INT >= 21) {
            this.mDefaultPageTransformer = new OverlapPageTransformer(this.mBannerOptions.getOrientation(), f, 0.0f, 1.0f, 0.0f);
        } else {
            this.mDefaultPageTransformer = new ScaleInTransformer(f);
        }
        this.mCompositePageTransformer.addTransformer(this.mDefaultPageTransformer);
    }
}
