package com.shangxian.pinkink.ui.mine;

import android.view.View;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.lazyee.klib.extension.NumberExtensionsKt;
import com.lazyee.klib.mvvm.ViewModel;
import com.lazyee.klib.recyclerview.decoration.GridSpacingItemDecoration;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.bean.FontBean;
import com.shangxian.pinkink.databinding.ActivityUserFontLibraryBinding;
import com.shangxian.pinkink.mvvm.viewmodel.FontViewModel;
import com.shangxian.pinkink.ui.mine.adapter.FontAdapter;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: UserFontLibraryActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u000f\u001a\u00020\u0010H\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f¨\u0006\u0011"}, d2 = {"Lcom/shangxian/pinkink/ui/mine/UserFontLibraryActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityUserFontLibraryBinding;", "()V", "fontAdapter", "Lcom/shangxian/pinkink/ui/mine/adapter/FontAdapter;", "fontList", "", "Lcom/shangxian/pinkink/bean/FontBean;", "fontViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/FontViewModel;", "getFontViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/FontViewModel;", "fontViewModel$delegate", "Lkotlin/Lazy;", "initView", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class UserFontLibraryActivity extends BaseActivity<ActivityUserFontLibraryBinding> {
    private final FontAdapter fontAdapter;
    private final List<FontBean> fontList;

    /* JADX INFO: renamed from: fontViewModel$delegate, reason: from kotlin metadata */
    private final Lazy fontViewModel = LazyKt.lazy(new Function0<FontViewModel>() { // from class: com.shangxian.pinkink.ui.mine.UserFontLibraryActivity$fontViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final FontViewModel invoke() {
            return (FontViewModel) new ViewModelProvider(this.this$0).get(FontViewModel.class);
        }
    });

    public UserFontLibraryActivity() {
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
        ActivityUserFontLibraryBinding activityUserFontLibraryBinding = (ActivityUserFontLibraryBinding) getMViewBinding();
        activityUserFontLibraryBinding.titleBar.tvTitle.setText(getString(R.string.title_my_font));
        activityUserFontLibraryBinding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.mine.UserFontLibraryActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UserFontLibraryActivity.m293initView$lambda1$lambda0(this.f$0, view);
            }
        });
        activityUserFontLibraryBinding.rvFont.addItemDecoration(new GridSpacingItemDecoration(NumberExtensionsKt.dp2px(10.0f)));
        activityUserFontLibraryBinding.rvFont.setAdapter(this.fontAdapter);
        getFontViewModel().getUserFontListLiveData().observe(this, new Observer() { // from class: com.shangxian.pinkink.ui.mine.UserFontLibraryActivity$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                UserFontLibraryActivity.m294initView$lambda2(this.f$0, (List) obj);
            }
        });
        getFontViewModel().getUserFontList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-1$lambda-0, reason: not valid java name */
    public static final void m293initView$lambda1$lambda0(UserFontLibraryActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-2, reason: not valid java name */
    public static final void m294initView$lambda2(UserFontLibraryActivity this$0, List it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.fontList.clear();
        List<FontBean> list = this$0.fontList;
        Intrinsics.checkNotNullExpressionValue(it, "it");
        list.addAll(it);
        this$0.fontAdapter.notifyDataSetChanged();
    }
}
