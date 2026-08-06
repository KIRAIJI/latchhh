package kotlin.reflect.jvm.internal.impl.types.model;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TypeSystemContext.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface TypeSystemOptimizationContext {

    /* JADX INFO: compiled from: TypeSystemContext.kt */
    public static final class DefaultImpls {
        public static boolean identicalArguments(TypeSystemOptimizationContext typeSystemOptimizationContext, SimpleTypeMarker a, SimpleTypeMarker b) {
            Intrinsics.checkNotNullParameter(typeSystemOptimizationContext, "this");
            Intrinsics.checkNotNullParameter(a, "a");
            Intrinsics.checkNotNullParameter(b, "b");
            return false;
        }
    }

    boolean identicalArguments(SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2);
}
