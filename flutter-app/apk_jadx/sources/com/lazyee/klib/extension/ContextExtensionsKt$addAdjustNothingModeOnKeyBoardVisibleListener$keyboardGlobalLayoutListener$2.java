package com.lazyee.klib.extension;

import android.view.View;
import com.lazyee.klib.extension.ContextExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ContextExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "p1", "Landroid/view/View;", "invoke"}, k = 3, mv = {1, 4, 2})
final /* synthetic */ class ContextExtensionsKt$addAdjustNothingModeOnKeyBoardVisibleListener$keyboardGlobalLayoutListener$2 extends FunctionReferenceImpl implements Function1<View, Integer> {
    public static final ContextExtensionsKt$addAdjustNothingModeOnKeyBoardVisibleListener$keyboardGlobalLayoutListener$2 INSTANCE = new ContextExtensionsKt$addAdjustNothingModeOnKeyBoardVisibleListener$keyboardGlobalLayoutListener$2();

    ContextExtensionsKt$addAdjustNothingModeOnKeyBoardVisibleListener$keyboardGlobalLayoutListener$2() {
        super(1, null, "getVisibleHeight", "invoke(Landroid/view/View;)I", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Integer invoke(View view) {
        return Integer.valueOf(invoke2(view));
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final int invoke2(View p1) {
        Intrinsics.checkNotNullParameter(p1, "p1");
        return ContextExtensionsKt.AnonymousClass4.INSTANCE.invoke2(p1);
    }
}
