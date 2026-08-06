package com.shangxian.pinkink.ui;

import com.lazyee.klib.extension.ContextExtensionsKt;
import com.shangxian.pinkink.base.BaseActivity;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.databinding.ActivitySplashBinding;
import com.shangxian.pinkink.ui.login.LoginActivity;
import com.shangxian.pinkink.ui.main.MainActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SplashActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"Lcom/shangxian/pinkink/ui/SplashActivity;", "Lcom/shangxian/pinkink/base/BaseActivity;", "Lcom/shangxian/pinkink/databinding/ActivitySplashBinding;", "()V", "initView", "", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    @Override // com.lazyee.klib.base.ViewBindingActivity
    public void initView() {
        super.initView();
        getWindow().getDecorView().postDelayed(new Runnable() { // from class: com.shangxian.pinkink.ui.SplashActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws Exception {
                SplashActivity.m120initView$lambda0(this.f$0);
            }
        }, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initView$lambda-0, reason: not valid java name */
    public static final void m120initView$lambda0(SplashActivity this$0) throws Exception {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (AppConfig.INSTANCE.isLogin()) {
            MainActivity.INSTANCE.gotoThis(this$0);
        } else {
            ContextExtensionsKt.goto$default(this$0, LoginActivity.class, null, null, null, null, 30, null);
        }
        this$0.finish();
    }
}
