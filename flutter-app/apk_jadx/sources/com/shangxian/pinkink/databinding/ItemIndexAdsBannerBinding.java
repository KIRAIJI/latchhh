package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;
import com.zhpan.bannerview.BannerViewPager;

/* JADX INFO: loaded from: classes.dex */
public final class ItemIndexAdsBannerBinding implements ViewBinding {
    public final BannerViewPager bannerView;
    private final LinearLayout rootView;

    private ItemIndexAdsBannerBinding(LinearLayout rootView, BannerViewPager bannerView) {
        this.rootView = rootView;
        this.bannerView = bannerView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemIndexAdsBannerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemIndexAdsBannerBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.item_index_ads_banner, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemIndexAdsBannerBinding bind(View rootView) {
        BannerViewPager bannerViewPager = (BannerViewPager) ViewBindings.findChildViewById(rootView, R.id.bannerView);
        if (bannerViewPager != null) {
            return new ItemIndexAdsBannerBinding((LinearLayout) rootView, bannerViewPager);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(R.id.bannerView)));
    }
}
