package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

/* JADX INFO: compiled from: TypeComponentPosition.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum TypeComponentPosition {
    FLEXIBLE_LOWER,
    FLEXIBLE_UPPER,
    INFLEXIBLE;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static TypeComponentPosition[] valuesCustom() {
        TypeComponentPosition[] typeComponentPositionArrValuesCustom = values();
        TypeComponentPosition[] typeComponentPositionArr = new TypeComponentPosition[typeComponentPositionArrValuesCustom.length];
        System.arraycopy(typeComponentPositionArrValuesCustom, 0, typeComponentPositionArr, 0, typeComponentPositionArrValuesCustom.length);
        return typeComponentPositionArr;
    }
}
