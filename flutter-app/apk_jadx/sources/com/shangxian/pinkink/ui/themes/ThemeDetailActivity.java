package com.shangxian.pinkink.ui.themes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.lazyee.klib.mvvm.LoadingState;
import com.lazyee.klib.mvvm.ViewModel;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.bean.ThemeBean;
import com.shangxian.pinkink.bean.UserCreateThemeBean;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.constants.InkScreenSize;
import com.shangxian.pinkink.constants.Keys;
import com.shangxian.pinkink.databinding.ActivityThemeDetailBinding;
import com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel;
import com.shangxian.pinkink.ui.themes.ThemesEditActivity;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ThemeDetailActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001b2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001bB\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\b\u0010\u0017\u001a\u00020\u0012H\u0016J\u0010\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001aH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001b\u0010\f\u001a\u00020\r8CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001c"}, d2 = {"Lcom/shangxian/pinkink/ui/themes/ThemeDetailActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityThemeDetailBinding;", "()V", "isLike", "", "themeId", "", "getThemeId", "()Ljava/lang/String;", "themeId$delegate", "Lkotlin/Lazy;", "themesViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "getThemesViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "themesViewModel$delegate", "bindThemeDetailToView", "", "theme", "Lcom/shangxian/pinkink/bean/ThemeBean;", "getLikeStatus", "getThemeDetail", "initView", "onPageLoadingStateChanged", "state", "Lcom/lazyee/klib/mvvm/LoadingState;", "Companion", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ThemeDetailActivity extends BaseActivity<ActivityThemeDetailBinding> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private boolean isLike;

    /* JADX INFO: renamed from: themesViewModel$delegate, reason: from kotlin metadata */
    private final Lazy themesViewModel = LazyKt.lazy(new Function0<ThemesViewModel>() { // from class: com.shangxian.pinkink.ui.themes.ThemeDetailActivity$themesViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ThemesViewModel invoke() {
            return (ThemesViewModel) new ViewModelProvider(this.this$0).get(ThemesViewModel.class);
        }
    });

    /* JADX INFO: renamed from: themeId$delegate, reason: from kotlin metadata */
    private final Lazy themeId = LazyKt.lazy(new Function0<String>() { // from class: com.shangxian.pinkink.ui.themes.ThemeDetailActivity$themeId$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            String stringExtra = this.this$0.getIntent().getStringExtra(Keys.THEME_ID);
            return stringExtra == null ? "" : stringExtra;
        }
    });

    /* JADX INFO: compiled from: ThemeDetailActivity.kt */
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

    /* JADX INFO: compiled from: ThemeDetailActivity.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lcom/shangxian/pinkink/ui/themes/ThemeDetailActivity$Companion;", "", "()V", "gotoThis", "", "context", "Landroid/content/Context;", "themeId", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void gotoThis(Context context, String themeId) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(themeId, "themeId");
            Intent intent = new Intent(context, (Class<?>) ThemeDetailActivity.class);
            intent.putExtra(Keys.THEME_ID, themeId);
            context.startActivity(intent);
        }
    }

    @ViewModel
    private final ThemesViewModel getThemesViewModel() {
        return (ThemesViewModel) this.themesViewModel.getValue();
    }

    private final String getThemeId() {
        return (String) this.themeId.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        ActivityThemeDetailBinding activityThemeDetailBinding = (ActivityThemeDetailBinding) getMViewBinding();
        activityThemeDetailBinding.titleBar.tvTitle.setText(getString(R.string.title_theme));
        activityThemeDetailBinding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.ThemeDetailActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThemeDetailActivity.m313initView$lambda3$lambda0(this.f$0, view);
            }
        });
        Iterator it = activityThemeDetailBinding.pageStateSwitcher.getTargetViews(R.id.tvRefresh).iterator();
        while (it.hasNext()) {
            ((TextView) it.next()).setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.ThemeDetailActivity$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ThemeDetailActivity.m314initView$lambda3$lambda2$lambda1(this.f$0, view);
                }
            });
        }
        getThemeDetail(getThemeId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3$lambda-0, reason: not valid java name */
    public static final void m313initView$lambda3$lambda0(ThemeDetailActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3$lambda-2$lambda-1, reason: not valid java name */
    public static final void m314initView$lambda3$lambda2$lambda1(ThemeDetailActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getThemeDetail(this$0.getThemeId());
    }

    private final void getThemeDetail(String themeId) {
        getThemesViewModel().requestThemeDetail(themeId, new Function1<ThemeBean, Unit>() { // from class: com.shangxian.pinkink.ui.themes.ThemeDetailActivity.getThemeDetail.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ThemeBean themeBean) {
                invoke2(themeBean);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ThemeBean it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ThemeDetailActivity.this.bindThemeDetailToView(it);
                ThemeDetailActivity.this.getLikeStatus(it.getId());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void bindThemeDetailToView(final ThemeBean theme) {
        final ActivityThemeDetailBinding activityThemeDetailBinding = (ActivityThemeDetailBinding) getMViewBinding();
        activityThemeDetailBinding.titleBar.tvTitle.setText(theme.getName());
        activityThemeDetailBinding.ivImage.post(new Runnable() { // from class: com.shangxian.pinkink.ui.themes.ThemeDetailActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ThemeDetailActivity.m308bindThemeDetailToView$lambda8$lambda4(activityThemeDetailBinding, this, theme);
            }
        });
        activityThemeDetailBinding.llEdit.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.ThemeDetailActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThemeDetailActivity.m309bindThemeDetailToView$lambda8$lambda5(this.f$0, theme, view);
            }
        });
        activityThemeDetailBinding.llInstall.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.ThemeDetailActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThemeDetailActivity.m310bindThemeDetailToView$lambda8$lambda6(theme, this, view);
            }
        });
        activityThemeDetailBinding.llCollect.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.ThemeDetailActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThemeDetailActivity.m311bindThemeDetailToView$lambda8$lambda7(this.f$0, theme, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: bindThemeDetailToView$lambda-8$lambda-4, reason: not valid java name */
    public static final void m308bindThemeDetailToView$lambda8$lambda4(ActivityThemeDetailBinding this_run, ThemeDetailActivity this$0, ThemeBean theme) {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(theme, "$theme");
        double measuredHeight = ((double) this_run.llContent.getMeasuredHeight()) * 0.8d;
        InkScreenSize currentInkScreenSize = AppConfig.INSTANCE.getCurrentInkScreenSize();
        this_run.ivImage.setLayoutParams(new LinearLayout.LayoutParams((int) (((double) (currentInkScreenSize.getWidth() / currentInkScreenSize.getHeight())) * measuredHeight), (int) measuredHeight));
        Glide.with((FragmentActivity) this$0).load(theme.getCover()).fitCenter().into(this_run.ivImage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: bindThemeDetailToView$lambda-8$lambda-5, reason: not valid java name */
    public static final void m309bindThemeDetailToView$lambda8$lambda5(ThemeDetailActivity this$0, ThemeBean theme, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(theme, "$theme");
        ThemesEditActivity.Companion companion = ThemesEditActivity.INSTANCE;
        Activity activity = this$0.getActivity();
        String cover = theme.getCover();
        if (cover == null) {
            cover = "";
        }
        companion.gotoThis(activity, new UserCreateThemeBean(0L, null, 0L, null, cover, 15, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: bindThemeDetailToView$lambda-8$lambda-6, reason: not valid java name */
    public static final void m310bindThemeDetailToView$lambda8$lambda6(ThemeBean theme, ThemeDetailActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(theme, "$theme");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String cover = theme.getCover();
        Intrinsics.checkNotNull(cover);
        TransThemesActivity.INSTANCE.gotoThis(this$0.getActivity(), new UserCreateThemeBean(0L, null, 0L, cover, cover, 7, null), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: bindThemeDetailToView$lambda-8$lambda-7, reason: not valid java name */
    public static final void m311bindThemeDetailToView$lambda8$lambda7(ThemeDetailActivity this$0, ThemeBean theme, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(theme, "$theme");
        if (this$0.isLike) {
            this$0.getThemesViewModel().unLikeTheme(theme.getId());
        } else {
            this$0.getThemesViewModel().likeTheme(theme);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getLikeStatus(String themeId) {
        ThemesViewModel themesViewModel = getThemesViewModel();
        themesViewModel.getLikeStatus(themeId);
        themesViewModel.isLike().observe(this, new Observer() { // from class: com.shangxian.pinkink.ui.themes.ThemeDetailActivity$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ThemeDetailActivity.m312getLikeStatus$lambda10$lambda9(this.f$0, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: getLikeStatus$lambda-10$lambda-9, reason: not valid java name */
    public static final void m312getLikeStatus$lambda10$lambda9(ThemeDetailActivity this$0, Boolean isLike) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullExpressionValue(isLike, "isLike");
        this$0.isLike = isLike.booleanValue();
        ((ActivityThemeDetailBinding) this$0.getMViewBinding()).ivCollect.setImageResource(isLike.booleanValue() ? R.drawable.ic_themes_collected : R.drawable.ic_themes_collect);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.BaseActivity, com.lazyee.klib.mvvm.MVVMBaseView
    public void onPageLoadingStateChanged(LoadingState state) {
        Intrinsics.checkNotNullParameter(state, "state");
        super.onPageLoadingStateChanged(state);
        int i = WhenMappings.$EnumSwitchMapping$0[state.ordinal()];
        if (i == 1) {
            ((ActivityThemeDetailBinding) getMViewBinding()).pageStateSwitcher.showLoadingView();
        } else if (i == 2) {
            ((ActivityThemeDetailBinding) getMViewBinding()).pageStateSwitcher.showContentView();
        } else {
            if (i != 3) {
                return;
            }
            ((ActivityThemeDetailBinding) getMViewBinding()).pageStateSwitcher.showNetworkErrorView();
        }
    }
}
