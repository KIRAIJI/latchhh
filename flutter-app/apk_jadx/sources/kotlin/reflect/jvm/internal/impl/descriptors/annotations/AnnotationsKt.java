package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class AnnotationsKt {
    public static final Annotations composeAnnotations(Annotations first, Annotations second) {
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        return first.isEmpty() ? second : second.isEmpty() ? first : new CompositeAnnotations(first, second);
    }
}
