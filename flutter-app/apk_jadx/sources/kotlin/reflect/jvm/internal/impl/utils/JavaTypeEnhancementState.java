package kotlin.reflect.jvm.internal.impl.utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JavaTypeEnhancementState.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class JavaTypeEnhancementState {
    public static final JavaTypeEnhancementState DEFAULT;
    public static final JavaTypeEnhancementState STRICT;
    private final Lazy description$delegate;
    private final boolean disabledDefaultAnnotations;
    private final boolean disabledJsr305;
    private final boolean enableCompatqualCheckerFrameworkAnnotations;
    private final ReportLevel globalJsr305Level;
    private final ReportLevel jspecifyReportLevel;
    private final ReportLevel migrationLevelForJsr305;
    private final Map<String, ReportLevel> userDefinedLevelForSpecificJsr305Annotation;
    public static final Companion Companion = new Companion(null);
    public static final ReportLevel DEFAULT_REPORT_LEVEL_FOR_JSPECIFY = ReportLevel.WARN;
    public static final JavaTypeEnhancementState DISABLED_JSR_305 = new JavaTypeEnhancementState(ReportLevel.IGNORE, ReportLevel.IGNORE, MapsKt.emptyMap(), false, null, 24, null);

    /* JADX WARN: Multi-variable type inference failed */
    public JavaTypeEnhancementState(ReportLevel globalJsr305Level, ReportLevel reportLevel, Map<String, ? extends ReportLevel> userDefinedLevelForSpecificJsr305Annotation, boolean z, ReportLevel jspecifyReportLevel) {
        Intrinsics.checkNotNullParameter(globalJsr305Level, "globalJsr305Level");
        Intrinsics.checkNotNullParameter(userDefinedLevelForSpecificJsr305Annotation, "userDefinedLevelForSpecificJsr305Annotation");
        Intrinsics.checkNotNullParameter(jspecifyReportLevel, "jspecifyReportLevel");
        this.globalJsr305Level = globalJsr305Level;
        this.migrationLevelForJsr305 = reportLevel;
        this.userDefinedLevelForSpecificJsr305Annotation = userDefinedLevelForSpecificJsr305Annotation;
        this.enableCompatqualCheckerFrameworkAnnotations = z;
        this.jspecifyReportLevel = jspecifyReportLevel;
        this.description$delegate = LazyKt.lazy(new Function0<String[]>() { // from class: kotlin.reflect.jvm.internal.impl.utils.JavaTypeEnhancementState$description$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String[] invoke() {
                ArrayList arrayList = new ArrayList();
                arrayList.add(this.this$0.getGlobalJsr305Level().getDescription());
                ReportLevel migrationLevelForJsr305 = this.this$0.getMigrationLevelForJsr305();
                if (migrationLevelForJsr305 != null) {
                    arrayList.add(Intrinsics.stringPlus("under-migration:", migrationLevelForJsr305.getDescription()));
                }
                for (Map.Entry<String, ReportLevel> entry : this.this$0.getUserDefinedLevelForSpecificJsr305Annotation().entrySet()) {
                    arrayList.add('@' + entry.getKey() + ':' + entry.getValue().getDescription());
                }
                Object[] array = arrayList.toArray(new String[0]);
                Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T>");
                return (String[]) array;
            }
        });
        boolean z2 = true;
        boolean z3 = globalJsr305Level == ReportLevel.IGNORE && reportLevel == ReportLevel.IGNORE && userDefinedLevelForSpecificJsr305Annotation.isEmpty();
        this.disabledJsr305 = z3;
        if (!z3 && jspecifyReportLevel != ReportLevel.IGNORE) {
            z2 = false;
        }
        this.disabledDefaultAnnotations = z2;
    }

    public final ReportLevel getGlobalJsr305Level() {
        return this.globalJsr305Level;
    }

    public final ReportLevel getMigrationLevelForJsr305() {
        return this.migrationLevelForJsr305;
    }

    public final Map<String, ReportLevel> getUserDefinedLevelForSpecificJsr305Annotation() {
        return this.userDefinedLevelForSpecificJsr305Annotation;
    }

    public final boolean getEnableCompatqualCheckerFrameworkAnnotations() {
        return this.enableCompatqualCheckerFrameworkAnnotations;
    }

    public /* synthetic */ JavaTypeEnhancementState(ReportLevel reportLevel, ReportLevel reportLevel2, Map map, boolean z, ReportLevel reportLevel3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(reportLevel, reportLevel2, map, (i & 8) != 0 ? true : z, (i & 16) != 0 ? DEFAULT_REPORT_LEVEL_FOR_JSPECIFY : reportLevel3);
    }

    public final ReportLevel getJspecifyReportLevel() {
        return this.jspecifyReportLevel;
    }

    public final boolean getDisabledJsr305() {
        return this.disabledJsr305;
    }

    public final boolean getDisabledDefaultAnnotations() {
        return this.disabledDefaultAnnotations;
    }

    /* JADX INFO: compiled from: JavaTypeEnhancementState.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        boolean z = false;
        ReportLevel reportLevel = null;
        int i = 24;
        DefaultConstructorMarker defaultConstructorMarker = null;
        DEFAULT = new JavaTypeEnhancementState(ReportLevel.WARN, null, MapsKt.emptyMap(), z, reportLevel, i, defaultConstructorMarker);
        STRICT = new JavaTypeEnhancementState(ReportLevel.STRICT, ReportLevel.STRICT, MapsKt.emptyMap(), z, reportLevel, i, defaultConstructorMarker);
    }
}
