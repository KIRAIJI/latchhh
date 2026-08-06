package no.nordicsemi.android.support.v18.scanner;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat.ScanCallbackWrapper;

/* JADX INFO: loaded from: classes2.dex */
class ScanCallbackWrapperSet<W extends BluetoothLeScannerCompat.ScanCallbackWrapper> {
    private final Set<W> wrappers = new HashSet();

    ScanCallbackWrapperSet() {
    }

    public Set<W> values() {
        return this.wrappers;
    }

    boolean isEmpty() {
        return this.wrappers.isEmpty();
    }

    void add(W w) {
        this.wrappers.add(w);
    }

    boolean contains(ScanCallback scanCallback) {
        for (W w : this.wrappers) {
            if (w.scanCallback == scanCallback) {
                return true;
            }
            if ((w.scanCallback instanceof UserScanCallbackWrapper) && ((UserScanCallbackWrapper) w.scanCallback).get() == scanCallback) {
                return true;
            }
        }
        return false;
    }

    W get(ScanCallback scanCallback) {
        for (W w : this.wrappers) {
            if (w.scanCallback == scanCallback) {
                return w;
            }
            if ((w.scanCallback instanceof UserScanCallbackWrapper) && ((UserScanCallbackWrapper) w.scanCallback).get() == scanCallback) {
                return w;
            }
        }
        return null;
    }

    W remove(ScanCallback scanCallback) {
        for (W w : this.wrappers) {
            if (w.scanCallback == scanCallback) {
                return w;
            }
            if ((w.scanCallback instanceof UserScanCallbackWrapper) && ((UserScanCallbackWrapper) w.scanCallback).get() == scanCallback) {
                this.wrappers.remove(w);
                return w;
            }
        }
        cleanUp();
        return null;
    }

    private void cleanUp() {
        LinkedList linkedList = new LinkedList();
        for (W w : this.wrappers) {
            if ((w.scanCallback instanceof UserScanCallbackWrapper) && ((UserScanCallbackWrapper) w.scanCallback).isDead()) {
                linkedList.add(w);
            }
        }
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            this.wrappers.remove((BluetoothLeScannerCompat.ScanCallbackWrapper) it.next());
        }
    }
}
