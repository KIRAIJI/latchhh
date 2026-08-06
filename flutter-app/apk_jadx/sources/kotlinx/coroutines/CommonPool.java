package kotlinx.coroutines;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: CommonPool.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0004\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0011\u001a\u0004\u0018\u0001H\u0012\"\u0004\b\u0000\u0010\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0014H\u0082\b¢\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u0019H\u0002J\u001c\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001d2\n\u0010\u0013\u001a\u00060\u001ej\u0002`\u001fH\u0016J\b\u0010 \u001a\u00020\u0006H\u0002J!\u0010!\u001a\u00020\u00102\n\u0010\"\u001a\u0006\u0012\u0002\b\u00030#2\u0006\u0010\u0005\u001a\u00020\u0019H\u0000¢\u0006\u0002\b$J\r\u0010%\u001a\u00020\u0017H\u0000¢\u0006\u0002\b&J\u0015\u0010'\u001a\u00020\u00172\u0006\u0010(\u001a\u00020)H\u0000¢\u0006\u0002\b*J\b\u0010+\u001a\u00020\u0004H\u0016J\r\u0010\u000f\u001a\u00020\u0017H\u0000¢\u0006\u0002\b,R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\r\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lkotlinx/coroutines/CommonPool;", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "()V", "DEFAULT_PARALLELISM_PROPERTY_NAME", "", "executor", "Ljava/util/concurrent/Executor;", "getExecutor", "()Ljava/util/concurrent/Executor;", "parallelism", "", "getParallelism", "()I", "pool", "requestedParallelism", "usePrivatePool", "", "Try", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "close", "", "createPlainPool", "Ljava/util/concurrent/ExecutorService;", "createPool", "dispatch", "context", "Lkotlin/coroutines/CoroutineContext;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "getOrCreatePoolSync", "isGoodCommonPool", "fjpClass", "Ljava/lang/Class;", "isGoodCommonPool$kotlinx_coroutines_core", "restore", "restore$kotlinx_coroutines_core", "shutdown", "timeout", "", "shutdown$kotlinx_coroutines_core", "toString", "usePrivatePool$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class CommonPool extends ExecutorCoroutineDispatcher {
    private static final String DEFAULT_PARALLELISM_PROPERTY_NAME = "kotlinx.coroutines.default.parallelism";
    public static final CommonPool INSTANCE = new CommonPool();
    private static volatile Executor pool;
    private static final int requestedParallelism;
    private static boolean usePrivatePool;

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: isGoodCommonPool$lambda-9, reason: not valid java name */
    public static final void m1883isGoodCommonPool$lambda9() {
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public String toString() {
        return "CommonPool";
    }

    private CommonPool() {
    }

    @Override // kotlinx.coroutines.ExecutorCoroutineDispatcher
    public Executor getExecutor() {
        Executor executor = pool;
        return executor == null ? getOrCreatePoolSync() : executor;
    }

    static {
        String property;
        int iIntValue;
        try {
            property = System.getProperty(DEFAULT_PARALLELISM_PROPERTY_NAME);
        } catch (Throwable unused) {
            property = null;
        }
        if (property == null) {
            iIntValue = -1;
        } else {
            Integer intOrNull = StringsKt.toIntOrNull(property);
            if (intOrNull == null || intOrNull.intValue() < 1) {
                throw new IllegalStateException(Intrinsics.stringPlus("Expected positive number in kotlinx.coroutines.default.parallelism, but has ", property).toString());
            }
            iIntValue = intOrNull.intValue();
        }
        requestedParallelism = iIntValue;
    }

    private final int getParallelism() {
        Integer numValueOf = Integer.valueOf(requestedParallelism);
        if (!(numValueOf.intValue() > 0)) {
            numValueOf = null;
        }
        if (numValueOf != null) {
            return numValueOf.intValue();
        }
        return RangesKt.coerceAtLeast(Runtime.getRuntime().availableProcessors() - 1, 1);
    }

    private final <T> T Try(Function0<? extends T> block) {
        try {
            return block.invoke();
        } catch (Throwable unused) {
            return null;
        }
    }

    private final ExecutorService createPool() {
        Class<?> cls;
        Object objInvoke;
        if (System.getSecurityManager() != null) {
            return createPlainPool();
        }
        ExecutorService executorService = null;
        try {
            cls = Class.forName("java.util.concurrent.ForkJoinPool");
        } catch (Throwable unused) {
            cls = null;
        }
        if (cls == null) {
            return createPlainPool();
        }
        if (!usePrivatePool && requestedParallelism < 0) {
            try {
                objInvoke = cls.getMethod("commonPool", new Class[0]).invoke(null, new Object[0]);
            } catch (Throwable unused2) {
            }
            ExecutorService executorService2 = objInvoke instanceof ExecutorService ? (ExecutorService) objInvoke : null;
            if (executorService2 != null) {
                if (!INSTANCE.isGoodCommonPool$kotlinx_coroutines_core(cls, executorService2)) {
                    executorService2 = null;
                }
                if (executorService2 != null) {
                    return executorService2;
                }
            }
        }
        try {
            Object objNewInstance = cls.getConstructor(Integer.TYPE).newInstance(Integer.valueOf(INSTANCE.getParallelism()));
            if (objNewInstance instanceof ExecutorService) {
                executorService = (ExecutorService) objNewInstance;
            }
        } catch (Throwable unused3) {
        }
        return executorService == null ? createPlainPool() : executorService;
    }

    public final boolean isGoodCommonPool$kotlinx_coroutines_core(Class<?> fjpClass, ExecutorService executor) {
        executor.submit(new Runnable() { // from class: kotlinx.coroutines.CommonPool$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CommonPool.m1883isGoodCommonPool$lambda9();
            }
        });
        Integer num = null;
        try {
            Object objInvoke = fjpClass.getMethod("getPoolSize", new Class[0]).invoke(executor, new Object[0]);
            if (objInvoke instanceof Integer) {
                num = (Integer) objInvoke;
            }
        } catch (Throwable unused) {
        }
        return num != null && num.intValue() >= 1;
    }

    private final ExecutorService createPlainPool() {
        final AtomicInteger atomicInteger = new AtomicInteger();
        return Executors.newFixedThreadPool(getParallelism(), new ThreadFactory() { // from class: kotlinx.coroutines.CommonPool$$ExternalSyntheticLambda2
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return CommonPool.m1882createPlainPool$lambda12(atomicInteger, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: createPlainPool$lambda-12, reason: not valid java name */
    public static final Thread m1882createPlainPool$lambda12(AtomicInteger atomicInteger, Runnable runnable) {
        Thread thread = new Thread(runnable, Intrinsics.stringPlus("CommonPool-worker-", Integer.valueOf(atomicInteger.incrementAndGet())));
        thread.setDaemon(true);
        return thread;
    }

    private final synchronized Executor getOrCreatePoolSync() {
        ExecutorService executorService;
        executorService = pool;
        if (executorService == null) {
            ExecutorService executorServiceCreatePool = createPool();
            pool = executorServiceCreatePool;
            executorService = executorServiceCreatePool;
        }
        return executorService;
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    /* JADX INFO: renamed from: dispatch */
    public void mo1946dispatch(CoroutineContext context, Runnable block) {
        try {
            Executor orCreatePoolSync = pool;
            if (orCreatePoolSync == null) {
                orCreatePoolSync = getOrCreatePoolSync();
            }
            AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
            orCreatePoolSync.execute(timeSource == null ? block : timeSource.wrapTask(block));
        } catch (RejectedExecutionException unused) {
            AbstractTimeSource timeSource2 = AbstractTimeSourceKt.getTimeSource();
            if (timeSource2 != null) {
                timeSource2.unTrackTask();
            }
            DefaultExecutor.INSTANCE.enqueue(block);
        }
    }

    public final synchronized void usePrivatePool$kotlinx_coroutines_core() {
        shutdown$kotlinx_coroutines_core(0L);
        usePrivatePool = true;
        pool = null;
    }

    public final synchronized void shutdown$kotlinx_coroutines_core(long timeout) {
        Executor executor = pool;
        ExecutorService executorService = executor instanceof ExecutorService ? (ExecutorService) executor : null;
        if (executorService != null) {
            executorService.shutdown();
            if (timeout > 0) {
                executorService.awaitTermination(timeout, TimeUnit.MILLISECONDS);
            }
            Iterator<T> it = executorService.shutdownNow().iterator();
            while (it.hasNext()) {
                DefaultExecutor.INSTANCE.enqueue((Runnable) it.next());
            }
        }
        pool = new Executor() { // from class: kotlinx.coroutines.CommonPool$$ExternalSyntheticLambda1
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                CommonPool.m1884shutdown$lambda16(runnable);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: shutdown$lambda-16, reason: not valid java name */
    public static final void m1884shutdown$lambda16(Runnable runnable) {
        throw new RejectedExecutionException("CommonPool was shutdown");
    }

    public final synchronized void restore$kotlinx_coroutines_core() {
        shutdown$kotlinx_coroutines_core(0L);
        usePrivatePool = false;
        pool = null;
    }

    @Override // kotlinx.coroutines.ExecutorCoroutineDispatcher, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        throw new IllegalStateException("Close cannot be invoked on CommonPool".toString());
    }
}
