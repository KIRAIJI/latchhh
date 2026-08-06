package com.lazyee.klib.edittext.filter;

import android.text.InputFilter;
import android.text.Spanned;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: BanSpecialCharInputFilter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004H\u0002J<\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\rH\u0016J\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004H\u0002R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/lazyee/klib/edittext/filter/BanSpecialCharInputFilter;", "Landroid/text/InputFilter;", "banSpecialChars", "", "", "(Ljava/util/List;)V", "checkInputLegal", "", "input", "filter", "", "source", "start", "", "end", "dest", "Landroid/text/Spanned;", "dstart", "dend", "replaceAllIllegalChar", "library_release"}, k = 1, mv = {1, 4, 2})
public final class BanSpecialCharInputFilter implements InputFilter {
    private final List<String> banSpecialChars;

    public BanSpecialCharInputFilter(List<String> banSpecialChars) {
        Intrinsics.checkNotNullParameter(banSpecialChars, "banSpecialChars");
        this.banSpecialChars = banSpecialChars;
    }

    @Override // android.text.InputFilter
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (source == null) {
            return "";
        }
        String string = source.toString();
        return !checkInputLegal(string) ? replaceAllIllegalChar(string) : source;
    }

    private final boolean checkInputLegal(String input) {
        Iterator<T> it = this.banSpecialChars.iterator();
        while (it.hasNext()) {
            if (StringsKt.contains$default((CharSequence) input, (CharSequence) it.next(), false, 2, (Object) null)) {
                return false;
            }
        }
        return true;
    }

    private final String replaceAllIllegalChar(String input) {
        Iterator<T> it = this.banSpecialChars.iterator();
        String strReplace$default = input;
        while (it.hasNext()) {
            strReplace$default = StringsKt.replace$default(strReplace$default, (String) it.next(), "", false, 4, (Object) null);
        }
        return strReplace$default;
    }
}
