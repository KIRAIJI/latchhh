package kotlin.reflect.jvm.internal.impl.builtins;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'UBYTE' uses external variables
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
public final class UnsignedType {
    private static final /* synthetic */ UnsignedType[] $VALUES;
    public static final UnsignedType UBYTE;
    public static final UnsignedType UINT;
    public static final UnsignedType ULONG;
    public static final UnsignedType USHORT;
    private final ClassId arrayClassId;
    private final ClassId classId;
    private final Name typeName;

    public static UnsignedType[] values() {
        UnsignedType[] unsignedTypeArr = $VALUES;
        UnsignedType[] unsignedTypeArr2 = new UnsignedType[unsignedTypeArr.length];
        System.arraycopy(unsignedTypeArr, 0, unsignedTypeArr2, 0, unsignedTypeArr.length);
        return unsignedTypeArr2;
    }

    private UnsignedType(String str, int i, ClassId classId) {
        this.classId = classId;
        Name shortClassName = classId.getShortClassName();
        Intrinsics.checkNotNullExpressionValue(shortClassName, "classId.shortClassName");
        this.typeName = shortClassName;
        this.arrayClassId = new ClassId(classId.getPackageFqName(), Name.identifier(Intrinsics.stringPlus(shortClassName.asString(), "Array")));
    }

    public final ClassId getClassId() {
        return this.classId;
    }

    static {
        ClassId classIdFromString = ClassId.fromString("kotlin/UByte");
        Intrinsics.checkNotNullExpressionValue(classIdFromString, "fromString(\"kotlin/UByte\")");
        UnsignedType unsignedType = new UnsignedType("UBYTE", 0, classIdFromString);
        UBYTE = unsignedType;
        ClassId classIdFromString2 = ClassId.fromString("kotlin/UShort");
        Intrinsics.checkNotNullExpressionValue(classIdFromString2, "fromString(\"kotlin/UShort\")");
        UnsignedType unsignedType2 = new UnsignedType("USHORT", 1, classIdFromString2);
        USHORT = unsignedType2;
        ClassId classIdFromString3 = ClassId.fromString("kotlin/UInt");
        Intrinsics.checkNotNullExpressionValue(classIdFromString3, "fromString(\"kotlin/UInt\")");
        UnsignedType unsignedType3 = new UnsignedType("UINT", 2, classIdFromString3);
        UINT = unsignedType3;
        ClassId classIdFromString4 = ClassId.fromString("kotlin/ULong");
        Intrinsics.checkNotNullExpressionValue(classIdFromString4, "fromString(\"kotlin/ULong\")");
        UnsignedType unsignedType4 = new UnsignedType("ULONG", 3, classIdFromString4);
        ULONG = unsignedType4;
        $VALUES = new UnsignedType[]{unsignedType, unsignedType2, unsignedType3, unsignedType4};
    }

    public final Name getTypeName() {
        return this.typeName;
    }

    public final ClassId getArrayClassId() {
        return this.arrayClassId;
    }

    public static UnsignedType valueOf(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return (UnsignedType) Enum.valueOf(UnsignedType.class, value);
    }
}
