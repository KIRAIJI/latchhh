package com.lazyee.klib.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.viewbinding.ViewBinding;
import com.lazyee.klib.R;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class LayoutDebugConfigBinding implements ViewBinding {
    public final FrameLayout frameLayout;
    private final FrameLayout rootView;

    private LayoutDebugConfigBinding(FrameLayout frameLayout, FrameLayout frameLayout2) {
        this.rootView = frameLayout;
        this.frameLayout = frameLayout2;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static LayoutDebugConfigBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static LayoutDebugConfigBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(R.layout.layout_debug_config, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static LayoutDebugConfigBinding bind(View view) {
        Objects.requireNonNull(view, "rootView");
        FrameLayout frameLayout = (FrameLayout) view;
        return new LayoutDebugConfigBinding(frameLayout, frameLayout);
    }
}
