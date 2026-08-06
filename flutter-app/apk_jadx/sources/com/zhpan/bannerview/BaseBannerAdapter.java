package com.zhpan.bannerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.zhpan.bannerview.utils.BannerUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseBannerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {
    public static final int MAX_VALUE = 1000;
    private boolean isCanLoop;
    protected List<T> mList = new ArrayList();
    private PageClickListener mPageClickListener;

    interface PageClickListener {
        void onPageClick(View view, int i, int i2);
    }

    protected abstract void bindData(BaseViewHolder<T> baseViewHolder, T t, int i, int i2);

    public abstract int getLayoutId(int i);

    protected int getViewType(int i) {
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final BaseViewHolder<T> onCreateViewHolder(ViewGroup viewGroup, int i) {
        View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(getLayoutId(i), viewGroup, false);
        final BaseViewHolder<T> baseViewHolderCreateViewHolder = createViewHolder(viewGroup, viewInflate, i);
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.zhpan.bannerview.BaseBannerAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m381x4bb5ecaf(baseViewHolderCreateViewHolder, view);
            }
        });
        return baseViewHolderCreateViewHolder;
    }

    /* JADX INFO: renamed from: lambda$onCreateViewHolder$0$com-zhpan-bannerview-BaseBannerAdapter, reason: not valid java name */
    public /* synthetic */ void m381x4bb5ecaf(BaseViewHolder baseViewHolder, View view) {
        int adapterPosition = baseViewHolder.getAdapterPosition();
        if (this.mPageClickListener == null || adapterPosition == -1) {
            return;
        }
        this.mPageClickListener.onPageClick(view, BannerUtils.getRealPosition(adapterPosition, getListSize()), adapterPosition);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(BaseViewHolder<T> baseViewHolder, int i) {
        int realPosition = BannerUtils.getRealPosition(i, getListSize());
        bindData(baseViewHolder, this.mList.get(realPosition), realPosition, getListSize());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        return getViewType(BannerUtils.getRealPosition(i, getListSize()));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        if (!this.isCanLoop || getListSize() <= 1) {
            return getListSize();
        }
        return 1000;
    }

    List<T> getData() {
        return this.mList;
    }

    void setData(List<? extends T> list) {
        if (list != null) {
            this.mList.clear();
            this.mList.addAll(list);
        }
    }

    void setCanLoop(boolean z) {
        this.isCanLoop = z;
    }

    void setPageClickListener(PageClickListener pageClickListener) {
        this.mPageClickListener = pageClickListener;
    }

    int getListSize() {
        return this.mList.size();
    }

    public boolean isCanLoop() {
        return this.isCanLoop;
    }

    public BaseViewHolder<T> createViewHolder(ViewGroup viewGroup, View view, int i) {
        return new BaseViewHolder<>(view);
    }
}
