package com.luck.picture.lib.engine;

import android.content.Context;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnCallbackIndexListener;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public interface SandboxFileEngine {
    void onStartSandboxFileTransform(Context context, boolean z, int i, LocalMedia localMedia, OnCallbackIndexListener<LocalMedia> onCallbackIndexListener);
}
