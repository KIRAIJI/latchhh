package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import com.shangxian.pinkink.R;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class LayoutPageLoadingBinding implements ViewBinding {
    public final LinearLayout contentView;
    private final LinearLayout rootView;

    private LayoutPageLoadingBinding(LinearLayout rootView, LinearLayout contentView) {
        this.rootView = rootView;
        this.contentView = contentView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static LayoutPageLoadingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static LayoutPageLoadingBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.layout_page_loading, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static LayoutPageLoadingBinding bind(View rootView) {
        Objects.requireNonNull(rootView, "rootView");
        LinearLayout linearLayout = (LinearLayout) rootView;
        return new LayoutPageLoadingBinding(linearLayout, linearLayout);
    }
}
