package com.lazyee.klib.util;

import android.content.Context;
import android.os.Build;
import android.os.StatFs;
import android.text.TextUtils;
import com.lazyee.klib.listener.OnFileDownloadListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: FileUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\bJ\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\nJ\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\bJ#\u0010\u000f\u001a\u00020\u00042\b\u0010\u0010\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0011\u001a\u00020\nH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0012J\"\u0010\u000f\u001a\u00020\u00132\b\u0010\u0010\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0011\u001a\u00020\n2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\bJ\u000e\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001a"}, d2 = {"Lcom/lazyee/klib/util/FileUtils;", "", "()V", "copyAssetsFile", "", "context", "Landroid/content/Context;", "assetsFilePath", "", "targetFile", "Ljava/io/File;", "targetFilePath", "delete", "file", "filePath", "download", "downloadFileUrl", "outFile", "(Ljava/lang/String;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "", "listener", "Lcom/lazyee/klib/listener/OnFileDownloadListener;", "getFsAvailableSize", "", "anyPathInFs", "getFsTotalSize", "library_release"}, k = 1, mv = {1, 4, 2})
public final class FileUtils {
    public static final FileUtils INSTANCE = new FileUtils();

    private FileUtils() {
    }

    public final long getFsTotalSize(String anyPathInFs) {
        long blockSize;
        long blockCount;
        Intrinsics.checkNotNullParameter(anyPathInFs, "anyPathInFs");
        if (TextUtils.isEmpty(anyPathInFs)) {
            return 0L;
        }
        StatFs statFs = new StatFs(anyPathInFs);
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = statFs.getBlockSizeLong();
            blockCount = statFs.getBlockCountLong();
        } else {
            blockSize = statFs.getBlockSize();
            blockCount = statFs.getBlockCount();
        }
        return blockSize * blockCount;
    }

    public final long getFsAvailableSize(String anyPathInFs) {
        long blockSize;
        long availableBlocks;
        Intrinsics.checkNotNullParameter(anyPathInFs, "anyPathInFs");
        if (TextUtils.isEmpty(anyPathInFs)) {
            return 0L;
        }
        StatFs statFs = new StatFs(anyPathInFs);
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = statFs.getBlockSizeLong();
            availableBlocks = statFs.getAvailableBlocksLong();
        } else {
            blockSize = statFs.getBlockSize();
            availableBlocks = statFs.getAvailableBlocks();
        }
        return blockSize * availableBlocks;
    }

    public final boolean copyAssetsFile(Context context, String assetsFilePath, String targetFilePath) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(assetsFilePath, "assetsFilePath");
        Intrinsics.checkNotNullParameter(targetFilePath, "targetFilePath");
        return copyAssetsFile(context, assetsFilePath, new File(targetFilePath));
    }

    public final boolean copyAssetsFile(Context context, String assetsFilePath, File targetFile) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(assetsFilePath, "assetsFilePath");
        Intrinsics.checkNotNullParameter(targetFile, "targetFile");
        try {
            if (targetFile.exists()) {
                if (!targetFile.canWrite()) {
                    return false;
                }
                targetFile.delete();
            } else {
                String parent = targetFile.getParent();
                if (parent == null) {
                    parent = File.separator;
                }
                File file = new File(parent);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            targetFile.createNewFile();
            InputStream inputStreamOpen = context.getAssets().open(assetsFilePath);
            Intrinsics.checkNotNullExpressionValue(inputStreamOpen, "context.assets.open(assetsFilePath)");
            byte[] bArr = new byte[8192];
            FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
            while (true) {
                int i = inputStreamOpen.read(bArr);
                if (i != -1) {
                    fileOutputStream.write(bArr, 0, i);
                } else {
                    inputStreamOpen.close();
                    fileOutputStream.close();
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final Object download(String str, File file, Continuation<? super Boolean> continuation) {
        if (TextUtils.isEmpty(str)) {
            return Boxing.boxBoolean(false);
        }
        try {
            URLConnection uRLConnectionOpenConnection = new URL(str).openConnection();
            if (uRLConnectionOpenConnection == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.net.HttpURLConnection");
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setConnectTimeout(4000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(false);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Charset", "utf-8");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() != 200) {
                return Boxing.boxBoolean(false);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            InputStream inputStream = httpURLConnection.getInputStream();
            httpURLConnection.getContentLength();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] bArr = new byte[8192];
            while (true) {
                Integer numBoxInt = Boxing.boxInt(bufferedInputStream.read(bArr));
                int iIntValue = numBoxInt.intValue();
                if (numBoxInt.intValue() != -1) {
                    fileOutputStream.write(bArr, 0, iIntValue);
                } else {
                    fileOutputStream.close();
                    inputStream.close();
                    bufferedInputStream.close();
                    return Boxing.boxBoolean(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Boxing.boxBoolean(false);
        }
    }

    public final boolean delete(String filePath) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        return delete(new File(filePath));
    }

    public final boolean delete(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (file.isDirectory()) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles != null) {
                for (File it : fileArrListFiles) {
                    FileUtils fileUtils = INSTANCE;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    fileUtils.delete(it);
                }
            }
            return file.delete();
        }
        return false;
    }

    /* JADX INFO: renamed from: com.lazyee.klib.util.FileUtils$download$3, reason: invalid class name */
    /* JADX INFO: compiled from: FileUtils.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
    @DebugMetadata(c = "com.lazyee.klib.util.FileUtils$download$3", f = "FileUtils.kt", i = {2, 2, 2, 3, 3, 3, 3, 3, 3, 3}, l = {205, 233, 246, 264, 272, 279}, m = "invokeSuspend", n = {"outputStream", "inputStream", "contentLength", "outputStream", "inputStream", "contentLength", "bfi", "len", "total", "bytes"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6"})
    static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $downloadFileUrl;
        final /* synthetic */ OnFileDownloadListener $listener;
        final /* synthetic */ File $outFile;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(String str, OnFileDownloadListener onFileDownloadListener, File file, Continuation continuation) {
            super(2, continuation);
            this.$downloadFileUrl = str;
            this.$listener = onFileDownloadListener;
            this.$outFile = file;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> completion) {
            Intrinsics.checkNotNullParameter(completion, "completion");
            return new AnonymousClass3(this.$downloadFileUrl, this.$listener, this.$outFile, completion);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:51:0x0153 A[Catch: Exception -> 0x01de, TryCatch #0 {Exception -> 0x01de, blocks: (B:49:0x0139, B:51:0x0153, B:53:0x0163), top: B:82:0x0139 }] */
        /* JADX WARN: Removed duplicated region for block: B:62:0x01a6 A[Catch: Exception -> 0x01db, TryCatch #1 {Exception -> 0x01db, blocks: (B:55:0x0175, B:62:0x01a6, B:64:0x01b5), top: B:84:0x0175 }] */
        /* JADX WARN: Removed duplicated region for block: B:77:0x01f3  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:58:0x0194 -> B:59:0x019a). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:60:0x019f -> B:61:0x01a1). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r18) throws java.lang.Throwable {
            /*
                Method dump skipped, instruction units count: 558
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lazyee.klib.util.FileUtils.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public final void download(String downloadFileUrl, File outFile, OnFileDownloadListener listener) {
        Intrinsics.checkNotNullParameter(outFile, "outFile");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass3(downloadFileUrl, listener, outFile, null), 3, null);
    }
}
