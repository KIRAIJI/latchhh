package com.shangxian.pinkink.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import com.lazyee.klib.base.ViewBindingDialog;
import com.lazyee.klib.extension.WindowExtensionsKt;
import com.lazyee.klib.util.ToastUtils;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.databinding.DialogUpdateNicknameBinding;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: UpdateNickNameDialog.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0011B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010\f\u001a\u00020\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\u0010\u0010\u000f\u001a\u00020\u00002\b\u0010\u0010\u001a\u0004\u0018\u00010\tR\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/shangxian/pinkink/ui/dialog/UpdateNickNameDialog;", "Lcom/lazyee/klib/base/ViewBindingDialog;", "Lcom/shangxian/pinkink/databinding/DialogUpdateNicknameBinding;", "context", "Landroid/content/Context;", "oldNickName", "", "(Landroid/content/Context;Ljava/lang/String;)V", "mActionCallback", "Lcom/shangxian/pinkink/ui/dialog/UpdateNickNameDialog$ActionCallback;", "initView", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setActionCallback", "callback", "ActionCallback", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class UpdateNickNameDialog extends ViewBindingDialog<DialogUpdateNicknameBinding> {
    private ActionCallback mActionCallback;
    private final String oldNickName;

    /* JADX INFO: compiled from: UpdateNickNameDialog.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/shangxian/pinkink/ui/dialog/UpdateNickNameDialog$ActionCallback;", "", "onUpdateNickName", "", "newNickName", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public interface ActionCallback {
        void onUpdateNickName(String newNickName);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UpdateNickNameDialog(Context context, String oldNickName) {
        super(context, R.style.Dialog_FontDownload);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(oldNickName, "oldNickName");
        this.oldNickName = oldNickName;
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
        final DialogUpdateNicknameBinding mViewBinding = getMViewBinding();
        if (mViewBinding == null) {
            return;
        }
        mViewBinding.etNickName.setText(this.oldNickName);
        mViewBinding.tvCancel.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.UpdateNickNameDialog$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UpdateNickNameDialog.m195initView$lambda2$lambda0(this.f$0, view);
            }
        });
        mViewBinding.tvConfirm.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.UpdateNickNameDialog$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UpdateNickNameDialog.m196initView$lambda2$lambda1(mViewBinding, this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2$lambda-0, reason: not valid java name */
    public static final void m195initView$lambda2$lambda0(UpdateNickNameDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2$lambda-1, reason: not valid java name */
    public static final void m196initView$lambda2$lambda1(DialogUpdateNicknameBinding this_run, UpdateNickNameDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String string = this_run.etNickName.getText().toString();
        if (TextUtils.isEmpty(string)) {
            ToastUtils toastUtils = ToastUtils.INSTANCE;
            Context context = this$0.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            String string2 = this$0.getContext().getString(R.string.toast_nickname_cannot_be_empty);
            Intrinsics.checkNotNullExpressionValue(string2, "context.getString(R.stri…nickname_cannot_be_empty)");
            toastUtils.toastShort(context, string2);
            return;
        }
        ActionCallback actionCallback = this$0.mActionCallback;
        if (actionCallback != null) {
            actionCallback.onUpdateNickName(string);
        }
        this$0.dismiss();
    }

    public final UpdateNickNameDialog setActionCallback(ActionCallback callback) {
        this.mActionCallback = callback;
        return this;
    }
}
