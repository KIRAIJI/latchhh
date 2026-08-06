package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassOrPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationQualifierApplicabilityType;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver;
import kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultQualifiers;
import kotlin.reflect.jvm.internal.impl.load.java.JavaTypeQualifiersByElementType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameterListOwner;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;
import kotlin.reflect.jvm.internal.impl.utils.ReportLevel;

/* JADX INFO: compiled from: context.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ContextKt {
    public static final LazyJavaResolverContext child(LazyJavaResolverContext lazyJavaResolverContext, TypeParameterResolver typeParameterResolver) {
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "<this>");
        Intrinsics.checkNotNullParameter(typeParameterResolver, "typeParameterResolver");
        return new LazyJavaResolverContext(lazyJavaResolverContext.getComponents(), typeParameterResolver, lazyJavaResolverContext.getDelegateForDefaultTypeQualifiers$descriptors_jvm());
    }

    public static final JavaTypeQualifiersByElementType computeNewDefaultTypeQualifiers(LazyJavaResolverContext lazyJavaResolverContext, Annotations additionalAnnotations) {
        EnumMap<AnnotationQualifierApplicabilityType, JavaDefaultQualifiers> defaultQualifiers;
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "<this>");
        Intrinsics.checkNotNullParameter(additionalAnnotations, "additionalAnnotations");
        if (lazyJavaResolverContext.getComponents().getJavaTypeEnhancementState().getDisabledDefaultAnnotations()) {
            return lazyJavaResolverContext.getDefaultTypeQualifiers();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<AnnotationDescriptor> it = additionalAnnotations.iterator();
        while (it.hasNext()) {
            JavaDefaultQualifiers javaDefaultQualifiersExtractDefaultNullabilityQualifier = extractDefaultNullabilityQualifier(lazyJavaResolverContext, it.next());
            if (javaDefaultQualifiersExtractDefaultNullabilityQualifier != null) {
                arrayList.add(javaDefaultQualifiersExtractDefaultNullabilityQualifier);
            }
        }
        ArrayList<JavaDefaultQualifiers> arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return lazyJavaResolverContext.getDefaultTypeQualifiers();
        }
        JavaTypeQualifiersByElementType defaultTypeQualifiers = lazyJavaResolverContext.getDefaultTypeQualifiers();
        EnumMap enumMap = null;
        if (defaultTypeQualifiers != null && (defaultQualifiers = defaultTypeQualifiers.getDefaultQualifiers()) != null) {
            enumMap = new EnumMap((EnumMap) defaultQualifiers);
        }
        if (enumMap == null) {
            enumMap = new EnumMap(AnnotationQualifierApplicabilityType.class);
        }
        boolean z = false;
        for (JavaDefaultQualifiers javaDefaultQualifiers : arrayList2) {
            Iterator<AnnotationQualifierApplicabilityType> it2 = javaDefaultQualifiers.getQualifierApplicabilityTypes().iterator();
            while (it2.hasNext()) {
                enumMap.put(it2.next(), javaDefaultQualifiers);
                z = true;
            }
        }
        return !z ? lazyJavaResolverContext.getDefaultTypeQualifiers() : new JavaTypeQualifiersByElementType(enumMap);
    }

    private static final JavaDefaultQualifiers extractDefaultNullabilityQualifier(LazyJavaResolverContext lazyJavaResolverContext, AnnotationDescriptor annotationDescriptor) {
        AnnotationTypeQualifierResolver annotationTypeQualifierResolver = lazyJavaResolverContext.getComponents().getAnnotationTypeQualifierResolver();
        JavaDefaultQualifiers javaDefaultQualifiersResolveQualifierBuiltInDefaultAnnotation = annotationTypeQualifierResolver.resolveQualifierBuiltInDefaultAnnotation(annotationDescriptor);
        if (javaDefaultQualifiersResolveQualifierBuiltInDefaultAnnotation != null) {
            return javaDefaultQualifiersResolveQualifierBuiltInDefaultAnnotation;
        }
        AnnotationTypeQualifierResolver.TypeQualifierWithApplicability typeQualifierWithApplicabilityResolveTypeQualifierDefaultAnnotation = annotationTypeQualifierResolver.resolveTypeQualifierDefaultAnnotation(annotationDescriptor);
        if (typeQualifierWithApplicabilityResolveTypeQualifierDefaultAnnotation == null) {
            return null;
        }
        AnnotationDescriptor annotationDescriptorComponent1 = typeQualifierWithApplicabilityResolveTypeQualifierDefaultAnnotation.component1();
        List<AnnotationQualifierApplicabilityType> listComponent2 = typeQualifierWithApplicabilityResolveTypeQualifierDefaultAnnotation.component2();
        ReportLevel reportLevelResolveJsr305CustomState = annotationTypeQualifierResolver.resolveJsr305CustomState(annotationDescriptor);
        if (reportLevelResolveJsr305CustomState == null) {
            reportLevelResolveJsr305CustomState = annotationTypeQualifierResolver.resolveJsr305AnnotationState(annotationDescriptorComponent1);
        }
        if (reportLevelResolveJsr305CustomState.isIgnore()) {
            return null;
        }
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusExtractNullability = lazyJavaResolverContext.getComponents().getSignatureEnhancement().extractNullability(annotationDescriptorComponent1, lazyJavaResolverContext.getComponents().getSettings().getTypeEnhancementImprovements(), false);
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusCopy$default = nullabilityQualifierWithMigrationStatusExtractNullability == null ? null : NullabilityQualifierWithMigrationStatus.copy$default(nullabilityQualifierWithMigrationStatusExtractNullability, null, reportLevelResolveJsr305CustomState.isWarning(), 1, null);
        if (nullabilityQualifierWithMigrationStatusCopy$default == null) {
            return null;
        }
        return new JavaDefaultQualifiers(nullabilityQualifierWithMigrationStatusCopy$default, listComponent2, false, 4, null);
    }

    public static final LazyJavaResolverContext replaceComponents(LazyJavaResolverContext lazyJavaResolverContext, JavaResolverComponents components) {
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "<this>");
        Intrinsics.checkNotNullParameter(components, "components");
        return new LazyJavaResolverContext(components, lazyJavaResolverContext.getTypeParameterResolver(), lazyJavaResolverContext.getDelegateForDefaultTypeQualifiers$descriptors_jvm());
    }

    private static final LazyJavaResolverContext child(LazyJavaResolverContext lazyJavaResolverContext, DeclarationDescriptor declarationDescriptor, JavaTypeParameterListOwner javaTypeParameterListOwner, int i, Lazy<JavaTypeQualifiersByElementType> lazy) {
        LazyJavaTypeParameterResolver typeParameterResolver;
        JavaResolverComponents components = lazyJavaResolverContext.getComponents();
        LazyJavaTypeParameterResolver lazyJavaTypeParameterResolver = javaTypeParameterListOwner == null ? null : new LazyJavaTypeParameterResolver(lazyJavaResolverContext, declarationDescriptor, javaTypeParameterListOwner, i);
        if (lazyJavaTypeParameterResolver != null) {
            typeParameterResolver = lazyJavaTypeParameterResolver;
        } else {
            typeParameterResolver = lazyJavaResolverContext.getTypeParameterResolver();
        }
        return new LazyJavaResolverContext(components, typeParameterResolver, lazy);
    }

    public static /* synthetic */ LazyJavaResolverContext childForMethod$default(LazyJavaResolverContext lazyJavaResolverContext, DeclarationDescriptor declarationDescriptor, JavaTypeParameterListOwner javaTypeParameterListOwner, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return childForMethod(lazyJavaResolverContext, declarationDescriptor, javaTypeParameterListOwner, i);
    }

    public static final LazyJavaResolverContext childForMethod(LazyJavaResolverContext lazyJavaResolverContext, DeclarationDescriptor containingDeclaration, JavaTypeParameterListOwner typeParameterOwner, int i) {
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "<this>");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(typeParameterOwner, "typeParameterOwner");
        return child(lazyJavaResolverContext, containingDeclaration, typeParameterOwner, i, lazyJavaResolverContext.getDelegateForDefaultTypeQualifiers$descriptors_jvm());
    }

    public static /* synthetic */ LazyJavaResolverContext childForClassOrPackage$default(LazyJavaResolverContext lazyJavaResolverContext, ClassOrPackageFragmentDescriptor classOrPackageFragmentDescriptor, JavaTypeParameterListOwner javaTypeParameterListOwner, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            javaTypeParameterListOwner = null;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return childForClassOrPackage(lazyJavaResolverContext, classOrPackageFragmentDescriptor, javaTypeParameterListOwner, i);
    }

    public static final LazyJavaResolverContext childForClassOrPackage(final LazyJavaResolverContext lazyJavaResolverContext, final ClassOrPackageFragmentDescriptor containingDeclaration, JavaTypeParameterListOwner javaTypeParameterListOwner, int i) {
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "<this>");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        return child(lazyJavaResolverContext, containingDeclaration, javaTypeParameterListOwner, i, LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<JavaTypeQualifiersByElementType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt.childForClassOrPackage.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JavaTypeQualifiersByElementType invoke() {
                return ContextKt.computeNewDefaultTypeQualifiers(lazyJavaResolverContext, containingDeclaration.getAnnotations());
            }
        }));
    }

    public static final LazyJavaResolverContext copyWithNewDefaultTypeQualifiers(final LazyJavaResolverContext lazyJavaResolverContext, final Annotations additionalAnnotations) {
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "<this>");
        Intrinsics.checkNotNullParameter(additionalAnnotations, "additionalAnnotations");
        return additionalAnnotations.isEmpty() ? lazyJavaResolverContext : new LazyJavaResolverContext(lazyJavaResolverContext.getComponents(), lazyJavaResolverContext.getTypeParameterResolver(), LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<JavaTypeQualifiersByElementType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt.copyWithNewDefaultTypeQualifiers.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JavaTypeQualifiersByElementType invoke() {
                return ContextKt.computeNewDefaultTypeQualifiers(lazyJavaResolverContext, additionalAnnotations);
            }
        }));
    }
}
