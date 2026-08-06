package com.lazyee.klib.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import com.lazyee.klib.app.GlobalExceptionCatcher;
import com.lazyee.klib.extension.AnyExtensionsKt;
import com.lazyee.klib.log.Log2File;
import com.lazyee.klib.util.DateUtils;
import java.io.File;
import java.lang.Thread;
import kotlin.ExceptionsKt;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference0Impl;

/* JADX INFO: compiled from: GlobalExceptionCatcher.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u001dB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\u0014J\u0013\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0016¢\u0006\u0002\u0010\u0018J\b\u0010\u0019\u001a\u0004\u0018\u00010\u0017J\u000e\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\nJ\u0018\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\n2\b\u0010\u001c\u001a\u0004\u0018\u00010\u000eR\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/lazyee/klib/app/GlobalExceptionCatcher;", "", "()V", "crashLog2File", "Lcom/lazyee/klib/log/Log2File;", "getCrashLog2File", "()Lcom/lazyee/klib/log/Log2File;", "crashLog2File$delegate", "Lkotlin/Lazy;", "mApplicationContext", "Landroid/content/Context;", "mCrashLogDirPath", "", "mUncaughtExceptionCallback", "Lcom/lazyee/klib/app/GlobalExceptionCatcher$UncaughtExceptionCallback;", "mUncaughtExceptionHandler", "Ljava/lang/Thread$UncaughtExceptionHandler;", "clearCrashLog", "", "exitProcess", "", "getCrashLogFileList", "", "Ljava/io/File;", "()[Ljava/io/File;", "getLastCrashLog", "init", "applicationContext", "callback", "UncaughtExceptionCallback", "library_release"}, k = 1, mv = {1, 4, 2})
public final class GlobalExceptionCatcher {
    private static Context mApplicationContext;
    private static String mCrashLogDirPath;
    private static UncaughtExceptionCallback mUncaughtExceptionCallback;
    public static final GlobalExceptionCatcher INSTANCE = new GlobalExceptionCatcher();

    /* JADX INFO: renamed from: crashLog2File$delegate, reason: from kotlin metadata */
    private static final Lazy crashLog2File = LazyKt.lazy(new Function0<Log2File>() { // from class: com.lazyee.klib.app.GlobalExceptionCatcher$crashLog2File$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Log2File invoke() {
            return new Log2File(true, GlobalExceptionCatcher.access$getMCrashLogDirPath$p(GlobalExceptionCatcher.INSTANCE), "crash");
        }
    });
    private static final Thread.UncaughtExceptionHandler mUncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() { // from class: com.lazyee.klib.app.GlobalExceptionCatcher$mUncaughtExceptionHandler$1
        @Override // java.lang.Thread.UncaughtExceptionHandler
        public final void uncaughtException(Thread thread, Throwable throwable) throws PackageManager.NameNotFoundException {
            String str;
            PackageInfo packageInfo = GlobalExceptionCatcher.access$getMApplicationContext$p(GlobalExceptionCatcher.INSTANCE).getPackageManager().getPackageInfo(GlobalExceptionCatcher.access$getMApplicationContext$p(GlobalExceptionCatcher.INSTANCE).getPackageName(), 1);
            StringBuilder sb = new StringBuilder();
            sb.append("==============================================================================================\n");
            sb.append(" CRASH DATE            : " + DateUtils.INSTANCE.format(System.currentTimeMillis(), DateUtils.yyyyMMddHHmmss) + '\n');
            sb.append(" BRAND                 : " + Build.BRAND + '\n');
            sb.append(" DEVICE                : " + Build.DEVICE + '\n');
            sb.append(" OS VERSION            : " + Build.VERSION.RELEASE + '\n');
            StringBuilder sb2 = new StringBuilder();
            sb2.append(" APP VERSION CODE      : ");
            String str2 = "null";
            sb2.append(packageInfo != null ? Integer.valueOf(packageInfo.versionCode) : "null");
            sb2.append('\n');
            sb.append(sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(" APP VERSION NAME      : ");
            if (packageInfo != null && (str = packageInfo.versionName) != null) {
                str2 = str;
            }
            sb3.append(str2);
            sb3.append('\n');
            sb.append(sb3.toString());
            sb.append(" SCREEN PIXEL          : " + AnyExtensionsKt.getScreenWidth(GlobalExceptionCatcher.access$getMApplicationContext$p(GlobalExceptionCatcher.INSTANCE)) + 'x' + AnyExtensionsKt.getScreenHeight(GlobalExceptionCatcher.access$getMApplicationContext$p(GlobalExceptionCatcher.INSTANCE)) + '\n');
            sb.append(" ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ CRASH STACK TRACE ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓\n");
            Intrinsics.checkNotNullExpressionValue(throwable, "throwable");
            sb.append(ExceptionsKt.stackTraceToString(throwable));
            sb.append("==============================================================================================\n");
            GlobalExceptionCatcher.INSTANCE.getCrashLog2File().log(sb);
            GlobalExceptionCatcher globalExceptionCatcher = GlobalExceptionCatcher.INSTANCE;
            GlobalExceptionCatcher.UncaughtExceptionCallback uncaughtExceptionCallback = GlobalExceptionCatcher.mUncaughtExceptionCallback;
            if (uncaughtExceptionCallback != null) {
                uncaughtExceptionCallback.onUncaughtException(throwable);
            }
        }
    };

    /* JADX INFO: compiled from: GlobalExceptionCatcher.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/lazyee/klib/app/GlobalExceptionCatcher$UncaughtExceptionCallback;", "", "onUncaughtException", "", "throwable", "", "library_release"}, k = 1, mv = {1, 4, 2})
    public interface UncaughtExceptionCallback {
        void onUncaughtException(Throwable throwable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Log2File getCrashLog2File() {
        return (Log2File) crashLog2File.getValue();
    }

    private GlobalExceptionCatcher() {
    }

    public static final /* synthetic */ Context access$getMApplicationContext$p(GlobalExceptionCatcher globalExceptionCatcher) {
        Context context = mApplicationContext;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mApplicationContext");
        }
        return context;
    }

    public static final /* synthetic */ String access$getMCrashLogDirPath$p(GlobalExceptionCatcher globalExceptionCatcher) {
        String str = mCrashLogDirPath;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrashLogDirPath");
        }
        return str;
    }

    public final void init(Context applicationContext) {
        Intrinsics.checkNotNullParameter(applicationContext, "applicationContext");
        init(applicationContext, null);
    }

    public final void init(Context applicationContext, UncaughtExceptionCallback callback) {
        Intrinsics.checkNotNullParameter(applicationContext, "applicationContext");
        mUncaughtExceptionCallback = callback;
        mApplicationContext = applicationContext;
        StringBuilder sb = new StringBuilder();
        File filesDir = applicationContext.getFilesDir();
        Intrinsics.checkNotNullExpressionValue(filesDir, "applicationContext.filesDir");
        sb.append(filesDir.getAbsolutePath());
        sb.append(File.separator);
        sb.append("crash");
        mCrashLogDirPath = sb.toString();
        Thread.setDefaultUncaughtExceptionHandler(mUncaughtExceptionHandler);
    }

    public final void exitProcess() {
        Process.killProcess(Process.myPid());
        System.exit(1);
        throw new RuntimeException("System.exit returned normally, while it was supposed to halt JVM.");
    }

    /* JADX INFO: renamed from: com.lazyee.klib.app.GlobalExceptionCatcher$clearCrashLog$1, reason: invalid class name */
    /* JADX INFO: compiled from: GlobalExceptionCatcher.kt */
    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 2})
    final /* synthetic */ class AnonymousClass1 extends MutablePropertyReference0Impl {
        AnonymousClass1(GlobalExceptionCatcher globalExceptionCatcher) {
            super(globalExceptionCatcher, GlobalExceptionCatcher.class, "mCrashLogDirPath", "getMCrashLogDirPath()Ljava/lang/String;", 0);
        }

        @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KProperty0
        public Object get() {
            return GlobalExceptionCatcher.access$getMCrashLogDirPath$p((GlobalExceptionCatcher) this.receiver);
        }

        @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KMutableProperty0
        public void set(Object obj) {
            GlobalExceptionCatcher.mCrashLogDirPath = (String) obj;
        }
    }

    public final boolean clearCrashLog() {
        if (mCrashLogDirPath == null) {
            return false;
        }
        String str = mCrashLogDirPath;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrashLogDirPath");
        }
        File file = new File(str);
        if (!file.exists()) {
            return false;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return true;
        }
        for (File file2 : fileArrListFiles) {
            file2.delete();
        }
        return true;
    }

    /* JADX INFO: renamed from: com.lazyee.klib.app.GlobalExceptionCatcher$getLastCrashLog$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: GlobalExceptionCatcher.kt */
    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 2})
    final /* synthetic */ class C00451 extends MutablePropertyReference0Impl {
        C00451(GlobalExceptionCatcher globalExceptionCatcher) {
            super(globalExceptionCatcher, GlobalExceptionCatcher.class, "mCrashLogDirPath", "getMCrashLogDirPath()Ljava/lang/String;", 0);
        }

        @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KProperty0
        public Object get() {
            return GlobalExceptionCatcher.access$getMCrashLogDirPath$p((GlobalExceptionCatcher) this.receiver);
        }

        @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KMutableProperty0
        public void set(Object obj) {
            GlobalExceptionCatcher.mCrashLogDirPath = (String) obj;
        }
    }

    public final File getLastCrashLog() {
        File file = null;
        if (mCrashLogDirPath == null) {
            return null;
        }
        String str = mCrashLogDirPath;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrashLogDirPath");
        }
        File file2 = new File(str);
        if (!file2.exists()) {
            return null;
        }
        File[] fileArrListFiles = file2.listFiles();
        if (fileArrListFiles != null) {
            int i = 1;
            if (!(fileArrListFiles.length == 0)) {
                file = fileArrListFiles[0];
                int lastIndex = ArraysKt.getLastIndex(fileArrListFiles);
                if (lastIndex != 0) {
                    long jLastModified = file.lastModified();
                    if (1 <= lastIndex) {
                        while (true) {
                            File file3 = fileArrListFiles[i];
                            long jLastModified2 = file3.lastModified();
                            if (jLastModified < jLastModified2) {
                                file = file3;
                                jLastModified = jLastModified2;
                            }
                            if (i == lastIndex) {
                                break;
                            }
                            i++;
                        }
                    }
                }
            }
        }
        return file;
    }

    /* JADX INFO: renamed from: com.lazyee.klib.app.GlobalExceptionCatcher$getCrashLogFileList$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: GlobalExceptionCatcher.kt */
    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 2})
    final /* synthetic */ class C00441 extends MutablePropertyReference0Impl {
        C00441(GlobalExceptionCatcher globalExceptionCatcher) {
            super(globalExceptionCatcher, GlobalExceptionCatcher.class, "mCrashLogDirPath", "getMCrashLogDirPath()Ljava/lang/String;", 0);
        }

        @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KProperty0
        public Object get() {
            return GlobalExceptionCatcher.access$getMCrashLogDirPath$p((GlobalExceptionCatcher) this.receiver);
        }

        @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KMutableProperty0
        public void set(Object obj) {
            GlobalExceptionCatcher.mCrashLogDirPath = (String) obj;
        }
    }

    public final File[] getCrashLogFileList() {
        if (mCrashLogDirPath == null) {
            return null;
        }
        String str = mCrashLogDirPath;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCrashLogDirPath");
        }
        File file = new File(str);
        return !file.exists() ? new File[0] : file.listFiles();
    }
}
