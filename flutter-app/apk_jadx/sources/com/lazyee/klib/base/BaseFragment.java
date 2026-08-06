package com.lazyee.klib.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import com.lazyee.klib.extension.ContextExtensionsKt;
import com.lazyee.klib.http.ApiManager;
import com.lazyee.klib.mvvm.LoadingState;
import com.lazyee.klib.mvvm.MVVMBaseView;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseFragment.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0012\u0010\u0015\u001a\u00020\u00102\b\u0010\u0018\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0012\u0010\u0019\u001a\u00020\u00102\b\u0010\u0018\u001a\u0004\u0018\u00010\u0005H\u0016J\u001a\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\u000e\u0010\u001f\u001a\u00020\u00102\u0006\u0010 \u001a\u00020!R\u001b\u0010\u0004\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\""}, d2 = {"Lcom/lazyee/klib/base/BaseFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/lazyee/klib/mvvm/MVVMBaseView;", "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "TAG$delegate", "Lkotlin/Lazy;", "isViewCreated", "", "()Z", "setViewCreated", "(Z)V", "onDestroy", "", "onLoadingStateChanged", "state", "Lcom/lazyee/klib/mvvm/LoadingState;", "onPageLoadingStateChanged", "onShowLongToast", "resId", "", NotificationCompat.CATEGORY_MESSAGE, "onShowShortToast", "onViewCreated", "view", "Landroid/view/View;", "savedInstanceState", "Landroid/os/Bundle;", "runOnUiThread", "action", "Ljava/lang/Runnable;", "library_release"}, k = 1, mv = {1, 4, 2})
public class BaseFragment extends Fragment implements MVVMBaseView {

    /* JADX INFO: renamed from: TAG$delegate, reason: from kotlin metadata */
    private final Lazy TAG = LazyKt.lazy(new Function0<String>() { // from class: com.lazyee.klib.base.BaseFragment$TAG$2
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
    private boolean isViewCreated;

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

    @Override // com.lazyee.klib.mvvm.MVVMBaseView
    public void initViewModel(LifecycleOwner owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        MVVMBaseView.DefaultImpls.initViewModel(this, owner);
    }

    /* JADX INFO: renamed from: isViewCreated, reason: from getter */
    public final boolean getIsViewCreated() {
        return this.isViewCreated;
    }

    public final void setViewCreated(boolean z) {
        this.isViewCreated = z;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        this.isViewCreated = true;
        initViewModel(this);
    }

    public final void runOnUiThread(Runnable action) {
        Intrinsics.checkNotNullParameter(action, "action");
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(action);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        ApiManager.INSTANCE.cancel(this);
    }

    @Override // com.lazyee.klib.mvvm.MVVMBaseView
    public void onShowLongToast(String msg) {
        FragmentActivity activity;
        if (TextUtils.isEmpty(msg) || (activity = getActivity()) == null) {
            return;
        }
        Intrinsics.checkNotNull(msg);
        ContextExtensionsKt.toastLong(activity, msg);
    }

    @Override // com.lazyee.klib.mvvm.MVVMBaseView
    public void onShowShortToast(String msg) {
        FragmentActivity activity;
        if (TextUtils.isEmpty(msg) || (activity = getActivity()) == null) {
            return;
        }
        Intrinsics.checkNotNull(msg);
        ContextExtensionsKt.toastShort(activity, msg);
    }

    @Override // com.lazyee.klib.mvvm.MVVMBaseView
    public void onShowLongToast(int resId) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ContextExtensionsKt.toastLong(activity, resId);
        }
    }

    @Override // com.lazyee.klib.mvvm.MVVMBaseView
    public void onShowShortToast(int resId) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ContextExtensionsKt.toastShort(activity, resId);
        }
    }
}
