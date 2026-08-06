package com.shangxian.pinkink.widget.loadmore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shangxian.pinkink.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GlobalLoadMoreView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Lcom/shangxian/pinkink/widget/loadmore/GlobalLoadMoreView;", "Lcom/chad/library/adapter/base/loadmore/BaseLoadMoreView;", "()V", "getLoadComplete", "Landroid/view/View;", "holder", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "getLoadEndView", "getLoadFailView", "getLoadingView", "getRootView", "parent", "Landroid/view/ViewGroup;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class GlobalLoadMoreView extends BaseLoadMoreView {
    @Override // com.chad.library.adapter.base.loadmore.BaseLoadMoreView
    public View getLoadComplete(BaseViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        return holder.getView(R.id.llLoadMoreEnd);
    }

    @Override // com.chad.library.adapter.base.loadmore.BaseLoadMoreView
    public View getLoadEndView(BaseViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        return holder.getView(R.id.llLoadMoreEnd);
    }

    @Override // com.chad.library.adapter.base.loadmore.BaseLoadMoreView
    public View getLoadFailView(BaseViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        return holder.getView(R.id.llLoadMoreFail);
    }

    @Override // com.chad.library.adapter.base.loadmore.BaseLoadMoreView
    public View getLoadingView(BaseViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        return holder.getView(R.id.loadingImageView);
    }

    @Override // com.chad.library.adapter.base.loadmore.BaseLoadMoreView
    public View getRootView(ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_global_load_more_view, parent, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "from(parent.context).inf…d_more_view,parent,false)");
        return viewInflate;
    }
}
