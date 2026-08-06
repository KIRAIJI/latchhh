package kotlin.reflect.jvm.internal.impl.renderer;

/* JADX INFO: compiled from: DescriptorRenderer.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum ParameterNameRenderingPolicy {
    ALL,
    ONLY_NON_SYNTHESIZED,
    NONE;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static ParameterNameRenderingPolicy[] valuesCustom() {
        ParameterNameRenderingPolicy[] parameterNameRenderingPolicyArrValuesCustom = values();
        ParameterNameRenderingPolicy[] parameterNameRenderingPolicyArr = new ParameterNameRenderingPolicy[parameterNameRenderingPolicyArrValuesCustom.length];
        System.arraycopy(parameterNameRenderingPolicyArrValuesCustom, 0, parameterNameRenderingPolicyArr, 0, parameterNameRenderingPolicyArrValuesCustom.length);
        return parameterNameRenderingPolicyArr;
    }
}
