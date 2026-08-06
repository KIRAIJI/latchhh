package com.shangxian.pinkink.ui.themes;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lazyee.klib.extension.AnyExtensionsKt;
import com.lazyee.klib.extension.NumberExtensionsKt;
import com.lazyee.klib.mvvm.LoadingState;
import com.lazyee.klib.mvvm.ViewModel;
import com.lazyee.klib.recyclerview.decoration.GridSpacingItemDecoration;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.bean.PageDataBean;
import com.shangxian.pinkink.bean.ThemeBean;
import com.shangxian.pinkink.constants.Keys;
import com.shangxian.pinkink.databinding.ActivityThemesListBinding;
import com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel;
import com.shangxian.pinkink.ui.themes.ThemesListActivity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ThemesListActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 '2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\u00020\u0004:\u0002'(B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010\u001c\u001a\u00020\u001dH\u0017J\b\u0010\u001e\u001a\u00020\u001dH\u0017J\u0010\u0010\u001f\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020$H\u0017J\u0016\u0010%\u001a\u00020\u001d2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0002R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0012\u001a\u00060\u0013R\u00020\u0000X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0014\u001a\u00020\u00158CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u000b\u001a\u0004\b\u0016\u0010\u0017R\u001b\u0010\u0019\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u000b\u001a\u0004\b\u001a\u0010\t¨\u0006)"}, d2 = {"Lcom/shangxian/pinkink/ui/themes/ThemesListActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityThemesListBinding;", "Lcom/scwang/smart/refresh/layout/listener/OnRefreshListener;", "Lcom/chad/library/adapter/base/listener/OnLoadMoreListener;", "()V", "categoryId", "", "getCategoryId", "()Ljava/lang/String;", "categoryId$delegate", "Lkotlin/Lazy;", "pageNum", "", "pageSize", "themeList", "", "Lcom/shangxian/pinkink/bean/ThemeBean;", "themesAdapter", "Lcom/shangxian/pinkink/ui/themes/ThemesListActivity$ThemesAdapter;", "themesViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "getThemesViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "themesViewModel$delegate", "title", "getTitle", "title$delegate", "initView", "", "onLoadMore", "onPageLoadingStateChanged", "state", "Lcom/lazyee/klib/mvvm/LoadingState;", "onRefresh", "refreshLayout", "Lcom/scwang/smart/refresh/layout/api/RefreshLayout;", "setThemesAdapter", "dataSource", "Companion", "ThemesAdapter", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ThemesListActivity extends BaseActivity<ActivityThemesListBinding> implements OnRefreshListener, OnLoadMoreListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private ThemesAdapter themesAdapter;

    /* JADX INFO: renamed from: themesViewModel$delegate, reason: from kotlin metadata */
    private final Lazy themesViewModel = LazyKt.lazy(new Function0<ThemesViewModel>() { // from class: com.shangxian.pinkink.ui.themes.ThemesListActivity$themesViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ThemesViewModel invoke() {
            return (ThemesViewModel) new ViewModelProvider(this.this$0).get(ThemesViewModel.class);
        }
    });

    /* JADX INFO: renamed from: categoryId$delegate, reason: from kotlin metadata */
    private final Lazy categoryId = LazyKt.lazy(new Function0<String>() { // from class: com.shangxian.pinkink.ui.themes.ThemesListActivity$categoryId$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            String stringExtra;
            Intent intent = this.this$0.getIntent();
            return (intent == null || (stringExtra = intent.getStringExtra(Keys.CATEGORY_ID)) == null) ? "" : stringExtra;
        }
    });

    /* JADX INFO: renamed from: title$delegate, reason: from kotlin metadata */
    private final Lazy title = LazyKt.lazy(new Function0<String>() { // from class: com.shangxian.pinkink.ui.themes.ThemesListActivity$title$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            String stringExtra;
            Intent intent = this.this$0.getIntent();
            return (intent == null || (stringExtra = intent.getStringExtra(Keys.TITLE)) == null) ? "" : stringExtra;
        }
    });
    private final List<ThemeBean> themeList = new ArrayList();
    private int pageNum = 1;
    private int pageSize = 20;

    /* JADX INFO: compiled from: ThemesListActivity.kt */
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

    /* JADX INFO: compiled from: ThemesListActivity.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b¨\u0006\n"}, d2 = {"Lcom/shangxian/pinkink/ui/themes/ThemesListActivity$Companion;", "", "()V", "gotoThis", "", "context", "Landroid/content/Context;", "title", "", "categoryId", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void gotoThis(Context context, String title, String categoryId) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(categoryId, "categoryId");
            Intent intent = new Intent(context, (Class<?>) ThemesListActivity.class);
            intent.putExtra(Keys.TITLE, title);
            intent.putExtra(Keys.CATEGORY_ID, categoryId);
            context.startActivity(intent);
        }
    }

    @ViewModel
    private final ThemesViewModel getThemesViewModel() {
        return (ThemesViewModel) this.themesViewModel.getValue();
    }

    private final String getCategoryId() {
        return (String) this.categoryId.getValue();
    }

    private final String getTitle() {
        return (String) this.title.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        ActivityThemesListBinding activityThemesListBinding = (ActivityThemesListBinding) getMViewBinding();
        activityThemesListBinding.titleBar.tvTitle.setText(getTitle());
        activityThemesListBinding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.ThemesListActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThemesListActivity.m331initView$lambda4$lambda0(this.f$0, view);
            }
        });
        Iterator it = activityThemesListBinding.pageStateSwitcher.getTargetViews(R.id.tvRefresh).iterator();
        while (it.hasNext()) {
            ((TextView) it.next()).setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.ThemesListActivity$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ThemesListActivity.m332initView$lambda4$lambda2$lambda1(this.f$0, view);
                }
            });
        }
        SmartRefreshLayout smartRefreshLayout = activityThemesListBinding.refreshLayout;
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        smartRefreshLayout.setOnRefreshListener(this);
        setThemesAdapter(this.themeList);
        getThemesViewModel().getThemeList(getCategoryId(), this.pageNum, this.pageSize, true);
        getThemesViewModel().getThemeList().observe(this, new Observer() { // from class: com.shangxian.pinkink.ui.themes.ThemesListActivity$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ThemesListActivity.m333initView$lambda7(this.f$0, (PageDataBean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-0, reason: not valid java name */
    public static final void m331initView$lambda4$lambda0(ThemesListActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-2$lambda-1, reason: not valid java name */
    public static final void m332initView$lambda4$lambda2$lambda1(ThemesListActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.themeList.clear();
        this$0.pageNum = 1;
        this$0.getThemesViewModel().getThemeList(this$0.getCategoryId(), this$0.pageNum, this$0.pageSize, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: initView$lambda-7, reason: not valid java name */
    public static final void m333initView$lambda7(ThemesListActivity this$0, PageDataBean pageDataBean) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int pageNum = pageDataBean.getPageNum();
        this$0.pageNum = pageNum;
        if (pageNum == 1) {
            this$0.themeList.clear();
        }
        List listData = pageDataBean.getListData();
        if (listData != null) {
            this$0.themeList.addAll(listData);
        }
        ThemesAdapter themesAdapter = this$0.themesAdapter;
        ThemesAdapter themesAdapter2 = null;
        if (themesAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("themesAdapter");
            themesAdapter = null;
        }
        themesAdapter.notifyDataSetChanged();
        ((ActivityThemesListBinding) this$0.getMViewBinding()).refreshLayout.finishRefresh();
        if (pageDataBean.isMore()) {
            ThemesAdapter themesAdapter3 = this$0.themesAdapter;
            if (themesAdapter3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("themesAdapter");
            } else {
                themesAdapter2 = themesAdapter3;
            }
            themesAdapter2.getLoadMoreModule().loadMoreComplete();
            return;
        }
        ThemesAdapter themesAdapter4 = this$0.themesAdapter;
        if (themesAdapter4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("themesAdapter");
            themesAdapter4 = null;
        }
        BaseLoadMoreModule.loadMoreEnd$default(themesAdapter4.getLoadMoreModule(), false, 1, null);
    }

    @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
    public void onRefresh(RefreshLayout refreshLayout) {
        Intrinsics.checkNotNullParameter(refreshLayout, "refreshLayout");
        this.themeList.clear();
        this.pageNum = 1;
        getThemesViewModel().getThemeList(getCategoryId(), this.pageNum, this.pageSize, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.BaseActivity, com.lazyee.klib.mvvm.MVVMBaseView
    public void onPageLoadingStateChanged(LoadingState state) {
        Intrinsics.checkNotNullParameter(state, "state");
        super.onPageLoadingStateChanged(state);
        int i = WhenMappings.$EnumSwitchMapping$0[state.ordinal()];
        if (i == 1) {
            ((ActivityThemesListBinding) getMViewBinding()).pageStateSwitcher.showLoadingView();
        } else if (i == 2) {
            ((ActivityThemesListBinding) getMViewBinding()).pageStateSwitcher.showContentView();
        } else {
            if (i != 3) {
                return;
            }
            ((ActivityThemesListBinding) getMViewBinding()).pageStateSwitcher.showNetworkErrorView();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void setThemesAdapter(List<ThemeBean> dataSource) {
        this.themesAdapter = new ThemesAdapter(this, dataSource);
        RecyclerView recyclerView = ((ActivityThemesListBinding) getMViewBinding()).rvThemes;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(NumberExtensionsKt.dp2px(11.0f)));
        ThemesAdapter themesAdapter = this.themesAdapter;
        ThemesAdapter themesAdapter2 = null;
        if (themesAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("themesAdapter");
            themesAdapter = null;
        }
        recyclerView.setAdapter(themesAdapter);
        ThemesAdapter themesAdapter3 = this.themesAdapter;
        if (themesAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("themesAdapter");
        } else {
            themesAdapter2 = themesAdapter3;
        }
        themesAdapter2.getLoadMoreModule().setOnLoadMoreListener(this);
    }

    @Override // com.chad.library.adapter.base.listener.OnLoadMoreListener
    public void onLoadMore() {
        getThemesViewModel().getThemeList(getCategoryId(), this.pageNum + 1, this.pageSize, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: ThemesListActivity.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004B\u0013\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0002H\u0014R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/shangxian/pinkink/ui/themes/ThemesListActivity$ThemesAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/shangxian/pinkink/bean/ThemeBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "Lcom/chad/library/adapter/base/module/LoadMoreModule;", "themesList", "", "(Lcom/shangxian/pinkink/ui/themes/ThemesListActivity;Ljava/util/List;)V", "themesItemHeight", "", "themesItemWidth", "convert", "", "holder", "item", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    final class ThemesAdapter extends BaseQuickAdapter<ThemeBean, BaseViewHolder> implements LoadMoreModule {
        private final int themesItemHeight;
        private final int themesItemWidth;
        private final List<ThemeBean> themesList;
        final /* synthetic */ ThemesListActivity this$0;

        @Override // com.chad.library.adapter.base.module.LoadMoreModule
        public /* synthetic */ BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapter baseQuickAdapter) {
            return LoadMoreModule.CC.$default$addLoadMoreModule(this, baseQuickAdapter);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ThemesAdapter(ThemesListActivity this$0, List<ThemeBean> themesList) {
            super(R.layout.item_themes, themesList);
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(themesList, "themesList");
            this.this$0 = this$0;
            this.themesList = themesList;
            int screenWidth = (AnyExtensionsKt.getScreenWidth(this) - NumberExtensionsKt.dp2px(41)) / 2;
            this.themesItemWidth = screenWidth;
            this.themesItemHeight = (int) (((double) screenWidth) * 1.733d);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder holder, final ThemeBean item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.rlThemes);
            relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, this.themesItemHeight));
            Glide.with(getContext()).load(item.getCover()).into((ImageView) holder.getView(R.id.ivThemes));
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.ThemesListActivity$ThemesAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ThemesListActivity.ThemesAdapter.m334convert$lambda0(this.f$0, item, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: convert$lambda-0, reason: not valid java name */
        public static final void m334convert$lambda0(ThemesAdapter this$0, ThemeBean item, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(item, "$item");
            ThemeDetailActivity.INSTANCE.gotoThis(this$0.getContext(), item.getId());
        }
    }
}
