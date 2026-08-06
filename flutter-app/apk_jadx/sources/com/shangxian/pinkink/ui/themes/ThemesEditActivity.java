package com.shangxian.pinkink.ui.themes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.lazyee.klib.extension.BitmapExtensionsKt;
import com.lazyee.klib.extension.ContextExtensionsKt;
import com.lazyee.klib.extension.NumberExtensionsKt;
import com.lazyee.klib.extension.ViewExtensionsKt;
import com.lazyee.klib.mvvm.ViewModel;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.app.GlideEngine;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.bean.FontBean;
import com.shangxian.pinkink.bean.ThemeMaterialBean;
import com.shangxian.pinkink.bean.UserCreateThemeBean;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.constants.InkScreenSize;
import com.shangxian.pinkink.constants.Keys;
import com.shangxian.pinkink.databinding.ActivityThemesEditBinding;
import com.shangxian.pinkink.mvvm.viewmodel.FontViewModel;
import com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel;
import com.shangxian.pinkink.ui.dialog.TextOptionsDialog;
import com.shangxian.pinkink.widget.GestureScaleRotateView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ThemesEditActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 92\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u00019B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010&\u001a\u00020'H\u0002J\b\u0010(\u001a\u00020'H\u0002J\u001c\u0010)\u001a\u00020'2\u0006\u0010*\u001a\u00020\u00162\n\b\u0002\u0010+\u001a\u0004\u0018\u00010,H\u0002J\u0014\u0010-\u001a\u00020'2\n\b\u0002\u0010+\u001a\u0004\u0018\u00010,H\u0002J\u0014\u0010.\u001a\u00020'2\n\u0010/\u001a\u0006\u0012\u0002\b\u000300H\u0002J\b\u00101\u001a\u00020'H\u0002J\b\u00102\u001a\u00020'H\u0016J\u0016\u00103\u001a\u00020'2\f\u00104\u001a\b\u0012\u0004\u0012\u00020,0$H\u0002J\u0014\u00105\u001a\u00020'2\n\u0010/\u001a\u0006\u0012\u0002\b\u000300H\u0016J\u0014\u00106\u001a\u00020'2\n\u0010/\u001a\u0006\u0012\u0002\b\u000300H\u0016J\b\u00107\u001a\u00020'H\u0014J\u001c\u00108\u001a\u00020'2\u0006\u0010/\u001a\u00020\u00152\n\b\u0002\u0010+\u001a\u0004\u0018\u00010,H\u0002R\u001c\u0010\u0005\u001a\u0010\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00070\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0010\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00070\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R*\u0010\u0013\u001a\u001e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014j\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0016`\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001c\u001a\u00020\u001d8CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b \u0010\u0012\u001a\u0004\b\u001e\u0010\u001fR\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lcom/shangxian/pinkink/ui/themes/ThemesEditActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityThemesEditBinding;", "Lcom/shangxian/pinkink/widget/GestureScaleRotateView$OnOperateListener;", "()V", "addBackgroundActivityResult", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "addImageActivityResult", "dp13", "", "dp6", "fontViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/FontViewModel;", "getFontViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/FontViewModel;", "fontViewModel$delegate", "Lkotlin/Lazy;", "imageFilePathHasMap", "Ljava/util/HashMap;", "Landroid/view/View;", "", "Lkotlin/collections/HashMap;", "isLayout", "", "onGlobalLayoutListener", "Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;", "themesViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "getThemesViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "themesViewModel$delegate", "userCreateTheme", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "userFontList", "", "Lcom/shangxian/pinkink/bean/FontBean;", "addBackground", "", "addImage", "addImageToView", "imageFile", "material", "Lcom/shangxian/pinkink/bean/ThemeMaterialBean;", "addTextToView", "addView2Canvas", "view", "Lcom/shangxian/pinkink/widget/GestureScaleRotateView;", "composeImage", "initView", "layoutRestoration", "themeMaterialList", "onContentClick", "onDeleteClick", "onDestroy", "setCenterMargin", "Companion", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ThemesEditActivity extends BaseActivity<ActivityThemesEditBinding> implements GestureScaleRotateView.OnOperateListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final List<GestureScaleRotateView<?>> gestureScaleRotateViewList = new ArrayList();
    private final ActivityResultLauncher<Intent> addBackgroundActivityResult;
    private final ActivityResultLauncher<Intent> addImageActivityResult;
    private boolean isLayout;
    private UserCreateThemeBean userCreateTheme;

    /* JADX INFO: renamed from: fontViewModel$delegate, reason: from kotlin metadata */
    private final Lazy fontViewModel = LazyKt.lazy(new Function0<FontViewModel>() { // from class: com.shangxian.pinkink.ui.themes.ThemesEditActivity$fontViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final FontViewModel invoke() {
            return (FontViewModel) new ViewModelProvider(this.this$0).get(FontViewModel.class);
        }
    });

    /* JADX INFO: renamed from: themesViewModel$delegate, reason: from kotlin metadata */
    private final Lazy themesViewModel = LazyKt.lazy(new Function0<ThemesViewModel>() { // from class: com.shangxian.pinkink.ui.themes.ThemesEditActivity$themesViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ThemesViewModel invoke() {
            return (ThemesViewModel) new ViewModelProvider(this.this$0).get(ThemesViewModel.class);
        }
    });
    private final int dp13 = NumberExtensionsKt.dp2px(13);
    private final int dp6 = NumberExtensionsKt.dp2px(6);
    private final HashMap<View, String> imageFilePathHasMap = new HashMap<>();
    private final List<FontBean> userFontList = new ArrayList();
    private final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.shangxian.pinkink.ui.themes.ThemesEditActivity$$ExternalSyntheticLambda6
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() {
            ThemesEditActivity.m328onGlobalLayoutListener$lambda10(this.f$0);
        }
    };

    public ThemesEditActivity() {
        ActivityResultLauncher<Intent> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.shangxian.pinkink.ui.themes.ThemesEditActivity$$ExternalSyntheticLambda8
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                ThemesEditActivity.m318addBackgroundActivityResult$lambda17(this.f$0, (ActivityResult) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResul…nding.ivBackground)\n    }");
        this.addBackgroundActivityResult = activityResultLauncherRegisterForActivityResult;
        ActivityResultLauncher<Intent> activityResultLauncherRegisterForActivityResult2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.shangxian.pinkink.ui.themes.ThemesEditActivity$$ExternalSyntheticLambda7
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                ThemesEditActivity.m319addImageActivityResult$lambda18(this.f$0, (ActivityResult) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult2, "registerForActivityResul…ageToView(filePath)\n    }");
        this.addImageActivityResult = activityResultLauncherRegisterForActivityResult2;
    }

    /* JADX INFO: compiled from: ThemesEditActivity.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rR\u001b\u0010\u0003\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000e"}, d2 = {"Lcom/shangxian/pinkink/ui/themes/ThemesEditActivity$Companion;", "", "()V", "gestureScaleRotateViewList", "", "Lcom/shangxian/pinkink/widget/GestureScaleRotateView;", "getGestureScaleRotateViewList", "()Ljava/util/List;", "gotoThis", "", "context", "Landroid/content/Context;", "theme", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void gotoThis(Context context, UserCreateThemeBean theme) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intent intent = new Intent(context, (Class<?>) ThemesEditActivity.class);
            intent.putExtra(Keys.THEME_DATA, theme);
            context.startActivity(intent);
        }

        public final List<GestureScaleRotateView<?>> getGestureScaleRotateViewList() {
            return ThemesEditActivity.gestureScaleRotateViewList;
        }
    }

    @ViewModel
    private final FontViewModel getFontViewModel() {
        return (FontViewModel) this.fontViewModel.getValue();
    }

    @ViewModel
    private final ThemesViewModel getThemesViewModel() {
        return (ThemesViewModel) this.themesViewModel.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        UserCreateThemeBean userCreateThemeBean = (UserCreateThemeBean) getIntent().getParcelableExtra(Keys.THEME_DATA);
        this.userCreateTheme = userCreateThemeBean;
        if (userCreateThemeBean == null) {
            this.userCreateTheme = new UserCreateThemeBean(0L, null, 0L, null, null, 31, null);
        }
        final ActivityThemesEditBinding activityThemesEditBinding = (ActivityThemesEditBinding) getMViewBinding();
        activityThemesEditBinding.titleBar.tvTitle.setText(getString(getTheme() == null ? R.string.string_create_new_theme : R.string.string_click_input_text));
        activityThemesEditBinding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.ThemesEditActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThemesEditActivity.m320initView$lambda6$lambda0(this.f$0, view);
            }
        });
        activityThemesEditBinding.flCanvas.post(new Runnable() { // from class: com.shangxian.pinkink.ui.themes.ThemesEditActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ThemesEditActivity.m321initView$lambda6$lambda1(activityThemesEditBinding, this);
            }
        });
        activityThemesEditBinding.ivNext.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.ThemesEditActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws IOException {
                ThemesEditActivity.m322initView$lambda6$lambda2(this.f$0, view);
            }
        });
        activityThemesEditBinding.llAddText.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.ThemesEditActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThemesEditActivity.m323initView$lambda6$lambda3(this.f$0, view);
            }
        });
        activityThemesEditBinding.llAddImage.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.ThemesEditActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThemesEditActivity.m324initView$lambda6$lambda4(this.f$0, view);
            }
        });
        activityThemesEditBinding.llAddBackground.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.ThemesEditActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ThemesEditActivity.m325initView$lambda6$lambda5(this.f$0, view);
            }
        });
        ThemesEditActivity themesEditActivity = this;
        getFontViewModel().getUserFontListLiveData().observe(themesEditActivity, new Observer() { // from class: com.shangxian.pinkink.ui.themes.ThemesEditActivity$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ThemesEditActivity.m326initView$lambda7(this.f$0, (List) obj);
            }
        });
        getThemesViewModel().getThemeMaterialList().observe(themesEditActivity, new Observer() { // from class: com.shangxian.pinkink.ui.themes.ThemesEditActivity$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ThemesEditActivity.m327initView$lambda8(this.f$0, (List) obj);
            }
        });
        getFontViewModel().getUserFontList();
        ((ActivityThemesEditBinding) getMViewBinding()).flCanvas.getViewTreeObserver().addOnGlobalLayoutListener(this.onGlobalLayoutListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-6$lambda-0, reason: not valid java name */
    public static final void m320initView$lambda6$lambda0(ThemesEditActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-6$lambda-1, reason: not valid java name */
    public static final void m321initView$lambda6$lambda1(ActivityThemesEditBinding this_run, ThemesEditActivity this$0) {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        double measuredHeight = ((double) this_run.llContent.getMeasuredHeight()) * 0.8d;
        InkScreenSize currentInkScreenSize = AppConfig.INSTANCE.getCurrentInkScreenSize();
        this_run.flCanvas.setLayoutParams(new LinearLayout.LayoutParams((int) (((double) (currentInkScreenSize.getWidth() / currentInkScreenSize.getHeight())) * measuredHeight), (int) measuredHeight));
        RequestManager requestManagerWith = Glide.with((FragmentActivity) this$0);
        UserCreateThemeBean userCreateThemeBean = this$0.userCreateTheme;
        requestManagerWith.load(userCreateThemeBean == null ? null : userCreateThemeBean.getBackgroundImagePath()).fitCenter().into(this_run.ivBackground);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-6$lambda-2, reason: not valid java name */
    public static final void m322initView$lambda6$lambda2(ThemesEditActivity this$0, View view) throws IOException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.composeImage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-6$lambda-3, reason: not valid java name */
    public static final void m323initView$lambda6$lambda3(ThemesEditActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        addTextToView$default(this$0, null, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-6$lambda-4, reason: not valid java name */
    public static final void m324initView$lambda6$lambda4(ThemesEditActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.addImage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-6$lambda-5, reason: not valid java name */
    public static final void m325initView$lambda6$lambda5(ThemesEditActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.addBackground();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-7, reason: not valid java name */
    public static final void m326initView$lambda7(ThemesEditActivity this$0, List it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.userFontList.clear();
        List<FontBean> list = this$0.userFontList;
        Intrinsics.checkNotNullExpressionValue(it, "it");
        list.addAll(it);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-8, reason: not valid java name */
    public static final void m327initView$lambda8(ThemesEditActivity this$0, List it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullExpressionValue(it, "it");
        this$0.layoutRestoration(it);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: onGlobalLayoutListener$lambda-10, reason: not valid java name */
    public static final void m328onGlobalLayoutListener$lambda10(ThemesEditActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isLayout || ((ActivityThemesEditBinding) this$0.getMViewBinding()).flCanvas.getMeasuredWidth() == 0 || ((ActivityThemesEditBinding) this$0.getMViewBinding()).flCanvas.getMeasuredHeight() == 0) {
            return;
        }
        UserCreateThemeBean userCreateThemeBean = this$0.userCreateTheme;
        if (userCreateThemeBean != null) {
            this$0.getThemesViewModel().getCreateMaterialList(userCreateThemeBean.getColumnId());
        }
        this$0.isLayout = true;
    }

    private final void layoutRestoration(List<ThemeMaterialBean> themeMaterialList) {
        for (ThemeMaterialBean themeMaterialBean : themeMaterialList) {
            if (themeMaterialBean.getType() == 1) {
                addTextToView(themeMaterialBean);
            } else {
                addImageToView(themeMaterialBean.getContent(), themeMaterialBean);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void composeImage() throws IOException {
        Iterator<T> it = gestureScaleRotateViewList.iterator();
        while (it.hasNext()) {
            ((GestureScaleRotateView) it.next()).hideOperateView();
        }
        FrameLayout frameLayout = ((ActivityThemesEditBinding) getMViewBinding()).flCanvas;
        int width = AppConfig.INSTANCE.getCurrentInkScreenSize().getWidth();
        int height = AppConfig.INSTANCE.getCurrentInkScreenSize().getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.scale(width / frameLayout.getMeasuredWidth(), height / frameLayout.getMeasuredHeight());
        frameLayout.draw(canvas);
        File file = new File(AppConfig.INSTANCE.getImageSavePath() + System.currentTimeMillis() + PictureMimeType.PNG);
        Intrinsics.checkNotNullExpressionValue(bitmap, "bitmap");
        BitmapExtensionsKt.savePNGFile$default(bitmap, file, 0, 2, null);
        UserCreateThemeBean userCreateThemeBean = this.userCreateTheme;
        if (userCreateThemeBean != null) {
            String absolutePath = file.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath, "outFile.absolutePath");
            userCreateThemeBean.setComposeImagePath(absolutePath);
        }
        UserCreateThemeBean userCreateThemeBean2 = this.userCreateTheme;
        Intrinsics.checkNotNull(userCreateThemeBean2);
        TransThemesActivity.INSTANCE.gotoThis(this, userCreateThemeBean2, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void addView2Canvas(GestureScaleRotateView<?> view) {
        view.setOnOperateListener(this);
        Iterator<T> it = gestureScaleRotateViewList.iterator();
        while (it.hasNext()) {
            ((GestureScaleRotateView) it.next()).hideOperateView();
        }
        gestureScaleRotateViewList.add(view);
        ((ActivityThemesEditBinding) getMViewBinding()).flCanvas.addView(view);
    }

    static /* synthetic */ void addTextToView$default(ThemesEditActivity themesEditActivity, ThemeMaterialBean themeMaterialBean, int i, Object obj) {
        if ((i & 1) != 0) {
            themeMaterialBean = null;
        }
        themesEditActivity.addTextToView(themeMaterialBean);
    }

    private final void addTextToView(ThemeMaterialBean material) {
        ThemeMaterialBean themeMaterialBean;
        if (material == null) {
            ThemeMaterialBean themeMaterialBean2 = new ThemeMaterialBean(0L, 0, null, 0.0f, 0, 0, 0.0f, 0.0f, 0L, 0.0f, null, null, 4095, null);
            String string = getString(R.string.string_click_input_text);
            Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.string_click_input_text)");
            themeMaterialBean2.setContent(string);
            themeMaterialBean2.setType(1);
            themeMaterialBean2.setAngle(0.0f);
            themeMaterialBean2.setTextColor("#000000");
            themeMaterialBean2.setTextSize(14.0f);
            themeMaterialBean = themeMaterialBean2;
        } else {
            themeMaterialBean = material;
        }
        GestureScaleRotateView<?> gestureScaleRotateView = new GestureScaleRotateView<>(getActivity());
        TextView textView = new TextView(getActivity());
        textView.setTextSize(2, themeMaterialBean.getTextSize());
        int i = this.dp13;
        int i2 = this.dp6;
        textView.setPadding(i, i2, i, i2);
        textView.setText(themeMaterialBean.getContent());
        if (!TextUtils.isEmpty(themeMaterialBean.getFontFamilyFileName())) {
            textView.setTypeface(Typeface.createFromFile(Intrinsics.stringPlus(AppConfig.INSTANCE.getFontSavePath(), themeMaterialBean.getFontFamilyFileName())));
        }
        textView.setTextColor(Color.parseColor(themeMaterialBean.getTextColor()));
        gestureScaleRotateView.addContentView(textView);
        gestureScaleRotateView.setRotation(themeMaterialBean.getAngle());
        gestureScaleRotateView.setRotateEnable(false);
        gestureScaleRotateView.setScaleEnable(false);
        float[] fArrMeasureText$default = ContextExtensionsKt.measureText$default(this, textView.getText().toString(), themeMaterialBean.getTextSize(), null, 4, null);
        gestureScaleRotateView.updateContentSize(fArrMeasureText$default[0] + (this.dp13 * 2), fArrMeasureText$default[1] + (this.dp6 * 2));
        gestureScaleRotateView.setTag(themeMaterialBean);
        setCenterMargin(gestureScaleRotateView, material);
        addView2Canvas(gestureScaleRotateView);
    }

    private final void addImage() {
        PictureSelector.create((AppCompatActivity) this).openGallery(SelectMimeType.ofImage()).setSelectionMode(1).isDisplayCamera(false).setImageEngine(GlideEngine.createGlideEngine()).forResult(new OnResultCallbackListener<LocalMedia>() { // from class: com.shangxian.pinkink.ui.themes.ThemesEditActivity.addImage.1
            @Override // com.luck.picture.lib.interfaces.OnResultCallbackListener
            public void onCancel() {
            }

            @Override // com.luck.picture.lib.interfaces.OnResultCallbackListener
            public void onResult(ArrayList<LocalMedia> result) {
                Intrinsics.checkNotNullParameter(result, "result");
                LocalMedia localMedia = (LocalMedia) CollectionsKt.first((List) result);
                if (localMedia == null) {
                    return;
                }
                Intent intent = new Intent(ThemesEditActivity.this, (Class<?>) CropImageActivity.class);
                intent.putExtra(Keys.LOCAL_MEDIA, localMedia);
                ThemesEditActivity.this.addImageActivityResult.launch(intent);
            }
        });
    }

    static /* synthetic */ void addImageToView$default(ThemesEditActivity themesEditActivity, String str, ThemeMaterialBean themeMaterialBean, int i, Object obj) {
        if ((i & 2) != 0) {
            themeMaterialBean = null;
        }
        themesEditActivity.addImageToView(str, themeMaterialBean);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x009b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void addImageToView(java.lang.String r22, com.shangxian.pinkink.bean.ThemeMaterialBean r23) {
        /*
            Method dump skipped, instruction units count: 247
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shangxian.pinkink.ui.themes.ThemesEditActivity.addImageToView(java.lang.String, com.shangxian.pinkink.bean.ThemeMaterialBean):void");
    }

    private final void addBackground() {
        PictureSelector.create((AppCompatActivity) this).openGallery(SelectMimeType.ofImage()).setSelectionMode(1).isDisplayCamera(false).setImageEngine(GlideEngine.createGlideEngine()).forResult(new OnResultCallbackListener<LocalMedia>() { // from class: com.shangxian.pinkink.ui.themes.ThemesEditActivity.addBackground.1
            @Override // com.luck.picture.lib.interfaces.OnResultCallbackListener
            public void onCancel() {
            }

            @Override // com.luck.picture.lib.interfaces.OnResultCallbackListener
            public void onResult(ArrayList<LocalMedia> result) {
                Intrinsics.checkNotNullParameter(result, "result");
                LocalMedia localMedia = (LocalMedia) CollectionsKt.first((List) result);
                if (localMedia == null) {
                    return;
                }
                Intent intent = new Intent(ThemesEditActivity.this, (Class<?>) CropImageActivity.class);
                intent.putExtra(Keys.LOCAL_MEDIA, localMedia);
                intent.putExtra(Keys.FLAG, true);
                ThemesEditActivity.this.addBackgroundActivityResult.launch(intent);
            }
        });
    }

    static /* synthetic */ void setCenterMargin$default(ThemesEditActivity themesEditActivity, View view, ThemeMaterialBean themeMaterialBean, int i, Object obj) {
        if ((i & 2) != 0) {
            themeMaterialBean = null;
        }
        themesEditActivity.setCenterMargin(view, themeMaterialBean);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void setCenterMargin(View view, ThemeMaterialBean material) {
        int x;
        int y;
        int measuredWidth = ((ActivityThemesEditBinding) getMViewBinding()).flCanvas.getMeasuredWidth();
        int measuredHeight = ((ActivityThemesEditBinding) getMViewBinding()).flCanvas.getMeasuredHeight();
        int i = view.getLayoutParams().width;
        int i2 = view.getLayoutParams().height;
        if (material == null) {
            x = (measuredWidth - i) / 2;
            y = (measuredHeight - i2) / 2;
        } else {
            x = material.getX();
            y = material.getY();
        }
        ViewExtensionsKt.setMargins(view, Integer.valueOf(x), Integer.valueOf(y), 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: addBackgroundActivityResult$lambda-17, reason: not valid java name */
    public static final void m318addBackgroundActivityResult$lambda17(ThemesEditActivity this$0, ActivityResult activityResult) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (activityResult.getResultCode() == -1 && activityResult.getData() != null) {
            Intent data = activityResult.getData();
            Intrinsics.checkNotNull(data);
            String stringExtra = data.getStringExtra(Keys.FILE_PATH);
            UserCreateThemeBean userCreateThemeBean = this$0.userCreateTheme;
            if (userCreateThemeBean != null) {
                userCreateThemeBean.setBackgroundImagePath(stringExtra == null ? "" : stringExtra);
            }
            Glide.with((FragmentActivity) this$0).load(stringExtra).into(((ActivityThemesEditBinding) this$0.getMViewBinding()).ivBackground);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: addImageActivityResult$lambda-18, reason: not valid java name */
    public static final void m319addImageActivityResult$lambda18(ThemesEditActivity this$0, ActivityResult activityResult) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (activityResult.getResultCode() == -1 && activityResult.getData() != null) {
            Intent data = activityResult.getData();
            Intrinsics.checkNotNull(data);
            String stringExtra = data.getStringExtra(Keys.FILE_PATH);
            if (stringExtra == null) {
                return;
            }
            addImageToView$default(this$0, stringExtra, null, 2, null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.shangxian.pinkink.widget.GestureScaleRotateView.OnOperateListener
    public void onDeleteClick(GestureScaleRotateView<?> view) {
        Intrinsics.checkNotNullParameter(view, "view");
        gestureScaleRotateViewList.remove(view);
        ((ActivityThemesEditBinding) getMViewBinding()).flCanvas.removeView(view);
        this.imageFilePathHasMap.remove(view);
    }

    @Override // com.shangxian.pinkink.widget.GestureScaleRotateView.OnOperateListener
    public void onContentClick(GestureScaleRotateView<?> view) {
        Intrinsics.checkNotNullParameter(view, "view");
        Iterator<T> it = gestureScaleRotateViewList.iterator();
        while (it.hasNext()) {
            ((GestureScaleRotateView) it.next()).hideOperateView();
        }
        view.showOperateView();
        if (view.getContentView() instanceof TextView) {
            new TextOptionsDialog(this, this.userFontList, view).show();
        }
    }

    @Override // com.lazyee.klib.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        gestureScaleRotateViewList.clear();
    }
}
