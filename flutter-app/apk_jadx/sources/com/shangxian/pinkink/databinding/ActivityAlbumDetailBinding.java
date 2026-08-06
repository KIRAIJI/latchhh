package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.lazyee.klib.widget.PageStateSwitcher;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityAlbumDetailBinding implements ViewBinding {
    public final LinearLayout contentView;
    public final ImageView ivAdd;
    public final ImageView ivEdit;
    public final LinearLayout llBottom;
    public final PageStateSwitcher pageStateSwitcher;
    public final SmartRefreshLayout refreshLayout;
    private final LinearLayout rootView;
    public final RecyclerView rvThemes;
    public final LayoutTitleBarBinding titleBar;
    public final TextView tvDelete;
    public final TextView tvRemainingTip;
    public final TextView tvSelectedCount;
    public final TextView tvSend;

    private ActivityAlbumDetailBinding(LinearLayout rootView, LinearLayout contentView, ImageView ivAdd, ImageView ivEdit, LinearLayout llBottom, PageStateSwitcher pageStateSwitcher, SmartRefreshLayout refreshLayout, RecyclerView rvThemes, LayoutTitleBarBinding titleBar, TextView tvDelete, TextView tvRemainingTip, TextView tvSelectedCount, TextView tvSend) {
        this.rootView = rootView;
        this.contentView = contentView;
        this.ivAdd = ivAdd;
        this.ivEdit = ivEdit;
        this.llBottom = llBottom;
        this.pageStateSwitcher = pageStateSwitcher;
        this.refreshLayout = refreshLayout;
        this.rvThemes = rvThemes;
        this.titleBar = titleBar;
        this.tvDelete = tvDelete;
        this.tvRemainingTip = tvRemainingTip;
        this.tvSelectedCount = tvSelectedCount;
        this.tvSend = tvSend;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityAlbumDetailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityAlbumDetailBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_album_detail, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityAlbumDetailBinding bind(View rootView) {
        int i = R.id.contentView;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.contentView);
        if (linearLayout != null) {
            i = R.id.ivAdd;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivAdd);
            if (imageView != null) {
                i = R.id.ivEdit;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivEdit);
                if (imageView2 != null) {
                    i = R.id.llBottom;
                    LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llBottom);
                    if (linearLayout2 != null) {
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
                                        i = R.id.tvDelete;
                                        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvDelete);
                                        if (textView != null) {
                                            i = R.id.tvRemainingTip;
                                            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvRemainingTip);
                                            if (textView2 != null) {
                                                i = R.id.tvSelectedCount;
                                                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvSelectedCount);
                                                if (textView3 != null) {
                                                    i = R.id.tvSend;
                                                    TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvSend);
                                                    if (textView4 != null) {
                                                        return new ActivityAlbumDetailBinding((LinearLayout) rootView, linearLayout, imageView, imageView2, linearLayout2, pageStateSwitcher, smartRefreshLayout, recyclerView, layoutTitleBarBindingBind, textView, textView2, textView3, textView4);
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
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
