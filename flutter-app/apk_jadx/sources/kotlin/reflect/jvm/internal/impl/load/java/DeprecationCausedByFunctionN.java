package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class DeprecationCausedByFunctionN {
    private final DeclarationDescriptor target;

    public DeprecationCausedByFunctionN(DeclarationDescriptor target) {
        Intrinsics.checkNotNullParameter(target, "target");
        this.target = target;
    }
}
