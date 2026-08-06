package com.shangxian.pinkink.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import com.lazyee.klib.extension.ViewExtensionsKt;
import com.lazyee.klib.mvvm.LoadingState;
import com.lazyee.klib.mvvm.ViewModel;
import com.scwang.smart.refresh.layout.util.SmartUtil;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseFragment;
import com.shangxian.pinkink.bean.ThemeCategoryBean;
import com.shangxian.pinkink.databinding.FragmentThemesBinding;
import com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel;
import com.shangxian.pinkink.ui.main.ThemesFragment;
import com.shangxian.pinkink.ui.themes.ThemesListFragment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/* JADX INFO: compiled from: ThemesFragment.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0013B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\u000eH\u0016J\u0010\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\u00020\b8CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lcom/shangxian/pinkink/ui/main/ThemesFragment;", "Lcom/shangxian/pinkink/base/BaseFragment;", "Lcom/shangxian/pinkink/databinding/FragmentThemesBinding;", "()V", "themeCategoryList", "", "Lcom/shangxian/pinkink/bean/ThemeCategoryBean;", "themesViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "getThemesViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "themesViewModel$delegate", "Lkotlin/Lazy;", "initMagicIndicator", "", "initView", "onPageLoadingStateChanged", "state", "Lcom/lazyee/klib/mvvm/LoadingState;", "ThemesFragmentAdapter", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ThemesFragment extends BaseFragment<FragmentThemesBinding> {

    /* JADX INFO: renamed from: themesViewModel$delegate, reason: from kotlin metadata */
    private final Lazy themesViewModel = LazyKt.lazy(new Function0<ThemesViewModel>() { // from class: com.shangxian.pinkink.ui.main.ThemesFragment$themesViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ThemesViewModel invoke() {
            return (ThemesViewModel) new ViewModelProvider(this.this$0).get(ThemesViewModel.class);
        }
    });
    private final List<ThemeCategoryBean> themeCategoryList = new ArrayList();

    /* JADX INFO: compiled from: ThemesFragment.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LoadingState.values().length];
            iArr[LoadingState.LOADING.ordinal()] = 1;
            iArr[LoadingState.SUCCESS.ordinal()] = 2;
            iArr[LoadingState.FAILURE.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @ViewModel
    private final ThemesViewModel getThemesViewModel() {
        return (ThemesViewModel) this.themesViewModel.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingFragment
    public void initView() {
        super.initView();
        ImageView imageView = ((FragmentThemesBinding) getMViewBinding()).titleBar.ivBack;
        Intrinsics.checkNotNullExpressionValue(imageView, "mViewBinding.titleBar.ivBack");
        ViewExtensionsKt.gone(imageView);
        ((FragmentThemesBinding) getMViewBinding()).titleBar.tvTitle.setText(getString(R.string.title_theme));
        Iterator it = ((FragmentThemesBinding) getMViewBinding()).pageStateSwitcher.getTargetViews(R.id.tvRefresh).iterator();
        while (it.hasNext()) {
            ((TextView) it.next()).setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.ThemesFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ThemesFragment.m273initView$lambda1$lambda0(this.f$0, view);
                }
            });
        }
        getThemesViewModel().m118getThemeCategoryList();
        getThemesViewModel().getThemeCategoryList().observe(this, new Observer() { // from class: com.shangxian.pinkink.ui.main.ThemesFragment$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ThemesFragment.m274initView$lambda2(this.f$0, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-1$lambda-0, reason: not valid java name */
    public static final void m273initView$lambda1$lambda0(ThemesFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getThemesViewModel().m118getThemeCategoryList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: initView$lambda-2, reason: not valid java name */
    public static final void m274initView$lambda2(ThemesFragment this$0, List data) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.themeCategoryList.clear();
        List<ThemeCategoryBean> list = this$0.themeCategoryList;
        Intrinsics.checkNotNullExpressionValue(data, "data");
        list.addAll(data);
        this$0.initMagicIndicator();
        ViewPager viewPager = ((FragmentThemesBinding) this$0.getMViewBinding()).vpThemes;
        FragmentManager childFragmentManager = this$0.getChildFragmentManager();
        Intrinsics.checkNotNullExpressionValue(childFragmentManager, "childFragmentManager");
        viewPager.setAdapter(new ThemesFragmentAdapter(childFragmentManager, this$0.themeCategoryList));
        ViewPagerHelper.bind(((FragmentThemesBinding) this$0.getMViewBinding()).magicIndicator, ((FragmentThemesBinding) this$0.getMViewBinding()).vpThemes);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.BaseFragment, com.lazyee.klib.mvvm.MVVMBaseView
    public void onPageLoadingStateChanged(LoadingState state) {
        Intrinsics.checkNotNullParameter(state, "state");
        super.onPageLoadingStateChanged(state);
        int i = WhenMappings.$EnumSwitchMapping$0[state.ordinal()];
        if (i == 1) {
            ((FragmentThemesBinding) getMViewBinding()).pageStateSwitcher.showLoadingView();
        } else if (i == 2) {
            ((FragmentThemesBinding) getMViewBinding()).pageStateSwitcher.showContentView();
        } else {
            if (i != 3) {
                return;
            }
            ((FragmentThemesBinding) getMViewBinding()).pageStateSwitcher.showExceptionView();
        }
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.ui.main.ThemesFragment$initMagicIndicator$1, reason: invalid class name */
    /* JADX INFO: compiled from: ThemesFragment.kt */
    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"com/shangxian/pinkink/ui/main/ThemesFragment$initMagicIndicator$1", "Lnet/lucode/hackware/magicindicator/buildins/commonnavigator/abs/CommonNavigatorAdapter;", "getCount", "", "getIndicator", "Lnet/lucode/hackware/magicindicator/buildins/commonnavigator/abs/IPagerIndicator;", "context", "Landroid/content/Context;", "getTitleView", "Lnet/lucode/hackware/magicindicator/buildins/commonnavigator/abs/IPagerTitleView;", "index", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class AnonymousClass1 extends CommonNavigatorAdapter {
        AnonymousClass1() {
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return ThemesFragment.this.themeCategoryList.size();
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            Intrinsics.checkNotNullParameter(context, "context");
            ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
            colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#FF999999"));
            colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#FF333333"));
            colorTransitionPagerTitleView.setTextSize(2, 16.0f);
            colorTransitionPagerTitleView.setText(((ThemeCategoryBean) ThemesFragment.this.themeCategoryList.get(index)).getName());
            final ThemesFragment themesFragment = ThemesFragment.this;
            colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.ThemesFragment$initMagicIndicator$1$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ThemesFragment.AnonymousClass1.m275getTitleView$lambda0(themesFragment, index, view);
                }
            });
            return colorTransitionPagerTitleView;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX INFO: renamed from: getTitleView$lambda-0, reason: not valid java name */
        public static final void m275getTitleView$lambda0(ThemesFragment this$0, int i, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            ((FragmentThemesBinding) this$0.getMViewBinding()).vpThemes.setCurrentItem(i);
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            linePagerIndicator.setMode(2);
            linePagerIndicator.setLayoutParams(new ViewGroup.LayoutParams(SmartUtil.dp2px(12.0f), SmartUtil.dp2px(3.0f)));
            linePagerIndicator.setRoundRadius(SmartUtil.dp2px(1.5f));
            linePagerIndicator.setColors(Integer.valueOf(Color.parseColor("#FFFF7A9A")));
            return linePagerIndicator;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new AnonymousClass1());
        ((FragmentThemesBinding) getMViewBinding()).magicIndicator.setNavigator(commonNavigator);
    }

    /* JADX INFO: compiled from: ThemesFragment.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fH\u0016R\u0018\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0013"}, d2 = {"Lcom/shangxian/pinkink/ui/main/ThemesFragment$ThemesFragmentAdapter;", "Landroidx/fragment/app/FragmentStatePagerAdapter;", "fm", "Landroidx/fragment/app/FragmentManager;", "themesCategoryList", "", "Lcom/shangxian/pinkink/bean/ThemeCategoryBean;", "(Landroidx/fragment/app/FragmentManager;Ljava/util/List;)V", "fragmentList", "", "Lcom/shangxian/pinkink/ui/themes/ThemesListFragment;", "[Lcom/shangxian/pinkink/ui/themes/ThemesListFragment;", "getThemesCategoryList", "()Ljava/util/List;", "getCount", "", "getItem", "Landroidx/fragment/app/Fragment;", "position", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    private static final class ThemesFragmentAdapter extends FragmentStatePagerAdapter {
        private final ThemesListFragment[] fragmentList;
        private final List<ThemeCategoryBean> themesCategoryList;

        public final List<ThemeCategoryBean> getThemesCategoryList() {
            return this.themesCategoryList;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ThemesFragmentAdapter(FragmentManager fm, List<ThemeCategoryBean> themesCategoryList) {
            super(fm, 1);
            Intrinsics.checkNotNullParameter(fm, "fm");
            Intrinsics.checkNotNullParameter(themesCategoryList, "themesCategoryList");
            this.themesCategoryList = themesCategoryList;
            int size = themesCategoryList.size();
            ThemesListFragment[] themesListFragmentArr = new ThemesListFragment[size];
            for (int i = 0; i < size; i++) {
                themesListFragmentArr[i] = null;
            }
            this.fragmentList = themesListFragmentArr;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return this.themesCategoryList.size();
        }

        @Override // androidx.fragment.app.FragmentStatePagerAdapter
        public Fragment getItem(int position) {
            ThemesListFragment[] themesListFragmentArr = this.fragmentList;
            if (themesListFragmentArr[position] == null) {
                themesListFragmentArr[position] = ThemesListFragment.INSTANCE.newInstance(this.themesCategoryList.get(position).getId());
            }
            ThemesListFragment themesListFragment = this.fragmentList[position];
            Intrinsics.checkNotNull(themesListFragment);
            return themesListFragment;
        }
    }
}
