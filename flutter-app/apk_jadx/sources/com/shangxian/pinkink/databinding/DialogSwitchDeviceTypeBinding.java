package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class DialogSwitchDeviceTypeBinding implements ViewBinding {
    public final LinearLayout llDialog;
    private final RelativeLayout rootView;
    public final TextView tvCancel;
    public final TextView tvDeviceTypeBlueTooth;
    public final TextView tvDeviceTypeNfc;

    private DialogSwitchDeviceTypeBinding(RelativeLayout rootView, LinearLayout llDialog, TextView tvCancel, TextView tvDeviceTypeBlueTooth, TextView tvDeviceTypeNfc) {
        this.rootView = rootView;
        this.llDialog = llDialog;
        this.tvCancel = tvCancel;
        this.tvDeviceTypeBlueTooth = tvDeviceTypeBlueTooth;
        this.tvDeviceTypeNfc = tvDeviceTypeNfc;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static DialogSwitchDeviceTypeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogSwitchDeviceTypeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_switch_device_type, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogSwitchDeviceTypeBinding bind(View rootView) {
        int i = R.id.llDialog;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llDialog);
        if (linearLayout != null) {
            i = R.id.tvCancel;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvCancel);
            if (textView != null) {
                i = R.id.tvDeviceTypeBlueTooth;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvDeviceTypeBlueTooth);
                if (textView2 != null) {
                    i = R.id.tvDeviceTypeNfc;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvDeviceTypeNfc);
                    if (textView3 != null) {
                        return new DialogSwitchDeviceTypeBinding((RelativeLayout) rootView, linearLayout, textView, textView2, textView3);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
