package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityMainBinding implements ViewBinding {
    public final FrameLayout flContent;
    public final LinearLayout llAlbum;
    public final LinearLayout llIndex;
    public final LinearLayout llMine;
    private final LinearLayout rootView;

    private ActivityMainBinding(LinearLayout rootView, FrameLayout flContent, LinearLayout llAlbum, LinearLayout llIndex, LinearLayout llMine) {
        this.rootView = rootView;
        this.flContent = flContent;
        this.llAlbum = llAlbum;
        this.llIndex = llIndex;
        this.llMine = llMine;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityMainBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityMainBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_main, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityMainBinding bind(View rootView) {
        int i = R.id.flContent;
        FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(rootView, R.id.flContent);
        if (frameLayout != null) {
            i = R.id.llAlbum;
            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llAlbum);
            if (linearLayout != null) {
                i = R.id.llIndex;
                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llIndex);
                if (linearLayout2 != null) {
                    i = R.id.llMine;
                    LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llMine);
                    if (linearLayout3 != null) {
                        return new ActivityMainBinding((LinearLayout) rootView, frameLayout, linearLayout, linearLayout2, linearLayout3);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
