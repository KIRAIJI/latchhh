package com.lazyee.klib.listener;

import kotlin.Metadata;

/* JADX INFO: compiled from: OnFileCopyListener.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0018\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH&J\b\u0010\u000b\u001a\u00020\u0003H&¨\u0006\f"}, d2 = {"Lcom/lazyee/klib/listener/OnFileCopyListener;", "", "onCopyComplete", "", "onCopyFailed", "errorMessage", "", "onCopyProgress", "progress", "", "total", "onCopyStart", "library_release"}, k = 1, mv = {1, 4, 2})
public interface OnFileCopyListener {
    void onCopyComplete();

    void onCopyFailed(String errorMessage);

    void onCopyProgress(long progress, long total);

    void onCopyStart();
}
