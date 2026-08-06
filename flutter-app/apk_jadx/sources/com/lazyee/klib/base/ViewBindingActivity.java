package com.lazyee.klib.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.viewbinding.ViewBinding;
import com.lazyee.klib.util.ViewBindingUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ViewBindingActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\fH\u0016J\u0012\u0010\u000e\u001a\u00020\f2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014R\u001c\u0010\u0005\u001a\u00028\u0000X\u0086.¢\u0006\u0010\n\u0002\u0010\n\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u0011"}, d2 = {"Lcom/lazyee/klib/base/ViewBindingActivity;", "VB", "Landroidx/viewbinding/ViewBinding;", "Lcom/lazyee/klib/base/BaseActivity;", "()V", "mViewBinding", "getMViewBinding", "()Landroidx/viewbinding/ViewBinding;", "setMViewBinding", "(Landroidx/viewbinding/ViewBinding;)V", "Landroidx/viewbinding/ViewBinding;", "initData", "", "initView", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "library_release"}, k = 1, mv = {1, 4, 2})
public class ViewBindingActivity<VB extends ViewBinding> extends BaseActivity {
    public VB mViewBinding;

    public void initData() {
    }

    public void initView() {
    }

    public final VB getMViewBinding() {
        VB vb = this.mViewBinding;
        if (vb == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mViewBinding");
        }
        return vb;
    }

    public final void setMViewBinding(VB vb) {
        Intrinsics.checkNotNullParameter(vb, "<set-?>");
        this.mViewBinding = vb;
    }

    @Override // com.lazyee.klib.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewBindingUtils viewBindingUtils = ViewBindingUtils.INSTANCE;
        Class<?> cls = getClass();
        LayoutInflater layoutInflater = getLayoutInflater();
        Intrinsics.checkNotNullExpressionValue(layoutInflater, "layoutInflater");
        VB vb = (VB) viewBindingUtils.getViewBinding(cls, layoutInflater);
        this.mViewBinding = vb;
        if (vb == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mViewBinding");
        }
        setContentView(vb.getRoot());
        initView();
    }
}
