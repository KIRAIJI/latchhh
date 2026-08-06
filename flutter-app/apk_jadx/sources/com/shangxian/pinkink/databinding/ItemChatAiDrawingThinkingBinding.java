package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ItemChatAiDrawingThinkingBinding implements ViewBinding {
    public final ImageView ivThinking;
    private final LinearLayout rootView;
    public final TextView tvTextMsg;

    private ItemChatAiDrawingThinkingBinding(LinearLayout rootView, ImageView ivThinking, TextView tvTextMsg) {
        this.rootView = rootView;
        this.ivThinking = ivThinking;
        this.tvTextMsg = tvTextMsg;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemChatAiDrawingThinkingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemChatAiDrawingThinkingBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.item_chat_ai_drawing_thinking, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemChatAiDrawingThinkingBinding bind(View rootView) {
        int i = R.id.ivThinking;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivThinking);
        if (imageView != null) {
            i = R.id.tvTextMsg;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvTextMsg);
            if (textView != null) {
                return new ItemChatAiDrawingThinkingBinding((LinearLayout) rootView, imageView, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
