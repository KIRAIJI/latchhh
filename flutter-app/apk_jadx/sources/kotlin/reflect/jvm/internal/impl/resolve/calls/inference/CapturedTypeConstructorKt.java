package kotlin.reflect.jvm.internal.impl.resolve.calls.inference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.DelegatedTypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.IndexedParametersSubstitution;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.LazyWrappedType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.Variance;

/* JADX INFO: compiled from: CapturedTypeConstructor.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class CapturedTypeConstructorKt {
    public static final KotlinType createCapturedType(TypeProjection typeProjection) {
        Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
        return new CapturedType(typeProjection, null, false, null, 14, null);
    }

    public static final boolean isCaptured(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return kotlinType.getConstructor() instanceof CapturedTypeConstructor;
    }

    public static /* synthetic */ TypeSubstitution wrapWithCapturingSubstitution$default(TypeSubstitution typeSubstitution, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return wrapWithCapturingSubstitution(typeSubstitution, z);
    }

    public static final TypeSubstitution wrapWithCapturingSubstitution(TypeSubstitution typeSubstitution, boolean z) {
        Intrinsics.checkNotNullParameter(typeSubstitution, "<this>");
        if (typeSubstitution instanceof IndexedParametersSubstitution) {
            IndexedParametersSubstitution indexedParametersSubstitution = (IndexedParametersSubstitution) typeSubstitution;
            TypeParameterDescriptor[] parameters = indexedParametersSubstitution.getParameters();
            List<Pair> listZip = ArraysKt.zip(indexedParametersSubstitution.getArguments(), indexedParametersSubstitution.getParameters());
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listZip, 10));
            for (Pair pair : listZip) {
                arrayList.add(createCapturedIfNeeded((TypeProjection) pair.getFirst(), (TypeParameterDescriptor) pair.getSecond()));
            }
            Object[] array = arrayList.toArray(new TypeProjection[0]);
            Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T>");
            return new IndexedParametersSubstitution(parameters, (TypeProjection[]) array, z);
        }
        return new DelegatedTypeSubstitution(z, typeSubstitution) { // from class: kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorKt.wrapWithCapturingSubstitution.2
            final /* synthetic */ boolean $needApproximation;
            final /* synthetic */ TypeSubstitution $this_wrapWithCapturingSubstitution;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(typeSubstitution);
                this.$this_wrapWithCapturingSubstitution = typeSubstitution;
            }

            @Override // kotlin.reflect.jvm.internal.impl.types.DelegatedTypeSubstitution, kotlin.reflect.jvm.internal.impl.types.TypeSubstitution
            public boolean approximateContravariantCapturedTypes() {
                return this.$needApproximation;
            }

            @Override // kotlin.reflect.jvm.internal.impl.types.DelegatedTypeSubstitution, kotlin.reflect.jvm.internal.impl.types.TypeSubstitution
            /* JADX INFO: renamed from: get */
            public TypeProjection mo1682get(KotlinType key) {
                Intrinsics.checkNotNullParameter(key, "key");
                TypeProjection typeProjectionMo1682get = super.mo1682get(key);
                if (typeProjectionMo1682get == null) {
                    return null;
                }
                ClassifierDescriptor classifierDescriptorMo1677getDeclarationDescriptor = key.getConstructor().mo1677getDeclarationDescriptor();
                return CapturedTypeConstructorKt.createCapturedIfNeeded(typeProjectionMo1682get, classifierDescriptorMo1677getDeclarationDescriptor instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) classifierDescriptorMo1677getDeclarationDescriptor : null);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final TypeProjection createCapturedIfNeeded(final TypeProjection typeProjection, TypeParameterDescriptor typeParameterDescriptor) {
        if (typeParameterDescriptor == null || typeProjection.getProjectionKind() == Variance.INVARIANT) {
            return typeProjection;
        }
        if (typeParameterDescriptor.getVariance() == typeProjection.getProjectionKind()) {
            if (typeProjection.isStarProjection()) {
                StorageManager NO_LOCKS = LockBasedStorageManager.NO_LOCKS;
                Intrinsics.checkNotNullExpressionValue(NO_LOCKS, "NO_LOCKS");
                return new TypeProjectionImpl(new LazyWrappedType(NO_LOCKS, new Function0<KotlinType>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorKt.createCapturedIfNeeded.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final KotlinType invoke() {
                        KotlinType type = typeProjection.getType();
                        Intrinsics.checkNotNullExpressionValue(type, "this@createCapturedIfNeeded.type");
                        return type;
                    }
                }));
            }
            return new TypeProjectionImpl(typeProjection.getType());
        }
        return new TypeProjectionImpl(createCapturedType(typeProjection));
    }
}
