package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ItemChatAiDrawingRecommendBinding implements ViewBinding {
    public final LinearLayout llRecommendItem1;
    public final LinearLayout llRecommendItem2;
    public final LinearLayout llRecommendItem3;
    private final LinearLayout rootView;
    public final TextView tvRecommendItem1;
    public final TextView tvRecommendItem2;
    public final TextView tvRecommendItem3;

    private ItemChatAiDrawingRecommendBinding(LinearLayout rootView, LinearLayout llRecommendItem1, LinearLayout llRecommendItem2, LinearLayout llRecommendItem3, TextView tvRecommendItem1, TextView tvRecommendItem2, TextView tvRecommendItem3) {
        this.rootView = rootView;
        this.llRecommendItem1 = llRecommendItem1;
        this.llRecommendItem2 = llRecommendItem2;
        this.llRecommendItem3 = llRecommendItem3;
        this.tvRecommendItem1 = tvRecommendItem1;
        this.tvRecommendItem2 = tvRecommendItem2;
        this.tvRecommendItem3 = tvRecommendItem3;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemChatAiDrawingRecommendBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemChatAiDrawingRecommendBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.item_chat_ai_drawing_recommend, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemChatAiDrawingRecommendBinding bind(View rootView) {
        int i = R.id.llRecommendItem1;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llRecommendItem1);
        if (linearLayout != null) {
            i = R.id.llRecommendItem2;
            LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llRecommendItem2);
            if (linearLayout2 != null) {
                i = R.id.llRecommendItem3;
                LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llRecommendItem3);
                if (linearLayout3 != null) {
                    i = R.id.tvRecommendItem1;
                    TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvRecommendItem1);
                    if (textView != null) {
                        i = R.id.tvRecommendItem2;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvRecommendItem2);
                        if (textView2 != null) {
                            i = R.id.tvRecommendItem3;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvRecommendItem3);
                            if (textView3 != null) {
                                return new ItemChatAiDrawingRecommendBinding((LinearLayout) rootView, linearLayout, linearLayout2, linearLayout3, textView, textView2, textView3);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
