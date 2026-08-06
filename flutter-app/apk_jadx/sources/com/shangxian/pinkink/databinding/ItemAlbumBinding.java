package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ItemAlbumBinding implements ViewBinding {
    public final ConstraintLayout clItem;
    public final RoundedImageView ivCover;
    public final ImageView ivRename;
    public final ImageView ivSelect;
    private final LinearLayout rootView;
    public final TextView tvAlbumName;
    public final TextView tvThemeCount;

    private ItemAlbumBinding(LinearLayout rootView, ConstraintLayout clItem, RoundedImageView ivCover, ImageView ivRename, ImageView ivSelect, TextView tvAlbumName, TextView tvThemeCount) {
        this.rootView = rootView;
        this.clItem = clItem;
        this.ivCover = ivCover;
        this.ivRename = ivRename;
        this.ivSelect = ivSelect;
        this.tvAlbumName = tvAlbumName;
        this.tvThemeCount = tvThemeCount;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemAlbumBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemAlbumBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.item_album, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemAlbumBinding bind(View rootView) {
        int i = R.id.clItem;
        ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(rootView, R.id.clItem);
        if (constraintLayout != null) {
            i = R.id.ivCover;
            RoundedImageView roundedImageView = (RoundedImageView) ViewBindings.findChildViewById(rootView, R.id.ivCover);
            if (roundedImageView != null) {
                i = R.id.ivRename;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivRename);
                if (imageView != null) {
                    i = R.id.ivSelect;
                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivSelect);
                    if (imageView2 != null) {
                        i = R.id.tvAlbumName;
                        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvAlbumName);
                        if (textView != null) {
                            i = R.id.tvThemeCount;
                            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvThemeCount);
                            if (textView2 != null) {
                                return new ItemAlbumBinding((LinearLayout) rootView, constraintLayout, roundedImageView, imageView, imageView2, textView, textView2);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
