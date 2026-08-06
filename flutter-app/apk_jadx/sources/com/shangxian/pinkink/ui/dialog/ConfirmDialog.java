package com.shangxian.pinkink.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.lazyee.klib.base.ViewBindingDialog;
import com.lazyee.klib.extension.WindowExtensionsKt;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.databinding.DialogConfirmBinding;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ConfirmDialog.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0013B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010\f\u001a\u00020\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\u0010\u0010\u000f\u001a\u00020\u00002\b\u0010\u0010\u001a\u0004\u0018\u00010\u0007J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\tR\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/shangxian/pinkink/ui/dialog/ConfirmDialog;", "Lcom/lazyee/klib/base/ViewBindingDialog;", "Lcom/shangxian/pinkink/databinding/DialogConfirmBinding;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "mActionCallback", "Lcom/shangxian/pinkink/ui/dialog/ConfirmDialog$ActionCallback;", "mMessage", "", "initView", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setActionCallback", "callback", "setMessage", "message", "ActionCallback", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class ConfirmDialog extends ViewBindingDialog<DialogConfirmBinding> {
    private ActionCallback mActionCallback;
    private String mMessage;

    /* JADX INFO: compiled from: ConfirmDialog.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&¨\u0006\u0005"}, d2 = {"Lcom/shangxian/pinkink/ui/dialog/ConfirmDialog$ActionCallback;", "", "onCancel", "", "onConfirm", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public interface ActionCallback {
        void onCancel();

        void onConfirm();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConfirmDialog(Context context) {
        super(context, R.style.Dialog_FontDownload);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mMessage = "";
    }

    @Override // com.lazyee.klib.base.ViewBindingDialog, android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window == null) {
            return;
        }
        WindowExtensionsKt.setSize(window, -1, -2);
    }

    @Override // com.lazyee.klib.base.ViewBindingDialog
    public void initView() {
        super.initView();
        DialogConfirmBinding mViewBinding = getMViewBinding();
        if (mViewBinding == null) {
            return;
        }
        mViewBinding.tvMessage.setText(this.mMessage);
        mViewBinding.tvCancel.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.ConfirmDialog$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ConfirmDialog.m161initView$lambda2$lambda0(this.f$0, view);
            }
        });
        mViewBinding.tvConfirm.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.ConfirmDialog$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ConfirmDialog.m162initView$lambda2$lambda1(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2$lambda-0, reason: not valid java name */
    public static final void m161initView$lambda2$lambda0(ConfirmDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActionCallback actionCallback = this$0.mActionCallback;
        if (actionCallback != null) {
            actionCallback.onCancel();
        }
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2$lambda-1, reason: not valid java name */
    public static final void m162initView$lambda2$lambda1(ConfirmDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActionCallback actionCallback = this$0.mActionCallback;
        if (actionCallback != null) {
            actionCallback.onConfirm();
        }
        this$0.dismiss();
    }

    public final ConfirmDialog setActionCallback(ActionCallback callback) {
        this.mActionCallback = callback;
        return this;
    }

    public final ConfirmDialog setMessage(String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        this.mMessage = message;
        return this;
    }
}
