package com.lazyee.klib.zip;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.lazyee.klib.handler.SimpleHandler;
import com.lazyee.klib.listener.OnZipListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Zipper.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 '2\u00020\u0001:\u0001'B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u0015\b\u0016\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t¢\u0006\u0002\u0010\nJ\u000e\u0010\u0016\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\fJ\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u001a\u001a\u00020\u0010J\u000e\u0010\u001b\u001a\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u0012J \u0010\u001d\u001a\u00020\u00182\u0006\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020!H\u0002J&\u0010\u001d\u001a\u00020\u00182\u0006\u0010\"\u001a\u00020\u00032\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00060\t2\u0006\u0010 \u001a\u00020!H\u0002J\u000e\u0010$\u001a\u00020\u00002\u0006\u0010%\u001a\u00020\u0006J\u000e\u0010$\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u0003R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lcom/lazyee/klib/zip/Zipper;", "", "filePath", "", "(Ljava/lang/String;)V", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "fileList", "", "(Ljava/util/List;)V", "mCharset", "Ljava/nio/charset/Charset;", "mHandler", "Lcom/lazyee/klib/handler/SimpleHandler;", "mOnZipListener", "Lcom/lazyee/klib/listener/OnZipListener;", "mOverwrite", "", "mSourceFileList", "", "mTargetZipFile", "charset", "excetue", "", "listen", "listener", "overwrite", "b", "realZip", "parentDirName", "sourceFile", "zipOutputStream", "Ljava/util/zip/ZipOutputStream;", "dirName", "sourceFileList", TypedValues.AttributesType.S_TARGET, "targetZipFile", "targetZipFilePath", "Companion", "library_release"}, k = 1, mv = {1, 4, 2})
public final class Zipper {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private Charset mCharset;
    private SimpleHandler mHandler;
    private OnZipListener mOnZipListener;
    private boolean mOverwrite;
    private List<File> mSourceFileList;
    private File mTargetZipFile;

    public Zipper(String filePath) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        this.mSourceFileList = new ArrayList();
        Charset charset = StandardCharsets.UTF_8;
        Intrinsics.checkNotNullExpressionValue(charset, "StandardCharsets.UTF_8");
        this.mCharset = charset;
        this.mOverwrite = true;
        this.mHandler = new SimpleHandler();
        this.mSourceFileList.add(new File(filePath));
    }

    public Zipper(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        this.mSourceFileList = new ArrayList();
        Charset charset = StandardCharsets.UTF_8;
        Intrinsics.checkNotNullExpressionValue(charset, "StandardCharsets.UTF_8");
        this.mCharset = charset;
        this.mOverwrite = true;
        this.mHandler = new SimpleHandler();
        this.mSourceFileList.add(file);
    }

    public Zipper(List<? extends File> fileList) {
        Intrinsics.checkNotNullParameter(fileList, "fileList");
        this.mSourceFileList = new ArrayList();
        Charset charset = StandardCharsets.UTF_8;
        Intrinsics.checkNotNullExpressionValue(charset, "StandardCharsets.UTF_8");
        this.mCharset = charset;
        this.mOverwrite = true;
        this.mHandler = new SimpleHandler();
        Iterator<T> it = fileList.iterator();
        while (it.hasNext()) {
            this.mSourceFileList.add((File) it.next());
        }
    }

    /* JADX INFO: compiled from: Zipper.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bJ\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0007¢\u0006\u0002\b\u000bJ\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\b0\nH\u0007¢\u0006\u0002\b\r¨\u0006\u000e"}, d2 = {"Lcom/lazyee/klib/zip/Zipper$Companion;", "", "()V", TypedValues.TransitionType.S_FROM, "Lcom/lazyee/klib/zip/Zipper;", "file", "Ljava/io/File;", "filePath", "", "fileList", "", "fromFileList", "filePathList", "fromFilePathList", "library_release"}, k = 1, mv = {1, 4, 2})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Zipper from(String filePath) {
            Intrinsics.checkNotNullParameter(filePath, "filePath");
            return new Zipper(filePath);
        }

        public final Zipper from(File file) {
            Intrinsics.checkNotNullParameter(file, "file");
            return new Zipper(file);
        }

        public final Zipper fromFilePathList(List<String> filePathList) {
            Intrinsics.checkNotNullParameter(filePathList, "filePathList");
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = filePathList.iterator();
            while (it.hasNext()) {
                arrayList.add(new File((String) it.next()));
            }
            return new Zipper(arrayList);
        }

        public final Zipper fromFileList(List<? extends File> fileList) {
            Intrinsics.checkNotNullParameter(fileList, "fileList");
            return new Zipper(fileList);
        }
    }

    public final Zipper target(String targetZipFilePath) {
        Intrinsics.checkNotNullParameter(targetZipFilePath, "targetZipFilePath");
        this.mTargetZipFile = new File(targetZipFilePath);
        return this;
    }

    public final Zipper target(File targetZipFile) {
        Intrinsics.checkNotNullParameter(targetZipFile, "targetZipFile");
        this.mTargetZipFile = targetZipFile;
        return this;
    }

    public final Zipper charset(Charset charset) {
        Intrinsics.checkNotNullParameter(charset, "charset");
        this.mCharset = charset;
        return this;
    }

    public final Zipper overwrite(boolean b) {
        this.mOverwrite = b;
        return this;
    }

    public final Zipper listen(OnZipListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mOnZipListener = listener;
        return this;
    }

    public final void excetue() {
        if (this.mTargetZipFile != null) {
            OnZipListener onZipListener = this.mOnZipListener;
            if (onZipListener != null) {
                onZipListener.onZipStart();
            }
            new Thread(new Runnable() { // from class: com.lazyee.klib.zip.Zipper.excetue.1
                @Override // java.lang.Runnable
                public final void run() {
                    Object next;
                    Iterator it = Zipper.this.mSourceFileList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        } else {
                            next = it.next();
                            if (((File) next).exists()) {
                                break;
                            }
                        }
                    }
                    if (next != null) {
                        if (!Zipper.this.mOverwrite) {
                            Zipper.this.mHandler.callback(new Function0<Unit>() { // from class: com.lazyee.klib.zip.Zipper.excetue.1.1
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public /* bridge */ /* synthetic */ Unit invoke() {
                                    invoke2();
                                    return Unit.INSTANCE;
                                }

                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2() {
                                    OnZipListener onZipListener2 = Zipper.this.mOnZipListener;
                                    if (onZipListener2 != null) {
                                        onZipListener2.onZipEnd(false);
                                    }
                                }
                            });
                            return;
                        }
                        try {
                            File file = Zipper.this.mTargetZipFile;
                            Intrinsics.checkNotNull(file);
                            if (file.exists()) {
                                File file2 = Zipper.this.mTargetZipFile;
                                Intrinsics.checkNotNull(file2);
                                file2.delete();
                            }
                            File file3 = Zipper.this.mTargetZipFile;
                            Intrinsics.checkNotNull(file3);
                            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file3));
                            Zipper zipper = Zipper.this;
                            zipper.realZip("", (List<? extends File>) zipper.mSourceFileList, zipOutputStream);
                            zipOutputStream.close();
                            Zipper.this.mHandler.callback(new Function0<Unit>() { // from class: com.lazyee.klib.zip.Zipper.excetue.1.2
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public /* bridge */ /* synthetic */ Unit invoke() {
                                    invoke2();
                                    return Unit.INSTANCE;
                                }

                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2() {
                                    OnZipListener onZipListener2 = Zipper.this.mOnZipListener;
                                    if (onZipListener2 != null) {
                                        onZipListener2.onZipEnd(true);
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            Zipper.this.mHandler.callback(new Function0<Unit>() { // from class: com.lazyee.klib.zip.Zipper.excetue.1.3
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public /* bridge */ /* synthetic */ Unit invoke() {
                                    invoke2();
                                    return Unit.INSTANCE;
                                }

                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2() {
                                    OnZipListener onZipListener2 = Zipper.this.mOnZipListener;
                                    if (onZipListener2 != null) {
                                        onZipListener2.onZipEnd(false);
                                    }
                                }
                            });
                        }
                    }
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void realZip(String dirName, List<? extends File> sourceFileList, ZipOutputStream zipOutputStream) throws IOException {
        Iterator<T> it = sourceFileList.iterator();
        while (it.hasNext()) {
            realZip(dirName, (File) it.next(), zipOutputStream);
        }
    }

    private final void realZip(String parentDirName, File sourceFile, ZipOutputStream zipOutputStream) throws IOException {
        List<? extends File> listEmptyList;
        if (!sourceFile.exists()) {
            return;
        }
        if (sourceFile.isDirectory()) {
            String str = parentDirName + sourceFile.getName() + File.separator;
            zipOutputStream.putNextEntry(new ZipEntry(str));
            File[] fileArrListFiles = sourceFile.listFiles();
            if (fileArrListFiles == null || (listEmptyList = ArraysKt.toList(fileArrListFiles)) == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            if (!listEmptyList.isEmpty()) {
                realZip(str, listEmptyList, zipOutputStream);
                return;
            }
            return;
        }
        final ZipEntry zipEntry = new ZipEntry(parentDirName + sourceFile.getName());
        zipOutputStream.putNextEntry(zipEntry);
        this.mHandler.callback(new Function0<Unit>() { // from class: com.lazyee.klib.zip.Zipper.realZip.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                OnZipListener onZipListener = Zipper.this.mOnZipListener;
                if (onZipListener != null) {
                    String name = zipEntry.getName();
                    Intrinsics.checkNotNullExpressionValue(name, "zipEntry.name");
                    onZipListener.onZipProgress(name);
                }
            }
        });
        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        byte[] bArr = new byte[1024];
        while (true) {
            int i = fileInputStream.read(bArr);
            if (i <= 0) {
                return;
            } else {
                zipOutputStream.write(bArr, 0, i);
            }
        }
    }
}
