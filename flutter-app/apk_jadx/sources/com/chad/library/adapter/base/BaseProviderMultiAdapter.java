package com.chad.library.adapter.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.exifinterface.media.ExifInterface;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseProviderMultiAdapter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00030\u0002B\u0017\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0016J\u0018\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H\u0014J\u0010\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0003H\u0014J\u0018\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H\u0014J\u001d\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u001aJ+\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00028\u00002\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0014¢\u0006\u0002\u0010\u001eJ\u0010\u0010\u001f\u001a\u00020\u00142\u0006\u0010 \u001a\u00020\u0014H\u0014J\u0018\u0010!\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\t2\u0006\u0010\u0013\u001a\u00020\u0014H\u0014J\u001e\u0010\"\u001a\u00020\u00142\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u001c2\u0006\u0010 \u001a\u00020\u0014H$J\u0018\u0010#\u001a\u00020\u00032\u0006\u0010$\u001a\u00020%2\u0006\u0010\u0013\u001a\u00020\u0014H\u0014J\u0010\u0010&\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0003H\u0016J\u0010\u0010'\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0003H\u0016R'\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\t0\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006("}, d2 = {"Lcom/chad/library/adapter/base/BaseProviderMultiAdapter;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "data", "", "(Ljava/util/List;)V", "mItemProviders", "Landroid/util/SparseArray;", "Lcom/chad/library/adapter/base/provider/BaseItemProvider;", "getMItemProviders", "()Landroid/util/SparseArray;", "mItemProviders$delegate", "Lkotlin/Lazy;", "addItemProvider", "", "provider", "bindChildClick", "viewHolder", "viewType", "", "bindClick", "bindViewClickListener", "convert", "holder", "item", "(Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;Ljava/lang/Object;)V", "payloads", "", "", "(Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;Ljava/lang/Object;Ljava/util/List;)V", "getDefItemViewType", "position", "getItemProvider", "getItemType", "onCreateDefViewHolder", "parent", "Landroid/view/ViewGroup;", "onViewAttachedToWindow", "onViewDetachedFromWindow", "com.github.CymChad.brvah"}, k = 1, mv = {1, 6, 0}, xi = 48)
public abstract class BaseProviderMultiAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    /* JADX INFO: renamed from: mItemProviders$delegate, reason: from kotlin metadata */
    private final Lazy mItemProviders;

    /* JADX WARN: Multi-variable type inference failed */
    public BaseProviderMultiAdapter() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    protected abstract int getItemType(List<? extends T> data, int position);

    public /* synthetic */ BaseProviderMultiAdapter(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : list);
    }

    public BaseProviderMultiAdapter(List<T> list) {
        super(0, list);
        this.mItemProviders = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<SparseArray<BaseItemProvider<T>>>() { // from class: com.chad.library.adapter.base.BaseProviderMultiAdapter$mItemProviders$2
            @Override // kotlin.jvm.functions.Function0
            public final SparseArray<BaseItemProvider<T>> invoke() {
                return new SparseArray<>();
            }
        });
    }

    private final SparseArray<BaseItemProvider<T>> getMItemProviders() {
        return (SparseArray) this.mItemProviders.getValue();
    }

    public void addItemProvider(BaseItemProvider<T> provider) {
        Intrinsics.checkNotNullParameter(provider, "provider");
        provider.setAdapter$com_github_CymChad_brvah(this);
        getMItemProviders().put(provider.getItemViewType(), provider);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        BaseItemProvider<T> itemProvider = getItemProvider(viewType);
        if (itemProvider == null) {
            throw new IllegalStateException(("ViewType: " + viewType + " no such provider found，please use addItemProvider() first!").toString());
        }
        Context context = parent.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "parent.context");
        itemProvider.setContext(context);
        BaseViewHolder baseViewHolderOnCreateViewHolder = itemProvider.onCreateViewHolder(parent, viewType);
        itemProvider.onViewHolderCreated(baseViewHolderOnCreateViewHolder, viewType);
        return baseViewHolderOnCreateViewHolder;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected int getDefItemViewType(int position) {
        return getItemType(getData(), position);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected void convert(BaseViewHolder holder, T item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        BaseItemProvider<T> itemProvider = getItemProvider(holder.getItemViewType());
        Intrinsics.checkNotNull(itemProvider);
        itemProvider.convert(holder, item);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected void convert(BaseViewHolder holder, T item, List<? extends Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
        BaseItemProvider<T> itemProvider = getItemProvider(holder.getItemViewType());
        Intrinsics.checkNotNull(itemProvider);
        itemProvider.convert(holder, item, payloads);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected void bindViewClickListener(BaseViewHolder viewHolder, int viewType) {
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        super.bindViewClickListener(viewHolder, viewType);
        bindClick(viewHolder);
        bindChildClick(viewHolder, viewType);
    }

    protected BaseItemProvider<T> getItemProvider(int viewType) {
        return getMItemProviders().get(viewType);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        super.onViewAttachedToWindow(holder);
        BaseItemProvider<T> itemProvider = getItemProvider(holder.getItemViewType());
        if (itemProvider != null) {
            itemProvider.onViewAttachedToWindow(holder);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(BaseViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        super.onViewDetachedFromWindow(holder);
        BaseItemProvider<T> itemProvider = getItemProvider(holder.getItemViewType());
        if (itemProvider != null) {
            itemProvider.onViewDetachedFromWindow(holder);
        }
    }

    protected void bindClick(final BaseViewHolder viewHolder) {
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        if (getMOnItemClickListener() == null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.chad.library.adapter.base.BaseProviderMultiAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    BaseProviderMultiAdapter.m68bindClick$lambda2(viewHolder, this, view);
                }
            });
        }
        if (getMOnItemLongClickListener() == null) {
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.chad.library.adapter.base.BaseProviderMultiAdapter$$ExternalSyntheticLambda2
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return BaseProviderMultiAdapter.m69bindClick$lambda3(viewHolder, this, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: bindClick$lambda-2, reason: not valid java name */
    public static final void m68bindClick$lambda2(BaseViewHolder viewHolder, BaseProviderMultiAdapter this$0, View it) {
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
        if (bindingAdapterPosition == -1) {
            return;
        }
        int headerLayoutCount = bindingAdapterPosition - this$0.getHeaderLayoutCount();
        BaseItemProvider<T> baseItemProvider = this$0.getMItemProviders().get(viewHolder.getItemViewType());
        Intrinsics.checkNotNullExpressionValue(it, "it");
        baseItemProvider.onClick(viewHolder, it, this$0.getData().get(headerLayoutCount), headerLayoutCount);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: bindClick$lambda-3, reason: not valid java name */
    public static final boolean m69bindClick$lambda3(BaseViewHolder viewHolder, BaseProviderMultiAdapter this$0, View it) {
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
        if (bindingAdapterPosition == -1) {
            return false;
        }
        int headerLayoutCount = bindingAdapterPosition - this$0.getHeaderLayoutCount();
        BaseItemProvider<T> baseItemProvider = this$0.getMItemProviders().get(viewHolder.getItemViewType());
        Intrinsics.checkNotNullExpressionValue(it, "it");
        return baseItemProvider.onLongClick(viewHolder, it, this$0.getData().get(headerLayoutCount), headerLayoutCount);
    }

    protected void bindChildClick(final BaseViewHolder viewHolder, int viewType) {
        final BaseItemProvider<T> itemProvider;
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        if (getMOnItemChildClickListener() == null) {
            final BaseItemProvider<T> itemProvider2 = getItemProvider(viewType);
            if (itemProvider2 == null) {
                return;
            }
            Iterator<T> it = itemProvider2.getChildClickViewIds().iterator();
            while (it.hasNext()) {
                View viewFindViewById = viewHolder.itemView.findViewById(((Number) it.next()).intValue());
                if (viewFindViewById != null) {
                    Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById<View>(id)");
                    if (!viewFindViewById.isClickable()) {
                        viewFindViewById.setClickable(true);
                    }
                    viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.chad.library.adapter.base.BaseProviderMultiAdapter$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            BaseProviderMultiAdapter.m66bindChildClick$lambda6$lambda5$lambda4(viewHolder, this, itemProvider2, view);
                        }
                    });
                }
            }
        }
        if (getMOnItemChildLongClickListener() != null || (itemProvider = getItemProvider(viewType)) == null) {
            return;
        }
        Iterator<T> it2 = itemProvider.getChildLongClickViewIds().iterator();
        while (it2.hasNext()) {
            View viewFindViewById2 = viewHolder.itemView.findViewById(((Number) it2.next()).intValue());
            if (viewFindViewById2 != null) {
                Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById<View>(id)");
                if (!viewFindViewById2.isLongClickable()) {
                    viewFindViewById2.setLongClickable(true);
                }
                viewFindViewById2.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.chad.library.adapter.base.BaseProviderMultiAdapter$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        return BaseProviderMultiAdapter.m67bindChildClick$lambda9$lambda8$lambda7(viewHolder, this, itemProvider, view);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: bindChildClick$lambda-6$lambda-5$lambda-4, reason: not valid java name */
    public static final void m66bindChildClick$lambda6$lambda5$lambda4(BaseViewHolder viewHolder, BaseProviderMultiAdapter this$0, BaseItemProvider provider, View v) {
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(provider, "$provider");
        int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
        if (bindingAdapterPosition == -1) {
            return;
        }
        int headerLayoutCount = bindingAdapterPosition - this$0.getHeaderLayoutCount();
        Intrinsics.checkNotNullExpressionValue(v, "v");
        provider.onChildClick(viewHolder, v, this$0.getData().get(headerLayoutCount), headerLayoutCount);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: bindChildClick$lambda-9$lambda-8$lambda-7, reason: not valid java name */
    public static final boolean m67bindChildClick$lambda9$lambda8$lambda7(BaseViewHolder viewHolder, BaseProviderMultiAdapter this$0, BaseItemProvider provider, View v) {
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(provider, "$provider");
        int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
        if (bindingAdapterPosition == -1) {
            return false;
        }
        int headerLayoutCount = bindingAdapterPosition - this$0.getHeaderLayoutCount();
        Intrinsics.checkNotNullExpressionValue(v, "v");
        return provider.onChildLongClick(viewHolder, v, this$0.getData().get(headerLayoutCount), headerLayoutCount);
    }
}
