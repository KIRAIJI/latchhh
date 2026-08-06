package kotlin.reflect.jvm.internal.impl.load.java.structure;

/* JADX INFO: compiled from: javaElements.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum LightClassOriginKind {
    SOURCE,
    BINARY;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static LightClassOriginKind[] valuesCustom() {
        LightClassOriginKind[] lightClassOriginKindArrValuesCustom = values();
        LightClassOriginKind[] lightClassOriginKindArr = new LightClassOriginKind[lightClassOriginKindArrValuesCustom.length];
        System.arraycopy(lightClassOriginKindArrValuesCustom, 0, lightClassOriginKindArr, 0, lightClassOriginKindArrValuesCustom.length);
        return lightClassOriginKindArr;
    }
}
