package com.shangxian.pinkink.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.lazyee.klib.extension.AnyExtensionsKt;
import com.lazyee.klib.extension.ContextExtensionsKt;
import com.lazyee.klib.extension.NumberExtensionsKt;
import com.lazyee.klib.mvvm.LoadingState;
import com.lazyee.klib.mvvm.ViewModel;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseFragment;
import com.shangxian.pinkink.bean.ActionBean;
import com.shangxian.pinkink.bean.IndexAdsBean;
import com.shangxian.pinkink.bean.IndexPageDataBean;
import com.shangxian.pinkink.bean.PageDataBean;
import com.shangxian.pinkink.bean.ThemeBean;
import com.shangxian.pinkink.bean.ThemeCategoryBean;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.constants.DeviceType;
import com.shangxian.pinkink.databinding.FragmentIndex2Binding;
import com.shangxian.pinkink.event.AIAlbumCoverChangedEvent;
import com.shangxian.pinkink.event.BlueToothConnectStateEvent;
import com.shangxian.pinkink.event.DeviceTypeChangedEvent;
import com.shangxian.pinkink.manager.PinkInkBlueToothManager;
import com.shangxian.pinkink.mvvm.viewmodel.IndexViewModel;
import com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel;
import com.shangxian.pinkink.ui.ai.AiDrawingChatActivity;
import com.shangxian.pinkink.ui.device.SwitchDeviceActivity;
import com.shangxian.pinkink.ui.main.IndexFragment2;
import com.shangxian.pinkink.ui.themes.ThemeDetailActivity;
import com.shangxian.pinkink.ui.themes.ThemesEditActivity;
import com.shangxian.pinkink.ui.themes.TransThemesActivity;
import com.shangxian.pinkink.ui.webview.CommonWebViewActivity;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* JADX INFO: compiled from: IndexFragment2.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\u00020\u0004:\u0003567B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010#\u001a\u00020$H\u0017J\u0010\u0010%\u001a\u00020$2\u0006\u0010&\u001a\u00020'H\u0007J\u0012\u0010(\u001a\u00020$2\b\u0010)\u001a\u0004\u0018\u00010*H\u0016J\b\u0010+\u001a\u00020$H\u0016J\b\u0010,\u001a\u00020$H\u0016J\u0010\u0010-\u001a\u00020$2\u0006\u0010.\u001a\u00020/H\u0016J\u0010\u00100\u001a\u00020$2\u0006\u00101\u001a\u000202H\u0016J\u0010\u00103\u001a\u00020$2\u0006\u0010&\u001a\u000204H\u0007R\u001f\u0010\u0006\u001a\u00060\u0007R\u00020\u00008BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001f\u0010\u000f\u001a\u00060\u0010R\u00020\u00008BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000b\u001a\u0004\b\u0011\u0010\u0012R\u001b\u0010\u0014\u001a\u00020\u00158CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u000b\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u001e\u001a\u00020\u001f8CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010\u000b\u001a\u0004\b \u0010!¨\u00068"}, d2 = {"Lcom/shangxian/pinkink/ui/main/IndexFragment2;", "Lcom/shangxian/pinkink/base/BaseFragment;", "Lcom/shangxian/pinkink/databinding/FragmentIndex2Binding;", "Lcom/scwang/smart/refresh/layout/listener/OnRefreshListener;", "Lcom/chad/library/adapter/base/listener/OnLoadMoreListener;", "()V", "indexHeader", "Lcom/shangxian/pinkink/ui/main/IndexFragment2$IndexHeader;", "getIndexHeader", "()Lcom/shangxian/pinkink/ui/main/IndexFragment2$IndexHeader;", "indexHeader$delegate", "Lkotlin/Lazy;", "indexThemeList", "", "Lcom/shangxian/pinkink/bean/ThemeBean;", "indexThemesAdapter", "Lcom/shangxian/pinkink/ui/main/IndexFragment2$IndexThemeAdapter;", "getIndexThemesAdapter", "()Lcom/shangxian/pinkink/ui/main/IndexFragment2$IndexThemeAdapter;", "indexThemesAdapter$delegate", "indexViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/IndexViewModel;", "getIndexViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/IndexViewModel;", "indexViewModel$delegate", "pageNum", "", "pageSize", "selectedThemeCategoryId", "", "themesViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "getThemesViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "themesViewModel$delegate", "initView", "", "onBluetoothConnectStateChanged", NotificationCompat.CATEGORY_EVENT, "Lcom/shangxian/pinkink/event/BlueToothConnectStateEvent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onLoadMore", "onPageLoadingStateChanged", "state", "Lcom/lazyee/klib/mvvm/LoadingState;", "onRefresh", "refreshLayout", "Lcom/scwang/smart/refresh/layout/api/RefreshLayout;", "onSwitchDeviceType", "Lcom/shangxian/pinkink/event/DeviceTypeChangedEvent;", "IndexBannerAdapter", "IndexHeader", "IndexThemeAdapter", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class IndexFragment2 extends BaseFragment<FragmentIndex2Binding> implements OnRefreshListener, OnLoadMoreListener {

    /* JADX INFO: renamed from: indexViewModel$delegate, reason: from kotlin metadata */
    private final Lazy indexViewModel = LazyKt.lazy(new Function0<IndexViewModel>() { // from class: com.shangxian.pinkink.ui.main.IndexFragment2$indexViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final IndexViewModel invoke() {
            return (IndexViewModel) new ViewModelProvider(this.this$0).get(IndexViewModel.class);
        }
    });

    /* JADX INFO: renamed from: themesViewModel$delegate, reason: from kotlin metadata */
    private final Lazy themesViewModel = LazyKt.lazy(new Function0<ThemesViewModel>() { // from class: com.shangxian.pinkink.ui.main.IndexFragment2$themesViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ThemesViewModel invoke() {
            return (ThemesViewModel) new ViewModelProvider(this.this$0).get(ThemesViewModel.class);
        }
    });
    private final List<ThemeBean> indexThemeList = new ArrayList();

    /* JADX INFO: renamed from: indexThemesAdapter$delegate, reason: from kotlin metadata */
    private final Lazy indexThemesAdapter = LazyKt.lazy(new Function0<IndexThemeAdapter>() { // from class: com.shangxian.pinkink.ui.main.IndexFragment2$indexThemesAdapter$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final IndexFragment2.IndexThemeAdapter invoke() {
            IndexFragment2 indexFragment2 = this.this$0;
            return new IndexFragment2.IndexThemeAdapter(indexFragment2, indexFragment2.indexThemeList);
        }
    });
    private int pageNum = 1;
    private int pageSize = 20;

    /* JADX INFO: renamed from: indexHeader$delegate, reason: from kotlin metadata */
    private final Lazy indexHeader = LazyKt.lazy(new Function0<IndexHeader>() { // from class: com.shangxian.pinkink.ui.main.IndexFragment2$indexHeader$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final IndexFragment2.IndexHeader invoke() {
            return new IndexFragment2.IndexHeader(this.this$0);
        }
    });
    private String selectedThemeCategoryId = "";

    /* JADX INFO: compiled from: IndexFragment2.kt */
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
    private final IndexViewModel getIndexViewModel() {
        return (IndexViewModel) this.indexViewModel.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @ViewModel
    public final ThemesViewModel getThemesViewModel() {
        return (ThemesViewModel) this.themesViewModel.getValue();
    }

    private final IndexThemeAdapter getIndexThemesAdapter() {
        return (IndexThemeAdapter) this.indexThemesAdapter.getValue();
    }

    private final IndexHeader getIndexHeader() {
        return (IndexHeader) this.indexHeader.getValue();
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingFragment
    public void initView() {
        super.initView();
        FragmentIndex2Binding fragmentIndex2Binding = (FragmentIndex2Binding) getMViewBinding();
        Iterator it = fragmentIndex2Binding.pageStateSwitcher.getTargetViews(R.id.tvRefresh).iterator();
        while (it.hasNext()) {
            ((TextView) it.next()).setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.IndexFragment2$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    IndexFragment2.m223initView$lambda4$lambda1$lambda0(this.f$0, view);
                }
            });
        }
        fragmentIndex2Binding.ivBlueToothConnectState.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.IndexFragment2$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Exception {
                IndexFragment2.m224initView$lambda4$lambda2(this.f$0, view);
            }
        });
        SmartRefreshLayout smartRefreshLayout = ((FragmentIndex2Binding) getMViewBinding()).refreshLayout;
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        smartRefreshLayout.setOnRefreshListener(this);
        fragmentIndex2Binding.ivBlueToothConnectState.setVisibility(AppConfig.INSTANCE.isBlueToothDevice() ? 0 : 8);
        BaseQuickAdapter.addHeaderView$default(getIndexThemesAdapter(), getIndexHeader(), 0, 0, 6, null);
        getIndexThemesAdapter().getLoadMoreModule().setOnLoadMoreListener(this);
        fragmentIndex2Binding.rvContent.setAdapter(getIndexThemesAdapter());
        getIndexViewModel().getIndexData(true);
        IndexFragment2 indexFragment2 = this;
        getIndexViewModel().getIndexPageData().observe(indexFragment2, new Observer() { // from class: com.shangxian.pinkink.ui.main.IndexFragment2$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                IndexFragment2.m225initView$lambda6(this.f$0, (IndexPageDataBean) obj);
            }
        });
        getThemesViewModel().getThemeCategoryList().observe(indexFragment2, new Observer() { // from class: com.shangxian.pinkink.ui.main.IndexFragment2$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                IndexFragment2.m226initView$lambda7(this.f$0, (List) obj);
            }
        });
        getThemesViewModel().getThemeList().observe(indexFragment2, new Observer() { // from class: com.shangxian.pinkink.ui.main.IndexFragment2$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                IndexFragment2.m222initView$lambda10(this.f$0, (PageDataBean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-1$lambda-0, reason: not valid java name */
    public static final void m223initView$lambda4$lambda1$lambda0(IndexFragment2 this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getIndexViewModel().getIndexData(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-2, reason: not valid java name */
    public static final void m224initView$lambda4$lambda2(IndexFragment2 this$0, View view) throws Exception {
        FragmentActivity activity;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (PinkInkBlueToothManager.INSTANCE.isConnected() || (activity = this$0.getActivity()) == null) {
            return;
        }
        ContextExtensionsKt.goto$default(activity, SwitchDeviceActivity.class, null, null, null, null, 30, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: initView$lambda-6, reason: not valid java name */
    public static final void m225initView$lambda6(IndexFragment2 this$0, IndexPageDataBean indexPageDataBean) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getIndexHeader().setBannerData(indexPageDataBean.getAds());
        this$0.getIndexThemesAdapter().notifyDataSetChanged();
        this$0.getThemesViewModel().m118getThemeCategoryList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-7, reason: not valid java name */
    public static final void m226initView$lambda7(IndexFragment2 this$0, List themeCategoryList) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (themeCategoryList.isEmpty()) {
            return;
        }
        IndexHeader indexHeader = this$0.getIndexHeader();
        Intrinsics.checkNotNullExpressionValue(themeCategoryList, "themeCategoryList");
        indexHeader.setThemeCategory(themeCategoryList);
        this$0.getIndexThemesAdapter().notifyDataSetChanged();
        this$0.selectedThemeCategoryId = ((ThemeCategoryBean) themeCategoryList.get(0)).getId();
        this$0.pageNum = 1;
        this$0.getThemesViewModel().getThemeList(this$0.selectedThemeCategoryId, this$0.pageNum, this$0.pageSize, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: initView$lambda-10, reason: not valid java name */
    public static final void m222initView$lambda10(IndexFragment2 this$0, PageDataBean pageDataBean) {
        ThemeBean themeBean;
        String cover;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int pageNum = pageDataBean.getPageNum();
        this$0.pageNum = pageNum;
        if (pageNum == 1) {
            if (Intrinsics.areEqual(this$0.selectedThemeCategoryId, AppConfig.INSTANCE.getAiThemeCategoryId())) {
                AppConfig appConfig = AppConfig.INSTANCE;
                List listData = pageDataBean.getListData();
                String str = "";
                if (listData != null && (themeBean = (ThemeBean) CollectionsKt.firstOrNull(listData)) != null && (cover = themeBean.getCover()) != null) {
                    str = cover;
                }
                appConfig.setAiAlbumCoverUrl(str);
                if (!TextUtils.isEmpty(AppConfig.INSTANCE.getAiAlbumCoverUrl())) {
                    EventBus.getDefault().post(new AIAlbumCoverChangedEvent(AppConfig.INSTANCE.getAiAlbumCoverUrl()));
                }
            }
            this$0.indexThemeList.clear();
        }
        List listData2 = pageDataBean.getListData();
        if (listData2 != null) {
            this$0.indexThemeList.addAll(listData2);
        }
        this$0.getIndexThemesAdapter().notifyDataSetChanged();
        ((FragmentIndex2Binding) this$0.getMViewBinding()).refreshLayout.finishRefresh();
        if (pageDataBean.isMore()) {
            this$0.getIndexThemesAdapter().getLoadMoreModule().loadMoreComplete();
        } else {
            BaseLoadMoreModule.loadMoreEnd$default(this$0.getIndexThemesAdapter().getLoadMoreModule(), false, 1, null);
        }
    }

    @Override // com.lazyee.klib.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onSwitchDeviceType(DeviceTypeChangedEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        ((FragmentIndex2Binding) getMViewBinding()).ivBlueToothConnectState.setVisibility(Intrinsics.areEqual(event.getNewDeviceType(), DeviceType.TYPE_BLUE_TOOTH) ? 0 : 8);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onBluetoothConnectStateChanged(BlueToothConnectStateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        ((FragmentIndex2Binding) getMViewBinding()).ivBlueToothConnectState.setSelected(PinkInkBlueToothManager.INSTANCE.isConnected());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.BaseFragment, com.lazyee.klib.mvvm.MVVMBaseView
    public void onPageLoadingStateChanged(LoadingState state) {
        Intrinsics.checkNotNullParameter(state, "state");
        super.onPageLoadingStateChanged(state);
        int i = WhenMappings.$EnumSwitchMapping$0[state.ordinal()];
        if (i == 1) {
            ((FragmentIndex2Binding) getMViewBinding()).pageStateSwitcher.showLoadingView();
        } else if (i == 2) {
            ((FragmentIndex2Binding) getMViewBinding()).pageStateSwitcher.showContentView();
        } else {
            if (i != 3) {
                return;
            }
            ((FragmentIndex2Binding) getMViewBinding()).pageStateSwitcher.showNetworkErrorView();
        }
    }

    @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
    public void onRefresh(RefreshLayout refreshLayout) {
        Intrinsics.checkNotNullParameter(refreshLayout, "refreshLayout");
        this.pageNum = 1;
        getIndexViewModel().getIndexData(false);
    }

    @Override // com.chad.library.adapter.base.listener.OnLoadMoreListener
    public void onLoadMore() {
        getThemesViewModel().getThemeList(this.selectedThemeCategoryId, this.pageNum + 1, this.pageSize, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: IndexFragment2.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u0014J\u0014\u0010\u0015\u001a\u00020\u00122\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0014R!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\t\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0018"}, d2 = {"Lcom/shangxian/pinkink/ui/main/IndexFragment2$IndexHeader;", "Landroid/widget/LinearLayout;", "(Lcom/shangxian/pinkink/ui/main/IndexFragment2;)V", "bannerView", "Lcom/zhpan/bannerview/BannerViewPager;", "Lcom/shangxian/pinkink/bean/IndexAdsBean;", "getBannerView", "()Lcom/zhpan/bannerview/BannerViewPager;", "bannerView$delegate", "Lkotlin/Lazy;", "mFragmentContainerHelper", "Lnet/lucode/hackware/magicindicator/FragmentContainerHelper;", "magicIndicator", "Lnet/lucode/hackware/magicindicator/MagicIndicator;", "getMagicIndicator", "()Lnet/lucode/hackware/magicindicator/MagicIndicator;", "magicIndicator$delegate", "setBannerData", "", "indexAdsList", "", "setThemeCategory", "themeCategoryList", "Lcom/shangxian/pinkink/bean/ThemeCategoryBean;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    final class IndexHeader extends LinearLayout {

        /* JADX INFO: renamed from: bannerView$delegate, reason: from kotlin metadata */
        private final Lazy bannerView;
        private final FragmentContainerHelper mFragmentContainerHelper;

        /* JADX INFO: renamed from: magicIndicator$delegate, reason: from kotlin metadata */
        private final Lazy magicIndicator;
        final /* synthetic */ IndexFragment2 this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IndexHeader(final IndexFragment2 this$0) {
            super(this$0.getContext());
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this.this$0 = this$0;
            this.bannerView = LazyKt.lazy(new Function0<BannerViewPager<IndexAdsBean>>() { // from class: com.shangxian.pinkink.ui.main.IndexFragment2$IndexHeader$bannerView$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final BannerViewPager<IndexAdsBean> invoke() {
                    return (BannerViewPager) this.this$0.findViewById(R.id.bannerView);
                }
            });
            this.magicIndicator = LazyKt.lazy(new Function0<MagicIndicator>() { // from class: com.shangxian.pinkink.ui.main.IndexFragment2$IndexHeader$magicIndicator$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final MagicIndicator invoke() {
                    return (MagicIndicator) this.this$0.findViewById(R.id.magicIndicator);
                }
            });
            this.mFragmentContainerHelper = new FragmentContainerHelper();
            LayoutInflater.from(getContext()).inflate(R.layout.layout_index_header2, (ViewGroup) this, true);
            ((LinearLayout) findViewById(R.id.llCreate)).setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.IndexFragment2$IndexHeader$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    IndexFragment2.IndexHeader.m230_init_$lambda0(this.f$0, view);
                }
            });
            ((LinearLayout) findViewById(R.id.llCreateByAI)).setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.IndexFragment2$IndexHeader$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Exception {
                    IndexFragment2.IndexHeader.m231_init_$lambda1(this$0, view);
                }
            });
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            int screenWidth = AnyExtensionsKt.getScreenWidth(context);
            getBannerView().setLayoutParams(new LinearLayout.LayoutParams(screenWidth, (int) (((double) screenWidth) * 0.3733d)));
        }

        private final BannerViewPager<IndexAdsBean> getBannerView() {
            Object value = this.bannerView.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "<get-bannerView>(...)");
            return (BannerViewPager) value;
        }

        private final MagicIndicator getMagicIndicator() {
            Object value = this.magicIndicator.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "<get-magicIndicator>(...)");
            return (MagicIndicator) value;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: _init_$lambda-0, reason: not valid java name */
        public static final void m230_init_$lambda0(IndexHeader this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            ThemesEditActivity.Companion companion = ThemesEditActivity.INSTANCE;
            Context context = this$0.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            companion.gotoThis(context, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: _init_$lambda-1, reason: not valid java name */
        public static final void m231_init_$lambda1(IndexFragment2 this$0, View view) throws Exception {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            FragmentActivity activity = this$0.getActivity();
            if (activity == null) {
                return;
            }
            ContextExtensionsKt.goto$default(activity, AiDrawingChatActivity.class, null, null, null, null, 30, null);
        }

        public final void setBannerData(List<IndexAdsBean> indexAdsList) {
            Intrinsics.checkNotNullParameter(indexAdsList, "indexAdsList");
            BannerViewPager<IndexAdsBean> bannerView = getBannerView();
            IndexFragment2 indexFragment2 = this.this$0;
            bannerView.setAdapter(new IndexBannerAdapter(indexFragment2));
            bannerView.registerLifecycleObserver(indexFragment2.getLifecycle());
            bannerView.setInterval(5000);
            bannerView.setIndicatorSliderColor(Color.parseColor("#FFE0E0E0"), Color.parseColor("#FFFF7A9A"));
            bannerView.create(indexAdsList);
        }

        public final void setThemeCategory(List<ThemeCategoryBean> themeCategoryList) {
            Intrinsics.checkNotNullParameter(themeCategoryList, "themeCategoryList");
            CommonNavigator commonNavigator = new CommonNavigator(this.this$0.getActivity());
            commonNavigator.setAdapter(new IndexFragment2$IndexHeader$setThemeCategory$1(themeCategoryList, this, this.this$0));
            getMagicIndicator().setNavigator(commonNavigator);
            this.mFragmentContainerHelper.attachMagicIndicator(getMagicIndicator());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: IndexFragment2.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J.\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u00072\u0006\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0014J\u0010\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\nH\u0016¨\u0006\u000e"}, d2 = {"Lcom/shangxian/pinkink/ui/main/IndexFragment2$IndexBannerAdapter;", "Lcom/zhpan/bannerview/BaseBannerAdapter;", "Lcom/shangxian/pinkink/bean/IndexAdsBean;", "(Lcom/shangxian/pinkink/ui/main/IndexFragment2;)V", "bindData", "", "holder", "Lcom/zhpan/bannerview/BaseViewHolder;", "data", "position", "", "pageSize", "getLayoutId", "viewType", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    final class IndexBannerAdapter extends BaseBannerAdapter<IndexAdsBean> {
        final /* synthetic */ IndexFragment2 this$0;

        @Override // com.zhpan.bannerview.BaseBannerAdapter
        public int getLayoutId(int viewType) {
            return R.layout.item_index_banner_image;
        }

        public IndexBannerAdapter(IndexFragment2 this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this.this$0 = this$0;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.zhpan.bannerview.BaseBannerAdapter
        public void bindData(BaseViewHolder<IndexAdsBean> holder, final IndexAdsBean data, int position, int pageSize) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(data, "data");
            RoundedImageView roundedImageView = (RoundedImageView) holder.itemView.findViewById(R.id.ivBanner);
            Glide.with(this.this$0).load(data.getImg()).into(roundedImageView);
            final IndexFragment2 indexFragment2 = this.this$0;
            roundedImageView.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.IndexFragment2$IndexBannerAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    IndexFragment2.IndexBannerAdapter.m227bindData$lambda0(indexFragment2, data, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: bindData$lambda-0, reason: not valid java name */
        public static final void m227bindData$lambda0(IndexFragment2 this$0, IndexAdsBean data, View view) {
            String target;
            String target2;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(data, "$data");
            if (this$0.getActivity() == null || data.getAction() == null) {
                return;
            }
            ActionBean action = data.getAction();
            String str = "";
            if (Intrinsics.areEqual(action == null ? null : action.getType(), "url")) {
                CommonWebViewActivity.Companion companion = CommonWebViewActivity.INSTANCE;
                FragmentActivity activity = this$0.getActivity();
                Intrinsics.checkNotNull(activity);
                Intrinsics.checkNotNullExpressionValue(activity, "activity!!");
                FragmentActivity fragmentActivity = activity;
                ActionBean action2 = data.getAction();
                if (action2 != null && (target2 = action2.getTarget()) != null) {
                    str = target2;
                }
                companion.gotoThis(fragmentActivity, str);
                return;
            }
            ActionBean action3 = data.getAction();
            if (Intrinsics.areEqual(action3 != null ? action3.getType() : null, "theme")) {
                ThemeDetailActivity.Companion companion2 = ThemeDetailActivity.INSTANCE;
                FragmentActivity activity2 = this$0.getActivity();
                Intrinsics.checkNotNull(activity2);
                Intrinsics.checkNotNullExpressionValue(activity2, "activity!!");
                FragmentActivity fragmentActivity2 = activity2;
                ActionBean action4 = data.getAction();
                if (action4 != null && (target = action4.getTarget()) != null) {
                    str = target;
                }
                companion2.gotoThis(fragmentActivity2, str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: IndexFragment2.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004B\u0013\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0002H\u0014R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/shangxian/pinkink/ui/main/IndexFragment2$IndexThemeAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/shangxian/pinkink/bean/ThemeBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "Lcom/chad/library/adapter/base/module/LoadMoreModule;", "themeList", "", "(Lcom/shangxian/pinkink/ui/main/IndexFragment2;Ljava/util/List;)V", "themesItemHeight", "", "themesItemWidth", "convert", "", "holder", "item", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    final class IndexThemeAdapter extends BaseQuickAdapter<ThemeBean, com.chad.library.adapter.base.viewholder.BaseViewHolder> implements LoadMoreModule {
        private final int themesItemHeight;
        private final int themesItemWidth;
        final /* synthetic */ IndexFragment2 this$0;

        @Override // com.chad.library.adapter.base.module.LoadMoreModule
        public /* synthetic */ BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapter baseQuickAdapter) {
            return LoadMoreModule.CC.$default$addLoadMoreModule(this, baseQuickAdapter);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IndexThemeAdapter(IndexFragment2 this$0, List<ThemeBean> themeList) {
            super(R.layout.item_index_themes, themeList);
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(themeList, "themeList");
            this.this$0 = this$0;
            int screenWidth = (AnyExtensionsKt.getScreenWidth(this) - NumberExtensionsKt.dp2px(30)) / 2;
            this.themesItemWidth = screenWidth;
            this.themesItemHeight = (int) (((double) screenWidth) * 1.733d);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(com.chad.library.adapter.base.viewholder.BaseViewHolder holder, final ThemeBean item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            ImageView imageView = (ImageView) holder.getView(R.id.ivThemes);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, this.themesItemHeight);
            layoutParams.leftMargin = NumberExtensionsKt.dp2px(10);
            layoutParams.bottomMargin = NumberExtensionsKt.dp2px(10);
            if (this.this$0.indexThemeList.indexOf(item) % 2 == 1) {
                layoutParams.rightMargin = NumberExtensionsKt.dp2px(10);
            }
            imageView.setLayoutParams(layoutParams);
            Glide.with(getContext()).load(item.getCover()).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.main.IndexFragment2$IndexThemeAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    IndexFragment2.IndexThemeAdapter.m233convert$lambda0(this.f$0, item, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: convert$lambda-0, reason: not valid java name */
        public static final void m233convert$lambda0(IndexThemeAdapter this$0, ThemeBean theme, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(theme, "$theme");
            TransThemesActivity.Companion companion = TransThemesActivity.INSTANCE;
            Context context = this$0.getContext();
            String cover = theme.getCover();
            if (cover == null) {
                cover = "";
            }
            companion.gotoThis(context, cover);
        }
    }
}
