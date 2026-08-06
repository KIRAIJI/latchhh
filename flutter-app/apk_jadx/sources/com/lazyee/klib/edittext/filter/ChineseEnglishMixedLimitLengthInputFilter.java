package com.lazyee.klib.edittext.filter;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;
import java.util.regex.Pattern;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ChineseEnglishMixedInputFilter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\r\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\f\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J<\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00052\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u0005H\u0016J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R#\u0010\b\u001a\n \n*\u0004\u0018\u00010\t0\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lcom/lazyee/klib/edittext/filter/ChineseEnglishMixedLimitLengthInputFilter;", "Landroid/text/InputFilter;", "editText", "Landroid/widget/EditText;", "maxLength", "", "singleChineseLength", "(Landroid/widget/EditText;II)V", "chinesePattern", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "getChinesePattern", "()Ljava/util/regex/Pattern;", "chinesePattern$delegate", "Lkotlin/Lazy;", "getEditText", "()Landroid/widget/EditText;", "getMaxLength", "()I", "filter", "", "source", "start", "end", "dest", "Landroid/text/Spanned;", "dstart", "dend", "isChinese", "", "char", "", "library_release"}, k = 1, mv = {1, 4, 2})
public final class ChineseEnglishMixedLimitLengthInputFilter implements InputFilter {

    /* JADX INFO: renamed from: chinesePattern$delegate, reason: from kotlin metadata */
    private final Lazy chinesePattern;
    private final EditText editText;
    private final int maxLength;
    private final int singleChineseLength;

    private final Pattern getChinesePattern() {
        return (Pattern) this.chinesePattern.getValue();
    }

    public ChineseEnglishMixedLimitLengthInputFilter(EditText editText, int i, int i2) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        this.editText = editText;
        this.maxLength = i;
        this.singleChineseLength = i2;
        this.chinesePattern = LazyKt.lazy(new Function0<Pattern>() { // from class: com.lazyee.klib.edittext.filter.ChineseEnglishMixedLimitLengthInputFilter$chinesePattern$2
            @Override // kotlin.jvm.functions.Function0
            public final Pattern invoke() {
                return Pattern.compile("[一-龥]");
            }
        });
    }

    public final EditText getEditText() {
        return this.editText;
    }

    public final int getMaxLength() {
        return this.maxLength;
    }

    public /* synthetic */ ChineseEnglishMixedLimitLengthInputFilter(EditText editText, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(editText, (i3 & 2) != 0 ? 16 : i, (i3 & 4) != 0 ? 2 : i2);
    }

    @Override // android.text.InputFilter
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        int i = this.maxLength * 2;
        Editable text = this.editText.getText();
        Intrinsics.checkNotNullExpressionValue(text, "editText.text");
        Editable editable = text;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = 1;
            if (i2 >= editable.length()) {
                break;
            }
            if (isChinese(editable.charAt(i2))) {
                i4 = this.singleChineseLength;
            }
            i3 += i4;
            i2++;
        }
        StringBuilder sb = new StringBuilder();
        if (source != null) {
            for (int i5 = 0; i5 < source.length(); i5++) {
                char cCharAt = source.charAt(i5);
                if (i3 < i) {
                    i3 += isChinese(cCharAt) ? this.singleChineseLength : 1;
                    sb.append(cCharAt);
                }
            }
        }
        return sb;
    }

    private final boolean isChinese(char c) {
        return getChinesePattern().matcher(String.valueOf(c)).matches();
    }
}
