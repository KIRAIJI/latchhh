package com.lazyee.klib.extension;

import android.content.Context;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RecyclerViewExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003\u001a\u001a\u0010\u0004\u001a\u00020\u0005*\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007¨\u0006\t"}, d2 = {"getAllVisibleItems", "", "Lcom/lazyee/klib/extension/VisibleItemView;", "Landroidx/recyclerview/widget/RecyclerView;", "scrollPositionToCenter", "", "position", "", TypedValues.TransitionType.S_DURATION, "library_release"}, k = 2, mv = {1, 4, 2})
public final class RecyclerViewExtensionsKt {
    public static final void scrollPositionToCenter(RecyclerView scrollPositionToCenter, int i, int i2) {
        Intrinsics.checkNotNullParameter(scrollPositionToCenter, "$this$scrollPositionToCenter");
        try {
            RecyclerView.LayoutManager layoutManager = scrollPositionToCenter.getLayoutManager();
            if (layoutManager == null) {
                throw new NullPointerException("null cannot be cast to non-null type androidx.recyclerview.widget.LinearLayoutManager");
            }
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            Context context = scrollPositionToCenter.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            CenterSmoothScroller centerSmoothScroller = new CenterSmoothScroller(context, i2);
            centerSmoothScroller.setTargetPosition(i < 0 ? 0 : i);
            if (centerSmoothScroller.isRunning()) {
                return;
            }
            linearLayoutManager.startSmoothScroll(centerSmoothScroller);
        } catch (Exception e) {
            e.printStackTrace();
            scrollPositionToCenter.scrollToPosition(i);
        }
    }

    public static final List<VisibleItemView> getAllVisibleItems(RecyclerView getAllVisibleItems) {
        int iFindLastVisibleItemPosition;
        int iFindFirstVisibleItemPosition;
        Intrinsics.checkNotNullParameter(getAllVisibleItems, "$this$getAllVisibleItems");
        ArrayList arrayList = new ArrayList();
        if (getAllVisibleItems.getLayoutManager() != null) {
            if (getAllVisibleItems.getLayoutManager() instanceof LinearLayoutManager) {
                RecyclerView.LayoutManager layoutManager = getAllVisibleItems.getLayoutManager();
                Objects.requireNonNull(layoutManager, "null cannot be cast to non-null type androidx.recyclerview.widget.LinearLayoutManager");
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                iFindLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            } else if (getAllVisibleItems.getLayoutManager() instanceof GridLayoutManager) {
                RecyclerView.LayoutManager layoutManager2 = getAllVisibleItems.getLayoutManager();
                Objects.requireNonNull(layoutManager2, "null cannot be cast to non-null type androidx.recyclerview.widget.GridLayoutManager");
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager2;
                iFindFirstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
                iFindLastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
            } else {
                iFindLastVisibleItemPosition = -1;
                iFindFirstVisibleItemPosition = -1;
            }
            if (iFindFirstVisibleItemPosition != -1 && iFindLastVisibleItemPosition != -1 && iFindFirstVisibleItemPosition <= iFindLastVisibleItemPosition) {
                while (true) {
                    RecyclerView.LayoutManager layoutManager3 = getAllVisibleItems.getLayoutManager();
                    Intrinsics.checkNotNull(layoutManager3);
                    View viewFindViewByPosition = layoutManager3.findViewByPosition(iFindFirstVisibleItemPosition);
                    if (viewFindViewByPosition != null) {
                        Intrinsics.checkNotNullExpressionValue(viewFindViewByPosition, "layoutManager!!.findView…ion(position) ?: continue");
                        arrayList.add(new VisibleItemView(iFindFirstVisibleItemPosition, viewFindViewByPosition));
                    }
                    if (iFindFirstVisibleItemPosition == iFindLastVisibleItemPosition) {
                        break;
                    }
                    iFindFirstVisibleItemPosition++;
                }
            }
        }
        return arrayList;
    }
}
