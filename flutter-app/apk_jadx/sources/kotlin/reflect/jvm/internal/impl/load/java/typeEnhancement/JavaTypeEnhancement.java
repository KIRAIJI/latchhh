package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaResolverSettings;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.RawType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: typeEnhancement.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class JavaTypeEnhancement {
    private final JavaResolverSettings javaResolverSettings;

    public JavaTypeEnhancement(JavaResolverSettings javaResolverSettings) {
        Intrinsics.checkNotNullParameter(javaResolverSettings, "javaResolverSettings");
        this.javaResolverSettings = javaResolverSettings;
    }

    /* JADX INFO: compiled from: typeEnhancement.kt */
    private static class Result {
        private final int subtreeSize;
        private final KotlinType type;
        private final boolean wereChanges;

        public Result(KotlinType type, int i, boolean z) {
            Intrinsics.checkNotNullParameter(type, "type");
            this.type = type;
            this.subtreeSize = i;
            this.wereChanges = z;
        }

        public final int getSubtreeSize() {
            return this.subtreeSize;
        }

        public KotlinType getType() {
            return this.type;
        }

        public final boolean getWereChanges() {
            return this.wereChanges;
        }

        public final KotlinType getTypeIfChanged() {
            KotlinType type = getType();
            if (getWereChanges()) {
                return type;
            }
            return null;
        }
    }

    /* JADX INFO: compiled from: typeEnhancement.kt */
    private static final class SimpleResult extends Result {
        private final SimpleType type;

        @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeEnhancement.Result
        public SimpleType getType() {
            return this.type;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SimpleResult(SimpleType type, int i, boolean z) {
            super(type, i, z);
            Intrinsics.checkNotNullParameter(type, "type");
            this.type = type;
        }
    }

    public final KotlinType enhance(KotlinType kotlinType, Function1<? super Integer, JavaTypeQualifiers> qualifiers) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Intrinsics.checkNotNullParameter(qualifiers, "qualifiers");
        return enhancePossiblyFlexible(kotlinType.unwrap(), qualifiers, 0).getTypeIfChanged();
    }

    private final KotlinType buildEnhancementByFlexibleTypeBounds(KotlinType kotlinType, KotlinType kotlinType2) {
        KotlinType enhancement = TypeWithEnhancementKt.getEnhancement(kotlinType2);
        KotlinType enhancement2 = TypeWithEnhancementKt.getEnhancement(kotlinType);
        if (enhancement2 == null) {
            if (enhancement == null) {
                return null;
            }
            enhancement2 = enhancement;
        }
        if (enhancement == null) {
            return enhancement2;
        }
        KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
        return KotlinTypeFactory.flexibleType(FlexibleTypesKt.lowerIfFlexible(enhancement2), FlexibleTypesKt.upperIfFlexible(enhancement));
    }

    private final Result enhancePossiblyFlexible(UnwrappedType unwrappedType, Function1<? super Integer, JavaTypeQualifiers> function1, int i) {
        RawTypeImpl rawTypeImplFlexibleType;
        UnwrappedType unwrappedType2 = unwrappedType;
        if (KotlinTypeKt.isError(unwrappedType2)) {
            return new Result(unwrappedType2, 1, false);
        }
        if (unwrappedType instanceof FlexibleType) {
            boolean z = unwrappedType instanceof RawType;
            FlexibleType flexibleType = (FlexibleType) unwrappedType;
            SimpleResult simpleResultEnhanceInflexible = enhanceInflexible(flexibleType.getLowerBound(), function1, i, TypeComponentPosition.FLEXIBLE_LOWER, z);
            SimpleResult simpleResultEnhanceInflexible2 = enhanceInflexible(flexibleType.getUpperBound(), function1, i, TypeComponentPosition.FLEXIBLE_UPPER, z);
            simpleResultEnhanceInflexible.getSubtreeSize();
            simpleResultEnhanceInflexible2.getSubtreeSize();
            boolean z2 = simpleResultEnhanceInflexible.getWereChanges() || simpleResultEnhanceInflexible2.getWereChanges();
            KotlinType kotlinTypeBuildEnhancementByFlexibleTypeBounds = buildEnhancementByFlexibleTypeBounds(simpleResultEnhanceInflexible.getType(), simpleResultEnhanceInflexible2.getType());
            if (z2) {
                if (unwrappedType instanceof RawTypeImpl) {
                    rawTypeImplFlexibleType = new RawTypeImpl(simpleResultEnhanceInflexible.getType(), simpleResultEnhanceInflexible2.getType());
                } else {
                    KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
                    rawTypeImplFlexibleType = KotlinTypeFactory.flexibleType(simpleResultEnhanceInflexible.getType(), simpleResultEnhanceInflexible2.getType());
                }
                unwrappedType = TypeWithEnhancementKt.wrapEnhancement(rawTypeImplFlexibleType, kotlinTypeBuildEnhancementByFlexibleTypeBounds);
            }
            return new Result(unwrappedType, simpleResultEnhanceInflexible.getSubtreeSize(), z2);
        }
        if (unwrappedType instanceof SimpleType) {
            return enhanceInflexible$default(this, (SimpleType) unwrappedType, function1, i, TypeComponentPosition.INFLEXIBLE, false, 8, null);
        }
        throw new NoWhenBranchMatchedException();
    }

    static /* synthetic */ SimpleResult enhanceInflexible$default(JavaTypeEnhancement javaTypeEnhancement, SimpleType simpleType, Function1 function1, int i, TypeComponentPosition typeComponentPosition, boolean z, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            z = false;
        }
        return javaTypeEnhancement.enhanceInflexible(simpleType, function1, i, typeComponentPosition, z);
    }

    private final SimpleResult enhanceInflexible(SimpleType simpleType, Function1<? super Integer, JavaTypeQualifiers> function1, int i, TypeComponentPosition typeComponentPosition, boolean z) {
        TypeProjection typeProjectionCreateProjection;
        if (!TypeComponentPositionKt.shouldEnhance(typeComponentPosition) && simpleType.getArguments().isEmpty()) {
            return new SimpleResult(simpleType, 1, false);
        }
        ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = simpleType.getConstructor().mo1677getDeclarationDescriptor();
        if (classifierDescriptorMo1677getDeclarationDescriptor == null) {
            return new SimpleResult(simpleType, 1, false);
        }
        JavaTypeQualifiers javaTypeQualifiersInvoke = function1.invoke(Integer.valueOf(i));
        EnhancementResult enhancementResultEnhanceMutability = TypeEnhancementKt.enhanceMutability(classifierDescriptorMo1677getDeclarationDescriptor, javaTypeQualifiersInvoke, typeComponentPosition);
        ClassifierDescriptor classifierDescriptor = (ClassifierDescriptor) enhancementResultEnhanceMutability.component1();
        Annotations annotationsComponent2 = enhancementResultEnhanceMutability.component2();
        TypeConstructor typeConstructor = classifierDescriptor.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor, "enhancedClassifier.typeConstructor");
        int subtreeSize = i + 1;
        boolean z2 = annotationsComponent2 != null;
        List<TypeProjection> arguments = simpleType.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        int i2 = 0;
        for (Object obj : arguments) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TypeProjection typeProjection = (TypeProjection) obj;
            if (typeProjection.isStarProjection()) {
                JavaTypeQualifiers javaTypeQualifiersInvoke2 = function1.invoke(Integer.valueOf(subtreeSize));
                int i4 = subtreeSize + 1;
                if (javaTypeQualifiersInvoke2.getNullability() == NullabilityQualifier.NOT_NULL && !z) {
                    KotlinType kotlinTypeMakeNotNullable = TypeUtilsKt.makeNotNullable(typeProjection.getType().unwrap());
                    Variance projectionKind = typeProjection.getProjectionKind();
                    Intrinsics.checkNotNullExpressionValue(projectionKind, "arg.projectionKind");
                    typeProjectionCreateProjection = TypeUtilsKt.createProjection(kotlinTypeMakeNotNullable, projectionKind, typeConstructor.getParameters().get(i2));
                } else {
                    typeProjectionCreateProjection = TypeUtils.makeStarProjection(classifierDescriptor.getTypeConstructor().getParameters().get(i2));
                    Intrinsics.checkNotNullExpressionValue(typeProjectionCreateProjection, "{\n                    TypeUtils.makeStarProjection(enhancedClassifier.typeConstructor.parameters[localArgIndex])\n                }");
                }
                subtreeSize = i4;
            } else {
                Result resultEnhancePossiblyFlexible = enhancePossiblyFlexible(typeProjection.getType().unwrap(), function1, subtreeSize);
                z2 = z2 || resultEnhancePossiblyFlexible.getWereChanges();
                subtreeSize += resultEnhancePossiblyFlexible.getSubtreeSize();
                KotlinType type = resultEnhancePossiblyFlexible.getType();
                Variance projectionKind2 = typeProjection.getProjectionKind();
                Intrinsics.checkNotNullExpressionValue(projectionKind2, "arg.projectionKind");
                typeProjectionCreateProjection = TypeUtilsKt.createProjection(type, projectionKind2, typeConstructor.getParameters().get(i2));
            }
            arrayList.add(typeProjectionCreateProjection);
            i2 = i3;
        }
        ArrayList arrayList2 = arrayList;
        EnhancementResult enhancedNullability = TypeEnhancementKt.getEnhancedNullability(simpleType, javaTypeQualifiersInvoke, typeComponentPosition);
        boolean zBooleanValue = ((Boolean) enhancedNullability.component1()).booleanValue();
        Annotations annotationsComponent22 = enhancedNullability.component2();
        int i5 = subtreeSize - i;
        if (z2 || annotationsComponent22 != null) {
            boolean z3 = false;
            Annotations annotationsCompositeAnnotationsOrSingle = TypeEnhancementKt.compositeAnnotationsOrSingle(CollectionsKt.listOfNotNull((Object[]) new Annotations[]{simpleType.getAnnotations(), annotationsComponent2, annotationsComponent22}));
            KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
            SimpleType simpleTypeSimpleType$default = KotlinTypeFactory.simpleType$default(annotationsCompositeAnnotationsOrSingle, typeConstructor, arrayList2, zBooleanValue, null, 16, null);
            if (javaTypeQualifiersInvoke.isNotNullTypeParameter()) {
                simpleTypeSimpleType$default = notNullTypeParameter(simpleTypeSimpleType$default);
            }
            if (annotationsComponent22 != null && javaTypeQualifiersInvoke.isNullabilityQualifierForWarning()) {
                z3 = true;
            }
            return new SimpleResult((SimpleType) (z3 ? TypeWithEnhancementKt.wrapEnhancement(simpleType, simpleTypeSimpleType$default) : simpleTypeSimpleType$default), i5, true);
        }
        return new SimpleResult(simpleType, i5, false);
    }

    private final SimpleType notNullTypeParameter(SimpleType simpleType) {
        if (this.javaResolverSettings.getCorrectNullabilityForNotNullTypeParameter()) {
            return SpecialTypesKt.makeSimpleTypeDefinitelyNotNullOrNotNull(simpleType, true);
        }
        return new NotNullTypeParameter(simpleType);
    }
}
