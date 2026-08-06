package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: AnnotationTypeQualifierResolver.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class AnnotationTypeQualifierResolverKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isAnnotatedWithTypeQualifier(ClassDescriptor classDescriptor) {
        return AnnotationQualifiersFqNamesKt.getBUILT_IN_TYPE_QUALIFIER_FQ_NAMES().contains(DescriptorUtilsKt.getFqNameSafe(classDescriptor)) || classDescriptor.getAnnotations().hasAnnotation(AnnotationQualifiersFqNamesKt.getTYPE_QUALIFIER_FQNAME());
    }
}
