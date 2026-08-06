package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager.widget.ViewPager;
import com.shangxian.pinkink.R;
import net.lucode.hackware.magicindicator.MagicIndicator;

/* JADX INFO: loaded from: classes.dex */
public final class DialogTextOptionsBinding implements ViewBinding {
    public final EditText etContent;
    public final ImageView ivClear;
    public final ImageView ivComplete;
    public final View keyboardView;
    public final LinearLayout llDialog;
    public final MagicIndicator magicIndicator;
    private final RelativeLayout rootView;
    public final ViewPager vpTextOptions;

    private DialogTextOptionsBinding(RelativeLayout rootView, EditText etContent, ImageView ivClear, ImageView ivComplete, View keyboardView, LinearLayout llDialog, MagicIndicator magicIndicator, ViewPager vpTextOptions) {
        this.rootView = rootView;
        this.etContent = etContent;
        this.ivClear = ivClear;
        this.ivComplete = ivComplete;
        this.keyboardView = keyboardView;
        this.llDialog = llDialog;
        this.magicIndicator = magicIndicator;
        this.vpTextOptions = vpTextOptions;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static DialogTextOptionsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogTextOptionsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_text_options, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogTextOptionsBinding bind(View rootView) {
        int i = R.id.etContent;
        EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.etContent);
        if (editText != null) {
            i = R.id.ivClear;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivClear);
            if (imageView != null) {
                i = R.id.ivComplete;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivComplete);
                if (imageView2 != null) {
                    i = R.id.keyboardView;
                    View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.keyboardView);
                    if (viewFindChildViewById != null) {
                        i = R.id.llDialog;
                        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llDialog);
                        if (linearLayout != null) {
                            i = R.id.magicIndicator;
                            MagicIndicator magicIndicator = (MagicIndicator) ViewBindings.findChildViewById(rootView, R.id.magicIndicator);
                            if (magicIndicator != null) {
                                i = R.id.vpTextOptions;
                                ViewPager viewPager = (ViewPager) ViewBindings.findChildViewById(rootView, R.id.vpTextOptions);
                                if (viewPager != null) {
                                    return new DialogTextOptionsBinding((RelativeLayout) rootView, editText, imageView, imageView2, viewFindChildViewById, linearLayout, magicIndicator, viewPager);
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
