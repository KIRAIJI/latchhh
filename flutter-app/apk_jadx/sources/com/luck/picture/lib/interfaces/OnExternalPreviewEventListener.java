package com.luck.picture.lib.interfaces;

import android.content.Context;
import com.luck.picture.lib.entity.LocalMedia;

/* JADX INFO: loaded from: classes.dex */
public interface OnExternalPreviewEventListener {
    boolean onLongPressDownload(Context context, LocalMedia localMedia);

    void onPreviewDelete(int i);
}
