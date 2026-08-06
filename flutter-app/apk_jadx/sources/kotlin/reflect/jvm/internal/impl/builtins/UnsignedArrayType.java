package kotlin.reflect.jvm.internal.impl.builtins;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'UBYTEARRAY' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: UnsignedType.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class UnsignedArrayType {
    private static final /* synthetic */ UnsignedArrayType[] $VALUES;
    public static final UnsignedArrayType UBYTEARRAY;
    public static final UnsignedArrayType UINTARRAY;
    public static final UnsignedArrayType ULONGARRAY;
    public static final UnsignedArrayType USHORTARRAY;
    private final ClassId classId;
    private final Name typeName;

    public static UnsignedArrayType[] values() {
        UnsignedArrayType[] unsignedArrayTypeArr = $VALUES;
        UnsignedArrayType[] unsignedArrayTypeArr2 = new UnsignedArrayType[unsignedArrayTypeArr.length];
        System.arraycopy(unsignedArrayTypeArr, 0, unsignedArrayTypeArr2, 0, unsignedArrayTypeArr.length);
        return unsignedArrayTypeArr2;
    }

    private UnsignedArrayType(String str, int i, ClassId classId) {
        this.classId = classId;
        Name shortClassName = classId.getShortClassName();
        Intrinsics.checkNotNullExpressionValue(shortClassName, "classId.shortClassName");
        this.typeName = shortClassName;
    }

    static {
        ClassId classIdFromString = ClassId.fromString("kotlin/UByteArray");
        Intrinsics.checkNotNullExpressionValue(classIdFromString, "fromString(\"kotlin/UByteArray\")");
        UnsignedArrayType unsignedArrayType = new UnsignedArrayType("UBYTEARRAY", 0, classIdFromString);
        UBYTEARRAY = unsignedArrayType;
        ClassId classIdFromString2 = ClassId.fromString("kotlin/UShortArray");
        Intrinsics.checkNotNullExpressionValue(classIdFromString2, "fromString(\"kotlin/UShortArray\")");
        UnsignedArrayType unsignedArrayType2 = new UnsignedArrayType("USHORTARRAY", 1, classIdFromString2);
        USHORTARRAY = unsignedArrayType2;
        ClassId classIdFromString3 = ClassId.fromString("kotlin/UIntArray");
        Intrinsics.checkNotNullExpressionValue(classIdFromString3, "fromString(\"kotlin/UIntArray\")");
        UnsignedArrayType unsignedArrayType3 = new UnsignedArrayType("UINTARRAY", 2, classIdFromString3);
        UINTARRAY = unsignedArrayType3;
        ClassId classIdFromString4 = ClassId.fromString("kotlin/ULongArray");
        Intrinsics.checkNotNullExpressionValue(classIdFromString4, "fromString(\"kotlin/ULongArray\")");
        UnsignedArrayType unsignedArrayType4 = new UnsignedArrayType("ULONGARRAY", 3, classIdFromString4);
        ULONGARRAY = unsignedArrayType4;
        $VALUES = new UnsignedArrayType[]{unsignedArrayType, unsignedArrayType2, unsignedArrayType3, unsignedArrayType4};
    }

    public final Name getTypeName() {
        return this.typeName;
    }

    public static UnsignedArrayType valueOf(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return (UnsignedArrayType) Enum.valueOf(UnsignedArrayType.class, value);
    }
}
