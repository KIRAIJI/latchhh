package com.shangxian.pinkink.ui.device;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.constants.DeviceType;
import com.shangxian.pinkink.databinding.ActivitySwitchDeviceBinding;
import com.shangxian.pinkink.ui.main.MainActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SwitchDeviceActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u00062\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0006B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0007"}, d2 = {"Lcom/shangxian/pinkink/ui/device/SwitchDeviceActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivitySwitchDeviceBinding;", "()V", "initView", "", "Companion", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class SwitchDeviceActivity extends BaseActivity<ActivitySwitchDeviceBinding> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: compiled from: SwitchDeviceActivity.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/shangxian/pinkink/ui/device/SwitchDeviceActivity$Companion;", "", "()V", "gotoThis", "", "context", "Landroid/content/Context;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void gotoThis(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.startActivity(new Intent(context, (Class<?>) SwitchDeviceActivity.class));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        final ActivitySwitchDeviceBinding activitySwitchDeviceBinding = (ActivitySwitchDeviceBinding) getMViewBinding();
        activitySwitchDeviceBinding.llDeviceTypeNFC.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.device.SwitchDeviceActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SwitchDeviceActivity.m157initView$lambda3$lambda0(this.f$0, view);
            }
        });
        activitySwitchDeviceBinding.llDeviceTypeBlueTooth.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.device.SwitchDeviceActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SwitchDeviceActivity.m158initView$lambda3$lambda1(activitySwitchDeviceBinding, view);
            }
        });
        activitySwitchDeviceBinding.llDeviceTypeBlueTooth.setSelected(true);
        activitySwitchDeviceBinding.ivDeviceTypeNext.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.device.SwitchDeviceActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SwitchDeviceActivity.m159initView$lambda3$lambda2(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3$lambda-0, reason: not valid java name */
    public static final void m157initView$lambda3$lambda0(SwitchDeviceActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AppConfig.INSTANCE.setDeviceType(DeviceType.TYPE_NFC);
        MainActivity.INSTANCE.gotoThis(this$0);
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3$lambda-1, reason: not valid java name */
    public static final void m158initView$lambda3$lambda1(ActivitySwitchDeviceBinding this_run, View view) {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        this_run.llDeviceTypeBlueTooth.setSelected(true);
        this_run.llDeviceTypeNFC.setSelected(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3$lambda-2, reason: not valid java name */
    public static final void m159initView$lambda3$lambda2(SwitchDeviceActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AppConfig.INSTANCE.setDeviceType(DeviceType.TYPE_BLUE_TOOTH);
        MainActivity.INSTANCE.gotoThis(this$0);
        this$0.finish();
    }
}
