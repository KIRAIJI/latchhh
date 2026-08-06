package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ItemTextOptionsFontFamilyBinding implements ViewBinding {
    public final ImageView ivFontFamilyExample;
    public final LinearLayout llFontFamily;
    private final LinearLayout rootView;
    public final TextView tvFontFamilyExample;
    public final TextView tvFontFamilyName;

    private ItemTextOptionsFontFamilyBinding(LinearLayout rootView, ImageView ivFontFamilyExample, LinearLayout llFontFamily, TextView tvFontFamilyExample, TextView tvFontFamilyName) {
        this.rootView = rootView;
        this.ivFontFamilyExample = ivFontFamilyExample;
        this.llFontFamily = llFontFamily;
        this.tvFontFamilyExample = tvFontFamilyExample;
        this.tvFontFamilyName = tvFontFamilyName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemTextOptionsFontFamilyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemTextOptionsFontFamilyBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.item_text_options_font_family, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemTextOptionsFontFamilyBinding bind(View rootView) {
        int i = R.id.ivFontFamilyExample;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivFontFamilyExample);
        if (imageView != null) {
            i = R.id.llFontFamily;
            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llFontFamily);
            if (linearLayout != null) {
                i = R.id.tvFontFamilyExample;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvFontFamilyExample);
                if (textView != null) {
                    i = R.id.tvFontFamilyName;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvFontFamilyName);
                    if (textView2 != null) {
                        return new ItemTextOptionsFontFamilyBinding((LinearLayout) rootView, imageView, linearLayout, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
