package com.hjq.permissions;

/* JADX INFO: loaded from: classes.dex */
public interface OnPermissionPageCallback {

    /* JADX INFO: renamed from: com.hjq.permissions.OnPermissionPageCallback$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$onDenied(OnPermissionPageCallback onPermissionPageCallback) {
        }
    }

    void onDenied();

    void onGranted();
}
