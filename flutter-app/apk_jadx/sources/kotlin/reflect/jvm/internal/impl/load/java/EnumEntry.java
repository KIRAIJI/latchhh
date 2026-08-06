package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class EnumEntry extends JavaDefaultValue {
    private final ClassDescriptor descriptor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EnumEntry(ClassDescriptor descriptor) {
        super(null);
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.descriptor = descriptor;
    }
}
