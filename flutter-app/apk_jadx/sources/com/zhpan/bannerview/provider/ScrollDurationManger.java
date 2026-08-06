package com.zhpan.bannerview.provider;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.zhpan.bannerview.utils.BannerUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class ScrollDurationManger extends LinearLayoutManager {
    private final LinearLayoutManager mParent;
    private final int scrollDuration;

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
        return false;
    }

    public ScrollDurationManger(ViewPager2 viewPager2, int i, LinearLayoutManager linearLayoutManager) {
        super(viewPager2.getContext(), linearLayoutManager.getOrientation(), false);
        this.scrollDuration = i;
        this.mParent = linearLayoutManager;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) { // from class: com.zhpan.bannerview.provider.ScrollDurationManger.1
            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            protected int calculateTimeForDeceleration(int i2) {
                return ScrollDurationManger.this.scrollDuration;
            }
        };
        linearSmoothScroller.setTargetPosition(i);
        startSmoothScroll(linearSmoothScroller);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean performAccessibilityAction(RecyclerView.Recycler recycler, RecyclerView.State state, int i, Bundle bundle) {
        return this.mParent.performAccessibilityAction(recycler, state, i, bundle);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        this.mParent.onInitializeAccessibilityNodeInfo(recycler, state, accessibilityNodeInfoCompat);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    protected void calculateExtraLayoutSpace(RecyclerView.State state, int[] iArr) {
        try {
            Method declaredMethod = this.mParent.getClass().getDeclaredMethod("calculateExtraLayoutSpace", state.getClass(), iArr.getClass());
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(this.mParent, state, iArr);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            BannerUtils.log(e.getMessage());
        }
    }
}
