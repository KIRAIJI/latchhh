package kotlin.reflect.jvm.internal.impl.renderer;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: DescriptorRenderer.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum AnnotationArgumentsRenderingPolicy {
    NO_ARGUMENTS(false, false, 3, null),
    UNLESS_EMPTY(true, false, 2, null),
    ALWAYS_PARENTHESIZED(true, true);

    private final boolean includeAnnotationArguments;
    private final boolean includeEmptyAnnotationArguments;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static AnnotationArgumentsRenderingPolicy[] valuesCustom() {
        AnnotationArgumentsRenderingPolicy[] annotationArgumentsRenderingPolicyArrValuesCustom = values();
        AnnotationArgumentsRenderingPolicy[] annotationArgumentsRenderingPolicyArr = new AnnotationArgumentsRenderingPolicy[annotationArgumentsRenderingPolicyArrValuesCustom.length];
        System.arraycopy(annotationArgumentsRenderingPolicyArrValuesCustom, 0, annotationArgumentsRenderingPolicyArr, 0, annotationArgumentsRenderingPolicyArrValuesCustom.length);
        return annotationArgumentsRenderingPolicyArr;
    }

    AnnotationArgumentsRenderingPolicy(boolean z, boolean z2) {
        this.includeAnnotationArguments = z;
        this.includeEmptyAnnotationArguments = z2;
    }

    /* synthetic */ AnnotationArgumentsRenderingPolicy(boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2);
    }

    public final boolean getIncludeAnnotationArguments() {
        return this.includeAnnotationArguments;
    }

    public final boolean getIncludeEmptyAnnotationArguments() {
        return this.includeEmptyAnnotationArguments;
    }
}
