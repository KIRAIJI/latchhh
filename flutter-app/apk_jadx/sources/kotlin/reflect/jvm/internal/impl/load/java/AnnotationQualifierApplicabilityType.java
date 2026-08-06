package kotlin.reflect.jvm.internal.impl.load.java;

/* JADX INFO: compiled from: AnnotationQualifierApplicabilityType.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum AnnotationQualifierApplicabilityType {
    METHOD_RETURN_TYPE("METHOD"),
    VALUE_PARAMETER("PARAMETER"),
    FIELD("FIELD"),
    TYPE_USE("TYPE_USE"),
    TYPE_PARAMETER_BOUNDS("TYPE_USE"),
    TYPE_PARAMETER("TYPE_PARAMETER");

    private final String javaTarget;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static AnnotationQualifierApplicabilityType[] valuesCustom() {
        AnnotationQualifierApplicabilityType[] annotationQualifierApplicabilityTypeArrValuesCustom = values();
        AnnotationQualifierApplicabilityType[] annotationQualifierApplicabilityTypeArr = new AnnotationQualifierApplicabilityType[annotationQualifierApplicabilityTypeArrValuesCustom.length];
        System.arraycopy(annotationQualifierApplicabilityTypeArrValuesCustom, 0, annotationQualifierApplicabilityTypeArr, 0, annotationQualifierApplicabilityTypeArrValuesCustom.length);
        return annotationQualifierApplicabilityTypeArr;
    }

    AnnotationQualifierApplicabilityType(String str) {
        this.javaTarget = str;
    }

    public final String getJavaTarget() {
        return this.javaTarget;
    }
}
