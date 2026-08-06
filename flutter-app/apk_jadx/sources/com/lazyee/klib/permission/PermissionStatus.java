package com.lazyee.klib.permission;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PermissionStatus.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00052\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\t\"\u0004\b\f\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0018"}, d2 = {"Lcom/lazyee/klib/permission/PermissionStatus;", "", "permission", "", "isGranted", "", "doNotAskAgain", "(Ljava/lang/String;ZZ)V", "getDoNotAskAgain", "()Z", "setDoNotAskAgain", "(Z)V", "setGranted", "getPermission", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "library_release"}, k = 1, mv = {1, 4, 2})
public final /* data */ class PermissionStatus {
    private boolean doNotAskAgain;
    private boolean isGranted;
    private final String permission;

    public static /* synthetic */ PermissionStatus copy$default(PermissionStatus permissionStatus, String str, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = permissionStatus.permission;
        }
        if ((i & 2) != 0) {
            z = permissionStatus.isGranted;
        }
        if ((i & 4) != 0) {
            z2 = permissionStatus.doNotAskAgain;
        }
        return permissionStatus.copy(str, z, z2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getPermission() {
        return this.permission;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getIsGranted() {
        return this.isGranted;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getDoNotAskAgain() {
        return this.doNotAskAgain;
    }

    public final PermissionStatus copy(String permission, boolean isGranted, boolean doNotAskAgain) {
        Intrinsics.checkNotNullParameter(permission, "permission");
        return new PermissionStatus(permission, isGranted, doNotAskAgain);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PermissionStatus)) {
            return false;
        }
        PermissionStatus permissionStatus = (PermissionStatus) other;
        return Intrinsics.areEqual(this.permission, permissionStatus.permission) && this.isGranted == permissionStatus.isGranted && this.doNotAskAgain == permissionStatus.doNotAskAgain;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [int] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [int] */
    /* JADX WARN: Type inference failed for: r2v2 */
    public int hashCode() {
        String str = this.permission;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        boolean z = this.isGranted;
        ?? r1 = z;
        if (z) {
            r1 = 1;
        }
        int i = (iHashCode + r1) * 31;
        boolean z2 = this.doNotAskAgain;
        return i + (z2 ? 1 : z2);
    }

    public String toString() {
        return "PermissionStatus(permission=" + this.permission + ", isGranted=" + this.isGranted + ", doNotAskAgain=" + this.doNotAskAgain + ")";
    }

    public PermissionStatus(String permission, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(permission, "permission");
        this.permission = permission;
        this.isGranted = z;
        this.doNotAskAgain = z2;
    }

    public final String getPermission() {
        return this.permission;
    }

    public final boolean isGranted() {
        return this.isGranted;
    }

    public final void setGranted(boolean z) {
        this.isGranted = z;
    }

    public /* synthetic */ PermissionStatus(String str, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? false : z, (i & 4) != 0 ? false : z2);
    }

    public final boolean getDoNotAskAgain() {
        return this.doNotAskAgain;
    }

    public final void setDoNotAskAgain(boolean z) {
        this.doNotAskAgain = z;
    }
}
