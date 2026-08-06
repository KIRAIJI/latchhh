package kotlin.reflect.full;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KParameter;

/* JADX INFO: compiled from: KCallables.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u001a9\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\u00022\u0016\u0010\u0011\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00130\u0012\"\u0004\u0018\u00010\u0013H\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a7\u0010\u0015\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\u00022\u0014\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u0016H\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u0017\u001a\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0019\u001a\u00020\u001aH\u0007\"$\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"$\u0010\u0007\u001a\u0004\u0018\u00010\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\b\u0010\u0004\u001a\u0004\b\t\u0010\u0006\"(\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\f\u0010\u0004\u001a\u0004\b\r\u0010\u000e\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001b"}, d2 = {"extensionReceiverParameter", "Lkotlin/reflect/KParameter;", "Lkotlin/reflect/KCallable;", "getExtensionReceiverParameter$annotations", "(Lkotlin/reflect/KCallable;)V", "getExtensionReceiverParameter", "(Lkotlin/reflect/KCallable;)Lkotlin/reflect/KParameter;", "instanceParameter", "getInstanceParameter$annotations", "getInstanceParameter", "valueParameters", "", "getValueParameters$annotations", "getValueParameters", "(Lkotlin/reflect/KCallable;)Ljava/util/List;", "callSuspend", "R", "args", "", "", "(Lkotlin/reflect/KCallable;[Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "callSuspendBy", "", "(Lkotlin/reflect/KCallable;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findParameterByName", "name", "", "kotlin-reflection"}, k = 2, mv = {1, 4, 1})
public final class KCallables {

    /* JADX INFO: renamed from: kotlin.reflect.full.KCallables$callSuspend$1, reason: invalid class name */
    /* JADX INFO: compiled from: KCallables.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0016\u0010\u0004\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0005\"\u0004\u0018\u00010\u00012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u0087@"}, d2 = {"callSuspend", "", "R", "Lkotlin/reflect/KCallable;", "args", "", "continuation", "Lkotlin/coroutines/Continuation;"}, k = 3, mv = {1, 4, 1})
    @DebugMetadata(c = "kotlin.reflect.full.KCallables", f = "KCallables.kt", i = {0}, l = {55}, m = "callSuspend", n = {"$this$callSuspend"}, s = {"L$0"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KCallables.callSuspend(null, null, this);
        }
    }

    /* JADX INFO: renamed from: kotlin.reflect.full.KCallables$callSuspendBy$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: KCallables.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0014\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0087@"}, d2 = {"callSuspendBy", "", "R", "Lkotlin/reflect/KCallable;", "args", "", "Lkotlin/reflect/KParameter;", "continuation", "Lkotlin/coroutines/Continuation;"}, k = 3, mv = {1, 4, 1})
    @DebugMetadata(c = "kotlin.reflect.full.KCallables", f = "KCallables.kt", i = {0}, l = {73}, m = "callSuspendBy", n = {"$this$callSuspendBy"}, s = {"L$0"})
    static final class C01341 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        C01341(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KCallables.callSuspendBy(null, null, this);
        }
    }

    public static /* synthetic */ void getExtensionReceiverParameter$annotations(KCallable kCallable) {
    }

    public static /* synthetic */ void getInstanceParameter$annotations(KCallable kCallable) {
    }

    public static /* synthetic */ void getValueParameters$annotations(KCallable kCallable) {
    }

    public static final KParameter getInstanceParameter(KCallable<?> instanceParameter) {
        Intrinsics.checkNotNullParameter(instanceParameter, "$this$instanceParameter");
        Iterator<T> it = instanceParameter.getParameters().iterator();
        Object obj = null;
        boolean z = false;
        Object obj2 = null;
        while (true) {
            if (it.hasNext()) {
                Object next = it.next();
                if (((KParameter) next).getKind() == KParameter.Kind.INSTANCE) {
                    if (z) {
                        break;
                    }
                    obj2 = next;
                    z = true;
                }
            } else if (z) {
                obj = obj2;
            }
        }
        return (KParameter) obj;
    }

    public static final KParameter getExtensionReceiverParameter(KCallable<?> extensionReceiverParameter) {
        Intrinsics.checkNotNullParameter(extensionReceiverParameter, "$this$extensionReceiverParameter");
        Iterator<T> it = extensionReceiverParameter.getParameters().iterator();
        Object obj = null;
        boolean z = false;
        Object obj2 = null;
        while (true) {
            if (it.hasNext()) {
                Object next = it.next();
                if (((KParameter) next).getKind() == KParameter.Kind.EXTENSION_RECEIVER) {
                    if (z) {
                        break;
                    }
                    obj2 = next;
                    z = true;
                }
            } else if (z) {
                obj = obj2;
            }
        }
        return (KParameter) obj;
    }

    public static final List<KParameter> getValueParameters(KCallable<?> valueParameters) {
        Intrinsics.checkNotNullParameter(valueParameters, "$this$valueParameters");
        List<KParameter> parameters = valueParameters.getParameters();
        ArrayList arrayList = new ArrayList();
        for (Object obj : parameters) {
            if (((KParameter) obj).getKind() == KParameter.Kind.VALUE) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final KParameter findParameterByName(KCallable<?> findParameterByName, String name) {
        Intrinsics.checkNotNullParameter(findParameterByName, "$this$findParameterByName");
        Intrinsics.checkNotNullParameter(name, "name");
        Iterator<T> it = findParameterByName.getParameters().iterator();
        Object obj = null;
        boolean z = false;
        Object obj2 = null;
        while (true) {
            if (it.hasNext()) {
                Object next = it.next();
                if (Intrinsics.areEqual(((KParameter) next).getName(), name)) {
                    if (z) {
                        break;
                    }
                    z = true;
                    obj2 = next;
                }
            } else if (z) {
                obj = obj2;
            }
        }
        return (KParameter) obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final <R> java.lang.Object callSuspend(kotlin.reflect.KCallable<? extends R> r4, java.lang.Object[] r5, kotlin.coroutines.Continuation<? super R> r6) throws java.lang.Throwable {
        /*
            boolean r0 = r6 instanceof kotlin.reflect.full.KCallables.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r6
            kotlin.reflect.full.KCallables$callSuspend$1 r0 = (kotlin.reflect.full.KCallables.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r6 = r0.label
            int r6 = r6 - r2
            r0.label = r6
            goto L19
        L14:
            kotlin.reflect.full.KCallables$callSuspend$1 r0 = new kotlin.reflect.full.KCallables$callSuspend$1
            r0.<init>(r6)
        L19:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3a
            if (r2 != r3) goto L32
            java.lang.Object r4 = r0.L$1
            java.lang.Object[] r4 = (java.lang.Object[]) r4
            java.lang.Object r4 = r0.L$0
            kotlin.reflect.KCallable r4 = (kotlin.reflect.KCallable) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L7f
        L32:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L3a:
            kotlin.ResultKt.throwOnFailure(r6)
            boolean r6 = r4.isSuspend()
            if (r6 != 0) goto L4d
            int r6 = r5.length
            java.lang.Object[] r5 = java.util.Arrays.copyOf(r5, r6)
            java.lang.Object r4 = r4.call(r5)
            return r4
        L4d:
            boolean r6 = r4 instanceof kotlin.reflect.KFunction
            if (r6 == 0) goto La3
            r0.L$0 = r4
            r0.L$1 = r5
            r0.label = r3
            kotlin.coroutines.Continuation r0 = (kotlin.coroutines.Continuation) r0
            kotlin.jvm.internal.SpreadBuilder r6 = new kotlin.jvm.internal.SpreadBuilder
            r2 = 2
            r6.<init>(r2)
            r6.addSpread(r5)
            r6.add(r0)
            int r5 = r6.size()
            java.lang.Object[] r5 = new java.lang.Object[r5]
            java.lang.Object[] r5 = r6.toArray(r5)
            java.lang.Object r6 = r4.call(r5)
            java.lang.Object r5 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r6 != r5) goto L7c
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r0)
        L7c:
            if (r6 != r1) goto L7f
            return r1
        L7f:
            kotlin.reflect.KType r5 = r4.getReturnType()
            kotlin.reflect.KClassifier r5 = r5.getClassifier()
            java.lang.Class<kotlin.Unit> r0 = kotlin.Unit.class
            kotlin.reflect.KClass r0 = kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r0)
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r0)
            if (r5 == 0) goto La2
            kotlin.reflect.KType r4 = r4.getReturnType()
            boolean r4 = r4.isMarkedNullable()
            if (r4 != 0) goto La2
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            java.lang.Object r4 = (java.lang.Object) r4
            return r4
        La2:
            return r6
        La3:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "Cannot callSuspend on a property "
            r6.append(r0)
            r6.append(r4)
            java.lang.String r4 = ": suspend properties are not supported yet"
            r6.append(r4)
            java.lang.String r4 = r6.toString()
            r5.<init>(r4)
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.full.KCallables.callSuspend(kotlin.reflect.KCallable, java.lang.Object[], kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, kotlin.reflect.jvm.internal.KCallableImpl] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final <R> java.lang.Object callSuspendBy(kotlin.reflect.KCallable<? extends R> r4, java.util.Map<kotlin.reflect.KParameter, ? extends java.lang.Object> r5, kotlin.coroutines.Continuation<? super R> r6) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 214
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.full.KCallables.callSuspendBy(kotlin.reflect.KCallable, java.util.Map, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
