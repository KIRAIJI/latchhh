package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

/* JADX INFO: compiled from: KotlinRetention.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum KotlinRetention {
    RUNTIME,
    BINARY,
    SOURCE;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static KotlinRetention[] valuesCustom() {
        KotlinRetention[] kotlinRetentionArrValuesCustom = values();
        KotlinRetention[] kotlinRetentionArr = new KotlinRetention[kotlinRetentionArrValuesCustom.length];
        System.arraycopy(kotlinRetentionArrValuesCustom, 0, kotlinRetentionArr, 0, kotlinRetentionArrValuesCustom.length);
        return kotlinRetentionArr;
    }
}
