package com.shangxian.pinkink.ui.themes;

import android.graphics.Bitmap;
import com.shangxian.pinkink.manager.PinkInkBlueToothManager;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: TransThemesActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
@DebugMetadata(c = "com.shangxian.pinkink.ui.themes.TransThemesActivity$initView$1$10$1", f = "TransThemesActivity.kt", i = {}, l = {152}, m = "invokeSuspend", n = {}, s = {})
final class TransThemesActivity$initView$1$10$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ TransThemesActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    TransThemesActivity$initView$1$10$1(TransThemesActivity transThemesActivity, Continuation<? super TransThemesActivity$initView$1$10$1> continuation) {
        super(2, continuation);
        this.this$0 = transThemesActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new TransThemesActivity$initView$1$10$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((TransThemesActivity$initView$1$10$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            PinkInkBlueToothManager pinkInkBlueToothManager = PinkInkBlueToothManager.INSTANCE;
            Bitmap bitmap = this.this$0.effectBitmap;
            Intrinsics.checkNotNull(bitmap);
            this.label = 1;
            if (pinkInkBlueToothManager.sendImage(bitmap, 1, 1, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
