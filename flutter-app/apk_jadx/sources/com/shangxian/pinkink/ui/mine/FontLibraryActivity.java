package com.shangxian.pinkink.ui.mine;

import android.view.View;
import android.widget.LinearLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.lazyee.klib.extension.ContextExtensionsKt;
import com.lazyee.klib.extension.NumberExtensionsKt;
import com.lazyee.klib.extension.ViewExtensionsKt;
import com.lazyee.klib.mvvm.LoadingState;
import com.lazyee.klib.mvvm.ViewModel;
import com.lazyee.klib.recyclerview.decoration.GridSpacingItemDecoration;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.bean.FontBean;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.databinding.ActivityFontLibraryBinding;
import com.shangxian.pinkink.mvvm.viewmodel.FontViewModel;
import com.shangxian.pinkink.ui.dialog.FontDownloadDialog;
import com.shangxian.pinkink.ui.mine.adapter.FontAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: FontLibraryActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u0010\u001a\u00020\u0011H\u0017J\u0018\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u000b8CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\r¨\u0006\u0019"}, d2 = {"Lcom/shangxian/pinkink/ui/mine/FontLibraryActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityFontLibraryBinding;", "Lcom/shangxian/pinkink/ui/dialog/FontDownloadDialog$OnDownloadCompleteListener;", "()V", "fontAdapter", "Lcom/shangxian/pinkink/ui/mine/adapter/FontAdapter;", "fontList", "", "Lcom/shangxian/pinkink/bean/FontBean;", "fontViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/FontViewModel;", "getFontViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/FontViewModel;", "fontViewModel$delegate", "Lkotlin/Lazy;", "initView", "", "onDownloadComplete", "font", "file", "Ljava/io/File;", "onPageLoadingStateChanged", "state", "Lcom/lazyee/klib/mvvm/LoadingState;", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class FontLibraryActivity extends BaseActivity<ActivityFontLibraryBinding> implements FontDownloadDialog.OnDownloadCompleteListener {
    private final FontAdapter fontAdapter;
    private final List<FontBean> fontList;

    /* JADX INFO: renamed from: fontViewModel$delegate, reason: from kotlin metadata */
    private final Lazy fontViewModel = LazyKt.lazy(new Function0<FontViewModel>() { // from class: com.shangxian.pinkink.ui.mine.FontLibraryActivity$fontViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final FontViewModel invoke() {
            return (FontViewModel) new ViewModelProvider(this.this$0).get(FontViewModel.class);
        }
    });

    /* JADX INFO: compiled from: FontLibraryActivity.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LoadingState.values().length];
            iArr[LoadingState.LOADING.ordinal()] = 1;
            iArr[LoadingState.SUCCESS.ordinal()] = 2;
            iArr[LoadingState.FAILURE.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public FontLibraryActivity() {
        ArrayList arrayList = new ArrayList();
        this.fontList = arrayList;
        this.fontAdapter = new FontAdapter(arrayList);
    }

    @ViewModel
    private final FontViewModel getFontViewModel() {
        return (FontViewModel) this.fontViewModel.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        ActivityFontLibraryBinding activityFontLibraryBinding = (ActivityFontLibraryBinding) getMViewBinding();
        activityFontLibraryBinding.titleBar.tvTitle.setText(getString(R.string.font_library));
        activityFontLibraryBinding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.mine.FontLibraryActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FontLibraryActivity.m291initView$lambda1$lambda0(this.f$0, view);
            }
        });
        activityFontLibraryBinding.rvFont.addItemDecoration(new GridSpacingItemDecoration(NumberExtensionsKt.dp2px(10.0f)));
        this.fontAdapter.setOnFontItemClickListener(new FontAdapter.OnFontItemClickListener() { // from class: com.shangxian.pinkink.ui.mine.FontLibraryActivity$initView$1$2
            @Override // com.shangxian.pinkink.ui.mine.adapter.FontAdapter.OnFontItemClickListener
            public void onClickFont(FontBean font) {
                Intrinsics.checkNotNullParameter(font, "font");
                String fontFilePath = font.getFontFilePath();
                Intrinsics.checkNotNullExpressionValue(fontFilePath, "font.fontFilePath");
                if (new File(AppConfig.INSTANCE.getFontSavePath() + ((Object) font.getId()) + '.' + ((String) CollectionsKt.last(StringsKt.split$default((CharSequence) fontFilePath, new String[]{"."}, false, 0, 6, (Object) null)))).exists()) {
                    FontLibraryActivity fontLibraryActivity = this.this$0;
                    FontLibraryActivity fontLibraryActivity2 = fontLibraryActivity;
                    String string = fontLibraryActivity.getString(R.string.toast_fonts_have_been_downloaded);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.toast…nts_have_been_downloaded)");
                    ContextExtensionsKt.toastShort(fontLibraryActivity2, string);
                    return;
                }
                new FontDownloadDialog(this.this$0, font).setOnDownloadCompleteListener(this.this$0).show();
            }
        });
        activityFontLibraryBinding.rvFont.setAdapter(this.fontAdapter);
        getFontViewModel().getFontList();
        getFontViewModel().getFontListLiveData().observe(this, new Observer() { // from class: com.shangxian.pinkink.ui.mine.FontLibraryActivity$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FontLibraryActivity.m292initView$lambda2(this.f$0, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-1$lambda-0, reason: not valid java name */
    public static final void m291initView$lambda1$lambda0(FontLibraryActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2, reason: not valid java name */
    public static final void m292initView$lambda2(FontLibraryActivity this$0, List it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.fontList.clear();
        List<FontBean> list = this$0.fontList;
        Intrinsics.checkNotNullExpressionValue(it, "it");
        list.addAll(it);
        this$0.fontAdapter.notifyDataSetChanged();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.BaseActivity, com.lazyee.klib.mvvm.MVVMBaseView
    public void onPageLoadingStateChanged(LoadingState state) {
        Intrinsics.checkNotNullParameter(state, "state");
        super.onPageLoadingStateChanged(state);
        int i = WhenMappings.$EnumSwitchMapping$0[state.ordinal()];
        if (i == 1) {
            ActivityFontLibraryBinding activityFontLibraryBinding = (ActivityFontLibraryBinding) getMViewBinding();
            LinearLayout linearLayout = activityFontLibraryBinding.pageLoadingView.contentView;
            Intrinsics.checkNotNullExpressionValue(linearLayout, "pageLoadingView.contentView");
            ViewExtensionsKt.visible(linearLayout);
            RecyclerView rvFont = activityFontLibraryBinding.rvFont;
            Intrinsics.checkNotNullExpressionValue(rvFont, "rvFont");
            ViewExtensionsKt.gone(rvFont);
            return;
        }
        if (i != 2) {
            return;
        }
        ActivityFontLibraryBinding activityFontLibraryBinding2 = (ActivityFontLibraryBinding) getMViewBinding();
        LinearLayout linearLayout2 = activityFontLibraryBinding2.pageLoadingView.contentView;
        Intrinsics.checkNotNullExpressionValue(linearLayout2, "pageLoadingView.contentView");
        ViewExtensionsKt.gone(linearLayout2);
        RecyclerView rvFont2 = activityFontLibraryBinding2.rvFont;
        Intrinsics.checkNotNullExpressionValue(rvFont2, "rvFont");
        ViewExtensionsKt.visible(rvFont2);
    }

    @Override // com.shangxian.pinkink.ui.dialog.FontDownloadDialog.OnDownloadCompleteListener
    public void onDownloadComplete(FontBean font, File file) {
        Intrinsics.checkNotNullParameter(font, "font");
        Intrinsics.checkNotNullParameter(file, "file");
        getFontViewModel().addFontToUser(font);
        ContextExtensionsKt.toastShort(this, "字体已下载");
    }
}
