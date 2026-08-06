package com.lazyee.klib.extension;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.lazyee.klib.constant.AppConstants;
import com.lazyee.klib.listener.OnKeyboardVisibleListener;
import com.lazyee.klib.util.FileUtils;
import java.io.File;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;

/* JADX INFO: compiled from: ContextExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000¦\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u001a=\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032!\u0010\u0004\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u00020\b0\u00052\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0002\u001a\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002\u001a\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u0001*\u00020\u000e2\b\u0010\t\u001a\u0004\u0018\u00010\n\u001a\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u0001*\u00020\u00102\b\u0010\t\u001a\u0004\u0018\u00010\n\u001a\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u0001*\u00020\u000e2\b\u0010\t\u001a\u0004\u0018\u00010\n\u001a\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u0001*\u00020\u00102\b\u0010\t\u001a\u0004\u0018\u00010\n\u001a\u0012\u0010\u0012\u001a\u00020\u0013*\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\b\u001a\u0014\u0010\u0015\u001a\u00020\u0016*\u00020\u000e2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018\u001a\n\u0010\u0019\u001a\u00020\u001a*\u00020\u000e\u001a\n\u0010\u001b\u001a\u00020\u001a*\u00020\u000e\u001a\n\u0010\u001c\u001a\u00020\u001a*\u00020\u000e\u001a\n\u0010\u001d\u001a\u00020\u001a*\u00020\u000e\u001a\n\u0010\u001e\u001a\u00020\b*\u00020\u000e\u001a\n\u0010\u001f\u001a\u00020\u0018*\u00020\u000e\u001aS\u0010 \u001a\u00020\u0013*\u00020\u000e2\u0012\b\u0002\u0010!\u001a\f\u0012\u0006\b\u0001\u0012\u00020#\u0018\u00010\"2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00182\n\b\u0002\u0010%\u001a\u0004\u0018\u00010&2\n\b\u0002\u0010'\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010(\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010)\u001a\n\u0010*\u001a\u00020\u0013*\u00020\u000e\u001a\u0012\u0010+\u001a\u00020\u0003*\u00020\u000e2\u0006\u0010,\u001a\u00020\b\u001a\n\u0010-\u001a\u00020\u0016*\u00020\u000e\u001a\n\u0010.\u001a\u00020\u0016*\u00020\u000e\u001a\n\u0010/\u001a\u00020\u0016*\u00020\u000e\u001a&\u00100\u001a\u000201*\u00020\u000e2\u0006\u00100\u001a\u00020\u00182\u0006\u00102\u001a\u0002032\n\b\u0002\u00104\u001a\u0004\u0018\u000105\u001a\u0012\u00106\u001a\u00020\b*\u00020\u000e2\u0006\u00107\u001a\u00020\b\u001a\u0014\u00108\u001a\u0004\u0018\u000109*\u00020\u000e2\u0006\u0010:\u001a\u00020\b\u001a\f\u0010;\u001a\u0004\u0018\u00010<*\u00020\u000e\u001a0\u0010=\u001a\u00020\u0013*\u00020\u000e2\u0006\u0010>\u001a\u00020?2\u001c\u0010@\u001a\u0018\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00130\u0005j\b\u0012\u0004\u0012\u00020\u0016`A\u001a(\u0010=\u001a\u00020B*\u00020\u000e2\u001c\u0010@\u001a\u0018\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00130\u0005j\b\u0012\u0004\u0012\u00020\u0016`A\u001a\u0012\u0010C\u001a\u00020\u0013*\u00020\u000e2\u0006\u0010\t\u001a\u00020\u0001\u001a\u0012\u0010C\u001a\u00020\u0013*\u00020\u00102\u0006\u0010\t\u001a\u00020\u0001\u001aI\u0010D\u001a\u00020\u0013*\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\b2!\u0010E\u001a\u001d\u0012\u0013\u0012\u00110F¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(G\u0012\u0004\u0012\u00020\u00130\u00052\b\b\u0002\u0010H\u001a\u00020\u00182\b\b\u0002\u0010I\u001a\u00020\u0018\u001a\u0012\u0010J\u001a\u00020\u0013*\u00020\u000e2\u0006\u0010K\u001a\u00020\b\u001a\u0012\u0010J\u001a\u00020\u0013*\u00020\u000e2\u0006\u0010L\u001a\u00020\u0018\u001a\u0012\u0010M\u001a\u00020\u0013*\u00020\u000e2\u0006\u0010K\u001a\u00020\b\u001a\u0012\u0010M\u001a\u00020\u0013*\u00020\u000e2\u0006\u0010L\u001a\u00020\u0018¨\u0006N"}, d2 = {"createKeyboardGlobalLayoutListener", "Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;", "view", "Landroid/view/View;", "getVisibleHeight", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "", "listener", "Lcom/lazyee/klib/listener/OnKeyboardVisibleListener;", "createListenKeyboardVisiblePopupWindow", "Landroid/widget/PopupWindow;", "context", "Landroid/content/Context;", "addAdjustNothingModeOnKeyBoardVisibleListener", "Landroid/view/Window;", "addOnKeyBoardVisibleListener", "cancelNotification", "", "notificationId", "copy", "", "content", "", "getExternalFsAvailableSize", "", "getExternalFsTotalSize", "getInternalFsAvailableSize", "getInternalFsTotalSize", "getVersionCode", "getVersionName", "goto", "clazz", "Ljava/lang/Class;", "Landroid/app/Activity;", "action", "bundle", "Landroid/os/Bundle;", "flag", "requestCode", "(Landroid/content/Context;Ljava/lang/Class;Ljava/lang/String;Landroid/os/Bundle;Ljava/lang/Integer;Ljava/lang/Integer;)V", "gotoSystemNotificationSetting", "inflate", "layoutId", "isLandscape", "isNetworkAvailable", "isPortrait", "measureText", "", "textSize", "", "typeface", "Landroid/graphics/Typeface;", "ofColor", "colorResId", "ofDrawable", "Landroid/graphics/drawable/Drawable;", "resId", "paste", "", "registerNetworkStateChangedCallback", "lifecycle", "Landroidx/lifecycle/Lifecycle;", "callback", "Lcom/lazyee/klib/typed/TCallback;", "Landroid/content/BroadcastReceiver;", "removeKeyBoardVisibleListener", "showNotification", "buildNotification", "Landroid/app/Notification$Builder;", "builder", "channelId", "channelName", "toastLong", "strResId", NotificationCompat.CATEGORY_MESSAGE, "toastShort", "library_release"}, k = 2, mv = {1, 4, 2})
public final class ContextExtensionsKt {
    public static final int getVersionCode(Context getVersionCode) {
        Intrinsics.checkNotNullParameter(getVersionCode, "$this$getVersionCode");
        return getVersionCode.getPackageManager().getPackageInfo(getVersionCode.getPackageName(), 1).versionCode;
    }

    public static final String getVersionName(Context getVersionName) {
        Intrinsics.checkNotNullParameter(getVersionName, "$this$getVersionName");
        String str = getVersionName.getPackageManager().getPackageInfo(getVersionName.getPackageName(), 1).versionName;
        Intrinsics.checkNotNullExpressionValue(str, "packageInfo.versionName");
        return str;
    }

    public static /* synthetic */ void goto$default(Context context, Class cls, String str, Bundle bundle, Integer num, Integer num2, int i, Object obj) throws Exception {
        if ((i & 1) != 0) {
            cls = (Class) null;
        }
        if ((i & 2) != 0) {
            str = (String) null;
        }
        String str2 = str;
        if ((i & 4) != 0) {
            bundle = (Bundle) null;
        }
        Bundle bundle2 = bundle;
        if ((i & 8) != 0) {
            num = (Integer) null;
        }
        Integer num3 = num;
        if ((i & 16) != 0) {
            num2 = (Integer) null;
        }
        m99goto(context, cls, str2, bundle2, num3, num2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: goto, reason: not valid java name */
    public static final void m99goto(Context context, Class<? extends Activity> cls, String str, Bundle bundle, Integer num, Integer num2) throws Exception {
        Intrinsics.checkNotNullParameter(context, "$this$goto");
        Intent intent = (Intent) null;
        if (cls != null) {
            intent = new Intent(context, cls);
        } else if (!TextUtils.isEmpty(str)) {
            intent = new Intent(str);
        }
        if (intent != null) {
            if (num != null) {
                intent.setFlags(num.intValue());
            }
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            if (context instanceof Activity) {
                if (num2 == null) {
                    context.startActivity(intent);
                    return;
                } else {
                    ((Activity) context).startActivityForResult(intent, num2.intValue());
                    return;
                }
            }
            if (context instanceof Fragment) {
                if (num2 == null) {
                    context.startActivity(intent);
                    return;
                } else {
                    ((Fragment) context).startActivityForResult(intent, num2.intValue());
                    return;
                }
            }
            if (!(context instanceof android.app.Fragment)) {
                throw new Exception("不支持的Context类型");
            }
            if (num2 == null) {
                context.startActivity(intent);
            } else {
                ((android.app.Fragment) context).startActivityForResult(intent, num2.intValue());
            }
        }
    }

    public static final void gotoSystemNotificationSetting(Context gotoSystemNotificationSetting) {
        Intrinsics.checkNotNullParameter(gotoSystemNotificationSetting, "$this$gotoSystemNotificationSetting");
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 26) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            Intrinsics.checkNotNullExpressionValue(intent.putExtra("android.provider.extra.APP_PACKAGE", gotoSystemNotificationSetting.getPackageName()), "intent.putExtra(Settings…APP_PACKAGE, packageName)");
        } else if (Build.VERSION.SDK_INT >= 21) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", gotoSystemNotificationSetting.getPackageName());
            Intrinsics.checkNotNullExpressionValue(intent.putExtra("app_uid", gotoSystemNotificationSetting.getApplicationInfo().uid), "intent.putExtra(\"app_uid\", applicationInfo.uid)");
        } else {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse("package:" + gotoSystemNotificationSetting.getPackageName()));
        }
        gotoSystemNotificationSetting.startActivity(intent);
    }

    public static final View inflate(Context inflate, int i) {
        Intrinsics.checkNotNullParameter(inflate, "$this$inflate");
        View viewInflate = LayoutInflater.from(inflate).inflate(i, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "LayoutInflater.from(this).inflate(layoutId, null)");
        return viewInflate;
    }

    public static /* synthetic */ float[] measureText$default(Context context, String str, float f, Typeface typeface, int i, Object obj) {
        if ((i & 4) != 0) {
            typeface = (Typeface) null;
        }
        return measureText(context, str, f, typeface);
    }

    public static final float[] measureText(Context measureText, String measureText2, float f, Typeface typeface) {
        Intrinsics.checkNotNullParameter(measureText, "$this$measureText");
        Intrinsics.checkNotNullParameter(measureText2, "measureText");
        Paint paint = new Paint();
        if (typeface != null) {
            paint.setTypeface(typeface);
        }
        Resources resources = measureText.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "resources");
        paint.setTextSize(TypedValue.applyDimension(2, f, resources.getDisplayMetrics()));
        return new float[]{paint.measureText(measureText2), paint.getFontMetrics().bottom - paint.getFontMetrics().top};
    }

    public static final boolean copy(Context copy, String str) {
        Intrinsics.checkNotNullParameter(copy, "$this$copy");
        String str2 = str;
        if (TextUtils.isEmpty(str2)) {
            return false;
        }
        Object systemService = copy.getSystemService("clipboard");
        Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ((ClipboardManager) systemService).setPrimaryClip(ClipData.newPlainText("label", str2));
        return true;
    }

    public static final CharSequence paste(Context paste) {
        Intrinsics.checkNotNullParameter(paste, "$this$paste");
        Object systemService = paste.getSystemService("clipboard");
        Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipData primaryClip = ((ClipboardManager) systemService).getPrimaryClip();
        if (primaryClip == null || primaryClip.getItemCount() <= 0) {
            return null;
        }
        return primaryClip.getItemAt(0).coerceToText(paste);
    }

    public static final boolean isLandscape(Context isLandscape) {
        Intrinsics.checkNotNullParameter(isLandscape, "$this$isLandscape");
        Resources resources = isLandscape.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "resources");
        return resources.getConfiguration().orientation == 2;
    }

    public static final boolean isPortrait(Context isPortrait) {
        Intrinsics.checkNotNullParameter(isPortrait, "$this$isPortrait");
        Resources resources = isPortrait.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "resources");
        return resources.getConfiguration().orientation == 1;
    }

    public static final int ofColor(Context ofColor, int i) {
        Intrinsics.checkNotNullParameter(ofColor, "$this$ofColor");
        return ContextCompat.getColor(ofColor, i);
    }

    public static final Drawable ofDrawable(Context ofDrawable, int i) {
        Intrinsics.checkNotNullParameter(ofDrawable, "$this$ofDrawable");
        return ContextCompat.getDrawable(ofDrawable, i);
    }

    public static final void toastShort(Context toastShort, int i) {
        Intrinsics.checkNotNullParameter(toastShort, "$this$toastShort");
        Toast.makeText(toastShort, i, 1).show();
    }

    public static final void toastLong(Context toastLong, int i) {
        Intrinsics.checkNotNullParameter(toastLong, "$this$toastLong");
        Toast.makeText(toastLong, i, 0).show();
    }

    public static final void toastShort(Context toastShort, String msg) {
        Intrinsics.checkNotNullParameter(toastShort, "$this$toastShort");
        Intrinsics.checkNotNullParameter(msg, "msg");
        Toast.makeText(toastShort, msg, 0).show();
    }

    public static final void toastLong(Context toastLong, String msg) {
        Intrinsics.checkNotNullParameter(toastLong, "$this$toastLong");
        Intrinsics.checkNotNullParameter(msg, "msg");
        Toast.makeText(toastLong, msg, 1).show();
    }

    public static final ViewTreeObserver.OnGlobalLayoutListener addOnKeyBoardVisibleListener(Context addOnKeyBoardVisibleListener, OnKeyboardVisibleListener onKeyboardVisibleListener) {
        Intrinsics.checkNotNullParameter(addOnKeyBoardVisibleListener, "$this$addOnKeyBoardVisibleListener");
        if (!(addOnKeyBoardVisibleListener instanceof Activity)) {
            return null;
        }
        Window window = ((Activity) addOnKeyBoardVisibleListener).getWindow();
        Intrinsics.checkNotNullExpressionValue(window, "window");
        return addOnKeyBoardVisibleListener(window, onKeyboardVisibleListener);
    }

    /* JADX INFO: renamed from: com.lazyee.klib.extension.ContextExtensionsKt$addOnKeyBoardVisibleListener$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ContextExtensions.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"getVisibleHeight", "", "view", "Landroid/view/View;", "invoke"}, k = 3, mv = {1, 4, 2})
    static final class C00561 extends Lambda implements Function1<View, Integer> {
        public static final C00561 INSTANCE = new C00561();

        C00561() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Integer invoke(View view) {
            return Integer.valueOf(invoke2(view));
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final int invoke2(View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            Rect rect = new Rect();
            view.getWindowVisibleDisplayFrame(rect);
            return rect.height();
        }
    }

    public static final ViewTreeObserver.OnGlobalLayoutListener addOnKeyBoardVisibleListener(Window addOnKeyBoardVisibleListener, OnKeyboardVisibleListener onKeyboardVisibleListener) {
        Intrinsics.checkNotNullParameter(addOnKeyBoardVisibleListener, "$this$addOnKeyBoardVisibleListener");
        C00561 c00561 = C00561.INSTANCE;
        View decorView = addOnKeyBoardVisibleListener.getDecorView();
        Intrinsics.checkNotNullExpressionValue(decorView, "decorView");
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListenerCreateKeyboardGlobalLayoutListener = createKeyboardGlobalLayoutListener(decorView, ContextExtensionsKt$addOnKeyBoardVisibleListener$keyboardGlobalLayoutListener$1.INSTANCE, onKeyboardVisibleListener);
        View decorView2 = addOnKeyBoardVisibleListener.getDecorView();
        Intrinsics.checkNotNullExpressionValue(decorView2, "decorView");
        decorView2.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListenerCreateKeyboardGlobalLayoutListener);
        return onGlobalLayoutListenerCreateKeyboardGlobalLayoutListener;
    }

    public static final ViewTreeObserver.OnGlobalLayoutListener addAdjustNothingModeOnKeyBoardVisibleListener(Context addAdjustNothingModeOnKeyBoardVisibleListener, OnKeyboardVisibleListener onKeyboardVisibleListener) {
        Intrinsics.checkNotNullParameter(addAdjustNothingModeOnKeyBoardVisibleListener, "$this$addAdjustNothingModeOnKeyBoardVisibleListener");
        if (!(addAdjustNothingModeOnKeyBoardVisibleListener instanceof Activity)) {
            return null;
        }
        AnonymousClass1 anonymousClass1 = AnonymousClass1.INSTANCE;
        final PopupWindow popupWindowCreateListenKeyboardVisiblePopupWindow = createListenKeyboardVisiblePopupWindow(addAdjustNothingModeOnKeyBoardVisibleListener);
        View contentView = popupWindowCreateListenKeyboardVisiblePopupWindow.getContentView();
        Intrinsics.checkNotNullExpressionValue(contentView, "popupWindow.contentView");
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListenerCreateKeyboardGlobalLayoutListener = createKeyboardGlobalLayoutListener(contentView, ContextExtensionsKt$addAdjustNothingModeOnKeyBoardVisibleListener$keyboardGlobalLayoutListener$1.INSTANCE, onKeyboardVisibleListener);
        View contentView2 = popupWindowCreateListenKeyboardVisiblePopupWindow.getContentView();
        Intrinsics.checkNotNullExpressionValue(contentView2, "popupWindow.contentView");
        contentView2.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListenerCreateKeyboardGlobalLayoutListener);
        Window window = ((Activity) addAdjustNothingModeOnKeyBoardVisibleListener).getWindow();
        Intrinsics.checkNotNullExpressionValue(window, "window");
        final View decorView = window.getDecorView();
        decorView.post(new Runnable() { // from class: com.lazyee.klib.extension.ContextExtensionsKt$addAdjustNothingModeOnKeyBoardVisibleListener$$inlined$run$lambda$1
            @Override // java.lang.Runnable
            public final void run() {
                popupWindowCreateListenKeyboardVisiblePopupWindow.showAtLocation(decorView, 0, 0, 0);
            }
        });
        if (addAdjustNothingModeOnKeyBoardVisibleListener instanceof AppCompatActivity) {
            ((AppCompatActivity) addAdjustNothingModeOnKeyBoardVisibleListener).getLifecycle().addObserver(new LifecycleObserver() { // from class: com.lazyee.klib.extension.ContextExtensionsKt.addAdjustNothingModeOnKeyBoardVisibleListener.3
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                public final void onDestroy() {
                    popupWindowCreateListenKeyboardVisiblePopupWindow.dismiss();
                }
            });
        }
        return onGlobalLayoutListenerCreateKeyboardGlobalLayoutListener;
    }

    /* JADX INFO: renamed from: com.lazyee.klib.extension.ContextExtensionsKt$addAdjustNothingModeOnKeyBoardVisibleListener$1, reason: invalid class name */
    /* JADX INFO: compiled from: ContextExtensions.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"getVisibleHeight", "", "view", "Landroid/view/View;", "invoke"}, k = 3, mv = {1, 4, 2})
    static final class AnonymousClass1 extends Lambda implements Function1<View, Integer> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Integer invoke(View view) {
            return Integer.valueOf(invoke2(view));
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final int invoke2(View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            return view.getMeasuredHeight();
        }
    }

    public static final ViewTreeObserver.OnGlobalLayoutListener addAdjustNothingModeOnKeyBoardVisibleListener(Window addAdjustNothingModeOnKeyBoardVisibleListener, OnKeyboardVisibleListener onKeyboardVisibleListener) throws Exception {
        Intrinsics.checkNotNullParameter(addAdjustNothingModeOnKeyBoardVisibleListener, "$this$addAdjustNothingModeOnKeyBoardVisibleListener");
        if (addAdjustNothingModeOnKeyBoardVisibleListener.getAttributes().height != -1) {
            int i = addAdjustNothingModeOnKeyBoardVisibleListener.getAttributes().height;
            View decorView = addAdjustNothingModeOnKeyBoardVisibleListener.getDecorView();
            Intrinsics.checkNotNullExpressionValue(decorView, "decorView");
            Context context = decorView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "decorView.context");
            if (i != AnyExtensionsKt.getScreenHeight(context)) {
                throw new Exception("window height must be full the screen!!!");
            }
        }
        AnonymousClass4 anonymousClass4 = AnonymousClass4.INSTANCE;
        View decorView2 = addAdjustNothingModeOnKeyBoardVisibleListener.getDecorView();
        Intrinsics.checkNotNullExpressionValue(decorView2, "decorView");
        Context context2 = decorView2.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "decorView.context");
        final PopupWindow popupWindowCreateListenKeyboardVisiblePopupWindow = createListenKeyboardVisiblePopupWindow(context2);
        View contentView = popupWindowCreateListenKeyboardVisiblePopupWindow.getContentView();
        Intrinsics.checkNotNullExpressionValue(contentView, "popupWindow.contentView");
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListenerCreateKeyboardGlobalLayoutListener = createKeyboardGlobalLayoutListener(contentView, ContextExtensionsKt$addAdjustNothingModeOnKeyBoardVisibleListener$keyboardGlobalLayoutListener$2.INSTANCE, onKeyboardVisibleListener);
        View contentView2 = popupWindowCreateListenKeyboardVisiblePopupWindow.getContentView();
        Intrinsics.checkNotNullExpressionValue(contentView2, "popupWindow.contentView");
        contentView2.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListenerCreateKeyboardGlobalLayoutListener);
        final View decorView3 = addAdjustNothingModeOnKeyBoardVisibleListener.getDecorView();
        decorView3.post(new Runnable() { // from class: com.lazyee.klib.extension.ContextExtensionsKt$addAdjustNothingModeOnKeyBoardVisibleListener$$inlined$run$lambda$2
            @Override // java.lang.Runnable
            public final void run() {
                popupWindowCreateListenKeyboardVisiblePopupWindow.showAtLocation(decorView3, 0, 0, 0);
            }
        });
        return onGlobalLayoutListenerCreateKeyboardGlobalLayoutListener;
    }

    /* JADX INFO: renamed from: com.lazyee.klib.extension.ContextExtensionsKt$addAdjustNothingModeOnKeyBoardVisibleListener$4, reason: invalid class name */
    /* JADX INFO: compiled from: ContextExtensions.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"getVisibleHeight", "", "view", "Landroid/view/View;", "invoke"}, k = 3, mv = {1, 4, 2})
    static final class AnonymousClass4 extends Lambda implements Function1<View, Integer> {
        public static final AnonymousClass4 INSTANCE = new AnonymousClass4();

        AnonymousClass4() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Integer invoke(View view) {
            return Integer.valueOf(invoke2(view));
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final int invoke2(View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            return view.getMeasuredHeight();
        }
    }

    private static final PopupWindow createListenKeyboardVisiblePopupWindow(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        linearLayout.setBackgroundColor(Color.parseColor("#FF0000"));
        PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setContentView(linearLayout);
        popupWindow.setSoftInputMode(21);
        popupWindow.setInputMethodMode(1);
        popupWindow.setWidth(0);
        popupWindow.setHeight(-1);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        return popupWindow;
    }

    private static final ViewTreeObserver.OnGlobalLayoutListener createKeyboardGlobalLayoutListener(final View view, final Function1<? super View, Integer> function1, final OnKeyboardVisibleListener onKeyboardVisibleListener) {
        final Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = 0;
        return new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.lazyee.klib.extension.ContextExtensionsKt.createKeyboardGlobalLayoutListener.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int iIntValue = ((Number) function1.invoke(view)).intValue();
                if (intRef.element == 0) {
                    intRef.element = iIntValue;
                    return;
                }
                if (intRef.element == iIntValue) {
                    return;
                }
                int i = intRef.element - iIntValue;
                if (i > 200) {
                    AppConstants.INSTANCE.setSOFT_KEYBOARD_HEIGHT(i);
                    OnKeyboardVisibleListener onKeyboardVisibleListener2 = onKeyboardVisibleListener;
                    if (onKeyboardVisibleListener2 != null) {
                        onKeyboardVisibleListener2.onSoftKeyboardShow(i);
                    }
                    intRef.element = iIntValue;
                    return;
                }
                if (iIntValue - intRef.element > 200) {
                    OnKeyboardVisibleListener onKeyboardVisibleListener3 = onKeyboardVisibleListener;
                    if (onKeyboardVisibleListener3 != null) {
                        onKeyboardVisibleListener3.onSoftKeyboardHide();
                    }
                    intRef.element = iIntValue;
                }
            }
        };
    }

    public static final void removeKeyBoardVisibleListener(Context removeKeyBoardVisibleListener, ViewTreeObserver.OnGlobalLayoutListener listener) {
        Intrinsics.checkNotNullParameter(removeKeyBoardVisibleListener, "$this$removeKeyBoardVisibleListener");
        Intrinsics.checkNotNullParameter(listener, "listener");
        if (removeKeyBoardVisibleListener instanceof Activity) {
            Window window = ((Activity) removeKeyBoardVisibleListener).getWindow();
            Intrinsics.checkNotNullExpressionValue(window, "window");
            View decorView = window.getDecorView();
            Intrinsics.checkNotNullExpressionValue(decorView, "window.decorView");
            decorView.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }

    public static final void removeKeyBoardVisibleListener(Window removeKeyBoardVisibleListener, ViewTreeObserver.OnGlobalLayoutListener listener) {
        Intrinsics.checkNotNullParameter(removeKeyBoardVisibleListener, "$this$removeKeyBoardVisibleListener");
        Intrinsics.checkNotNullParameter(listener, "listener");
        View decorView = removeKeyBoardVisibleListener.getDecorView();
        Intrinsics.checkNotNullExpressionValue(decorView, "decorView");
        decorView.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
    }

    public static /* synthetic */ void showNotification$default(Context context, int i, Function1 function1, String str, String str2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            str = "defaultChannelId";
        }
        if ((i2 & 8) != 0) {
            str2 = "defaultChannelName";
        }
        showNotification(context, i, function1, str, str2);
    }

    public static final void showNotification(Context showNotification, int i, Function1<? super Notification.Builder, Unit> buildNotification, String channelId, String channelName) {
        Notification.Builder builder;
        Intrinsics.checkNotNullParameter(showNotification, "$this$showNotification");
        Intrinsics.checkNotNullParameter(buildNotification, "buildNotification");
        Intrinsics.checkNotNullParameter(channelId, "channelId");
        Intrinsics.checkNotNullParameter(channelName, "channelName");
        Object systemService = showNotification.getSystemService("notification");
        Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        NotificationManager notificationManager = (NotificationManager) systemService;
        if (Build.VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName, 2));
            builder = new Notification.Builder(showNotification, channelId);
            builder.setCategory(NotificationCompat.CATEGORY_MESSAGE);
            builder.setAutoCancel(true);
        } else {
            builder = new Notification.Builder(showNotification);
        }
        buildNotification.invoke(builder);
        notificationManager.notify(i, builder.build());
    }

    public static final void cancelNotification(Context cancelNotification, int i) {
        Intrinsics.checkNotNullParameter(cancelNotification, "$this$cancelNotification");
        Object systemService = cancelNotification.getSystemService("notification");
        Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        ((NotificationManager) systemService).cancel(i);
    }

    public static final long getInternalFsTotalSize(Context getInternalFsTotalSize) {
        Intrinsics.checkNotNullParameter(getInternalFsTotalSize, "$this$getInternalFsTotalSize");
        FileUtils fileUtils = FileUtils.INSTANCE;
        File cacheDir = getInternalFsTotalSize.getCacheDir();
        Intrinsics.checkNotNullExpressionValue(cacheDir, "cacheDir");
        String absolutePath = cacheDir.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "cacheDir.absolutePath");
        return fileUtils.getFsTotalSize(absolutePath);
    }

    public static final long getInternalFsAvailableSize(Context getInternalFsAvailableSize) {
        Intrinsics.checkNotNullParameter(getInternalFsAvailableSize, "$this$getInternalFsAvailableSize");
        FileUtils fileUtils = FileUtils.INSTANCE;
        File cacheDir = getInternalFsAvailableSize.getCacheDir();
        Intrinsics.checkNotNullExpressionValue(cacheDir, "cacheDir");
        String absolutePath = cacheDir.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "cacheDir.absolutePath");
        return fileUtils.getFsAvailableSize(absolutePath);
    }

    public static final long getExternalFsTotalSize(Context getExternalFsTotalSize) {
        String absolutePath;
        Intrinsics.checkNotNullParameter(getExternalFsTotalSize, "$this$getExternalFsTotalSize");
        FileUtils fileUtils = FileUtils.INSTANCE;
        File externalCacheDir = getExternalFsTotalSize.getExternalCacheDir();
        if (externalCacheDir == null || (absolutePath = externalCacheDir.getAbsolutePath()) == null) {
            absolutePath = "";
        }
        return fileUtils.getFsTotalSize(absolutePath);
    }

    public static final long getExternalFsAvailableSize(Context getExternalFsAvailableSize) {
        String absolutePath;
        Intrinsics.checkNotNullParameter(getExternalFsAvailableSize, "$this$getExternalFsAvailableSize");
        FileUtils fileUtils = FileUtils.INSTANCE;
        File externalCacheDir = getExternalFsAvailableSize.getExternalCacheDir();
        if (externalCacheDir == null || (absolutePath = externalCacheDir.getAbsolutePath()) == null) {
            absolutePath = "";
        }
        return fileUtils.getFsAvailableSize(absolutePath);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.lazyee.klib.extension.ContextExtensionsKt$registerNetworkStateChangedCallback$broadcastReceiver$1] */
    public static final void registerNetworkStateChangedCallback(final Context registerNetworkStateChangedCallback, Lifecycle lifecycle, final Function1<? super Boolean, Unit> callback) {
        Intrinsics.checkNotNullParameter(registerNetworkStateChangedCallback, "$this$registerNetworkStateChangedCallback");
        Intrinsics.checkNotNullParameter(lifecycle, "lifecycle");
        Intrinsics.checkNotNullParameter(callback, "callback");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        booleanRef.element = isNetworkAvailable(registerNetworkStateChangedCallback);
        final ?? r1 = new BroadcastReceiver() { // from class: com.lazyee.klib.extension.ContextExtensionsKt$registerNetworkStateChangedCallback$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                boolean zIsNetworkAvailable = ContextExtensionsKt.isNetworkAvailable(registerNetworkStateChangedCallback);
                if (zIsNetworkAvailable != booleanRef.element) {
                    booleanRef.element = zIsNetworkAvailable;
                    callback.invoke(Boolean.valueOf(booleanRef.element));
                }
            }
        };
        registerNetworkStateChangedCallback.registerReceiver((BroadcastReceiver) r1, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        lifecycle.addObserver(new LifecycleObserver() { // from class: com.lazyee.klib.extension.ContextExtensionsKt.registerNetworkStateChangedCallback.1
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            public final void onDestroy() {
                registerNetworkStateChangedCallback.unregisterReceiver(r1);
            }
        });
    }

    public static final BroadcastReceiver registerNetworkStateChangedCallback(final Context registerNetworkStateChangedCallback, final Function1<? super Boolean, Unit> callback) {
        Intrinsics.checkNotNullParameter(registerNetworkStateChangedCallback, "$this$registerNetworkStateChangedCallback");
        Intrinsics.checkNotNullParameter(callback, "callback");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        booleanRef.element = isNetworkAvailable(registerNetworkStateChangedCallback);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.lazyee.klib.extension.ContextExtensionsKt$registerNetworkStateChangedCallback$broadcastReceiver$2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                boolean zIsNetworkAvailable = ContextExtensionsKt.isNetworkAvailable(registerNetworkStateChangedCallback);
                if (zIsNetworkAvailable != booleanRef.element) {
                    booleanRef.element = zIsNetworkAvailable;
                    callback.invoke(Boolean.valueOf(booleanRef.element));
                }
            }
        };
        registerNetworkStateChangedCallback.registerReceiver(broadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        return broadcastReceiver;
    }

    public static final boolean isNetworkAvailable(Context isNetworkAvailable) {
        Intrinsics.checkNotNullParameter(isNetworkAvailable, "$this$isNetworkAvailable");
        Object systemService = isNetworkAvailable.getSystemService("connectivity");
        Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) systemService).getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.isAvailable();
        }
        return false;
    }
}
