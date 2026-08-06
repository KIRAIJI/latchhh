package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultQualifiers;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

/* JADX INFO: compiled from: signatureEnhancement.kt */
/* JADX INFO: loaded from: classes2.dex */
final class TypeAndDefaultQualifiers {
    private final JavaDefaultQualifiers defaultQualifiers;
    private final boolean isFromStarProjection;
    private final KotlinType type;
    private final TypeParameterDescriptor typeParameterForArgument;

    public final KotlinType component1() {
        return this.type;
    }

    public final JavaDefaultQualifiers component2() {
        return this.defaultQualifiers;
    }

    public final TypeParameterDescriptor component3() {
        return this.typeParameterForArgument;
    }

    public final boolean component4() {
        return this.isFromStarProjection;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TypeAndDefaultQualifiers)) {
            return false;
        }
        TypeAndDefaultQualifiers typeAndDefaultQualifiers = (TypeAndDefaultQualifiers) obj;
        return Intrinsics.areEqual(this.type, typeAndDefaultQualifiers.type) && Intrinsics.areEqual(this.defaultQualifiers, typeAndDefaultQualifiers.defaultQualifiers) && Intrinsics.areEqual(this.typeParameterForArgument, typeAndDefaultQualifiers.typeParameterForArgument) && this.isFromStarProjection == typeAndDefaultQualifiers.isFromStarProjection;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5, types: [int] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v8 */
    public int hashCode() {
        int iHashCode = this.type.hashCode() * 31;
        JavaDefaultQualifiers javaDefaultQualifiers = this.defaultQualifiers;
        int iHashCode2 = (iHashCode + (javaDefaultQualifiers == null ? 0 : javaDefaultQualifiers.hashCode())) * 31;
        TypeParameterDescriptor typeParameterDescriptor = this.typeParameterForArgument;
        int iHashCode3 = (iHashCode2 + (typeParameterDescriptor != null ? typeParameterDescriptor.hashCode() : 0)) * 31;
        boolean z = this.isFromStarProjection;
        ?? r1 = z;
        if (z) {
            r1 = 1;
        }
        return iHashCode3 + r1;
    }

    public String toString() {
        return "TypeAndDefaultQualifiers(type=" + this.type + ", defaultQualifiers=" + this.defaultQualifiers + ", typeParameterForArgument=" + this.typeParameterForArgument + ", isFromStarProjection=" + this.isFromStarProjection + ')';
    }

    public TypeAndDefaultQualifiers(KotlinType type, JavaDefaultQualifiers javaDefaultQualifiers, TypeParameterDescriptor typeParameterDescriptor, boolean z) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.defaultQualifiers = javaDefaultQualifiers;
        this.typeParameterForArgument = typeParameterDescriptor;
        this.isFromStarProjection = z;
    }

    public final KotlinType getType() {
        return this.type;
    }
}
