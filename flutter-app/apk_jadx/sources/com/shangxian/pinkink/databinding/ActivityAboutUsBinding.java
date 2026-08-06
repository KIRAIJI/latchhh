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
public final class ActivityAboutUsBinding implements ViewBinding {
    public final LinearLayout llCheckUpdate;
    public final LinearLayout llFirmwareVersion;
    public final LinearLayout llPrivacyProtocol;
    public final LinearLayout llUserProtocol;
    private final LinearLayout rootView;
    public final LayoutWhiteTitleBarBinding titleBar;
    public final TextView tvFirmwareVersion;

    private ActivityAboutUsBinding(LinearLayout rootView, LinearLayout llCheckUpdate, LinearLayout llFirmwareVersion, LinearLayout llPrivacyProtocol, LinearLayout llUserProtocol, LayoutWhiteTitleBarBinding titleBar, TextView tvFirmwareVersion) {
        this.rootView = rootView;
        this.llCheckUpdate = llCheckUpdate;
        this.llFirmwareVersion = llFirmwareVersion;
        this.llPrivacyProtocol = llPrivacyProtocol;
        this.llUserProtocol = llUserProtocol;
        this.titleBar = titleBar;
        this.tvFirmwareVersion = tvFirmwareVersion;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityAboutUsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityAboutUsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_about_us, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityAboutUsBinding bind(View rootView) {
        int i = R.id.llCheckUpdate;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llCheckUpdate);
        if (linearLayout != null) {
            i = R.id.llFirmwareVersion;
            LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llFirmwareVersion);
            if (linearLayout2 != null) {
                i = R.id.llPrivacyProtocol;
                LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llPrivacyProtocol);
                if (linearLayout3 != null) {
                    i = R.id.llUserProtocol;
                    LinearLayout linearLayout4 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llUserProtocol);
                    if (linearLayout4 != null) {
                        i = R.id.titleBar;
                        View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.titleBar);
                        if (viewFindChildViewById != null) {
                            LayoutWhiteTitleBarBinding layoutWhiteTitleBarBindingBind = LayoutWhiteTitleBarBinding.bind(viewFindChildViewById);
                            i = R.id.tvFirmwareVersion;
                            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvFirmwareVersion);
                            if (textView != null) {
                                return new ActivityAboutUsBinding((LinearLayout) rootView, linearLayout, linearLayout2, linearLayout3, linearLayout4, layoutWhiteTitleBarBindingBind, textView);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
