package kotlin.reflect.jvm.internal.impl.incremental.components;

/* JADX INFO: compiled from: LookupTracker.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum ScopeKind {
    PACKAGE,
    CLASSIFIER;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static ScopeKind[] valuesCustom() {
        ScopeKind[] scopeKindArrValuesCustom = values();
        ScopeKind[] scopeKindArr = new ScopeKind[scopeKindArrValuesCustom.length];
        System.arraycopy(scopeKindArrValuesCustom, 0, scopeKindArr, 0, scopeKindArrValuesCustom.length);
        return scopeKindArr;
    }
}
