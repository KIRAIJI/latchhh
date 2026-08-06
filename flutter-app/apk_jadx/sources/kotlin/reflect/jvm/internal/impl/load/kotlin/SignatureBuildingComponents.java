package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SignatureBuildingComponents.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class SignatureBuildingComponents {
    public static final SignatureBuildingComponents INSTANCE = new SignatureBuildingComponents();

    private SignatureBuildingComponents() {
    }

    public final String javaLang(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return Intrinsics.stringPlus("java/lang/", name);
    }

    public final String javaUtil(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return Intrinsics.stringPlus("java/util/", name);
    }

    public final String javaFunction(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return Intrinsics.stringPlus("java/util/function/", name);
    }

    public final Set<String> inJavaLang(String name, String... signatures) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        String strJavaLang = javaLang(name);
        String[] strArr = new String[signatures.length];
        System.arraycopy(signatures, 0, strArr, 0, signatures.length);
        return inClass(strJavaLang, strArr);
    }

    public final Set<String> inJavaUtil(String name, String... signatures) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        String strJavaUtil = javaUtil(name);
        String[] strArr = new String[signatures.length];
        System.arraycopy(signatures, 0, strArr, 0, signatures.length);
        return inClass(strJavaUtil, strArr);
    }

    public final Set<String> inClass(String internalName, String... signatures) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (String str : signatures) {
            linkedHashSet.add(internalName + '.' + str);
        }
        return linkedHashSet;
    }

    public final String signature(String internalName, String jvmDescriptor) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        Intrinsics.checkNotNullParameter(jvmDescriptor, "jvmDescriptor");
        return internalName + '.' + jvmDescriptor;
    }

    public final String jvmDescriptor(String name, List<String> parameters, String ret) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(ret, "ret");
        return name + '(' + CollectionsKt.joinToString$default(parameters, "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents.jvmDescriptor.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return SignatureBuildingComponents.this.escapeClassName(it);
            }
        }, 30, null) + ')' + escapeClassName(ret);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String escapeClassName(String str) {
        if (str.length() <= 1) {
            return str;
        }
        return 'L' + str + ';';
    }

    public final String[] constructors(String... signatures) {
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        ArrayList arrayList = new ArrayList(signatures.length);
        for (String str : signatures) {
            arrayList.add("<init>(" + str + ")V");
        }
        Object[] array = arrayList.toArray(new String[0]);
        Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T>");
        return (String[]) array;
    }
}
