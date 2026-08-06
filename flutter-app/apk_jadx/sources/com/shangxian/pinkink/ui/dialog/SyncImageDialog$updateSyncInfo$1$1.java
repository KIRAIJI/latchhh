package com.shangxian.pinkink.ui.dialog;

import android.widget.LinearLayout;
import android.widget.TextView;
import com.lazyee.klib.extension.ViewExtensionsKt;
import com.shangxian.pinkink.databinding.DialogSyncImageBinding;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: SyncImageDialog.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
@DebugMetadata(c = "com.shangxian.pinkink.ui.dialog.SyncImageDialog$updateSyncInfo$1$1", f = "SyncImageDialog.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class SyncImageDialog$updateSyncInfo$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ int $currentBitmapIndex;
    final /* synthetic */ int $currentSize;
    final /* synthetic */ DialogSyncImageBinding $this_run;
    final /* synthetic */ int $totalBitmapCount;
    final /* synthetic */ int $totalSize;
    int label;
    final /* synthetic */ SyncImageDialog this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    SyncImageDialog$updateSyncInfo$1$1(int i, int i2, SyncImageDialog syncImageDialog, DialogSyncImageBinding dialogSyncImageBinding, int i3, int i4, Continuation<? super SyncImageDialog$updateSyncInfo$1$1> continuation) {
        super(2, continuation);
        this.$currentSize = i;
        this.$totalSize = i2;
        this.this$0 = syncImageDialog;
        this.$this_run = dialogSyncImageBinding;
        this.$currentBitmapIndex = i3;
        this.$totalBitmapCount = i4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new SyncImageDialog$updateSyncInfo$1$1(this.$currentSize, this.$totalSize, this.this$0, this.$this_run, this.$currentBitmapIndex, this.$totalBitmapCount, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((SyncImageDialog$updateSyncInfo$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        float f = this.$currentSize / this.$totalSize;
        LinearLayout llProgress = this.$this_run.llProgress;
        Intrinsics.checkNotNullExpressionValue(llProgress, "llProgress");
        ViewExtensionsKt.setSize(llProgress, Boxing.boxInt(((int) (this.this$0.getProgressWidth() * f)) + this.this$0.minProgressWidth), Boxing.boxInt(this.this$0.dp28));
        TextView textView = this.$this_run.tvDownloadProgress;
        StringBuilder sb = new StringBuilder();
        sb.append((int) (f * 100));
        sb.append('%');
        textView.setText(sb.toString());
        TextView textView2 = this.$this_run.tvIndicator;
        StringBuilder sb2 = new StringBuilder();
        sb2.append('(');
        sb2.append(this.$currentBitmapIndex);
        sb2.append('/');
        sb2.append(this.$totalBitmapCount);
        sb2.append(')');
        textView2.setText(sb2.toString());
        return Unit.INSTANCE;
    }
}
