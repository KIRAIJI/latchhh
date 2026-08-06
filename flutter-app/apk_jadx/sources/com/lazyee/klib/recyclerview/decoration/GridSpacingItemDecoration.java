package com.lazyee.klib.recyclerview.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Iterator;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: GridSpacingItemDecoration.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u001b\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B\u0017\b\u0016\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nB#\b\u0016\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u000bJ(\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J \u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0015H\u0002J\u0018\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J \u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/lazyee/klib/recyclerview/decoration/GridSpacingItemDecoration;", "Landroidx/recyclerview/widget/RecyclerView$ItemDecoration;", "spacing", "", "(F)V", TypedValues.Custom.S_COLOR, "", "(FLjava/lang/Integer;)V", "horizontalSpacing", "verticalSpacing", "(FF)V", "(FFLjava/lang/Integer;)V", "paint", "Landroid/graphics/Paint;", "getItemOffsets", "", "outRect", "Landroid/graphics/Rect;", "view", "Landroid/view/View;", "parent", "Landroidx/recyclerview/widget/RecyclerView;", "state", "Landroidx/recyclerview/widget/RecyclerView$State;", "isLastLine", "", "position", "layoutManager", "Landroidx/recyclerview/widget/GridLayoutManager;", "recyclerView", "isLineEnd", "onDraw", "c", "Landroid/graphics/Canvas;", "library_release"}, k = 1, mv = {1, 4, 2})
public final class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private float horizontalSpacing;
    private final Paint paint;
    private float verticalSpacing;

    public GridSpacingItemDecoration(float f) {
        this(f, (Integer) null);
    }

    public GridSpacingItemDecoration(float f, Integer num) {
        this(f, f, num);
    }

    public /* synthetic */ GridSpacingItemDecoration(float f, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, (i & 2) != 0 ? (Integer) null : num);
    }

    public GridSpacingItemDecoration(float f, float f2) {
        this(f, f2, null);
    }

    public /* synthetic */ GridSpacingItemDecoration(float f, float f2, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, (i & 4) != 0 ? (Integer) null : num);
    }

    public GridSpacingItemDecoration(float f, float f2, Integer num) {
        Paint paint = new Paint();
        this.paint = paint;
        this.horizontalSpacing = f;
        this.verticalSpacing = f2;
        paint.setColor(num != null ? num.intValue() : 0);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) throws Exception {
        Intrinsics.checkNotNullParameter(outRect, "outRect");
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(state, "state");
        if (!(parent.getLayoutManager() instanceof GridLayoutManager)) {
            throw new Exception("只支持GridLayoutManager");
        }
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        Objects.requireNonNull(layoutManager, "null cannot be cast to non-null type androidx.recyclerview.widget.GridLayoutManager");
        GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
        int position = gridLayoutManager.getPosition(view);
        int spanCount = gridLayoutManager.getSpanCount();
        float f = spanCount;
        outRect.left = (int) (((position % gridLayoutManager.getSpanCount()) * this.horizontalSpacing) / f);
        float f2 = this.horizontalSpacing;
        outRect.right = (int) (f2 - (((r1 + 1) * f2) / f));
        outRect.bottom = isLastLine(position, gridLayoutManager, parent) ? 0 : (int) this.verticalSpacing;
    }

    private final boolean isLineEnd(int position, GridLayoutManager layoutManager) {
        return (position % layoutManager.getSpanCount()) + 1 == layoutManager.getSpanCount();
    }

    private final boolean isLastLine(int position, GridLayoutManager layoutManager, RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        int itemCount = adapter != null ? adapter.getItemCount() : 0;
        int spanCount = itemCount % layoutManager.getSpanCount();
        if (spanCount == 0) {
            spanCount = layoutManager.getSpanCount();
        }
        return (itemCount - position) - 1 < spanCount;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(state, "state");
        super.onDraw(c, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        Objects.requireNonNull(layoutManager, "null cannot be cast to non-null type androidx.recyclerview.widget.GridLayoutManager");
        GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
        int childCount = parent.getChildCount();
        if (childCount - 1 == 0) {
            return;
        }
        Iterator<Integer> it = RangesKt.until(0, childCount).iterator();
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            View it2 = parent.getChildAt(iNextInt);
            Intrinsics.checkNotNullExpressionValue(it2, "it");
            float f = 0.0f;
            c.drawRect(it2.getRight(), it2.getTop(), it2.getRight() + (isLineEnd(iNextInt, gridLayoutManager) ? 0.0f : this.horizontalSpacing), it2.getBottom(), this.paint);
            float left = it2.getLeft();
            float bottom = it2.getBottom();
            float right = it2.getRight() + this.horizontalSpacing;
            float bottom2 = it2.getBottom();
            if (!isLastLine(iNextInt, gridLayoutManager, parent)) {
                f = this.verticalSpacing;
            }
            c.drawRect(left, bottom, right, bottom2 + f, this.paint);
        }
    }
}
