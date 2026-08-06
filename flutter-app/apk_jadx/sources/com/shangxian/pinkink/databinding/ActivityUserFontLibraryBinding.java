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
public final class ActivityUserFontLibraryBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final RecyclerView rvFont;
    public final LayoutWhiteTitleBarBinding titleBar;

    private ActivityUserFontLibraryBinding(LinearLayout rootView, RecyclerView rvFont, LayoutWhiteTitleBarBinding titleBar) {
        this.rootView = rootView;
        this.rvFont = rvFont;
        this.titleBar = titleBar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityUserFontLibraryBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityUserFontLibraryBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_user_font_library, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityUserFontLibraryBinding bind(View rootView) {
        int i = R.id.rvFont;
        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.rvFont);
        if (recyclerView != null) {
            i = R.id.titleBar;
            View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.titleBar);
            if (viewFindChildViewById != null) {
                return new ActivityUserFontLibraryBinding((LinearLayout) rootView, recyclerView, LayoutWhiteTitleBarBinding.bind(viewFindChildViewById));
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
