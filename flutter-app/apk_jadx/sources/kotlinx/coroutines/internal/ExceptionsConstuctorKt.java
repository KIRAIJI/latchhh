package kotlinx.coroutines.internal;

import androidx.exifinterface.media.ExifInterface;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.Iterator;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CopyableThrowable;

/* JADX INFO: compiled from: ExceptionsConstuctor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u001a*\u0010\n\u001a\u0018\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010\u0006j\u0004\u0018\u0001`\u00072\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\fH\u0002\u001a1\u0010\r\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0006j\u0002`\u00072\u0014\b\u0004\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0006H\u0082\b\u001a!\u0010\u000f\u001a\u0004\u0018\u0001H\u0010\"\b\b\u0000\u0010\u0010*\u00020\u00052\u0006\u0010\u0011\u001a\u0002H\u0010H\u0000¢\u0006\u0002\u0010\u0012\u001a\u001b\u0010\u0013\u001a\u00020\t*\u0006\u0012\u0002\b\u00030\u00042\b\b\u0002\u0010\u0014\u001a\u00020\tH\u0082\u0010\u001a\u0018\u0010\u0015\u001a\u00020\t*\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0016\u001a\u00020\tH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"4\u0010\u0002\u001a(\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0006j\u0002`\u00070\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000*(\b\u0002\u0010\u0017\"\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00062\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0006¨\u0006\u0018"}, d2 = {"cacheLock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "exceptionCtors", "Ljava/util/WeakHashMap;", "Ljava/lang/Class;", "", "Lkotlin/Function1;", "Lkotlinx/coroutines/internal/Ctor;", "throwableFields", "", "createConstructor", "constructor", "Ljava/lang/reflect/Constructor;", "safeCtor", "block", "tryCopyException", ExifInterface.LONGITUDE_EAST, "exception", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "fieldsCount", "accumulator", "fieldsCountOrDefault", "defaultValue", "Ctor", "kotlinx-coroutines-core"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class ExceptionsConstuctorKt {
    private static final int throwableFields = fieldsCountOrDefault(Throwable.class, -1);
    private static final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
    private static final WeakHashMap<Class<? extends Throwable>, Function1<Throwable, Throwable>> exceptionCtors = new WeakHashMap<>();

    public static final <E extends Throwable> E tryCopyException(E e) {
        Object objM386constructorimpl;
        ReentrantReadWriteLock.ReadLock lock;
        int readHoldCount;
        ReentrantReadWriteLock.WriteLock writeLock;
        if (e instanceof CopyableThrowable) {
            try {
                Result.Companion companion = Result.INSTANCE;
                objM386constructorimpl = Result.m386constructorimpl(((CopyableThrowable) e).createCopy());
            } catch (Throwable th) {
                Result.Companion companion2 = Result.INSTANCE;
                objM386constructorimpl = Result.m386constructorimpl(ResultKt.createFailure(th));
            }
            return (E) (Result.m392isFailureimpl(objM386constructorimpl) ? null : objM386constructorimpl);
        }
        ReentrantReadWriteLock reentrantReadWriteLock = cacheLock;
        ReentrantReadWriteLock.ReadLock lock2 = reentrantReadWriteLock.readLock();
        lock2.lock();
        try {
            Function1<Throwable, Throwable> function1 = exceptionCtors.get(e.getClass());
            if (function1 != null) {
                return (E) function1.invoke(e);
            }
            int i = 0;
            if (throwableFields != fieldsCountOrDefault(e.getClass(), 0)) {
                lock = reentrantReadWriteLock.readLock();
                readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
                for (int i2 = 0; i2 < readHoldCount; i2++) {
                    lock.unlock();
                }
                writeLock = reentrantReadWriteLock.writeLock();
                writeLock.lock();
                try {
                    exceptionCtors.put(e.getClass(), new Function1() { // from class: kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$4$1
                        @Override // kotlin.jvm.functions.Function1
                        public final Void invoke(Throwable th2) {
                            return null;
                        }
                    });
                    Unit unit = Unit.INSTANCE;
                    return null;
                } finally {
                    while (i < readHoldCount) {
                        lock.lock();
                        i++;
                    }
                    writeLock.unlock();
                }
            }
            Iterator it = ArraysKt.sortedWith(e.getClass().getConstructors(), new Comparator() { // from class: kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$$inlined$sortedByDescending$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(Integer.valueOf(((Constructor) t2).getParameterTypes().length), Integer.valueOf(((Constructor) t).getParameterTypes().length));
                }
            }).iterator();
            Function1<Throwable, Throwable> function1CreateConstructor = null;
            while (it.hasNext() && (function1CreateConstructor = createConstructor((Constructor) it.next())) == null) {
            }
            ReentrantReadWriteLock reentrantReadWriteLock2 = cacheLock;
            lock = reentrantReadWriteLock2.readLock();
            readHoldCount = reentrantReadWriteLock2.getWriteHoldCount() == 0 ? reentrantReadWriteLock2.getReadHoldCount() : 0;
            for (int i3 = 0; i3 < readHoldCount; i3++) {
                lock.unlock();
            }
            writeLock = reentrantReadWriteLock2.writeLock();
            writeLock.lock();
            try {
                exceptionCtors.put(e.getClass(), function1CreateConstructor == null ? new Function1() { // from class: kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$5$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Void invoke(Throwable th2) {
                        return null;
                    }
                } : function1CreateConstructor);
                Unit unit2 = Unit.INSTANCE;
                while (i < readHoldCount) {
                    lock.lock();
                    i++;
                }
                writeLock.unlock();
                if (function1CreateConstructor == null) {
                    return null;
                }
                return (E) function1CreateConstructor.invoke(e);
            } finally {
                while (i < readHoldCount) {
                    lock.lock();
                    i++;
                }
                writeLock.unlock();
            }
        } finally {
            lock2.unlock();
        }
    }

    private static final Function1<Throwable, Throwable> createConstructor(final Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        int length = parameterTypes.length;
        if (length == 0) {
            return new Function1<Throwable, Throwable>() { // from class: kotlinx.coroutines.internal.ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Throwable invoke(Throwable th) {
                    Object objM386constructorimpl;
                    Object objNewInstance;
                    try {
                        Result.Companion companion = Result.INSTANCE;
                        objNewInstance = constructor.newInstance(new Object[0]);
                    } catch (Throwable th2) {
                        Result.Companion companion2 = Result.INSTANCE;
                        objM386constructorimpl = Result.m386constructorimpl(ResultKt.createFailure(th2));
                    }
                    if (objNewInstance != null) {
                        Throwable th3 = (Throwable) objNewInstance;
                        th3.initCause(th);
                        objM386constructorimpl = Result.m386constructorimpl(th3);
                        if (Result.m392isFailureimpl(objM386constructorimpl)) {
                            objM386constructorimpl = null;
                        }
                        return (Throwable) objM386constructorimpl;
                    }
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
                }
            };
        }
        if (length != 1) {
            if (length == 2 && Intrinsics.areEqual(parameterTypes[0], String.class) && Intrinsics.areEqual(parameterTypes[1], Throwable.class)) {
                return new Function1<Throwable, Throwable>() { // from class: kotlinx.coroutines.internal.ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Throwable invoke(Throwable th) {
                        Object objM386constructorimpl;
                        Object objNewInstance;
                        try {
                            Result.Companion companion = Result.INSTANCE;
                            objNewInstance = constructor.newInstance(th.getMessage(), th);
                        } catch (Throwable th2) {
                            Result.Companion companion2 = Result.INSTANCE;
                            objM386constructorimpl = Result.m386constructorimpl(ResultKt.createFailure(th2));
                        }
                        if (objNewInstance != null) {
                            objM386constructorimpl = Result.m386constructorimpl((Throwable) objNewInstance);
                            if (Result.m392isFailureimpl(objM386constructorimpl)) {
                                objM386constructorimpl = null;
                            }
                            return (Throwable) objM386constructorimpl;
                        }
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
                    }
                };
            }
            return null;
        }
        Class<?> cls = parameterTypes[0];
        if (!Intrinsics.areEqual(cls, Throwable.class)) {
            if (Intrinsics.areEqual(cls, String.class)) {
                return new Function1<Throwable, Throwable>() { // from class: kotlinx.coroutines.internal.ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Throwable invoke(Throwable th) {
                        Object objM386constructorimpl;
                        Object objNewInstance;
                        try {
                            Result.Companion companion = Result.INSTANCE;
                            objNewInstance = constructor.newInstance(th.getMessage());
                        } catch (Throwable th2) {
                            Result.Companion companion2 = Result.INSTANCE;
                            objM386constructorimpl = Result.m386constructorimpl(ResultKt.createFailure(th2));
                        }
                        if (objNewInstance != null) {
                            Throwable th3 = (Throwable) objNewInstance;
                            th3.initCause(th);
                            objM386constructorimpl = Result.m386constructorimpl(th3);
                            if (Result.m392isFailureimpl(objM386constructorimpl)) {
                                objM386constructorimpl = null;
                            }
                            return (Throwable) objM386constructorimpl;
                        }
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
                    }
                };
            }
            return null;
        }
        return new Function1<Throwable, Throwable>() { // from class: kotlinx.coroutines.internal.ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Throwable invoke(Throwable th) {
                Object objM386constructorimpl;
                Object objNewInstance;
                try {
                    Result.Companion companion = Result.INSTANCE;
                    objNewInstance = constructor.newInstance(th);
                } catch (Throwable th2) {
                    Result.Companion companion2 = Result.INSTANCE;
                    objM386constructorimpl = Result.m386constructorimpl(ResultKt.createFailure(th2));
                }
                if (objNewInstance != null) {
                    objM386constructorimpl = Result.m386constructorimpl((Throwable) objNewInstance);
                    if (Result.m392isFailureimpl(objM386constructorimpl)) {
                        objM386constructorimpl = null;
                    }
                    return (Throwable) objM386constructorimpl;
                }
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
            }
        };
    }

    private static final Function1<Throwable, Throwable> safeCtor(final Function1<? super Throwable, ? extends Throwable> function1) {
        return new Function1<Throwable, Throwable>() { // from class: kotlinx.coroutines.internal.ExceptionsConstuctorKt.safeCtor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Throwable invoke(Throwable th) {
                Object objM386constructorimpl;
                Function1<Throwable, Throwable> function12 = function1;
                try {
                    Result.Companion companion = Result.INSTANCE;
                    objM386constructorimpl = Result.m386constructorimpl(function12.invoke(th));
                } catch (Throwable th2) {
                    Result.Companion companion2 = Result.INSTANCE;
                    objM386constructorimpl = Result.m386constructorimpl(ResultKt.createFailure(th2));
                }
                if (Result.m392isFailureimpl(objM386constructorimpl)) {
                    objM386constructorimpl = null;
                }
                return (Throwable) objM386constructorimpl;
            }
        };
    }

    private static final int fieldsCountOrDefault(Class<?> cls, int i) {
        Object objM386constructorimpl;
        JvmClassMappingKt.getKotlinClass(cls);
        try {
            Result.Companion companion = Result.INSTANCE;
            objM386constructorimpl = Result.m386constructorimpl(Integer.valueOf(fieldsCount$default(cls, 0, 1, null)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM386constructorimpl = Result.m386constructorimpl(ResultKt.createFailure(th));
        }
        Integer numValueOf = Integer.valueOf(i);
        if (Result.m392isFailureimpl(objM386constructorimpl)) {
            objM386constructorimpl = numValueOf;
        }
        return ((Number) objM386constructorimpl).intValue();
    }

    static /* synthetic */ int fieldsCount$default(Class cls, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return fieldsCount(cls, i);
    }

    private static final int fieldsCount(Class<?> cls, int i) {
        do {
            int length = cls.getDeclaredFields().length;
            int i2 = 0;
            for (int i3 = 0; i3 < length; i3++) {
                if (!Modifier.isStatic(r0[i3].getModifiers())) {
                    i2++;
                }
            }
            i += i2;
            cls = cls.getSuperclass();
        } while (cls != null);
        return i;
    }
}
