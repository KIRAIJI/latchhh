package com.shangxian.pinkink.ui.themes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableKt;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lazyee.klib.extension.BitmapExtensionsKt;
import com.lazyee.klib.extension.ViewExtensionsKt;
import com.lazyee.klib.mvvm.ViewModel;
import com.lazyee.nfc.util.BmpUtils;
import com.luck.picture.lib.config.PictureMimeType;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BlueToothSyncImageActivity;
import com.shangxian.pinkink.bean.UserCreateThemeBean;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.constants.InkScreenSize;
import com.shangxian.pinkink.constants.Keys;
import com.shangxian.pinkink.databinding.ActivityTransThemesBinding;
import com.shangxian.pinkink.manager.PinkInkBlueToothManager;
import com.shangxian.pinkink.mvvm.viewmodel.ThemesViewModel;
import com.shangxian.pinkink.ui.nfc.PinkinkNfcActivity;
import java.io.File;
import java.io.IOException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: TransThemesActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\b\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0002\u0012\u0015\u0018\u0000 +2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001+B\u0005¢\u0006\u0002\u0010\u0003J\u0012\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0005H\u0002J\b\u0010%\u001a\u00020#H\u0016J\u0012\u0010&\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0005H\u0002J\u0012\u0010'\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0005H\u0002J\u0010\u0010(\u001a\u00020)2\u0006\u0010$\u001a\u00020\u0005H\u0002J\u0012\u0010*\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0005H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u0006\u001a\u0004\u0018\u00010\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u000e\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0013R\u0010\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0016R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0018\u001a\u00020\u00198CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u000b\u001a\u0004\b\u001a\u0010\u001bR\u001d\u0010\u001d\u001a\u0004\u0018\u00010\u001e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\u000b\u001a\u0004\b\u001f\u0010 ¨\u0006,"}, d2 = {"Lcom/shangxian/pinkink/ui/themes/TransThemesActivity;", "Lcom/shangxian/pinkink/base/BlueToothSyncImageActivity;", "Lcom/shangxian/pinkink/databinding/ActivityTransThemesBinding;", "()V", "effectBitmap", "Landroid/graphics/Bitmap;", "imageUrl", "", "getImageUrl", "()Ljava/lang/String;", "imageUrl$delegate", "Lkotlin/Lazy;", "isShowOriginBitmap", "", "isShowSaveBtn", "()Z", "isShowSaveBtn$delegate", "onBitmapRequestListener", "com/shangxian/pinkink/ui/themes/TransThemesActivity$onBitmapRequestListener$1", "Lcom/shangxian/pinkink/ui/themes/TransThemesActivity$onBitmapRequestListener$1;", "onDrawableRequestListener", "com/shangxian/pinkink/ui/themes/TransThemesActivity$onDrawableRequestListener$1", "Lcom/shangxian/pinkink/ui/themes/TransThemesActivity$onDrawableRequestListener$1;", "originBitmap", "themesViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "getThemesViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/ThemesViewModel;", "themesViewModel$delegate", "userCreateTheme", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "getUserCreateTheme", "()Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "userCreateTheme$delegate", "grayScaleEffect", "", "bitmap", "initView", "jitterEffect", "levelEffect", "saveBitmapToPNG", "Ljava/io/File;", "setImageByBitmap", "Companion", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class TransThemesActivity extends BlueToothSyncImageActivity<ActivityTransThemesBinding> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private Bitmap effectBitmap;
    private boolean isShowOriginBitmap;
    private Bitmap originBitmap;

    /* JADX INFO: renamed from: themesViewModel$delegate, reason: from kotlin metadata */
    private final Lazy themesViewModel = LazyKt.lazy(new Function0<ThemesViewModel>() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$themesViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ThemesViewModel invoke() {
            return (ThemesViewModel) new ViewModelProvider(this.this$0).get(ThemesViewModel.class);
        }
    });

    /* JADX INFO: renamed from: userCreateTheme$delegate, reason: from kotlin metadata */
    private final Lazy userCreateTheme = LazyKt.lazy(new Function0<UserCreateThemeBean>() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$userCreateTheme$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UserCreateThemeBean invoke() {
            return (UserCreateThemeBean) this.this$0.getIntent().getParcelableExtra(Keys.THEME_DATA);
        }
    });

    /* JADX INFO: renamed from: imageUrl$delegate, reason: from kotlin metadata */
    private final Lazy imageUrl = LazyKt.lazy(new Function0<String>() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$imageUrl$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            return this.this$0.getIntent().getStringExtra(Keys.IMAGE_URL);
        }
    });

    /* JADX INFO: renamed from: isShowSaveBtn$delegate, reason: from kotlin metadata */
    private final Lazy isShowSaveBtn = LazyKt.lazy(new Function0<Boolean>() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity.isShowSaveBtn.2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Boolean invoke() {
            return Boolean.valueOf(TransThemesActivity.this.getIntent().getBooleanExtra(Keys.SHOW, true));
        }
    });
    private final TransThemesActivity$onBitmapRequestListener$1 onBitmapRequestListener = new RequestListener<Bitmap>() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$onBitmapRequestListener$1
        @Override // com.bumptech.glide.request.RequestListener
        public boolean onLoadFailed(GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
            return true;
        }

        @Override // com.bumptech.glide.request.RequestListener
        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
            this.this$0.setImageByBitmap(resource);
            return true;
        }
    };
    private final TransThemesActivity$onDrawableRequestListener$1 onDrawableRequestListener = new RequestListener<Drawable>() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$onDrawableRequestListener$1
        @Override // com.bumptech.glide.request.RequestListener
        public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            return false;
        }

        @Override // com.bumptech.glide.request.RequestListener
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            this.this$0.setImageByBitmap(resource == null ? null : DrawableKt.toBitmap$default(resource, 0, 0, null, 7, null));
            return false;
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-11$lambda-8, reason: not valid java name */
    public static final void m353initView$lambda11$lambda8(View view) {
    }

    /* JADX INFO: compiled from: TransThemesActivity.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"Lcom/shangxian/pinkink/ui/themes/TransThemesActivity$Companion;", "", "()V", "gotoThis", "", "context", "Landroid/content/Context;", "userCreateTheme", "Lcom/shangxian/pinkink/bean/UserCreateThemeBean;", "isShowSaveBtn", "", "imageUrl", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void gotoThis(Context context, UserCreateThemeBean userCreateTheme, boolean isShowSaveBtn) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(userCreateTheme, "userCreateTheme");
            Intent intent = new Intent(context, (Class<?>) TransThemesActivity.class);
            intent.putExtra(Keys.THEME_DATA, userCreateTheme);
            intent.putExtra(Keys.SHOW, isShowSaveBtn);
            context.startActivity(intent);
        }

        public final void gotoThis(Context context, String imageUrl) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(imageUrl, "imageUrl");
            Intent intent = new Intent(context, (Class<?>) TransThemesActivity.class);
            intent.putExtra(Keys.IMAGE_URL, imageUrl);
            intent.putExtra(Keys.SHOW, true);
            context.startActivity(intent);
        }
    }

    @ViewModel
    private final ThemesViewModel getThemesViewModel() {
        return (ThemesViewModel) this.themesViewModel.getValue();
    }

    private final UserCreateThemeBean getUserCreateTheme() {
        return (UserCreateThemeBean) this.userCreateTheme.getValue();
    }

    private final String getImageUrl() {
        return (String) this.imageUrl.getValue();
    }

    private final boolean isShowSaveBtn() {
        return ((Boolean) this.isShowSaveBtn.getValue()).booleanValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        final ActivityTransThemesBinding activityTransThemesBinding = (ActivityTransThemesBinding) getMViewBinding();
        activityTransThemesBinding.titleBar.tvTitle.setText(getString(R.string.title_trans_theme));
        activityTransThemesBinding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TransThemesActivity.m344initView$lambda11$lambda0(this.f$0, view);
            }
        });
        activityTransThemesBinding.clContent.post(new Runnable() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                TransThemesActivity.m345initView$lambda11$lambda1(activityTransThemesBinding, this);
            }
        });
        activityTransThemesBinding.llJitterEffect.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TransThemesActivity.m347initView$lambda11$lambda2(this.f$0, view);
            }
        });
        activityTransThemesBinding.llLevelEffect.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TransThemesActivity.m348initView$lambda11$lambda3(this.f$0, view);
            }
        });
        activityTransThemesBinding.llGrayScaleEffect.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TransThemesActivity.m349initView$lambda11$lambda4(this.f$0, view);
            }
        });
        activityTransThemesBinding.ivImage.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TransThemesActivity.m350initView$lambda11$lambda6(this.f$0, activityTransThemesBinding, view);
            }
        });
        if (isShowSaveBtn()) {
            ImageView ivSave = activityTransThemesBinding.ivSave;
            Intrinsics.checkNotNullExpressionValue(ivSave, "ivSave");
            ViewExtensionsKt.visible(ivSave);
        } else {
            ImageView ivSave2 = activityTransThemesBinding.ivSave;
            Intrinsics.checkNotNullExpressionValue(ivSave2, "ivSave");
            ViewExtensionsKt.gone(ivSave2);
        }
        activityTransThemesBinding.ivSave.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TransThemesActivity.m352initView$lambda11$lambda7(this.f$0, view);
            }
        });
        activityTransThemesBinding.ivShare.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TransThemesActivity.m353initView$lambda11$lambda8(view);
            }
        });
        activityTransThemesBinding.ivEdit.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TransThemesActivity.m354initView$lambda11$lambda9(this.f$0, view);
            }
        });
        activityTransThemesBinding.llSyncNow.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TransThemesActivity.m346initView$lambda11$lambda10(this.f$0, view);
            }
        });
        getThemesViewModel().getInkEffectBitmap().observe(this, new Observer() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                TransThemesActivity.m355initView$lambda12(this.f$0, (Bitmap) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-11$lambda-0, reason: not valid java name */
    public static final void m344initView$lambda11$lambda0(TransThemesActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-11$lambda-1, reason: not valid java name */
    public static final void m345initView$lambda11$lambda1(ActivityTransThemesBinding this_run, TransThemesActivity this$0) {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        double measuredHeight = ((double) this_run.clContent.getMeasuredHeight()) * 0.8d;
        InkScreenSize currentInkScreenSize = AppConfig.INSTANCE.getCurrentInkScreenSize();
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams((int) (((double) (currentInkScreenSize.getWidth() / currentInkScreenSize.getHeight())) * measuredHeight), (int) measuredHeight);
        layoutParams.leftToLeft = R.id.clContent;
        layoutParams.rightToRight = R.id.clContent;
        layoutParams.bottomToBottom = R.id.clContent;
        layoutParams.topToTop = R.id.clContent;
        this_run.ivImage.setLayoutParams(layoutParams);
        if (this$0.getImageUrl() != null) {
            this_run.pageStateSwitcher.showLoadingView();
            Glide.with(this$0.getActivity()).asBitmap().load(this$0.getImageUrl()).fitCenter().addListener(this$0.onBitmapRequestListener).submit();
            return;
        }
        RequestManager requestManagerWith = Glide.with((FragmentActivity) this$0);
        UserCreateThemeBean userCreateTheme = this$0.getUserCreateTheme();
        String composeImagePath = null;
        if (TextUtils.isEmpty(userCreateTheme == null ? null : userCreateTheme.getComposeImagePath())) {
            UserCreateThemeBean userCreateTheme2 = this$0.getUserCreateTheme();
            if (userCreateTheme2 != null) {
                composeImagePath = userCreateTheme2.getBackgroundImagePath();
            }
        } else {
            UserCreateThemeBean userCreateTheme3 = this$0.getUserCreateTheme();
            if (userCreateTheme3 != null) {
                composeImagePath = userCreateTheme3.getComposeImagePath();
            }
        }
        requestManagerWith.load(composeImagePath).addListener(this$0.onDrawableRequestListener).fitCenter().into(this_run.ivImage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-11$lambda-2, reason: not valid java name */
    public static final void m347initView$lambda11$lambda2(TransThemesActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.jitterEffect(this$0.originBitmap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-11$lambda-3, reason: not valid java name */
    public static final void m348initView$lambda11$lambda3(TransThemesActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.levelEffect(this$0.originBitmap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-11$lambda-4, reason: not valid java name */
    public static final void m349initView$lambda11$lambda4(TransThemesActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.grayScaleEffect(this$0.originBitmap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-11$lambda-6, reason: not valid java name */
    public static final void m350initView$lambda11$lambda6(final TransThemesActivity this$0, final ActivityTransThemesBinding this_run, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        if (this$0.isShowOriginBitmap) {
            return;
        }
        this_run.ivImage.setImageBitmap(this$0.originBitmap);
        this$0.isShowOriginBitmap = true;
        this_run.ivImage.postDelayed(new Runnable() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                TransThemesActivity.m351initView$lambda11$lambda6$lambda5(this_run, this$0);
            }
        }, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-11$lambda-6$lambda-5, reason: not valid java name */
    public static final void m351initView$lambda11$lambda6$lambda5(ActivityTransThemesBinding this_run, TransThemesActivity this$0) {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this_run.ivImage.setImageBitmap(this$0.effectBitmap);
        this$0.isShowOriginBitmap = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-11$lambda-7, reason: not valid java name */
    public static final void m352initView$lambda11$lambda7(TransThemesActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.getUserCreateTheme() == null) {
            Bitmap bitmap = this$0.originBitmap;
            Intrinsics.checkNotNull(bitmap);
            String path = this$0.saveBitmapToPNG(bitmap).getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(path, "path");
            String imageUrl = this$0.getImageUrl();
            if (imageUrl == null) {
                imageUrl = "";
            }
            this$0.getThemesViewModel().saveUserCreateTheme(new UserCreateThemeBean(0L, null, 0L, path, imageUrl, 7, null), null);
            return;
        }
        ThemesViewModel themesViewModel = this$0.getThemesViewModel();
        UserCreateThemeBean userCreateTheme = this$0.getUserCreateTheme();
        Intrinsics.checkNotNull(userCreateTheme);
        themesViewModel.saveUserCreateTheme(userCreateTheme, ThemesEditActivity.INSTANCE.getGestureScaleRotateViewList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-11$lambda-9, reason: not valid java name */
    public static final void m354initView$lambda11$lambda9(TransThemesActivity this$0, View view) {
        UserCreateThemeBean userCreateTheme;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.getUserCreateTheme() == null) {
            String imageUrl = this$0.getImageUrl();
            if (imageUrl == null) {
                imageUrl = "";
            }
            userCreateTheme = new UserCreateThemeBean(0L, null, 0L, null, imageUrl, 15, null);
        } else {
            userCreateTheme = this$0.getUserCreateTheme();
        }
        ThemesEditActivity.INSTANCE.gotoThis(this$0.getActivity(), userCreateTheme);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-11$lambda-10, reason: not valid java name */
    public static final void m346initView$lambda11$lambda10(TransThemesActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (AppConfig.INSTANCE.isNfcDevice()) {
            String str = AppConfig.INSTANCE.getImageTempPath() + System.currentTimeMillis() + PictureMimeType.BMP;
            this$0.addLocalTmpFilePath(str);
            BmpUtils.saveBmp(this$0.effectBitmap, str);
            PinkinkNfcActivity.gotoThis(this$0.getActivity(), str);
            return;
        }
        if (PinkInkBlueToothManager.INSTANCE.isConnected()) {
            this$0.showSyncImageDialog();
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new TransThemesActivity$initView$1$10$1(this$0, null), 3, null);
        } else {
            this$0.showConnectDeviceDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: initView$lambda-12, reason: not valid java name */
    public static final void m355initView$lambda12(TransThemesActivity this$0, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getLoadingDialog().dismiss();
        this$0.effectBitmap = bitmap;
        ((ActivityTransThemesBinding) this$0.getMViewBinding()).ivImage.setImageBitmap(bitmap);
    }

    private final File saveBitmapToPNG(Bitmap bitmap) throws IOException {
        File file = new File(AppConfig.INSTANCE.getImageSavePath() + System.currentTimeMillis() + PictureMimeType.PNG);
        BitmapExtensionsKt.savePNGFile$default(bitmap, file, 0, 2, null);
        return file;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void levelEffect(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        ActivityTransThemesBinding activityTransThemesBinding = (ActivityTransThemesBinding) getMViewBinding();
        getLoadingDialog().show();
        getThemesViewModel().levelEffect(bitmap);
        activityTransThemesBinding.llJitterEffect.setEnabled(true);
        activityTransThemesBinding.llLevelEffect.setEnabled(false);
        activityTransThemesBinding.llGrayScaleEffect.setEnabled(true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void jitterEffect(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        ActivityTransThemesBinding activityTransThemesBinding = (ActivityTransThemesBinding) getMViewBinding();
        getLoadingDialog().show();
        getThemesViewModel().jitterEffect(bitmap);
        activityTransThemesBinding.llJitterEffect.setEnabled(false);
        activityTransThemesBinding.llLevelEffect.setEnabled(true);
        activityTransThemesBinding.llGrayScaleEffect.setEnabled(true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void grayScaleEffect(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        ActivityTransThemesBinding activityTransThemesBinding = (ActivityTransThemesBinding) getMViewBinding();
        getLoadingDialog().show();
        getThemesViewModel().grayScaleEffect(bitmap);
        activityTransThemesBinding.llJitterEffect.setEnabled(true);
        activityTransThemesBinding.llLevelEffect.setEnabled(true);
        activityTransThemesBinding.llGrayScaleEffect.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void setImageByBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        this.originBitmap = bitmap;
        InkScreenSize currentInkScreenSize = AppConfig.INSTANCE.getCurrentInkScreenSize();
        Bitmap bitmap2 = this.originBitmap;
        Intrinsics.checkNotNull(bitmap2);
        this.originBitmap = Bitmap.createScaledBitmap(bitmap2, currentInkScreenSize.getWidth(), currentInkScreenSize.getHeight(), true);
        ((ActivityTransThemesBinding) getMViewBinding()).ivImage.postDelayed(new Runnable() { // from class: com.shangxian.pinkink.ui.themes.TransThemesActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                TransThemesActivity.m356setImageByBitmap$lambda16(this.f$0);
            }
        }, 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: setImageByBitmap$lambda-16, reason: not valid java name */
    public static final void m356setImageByBitmap$lambda16(TransThemesActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ImageView imageView = ((ActivityTransThemesBinding) this$0.getMViewBinding()).ivEdit;
        Intrinsics.checkNotNullExpressionValue(imageView, "mViewBinding.ivEdit");
        ViewExtensionsKt.visible(imageView);
        ImageView imageView2 = ((ActivityTransThemesBinding) this$0.getMViewBinding()).ivSave;
        Intrinsics.checkNotNullExpressionValue(imageView2, "mViewBinding.ivSave");
        ViewExtensionsKt.visible(imageView2);
        Bitmap bitmap = this$0.originBitmap;
        Intrinsics.checkNotNull(bitmap);
        this$0.jitterEffect(bitmap);
        ((ActivityTransThemesBinding) this$0.getMViewBinding()).pageStateSwitcher.showContentView();
    }
}
