package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ItemThemesBinding implements ViewBinding {
    public final ImageView ivDelete;
    public final ImageView ivSelect;
    public final RoundedImageView ivThemes;
    public final LinearLayout llCreate;
    public final RelativeLayout rlThemes;
    private final LinearLayout rootView;

    private ItemThemesBinding(LinearLayout rootView, ImageView ivDelete, ImageView ivSelect, RoundedImageView ivThemes, LinearLayout llCreate, RelativeLayout rlThemes) {
        this.rootView = rootView;
        this.ivDelete = ivDelete;
        this.ivSelect = ivSelect;
        this.ivThemes = ivThemes;
        this.llCreate = llCreate;
        this.rlThemes = rlThemes;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemThemesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemThemesBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.item_themes, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemThemesBinding bind(View rootView) {
        int i = R.id.ivDelete;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivDelete);
        if (imageView != null) {
            i = R.id.ivSelect;
            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivSelect);
            if (imageView2 != null) {
                i = R.id.ivThemes;
                RoundedImageView roundedImageView = (RoundedImageView) ViewBindings.findChildViewById(rootView, R.id.ivThemes);
                if (roundedImageView != null) {
                    i = R.id.llCreate;
                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llCreate);
                    if (linearLayout != null) {
                        i = R.id.rlThemes;
                        RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(rootView, R.id.rlThemes);
                        if (relativeLayout != null) {
                            return new ItemThemesBinding((LinearLayout) rootView, imageView, imageView2, roundedImageView, linearLayout, relativeLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
