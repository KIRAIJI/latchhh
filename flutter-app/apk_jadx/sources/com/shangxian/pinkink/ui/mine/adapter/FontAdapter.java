package com.shangxian.pinkink.ui.mine.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.shangxian.pinkink.bean.FontBean;
import com.shangxian.pinkink.databinding.ItemFontBinding;
import com.shangxian.pinkink.ui.mine.adapter.FontAdapter;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FontAdapter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0002\u0015\u0016B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u00020\nH\u0016J\u001c\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000e\u001a\u00020\nH\u0016J\u001c\u0010\u000f\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nH\u0016J\u0010\u0010\u0013\u001a\u00020\f2\b\u0010\u0014\u001a\u0004\u0018\u00010\bR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/shangxian/pinkink/ui/mine/adapter/FontAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/shangxian/pinkink/ui/mine/adapter/FontAdapter$FontViewHolder;", "fontList", "", "Lcom/shangxian/pinkink/bean/FontBean;", "(Ljava/util/List;)V", "onFontItemClickListener", "Lcom/shangxian/pinkink/ui/mine/adapter/FontAdapter$OnFontItemClickListener;", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setOnFontItemClickListener", "listener", "FontViewHolder", "OnFontItemClickListener", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class FontAdapter extends RecyclerView.Adapter<FontViewHolder> {
    private final List<FontBean> fontList;
    private OnFontItemClickListener onFontItemClickListener;

    /* JADX INFO: compiled from: FontAdapter.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/shangxian/pinkink/ui/mine/adapter/FontAdapter$OnFontItemClickListener;", "", "onClickFont", "", "font", "Lcom/shangxian/pinkink/bean/FontBean;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public interface OnFontItemClickListener {
        void onClickFont(FontBean font);
    }

    public FontAdapter(List<FontBean> fontList) {
        Intrinsics.checkNotNullParameter(fontList, "fontList");
        this.fontList = fontList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public FontViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        ItemFontBinding itemFontBindingInflate = ItemFontBinding.inflate(LayoutInflater.from(parent.getContext()));
        Intrinsics.checkNotNullExpressionValue(itemFontBindingInflate, "inflate(LayoutInflater.from(parent.context))");
        return new FontViewHolder(this, itemFontBindingInflate);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.fontList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(FontViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        holder.bind(this.fontList.get(position));
    }

    public final void setOnFontItemClickListener(OnFontItemClickListener listener) {
        this.onFontItemClickListener = listener;
    }

    /* JADX INFO: compiled from: FontAdapter.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/shangxian/pinkink/ui/mine/adapter/FontAdapter$FontViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/shangxian/pinkink/databinding/ItemFontBinding;", "(Lcom/shangxian/pinkink/ui/mine/adapter/FontAdapter;Lcom/shangxian/pinkink/databinding/ItemFontBinding;)V", "bind", "", "font", "Lcom/shangxian/pinkink/bean/FontBean;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public final class FontViewHolder extends RecyclerView.ViewHolder {
        private final ItemFontBinding binding;
        final /* synthetic */ FontAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FontViewHolder(FontAdapter this$0, ItemFontBinding binding) {
            super(binding.getRoot());
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(binding, "binding");
            this.this$0 = this$0;
            this.binding = binding;
        }

        public final void bind(final FontBean font) {
            Intrinsics.checkNotNullParameter(font, "font");
            ItemFontBinding itemFontBinding = this.binding;
            final FontAdapter fontAdapter = this.this$0;
            Glide.with(itemFontBinding.ivFontLibrary).load(font.getFontCover()).into(itemFontBinding.ivFontLibrary);
            itemFontBinding.tvFontName.setText(font.getFontName());
            this.binding.getRoot().setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.mine.adapter.FontAdapter$FontViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FontAdapter.FontViewHolder.m295bind$lambda1$lambda0(fontAdapter, font, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: bind$lambda-1$lambda-0, reason: not valid java name */
        public static final void m295bind$lambda1$lambda0(FontAdapter this$0, FontBean font, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(font, "$font");
            OnFontItemClickListener onFontItemClickListener = this$0.onFontItemClickListener;
            if (onFontItemClickListener == null) {
                return;
            }
            onFontItemClickListener.onClickFont(font);
        }
    }
}
