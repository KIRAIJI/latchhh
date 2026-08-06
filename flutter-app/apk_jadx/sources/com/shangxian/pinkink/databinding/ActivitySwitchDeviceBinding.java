package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivitySwitchDeviceBinding implements ViewBinding {
    public final ImageView ivDeviceTypeNext;
    public final LinearLayout llDeviceTypeBlueTooth;
    public final LinearLayout llDeviceTypeNFC;
    private final ScrollView rootView;

    private ActivitySwitchDeviceBinding(ScrollView rootView, ImageView ivDeviceTypeNext, LinearLayout llDeviceTypeBlueTooth, LinearLayout llDeviceTypeNFC) {
        this.rootView = rootView;
        this.ivDeviceTypeNext = ivDeviceTypeNext;
        this.llDeviceTypeBlueTooth = llDeviceTypeBlueTooth;
        this.llDeviceTypeNFC = llDeviceTypeNFC;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static ActivitySwitchDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivitySwitchDeviceBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_switch_device, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivitySwitchDeviceBinding bind(View rootView) {
        int i = R.id.ivDeviceTypeNext;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivDeviceTypeNext);
        if (imageView != null) {
            i = R.id.llDeviceTypeBlueTooth;
            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llDeviceTypeBlueTooth);
            if (linearLayout != null) {
                i = R.id.llDeviceTypeNFC;
                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llDeviceTypeNFC);
                if (linearLayout2 != null) {
                    return new ActivitySwitchDeviceBinding((ScrollView) rootView, imageView, linearLayout, linearLayout2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
