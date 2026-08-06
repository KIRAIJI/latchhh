package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

/* JADX INFO: compiled from: typeQualifiers.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum NullabilityQualifier {
    NULLABLE,
    NOT_NULL,
    FORCE_FLEXIBILITY;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static NullabilityQualifier[] valuesCustom() {
        NullabilityQualifier[] nullabilityQualifierArrValuesCustom = values();
        NullabilityQualifier[] nullabilityQualifierArr = new NullabilityQualifier[nullabilityQualifierArrValuesCustom.length];
        System.arraycopy(nullabilityQualifierArrValuesCustom, 0, nullabilityQualifierArr, 0, nullabilityQualifierArrValuesCustom.length);
        return nullabilityQualifierArr;
    }
}
