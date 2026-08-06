package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Objects;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AnnotationUseSiteTarget.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum AnnotationUseSiteTarget {
    FIELD(null, 1, null),
    FILE(null, 1, null),
    PROPERTY(null, 1, null),
    PROPERTY_GETTER("get"),
    PROPERTY_SETTER("set"),
    RECEIVER(0 == true ? 1 : 0, 1, null),
    CONSTRUCTOR_PARAMETER("param"),
    SETTER_PARAMETER("setparam"),
    PROPERTY_DELEGATE_FIELD("delegate");

    private final String renderName;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static AnnotationUseSiteTarget[] valuesCustom() {
        AnnotationUseSiteTarget[] annotationUseSiteTargetArrValuesCustom = values();
        AnnotationUseSiteTarget[] annotationUseSiteTargetArr = new AnnotationUseSiteTarget[annotationUseSiteTargetArrValuesCustom.length];
        System.arraycopy(annotationUseSiteTargetArrValuesCustom, 0, annotationUseSiteTargetArr, 0, annotationUseSiteTargetArrValuesCustom.length);
        return annotationUseSiteTargetArr;
    }

    AnnotationUseSiteTarget(String str) {
        if (str == null) {
            String strName = name();
            Objects.requireNonNull(strName, "null cannot be cast to non-null type java.lang.String");
            str = strName.toLowerCase();
            Intrinsics.checkNotNullExpressionValue(str, "(this as java.lang.String).toLowerCase()");
        }
        this.renderName = str;
    }

    /* synthetic */ AnnotationUseSiteTarget(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str);
    }

    public final String getRenderName() {
        return this.renderName;
    }
}
