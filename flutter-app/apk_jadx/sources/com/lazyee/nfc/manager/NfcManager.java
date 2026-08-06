package com.lazyee.nfc.manager;

import android.content.Context;
import android.os.Bundle;
import com.lazyee.nfc.bean.DeviceInfo;
import com.lazyee.nfc.util.IsoDepUtils;

/* JADX INFO: loaded from: classes.dex */
public class NfcManager {
    private static DeviceInfo mDeviceInfo;

    public static DeviceInfo getDeviceInfo() {
        return mDeviceInfo;
    }

    public static void setDeviceInfo(DeviceInfo deviceInfo) {
        mDeviceInfo = deviceInfo;
    }

    public static void startIsoDep(final Context context, final Bundle bundle) {
        new Thread(new Runnable() { // from class: com.lazyee.nfc.manager.NfcManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                IsoDepUtils.startIsoDep(context, bundle);
            }
        }).start();
    }
}
