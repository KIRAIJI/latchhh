package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

/* JADX INFO: compiled from: typeQualifiers.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum MutabilityQualifier {
    READ_ONLY,
    MUTABLE;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static MutabilityQualifier[] valuesCustom() {
        MutabilityQualifier[] mutabilityQualifierArrValuesCustom = values();
        MutabilityQualifier[] mutabilityQualifierArr = new MutabilityQualifier[mutabilityQualifierArrValuesCustom.length];
        System.arraycopy(mutabilityQualifierArrValuesCustom, 0, mutabilityQualifierArr, 0, mutabilityQualifierArrValuesCustom.length);
        return mutabilityQualifierArr;
    }
}
