package com.lazyee.klib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.exifinterface.media.ExifInterface;
import com.lazyee.klib.R;
import com.lazyee.klib.extension.ViewExtensionsKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PageStateSwitcher.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u000f\b\u0016\u0018\u0000 ,2\u00020\u0001:\u0002,-B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u001e\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00130\u0012\"\b\b\u0000\u0010\u0013*\u00020\f2\u0006\u0010\u0014\u001a\u00020\tJ\b\u0010\u0015\u001a\u0004\u0018\u00010\fJ\b\u0010\u0016\u001a\u0004\u0018\u00010\fJ\b\u0010\u0017\u001a\u0004\u0018\u00010\fJ\b\u0010\u0018\u001a\u0004\u0018\u00010\fJ\b\u0010\u0019\u001a\u0004\u0018\u00010\fJ \u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00130\u0012\"\b\b\u0000\u0010\u0013*\u00020\f2\u0006\u0010\u0014\u001a\u00020\tH\u0007J\u0019\u0010\u001b\u001a\u0004\u0018\u00010\f2\b\u0010\u001c\u001a\u0004\u0018\u00010\tH\u0002¢\u0006\u0002\u0010\u001dJ\b\u0010\u001e\u001a\u00020\u001fH\u0014J\u0015\u0010 \u001a\u00020\u001f2\b\u0010!\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\"J\u0015\u0010#\u001a\u00020\u001f2\b\u0010!\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\"J\u0015\u0010$\u001a\u00020\u001f2\b\u0010!\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\"J\u0015\u0010%\u001a\u00020\u001f2\b\u0010!\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\"J\u0015\u0010&\u001a\u00020\u001f2\b\u0010!\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\"J\u0006\u0010'\u001a\u00020\u001fJ\u0006\u0010(\u001a\u00020\u001fJ\u0006\u0010)\u001a\u00020\u001fJ\u0006\u0010*\u001a\u00020\u001fJ\u0006\u0010+\u001a\u00020\u001fR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lcom/lazyee/klib/widget/PageStateSwitcher;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "contentView", "Landroid/view/View;", "emptyView", "exceptionView", "loadingView", "networkErrorView", "findTargetViewsById", "", ExifInterface.GPS_DIRECTION_TRUE, "viewId", "getContentView", "getEmptyView", "getExceptionView", "getLoadingView", "getNetworkErrorView", "getTargetViews", "inflateLayoutById", "resId", "(Ljava/lang/Integer;)Landroid/view/View;", "onFinishInflate", "", "setContentViewLayoutId", "layoutId", "(Ljava/lang/Integer;)V", "setEmptyViewLayoutId", "setExceptionViewLayoutId", "setLoadingViewLayoutId", "setNetworkErrorViewLayoutId", "showContentView", "showEmptyView", "showExceptionView", "showLoadingView", "showNetworkErrorView", "Companion", "LayoutProvider", "library_release"}, k = 1, mv = {1, 4, 2})
public class PageStateSwitcher extends FrameLayout {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static LayoutProvider provider;
    private View contentView;
    private View emptyView;
    private View exceptionView;
    private View loadingView;
    private View networkErrorView;

    /* JADX INFO: compiled from: PageStateSwitcher.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u000f\u0010\u0002\u001a\u0004\u0018\u00010\u0003H&¢\u0006\u0002\u0010\u0004J\u000f\u0010\u0005\u001a\u0004\u0018\u00010\u0003H&¢\u0006\u0002\u0010\u0004J\u000f\u0010\u0006\u001a\u0004\u0018\u00010\u0003H&¢\u0006\u0002\u0010\u0004J\u000f\u0010\u0007\u001a\u0004\u0018\u00010\u0003H&¢\u0006\u0002\u0010\u0004¨\u0006\b"}, d2 = {"Lcom/lazyee/klib/widget/PageStateSwitcher$LayoutProvider;", "", "getEmptyViewLayoutId", "", "()Ljava/lang/Integer;", "getExceptionViewLayoutId", "getLoadingViewLayoutId", "getNetworkErrorViewLayoutId", "library_release"}, k = 1, mv = {1, 4, 2})
    public interface LayoutProvider {
        Integer getEmptyViewLayoutId();

        Integer getExceptionViewLayoutId();

        Integer getLoadingViewLayoutId();

        Integer getNetworkErrorViewLayoutId();
    }

    /* JADX INFO: compiled from: PageStateSwitcher.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u000b"}, d2 = {"Lcom/lazyee/klib/widget/PageStateSwitcher$Companion;", "", "()V", "provider", "Lcom/lazyee/klib/widget/PageStateSwitcher$LayoutProvider;", "getProvider", "()Lcom/lazyee/klib/widget/PageStateSwitcher$LayoutProvider;", "setProvider", "(Lcom/lazyee/klib/widget/PageStateSwitcher$LayoutProvider;)V", "config", "", "library_release"}, k = 1, mv = {1, 4, 2})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final LayoutProvider getProvider() {
            return PageStateSwitcher.provider;
        }

        public final void setProvider(LayoutProvider layoutProvider) {
            PageStateSwitcher.provider = layoutProvider;
        }

        public final void config(LayoutProvider provider) {
            setProvider(provider);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PageStateSwitcher(Context context) {
        super(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PageStateSwitcher(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PageStateSwitcher(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.contentView = findViewById(R.id.contentView);
        this.networkErrorView = findViewById(R.id.networkErrorView);
        this.loadingView = findViewById(R.id.loadingView);
        this.emptyView = findViewById(R.id.emptyView);
        this.exceptionView = findViewById(R.id.exceptionView);
        if (this.loadingView == null) {
            LayoutProvider layoutProvider = provider;
            this.loadingView = inflateLayoutById(layoutProvider != null ? layoutProvider.getLoadingViewLayoutId() : null);
        }
        if (this.networkErrorView == null) {
            LayoutProvider layoutProvider2 = provider;
            this.networkErrorView = inflateLayoutById(layoutProvider2 != null ? layoutProvider2.getNetworkErrorViewLayoutId() : null);
        }
        if (this.exceptionView == null) {
            LayoutProvider layoutProvider3 = provider;
            this.exceptionView = inflateLayoutById(layoutProvider3 != null ? layoutProvider3.getExceptionViewLayoutId() : null);
        }
        if (this.emptyView == null) {
            LayoutProvider layoutProvider4 = provider;
            this.emptyView = inflateLayoutById(layoutProvider4 != null ? layoutProvider4.getEmptyViewLayoutId() : null);
        }
        showContentView();
    }

    public final void setContentViewLayoutId(Integer layoutId) {
        View view = this.contentView;
        if (view != null) {
            removeView(view);
        }
        this.contentView = inflateLayoutById(layoutId);
    }

    public final void setNetworkErrorViewLayoutId(Integer layoutId) {
        View view = this.networkErrorView;
        if (view != null) {
            removeView(view);
        }
        this.networkErrorView = inflateLayoutById(layoutId);
    }

    public final void setExceptionViewLayoutId(Integer layoutId) {
        View view = this.exceptionView;
        if (view != null) {
            removeView(view);
        }
        this.exceptionView = inflateLayoutById(layoutId);
    }

    public final void setEmptyViewLayoutId(Integer layoutId) {
        View view = this.emptyView;
        if (view != null) {
            removeView(view);
        }
        this.emptyView = inflateLayoutById(layoutId);
    }

    public final void setLoadingViewLayoutId(Integer layoutId) {
        View view = this.loadingView;
        if (view != null) {
            removeView(view);
        }
        this.loadingView = inflateLayoutById(layoutId);
    }

    @Deprecated(message = "该方法已经废弃了，请使用findTargetViewsById方法", replaceWith = @ReplaceWith(expression = "findTargetViewsById(viewId)", imports = {}))
    public final <T extends View> List<T> getTargetViews(int viewId) {
        return findTargetViewsById(viewId);
    }

    public final <T extends View> List<T> findTargetViewsById(int viewId) {
        View viewFindViewById;
        View viewFindViewById2;
        View viewFindViewById3;
        View viewFindViewById4;
        View viewFindViewById5;
        ArrayList arrayList = new ArrayList();
        View view = this.loadingView;
        if (view != null && (viewFindViewById5 = view.findViewById(viewId)) != null) {
            arrayList.add(viewFindViewById5);
        }
        View view2 = this.contentView;
        if (view2 != null && (viewFindViewById4 = view2.findViewById(viewId)) != null) {
            arrayList.add(viewFindViewById4);
        }
        View view3 = this.exceptionView;
        if (view3 != null && (viewFindViewById3 = view3.findViewById(viewId)) != null) {
            arrayList.add(viewFindViewById3);
        }
        View view4 = this.networkErrorView;
        if (view4 != null && (viewFindViewById2 = view4.findViewById(viewId)) != null) {
            arrayList.add(viewFindViewById2);
        }
        View view5 = this.emptyView;
        if (view5 != null && (viewFindViewById = view5.findViewById(viewId)) != null) {
            arrayList.add(viewFindViewById);
        }
        return arrayList;
    }

    private final View inflateLayoutById(Integer resId) {
        if (resId == null) {
            return null;
        }
        resId.intValue();
        if (resId.intValue() == 0) {
            return null;
        }
        View viewInflate = LayoutInflater.from(getContext()).inflate(resId.intValue(), (ViewGroup) this, false);
        addView(viewInflate);
        return viewInflate;
    }

    public final View getContentView() {
        return this.contentView;
    }

    public final View getNetworkErrorView() {
        return this.networkErrorView;
    }

    public final View getLoadingView() {
        return this.loadingView;
    }

    public final View getExceptionView() {
        return this.exceptionView;
    }

    public final View getEmptyView() {
        return this.emptyView;
    }

    public final void showExceptionView() {
        View view = this.contentView;
        if (view != null) {
            ViewExtensionsKt.gone(view);
        }
        View view2 = this.networkErrorView;
        if (view2 != null) {
            ViewExtensionsKt.gone(view2);
        }
        View view3 = this.loadingView;
        if (view3 != null) {
            ViewExtensionsKt.gone(view3);
        }
        View view4 = this.emptyView;
        if (view4 != null) {
            ViewExtensionsKt.gone(view4);
        }
        View view5 = this.exceptionView;
        if (view5 != null) {
            ViewExtensionsKt.visible(view5);
        }
    }

    public final void showContentView() {
        View view = this.contentView;
        if (view != null) {
            ViewExtensionsKt.visible(view);
        }
        View view2 = this.networkErrorView;
        if (view2 != null) {
            ViewExtensionsKt.gone(view2);
        }
        View view3 = this.loadingView;
        if (view3 != null) {
            ViewExtensionsKt.gone(view3);
        }
        View view4 = this.emptyView;
        if (view4 != null) {
            ViewExtensionsKt.gone(view4);
        }
        View view5 = this.exceptionView;
        if (view5 != null) {
            ViewExtensionsKt.gone(view5);
        }
    }

    public final void showNetworkErrorView() {
        View view = this.contentView;
        if (view != null) {
            ViewExtensionsKt.gone(view);
        }
        View view2 = this.networkErrorView;
        if (view2 != null) {
            ViewExtensionsKt.visible(view2);
        }
        View view3 = this.loadingView;
        if (view3 != null) {
            ViewExtensionsKt.gone(view3);
        }
        View view4 = this.emptyView;
        if (view4 != null) {
            ViewExtensionsKt.gone(view4);
        }
        View view5 = this.exceptionView;
        if (view5 != null) {
            ViewExtensionsKt.gone(view5);
        }
    }

    public final void showLoadingView() {
        View view = this.contentView;
        if (view != null) {
            ViewExtensionsKt.gone(view);
        }
        View view2 = this.networkErrorView;
        if (view2 != null) {
            ViewExtensionsKt.gone(view2);
        }
        View view3 = this.loadingView;
        if (view3 != null) {
            ViewExtensionsKt.visible(view3);
        }
        View view4 = this.emptyView;
        if (view4 != null) {
            ViewExtensionsKt.gone(view4);
        }
        View view5 = this.exceptionView;
        if (view5 != null) {
            ViewExtensionsKt.gone(view5);
        }
    }

    public final void showEmptyView() {
        View view = this.contentView;
        if (view != null) {
            ViewExtensionsKt.gone(view);
        }
        View view2 = this.networkErrorView;
        if (view2 != null) {
            ViewExtensionsKt.gone(view2);
        }
        View view3 = this.loadingView;
        if (view3 != null) {
            ViewExtensionsKt.gone(view3);
        }
        View view4 = this.emptyView;
        if (view4 != null) {
            ViewExtensionsKt.visible(view4);
        }
        View view5 = this.exceptionView;
        if (view5 != null) {
            ViewExtensionsKt.gone(view5);
        }
    }
}
