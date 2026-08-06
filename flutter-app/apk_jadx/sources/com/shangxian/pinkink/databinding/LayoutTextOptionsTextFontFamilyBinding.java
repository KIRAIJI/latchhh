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
public final class LayoutTextOptionsTextFontFamilyBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final RecyclerView rvFontFamily;

    private LayoutTextOptionsTextFontFamilyBinding(LinearLayout rootView, RecyclerView rvFontFamily) {
        this.rootView = rootView;
        this.rvFontFamily = rvFontFamily;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static LayoutTextOptionsTextFontFamilyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static LayoutTextOptionsTextFontFamilyBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.layout_text_options_text_font_family, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static LayoutTextOptionsTextFontFamilyBinding bind(View rootView) {
        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.rvFontFamily);
        if (recyclerView != null) {
            return new LayoutTextOptionsTextFontFamilyBinding((LinearLayout) rootView, recyclerView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(R.id.rvFontFamily)));
    }
}
