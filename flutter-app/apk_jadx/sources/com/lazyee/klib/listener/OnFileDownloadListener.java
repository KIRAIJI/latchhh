package com.lazyee.klib.listener;

import java.io.File;
import kotlin.Metadata;

/* JADX INFO: compiled from: OnFileDownloadListener.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0014\u0010\u0006\u001a\u00020\u00032\n\u0010\u0007\u001a\u00060\bj\u0002`\tH&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH&J\u0018\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\fH&¨\u0006\u000f"}, d2 = {"Lcom/lazyee/klib/listener/OnFileDownloadListener;", "", "onDownloadComplete", "", "file", "Ljava/io/File;", "onDownloadFailure", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "onDownloadStart", "totalSize", "", "onDownloading", "currentSize", "library_release"}, k = 1, mv = {1, 4, 2})
public interface OnFileDownloadListener {
    void onDownloadComplete(File file);

    void onDownloadFailure(Exception e);

    void onDownloadStart(int totalSize);

    void onDownloading(int currentSize, int totalSize);
}
