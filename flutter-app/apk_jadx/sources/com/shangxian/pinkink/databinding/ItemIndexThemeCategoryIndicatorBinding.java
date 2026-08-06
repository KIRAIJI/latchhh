package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;
import net.lucode.hackware.magicindicator.MagicIndicator;

/* JADX INFO: loaded from: classes.dex */
public final class ItemIndexThemeCategoryIndicatorBinding implements ViewBinding {
    public final MagicIndicator magicIndicator;
    private final LinearLayout rootView;

    private ItemIndexThemeCategoryIndicatorBinding(LinearLayout rootView, MagicIndicator magicIndicator) {
        this.rootView = rootView;
        this.magicIndicator = magicIndicator;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemIndexThemeCategoryIndicatorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemIndexThemeCategoryIndicatorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.item_index_theme_category_indicator, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemIndexThemeCategoryIndicatorBinding bind(View rootView) {
        MagicIndicator magicIndicator = (MagicIndicator) ViewBindings.findChildViewById(rootView, R.id.magicIndicator);
        if (magicIndicator != null) {
            return new ItemIndexThemeCategoryIndicatorBinding((LinearLayout) rootView, magicIndicator);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(R.id.magicIndicator)));
    }
}
