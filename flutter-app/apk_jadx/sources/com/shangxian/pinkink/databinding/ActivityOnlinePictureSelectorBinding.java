package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.lazyee.klib.widget.PageStateSwitcher;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityOnlinePictureSelectorBinding implements ViewBinding {
    public final LinearLayout contentView;
    public final PageStateSwitcher pageStateSwitcher;
    public final SmartRefreshLayout refreshLayout;
    private final LinearLayout rootView;
    public final RecyclerView rvThemes;
    public final LayoutTitleBarBinding titleBar;
    public final TextView tvComplete;
    public final TextView tvRemainingTip;
    public final TextView tvSelectedCount;

    private ActivityOnlinePictureSelectorBinding(LinearLayout rootView, LinearLayout contentView, PageStateSwitcher pageStateSwitcher, SmartRefreshLayout refreshLayout, RecyclerView rvThemes, LayoutTitleBarBinding titleBar, TextView tvComplete, TextView tvRemainingTip, TextView tvSelectedCount) {
        this.rootView = rootView;
        this.contentView = contentView;
        this.pageStateSwitcher = pageStateSwitcher;
        this.refreshLayout = refreshLayout;
        this.rvThemes = rvThemes;
        this.titleBar = titleBar;
        this.tvComplete = tvComplete;
        this.tvRemainingTip = tvRemainingTip;
        this.tvSelectedCount = tvSelectedCount;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityOnlinePictureSelectorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityOnlinePictureSelectorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_online_picture_selector, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityOnlinePictureSelectorBinding bind(View rootView) {
        int i = R.id.contentView;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.contentView);
        if (linearLayout != null) {
            i = R.id.pageStateSwitcher;
            PageStateSwitcher pageStateSwitcher = (PageStateSwitcher) ViewBindings.findChildViewById(rootView, R.id.pageStateSwitcher);
            if (pageStateSwitcher != null) {
                i = R.id.refreshLayout;
                SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) ViewBindings.findChildViewById(rootView, R.id.refreshLayout);
                if (smartRefreshLayout != null) {
                    i = R.id.rvThemes;
                    RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.rvThemes);
                    if (recyclerView != null) {
                        i = R.id.titleBar;
                        View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.titleBar);
                        if (viewFindChildViewById != null) {
                            LayoutTitleBarBinding layoutTitleBarBindingBind = LayoutTitleBarBinding.bind(viewFindChildViewById);
                            i = R.id.tvComplete;
                            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvComplete);
                            if (textView != null) {
                                i = R.id.tvRemainingTip;
                                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvRemainingTip);
                                if (textView2 != null) {
                                    i = R.id.tvSelectedCount;
                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvSelectedCount);
                                    if (textView3 != null) {
                                        return new ActivityOnlinePictureSelectorBinding((LinearLayout) rootView, linearLayout, pageStateSwitcher, smartRefreshLayout, recyclerView, layoutTitleBarBindingBind, textView, textView2, textView3);
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
