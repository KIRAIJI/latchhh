package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.List;
import java.util.Objects;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImplKt;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: JavaTypeResolver.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class JavaTypeResolverKt {
    private static final FqName JAVA_LANG_CLASS_FQ_NAME = new FqName("java.lang.Class");

    public static final TypeProjection makeStarProjection(TypeParameterDescriptor typeParameter, JavaTypeAttributes attr) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(attr, "attr");
        if (attr.getHowThisTypeIsUsed() == TypeUsage.SUPERTYPE) {
            return new TypeProjectionImpl(StarProjectionImplKt.starProjectionType(typeParameter));
        }
        return new StarProjectionImpl(typeParameter);
    }

    public static /* synthetic */ JavaTypeAttributes toAttributes$default(TypeUsage typeUsage, boolean z, TypeParameterDescriptor typeParameterDescriptor, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            typeParameterDescriptor = null;
        }
        return toAttributes(typeUsage, z, typeParameterDescriptor);
    }

    public static final JavaTypeAttributes toAttributes(TypeUsage typeUsage, boolean z, TypeParameterDescriptor typeParameterDescriptor) {
        Intrinsics.checkNotNullParameter(typeUsage, "<this>");
        return new JavaTypeAttributes(typeUsage, null, z, typeParameterDescriptor, 2, null);
    }

    public static /* synthetic */ KotlinType getErasedUpperBound$default(final TypeParameterDescriptor typeParameterDescriptor, TypeParameterDescriptor typeParameterDescriptor2, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            typeParameterDescriptor2 = null;
        }
        if ((i & 2) != 0) {
            function0 = new Function0<SimpleType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolverKt.getErasedUpperBound.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final SimpleType invoke() {
                    SimpleType simpleTypeCreateErrorType = ErrorUtils.createErrorType("Can't compute erased upper bound of type parameter `" + typeParameterDescriptor + '`');
                    Intrinsics.checkNotNullExpressionValue(simpleTypeCreateErrorType, "createErrorType(\"Can't compute erased upper bound of type parameter `$this`\")");
                    return simpleTypeCreateErrorType;
                }
            };
        }
        return getErasedUpperBound(typeParameterDescriptor, typeParameterDescriptor2, function0);
    }

    public static final KotlinType getErasedUpperBound(TypeParameterDescriptor typeParameterDescriptor, TypeParameterDescriptor typeParameterDescriptor2, Function0<? extends KotlinType> defaultValue) {
        Intrinsics.checkNotNullParameter(typeParameterDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        if (typeParameterDescriptor == typeParameterDescriptor2) {
            return defaultValue.invoke();
        }
        List<KotlinType> upperBounds = typeParameterDescriptor.getUpperBounds();
        Intrinsics.checkNotNullExpressionValue(upperBounds, "upperBounds");
        KotlinType firstUpperBound = (KotlinType) CollectionsKt.first((List) upperBounds);
        if (firstUpperBound.getConstructor().mo1677getDeclarationDescriptor() instanceof ClassDescriptor) {
            Intrinsics.checkNotNullExpressionValue(firstUpperBound, "firstUpperBound");
            return TypeUtilsKt.replaceArgumentsWithStarProjections(firstUpperBound);
        }
        if (typeParameterDescriptor2 != null) {
            typeParameterDescriptor = typeParameterDescriptor2;
        }
        ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = firstUpperBound.getConstructor().mo1677getDeclarationDescriptor();
        Objects.requireNonNull(classifierDescriptorMo1677getDeclarationDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeParameterDescriptor");
        while (true) {
            TypeParameterDescriptor typeParameterDescriptor3 = (TypeParameterDescriptor) classifierDescriptorMo1677getDeclarationDescriptor;
            if (!Intrinsics.areEqual(typeParameterDescriptor3, typeParameterDescriptor)) {
                List<KotlinType> upperBounds2 = typeParameterDescriptor3.getUpperBounds();
                Intrinsics.checkNotNullExpressionValue(upperBounds2, "current.upperBounds");
                KotlinType nextUpperBound = (KotlinType) CollectionsKt.first((List) upperBounds2);
                if (nextUpperBound.getConstructor().mo1677getDeclarationDescriptor() instanceof ClassDescriptor) {
                    Intrinsics.checkNotNullExpressionValue(nextUpperBound, "nextUpperBound");
                    return TypeUtilsKt.replaceArgumentsWithStarProjections(nextUpperBound);
                }
                classifierDescriptorMo1677getDeclarationDescriptor = nextUpperBound.getConstructor().mo1677getDeclarationDescriptor();
                Objects.requireNonNull(classifierDescriptorMo1677getDeclarationDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeParameterDescriptor");
            } else {
                return defaultValue.invoke();
            }
        }
    }
}
