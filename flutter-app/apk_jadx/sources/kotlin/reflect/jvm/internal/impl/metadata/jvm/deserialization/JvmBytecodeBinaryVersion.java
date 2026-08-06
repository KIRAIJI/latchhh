package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;

/* JADX INFO: compiled from: JvmBytecodeBinaryVersion.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class JvmBytecodeBinaryVersion extends BinaryVersion {
    public static final Companion Companion = new Companion(null);
    public static final JvmBytecodeBinaryVersion INSTANCE = new JvmBytecodeBinaryVersion(1, 0, 3);
    public static final JvmBytecodeBinaryVersion INVALID_VERSION = new JvmBytecodeBinaryVersion(new int[0]);

    /* JADX WARN: Illegal instructions before constructor call */
    public JvmBytecodeBinaryVersion(int... numbers) {
        Intrinsics.checkNotNullParameter(numbers, "numbers");
        int[] iArr = new int[numbers.length];
        System.arraycopy(numbers, 0, iArr, 0, numbers.length);
        super(iArr);
    }

    /* JADX INFO: compiled from: JvmBytecodeBinaryVersion.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
