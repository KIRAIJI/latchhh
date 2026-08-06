package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ItemIndexQuickEntryBinding implements ViewBinding {
    public final ImageView ivDeviceType;
    public final LinearLayout llCreate;
    public final LinearLayout llCreateByAI;
    private final LinearLayout rootView;

    private ItemIndexQuickEntryBinding(LinearLayout rootView, ImageView ivDeviceType, LinearLayout llCreate, LinearLayout llCreateByAI) {
        this.rootView = rootView;
        this.ivDeviceType = ivDeviceType;
        this.llCreate = llCreate;
        this.llCreateByAI = llCreateByAI;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemIndexQuickEntryBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemIndexQuickEntryBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.item_index_quick_entry, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemIndexQuickEntryBinding bind(View rootView) {
        int i = R.id.ivDeviceType;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivDeviceType);
        if (imageView != null) {
            i = R.id.llCreate;
            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llCreate);
            if (linearLayout != null) {
                i = R.id.llCreateByAI;
                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llCreateByAI);
                if (linearLayout2 != null) {
                    return new ItemIndexQuickEntryBinding((LinearLayout) rootView, imageView, linearLayout, linearLayout2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
