package com.hjq.permissions;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/* JADX INFO: loaded from: classes.dex */
final class WindowPermissionCompat {
    private static final int OP_SYSTEM_ALERT_WINDOW_DEFAULT_VALUE = 24;
    private static final String OP_SYSTEM_ALERT_WINDOW_FIELD_NAME = "OP_SYSTEM_ALERT_WINDOW";

    WindowPermissionCompat() {
    }

    static boolean isGrantedPermission(Context context) {
        if (AndroidVersion.isAndroid6()) {
            return Settings.canDrawOverlays(context);
        }
        if (AndroidVersion.isAndroid4_4()) {
            return PermissionUtils.checkOpNoThrow(context, OP_SYSTEM_ALERT_WINDOW_FIELD_NAME, 24);
        }
        return true;
    }

    static Intent getPermissionIntent(Context context) {
        if (AndroidVersion.isAndroid6()) {
            if (AndroidVersion.isAndroid11() && PhoneRomUtils.isMiui() && PhoneRomUtils.isMiuiOptimization()) {
                return StartActivityManager.addSubIntentToMainIntent(PermissionIntentManager.getMiuiPermissionPageIntent(context), PermissionUtils.getApplicationDetailsIntent(context));
            }
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
            intent.setData(PermissionUtils.getPackageNameUri(context));
            return PermissionUtils.areActivityIntent(context, intent) ? intent : PermissionUtils.getApplicationDetailsIntent(context);
        }
        if (PhoneRomUtils.isEmui()) {
            return StartActivityManager.addSubIntentToMainIntent(PermissionIntentManager.getEmuiWindowPermissionPageIntent(context), PermissionUtils.getApplicationDetailsIntent(context));
        }
        if (PhoneRomUtils.isMiui()) {
            return StartActivityManager.addSubIntentToMainIntent(PhoneRomUtils.isMiuiOptimization() ? PermissionIntentManager.getMiuiWindowPermissionPageIntent(context) : null, PermissionUtils.getApplicationDetailsIntent(context));
        }
        if (PhoneRomUtils.isColorOs()) {
            return StartActivityManager.addSubIntentToMainIntent(PermissionIntentManager.getColorOsWindowPermissionPageIntent(context), PermissionUtils.getApplicationDetailsIntent(context));
        }
        if (PhoneRomUtils.isOriginOs()) {
            return StartActivityManager.addSubIntentToMainIntent(PermissionIntentManager.getOriginOsWindowPermissionPageIntent(context), PermissionUtils.getApplicationDetailsIntent(context));
        }
        if (PhoneRomUtils.isOneUi()) {
            return StartActivityManager.addSubIntentToMainIntent(PermissionIntentManager.getOneUiWindowPermissionPageIntent(context), PermissionUtils.getApplicationDetailsIntent(context));
        }
        return PermissionUtils.getApplicationDetailsIntent(context);
    }
}
