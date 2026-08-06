package kotlin.reflect.jvm.internal.impl.descriptors.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

/* JADX INFO: compiled from: PlatformDependentTypeTransformer.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PlatformDependentTypeTransformer {
    SimpleType transformPlatformType(ClassId classId, SimpleType simpleType);

    /* JADX INFO: compiled from: PlatformDependentTypeTransformer.kt */
    public static final class None implements PlatformDependentTypeTransformer {
        public static final None INSTANCE = new None();

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentTypeTransformer
        public SimpleType transformPlatformType(ClassId classId, SimpleType computedType) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            Intrinsics.checkNotNullParameter(computedType, "computedType");
            return computedType;
        }

        private None() {
        }
    }
}
