package com.lazyee.klib.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewTreeObserver;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;
import com.lazyee.klib.extension.ContextExtensionsKt;
import com.lazyee.klib.http.ApiManager;
import com.lazyee.klib.listener.OnKeyboardVisibleListener;
import com.lazyee.klib.mvvm.LoadingState;
import com.lazyee.klib.mvvm.MVVMBaseView;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\b\u0016\u0018\u0000 )2\u00020\u00012\u00020\u0002:\u0001)B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0013J\u0012\u0010\u0017\u001a\u00020\u00152\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0014J\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u00192\b\u0010\u001b\u001a\u0004\u0018\u00010\u0019H\u0002J\u0012\u0010\u001c\u001a\u00020\u00152\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0014J\b\u0010\u001f\u001a\u00020\u0015H\u0014J\u0010\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\u00152\u0006\u0010!\u001a\u00020\"H\u0016J\u0010\u0010$\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\u000fH\u0016J\u0012\u0010$\u001a\u00020\u00152\b\u0010&\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u0010'\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\u000fH\u0016J\u0012\u0010'\u001a\u00020\u00152\b\u0010&\u001a\u0004\u0018\u00010\u0005H\u0016J\u0006\u0010(\u001a\u00020\u0015R\u001b\u0010\u0004\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\n\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lcom/lazyee/klib/base/BaseActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/lazyee/klib/mvvm/MVVMBaseView;", "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "TAG$delegate", "Lkotlin/Lazy;", "activity", "Landroid/app/Activity;", "getActivity", "()Landroid/app/Activity;", "mDecorViewVisibleHeight", "", "mOnKeyboardVisibleChangeGlobalLayoutListener", "Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;", "mOnKeyboardVisibleListener", "Lcom/lazyee/klib/listener/OnKeyboardVisibleListener;", "addOnKeyboardVisibleListener", "", "listener", "attachBaseContext", "newBase", "Landroid/content/Context;", "getConfigurationContext", "context", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onLoadingStateChanged", "state", "Lcom/lazyee/klib/mvvm/LoadingState;", "onPageLoadingStateChanged", "onShowLongToast", "resId", NotificationCompat.CATEGORY_MESSAGE, "onShowShortToast", "removeOnKeyboardVisibleListener", "Companion", "library_release"}, k = 1, mv = {1, 4, 2})
public class BaseActivity extends AppCompatActivity implements MVVMBaseView {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static boolean isFollowSystemFontScale = true;

    /* JADX INFO: renamed from: TAG$delegate, reason: from kotlin metadata */
    private final Lazy TAG = LazyKt.lazy(new Function0<String>() { // from class: com.lazyee.klib.base.BaseActivity$TAG$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            String simpleName = this.this$0.getClass().getSimpleName();
            Intrinsics.checkNotNullExpressionValue(simpleName, "this::class.java.simpleName");
            return simpleName;
        }
    });
    private int mDecorViewVisibleHeight;
    private ViewTreeObserver.OnGlobalLayoutListener mOnKeyboardVisibleChangeGlobalLayoutListener;
    private OnKeyboardVisibleListener mOnKeyboardVisibleListener;

    public final String getTAG() {
        return (String) this.TAG.getValue();
    }

    @Override // com.lazyee.klib.mvvm.MVVMBaseView
    public void onLoadingStateChanged(LoadingState state) {
        Intrinsics.checkNotNullParameter(state, "state");
    }

    @Override // com.lazyee.klib.mvvm.MVVMBaseView
    public void onPageLoadingStateChanged(LoadingState state) {
        Intrinsics.checkNotNullParameter(state, "state");
    }

    /* JADX INFO: compiled from: BaseActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lcom/lazyee/klib/base/BaseActivity$Companion;", "", "()V", "isFollowSystemFontScale", "", "()Z", "setFollowSystemFontScale", "(Z)V", "library_release"}, k = 1, mv = {1, 4, 2})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean isFollowSystemFontScale() {
            return BaseActivity.isFollowSystemFontScale;
        }

        public final void setFollowSystemFontScale(boolean z) {
            BaseActivity.isFollowSystemFontScale = z;
        }
    }

    @Override // com.lazyee.klib.mvvm.MVVMBaseView
    public void initViewModel(LifecycleOwner owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        MVVMBaseView.DefaultImpls.initViewModel(this, owner);
    }

    public final Activity getActivity() {
        return this;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context newBase) {
        if (isFollowSystemFontScale) {
            super.attachBaseContext(newBase);
        } else {
            super.attachBaseContext(getConfigurationContext(newBase));
        }
    }

    private final Context getConfigurationContext(Context context) {
        if (context == null) {
            return null;
        }
        Resources resources = context.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "context.resources");
        Configuration configuration = resources.getConfiguration();
        Intrinsics.checkNotNullExpressionValue(configuration, "context.resources.configuration");
        configuration.fontScale = 1.0f;
        return context.createConfigurationContext(configuration);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel(this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        ApiManager.INSTANCE.cancel(this);
        removeOnKeyboardVisibleListener();
    }

    @Override // com.lazyee.klib.mvvm.MVVMBaseView
    public void onShowLongToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Intrinsics.checkNotNull(msg);
        ContextExtensionsKt.toastLong(this, msg);
    }

    @Override // com.lazyee.klib.mvvm.MVVMBaseView
    public void onShowShortToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Intrinsics.checkNotNull(msg);
        ContextExtensionsKt.toastShort(this, msg);
    }

    @Override // com.lazyee.klib.mvvm.MVVMBaseView
    public void onShowLongToast(int resId) {
        ContextExtensionsKt.toastLong(this, resId);
    }

    @Override // com.lazyee.klib.mvvm.MVVMBaseView
    public void onShowShortToast(int resId) {
        ContextExtensionsKt.toastShort(this, resId);
    }

    public final void addOnKeyboardVisibleListener(OnKeyboardVisibleListener listener) {
        this.mOnKeyboardVisibleListener = listener;
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = this.mOnKeyboardVisibleChangeGlobalLayoutListener;
        if (onGlobalLayoutListener != null) {
            ContextExtensionsKt.removeKeyBoardVisibleListener(this, onGlobalLayoutListener);
        }
        Window window = getWindow();
        Intrinsics.checkNotNullExpressionValue(window, "window");
        if (window.getAttributes().softInputMode == 48) {
            this.mOnKeyboardVisibleChangeGlobalLayoutListener = ContextExtensionsKt.addAdjustNothingModeOnKeyBoardVisibleListener(this, this.mOnKeyboardVisibleListener);
        } else {
            this.mOnKeyboardVisibleChangeGlobalLayoutListener = ContextExtensionsKt.addOnKeyBoardVisibleListener(this, this.mOnKeyboardVisibleListener);
        }
    }

    public final void removeOnKeyboardVisibleListener() {
        this.mOnKeyboardVisibleListener = (OnKeyboardVisibleListener) null;
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = this.mOnKeyboardVisibleChangeGlobalLayoutListener;
        if (onGlobalLayoutListener != null) {
            ContextExtensionsKt.removeKeyBoardVisibleListener(this, onGlobalLayoutListener);
        }
        this.mOnKeyboardVisibleChangeGlobalLayoutListener = (ViewTreeObserver.OnGlobalLayoutListener) null;
    }
}
