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
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class FragmentAlbumBinding implements ViewBinding {
    public final ImageView ivAdd;
    public final ImageView ivEdit;
    public final ImageView ivExitEditMode;
    public final LinearLayout llEditModeTitleBar;
    private final LinearLayout rootView;
    public final RecyclerView rvAlbum;
    public final LayoutTitleBarBinding titleBar;
    public final TextView tvSelectCount;

    private FragmentAlbumBinding(LinearLayout rootView, ImageView ivAdd, ImageView ivEdit, ImageView ivExitEditMode, LinearLayout llEditModeTitleBar, RecyclerView rvAlbum, LayoutTitleBarBinding titleBar, TextView tvSelectCount) {
        this.rootView = rootView;
        this.ivAdd = ivAdd;
        this.ivEdit = ivEdit;
        this.ivExitEditMode = ivExitEditMode;
        this.llEditModeTitleBar = llEditModeTitleBar;
        this.rvAlbum = rvAlbum;
        this.titleBar = titleBar;
        this.tvSelectCount = tvSelectCount;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentAlbumBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentAlbumBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_album, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentAlbumBinding bind(View rootView) {
        int i = R.id.ivAdd;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivAdd);
        if (imageView != null) {
            i = R.id.ivEdit;
            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivEdit);
            if (imageView2 != null) {
                i = R.id.ivExitEditMode;
                ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivExitEditMode);
                if (imageView3 != null) {
                    i = R.id.llEditModeTitleBar;
                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llEditModeTitleBar);
                    if (linearLayout != null) {
                        i = R.id.rvAlbum;
                        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, R.id.rvAlbum);
                        if (recyclerView != null) {
                            i = R.id.titleBar;
                            View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.titleBar);
                            if (viewFindChildViewById != null) {
                                LayoutTitleBarBinding layoutTitleBarBindingBind = LayoutTitleBarBinding.bind(viewFindChildViewById);
                                i = R.id.tvSelectCount;
                                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvSelectCount);
                                if (textView != null) {
                                    return new FragmentAlbumBinding((LinearLayout) rootView, imageView, imageView2, imageView3, linearLayout, recyclerView, layoutTitleBarBindingBind, textView);
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
