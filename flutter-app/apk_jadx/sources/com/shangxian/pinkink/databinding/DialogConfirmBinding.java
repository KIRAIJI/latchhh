package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class DialogConfirmBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final TextView tvCancel;
    public final TextView tvConfirm;
    public final TextView tvMessage;

    private DialogConfirmBinding(LinearLayout rootView, TextView tvCancel, TextView tvConfirm, TextView tvMessage) {
        this.rootView = rootView;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
        this.tvMessage = tvMessage;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogConfirmBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogConfirmBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_confirm, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogConfirmBinding bind(View rootView) {
        int i = R.id.tvCancel;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvCancel);
        if (textView != null) {
            i = R.id.tvConfirm;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvConfirm);
            if (textView2 != null) {
                i = R.id.tvMessage;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvMessage);
                if (textView3 != null) {
                    return new DialogConfirmBinding((LinearLayout) rootView, textView, textView2, textView3);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
