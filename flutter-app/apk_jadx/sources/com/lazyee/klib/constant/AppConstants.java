package com.lazyee.klib.constant;

import kotlin.Metadata;

/* JADX INFO: compiled from: AppConstants.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/lazyee/klib/constant/AppConstants;", "", "()V", "SOFT_KEYBOARD_HEIGHT", "", "getSOFT_KEYBOARD_HEIGHT", "()I", "setSOFT_KEYBOARD_HEIGHT", "(I)V", "library_release"}, k = 1, mv = {1, 4, 2})
public final class AppConstants {
    public static final AppConstants INSTANCE = new AppConstants();
    private static int SOFT_KEYBOARD_HEIGHT;

    private AppConstants() {
    }

    public final int getSOFT_KEYBOARD_HEIGHT() {
        return SOFT_KEYBOARD_HEIGHT;
    }

    public final void setSOFT_KEYBOARD_HEIGHT(int i) {
        SOFT_KEYBOARD_HEIGHT = i;
    }
}
