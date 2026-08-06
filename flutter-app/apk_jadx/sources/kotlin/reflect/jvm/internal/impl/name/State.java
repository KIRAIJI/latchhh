package kotlin.reflect.jvm.internal.impl.name;

/* JADX INFO: compiled from: FqNamesUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
enum State {
    BEGINNING,
    MIDDLE,
    AFTER_DOT;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static State[] valuesCustom() {
        State[] stateArrValuesCustom = values();
        State[] stateArr = new State[stateArrValuesCustom.length];
        System.arraycopy(stateArrValuesCustom, 0, stateArr, 0, stateArrValuesCustom.length);
        return stateArr;
    }
}
