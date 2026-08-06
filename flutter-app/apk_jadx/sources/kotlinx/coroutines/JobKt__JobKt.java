package kotlinx.coroutines;

import java.util.Iterator;
import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Job;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: Job.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\u001a\u0019\u0010\b\u001a\u00020\t2\u000e\b\u0004\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0087\b\u001a\u0012\u0010\r\u001a\u00020\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u001a\u0019\u0010\u0010\u001a\u00020\u00052\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0005H\u0007¢\u0006\u0002\b\r\u001a\f\u0010\u0011\u001a\u00020\f*\u00020\u0002H\u0007\u001a\u0018\u0010\u0011\u001a\u00020\u0001*\u00020\u00022\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0007\u001a\u001c\u0010\u0011\u001a\u00020\f*\u00020\u00022\u0010\b\u0002\u0010\u0012\u001a\n\u0018\u00010\u0014j\u0004\u0018\u0001`\u0015\u001a\u001e\u0010\u0011\u001a\u00020\f*\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00172\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u001a\u0015\u0010\u0018\u001a\u00020\f*\u00020\u0005H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0019\u001a\f\u0010\u001a\u001a\u00020\f*\u00020\u0002H\u0007\u001a\u0018\u0010\u001a\u001a\u00020\f*\u00020\u00022\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0007\u001a\u001c\u0010\u001a\u001a\u00020\f*\u00020\u00022\u0010\b\u0002\u0010\u0012\u001a\n\u0018\u00010\u0014j\u0004\u0018\u0001`\u0015\u001a\f\u0010\u001a\u001a\u00020\f*\u00020\u0005H\u0007\u001a\u0018\u0010\u001a\u001a\u00020\f*\u00020\u00052\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0007\u001a\u001c\u0010\u001a\u001a\u00020\f*\u00020\u00052\u0010\b\u0002\u0010\u0012\u001a\n\u0018\u00010\u0014j\u0004\u0018\u0001`\u0015\u001a\u0014\u0010\u001b\u001a\u00020\t*\u00020\u00052\u0006\u0010\u001c\u001a\u00020\tH\u0000\u001a\n\u0010\u001d\u001a\u00020\f*\u00020\u0002\u001a\n\u0010\u001d\u001a\u00020\f*\u00020\u0005\u001a\u001b\u0010\u001e\u001a\u00020\u0013*\u0004\u0018\u00010\u00132\u0006\u0010\u0004\u001a\u00020\u0005H\u0002¢\u0006\u0002\b\u001f\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0005*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006 "}, d2 = {"isActive", "", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)Z", "job", "Lkotlinx/coroutines/Job;", "getJob", "(Lkotlin/coroutines/CoroutineContext;)Lkotlinx/coroutines/Job;", "DisposableHandle", "Lkotlinx/coroutines/DisposableHandle;", "block", "Lkotlin/Function0;", "", "Job", "Lkotlinx/coroutines/CompletableJob;", "parent", "Job0", "cancel", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "message", "", "cancelAndJoin", "(Lkotlinx/coroutines/Job;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancelChildren", "disposeOnCompletion", "handle", "ensureActive", "orCancellation", "orCancellation$JobKt__JobKt", "kotlinx-coroutines-core"}, k = 5, mv = {1, 5, 1}, xi = 48, xs = "kotlinx/coroutines/JobKt")
public final /* synthetic */ class JobKt__JobKt {
    public static final CompletableJob Job(Job job) {
        return new JobImpl(job);
    }

    public static /* synthetic */ CompletableJob Job$default(Job job, int i, Object obj) {
        if ((i & 1) != 0) {
            job = null;
        }
        return JobKt.Job(job);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    /* JADX INFO: renamed from: Job, reason: collision with other method in class */
    public static final /* synthetic */ Job m1890Job(Job job) {
        return JobKt.Job(job);
    }

    /* JADX INFO: renamed from: Job$default, reason: collision with other method in class */
    public static /* synthetic */ Job m1891Job$default(Job job, int i, Object obj) {
        if ((i & 1) != 0) {
            job = null;
        }
        return m1890Job(job);
    }

    public static final DisposableHandle DisposableHandle(final Function0<Unit> function0) {
        return new DisposableHandle() { // from class: kotlinx.coroutines.JobKt__JobKt.DisposableHandle.1
            @Override // kotlinx.coroutines.DisposableHandle
            public void dispose() {
                function0.invoke();
            }
        };
    }

    public static final DisposableHandle disposeOnCompletion(Job job, DisposableHandle disposableHandle) {
        return job.invokeOnCompletion(new DisposeOnCompletion(disposableHandle));
    }

    public static final Object cancelAndJoin(Job job, Continuation<? super Unit> continuation) {
        Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        Object objJoin = job.join(continuation);
        return objJoin == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objJoin : Unit.INSTANCE;
    }

    public static /* synthetic */ void cancelChildren$default(Job job, CancellationException cancellationException, int i, Object obj) {
        if ((i & 1) != 0) {
            cancellationException = null;
        }
        JobKt.cancelChildren(job, cancellationException);
    }

    public static final void cancelChildren(Job job, CancellationException cancellationException) {
        Iterator<Job> it = job.getChildren().iterator();
        while (it.hasNext()) {
            it.next().cancel(cancellationException);
        }
    }

    public static /* synthetic */ void cancelChildren$default(Job job, Throwable th, int i, Object obj) throws Throwable {
        if ((i & 1) != 0) {
            th = null;
        }
        cancelChildren(job, th);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public static final /* synthetic */ void cancelChildren(Job job, Throwable th) throws Throwable {
        for (Job job2 : job.getChildren()) {
            JobSupport jobSupport = job2 instanceof JobSupport ? (JobSupport) job2 : null;
            if (jobSupport != null) {
                jobSupport.cancelInternal(orCancellation$JobKt__JobKt(th, job));
            }
        }
    }

    public static final boolean isActive(CoroutineContext coroutineContext) {
        Job job = (Job) coroutineContext.get(Job.INSTANCE);
        return job != null && job.isActive();
    }

    public static /* synthetic */ void cancel$default(CoroutineContext coroutineContext, CancellationException cancellationException, int i, Object obj) {
        if ((i & 1) != 0) {
            cancellationException = null;
        }
        JobKt.cancel(coroutineContext, cancellationException);
    }

    public static final void cancel(CoroutineContext coroutineContext, CancellationException cancellationException) {
        Job job = (Job) coroutineContext.get(Job.INSTANCE);
        if (job == null) {
            return;
        }
        job.cancel(cancellationException);
    }

    public static final void ensureActive(Job job) {
        if (!job.isActive()) {
            throw job.getCancellationException();
        }
    }

    public static final void ensureActive(CoroutineContext coroutineContext) {
        Job job = (Job) coroutineContext.get(Job.INSTANCE);
        if (job == null) {
            return;
        }
        JobKt.ensureActive(job);
    }

    public static final void cancel(Job job, String str, Throwable th) {
        job.cancel(ExceptionsKt.CancellationException(str, th));
    }

    public static /* synthetic */ void cancel$default(Job job, String str, Throwable th, int i, Object obj) {
        if ((i & 2) != 0) {
            th = null;
        }
        JobKt.cancel(job, str, th);
    }

    public static /* synthetic */ boolean cancel$default(CoroutineContext coroutineContext, Throwable th, int i, Object obj) {
        if ((i & 1) != 0) {
            th = null;
        }
        return cancel(coroutineContext, th);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public static final /* synthetic */ boolean cancel(CoroutineContext coroutineContext, Throwable th) throws Throwable {
        CoroutineContext.Element element = coroutineContext.get(Job.INSTANCE);
        JobSupport jobSupport = element instanceof JobSupport ? (JobSupport) element : null;
        if (jobSupport == null) {
            return false;
        }
        jobSupport.cancelInternal(orCancellation$JobKt__JobKt(th, jobSupport));
        return true;
    }

    public static /* synthetic */ void cancelChildren$default(CoroutineContext coroutineContext, CancellationException cancellationException, int i, Object obj) {
        if ((i & 1) != 0) {
            cancellationException = null;
        }
        JobKt.cancelChildren(coroutineContext, cancellationException);
    }

    public static final void cancelChildren(CoroutineContext coroutineContext, CancellationException cancellationException) {
        Job job = (Job) coroutineContext.get(Job.INSTANCE);
        if (job == null) {
            return;
        }
        Iterator<Job> it = job.getChildren().iterator();
        while (it.hasNext()) {
            it.next().cancel(cancellationException);
        }
    }

    public static final Job getJob(CoroutineContext coroutineContext) {
        Job job = (Job) coroutineContext.get(Job.INSTANCE);
        if (job != null) {
            return job;
        }
        throw new IllegalStateException(Intrinsics.stringPlus("Current context doesn't contain Job in it: ", coroutineContext).toString());
    }

    public static /* synthetic */ void cancelChildren$default(CoroutineContext coroutineContext, Throwable th, int i, Object obj) throws Throwable {
        if ((i & 1) != 0) {
            th = null;
        }
        cancelChildren(coroutineContext, th);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public static final /* synthetic */ void cancelChildren(CoroutineContext coroutineContext, Throwable th) throws Throwable {
        Job job = (Job) coroutineContext.get(Job.INSTANCE);
        if (job == null) {
            return;
        }
        for (Job job2 : job.getChildren()) {
            JobSupport jobSupport = job2 instanceof JobSupport ? (JobSupport) job2 : null;
            if (jobSupport != null) {
                jobSupport.cancelInternal(orCancellation$JobKt__JobKt(th, job));
            }
        }
    }

    private static final Throwable orCancellation$JobKt__JobKt(Throwable th, Job job) {
        return th == null ? new JobCancellationException("Job was cancelled", null, job) : th;
    }
}
