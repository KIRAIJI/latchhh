package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: JavaTypeEnhancementState.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum ReportLevel {
    IGNORE("ignore"),
    WARN("warn"),
    STRICT("strict");

    public static final Companion Companion = new Companion(null);
    private final String description;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static ReportLevel[] valuesCustom() {
        ReportLevel[] reportLevelArrValuesCustom = values();
        ReportLevel[] reportLevelArr = new ReportLevel[reportLevelArrValuesCustom.length];
        System.arraycopy(reportLevelArrValuesCustom, 0, reportLevelArr, 0, reportLevelArrValuesCustom.length);
        return reportLevelArr;
    }

    ReportLevel(String str) {
        this.description = str;
    }

    public final String getDescription() {
        return this.description;
    }

    /* JADX INFO: compiled from: JavaTypeEnhancementState.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final boolean isWarning() {
        return this == WARN;
    }

    public final boolean isIgnore() {
        return this == IGNORE;
    }
}
