package com.lazyee.klib.extension;

import android.text.TextUtils;
import android.widget.TextView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TextViewExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¨\u0006\u0005"}, d2 = {"setTextOrGone", "", "Landroid/widget/TextView;", "str", "", "library_release"}, k = 2, mv = {1, 4, 2})
public final class TextViewExtensionsKt {
    public static final void setTextOrGone(TextView setTextOrGone, String str) {
        Intrinsics.checkNotNullParameter(setTextOrGone, "$this$setTextOrGone");
        String str2 = str;
        if (TextUtils.isEmpty(str2)) {
            ViewExtensionsKt.gone(setTextOrGone);
        } else {
            ViewExtensionsKt.visible(setTextOrGone);
            setTextOrGone.setText(str2);
        }
    }
}
