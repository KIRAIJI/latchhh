package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class LayoutTextOptionsTextColorBinding implements ViewBinding {
    public final ImageView ivColorBlack;
    public final ImageView ivColorRed;
    public final ImageView ivColorWhite;
    private final LinearLayout rootView;

    private LayoutTextOptionsTextColorBinding(LinearLayout rootView, ImageView ivColorBlack, ImageView ivColorRed, ImageView ivColorWhite) {
        this.rootView = rootView;
        this.ivColorBlack = ivColorBlack;
        this.ivColorRed = ivColorRed;
        this.ivColorWhite = ivColorWhite;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static LayoutTextOptionsTextColorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static LayoutTextOptionsTextColorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.layout_text_options_text_color, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static LayoutTextOptionsTextColorBinding bind(View rootView) {
        int i = R.id.ivColorBlack;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivColorBlack);
        if (imageView != null) {
            i = R.id.ivColorRed;
            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivColorRed);
            if (imageView2 != null) {
                i = R.id.ivColorWhite;
                ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivColorWhite);
                if (imageView3 != null) {
                    return new LayoutTextOptionsTextColorBinding((LinearLayout) rootView, imageView, imageView2, imageView3);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
