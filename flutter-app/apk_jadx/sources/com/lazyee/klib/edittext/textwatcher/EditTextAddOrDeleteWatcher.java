package com.lazyee.klib.edittext.textwatcher;

import android.text.Editable;
import android.text.TextWatcher;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EditTextAddOrDeleteWatcher.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\t\u0018\u00002\u00020\u0001:\u0001\u0018B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J*\u0010\u000f\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004H\u0016J*\u0010\u0014\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004H\u0016J\u0010\u0010\u0016\u001a\u00020\f2\b\u0010\u0017\u001a\u0004\u0018\u00010\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/lazyee/klib/edittext/textwatcher/EditTextAddOrDeleteWatcher;", "Landroid/text/TextWatcher;", "()V", "addedCount", "", "beforeString", "", "deleteCount", "editIndex", "mEditCallback", "Lcom/lazyee/klib/edittext/textwatcher/EditTextAddOrDeleteWatcher$EditCallback;", "afterTextChanged", "", "s", "Landroid/text/Editable;", "beforeTextChanged", "", "start", "count", "after", "onTextChanged", "before", "setEditCallback", "callback", "EditCallback", "library_release"}, k = 1, mv = {1, 4, 2})
public final class EditTextAddOrDeleteWatcher implements TextWatcher {
    private int addedCount;
    private int deleteCount;
    private EditCallback mEditCallback;
    private String beforeString = "";
    private int editIndex = -1;

    /* JADX INFO: compiled from: EditTextAddOrDeleteWatcher.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0007H&¨\u0006\n"}, d2 = {"Lcom/lazyee/klib/edittext/textwatcher/EditTextAddOrDeleteWatcher$EditCallback;", "", "onTextAdded", "", "index", "", "addedText", "", "onTextDeleted", "deletedText", "library_release"}, k = 1, mv = {1, 4, 2})
    public interface EditCallback {
        void onTextAdded(int index, String addedText);

        void onTextDeleted(int index, String deletedText);
    }

    public final void setEditCallback(EditCallback callback) {
        this.mEditCallback = callback;
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        this.beforeString = String.valueOf(s);
        this.deleteCount = 0;
        this.addedCount = 0;
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.editIndex = start;
        this.deleteCount = before;
        this.addedCount = count;
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable s) {
        int i = this.deleteCount;
        if (i > 0) {
            String str = this.beforeString;
            int i2 = this.editIndex;
            Objects.requireNonNull(str, "null cannot be cast to non-null type java.lang.String");
            String strSubstring = str.substring(i2, i + i2);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            EditCallback editCallback = this.mEditCallback;
            if (editCallback != null) {
                editCallback.onTextDeleted(this.editIndex, strSubstring);
            }
        }
        if (this.addedCount > 0) {
            String strValueOf = String.valueOf(s);
            int i3 = this.editIndex;
            int i4 = this.addedCount + i3;
            Objects.requireNonNull(strValueOf, "null cannot be cast to non-null type java.lang.String");
            String strSubstring2 = strValueOf.substring(i3, i4);
            Intrinsics.checkNotNullExpressionValue(strSubstring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            EditCallback editCallback2 = this.mEditCallback;
            if (editCallback2 != null) {
                editCallback2.onTextAdded(this.editIndex, strSubstring2);
            }
        }
    }
}
