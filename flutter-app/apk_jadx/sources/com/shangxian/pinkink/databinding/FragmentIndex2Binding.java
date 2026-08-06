package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.lazyee.klib.widget.PageStateSwitcher;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class FragmentIndex2Binding implements ViewBinding {
    public final LinearLayout contentView;
    public final ImageView ivBlueToothConnectState;
    public final PageStateSwitcher pageStateSwitcher;
    public final SmartRefreshLayout refreshLayout;
    private final LinearLayout rootView;
    public final RecyclerView rvContent;
    public final LayoutTitleBarBinding titleBar;

    private FragmentIndex2Binding(LinearLayout rootView, LinearLayout contentView, ImageView ivBlueToothConnectState, PageStateSwitcher pageStateSwitcher, SmartRefreshLayout refreshLayout, RecyclerView rvContent, LayoutTitleBarBinding titleBar) {
        this.rootView = rootView;
        this.contentView = contentView;
        this.ivBlueToothConnectState = ivBlueToothConnectState;
        this.pageStateSwitcher = pageStateSwitcher;
        this.refreshLayout = refreshLayout;
        this.rvContent = rvContent;
        this.titleBar = titleBar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentIndex2Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentIndex2Binding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_index2, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentIndex2Binding bind(View rootView) {
        int i = R.id.contentView;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.contentView);
        if (linearLayout != null) {
            i = R.id.ivBlueToothConnectState;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivBlueToothConnectState);
            if (imageView != null) {
                i = R.id.pageStateSwitcher;
                PageStateSwitcher pageStateSwitcher = (PageStateSwitcher) ViewBindings.findChildViewById(rootView, R.id.pageStateSwitcher);
                if (pageStateSwitcher != null) {
                    i = R.id.refreshLayout;
                    SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) ViewBindings.findChildViewById(rootView, R.id.refreshLayout);
                    if (smartRefreshLayout != null) {
                        i = R.id.rvContent;
                        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.rvContent);
                        if (recyclerView != null) {
                            i = R.id.titleBar;
                            View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.titleBar);
                            if (viewFindChildViewById != null) {
                                return new FragmentIndex2Binding((LinearLayout) rootView, linearLayout, imageView, pageStateSwitcher, smartRefreshLayout, recyclerView, LayoutTitleBarBinding.bind(viewFindChildViewById));
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
