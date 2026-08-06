package com.lazyee.klib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DelayDialog.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B#\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u000e\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u000eJ\b\u0010\u0014\u001a\u00020\u0011H\u0016R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/lazyee/klib/dialog/DelayDialog;", "Landroid/app/Dialog;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "themeResId", "", "(Landroid/content/Context;I)V", "cancelable", "", "cancelListener", "Landroid/content/DialogInterface$OnCancelListener;", "(Landroid/content/Context;ZLandroid/content/DialogInterface$OnCancelListener;)V", "delayDuration", "", "isDelayEnd", "dismiss", "", "setDelayDuration", "millis", "show", "library_release"}, k = 1, mv = {1, 4, 2})
public class DelayDialog extends Dialog {
    private long delayDuration;
    private boolean isDelayEnd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DelayDialog(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.isDelayEnd = true;
        this.delayDuration = 200L;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DelayDialog(Context context, int i) {
        super(context, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.isDelayEnd = true;
        this.delayDuration = 200L;
    }

    public /* synthetic */ DelayDialog(Context context, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? 0 : i);
    }

    public /* synthetic */ DelayDialog(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? true : z, onCancelListener);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DelayDialog(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
        Intrinsics.checkNotNullParameter(context, "context");
        this.isDelayEnd = true;
        this.delayDuration = 200L;
    }

    public final void setDelayDuration(long millis) {
        this.delayDuration = millis;
    }

    @Override // android.app.Dialog
    public void show() {
        if (!isShowing() && this.isDelayEnd) {
            this.isDelayEnd = false;
            new Handler().postDelayed(new Runnable() { // from class: com.lazyee.klib.dialog.DelayDialog.show.1
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        try {
                            if (!DelayDialog.this.isShowing() && !DelayDialog.this.isDelayEnd) {
                                DelayDialog.super.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } finally {
                        DelayDialog.this.isDelayEnd = true;
                    }
                }
            }, this.delayDuration);
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
        this.isDelayEnd = true;
    }
}
