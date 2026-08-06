package kotlin.reflect.jvm.internal.impl.types.model;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemOptimizationContext;

/* JADX INFO: compiled from: TypeSystemContext.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface TypeSystemContext extends TypeSystemOptimizationContext {
    boolean areEqualTypeConstructors(TypeConstructorMarker typeConstructorMarker, TypeConstructorMarker typeConstructorMarker2);

    int argumentsCount(KotlinTypeMarker kotlinTypeMarker);

    TypeArgumentListMarker asArgumentList(SimpleTypeMarker simpleTypeMarker);

    CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker);

    DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker);

    DynamicTypeMarker asDynamicType(FlexibleTypeMarker flexibleTypeMarker);

    FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker);

    SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker);

    TypeArgumentMarker asTypeArgument(KotlinTypeMarker kotlinTypeMarker);

    SimpleTypeMarker captureFromArguments(SimpleTypeMarker simpleTypeMarker, CaptureStatus captureStatus);

    TypeArgumentMarker get(TypeArgumentListMarker typeArgumentListMarker, int i);

    TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i);

    TypeParameterMarker getParameter(TypeConstructorMarker typeConstructorMarker, int i);

    KotlinTypeMarker getType(TypeArgumentMarker typeArgumentMarker);

    TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker);

    TypeVariance getVariance(TypeParameterMarker typeParameterMarker);

    KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> list);

    boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isDenotable(TypeConstructorMarker typeConstructorMarker);

    boolean isError(KotlinTypeMarker kotlinTypeMarker);

    boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isIntersection(TypeConstructorMarker typeConstructorMarker);

    boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker);

    boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker);

    boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isNullableType(KotlinTypeMarker kotlinTypeMarker);

    boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker);

    boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker);

    boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker);

    boolean isStarProjection(TypeArgumentMarker typeArgumentMarker);

    boolean isStubType(SimpleTypeMarker simpleTypeMarker);

    SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker);

    SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker);

    KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker);

    KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker);

    SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker);

    int parametersCount(TypeConstructorMarker typeConstructorMarker);

    Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker);

    int size(TypeArgumentListMarker typeArgumentListMarker);

    Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker);

    TypeConstructorMarker typeConstructor(KotlinTypeMarker kotlinTypeMarker);

    TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker);

    SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker);

    SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker);

    KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z);

    SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z);

    /* JADX INFO: compiled from: TypeSystemContext.kt */
    public static final class DefaultImpls {
        public static List<SimpleTypeMarker> fastCorrespondingSupertypes(TypeSystemContext typeSystemContext, SimpleTypeMarker receiver, TypeConstructorMarker constructor) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            return null;
        }

        public static boolean identicalArguments(TypeSystemContext typeSystemContext, SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
            return TypeSystemOptimizationContext.DefaultImpls.identicalArguments(typeSystemContext, simpleTypeMarker, simpleTypeMarker2);
        }

        public static boolean isMarkedNullable(TypeSystemContext typeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            return (receiver instanceof SimpleTypeMarker) && typeSystemContext.isMarkedNullable((SimpleTypeMarker) receiver);
        }

        public static TypeArgumentMarker getArgumentOrNull(TypeSystemContext typeSystemContext, SimpleTypeMarker receiver, int i) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            boolean z = false;
            if (i >= 0 && i < typeSystemContext.argumentsCount(receiver)) {
                z = true;
            }
            if (z) {
                return typeSystemContext.getArgument(receiver, i);
            }
            return null;
        }

        public static SimpleTypeMarker lowerBoundIfFlexible(TypeSystemContext typeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            FlexibleTypeMarker flexibleTypeMarkerAsFlexibleType = typeSystemContext.asFlexibleType(receiver);
            if (flexibleTypeMarkerAsFlexibleType != null) {
                return typeSystemContext.lowerBound(flexibleTypeMarkerAsFlexibleType);
            }
            SimpleTypeMarker simpleTypeMarkerAsSimpleType = typeSystemContext.asSimpleType(receiver);
            Intrinsics.checkNotNull(simpleTypeMarkerAsSimpleType);
            return simpleTypeMarkerAsSimpleType;
        }

        public static SimpleTypeMarker upperBoundIfFlexible(TypeSystemContext typeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            FlexibleTypeMarker flexibleTypeMarkerAsFlexibleType = typeSystemContext.asFlexibleType(receiver);
            if (flexibleTypeMarkerAsFlexibleType != null) {
                return typeSystemContext.upperBound(flexibleTypeMarkerAsFlexibleType);
            }
            SimpleTypeMarker simpleTypeMarkerAsSimpleType = typeSystemContext.asSimpleType(receiver);
            Intrinsics.checkNotNull(simpleTypeMarkerAsSimpleType);
            return simpleTypeMarkerAsSimpleType;
        }

        public static boolean isDynamic(TypeSystemContext typeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            FlexibleTypeMarker flexibleTypeMarkerAsFlexibleType = typeSystemContext.asFlexibleType(receiver);
            return (flexibleTypeMarkerAsFlexibleType == null ? null : typeSystemContext.asDynamicType(flexibleTypeMarkerAsFlexibleType)) != null;
        }

        public static boolean isDefinitelyNotNullType(TypeSystemContext typeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            SimpleTypeMarker simpleTypeMarkerAsSimpleType = typeSystemContext.asSimpleType(receiver);
            return (simpleTypeMarkerAsSimpleType == null ? null : typeSystemContext.asDefinitelyNotNullType(simpleTypeMarkerAsSimpleType)) != null;
        }

        public static boolean hasFlexibleNullability(TypeSystemContext typeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            return typeSystemContext.isMarkedNullable(typeSystemContext.lowerBoundIfFlexible(receiver)) != typeSystemContext.isMarkedNullable(typeSystemContext.upperBoundIfFlexible(receiver));
        }

        public static TypeConstructorMarker typeConstructor(TypeSystemContext typeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            SimpleTypeMarker simpleTypeMarkerAsSimpleType = typeSystemContext.asSimpleType(receiver);
            if (simpleTypeMarkerAsSimpleType == null) {
                simpleTypeMarkerAsSimpleType = typeSystemContext.lowerBoundIfFlexible(receiver);
            }
            return typeSystemContext.typeConstructor(simpleTypeMarkerAsSimpleType);
        }

        public static boolean isNothing(TypeSystemContext typeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            return typeSystemContext.isNothingConstructor(typeSystemContext.typeConstructor(receiver)) && !typeSystemContext.isNullableType(receiver);
        }

        public static boolean isClassType(TypeSystemContext typeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            return typeSystemContext.isClassTypeConstructor(typeSystemContext.typeConstructor(receiver));
        }

        public static boolean isIntegerLiteralType(TypeSystemContext typeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            return typeSystemContext.isIntegerLiteralTypeConstructor(typeSystemContext.typeConstructor(receiver));
        }

        public static TypeArgumentMarker get(TypeSystemContext typeSystemContext, TypeArgumentListMarker receiver, int i) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof SimpleTypeMarker) {
                return typeSystemContext.getArgument((KotlinTypeMarker) receiver, i);
            }
            if (receiver instanceof ArgumentList) {
                TypeArgumentMarker typeArgumentMarker = ((ArgumentList) receiver).get(i);
                Intrinsics.checkNotNullExpressionValue(typeArgumentMarker, "get(index)");
                return typeArgumentMarker;
            }
            throw new IllegalStateException(("unknown type argument list type: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }

        public static int size(TypeSystemContext typeSystemContext, TypeArgumentListMarker receiver) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(receiver, "receiver");
            if (receiver instanceof SimpleTypeMarker) {
                return typeSystemContext.argumentsCount((KotlinTypeMarker) receiver);
            }
            if (receiver instanceof ArgumentList) {
                return ((ArgumentList) receiver).size();
            }
            throw new IllegalStateException(("unknown type argument list type: " + receiver + ", " + Reflection.getOrCreateKotlinClass(receiver.getClass())).toString());
        }
    }
}
