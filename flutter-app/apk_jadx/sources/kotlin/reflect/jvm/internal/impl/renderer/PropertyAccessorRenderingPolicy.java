package kotlin.reflect.jvm.internal.impl.renderer;

/* JADX INFO: compiled from: DescriptorRenderer.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum PropertyAccessorRenderingPolicy {
    PRETTY,
    DEBUG,
    NONE;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static PropertyAccessorRenderingPolicy[] valuesCustom() {
        PropertyAccessorRenderingPolicy[] propertyAccessorRenderingPolicyArrValuesCustom = values();
        PropertyAccessorRenderingPolicy[] propertyAccessorRenderingPolicyArr = new PropertyAccessorRenderingPolicy[propertyAccessorRenderingPolicyArrValuesCustom.length];
        System.arraycopy(propertyAccessorRenderingPolicyArrValuesCustom, 0, propertyAccessorRenderingPolicyArr, 0, propertyAccessorRenderingPolicyArrValuesCustom.length);
        return propertyAccessorRenderingPolicyArr;
    }
}
