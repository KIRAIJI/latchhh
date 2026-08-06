package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

/* JADX INFO: compiled from: DeserializedContainerSource.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum DeserializedContainerAbiStability {
    STABLE,
    FIR_UNSTABLE,
    IR_UNSTABLE;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static DeserializedContainerAbiStability[] valuesCustom() {
        DeserializedContainerAbiStability[] deserializedContainerAbiStabilityArrValuesCustom = values();
        DeserializedContainerAbiStability[] deserializedContainerAbiStabilityArr = new DeserializedContainerAbiStability[deserializedContainerAbiStabilityArrValuesCustom.length];
        System.arraycopy(deserializedContainerAbiStabilityArrValuesCustom, 0, deserializedContainerAbiStabilityArr, 0, deserializedContainerAbiStabilityArrValuesCustom.length);
        return deserializedContainerAbiStabilityArr;
    }
}
