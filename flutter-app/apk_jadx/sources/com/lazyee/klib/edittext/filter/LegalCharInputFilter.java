package com.lazyee.klib.edittext.filter;

import android.text.InputFilter;
import android.text.Spanned;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: LegalCharInputFilter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J<\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\tH\u0016J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0003H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/lazyee/klib/edittext/filter/LegalCharInputFilter;", "Landroid/text/InputFilter;", "legalRule", "", "(Ljava/lang/String;)V", "filter", "", "source", "start", "", "end", "dest", "Landroid/text/Spanned;", "dstart", "dend", "replaceAllIllegalChar", "input", "Companion", "library_release"}, k = 1, mv = {1, 4, 2})
public final class LegalCharInputFilter implements InputFilter {
    public static final String CHINESE_ID = "0123456789Xx";
    public static final String DECIMAL = "0123456789.";
    public static final String EMAIL = "0123456789@ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.";
    public static final String INT = "0123456789";
    public static final String PHONE = "0123456789-";
    private final String legalRule;

    public LegalCharInputFilter(String legalRule) {
        Intrinsics.checkNotNullParameter(legalRule, "legalRule");
        this.legalRule = legalRule;
    }

    @Override // android.text.InputFilter
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (source == null) {
            return "";
        }
        return replaceAllIllegalChar(source.toString());
    }

    private final String replaceAllIllegalChar(String input) {
        StringBuilder sb = new StringBuilder();
        String str = input;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (StringsKt.contains$default((CharSequence) this.legalRule, cCharAt, false, 2, (Object) null)) {
                sb.append(cCharAt);
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "result.toString()");
        return string;
    }
}
