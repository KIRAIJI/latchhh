package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ItemIndexBannerImageBinding implements ViewBinding {
    public final RoundedImageView ivBanner;
    private final RelativeLayout rootView;

    private ItemIndexBannerImageBinding(RelativeLayout rootView, RoundedImageView ivBanner) {
        this.rootView = rootView;
        this.ivBanner = ivBanner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static ItemIndexBannerImageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemIndexBannerImageBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.item_index_banner_image, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemIndexBannerImageBinding bind(View rootView) {
        RoundedImageView roundedImageView = (RoundedImageView) ViewBindings.findChildViewById(rootView, R.id.ivBanner);
        if (roundedImageView != null) {
            return new ItemIndexBannerImageBinding((RelativeLayout) rootView, roundedImageView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(R.id.ivBanner)));
    }
}
