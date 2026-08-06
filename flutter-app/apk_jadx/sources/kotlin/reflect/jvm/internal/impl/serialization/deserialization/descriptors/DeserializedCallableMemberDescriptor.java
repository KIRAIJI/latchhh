package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberDescriptor;

/* JADX INFO: compiled from: DeserializedMemberDescriptor.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface DeserializedCallableMemberDescriptor extends CallableMemberDescriptor, DeserializedMemberDescriptor {

    /* JADX INFO: compiled from: DeserializedMemberDescriptor.kt */
    public static final class DefaultImpls {
        public static List<VersionRequirement> getVersionRequirements(DeserializedCallableMemberDescriptor deserializedCallableMemberDescriptor) {
            return DeserializedMemberDescriptor.DefaultImpls.getVersionRequirements(deserializedCallableMemberDescriptor);
        }
    }
}
