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
public final class DialogCreateAlbumBinding implements ViewBinding {
    public final EditText etAlbumName;
    private final LinearLayout rootView;
    public final TextView tvCancel;
    public final TextView tvConfirm;

    private DialogCreateAlbumBinding(LinearLayout rootView, EditText etAlbumName, TextView tvCancel, TextView tvConfirm) {
        this.rootView = rootView;
        this.etAlbumName = etAlbumName;
        this.tvCancel = tvCancel;
        this.tvConfirm = tvConfirm;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogCreateAlbumBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogCreateAlbumBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_create_album, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogCreateAlbumBinding bind(View rootView) {
        int i = R.id.etAlbumName;
        EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.etAlbumName);
        if (editText != null) {
            i = R.id.tvCancel;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvCancel);
            if (textView != null) {
                i = R.id.tvConfirm;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvConfirm);
                if (textView2 != null) {
                    return new DialogCreateAlbumBinding((LinearLayout) rootView, editText, textView, textView2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
