package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class FragmentMineBinding implements ViewBinding {
    public final RoundedImageView ivAvatar;
    public final ImageView ivLogout;
    public final LinearLayout llAboutUs;
    public final LinearLayout llCancelAccount;
    public final LinearLayout llClearCache;
    public final LinearLayout llDesignerSettlement;
    public final LinearLayout llFontLibrary;
    public final LinearLayout llHelp;
    public final LinearLayout llMyFontLibrary;
    public final LinearLayout llPictureLoopSetting;
    public final LinearLayout llSwitchDeviceType;
    public final LinearLayout llSwitchLanguage;
    public final LinearLayout llUserCreateThemes;
    public final LinearLayout llUserLikeThemes;
    private final LinearLayout rootView;
    public final SwitchCompat switchLanguage;
    public final SwitchCompat switchPictureSetting;
    public final LayoutTitleBarBinding titleBar;
    public final TextView tvCreateCount;
    public final TextView tvDeviceType;
    public final TextView tvLikeCount;
    public final TextView tvNickName;

    private FragmentMineBinding(LinearLayout rootView, RoundedImageView ivAvatar, ImageView ivLogout, LinearLayout llAboutUs, LinearLayout llCancelAccount, LinearLayout llClearCache, LinearLayout llDesignerSettlement, LinearLayout llFontLibrary, LinearLayout llHelp, LinearLayout llMyFontLibrary, LinearLayout llPictureLoopSetting, LinearLayout llSwitchDeviceType, LinearLayout llSwitchLanguage, LinearLayout llUserCreateThemes, LinearLayout llUserLikeThemes, SwitchCompat switchLanguage, SwitchCompat switchPictureSetting, LayoutTitleBarBinding titleBar, TextView tvCreateCount, TextView tvDeviceType, TextView tvLikeCount, TextView tvNickName) {
        this.rootView = rootView;
        this.ivAvatar = ivAvatar;
        this.ivLogout = ivLogout;
        this.llAboutUs = llAboutUs;
        this.llCancelAccount = llCancelAccount;
        this.llClearCache = llClearCache;
        this.llDesignerSettlement = llDesignerSettlement;
        this.llFontLibrary = llFontLibrary;
        this.llHelp = llHelp;
        this.llMyFontLibrary = llMyFontLibrary;
        this.llPictureLoopSetting = llPictureLoopSetting;
        this.llSwitchDeviceType = llSwitchDeviceType;
        this.llSwitchLanguage = llSwitchLanguage;
        this.llUserCreateThemes = llUserCreateThemes;
        this.llUserLikeThemes = llUserLikeThemes;
        this.switchLanguage = switchLanguage;
        this.switchPictureSetting = switchPictureSetting;
        this.titleBar = titleBar;
        this.tvCreateCount = tvCreateCount;
        this.tvDeviceType = tvDeviceType;
        this.tvLikeCount = tvLikeCount;
        this.tvNickName = tvNickName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentMineBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentMineBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_mine, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentMineBinding bind(View rootView) {
        int i = R.id.ivAvatar;
        RoundedImageView roundedImageView = (RoundedImageView) ViewBindings.findChildViewById(rootView, R.id.ivAvatar);
        if (roundedImageView != null) {
            i = R.id.ivLogout;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivLogout);
            if (imageView != null) {
                i = R.id.llAboutUs;
                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llAboutUs);
                if (linearLayout != null) {
                    i = R.id.llCancelAccount;
                    LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llCancelAccount);
                    if (linearLayout2 != null) {
                        i = R.id.llClearCache;
                        LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llClearCache);
                        if (linearLayout3 != null) {
                            i = R.id.llDesignerSettlement;
                            LinearLayout linearLayout4 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llDesignerSettlement);
                            if (linearLayout4 != null) {
                                i = R.id.llFontLibrary;
                                LinearLayout linearLayout5 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llFontLibrary);
                                if (linearLayout5 != null) {
                                    i = R.id.llHelp;
                                    LinearLayout linearLayout6 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llHelp);
                                    if (linearLayout6 != null) {
                                        i = R.id.llMyFontLibrary;
                                        LinearLayout linearLayout7 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llMyFontLibrary);
                                        if (linearLayout7 != null) {
                                            i = R.id.llPictureLoopSetting;
                                            LinearLayout linearLayout8 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llPictureLoopSetting);
                                            if (linearLayout8 != null) {
                                                i = R.id.llSwitchDeviceType;
                                                LinearLayout linearLayout9 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llSwitchDeviceType);
                                                if (linearLayout9 != null) {
                                                    i = R.id.llSwitchLanguage;
                                                    LinearLayout linearLayout10 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llSwitchLanguage);
                                                    if (linearLayout10 != null) {
                                                        i = R.id.llUserCreateThemes;
                                                        LinearLayout linearLayout11 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llUserCreateThemes);
                                                        if (linearLayout11 != null) {
                                                            i = R.id.llUserLikeThemes;
                                                            LinearLayout linearLayout12 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llUserLikeThemes);
                                                            if (linearLayout12 != null) {
                                                                i = R.id.switchLanguage;
                                                                SwitchCompat switchCompat = (SwitchCompat) ViewBindings.findChildViewById(rootView, R.id.switchLanguage);
                                                                if (switchCompat != null) {
                                                                    i = R.id.switchPictureSetting;
                                                                    SwitchCompat switchCompat2 = (SwitchCompat) ViewBindings.findChildViewById(rootView, R.id.switchPictureSetting);
                                                                    if (switchCompat2 != null) {
                                                                        i = R.id.titleBar;
                                                                        View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.titleBar);
                                                                        if (viewFindChildViewById != null) {
                                                                            LayoutTitleBarBinding layoutTitleBarBindingBind = LayoutTitleBarBinding.bind(viewFindChildViewById);
                                                                            i = R.id.tvCreateCount;
                                                                            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvCreateCount);
                                                                            if (textView != null) {
                                                                                i = R.id.tvDeviceType;
                                                                                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvDeviceType);
                                                                                if (textView2 != null) {
                                                                                    i = R.id.tvLikeCount;
                                                                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvLikeCount);
                                                                                    if (textView3 != null) {
                                                                                        i = R.id.tvNickName;
                                                                                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvNickName);
                                                                                        if (textView4 != null) {
                                                                                            return new FragmentMineBinding((LinearLayout) rootView, roundedImageView, imageView, linearLayout, linearLayout2, linearLayout3, linearLayout4, linearLayout5, linearLayout6, linearLayout7, linearLayout8, linearLayout9, linearLayout10, linearLayout11, linearLayout12, switchCompat, switchCompat2, layoutTitleBarBindingBind, textView, textView2, textView3, textView4);
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
