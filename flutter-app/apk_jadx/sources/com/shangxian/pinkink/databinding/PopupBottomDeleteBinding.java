package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import com.shangxian.pinkink.R;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class PopupBottomDeleteBinding implements ViewBinding {
    private final LinearLayout rootView;

    private PopupBottomDeleteBinding(LinearLayout rootView) {
        this.rootView = rootView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static PopupBottomDeleteBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static PopupBottomDeleteBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.popup_bottom_delete, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static PopupBottomDeleteBinding bind(View rootView) {
        Objects.requireNonNull(rootView, "rootView");
        return new PopupBottomDeleteBinding((LinearLayout) rootView);
    }
}
