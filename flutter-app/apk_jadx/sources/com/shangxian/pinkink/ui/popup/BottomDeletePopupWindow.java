package com.shangxian.pinkink.ui.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import com.shangxian.pinkink.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BottomDeletePopupWindow.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u000bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\u00002\b\u0010\n\u001a\u0004\u0018\u00010\u0006R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/shangxian/pinkink/ui/popup/BottomDeletePopupWindow;", "Landroid/widget/PopupWindow;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "mActionCallback", "Lcom/shangxian/pinkink/ui/popup/BottomDeletePopupWindow$ActionCallback;", "mContentView", "Landroid/view/View;", "setActionCallback", "callback", "ActionCallback", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class BottomDeletePopupWindow extends PopupWindow {
    private ActionCallback mActionCallback;
    private final View mContentView;

    /* JADX INFO: compiled from: BottomDeletePopupWindow.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lcom/shangxian/pinkink/ui/popup/BottomDeletePopupWindow$ActionCallback;", "", "onDelete", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public interface ActionCallback {
        void onDelete();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BottomDeletePopupWindow(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.popup_bottom_delete, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "from(context).inflate(R.…popup_bottom_delete,null)");
        this.mContentView = viewInflate;
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.popup.BottomDeletePopupWindow$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BottomDeletePopupWindow.m301_init_$lambda0(this.f$0, view);
            }
        });
        setBackgroundDrawable(null);
        setWidth(-1);
        setContentView(viewInflate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-0, reason: not valid java name */
    public static final void m301_init_$lambda0(BottomDeletePopupWindow this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActionCallback actionCallback = this$0.mActionCallback;
        if (actionCallback == null) {
            return;
        }
        actionCallback.onDelete();
    }

    public final BottomDeletePopupWindow setActionCallback(ActionCallback callback) {
        this.mActionCallback = callback;
        return this;
    }
}
