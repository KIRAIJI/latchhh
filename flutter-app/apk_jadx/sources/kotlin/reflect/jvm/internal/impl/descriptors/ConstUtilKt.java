package kotlin.reflect.jvm.internal.impl.descriptors;

/* JADX INFO: compiled from: ConstUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ConstUtilKt {
    /* JADX WARN: Removed duplicated region for block: B:6:0x0013  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0019  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final boolean canBeUsedForConstVal(kotlin.reflect.jvm.internal.impl.types.KotlinType r1) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r1, r0)
            boolean r0 = kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.isPrimitiveType(r1)
            if (r0 != 0) goto L13
            kotlin.reflect.jvm.internal.impl.builtins.UnsignedTypes r0 = kotlin.reflect.jvm.internal.impl.builtins.UnsignedTypes.INSTANCE
            boolean r0 = kotlin.reflect.jvm.internal.impl.builtins.UnsignedTypes.isUnsignedType(r1)
            if (r0 == 0) goto L19
        L13:
            boolean r0 = kotlin.reflect.jvm.internal.impl.types.TypeUtils.isNullableType(r1)
            if (r0 == 0) goto L22
        L19:
            boolean r1 = kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.isString(r1)
            if (r1 == 0) goto L20
            goto L22
        L20:
            r1 = 0
            goto L23
        L22:
            r1 = 1
        L23:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.descriptors.ConstUtilKt.canBeUsedForConstVal(kotlin.reflect.jvm.internal.impl.types.KotlinType):boolean");
    }
}
