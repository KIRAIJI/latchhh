package com.lazyee.klib.extension;

import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RecyclerViewExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lcom/lazyee/klib/extension/VisibleItemView;", "", "position", "", "itemView", "Landroid/view/View;", "(ILandroid/view/View;)V", "getItemView", "()Landroid/view/View;", "getPosition", "()I", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "library_release"}, k = 1, mv = {1, 4, 2})
public final /* data */ class VisibleItemView {
    private final View itemView;
    private final int position;

    public static /* synthetic */ VisibleItemView copy$default(VisibleItemView visibleItemView, int i, View view, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = visibleItemView.position;
        }
        if ((i2 & 2) != 0) {
            view = visibleItemView.itemView;
        }
        return visibleItemView.copy(i, view);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getPosition() {
        return this.position;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final View getItemView() {
        return this.itemView;
    }

    public final VisibleItemView copy(int position, View itemView) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        return new VisibleItemView(position, itemView);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof VisibleItemView)) {
            return false;
        }
        VisibleItemView visibleItemView = (VisibleItemView) other;
        return this.position == visibleItemView.position && Intrinsics.areEqual(this.itemView, visibleItemView.itemView);
    }

    public int hashCode() {
        int i = this.position * 31;
        View view = this.itemView;
        return i + (view != null ? view.hashCode() : 0);
    }

    public String toString() {
        return "VisibleItemView(position=" + this.position + ", itemView=" + this.itemView + ")";
    }

    public VisibleItemView(int i, View itemView) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        this.position = i;
        this.itemView = itemView;
    }

    public final View getItemView() {
        return this.itemView;
    }

    public final int getPosition() {
        return this.position;
    }
}
