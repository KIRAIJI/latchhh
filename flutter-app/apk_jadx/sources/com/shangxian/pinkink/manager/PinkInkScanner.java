package com.shangxian.pinkink.manager;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.lazyee.klib.util.LogUtils;
import com.shangxian.pinkink.manager.PinkInkScanner;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanCallback;
import no.nordicsemi.android.support.v18.scanner.ScanResult;

/* JADX INFO: compiled from: PinkInkScanner.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000;\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004*\u0001\f\u0018\u00002\u00020\u0001:\u0001\u0014B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nJ\u0006\u0010\u0013\u001a\u00020\u0011R\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/shangxian/pinkink/manager/PinkInkScanner;", "", "()V", "bluetoothDeviceList", "Ljava/util/ArrayList;", "Landroid/bluetooth/BluetoothDevice;", "Lkotlin/collections/ArrayList;", "mHandler", "Landroid/os/Handler;", "mOnScanDeviceCallback", "Lcom/shangxian/pinkink/manager/PinkInkScanner$OnScanDeviceCallback;", "mScanCallback", "com/shangxian/pinkink/manager/PinkInkScanner$mScanCallback$1", "Lcom/shangxian/pinkink/manager/PinkInkScanner$mScanCallback$1;", "mSearchEInkTimeoutCallback", "Ljava/lang/Runnable;", "startScanDevice", "", "callback", "stopScanDevice", "OnScanDeviceCallback", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class PinkInkScanner {
    private final ArrayList<BluetoothDevice> bluetoothDeviceList = new ArrayList<>();
    private Handler mHandler;
    private OnScanDeviceCallback mOnScanDeviceCallback;
    private final PinkInkScanner$mScanCallback$1 mScanCallback;
    private final Runnable mSearchEInkTimeoutCallback;

    /* JADX INFO: compiled from: PinkInkScanner.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0016\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007H&J\b\u0010\b\u001a\u00020\u0003H&¨\u0006\t"}, d2 = {"Lcom/shangxian/pinkink/manager/PinkInkScanner$OnScanDeviceCallback;", "", "onScanTargetDevice", "", "deviceList", "Ljava/util/ArrayList;", "Landroid/bluetooth/BluetoothDevice;", "Lkotlin/collections/ArrayList;", "onScanTimeout", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public interface OnScanDeviceCallback {
        void onScanTargetDevice(ArrayList<BluetoothDevice> deviceList);

        void onScanTimeout();
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.shangxian.pinkink.manager.PinkInkScanner$mScanCallback$1] */
    public PinkInkScanner() {
        Runnable runnable = new Runnable() { // from class: com.shangxian.pinkink.manager.PinkInkScanner$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PinkInkScanner.m112mSearchEInkTimeoutCallback$lambda0(this.f$0);
            }
        };
        this.mSearchEInkTimeoutCallback = runnable;
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        handler.postDelayed(runnable, 10000L);
        this.mScanCallback = new ScanCallback() { // from class: com.shangxian.pinkink.manager.PinkInkScanner$mScanCallback$1
            @Override // no.nordicsemi.android.support.v18.scanner.ScanCallback
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
            }

            @Override // no.nordicsemi.android.support.v18.scanner.ScanCallback
            public void onScanResult(int callbackType, ScanResult result) {
                Intrinsics.checkNotNullParameter(result, "result");
                super.onScanResult(callbackType, result);
                String deviceName = result.getDevice().getName();
                if (TextUtils.isEmpty(deviceName)) {
                    return;
                }
                LogUtils.INSTANCE.e("[BlueToothUtils]", Intrinsics.stringPlus("搜索结果:", deviceName));
                Intrinsics.checkNotNullExpressionValue(deviceName, "deviceName");
                Object obj = null;
                if (StringsKt.startsWith$default(deviceName, "粉墨", false, 2, (Object) null) || StringsKt.startsWith$default(deviceName, "PPlusOTA", false, 2, (Object) null)) {
                    Iterator it = this.this$0.bluetoothDeviceList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Object next = it.next();
                        if (Intrinsics.areEqual(((BluetoothDevice) next).getName(), deviceName)) {
                            obj = next;
                            break;
                        }
                    }
                    if (obj == null) {
                        this.this$0.bluetoothDeviceList.add(result.getDevice());
                        Handler handler2 = this.this$0.mHandler;
                        if (handler2 != null) {
                            handler2.removeCallbacks(this.this$0.mSearchEInkTimeoutCallback);
                        }
                        PinkInkScanner.OnScanDeviceCallback onScanDeviceCallback = this.this$0.mOnScanDeviceCallback;
                        if (onScanDeviceCallback == null) {
                            return;
                        }
                        onScanDeviceCallback.onScanTargetDevice(this.this$0.bluetoothDeviceList);
                    }
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: mSearchEInkTimeoutCallback$lambda-0, reason: not valid java name */
    public static final void m112mSearchEInkTimeoutCallback$lambda0(PinkInkScanner this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnScanDeviceCallback onScanDeviceCallback = this$0.mOnScanDeviceCallback;
        if (onScanDeviceCallback == null) {
            return;
        }
        onScanDeviceCallback.onScanTimeout();
    }

    public final void startScanDevice(OnScanDeviceCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mOnScanDeviceCallback = callback;
        BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
        Intrinsics.checkNotNullExpressionValue(scanner, "getScanner()");
        scanner.startScan(this.mScanCallback);
    }

    public final void stopScanDevice() {
        BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
        Intrinsics.checkNotNullExpressionValue(scanner, "getScanner()");
        scanner.stopScan(this.mScanCallback);
    }
}
