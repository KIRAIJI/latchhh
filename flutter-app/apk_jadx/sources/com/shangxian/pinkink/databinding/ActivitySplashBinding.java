package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.viewbinding.ViewBinding;
import com.shangxian.pinkink.R;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class ActivitySplashBinding implements ViewBinding {
    private final RelativeLayout rootView;

    private ActivitySplashBinding(RelativeLayout rootView) {
        this.rootView = rootView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static ActivitySplashBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivitySplashBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_splash, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivitySplashBinding bind(View rootView) {
        Objects.requireNonNull(rootView, "rootView");
        return new ActivitySplashBinding((RelativeLayout) rootView);
    }
}
