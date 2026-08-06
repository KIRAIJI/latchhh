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
import com.shangxian.pinkink.databinding.DialogCreateAlbumBinding;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: CreateAlbumDialog.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u000fB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\tH\u0016J\u0012\u0010\n\u001a\u00020\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0014J\u0010\u0010\r\u001a\u00020\u00002\b\u0010\u000e\u001a\u0004\u0018\u00010\u0007R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/shangxian/pinkink/ui/dialog/CreateAlbumDialog;", "Lcom/lazyee/klib/base/ViewBindingDialog;", "Lcom/shangxian/pinkink/databinding/DialogCreateAlbumBinding;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "mActionCallback", "Lcom/shangxian/pinkink/ui/dialog/CreateAlbumDialog$ActionCallback;", "initView", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setActionCallback", "callback", "ActionCallback", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class CreateAlbumDialog extends ViewBindingDialog<DialogCreateAlbumBinding> {
    private ActionCallback mActionCallback;

    /* JADX INFO: compiled from: CreateAlbumDialog.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/shangxian/pinkink/ui/dialog/CreateAlbumDialog$ActionCallback;", "", "onConfirmAlbumName", "", "albumName", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public interface ActionCallback {
        void onConfirmAlbumName(String albumName);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CreateAlbumDialog(Context context) {
        super(context, R.style.Dialog_FontDownload);
        Intrinsics.checkNotNullParameter(context, "context");
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
        final DialogCreateAlbumBinding mViewBinding = getMViewBinding();
        if (mViewBinding == null) {
            return;
        }
        mViewBinding.tvCancel.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.CreateAlbumDialog$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CreateAlbumDialog.m164initView$lambda2$lambda0(this.f$0, view);
            }
        });
        mViewBinding.tvConfirm.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.CreateAlbumDialog$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CreateAlbumDialog.m165initView$lambda2$lambda1(mViewBinding, this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2$lambda-0, reason: not valid java name */
    public static final void m164initView$lambda2$lambda0(CreateAlbumDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2$lambda-1, reason: not valid java name */
    public static final void m165initView$lambda2$lambda1(DialogCreateAlbumBinding this_run, CreateAlbumDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String string = this_run.etAlbumName.getText().toString();
        if (TextUtils.isEmpty(StringsKt.trim((CharSequence) string).toString())) {
            ToastUtils toastUtils = ToastUtils.INSTANCE;
            Context context = this$0.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            String string2 = this$0.getContext().getString(R.string.toast_please_input_album_name);
            Intrinsics.checkNotNullExpressionValue(string2, "context.getString(R.stri…_please_input_album_name)");
            toastUtils.toastLong(context, string2);
            return;
        }
        ActionCallback actionCallback = this$0.mActionCallback;
        if (actionCallback != null) {
            actionCallback.onConfirmAlbumName(string);
        }
        this$0.dismiss();
    }

    public final CreateAlbumDialog setActionCallback(ActionCallback callback) {
        this.mActionCallback = callback;
        return this;
    }
}
