package kotlin.reflect.jvm;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;

/* JADX INFO: compiled from: KTypesJvm.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\"\u001c\u0010\u0000\u001a\u0006\u0012\u0002\b\u00030\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\"\u0010\u0000\u001a\u0006\u0012\u0002\b\u00030\u0001*\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0006\u0010\u0007\u001a\u0004\b\u0003\u0010\b¨\u0006\t"}, d2 = {"jvmErasure", "Lkotlin/reflect/KClass;", "Lkotlin/reflect/KClassifier;", "getJvmErasure", "(Lkotlin/reflect/KClassifier;)Lkotlin/reflect/KClass;", "Lkotlin/reflect/KType;", "getJvmErasure$annotations", "(Lkotlin/reflect/KType;)V", "(Lkotlin/reflect/KType;)Lkotlin/reflect/KClass;", "kotlin-reflection"}, k = 2, mv = {1, 4, 1})
public final class KTypesJvm {
    public static /* synthetic */ void getJvmErasure$annotations(KType kType) {
    }

    public static final KClass<?> getJvmErasure(KType jvmErasure) {
        KClass<?> jvmErasure2;
        Intrinsics.checkNotNullParameter(jvmErasure, "$this$jvmErasure");
        KClassifier classifier = jvmErasure.getClassifier();
        if (classifier != null && (jvmErasure2 = getJvmErasure(classifier)) != null) {
            return jvmErasure2;
        }
        throw new KotlinReflectionInternalError("Cannot calculate JVM erasure for type: " + jvmErasure);
    }

    public static final KClass<?> getJvmErasure(KClassifier jvmErasure) {
        Object obj;
        KClass<?> jvmErasure2;
        Intrinsics.checkNotNullParameter(jvmErasure, "$this$jvmErasure");
        if (jvmErasure instanceof KClass) {
            return (KClass) jvmErasure;
        }
        if (jvmErasure instanceof KTypeParameter) {
            List<KType> upperBounds = ((KTypeParameter) jvmErasure).getUpperBounds();
            Iterator<T> it = upperBounds.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                KType kType = (KType) next;
                Objects.requireNonNull(kType, "null cannot be cast to non-null type kotlin.reflect.jvm.internal.KTypeImpl");
                ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = ((KTypeImpl) kType).getType().getConstructor().mo1677getDeclarationDescriptor();
                ClassDescriptor classDescriptor = (ClassDescriptor) (classifierDescriptorMo1677getDeclarationDescriptor instanceof ClassDescriptor ? classifierDescriptorMo1677getDeclarationDescriptor : null);
                if ((classDescriptor == null || classDescriptor.getKind() == ClassKind.INTERFACE || classDescriptor.getKind() == ClassKind.ANNOTATION_CLASS) ? false : true) {
                    obj = next;
                    break;
                }
            }
            KType kType2 = (KType) obj;
            if (kType2 == null) {
                kType2 = (KType) CollectionsKt.firstOrNull((List) upperBounds);
            }
            return (kType2 == null || (jvmErasure2 = getJvmErasure(kType2)) == null) ? Reflection.getOrCreateKotlinClass(Object.class) : jvmErasure2;
        }
        throw new KotlinReflectionInternalError("Cannot calculate JVM erasure for type: " + jvmErasure);
    }
}
