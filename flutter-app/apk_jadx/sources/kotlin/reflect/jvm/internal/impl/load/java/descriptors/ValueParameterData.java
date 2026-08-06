package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

/* JADX INFO: compiled from: util.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ValueParameterData {
    private final boolean hasDefaultValue;
    private final KotlinType type;

    public ValueParameterData(KotlinType type, boolean z) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.hasDefaultValue = z;
    }

    public final boolean getHasDefaultValue() {
        return this.hasDefaultValue;
    }

    public final KotlinType getType() {
        return this.type;
    }
}
