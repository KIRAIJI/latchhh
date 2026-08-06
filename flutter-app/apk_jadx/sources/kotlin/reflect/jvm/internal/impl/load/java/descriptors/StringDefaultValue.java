package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AnnotationDefaultValue.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class StringDefaultValue extends AnnotationDefaultValue {
    private final String value;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StringDefaultValue(String value) {
        super(null);
        Intrinsics.checkNotNullParameter(value, "value");
        this.value = value;
    }

    public final String getValue() {
        return this.value;
    }
}
