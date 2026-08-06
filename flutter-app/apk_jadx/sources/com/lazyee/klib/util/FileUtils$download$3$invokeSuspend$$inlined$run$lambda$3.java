package com.lazyee.klib.util;

import com.lazyee.klib.listener.OnFileDownloadListener;
import com.lazyee.klib.util.FileUtils;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: FileUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "com/lazyee/klib/util/FileUtils$download$3$5$1"}, k = 3, mv = {1, 4, 2})
@DebugMetadata(c = "com.lazyee.klib.util.FileUtils$download$3$5$1", f = "FileUtils.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class FileUtils$download$3$invokeSuspend$$inlined$run$lambda$3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Ref.IntRef $contentLength$inlined;
    final /* synthetic */ OnFileDownloadListener $this_run;
    final /* synthetic */ Ref.IntRef $total$inlined;
    int label;
    final /* synthetic */ FileUtils.AnonymousClass3 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    FileUtils$download$3$invokeSuspend$$inlined$run$lambda$3(OnFileDownloadListener onFileDownloadListener, Continuation continuation, FileUtils.AnonymousClass3 anonymousClass3, Ref.IntRef intRef, Ref.IntRef intRef2) {
        super(2, continuation);
        this.$this_run = onFileDownloadListener;
        this.this$0 = anonymousClass3;
        this.$total$inlined = intRef;
        this.$contentLength$inlined = intRef2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> completion) {
        Intrinsics.checkNotNullParameter(completion, "completion");
        return new FileUtils$download$3$invokeSuspend$$inlined$run$lambda$3(this.$this_run, completion, this.this$0, this.$total$inlined, this.$contentLength$inlined);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((FileUtils$download$3$invokeSuspend$$inlined$run$lambda$3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.$this_run.onDownloading(this.$total$inlined.element, this.$contentLength$inlined.element);
        return Unit.INSTANCE;
    }
}
