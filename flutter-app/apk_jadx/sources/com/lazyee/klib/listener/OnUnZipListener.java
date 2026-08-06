package com.lazyee.klib.listener;

import kotlin.Metadata;

/* JADX INFO: compiled from: OnUnZipListener.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\u0003H&¨\u0006\b"}, d2 = {"Lcom/lazyee/klib/listener/OnUnZipListener;", "", "onUnZipEnd", "", "onUnZipProgress", "fileName", "", "onUnZipStart", "library_release"}, k = 1, mv = {1, 4, 2})
public interface OnUnZipListener {
    void onUnZipEnd();

    void onUnZipProgress(String fileName);

    void onUnZipStart();
}
