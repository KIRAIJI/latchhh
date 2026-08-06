package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;
import com.zhpan.bannerview.BannerViewPager;

/* JADX INFO: loaded from: classes.dex */
public final class LayoutIndexHeaderBinding implements ViewBinding {
    public final BannerViewPager bannerView;
    public final ImageView ivDeviceType;
    public final ImageView ivGotoMyThemes;
    public final LinearLayout llAddDevice;
    public final LinearLayout llBluetoothDevice;
    private final LinearLayout rootView;
    public final TextView tvAddDevice;
    public final TextView tvDeviceConnectedState;
    public final TextView tvDeviceMac;
    public final TextView tvDeviceName;
    public final ImageView tvSwitchDevice;

    private LayoutIndexHeaderBinding(LinearLayout rootView, BannerViewPager bannerView, ImageView ivDeviceType, ImageView ivGotoMyThemes, LinearLayout llAddDevice, LinearLayout llBluetoothDevice, TextView tvAddDevice, TextView tvDeviceConnectedState, TextView tvDeviceMac, TextView tvDeviceName, ImageView tvSwitchDevice) {
        this.rootView = rootView;
        this.bannerView = bannerView;
        this.ivDeviceType = ivDeviceType;
        this.ivGotoMyThemes = ivGotoMyThemes;
        this.llAddDevice = llAddDevice;
        this.llBluetoothDevice = llBluetoothDevice;
        this.tvAddDevice = tvAddDevice;
        this.tvDeviceConnectedState = tvDeviceConnectedState;
        this.tvDeviceMac = tvDeviceMac;
        this.tvDeviceName = tvDeviceName;
        this.tvSwitchDevice = tvSwitchDevice;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static LayoutIndexHeaderBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static LayoutIndexHeaderBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.layout_index_header, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static LayoutIndexHeaderBinding bind(View rootView) {
        int i = R.id.bannerView;
        BannerViewPager bannerViewPager = (BannerViewPager) ViewBindings.findChildViewById(rootView, R.id.bannerView);
        if (bannerViewPager != null) {
            i = R.id.ivDeviceType;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivDeviceType);
            if (imageView != null) {
                i = R.id.ivGotoMyThemes;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivGotoMyThemes);
                if (imageView2 != null) {
                    i = R.id.llAddDevice;
                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llAddDevice);
                    if (linearLayout != null) {
                        i = R.id.llBluetoothDevice;
                        LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llBluetoothDevice);
                        if (linearLayout2 != null) {
                            i = R.id.tvAddDevice;
                            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvAddDevice);
                            if (textView != null) {
                                i = R.id.tvDeviceConnectedState;
                                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvDeviceConnectedState);
                                if (textView2 != null) {
                                    i = R.id.tvDeviceMac;
                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvDeviceMac);
                                    if (textView3 != null) {
                                        i = R.id.tvDeviceName;
                                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvDeviceName);
                                        if (textView4 != null) {
                                            i = R.id.tvSwitchDevice;
                                            ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.tvSwitchDevice);
                                            if (imageView3 != null) {
                                                return new LayoutIndexHeaderBinding((LinearLayout) rootView, bannerViewPager, imageView, imageView2, linearLayout, linearLayout2, textView, textView2, textView3, textView4, imageView3);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
