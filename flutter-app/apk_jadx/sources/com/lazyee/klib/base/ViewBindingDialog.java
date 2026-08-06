package com.lazyee.klib.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.viewbinding.ViewBinding;
import com.lazyee.klib.util.ViewBindingUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ViewBindingDialog.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0017\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tB\u001f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0012\u0010\u0017\u001a\u00020\u00162\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0014R\u001e\u0010\u000f\u001a\u0004\u0018\u00018\u0000X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0014\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\u001a"}, d2 = {"Lcom/lazyee/klib/base/ViewBindingDialog;", "VB", "Landroidx/viewbinding/ViewBinding;", "Landroid/app/Dialog;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "themeResId", "", "(Landroid/content/Context;I)V", "cancelable", "", "cancelListener", "Landroid/content/DialogInterface$OnCancelListener;", "(Landroid/content/Context;ZLandroid/content/DialogInterface$OnCancelListener;)V", "mViewBinding", "getMViewBinding", "()Landroidx/viewbinding/ViewBinding;", "setMViewBinding", "(Landroidx/viewbinding/ViewBinding;)V", "Landroidx/viewbinding/ViewBinding;", "initView", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "library_release"}, k = 1, mv = {1, 4, 2})
public class ViewBindingDialog<VB extends ViewBinding> extends Dialog {
    private VB mViewBinding;

    public void initView() {
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ViewBindingDialog(Context context) {
        this(context, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewBindingDialog(Context context, int i) {
        super(context, i);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewBindingDialog(Context context, boolean z, DialogInterface.OnCancelListener cancelListener) {
        super(context, z, cancelListener);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(cancelListener, "cancelListener");
    }

    public final VB getMViewBinding() {
        return this.mViewBinding;
    }

    public final void setMViewBinding(VB vb) {
        this.mViewBinding = vb;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewBindingUtils viewBindingUtils = ViewBindingUtils.INSTANCE;
        Class<?> cls = getClass();
        LayoutInflater layoutInflater = getLayoutInflater();
        Intrinsics.checkNotNullExpressionValue(layoutInflater, "layoutInflater");
        VB vb = (VB) viewBindingUtils.getViewBinding(cls, layoutInflater);
        this.mViewBinding = vb;
        if (vb != null) {
            setContentView(vb.getRoot());
        }
        initView();
    }
}
