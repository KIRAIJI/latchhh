package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.lazyee.klib.widget.PageStateSwitcher;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityThemeDetailBinding implements ViewBinding {
    public final LinearLayout contentView;
    public final ImageView ivCollect;
    public final ImageView ivImage;
    public final LinearLayout llCollect;
    public final LinearLayout llContent;
    public final LinearLayout llEdit;
    public final LinearLayout llInstall;
    public final PageStateSwitcher pageStateSwitcher;
    private final LinearLayout rootView;
    public final LayoutWhiteTitleBarBinding titleBar;

    private ActivityThemeDetailBinding(LinearLayout rootView, LinearLayout contentView, ImageView ivCollect, ImageView ivImage, LinearLayout llCollect, LinearLayout llContent, LinearLayout llEdit, LinearLayout llInstall, PageStateSwitcher pageStateSwitcher, LayoutWhiteTitleBarBinding titleBar) {
        this.rootView = rootView;
        this.contentView = contentView;
        this.ivCollect = ivCollect;
        this.ivImage = ivImage;
        this.llCollect = llCollect;
        this.llContent = llContent;
        this.llEdit = llEdit;
        this.llInstall = llInstall;
        this.pageStateSwitcher = pageStateSwitcher;
        this.titleBar = titleBar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityThemeDetailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityThemeDetailBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_theme_detail, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityThemeDetailBinding bind(View rootView) {
        int i = R.id.contentView;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.contentView);
        if (linearLayout != null) {
            i = R.id.ivCollect;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivCollect);
            if (imageView != null) {
                i = R.id.ivImage;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivImage);
                if (imageView2 != null) {
                    i = R.id.llCollect;
                    LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llCollect);
                    if (linearLayout2 != null) {
                        i = R.id.llContent;
                        LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llContent);
                        if (linearLayout3 != null) {
                            i = R.id.llEdit;
                            LinearLayout linearLayout4 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llEdit);
                            if (linearLayout4 != null) {
                                i = R.id.llInstall;
                                LinearLayout linearLayout5 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llInstall);
                                if (linearLayout5 != null) {
                                    i = R.id.pageStateSwitcher;
                                    PageStateSwitcher pageStateSwitcher = (PageStateSwitcher) ViewBindings.findChildViewById(rootView, R.id.pageStateSwitcher);
                                    if (pageStateSwitcher != null) {
                                        i = R.id.titleBar;
                                        View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.titleBar);
                                        if (viewFindChildViewById != null) {
                                            return new ActivityThemeDetailBinding((LinearLayout) rootView, linearLayout, imageView, imageView2, linearLayout2, linearLayout3, linearLayout4, linearLayout5, pageStateSwitcher, LayoutWhiteTitleBarBinding.bind(viewFindChildViewById));
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
