package com.lazyee.klib.manager;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LocaleManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J&\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0016\b\u0002\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u000bj\u0004\u0018\u0001`\fJ\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u000f\u001a\u00020\u000eJ\u0006\u0010\u0010\u001a\u00020\u000eR\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/lazyee/klib/manager/LocaleManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "mContext", "changeLocale", "", "locale", "Ljava/util/Locale;", "callback", "Lkotlin/Function0;", "Lcom/lazyee/klib/typed/VoidCallback;", "isEnglish", "", "isSimplifiedChinese", "isTraditionalChinese", "library_release"}, k = 1, mv = {1, 4, 2})
public final class LocaleManager {
    private final Context mContext;

    public LocaleManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.mContext = context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void changeLocale$default(LocaleManager localeManager, Locale locale, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = (Function0) null;
        }
        localeManager.changeLocale(locale, function0);
    }

    public final void changeLocale(Locale locale, Function0<Unit> callback) {
        Intrinsics.checkNotNullParameter(locale, "locale");
        Resources resources = this.mContext.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "resources");
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        if (callback != null) {
            callback.invoke();
        }
    }

    public final boolean isSimplifiedChinese() {
        Resources resources = this.mContext.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "resources");
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= 24) {
            Intrinsics.checkNotNullExpressionValue(configuration, "configuration");
            return Intrinsics.areEqual(configuration.getLocales().get(0), Locale.SIMPLIFIED_CHINESE);
        }
        return Intrinsics.areEqual(configuration.locale, Locale.SIMPLIFIED_CHINESE);
    }

    public final boolean isTraditionalChinese() {
        Resources resources = this.mContext.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "resources");
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= 24) {
            Intrinsics.checkNotNullExpressionValue(configuration, "configuration");
            return Intrinsics.areEqual(configuration.getLocales().get(0), Locale.TRADITIONAL_CHINESE);
        }
        return Intrinsics.areEqual(configuration.locale, Locale.TRADITIONAL_CHINESE);
    }

    public final boolean isEnglish() {
        Resources resources = this.mContext.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "resources");
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= 24) {
            Intrinsics.checkNotNullExpressionValue(configuration, "configuration");
            return Intrinsics.areEqual(configuration.getLocales().get(0), Locale.ENGLISH);
        }
        return Intrinsics.areEqual(configuration.locale, Locale.ENGLISH);
    }
}
