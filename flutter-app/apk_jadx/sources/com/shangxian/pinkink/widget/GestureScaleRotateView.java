package com.shangxian.pinkink.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.lazyee.klib.extension.NumberExtensionsKt;
import com.lazyee.klib.extension.ViewExtensionsKt;
import com.shangxian.pinkink.R;
import java.util.Objects;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GestureScaleRotateView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0089\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u0014\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0010*\u00017\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u00032\u00020\u0004:\u0001^B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u0019\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nB!\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0013\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00028\u0000¢\u0006\u0002\u0010>J(\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020\u000f2\u0006\u0010B\u001a\u00020\u000f2\u0006\u0010C\u001a\u00020\u000f2\u0006\u0010D\u001a\u00020\u000fH\u0002J\u0018\u0010E\u001a\u00020\u000f2\u0006\u0010A\u001a\u00020\u000f2\u0006\u0010B\u001a\u00020\u000fH\u0002J\b\u0010F\u001a\u00020GH\u0002J\r\u0010H\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010IJ\u0006\u0010J\u001a\u00020<J\b\u0010K\u001a\u00020<H\u0003J\u001c\u0010L\u001a\u00020\"2\b\u0010M\u001a\u0004\u0018\u00010\u00022\b\u0010N\u001a\u0004\u0018\u00010OH\u0016J\u0018\u0010P\u001a\u00020<2\u0006\u0010Q\u001a\u00020\u000f2\u0006\u0010R\u001a\u00020\u000fH\u0002J\u0018\u0010P\u001a\u00020<2\u0006\u0010Q\u001a\u00020\f2\u0006\u0010R\u001a\u00020\fH\u0002J\u000e\u0010S\u001a\u00020<2\u0006\u0010T\u001a\u00020:J\u000e\u0010U\u001a\u00020<2\u0006\u0010V\u001a\u00020\"J\u000e\u0010W\u001a\u00020<2\u0006\u0010V\u001a\u00020\"J\u0006\u0010X\u001a\u00020<J\u0016\u0010Y\u001a\u00020<2\u0006\u0010Q\u001a\u00020\u000f2\u0006\u0010R\u001a\u00020\u000fJ\b\u0010Z\u001a\u00020<H\u0002J\u0018\u0010[\u001a\u00020<2\u0006\u0010A\u001a\u00020\u000f2\u0006\u0010B\u001a\u00020\u000fH\u0002J\u0018\u0010\\\u001a\u00020<2\u0006\u0010A\u001a\u00020\u000f2\u0006\u0010B\u001a\u00020\u000fH\u0002J\u0018\u0010]\u001a\u00020<2\u0006\u0010A\u001a\u00020\u000f2\u0006\u0010B\u001a\u00020\u000fH\u0002R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R#\u0010\u0011\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0018\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0019\u001a\u0004\u0018\u00018\u0000X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001c\u001a\u00020\u001d8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b \u0010\u0017\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010!\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010%\u001a\u00020&8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b)\u0010\u0017\u001a\u0004\b'\u0010(R\u001b\u0010*\u001a\u00020&8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b,\u0010\u0017\u001a\u0004\b+\u0010(R\u000e\u0010-\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u000203X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u00106\u001a\b\u0012\u0004\u0012\u00028\u000007X\u0082\u0004¢\u0006\u0004\n\u0002\u00108R\u0010\u00109\u001a\u0004\u0018\u00010:X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006_"}, d2 = {"Lcom/shangxian/pinkink/widget/GestureScaleRotateView;", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/view/View;", "Landroid/widget/RelativeLayout;", "Landroid/view/View$OnTouchListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "calculateHeight", "", "calculateWidth", "contentLayoutParams", "Landroid/view/ViewGroup$LayoutParams;", "kotlin.jvm.PlatformType", "getContentLayoutParams", "()Landroid/view/ViewGroup$LayoutParams;", "contentLayoutParams$delegate", "Lkotlin/Lazy;", "contentPadding", "contentView", "Landroid/view/View;", "dp20", "flContent", "Landroid/widget/FrameLayout;", "getFlContent", "()Landroid/widget/FrameLayout;", "flContent$delegate", "isDragTouchDown", "", "isRotateEnable", "isScaleEnable", "ivEditDelete", "Landroid/widget/ImageView;", "getIvEditDelete", "()Landroid/widget/ImageView;", "ivEditDelete$delegate", "ivEditDrag", "getIvEditDrag", "ivEditDrag$delegate", "lastDragAngle", "lastDragTouchX", "lastDragTouchY", "lastTouchDownX", "lastTouchDownY", "lastTouchTime", "", "lastTouchX", "lastTouchY", "onDragImageViewTouchListener", "com/shangxian/pinkink/widget/GestureScaleRotateView$onDragImageViewTouchListener$1", "Lcom/shangxian/pinkink/widget/GestureScaleRotateView$onDragImageViewTouchListener$1;", "onOperateListener", "Lcom/shangxian/pinkink/widget/GestureScaleRotateView$OnOperateListener;", "addContentView", "", "view", "(Landroid/view/View;)V", "calculateDistance", "", "x", "y", "centerX", "centerY", "getAngle", "getCenterXY", "", "getContentView", "()Landroid/view/View;", "hideOperateView", "initView", "onTouch", "v", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "realUpdateContentSize", "width", "height", "setOnOperateListener", "listener", "setRotateEnable", "enable", "setScaleEnable", "showOperateView", "updateContentSize", "updateDragBtnVisible", "updatePosition", "updateRotateAngle", "updateScale", "OnOperateListener", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class GestureScaleRotateView<T extends View> extends RelativeLayout implements View.OnTouchListener {
    private float calculateHeight;
    private float calculateWidth;

    /* JADX INFO: renamed from: contentLayoutParams$delegate, reason: from kotlin metadata */
    private final Lazy contentLayoutParams;
    private final int contentPadding;
    private T contentView;
    private final int dp20;

    /* JADX INFO: renamed from: flContent$delegate, reason: from kotlin metadata */
    private final Lazy flContent;
    private boolean isDragTouchDown;
    private boolean isRotateEnable;
    private boolean isScaleEnable;

    /* JADX INFO: renamed from: ivEditDelete$delegate, reason: from kotlin metadata */
    private final Lazy ivEditDelete;

    /* JADX INFO: renamed from: ivEditDrag$delegate, reason: from kotlin metadata */
    private final Lazy ivEditDrag;
    private float lastDragAngle;
    private float lastDragTouchX;
    private float lastDragTouchY;
    private float lastTouchDownX;
    private float lastTouchDownY;
    private long lastTouchTime;
    private float lastTouchX;
    private float lastTouchY;
    private final GestureScaleRotateView$onDragImageViewTouchListener$1 onDragImageViewTouchListener;
    private OnOperateListener onOperateListener;

    /* JADX INFO: compiled from: GestureScaleRotateView.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u00020\u00032\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005H&J\u0014\u0010\u0006\u001a\u00020\u00032\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005H&¨\u0006\u0007"}, d2 = {"Lcom/shangxian/pinkink/widget/GestureScaleRotateView$OnOperateListener;", "", "onContentClick", "", "view", "Lcom/shangxian/pinkink/widget/GestureScaleRotateView;", "onDeleteClick", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public interface OnOperateListener {
        void onContentClick(GestureScaleRotateView<?> view);

        void onDeleteClick(GestureScaleRotateView<?> view);
    }

    private final ImageView getIvEditDelete() {
        Object value = this.ivEditDelete.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-ivEditDelete>(...)");
        return (ImageView) value;
    }

    private final ImageView getIvEditDrag() {
        Object value = this.ivEditDrag.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-ivEditDrag>(...)");
        return (ImageView) value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FrameLayout getFlContent() {
        Object value = this.flContent.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-flContent>(...)");
        return (FrameLayout) value;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public GestureScaleRotateView(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public GestureScaleRotateView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v19, types: [com.shangxian.pinkink.widget.GestureScaleRotateView$onDragImageViewTouchListener$1] */
    public GestureScaleRotateView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.ivEditDelete = LazyKt.lazy(new Function0<ImageView>(this) { // from class: com.shangxian.pinkink.widget.GestureScaleRotateView$ivEditDelete$2
            final /* synthetic */ GestureScaleRotateView<T> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ImageView invoke() {
                return (ImageView) this.this$0.findViewById(R.id.ivEditDelete);
            }
        });
        this.ivEditDrag = LazyKt.lazy(new Function0<ImageView>(this) { // from class: com.shangxian.pinkink.widget.GestureScaleRotateView$ivEditDrag$2
            final /* synthetic */ GestureScaleRotateView<T> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ImageView invoke() {
                return (ImageView) this.this$0.findViewById(R.id.ivEditDrag);
            }
        });
        this.flContent = LazyKt.lazy(new Function0<FrameLayout>(this) { // from class: com.shangxian.pinkink.widget.GestureScaleRotateView$flContent$2
            final /* synthetic */ GestureScaleRotateView<T> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final FrameLayout invoke() {
                return (FrameLayout) this.this$0.findViewById(R.id.flContent);
            }
        });
        this.calculateWidth = -1.0f;
        this.calculateHeight = -1.0f;
        this.isScaleEnable = true;
        this.isRotateEnable = true;
        this.dp20 = NumberExtensionsKt.dp2px(20);
        this.contentPadding = NumberExtensionsKt.dp2px(2);
        this.contentLayoutParams = LazyKt.lazy(new Function0<ViewGroup.LayoutParams>(this) { // from class: com.shangxian.pinkink.widget.GestureScaleRotateView$contentLayoutParams$2
            final /* synthetic */ GestureScaleRotateView<T> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewGroup.LayoutParams invoke() {
                return this.this$0.getFlContent().getLayoutParams();
            }
        });
        this.onDragImageViewTouchListener = new View.OnTouchListener(this) { // from class: com.shangxian.pinkink.widget.GestureScaleRotateView$onDragImageViewTouchListener$1
            final /* synthetic */ GestureScaleRotateView<T> this$0;

            {
                this.this$0 = this;
            }

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                if (event == null) {
                    return true;
                }
                int action = event.getAction();
                if (action == 0) {
                    GestureScaleRotateView<T> gestureScaleRotateView = this.this$0;
                    ((GestureScaleRotateView) gestureScaleRotateView).lastDragAngle = gestureScaleRotateView.getAngle(event.getRawX(), event.getRawY());
                    ((GestureScaleRotateView) this.this$0).lastDragTouchX = event.getX();
                    ((GestureScaleRotateView) this.this$0).lastDragTouchY = event.getY();
                    ((GestureScaleRotateView) this.this$0).isDragTouchDown = true;
                } else if (action == 2) {
                    float rawX = event.getRawX();
                    float rawY = event.getRawY();
                    if (((GestureScaleRotateView) this.this$0).isRotateEnable) {
                        this.this$0.updateRotateAngle(rawX, rawY);
                    }
                    if (((GestureScaleRotateView) this.this$0).isScaleEnable) {
                        this.this$0.updateScale(rawX, rawY);
                    }
                }
                return true;
            }
        };
        initView();
    }

    public final void setOnOperateListener(OnOperateListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.onOperateListener = listener;
    }

    public final void addContentView(T view) {
        Intrinsics.checkNotNullParameter(view, "view");
        this.contentView = view;
        getFlContent().removeAllViews();
        getFlContent().addView(view);
    }

    public final T getContentView() {
        return this.contentView;
    }

    public final void setScaleEnable(boolean enable) {
        this.isScaleEnable = enable;
        updateDragBtnVisible();
    }

    public final void setRotateEnable(boolean enable) {
        this.isRotateEnable = enable;
        updateDragBtnVisible();
    }

    public final void showOperateView() {
        getFlContent().setBackgroundResource(R.drawable.shape_gesture_scale_rotate_content);
        updateDragBtnVisible();
        ViewExtensionsKt.visible(getIvEditDelete());
    }

    public final void hideOperateView() {
        getFlContent().setBackgroundResource(R.drawable.shape_gesture_scale_rotate_content_hide);
        ViewExtensionsKt.gone(getIvEditDrag());
        ViewExtensionsKt.gone(getIvEditDelete());
    }

    public final void updateContentSize(float width, float height) {
        int i = this.contentPadding;
        realUpdateContentSize(((int) width) + i, ((int) height) + i);
    }

    private final void realUpdateContentSize(int width, int height) {
        getContentLayoutParams().width = width;
        getContentLayoutParams().height = height;
        getFlContent().setLayoutParams(getContentLayoutParams());
        getLayoutParams().width = width + this.dp20;
        getLayoutParams().height = height + this.dp20;
    }

    private final void realUpdateContentSize(float width, float height) {
        realUpdateContentSize((int) width, (int) height);
    }

    private final void updateDragBtnVisible() {
        getIvEditDrag().setVisibility((this.isScaleEnable || this.isRotateEnable) ? 0 : 8);
    }

    private final void initView() {
        setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        LayoutInflater.from(getContext()).inflate(R.layout.widget_gesture_scale_rotate_view, (ViewGroup) this, true);
        getIvEditDrag().setOnTouchListener(this.onDragImageViewTouchListener);
        getIvEditDelete().setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.widget.GestureScaleRotateView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GestureScaleRotateView.m377initView$lambda0(this.f$0, view);
            }
        });
        setOnTouchListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-0, reason: not valid java name */
    public static final void m377initView$lambda0(GestureScaleRotateView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnOperateListener onOperateListener = this$0.onOperateListener;
        if (onOperateListener == null) {
            return;
        }
        onOperateListener.onDeleteClick(this$0);
    }

    private final float[] getCenterXY() {
        if (this.calculateWidth < 0.0f) {
            this.calculateWidth = getFlContent().getMeasuredWidth();
        }
        if (this.calculateHeight < 0.0f) {
            this.calculateHeight = getFlContent().getMeasuredHeight();
        }
        int[] iArr = new int[2];
        View view = (View) getParent();
        if (view != null) {
            view.getLocationOnScreen(iArr);
        }
        float f = 2;
        return new float[]{getLeft() + iArr[0] + (this.calculateWidth / f), getTop() + iArr[1] + (this.calculateHeight / f)};
    }

    private final void updatePosition(float x, float y) {
        float f = x - this.lastTouchX;
        float f2 = y - this.lastTouchY;
        GestureScaleRotateView<T> gestureScaleRotateView = this;
        ViewGroup.LayoutParams layoutParams = gestureScaleRotateView.getLayoutParams();
        Integer numValueOf = Integer.valueOf((int) (((layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null) != null ? r1.leftMargin : 0) + f));
        ViewGroup.LayoutParams layoutParams2 = gestureScaleRotateView.getLayoutParams();
        ViewExtensionsKt.setMargins(gestureScaleRotateView, numValueOf, Integer.valueOf((int) (((layoutParams2 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams2 : null) != null ? r3.topMargin : 0) + f2)), 0, 0);
    }

    private final double calculateDistance(float x, float y, float centerX, float centerY) {
        float f = x - centerX;
        float f2 = y - centerY;
        return Math.sqrt((f * f) + (f2 * f2));
    }

    private final ViewGroup.LayoutParams getContentLayoutParams() {
        return (ViewGroup.LayoutParams) this.contentLayoutParams.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateScale(float x, float y) {
        float[] centerXY = getCenterXY();
        float f = centerXY[0];
        float f2 = centerXY[1];
        float f3 = 2;
        double dCalculateDistance = calculateDistance(x, y, f, f2) / ((double) ((float) calculateDistance(((this.calculateWidth / f3) + f) + this.lastDragTouchX, ((this.calculateHeight / f3) + f2) + this.lastDragTouchY, f, f2)));
        float f4 = this.calculateWidth;
        float f5 = this.calculateHeight;
        double d = f4;
        double d2 = f5;
        double d3 = 2;
        int i = (int) ((d - (d * dCalculateDistance)) / d3);
        int i2 = (int) ((d2 - (d2 * dCalculateDistance)) / d3);
        float f6 = (float) (((double) f4) * dCalculateDistance);
        this.calculateWidth = f6;
        float f7 = (float) (((double) f5) * dCalculateDistance);
        this.calculateHeight = f7;
        realUpdateContentSize(f6, f7);
        if (getLayoutParams() instanceof FrameLayout.LayoutParams) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            Objects.requireNonNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            Integer numValueOf = Integer.valueOf(((FrameLayout.LayoutParams) layoutParams).leftMargin + i);
            ViewGroup.LayoutParams layoutParams2 = getLayoutParams();
            Objects.requireNonNull(layoutParams2, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            ViewExtensionsKt.setMargins(this, numValueOf, Integer.valueOf(((FrameLayout.LayoutParams) layoutParams2).topMargin + i2), 0, 0);
            return;
        }
        requestLayout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateRotateAngle(float x, float y) {
        float angle = getAngle(x, y);
        setRotation(getRotation() + (angle - this.lastDragAngle));
        this.lastDragAngle = angle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final float getAngle(float x, float y) {
        float[] centerXY = getCenterXY();
        return (float) Math.toDegrees(Math.atan2(y - centerXY[1], x - centerXY[0]));
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v, MotionEvent event) {
        OnOperateListener onOperateListener;
        if (event == null) {
            return true;
        }
        int action = event.getAction();
        if (action == 0) {
            this.lastTouchTime = System.currentTimeMillis();
            showOperateView();
            this.lastTouchDownX = event.getRawX();
            float rawY = event.getRawY();
            this.lastTouchDownY = rawY;
            this.lastTouchX = this.lastTouchDownX;
            this.lastTouchY = rawY;
            this.isDragTouchDown = false;
        } else if (action != 1) {
            if (action == 2) {
                updatePosition(event.getRawX(), event.getRawY());
                this.lastTouchX = event.getRawX();
                this.lastTouchY = event.getRawY();
            }
        } else if (System.currentTimeMillis() - this.lastTouchTime < 300) {
            if (this.lastTouchDownX == this.lastTouchX) {
                if ((this.lastTouchDownY == this.lastTouchY) && (onOperateListener = this.onOperateListener) != null) {
                    onOperateListener.onContentClick(this);
                }
            }
        }
        return true;
    }
}
