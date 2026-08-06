package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;

/* JADX INFO: compiled from: signatureEnhancement.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class SignatureEnhancementKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isNullabilityFlexible(KotlinType kotlinType) {
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        FlexibleType flexibleType = unwrappedTypeUnwrap instanceof FlexibleType ? (FlexibleType) unwrappedTypeUnwrap : null;
        return (flexibleType == null || flexibleType.getLowerBound().isMarkedNullable() == flexibleType.getUpperBound().isMarkedNullable()) ? false : true;
    }
}
