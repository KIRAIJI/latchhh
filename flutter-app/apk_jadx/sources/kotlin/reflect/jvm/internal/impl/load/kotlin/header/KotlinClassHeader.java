package kotlin.reflect.jvm.internal.impl.load.kotlin.header;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmBytecodeBinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;

/* JADX INFO: compiled from: KotlinClassHeader.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class KotlinClassHeader {
    private final JvmBytecodeBinaryVersion bytecodeVersion;
    private final String[] data;
    private final int extraInt;
    private final String extraString;
    private final String[] incompatibleData;
    private final Kind kind;
    private final JvmMetadataVersion metadataVersion;
    private final String packageName;
    private final String[] strings;

    private final boolean has(int i, int i2) {
        return (i & i2) != 0;
    }

    public KotlinClassHeader(Kind kind, JvmMetadataVersion metadataVersion, JvmBytecodeBinaryVersion bytecodeVersion, String[] strArr, String[] strArr2, String[] strArr3, String str, int i, String str2) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(bytecodeVersion, "bytecodeVersion");
        this.kind = kind;
        this.metadataVersion = metadataVersion;
        this.bytecodeVersion = bytecodeVersion;
        this.data = strArr;
        this.incompatibleData = strArr2;
        this.strings = strArr3;
        this.extraString = str;
        this.extraInt = i;
        this.packageName = str2;
    }

    public final Kind getKind() {
        return this.kind;
    }

    public final JvmMetadataVersion getMetadataVersion() {
        return this.metadataVersion;
    }

    public final String[] getData() {
        return this.data;
    }

    public final String[] getIncompatibleData() {
        return this.incompatibleData;
    }

    public final String[] getStrings() {
        return this.strings;
    }

    /* JADX INFO: compiled from: KotlinClassHeader.kt */
    public enum Kind {
        UNKNOWN(0),
        CLASS(1),
        FILE_FACADE(2),
        SYNTHETIC_CLASS(3),
        MULTIFILE_CLASS(4),
        MULTIFILE_CLASS_PART(5);

        public static final Companion Companion = new Companion(null);
        private static final Map<Integer, Kind> entryById;
        private final int id;

        @JvmStatic
        public static final Kind getById(int i) {
            return Companion.getById(i);
        }

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static Kind[] valuesCustom() {
            Kind[] kindArrValuesCustom = values();
            Kind[] kindArr = new Kind[kindArrValuesCustom.length];
            System.arraycopy(kindArrValuesCustom, 0, kindArr, 0, kindArrValuesCustom.length);
            return kindArr;
        }

        Kind(int i) {
            this.id = i;
        }

        public final int getId() {
            return this.id;
        }

        static {
            Kind[] kindArrValuesCustom = valuesCustom();
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(kindArrValuesCustom.length), 16));
            for (Kind kind : kindArrValuesCustom) {
                linkedHashMap.put(Integer.valueOf(kind.getId()), kind);
            }
            entryById = linkedHashMap;
        }

        /* JADX INFO: compiled from: KotlinClassHeader.kt */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            @JvmStatic
            public final Kind getById(int i) {
                Kind kind = (Kind) Kind.entryById.get(Integer.valueOf(i));
                return kind == null ? Kind.UNKNOWN : kind;
            }
        }
    }

    public final String getMultifileClassName() {
        String str = this.extraString;
        if (getKind() == Kind.MULTIFILE_CLASS_PART) {
            return str;
        }
        return null;
    }

    public final List<String> getMultifilePartNames() {
        String[] strArr = this.data;
        if (!(getKind() == Kind.MULTIFILE_CLASS)) {
            strArr = null;
        }
        List<String> listAsList = strArr != null ? ArraysKt.asList(strArr) : null;
        return listAsList != null ? listAsList : CollectionsKt.emptyList();
    }

    public final boolean isUnstableJvmIrBinary() {
        return has(this.extraInt, 16) && !has(this.extraInt, 32);
    }

    public final boolean isUnstableFirBinary() {
        return has(this.extraInt, 64) && !has(this.extraInt, 32);
    }

    public final boolean isPreRelease() {
        return has(this.extraInt, 2);
    }

    public String toString() {
        return this.kind + " version=" + this.metadataVersion;
    }
}
