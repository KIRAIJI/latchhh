package com.shangxian.pinkink.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DeviceTypeChangedEvent.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lcom/shangxian/pinkink/event/DeviceTypeChangedEvent;", "", "newDeviceType", "", "(Ljava/lang/String;)V", "getNewDeviceType", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class DeviceTypeChangedEvent {
    private final String newDeviceType;

    public static /* synthetic */ DeviceTypeChangedEvent copy$default(DeviceTypeChangedEvent deviceTypeChangedEvent, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = deviceTypeChangedEvent.newDeviceType;
        }
        return deviceTypeChangedEvent.copy(str);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getNewDeviceType() {
        return this.newDeviceType;
    }

    public final DeviceTypeChangedEvent copy(String newDeviceType) {
        Intrinsics.checkNotNullParameter(newDeviceType, "newDeviceType");
        return new DeviceTypeChangedEvent(newDeviceType);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof DeviceTypeChangedEvent) && Intrinsics.areEqual(this.newDeviceType, ((DeviceTypeChangedEvent) other).newDeviceType);
    }

    public int hashCode() {
        return this.newDeviceType.hashCode();
    }

    public String toString() {
        return "DeviceTypeChangedEvent(newDeviceType=" + this.newDeviceType + ')';
    }

    public DeviceTypeChangedEvent(String newDeviceType) {
        Intrinsics.checkNotNullParameter(newDeviceType, "newDeviceType");
        this.newDeviceType = newDeviceType;
    }

    public final String getNewDeviceType() {
        return this.newDeviceType;
    }
}
