package com.shangxian.pinkink.base;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.viewbinding.ViewBinding;
import com.gyf.immersionbar.ImmersionBar;
import com.lazyee.klib.base.ViewBindingFragment;
import com.lazyee.klib.mvvm.LoadingState;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.ui.dialog.LoadingDialog;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseFragment.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001a\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016R\u001b\u0010\u0005\u001a\u00020\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u0006\u0014"}, d2 = {"Lcom/shangxian/pinkink/base/BaseFragment;", "VB", "Landroidx/viewbinding/ViewBinding;", "Lcom/lazyee/klib/base/ViewBindingFragment;", "()V", "loadingDialog", "Lcom/shangxian/pinkink/ui/dialog/LoadingDialog;", "getLoadingDialog", "()Lcom/shangxian/pinkink/ui/dialog/LoadingDialog;", "loadingDialog$delegate", "Lkotlin/Lazy;", "onLoadingStateChanged", "", "state", "Lcom/lazyee/klib/mvvm/LoadingState;", "onViewCreated", "view", "Landroid/view/View;", "savedInstanceState", "Landroid/os/Bundle;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public class BaseFragment<VB extends ViewBinding> extends ViewBindingFragment<VB> {

    /* JADX INFO: renamed from: loadingDialog$delegate, reason: from kotlin metadata */
    private final Lazy loadingDialog = LazyKt.lazy(new Function0<LoadingDialog>(this) { // from class: com.shangxian.pinkink.base.BaseFragment$loadingDialog$2
        final /* synthetic */ BaseFragment<VB> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        {
            super(0);
            this.this$0 = this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final LoadingDialog invoke() {
            FragmentActivity fragmentActivityRequireActivity = this.this$0.requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity()");
            return new LoadingDialog(fragmentActivityRequireActivity);
        }
    });

    /* JADX INFO: compiled from: BaseFragment.kt */
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

    public final LoadingDialog getLoadingDialog() {
        return (LoadingDialog) this.loadingDialog.getValue();
    }

    @Override // com.lazyee.klib.base.ViewBindingFragment, com.lazyee.klib.base.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).titleBar(R.id.titleBar).init();
    }

    @Override // com.lazyee.klib.base.BaseFragment, com.lazyee.klib.mvvm.MVVMBaseView
    public void onLoadingStateChanged(LoadingState state) {
        Intrinsics.checkNotNullParameter(state, "state");
        super.onLoadingStateChanged(state);
        int i = WhenMappings.$EnumSwitchMapping$0[state.ordinal()];
        if (i == 1) {
            getLoadingDialog().show();
        } else if (i == 2) {
            getLoadingDialog().dismiss();
        } else {
            if (i != 3) {
                return;
            }
            getLoadingDialog().dismiss();
        }
    }
}
