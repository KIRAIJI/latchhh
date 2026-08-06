package kotlin.reflect.jvm.internal.impl.types.model;

/* JADX INFO: compiled from: TypeSystemContext.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum CaptureStatus {
    FOR_SUBTYPING,
    FOR_INCORPORATION,
    FROM_EXPRESSION;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static CaptureStatus[] valuesCustom() {
        CaptureStatus[] captureStatusArrValuesCustom = values();
        CaptureStatus[] captureStatusArr = new CaptureStatus[captureStatusArrValuesCustom.length];
        System.arraycopy(captureStatusArrValuesCustom, 0, captureStatusArr, 0, captureStatusArrValuesCustom.length);
        return captureStatusArr;
    }
}
