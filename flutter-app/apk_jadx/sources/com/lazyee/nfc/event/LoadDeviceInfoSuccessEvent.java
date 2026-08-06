package com.lazyee.nfc.event;

/* JADX INFO: loaded from: classes.dex */
public class LoadDeviceInfoSuccessEvent {
    String info;

    public LoadDeviceInfoSuccessEvent(String str) {
        this.info = "";
        this.info = str;
    }

    public String getInfo() {
        return this.info;
    }
}
