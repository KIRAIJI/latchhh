package com.lazyee.klib.log;

import androidx.core.app.NotificationCompat;
import com.lazyee.klib.util.DateUtils;
import com.lazyee.klib.util.LogUtils;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: Log2File.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u001a\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00052\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001J\b\u0010\u0014\u001a\u00020\u0011H\u0002J\u001a\u0010\u0015\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00052\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001J\u0010\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u0005H\u0002J\u001a\u0010\u0018\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00052\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001J \u0010\u0019\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u0005H\u0002J\u000e\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u001fJ\u000e\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u0005J \u0010 \u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u0005H\u0002J\u0010\u0010 \u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u0005H\u0002J\u001a\u0010!\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00052\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001J\u001a\u0010\"\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00052\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\r¨\u0006#"}, d2 = {"Lcom/lazyee/klib/log/Log2File;", "", "isRecord", "", "logFileDirPath", "", "logName", "(ZLjava/lang/String;Ljava/lang/String;)V", "logFile", "Ljava/io/File;", "mExecutorService", "Ljava/util/concurrent/ExecutorService;", "getMExecutorService", "()Ljava/util/concurrent/ExecutorService;", "mExecutorService$delegate", "Lkotlin/Lazy;", "d", "", "tag", "any", "deleteTimeOutLogFile", "e", "getTargetLogFile", "date", "i", "log", "level", "Lcom/lazyee/klib/log/LogLevel;", NotificationCompat.CATEGORY_MESSAGE, "sb", "Ljava/lang/StringBuffer;", "Ljava/lang/StringBuilder;", "log2File", "v", "w", "library_release"}, k = 1, mv = {1, 4, 2})
public final class Log2File {
    private final boolean isRecord;
    private File logFile;
    private final String logFileDirPath;
    private final String logName;

    /* JADX INFO: renamed from: mExecutorService$delegate, reason: from kotlin metadata */
    private final Lazy mExecutorService;

    private final ExecutorService getMExecutorService() {
        return (ExecutorService) this.mExecutorService.getValue();
    }

    public Log2File(boolean z, String logFileDirPath, String logName) {
        Intrinsics.checkNotNullParameter(logFileDirPath, "logFileDirPath");
        Intrinsics.checkNotNullParameter(logName, "logName");
        this.isRecord = z;
        this.logFileDirPath = logFileDirPath;
        this.logName = logName;
        this.mExecutorService = LazyKt.lazy(new Function0<ExecutorService>() { // from class: com.lazyee.klib.log.Log2File$mExecutorService$2
            @Override // kotlin.jvm.functions.Function0
            public final ExecutorService invoke() {
                return Executors.newSingleThreadExecutor();
            }
        });
        getMExecutorService();
        deleteTimeOutLogFile();
    }

    public /* synthetic */ Log2File(boolean z, String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, str, (i & 4) != 0 ? "log" : str2);
    }

    public final void log(String log) {
        Intrinsics.checkNotNullParameter(log, "log");
        log2File(log);
    }

    public final void log(StringBuilder sb) {
        Intrinsics.checkNotNullParameter(sb, "sb");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
        log(string);
    }

    public final void log(StringBuffer sb) {
        Intrinsics.checkNotNullParameter(sb, "sb");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
        log(string);
    }

    public final void d(String tag, Object any) {
        log(LogLevel.D, LogUtils.INSTANCE.getTag$library_release(tag), LogUtils.INSTANCE.getMsg$library_release(any));
    }

    public final void e(String tag, Object any) {
        log(LogLevel.E, LogUtils.INSTANCE.getTag$library_release(tag), LogUtils.INSTANCE.getMsg$library_release(any));
    }

    public final void i(String tag, Object any) {
        log(LogLevel.I, LogUtils.INSTANCE.getTag$library_release(tag), LogUtils.INSTANCE.getMsg$library_release(any));
    }

    public final void w(String tag, Object any) {
        log(LogLevel.W, LogUtils.INSTANCE.getTag$library_release(tag), LogUtils.INSTANCE.getMsg$library_release(any));
    }

    public final void v(String tag, Object any) {
        log(LogLevel.V, LogUtils.INSTANCE.getTag$library_release(tag), LogUtils.INSTANCE.getMsg$library_release(any));
    }

    private final synchronized File getTargetLogFile(String date) {
        String str = this.logFileDirPath + File.separator + this.logName + '-' + date + ".log";
        File file = this.logFile;
        if (file != null) {
            Intrinsics.checkNotNull(file);
            if (Intrinsics.areEqual(file.getAbsolutePath(), str)) {
                File file2 = this.logFile;
                Intrinsics.checkNotNull(file2);
                return file2;
            }
        }
        File file3 = new File(this.logFileDirPath);
        if (!file3.exists()) {
            file3.mkdirs();
        }
        File file4 = new File(str);
        this.logFile = file4;
        Intrinsics.checkNotNull(file4);
        if (!file4.exists()) {
            File file5 = this.logFile;
            Intrinsics.checkNotNull(file5);
            file5.createNewFile();
        }
        File file6 = this.logFile;
        Intrinsics.checkNotNull(file6);
        return file6;
    }

    private final void log(LogLevel level, String tag, String msg) {
        LogUtils.INSTANCE.log$library_release(level, tag, msg);
        if (this.isRecord) {
            getMExecutorService();
            log2File(level, tag, msg);
        }
    }

    private final void log2File(LogLevel level, String tag, String msg) {
        if (this.logFileDirPath.length() == 0) {
            return;
        }
        String str = DateUtils.INSTANCE.format(System.currentTimeMillis(), DateUtils.yyyyMMddHHmmss);
        FilesKt.appendText$default(getTargetLogFile((String) StringsKt.split$default((CharSequence) str, new String[]{" "}, false, 0, 6, (Object) null).get(0)), str + " -> [" + level.name() + "] " + tag + " : " + msg + '\n', null, 2, null);
    }

    private final void log2File(String log) {
        if (this.logFileDirPath.length() == 0) {
            return;
        }
        FilesKt.appendText$default(getTargetLogFile((String) StringsKt.split$default((CharSequence) DateUtils.INSTANCE.format(System.currentTimeMillis(), DateUtils.yyyyMMddHHmmss), new String[]{" "}, false, 0, 6, (Object) null).get(0)), log, null, 2, null);
    }

    private final void deleteTimeOutLogFile() {
        File file = new File(this.logFileDirPath);
        if (file.exists()) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles != null) {
                for (File file2 : fileArrListFiles) {
                    if (jCurrentTimeMillis - file2.lastModified() > 864000000) {
                        file2.delete();
                    }
                }
            }
        }
    }
}
