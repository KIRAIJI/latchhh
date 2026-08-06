package kotlin.reflect.jvm.internal.impl.util;

import java.util.Collection;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.util.CheckResult;
import kotlin.text.Regex;

/* JADX INFO: compiled from: modifierChecks.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class Checks {
    private final Function1<FunctionDescriptor, String> additionalCheck;
    private final Check[] checks;
    private final Name name;
    private final Collection<Name> nameList;
    private final Regex regex;

    /* JADX WARN: Multi-variable type inference failed */
    private Checks(Name name, Regex regex, Collection<Name> collection, Function1<? super FunctionDescriptor, String> function1, Check... checkArr) {
        this.name = name;
        this.regex = regex;
        this.nameList = collection;
        this.additionalCheck = function1;
        this.checks = checkArr;
    }

    public final boolean isApplicable(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        if (this.name != null && !Intrinsics.areEqual(functionDescriptor.getName(), this.name)) {
            return false;
        }
        if (this.regex != null) {
            String strAsString = functionDescriptor.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "functionDescriptor.name.asString()");
            if (!this.regex.matches(strAsString)) {
                return false;
            }
        }
        Collection<Name> collection = this.nameList;
        return collection == null || collection.contains(functionDescriptor.getName());
    }

    public final CheckResult checkAll(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Check[] checkArr = this.checks;
        int length = checkArr.length;
        int i = 0;
        while (i < length) {
            Check check = checkArr[i];
            i++;
            String strInvoke = check.invoke(functionDescriptor);
            if (strInvoke != null) {
                return new CheckResult.IllegalSignature(strInvoke);
            }
        }
        String strInvoke2 = this.additionalCheck.invoke(functionDescriptor);
        if (strInvoke2 != null) {
            return new CheckResult.IllegalSignature(strInvoke2);
        }
        return CheckResult.SuccessCheck.INSTANCE;
    }

    public /* synthetic */ Checks(Name name, Check[] checkArr, AnonymousClass2 anonymousClass2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(name, checkArr, (Function1<? super FunctionDescriptor, String>) ((i & 4) != 0 ? new Function1() { // from class: kotlin.reflect.jvm.internal.impl.util.Checks.2
            @Override // kotlin.jvm.functions.Function1
            public final Void invoke(FunctionDescriptor functionDescriptor) {
                Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
                return null;
            }
        } : anonymousClass2));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Checks(Name name, Check[] checks, Function1<? super FunctionDescriptor, String> additionalChecks) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(checks, "checks");
        Intrinsics.checkNotNullParameter(additionalChecks, "additionalChecks");
        Check[] checkArr = new Check[checks.length];
        System.arraycopy(checks, 0, checkArr, 0, checks.length);
        this(name, (Regex) null, (Collection<Name>) null, additionalChecks, checkArr);
    }

    public /* synthetic */ Checks(Regex regex, Check[] checkArr, AnonymousClass3 anonymousClass3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(regex, checkArr, (Function1<? super FunctionDescriptor, String>) ((i & 4) != 0 ? new Function1() { // from class: kotlin.reflect.jvm.internal.impl.util.Checks.3
            @Override // kotlin.jvm.functions.Function1
            public final Void invoke(FunctionDescriptor functionDescriptor) {
                Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
                return null;
            }
        } : anonymousClass3));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Checks(Regex regex, Check[] checks, Function1<? super FunctionDescriptor, String> additionalChecks) {
        Intrinsics.checkNotNullParameter(regex, "regex");
        Intrinsics.checkNotNullParameter(checks, "checks");
        Intrinsics.checkNotNullParameter(additionalChecks, "additionalChecks");
        Check[] checkArr = new Check[checks.length];
        System.arraycopy(checks, 0, checkArr, 0, checks.length);
        this((Name) null, regex, (Collection<Name>) null, additionalChecks, checkArr);
    }

    public /* synthetic */ Checks(Collection collection, Check[] checkArr, AnonymousClass4 anonymousClass4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((Collection<Name>) collection, checkArr, (Function1<? super FunctionDescriptor, String>) ((i & 4) != 0 ? new Function1() { // from class: kotlin.reflect.jvm.internal.impl.util.Checks.4
            @Override // kotlin.jvm.functions.Function1
            public final Void invoke(FunctionDescriptor functionDescriptor) {
                Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
                return null;
            }
        } : anonymousClass4));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Checks(Collection<Name> nameList, Check[] checks, Function1<? super FunctionDescriptor, String> additionalChecks) {
        Intrinsics.checkNotNullParameter(nameList, "nameList");
        Intrinsics.checkNotNullParameter(checks, "checks");
        Intrinsics.checkNotNullParameter(additionalChecks, "additionalChecks");
        Check[] checkArr = new Check[checks.length];
        System.arraycopy(checks, 0, checkArr, 0, checks.length);
        this((Name) null, (Regex) null, nameList, additionalChecks, checkArr);
    }
}
