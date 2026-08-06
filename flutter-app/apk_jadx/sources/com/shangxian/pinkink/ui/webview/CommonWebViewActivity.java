package com.shangxian.pinkink.ui.webview;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.lazyee.klib.extension.LogExtensionsKt;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.constants.Keys;
import com.shangxian.pinkink.databinding.ActivityCommonWebviewBinding;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CommonWebViewActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \f2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\fB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\n\u001a\u00020\u000bH\u0017R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\r"}, d2 = {"Lcom/shangxian/pinkink/ui/webview/CommonWebViewActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivityCommonWebviewBinding;", "()V", "loadUrl", "", "getLoadUrl", "()Ljava/lang/String;", "loadUrl$delegate", "Lkotlin/Lazy;", "initView", "", "Companion", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class CommonWebViewActivity extends BaseActivity<ActivityCommonWebviewBinding> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: renamed from: loadUrl$delegate, reason: from kotlin metadata */
    private final Lazy loadUrl = LazyKt.lazy(new Function0<String>() { // from class: com.shangxian.pinkink.ui.webview.CommonWebViewActivity$loadUrl$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            String stringExtra = this.this$0.getIntent().getStringExtra(Keys.URL);
            return stringExtra == null ? "" : stringExtra;
        }
    });

    /* JADX INFO: compiled from: CommonWebViewActivity.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lcom/shangxian/pinkink/ui/webview/CommonWebViewActivity$Companion;", "", "()V", "gotoThis", "", "context", "Landroid/content/Context;", "url", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void gotoThis(Context context, String url) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(url, "url");
            Intent intent = new Intent(context, (Class<?>) CommonWebViewActivity.class);
            intent.putExtra(Keys.URL, url);
            context.startActivity(intent);
        }
    }

    private final String getLoadUrl() {
        return (String) this.loadUrl.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        LogExtensionsKt.e(this, Intrinsics.stringPlus("url:", getLoadUrl()));
        final ActivityCommonWebviewBinding activityCommonWebviewBinding = (ActivityCommonWebviewBinding) getMViewBinding();
        activityCommonWebviewBinding.titleBar.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.shangxian.pinkink.ui.webview.CommonWebViewActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CommonWebViewActivity.m376initView$lambda1$lambda0(this.f$0, view);
            }
        });
        activityCommonWebviewBinding.wvContent.getSettings().setUseWideViewPort(true);
        activityCommonWebviewBinding.wvContent.getSettings().setJavaScriptEnabled(true);
        activityCommonWebviewBinding.wvContent.setWebChromeClient(new WebChromeClient() { // from class: com.shangxian.pinkink.ui.webview.CommonWebViewActivity$initView$1$2
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                activityCommonWebviewBinding.pbProgress.setProgress(newProgress);
                activityCommonWebviewBinding.pbProgress.setVisibility(newProgress == 100 ? 8 : 0);
            }

            @Override // android.webkit.WebChromeClient
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                activityCommonWebviewBinding.titleBar.tvTitle.setText(title);
            }
        });
        activityCommonWebviewBinding.wvContent.loadUrl(getLoadUrl());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-1$lambda-0, reason: not valid java name */
    public static final void m376initView$lambda1$lambda0(CommonWebViewActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getOnBackPressedDispatcher().onBackPressed();
    }
}
