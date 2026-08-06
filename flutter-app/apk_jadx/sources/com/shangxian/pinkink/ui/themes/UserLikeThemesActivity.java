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
import com.shangxian.pinkink.bean.PageDataBean;
import com.shangxian.pinkink.bean.ThemeBean;
import com.shangxian.pinkink.databinding.ActivityUserThemesBinding;
import com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel;
import com.shangxian.pinkink.ui.themes.UserLikeThemesActivity;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: UserLikeThemesActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u00112\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u0011\u0012B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u000f\u001a\u00020\u0010H\u0017R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00060\bR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f¨\u0006\u0013"}, d2 = {"Lcom/shangxian/pinkink/ui/themes/UserLikeThemesActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityUserThemesBinding;", "()V", "themeList", "", "Lcom/shangxian/pinkink/bean/ThemeBean;", "themesAdapter", "Lcom/shangxian/pinkink/ui/themes/UserLikeThemesActivity$ThemesAdapter;", "themesViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "getThemesViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "themesViewModel$delegate", "Lkotlin/Lazy;", "initView", "", "Companion", "ThemesAdapter", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class UserLikeThemesActivity extends BaseActivity<ActivityUserThemesBinding> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<ThemeBean> themeList;
    private final ThemesAdapter themesAdapter;

    /* JADX INFO: renamed from: themesViewModel$delegate, reason: from kotlin metadata */
    private final Lazy themesViewModel = LazyKt.lazy(new Function0<ThemesViewModel>() { // from class: com.shangxian.pinkink.ui.themes.UserLikeThemesActivity$themesViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ThemesViewModel invoke() {
            return (ThemesViewModel) new ViewModelProvider(this.this$0).get(ThemesViewModel.class);
        }
    });

    public UserLikeThemesActivity() {
        ArrayList arrayList = new ArrayList();
        this.themeList = arrayList;
        this.themesAdapter = new ThemesAdapter(this, arrayList);
    }

    /* JADX INFO: compiled from: UserLikeThemesActivity.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¨\u0006\u0007"}, d2 = {"Lcom/shangxian/pinkink/ui/themes/UserLikeThemesActivity$Companion;", "", "()V", "gotoThis", "", "context", "Landroid/content/Context;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void gotoThis(Context context) {
            if (context == null) {
                return;
            }
            context.startActivity(new Intent(context, (Class<?>) UserLikeThemesActivity.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @ViewModel
    public final ThemesViewModel getThemesViewModel() {
        return (ThemesViewModel) this.themesViewModel.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        final ActivityUserThemesBinding activityUserThemesBinding = (ActivityUserThemesBinding) getMViewBinding();
        activityUserThemesBinding.titleBar.tvTitle.setText(getString(R.string.title_my_collection));
        activityUserThemesBinding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.UserLikeThemesActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UserLikeThemesActivity.m358initView$lambda2$lambda0(this.f$0, view);
            }
        });
        activityUserThemesBinding.rvThemes.addItemDecoration(new GridSpacingItemDecoration(NumberExtensionsKt.dp2px(11.0f)));
        activityUserThemesBinding.rvThemes.setAdapter(this.themesAdapter);
        activityUserThemesBinding.ivEdit.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.UserLikeThemesActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UserLikeThemesActivity.m359initView$lambda2$lambda1(this.f$0, activityUserThemesBinding, view);
            }
        });
        getThemesViewModel().getUserLikeThemeList();
        getThemesViewModel().getThemeList().observe(this, new Observer() { // from class: com.shangxian.pinkink.ui.themes.UserLikeThemesActivity$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                UserLikeThemesActivity.m360initView$lambda3(this.f$0, (PageDataBean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2$lambda-0, reason: not valid java name */
    public static final void m358initView$lambda2$lambda0(UserLikeThemesActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2$lambda-1, reason: not valid java name */
    public static final void m359initView$lambda2$lambda1(UserLikeThemesActivity this$0, ActivityUserThemesBinding this_run, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        this$0.themesAdapter.setEditMode(!r3.getIsEditMode());
        this_run.ivEdit.setImageResource(this$0.themesAdapter.getIsEditMode() ? R.drawable.ic_my_themes_edit_complete : R.drawable.ic_my_themes_start_edit);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3, reason: not valid java name */
    public static final void m360initView$lambda3(UserLikeThemesActivity this$0, PageDataBean pageDataBean) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (pageDataBean.getListData() == null) {
            return;
        }
        this$0.themeList.clear();
        this$0.themeList.addAll(pageDataBean.getListData());
        this$0.themesAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: UserLikeThemesActivity.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0082\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004B\u0013\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0002H\u0015J\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\tH\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/shangxian/pinkink/ui/themes/UserLikeThemesActivity$ThemesAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/shangxian/pinkink/bean/ThemeBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "Lcom/chad/library/adapter/base/module/LoadMoreModule;", "themesList", "", "(Lcom/shangxian/pinkink/ui/themes/UserLikeThemesActivity;Ljava/util/List;)V", "isEditMode", "", "themesItemHeight", "", "themesItemWidth", "convert", "", "holder", "item", "setEditMode", "mode", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    final class ThemesAdapter extends BaseQuickAdapter<ThemeBean, BaseViewHolder> implements LoadMoreModule {
        private boolean isEditMode;
        private final int themesItemHeight;
        private final int themesItemWidth;
        private final List<ThemeBean> themesList;
        final /* synthetic */ UserLikeThemesActivity this$0;

        @Override // com.chad.library.adapter.base.module.LoadMoreModule
        public /* synthetic */ BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapter baseQuickAdapter) {
            return LoadMoreModule.CC.$default$addLoadMoreModule(this, baseQuickAdapter);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ThemesAdapter(UserLikeThemesActivity this$0, List<ThemeBean> themesList) {
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
        public void convert(BaseViewHolder holder, final ThemeBean item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.rlThemes);
            ImageView imageView = (ImageView) holder.getView(R.id.ivDelete);
            LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.llCreate);
            ImageView imageView2 = (ImageView) holder.getView(R.id.ivThemes);
            relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, this.themesItemHeight));
            ViewExtensionsKt.visible(imageView2);
            ViewExtensionsKt.gone(linearLayout);
            imageView.setVisibility(this.isEditMode ? 0 : 8);
            final UserLikeThemesActivity userLikeThemesActivity = this.this$0;
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.UserLikeThemesActivity$ThemesAdapter$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UserLikeThemesActivity.ThemesAdapter.m362convert$lambda0(userLikeThemesActivity, item, this, view);
                }
            });
            Glide.with(getContext()).load(item.getCover()).into(imageView2);
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.UserLikeThemesActivity$ThemesAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UserLikeThemesActivity.ThemesAdapter.m363convert$lambda1(this.f$0, item, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: convert$lambda-0, reason: not valid java name */
        public static final void m362convert$lambda0(UserLikeThemesActivity this$0, ThemeBean item, ThemesAdapter this$1, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(item, "$item");
            Intrinsics.checkNotNullParameter(this$1, "this$1");
            this$0.getThemesViewModel().deleteLikeTheme(item);
            this$1.themesList.remove(item);
            this$1.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: convert$lambda-1, reason: not valid java name */
        public static final void m363convert$lambda1(ThemesAdapter this$0, ThemeBean item, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(item, "$item");
            ThemeDetailActivity.INSTANCE.gotoThis(this$0.getContext(), item.getId());
        }
    }
}
