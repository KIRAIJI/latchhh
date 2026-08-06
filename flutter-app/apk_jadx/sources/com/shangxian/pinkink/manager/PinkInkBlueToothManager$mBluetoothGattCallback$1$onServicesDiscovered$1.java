package com.shangxian.pinkink.manager;

import android.bluetooth.BluetoothGatt;
import com.lazyee.klib.util.LogUtils;
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
import kotlinx.coroutines.DelayKt;

/* JADX INFO: compiled from: PinkInkBlueToothManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
@DebugMetadata(c = "com.shangxian.pinkink.manager.PinkInkBlueToothManager$mBluetoothGattCallback$1$onServicesDiscovered$1", f = "PinkInkBlueToothManager.kt", i = {0}, l = {166}, m = "invokeSuspend", n = {"tryCount"}, s = {"I$0"})
final class PinkInkBlueToothManager$mBluetoothGattCallback$1$onServicesDiscovered$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int I$0;
    int label;

    PinkInkBlueToothManager$mBluetoothGattCallback$1$onServicesDiscovered$1(Continuation<? super PinkInkBlueToothManager$mBluetoothGattCallback$1$onServicesDiscovered$1> continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new PinkInkBlueToothManager$mBluetoothGattCallback$1$onServicesDiscovered$1(continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((PinkInkBlueToothManager$mBluetoothGattCallback$1$onServicesDiscovered$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        int i;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            i = 0;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i3 = this.I$0;
            ResultKt.throwOnFailure(obj);
            i = i3;
        }
        while (i < 10 && !PinkInkBlueToothManager.isConnected) {
            BluetoothGatt bluetoothGatt = PinkInkBlueToothManager.bluetoothGatt;
            LogUtils.INSTANCE.e("[PinkInkBlueToothManager]", Intrinsics.stringPlus("requestMtu:", bluetoothGatt == null ? null : Boxing.boxBoolean(bluetoothGatt.requestMtu(230))));
            i++;
            this.I$0 = i;
            this.label = 1;
            if (DelayKt.delay(500L, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        }
        return Unit.INSTANCE;
    }
}
