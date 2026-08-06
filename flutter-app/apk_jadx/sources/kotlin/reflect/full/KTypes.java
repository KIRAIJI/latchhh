package kotlin.reflect.full;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KType;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: KTypes.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0007\u001a\u0014\u0010\u0004\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0007\u001a\u0014\u0010\u0005\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0001H\u0007¨\u0006\u0007"}, d2 = {"isSubtypeOf", "", "Lkotlin/reflect/KType;", "other", "isSupertypeOf", "withNullability", "nullable", "kotlin-reflection"}, k = 2, mv = {1, 4, 1})
public final class KTypes {
    public static final KType withNullability(KType withNullability, boolean z) {
        Intrinsics.checkNotNullParameter(withNullability, "$this$withNullability");
        return ((KTypeImpl) withNullability).makeNullableAsSpecified$kotlin_reflection(z);
    }

    public static final boolean isSubtypeOf(KType isSubtypeOf, KType other) {
        Intrinsics.checkNotNullParameter(isSubtypeOf, "$this$isSubtypeOf");
        Intrinsics.checkNotNullParameter(other, "other");
        return TypeUtilsKt.isSubtypeOf(((KTypeImpl) isSubtypeOf).getType(), ((KTypeImpl) other).getType());
    }

    public static final boolean isSupertypeOf(KType isSupertypeOf, KType other) {
        Intrinsics.checkNotNullParameter(isSupertypeOf, "$this$isSupertypeOf");
        Intrinsics.checkNotNullParameter(other, "other");
        return isSubtypeOf(other, isSupertypeOf);
    }
}
