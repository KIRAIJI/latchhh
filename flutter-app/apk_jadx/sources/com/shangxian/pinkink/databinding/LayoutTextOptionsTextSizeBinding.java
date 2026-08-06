package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class LayoutTextOptionsTextSizeBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final SeekBar sbFontSize;
    public final TextView tvFontSize;

    private LayoutTextOptionsTextSizeBinding(LinearLayout rootView, SeekBar sbFontSize, TextView tvFontSize) {
        this.rootView = rootView;
        this.sbFontSize = sbFontSize;
        this.tvFontSize = tvFontSize;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static LayoutTextOptionsTextSizeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static LayoutTextOptionsTextSizeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.layout_text_options_text_size, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static LayoutTextOptionsTextSizeBinding bind(View rootView) {
        int i = R.id.sbFontSize;
        SeekBar seekBar = (SeekBar) ViewBindings.findChildViewById(rootView, R.id.sbFontSize);
        if (seekBar != null) {
            i = R.id.tvFontSize;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvFontSize);
            if (textView != null) {
                return new LayoutTextOptionsTextSizeBinding((LinearLayout) rootView, seekBar, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
