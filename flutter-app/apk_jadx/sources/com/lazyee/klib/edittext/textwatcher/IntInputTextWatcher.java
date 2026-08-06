package com.lazyee.klib.edittext.textwatcher;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import kotlin.Metadata;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: IntInputTextWatcher.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016J*\u0010\u0007\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0016J*\u0010\r\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016¨\u0006\u000f"}, d2 = {"Lcom/lazyee/klib/edittext/textwatcher/IntInputTextWatcher;", "Landroid/text/TextWatcher;", "()V", "afterTextChanged", "", "s", "Landroid/text/Editable;", "beforeTextChanged", "", "start", "", "count", "after", "onTextChanged", "before", "library_release"}, k = 1, mv = {1, 4, 2})
public final class IntInputTextWatcher implements TextWatcher {
    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable s) {
        if (s == null || s.length() <= 1) {
            return;
        }
        Editable editable = s;
        if (StringsKt.startsWith$default((CharSequence) editable, '0', false, 2, (Object) null)) {
            s.delete(0, s.length() - StringsKt.trimStart(editable, '0').length());
            if (TextUtils.isEmpty(editable)) {
                s.append("0");
            }
        }
    }
}
