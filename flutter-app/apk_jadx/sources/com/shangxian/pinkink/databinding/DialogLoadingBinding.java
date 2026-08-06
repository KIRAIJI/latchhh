package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import com.shangxian.pinkink.R;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class DialogLoadingBinding implements ViewBinding {
    private final LinearLayout rootView;

    private DialogLoadingBinding(LinearLayout rootView) {
        this.rootView = rootView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogLoadingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogLoadingBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_loading, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogLoadingBinding bind(View rootView) {
        Objects.requireNonNull(rootView, "rootView");
        return new DialogLoadingBinding((LinearLayout) rootView);
    }
}
