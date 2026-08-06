package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.lazyee.klib.widget.PageStateSwitcher;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityTransThemesBinding implements ViewBinding {
    public final ConstraintLayout clContent;
    public final LinearLayout contentView;
    public final ImageView ivEdit;
    public final ImageView ivImage;
    public final ImageView ivSave;
    public final ImageView ivShare;
    public final LinearLayout llGrayScaleEffect;
    public final LinearLayout llJitterEffect;
    public final LinearLayout llLevelEffect;
    public final LinearLayout llSyncNow;
    public final PageStateSwitcher pageStateSwitcher;
    private final LinearLayout rootView;
    public final LayoutWhiteTitleBarBinding titleBar;

    private ActivityTransThemesBinding(LinearLayout rootView, ConstraintLayout clContent, LinearLayout contentView, ImageView ivEdit, ImageView ivImage, ImageView ivSave, ImageView ivShare, LinearLayout llGrayScaleEffect, LinearLayout llJitterEffect, LinearLayout llLevelEffect, LinearLayout llSyncNow, PageStateSwitcher pageStateSwitcher, LayoutWhiteTitleBarBinding titleBar) {
        this.rootView = rootView;
        this.clContent = clContent;
        this.contentView = contentView;
        this.ivEdit = ivEdit;
        this.ivImage = ivImage;
        this.ivSave = ivSave;
        this.ivShare = ivShare;
        this.llGrayScaleEffect = llGrayScaleEffect;
        this.llJitterEffect = llJitterEffect;
        this.llLevelEffect = llLevelEffect;
        this.llSyncNow = llSyncNow;
        this.pageStateSwitcher = pageStateSwitcher;
        this.titleBar = titleBar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityTransThemesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityTransThemesBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_trans_themes, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityTransThemesBinding bind(View rootView) {
        int i = R.id.clContent;
        ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(rootView, R.id.clContent);
        if (constraintLayout != null) {
            i = R.id.contentView;
            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.contentView);
            if (linearLayout != null) {
                i = R.id.ivEdit;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivEdit);
                if (imageView != null) {
                    i = R.id.ivImage;
                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivImage);
                    if (imageView2 != null) {
                        i = R.id.ivSave;
                        ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivSave);
                        if (imageView3 != null) {
                            i = R.id.ivShare;
                            ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivShare);
                            if (imageView4 != null) {
                                i = R.id.llGrayScaleEffect;
                                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llGrayScaleEffect);
                                if (linearLayout2 != null) {
                                    i = R.id.llJitterEffect;
                                    LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llJitterEffect);
                                    if (linearLayout3 != null) {
                                        i = R.id.llLevelEffect;
                                        LinearLayout linearLayout4 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llLevelEffect);
                                        if (linearLayout4 != null) {
                                            i = R.id.llSyncNow;
                                            LinearLayout linearLayout5 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llSyncNow);
                                            if (linearLayout5 != null) {
                                                i = R.id.pageStateSwitcher;
                                                PageStateSwitcher pageStateSwitcher = (PageStateSwitcher) ViewBindings.findChildViewById(rootView, R.id.pageStateSwitcher);
                                                if (pageStateSwitcher != null) {
                                                    i = R.id.titleBar;
                                                    View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.titleBar);
                                                    if (viewFindChildViewById != null) {
                                                        return new ActivityTransThemesBinding((LinearLayout) rootView, constraintLayout, linearLayout, imageView, imageView2, imageView3, imageView4, linearLayout2, linearLayout3, linearLayout4, linearLayout5, pageStateSwitcher, LayoutWhiteTitleBarBinding.bind(viewFindChildViewById));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
