package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: NewCapturedType.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class NewCapturedTypeKt {
    public static final SimpleType captureFromArguments(SimpleType type, CaptureStatus status) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(status, "status");
        SimpleType simpleType = type;
        List<TypeProjection> listCaptureArguments = captureArguments(simpleType, status);
        if (listCaptureArguments == null) {
            return null;
        }
        return replaceArguments(simpleType, listCaptureArguments);
    }

    private static final SimpleType replaceArguments(UnwrappedType unwrappedType, List<? extends TypeProjection> list) {
        KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
        return KotlinTypeFactory.simpleType$default(unwrappedType.getAnnotations(), unwrappedType.getConstructor(), list, unwrappedType.isMarkedNullable(), null, 16, null);
    }

    private static final List<TypeProjection> captureArguments(UnwrappedType unwrappedType, CaptureStatus captureStatus) {
        UnwrappedType unwrappedTypeUnwrap;
        if (unwrappedType.getArguments().size() != unwrappedType.getConstructor().getParameters().size()) {
            return null;
        }
        List<TypeProjection> arguments = unwrappedType.getArguments();
        List<TypeProjection> list = arguments;
        int i = 0;
        boolean z = true;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (!(((TypeProjection) it.next()).getProjectionKind() == Variance.INVARIANT)) {
                    z = false;
                    break;
                }
            }
        }
        if (z) {
            return null;
        }
        List<TypeParameterDescriptor> parameters = unwrappedType.getConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "type.constructor.parameters");
        List<Pair> listZip = CollectionsKt.zip(list, parameters);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listZip, 10));
        for (Pair pair : listZip) {
            TypeProjection typeProjectionAsTypeProjection = (TypeProjection) pair.component1();
            TypeParameterDescriptor parameter = (TypeParameterDescriptor) pair.component2();
            if (typeProjectionAsTypeProjection.getProjectionKind() != Variance.INVARIANT) {
                if (!typeProjectionAsTypeProjection.isStarProjection() && typeProjectionAsTypeProjection.getProjectionKind() == Variance.IN_VARIANCE) {
                    unwrappedTypeUnwrap = typeProjectionAsTypeProjection.getType().unwrap();
                } else {
                    unwrappedTypeUnwrap = (UnwrappedType) null;
                }
                Intrinsics.checkNotNullExpressionValue(parameter, "parameter");
                typeProjectionAsTypeProjection = TypeUtilsKt.asTypeProjection(new NewCapturedType(captureStatus, unwrappedTypeUnwrap, typeProjectionAsTypeProjection, parameter));
            }
            arrayList.add(typeProjectionAsTypeProjection);
        }
        ArrayList arrayList2 = arrayList;
        TypeSubstitutor typeSubstitutorBuildSubstitutor = TypeConstructorSubstitution.Companion.create(unwrappedType.getConstructor(), arrayList2).buildSubstitutor();
        int size = arguments.size() - 1;
        if (size >= 0) {
            while (true) {
                int i2 = i + 1;
                TypeProjection typeProjection = arguments.get(i);
                TypeProjection typeProjection2 = (TypeProjection) arrayList2.get(i);
                if (typeProjection.getProjectionKind() != Variance.INVARIANT) {
                    List<KotlinType> upperBounds = unwrappedType.getConstructor().getParameters().get(i).getUpperBounds();
                    Intrinsics.checkNotNullExpressionValue(upperBounds, "type.constructor.parameters[index].upperBounds");
                    ArrayList arrayList3 = new ArrayList();
                    Iterator<T> it2 = upperBounds.iterator();
                    while (it2.hasNext()) {
                        arrayList3.add(NewKotlinTypeChecker.Companion.getDefault().transformToNewType(typeSubstitutorBuildSubstitutor.safeSubstitute((KotlinType) it2.next(), Variance.INVARIANT).unwrap()));
                    }
                    ArrayList arrayList4 = arrayList3;
                    if (!typeProjection.isStarProjection() && typeProjection.getProjectionKind() == Variance.OUT_VARIANCE) {
                        arrayList4.add(NewKotlinTypeChecker.Companion.getDefault().transformToNewType(typeProjection.getType().unwrap()));
                    }
                    ((NewCapturedType) typeProjection2.getType()).getConstructor().initializeSupertypes(arrayList4);
                }
                if (i2 > size) {
                    break;
                }
                i = i2;
            }
        }
        return arrayList2;
    }
}
