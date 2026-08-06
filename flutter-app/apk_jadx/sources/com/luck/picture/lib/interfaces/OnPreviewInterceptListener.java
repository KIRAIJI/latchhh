package com.luck.picture.lib.interfaces;

import android.content.Context;
import com.luck.picture.lib.entity.LocalMedia;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface OnPreviewInterceptListener {
    void onPreview(Context context, int i, int i2, int i3, long j, String str, boolean z, ArrayList<LocalMedia> arrayList, boolean z2);
}
