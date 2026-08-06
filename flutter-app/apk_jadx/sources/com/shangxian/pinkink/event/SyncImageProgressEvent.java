package com.shangxian.pinkink.event;

import kotlin.Metadata;

/* JADX INFO: compiled from: SyncImageProgressEvent.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J1\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018"}, d2 = {"Lcom/shangxian/pinkink/event/SyncImageProgressEvent;", "", "current", "", "total", "currentBitmapIndex", "totalBitmapCount", "(IIII)V", "getCurrent", "()I", "getCurrentBitmapIndex", "getTotal", "getTotalBitmapCount", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class SyncImageProgressEvent {
    private final int current;
    private final int currentBitmapIndex;
    private final int total;
    private final int totalBitmapCount;

    public static /* synthetic */ SyncImageProgressEvent copy$default(SyncImageProgressEvent syncImageProgressEvent, int i, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i = syncImageProgressEvent.current;
        }
        if ((i5 & 2) != 0) {
            i2 = syncImageProgressEvent.total;
        }
        if ((i5 & 4) != 0) {
            i3 = syncImageProgressEvent.currentBitmapIndex;
        }
        if ((i5 & 8) != 0) {
            i4 = syncImageProgressEvent.totalBitmapCount;
        }
        return syncImageProgressEvent.copy(i, i2, i3, i4);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getCurrent() {
        return this.current;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getTotal() {
        return this.total;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getCurrentBitmapIndex() {
        return this.currentBitmapIndex;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getTotalBitmapCount() {
        return this.totalBitmapCount;
    }

    public final SyncImageProgressEvent copy(int current, int total, int currentBitmapIndex, int totalBitmapCount) {
        return new SyncImageProgressEvent(current, total, currentBitmapIndex, totalBitmapCount);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SyncImageProgressEvent)) {
            return false;
        }
        SyncImageProgressEvent syncImageProgressEvent = (SyncImageProgressEvent) other;
        return this.current == syncImageProgressEvent.current && this.total == syncImageProgressEvent.total && this.currentBitmapIndex == syncImageProgressEvent.currentBitmapIndex && this.totalBitmapCount == syncImageProgressEvent.totalBitmapCount;
    }

    public int hashCode() {
        return (((((this.current * 31) + this.total) * 31) + this.currentBitmapIndex) * 31) + this.totalBitmapCount;
    }

    public String toString() {
        return "SyncImageProgressEvent(current=" + this.current + ", total=" + this.total + ", currentBitmapIndex=" + this.currentBitmapIndex + ", totalBitmapCount=" + this.totalBitmapCount + ')';
    }

    public SyncImageProgressEvent(int i, int i2, int i3, int i4) {
        this.current = i;
        this.total = i2;
        this.currentBitmapIndex = i3;
        this.totalBitmapCount = i4;
    }

    public final int getCurrent() {
        return this.current;
    }

    public final int getCurrentBitmapIndex() {
        return this.currentBitmapIndex;
    }

    public final int getTotal() {
        return this.total;
    }

    public final int getTotalBitmapCount() {
        return this.totalBitmapCount;
    }
}
