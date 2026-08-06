package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ItemChatAiDrawingResultBinding implements ViewBinding {
    public final RoundedImageView ivAiDrawResult;
    private final LinearLayout rootView;
    public final TextView tvTime;

    private ItemChatAiDrawingResultBinding(LinearLayout rootView, RoundedImageView ivAiDrawResult, TextView tvTime) {
        this.rootView = rootView;
        this.ivAiDrawResult = ivAiDrawResult;
        this.tvTime = tvTime;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemChatAiDrawingResultBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemChatAiDrawingResultBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.item_chat_ai_drawing_result, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemChatAiDrawingResultBinding bind(View rootView) {
        int i = R.id.ivAiDrawResult;
        RoundedImageView roundedImageView = (RoundedImageView) ViewBindings.findChildViewById(rootView, R.id.ivAiDrawResult);
        if (roundedImageView != null) {
            i = R.id.tvTime;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvTime);
            if (textView != null) {
                return new ItemChatAiDrawingResultBinding((LinearLayout) rootView, roundedImageView, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
