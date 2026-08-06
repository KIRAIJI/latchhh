package com.shangxian.pinkink.ui.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.lazyee.klib.base.ViewBindingDialog;
import com.lazyee.klib.extension.AnyExtensionsKt;
import com.lazyee.klib.extension.ContextExtensionsKt;
import com.lazyee.klib.extension.NumberExtensionsKt;
import com.lazyee.klib.extension.ViewExtensionsKt;
import com.lazyee.klib.extension.WindowExtensionsKt;
import com.lazyee.klib.listener.OnKeyboardVisibleListener;
import com.scwang.smart.refresh.layout.util.SmartUtil;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.bean.FontBean;
import com.shangxian.pinkink.bean.ThemeMaterialBean;
import com.shangxian.pinkink.databinding.DialogTextOptionsBinding;
import com.shangxian.pinkink.databinding.ItemTextOptionsFontFamilyBinding;
import com.shangxian.pinkink.ui.dialog.TextOptionsDialog;
import com.shangxian.pinkink.widget.GestureScaleRotateView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/* JADX INFO: compiled from: TextOptionsDialog.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0002()B)\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\u0010\fJ\b\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0017H\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\u001f\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010 \u001a\u00020\u0017H\u0016J\u0010\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020\u00172\u0006\u0010%\u001a\u00020&H\u0002J\u0010\u0010$\u001a\u00020\u00172\u0006\u0010'\u001a\u00020\u0010H\u0002R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0011\u001a\u0004\u0018\u00010\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lcom/shangxian/pinkink/ui/dialog/TextOptionsDialog;", "Lcom/lazyee/klib/base/ViewBindingDialog;", "Lcom/shangxian/pinkink/databinding/DialogTextOptionsBinding;", "Lcom/lazyee/klib/listener/OnKeyboardVisibleListener;", "context", "Landroid/content/Context;", "userFontList", "", "Lcom/shangxian/pinkink/bean/FontBean;", "gestureScaleRotateView", "Lcom/shangxian/pinkink/widget/GestureScaleRotateView;", "Landroid/widget/TextView;", "(Landroid/content/Context;Ljava/util/List;Lcom/shangxian/pinkink/widget/GestureScaleRotateView;)V", "currentTypeFace", "Landroid/graphics/Typeface;", "textOptionsList", "", "textView", "getTextView", "()Landroid/widget/TextView;", "textView$delegate", "Lkotlin/Lazy;", "initMagicIndicator", "", "initView", "instantiateTextColorOptions", "Landroid/view/View;", "container", "Landroid/view/ViewGroup;", "instantiateTextFontOptions", "instantiateTextRotateOptions", "instantiateTextSizeOptions", "onSoftKeyboardHide", "onSoftKeyboardShow", "keyboardHeight", "", "updateGestureScaleRotateViewSize", "textSize", "", "text", "FontFamilyAdapter", "TextOptionsPagerAdapter", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class TextOptionsDialog extends ViewBindingDialog<DialogTextOptionsBinding> implements OnKeyboardVisibleListener {
    private Typeface currentTypeFace;
    private final GestureScaleRotateView<TextView> gestureScaleRotateView;
    private final List<String> textOptionsList;

    /* JADX INFO: renamed from: textView$delegate, reason: from kotlin metadata */
    private final Lazy textView;
    private final List<FontBean> userFontList;

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4, reason: not valid java name */
    public static final boolean m181initView$lambda4(View view, MotionEvent motionEvent) {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextOptionsDialog(Context context, List<FontBean> userFontList, GestureScaleRotateView<TextView> gestureScaleRotateView) {
        super(context, R.style.Dialog_TextOptions);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(userFontList, "userFontList");
        Intrinsics.checkNotNullParameter(gestureScaleRotateView, "gestureScaleRotateView");
        this.userFontList = userFontList;
        this.gestureScaleRotateView = gestureScaleRotateView;
        this.textOptionsList = new ArrayList();
        this.textView = LazyKt.lazy(new Function0<TextView>() { // from class: com.shangxian.pinkink.ui.dialog.TextOptionsDialog$textView$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final TextView invoke() {
                return (TextView) this.this$0.gestureScaleRotateView.getContentView();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final TextView getTextView() {
        return (TextView) this.textView.getValue();
    }

    @Override // com.lazyee.klib.base.ViewBindingDialog
    public void initView() throws Exception {
        ViewPager viewPager;
        CharSequence text;
        super.initView();
        List<String> list = this.textOptionsList;
        String string = getContext().getString(R.string.string_color);
        Intrinsics.checkNotNullExpressionValue(string, "context.getString(R.string.string_color)");
        list.add(string);
        List<String> list2 = this.textOptionsList;
        String string2 = getContext().getString(R.string.string_font);
        Intrinsics.checkNotNullExpressionValue(string2, "context.getString(R.string.string_font)");
        list2.add(string2);
        List<String> list3 = this.textOptionsList;
        String string3 = getContext().getString(R.string.string_size);
        Intrinsics.checkNotNullExpressionValue(string3, "context.getString(R.string.string_size)");
        list3.add(string3);
        List<String> list4 = this.textOptionsList;
        String string4 = getContext().getString(R.string.string_rotate);
        Intrinsics.checkNotNullExpressionValue(string4, "context.getString(R.string.string_rotate)");
        list4.add(string4);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window == null ? null : window.getAttributes();
        if (attributes != null) {
            attributes.gravity = 80;
        }
        Window window2 = getWindow();
        if (window2 != null) {
            window2.setAttributes(attributes);
        }
        Window window3 = getWindow();
        if (window3 != null) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            WindowExtensionsKt.setSize(window3, -1, AnyExtensionsKt.getScreenHeight(context));
        }
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        Window window4 = getWindow();
        if (window4 != null) {
            ContextExtensionsKt.addAdjustNothingModeOnKeyBoardVisibleListener(window4, this);
        }
        final DialogTextOptionsBinding mViewBinding = getMViewBinding();
        if (mViewBinding != null) {
            EditText etContent = mViewBinding.etContent;
            Intrinsics.checkNotNullExpressionValue(etContent, "etContent");
            etContent.addTextChangedListener(new TextWatcher() { // from class: com.shangxian.pinkink.ui.dialog.TextOptionsDialog$initView$lambda-3$$inlined$addTextChangedListener$default$1
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence text2, int start, int count, int after) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence text2, int start, int before, int count) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable s) {
                    mViewBinding.ivClear.setVisibility(TextUtils.isEmpty(mViewBinding.etContent.getText()) ? 4 : 0);
                }
            });
            EditText editText = mViewBinding.etContent;
            TextView textView = getTextView();
            editText.setText((textView == null || (text = textView.getText()) == null) ? "" : text);
            mViewBinding.ivClear.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.TextOptionsDialog$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    TextOptionsDialog.m179initView$lambda3$lambda1(mViewBinding, view);
                }
            });
            mViewBinding.ivComplete.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.TextOptionsDialog$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    TextOptionsDialog.m180initView$lambda3$lambda2(mViewBinding, this, view);
                }
            });
        }
        initMagicIndicator();
        DialogTextOptionsBinding mViewBinding2 = getMViewBinding();
        ViewPager viewPager2 = mViewBinding2 == null ? null : mViewBinding2.vpTextOptions;
        if (viewPager2 != null) {
            viewPager2.setAdapter(new TextOptionsPagerAdapter(this));
        }
        DialogTextOptionsBinding mViewBinding3 = getMViewBinding();
        if (mViewBinding3 != null && (viewPager = mViewBinding3.vpTextOptions) != null) {
            viewPager.setOnTouchListener(new View.OnTouchListener() { // from class: com.shangxian.pinkink.ui.dialog.TextOptionsDialog$$ExternalSyntheticLambda9
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return TextOptionsDialog.m181initView$lambda4(view, motionEvent);
                }
            });
        }
        DialogTextOptionsBinding mViewBinding4 = getMViewBinding();
        MagicIndicator magicIndicator = mViewBinding4 == null ? null : mViewBinding4.magicIndicator;
        DialogTextOptionsBinding mViewBinding5 = getMViewBinding();
        ViewPagerHelper.bind(magicIndicator, mViewBinding5 != null ? mViewBinding5.vpTextOptions : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3$lambda-1, reason: not valid java name */
    public static final void m179initView$lambda3$lambda1(DialogTextOptionsBinding this_run, View view) {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        this_run.etContent.setText("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-3$lambda-2, reason: not valid java name */
    public static final void m180initView$lambda3$lambda2(DialogTextOptionsBinding this_run, TextOptionsDialog this$0, View view) {
        Intrinsics.checkNotNullParameter(this_run, "$this_run");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String string = this_run.etContent.getText().toString();
        this$0.updateGestureScaleRotateViewSize(string);
        TextView textView = this$0.getTextView();
        if (textView != null) {
            textView.setText(string);
        }
        this$0.dismiss();
    }

    @Override // com.lazyee.klib.listener.OnKeyboardVisibleListener
    public void onSoftKeyboardHide() {
        DialogTextOptionsBinding mViewBinding = getMViewBinding();
        View view = mViewBinding == null ? null : mViewBinding.keyboardView;
        if (view == null) {
            return;
        }
        view.setLayoutParams(new RelativeLayout.LayoutParams(-1, 0));
    }

    @Override // com.lazyee.klib.listener.OnKeyboardVisibleListener
    public void onSoftKeyboardShow(int keyboardHeight) {
        DialogTextOptionsBinding mViewBinding = getMViewBinding();
        View view = mViewBinding == null ? null : mViewBinding.keyboardView;
        if (view == null) {
            return;
        }
        view.setLayoutParams(new RelativeLayout.LayoutParams(-1, keyboardHeight));
    }

    private final void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new AnonymousClass1());
        DialogTextOptionsBinding mViewBinding = getMViewBinding();
        MagicIndicator magicIndicator = mViewBinding == null ? null : mViewBinding.magicIndicator;
        if (magicIndicator == null) {
            return;
        }
        magicIndicator.setNavigator(commonNavigator);
    }

    /* JADX INFO: renamed from: com.shangxian.pinkink.ui.dialog.TextOptionsDialog$initMagicIndicator$1, reason: invalid class name */
    /* JADX INFO: compiled from: TextOptionsDialog.kt */
    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"com/shangxian/pinkink/ui/dialog/TextOptionsDialog$initMagicIndicator$1", "Lnet/lucode/hackware/magicindicator/buildins/commonnavigator/abs/CommonNavigatorAdapter;", "getCount", "", "getIndicator", "Lnet/lucode/hackware/magicindicator/buildins/commonnavigator/abs/IPagerIndicator;", "context", "Landroid/content/Context;", "getTitleView", "Lnet/lucode/hackware/magicindicator/buildins/commonnavigator/abs/IPagerTitleView;", "index", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class AnonymousClass1 extends CommonNavigatorAdapter {
        AnonymousClass1() {
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return TextOptionsDialog.this.textOptionsList.size();
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            Intrinsics.checkNotNullParameter(context, "context");
            ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
            colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#FF999999"));
            colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#FF333333"));
            colorTransitionPagerTitleView.setTextSize(2, 16.0f);
            colorTransitionPagerTitleView.setText((CharSequence) TextOptionsDialog.this.textOptionsList.get(index));
            final TextOptionsDialog textOptionsDialog = TextOptionsDialog.this;
            colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.TextOptionsDialog$initMagicIndicator$1$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    TextOptionsDialog.AnonymousClass1.m192getTitleView$lambda0(textOptionsDialog, index, view);
                }
            });
            return colorTransitionPagerTitleView;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: getTitleView$lambda-0, reason: not valid java name */
        public static final void m192getTitleView$lambda0(TextOptionsDialog this$0, int i, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            DialogTextOptionsBinding mViewBinding = this$0.getMViewBinding();
            ViewPager viewPager = mViewBinding == null ? null : mViewBinding.vpTextOptions;
            if (viewPager == null) {
                return;
            }
            viewPager.setCurrentItem(i);
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            linePagerIndicator.setMode(2);
            linePagerIndicator.setLayoutParams(new ViewGroup.LayoutParams(SmartUtil.dp2px(12.0f), SmartUtil.dp2px(3.0f)));
            linePagerIndicator.setRoundRadius(SmartUtil.dp2px(1.5f));
            linePagerIndicator.setColors(Integer.valueOf(Color.parseColor("#FFFF7A9A")));
            return linePagerIndicator;
        }
    }

    /* JADX INFO: compiled from: TextOptionsDialog.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\bH\u0016J\u0018\u0010\f\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u0011"}, d2 = {"Lcom/shangxian/pinkink/ui/dialog/TextOptionsDialog$TextOptionsPagerAdapter;", "Landroidx/viewpager/widget/PagerAdapter;", "(Lcom/shangxian/pinkink/ui/dialog/TextOptionsDialog;)V", "destroyItem", "", "container", "Landroid/view/ViewGroup;", "position", "", "object", "", "getCount", "instantiateItem", "isViewFromObject", "", "view", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    private final class TextOptionsPagerAdapter extends PagerAdapter {
        final /* synthetic */ TextOptionsDialog this$0;

        public TextOptionsPagerAdapter(TextOptionsDialog this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this.this$0 = this$0;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return this.this$0.textOptionsList.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view, Object object) {
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(object, "object");
            return Intrinsics.areEqual(view, object);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup container, int position, Object object) {
            Intrinsics.checkNotNullParameter(container, "container");
            Intrinsics.checkNotNullParameter(object, "object");
            container.removeView((View) object);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup container, int position) {
            Intrinsics.checkNotNullParameter(container, "container");
            if (position == 0) {
                return this.this$0.instantiateTextColorOptions(container);
            }
            if (position == 1) {
                return this.this$0.instantiateTextFontOptions(container);
            }
            return position == 2 ? this.this$0.instantiateTextSizeOptions(container) : this.this$0.instantiateTextRotateOptions(container);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateGestureScaleRotateViewSize(String text) {
        ThemeMaterialBean themeMaterialBean = (ThemeMaterialBean) this.gestureScaleRotateView.getTag();
        if (themeMaterialBean != null) {
            themeMaterialBean.setContent(text);
        }
        TextView textView = getTextView();
        float fPx2sp = NumberExtensionsKt.px2sp(textView == null ? 0.0f : textView.getTextSize());
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        float[] fArrMeasureText = ContextExtensionsKt.measureText(context, text, fPx2sp, this.currentTypeFace);
        TextView textView2 = getTextView();
        int paddingLeft = textView2 == null ? 0 : textView2.getPaddingLeft();
        TextView textView3 = getTextView();
        int paddingTop = textView3 == null ? 0 : textView3.getPaddingTop();
        TextView textView4 = getTextView();
        int paddingRight = textView4 == null ? 0 : textView4.getPaddingRight();
        this.gestureScaleRotateView.updateContentSize(paddingLeft + paddingRight + fArrMeasureText[0], paddingTop + (getTextView() == null ? 0 : r4.getPaddingBottom()) + fArrMeasureText[1]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateGestureScaleRotateViewSize(float textSize) {
        ThemeMaterialBean themeMaterialBean = (ThemeMaterialBean) this.gestureScaleRotateView.getTag();
        if (themeMaterialBean != null) {
            themeMaterialBean.setTextSize(textSize);
        }
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        TextView textView = getTextView();
        String strValueOf = String.valueOf(textView == null ? null : textView.getText());
        if (strValueOf == null) {
            strValueOf = "";
        }
        float[] fArrMeasureText = ContextExtensionsKt.measureText(context, strValueOf, textSize, this.currentTypeFace);
        TextView textView2 = getTextView();
        int paddingLeft = textView2 == null ? 0 : textView2.getPaddingLeft();
        TextView textView3 = getTextView();
        int paddingTop = textView3 == null ? 0 : textView3.getPaddingTop();
        TextView textView4 = getTextView();
        int paddingRight = textView4 == null ? 0 : textView4.getPaddingRight();
        this.gestureScaleRotateView.updateContentSize(paddingLeft + paddingRight + fArrMeasureText[0], paddingTop + (getTextView() == null ? 0 : r4.getPaddingBottom()) + fArrMeasureText[1]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final View instantiateTextColorOptions(ViewGroup container) {
        View textColorOptionsView = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_text_options_text_color, container, false);
        container.addView(textColorOptionsView);
        final ImageView imageView = (ImageView) textColorOptionsView.findViewById(R.id.ivColorWhite);
        final ImageView imageView2 = (ImageView) textColorOptionsView.findViewById(R.id.ivColorRed);
        final ImageView imageView3 = (ImageView) textColorOptionsView.findViewById(R.id.ivColorBlack);
        Object tag = this.gestureScaleRotateView.getTag();
        Objects.requireNonNull(tag, "null cannot be cast to non-null type com.shangxian.pinkink.bean.ThemeMaterialBean");
        final ThemeMaterialBean themeMaterialBean = (ThemeMaterialBean) tag;
        int color = TextUtils.isEmpty(themeMaterialBean.getTextColor()) ? -1 : Color.parseColor(themeMaterialBean.getTextColor());
        imageView.setEnabled(color != -1);
        imageView3.setEnabled(color != -16777216);
        imageView2.setEnabled(color != -65536);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.TextOptionsDialog$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TextOptionsDialog.m182instantiateTextColorOptions$lambda5(this.f$0, themeMaterialBean, imageView, imageView2, imageView3, view);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.TextOptionsDialog$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TextOptionsDialog.m183instantiateTextColorOptions$lambda6(this.f$0, themeMaterialBean, imageView, imageView2, imageView3, view);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.TextOptionsDialog$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TextOptionsDialog.m184instantiateTextColorOptions$lambda7(this.f$0, themeMaterialBean, imageView, imageView2, imageView3, view);
            }
        });
        Intrinsics.checkNotNullExpressionValue(textColorOptionsView, "textColorOptionsView");
        return textColorOptionsView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: instantiateTextColorOptions$lambda-5, reason: not valid java name */
    public static final void m182instantiateTextColorOptions$lambda5(TextOptionsDialog this$0, ThemeMaterialBean themeMaterial, ImageView imageView, ImageView imageView2, ImageView imageView3, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(themeMaterial, "$themeMaterial");
        TextView textView = this$0.getTextView();
        if (textView != null) {
            textView.setTextColor(-1);
        }
        themeMaterial.setTextColor("#FFFFFF");
        imageView.setEnabled(false);
        imageView2.setEnabled(true);
        imageView3.setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: instantiateTextColorOptions$lambda-6, reason: not valid java name */
    public static final void m183instantiateTextColorOptions$lambda6(TextOptionsDialog this$0, ThemeMaterialBean themeMaterial, ImageView imageView, ImageView imageView2, ImageView imageView3, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(themeMaterial, "$themeMaterial");
        TextView textView = this$0.getTextView();
        if (textView != null) {
            textView.setTextColor(SupportMenu.CATEGORY_MASK);
        }
        themeMaterial.setTextColor("#FF0000");
        imageView.setEnabled(true);
        imageView2.setEnabled(false);
        imageView3.setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: instantiateTextColorOptions$lambda-7, reason: not valid java name */
    public static final void m184instantiateTextColorOptions$lambda7(TextOptionsDialog this$0, ThemeMaterialBean themeMaterial, ImageView imageView, ImageView imageView2, ImageView imageView3, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(themeMaterial, "$themeMaterial");
        TextView textView = this$0.getTextView();
        if (textView != null) {
            textView.setTextColor(-16777216);
        }
        themeMaterial.setTextColor("#000000");
        imageView.setEnabled(true);
        imageView2.setEnabled(true);
        imageView3.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final View instantiateTextSizeOptions(ViewGroup container) {
        float fCeil = ((float) Math.ceil(NumberExtensionsKt.px2sp(getTextView() == null ? 0.0f : r0.getTextSize()) * r1)) / 10.0f;
        View optionsView = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_text_options_text_size, container, false);
        final TextView textView = (TextView) optionsView.findViewById(R.id.tvFontSize);
        textView.setText(String.valueOf(fCeil));
        SeekBar seekBar = (SeekBar) optionsView.findViewById(R.id.sbFontSize);
        seekBar.setProgress((int) (fCeil * 10));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.shangxian.pinkink.ui.dialog.TextOptionsDialog.instantiateTextSizeOptions.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar p0) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar p0) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar p0, int progress, boolean p2) {
                float f = progress / 10.0f;
                textView.setText(String.valueOf(f));
                this.updateGestureScaleRotateViewSize(f);
                TextView textView2 = this.getTextView();
                if (textView2 == null) {
                    return;
                }
                textView2.setTextSize(2, f);
            }
        });
        container.addView(optionsView);
        Intrinsics.checkNotNullExpressionValue(optionsView, "optionsView");
        return optionsView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final View instantiateTextFontOptions(ViewGroup container) {
        View optionsView = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_text_options_text_font_family, container, false);
        RecyclerView recyclerView = (RecyclerView) optionsView.findViewById(R.id.rvFontFamily);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new FontBean());
        arrayList.addAll(this.userFontList);
        recyclerView.setAdapter(new FontFamilyAdapter(this, arrayList));
        container.addView(optionsView);
        Intrinsics.checkNotNullExpressionValue(optionsView, "optionsView");
        return optionsView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: TextOptionsDialog.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u0010\u0012\f\u0012\n0\u0002R\u00060\u0000R\u00020\u00030\u0001:\u0001\u0012B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016J \u0010\n\u001a\u00020\u000b2\u000e\u0010\f\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\r\u001a\u00020\tH\u0016J \u0010\u000e\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\tH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/shangxian/pinkink/ui/dialog/TextOptionsDialog$FontFamilyAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/shangxian/pinkink/ui/dialog/TextOptionsDialog$FontFamilyAdapter$FontFamilyViewHolder;", "Lcom/shangxian/pinkink/ui/dialog/TextOptionsDialog;", "fontList", "", "Lcom/shangxian/pinkink/bean/FontBean;", "(Lcom/shangxian/pinkink/ui/dialog/TextOptionsDialog;Ljava/util/List;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "FontFamilyViewHolder", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    final class FontFamilyAdapter extends RecyclerView.Adapter<FontFamilyViewHolder> {
        private final List<FontBean> fontList;
        final /* synthetic */ TextOptionsDialog this$0;

        public FontFamilyAdapter(TextOptionsDialog this$0, List<FontBean> fontList) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(fontList, "fontList");
            this.this$0 = this$0;
            this.fontList = fontList;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public FontFamilyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            ItemTextOptionsFontFamilyBinding itemTextOptionsFontFamilyBindingInflate = ItemTextOptionsFontFamilyBinding.inflate(LayoutInflater.from(parent.getContext()));
            Intrinsics.checkNotNullExpressionValue(itemTextOptionsFontFamilyBindingInflate, "inflate(LayoutInflater.from(parent.context))");
            return new FontFamilyViewHolder(this, itemTextOptionsFontFamilyBindingInflate);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.fontList.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(FontFamilyViewHolder holder, int position) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            holder.bind(this.fontList.get(position));
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: compiled from: TextOptionsDialog.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/shangxian/pinkink/ui/dialog/TextOptionsDialog$FontFamilyAdapter$FontFamilyViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/shangxian/pinkink/databinding/ItemTextOptionsFontFamilyBinding;", "(Lcom/shangxian/pinkink/ui/dialog/TextOptionsDialog$FontFamilyAdapter;Lcom/shangxian/pinkink/databinding/ItemTextOptionsFontFamilyBinding;)V", "bind", "", "item", "Lcom/shangxian/pinkink/bean/FontBean;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
        final class FontFamilyViewHolder extends RecyclerView.ViewHolder {
            private final ItemTextOptionsFontFamilyBinding binding;
            final /* synthetic */ FontFamilyAdapter this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public FontFamilyViewHolder(FontFamilyAdapter this$0, ItemTextOptionsFontFamilyBinding binding) {
                super(binding.getRoot());
                Intrinsics.checkNotNullParameter(this$0, "this$0");
                Intrinsics.checkNotNullParameter(binding, "binding");
                this.this$0 = this$0;
                this.binding = binding;
            }

            public final void bind(final FontBean item) {
                Intrinsics.checkNotNullParameter(item, "item");
                ItemTextOptionsFontFamilyBinding itemTextOptionsFontFamilyBinding = this.binding;
                final TextOptionsDialog textOptionsDialog = this.this$0.this$0;
                final FontFamilyAdapter fontFamilyAdapter = this.this$0;
                itemTextOptionsFontFamilyBinding.llFontFamily.setEnabled(!item.isChecked());
                if (TextUtils.isEmpty(item.getId())) {
                    TextView tvFontFamilyExample = itemTextOptionsFontFamilyBinding.tvFontFamilyExample;
                    Intrinsics.checkNotNullExpressionValue(tvFontFamilyExample, "tvFontFamilyExample");
                    ViewExtensionsKt.visible(tvFontFamilyExample);
                    ImageView ivFontFamilyExample = itemTextOptionsFontFamilyBinding.ivFontFamilyExample;
                    Intrinsics.checkNotNullExpressionValue(ivFontFamilyExample, "ivFontFamilyExample");
                    ViewExtensionsKt.gone(ivFontFamilyExample);
                    itemTextOptionsFontFamilyBinding.tvFontFamilyExample.setText(textOptionsDialog.getContext().getString(R.string.string_default_font));
                    itemTextOptionsFontFamilyBinding.tvFontFamilyName.setText(textOptionsDialog.getContext().getString(R.string.string_default_font));
                } else {
                    TextView tvFontFamilyExample2 = itemTextOptionsFontFamilyBinding.tvFontFamilyExample;
                    Intrinsics.checkNotNullExpressionValue(tvFontFamilyExample2, "tvFontFamilyExample");
                    ViewExtensionsKt.gone(tvFontFamilyExample2);
                    ImageView ivFontFamilyExample2 = itemTextOptionsFontFamilyBinding.ivFontFamilyExample;
                    Intrinsics.checkNotNullExpressionValue(ivFontFamilyExample2, "ivFontFamilyExample");
                    ViewExtensionsKt.visible(ivFontFamilyExample2);
                    Glide.with(itemTextOptionsFontFamilyBinding.ivFontFamilyExample).load(item.getFontCover()).into(itemTextOptionsFontFamilyBinding.ivFontFamilyExample);
                    itemTextOptionsFontFamilyBinding.tvFontFamilyName.setText(item.getFontName());
                }
                itemTextOptionsFontFamilyBinding.llFontFamily.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.TextOptionsDialog$FontFamilyAdapter$FontFamilyViewHolder$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        TextOptionsDialog.FontFamilyAdapter.FontFamilyViewHolder.m190bind$lambda2$lambda1(fontFamilyAdapter, textOptionsDialog, item, view);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            /* JADX INFO: renamed from: bind$lambda-2$lambda-1, reason: not valid java name */
            public static final void m190bind$lambda2$lambda1(FontFamilyAdapter this$0, TextOptionsDialog this$1, FontBean item, View view) {
                Intrinsics.checkNotNullParameter(this$0, "this$0");
                Intrinsics.checkNotNullParameter(this$1, "this$1");
                Intrinsics.checkNotNullParameter(item, "$item");
                for (FontBean fontBean : this$0.fontList) {
                    fontBean.setChecked(Intrinsics.areEqual(fontBean, item));
                }
                this$1.currentTypeFace = TextUtils.isEmpty(item.getId()) ? null : Typeface.createFromFile(item.getLocalFilePath());
                TextView textView = this$1.getTextView();
                if (textView != null) {
                    textView.setTypeface(this$1.currentTypeFace);
                }
                ThemeMaterialBean themeMaterialBean = (ThemeMaterialBean) this$1.gestureScaleRotateView.getTag();
                if (themeMaterialBean != null) {
                    String fileName = item.getFileName();
                    Intrinsics.checkNotNullExpressionValue(fileName, "item.fileName");
                    themeMaterialBean.setFontFamilyFileName(fileName);
                }
                TextView textView2 = this$1.getTextView();
                this$1.updateGestureScaleRotateViewSize(String.valueOf(textView2 != null ? textView2.getText() : null));
                this$0.notifyDataSetChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final View instantiateTextRotateOptions(ViewGroup container) {
        View optionsView = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_text_options_text_rotate, container, false);
        final LinearLayout linearLayout = (LinearLayout) optionsView.findViewById(R.id.llRotate0Angle);
        final LinearLayout linearLayout2 = (LinearLayout) optionsView.findViewById(R.id.llRotate90Angle);
        final LinearLayout linearLayout3 = (LinearLayout) optionsView.findViewById(R.id.llRotate180Angle);
        final LinearLayout linearLayout4 = (LinearLayout) optionsView.findViewById(R.id.llRotate270Angle);
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.TextOptionsDialog$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TextOptionsDialog.m187instantiateTextRotateOptions$lambda8(this.f$0, linearLayout, linearLayout2, linearLayout3, linearLayout4, view);
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.TextOptionsDialog$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TextOptionsDialog.m188instantiateTextRotateOptions$lambda9(this.f$0, linearLayout, linearLayout2, linearLayout3, linearLayout4, view);
            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.TextOptionsDialog$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TextOptionsDialog.m185instantiateTextRotateOptions$lambda10(this.f$0, linearLayout, linearLayout2, linearLayout3, linearLayout4, view);
            }
        });
        linearLayout4.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.dialog.TextOptionsDialog$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TextOptionsDialog.m186instantiateTextRotateOptions$lambda11(this.f$0, linearLayout, linearLayout2, linearLayout3, linearLayout4, view);
            }
        });
        container.addView(optionsView);
        Intrinsics.checkNotNullExpressionValue(optionsView, "optionsView");
        return optionsView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: instantiateTextRotateOptions$lambda-8, reason: not valid java name */
    public static final void m187instantiateTextRotateOptions$lambda8(TextOptionsDialog this$0, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.gestureScaleRotateView.setRotation(0.0f);
        linearLayout.setEnabled(false);
        linearLayout2.setEnabled(true);
        linearLayout3.setEnabled(true);
        linearLayout4.setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: instantiateTextRotateOptions$lambda-9, reason: not valid java name */
    public static final void m188instantiateTextRotateOptions$lambda9(TextOptionsDialog this$0, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.gestureScaleRotateView.setRotation(90.0f);
        linearLayout.setEnabled(true);
        linearLayout2.setEnabled(false);
        linearLayout3.setEnabled(true);
        linearLayout4.setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: instantiateTextRotateOptions$lambda-10, reason: not valid java name */
    public static final void m185instantiateTextRotateOptions$lambda10(TextOptionsDialog this$0, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.gestureScaleRotateView.setRotation(180.0f);
        linearLayout.setEnabled(true);
        linearLayout2.setEnabled(true);
        linearLayout3.setEnabled(false);
        linearLayout4.setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: instantiateTextRotateOptions$lambda-11, reason: not valid java name */
    public static final void m186instantiateTextRotateOptions$lambda11(TextOptionsDialog this$0, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.gestureScaleRotateView.setRotation(270.0f);
        linearLayout.setEnabled(true);
        linearLayout2.setEnabled(true);
        linearLayout3.setEnabled(true);
        linearLayout4.setEnabled(false);
    }
}
