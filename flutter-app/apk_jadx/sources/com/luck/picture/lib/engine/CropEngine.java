package com.luck.picture.lib.engine;

import androidx.fragment.app.Fragment;
import com.luck.picture.lib.entity.LocalMedia;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface CropEngine {
    void onStartCrop(Fragment fragment, LocalMedia localMedia, ArrayList<LocalMedia> arrayList, int i);
}
