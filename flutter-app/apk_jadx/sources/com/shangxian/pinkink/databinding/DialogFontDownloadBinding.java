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
public final class DialogFontDownloadBinding implements ViewBinding {
    public final LinearLayout llProgress;
    public final LinearLayout llProgressBg;
    private final LinearLayout rootView;
    public final TextView tvDownloadProgress;

    private DialogFontDownloadBinding(LinearLayout rootView, LinearLayout llProgress, LinearLayout llProgressBg, TextView tvDownloadProgress) {
        this.rootView = rootView;
        this.llProgress = llProgress;
        this.llProgressBg = llProgressBg;
        this.tvDownloadProgress = tvDownloadProgress;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogFontDownloadBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogFontDownloadBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_font_download, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogFontDownloadBinding bind(View rootView) {
        int i = R.id.llProgress;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llProgress);
        if (linearLayout != null) {
            i = R.id.llProgressBg;
            LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llProgressBg);
            if (linearLayout2 != null) {
                i = R.id.tvDownloadProgress;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvDownloadProgress);
                if (textView != null) {
                    return new DialogFontDownloadBinding((LinearLayout) rootView, linearLayout, linearLayout2, textView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
