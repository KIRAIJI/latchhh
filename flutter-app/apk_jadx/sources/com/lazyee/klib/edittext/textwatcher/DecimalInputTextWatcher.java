package com.lazyee.klib.edittext.textwatcher;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import kotlin.Metadata;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: DecimalInputTextWatcher.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J*\u0010\t\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0003H\u0016J*\u0010\u000e\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/lazyee/klib/edittext/textwatcher/DecimalInputTextWatcher;", "Landroid/text/TextWatcher;", "decimalLength", "", "(I)V", "afterTextChanged", "", "s", "Landroid/text/Editable;", "beforeTextChanged", "", "start", "count", "after", "onTextChanged", "before", "library_release"}, k = 1, mv = {1, 4, 2})
public final class DecimalInputTextWatcher implements TextWatcher {
    private final int decimalLength;

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public DecimalInputTextWatcher(int i) {
        this.decimalLength = i;
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable s) {
        int iIndexOf$default;
        if (s != null) {
            if (s.length() > 1) {
                Editable editable = s;
                if (StringsKt.startsWith$default((CharSequence) editable, '0', false, 2, (Object) null) && !StringsKt.startsWith$default((CharSequence) editable, (CharSequence) "0.", false, 2, (Object) null)) {
                    s.delete(0, s.length() - StringsKt.trimStart(editable, '0').length());
                    if (TextUtils.isEmpty(editable)) {
                        s.append("0");
                    }
                }
            }
            Editable editable2 = s;
            if ((editable2.length() > 0) && StringsKt.startsWith$default((CharSequence) editable2, (CharSequence) ".", false, 2, (Object) null)) {
                s.insert(0, "0");
            }
            int i = 0;
            for (int i2 = 0; i2 < editable2.length(); i2++) {
                if (editable2.charAt(i2) == '.') {
                    i++;
                }
            }
            if (i > 1) {
                while (true) {
                    int i3 = 0;
                    for (int i4 = 0; i4 < editable2.length(); i4++) {
                        if (editable2.charAt(i4) == '.') {
                            i3++;
                        }
                    }
                    if (i3 <= 1) {
                        break;
                    }
                    int i5 = -1;
                    int length = editable2.length() - 1;
                    while (true) {
                        if (length < 0) {
                            break;
                        }
                        if (editable2.charAt(length) == '.') {
                            i5 = length;
                            break;
                        }
                        length--;
                    }
                    s.delete(i5, i5 + 1);
                }
            }
            if (!StringsKt.contains$default((CharSequence) editable2, '.', false, 2, (Object) null) || s.length() <= (iIndexOf$default = StringsKt.indexOf$default((CharSequence) editable2, '.', 0, false, 6, (Object) null) + this.decimalLength + 1)) {
                return;
            }
            s.delete(iIndexOf$default, s.length());
        }
    }
}
