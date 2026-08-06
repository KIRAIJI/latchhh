package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ItemPictureSelectorThemesBinding implements ViewBinding {
    public final ConstraintLayout clThemes;
    public final ImageView ivSelect;
    public final RoundedImageView ivThemes;
    private final LinearLayout rootView;

    private ItemPictureSelectorThemesBinding(LinearLayout rootView, ConstraintLayout clThemes, ImageView ivSelect, RoundedImageView ivThemes) {
        this.rootView = rootView;
        this.clThemes = clThemes;
        this.ivSelect = ivSelect;
        this.ivThemes = ivThemes;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemPictureSelectorThemesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemPictureSelectorThemesBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.item_picture_selector_themes, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemPictureSelectorThemesBinding bind(View rootView) {
        int i = R.id.clThemes;
        ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(rootView, R.id.clThemes);
        if (constraintLayout != null) {
            i = R.id.ivSelect;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivSelect);
            if (imageView != null) {
                i = R.id.ivThemes;
                RoundedImageView roundedImageView = (RoundedImageView) ViewBindings.findChildViewById(rootView, R.id.ivThemes);
                if (roundedImageView != null) {
                    return new ItemPictureSelectorThemesBinding((LinearLayout) rootView, constraintLayout, imageView, roundedImageView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
