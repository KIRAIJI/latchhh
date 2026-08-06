package com.shangxian.pinkink.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import com.lazyee.klib.base.ViewBindingDialog;
import com.lazyee.klib.extension.NumberExtensionsKt;
import com.lazyee.klib.extension.ViewExtensionsKt;
import com.lazyee.klib.extension.WindowExtensionsKt;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.databinding.DialogSyncImageBinding;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: SyncImageDialog.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014J\u0006\u0010\u0012\u001a\u00020\u000fJ&\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u0007R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006\u0018"}, d2 = {"Lcom/shangxian/pinkink/ui/dialog/SyncImageDialog;", "Lcom/lazyee/klib/base/ViewBindingDialog;", "Lcom/shangxian/pinkink/databinding/DialogSyncImageBinding;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "dp28", "", "minProgressWidth", "progressWidth", "getProgressWidth", "()I", "progressWidth$delegate", "Lkotlin/Lazy;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "reset", "updateSyncInfo", "currentSize", "totalSize", "currentBitmapIndex", "totalBitmapCount", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class SyncImageDialog extends ViewBindingDialog<DialogSyncImageBinding> {
    private final int dp28;
    private final int minProgressWidth;

    /* JADX INFO: renamed from: progressWidth$delegate, reason: from kotlin metadata */
    private final Lazy progressWidth;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SyncImageDialog(Context context) {
        super(context, R.style.Dialog_FontDownload);
        Intrinsics.checkNotNullParameter(context, "context");
        this.minProgressWidth = NumberExtensionsKt.dp2px(50);
        this.dp28 = NumberExtensionsKt.dp2px(28);
        this.progressWidth = LazyKt.lazy(new Function0<Integer>() { // from class: com.shangxian.pinkink.ui.dialog.SyncImageDialog$progressWidth$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Integer invoke() {
                DialogSyncImageBinding mViewBinding = this.this$0.getMViewBinding();
                Intrinsics.checkNotNull(mViewBinding);
                return Integer.valueOf(mViewBinding.llProgressBg.getMeasuredWidth() - this.this$0.minProgressWidth);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getProgressWidth() {
        return ((Number) this.progressWidth.getValue()).intValue();
    }

    @Override // com.lazyee.klib.base.ViewBindingDialog, android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            WindowExtensionsKt.setSize(window, -1, -2);
        }
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    public final void reset() {
        DialogSyncImageBinding mViewBinding = getMViewBinding();
        if (mViewBinding == null) {
            return;
        }
        LinearLayout llProgress = mViewBinding.llProgress;
        Intrinsics.checkNotNullExpressionValue(llProgress, "llProgress");
        ViewExtensionsKt.setSize(llProgress, Integer.valueOf(this.minProgressWidth), Integer.valueOf(this.dp28));
        mViewBinding.tvDownloadProgress.setText("0%");
        mViewBinding.tvIndicator.setText("");
    }

    public final void updateSyncInfo(int currentSize, int totalSize, int currentBitmapIndex, int totalBitmapCount) {
        DialogSyncImageBinding mViewBinding = getMViewBinding();
        if (mViewBinding == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getMain()), null, null, new SyncImageDialog$updateSyncInfo$1$1(currentSize, totalSize, this, mViewBinding, currentBitmapIndex, totalBitmapCount, null), 3, null);
    }
}
