package com.lazyee.klib.debug;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.lazyee.klib.common.SP;
import com.lazyee.klib.databinding.ItemDebugConfigBinding;
import com.lazyee.klib.databinding.WidgetDebugViewBinding;
import com.lazyee.klib.debug.DebugContainer;
import com.lazyee.klib.extension.ViewExtensionsKt;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DebugContainer.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000i\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0005*\u0001\u0016\u0018\u00002\u00020\u0001:\u0003123B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0006\u0010'\u001a\u00020(J\u000e\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u0019J\u000e\u0010+\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\tJ\u000e\u0010-\u001a\u00020\u00002\u0006\u0010.\u001a\u00020/J\u0006\u00100\u001a\u00020(R\u0012\u0010\u000b\u001a\u00060\fR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R)\u0010\r\u001a\u0010\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u000f0\u000f0\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u0010\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0017R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u001d\u001a\u00020\u001e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\u0014\u001a\u0004\b\u001f\u0010 R\u001b\u0010\"\u001a\u00020#8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b&\u0010\u0014\u001a\u0004\b$\u0010%¨\u00064"}, d2 = {"Lcom/lazyee/klib/debug/DebugContainer;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "mAdapter", "Lcom/lazyee/klib/debug/DebugContainer$DebugConfigAdapter;", "mBottomSheetBehavior", "Lcom/google/android/material/bottomsheet/BottomSheetBehavior;", "Landroid/widget/LinearLayout;", "kotlin.jvm.PlatformType", "getMBottomSheetBehavior", "()Lcom/google/android/material/bottomsheet/BottomSheetBehavior;", "mBottomSheetBehavior$delegate", "Lkotlin/Lazy;", "mBottomSheetCallback", "com/lazyee/klib/debug/DebugContainer$mBottomSheetCallback$1", "Lcom/lazyee/klib/debug/DebugContainer$mBottomSheetCallback$1;", "mDebugViewCallback", "Lcom/lazyee/klib/debug/DebugContainer$Callback;", "mItemViewResId", "mSelectedDebugConfig", "Lcom/lazyee/klib/debug/DebugConfig;", "mViewBinding", "Lcom/lazyee/klib/databinding/WidgetDebugViewBinding;", "getMViewBinding", "()Lcom/lazyee/klib/databinding/WidgetDebugViewBinding;", "mViewBinding$delegate", "sp", "Lcom/lazyee/klib/common/SP;", "getSp", "()Lcom/lazyee/klib/common/SP;", "sp$delegate", "hide", "", "setDebugViewCallback", "callback", "setItemViewResId", "resId", "setTitle", "title", "", "show", "Callback", "DebugConfigAdapter", "DebugConfigViewHolder", "library_release"}, k = 1, mv = {1, 4, 2})
public final class DebugContainer extends FrameLayout {
    private final DebugConfigAdapter mAdapter;

    /* JADX INFO: renamed from: mBottomSheetBehavior$delegate, reason: from kotlin metadata */
    private final Lazy mBottomSheetBehavior;
    private final DebugContainer$mBottomSheetCallback$1 mBottomSheetCallback;
    private Callback mDebugViewCallback;
    private int mItemViewResId;
    private DebugConfig mSelectedDebugConfig;

    /* JADX INFO: renamed from: mViewBinding$delegate, reason: from kotlin metadata */
    private final Lazy mViewBinding;

    /* JADX INFO: renamed from: sp$delegate, reason: from kotlin metadata */
    private final Lazy sp;

    /* JADX INFO: compiled from: DebugContainer.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0007H&J\u000e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000bH&J\b\u0010\f\u001a\u00020\rH&¨\u0006\u000e"}, d2 = {"Lcom/lazyee/klib/debug/DebugContainer$Callback;", "", "onBindItemView", "", "itemView", "Landroid/view/View;", "item", "Lcom/lazyee/klib/debug/DebugConfig;", "onSelectedDebugConfig", "selectedDebugConfig", "provideDebugConfig", "", "provideDebugConfigKey", "", "library_release"}, k = 1, mv = {1, 4, 2})
    public interface Callback {
        void onBindItemView(View itemView, DebugConfig item);

        void onSelectedDebugConfig(DebugConfig selectedDebugConfig);

        List<DebugConfig> provideDebugConfig();

        String provideDebugConfigKey();
    }

    private final BottomSheetBehavior<LinearLayout> getMBottomSheetBehavior() {
        return (BottomSheetBehavior) this.mBottomSheetBehavior.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final WidgetDebugViewBinding getMViewBinding() {
        return (WidgetDebugViewBinding) this.mViewBinding.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SP getSp() {
        return (SP) this.sp.getValue();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DebugContainer(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DebugContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v12, types: [com.lazyee.klib.debug.DebugContainer$mBottomSheetCallback$1] */
    public DebugContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.sp = LazyKt.lazy(new Function0<SP>() { // from class: com.lazyee.klib.debug.DebugContainer$sp$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final SP invoke() {
                Context context2 = this.this$0.getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "context");
                return new SP(context2, "__config__", 0);
            }
        });
        this.mViewBinding = LazyKt.lazy(new Function0<WidgetDebugViewBinding>() { // from class: com.lazyee.klib.debug.DebugContainer$mViewBinding$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final WidgetDebugViewBinding invoke() {
                return WidgetDebugViewBinding.inflate(LayoutInflater.from(this.this$0.getContext()), this.this$0, true);
            }
        });
        this.mBottomSheetBehavior = LazyKt.lazy(new Function0<BottomSheetBehavior<LinearLayout>>() { // from class: com.lazyee.klib.debug.DebugContainer$mBottomSheetBehavior$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final BottomSheetBehavior<LinearLayout> invoke() {
                return BottomSheetBehavior.from(this.this$0.getMViewBinding().llDebugContent);
            }
        });
        this.mAdapter = new DebugConfigAdapter();
        this.mItemViewResId = -1;
        ?? r2 = new BottomSheetBehavior.BottomSheetCallback() { // from class: com.lazyee.klib.debug.DebugContainer$mBottomSheetCallback$1
            @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
            public void onSlide(View bottomSheet, float slideOffset) {
                Intrinsics.checkNotNullParameter(bottomSheet, "bottomSheet");
            }

            @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
            public void onStateChanged(View bottomSheet, int newState) {
                Intrinsics.checkNotNullParameter(bottomSheet, "bottomSheet");
                if (this.this$0.mDebugViewCallback == null || this.this$0.mSelectedDebugConfig == null || newState != 5) {
                    return;
                }
                SP sp = this.this$0.getSp();
                DebugContainer.Callback callback = this.this$0.mDebugViewCallback;
                Intrinsics.checkNotNull(callback);
                String strProvideDebugConfigKey = callback.provideDebugConfigKey();
                DebugConfig debugConfig = this.this$0.mSelectedDebugConfig;
                Intrinsics.checkNotNull(debugConfig);
                sp.put(strProvideDebugConfigKey, debugConfig.getKey());
                DebugContainer.Callback callback2 = this.this$0.mDebugViewCallback;
                Intrinsics.checkNotNull(callback2);
                DebugConfig debugConfig2 = this.this$0.mSelectedDebugConfig;
                Intrinsics.checkNotNull(debugConfig2);
                callback2.onSelectedDebugConfig(debugConfig2);
            }
        };
        this.mBottomSheetCallback = r2;
        WidgetDebugViewBinding mViewBinding = getMViewBinding();
        mViewBinding.tvClose.setOnClickListener(new View.OnClickListener() { // from class: com.lazyee.klib.debug.DebugContainer$$special$$inlined$run$lambda$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.this$0.hide();
            }
        });
        mViewBinding.coordinatorLayout.bringToFront();
        hide();
        getMBottomSheetBehavior().addBottomSheetCallback((BottomSheetBehavior.BottomSheetCallback) r2);
    }

    public final DebugContainer setItemViewResId(int resId) {
        this.mItemViewResId = resId;
        return this;
    }

    public final DebugContainer setDebugViewCallback(Callback callback) {
        Object next;
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mDebugViewCallback = callback;
        Iterator<T> it = callback.provideDebugConfig().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            String key = ((DebugConfig) next).getKey();
            SP sp = getSp();
            Callback callback2 = this.mDebugViewCallback;
            Intrinsics.checkNotNull(callback2);
            if (Intrinsics.areEqual(key, sp.string(callback2.provideDebugConfigKey()))) {
                break;
            }
        }
        DebugConfig debugConfig = (DebugConfig) next;
        this.mSelectedDebugConfig = debugConfig;
        if (debugConfig != null) {
            Callback callback3 = this.mDebugViewCallback;
            Intrinsics.checkNotNull(callback3);
            callback3.onSelectedDebugConfig(debugConfig);
        }
        RecyclerView recyclerView = getMViewBinding().recyclerView;
        Intrinsics.checkNotNullExpressionValue(recyclerView, "mViewBinding.recyclerView");
        recyclerView.setAdapter(this.mAdapter);
        return this;
    }

    public final DebugContainer setTitle(String title) {
        Intrinsics.checkNotNullParameter(title, "title");
        TextView textView = getMViewBinding().tvTitle;
        Intrinsics.checkNotNullExpressionValue(textView, "mViewBinding.tvTitle");
        textView.setText(title);
        return this;
    }

    public final void show() {
        BottomSheetBehavior<LinearLayout> mBottomSheetBehavior = getMBottomSheetBehavior();
        Intrinsics.checkNotNullExpressionValue(mBottomSheetBehavior, "mBottomSheetBehavior");
        mBottomSheetBehavior.setState(3);
    }

    public final void hide() {
        BottomSheetBehavior<LinearLayout> mBottomSheetBehavior = getMBottomSheetBehavior();
        Intrinsics.checkNotNullExpressionValue(mBottomSheetBehavior, "mBottomSheetBehavior");
        mBottomSheetBehavior.setState(5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: DebugContainer.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u001c\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u00060\u0002R\u00020\u00032\u0006\u0010\n\u001a\u00020\u0006H\u0016J\u001c\u0010\u000b\u001a\u00060\u0002R\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006H\u0016¨\u0006\u000f"}, d2 = {"Lcom/lazyee/klib/debug/DebugContainer$DebugConfigAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/lazyee/klib/debug/DebugContainer$DebugConfigViewHolder;", "Lcom/lazyee/klib/debug/DebugContainer;", "(Lcom/lazyee/klib/debug/DebugContainer;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "library_release"}, k = 1, mv = {1, 4, 2})
    final class DebugConfigAdapter extends RecyclerView.Adapter<DebugConfigViewHolder> {
        public DebugConfigAdapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public DebugConfigViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            ItemDebugConfigBinding itemDebugConfigBindingInflate = ItemDebugConfigBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            Intrinsics.checkNotNullExpressionValue(itemDebugConfigBindingInflate, "ItemDebugConfigBinding.i….context), parent, false)");
            itemDebugConfigBindingInflate.flContent.addView(LayoutInflater.from(parent.getContext()).inflate(DebugContainer.this.mItemViewResId, (ViewGroup) null));
            return new DebugConfigViewHolder(DebugContainer.this, itemDebugConfigBindingInflate);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(DebugConfigViewHolder holder, int position) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Callback callback = DebugContainer.this.mDebugViewCallback;
            Intrinsics.checkNotNull(callback);
            holder.bindData(callback.provideDebugConfig().get(position));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            Callback callback = DebugContainer.this.mDebugViewCallback;
            Intrinsics.checkNotNull(callback);
            return callback.provideDebugConfig().size();
        }
    }

    /* JADX INFO: compiled from: DebugContainer.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lcom/lazyee/klib/debug/DebugContainer$DebugConfigViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "mViewBinding", "Lcom/lazyee/klib/databinding/ItemDebugConfigBinding;", "(Lcom/lazyee/klib/debug/DebugContainer;Lcom/lazyee/klib/databinding/ItemDebugConfigBinding;)V", "getMViewBinding", "()Lcom/lazyee/klib/databinding/ItemDebugConfigBinding;", "bindData", "", "config", "Lcom/lazyee/klib/debug/DebugConfig;", "library_release"}, k = 1, mv = {1, 4, 2})
    private final class DebugConfigViewHolder extends RecyclerView.ViewHolder {
        private final ItemDebugConfigBinding mViewBinding;
        final /* synthetic */ DebugContainer this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DebugConfigViewHolder(DebugContainer debugContainer, ItemDebugConfigBinding mViewBinding) {
            super(mViewBinding.getRoot());
            Intrinsics.checkNotNullParameter(mViewBinding, "mViewBinding");
            this.this$0 = debugContainer;
            this.mViewBinding = mViewBinding;
        }

        public final ItemDebugConfigBinding getMViewBinding() {
            return this.mViewBinding;
        }

        public final void bindData(final DebugConfig config) {
            Intrinsics.checkNotNullParameter(config, "config");
            ItemDebugConfigBinding itemDebugConfigBinding = this.mViewBinding;
            itemDebugConfigBinding.cardView.setOnClickListener(new View.OnClickListener() { // from class: com.lazyee.klib.debug.DebugContainer$DebugConfigViewHolder$bindData$$inlined$run$lambda$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.this$0.this$0.mSelectedDebugConfig = config;
                    this.this$0.this$0.mAdapter.notifyDataSetChanged();
                    this.this$0.this$0.hide();
                }
            });
            Callback callback = this.this$0.mDebugViewCallback;
            if (callback != null) {
                CardView root = itemDebugConfigBinding.getRoot();
                Intrinsics.checkNotNullExpressionValue(root, "root");
                callback.onBindItemView(root, config);
            }
            if (Intrinsics.areEqual(config, this.this$0.mSelectedDebugConfig)) {
                ImageView ivSelected = itemDebugConfigBinding.ivSelected;
                Intrinsics.checkNotNullExpressionValue(ivSelected, "ivSelected");
                ViewExtensionsKt.visible(ivSelected);
            } else {
                ImageView ivSelected2 = itemDebugConfigBinding.ivSelected;
                Intrinsics.checkNotNullExpressionValue(ivSelected2, "ivSelected");
                ViewExtensionsKt.gone(ivSelected2);
            }
        }
    }
}
