package com.shangxian.pinkink.manager;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.lazyee.klib.util.LogUtils;
import com.shangxian.pinkink.app.PinkInkApplication;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.constants.DeviceType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.UCollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;
import kotlin.text.UStringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: PinkInkBlueToothManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0002\u0010\u0013\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b\u001b\u0010\u001cJ\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u001e\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020\u0016ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b!\u0010\"J\u0006\u0010\r\u001a\u00020\u000eJ\b\u0010#\u001a\u00020\u001eH\u0002J)\u0010$\u001a\u00020\u001e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&H\u0087@ø\u0001\u0000¢\u0006\u0002\u0010(J\u0014\u0010$\u001a\u00020\u001e2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00160*J\u0019\u0010+\u001a\u00020\u001e2\u0006\u0010,\u001a\u00020-H\u0083@ø\u0001\u0000¢\u0006\u0002\u0010.J\"\u0010+\u001a\u00020\u001e2\f\u0010/\u001a\b\u0012\u0004\u0012\u0002000*H\u0083@ø\u0001\u0000ø\u0001\u0000¢\u0006\u0002\u00101R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R!\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t0\bj\b\u0012\u0004\u0012\u00020\t`\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0011R\u0010\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u00062"}, d2 = {"Lcom/shangxian/pinkink/manager/PinkInkBlueToothManager;", "", "()V", "bluetoothGatt", "Landroid/bluetooth/BluetoothGatt;", "characteristic", "Landroid/bluetooth/BluetoothGattCharacteristic;", "deviceList", "Ljava/util/ArrayList;", "Landroid/bluetooth/BluetoothDevice;", "Lkotlin/collections/ArrayList;", "getDeviceList", "()Ljava/util/ArrayList;", "isConnected", "", "mAutoConnectPinkInkHandler", "com/shangxian/pinkink/manager/PinkInkBlueToothManager$mAutoConnectPinkInkHandler$1", "Lcom/shangxian/pinkink/manager/PinkInkBlueToothManager$mAutoConnectPinkInkHandler$1;", "mBluetoothGattCallback", "com/shangxian/pinkink/manager/PinkInkBlueToothManager$mBluetoothGattCallback$1", "Lcom/shangxian/pinkink/manager/PinkInkBlueToothManager$mBluetoothGattCallback$1;", "macAddress", "", "bitmapConvertByteArray", "Lkotlin/UByteArray;", "bitmap", "Landroid/graphics/Bitmap;", "bitmapConvertByteArray-NTtOWj4", "(Landroid/graphics/Bitmap;)[B", "connect", "", "hexStringToUByteArray", "hexString", "hexStringToUByteArray-NTtOWj4", "(Ljava/lang/String;)[B", "sendGetVersionInfoCmd", "sendImage", "currentBitmapIndex", "", "totalBitmapCount", "(Landroid/graphics/Bitmap;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "imageFilePathList", "", "writeDataToDevice", "byteArray", "", "([BLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "bytes", "Lkotlin/UByte;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class PinkInkBlueToothManager {
    private static BluetoothGatt bluetoothGatt;
    private static BluetoothGattCharacteristic characteristic;
    private static boolean isConnected;
    private static PinkInkBlueToothManager$mAutoConnectPinkInkHandler$1 mAutoConnectPinkInkHandler;
    private static final PinkInkBlueToothManager$mBluetoothGattCallback$1 mBluetoothGattCallback;
    public static final PinkInkBlueToothManager INSTANCE = new PinkInkBlueToothManager();
    private static final ArrayList<BluetoothDevice> deviceList = new ArrayList<>();
    private static String macAddress = "";

    /* JADX INFO: renamed from: com.shangxian.pinkink.manager.PinkInkBlueToothManager$sendImage$2, reason: invalid class name */
    /* JADX INFO: compiled from: PinkInkBlueToothManager.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.manager.PinkInkBlueToothManager", f = "PinkInkBlueToothManager.kt", i = {0, 0, 0, 0, 0}, l = {295}, m = "sendImage", n = {"currentSendImageCount", "it", "currentBitmapIndex", "totalBitmapCount", "totalCount"}, s = {"L$0", "L$2", "I$0", "I$1", "I$2"})
    static final class AnonymousClass2 extends ContinuationImpl {
        int I$0;
        int I$1;
        int I$2;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PinkInkBlueToothManager.this.sendImage(null, 0, 0, this);
        }
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.manager.PinkInkBlueToothManager$writeDataToDevice$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PinkInkBlueToothManager.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.manager.PinkInkBlueToothManager", f = "PinkInkBlueToothManager.kt", i = {}, l = {321}, m = "writeDataToDevice", n = {}, s = {})
    static final class C00652 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        C00652(Continuation<? super C00652> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PinkInkBlueToothManager.this.writeDataToDevice((byte[]) null, this);
        }
    }

    private PinkInkBlueToothManager() {
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.shangxian.pinkink.manager.PinkInkBlueToothManager$mAutoConnectPinkInkHandler$1] */
    static {
        final Looper mainLooper = Looper.getMainLooper();
        mAutoConnectPinkInkHandler = new Handler(mainLooper) { // from class: com.shangxian.pinkink.manager.PinkInkBlueToothManager$mAutoConnectPinkInkHandler$1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                super.handleMessage(msg);
                if (AppConfig.INSTANCE.isNfcDevice()) {
                    return;
                }
                PinkInkBlueToothManager.INSTANCE.connect(PinkInkBlueToothManager.macAddress);
            }
        };
        mBluetoothGattCallback = new PinkInkBlueToothManager$mBluetoothGattCallback$1();
    }

    public final ArrayList<BluetoothDevice> getDeviceList() {
        return deviceList;
    }

    public final void connect(String macAddress2) {
        Intrinsics.checkNotNullParameter(macAddress2, "macAddress");
        if (TextUtils.isEmpty(macAddress2) || isConnected) {
            return;
        }
        BluetoothGatt bluetoothGatt2 = bluetoothGatt;
        if (bluetoothGatt2 != null) {
            bluetoothGatt2.disconnect();
        }
        macAddress = macAddress2;
        LogUtils.INSTANCE.e("[PinkInkBlueToothManager]", Intrinsics.stringPlus("device address:", macAddress2));
        Context applicationContext = PinkInkApplication.INSTANCE.getApplicationContext();
        Object systemService = applicationContext.getSystemService(DeviceType.TYPE_BLUE_TOOTH);
        Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.bluetooth.BluetoothManager");
        BluetoothDevice remoteDevice = ((BluetoothManager) systemService).getAdapter().getRemoteDevice(macAddress2);
        LogUtils.INSTANCE.e("[PinkInkBlueToothManager]", Intrinsics.stringPlus("blueToothDevice address:", remoteDevice.getAddress()));
        if (Build.VERSION.SDK_INT >= 23) {
            remoteDevice.connectGatt(applicationContext, true, mBluetoothGattCallback, 2);
        } else {
            remoteDevice.connectGatt(applicationContext, false, mBluetoothGattCallback);
        }
    }

    public final boolean isConnected() {
        return isConnected;
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.manager.PinkInkBlueToothManager$sendGetVersionInfoCmd$1, reason: invalid class name */
    /* JADX INFO: compiled from: PinkInkBlueToothManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.manager.PinkInkBlueToothManager$sendGetVersionInfoCmd$1", f = "PinkInkBlueToothManager.kt", i = {}, l = {224}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                byte[] bArrM109hexStringToUByteArrayNTtOWj4 = PinkInkBlueToothManager.INSTANCE.m109hexStringToUByteArrayNTtOWj4("6a1103000000");
                byte[] bArrCopyOf = Arrays.copyOf(bArrM109hexStringToUByteArrayNTtOWj4, bArrM109hexStringToUByteArrayNTtOWj4.length);
                Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(this, size)");
                this.label = 1;
                if (PinkInkBlueToothManager.INSTANCE.writeDataToDevice(bArrCopyOf, this) == coroutine_suspended) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendGetVersionInfoCmd() {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass1(null), 3, null);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.manager.PinkInkBlueToothManager$sendImage$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: PinkInkBlueToothManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.shangxian.pinkink.manager.PinkInkBlueToothManager$sendImage$1", f = "PinkInkBlueToothManager.kt", i = {0, 0, 0}, l = {235}, m = "invokeSuspend", n = {"bitmap", "newBitmap", "index$iv"}, s = {"L$2", "L$3", "I$0"})
    static final class C00641 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<String> $imageFilePathList;
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00641(List<String> list, Continuation<? super C00641> continuation) {
            super(2, continuation);
            this.$imageFilePathList = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C00641(this.$imageFilePathList, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00641) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0041  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x009b  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0090 -> B:18:0x0093). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r12) throws java.lang.Throwable {
            /*
                r11 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r11.label
                r2 = 1
                if (r1 == 0) goto L2b
                if (r1 != r2) goto L23
                int r1 = r11.I$0
                java.lang.Object r3 = r11.L$3
                android.graphics.Bitmap r3 = (android.graphics.Bitmap) r3
                java.lang.Object r4 = r11.L$2
                android.graphics.Bitmap r4 = (android.graphics.Bitmap) r4
                java.lang.Object r5 = r11.L$1
                java.util.Iterator r5 = (java.util.Iterator) r5
                java.lang.Object r6 = r11.L$0
                java.util.List r6 = (java.util.List) r6
                kotlin.ResultKt.throwOnFailure(r12)
                r12 = r11
                goto L93
            L23:
                java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r12.<init>(r0)
                throw r12
            L2b:
                kotlin.ResultKt.throwOnFailure(r12)
                java.util.List<java.lang.String> r12 = r11.$imageFilePathList
                r1 = r12
                java.lang.Iterable r1 = (java.lang.Iterable) r1
                r3 = 0
                java.util.Iterator r1 = r1.iterator()
                r6 = r12
                r5 = r1
                r12 = r11
            L3b:
                boolean r1 = r5.hasNext()
                if (r1 == 0) goto L9b
                java.lang.Object r1 = r5.next()
                int r4 = r3 + 1
                if (r3 >= 0) goto L4c
                kotlin.collections.CollectionsKt.throwIndexOverflow()
            L4c:
                java.lang.String r1 = (java.lang.String) r1
                android.graphics.Bitmap r1 = android.graphics.BitmapFactory.decodeFile(r1)
                com.shangxian.pinkink.constants.AppConfig r3 = com.shangxian.pinkink.constants.AppConfig.INSTANCE
                com.shangxian.pinkink.constants.InkScreenSize r3 = r3.getCurrentInkScreenSize()
                int r7 = r3.getWidth()
                int r3 = r3.getHeight()
                android.graphics.Bitmap r3 = android.graphics.Bitmap.createScaledBitmap(r1, r7, r3, r2)
                com.shangxian.pinkink.util.InkScreenImageDitherUtils r7 = com.shangxian.pinkink.util.InkScreenImageDitherUtils.INSTANCE
                java.lang.String r8 = "newBitmap"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r8)
                com.shangxian.pinkink.util.InkPalette r8 = com.shangxian.pinkink.util.InkPalette.INSTANCE
                com.shangxian.pinkink.util.PixelColor[] r8 = r8.getBWRY()
                com.shangxian.pinkink.util.InkFilterMode r9 = com.shangxian.pinkink.util.InkFilterMode.FLOYD_STEINBERG
                android.graphics.Bitmap r7 = r7.ditheringCanvasByPalette(r3, r8, r9)
                com.shangxian.pinkink.manager.PinkInkBlueToothManager r8 = com.shangxian.pinkink.manager.PinkInkBlueToothManager.INSTANCE
                int r9 = r6.size()
                r12.L$0 = r6
                r12.L$1 = r5
                r12.L$2 = r1
                r12.L$3 = r3
                r12.I$0 = r4
                r12.label = r2
                java.lang.Object r7 = r8.sendImage(r7, r4, r9, r12)
                if (r7 != r0) goto L90
                return r0
            L90:
                r10 = r4
                r4 = r1
                r1 = r10
            L93:
                r4.recycle()
                r3.recycle()
                r3 = r1
                goto L3b
            L9b:
                kotlin.Unit r12 = kotlin.Unit.INSTANCE
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.shangxian.pinkink.manager.PinkInkBlueToothManager.C00641.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public final void sendImage(List<String> imageFilePathList) {
        Intrinsics.checkNotNullParameter(imageFilePathList, "imageFilePathList");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C00641(imageFilePathList, null), 3, null);
    }

    /* JADX WARN: Path cross not found for [B:29:0x0195, B:42:0x021c], limit reached: 63 */
    /* JADX WARN: Removed duplicated region for block: B:46:0x022f A[LOOP:3: B:44:0x0229->B:46:0x022f, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x024f  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001a  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:51:0x0276 -> B:53:0x0279). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object sendImage(android.graphics.Bitmap r22, int r23, int r24, kotlin.coroutines.Continuation<? super kotlin.Unit> r25) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 670
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shangxian.pinkink.manager.PinkInkBlueToothManager.sendImage(android.graphics.Bitmap, int, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object writeDataToDevice(List<UByte> list, Continuation<? super Unit> continuation) throws Throwable {
        byte[] uByteArray = UCollectionsKt.toUByteArray(list);
        byte[] bArrCopyOf = Arrays.copyOf(uByteArray, uByteArray.length);
        Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(this, size)");
        Object objWriteDataToDevice = writeDataToDevice(bArrCopyOf, continuation);
        return objWriteDataToDevice == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWriteDataToDevice : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object writeDataToDevice(byte[] r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) throws java.lang.Throwable {
        /*
            r7 = this;
            boolean r0 = r9 instanceof com.shangxian.pinkink.manager.PinkInkBlueToothManager.C00652
            if (r0 == 0) goto L14
            r0 = r9
            com.shangxian.pinkink.manager.PinkInkBlueToothManager$writeDataToDevice$2 r0 = (com.shangxian.pinkink.manager.PinkInkBlueToothManager.C00652) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L19
        L14:
            com.shangxian.pinkink.manager.PinkInkBlueToothManager$writeDataToDevice$2 r0 = new com.shangxian.pinkink.manager.PinkInkBlueToothManager$writeDataToDevice$2
            r0.<init>(r9)
        L19:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L34
            if (r2 != r3) goto L2c
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Exception -> L2a
            goto L96
        L2a:
            r8 = move-exception
            goto L93
        L2c:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L34:
            kotlin.ResultKt.throwOnFailure(r9)
            android.bluetooth.BluetoothGattCharacteristic r9 = com.shangxian.pinkink.manager.PinkInkBlueToothManager.characteristic     // Catch: java.lang.Exception -> L2a
            if (r9 != 0) goto L3e
            kotlin.Unit r8 = kotlin.Unit.INSTANCE     // Catch: java.lang.Exception -> L2a
            return r8
        L3e:
            if (r9 != 0) goto L41
            goto L44
        L41:
            r9.setValue(r8)     // Catch: java.lang.Exception -> L2a
        L44:
            android.bluetooth.BluetoothGatt r9 = com.shangxian.pinkink.manager.PinkInkBlueToothManager.bluetoothGatt     // Catch: java.lang.Exception -> L2a
            if (r9 != 0) goto L4a
            r9 = 0
            goto L54
        L4a:
            android.bluetooth.BluetoothGattCharacteristic r2 = com.shangxian.pinkink.manager.PinkInkBlueToothManager.characteristic     // Catch: java.lang.Exception -> L2a
            boolean r9 = r9.writeCharacteristic(r2)     // Catch: java.lang.Exception -> L2a
            java.lang.Boolean r9 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r9)     // Catch: java.lang.Exception -> L2a
        L54:
            com.lazyee.klib.util.LogUtils r2 = com.lazyee.klib.util.LogUtils.INSTANCE     // Catch: java.lang.Exception -> L2a
            java.lang.String r4 = "[PinkInkBlueToothManager]"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L2a
            r5.<init>()     // Catch: java.lang.Exception -> L2a
            java.lang.String r6 = "发送"
            r5.append(r6)     // Catch: java.lang.Exception -> L2a
            int r8 = r8.length     // Catch: java.lang.Exception -> L2a
            r5.append(r8)     // Catch: java.lang.Exception -> L2a
            java.lang.String r8 = "长度的数据,发送"
            r5.append(r8)     // Catch: java.lang.Exception -> L2a
            java.lang.Boolean r8 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)     // Catch: java.lang.Exception -> L2a
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r8)     // Catch: java.lang.Exception -> L2a
            if (r8 == 0) goto L7b
            java.lang.String r8 = "成功"
            goto L7e
        L7b:
            java.lang.String r8 = "失败"
        L7e:
            r5.append(r8)     // Catch: java.lang.Exception -> L2a
            java.lang.String r8 = r5.toString()     // Catch: java.lang.Exception -> L2a
            r2.e(r4, r8)     // Catch: java.lang.Exception -> L2a
            r8 = 120(0x78, double:5.93E-322)
            r0.label = r3     // Catch: java.lang.Exception -> L2a
            java.lang.Object r8 = kotlinx.coroutines.DelayKt.delay(r8, r0)     // Catch: java.lang.Exception -> L2a
            if (r8 != r1) goto L96
            return r1
        L93:
            r8.printStackTrace()
        L96:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shangxian.pinkink.manager.PinkInkBlueToothManager.writeDataToDevice(byte[], kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: renamed from: hexStringToUByteArray-NTtOWj4, reason: not valid java name */
    public final byte[] m109hexStringToUByteArrayNTtOWj4(String hexString) {
        Intrinsics.checkNotNullParameter(hexString, "hexString");
        char[] charArray = hexString.toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
        byte[] bArrM455constructorimpl = UByteArray.m455constructorimpl(charArray.length / 2);
        IntRange indices = ArraysKt.getIndices(bArrM455constructorimpl);
        int first = indices.getFirst();
        int last = indices.getLast();
        if (first <= last) {
            while (true) {
                int i = first + 1;
                int i2 = first * 2;
                UByteArray.m466setVurrAj0(bArrM455constructorimpl, first, UByte.m404constructorimpl((byte) (Character.digit(charArray[i2 + 1], 16) | (Character.digit(charArray[i2], 16) << 4))));
                if (first == last) {
                    break;
                }
                first = i;
            }
        }
        return bArrM455constructorimpl;
    }

    /* JADX INFO: renamed from: bitmapConvertByteArray-NTtOWj4, reason: not valid java name */
    private final byte[] m108bitmapConvertByteArrayNTtOWj4(Bitmap bitmap) {
        ArrayList arrayList = new ArrayList();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (int i2 = 0; i2 < height; i2++) {
            for (int i3 = 0; i3 < width; i3++) {
                int pixel = bitmap.getPixel(i3, i2);
                if (pixel == -16777216) {
                    sb.append("00");
                } else if (pixel == -65536) {
                    sb.append("11");
                } else if (pixel == -256) {
                    sb.append("10");
                } else if (pixel == -1) {
                    sb.append("01");
                } else {
                    sb.append("01");
                }
                i++;
                if (i == 4) {
                    String string = sb.toString();
                    Intrinsics.checkNotNullExpressionValue(string, "pixelCombineByteStr.toString()");
                    arrayList.add(UByte.m398boximpl(UByte.m404constructorimpl((byte) UStringsKt.toUInt(string, 2))));
                    StringsKt.clear(sb);
                    i = 0;
                }
            }
        }
        return UCollectionsKt.toUByteArray(arrayList);
    }
}
