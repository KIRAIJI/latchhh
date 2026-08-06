package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityDeviceScanBinding implements ViewBinding {
    public final LinearLayout llCancel;
    private final LinearLayout rootView;

    private ActivityDeviceScanBinding(LinearLayout rootView, LinearLayout llCancel) {
        this.rootView = rootView;
        this.llCancel = llCancel;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityDeviceScanBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityDeviceScanBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_device_scan, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityDeviceScanBinding bind(View rootView) {
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llCancel);
        if (linearLayout != null) {
            return new ActivityDeviceScanBinding((LinearLayout) rootView, linearLayout);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(R.id.llCancel)));
    }
}
