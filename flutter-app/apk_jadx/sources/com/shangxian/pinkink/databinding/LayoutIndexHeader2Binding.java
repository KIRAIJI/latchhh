package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;
import com.zhpan.bannerview.BannerViewPager;
import net.lucode.hackware.magicindicator.MagicIndicator;

/* JADX INFO: loaded from: classes.dex */
public final class LayoutIndexHeader2Binding implements ViewBinding {
    public final BannerViewPager bannerView;
    public final ImageView ivDeviceType;
    public final LinearLayout llCreate;
    public final LinearLayout llCreateByAI;
    public final MagicIndicator magicIndicator;
    private final LinearLayout rootView;

    private LayoutIndexHeader2Binding(LinearLayout rootView, BannerViewPager bannerView, ImageView ivDeviceType, LinearLayout llCreate, LinearLayout llCreateByAI, MagicIndicator magicIndicator) {
        this.rootView = rootView;
        this.bannerView = bannerView;
        this.ivDeviceType = ivDeviceType;
        this.llCreate = llCreate;
        this.llCreateByAI = llCreateByAI;
        this.magicIndicator = magicIndicator;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static LayoutIndexHeader2Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static LayoutIndexHeader2Binding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.layout_index_header2, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static LayoutIndexHeader2Binding bind(View rootView) {
        int i = R.id.bannerView;
        BannerViewPager bannerViewPager = (BannerViewPager) ViewBindings.findChildViewById(rootView, R.id.bannerView);
        if (bannerViewPager != null) {
            i = R.id.ivDeviceType;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivDeviceType);
            if (imageView != null) {
                i = R.id.llCreate;
                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llCreate);
                if (linearLayout != null) {
                    i = R.id.llCreateByAI;
                    LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llCreateByAI);
                    if (linearLayout2 != null) {
                        i = R.id.magicIndicator;
                        MagicIndicator magicIndicator = (MagicIndicator) ViewBindings.findChildViewById(rootView, R.id.magicIndicator);
                        if (magicIndicator != null) {
                            return new LayoutIndexHeader2Binding((LinearLayout) rootView, bannerViewPager, imageView, linearLayout, linearLayout2, magicIndicator);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
