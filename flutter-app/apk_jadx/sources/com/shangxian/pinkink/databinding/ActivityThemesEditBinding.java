package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityThemesEditBinding implements ViewBinding {
    public final FrameLayout flCanvas;
    public final ImageView ivBackground;
    public final ImageView ivNext;
    public final LinearLayout llAddBackground;
    public final LinearLayout llAddImage;
    public final LinearLayout llAddText;
    public final LinearLayout llContent;
    private final LinearLayout rootView;
    public final LayoutTitleBarBinding titleBar;

    private ActivityThemesEditBinding(LinearLayout rootView, FrameLayout flCanvas, ImageView ivBackground, ImageView ivNext, LinearLayout llAddBackground, LinearLayout llAddImage, LinearLayout llAddText, LinearLayout llContent, LayoutTitleBarBinding titleBar) {
        this.rootView = rootView;
        this.flCanvas = flCanvas;
        this.ivBackground = ivBackground;
        this.ivNext = ivNext;
        this.llAddBackground = llAddBackground;
        this.llAddImage = llAddImage;
        this.llAddText = llAddText;
        this.llContent = llContent;
        this.titleBar = titleBar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityThemesEditBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityThemesEditBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_themes_edit, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityThemesEditBinding bind(View rootView) {
        int i = R.id.flCanvas;
        FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(rootView, R.id.flCanvas);
        if (frameLayout != null) {
            i = R.id.ivBackground;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivBackground);
            if (imageView != null) {
                i = R.id.ivNext;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivNext);
                if (imageView2 != null) {
                    i = R.id.llAddBackground;
                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llAddBackground);
                    if (linearLayout != null) {
                        i = R.id.llAddImage;
                        LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llAddImage);
                        if (linearLayout2 != null) {
                            i = R.id.llAddText;
                            LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llAddText);
                            if (linearLayout3 != null) {
                                i = R.id.llContent;
                                LinearLayout linearLayout4 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llContent);
                                if (linearLayout4 != null) {
                                    i = R.id.titleBar;
                                    View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.titleBar);
                                    if (viewFindChildViewById != null) {
                                        return new ActivityThemesEditBinding((LinearLayout) rootView, frameLayout, imageView, imageView2, linearLayout, linearLayout2, linearLayout3, linearLayout4, LayoutTitleBarBinding.bind(viewFindChildViewById));
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
