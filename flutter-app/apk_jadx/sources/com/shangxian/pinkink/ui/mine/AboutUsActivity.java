package com.shangxian.pinkink.ui.mine;

import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import com.lazyee.klib.extension.ContextExtensionsKt;
import com.lazyee.klib.mvvm.ViewModel;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.databinding.ActivityAboutUsBinding;
import com.shangxian.pinkink.mvvm.viewmodel.FirmwareViewModel;
import com.shangxian.pinkink.ui.webview.CommonWebViewActivity;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AboutUsActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\n\u001a\u00020\u000bH\u0016R\u001b\u0010\u0004\u001a\u00020\u00058CX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\f"}, d2 = {"Lcom/shangxian/pinkink/ui/mine/AboutUsActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityAboutUsBinding;", "()V", "firmwareViewModel", "Lcom/shangxian/pinkink/mvvm/viewmodel/FirmwareViewModel;", "getFirmwareViewModel", "()Lcom/shangxian/pinkink/mvvm/viewmodel/FirmwareViewModel;", "firmwareViewModel$delegate", "Lkotlin/Lazy;", "initView", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class AboutUsActivity extends BaseActivity<ActivityAboutUsBinding> {

    /* JADX INFO: renamed from: firmwareViewModel$delegate, reason: from kotlin metadata */
    private final Lazy firmwareViewModel = LazyKt.lazy(new Function0<FirmwareViewModel>() { // from class: com.shangxian.pinkink.ui.mine.AboutUsActivity$firmwareViewModel$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final FirmwareViewModel invoke() {
            return (FirmwareViewModel) new ViewModelProvider(this.this$0).get(FirmwareViewModel.class);
        }
    });

    @ViewModel
    private final FirmwareViewModel getFirmwareViewModel() {
        return (FirmwareViewModel) this.firmwareViewModel.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        ActivityAboutUsBinding activityAboutUsBinding = (ActivityAboutUsBinding) getMViewBinding();
        activityAboutUsBinding.titleBar.tvTitle.setText(getString(R.string.title_about));
        activityAboutUsBinding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.mine.AboutUsActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AboutUsActivity.m277initView$lambda4$lambda0(this.f$0, view);
            }
        });
        activityAboutUsBinding.llCheckUpdate.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.mine.AboutUsActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AboutUsActivity.m278initView$lambda4$lambda1(this.f$0, view);
            }
        });
        activityAboutUsBinding.llUserProtocol.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.mine.AboutUsActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AboutUsActivity.m279initView$lambda4$lambda2(this.f$0, view);
            }
        });
        activityAboutUsBinding.llPrivacyProtocol.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.mine.AboutUsActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AboutUsActivity.m280initView$lambda4$lambda3(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-0, reason: not valid java name */
    public static final void m277initView$lambda4$lambda0(AboutUsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-1, reason: not valid java name */
    public static final void m278initView$lambda4$lambda1(AboutUsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AboutUsActivity aboutUsActivity = this$0;
        String string = this$0.getString(R.string.toast_already_latest_version);
        Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.toast_already_latest_version)");
        ContextExtensionsKt.toastShort(aboutUsActivity, string);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-2, reason: not valid java name */
    public static final void m279initView$lambda4$lambda2(AboutUsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CommonWebViewActivity.INSTANCE.gotoThis(this$0, AppConfig.INSTANCE.getUserProtocolUrl() + "?t=" + System.currentTimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-4$lambda-3, reason: not valid java name */
    public static final void m280initView$lambda4$lambda3(AboutUsActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CommonWebViewActivity.INSTANCE.gotoThis(this$0, AppConfig.INSTANCE.getPrivacyAgreementUrl() + "?t=" + System.currentTimeMillis());
    }
}
