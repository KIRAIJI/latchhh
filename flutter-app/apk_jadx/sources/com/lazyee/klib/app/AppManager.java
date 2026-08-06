package com.lazyee.klib.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0012\n\u0002\u0010\b\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001:\u00010B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0005J\u0016\u0010\u0011\u001a\u00020\u000f2\u000e\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0013J\u000e\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u0016\u001a\u00020\u00172\u000e\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0013J\u000e\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0005J\u0016\u0010\u0018\u001a\u00020\u000f2\u000e\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0013J\u000e\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0019\u001a\u00020\u000fJ\u0016\u0010\u0019\u001a\u00020\u000f2\u000e\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0013J3\u0010\u001a\u001a\u00020\u000f2&\u0010\u001b\u001a\u0014\u0012\u0010\b\u0001\u0012\f\u0012\u0006\b\u0001\u0012\u00020\u0005\u0018\u00010\u00130\u001c\"\f\u0012\u0006\b\u0001\u0012\u00020\u0005\u0018\u00010\u0013¢\u0006\u0002\u0010\u001dJ\b\u0010\u001e\u001a\u0004\u0018\u00010\fJ\u001b\u0010\u001e\u001a\u0004\u0018\u0001H\u001f\"\b\b\u0000\u0010\u001f*\u00020\fH\u0007¢\u0006\u0004\b \u0010!J\b\u0010\"\u001a\u0004\u0018\u00010\u0005J\u001b\u0010\"\u001a\u0004\u0018\u0001H\u001f\"\b\b\u0000\u0010\u001f*\u00020\u0005H\u0007¢\u0006\u0004\b#\u0010\bJ\b\u0010$\u001a\u0004\u0018\u00010\u0005J\u001b\u0010$\u001a\u0004\u0018\u0001H\u001f\"\b\b\u0000\u0010\u001f*\u00020\u0005H\u0007¢\u0006\u0004\b%\u0010\bJ\b\u0010&\u001a\u0004\u0018\u00010\u0005J\u000e\u0010'\u001a\u00020\u000f2\u0006\u0010(\u001a\u00020\fJ\u001f\u0010)\u001a\u00020\u000f2\u0012\u0010\u001b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u001c\"\u00020\u0005¢\u0006\u0002\u0010*J\u001f\u0010+\u001a\u00020\u000f2\u0012\u0010,\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u001c\"\u00020\u0015¢\u0006\u0002\u0010-J\u0006\u0010.\u001a\u00020/R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u00058F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lcom/lazyee/klib/app/AppManager;", "", "()V", "activityList", "", "Landroid/app/Activity;", "last", "getLast", "()Landroid/app/Activity;", "mActivityLifecycleCallbacks", "Lcom/lazyee/klib/app/AppManager$ActivityLifecycleCallbacks;", "mApplication", "Landroid/app/Application;", "mForegroundActivity", "add", "", "activity", "backTo", "clazz", "Ljava/lang/Class;", "activitySimpleName", "", "contains", "", "finish", "finishAll", "finishAllExcept", "activityArr", "", "([Ljava/lang/Class;)V", "getApplication", ExifInterface.GPS_DIRECTION_TRUE, "getTargetApplication", "()Landroid/app/Application;", "getCurrentActivity", "getTargetCurrentActivity", "getForegroundActivity", "getTargetForegroundActivity", "lastActivity", "register", "application", "remove", "([Landroid/app/Activity;)V", "removeByClassName", "classNameArr", "([Ljava/lang/String;)V", "size", "", "ActivityLifecycleCallbacks", "library_release"}, k = 1, mv = {1, 4, 2})
public final class AppManager {
    private static Application mApplication;
    private static Activity mForegroundActivity;
    public static final AppManager INSTANCE = new AppManager();
    private static final ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new ActivityLifecycleCallbacks();
    private static final List<Activity> activityList = new ArrayList();

    private AppManager() {
    }

    public static final /* synthetic */ Activity access$getMForegroundActivity$p(AppManager appManager) {
        return mForegroundActivity;
    }

    public final void register(Application application) {
        Intrinsics.checkNotNullParameter(application, "application");
        mApplication = application;
        application.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    public final <T extends Application> T getTargetApplication() {
        T t = (T) mApplication;
        if (t == null) {
            return null;
        }
        Objects.requireNonNull(t, "null cannot be cast to non-null type T");
        return t;
    }

    public final Application getApplication() {
        return mApplication;
    }

    public final <T extends Activity> T getTargetForegroundActivity() {
        T t = (T) mForegroundActivity;
        if (t == null) {
            return null;
        }
        Objects.requireNonNull(t, "null cannot be cast to non-null type T");
        return t;
    }

    public final Activity getForegroundActivity() {
        return mForegroundActivity;
    }

    public final <T extends Activity> T getTargetCurrentActivity() {
        try {
            return (T) CollectionsKt.lastOrNull((List) activityList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final Activity getCurrentActivity() {
        return (Activity) CollectionsKt.lastOrNull((List) activityList);
    }

    public final int size() {
        return activityList.size();
    }

    public final Activity lastActivity() {
        return (Activity) CollectionsKt.lastOrNull((List) activityList);
    }

    public final Activity getLast() {
        return (Activity) CollectionsKt.lastOrNull((List) activityList);
    }

    public final void add(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        activityList.add(activity);
    }

    public final boolean contains(Class<? extends Activity> clazz) {
        Object next;
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Iterator<T> it = activityList.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (Intrinsics.areEqual(((Activity) next).getClass().getSimpleName(), clazz.getSimpleName())) {
                break;
            }
        }
        return next != null;
    }

    public final void backTo(Class<? extends Activity> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        String simpleName = clazz.getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "clazz.simpleName");
        backTo(simpleName);
    }

    public final void backTo(String activitySimpleName) {
        Intrinsics.checkNotNullParameter(activitySimpleName, "activitySimpleName");
        for (Activity activity : CollectionsKt.reversed(activityList)) {
            if (Intrinsics.areEqual(activity.getClass().getSimpleName(), activitySimpleName)) {
                return;
            } else {
                finish(activity);
            }
        }
    }

    public final void finish(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (!activity.isFinishing() && !activity.isDestroyed()) {
            activity.finish();
        }
        remove(activity);
    }

    public final void finish(Class<? extends Activity> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        String simpleName = clazz.getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "clazz.simpleName");
        finish(simpleName);
    }

    public final void finish(String activitySimpleName) {
        Object next;
        Intrinsics.checkNotNullParameter(activitySimpleName, "activitySimpleName");
        Iterator<T> it = activityList.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (Intrinsics.areEqual(((Activity) next).getClass().getSimpleName(), activitySimpleName)) {
                    break;
                }
            }
        }
        Activity activity = (Activity) next;
        if (activity != null) {
            finish(activity);
        }
    }

    public final void finishAllExcept(Class<? extends Activity>... activityArr) {
        Object obj;
        Intrinsics.checkNotNullParameter(activityArr, "activityArr");
        for (Activity act : new ArrayList(activityList)) {
            int length = activityArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Class<? extends Activity> cls = activityArr[i];
                if (Intrinsics.areEqual(cls != null ? cls.getSimpleName() : null, act.getClass().getSimpleName())) {
                    obj = cls;
                    break;
                }
                i++;
            }
            if (obj == null) {
                Intrinsics.checkNotNullExpressionValue(act, "act");
                finish(act);
            }
        }
    }

    public final void finishAll() {
        finishAllExcept(new Class[0]);
    }

    public final void finishAll(Class<? extends Activity> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        List<Activity> list = activityList;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (Intrinsics.areEqual(((Activity) obj).getClass().getSimpleName(), clazz.getSimpleName())) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return;
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            INSTANCE.finish((Activity) it.next());
        }
    }

    /* JADX INFO: compiled from: AppManager.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\bH\u0016J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0010"}, d2 = {"Lcom/lazyee/klib/app/AppManager$ActivityLifecycleCallbacks;", "Landroid/app/Application$ActivityLifecycleCallbacks;", "()V", "onActivityCreated", "", "activity", "Landroid/app/Activity;", "savedInstanceState", "Landroid/os/Bundle;", "onActivityDestroyed", "onActivityPaused", "onActivityResumed", "onActivitySaveInstanceState", "outState", "onActivityStarted", "onActivityStopped", "library_release"}, k = 1, mv = {1, 4, 2})
    private static final class ActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            Intrinsics.checkNotNullParameter(outState, "outState");
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            AppManager.INSTANCE.add(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            AppManager appManager = AppManager.INSTANCE;
            AppManager.mForegroundActivity = activity;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            if (Intrinsics.areEqual(AppManager.access$getMForegroundActivity$p(AppManager.INSTANCE), activity)) {
                AppManager appManager = AppManager.INSTANCE;
                AppManager.mForegroundActivity = (Activity) null;
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            AppManager.INSTANCE.remove(activity);
        }
    }

    public final void remove(Activity... activityArr) {
        Intrinsics.checkNotNullParameter(activityArr, "activityArr");
        for (Activity activity : activityArr) {
            activityList.remove(activity);
        }
    }

    public final void removeByClassName(String... classNameArr) {
        Intrinsics.checkNotNullParameter(classNameArr, "classNameArr");
        for (final String str : classNameArr) {
            CollectionsKt.removeAll((List) activityList, (Function1) new Function1<Activity, Boolean>() { // from class: com.lazyee.klib.app.AppManager$removeByClassName$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Boolean invoke(Activity activity) {
                    return Boolean.valueOf(invoke2(activity));
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final boolean invoke2(Activity it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Intrinsics.areEqual(it.getClass().getSimpleName(), str);
                }
            });
        }
    }
}
