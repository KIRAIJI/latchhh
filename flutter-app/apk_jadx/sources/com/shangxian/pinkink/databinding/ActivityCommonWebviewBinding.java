package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityCommonWebviewBinding implements ViewBinding {
    public final ProgressBar pbProgress;
    private final LinearLayout rootView;
    public final LayoutTitleBarBinding titleBar;
    public final WebView wvContent;

    private ActivityCommonWebviewBinding(LinearLayout rootView, ProgressBar pbProgress, LayoutTitleBarBinding titleBar, WebView wvContent) {
        this.rootView = rootView;
        this.pbProgress = pbProgress;
        this.titleBar = titleBar;
        this.wvContent = wvContent;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityCommonWebviewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityCommonWebviewBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_common_webview, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityCommonWebviewBinding bind(View rootView) {
        int i = R.id.pbProgress;
        ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, R.id.pbProgress);
        if (progressBar != null) {
            i = R.id.titleBar;
            View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.titleBar);
            if (viewFindChildViewById != null) {
                LayoutTitleBarBinding layoutTitleBarBindingBind = LayoutTitleBarBinding.bind(viewFindChildViewById);
                WebView webView = (WebView) ViewBindings.findChildViewById(rootView, R.id.wvContent);
                if (webView != null) {
                    return new ActivityCommonWebviewBinding((LinearLayout) rootView, progressBar, layoutTitleBarBindingBind, webView);
                }
                i = R.id.wvContent;
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
