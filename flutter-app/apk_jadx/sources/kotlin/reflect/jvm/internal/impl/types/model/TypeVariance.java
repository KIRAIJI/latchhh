package kotlin.reflect.jvm.internal.impl.types.model;

/* JADX INFO: compiled from: TypeSystemContext.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum TypeVariance {
    IN("in"),
    OUT("out"),
    INV("");

    private final String presentation;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static TypeVariance[] valuesCustom() {
        TypeVariance[] typeVarianceArrValuesCustom = values();
        TypeVariance[] typeVarianceArr = new TypeVariance[typeVarianceArrValuesCustom.length];
        System.arraycopy(typeVarianceArrValuesCustom, 0, typeVarianceArr, 0, typeVarianceArrValuesCustom.length);
        return typeVarianceArr;
    }

    TypeVariance(String str) {
        this.presentation = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.presentation;
    }
}
