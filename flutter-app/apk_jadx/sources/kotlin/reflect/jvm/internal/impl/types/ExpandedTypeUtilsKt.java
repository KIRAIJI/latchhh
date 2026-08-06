package kotlin.reflect.jvm.internal.impl.types;

import java.util.HashSet;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeParameterMarker;

/* JADX INFO: compiled from: expandedTypeUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ExpandedTypeUtilsKt {
    public static final KotlinTypeMarker computeExpandedTypeForInlineClass(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker inlineClassType) {
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(inlineClassType, "inlineClassType");
        return computeExpandedTypeInner(typeSystemCommonBackendContext, inlineClassType, new HashSet());
    }

    private static final KotlinTypeMarker computeExpandedTypeInner(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker kotlinTypeMarker, HashSet<TypeConstructorMarker> hashSet) {
        KotlinTypeMarker kotlinTypeMarkerComputeExpandedTypeInner;
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemCommonBackendContext.typeConstructor(kotlinTypeMarker);
        if (!hashSet.add(typeConstructorMarkerTypeConstructor)) {
            return null;
        }
        TypeParameterMarker typeParameterClassifier = typeSystemCommonBackendContext.getTypeParameterClassifier(typeConstructorMarkerTypeConstructor);
        if (typeParameterClassifier != null) {
            kotlinTypeMarkerComputeExpandedTypeInner = computeExpandedTypeInner(typeSystemCommonBackendContext, typeSystemCommonBackendContext.getRepresentativeUpperBound(typeParameterClassifier), hashSet);
            if (kotlinTypeMarkerComputeExpandedTypeInner == null) {
                return null;
            }
            if (!typeSystemCommonBackendContext.isNullableType(kotlinTypeMarkerComputeExpandedTypeInner) && typeSystemCommonBackendContext.isMarkedNullable(kotlinTypeMarker)) {
                return typeSystemCommonBackendContext.makeNullable(kotlinTypeMarkerComputeExpandedTypeInner);
            }
        } else {
            if (!typeSystemCommonBackendContext.isInlineClass(typeConstructorMarkerTypeConstructor)) {
                return kotlinTypeMarker;
            }
            KotlinTypeMarker substitutedUnderlyingType = typeSystemCommonBackendContext.getSubstitutedUnderlyingType(kotlinTypeMarker);
            if (substitutedUnderlyingType == null || (kotlinTypeMarkerComputeExpandedTypeInner = computeExpandedTypeInner(typeSystemCommonBackendContext, substitutedUnderlyingType, hashSet)) == null) {
                return null;
            }
            if (typeSystemCommonBackendContext.isNullableType(kotlinTypeMarker)) {
                return typeSystemCommonBackendContext.isNullableType(kotlinTypeMarkerComputeExpandedTypeInner) ? kotlinTypeMarker : ((kotlinTypeMarkerComputeExpandedTypeInner instanceof SimpleTypeMarker) && typeSystemCommonBackendContext.isPrimitiveType((SimpleTypeMarker) kotlinTypeMarkerComputeExpandedTypeInner)) ? kotlinTypeMarker : typeSystemCommonBackendContext.makeNullable(kotlinTypeMarkerComputeExpandedTypeInner);
            }
        }
        return kotlinTypeMarkerComputeExpandedTypeInner;
    }
}
