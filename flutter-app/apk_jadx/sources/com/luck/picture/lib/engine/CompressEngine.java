package com.luck.picture.lib.engine;

import android.content.Context;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnCallbackListener;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public interface CompressEngine {
    void onStartCompress(Context context, ArrayList<LocalMedia> arrayList, OnCallbackListener<ArrayList<LocalMedia>> onCallbackListener);
}
