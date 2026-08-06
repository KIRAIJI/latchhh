package com.lazyee.klib.extension;

import android.view.Window;
import android.view.WindowManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: WindowExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\u001a\u0010\u0003\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005\u001a$\u0010\u0003\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u001a\n\u0010\b\u001a\u00020\u0001*\u00020\u0002¨\u0006\t"}, d2 = {"hideKeyboard", "", "Landroid/view/Window;", "setSize", "width", "", "height", "gravity", "showKeyboard", "library_release"}, k = 2, mv = {1, 4, 2})
public final class WindowExtensionsKt {
    public static final void setSize(Window setSize, int i, int i2) {
        Intrinsics.checkNotNullParameter(setSize, "$this$setSize");
        WindowManager.LayoutParams attributes = setSize.getAttributes();
        attributes.width = i;
        attributes.height = i2;
        setSize.setAttributes(attributes);
    }

    public static /* synthetic */ void setSize$default(Window window, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 4) != 0) {
            i3 = 17;
        }
        setSize(window, i, i2, i3);
    }

    public static final void setSize(Window setSize, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(setSize, "$this$setSize");
        WindowManager.LayoutParams attributes = setSize.getAttributes();
        attributes.width = i;
        attributes.height = i2;
        attributes.gravity = i3;
        setSize.setAttributes(attributes);
    }

    public static final void hideKeyboard(Window hideKeyboard) {
        Intrinsics.checkNotNullParameter(hideKeyboard, "$this$hideKeyboard");
        hideKeyboard.setSoftInputMode(2);
    }

    public static final void showKeyboard(Window showKeyboard) {
        Intrinsics.checkNotNullParameter(showKeyboard, "$this$showKeyboard");
        showKeyboard.setSoftInputMode(4);
    }
}
