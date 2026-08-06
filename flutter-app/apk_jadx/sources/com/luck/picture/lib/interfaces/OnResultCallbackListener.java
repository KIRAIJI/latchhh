package com.luck.picture.lib.interfaces;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface OnResultCallbackListener<T> {
    void onCancel();

    void onResult(ArrayList<T> arrayList);
}
