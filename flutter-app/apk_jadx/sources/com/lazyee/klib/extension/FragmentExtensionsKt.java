package com.lazyee.klib.extension;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FragmentExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0006"}, d2 = {"hideKeyboard", "", "Landroidx/fragment/app/Fragment;", "view", "Landroid/view/View;", "showKeyboard", "library_release"}, k = 2, mv = {1, 4, 2})
public final class FragmentExtensionsKt {
    public static final void hideKeyboard(Fragment hideKeyboard, View view) {
        Intrinsics.checkNotNullParameter(hideKeyboard, "$this$hideKeyboard");
        Intrinsics.checkNotNullParameter(view, "view");
        FragmentActivity activity = hideKeyboard.getActivity();
        Object systemService = activity != null ? activity.getSystemService("input_method") : null;
        Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        ((InputMethodManager) systemService).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static final void showKeyboard(Fragment showKeyboard, View view) {
        Intrinsics.checkNotNullParameter(showKeyboard, "$this$showKeyboard");
        Intrinsics.checkNotNullParameter(view, "view");
        FragmentActivity activity = showKeyboard.getActivity();
        Object systemService = activity != null ? activity.getSystemService("input_method") : null;
        Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        ((InputMethodManager) systemService).showSoftInput(view, 1);
    }
}
