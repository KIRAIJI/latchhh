package com.lazyee.klib.extension;

import android.media.MediaMetadataRetriever;
import com.lazyee.klib.listener.OnFileCopyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FileExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\n\u0010\u0007\u001a\u00020\b*\u00020\u0002¨\u0006\t"}, d2 = {"copy", "", "Ljava/io/File;", "destFilePath", "", "listener", "Lcom/lazyee/klib/listener/OnFileCopyListener;", "getAudioDuration", "", "library_release"}, k = 2, mv = {1, 4, 2})
public final class FileExtensionsKt {
    public static final long getAudioDuration(File getAudioDuration) {
        Intrinsics.checkNotNullParameter(getAudioDuration, "$this$getAudioDuration");
        try {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(getAudioDuration.getAbsolutePath());
            String strExtractMetadata = mediaMetadataRetriever.extractMetadata(9);
            if (strExtractMetadata != null) {
                return Long.parseLong(strExtractMetadata);
            }
            return 0L;
        } catch (Exception unused) {
            return 0L;
        }
    }

    public static /* synthetic */ void copy$default(File file, String str, OnFileCopyListener onFileCopyListener, int i, Object obj) throws IOException {
        if ((i & 2) != 0) {
            onFileCopyListener = (OnFileCopyListener) null;
        }
        copy(file, str, onFileCopyListener);
    }

    public static final void copy(File copy, String destFilePath, OnFileCopyListener onFileCopyListener) throws IOException {
        Intrinsics.checkNotNullParameter(copy, "$this$copy");
        Intrinsics.checkNotNullParameter(destFilePath, "destFilePath");
        if (!copy.exists()) {
            if (onFileCopyListener != null) {
                onFileCopyListener.onCopyFailed("源文件不存在");
                return;
            }
            return;
        }
        if (!copy.isFile()) {
            if (onFileCopyListener != null) {
                onFileCopyListener.onCopyFailed("源文件不是一个文件");
                return;
            }
            return;
        }
        if (!copy.canRead()) {
            if (onFileCopyListener != null) {
                onFileCopyListener.onCopyFailed("源文件不可读");
                return;
            }
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(copy);
        File file = new File(destFilePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] bArr = new byte[8192];
        long j = 0;
        if (onFileCopyListener != null) {
            onFileCopyListener.onCopyStart();
        }
        while (true) {
            int i = fileInputStream.read(bArr);
            Unit unit = Unit.INSTANCE;
            if (-1 == i) {
                break;
            }
            j += (long) i;
            if (onFileCopyListener != null) {
                onFileCopyListener.onCopyProgress(j, copy.length());
            }
            fileOutputStream.write(bArr, 0, i);
        }
        fileInputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
        if (onFileCopyListener != null) {
            onFileCopyListener.onCopyComplete();
        }
    }
}
