package com.shangxian.pinkink.ui.mine;

import android.view.View;
import com.lazyee.klib.extension.ContextExtensionsKt;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.databinding.ActivityCancelAccountStep1Binding;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CancelAccountStep1Activity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"Lcom/shangxian/pinkink/ui/mine/CancelAccountStep1Activity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityCancelAccountStep1Binding;", "()V", "initView", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class CancelAccountStep1Activity extends BaseActivity<ActivityCancelAccountStep1Binding> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        ActivityCancelAccountStep1Binding activityCancelAccountStep1Binding = (ActivityCancelAccountStep1Binding) getMViewBinding();
        activityCancelAccountStep1Binding.titleBar.tvTitle.setText(getString(R.string.title_apply_account_cancellation_step1));
        activityCancelAccountStep1Binding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.mine.CancelAccountStep1Activity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CancelAccountStep1Activity.m283initView$lambda2$lambda0(this.f$0, view);
            }
        });
        activityCancelAccountStep1Binding.btnNext.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.mine.CancelAccountStep1Activity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Exception {
                CancelAccountStep1Activity.m284initView$lambda2$lambda1(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2$lambda-0, reason: not valid java name */
    public static final void m283initView$lambda2$lambda0(CancelAccountStep1Activity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2$lambda-1, reason: not valid java name */
    public static final void m284initView$lambda2$lambda1(CancelAccountStep1Activity this$0, View view) throws Exception {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ContextExtensionsKt.goto$default(this$0, CancelAccountStep2Activity.class, null, null, null, null, 30, null);
    }
}
