package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: numbers.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class NumbersKt {
    public static final NumberWithRadix extractRadix(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (StringsKt.startsWith$default(value, "0x", false, 2, (Object) null) || StringsKt.startsWith$default(value, "0X", false, 2, (Object) null)) {
            String strSubstring = value.substring(2);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.String).substring(startIndex)");
            return new NumberWithRadix(strSubstring, 16);
        }
        if (StringsKt.startsWith$default(value, "0b", false, 2, (Object) null) || StringsKt.startsWith$default(value, "0B", false, 2, (Object) null)) {
            String strSubstring2 = value.substring(2);
            Intrinsics.checkNotNullExpressionValue(strSubstring2, "(this as java.lang.String).substring(startIndex)");
            return new NumberWithRadix(strSubstring2, 2);
        }
        return new NumberWithRadix(value, 10);
    }
}
