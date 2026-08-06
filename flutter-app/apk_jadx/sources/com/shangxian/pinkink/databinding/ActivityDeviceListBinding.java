package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityDeviceListBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final RecyclerView rvDeviceList;
    public final LayoutWhiteTitleBarBinding titleBar;

    private ActivityDeviceListBinding(LinearLayout rootView, RecyclerView rvDeviceList, LayoutWhiteTitleBarBinding titleBar) {
        this.rootView = rootView;
        this.rvDeviceList = rvDeviceList;
        this.titleBar = titleBar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityDeviceListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityDeviceListBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_device_list, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityDeviceListBinding bind(View rootView) {
        int i = R.id.rvDeviceList;
        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.rvDeviceList);
        if (recyclerView != null) {
            i = R.id.titleBar;
            View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.titleBar);
            if (viewFindChildViewById != null) {
                return new ActivityDeviceListBinding((LinearLayout) rootView, recyclerView, LayoutWhiteTitleBarBinding.bind(viewFindChildViewById));
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
