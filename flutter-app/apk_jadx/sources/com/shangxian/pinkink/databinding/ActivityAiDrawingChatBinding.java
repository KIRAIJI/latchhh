package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.lazyee.klib.widget.PageStateSwitcher;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityAiDrawingChatBinding implements ViewBinding {
    public final LinearLayout contentView;
    public final EditText etMsg;
    public final ImageView ivSend;
    public final LinearLayout llBottom;
    public final PageStateSwitcher pageStateSwitcher;
    public final SmartRefreshLayout refreshLayout;
    private final LinearLayout rootView;
    public final RecyclerView rvContent;
    public final LayoutTitleBarBinding titleBar;

    private ActivityAiDrawingChatBinding(LinearLayout rootView, LinearLayout contentView, EditText etMsg, ImageView ivSend, LinearLayout llBottom, PageStateSwitcher pageStateSwitcher, SmartRefreshLayout refreshLayout, RecyclerView rvContent, LayoutTitleBarBinding titleBar) {
        this.rootView = rootView;
        this.contentView = contentView;
        this.etMsg = etMsg;
        this.ivSend = ivSend;
        this.llBottom = llBottom;
        this.pageStateSwitcher = pageStateSwitcher;
        this.refreshLayout = refreshLayout;
        this.rvContent = rvContent;
        this.titleBar = titleBar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityAiDrawingChatBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityAiDrawingChatBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_ai_drawing_chat, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityAiDrawingChatBinding bind(View rootView) {
        int i = R.id.contentView;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.contentView);
        if (linearLayout != null) {
            i = R.id.etMsg;
            EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.etMsg);
            if (editText != null) {
                i = R.id.ivSend;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivSend);
                if (imageView != null) {
                    i = R.id.llBottom;
                    LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llBottom);
                    if (linearLayout2 != null) {
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
                                        return new ActivityAiDrawingChatBinding((LinearLayout) rootView, linearLayout, editText, imageView, linearLayout2, pageStateSwitcher, smartRefreshLayout, recyclerView, LayoutTitleBarBinding.bind(viewFindChildViewById));
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
