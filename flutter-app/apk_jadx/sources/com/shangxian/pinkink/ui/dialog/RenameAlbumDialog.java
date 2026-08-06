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
import com.shangxian.pinkink.bean.AlbumBean;
import com.shangxian.pinkink.databinding.DialogRenameAlbumBinding;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: RenameAlbumDialog.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0011B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010\f\u001a\u00020\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\u0010\u0010\u000f\u001a\u00020\u00002\b\u0010\u0010\u001a\u0004\u0018\u00010\tR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/shangxian/pinkink/ui/dialog/RenameAlbumDialog;", "Lcom/lazyee/klib/base/ViewBindingDialog;", "Lcom/shangxian/pinkink/databinding/DialogRenameAlbumBinding;", "context", "Landroid/content/Context;", "album", "Lcom/shangxian/pinkink/bean/AlbumBean;", "(Landroid/content/Context;Lcom/shangxian/pinkink/bean/AlbumBean;)V", "mActionCallback", "Lcom/shangxian/pinkink/ui/dialog/RenameAlbumDialog$ActionCallback;", "initView", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setActionCallback", "callback", "ActionCallback", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class RenameAlbumDialog extends ViewBindingDialog<DialogRenameAlbumBinding> {
    private final AlbumBean album;
    private ActionCallback mActionCallback;

    /* JADX INFO: compiled from: RenameAlbumDialog.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\b"}, d2 = {"Lcom/shangxian/pinkink/ui/dialog/RenameAlbumDialog$ActionCallback;", "", "onRenameAlbumName", "", "album", "Lcom/shangxian/pinkink/bean/AlbumBean;", "albumName", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public interface ActionCallback {
        void onRenameAlbumName(AlbumBean album, String albumName);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RenameAlbumDialog(Context context, AlbumBean album) {
        super(context, R.style.Dialog_FontDownload);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(album, "album");
        this.album = album;
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
        final DialogRenameAlbumBinding mViewBinding = getMViewBinding();
        if (mViewBinding == null) {
            return;
        }
        mViewBinding.etAlbumName.setText(this.album.getName());
        mViewBinding.tvCancel.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.RenameAlbumDialog$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RenameAlbumDialog.m166initView$lambda2$lambda0(this.f$0, view);
            }
        });
        mViewBinding.tvConfirm.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.RenameAlbumDialog$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RenameAlbumDialog.m167initView$lambda2$lambda1(mViewBinding, this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2$lambda-0, reason: not valid java name */
    public static final void m166initView$lambda2$lambda0(RenameAlbumDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2$lambda-1, reason: not valid java name */
    public static final void m167initView$lambda2$lambda1(DialogRenameAlbumBinding this_run, RenameAlbumDialog this$0, View view) {
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
            actionCallback.onRenameAlbumName(this$0.album, string);
        }
        this$0.dismiss();
    }

    public final RenameAlbumDialog setActionCallback(ActionCallback callback) {
        this.mActionCallback = callback;
        return this;
    }
}
