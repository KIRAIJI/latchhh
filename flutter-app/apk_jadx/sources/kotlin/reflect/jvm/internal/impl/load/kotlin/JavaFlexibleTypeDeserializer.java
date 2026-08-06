package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.FlexibleTypeDeserializer;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

/* JADX INFO: compiled from: JavaFlexibleTypeDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class JavaFlexibleTypeDeserializer implements FlexibleTypeDeserializer {
    public static final JavaFlexibleTypeDeserializer INSTANCE = new JavaFlexibleTypeDeserializer();

    private JavaFlexibleTypeDeserializer() {
    }

    @Override // kotlin.reflect.jvm.internal.impl.serialization.deserialization.FlexibleTypeDeserializer
    public KotlinType create(ProtoBuf.Type proto, String flexibleId, SimpleType lowerBound, SimpleType upperBound) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(flexibleId, "flexibleId");
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        if (!Intrinsics.areEqual(flexibleId, "kotlin.jvm.PlatformType")) {
            SimpleType simpleTypeCreateErrorType = ErrorUtils.createErrorType("Error java flexible type with id: " + flexibleId + ". (" + lowerBound + ".." + upperBound + ')');
            Intrinsics.checkNotNullExpressionValue(simpleTypeCreateErrorType, "createErrorType(\"Error java flexible type with id: $flexibleId. ($lowerBound..$upperBound)\")");
            return simpleTypeCreateErrorType;
        }
        if (proto.hasExtension(JvmProtoBuf.isRaw)) {
            return new RawTypeImpl(lowerBound, upperBound);
        }
        KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
        return KotlinTypeFactory.flexibleType(lowerBound, upperBound);
    }
}
