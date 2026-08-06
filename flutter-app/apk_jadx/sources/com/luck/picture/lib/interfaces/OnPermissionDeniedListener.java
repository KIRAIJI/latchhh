package com.luck.picture.lib.interfaces;

import androidx.fragment.app.Fragment;

/* JADX INFO: loaded from: classes.dex */
public interface OnPermissionDeniedListener {
    void onDenied(Fragment fragment, String[] strArr, int i, OnCallbackListener<Boolean> onCallbackListener);
}
