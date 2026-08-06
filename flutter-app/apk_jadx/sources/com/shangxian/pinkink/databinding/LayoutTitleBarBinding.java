package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class LayoutTitleBarBinding implements ViewBinding {
    public final ImageView ivBack;
    private final RelativeLayout rootView;
    public final TextView tvTitle;

    private LayoutTitleBarBinding(RelativeLayout rootView, ImageView ivBack, TextView tvTitle) {
        this.rootView = rootView;
        this.ivBack = ivBack;
        this.tvTitle = tvTitle;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static LayoutTitleBarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static LayoutTitleBarBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.layout_title_bar, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static LayoutTitleBarBinding bind(View rootView) {
        int i = R.id.ivBack;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivBack);
        if (imageView != null) {
            i = R.id.tvTitle;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvTitle);
            if (textView != null) {
                return new LayoutTitleBarBinding((RelativeLayout) rootView, imageView, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
