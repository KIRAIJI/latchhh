package com.chad.library.adapter.base.diff;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface ListChangeListener<T> {
    void onCurrentListChanged(List<T> list, List<T> list2);
}
