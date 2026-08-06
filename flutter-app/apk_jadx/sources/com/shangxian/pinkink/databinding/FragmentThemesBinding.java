package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager.widget.ViewPager;
import com.lazyee.klib.widget.PageStateSwitcher;
import com.shangxian.pinkink.R;
import net.lucode.hackware.magicindicator.MagicIndicator;

/* JADX INFO: loaded from: classes.dex */
public final class FragmentThemesBinding implements ViewBinding {
    public final LinearLayout contentView;
    public final MagicIndicator magicIndicator;
    public final PageStateSwitcher pageStateSwitcher;
    private final LinearLayout rootView;
    public final LayoutTitleBarBinding titleBar;
    public final ViewPager vpThemes;

    private FragmentThemesBinding(LinearLayout rootView, LinearLayout contentView, MagicIndicator magicIndicator, PageStateSwitcher pageStateSwitcher, LayoutTitleBarBinding titleBar, ViewPager vpThemes) {
        this.rootView = rootView;
        this.contentView = contentView;
        this.magicIndicator = magicIndicator;
        this.pageStateSwitcher = pageStateSwitcher;
        this.titleBar = titleBar;
        this.vpThemes = vpThemes;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentThemesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentThemesBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_themes, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentThemesBinding bind(View rootView) {
        int i = R.id.contentView;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.contentView);
        if (linearLayout != null) {
            i = R.id.magicIndicator;
            MagicIndicator magicIndicator = (MagicIndicator) ViewBindings.findChildViewById(rootView, R.id.magicIndicator);
            if (magicIndicator != null) {
                i = R.id.pageStateSwitcher;
                PageStateSwitcher pageStateSwitcher = (PageStateSwitcher) ViewBindings.findChildViewById(rootView, R.id.pageStateSwitcher);
                if (pageStateSwitcher != null) {
                    i = R.id.titleBar;
                    View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.titleBar);
                    if (viewFindChildViewById != null) {
                        LayoutTitleBarBinding layoutTitleBarBindingBind = LayoutTitleBarBinding.bind(viewFindChildViewById);
                        i = R.id.vpThemes;
                        ViewPager viewPager = (ViewPager) ViewBindings.findChildViewById(rootView, R.id.vpThemes);
                        if (viewPager != null) {
                            return new FragmentThemesBinding((LinearLayout) rootView, linearLayout, magicIndicator, pageStateSwitcher, layoutTitleBarBindingBind, viewPager);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
