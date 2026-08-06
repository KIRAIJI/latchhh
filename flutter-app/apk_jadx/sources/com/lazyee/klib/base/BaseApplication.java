package com.lazyee.klib.base;

import android.app.Application;
import android.content.Context;
import com.lazyee.klib.app.AppManager;
import com.lazyee.klib.util.AppUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseApplication.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lcom/lazyee/klib/base/BaseApplication;", "Landroid/app/Application;", "()V", "isMainProcess", "", "onCreate", "", "library_release"}, k = 1, mv = {1, 4, 2})
public class BaseApplication extends Application {
    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        AppManager.INSTANCE.register(this);
    }

    public final boolean isMainProcess() {
        String packageName = getPackageName();
        AppUtils appUtils = AppUtils.INSTANCE;
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        return Intrinsics.areEqual(packageName, appUtils.getCurrentProcessName(applicationContext));
    }
}
