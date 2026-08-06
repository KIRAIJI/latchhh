package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityCancelAccountStep1Binding implements ViewBinding {
    public final Button btnNext;
    private final LinearLayout rootView;
    public final LayoutWhiteTitleBarBinding titleBar;

    private ActivityCancelAccountStep1Binding(LinearLayout rootView, Button btnNext, LayoutWhiteTitleBarBinding titleBar) {
        this.rootView = rootView;
        this.btnNext = btnNext;
        this.titleBar = titleBar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityCancelAccountStep1Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityCancelAccountStep1Binding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_cancel_account_step1, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityCancelAccountStep1Binding bind(View rootView) {
        int i = R.id.btnNext;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btnNext);
        if (button != null) {
            i = R.id.titleBar;
            View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.titleBar);
            if (viewFindChildViewById != null) {
                return new ActivityCancelAccountStep1Binding((LinearLayout) rootView, button, LayoutWhiteTitleBarBinding.bind(viewFindChildViewById));
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
