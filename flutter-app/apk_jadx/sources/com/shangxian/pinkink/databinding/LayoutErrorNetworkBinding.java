package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class LayoutErrorNetworkBinding implements ViewBinding {
    public final LinearLayout contentView;
    private final LinearLayout rootView;
    public final TextView tvRefresh;

    private LayoutErrorNetworkBinding(LinearLayout rootView, LinearLayout contentView, TextView tvRefresh) {
        this.rootView = rootView;
        this.contentView = contentView;
        this.tvRefresh = tvRefresh;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static LayoutErrorNetworkBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static LayoutErrorNetworkBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.layout_error_network, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static LayoutErrorNetworkBinding bind(View rootView) {
        LinearLayout linearLayout = (LinearLayout) rootView;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvRefresh);
        if (textView != null) {
            return new LayoutErrorNetworkBinding(linearLayout, linearLayout, textView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(R.id.tvRefresh)));
    }
}
