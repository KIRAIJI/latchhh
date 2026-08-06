package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.sync.Semaphore;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: compiled from: Collect.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ChannelFlowMerge$collectTo$$inlined$collect$1<T> implements FlowCollector<Flow<? extends T>> {
    final /* synthetic */ SendingCollector $collector$inlined;
    final /* synthetic */ Job $job$inlined;
    final /* synthetic */ ProducerScope $scope$inlined;
    final /* synthetic */ Semaphore $semaphore$inlined;

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1$1, reason: invalid class name */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1", f = "Merge.kt", i = {0, 0}, l = {136}, m = "emit", n = {"this", "inner"}, s = {"L$0", "L$1"})
    public static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelFlowMerge$collectTo$$inlined$collect$1.this.emit(null, this);
        }
    }

    public ChannelFlowMerge$collectTo$$inlined$collect$1(Job job, Semaphore semaphore, ProducerScope producerScope, SendingCollector sendingCollector) {
        this.$job$inlined = job;
        this.$semaphore$inlined = semaphore;
        this.$scope$inlined = producerScope;
        this.$collector$inlined = sendingCollector;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object emit(kotlinx.coroutines.flow.Flow<? extends T> r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) throws java.lang.Throwable {
        /*
            r7 = this;
            boolean r0 = r9 instanceof kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r9
            kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1$1 r0 = (kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L19
        L14:
            kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1$1 r0 = new kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1$1
            r0.<init>(r9)
        L19:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3a
            if (r2 != r3) goto L32
            java.lang.Object r8 = r0.L$1
            kotlinx.coroutines.flow.Flow r8 = (kotlinx.coroutines.flow.Flow) r8
            java.lang.Object r0 = r0.L$0
            kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1 r0 = (kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1) r0
            kotlin.ResultKt.throwOnFailure(r9)
            goto L5a
        L32:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L3a:
            kotlin.ResultKt.throwOnFailure(r9)
            r9 = r0
            kotlin.coroutines.Continuation r9 = (kotlin.coroutines.Continuation) r9
            kotlinx.coroutines.flow.Flow r8 = (kotlinx.coroutines.flow.Flow) r8
            kotlinx.coroutines.Job r9 = r7.$job$inlined
            if (r9 != 0) goto L47
            goto L4a
        L47:
            kotlinx.coroutines.JobKt.ensureActive(r9)
        L4a:
            kotlinx.coroutines.sync.Semaphore r9 = r7.$semaphore$inlined
            r0.L$0 = r7
            r0.L$1 = r8
            r0.label = r3
            java.lang.Object r9 = r9.acquire(r0)
            if (r9 != r1) goto L59
            return r1
        L59:
            r0 = r7
        L5a:
            kotlinx.coroutines.channels.ProducerScope r9 = r0.$scope$inlined
            r1 = r9
            kotlinx.coroutines.CoroutineScope r1 = (kotlinx.coroutines.CoroutineScope) r1
            r2 = 0
            r3 = 0
            kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$1 r9 = new kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$1
            kotlinx.coroutines.flow.internal.SendingCollector r4 = r0.$collector$inlined
            kotlinx.coroutines.sync.Semaphore r0 = r0.$semaphore$inlined
            r5 = 0
            r9.<init>(r8, r4, r0, r5)
            r4 = r9
            kotlin.jvm.functions.Function2 r4 = (kotlin.jvm.functions.Function2) r4
            r5 = 3
            r6 = 0
            kotlinx.coroutines.BuildersKt.launch$default(r1, r2, r3, r4, r5, r6)
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
