package com.lazyee.klib.extension;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.lazyee.klib.click.SingleClick;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ViewExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0004\n\u0002\b\u0004\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0004\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002\u001aW\u0010\u0007\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\t2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0010\u001a\u00020\u000e¢\u0006\u0002\u0010\u0011\u001a7\u0010\u0007\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\t2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0010\u001a\u00020\u000e¢\u0006\u0002\u0010\u0013\u001a\u0012\u0010\u0014\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u000e\u001a?\u0010\u0014\u001a\u00020\u0001*\u00020\u00022\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u001a\u001a\u001c\u0010\u001b\u001a\u00020\u0001*\u00020\u00022\u0010\u0010\u001c\u001a\f\u0012\u0004\u0012\u00020\u00010\u001dj\u0002`\u001e\u001a\"\u0010\u001f\u001a\u00020\u0001*\u00020\u00022\n\b\u0002\u0010 \u001a\u0004\u0018\u00010!2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010!\u001a\n\u0010#\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010$\u001a\u00020\u0001*\u00020\u0002¨\u0006%"}, d2 = {"disable", "", "Landroid/view/View;", "enable", "gone", "hideKeyboard", "invisible", "setBackground", "topLeftCorner", "", "topRightCorner", "bottomLeftCorner", "bottomRightCorner", "borderWidth", "", "borderColor", "backgroundColor", "(Landroid/view/View;FFFFLjava/lang/Integer;Ljava/lang/Integer;I)V", "corner", "(Landroid/view/View;FLjava/lang/Integer;Ljava/lang/Integer;I)V", "setMargins", "margin", "left", "top", "right", "bottom", "(Landroid/view/View;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)V", "setSingleClick", "onSingleClick", "Lkotlin/Function0;", "Lcom/lazyee/klib/typed/OnSingleClick;", "setSize", "w", "", "h", "showKeyboard", "visible", "library_release"}, k = 2, mv = {1, 4, 2})
public final class ViewExtensionsKt {
    public static final void disable(View disable) {
        Intrinsics.checkNotNullParameter(disable, "$this$disable");
        if (disable.isEnabled()) {
            disable.setEnabled(false);
        }
    }

    public static final void enable(View enable) {
        Intrinsics.checkNotNullParameter(enable, "$this$enable");
        if (enable.isEnabled()) {
            return;
        }
        enable.setEnabled(true);
    }

    public static final void gone(View gone) {
        Intrinsics.checkNotNullParameter(gone, "$this$gone");
        if (gone.getVisibility() != 8) {
            gone.setVisibility(8);
        }
    }

    public static final void visible(View visible) {
        Intrinsics.checkNotNullParameter(visible, "$this$visible");
        if (visible.getVisibility() != 0) {
            visible.setVisibility(0);
        }
    }

    public static final void invisible(View invisible) {
        Intrinsics.checkNotNullParameter(invisible, "$this$invisible");
        if (invisible.getVisibility() != 4) {
            invisible.setVisibility(4);
        }
    }

    public static final void setSingleClick(View setSingleClick, final Function0<Unit> onSingleClick) {
        Intrinsics.checkNotNullParameter(setSingleClick, "$this$setSingleClick");
        Intrinsics.checkNotNullParameter(onSingleClick, "onSingleClick");
        setSingleClick.setOnClickListener(new View.OnClickListener() { // from class: com.lazyee.klib.extension.ViewExtensionsKt.setSingleClick.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SingleClick.INSTANCE.click(onSingleClick);
            }
        });
    }

    public static /* synthetic */ void setBackground$default(View view, float f, Integer num, Integer num2, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            num = (Integer) null;
        }
        if ((i2 & 4) != 0) {
            num2 = (Integer) null;
        }
        setBackground(view, f, num, num2, i);
    }

    public static final void setBackground(View setBackground, float f, Integer num, Integer num2, int i) {
        Intrinsics.checkNotNullParameter(setBackground, "$this$setBackground");
        setBackground(setBackground, f, f, f, f, num, num2, i);
    }

    public static final void setBackground(View setBackground, float f, float f2, float f3, float f4, Integer num, Integer num2, int i) {
        Intrinsics.checkNotNullParameter(setBackground, "$this$setBackground");
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadii(new float[]{f, f, f2, f2, f3, f3, f4, f4});
        gradientDrawable.setColor(i);
        if (num2 != null && num != null) {
            gradientDrawable.setStroke(num.intValue(), num2.intValue());
        }
        setBackground.setBackground(gradientDrawable);
    }

    public static /* synthetic */ void setSize$default(View view, Number number, Number number2, int i, Object obj) {
        if ((i & 1) != 0) {
            number = (Number) null;
        }
        if ((i & 2) != 0) {
            number2 = (Number) null;
        }
        setSize(view, number, number2);
    }

    public static final void setSize(View setSize, Number number, Number number2) {
        Intrinsics.checkNotNullParameter(setSize, "$this$setSize");
        if (setSize.getLayoutParams() != null) {
            ViewGroup.LayoutParams layoutParams = setSize.getLayoutParams();
            if (number != null) {
                layoutParams.width = number.intValue();
            }
            if (number2 != null) {
                layoutParams.height = number2.intValue();
            }
            setSize.requestLayout();
        }
    }

    public static final void setMargins(View setMargins, int i) {
        Intrinsics.checkNotNullParameter(setMargins, "$this$setMargins");
        setMargins(setMargins, Integer.valueOf(i), Integer.valueOf(i), Integer.valueOf(i), Integer.valueOf(i));
    }

    public static /* synthetic */ void setMargins$default(View view, Integer num, Integer num2, Integer num3, Integer num4, int i, Object obj) {
        if ((i & 1) != 0) {
            num = (Integer) null;
        }
        if ((i & 2) != 0) {
            num2 = (Integer) null;
        }
        if ((i & 4) != 0) {
            num3 = (Integer) null;
        }
        if ((i & 8) != 0) {
            num4 = (Integer) null;
        }
        setMargins(view, num, num2, num3, num4);
    }

    public static final void setMargins(View setMargins, Integer num, Integer num2, Integer num3, Integer num4) {
        Intrinsics.checkNotNullParameter(setMargins, "$this$setMargins");
        if (num == null && num2 == null && num3 == null && num4 == null) {
            return;
        }
        if (setMargins.getLayoutParams() instanceof LinearLayout.LayoutParams) {
            ViewGroup.LayoutParams layoutParams = setMargins.getLayoutParams();
            Objects.requireNonNull(layoutParams, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
            layoutParams2.setMargins(num != null ? num.intValue() : layoutParams2.leftMargin, num2 != null ? num2.intValue() : layoutParams2.topMargin, num3 != null ? num3.intValue() : layoutParams2.rightMargin, num4 != null ? num4.intValue() : layoutParams2.rightMargin);
            setMargins.requestLayout();
            return;
        }
        if (setMargins.getLayoutParams() instanceof FrameLayout.LayoutParams) {
            ViewGroup.LayoutParams layoutParams3 = setMargins.getLayoutParams();
            Objects.requireNonNull(layoutParams3, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) layoutParams3;
            layoutParams4.setMargins(num != null ? num.intValue() : layoutParams4.leftMargin, num2 != null ? num2.intValue() : layoutParams4.topMargin, num3 != null ? num3.intValue() : layoutParams4.rightMargin, num4 != null ? num4.intValue() : layoutParams4.rightMargin);
            setMargins.requestLayout();
            return;
        }
        if (setMargins.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            ViewGroup.LayoutParams layoutParams5 = setMargins.getLayoutParams();
            Objects.requireNonNull(layoutParams5, "null cannot be cast to non-null type android.widget.RelativeLayout.LayoutParams");
            RelativeLayout.LayoutParams layoutParams6 = (RelativeLayout.LayoutParams) layoutParams5;
            layoutParams6.setMargins(num != null ? num.intValue() : layoutParams6.leftMargin, num2 != null ? num2.intValue() : layoutParams6.topMargin, num3 != null ? num3.intValue() : layoutParams6.rightMargin, num4 != null ? num4.intValue() : layoutParams6.rightMargin);
            setMargins.requestLayout();
            return;
        }
        if (setMargins.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
            ViewGroup.LayoutParams layoutParams7 = setMargins.getLayoutParams();
            Objects.requireNonNull(layoutParams7, "null cannot be cast to non-null type androidx.constraintlayout.widget.ConstraintLayout.LayoutParams");
            ConstraintLayout.LayoutParams layoutParams8 = (ConstraintLayout.LayoutParams) layoutParams7;
            layoutParams8.setMargins(num != null ? num.intValue() : layoutParams8.leftMargin, num2 != null ? num2.intValue() : layoutParams8.topMargin, num3 != null ? num3.intValue() : layoutParams8.rightMargin, num4 != null ? num4.intValue() : layoutParams8.rightMargin);
            setMargins.requestLayout();
        }
    }

    public static final void hideKeyboard(View hideKeyboard) {
        Intrinsics.checkNotNullParameter(hideKeyboard, "$this$hideKeyboard");
        if (hideKeyboard.getContext() instanceof Activity) {
            Object systemService = hideKeyboard.getContext().getSystemService("input_method");
            Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
            ((InputMethodManager) systemService).hideSoftInputFromWindow(hideKeyboard.getWindowToken(), 0);
        }
    }

    public static final void showKeyboard(View showKeyboard) {
        Intrinsics.checkNotNullParameter(showKeyboard, "$this$showKeyboard");
        if (showKeyboard.getContext() instanceof Activity) {
            Object systemService = showKeyboard.getContext().getSystemService("input_method");
            Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
            ((InputMethodManager) systemService).showSoftInput(showKeyboard, 1);
        }
    }
}
