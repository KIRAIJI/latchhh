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
public final class ActivityFontLibraryBinding implements ViewBinding {
    public final LayoutPageLoadingBinding pageLoadingView;
    private final LinearLayout rootView;
    public final RecyclerView rvFont;
    public final LayoutWhiteTitleBarBinding titleBar;

    private ActivityFontLibraryBinding(LinearLayout rootView, LayoutPageLoadingBinding pageLoadingView, RecyclerView rvFont, LayoutWhiteTitleBarBinding titleBar) {
        this.rootView = rootView;
        this.pageLoadingView = pageLoadingView;
        this.rvFont = rvFont;
        this.titleBar = titleBar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityFontLibraryBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityFontLibraryBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_font_library, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityFontLibraryBinding bind(View rootView) {
        int i = R.id.pageLoadingView;
        View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.pageLoadingView);
        if (viewFindChildViewById != null) {
            LayoutPageLoadingBinding layoutPageLoadingBindingBind = LayoutPageLoadingBinding.bind(viewFindChildViewById);
            int i2 = R.id.rvFont;
            RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.rvFont);
            if (recyclerView != null) {
                i2 = R.id.titleBar;
                View viewFindChildViewById2 = ViewBindings.findChildViewById(rootView, R.id.titleBar);
                if (viewFindChildViewById2 != null) {
                    return new ActivityFontLibraryBinding((LinearLayout) rootView, layoutPageLoadingBindingBind, recyclerView, LayoutWhiteTitleBarBinding.bind(viewFindChildViewById2));
                }
            }
            i = i2;
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
