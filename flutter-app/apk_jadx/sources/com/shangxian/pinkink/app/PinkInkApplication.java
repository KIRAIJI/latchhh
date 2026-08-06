package com.shangxian.pinkink.app;

import android.content.Context;
import com.chad.library.adapter.base.module.LoadMoreModuleConfig;
import com.lazyee.klib.base.BaseApplication;
import com.lazyee.klib.manager.LocaleManager;
import com.lazyee.klib.widget.PageStateSwitcher;
import com.shangxian.pinkink.R;
import com.shangxian.pinkink.api.Api;
import com.shangxian.pinkink.constants.AppConfig;
import com.shangxian.pinkink.widget.loadmore.GlobalLoadMoreView;
import com.tencent.bugly.crashreport.CrashReport;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PinkInkApplication.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u0007"}, d2 = {"Lcom/shangxian/pinkink/app/PinkInkApplication;", "Lcom/lazyee/klib/base/BaseApplication;", "()V", "onCreate", "", "onTerminate", "Companion", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class PinkInkApplication extends BaseApplication {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static Context context;

    /* JADX INFO: compiled from: PinkInkApplication.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\u0004R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\n"}, d2 = {"Lcom/shangxian/pinkink/app/PinkInkApplication$Companion;", "", "()V", "context", "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "getApplicationContext", "app_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Context getContext() {
            return PinkInkApplication.context;
        }

        public final void setContext(Context context) {
            PinkInkApplication.context = context;
        }

        public final Context getApplicationContext() {
            Context context = getContext();
            Intrinsics.checkNotNull(context);
            return context;
        }
    }

    @Override // com.lazyee.klib.base.BaseApplication, android.app.Application
    public void onCreate() {
        super.onCreate();
        PinkInkApplication pinkInkApplication = this;
        context = pinkInkApplication;
        CrashReport.initCrashReport(getApplicationContext(), "24e8eacec1", false);
        AppConfig.INSTANCE.init(pinkInkApplication);
        Api.INSTANCE.init(AppConfig.INSTANCE.getBaseUrl());
        LoadMoreModuleConfig.setDefLoadMoreView(new GlobalLoadMoreView());
        LocaleManager localeManager = new LocaleManager(pinkInkApplication);
        Locale locale = AppConfig.INSTANCE.isEnglishLanguage() ? Locale.ENGLISH : Locale.CHINA;
        Intrinsics.checkNotNullExpressionValue(locale, "if(AppConfig.isEnglishLa…ENGLISH else Locale.CHINA");
        LocaleManager.changeLocale$default(localeManager, locale, null, 2, null);
        PageStateSwitcher.INSTANCE.config(new PageStateSwitcher.LayoutProvider() { // from class: com.shangxian.pinkink.app.PinkInkApplication.onCreate.1
            @Override // com.lazyee.klib.widget.PageStateSwitcher.LayoutProvider
            public Integer getEmptyViewLayoutId() {
                return null;
            }

            @Override // com.lazyee.klib.widget.PageStateSwitcher.LayoutProvider
            public Integer getExceptionViewLayoutId() {
                return null;
            }

            @Override // com.lazyee.klib.widget.PageStateSwitcher.LayoutProvider
            public Integer getLoadingViewLayoutId() {
                return Integer.valueOf(R.layout.layout_page_loading);
            }

            @Override // com.lazyee.klib.widget.PageStateSwitcher.LayoutProvider
            public Integer getNetworkErrorViewLayoutId() {
                return Integer.valueOf(R.layout.layout_error_network);
            }
        });
    }

    @Override // android.app.Application
    public void onTerminate() {
        super.onTerminate();
        context = null;
    }
}
