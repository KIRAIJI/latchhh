package com.hjq.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public interface PermissionDelegate {
    Intent getPermissionIntent(Context context, String str);

    boolean isGrantedPermission(Context context, String str);

    boolean isPermissionPermanentDenied(Activity activity, String str);
}
