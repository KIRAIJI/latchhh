package com.shangxian.pinkink.ui.device;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.view.View;
import com.lazyee.klib.extension.ContextExtensionsKt;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.databinding.ActivityDeviceScanBinding;
import com.shangxian.pinkink.manager.PinkInkBlueToothManager;
import com.shangxian.pinkink.manager.PinkInkScanner;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DeviceScanActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u000eH\u0014J \u0010\u0010\u001a\u00020\u000e2\u0016\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u00130\u0012j\b\u0012\u0004\u0012\u00020\u0013`\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u000eH\u0016J\b\u0010\u0016\u001a\u00020\u000eH\u0002R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\n¨\u0006\u0017"}, d2 = {"Lcom/shangxian/pinkink/ui/device/DeviceScanActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityDeviceScanBinding;", "Lcom/shangxian/pinkink/manager/PinkInkScanner$OnScanDeviceCallback;", "()V", "mBluetoothAdapter", "Landroid/bluetooth/BluetoothAdapter;", "mPinkInkScanner", "Lcom/shangxian/pinkink/manager/PinkInkScanner;", "getMPinkInkScanner", "()Lcom/shangxian/pinkink/manager/PinkInkScanner;", "mPinkInkScanner$delegate", "Lkotlin/Lazy;", "initView", "", "onDestroy", "onScanTargetDevice", "deviceList", "Ljava/util/ArrayList;", "Landroid/bluetooth/BluetoothDevice;", "Lkotlin/collections/ArrayList;", "onScanTimeout", "scanBlueToothDevice", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class DeviceScanActivity extends BaseActivity<ActivityDeviceScanBinding> implements PinkInkScanner.OnScanDeviceCallback {
    private BluetoothAdapter mBluetoothAdapter;

    /* JADX INFO: renamed from: mPinkInkScanner$delegate, reason: from kotlin metadata */
    private final Lazy mPinkInkScanner = LazyKt.lazy(new Function0<PinkInkScanner>() { // from class: com.shangxian.pinkink.ui.device.DeviceScanActivity$mPinkInkScanner$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final PinkInkScanner invoke() {
            return new PinkInkScanner();
        }
    });

    private final PinkInkScanner getMPinkInkScanner() {
        return (PinkInkScanner) this.mPinkInkScanner.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        ((ActivityDeviceScanBinding) getMViewBinding()).llCancel.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.device.DeviceScanActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DeviceScanActivity.m155initView$lambda1$lambda0(this.f$0, view);
            }
        });
        scanBlueToothDevice();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-1$lambda-0, reason: not valid java name */
    public static final void m155initView$lambda1$lambda0(DeviceScanActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final void scanBlueToothDevice() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBluetoothAdapter = defaultAdapter;
        if (defaultAdapter == null) {
            String string = getString(R.string.toast_current_device_not_support_bluetooth);
            Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.toast…ce_not_support_bluetooth)");
            ContextExtensionsKt.toastShort(this, string);
            return;
        }
        Intrinsics.checkNotNull(defaultAdapter);
        if (!defaultAdapter.isEnabled()) {
            startActivity(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"));
            BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
            if (bluetoothAdapter != null) {
                bluetoothAdapter.enable();
            }
        }
        getMPinkInkScanner().startScanDevice(this);
    }

    @Override // com.lazyee.klib.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        getMPinkInkScanner().stopScanDevice();
    }

    @Override // com.shangxian.pinkink.manager.PinkInkScanner.OnScanDeviceCallback
    public void onScanTargetDevice(ArrayList<BluetoothDevice> deviceList) throws Exception {
        Intrinsics.checkNotNullParameter(deviceList, "deviceList");
        PinkInkBlueToothManager.INSTANCE.getDeviceList().clear();
        PinkInkBlueToothManager.INSTANCE.getDeviceList().addAll(deviceList);
        ContextExtensionsKt.goto$default(this, DeviceListActivity.class, null, null, null, null, 30, null);
        finish();
    }

    @Override // com.shangxian.pinkink.manager.PinkInkScanner.OnScanDeviceCallback
    public void onScanTimeout() {
        String string = getString(R.string.toast_bluetooth_device_scan_timeout);
        Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.toast…ooth_device_scan_timeout)");
        ContextExtensionsKt.toastShort(this, string);
    }
}
