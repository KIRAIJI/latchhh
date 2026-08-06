package kotlin.reflect.jvm.internal.impl.builtins.functions;

import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: FunctionClassKind.kt */
/* JADX INFO: loaded from: classes2.dex */
public enum FunctionClassKind {
    Function(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, "Function"),
    SuspendFunction(StandardNames.COROUTINES_PACKAGE_FQ_NAME_RELEASE, "SuspendFunction"),
    KFunction(StandardNames.KOTLIN_REFLECT_FQ_NAME, "KFunction"),
    KSuspendFunction(StandardNames.KOTLIN_REFLECT_FQ_NAME, "KSuspendFunction");

    public static final Companion Companion = new Companion(null);
    private final String classNamePrefix;
    private final FqName packageFqName;

    /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
    public static FunctionClassKind[] valuesCustom() {
        FunctionClassKind[] functionClassKindArrValuesCustom = values();
        FunctionClassKind[] functionClassKindArr = new FunctionClassKind[functionClassKindArrValuesCustom.length];
        System.arraycopy(functionClassKindArrValuesCustom, 0, functionClassKindArr, 0, functionClassKindArrValuesCustom.length);
        return functionClassKindArr;
    }

    FunctionClassKind(FqName fqName, String str) {
        this.packageFqName = fqName;
        this.classNamePrefix = str;
    }

    public final String getClassNamePrefix() {
        return this.classNamePrefix;
    }

    public final FqName getPackageFqName() {
        return this.packageFqName;
    }

    public final Name numberedClassName(int i) {
        Name nameIdentifier = Name.identifier(Intrinsics.stringPlus(this.classNamePrefix, Integer.valueOf(i)));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(\"$classNamePrefix$arity\")");
        return nameIdentifier;
    }

    /* JADX INFO: compiled from: FunctionClassKind.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FunctionClassKind byClassNamePrefix(FqName packageFqName, String className) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(className, "className");
            for (FunctionClassKind functionClassKind : FunctionClassKind.valuesCustom()) {
                if (Intrinsics.areEqual(functionClassKind.getPackageFqName(), packageFqName) && StringsKt.startsWith$default(className, functionClassKind.getClassNamePrefix(), false, 2, (Object) null)) {
                    return functionClassKind;
                }
            }
            return null;
        }

        /* JADX INFO: compiled from: FunctionClassKind.kt */
        public static final class KindWithArity {
            private final int arity;
            private final FunctionClassKind kind;

            public final FunctionClassKind component1() {
                return this.kind;
            }

            public final int component2() {
                return this.arity;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof KindWithArity)) {
                    return false;
                }
                KindWithArity kindWithArity = (KindWithArity) obj;
                return this.kind == kindWithArity.kind && this.arity == kindWithArity.arity;
            }

            public int hashCode() {
                return (this.kind.hashCode() * 31) + this.arity;
            }

            public String toString() {
                return "KindWithArity(kind=" + this.kind + ", arity=" + this.arity + ')';
            }

            public KindWithArity(FunctionClassKind kind, int i) {
                Intrinsics.checkNotNullParameter(kind, "kind");
                this.kind = kind;
                this.arity = i;
            }

            public final FunctionClassKind getKind() {
                return this.kind;
            }
        }

        public final KindWithArity parseClassName(String className, FqName packageFqName) {
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            FunctionClassKind functionClassKindByClassNamePrefix = byClassNamePrefix(packageFqName, className);
            if (functionClassKindByClassNamePrefix == null) {
                return null;
            }
            String strSubstring = className.substring(functionClassKindByClassNamePrefix.getClassNamePrefix().length());
            Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.String).substring(startIndex)");
            Integer num = toInt(strSubstring);
            if (num == null) {
                return null;
            }
            return new KindWithArity(functionClassKindByClassNamePrefix, num.intValue());
        }

        @JvmStatic
        public final FunctionClassKind getFunctionalClassKind(String className, FqName packageFqName) {
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            KindWithArity className2 = parseClassName(className, packageFqName);
            if (className2 == null) {
                return null;
            }
            return className2.getKind();
        }

        private final Integer toInt(String str) {
            if (str.length() == 0) {
                return null;
            }
            int length = str.length();
            int i = 0;
            int i2 = 0;
            while (i < length) {
                char cCharAt = str.charAt(i);
                i++;
                int i3 = cCharAt - '0';
                if (!(i3 >= 0 && i3 <= 9)) {
                    return null;
                }
                i2 = (i2 * 10) + i3;
            }
            return Integer.valueOf(i2);
        }
    }
}
