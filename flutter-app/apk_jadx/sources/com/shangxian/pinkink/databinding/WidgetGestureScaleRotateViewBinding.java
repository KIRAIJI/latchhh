package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class WidgetGestureScaleRotateViewBinding implements ViewBinding {
    public final FrameLayout flContent;
    public final ImageView ivEditDelete;
    public final ImageView ivEditDrag;
    private final RelativeLayout rootView;

    private WidgetGestureScaleRotateViewBinding(RelativeLayout rootView, FrameLayout flContent, ImageView ivEditDelete, ImageView ivEditDrag) {
        this.rootView = rootView;
        this.flContent = flContent;
        this.ivEditDelete = ivEditDelete;
        this.ivEditDrag = ivEditDrag;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static WidgetGestureScaleRotateViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static WidgetGestureScaleRotateViewBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.widget_gesture_scale_rotate_view, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static WidgetGestureScaleRotateViewBinding bind(View rootView) {
        int i = R.id.flContent;
        FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(rootView, R.id.flContent);
        if (frameLayout != null) {
            i = R.id.ivEditDelete;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivEditDelete);
            if (imageView != null) {
                i = R.id.ivEditDrag;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivEditDrag);
                if (imageView2 != null) {
                    return new WidgetGestureScaleRotateViewBinding((RelativeLayout) rootView, frameLayout, imageView, imageView2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
