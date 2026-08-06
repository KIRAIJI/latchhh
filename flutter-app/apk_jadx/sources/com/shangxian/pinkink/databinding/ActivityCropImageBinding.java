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
public final class ActivityCropImageBinding implements ViewBinding {
    public final CropImageView cropImageView;
    public final LinearLayout llContent;
    public final LinearLayout llCropImageComplete;
    public final LinearLayout llFreeCrop;
    public final LinearLayout llRotateCropRect;
    private final LinearLayout rootView;
    public final LayoutWhiteTitleBarBinding titleBar;

    private ActivityCropImageBinding(LinearLayout rootView, CropImageView cropImageView, LinearLayout llContent, LinearLayout llCropImageComplete, LinearLayout llFreeCrop, LinearLayout llRotateCropRect, LayoutWhiteTitleBarBinding titleBar) {
        this.rootView = rootView;
        this.cropImageView = cropImageView;
        this.llContent = llContent;
        this.llCropImageComplete = llCropImageComplete;
        this.llFreeCrop = llFreeCrop;
        this.llRotateCropRect = llRotateCropRect;
        this.titleBar = titleBar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityCropImageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityCropImageBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_crop_image, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityCropImageBinding bind(View rootView) {
        int i = R.id.cropImageView;
        CropImageView cropImageView = (CropImageView) ViewBindings.findChildViewById(rootView, R.id.cropImageView);
        if (cropImageView != null) {
            i = R.id.llContent;
            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llContent);
            if (linearLayout != null) {
                i = R.id.llCropImageComplete;
                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llCropImageComplete);
                if (linearLayout2 != null) {
                    i = R.id.llFreeCrop;
                    LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llFreeCrop);
                    if (linearLayout3 != null) {
                        i = R.id.llRotateCropRect;
                        LinearLayout linearLayout4 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llRotateCropRect);
                        if (linearLayout4 != null) {
                            i = R.id.titleBar;
                            View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.titleBar);
                            if (viewFindChildViewById != null) {
                                return new ActivityCropImageBinding((LinearLayout) rootView, cropImageView, linearLayout, linearLayout2, linearLayout3, linearLayout4, LayoutWhiteTitleBarBinding.bind(viewFindChildViewById));
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
