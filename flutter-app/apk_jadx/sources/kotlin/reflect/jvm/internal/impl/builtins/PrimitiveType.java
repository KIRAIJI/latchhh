package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;

/* JADX INFO: compiled from: PrimitiveType.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum PrimitiveType {
    BOOLEAN("Boolean"),
    CHAR("Char"),
    BYTE("Byte"),
    SHORT("Short"),
    INT("Int"),
    FLOAT("Float"),
    LONG("Long"),
    DOUBLE("Double");

    public static final Companion Companion;
    public static final Set<PrimitiveType> NUMBER_TYPES;
    private final Lazy arrayTypeFqName$delegate;
    private final Name arrayTypeName;
    private final Lazy typeFqName$delegate;
    private final Name typeName;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static PrimitiveType[] valuesCustom() {
        PrimitiveType[] primitiveTypeArrValuesCustom = values();
        PrimitiveType[] primitiveTypeArr = new PrimitiveType[primitiveTypeArrValuesCustom.length];
        System.arraycopy(primitiveTypeArrValuesCustom, 0, primitiveTypeArr, 0, primitiveTypeArrValuesCustom.length);
        return primitiveTypeArr;
    }

    PrimitiveType(String str) {
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(typeName)");
        this.typeName = nameIdentifier;
        Name nameIdentifier2 = Name.identifier(Intrinsics.stringPlus(str, "Array"));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(\"${typeName}Array\")");
        this.arrayTypeName = nameIdentifier2;
        this.typeFqName$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<FqName>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType$typeFqName$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FqName invoke() {
                FqName fqNameChild = StandardNames.BUILT_INS_PACKAGE_FQ_NAME.child(this.this$0.getTypeName());
                Intrinsics.checkNotNullExpressionValue(fqNameChild, "BUILT_INS_PACKAGE_FQ_NAME.child(this.typeName)");
                return fqNameChild;
            }
        });
        this.arrayTypeFqName$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<FqName>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType$arrayTypeFqName$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FqName invoke() {
                FqName fqNameChild = StandardNames.BUILT_INS_PACKAGE_FQ_NAME.child(this.this$0.getArrayTypeName());
                Intrinsics.checkNotNullExpressionValue(fqNameChild, "BUILT_INS_PACKAGE_FQ_NAME.child(arrayTypeName)");
                return fqNameChild;
            }
        });
    }

    static {
        PrimitiveType primitiveType = CHAR;
        PrimitiveType primitiveType2 = BYTE;
        PrimitiveType primitiveType3 = SHORT;
        PrimitiveType primitiveType4 = INT;
        PrimitiveType primitiveType5 = FLOAT;
        PrimitiveType primitiveType6 = LONG;
        PrimitiveType primitiveType7 = DOUBLE;
        Companion = new Companion(null);
        NUMBER_TYPES = SetsKt.setOf((Object[]) new PrimitiveType[]{primitiveType, primitiveType2, primitiveType3, primitiveType4, primitiveType5, primitiveType6, primitiveType7});
    }

    public final Name getTypeName() {
        return this.typeName;
    }

    public final Name getArrayTypeName() {
        return this.arrayTypeName;
    }

    public final FqName getTypeFqName() {
        return (FqName) this.typeFqName$delegate.getValue();
    }

    public final FqName getArrayTypeFqName() {
        return (FqName) this.arrayTypeFqName$delegate.getValue();
    }

    /* JADX INFO: compiled from: PrimitiveType.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
