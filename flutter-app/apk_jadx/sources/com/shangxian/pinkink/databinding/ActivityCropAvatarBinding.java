package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;
import com.theartofdev.edmodo.cropper.CropImageView;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityCropAvatarBinding implements ViewBinding {
    public final CropImageView cropImageView;
    public final LinearLayout llContent;
    public final LinearLayout llCropImageComplete;
    private final LinearLayout rootView;
    public final LayoutWhiteTitleBarBinding titleBar;

    private ActivityCropAvatarBinding(LinearLayout rootView, CropImageView cropImageView, LinearLayout llContent, LinearLayout llCropImageComplete, LayoutWhiteTitleBarBinding titleBar) {
        this.rootView = rootView;
        this.cropImageView = cropImageView;
        this.llContent = llContent;
        this.llCropImageComplete = llCropImageComplete;
        this.titleBar = titleBar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityCropAvatarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityCropAvatarBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_crop_avatar, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityCropAvatarBinding bind(View rootView) {
        int i = R.id.cropImageView;
        CropImageView cropImageView = (CropImageView) ViewBindings.findChildViewById(rootView, R.id.cropImageView);
        if (cropImageView != null) {
            i = R.id.llContent;
            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llContent);
            if (linearLayout != null) {
                i = R.id.llCropImageComplete;
                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llCropImageComplete);
                if (linearLayout2 != null) {
                    i = R.id.titleBar;
                    View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.titleBar);
                    if (viewFindChildViewById != null) {
                        return new ActivityCropAvatarBinding((LinearLayout) rootView, cropImageView, linearLayout, linearLayout2, LayoutWhiteTitleBarBinding.bind(viewFindChildViewById));
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
