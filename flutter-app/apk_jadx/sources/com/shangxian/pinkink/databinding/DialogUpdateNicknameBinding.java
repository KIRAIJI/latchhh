package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class DialogUpdateNicknameBinding implements ViewBinding {
    public final EditText etNickName;
    private final LinearLayout rootView;
    public final TextView tvCancel;
    public final TextView tvConfirm;

    private DialogUpdateNicknameBinding(LinearLayout rootView, EditText etNickName, TextView tvCancel, TextView tvConfirm) {
        this.rootView = rootView;
        this.etNickName = etNickName;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogUpdateNicknameBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogUpdateNicknameBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_update_nickname, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogUpdateNicknameBinding bind(View rootView) {
        int i = R.id.etNickName;
        EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.etNickName);
        if (editText != null) {
            i = R.id.tvCancel;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvCancel);
            if (textView != null) {
                i = R.id.tvConfirm;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvConfirm);
                if (textView2 != null) {
                    return new DialogUpdateNicknameBinding((LinearLayout) rootView, editText, textView, textView2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
