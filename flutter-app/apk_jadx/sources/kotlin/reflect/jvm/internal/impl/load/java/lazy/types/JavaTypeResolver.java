package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMapper;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaAnnotations;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.TypeParameterResolver;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifier;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifierType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPrimitiveType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaWildcardType;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: JavaTypeResolver.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class JavaTypeResolver {
    private final LazyJavaResolverContext c;
    private final TypeParameterResolver typeParameterResolver;

    public JavaTypeResolver(LazyJavaResolverContext c, TypeParameterResolver typeParameterResolver) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(typeParameterResolver, "typeParameterResolver");
        this.c = c;
        this.typeParameterResolver = typeParameterResolver;
    }

    public final KotlinType transformJavaType(JavaType javaType, JavaTypeAttributes attr) {
        SimpleType unitType;
        Intrinsics.checkNotNullParameter(attr, "attr");
        if (javaType instanceof JavaPrimitiveType) {
            PrimitiveType type = ((JavaPrimitiveType) javaType).getType();
            if (type != null) {
                unitType = this.c.getModule().getBuiltIns().getPrimitiveKotlinType(type);
            } else {
                unitType = this.c.getModule().getBuiltIns().getUnitType();
            }
            Intrinsics.checkNotNullExpressionValue(unitType, "{\n                val primitiveType = javaType.type\n                if (primitiveType != null) c.module.builtIns.getPrimitiveKotlinType(primitiveType)\n                else c.module.builtIns.unitType\n            }");
            return unitType;
        }
        if (javaType instanceof JavaClassifierType) {
            return transformJavaClassifierType((JavaClassifierType) javaType, attr);
        }
        if (javaType instanceof JavaArrayType) {
            return transformArrayType$default(this, (JavaArrayType) javaType, attr, false, 4, null);
        }
        if (!(javaType instanceof JavaWildcardType)) {
            if (javaType == null) {
                SimpleType defaultBound = this.c.getModule().getBuiltIns().getDefaultBound();
                Intrinsics.checkNotNullExpressionValue(defaultBound, "c.module.builtIns.defaultBound");
                return defaultBound;
            }
            throw new UnsupportedOperationException(Intrinsics.stringPlus("Unsupported type: ", javaType));
        }
        JavaType bound = ((JavaWildcardType) javaType).getBound();
        KotlinType kotlinTypeTransformJavaType = bound == null ? null : transformJavaType(bound, attr);
        if (kotlinTypeTransformJavaType != null) {
            return kotlinTypeTransformJavaType;
        }
        SimpleType defaultBound2 = this.c.getModule().getBuiltIns().getDefaultBound();
        Intrinsics.checkNotNullExpressionValue(defaultBound2, "c.module.builtIns.defaultBound");
        return defaultBound2;
    }

    public static /* synthetic */ KotlinType transformArrayType$default(JavaTypeResolver javaTypeResolver, JavaArrayType javaArrayType, JavaTypeAttributes javaTypeAttributes, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return javaTypeResolver.transformArrayType(javaArrayType, javaTypeAttributes, z);
    }

    public final KotlinType transformArrayType(JavaArrayType arrayType, JavaTypeAttributes attr, boolean z) {
        Intrinsics.checkNotNullParameter(arrayType, "arrayType");
        Intrinsics.checkNotNullParameter(attr, "attr");
        JavaType componentType = arrayType.getComponentType();
        JavaPrimitiveType javaPrimitiveType = componentType instanceof JavaPrimitiveType ? (JavaPrimitiveType) componentType : null;
        PrimitiveType type = javaPrimitiveType == null ? null : javaPrimitiveType.getType();
        LazyJavaAnnotations lazyJavaAnnotations = new LazyJavaAnnotations(this.c, arrayType, true);
        if (type != null) {
            SimpleType primitiveArrayKotlinType = this.c.getModule().getBuiltIns().getPrimitiveArrayKotlinType(type);
            Intrinsics.checkNotNullExpressionValue(primitiveArrayKotlinType, "c.module.builtIns.getPrimitiveArrayKotlinType(primitiveType)");
            primitiveArrayKotlinType.replaceAnnotations(Annotations.Companion.create(CollectionsKt.plus((Iterable) lazyJavaAnnotations, (Iterable) primitiveArrayKotlinType.getAnnotations())));
            if (attr.isForAnnotationParameter()) {
                return primitiveArrayKotlinType;
            }
            KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
            return KotlinTypeFactory.flexibleType(primitiveArrayKotlinType, primitiveArrayKotlinType.makeNullableAsSpecified(true));
        }
        KotlinType kotlinTypeTransformJavaType = transformJavaType(componentType, JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, attr.isForAnnotationParameter(), null, 2, null));
        if (attr.isForAnnotationParameter()) {
            SimpleType arrayType2 = this.c.getModule().getBuiltIns().getArrayType(z ? Variance.OUT_VARIANCE : Variance.INVARIANT, kotlinTypeTransformJavaType, lazyJavaAnnotations);
            Intrinsics.checkNotNullExpressionValue(arrayType2, "c.module.builtIns.getArrayType(projectionKind, componentType, annotations)");
            return arrayType2;
        }
        KotlinTypeFactory kotlinTypeFactory2 = KotlinTypeFactory.INSTANCE;
        LazyJavaAnnotations lazyJavaAnnotations2 = lazyJavaAnnotations;
        SimpleType arrayType3 = this.c.getModule().getBuiltIns().getArrayType(Variance.INVARIANT, kotlinTypeTransformJavaType, lazyJavaAnnotations2);
        Intrinsics.checkNotNullExpressionValue(arrayType3, "c.module.builtIns.getArrayType(INVARIANT, componentType, annotations)");
        return KotlinTypeFactory.flexibleType(arrayType3, this.c.getModule().getBuiltIns().getArrayType(Variance.OUT_VARIANCE, kotlinTypeTransformJavaType, lazyJavaAnnotations2).makeNullableAsSpecified(true));
    }

    private static final SimpleType transformJavaClassifierType$errorType(JavaClassifierType javaClassifierType) {
        SimpleType simpleTypeCreateErrorType = ErrorUtils.createErrorType(Intrinsics.stringPlus("Unresolved java class ", javaClassifierType.getPresentableText()));
        Intrinsics.checkNotNullExpressionValue(simpleTypeCreateErrorType, "createErrorType(\"Unresolved java class ${javaType.presentableText}\")");
        return simpleTypeCreateErrorType;
    }

    private final KotlinType transformJavaClassifierType(JavaClassifierType javaClassifierType, JavaTypeAttributes javaTypeAttributes) {
        boolean z = (javaTypeAttributes.isForAnnotationParameter() || javaTypeAttributes.getHowThisTypeIsUsed() == TypeUsage.SUPERTYPE) ? false : true;
        boolean zIsRaw = javaClassifierType.isRaw();
        if (!zIsRaw && !z) {
            SimpleType simpleTypeComputeSimpleJavaClassifierType = computeSimpleJavaClassifierType(javaClassifierType, javaTypeAttributes, null);
            return simpleTypeComputeSimpleJavaClassifierType == null ? transformJavaClassifierType$errorType(javaClassifierType) : simpleTypeComputeSimpleJavaClassifierType;
        }
        SimpleType simpleTypeComputeSimpleJavaClassifierType2 = computeSimpleJavaClassifierType(javaClassifierType, javaTypeAttributes.withFlexibility(JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND), null);
        if (simpleTypeComputeSimpleJavaClassifierType2 == null) {
            return transformJavaClassifierType$errorType(javaClassifierType);
        }
        SimpleType simpleTypeComputeSimpleJavaClassifierType3 = computeSimpleJavaClassifierType(javaClassifierType, javaTypeAttributes.withFlexibility(JavaTypeFlexibility.FLEXIBLE_UPPER_BOUND), simpleTypeComputeSimpleJavaClassifierType2);
        if (simpleTypeComputeSimpleJavaClassifierType3 == null) {
            return transformJavaClassifierType$errorType(javaClassifierType);
        }
        if (zIsRaw) {
            return new RawTypeImpl(simpleTypeComputeSimpleJavaClassifierType2, simpleTypeComputeSimpleJavaClassifierType3);
        }
        KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
        return KotlinTypeFactory.flexibleType(simpleTypeComputeSimpleJavaClassifierType2, simpleTypeComputeSimpleJavaClassifierType3);
    }

    private final SimpleType computeSimpleJavaClassifierType(JavaClassifierType javaClassifierType, JavaTypeAttributes javaTypeAttributes, SimpleType simpleType) {
        LazyJavaAnnotations annotations = simpleType == null ? null : simpleType.getAnnotations();
        if (annotations == null) {
            annotations = new LazyJavaAnnotations(this.c, javaClassifierType, false, 4, null);
        }
        Annotations annotations2 = annotations;
        TypeConstructor typeConstructorComputeTypeConstructor = computeTypeConstructor(javaClassifierType, javaTypeAttributes);
        if (typeConstructorComputeTypeConstructor == null) {
            return null;
        }
        boolean zIsNullable = isNullable(javaTypeAttributes);
        if (Intrinsics.areEqual(simpleType != null ? simpleType.getConstructor() : null, typeConstructorComputeTypeConstructor) && !javaClassifierType.isRaw() && zIsNullable) {
            return simpleType.makeNullableAsSpecified(true);
        }
        List<TypeProjection> listComputeArguments = computeArguments(javaClassifierType, javaTypeAttributes, typeConstructorComputeTypeConstructor);
        KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
        return KotlinTypeFactory.simpleType$default(annotations2, typeConstructorComputeTypeConstructor, listComputeArguments, zIsNullable, null, 16, null);
    }

    private final TypeConstructor computeTypeConstructor(JavaClassifierType javaClassifierType, JavaTypeAttributes javaTypeAttributes) {
        JavaClassifier classifier = javaClassifierType.getClassifier();
        if (classifier == null) {
            return createNotFoundClass(javaClassifierType);
        }
        if (classifier instanceof JavaClass) {
            JavaClass javaClass = (JavaClass) classifier;
            FqName fqName = javaClass.getFqName();
            if (fqName == null) {
                throw new AssertionError(Intrinsics.stringPlus("Class type should have a FQ name: ", classifier));
            }
            ClassDescriptor classDescriptorMapKotlinClass = mapKotlinClass(javaClassifierType, javaTypeAttributes, fqName);
            if (classDescriptorMapKotlinClass == null) {
                classDescriptorMapKotlinClass = this.c.getComponents().getModuleClassResolver().resolveClass(javaClass);
            }
            TypeConstructor typeConstructor = classDescriptorMapKotlinClass != null ? classDescriptorMapKotlinClass.getTypeConstructor() : null;
            return typeConstructor == null ? createNotFoundClass(javaClassifierType) : typeConstructor;
        }
        if (classifier instanceof JavaTypeParameter) {
            TypeParameterDescriptor typeParameterDescriptorResolveTypeParameter = this.typeParameterResolver.resolveTypeParameter((JavaTypeParameter) classifier);
            if (typeParameterDescriptorResolveTypeParameter == null) {
                return null;
            }
            return typeParameterDescriptorResolveTypeParameter.getTypeConstructor();
        }
        throw new IllegalStateException(Intrinsics.stringPlus("Unknown classifier kind: ", classifier));
    }

    private final TypeConstructor createNotFoundClass(JavaClassifierType javaClassifierType) {
        ClassId classId = ClassId.topLevel(new FqName(javaClassifierType.getClassifierQualifiedName()));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(FqName(javaType.classifierQualifiedName))");
        TypeConstructor typeConstructor = this.c.getComponents().getDeserializedDescriptorResolver().getComponents().getNotFoundClasses().getClass(classId, CollectionsKt.listOf(0)).getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor, "c.components.deserializedDescriptorResolver.components.notFoundClasses.getClass(classId, listOf(0)).typeConstructor");
        return typeConstructor;
    }

    private final ClassDescriptor mapKotlinClass(JavaClassifierType javaClassifierType, JavaTypeAttributes javaTypeAttributes, FqName fqName) {
        if (javaTypeAttributes.isForAnnotationParameter() && Intrinsics.areEqual(fqName, JavaTypeResolverKt.JAVA_LANG_CLASS_FQ_NAME)) {
            return this.c.getComponents().getReflectionTypes().getKClass();
        }
        JavaToKotlinClassMapper javaToKotlinClassMapper = JavaToKotlinClassMapper.INSTANCE;
        ClassDescriptor classDescriptorMapJavaToKotlin$default = JavaToKotlinClassMapper.mapJavaToKotlin$default(javaToKotlinClassMapper, fqName, this.c.getModule().getBuiltIns(), null, 4, null);
        if (classDescriptorMapJavaToKotlin$default == null) {
            return null;
        }
        return (javaToKotlinClassMapper.isReadOnly(classDescriptorMapJavaToKotlin$default) && (javaTypeAttributes.getFlexibility() == JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND || javaTypeAttributes.getHowThisTypeIsUsed() == TypeUsage.SUPERTYPE || argumentsMakeSenseOnlyForMutableContainer(javaClassifierType, classDescriptorMapJavaToKotlin$default))) ? javaToKotlinClassMapper.convertReadOnlyToMutable(classDescriptorMapJavaToKotlin$default) : classDescriptorMapJavaToKotlin$default;
    }

    private static final boolean argumentsMakeSenseOnlyForMutableContainer$isSuperWildcard(JavaType javaType) {
        JavaWildcardType javaWildcardType = javaType instanceof JavaWildcardType ? (JavaWildcardType) javaType : null;
        return (javaWildcardType == null || javaWildcardType.getBound() == null || javaWildcardType.isExtends()) ? false : true;
    }

    private final boolean argumentsMakeSenseOnlyForMutableContainer(JavaClassifierType javaClassifierType, ClassDescriptor classDescriptor) {
        if (!argumentsMakeSenseOnlyForMutableContainer$isSuperWildcard((JavaType) CollectionsKt.lastOrNull((List) javaClassifierType.getTypeArguments()))) {
            return false;
        }
        List<TypeParameterDescriptor> parameters = JavaToKotlinClassMapper.INSTANCE.convertReadOnlyToMutable(classDescriptor).getTypeConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "JavaToKotlinClassMapper.convertReadOnlyToMutable(readOnlyContainer)\n            .typeConstructor.parameters");
        TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) CollectionsKt.lastOrNull((List) parameters);
        Variance variance = typeParameterDescriptor == null ? null : typeParameterDescriptor.getVariance();
        return (variance == null || variance == Variance.OUT_VARIANCE) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final java.util.List<kotlin.reflect.jvm.internal.impl.types.TypeProjection> computeArguments(kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifierType r8, final kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeAttributes r9, final kotlin.reflect.jvm.internal.impl.types.TypeConstructor r10) {
        /*
            Method dump skipped, instruction units count: 291
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolver.computeArguments(kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifierType, kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeAttributes, kotlin.reflect.jvm.internal.impl.types.TypeConstructor):java.util.List");
    }

    private final TypeProjection transformToTypeProjection(JavaType javaType, JavaTypeAttributes javaTypeAttributes, TypeParameterDescriptor typeParameterDescriptor) {
        if (javaType instanceof JavaWildcardType) {
            JavaWildcardType javaWildcardType = (JavaWildcardType) javaType;
            JavaType bound = javaWildcardType.getBound();
            Variance variance = javaWildcardType.isExtends() ? Variance.OUT_VARIANCE : Variance.IN_VARIANCE;
            if (bound == null || isConflictingArgumentFor(variance, typeParameterDescriptor)) {
                return JavaTypeResolverKt.makeStarProjection(typeParameterDescriptor, javaTypeAttributes);
            }
            return TypeUtilsKt.createProjection(transformJavaType(bound, JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, false, null, 3, null)), variance, typeParameterDescriptor);
        }
        return new TypeProjectionImpl(Variance.INVARIANT, transformJavaType(javaType, javaTypeAttributes));
    }

    private final boolean isConflictingArgumentFor(Variance variance, TypeParameterDescriptor typeParameterDescriptor) {
        return (typeParameterDescriptor.getVariance() == Variance.INVARIANT || variance == typeParameterDescriptor.getVariance()) ? false : true;
    }

    private final boolean isNullable(JavaTypeAttributes javaTypeAttributes) {
        return (javaTypeAttributes.getFlexibility() == JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND || javaTypeAttributes.isForAnnotationParameter() || javaTypeAttributes.getHowThisTypeIsUsed() == TypeUsage.SUPERTYPE) ? false : true;
    }
}
