package com.lazyee.klib.click;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SingleClick.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0006\u001a\u00020\u00072\u0010\u0010\b\u001a\f\u0012\u0004\u0012\u00020\u00070\tj\u0002`\nJ\u000e\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/lazyee/klib/click/SingleClick;", "", "()V", "intervalTime", "", "lastClickTime", "click", "", "onClick", "Lkotlin/Function0;", "Lcom/lazyee/klib/typed/OnSingleClick;", "init", "time", "library_release"}, k = 1, mv = {1, 4, 2})
public final class SingleClick {
    public static final SingleClick INSTANCE = new SingleClick();
    private static long intervalTime = 300;
    private static long lastClickTime;

    private SingleClick() {
    }

    public final void init(long time) {
        intervalTime = time;
    }

    public final void click(Function0<Unit> onClick) {
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - lastClickTime < intervalTime) {
            return;
        }
        lastClickTime = jCurrentTimeMillis;
        onClick.invoke();
    }
}
