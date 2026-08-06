package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class WidgetGlobalLoadMoreViewBinding implements ViewBinding {
    public final LinearLayout llLoadMoreEnd;
    public final LinearLayout llLoadMoreFail;
    public final LinearLayout llRetry;
    public final ProgressBar loadingImageView;
    private final RelativeLayout rootView;

    private WidgetGlobalLoadMoreViewBinding(RelativeLayout rootView, LinearLayout llLoadMoreEnd, LinearLayout llLoadMoreFail, LinearLayout llRetry, ProgressBar loadingImageView) {
        this.rootView = rootView;
        this.llLoadMoreEnd = llLoadMoreEnd;
        this.llLoadMoreFail = llLoadMoreFail;
        this.llRetry = llRetry;
        this.loadingImageView = loadingImageView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static WidgetGlobalLoadMoreViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static WidgetGlobalLoadMoreViewBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.widget_global_load_more_view, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static WidgetGlobalLoadMoreViewBinding bind(View rootView) {
        int i = R.id.llLoadMoreEnd;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llLoadMoreEnd);
        if (linearLayout != null) {
            i = R.id.llLoadMoreFail;
            LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llLoadMoreFail);
            if (linearLayout2 != null) {
                i = R.id.llRetry;
                LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llRetry);
                if (linearLayout3 != null) {
                    i = R.id.loadingImageView;
                    ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, R.id.loadingImageView);
                    if (progressBar != null) {
                        return new WidgetGlobalLoadMoreViewBinding((RelativeLayout) rootView, linearLayout, linearLayout2, linearLayout3, progressBar);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
