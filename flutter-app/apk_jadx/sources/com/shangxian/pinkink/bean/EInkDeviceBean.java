package com.shangxian.pinkink.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EInkDeviceBean.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0006HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0016"}, d2 = {"Lcom/shangxian/pinkink/bean/EInkDeviceBean;", "", "mac", "", "name", "type", "", "(Ljava/lang/String;Ljava/lang/String;I)V", "getMac", "()Ljava/lang/String;", "getName", "getType", "()I", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class EInkDeviceBean {
    private final String mac;
    private final String name;
    private final int type;

    public static /* synthetic */ EInkDeviceBean copy$default(EInkDeviceBean eInkDeviceBean, String str, String str2, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = eInkDeviceBean.mac;
        }
        if ((i2 & 2) != 0) {
            str2 = eInkDeviceBean.name;
        }
        if ((i2 & 4) != 0) {
            i = eInkDeviceBean.type;
        }
        return eInkDeviceBean.copy(str, str2, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getMac() {
        return this.mac;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getType() {
        return this.type;
    }

    public final EInkDeviceBean copy(String mac, String name, int type) {
        Intrinsics.checkNotNullParameter(mac, "mac");
        Intrinsics.checkNotNullParameter(name, "name");
        return new EInkDeviceBean(mac, name, type);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EInkDeviceBean)) {
            return false;
        }
        EInkDeviceBean eInkDeviceBean = (EInkDeviceBean) other;
        return Intrinsics.areEqual(this.mac, eInkDeviceBean.mac) && Intrinsics.areEqual(this.name, eInkDeviceBean.name) && this.type == eInkDeviceBean.type;
    }

    public int hashCode() {
        return (((this.mac.hashCode() * 31) + this.name.hashCode()) * 31) + this.type;
    }

    public String toString() {
        return "EInkDeviceBean(mac=" + this.mac + ", name=" + this.name + ", type=" + this.type + ')';
    }

    public EInkDeviceBean(String mac, String name, int i) {
        Intrinsics.checkNotNullParameter(mac, "mac");
        Intrinsics.checkNotNullParameter(name, "name");
        this.mac = mac;
        this.name = name;
        this.type = i;
    }

    public /* synthetic */ EInkDeviceBean(String str, String str2, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i2 & 4) != 0 ? 1 : i);
    }

    public final String getMac() {
        return this.mac;
    }

    public final String getName() {
        return this.name;
    }

    public final int getType() {
        return this.type;
    }
}
