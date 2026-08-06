package com.shangxian.pinkink.ui.album;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.lazyee.klib.extension.ViewExtensionsKt;
import com.lazyee.klib.mvvm.LoadingState;
import com.lazyee.klib.mvvm.ViewModel;
import com.lazyee.klib.recyclerview.decoration.GridSpacingItemDecoration;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BlueToothSyncImageActivity;
import com.shangxian.pinkink.bean.AlbumBean;
import com.shangxian.pinkink.bean.PageDataBean;
import com.shangxian.pinkink.bean.ThemeBean;
import com.shangxian.pinkink.bean.UserCreateThemeBean;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.constants.Keys;
import com.shangxian.pinkink.databinding.ActivityAlbumDetailBinding;
import com.shangxian.pinkink.event.UserCreateThemeAddedEvent;
import com.shangxian.pinkink.event.UserCreateThemeDeleteEvent;
import com.shangxian.pinkink.manager.PinkInkBlueToothManager;
import com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel;
import com.shangxian.pinkink.ui.album.AlbumDetailActivity;
import com.shangxian.pinkink.ui.themes.ThemesEditActivity;
import com.shangxian.pinkink.ui.themes.TransThemesActivity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: compiled from: AlbumDetailActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 ;2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\u00020\u0004:\u0003;<=B\u0005¢\u0006\u0002\u0010\u0005J\u0016\u0010(\u001a\u00020)2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0*H\u0002J\b\u0010+\u001a\u00020)H\u0016J\b\u0010,\u001a\u00020)H\u0017J\b\u0010-\u001a\u00020)H\u0017J\u0010\u0010.\u001a\u00020)2\u0006\u0010/\u001a\u000200H\u0016J\u0010\u00101\u001a\u00020)2\u0006\u00102\u001a\u000203H\u0017J\u0016\u00104\u001a\u00020)2\f\u00105\u001a\b\u0012\u0004\u0012\u00020\r0*H\u0002J\u0016\u00106\u001a\u00020)2\f\u00107\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019H\u0002J\u0018\u00108\u001a\u00020)2\u000e\u00107\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010%0\u0019H\u0002J\u0010\u00109\u001a\u00020)2\u0006\u0010:\u001a\u00020\u0015H\u0002R\u001d\u0010\u0006\u001a\u0004\u0018\u00010\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0010\u001a\u0010\u0012\f\u0012\n \u0013*\u0004\u0018\u00010\u00120\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0018\u00010\u001cR\u00020\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001f\u001a\u00020 8CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u000b\u001a\u0004\b!\u0010\"R\u0016\u0010$\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010%0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010&\u001a\b\u0018\u00010'R\u00020\u0000X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006>"}, d2 = {"Lcom/shangxian/pinkink/ui/album/AlbumDetailActivity;", "Lcom/shangxian/pinkink/base/BlueToothSyncImageActivity;", "Lcom/shangxian/pinkink/databinding/ActivityAlbumDetailBinding;", "Lcom/scwang/smart/refresh/layout/listener/OnRefreshListener;", "Lcom/chad/library/adapter/base/listener/OnLoadMoreListener;", "()V", "album", "Lcom/shangxian/pinkink/bean/AlbumBean;", "getAlbum", "()Lcom/shangxian/pinkink/bean/AlbumBean;", "album$delegate", "Lkotlin/Lazy;", "categoryId", "", "isEditMode", "", "mSelectThemePictureActivityResult", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "maxSelectedCount", "", "pageNum", "pageSize", "themeList", "", "Lcom/shangxian/pinkink/bean/ThemeBean;", "themesAdapter", "Lcom/shangxian/pinkink/ui/album/AlbumDetailActivity$ThemesAdapter;", "themesItemHeight", "themesItemWidth", "themesViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "getThemesViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "themesViewModel$delegate", "userCreateThemeList", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "userCreateThemesAdapter", "Lcom/shangxian/pinkink/ui/album/AlbumDetailActivity$UserCreateThemesAdapter;", "generateAndSendLocalImage", "", "", "initData", "initView", "onLoadMore", "onPageLoadingStateChanged", "state", "Lcom/lazyee/klib/mvvm/LoadingState;", "onRefresh", "refreshLayout", "Lcom/scwang/smart/refresh/layout/api/RefreshLayout;", "sendImage2BlueToothDevice", "imageFilePathList", "setThemesAdapter", "dataSource", "setUserCreateThemeAdapter", "updateSelectedCount", "selectedCount", "Companion", "ThemesAdapter", "UserCreateThemesAdapter", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class AlbumDetailActivity extends BlueToothSyncImageActivity<ActivityAlbumDetailBinding> implements OnRefreshListener, OnLoadMoreListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private boolean isEditMode;
    private final ActivityResultLauncher<Intent> mSelectThemePictureActivityResult;
    private final int maxSelectedCount;
    private ThemesAdapter themesAdapter;
    private final int themesItemHeight;
    private final int themesItemWidth;
    private UserCreateThemesAdapter userCreateThemesAdapter;

    /* JADX INFO: renamed from: album$delegate, reason: from kotlin metadata */
    private final Lazy album = LazyKt.lazy(new Function0<AlbumBean>() { // from class: com.shangxian.pinkink.ui.album.AlbumDetailActivity$album$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final AlbumBean invoke() {
            Intent intent = this.this$0.getIntent();
            if (intent == null) {
                return null;
            }
            return (AlbumBean) intent.getParcelableExtra(Keys.ALBUM);
        }
    });

    /* JADX INFO: renamed from: themesViewModel$delegate, reason: from kotlin metadata */
    private final Lazy themesViewModel = LazyKt.lazy(new Function0<ThemesViewModel>() { // from class: com.shangxian.pinkink.ui.album.AlbumDetailActivity$themesViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ThemesViewModel invoke() {
            return (ThemesViewModel) new ViewModelProvider(this.this$0).get(ThemesViewModel.class);
        }
    });
    private int pageNum = 1;
    private int pageSize = 20;
    private final List<ThemeBean> themeList = new ArrayList();
    private final List<UserCreateThemeBean> userCreateThemeList = new ArrayList();
    private String categoryId = "";

    /* JADX INFO: compiled from: AlbumDetailActivity.kt */
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

    public AlbumDetailActivity() {
        this.maxSelectedCount = AppConfig.INSTANCE.isNfcDevice() ? 1 : 15;
        ActivityResultLauncher<Intent> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.shangxian.pinkink.ui.album.AlbumDetailActivity$$ExternalSyntheticLambda6
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                AlbumDetailActivity.m141mSelectThemePictureActivityResult$lambda15(this.f$0, (ActivityResult) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResul…ave_successfully))\n\n    }");
        this.mSelectThemePictureActivityResult = activityResultLauncherRegisterForActivityResult;
        int screenWidth = (AnyExtensionsKt.getScreenWidth(this) - NumberExtensionsKt.dp2px(41)) / 2;
        this.themesItemWidth = screenWidth;
        this.themesItemHeight = (int) (((double) screenWidth) * 1.733d);
    }

    /* JADX INFO: compiled from: AlbumDetailActivity.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lcom/shangxian/pinkink/ui/album/AlbumDetailActivity$Companion;", "", "()V", "gotoThis", "", "context", "Landroid/content/Context;", "album", "Lcom/shangxian/pinkink/bean/AlbumBean;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void gotoThis(Context context, AlbumBean album) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(album, "album");
            Intent intent = new Intent(context, (Class<?>) AlbumDetailActivity.class);
            intent.putExtra(Keys.ALBUM, album);
            context.startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final AlbumBean getAlbum() {
        return (AlbumBean) this.album.getValue();
    }

    @ViewModel
    private final ThemesViewModel getThemesViewModel() {
        return (ThemesViewModel) this.themesViewModel.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initData() {
        super.initData();
        if (getAlbum() == null) {
            return;
        }
        AlbumBean album = getAlbum();
        Intrinsics.checkNotNull(album);
        if (album.getItemType() == AlbumBean.INSTANCE.getITEM_TYPE_AI()) {
            setThemesAdapter(this.themeList);
            this.categoryId = AppConfig.INSTANCE.getAiThemeCategoryId();
            getThemesViewModel().getThemeList(this.categoryId, this.pageNum, this.pageSize, true);
        } else {
            setUserCreateThemeAdapter(this.userCreateThemeList);
            ((ActivityAlbumDetailBinding) getMViewBinding()).refreshLayout.setEnableRefresh(false);
            ThemesViewModel themesViewModel = getThemesViewModel();
            AlbumBean album2 = getAlbum();
            Intrinsics.checkNotNull(album2);
            themesViewModel.getUserAlbumCreateThemeList(album2.getColumnId());
        }
        AlbumDetailActivity albumDetailActivity = this;
        getThemesViewModel().getThemeList().observe(albumDetailActivity, new Observer() { // from class: com.shangxian.pinkink.ui.album.AlbumDetailActivity$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AlbumDetailActivity.m133initData$lambda2(this.f$0, (PageDataBean) obj);
            }
        });
        getThemesViewModel().getUserCreateThemeList().observe(albumDetailActivity, new Observer() { // from class: com.shangxian.pinkink.ui.album.AlbumDetailActivity$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AlbumDetailActivity.m134initData$lambda3(this.f$0, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: initData$lambda-2, reason: not valid java name */
    public static final void m133initData$lambda2(AlbumDetailActivity this$0, PageDataBean pageDataBean) {
        BaseLoadMoreModule loadMoreModule;
        BaseLoadMoreModule loadMoreModule2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (pageDataBean == null) {
            return;
        }
        if (pageDataBean.getPageNum() == 1) {
            this$0.themeList.clear();
        }
        this$0.pageNum = pageDataBean.getPageNum();
        List listData = pageDataBean.getListData();
        if (listData != null) {
            this$0.themeList.addAll(listData);
        }
        ThemesAdapter themesAdapter = this$0.themesAdapter;
        if (themesAdapter != null) {
            themesAdapter.notifyDataSetChanged();
        }
        ((ActivityAlbumDetailBinding) this$0.getMViewBinding()).refreshLayout.finishRefresh();
        if (pageDataBean.isMore()) {
            ThemesAdapter themesAdapter2 = this$0.themesAdapter;
            if (themesAdapter2 == null || (loadMoreModule2 = themesAdapter2.getLoadMoreModule()) == null) {
                return;
            }
            loadMoreModule2.loadMoreComplete();
            return;
        }
        ThemesAdapter themesAdapter3 = this$0.themesAdapter;
        if (themesAdapter3 == null || (loadMoreModule = themesAdapter3.getLoadMoreModule()) == null) {
            return;
        }
        BaseLoadMoreModule.loadMoreEnd$default(loadMoreModule, false, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initData$lambda-3, reason: not valid java name */
    public static final void m134initData$lambda3(AlbumDetailActivity this$0, List it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.userCreateThemeList.clear();
        AlbumBean album = this$0.getAlbum();
        Intrinsics.checkNotNull(album);
        if (album.getColumnId() == 0) {
            this$0.userCreateThemeList.add(null);
        }
        List<UserCreateThemeBean> list = this$0.userCreateThemeList;
        Intrinsics.checkNotNullExpressionValue(it, "it");
        list.addAll(it);
        UserCreateThemesAdapter userCreateThemesAdapter = this$0.userCreateThemesAdapter;
        if (userCreateThemesAdapter == null) {
            return;
        }
        userCreateThemesAdapter.notifyDataSetChanged();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        final ActivityAlbumDetailBinding activityAlbumDetailBinding = (ActivityAlbumDetailBinding) getMViewBinding();
        updateSelectedCount(0);
        if (getAlbum() == null) {
            activityAlbumDetailBinding.pageStateSwitcher.showExceptionView();
            return;
        }
        Iterator it = activityAlbumDetailBinding.pageStateSwitcher.getTargetViews(R.id.tvRefresh).iterator();
        while (it.hasNext()) {
            ((TextView) it.next()).setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.album.AlbumDetailActivity$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlbumDetailActivity.m137initView$lambda13$lambda5$lambda4(this.f$0, view);
                }
            });
        }
        SmartRefreshLayout smartRefreshLayout = activityAlbumDetailBinding.refreshLayout;
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        smartRefreshLayout.setOnRefreshListener(this);
        initData();
        TextView textView = activityAlbumDetailBinding.titleBar.tvTitle;
        AlbumBean album = getAlbum();
        Intrinsics.checkNotNull(album);
        textView.setText(album.getName());
        activityAlbumDetailBinding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.album.AlbumDetailActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlbumDetailActivity.m138initView$lambda13$lambda7(this.f$0, view);
            }
        });
        AlbumBean album2 = getAlbum();
        Intrinsics.checkNotNull(album2);
        if (album2.getItemType() == AlbumBean.INSTANCE.getITEM_TYPE_NORMAL()) {
            ImageView ivAdd = activityAlbumDetailBinding.ivAdd;
            Intrinsics.checkNotNullExpressionValue(ivAdd, "ivAdd");
            ViewExtensionsKt.visible(ivAdd);
            activityAlbumDetailBinding.ivAdd.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.album.AlbumDetailActivity$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlbumDetailActivity.m139initView$lambda13$lambda8(this.f$0, view);
                }
            });
        }
        activityAlbumDetailBinding.ivEdit.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.album.AlbumDetailActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlbumDetailActivity.m140initView$lambda13$lambda9(this.f$0, activityAlbumDetailBinding, view);
            }
        });
        AlbumBean album3 = getAlbum();
        Intrinsics.checkNotNull(album3);
        if (album3.getItemType() == AlbumBean.INSTANCE.getITEM_TYPE_AI()) {
            TextView tvDelete = activityAlbumDetailBinding.tvDelete;
            Intrinsics.checkNotNullExpressionValue(tvDelete, "tvDelete");
            ViewExtensionsKt.gone(tvDelete);
        } else {
            TextView tvDelete2 = activityAlbumDetailBinding.tvDelete;
            Intrinsics.checkNotNullExpressionValue(tvDelete2, "tvDelete");
            ViewExtensionsKt.visible(tvDelete2);
        }
        activityAlbumDetailBinding.tvDelete.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.album.AlbumDetailActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlbumDetailActivity.m135initView$lambda13$lambda10(this.f$0, view);
            }
        });
        activityAlbumDetailBinding.tvSend.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.album.AlbumDetailActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlbumDetailActivity.m136initView$lambda13$lambda12(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-13$lambda-5$lambda-4, reason: not valid java name */
    public static final void m137initView$lambda13$lambda5$lambda4(AlbumDetailActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.themeList.clear();
        this$0.pageNum = 1;
        this$0.getThemesViewModel().getThemeList(this$0.categoryId, this$0.pageNum, this$0.pageSize, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-13$lambda-7, reason: not valid java name */
    public static final void m138initView$lambda13$lambda7(AlbumDetailActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-13$lambda-8, reason: not valid java name */
    public static final void m139initView$lambda13$lambda8(AlbumDetailActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mSelectThemePictureActivityResult.launch(OnlinePictureSelectorActivity.INSTANCE.getGotoIntent(this$0.getActivity(), AppConfig.INSTANCE.getAiThemeCategoryId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-13$lambda-9, reason: not valid java name */
    public static final void m140initView$lambda13$lambda9(AlbumDetailActivity this$0, ActivityAlbumDetailBinding this_run, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        boolean z = !this$0.isEditMode;
        this$0.isEditMode = z;
        ThemesAdapter themesAdapter = this$0.themesAdapter;
        if (themesAdapter != null) {
            themesAdapter.setEditMode(z);
        }
        UserCreateThemesAdapter userCreateThemesAdapter = this$0.userCreateThemesAdapter;
        if (userCreateThemesAdapter != null) {
            userCreateThemesAdapter.setEditMode(this$0.isEditMode);
        }
        if (this$0.isEditMode) {
            ThemesAdapter themesAdapter2 = this$0.themesAdapter;
            if (themesAdapter2 != null) {
                Intrinsics.checkNotNull(themesAdapter2);
                this$0.updateSelectedCount(themesAdapter2.getSelectedCount());
            } else {
                UserCreateThemesAdapter userCreateThemesAdapter2 = this$0.userCreateThemesAdapter;
                if (userCreateThemesAdapter2 != null) {
                    Intrinsics.checkNotNull(userCreateThemesAdapter2);
                    this$0.updateSelectedCount(userCreateThemesAdapter2.getSelectedCount());
                }
            }
            LinearLayout llBottom = this_run.llBottom;
            Intrinsics.checkNotNullExpressionValue(llBottom, "llBottom");
            ViewExtensionsKt.visible(llBottom);
            this_run.ivEdit.setImageResource(R.drawable.ic_black_right);
            return;
        }
        LinearLayout llBottom2 = this_run.llBottom;
        Intrinsics.checkNotNullExpressionValue(llBottom2, "llBottom");
        ViewExtensionsKt.gone(llBottom2);
        this_run.ivEdit.setImageResource(R.drawable.ic_index_album_settings);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-13$lambda-10, reason: not valid java name */
    public static final void m135initView$lambda13$lambda10(AlbumDetailActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        UserCreateThemesAdapter userCreateThemesAdapter = this$0.userCreateThemesAdapter;
        if (userCreateThemesAdapter == null) {
            return;
        }
        Intrinsics.checkNotNull(userCreateThemesAdapter);
        if (userCreateThemesAdapter.getSelectedCount() <= 0) {
            AlbumDetailActivity albumDetailActivity = this$0;
            String string = this$0.getString(R.string.toast_you_have_no_choose_any_items);
            Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.toast…have_no_choose_any_items)");
            ContextExtensionsKt.toastShort(albumDetailActivity, string);
            return;
        }
        UserCreateThemesAdapter userCreateThemesAdapter2 = this$0.userCreateThemesAdapter;
        List<UserCreateThemeBean> selectedList = userCreateThemesAdapter2 == null ? null : userCreateThemesAdapter2.getSelectedList();
        if (selectedList == null) {
            selectedList = CollectionsKt.emptyList();
        }
        this$0.getThemesViewModel().batchDeleteUserCreateTheme(selectedList);
        this$0.userCreateThemeList.removeAll(selectedList);
        UserCreateThemesAdapter userCreateThemesAdapter3 = this$0.userCreateThemesAdapter;
        if (userCreateThemesAdapter3 != null) {
            userCreateThemesAdapter3.notifyDataSetChanged();
        }
        EventBus eventBus = EventBus.getDefault();
        AlbumBean album = this$0.getAlbum();
        Intrinsics.checkNotNull(album);
        eventBus.post(new UserCreateThemeDeleteEvent(album.getColumnId(), selectedList));
        UserCreateThemesAdapter userCreateThemesAdapter4 = this$0.userCreateThemesAdapter;
        Intrinsics.checkNotNull(userCreateThemesAdapter4);
        this$0.updateSelectedCount(userCreateThemesAdapter4.getSelectedCount());
        AlbumDetailActivity albumDetailActivity2 = this$0;
        String string2 = this$0.getString(R.string.toast_delete_successfully);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(R.string.toast_delete_successfully)");
        ContextExtensionsKt.toastShort(albumDetailActivity2, string2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-13$lambda-12, reason: not valid java name */
    public static final void m136initView$lambda13$lambda12(AlbumDetailActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AlbumBean album = this$0.getAlbum();
        Intrinsics.checkNotNull(album);
        if (album.getItemType() == AlbumBean.INSTANCE.getITEM_TYPE_AI()) {
            ThemesAdapter themesAdapter = this$0.themesAdapter;
            if (themesAdapter == null) {
                return;
            }
            Intrinsics.checkNotNull(themesAdapter);
            if (themesAdapter.getSelectedCount() <= 0) {
                AlbumDetailActivity albumDetailActivity = this$0;
                String string = this$0.getString(R.string.toast_you_have_no_choose_any_items);
                Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.toast…have_no_choose_any_items)");
                ContextExtensionsKt.toastShort(albumDetailActivity, string);
                return;
            }
            ThemesAdapter themesAdapter2 = this$0.themesAdapter;
            Intrinsics.checkNotNull(themesAdapter2);
            this$0.generateAndSendLocalImage(themesAdapter2.getSelectedThemeList());
            return;
        }
        UserCreateThemesAdapter userCreateThemesAdapter = this$0.userCreateThemesAdapter;
        if (userCreateThemesAdapter == null) {
            return;
        }
        Intrinsics.checkNotNull(userCreateThemesAdapter);
        if (userCreateThemesAdapter.getSelectedCount() <= 0) {
            AlbumDetailActivity albumDetailActivity2 = this$0;
            String string2 = this$0.getString(R.string.toast_you_have_no_choose_any_items);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(R.string.toast…have_no_choose_any_items)");
            ContextExtensionsKt.toastShort(albumDetailActivity2, string2);
            return;
        }
        UserCreateThemesAdapter userCreateThemesAdapter2 = this$0.userCreateThemesAdapter;
        Intrinsics.checkNotNull(userCreateThemesAdapter2);
        List listFilterNotNull = CollectionsKt.filterNotNull(userCreateThemesAdapter2.getSelectedList());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listFilterNotNull, 10));
        Iterator it = listFilterNotNull.iterator();
        while (it.hasNext()) {
            arrayList.add(((UserCreateThemeBean) it.next()).getComposeImagePath());
        }
        this$0.sendImage2BlueToothDevice(arrayList);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.ui.album.AlbumDetailActivity$generateAndSendLocalImage$1, reason: invalid class name */
    /* JADX INFO: compiled from: AlbumDetailActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.ui.album.AlbumDetailActivity$generateAndSendLocalImage$1", f = "AlbumDetailActivity.kt", i = {1, 1}, l = {236, 245, 251}, m = "invokeSuspend", n = {"saveFilePath", "nfcBmpImageFilePath"}, s = {"L$0", "L$1"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<ThemeBean> $themeList;
        Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ AlbumDetailActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(List<ThemeBean> list, AlbumDetailActivity albumDetailActivity, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$themeList = list;
            this.this$0 = albumDetailActivity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$themeList, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:25:0x00c9  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x00f2  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r10) throws java.lang.Throwable {
            /*
                Method dump skipped, instruction units count: 276
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.shangxian.pinkink.ui.album.AlbumDetailActivity.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.shangxian.pinkink.ui.album.AlbumDetailActivity$generateAndSendLocalImage$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: AlbumDetailActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
        @DebugMetadata(c = "com.shangxian.pinkink.ui.album.AlbumDetailActivity$generateAndSendLocalImage$1$1", f = "AlbumDetailActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00191 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ AlbumDetailActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00191(AlbumDetailActivity albumDetailActivity, Continuation<? super C00191> continuation) {
                super(2, continuation);
                this.this$0 = albumDetailActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00191(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00191) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                AlbumDetailActivity albumDetailActivity = this.this$0;
                AlbumDetailActivity albumDetailActivity2 = albumDetailActivity;
                String string = albumDetailActivity.getString(R.string.string_image_processing_failed);
                Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.string_image_processing_failed)");
                ContextExtensionsKt.toastShort(albumDetailActivity2, string);
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: renamed from: com.shangxian.pinkink.ui.album.AlbumDetailActivity$generateAndSendLocalImage$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: AlbumDetailActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
        @DebugMetadata(c = "com.shangxian.pinkink.ui.album.AlbumDetailActivity$generateAndSendLocalImage$1$2", f = "AlbumDetailActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ AlbumDetailActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(AlbumDetailActivity albumDetailActivity, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.this$0 = albumDetailActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                AlbumDetailActivity albumDetailActivity = this.this$0;
                AlbumDetailActivity albumDetailActivity2 = albumDetailActivity;
                String string = albumDetailActivity.getString(R.string.string_image_processing_failed);
                Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.string_image_processing_failed)");
                ContextExtensionsKt.toastShort(albumDetailActivity2, string);
                return Unit.INSTANCE;
            }
        }
    }

    private final void generateAndSendLocalImage(List<ThemeBean> themeList) {
        if (AppConfig.INSTANCE.isNfcDevice()) {
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass1(themeList, this, null), 3, null);
        } else if (PinkInkBlueToothManager.INSTANCE.isConnected()) {
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass2(themeList, this, null), 3, null);
        } else {
            showConnectDeviceDialog();
        }
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.ui.album.AlbumDetailActivity$generateAndSendLocalImage$2, reason: invalid class name */
    /* JADX INFO: compiled from: AlbumDetailActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.ui.album.AlbumDetailActivity$generateAndSendLocalImage$2", f = "AlbumDetailActivity.kt", i = {0, 0, 2}, l = {268, 274, 279}, m = "invokeSuspend", n = {"localImagePath", "saveFile", "localImagePath"}, s = {"L$0", "L$3", "L$0"})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<ThemeBean> $themeList;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        final /* synthetic */ AlbumDetailActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(List<ThemeBean> list, AlbumDetailActivity albumDetailActivity, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$themeList = list;
            this.this$0 = albumDetailActivity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass2(this.$themeList, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x005f  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x00b3  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x00cf  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x00a2 -> B:19:0x00ab). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r15) throws java.lang.Throwable {
            /*
                Method dump skipped, instruction units count: 295
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.shangxian.pinkink.ui.album.AlbumDetailActivity.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.shangxian.pinkink.ui.album.AlbumDetailActivity$generateAndSendLocalImage$2$2, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: AlbumDetailActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
        @DebugMetadata(c = "com.shangxian.pinkink.ui.album.AlbumDetailActivity$generateAndSendLocalImage$2$2", f = "AlbumDetailActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00202 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ AlbumDetailActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00202(AlbumDetailActivity albumDetailActivity, Continuation<? super C00202> continuation) {
                super(2, continuation);
                this.this$0 = albumDetailActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00202(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00202) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                AlbumDetailActivity albumDetailActivity = this.this$0;
                AlbumDetailActivity albumDetailActivity2 = albumDetailActivity;
                String string = albumDetailActivity.getString(R.string.string_image_processing_failed);
                Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.string_image_processing_failed)");
                ContextExtensionsKt.toastShort(albumDetailActivity2, string);
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: renamed from: com.shangxian.pinkink.ui.album.AlbumDetailActivity$generateAndSendLocalImage$2$3, reason: invalid class name */
        /* JADX INFO: compiled from: AlbumDetailActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
        @DebugMetadata(c = "com.shangxian.pinkink.ui.album.AlbumDetailActivity$generateAndSendLocalImage$2$3", f = "AlbumDetailActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ AlbumDetailActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(AlbumDetailActivity albumDetailActivity, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.this$0 = albumDetailActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass3(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.showSyncImageDialog();
                return Unit.INSTANCE;
            }
        }
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.ui.album.AlbumDetailActivity$sendImage2BlueToothDevice$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AlbumDetailActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.ui.album.AlbumDetailActivity$sendImage2BlueToothDevice$1", f = "AlbumDetailActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C01061 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<String> $imageFilePathList;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01061(List<String> list, Continuation<? super C01061> continuation) {
            super(2, continuation);
            this.$imageFilePathList = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C01061(this.$imageFilePathList, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C01061) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            PinkInkBlueToothManager.INSTANCE.sendImage(this.$imageFilePathList);
            return Unit.INSTANCE;
        }
    }

    private final void sendImage2BlueToothDevice(List<String> imageFilePathList) {
        if (PinkInkBlueToothManager.INSTANCE.isConnected()) {
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C01061(imageFilePathList, null), 3, null);
            showSyncImageDialog();
        } else {
            showConnectDeviceDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void updateSelectedCount(int selectedCount) {
        ActivityAlbumDetailBinding activityAlbumDetailBinding = (ActivityAlbumDetailBinding) getMViewBinding();
        activityAlbumDetailBinding.tvSelectedCount.setText(String.valueOf(selectedCount));
        activityAlbumDetailBinding.tvRemainingTip.setText(getString(R.string.string_remaining_x_can_be_added, new Object[]{Integer.valueOf(this.maxSelectedCount - selectedCount)}));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: mSelectThemePictureActivityResult$lambda-15, reason: not valid java name */
    public static final void m141mSelectThemePictureActivityResult$lambda15(final AlbumDetailActivity this$0, ActivityResult activityResult) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (activityResult.getResultCode() == -1 && activityResult.getData() != null) {
            Intent data = activityResult.getData();
            Intrinsics.checkNotNull(data);
            ArrayList parcelableArrayListExtra = data.getParcelableArrayListExtra(Keys.DATA);
            if (parcelableArrayListExtra == null) {
                return;
            }
            AlbumBean album = this$0.getAlbum();
            Intrinsics.checkNotNull(album);
            this$0.getThemesViewModel().batchSaveUserCreateTheme(parcelableArrayListExtra, album.getColumnId(), new Function1<List<UserCreateThemeBean>, Unit>() { // from class: com.shangxian.pinkink.ui.album.AlbumDetailActivity$mSelectThemePictureActivityResult$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(List<UserCreateThemeBean> list) {
                    invoke2(list);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(List<UserCreateThemeBean> list) {
                    Intrinsics.checkNotNullParameter(list, "list");
                    this.this$0.userCreateThemeList.addAll(list);
                    AlbumDetailActivity.UserCreateThemesAdapter userCreateThemesAdapter = this.this$0.userCreateThemesAdapter;
                    if (userCreateThemesAdapter != null) {
                        userCreateThemesAdapter.notifyDataSetChanged();
                    }
                    EventBus eventBus = EventBus.getDefault();
                    AlbumBean album2 = this.this$0.getAlbum();
                    Intrinsics.checkNotNull(album2);
                    eventBus.post(new UserCreateThemeAddedEvent(album2.getColumnId(), list));
                }
            });
            AlbumDetailActivity albumDetailActivity = this$0;
            String string = this$0.getString(R.string.toast_save_successfully);
            Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.toast_save_successfully)");
            ContextExtensionsKt.toastShort(albumDetailActivity, string);
        }
    }

    @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
    public void onRefresh(RefreshLayout refreshLayout) {
        Intrinsics.checkNotNullParameter(refreshLayout, "refreshLayout");
        this.themeList.clear();
        this.pageNum = 1;
        getThemesViewModel().getThemeList(this.categoryId, this.pageNum, this.pageSize, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.BaseActivity, com.lazyee.klib.mvvm.MVVMBaseView
    public void onPageLoadingStateChanged(LoadingState state) {
        Intrinsics.checkNotNullParameter(state, "state");
        super.onPageLoadingStateChanged(state);
        int i = WhenMappings.$EnumSwitchMapping$0[state.ordinal()];
        if (i == 1) {
            ((ActivityAlbumDetailBinding) getMViewBinding()).pageStateSwitcher.showLoadingView();
        } else if (i == 2) {
            ((ActivityAlbumDetailBinding) getMViewBinding()).pageStateSwitcher.showContentView();
        } else {
            if (i != 3) {
                return;
            }
            ((ActivityAlbumDetailBinding) getMViewBinding()).pageStateSwitcher.showNetworkErrorView();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void setThemesAdapter(List<ThemeBean> dataSource) {
        BaseLoadMoreModule loadMoreModule;
        this.themesAdapter = new ThemesAdapter(this, dataSource);
        RecyclerView recyclerView = ((ActivityAlbumDetailBinding) getMViewBinding()).rvThemes;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(NumberExtensionsKt.dp2px(11.0f)));
        recyclerView.setAdapter(this.themesAdapter);
        ThemesAdapter themesAdapter = this.themesAdapter;
        if (themesAdapter == null || (loadMoreModule = themesAdapter.getLoadMoreModule()) == null) {
            return;
        }
        loadMoreModule.setOnLoadMoreListener(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void setUserCreateThemeAdapter(List<UserCreateThemeBean> dataSource) {
        this.userCreateThemesAdapter = new UserCreateThemesAdapter(this, this.userCreateThemeList);
        RecyclerView recyclerView = ((ActivityAlbumDetailBinding) getMViewBinding()).rvThemes;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(NumberExtensionsKt.dp2px(11.0f)));
        recyclerView.setAdapter(this.userCreateThemesAdapter);
    }

    @Override // com.chad.library.adapter.base.listener.OnLoadMoreListener
    public void onLoadMore() {
        getThemesViewModel().getThemeList(this.categoryId, this.pageNum + 1, this.pageSize, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: AlbumDetailActivity.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004B\u0013\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0002H\u0014J\u0006\u0010\u000e\u001a\u00020\u000fJ\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u0011J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/shangxian/pinkink/ui/album/AlbumDetailActivity$ThemesAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/shangxian/pinkink/bean/ThemeBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "Lcom/chad/library/adapter/base/module/LoadMoreModule;", "themesList", "", "(Lcom/shangxian/pinkink/ui/album/AlbumDetailActivity;Ljava/util/List;)V", "isEditMode", "", "convert", "", "holder", "item", "getSelectedCount", "", "getSelectedThemeList", "", "setEditMode", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    final class ThemesAdapter extends BaseQuickAdapter<ThemeBean, BaseViewHolder> implements LoadMoreModule {
        private boolean isEditMode;
        private final List<ThemeBean> themesList;
        final /* synthetic */ AlbumDetailActivity this$0;

        @Override // com.chad.library.adapter.base.module.LoadMoreModule
        public /* synthetic */ BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapter baseQuickAdapter) {
            return LoadMoreModule.CC.$default$addLoadMoreModule(this, baseQuickAdapter);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ThemesAdapter(AlbumDetailActivity this$0, List<ThemeBean> themesList) {
            super(R.layout.item_themes, themesList);
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(themesList, "themesList");
            this.this$0 = this$0;
            this.themesList = themesList;
        }

        public final void setEditMode(boolean isEditMode) {
            this.isEditMode = isEditMode;
            notifyDataSetChanged();
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
            RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.rlThemes);
            final ImageView imageView = (ImageView) holder.getView(R.id.ivSelect);
            relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, this.this$0.themesItemHeight));
            Glide.with(getContext()).load(item.getCover()).into((ImageView) holder.getView(R.id.ivThemes));
            if (this.isEditMode) {
                ViewExtensionsKt.visible(imageView);
                imageView.setSelected(item.getIsSelected());
            } else {
                ViewExtensionsKt.gone(imageView);
            }
            final AlbumDetailActivity albumDetailActivity = this.this$0;
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.album.AlbumDetailActivity$ThemesAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlbumDetailActivity.ThemesAdapter.m142convert$lambda2(this.f$0, item, albumDetailActivity, imageView, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: convert$lambda-2, reason: not valid java name */
        public static final void m142convert$lambda2(ThemesAdapter this$0, ThemeBean item, AlbumDetailActivity this$1, ImageView ivSelect, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(item, "$item");
            Intrinsics.checkNotNullParameter(this$1, "this$1");
            Intrinsics.checkNotNullParameter(ivSelect, "$ivSelect");
            if (this$0.isEditMode) {
                if (item.getIsSelected() || this$0.getSelectedCount() != this$1.maxSelectedCount) {
                    item.setSelected(!item.getIsSelected());
                    ivSelect.setSelected(item.getIsSelected());
                    this$1.updateSelectedCount(this$0.getSelectedCount());
                    return;
                }
                return;
            }
            String cover = item.getCover();
            String str = cover == null ? "" : cover;
            String cover2 = item.getCover();
            UserCreateThemeBean userCreateThemeBean = new UserCreateThemeBean(0L, null, 0L, str, cover2 == null ? "" : cover2, 7, null);
            AlbumBean album = this$1.getAlbum();
            Intrinsics.checkNotNull(album);
            if (album.getItemType() != AlbumBean.INSTANCE.getITEM_TYPE_AI()) {
                AlbumBean album2 = this$1.getAlbum();
                Intrinsics.checkNotNull(album2);
                userCreateThemeBean.setAlbumColumnId(album2.getColumnId());
            } else {
                userCreateThemeBean.setAlbumColumnId(0L);
            }
            TransThemesActivity.INSTANCE.gotoThis(this$1.getActivity(), userCreateThemeBean, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: AlbumDetailActivity.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0015\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001a\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032\b\u0010\f\u001a\u0004\u0018\u00010\u0002H\u0014J\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0010J\u0010\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/shangxian/pinkink/ui/album/AlbumDetailActivity$UserCreateThemesAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "userCreateThemeList", "", "(Lcom/shangxian/pinkink/ui/album/AlbumDetailActivity;Ljava/util/List;)V", "isEditMode", "", "convert", "", "holder", "item", "getSelectedCount", "", "getSelectedList", "", "setEditMode", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    final class UserCreateThemesAdapter extends BaseQuickAdapter<UserCreateThemeBean, BaseViewHolder> {
        private boolean isEditMode;
        final /* synthetic */ AlbumDetailActivity this$0;
        private final List<UserCreateThemeBean> userCreateThemeList;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public UserCreateThemesAdapter(AlbumDetailActivity this$0, List<UserCreateThemeBean> userCreateThemeList) {
            super(R.layout.item_themes, userCreateThemeList);
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(userCreateThemeList, "userCreateThemeList");
            this.this$0 = this$0;
            this.userCreateThemeList = userCreateThemeList;
        }

        public final void setEditMode(boolean isEditMode) {
            this.isEditMode = isEditMode;
            notifyDataSetChanged();
        }

        public final List<UserCreateThemeBean> getSelectedList() {
            List<UserCreateThemeBean> list = this.userCreateThemeList;
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                UserCreateThemeBean userCreateThemeBean = (UserCreateThemeBean) obj;
                if (userCreateThemeBean != null && userCreateThemeBean.getIsSelected()) {
                    arrayList.add(obj);
                }
            }
            return arrayList;
        }

        public final int getSelectedCount() {
            List<UserCreateThemeBean> list = this.userCreateThemeList;
            if ((list instanceof Collection) && list.isEmpty()) {
                return 0;
            }
            int i = 0;
            for (UserCreateThemeBean userCreateThemeBean : list) {
                if ((userCreateThemeBean != null && userCreateThemeBean.getIsSelected()) && (i = i + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
            return i;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder holder, final UserCreateThemeBean item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            ((RelativeLayout) holder.getView(R.id.rlThemes)).setLayoutParams(new LinearLayout.LayoutParams(-1, this.this$0.themesItemHeight));
            RoundedImageView roundedImageView = (RoundedImageView) holder.getView(R.id.ivThemes);
            LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.llCreate);
            final ImageView imageView = (ImageView) holder.getView(R.id.ivSelect);
            if (item == null) {
                ViewExtensionsKt.visible(linearLayout);
                ViewExtensionsKt.gone(roundedImageView);
                ViewExtensionsKt.gone(imageView);
            } else {
                ViewExtensionsKt.gone(linearLayout);
                RoundedImageView roundedImageView2 = roundedImageView;
                ViewExtensionsKt.visible(roundedImageView2);
                if (this.isEditMode) {
                    ViewExtensionsKt.visible(imageView);
                    imageView.setSelected(item.getIsSelected());
                } else {
                    ViewExtensionsKt.gone(imageView);
                }
                Glide.with(roundedImageView2).load(TextUtils.isEmpty(item.getComposeImagePath()) ? item.getBackgroundImagePath() : item.getComposeImagePath()).into(roundedImageView);
            }
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.album.AlbumDetailActivity$UserCreateThemesAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlbumDetailActivity.UserCreateThemesAdapter.m144convert$lambda2(this.f$0, view);
                }
            });
            final AlbumDetailActivity albumDetailActivity = this.this$0;
            roundedImageView.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.album.AlbumDetailActivity$UserCreateThemesAdapter$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlbumDetailActivity.UserCreateThemesAdapter.m145convert$lambda3(this.f$0, item, albumDetailActivity, imageView, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: convert$lambda-2, reason: not valid java name */
        public static final void m144convert$lambda2(UserCreateThemesAdapter this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            ThemesEditActivity.INSTANCE.gotoThis(this$0.getContext(), null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: convert$lambda-3, reason: not valid java name */
        public static final void m145convert$lambda3(UserCreateThemesAdapter this$0, UserCreateThemeBean userCreateThemeBean, AlbumDetailActivity this$1, ImageView ivSelect, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this$1, "this$1");
            Intrinsics.checkNotNullParameter(ivSelect, "$ivSelect");
            if (this$0.isEditMode) {
                Intrinsics.checkNotNull(userCreateThemeBean);
                if (userCreateThemeBean.getIsSelected() || this$0.getSelectedCount() != this$1.maxSelectedCount) {
                    userCreateThemeBean.setSelected(!userCreateThemeBean.getIsSelected());
                    ivSelect.setSelected(userCreateThemeBean.getIsSelected());
                    this$1.updateSelectedCount(this$0.getSelectedCount());
                    return;
                }
                return;
            }
            TransThemesActivity.Companion companion = TransThemesActivity.INSTANCE;
            Context context = this$0.getContext();
            Intrinsics.checkNotNull(userCreateThemeBean);
            companion.gotoThis(context, userCreateThemeBean, true);
        }
    }
}
