package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TypeCapabilities.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class TypeCapabilitiesKt {
    public static final boolean isCustomTypeVariable(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Object objUnwrap = kotlinType.unwrap();
        CustomTypeVariable customTypeVariable = objUnwrap instanceof CustomTypeVariable ? (CustomTypeVariable) objUnwrap : null;
        if (customTypeVariable == null) {
            return false;
        }
        return customTypeVariable.isTypeVariable();
    }

    public static final CustomTypeVariable getCustomTypeVariable(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Object objUnwrap = kotlinType.unwrap();
        CustomTypeVariable customTypeVariable = objUnwrap instanceof CustomTypeVariable ? (CustomTypeVariable) objUnwrap : null;
        if (customTypeVariable != null && customTypeVariable.isTypeVariable()) {
            return customTypeVariable;
        }
        return null;
    }
}
