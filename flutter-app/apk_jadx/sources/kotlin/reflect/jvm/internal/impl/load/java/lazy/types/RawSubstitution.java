package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.text.Typography;

/* JADX INFO: compiled from: RawType.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class RawSubstitution extends TypeSubstitution {
    public static final RawSubstitution INSTANCE = new RawSubstitution();
    private static final JavaTypeAttributes lowerTypeAttr = JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, false, null, 3, null).withFlexibility(JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND);
    private static final JavaTypeAttributes upperTypeAttr = JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, false, null, 3, null).withFlexibility(JavaTypeFlexibility.FLEXIBLE_UPPER_BOUND);

    /* JADX INFO: compiled from: RawType.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JavaTypeFlexibility.valuesCustom().length];
            iArr[JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND.ordinal()] = 1;
            iArr[JavaTypeFlexibility.FLEXIBLE_UPPER_BOUND.ordinal()] = 2;
            iArr[JavaTypeFlexibility.INFLEXIBLE.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSubstitution
    public boolean isEmpty() {
        return false;
    }

    private RawSubstitution() {
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSubstitution
    /* JADX INFO: renamed from: get */
    public TypeProjectionImpl mo1682get(KotlinType key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return new TypeProjectionImpl(eraseType(key));
    }

    private final KotlinType eraseType(KotlinType kotlinType) {
        RawTypeImpl rawTypeImpl;
        ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = kotlinType.getConstructor().mo1677getDeclarationDescriptor();
        if (classifierDescriptorMo1677getDeclarationDescriptor instanceof TypeParameterDescriptor) {
            return eraseType(JavaTypeResolverKt.getErasedUpperBound$default((TypeParameterDescriptor) classifierDescriptorMo1677getDeclarationDescriptor, null, null, 3, null));
        }
        if (classifierDescriptorMo1677getDeclarationDescriptor instanceof ClassDescriptor) {
            ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor2 = FlexibleTypesKt.upperIfFlexible(kotlinType).getConstructor().mo1677getDeclarationDescriptor();
            if (!(classifierDescriptorMo1677getDeclarationDescriptor2 instanceof ClassDescriptor)) {
                throw new IllegalStateException(("For some reason declaration for upper bound is not a class but \"" + classifierDescriptorMo1677getDeclarationDescriptor2 + "\" while for lower it's \"" + classifierDescriptorMo1677getDeclarationDescriptor + Typography.quote).toString());
            }
            Pair<SimpleType, Boolean> pairEraseInflexibleBasedOnClassDescriptor = eraseInflexibleBasedOnClassDescriptor(FlexibleTypesKt.lowerIfFlexible(kotlinType), (ClassDescriptor) classifierDescriptorMo1677getDeclarationDescriptor, lowerTypeAttr);
            SimpleType simpleTypeComponent1 = pairEraseInflexibleBasedOnClassDescriptor.component1();
            boolean zBooleanValue = pairEraseInflexibleBasedOnClassDescriptor.component2().booleanValue();
            Pair<SimpleType, Boolean> pairEraseInflexibleBasedOnClassDescriptor2 = eraseInflexibleBasedOnClassDescriptor(FlexibleTypesKt.upperIfFlexible(kotlinType), (ClassDescriptor) classifierDescriptorMo1677getDeclarationDescriptor2, upperTypeAttr);
            SimpleType simpleTypeComponent12 = pairEraseInflexibleBasedOnClassDescriptor2.component1();
            boolean zBooleanValue2 = pairEraseInflexibleBasedOnClassDescriptor2.component2().booleanValue();
            if (zBooleanValue || zBooleanValue2) {
                rawTypeImpl = new RawTypeImpl(simpleTypeComponent1, simpleTypeComponent12);
            } else {
                KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
                rawTypeImpl = KotlinTypeFactory.flexibleType(simpleTypeComponent1, simpleTypeComponent12);
            }
            return rawTypeImpl;
        }
        throw new IllegalStateException(Intrinsics.stringPlus("Unexpected declaration kind: ", classifierDescriptorMo1677getDeclarationDescriptor).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Pair<SimpleType, Boolean> eraseInflexibleBasedOnClassDescriptor(final SimpleType simpleType, final ClassDescriptor classDescriptor, final JavaTypeAttributes javaTypeAttributes) {
        if (simpleType.getConstructor().getParameters().isEmpty()) {
            return TuplesKt.to(simpleType, false);
        }
        SimpleType simpleType2 = simpleType;
        if (KotlinBuiltIns.isArray(simpleType2)) {
            TypeProjection typeProjection = simpleType.getArguments().get(0);
            Variance projectionKind = typeProjection.getProjectionKind();
            KotlinType type = typeProjection.getType();
            Intrinsics.checkNotNullExpressionValue(type, "componentTypeProjection.type");
            List listListOf = CollectionsKt.listOf(new TypeProjectionImpl(projectionKind, eraseType(type)));
            KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
            return TuplesKt.to(KotlinTypeFactory.simpleType$default(simpleType.getAnnotations(), simpleType.getConstructor(), listListOf, simpleType.isMarkedNullable(), null, 16, null), false);
        }
        if (KotlinTypeKt.isError(simpleType2)) {
            SimpleType simpleTypeCreateErrorType = ErrorUtils.createErrorType(Intrinsics.stringPlus("Raw error type: ", simpleType.getConstructor()));
            Intrinsics.checkNotNullExpressionValue(simpleTypeCreateErrorType, "createErrorType(\"Raw error type: ${type.constructor}\")");
            return TuplesKt.to(simpleTypeCreateErrorType, false);
        }
        MemberScope memberScope = classDescriptor.getMemberScope(this);
        Intrinsics.checkNotNullExpressionValue(memberScope, "declaration.getMemberScope(RawSubstitution)");
        KotlinTypeFactory kotlinTypeFactory2 = KotlinTypeFactory.INSTANCE;
        Annotations annotations = simpleType.getAnnotations();
        TypeConstructor typeConstructor = classDescriptor.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor, "declaration.typeConstructor");
        List<TypeParameterDescriptor> parameters = classDescriptor.getTypeConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "declaration.typeConstructor.parameters");
        List<TypeParameterDescriptor> list = parameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (TypeParameterDescriptor parameter : list) {
            Intrinsics.checkNotNullExpressionValue(parameter, "parameter");
            arrayList.add(computeProjection$default(this, parameter, javaTypeAttributes, null, 4, null));
        }
        return TuplesKt.to(KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(annotations, typeConstructor, arrayList, simpleType.isMarkedNullable(), memberScope, new Function1<KotlinTypeRefiner, SimpleType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawSubstitution.eraseInflexibleBasedOnClassDescriptor.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final SimpleType invoke(KotlinTypeRefiner kotlinTypeRefiner) {
                ClassDescriptor classDescriptorFindClassAcrossModuleDependencies;
                Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
                ClassDescriptor classDescriptor2 = classDescriptor;
                if (!(classDescriptor2 instanceof ClassDescriptor)) {
                    classDescriptor2 = null;
                }
                ClassId classId = classDescriptor2 == null ? null : DescriptorUtilsKt.getClassId(classDescriptor2);
                if (classId == null || (classDescriptorFindClassAcrossModuleDependencies = kotlinTypeRefiner.findClassAcrossModuleDependencies(classId)) == null || Intrinsics.areEqual(classDescriptorFindClassAcrossModuleDependencies, classDescriptor)) {
                    return null;
                }
                return (SimpleType) this.eraseInflexibleBasedOnClassDescriptor(simpleType, classDescriptorFindClassAcrossModuleDependencies, javaTypeAttributes).getFirst();
            }
        }), true);
    }

    public static /* synthetic */ TypeProjection computeProjection$default(RawSubstitution rawSubstitution, TypeParameterDescriptor typeParameterDescriptor, JavaTypeAttributes javaTypeAttributes, KotlinType kotlinType, int i, Object obj) {
        if ((i & 4) != 0) {
            kotlinType = JavaTypeResolverKt.getErasedUpperBound$default(typeParameterDescriptor, null, null, 3, null);
        }
        return rawSubstitution.computeProjection(typeParameterDescriptor, javaTypeAttributes, kotlinType);
    }

    public final TypeProjection computeProjection(TypeParameterDescriptor parameter, JavaTypeAttributes attr, KotlinType erasedUpperBound) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(attr, "attr");
        Intrinsics.checkNotNullParameter(erasedUpperBound, "erasedUpperBound");
        int i = WhenMappings.$EnumSwitchMapping$0[attr.getFlexibility().ordinal()];
        if (i == 1) {
            return new TypeProjectionImpl(Variance.INVARIANT, erasedUpperBound);
        }
        if (i == 2 || i == 3) {
            if (!parameter.getVariance().getAllowsOutPosition()) {
                return new TypeProjectionImpl(Variance.INVARIANT, DescriptorUtilsKt.getBuiltIns(parameter).getNothingType());
            }
            Intrinsics.checkNotNullExpressionValue(erasedUpperBound.getConstructor().getParameters(), "erasedUpperBound.constructor.parameters");
            if (!r0.isEmpty()) {
                return new TypeProjectionImpl(Variance.OUT_VARIANCE, erasedUpperBound);
            }
            return JavaTypeResolverKt.makeStarProjection(parameter, attr);
        }
        throw new NoWhenBranchMatchedException();
    }
}
