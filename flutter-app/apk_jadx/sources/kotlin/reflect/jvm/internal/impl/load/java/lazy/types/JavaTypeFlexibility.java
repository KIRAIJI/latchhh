package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

/* JADX INFO: compiled from: JavaTypeResolver.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum JavaTypeFlexibility {
    INFLEXIBLE,
    FLEXIBLE_UPPER_BOUND,
    FLEXIBLE_LOWER_BOUND;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static JavaTypeFlexibility[] valuesCustom() {
        JavaTypeFlexibility[] javaTypeFlexibilityArrValuesCustom = values();
        JavaTypeFlexibility[] javaTypeFlexibilityArr = new JavaTypeFlexibility[javaTypeFlexibilityArrValuesCustom.length];
        System.arraycopy(javaTypeFlexibilityArrValuesCustom, 0, javaTypeFlexibilityArr, 0, javaTypeFlexibilityArrValuesCustom.length);
        return javaTypeFlexibilityArr;
    }
}
