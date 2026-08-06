package com.lazyee.klib.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.lazyee.klib.R;

/* JADX INFO: loaded from: classes.dex */
public final class WidgetDebugViewBinding implements ViewBinding {
    public final CoordinatorLayout coordinatorLayout;
    public final LinearLayout llDebugContent;
    public final RecyclerView recyclerView;
    private final FrameLayout rootView;
    public final TextView tvClose;
    public final TextView tvTitle;

    private WidgetDebugViewBinding(FrameLayout frameLayout, CoordinatorLayout coordinatorLayout, LinearLayout linearLayout, RecyclerView recyclerView, TextView textView, TextView textView2) {
        this.rootView = frameLayout;
        this.coordinatorLayout = coordinatorLayout;
        this.llDebugContent = linearLayout;
        this.recyclerView = recyclerView;
        this.tvClose = textView;
        this.tvTitle = textView2;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static WidgetDebugViewBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static WidgetDebugViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(R.layout.widget_debug_view, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static WidgetDebugViewBinding bind(View view) {
        int i = R.id.coordinatorLayout;
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) view.findViewById(i);
        if (coordinatorLayout != null) {
            i = R.id.llDebugContent;
            LinearLayout linearLayout = (LinearLayout) view.findViewById(i);
            if (linearLayout != null) {
                i = R.id.recyclerView;
                RecyclerView recyclerView = (RecyclerView) view.findViewById(i);
                if (recyclerView != null) {
                    i = R.id.tvClose;
                    TextView textView = (TextView) view.findViewById(i);
                    if (textView != null) {
                        i = R.id.tvTitle;
                        TextView textView2 = (TextView) view.findViewById(i);
                        if (textView2 != null) {
                            return new WidgetDebugViewBinding((FrameLayout) view, coordinatorLayout, linearLayout, recyclerView, textView, textView2);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
