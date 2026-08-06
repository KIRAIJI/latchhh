package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityCancelAccountStep2Binding implements ViewBinding {
    public final Button btnConfirmCancel;
    public final EditText etVerifyCode;
    public final ImageView ivGetVerifyCode;
    public final ImageView ivVerifyCodeClear;
    private final LinearLayout rootView;
    public final LayoutWhiteTitleBarBinding titleBar;

    private ActivityCancelAccountStep2Binding(LinearLayout rootView, Button btnConfirmCancel, EditText etVerifyCode, ImageView ivGetVerifyCode, ImageView ivVerifyCodeClear, LayoutWhiteTitleBarBinding titleBar) {
        this.rootView = rootView;
        this.btnConfirmCancel = btnConfirmCancel;
        this.etVerifyCode = etVerifyCode;
        this.ivGetVerifyCode = ivGetVerifyCode;
        this.ivVerifyCodeClear = ivVerifyCodeClear;
        this.titleBar = titleBar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityCancelAccountStep2Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityCancelAccountStep2Binding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_cancel_account_step2, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityCancelAccountStep2Binding bind(View rootView) {
        int i = R.id.btnConfirmCancel;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btnConfirmCancel);
        if (button != null) {
            i = R.id.etVerifyCode;
            EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.etVerifyCode);
            if (editText != null) {
                i = R.id.ivGetVerifyCode;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivGetVerifyCode);
                if (imageView != null) {
                    i = R.id.ivVerifyCodeClear;
                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivVerifyCodeClear);
                    if (imageView2 != null) {
                        i = R.id.titleBar;
                        View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.titleBar);
                        if (viewFindChildViewById != null) {
                            return new ActivityCancelAccountStep2Binding((LinearLayout) rootView, button, editText, imageView, imageView2, LayoutWhiteTitleBarBinding.bind(viewFindChildViewById));
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
