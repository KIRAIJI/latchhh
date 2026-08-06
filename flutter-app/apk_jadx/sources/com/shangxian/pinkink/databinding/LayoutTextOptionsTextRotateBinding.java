package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class LayoutTextOptionsTextRotateBinding implements ViewBinding {
    public final LinearLayout llRotate0Angle;
    public final LinearLayout llRotate180Angle;
    public final LinearLayout llRotate270Angle;
    public final LinearLayout llRotate90Angle;
    private final LinearLayout rootView;

    private LayoutTextOptionsTextRotateBinding(LinearLayout rootView, LinearLayout llRotate0Angle, LinearLayout llRotate180Angle, LinearLayout llRotate270Angle, LinearLayout llRotate90Angle) {
        this.rootView = rootView;
        this.llRotate0Angle = llRotate0Angle;
        this.llRotate180Angle = llRotate180Angle;
        this.llRotate270Angle = llRotate270Angle;
        this.llRotate90Angle = llRotate90Angle;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static LayoutTextOptionsTextRotateBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static LayoutTextOptionsTextRotateBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.layout_text_options_text_rotate, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static LayoutTextOptionsTextRotateBinding bind(View rootView) {
        int i = R.id.llRotate0Angle;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llRotate0Angle);
        if (linearLayout != null) {
            i = R.id.llRotate180Angle;
            LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llRotate180Angle);
            if (linearLayout2 != null) {
                i = R.id.llRotate270Angle;
                LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llRotate270Angle);
                if (linearLayout3 != null) {
                    i = R.id.llRotate90Angle;
                    LinearLayout linearLayout4 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llRotate90Angle);
                    if (linearLayout4 != null) {
                        return new LayoutTextOptionsTextRotateBinding((LinearLayout) rootView, linearLayout, linearLayout2, linearLayout3, linearLayout4);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
