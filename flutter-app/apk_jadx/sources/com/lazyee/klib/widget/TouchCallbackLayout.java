package com.lazyee.klib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TouchCallbackLayout.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ$\u0010\u0013\u001a\u00020\u000e2\u001c\u0010\u0014\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fj\b\u0012\u0004\u0012\u00020\r`\u000fJ$\u0010\u0015\u001a\u00020\u000e2\u001c\u0010\u0014\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fj\b\u0012\u0004\u0012\u00020\r`\u000fJ$\u0010\u0016\u001a\u00020\u000e2\u001c\u0010\u0014\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fj\b\u0012\u0004\u0012\u00020\r`\u000fJ$\u0010\u0017\u001a\u00020\u000e2\u001c\u0010\u0014\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fj\b\u0012\u0004\u0012\u00020\r`\u000fJ\u0012\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\rH\u0016J\u0010\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\rH\u0002J\u0010\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\rH\u0002J\u0010\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\rH\u0002J\u0010\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\rH\u0002R(\u0010\u000b\u001a\u001c\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0018\u00010\fj\n\u0012\u0004\u0012\u00020\r\u0018\u0001`\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R(\u0010\u0010\u001a\u001c\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0018\u00010\fj\n\u0012\u0004\u0012\u00020\r\u0018\u0001`\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R(\u0010\u0011\u001a\u001c\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0018\u00010\fj\n\u0012\u0004\u0012\u00020\r\u0018\u0001`\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R(\u0010\u0012\u001a\u001c\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0018\u00010\fj\n\u0012\u0004\u0012\u00020\r\u0018\u0001`\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/lazyee/klib/widget/TouchCallbackLayout;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "mTouchCancelCallback", "Lkotlin/Function1;", "Landroid/view/MotionEvent;", "", "Lcom/lazyee/klib/typed/TCallback;", "mTouchDownCallback", "mTouchMoveCallback", "mTouchUpCallback", "addTouchCancelCallback", "callback", "addTouchDownCallback", "addTouchMoveCallback", "addTouchUpCallback", "dispatchTouchEvent", "", "ev", "onTouchCancel", "onTouchDown", "onTouchMove", "onTouchUp", "library_release"}, k = 1, mv = {1, 4, 2})
public final class TouchCallbackLayout extends FrameLayout {
    private Function1<? super MotionEvent, Unit> mTouchCancelCallback;
    private Function1<? super MotionEvent, Unit> mTouchDownCallback;
    private Function1<? super MotionEvent, Unit> mTouchMoveCallback;
    private Function1<? super MotionEvent, Unit> mTouchUpCallback;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TouchCallbackLayout(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TouchCallbackLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TouchCallbackLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev != null) {
            int action = ev.getAction();
            if (action == 0) {
                onTouchDown(ev);
            } else if (action == 1) {
                onTouchUp(ev);
            } else if (action == 2) {
                onTouchMove(ev);
            } else if (action == 3) {
                onTouchCancel(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public final void addTouchDownCallback(Function1<? super MotionEvent, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mTouchDownCallback = callback;
    }

    public final void addTouchUpCallback(Function1<? super MotionEvent, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mTouchUpCallback = callback;
    }

    public final void addTouchMoveCallback(Function1<? super MotionEvent, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mTouchMoveCallback = callback;
    }

    public final void addTouchCancelCallback(Function1<? super MotionEvent, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mTouchCancelCallback = callback;
    }

    private final void onTouchDown(MotionEvent ev) {
        Function1<? super MotionEvent, Unit> function1 = this.mTouchDownCallback;
        if (function1 != null) {
            function1.invoke(ev);
        }
    }

    private final void onTouchMove(MotionEvent ev) {
        Function1<? super MotionEvent, Unit> function1 = this.mTouchMoveCallback;
        if (function1 != null) {
            function1.invoke(ev);
        }
    }

    private final void onTouchCancel(MotionEvent ev) {
        Function1<? super MotionEvent, Unit> function1 = this.mTouchCancelCallback;
        if (function1 != null) {
            function1.invoke(ev);
        }
    }

    private final void onTouchUp(MotionEvent ev) {
        Function1<? super MotionEvent, Unit> function1 = this.mTouchUpCallback;
        if (function1 != null) {
            function1.invoke(ev);
        }
    }
}
