package com.shangxian.pinkink.ui.dialog;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.lazyee.klib.base.ViewBindingDialog;
import com.lazyee.klib.extension.WindowExtensionsKt;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.constants.DeviceType;
import com.shangxian.pinkink.databinding.DialogSwitchDeviceTypeBinding;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SwitchDeviceTypeDialog.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\fB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\tH\u0016J\u000e\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u0007R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/shangxian/pinkink/ui/dialog/SwitchDeviceTypeDialog;", "Lcom/lazyee/klib/base/ViewBindingDialog;", "Lcom/shangxian/pinkink/databinding/DialogSwitchDeviceTypeBinding;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "mActionCallback", "Lcom/shangxian/pinkink/ui/dialog/SwitchDeviceTypeDialog$ActionCallback;", "initView", "", "setActionCallback", "callback", "ActionCallback", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class SwitchDeviceTypeDialog extends ViewBindingDialog<DialogSwitchDeviceTypeBinding> {
    private ActionCallback mActionCallback;

    /* JADX INFO: compiled from: SwitchDeviceTypeDialog.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/shangxian/pinkink/ui/dialog/SwitchDeviceTypeDialog$ActionCallback;", "", "onSwitchDeviceType", "", "type", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public interface ActionCallback {
        void onSwitchDeviceType(String type);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SwitchDeviceTypeDialog(Context context) {
        super(context, R.style.Dialog_SwitchDeviceType);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // com.lazyee.klib.base.ViewBindingDialog
    public void initView() {
        super.initView();
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window == null ? null : window.getAttributes();
        if (attributes != null) {
            attributes.gravity = 80;
        }
        Window window2 = getWindow();
        if (window2 != null) {
            window2.setAttributes(attributes);
        }
        Window window3 = getWindow();
        if (window3 != null) {
            WindowExtensionsKt.setSize(window3, -1, -2);
        }
        DialogSwitchDeviceTypeBinding mViewBinding = getMViewBinding();
        if (mViewBinding == null) {
            return;
        }
        mViewBinding.tvDeviceTypeBlueTooth.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.SwitchDeviceTypeDialog$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SwitchDeviceTypeDialog.m169initView$lambda3$lambda0(this.f$0, view);
            }
        });
        mViewBinding.tvDeviceTypeNfc.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.SwitchDeviceTypeDialog$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SwitchDeviceTypeDialog.m170initView$lambda3$lambda1(this.f$0, view);
            }
        });
        mViewBinding.tvCancel.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.SwitchDeviceTypeDialog$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SwitchDeviceTypeDialog.m171initView$lambda3$lambda2(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3$lambda-0, reason: not valid java name */
    public static final void m169initView$lambda3$lambda0(SwitchDeviceTypeDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActionCallback actionCallback = this$0.mActionCallback;
        if (actionCallback != null) {
            actionCallback.onSwitchDeviceType(DeviceType.TYPE_BLUE_TOOTH);
        }
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3$lambda-1, reason: not valid java name */
    public static final void m170initView$lambda3$lambda1(SwitchDeviceTypeDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActionCallback actionCallback = this$0.mActionCallback;
        if (actionCallback != null) {
            actionCallback.onSwitchDeviceType(DeviceType.TYPE_NFC);
        }
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3$lambda-2, reason: not valid java name */
    public static final void m171initView$lambda3$lambda2(SwitchDeviceTypeDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    public final SwitchDeviceTypeDialog setActionCallback(ActionCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mActionCallback = callback;
        return this;
    }
}
