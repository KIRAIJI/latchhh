package no.nordicsemi.android.support.v18.scanner;

import java.lang.ref.WeakReference;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
class UserScanCallbackWrapper extends ScanCallback {
    private final WeakReference<ScanCallback> weakScanCallback;

    UserScanCallbackWrapper(ScanCallback scanCallback) {
        this.weakScanCallback = new WeakReference<>(scanCallback);
    }

    boolean isDead() {
        return this.weakScanCallback.get() == null;
    }

    ScanCallback get() {
        return this.weakScanCallback.get();
    }

    @Override // no.nordicsemi.android.support.v18.scanner.ScanCallback
    public void onScanResult(int i, ScanResult scanResult) {
        ScanCallback scanCallback = this.weakScanCallback.get();
        if (scanCallback != null) {
            scanCallback.onScanResult(i, scanResult);
        }
    }

    @Override // no.nordicsemi.android.support.v18.scanner.ScanCallback
    public void onBatchScanResults(List<ScanResult> list) {
        ScanCallback scanCallback = this.weakScanCallback.get();
        if (scanCallback != null) {
            scanCallback.onBatchScanResults(list);
        }
    }

    @Override // no.nordicsemi.android.support.v18.scanner.ScanCallback
    public void onScanFailed(int i) {
        ScanCallback scanCallback = this.weakScanCallback.get();
        if (scanCallback != null) {
            scanCallback.onScanFailed(i);
        }
    }
}
