package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorImpl;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerValueTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: NewKotlinTypeChecker.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class NewKotlinTypeCheckerImpl implements NewKotlinTypeChecker {
    private final KotlinTypeRefiner kotlinTypeRefiner;
    private final OverridingUtil overridingUtil;

    public NewKotlinTypeCheckerImpl(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        this.kotlinTypeRefiner = kotlinTypeRefiner;
        OverridingUtil overridingUtilCreateWithTypeRefiner = OverridingUtil.createWithTypeRefiner(getKotlinTypeRefiner());
        Intrinsics.checkNotNullExpressionValue(overridingUtilCreateWithTypeRefiner, "createWithTypeRefiner(kotlinTypeRefiner)");
        this.overridingUtil = overridingUtilCreateWithTypeRefiner;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.checker.NewKotlinTypeChecker
    public KotlinTypeRefiner getKotlinTypeRefiner() {
        return this.kotlinTypeRefiner;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.checker.NewKotlinTypeChecker
    public OverridingUtil getOverridingUtil() {
        return this.overridingUtil;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker
    public boolean isSubtypeOf(KotlinType subtype, KotlinType supertype) {
        Intrinsics.checkNotNullParameter(subtype, "subtype");
        Intrinsics.checkNotNullParameter(supertype, "supertype");
        return isSubtypeOf(new ClassicTypeCheckerContext(true, false, false, getKotlinTypeRefiner(), 6, null), subtype.unwrap(), supertype.unwrap());
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker
    public boolean equalTypes(KotlinType a, KotlinType b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return equalTypes(new ClassicTypeCheckerContext(false, false, false, getKotlinTypeRefiner(), 6, null), a.unwrap(), b.unwrap());
    }

    public final boolean equalTypes(ClassicTypeCheckerContext classicTypeCheckerContext, UnwrappedType a, UnwrappedType b) {
        Intrinsics.checkNotNullParameter(classicTypeCheckerContext, "<this>");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return AbstractTypeChecker.INSTANCE.equalTypes(classicTypeCheckerContext, a, b);
    }

    public final boolean isSubtypeOf(ClassicTypeCheckerContext classicTypeCheckerContext, UnwrappedType subType, UnwrappedType superType) {
        Intrinsics.checkNotNullParameter(classicTypeCheckerContext, "<this>");
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        return AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, classicTypeCheckerContext, subType, superType, false, 8, null);
    }

    public final SimpleType transformToNewType(SimpleType type) {
        KotlinType type2;
        Intrinsics.checkNotNullParameter(type, "type");
        TypeConstructor constructor = type.getConstructor();
        boolean z = false;
        IntersectionTypeConstructor alternative = null;
        unwrappedTypeUnwrap = null;
        UnwrappedType unwrappedTypeUnwrap = null;
        if (constructor instanceof CapturedTypeConstructorImpl) {
            CapturedTypeConstructorImpl capturedTypeConstructorImpl = (CapturedTypeConstructorImpl) constructor;
            TypeProjection projection = capturedTypeConstructorImpl.getProjection();
            if (!(projection.getProjectionKind() == Variance.IN_VARIANCE)) {
                projection = null;
            }
            if (projection != null && (type2 = projection.getType()) != null) {
                unwrappedTypeUnwrap = type2.unwrap();
            }
            UnwrappedType unwrappedType = unwrappedTypeUnwrap;
            if (capturedTypeConstructorImpl.getNewTypeConstructor() == null) {
                TypeProjection projection2 = capturedTypeConstructorImpl.getProjection();
                Collection<KotlinType> collectionMo1678getSupertypes = capturedTypeConstructorImpl.mo1678getSupertypes();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionMo1678getSupertypes, 10));
                Iterator<T> it = collectionMo1678getSupertypes.iterator();
                while (it.hasNext()) {
                    arrayList.add(((KotlinType) it.next()).unwrap());
                }
                capturedTypeConstructorImpl.setNewTypeConstructor(new NewCapturedTypeConstructor(projection2, arrayList, null, 4, null));
            }
            CaptureStatus captureStatus = CaptureStatus.FOR_SUBTYPING;
            NewCapturedTypeConstructor newTypeConstructor = capturedTypeConstructorImpl.getNewTypeConstructor();
            Intrinsics.checkNotNull(newTypeConstructor);
            return new NewCapturedType(captureStatus, newTypeConstructor, unwrappedType, type.getAnnotations(), type.isMarkedNullable(), false, 32, null);
        }
        if (constructor instanceof IntegerValueTypeConstructor) {
            Collection<KotlinType> collectionMo1678getSupertypes2 = ((IntegerValueTypeConstructor) constructor).mo1678getSupertypes();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionMo1678getSupertypes2, 10));
            Iterator<T> it2 = collectionMo1678getSupertypes2.iterator();
            while (it2.hasNext()) {
                KotlinType kotlinTypeMakeNullableAsSpecified = TypeUtils.makeNullableAsSpecified((KotlinType) it2.next(), type.isMarkedNullable());
                Intrinsics.checkNotNullExpressionValue(kotlinTypeMakeNullableAsSpecified, "makeNullableAsSpecified(it, type.isMarkedNullable)");
                arrayList2.add(kotlinTypeMakeNullableAsSpecified);
            }
            IntersectionTypeConstructor intersectionTypeConstructor = new IntersectionTypeConstructor(arrayList2);
            KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
            return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(type.getAnnotations(), intersectionTypeConstructor, CollectionsKt.emptyList(), false, type.getMemberScope());
        }
        if (!(constructor instanceof IntersectionTypeConstructor) || !type.isMarkedNullable()) {
            return type;
        }
        IntersectionTypeConstructor intersectionTypeConstructor2 = (IntersectionTypeConstructor) constructor;
        Collection<KotlinType> collectionMo1678getSupertypes3 = intersectionTypeConstructor2.mo1678getSupertypes();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionMo1678getSupertypes3, 10));
        Iterator<T> it3 = collectionMo1678getSupertypes3.iterator();
        while (it3.hasNext()) {
            arrayList3.add(TypeUtilsKt.makeNullable((KotlinType) it3.next()));
            z = true;
        }
        ArrayList arrayList4 = arrayList3;
        if (z) {
            KotlinType alternativeType = intersectionTypeConstructor2.getAlternativeType();
            alternative = new IntersectionTypeConstructor(arrayList4).setAlternative(alternativeType != null ? TypeUtilsKt.makeNullable(alternativeType) : null);
        }
        if (alternative != null) {
            intersectionTypeConstructor2 = alternative;
        }
        return intersectionTypeConstructor2.createType();
    }

    public UnwrappedType transformToNewType(UnwrappedType type) {
        SimpleType simpleTypeFlexibleType;
        Intrinsics.checkNotNullParameter(type, "type");
        if (type instanceof SimpleType) {
            simpleTypeFlexibleType = transformToNewType((SimpleType) type);
        } else if (type instanceof FlexibleType) {
            FlexibleType flexibleType = (FlexibleType) type;
            SimpleType simpleTypeTransformToNewType = transformToNewType(flexibleType.getLowerBound());
            SimpleType simpleTypeTransformToNewType2 = transformToNewType(flexibleType.getUpperBound());
            if (simpleTypeTransformToNewType == flexibleType.getLowerBound() && simpleTypeTransformToNewType2 == flexibleType.getUpperBound()) {
                simpleTypeFlexibleType = type;
            } else {
                KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
                simpleTypeFlexibleType = KotlinTypeFactory.flexibleType(simpleTypeTransformToNewType, simpleTypeTransformToNewType2);
            }
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return TypeWithEnhancementKt.inheritEnhancement(simpleTypeFlexibleType, type);
    }
}
