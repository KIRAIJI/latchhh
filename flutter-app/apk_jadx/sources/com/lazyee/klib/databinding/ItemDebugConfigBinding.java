package com.lazyee.klib.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import com.lazyee.klib.R;

/* JADX INFO: loaded from: classes.dex */
public final class ItemDebugConfigBinding implements ViewBinding {
    public final CardView cardView;
    public final FrameLayout flContent;
    public final ImageView ivSelected;
    private final CardView rootView;

    private ItemDebugConfigBinding(CardView cardView, CardView cardView2, FrameLayout frameLayout, ImageView imageView) {
        this.rootView = cardView;
        this.cardView = cardView2;
        this.flContent = frameLayout;
        this.ivSelected = imageView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public CardView getRoot() {
        return this.rootView;
    }

    public static ItemDebugConfigBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ItemDebugConfigBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(R.layout.item_debug_config, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemDebugConfigBinding bind(View view) {
        CardView cardView = (CardView) view;
        int i = R.id.flContent;
        FrameLayout frameLayout = (FrameLayout) view.findViewById(i);
        if (frameLayout != null) {
            i = R.id.ivSelected;
            ImageView imageView = (ImageView) view.findViewById(i);
            if (imageView != null) {
                return new ItemDebugConfigBinding(cardView, cardView, frameLayout, imageView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
