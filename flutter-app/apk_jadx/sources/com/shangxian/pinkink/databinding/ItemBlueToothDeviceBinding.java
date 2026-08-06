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
public final class ItemBlueToothDeviceBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final TextView tvAddDevice;
    public final TextView tvDeviceMac;
    public final TextView tvDeviceName;

    private ItemBlueToothDeviceBinding(LinearLayout rootView, TextView tvAddDevice, TextView tvDeviceMac, TextView tvDeviceName) {
        this.rootView = rootView;
        this.tvAddDevice = tvAddDevice;
        this.tvDeviceMac = tvDeviceMac;
        this.tvDeviceName = tvDeviceName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemBlueToothDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemBlueToothDeviceBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.item_blue_tooth_device, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemBlueToothDeviceBinding bind(View rootView) {
        int i = R.id.tvAddDevice;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvAddDevice);
        if (textView != null) {
            i = R.id.tvDeviceMac;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvDeviceMac);
            if (textView2 != null) {
                i = R.id.tvDeviceName;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvDeviceName);
                if (textView3 != null) {
                    return new ItemBlueToothDeviceBinding((LinearLayout) rootView, textView, textView2, textView3);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
