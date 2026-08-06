package com.shangxian.pinkink.ui.themes;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lazyee.klib.extension.AnyExtensionsKt;
import com.lazyee.klib.extension.NumberExtensionsKt;
import com.lazyee.klib.extension.ViewExtensionsKt;
import com.lazyee.klib.mvvm.ViewModel;
import com.lazyee.klib.recyclerview.decoration.GridSpacingItemDecoration;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.bean.UserCreateThemeBean;
import com.shangxian.pinkink.constants.Keys;
import com.shangxian.pinkink.databinding.ActivityUserThemesBinding;
import com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel;
import com.shangxian.pinkink.ui.themes.UserThemesActivity;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: UserThemesActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u00152\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u0015\u0016B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0013\u001a\u00020\u0014H\u0017R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0004\u0010\u0006R\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u00060\rR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u000e\u001a\u00020\u000f8CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\b\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0017"}, d2 = {"Lcom/shangxian/pinkink/ui/themes/UserThemesActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityUserThemesBinding;", "()V", "isShowCreateItem", "", "()Z", "isShowCreateItem$delegate", "Lkotlin/Lazy;", "themeList", "", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "themesAdapter", "Lcom/shangxian/pinkink/ui/themes/UserThemesActivity$ThemesAdapter;", "themesViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "getThemesViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "themesViewModel$delegate", "initView", "", "Companion", "ThemesAdapter", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class UserThemesActivity extends BaseActivity<ActivityUserThemesBinding> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: renamed from: isShowCreateItem$delegate, reason: from kotlin metadata */
    private final Lazy isShowCreateItem;
    private final List<UserCreateThemeBean> themeList;
    private final ThemesAdapter themesAdapter;

    /* JADX INFO: renamed from: themesViewModel$delegate, reason: from kotlin metadata */
    private final Lazy themesViewModel = LazyKt.lazy(new Function0<ThemesViewModel>() { // from class: com.shangxian.pinkink.ui.themes.UserThemesActivity$themesViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ThemesViewModel invoke() {
            return (ThemesViewModel) new ViewModelProvider(this.this$0).get(ThemesViewModel.class);
        }
    });

    public UserThemesActivity() {
        ArrayList arrayList = new ArrayList();
        this.themeList = arrayList;
        this.themesAdapter = new ThemesAdapter(this, arrayList);
        this.isShowCreateItem = LazyKt.lazy(new Function0<Boolean>() { // from class: com.shangxian.pinkink.ui.themes.UserThemesActivity.isShowCreateItem.2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                return Boolean.valueOf(UserThemesActivity.this.getIntent().getBooleanExtra(Keys.FLAG, true));
            }
        });
    }

    /* JADX INFO: compiled from: UserThemesActivity.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lcom/shangxian/pinkink/ui/themes/UserThemesActivity$Companion;", "", "()V", "gotoThis", "", "context", "Landroid/content/Context;", "isShowCreateItem", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void gotoThis$default(Companion companion, Context context, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = true;
            }
            companion.gotoThis(context, z);
        }

        public final void gotoThis(Context context, boolean isShowCreateItem) {
            if (context == null) {
                return;
            }
            Intent intent = new Intent(context, (Class<?>) UserThemesActivity.class);
            intent.putExtra(Keys.FLAG, isShowCreateItem);
            context.startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @ViewModel
    public final ThemesViewModel getThemesViewModel() {
        return (ThemesViewModel) this.themesViewModel.getValue();
    }

    private final boolean isShowCreateItem() {
        return ((Boolean) this.isShowCreateItem.getValue()).booleanValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        final ActivityUserThemesBinding activityUserThemesBinding = (ActivityUserThemesBinding) getMViewBinding();
        activityUserThemesBinding.titleBar.tvTitle.setText(getString(R.string.string_my_theme));
        activityUserThemesBinding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.UserThemesActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UserThemesActivity.m365initView$lambda2$lambda0(this.f$0, view);
            }
        });
        if (isShowCreateItem()) {
            this.themeList.add(null);
        }
        activityUserThemesBinding.rvThemes.addItemDecoration(new GridSpacingItemDecoration(NumberExtensionsKt.dp2px(11.0f)));
        activityUserThemesBinding.rvThemes.setAdapter(this.themesAdapter);
        activityUserThemesBinding.ivEdit.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.UserThemesActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UserThemesActivity.m366initView$lambda2$lambda1(this.f$0, activityUserThemesBinding, view);
            }
        });
        getThemesViewModel().getUserCreateThemeList().observe(this, new Observer() { // from class: com.shangxian.pinkink.ui.themes.UserThemesActivity$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                UserThemesActivity.m367initView$lambda3(this.f$0, (List) obj);
            }
        });
        getThemesViewModel().m119getUserCreateThemeList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2$lambda-0, reason: not valid java name */
    public static final void m365initView$lambda2$lambda0(UserThemesActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2$lambda-1, reason: not valid java name */
    public static final void m366initView$lambda2$lambda1(UserThemesActivity this$0, ActivityUserThemesBinding this_run, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        this$0.themesAdapter.setEditMode(!r3.getIsEditMode());
        this_run.ivEdit.setImageResource(this$0.themesAdapter.getIsEditMode() ? R.drawable.ic_my_themes_edit_complete : R.drawable.ic_my_themes_start_edit);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3, reason: not valid java name */
    public static final void m367initView$lambda3(UserThemesActivity this$0, List it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        List<UserCreateThemeBean> list = this$0.themeList;
        Intrinsics.checkNotNullExpressionValue(it, "it");
        list.addAll(it);
        this$0.themesAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: UserThemesActivity.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0082\u0004\u0018\u00002\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004B\u0015\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006¢\u0006\u0002\u0010\u0007J\u001a\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00032\b\u0010\u0010\u001a\u0004\u0018\u00010\u0002H\u0015J\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\tH\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/shangxian/pinkink/ui/themes/UserThemesActivity$ThemesAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "Lcom/chad/library/adapter/base/module/LoadMoreModule;", "themesList", "", "(Lcom/shangxian/pinkink/ui/themes/UserThemesActivity;Ljava/util/List;)V", "isEditMode", "", "themesItemHeight", "", "themesItemWidth", "convert", "", "holder", "item", "setEditMode", "mode", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    final class ThemesAdapter extends BaseQuickAdapter<UserCreateThemeBean, BaseViewHolder> implements LoadMoreModule {
        private boolean isEditMode;
        private final int themesItemHeight;
        private final int themesItemWidth;
        private final List<UserCreateThemeBean> themesList;
        final /* synthetic */ UserThemesActivity this$0;

        @Override // com.chad.library.adapter.base.module.LoadMoreModule
        public /* synthetic */ BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapter baseQuickAdapter) {
            return LoadMoreModule.CC.$default$addLoadMoreModule(this, baseQuickAdapter);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ThemesAdapter(UserThemesActivity this$0, List<UserCreateThemeBean> themesList) {
            super(R.layout.item_themes, themesList);
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(themesList, "themesList");
            this.this$0 = this$0;
            this.themesList = themesList;
            int screenWidth = (AnyExtensionsKt.getScreenWidth(this) - NumberExtensionsKt.dp2px(41)) / 2;
            this.themesItemWidth = screenWidth;
            this.themesItemHeight = (int) (((double) screenWidth) * 1.733d);
        }

        public final void setEditMode(boolean mode) {
            this.isEditMode = mode;
            notifyDataSetChanged();
        }

        /* JADX INFO: renamed from: isEditMode, reason: from getter */
        public final boolean getIsEditMode() {
            return this.isEditMode;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder holder, final UserCreateThemeBean item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.rlThemes);
            ImageView imageView = (ImageView) holder.getView(R.id.ivDelete);
            LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.llCreate);
            ImageView imageView2 = (ImageView) holder.getView(R.id.ivThemes);
            relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, this.themesItemHeight));
            if (item == null) {
                ViewExtensionsKt.visible(linearLayout);
                linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.UserThemesActivity$ThemesAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        UserThemesActivity.ThemesAdapter.m370convert$lambda0(this.f$0, view);
                    }
                });
                ViewExtensionsKt.gone(imageView2);
                ViewExtensionsKt.gone(imageView);
                return;
            }
            ViewExtensionsKt.visible(imageView2);
            ViewExtensionsKt.gone(linearLayout);
            imageView.setVisibility(this.isEditMode ? 0 : 8);
            final UserThemesActivity userThemesActivity = this.this$0;
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.UserThemesActivity$ThemesAdapter$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UserThemesActivity.ThemesAdapter.m371convert$lambda1(this.f$0, item, userThemesActivity, view);
                }
            });
            Glide.with(getContext()).load(item.getComposeImagePath()).into(imageView2);
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.UserThemesActivity$ThemesAdapter$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UserThemesActivity.ThemesAdapter.m372convert$lambda2(this.f$0, item, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: convert$lambda-0, reason: not valid java name */
        public static final void m370convert$lambda0(ThemesAdapter this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            ThemesEditActivity.INSTANCE.gotoThis(this$0.getContext(), null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: convert$lambda-1, reason: not valid java name */
        public static final void m371convert$lambda1(ThemesAdapter this$0, UserCreateThemeBean userCreateThemeBean, UserThemesActivity this$1, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this$1, "this$1");
            this$0.themesList.remove(userCreateThemeBean);
            this$1.getThemesViewModel().deleteUserCreateTheme(userCreateThemeBean.getColumnId());
            this$0.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: convert$lambda-2, reason: not valid java name */
        public static final void m372convert$lambda2(ThemesAdapter this$0, UserCreateThemeBean userCreateThemeBean, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            ThemesEditActivity.INSTANCE.gotoThis(this$0.getContext(), userCreateThemeBean);
        }
    }
}
