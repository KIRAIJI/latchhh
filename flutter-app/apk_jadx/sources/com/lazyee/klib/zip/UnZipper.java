package com.lazyee.klib.zip;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.lazyee.klib.handler.SimpleHandler;
import com.lazyee.klib.listener.OnUnZipListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: UnZipper.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 %2\u00020\u0001:\u0001%B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\tJ\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u00002\u0006\u0010\u0017\u001a\u00020\u000eJ\u0012\u0010\u0018\u001a\u00020\u00152\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0002J\u000e\u0010\u001b\u001a\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u0010J\u000e\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u0003J\u000e\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020\u001aJ\u0018\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lcom/lazyee/klib/zip/UnZipper;", "", "zipFile", "Ljava/io/File;", "(Ljava/io/File;)V", "inputStream", "Ljava/io/InputStream;", "(Ljava/io/InputStream;)V", "mCharset", "Ljava/nio/charset/Charset;", "mHandler", "Lcom/lazyee/klib/handler/SimpleHandler;", "mInputStream", "mOnUnZipListener", "Lcom/lazyee/klib/listener/OnUnZipListener;", "mOverwrite", "", "mTargetUnZipFile", "mZipFile", "charset", "excetue", "", "listen", "listener", "mkdirs", "dirPath", "", "overwrite", "b", TypedValues.AttributesType.S_TARGET, "targetUnZipFile", "targetUnZipFilePath", "unZipFile", "zipInputStream", "Ljava/util/zip/ZipInputStream;", "zipEntry", "Ljava/util/zip/ZipEntry;", "Companion", "library_release"}, k = 1, mv = {1, 4, 2})
public final class UnZipper {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private Charset mCharset;
    private SimpleHandler mHandler;
    private InputStream mInputStream;
    private OnUnZipListener mOnUnZipListener;
    private boolean mOverwrite;
    private File mTargetUnZipFile;
    private File mZipFile;

    public UnZipper(File zipFile) {
        Intrinsics.checkNotNullParameter(zipFile, "zipFile");
        Charset charset = StandardCharsets.UTF_8;
        Intrinsics.checkNotNullExpressionValue(charset, "StandardCharsets.UTF_8");
        this.mCharset = charset;
        this.mOverwrite = true;
        this.mHandler = new SimpleHandler();
        this.mZipFile = zipFile;
    }

    public UnZipper(InputStream inputStream) {
        Intrinsics.checkNotNullParameter(inputStream, "inputStream");
        Charset charset = StandardCharsets.UTF_8;
        Intrinsics.checkNotNullExpressionValue(charset, "StandardCharsets.UTF_8");
        this.mCharset = charset;
        this.mOverwrite = true;
        this.mHandler = new SimpleHandler();
        this.mInputStream = inputStream;
    }

    /* JADX INFO: compiled from: UnZipper.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bJ\u0016\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\b¨\u0006\r"}, d2 = {"Lcom/lazyee/klib/zip/UnZipper$Companion;", "", "()V", TypedValues.TransitionType.S_FROM, "Lcom/lazyee/klib/zip/UnZipper;", "zipFile", "Ljava/io/File;", "zipFilePath", "", "fromAssetsFile", "context", "Landroid/content/Context;", "assetsFilePath", "library_release"}, k = 1, mv = {1, 4, 2})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final UnZipper from(String zipFilePath) {
            Intrinsics.checkNotNullParameter(zipFilePath, "zipFilePath");
            return new UnZipper(new File(zipFilePath));
        }

        public final UnZipper from(File zipFile) {
            Intrinsics.checkNotNullParameter(zipFile, "zipFile");
            return new UnZipper(zipFile);
        }

        public final UnZipper fromAssetsFile(Context context, String assetsFilePath) throws IOException {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(assetsFilePath, "assetsFilePath");
            InputStream inputStreamOpen = context.getAssets().open(assetsFilePath);
            Intrinsics.checkNotNullExpressionValue(inputStreamOpen, "context.assets.open(assetsFilePath)");
            return new UnZipper(inputStreamOpen);
        }
    }

    public final UnZipper charset(Charset charset) {
        Intrinsics.checkNotNullParameter(charset, "charset");
        this.mCharset = charset;
        return this;
    }

    public final UnZipper listen(OnUnZipListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mOnUnZipListener = listener;
        return this;
    }

    public final UnZipper target(String targetUnZipFilePath) {
        Intrinsics.checkNotNullParameter(targetUnZipFilePath, "targetUnZipFilePath");
        this.mTargetUnZipFile = new File(targetUnZipFilePath);
        return this;
    }

    public final UnZipper target(File targetUnZipFile) {
        Intrinsics.checkNotNullParameter(targetUnZipFile, "targetUnZipFile");
        this.mTargetUnZipFile = targetUnZipFile;
        return this;
    }

    public final UnZipper overwrite(boolean b) {
        this.mOverwrite = b;
        return this;
    }

    public final void excetue() {
        new Thread(new Runnable() { // from class: com.lazyee.klib.zip.UnZipper.excetue.1
            @Override // java.lang.Runnable
            public final void run() {
                ZipInputStream zipInputStream;
                UnZipper.this.mHandler.callback(new Function0<Unit>() { // from class: com.lazyee.klib.zip.UnZipper.excetue.1.1
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
                        OnUnZipListener onUnZipListener = UnZipper.this.mOnUnZipListener;
                        if (onUnZipListener != null) {
                            onUnZipListener.onUnZipStart();
                        }
                    }
                });
                try {
                    if (UnZipper.this.mInputStream == null) {
                        UnZipper.this.mInputStream = new FileInputStream(UnZipper.this.mZipFile);
                    }
                    if (Build.VERSION.SDK_INT >= 24) {
                        InputStream inputStream = UnZipper.this.mInputStream;
                        Intrinsics.checkNotNull(inputStream);
                        zipInputStream = new ZipInputStream(inputStream, UnZipper.this.mCharset);
                    } else {
                        InputStream inputStream2 = UnZipper.this.mInputStream;
                        Intrinsics.checkNotNull(inputStream2);
                        zipInputStream = new ZipInputStream(inputStream2);
                    }
                    UnZipper unZipper = UnZipper.this;
                    File file = unZipper.mTargetUnZipFile;
                    Intrinsics.checkNotNull(file);
                    unZipper.mkdirs(file.getAbsolutePath());
                    for (ZipEntry nextEntry = zipInputStream.getNextEntry(); nextEntry != null; nextEntry = zipInputStream.getNextEntry()) {
                        UnZipper.this.unZipFile(zipInputStream, nextEntry);
                    }
                    zipInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                UnZipper.this.mHandler.callback(new Function0<Unit>() { // from class: com.lazyee.klib.zip.UnZipper.excetue.1.2
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
                        OnUnZipListener onUnZipListener = UnZipper.this.mOnUnZipListener;
                        if (onUnZipListener != null) {
                            onUnZipListener.onUnZipEnd();
                        }
                    }
                });
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void unZipFile(ZipInputStream zipInputStream, final ZipEntry zipEntry) throws IOException {
        if (zipEntry.isDirectory()) {
            String name = zipEntry.getName();
            Intrinsics.checkNotNullExpressionValue(name, "name");
            int length = name.length() - 1;
            Objects.requireNonNull(name, "null cannot be cast to non-null type java.lang.String");
            String strSubstring = name.substring(0, length);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            StringBuilder sb = new StringBuilder();
            File file = this.mTargetUnZipFile;
            Intrinsics.checkNotNull(file);
            sb.append(file.getAbsolutePath());
            sb.append(File.separator);
            sb.append(strSubstring);
            mkdirs(sb.toString());
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        File file2 = this.mTargetUnZipFile;
        Intrinsics.checkNotNull(file2);
        sb2.append(file2.getAbsolutePath());
        sb2.append(File.separator);
        sb2.append(zipEntry.getName());
        File file3 = new File(sb2.toString());
        mkdirs(file3.getParent());
        if (file3.exists()) {
            if (!this.mOverwrite) {
                return;
            } else {
                file3.delete();
            }
        }
        file3.createNewFile();
        this.mHandler.callback(new Function0<Unit>() { // from class: com.lazyee.klib.zip.UnZipper.unZipFile.1
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
                OnUnZipListener onUnZipListener = UnZipper.this.mOnUnZipListener;
                if (onUnZipListener != null) {
                    String name2 = zipEntry.getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "zipEntry.name");
                    onUnZipListener.onUnZipProgress(name2);
                }
            }
        });
        FileOutputStream fileOutputStream = new FileOutputStream(file3);
        byte[] bArr = new byte[1024];
        while (true) {
            int i = zipInputStream.read(bArr);
            if (i > 0) {
                fileOutputStream.write(bArr, 0, i);
            } else {
                fileOutputStream.close();
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void mkdirs(String dirPath) {
        if (dirPath == null || TextUtils.isEmpty(dirPath)) {
            return;
        }
        String str = File.separator;
        Intrinsics.checkNotNullExpressionValue(str, "File.separator");
        if (StringsKt.endsWith$default(dirPath, str, false, 2, (Object) null)) {
            dirPath = dirPath.substring(0, dirPath.length() - 1);
            Intrinsics.checkNotNullExpressionValue(dirPath, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        }
        File file = new File(dirPath);
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }
}
