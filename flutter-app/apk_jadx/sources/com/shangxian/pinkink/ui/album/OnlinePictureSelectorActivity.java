package com.shangxian.pinkink.ui.album;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.lazyee.klib.extension.ContextExtensionsKt;
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
import com.shangxian.pinkink.databinding.ActivityOnlinePictureSelectorBinding;
import com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel;
import com.shangxian.pinkink.ui.album.OnlinePictureSelectorActivity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OnlinePictureSelectorActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 '2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\u00020\u0004:\u0002'(B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010\u001a\u001a\u00020\u001bH\u0017J\b\u0010\u001c\u001a\u00020\u001bH\u0017J\u0010\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\"H\u0017J\u0016\u0010#\u001a\u00020\u001b2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J\u0010\u0010%\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020\rH\u0002R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u000e\u0010\f\u001a\u00020\rX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0013\u001a\u00060\u0014R\u00020\u0000X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0015\u001a\u00020\u00168CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u000b\u001a\u0004\b\u0017\u0010\u0018¨\u0006)"}, d2 = {"Lcom/shangxian/pinkink/ui/album/OnlinePictureSelectorActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityOnlinePictureSelectorBinding;", "Lcom/scwang/smart/refresh/layout/listener/OnRefreshListener;", "Lcom/chad/library/adapter/base/listener/OnLoadMoreListener;", "()V", "categoryId", "", "getCategoryId", "()Ljava/lang/String;", "categoryId$delegate", "Lkotlin/Lazy;", "maxSelectedCount", "", "pageNum", "pageSize", "themeList", "", "Lcom/shangxian/pinkink/bean/ThemeBean;", "themesAdapter", "Lcom/shangxian/pinkink/ui/album/OnlinePictureSelectorActivity$ThemesAdapter;", "themesViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "getThemesViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "themesViewModel$delegate", "initView", "", "onLoadMore", "onPageLoadingStateChanged", "state", "Lcom/lazyee/klib/mvvm/LoadingState;", "onRefresh", "refreshLayout", "Lcom/scwang/smart/refresh/layout/api/RefreshLayout;", "setThemesAdapter", "dataSource", "updateSelectedCount", "selectedCount", "Companion", "ThemesAdapter", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class OnlinePictureSelectorActivity extends BaseActivity<ActivityOnlinePictureSelectorBinding> implements OnRefreshListener, OnLoadMoreListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private ThemesAdapter themesAdapter;

    /* JADX INFO: renamed from: themesViewModel$delegate, reason: from kotlin metadata */
    private final Lazy themesViewModel = LazyKt.lazy(new Function0<ThemesViewModel>() { // from class: com.shangxian.pinkink.ui.album.OnlinePictureSelectorActivity$themesViewModel$2
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
    private final Lazy categoryId = LazyKt.lazy(new Function0<String>() { // from class: com.shangxian.pinkink.ui.album.OnlinePictureSelectorActivity$categoryId$2
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
    private final List<ThemeBean> themeList = new ArrayList();
    private int pageNum = 1;
    private int pageSize = 21;
    private final int maxSelectedCount = 15;

    /* JADX INFO: compiled from: OnlinePictureSelectorActivity.kt */
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

    /* JADX INFO: compiled from: OnlinePictureSelectorActivity.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\u000b"}, d2 = {"Lcom/shangxian/pinkink/ui/album/OnlinePictureSelectorActivity$Companion;", "", "()V", "getGotoIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "categoryId", "", "gotoThis", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void gotoThis(Context context, String categoryId) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(categoryId, "categoryId");
            Intent intent = new Intent(context, (Class<?>) OnlinePictureSelectorActivity.class);
            intent.putExtra(Keys.CATEGORY_ID, categoryId);
            context.startActivity(intent);
        }

        public final Intent getGotoIntent(Context context, String categoryId) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(categoryId, "categoryId");
            Intent intent = new Intent(context, (Class<?>) OnlinePictureSelectorActivity.class);
            intent.putExtra(Keys.CATEGORY_ID, categoryId);
            return intent;
        }
    }

    @ViewModel
    private final ThemesViewModel getThemesViewModel() {
        return (ThemesViewModel) this.themesViewModel.getValue();
    }

    private final String getCategoryId() {
        return (String) this.categoryId.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        updateSelectedCount(0);
        ActivityOnlinePictureSelectorBinding activityOnlinePictureSelectorBinding = (ActivityOnlinePictureSelectorBinding) getMViewBinding();
        activityOnlinePictureSelectorBinding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.album.OnlinePictureSelectorActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                OnlinePictureSelectorActivity.m147initView$lambda5$lambda0(this.f$0, view);
            }
        });
        Iterator it = activityOnlinePictureSelectorBinding.pageStateSwitcher.getTargetViews(R.id.tvRefresh).iterator();
        while (it.hasNext()) {
            ((TextView) it.next()).setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.album.OnlinePictureSelectorActivity$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    OnlinePictureSelectorActivity.m148initView$lambda5$lambda2$lambda1(this.f$0, view);
                }
            });
        }
        SmartRefreshLayout smartRefreshLayout = activityOnlinePictureSelectorBinding.refreshLayout;
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        smartRefreshLayout.setOnRefreshListener(this);
        activityOnlinePictureSelectorBinding.tvComplete.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.album.OnlinePictureSelectorActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                OnlinePictureSelectorActivity.m149initView$lambda5$lambda4(this.f$0, view);
            }
        });
        setThemesAdapter(this.themeList);
        getThemesViewModel().getThemeList(getCategoryId(), this.pageNum, this.pageSize, true);
        getThemesViewModel().getThemeList().observe(this, new Observer() { // from class: com.shangxian.pinkink.ui.album.OnlinePictureSelectorActivity$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                OnlinePictureSelectorActivity.m150initView$lambda8(this.f$0, (PageDataBean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-5$lambda-0, reason: not valid java name */
    public static final void m147initView$lambda5$lambda0(OnlinePictureSelectorActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-5$lambda-2$lambda-1, reason: not valid java name */
    public static final void m148initView$lambda5$lambda2$lambda1(OnlinePictureSelectorActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.themeList.clear();
        this$0.pageNum = 1;
        this$0.getThemesViewModel().getThemeList(this$0.getCategoryId(), this$0.pageNum, this$0.pageSize, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-5$lambda-4, reason: not valid java name */
    public static final void m149initView$lambda5$lambda4(OnlinePictureSelectorActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ThemesAdapter themesAdapter = this$0.themesAdapter;
        ThemesAdapter themesAdapter2 = null;
        if (themesAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("themesAdapter");
            themesAdapter = null;
        }
        if (themesAdapter.getSelectedCount() <= 0) {
            OnlinePictureSelectorActivity onlinePictureSelectorActivity = this$0;
            String string = this$0.getString(R.string.toast_you_have_no_choose_any_items);
            Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.toast…have_no_choose_any_items)");
            ContextExtensionsKt.toastShort(onlinePictureSelectorActivity, string);
            return;
        }
        Intent intent = new Intent();
        ThemesAdapter themesAdapter3 = this$0.themesAdapter;
        if (themesAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("themesAdapter");
        } else {
            themesAdapter2 = themesAdapter3;
        }
        intent.putParcelableArrayListExtra(Keys.DATA, (ArrayList) themesAdapter2.getSelectedThemeList());
        this$0.setResult(-1, intent);
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: initView$lambda-8, reason: not valid java name */
    public static final void m150initView$lambda8(OnlinePictureSelectorActivity this$0, PageDataBean pageDataBean) {
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
        ((ActivityOnlinePictureSelectorBinding) this$0.getMViewBinding()).refreshLayout.finishRefresh();
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

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void updateSelectedCount(int selectedCount) {
        ActivityOnlinePictureSelectorBinding activityOnlinePictureSelectorBinding = (ActivityOnlinePictureSelectorBinding) getMViewBinding();
        activityOnlinePictureSelectorBinding.titleBar.tvTitle.setText(getString(R.string.string_selected_x_items, new Object[]{Integer.valueOf(selectedCount)}));
        activityOnlinePictureSelectorBinding.tvSelectedCount.setText(String.valueOf(selectedCount));
        activityOnlinePictureSelectorBinding.tvRemainingTip.setText(getString(R.string.string_remaining_x_can_be_added, new Object[]{Integer.valueOf(this.maxSelectedCount - selectedCount)}));
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
            ((ActivityOnlinePictureSelectorBinding) getMViewBinding()).pageStateSwitcher.showLoadingView();
        } else if (i == 2) {
            ((ActivityOnlinePictureSelectorBinding) getMViewBinding()).pageStateSwitcher.showContentView();
        } else {
            if (i != 3) {
                return;
            }
            ((ActivityOnlinePictureSelectorBinding) getMViewBinding()).pageStateSwitcher.showNetworkErrorView();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void setThemesAdapter(List<ThemeBean> dataSource) {
        this.themesAdapter = new ThemesAdapter(this, dataSource);
        RecyclerView recyclerView = ((ActivityOnlinePictureSelectorBinding) getMViewBinding()).rvThemes;
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
    /* JADX INFO: compiled from: OnlinePictureSelectorActivity.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\b\u0082\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004B\u0013\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0002H\u0014J\u0006\u0010\u000f\u001a\u00020\tJ\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u0011R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/shangxian/pinkink/ui/album/OnlinePictureSelectorActivity$ThemesAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/shangxian/pinkink/bean/ThemeBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "Lcom/chad/library/adapter/base/module/LoadMoreModule;", "themesList", "", "(Lcom/shangxian/pinkink/ui/album/OnlinePictureSelectorActivity;Ljava/util/List;)V", "themesItemHeight", "", "themesItemWidth", "convert", "", "holder", "item", "getSelectedCount", "getSelectedThemeList", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    final class ThemesAdapter extends BaseQuickAdapter<ThemeBean, BaseViewHolder> implements LoadMoreModule {
        private final int themesItemHeight;
        private final int themesItemWidth;
        private final List<ThemeBean> themesList;
        final /* synthetic */ OnlinePictureSelectorActivity this$0;

        @Override // com.chad.library.adapter.base.module.LoadMoreModule
        public /* synthetic */ BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapter baseQuickAdapter) {
            return LoadMoreModule.CC.$default$addLoadMoreModule(this, baseQuickAdapter);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ThemesAdapter(OnlinePictureSelectorActivity this$0, List<ThemeBean> themesList) {
            super(R.layout.item_picture_selector_themes, themesList);
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(themesList, "themesList");
            this.this$0 = this$0;
            this.themesList = themesList;
            int screenWidth = (AnyExtensionsKt.getScreenWidth(this) - NumberExtensionsKt.dp2px(52)) / 3;
            this.themesItemWidth = screenWidth;
            this.themesItemHeight = (int) (((double) screenWidth) * 1.733d);
        }

        public final List<ThemeBean> getSelectedThemeList() {
            List list = this.this$0.themeList;
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                if (((ThemeBean) obj).getIsSelected()) {
                    arrayList.add(obj);
                }
            }
            return arrayList;
        }

        public final int getSelectedCount() {
            List list = this.this$0.themeList;
            int i = 0;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (((ThemeBean) it.next()).getIsSelected() && (i = i + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            return i;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder holder, final ThemeBean item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            ConstraintLayout constraintLayout = (ConstraintLayout) holder.getView(R.id.clThemes);
            final ImageView imageView = (ImageView) holder.getView(R.id.ivSelect);
            constraintLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, this.themesItemHeight));
            Glide.with(getContext()).load(item.getCover()).into((ImageView) holder.getView(R.id.ivThemes));
            imageView.setSelected(item.getIsSelected());
            final OnlinePictureSelectorActivity onlinePictureSelectorActivity = this.this$0;
            constraintLayout.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.album.OnlinePictureSelectorActivity$ThemesAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    OnlinePictureSelectorActivity.ThemesAdapter.m151convert$lambda2(this.f$0, onlinePictureSelectorActivity, item, imageView, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: convert$lambda-2, reason: not valid java name */
        public static final void m151convert$lambda2(ThemesAdapter this$0, OnlinePictureSelectorActivity this$1, ThemeBean item, ImageView ivSelect, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this$1, "this$1");
            Intrinsics.checkNotNullParameter(item, "$item");
            Intrinsics.checkNotNullParameter(ivSelect, "$ivSelect");
            if (this$0.getSelectedCount() >= this$1.maxSelectedCount) {
                ContextExtensionsKt.toastLong(this$1, "一次最多只能选择" + this$1.maxSelectedCount + "张...");
                return;
            }
            item.setSelected(!item.getIsSelected());
            ivSelect.setSelected(item.getIsSelected());
            this$1.updateSelectedCount(this$0.getSelectedCount());
        }
    }
}
