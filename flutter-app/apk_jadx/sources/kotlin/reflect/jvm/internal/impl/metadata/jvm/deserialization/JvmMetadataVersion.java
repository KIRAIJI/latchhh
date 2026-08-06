package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;

/* JADX INFO: compiled from: JvmMetadataVersion.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class JvmMetadataVersion extends BinaryVersion {
    public static final Companion Companion = new Companion(null);
    public static final JvmMetadataVersion INSTANCE = new JvmMetadataVersion(1, 4, 2);
    public static final JvmMetadataVersion INVALID_VERSION = new JvmMetadataVersion(new int[0]);
    private final boolean isStrictSemantics;

    /* JADX WARN: Illegal instructions before constructor call */
    public JvmMetadataVersion(int[] versionArray, boolean z) {
        Intrinsics.checkNotNullParameter(versionArray, "versionArray");
        int[] iArr = new int[versionArray.length];
        System.arraycopy(versionArray, 0, iArr, 0, versionArray.length);
        super(iArr);
        this.isStrictSemantics = z;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JvmMetadataVersion(int... numbers) {
        this(numbers, false);
        Intrinsics.checkNotNullParameter(numbers, "numbers");
    }

    public boolean isCompatible() {
        boolean zIsCompatibleTo;
        if (getMajor() == 1 && getMinor() == 0) {
            return false;
        }
        if (this.isStrictSemantics) {
            zIsCompatibleTo = isCompatibleTo(INSTANCE);
        } else {
            int major = getMajor();
            JvmMetadataVersion jvmMetadataVersion = INSTANCE;
            zIsCompatibleTo = major == jvmMetadataVersion.getMajor() && getMinor() <= jvmMetadataVersion.getMinor() + 1;
        }
        return zIsCompatibleTo;
    }

    /* JADX INFO: compiled from: JvmMetadataVersion.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
