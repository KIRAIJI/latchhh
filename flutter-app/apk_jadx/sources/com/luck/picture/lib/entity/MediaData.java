package com.luck.picture.lib.entity;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class MediaData {
    public ArrayList<LocalMedia> data;
    public boolean isHasNextMore;

    public MediaData() {
    }

    public MediaData(boolean z, ArrayList<LocalMedia> arrayList) {
        this.isHasNextMore = z;
        this.data = arrayList;
    }
}
