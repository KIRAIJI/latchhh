package no.nordicsemi.android.support.v18.scanner;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;

/* JADX INFO: loaded from: classes2.dex */
class BluetoothLeScannerImplLollipop extends BluetoothLeScannerCompat {
    private final ScanCallbackWrapperSet<ScanCallbackWrapperLollipop> wrappers = new ScanCallbackWrapperSet<>();

    BluetoothLeScannerImplLollipop() {
    }

    @Override // no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat
    void startScanInternal(List<ScanFilter> list, ScanSettings scanSettings, ScanCallback scanCallback, Handler handler) {
        ScanCallbackWrapperLollipop scanCallbackWrapperLollipop;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothLeScanner bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner == null) {
            throw new IllegalStateException("BT le scanner not available");
        }
        boolean zIsOffloadedScanBatchingSupported = defaultAdapter.isOffloadedScanBatchingSupported();
        boolean zIsOffloadedFilteringSupported = defaultAdapter.isOffloadedFilteringSupported();
        synchronized (this.wrappers) {
            if (this.wrappers.contains(scanCallback)) {
                throw new IllegalArgumentException("scanner already started with given callback");
            }
            scanCallbackWrapperLollipop = new ScanCallbackWrapperLollipop(zIsOffloadedScanBatchingSupported, zIsOffloadedFilteringSupported, list, scanSettings, new UserScanCallbackWrapper(scanCallback), handler);
            this.wrappers.add(scanCallbackWrapperLollipop);
        }
        android.bluetooth.le.ScanSettings nativeScanSettings = toNativeScanSettings(defaultAdapter, scanSettings, false);
        ArrayList<android.bluetooth.le.ScanFilter> nativeScanFilters = null;
        if (!list.isEmpty() && zIsOffloadedFilteringSupported && scanSettings.getUseHardwareFilteringIfSupported()) {
            nativeScanFilters = toNativeScanFilters(list);
        }
        bluetoothLeScanner.startScan(nativeScanFilters, nativeScanSettings, scanCallbackWrapperLollipop.nativeCallback);
    }

    @Override // no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat
    void stopScanInternal(ScanCallback scanCallback) {
        ScanCallbackWrapperLollipop scanCallbackWrapperLollipop;
        BluetoothLeScanner bluetoothLeScanner;
        synchronized (this.wrappers) {
            scanCallbackWrapperLollipop = (ScanCallbackWrapperLollipop) this.wrappers.remove(scanCallback);
        }
        if (scanCallbackWrapperLollipop == null) {
            return;
        }
        scanCallbackWrapperLollipop.close();
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || (bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner()) == null) {
            return;
        }
        bluetoothLeScanner.stopScan(scanCallbackWrapperLollipop.nativeCallback);
    }

    @Override // no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat
    void startScanInternal(List<ScanFilter> list, ScanSettings scanSettings, Context context, PendingIntent pendingIntent, int i) {
        if (BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner() == null) {
            throw new IllegalStateException("BT le scanner not available");
        }
        Intent intent = new Intent(context, (Class<?>) ScannerService.class);
        intent.putParcelableArrayListExtra("no.nordicsemi.android.support.v18.EXTRA_FILTERS", new ArrayList<>(list));
        intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_SETTINGS", scanSettings);
        intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT", pendingIntent);
        intent.putExtra("no.nordicsemi.android.support.v18.REQUEST_CODE", i);
        intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_START", true);
        context.startService(intent);
    }

    @Override // no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat
    void stopScanInternal(Context context, PendingIntent pendingIntent, int i) {
        Intent intent = new Intent(context, (Class<?>) ScannerService.class);
        intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT", pendingIntent);
        intent.putExtra("no.nordicsemi.android.support.v18.REQUEST_CODE", i);
        intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_START", false);
        context.startService(intent);
    }

    @Override // no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat
    public void flushPendingScanResults(ScanCallback scanCallback) {
        ScanCallbackWrapperLollipop scanCallbackWrapperLollipop;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (scanCallback == null) {
            throw new IllegalArgumentException("callback cannot be null!");
        }
        synchronized (this.wrappers) {
            scanCallbackWrapperLollipop = (ScanCallbackWrapperLollipop) this.wrappers.get(scanCallback);
        }
        if (scanCallbackWrapperLollipop == null) {
            throw new IllegalArgumentException("callback not registered!");
        }
        ScanSettings scanSettings = scanCallbackWrapperLollipop.scanSettings;
        if (defaultAdapter.isOffloadedScanBatchingSupported() && scanSettings.getUseHardwareBatchingIfSupported()) {
            BluetoothLeScanner bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner();
            if (bluetoothLeScanner == null) {
                return;
            }
            bluetoothLeScanner.flushPendingScanResults(scanCallbackWrapperLollipop.nativeCallback);
            return;
        }
        scanCallbackWrapperLollipop.flushPendingScanResults();
    }

    android.bluetooth.le.ScanSettings toNativeScanSettings(BluetoothAdapter bluetoothAdapter, ScanSettings scanSettings, boolean z) {
        ScanSettings.Builder builder = new ScanSettings.Builder();
        if (z || (bluetoothAdapter.isOffloadedScanBatchingSupported() && scanSettings.getUseHardwareBatchingIfSupported())) {
            builder.setReportDelay(scanSettings.getReportDelayMillis());
        }
        if (scanSettings.getScanMode() != -1) {
            builder.setScanMode(scanSettings.getScanMode());
        } else {
            builder.setScanMode(0);
        }
        scanSettings.disableUseHardwareCallbackTypes();
        return builder.build();
    }

    ArrayList<android.bluetooth.le.ScanFilter> toNativeScanFilters(List<ScanFilter> list) {
        ArrayList<android.bluetooth.le.ScanFilter> arrayList = new ArrayList<>();
        Iterator<ScanFilter> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(toNativeScanFilter(it.next()));
        }
        return arrayList;
    }

    android.bluetooth.le.ScanFilter toNativeScanFilter(ScanFilter scanFilter) {
        ScanFilter.Builder builder = new ScanFilter.Builder();
        builder.setServiceUuid(scanFilter.getServiceUuid(), scanFilter.getServiceUuidMask()).setManufacturerData(scanFilter.getManufacturerId(), scanFilter.getManufacturerData(), scanFilter.getManufacturerDataMask());
        if (scanFilter.getDeviceAddress() != null) {
            builder.setDeviceAddress(scanFilter.getDeviceAddress());
        }
        if (scanFilter.getDeviceName() != null) {
            builder.setDeviceName(scanFilter.getDeviceName());
        }
        if (scanFilter.getServiceDataUuid() != null) {
            builder.setServiceData(scanFilter.getServiceDataUuid(), scanFilter.getServiceData(), scanFilter.getServiceDataMask());
        }
        return builder.build();
    }

    ScanResult fromNativeScanResult(android.bluetooth.le.ScanResult scanResult) {
        return new ScanResult(scanResult.getDevice(), ScanRecord.parseFromBytes(scanResult.getScanRecord() != null ? scanResult.getScanRecord().getBytes() : null), scanResult.getRssi(), scanResult.getTimestampNanos());
    }

    ArrayList<ScanResult> fromNativeScanResults(List<android.bluetooth.le.ScanResult> list) {
        ArrayList<ScanResult> arrayList = new ArrayList<>();
        Iterator<android.bluetooth.le.ScanResult> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(fromNativeScanResult(it.next()));
        }
        return arrayList;
    }

    static class ScanCallbackWrapperLollipop extends BluetoothLeScannerCompat.ScanCallbackWrapper {
        private final android.bluetooth.le.ScanCallback nativeCallback;

        private ScanCallbackWrapperLollipop(boolean z, boolean z2, List<ScanFilter> list, ScanSettings scanSettings, ScanCallback scanCallback, Handler handler) {
            super(z, z2, list, scanSettings, scanCallback, handler);
            this.nativeCallback = new AnonymousClass1();
        }

        /* JADX INFO: renamed from: no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerImplLollipop$ScanCallbackWrapperLollipop$1, reason: invalid class name */
        class AnonymousClass1 extends android.bluetooth.le.ScanCallback {
            private long lastBatchTimestamp;

            AnonymousClass1() {
            }

            @Override // android.bluetooth.le.ScanCallback
            public void onScanResult(final int i, final android.bluetooth.le.ScanResult scanResult) {
                ScanCallbackWrapperLollipop.this.handler.post(new Runnable() { // from class: no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerImplLollipop$ScanCallbackWrapperLollipop$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m1963x8522676a(scanResult, i);
                    }
                });
            }

            /* JADX INFO: renamed from: lambda$onScanResult$0$no-nordicsemi-android-support-v18-scanner-BluetoothLeScannerImplLollipop$ScanCallbackWrapperLollipop$1, reason: not valid java name */
            public /* synthetic */ void m1963x8522676a(android.bluetooth.le.ScanResult scanResult, int i) {
                ScanCallbackWrapperLollipop.this.handleScanResult(i, ((BluetoothLeScannerImplLollipop) BluetoothLeScannerCompat.getScanner()).fromNativeScanResult(scanResult));
            }

            @Override // android.bluetooth.le.ScanCallback
            public void onBatchScanResults(final List<android.bluetooth.le.ScanResult> list) {
                ScanCallbackWrapperLollipop.this.handler.post(new Runnable() { // from class: no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerImplLollipop$ScanCallbackWrapperLollipop$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m1961xfcd7ccc4(list);
                    }
                });
            }

            /* JADX INFO: renamed from: lambda$onBatchScanResults$1$no-nordicsemi-android-support-v18-scanner-BluetoothLeScannerImplLollipop$ScanCallbackWrapperLollipop$1, reason: not valid java name */
            public /* synthetic */ void m1961xfcd7ccc4(List list) {
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                if (this.lastBatchTimestamp > (jElapsedRealtime - ScanCallbackWrapperLollipop.this.scanSettings.getReportDelayMillis()) + 5) {
                    return;
                }
                this.lastBatchTimestamp = jElapsedRealtime;
                ScanCallbackWrapperLollipop.this.handleScanResults(((BluetoothLeScannerImplLollipop) BluetoothLeScannerCompat.getScanner()).fromNativeScanResults(list));
            }

            @Override // android.bluetooth.le.ScanCallback
            public void onScanFailed(final int i) {
                ScanCallbackWrapperLollipop.this.handler.post(new Runnable() { // from class: no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerImplLollipop$ScanCallbackWrapperLollipop$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m1962xd1f5b108(i);
                    }
                });
            }

            /* JADX INFO: renamed from: lambda$onScanFailed$2$no-nordicsemi-android-support-v18-scanner-BluetoothLeScannerImplLollipop$ScanCallbackWrapperLollipop$1, reason: not valid java name */
            public /* synthetic */ void m1962xd1f5b108(int i) {
                if (ScanCallbackWrapperLollipop.this.scanSettings.getUseHardwareCallbackTypesIfSupported() && ScanCallbackWrapperLollipop.this.scanSettings.getCallbackType() != 1) {
                    ScanCallbackWrapperLollipop.this.scanSettings.disableUseHardwareCallbackTypes();
                    BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
                    try {
                        scanner.stopScan(ScanCallbackWrapperLollipop.this.scanCallback);
                    } catch (Exception unused) {
                    }
                    try {
                        scanner.startScanInternal(ScanCallbackWrapperLollipop.this.filters, ScanCallbackWrapperLollipop.this.scanSettings, ScanCallbackWrapperLollipop.this.scanCallback, ScanCallbackWrapperLollipop.this.handler);
                        return;
                    } catch (Exception unused2) {
                        return;
                    }
                }
                ScanCallbackWrapperLollipop.this.handleScanError(i);
            }
        }
    }
}
