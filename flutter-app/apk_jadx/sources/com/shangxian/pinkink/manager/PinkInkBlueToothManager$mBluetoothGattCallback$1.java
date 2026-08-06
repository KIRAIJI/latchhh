package com.shangxian.pinkink.manager;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.lazyee.klib.extension.ByteExtensionsKt;
import com.lazyee.klib.util.LogUtils;
import com.shangxian.pinkink.bean.EInkDeviceBean;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.event.BlueToothConnectStateEvent;
import com.shangxian.pinkink.mvvm.repository.FirmwareRepository;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: compiled from: PinkInkBlueToothManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J$\u0010\b\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\t\u001a\u00020\nH\u0016J$\u0010\u000b\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\t\u001a\u00020\nH\u0016J\"\u0010\f\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\nH\u0017J\"\u0010\u000e\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001a\u0010\u0010\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\t\u001a\u00020\nH\u0017¨\u0006\u0011"}, d2 = {"com/shangxian/pinkink/manager/PinkInkBlueToothManager$mBluetoothGattCallback$1", "Landroid/bluetooth/BluetoothGattCallback;", "onCharacteristicChanged", "", "gatt", "Landroid/bluetooth/BluetoothGatt;", "characteristic", "Landroid/bluetooth/BluetoothGattCharacteristic;", "onCharacteristicRead", NotificationCompat.CATEGORY_STATUS, "", "onCharacteristicWrite", "onConnectionStateChange", "newState", "onMtuChanged", "mtu", "onServicesDiscovered", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class PinkInkBlueToothManager$mBluetoothGattCallback$1 extends BluetoothGattCallback {
    PinkInkBlueToothManager$mBluetoothGattCallback$1() {
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        LogUtils.INSTANCE.e("[PinkInkBlueToothManager]", Intrinsics.stringPlus("newState:", Integer.valueOf(newState)));
        if (newState != 2) {
            EventBus.getDefault().post(new BlueToothConnectStateEvent(newState));
        }
        if (newState == 0) {
            LogUtils.INSTANCE.e("[PinkInkBlueToothManager]", "STATE_DISCONNECTED");
            PinkInkBlueToothManager pinkInkBlueToothManager = PinkInkBlueToothManager.INSTANCE;
            PinkInkBlueToothManager.isConnected = false;
            BluetoothGatt bluetoothGatt = PinkInkBlueToothManager.bluetoothGatt;
            if (bluetoothGatt != null) {
                bluetoothGatt.disconnect();
            }
            PinkInkBlueToothManager pinkInkBlueToothManager2 = PinkInkBlueToothManager.INSTANCE;
            PinkInkBlueToothManager.bluetoothGatt = null;
            FirmwareRepository.INSTANCE.clearVersion();
            PinkInkBlueToothManager.mAutoConnectPinkInkHandler.sendEmptyMessageDelayed(0, 15000L);
            return;
        }
        if (newState == 1) {
            LogUtils.INSTANCE.e("[PinkInkBlueToothManager]", "STATE_CONNECTING");
            return;
        }
        if (newState != 2) {
            if (newState != 3) {
                return;
            }
            LogUtils.INSTANCE.e("[PinkInkBlueToothManager]", "STATE_DISCONNECTING");
            PinkInkBlueToothManager pinkInkBlueToothManager3 = PinkInkBlueToothManager.INSTANCE;
            PinkInkBlueToothManager.isConnected = false;
            return;
        }
        AppConfig.INSTANCE.setMacAddress(PinkInkBlueToothManager.macAddress);
        LogUtils.INSTANCE.e("[PinkInkBlueToothManager]", "STATE_CONNECTED");
        PinkInkBlueToothManager pinkInkBlueToothManager4 = PinkInkBlueToothManager.INSTANCE;
        PinkInkBlueToothManager.bluetoothGatt = gatt;
        BluetoothGatt bluetoothGatt2 = PinkInkBlueToothManager.bluetoothGatt;
        if (bluetoothGatt2 == null) {
            return;
        }
        bluetoothGatt2.discoverServices();
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
        super.onMtuChanged(gatt, mtu, status);
        LogUtils.INSTANCE.e("[PinkInkBlueToothManager]", "onMtuChanged:" + mtu + ";status:" + status);
        EventBus.getDefault().post(new BlueToothConnectStateEvent(2));
        PinkInkBlueToothManager pinkInkBlueToothManager = PinkInkBlueToothManager.INSTANCE;
        PinkInkBlueToothManager.isConnected = true;
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        List<BluetoothGattService> services;
        BluetoothDevice device;
        LogUtils.INSTANCE.e("[PinkInkBlueToothManager]", "onServicesDiscovered");
        LogUtils logUtils = LogUtils.INSTANCE;
        BluetoothGatt bluetoothGatt = PinkInkBlueToothManager.bluetoothGatt;
        logUtils.e("[PinkInkBlueToothManager]", Intrinsics.stringPlus("bluetoothGatt.services:", (bluetoothGatt == null || (services = bluetoothGatt.getServices()) == null) ? null : Integer.valueOf(services.size())));
        UUID uuidFromString = UUID.fromString("79223401-1a11-21e1-8300-0940a1146603");
        UUID uuidFromString2 = UUID.fromString("79223402-1a11-21e1-8300-0940a1146603");
        UUID uuidFromString3 = UUID.fromString("79223403-1a11-21e1-8300-0940a1146603");
        BluetoothGatt bluetoothGatt2 = PinkInkBlueToothManager.bluetoothGatt;
        BluetoothGattService service = bluetoothGatt2 == null ? null : bluetoothGatt2.getService(uuidFromString);
        PinkInkBlueToothManager pinkInkBlueToothManager = PinkInkBlueToothManager.INSTANCE;
        PinkInkBlueToothManager.characteristic = service == null ? null : service.getCharacteristic(uuidFromString2);
        BluetoothGattCharacteristic characteristic = service == null ? null : service.getCharacteristic(uuidFromString3);
        BluetoothGattDescriptor descriptor = characteristic == null ? null : characteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
        LogUtils.INSTANCE.e("[PinkInkBlueToothManager]", Intrinsics.stringPlus("descriptor == null:", Boolean.valueOf(descriptor == null)));
        if (descriptor != null) {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            BluetoothGatt bluetoothGatt3 = PinkInkBlueToothManager.bluetoothGatt;
            if (bluetoothGatt3 != null) {
                bluetoothGatt3.writeDescriptor(descriptor);
            }
            BluetoothGatt bluetoothGatt4 = PinkInkBlueToothManager.bluetoothGatt;
            if (bluetoothGatt4 != null) {
                bluetoothGatt4.setCharacteristicNotification(characteristic, true);
            }
        }
        if (PinkInkBlueToothManager.characteristic != null && Build.VERSION.SDK_INT >= 21) {
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new PinkInkBlueToothManager$mBluetoothGattCallback$1$onServicesDiscovered$1(null), 3, null);
        }
        BluetoothGatt bluetoothGatt5 = PinkInkBlueToothManager.bluetoothGatt;
        if (bluetoothGatt5 != null && (device = bluetoothGatt5.getDevice()) != null) {
            AppConfig appConfig = AppConfig.INSTANCE;
            String address = device.getAddress();
            Intrinsics.checkNotNullExpressionValue(address, "address");
            String name = device.getName();
            Intrinsics.checkNotNullExpressionValue(name, "name");
            appConfig.saveEInkDevice(new EInkDeviceBean(address, name, 0, 4, null));
        }
        LogUtils.INSTANCE.e("[PinkInkBlueToothManager]", Intrinsics.stringPlus("characteristic == null:", Boolean.valueOf(PinkInkBlueToothManager.characteristic == null)));
        new Thread(new Runnable() { // from class: com.shangxian.pinkink.manager.PinkInkBlueToothManager$mBluetoothGattCallback$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws InterruptedException {
                PinkInkBlueToothManager$mBluetoothGattCallback$1.m110onServicesDiscovered$lambda1();
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onServicesDiscovered$lambda-1, reason: not valid java name */
    public static final void m110onServicesDiscovered$lambda1() throws InterruptedException {
        Thread.sleep(500L);
        PinkInkBlueToothManager.INSTANCE.sendGetVersionInfoCmd();
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);
        LogUtils logUtils = LogUtils.INSTANCE;
        StringBuilder sb = new StringBuilder();
        sb.append("onCharacteristicRead:");
        sb.append(status);
        sb.append(";value:");
        sb.append(characteristic == null ? null : characteristic.getValue());
        logUtils.e("[PinkInkBlueToothManager]", sb.toString());
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        String hexString = ByteExtensionsKt.toHexString(characteristic == null ? null : characteristic.getValue());
        LogUtils.INSTANCE.e("[PinkInkBlueToothManager]", Intrinsics.stringPlus("characteristic;value:", hexString));
        if (hexString != null && StringsKt.startsWith$default(hexString, "6B11", false, 2, (Object) null)) {
            FirmwareRepository.INSTANCE.setVersion(hexString);
        }
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
        if (status == 0) {
            LogUtils.INSTANCE.e("[PinkInkBlueToothManager]", "数据写入成功");
        } else {
            LogUtils.INSTANCE.e("[PinkInkBlueToothManager]", "数据写入失败");
        }
    }
}
