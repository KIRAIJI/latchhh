package kotlin.reflect.jvm.internal.impl.renderer;

/* JADX INFO: compiled from: DescriptorRenderer.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum OverrideRenderingPolicy {
    RENDER_OVERRIDE,
    RENDER_OPEN,
    RENDER_OPEN_OVERRIDE;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static OverrideRenderingPolicy[] valuesCustom() {
        OverrideRenderingPolicy[] overrideRenderingPolicyArrValuesCustom = values();
        OverrideRenderingPolicy[] overrideRenderingPolicyArr = new OverrideRenderingPolicy[overrideRenderingPolicyArrValuesCustom.length];
        System.arraycopy(overrideRenderingPolicyArrValuesCustom, 0, overrideRenderingPolicyArr, 0, overrideRenderingPolicyArrValuesCustom.length);
        return overrideRenderingPolicyArr;
    }
}
